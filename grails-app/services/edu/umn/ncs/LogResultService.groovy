package edu.umn.ncs
import groovy.sql.Sql;
import org.joda.time.*
import org.codehaus.groovy.grails.plugins.orm.auditable.*

import edu.umn.ncs.ConsentAgreement
import edu.umn.ncs.ItemResult

// Let's us use security annotations
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

@Secured(['ROLE_NCS_IT', 'ROLE_NCS_STRESS'])
class LogResultService {
	def dataSource
	
	def authenticateService
	private static LocalTime midnight = new LocalTime(0,0)
	
	static transactional = true
	
	/*def logResult = { consentAgreementInstance, linkedResult, appName, username ->
		def sql = new Sql(dataSource)
		def procedure
		def dbPrefixName = "StudyData.dbo"
		def errorMessage
		def params
		def now = new LocalDate().toString()
		
		def dead = Result.findByAbbreviation('Dead')
	                    // grab dead result if logged
	    def deadLogged = ItemResult.executeQuery("from ItemResult ir WHERE ir.trackedItem = :sid and ir.result.id = :result ",[sid:consentAgreementInstance.sid, result:dead?.id])
	                    
	    if (deadLogged) {
			// do nothing
	        redirect(action: "find", params:[errorMessage:">Consent logged as dead",id: consentAgreementInstance.id])
	    } 
	        
	    // if exists, then transact ItemResult data
	    def received = Result.findByAbbreviation('Rcvd') // is instrument algety received?
	    //grab received result if logged
	    def resultLogged = ItemResult.executeQuery("from ItemResult ir WHERE ir.trackedItem = :sid and ir.result.id = :result ",[sid:consentAgreementInstance.sid, result:received?.id])
	    def resultCode = resultLogged.result
	
	    // def resultCode = resultLogged.result
	                            
	    if (itemResult) {
	
	        // result of received -> update to appropriate
	        if (itemResult &&  resultLogged) {
	            def transId = -9
	            //transId = storedProcedureService.transact('FirstName', 'Aaron', 'person', 'StudyData', 4121, transId)
	            // transact old result
	            transact('ResultCode', itemResult.result?.id, 'result',  consentAgreementInstance.sid?.id, transId)
	
	            if (debug) {
	                println "Transact:create:transid::::${transId}"
	            }
	
				// update result
	            itemResult.result = outcomeCode
	            itemResult.userUpdated = username
	            itemResult.lastUpdated = now
	
	            if (itemResult.save(flush:true)) {
	                println "Success updating result: ${itemResult.id}"
	            } else {
	                itemResult.errors.each{ e ->
	                    println "itemResult:save:error::${e}"
	                }
	            }
	        }
	                                      
	    }
	    // no result, so create a new one
	    else {
	        // create result
		    if (debug) {
		            println "trackedItemInstance:create:result::::${consentAgreementInstance.sid?.id}"
		    }
	                        
	        def newItemResult = new ItemResult(trackedItem: consentAgreementInstance.sid, 
	                    receivedDate:now,
	                    result:outcomeCode, 
	                    appCreated:appName, 
	                    userCreated:username, 
	                    dateCreated:now,
	                    userUpdated:username,
	                    lastUpdated:now)
	       
	        if (newItemResult.save(flush:true)) {
	            println "Success creating result: ${newItemResult}"
	        } else {
	            newItemResult.errors.each{ e ->
	                println "itemResult:save:error::${e}"
	            }
	        }
		
	} */
	
