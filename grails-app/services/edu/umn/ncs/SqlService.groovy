package edu.umn.ncs
import groovy.sql.Sql;
import org.joda.time.LocalDate;

class SqlService {

	def dataSource
	
	static transactional = true
	
	def logResult = { consentAgreementInstance, linkedResult, appName, username ->
		def sql = new Sql(dataSource)
		def procedure
		def dbPrefixName = "StudyData.dbo"
		def errorMessage
		def params
		def now = new LocalDate().toString()
		
		/*def dead = Result.findByAbbreviation('Dead')
                                        // grab dead result if logged
                                        def deadLogged = ItemResult.executeQuery("from ItemResult ir WHERE ir.trackedItem = :sid and ir.result.id = :result ",[sid:consentAgreementInstance.sid, result:dead?.id])
                                        
                                        if (deadLogged) {
                                                // do nothing
                                                redirect(action: "find", params:[errorMessage:">Consent logged as dead",id: consentAgreementInstance.id])
                                        } 
                                        
                                        storedProcedureService.logResult(consentAgreementInstance?.sid?.id, responseGroup.linkedResult, appName, username)
                                        //
                                        // moved to service, no longer needed!
                                        
                                        // if exists, then transact ItemResult data
                                        //def received = Result.findByAbbreviation('Rcvd') // is instrument already received?
                                        // grab received result if logged
                                        //def resultLogged = ItemResult.executeQuery("from ItemResult ir WHERE ir.trackedItem = :sid and ir.result.id = :result ",[sid:consentAgreementInstance.sid, result:received?.id])
                                        // def resultCode = resultLogged.result

                                        // def resultCode = resultLogged.result
                                                
                    /*if (itemResult) {

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
                        }*/
		
	}
}