	// logs itemResult for trackedItem
	def logResult = {trackedItemInstance, responseGroup, receiptDate ->
		
		def message = ''
		def today = new LocalDate()
		def result = null
		def itemResultInstance = null
		
		if (trackedItemInstance){

			// Saving Result
			//if (params.receiptDate && params?.result?.id) {
			
			if (responseGroup) {

				def username = authenticateService.principal().getUsername()
				def appName = "consent-entry"
				def receivedDate = receiptDate 
				
				// get result object associated with selected response group
				result = Result.read(responseGroup?.linkedResult?.id)
				
				println "GMS Debug; result: ${result}"
				println "GMS Debug; trackedItemInstance: ${trackedItemInstance}"
				
				if ( ! receivedDate ) {
					receivedDate = new LocalDate()
				} else {
					// Mon Jan 31 13:38:42 CST 2011
					//receivedDate = Date.parse('EEE MMM d HH:mm:ss z yyyy', receivedDate)
					receivedDate = new LocalDate(receivedDate)
				}

				if (receivedDate.isAfter(today)) {
					trackedItemInstance.errors.rejectValue("receiptDate", "trackedItem.result.receivedDate.receiptedWithFutureDate", [message(code: 'trackedItem.label', default: 'Tracked Item')] as Object[], "Received date must not be greater than todays date.")
						render(view: "edit", model: [ trackedItemInstance: trackedItemInstance])
						return
				}
				
				// converting receivedDate from Joda LocalDate to Java Date
				def javaReceivedDate = receivedDate.toDateTime(midnight).toCalendar().time
				
				if(trackedItemInstance.result) {
					
					itemResultInstance = ItemResult.get(trackedItemInstance?.result?.id)
					
					def jodaOldReceivedDate = new LocalDate(itemResultInstance.receivedDate)
					
					println "NGP Debug; jodaOldReceivedDate: ${jodaOldReceivedDate}"
					println "NGP Debug; receivedDate: ${receivedDate}"
					
					if (jodaOldReceivedDate.isEqual(receivedDate)) {
						println "NGP Debug; Dates are equal;"
					}
					
					if (itemResultInstance.result.id != result.id || itemResultInstance.receivedDate != javaReceivedDate) {
						
						if (itemResultInstance.result.id != result.id) {
							//Log old result
							//def auditLogInstance = AuditLogEvent.findByPersistedObjectId(itemResultInstance.id)
								
							// Log result
							message += auditLog("edu.umn.ncs.ItemResult",
								"UPDATE",
								"edu.umn.ncs.Result:${result.id}",
								"edu.umn.ncs.Result:${itemResultInstance.result.id}",
								itemResultInstance.id,
								"result")

							// Update result
							itemResultInstance.result = result
						}
						
						if (itemResultInstance.receivedDate != javaReceivedDate) {
							
							message += auditLog('edu.umn.ncs.ItemResult',
								'UPDATE',
								javaReceivedDate.toString(),
								"${itemResultInstance.receivedDate.toString()}",
								itemResultInstance.id,
								'receivedDate')
						
							// Update receiveDate
							itemResultInstance.receivedDate = javaReceivedDate
						}
						
						// Transact out userUpdated
						message += auditLog('edu.umn.ncs.ItemResult',
							'UPDATE',
							username,
							itemResultInstance.userUpdated,
							itemResultInstance.id,
							'userUpdated')

						// Update userUpdated
						itemResultInstance.userUpdated = username
						
						if (!itemResultInstance.hasErrors() && itemResultInstance.save(flush: true)) {
							message += "Result for Item ID: ${trackedItemInstance.id} updated. <br/>"
							trackedItemInstance.result = itemResultInstance
						} else {
							message += "Failed updating ItemResult for Item ID: ${trackedItemInstance} ItemResult ID: ${itemResultInstance.id}. "
							itemResultInstance.errors.each{ e ->
								e.fieldErrors.each{fe -> println "! Rejected '${fe.rejectedValues}' for field '${fe.field}'<br/>"}
							}
						}
					}
					println "GMS Debug; jodaOldReceivedDate:!!"
					
				} else {
				
					println "GMS Debug; result: ${result}"
					println "GMS Debug; itemResultInstance: ${itemResultInstance}"
					
					trackedItemInstance.result = new ItemResult(result: result, // need to get 
						userCreated: username,
						appCreated: appName,
						receivedDate: javaReceivedDate,
						trackedItem: trackedItemInstance)
				}
			}
			
			if (!trackedItemInstance.hasErrors() && trackedItemInstance.save(flush: true)) {
				message += "Item ${trackedItemInstance.id} updated successfully!"
				//render(view: "edit", model: [trackedItemInstance: trackedItemInstance, message: message])
			} else {
				message += "Failed updating Item ${trackedItemInstance.id}!"
				//render (view: "edit", model: [trackedItemInstance: trackedItemInstance, message: message])
			}
			
		} else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trackedItem.label', default: 'Tracked Item'), params.id])}"
			//redirect(controller: "batch", action: "list")
		}
	}
	
	def auditLog(className, eventName, newValue, oldValue, persistedObjectId, propertyName){
		
		def message = ""
		def username = authenticateService.principal().getUsername()
		
		def auditLog = new AuditLogEvent(actor: username,
			className: className,
			dateCreated: new Date(),
			eventName: eventName,
			lastUpdated: new Date(),
			newValue: newValue,
			oldValue: oldValue,
			persistedObjectId: persistedObjectId,
			propertyName: propertyName)
		
		if (!auditLog.hasErrors() && auditLog.save(flush: true)) {
			message += "Logged ${propertyName}: ${oldValue} <br/>"
			
		} else {
			message += "Failed to log old ${propertyName}: ${oldValue} <br/>"
			auditLog.errors.each{ e ->
				e.fieldErrors.each{fe -> println "! Rejected '${fe.rejectedValue}' for field '${fe.field}'\n"}
			}
		}
		return message
	}
}

