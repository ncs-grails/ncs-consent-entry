package edu.umn.ncs

import groovy.sql.Sql
import edu.umn.ncs.ConsentAgreement
import edu.umn.ncs.ConsentAgreementOtherOutcome
import edu.umn.ncs.ConsentAgreementOutcomeResponseCode
import edu.umn.ncs.ConsentAgreementOutcomeResponseCodeGroup
import edu.umn.ncs.consent.WitnessType
import edu.umn.ncs.BatchInstrument
import edu.umn.ncs.ItemResult
import edu.umn.ncs.TrackedItem
import edu.umn.ncs.Result
import edu.umn.ncs.consent.ConsentInstrument
import org.joda.time.format.DateTimeFormat

// Let's use security annotations
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_NCS_IT', 'ROLE_NCS_STRESS'])
class ConsentAgreementController {
    def dataSource
    static def debug = true
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def appName = 'consent'
    def springSecurityService
	def logResultService
	
	
	def fmt = DateTimeFormat.forPattern("M/d/yyyy");

    def index = {
		
        redirect(action: "find", params: params)
    }
    
    def find = {

        def trackedItemInstance = null

        // If find was passed a number...
        if (params?.id?.isInteger()) {
            def maxTries = 5
            def tries = 0
            // this is an attempt to fix the "find failed the first two times" bug.
            while (!trackedItemInstance && tries < maxTries) {
                if (tries > 1) {
                    // wait a half of a second
                    // import java.lang.Thread ???
                    Thread.sleep(500);
                }
                //trackedItemInstance = TrackedItem.findById(params?.id)
                trackedItemInstance = TrackedItem.get(params?.id)
                tries++
            }
        }
		
		// look up TrackedItem 
		if (trackedItemInstance) {
			// Extract person from TrackedItem
			def personInstance = trackedItemInstance?.person
			
			// Figure out instrument type of TrackedItem
			def instrumentInstance = trackedItemInstance?.batch?.primaryInstrument
			
			// if instrument exists then proceed
			if (instrumentInstance ){
				// look for any ConsentAgreement with the instrument associated with that trackedItem for that person
				def consentAgreementInstance = ConsentAgreement?.createCriteria()?.get{
					trackedItem{
						and{
							person{
								idEq(personInstance?.id)
							}
							batch{
								instruments{
									instrument{
										idEq(instrumentInstance?.id)
									}
								}
							}
						}
					}
					maxResults 1
				}
				// if a ConsentAgreement was not found then go to entry form
				if (!consentAgreementInstance) {
					// we're golden
					println "Go to view!"
					return [ trackedItemInstance: trackedItemInstance,
						instrumentInstance: instrumentInstance,
						consentAgreementInstance: consentAgreementInstance]
				}
				else {
					flash.message = "Tracked Item ID for consent already logged! ${params?.id}"
				}
			}
			else {
				flash.message = "Error!! Ill-formed tracked item!! ${params?.id}"
			}
		} 
		
    }
   
    def create = {
		
		def trackedItemInstance = TrackedItem.get(params.id)
		def instrumentInstance = trackedItemInstance?.batch?.primaryInstrument
		
		// check again if consent entered
		def consentAgreementInstance = ConsentAgreement.findByTrackedItem(trackedItemInstance)
		if (consentAgreementInstance) {
			flash.message = "That Consent has already been entered.  Sorry!"
			redirect(action: 'find')
		} else {
			// needed for display values in create view
	        consentAgreementInstance = new ConsentAgreement()
			consentAgreementInstance.trackedItem = trackedItemInstance
			consentAgreementInstance.person = consentAgreementInstance?.trackedItem?.person 
			
			if (debug) {
				println "consentAgreementInstance.trackedItem:c:::${consentAgreementInstance.trackedItem}"
				println "consentAgreementInstance.person:c:::${consentAgreementInstance.person}"
				println "consentAgreementInstance.params:c:::${consentAgreementInstance.properties}"
			}
	        
			//def batchEventInstance = consentAgreementInstance?.sid?.batch?.primaryBatchEvent
			//consentAgreementInstance.consent = ConsentInstrument.findByEventCode(batchEventInstance?.id)
			
			//def batchEventInstance = consentAgreementInstance?.trackedItem?.batch?.primaryInstrument
		
			// get instrument type and link ConsentAgreement to it so that we can grab the proper outcome disposition codes
			def consentInstrumentInstance = ConsentInstrument.findByInstrument(instrumentInstance)
			consentAgreementInstance.consent = consentInstrumentInstance
			
			if (debug) {
				println "consentInstrumentInstance:c:::${consentInstrumentInstance}"
				println "consentAgreementInstance.consent:c:::${consentAgreementInstance.consent}"
			}
						
			//def consentInstrumentInstance = ConsentInstrument.findByEventCode(batchEventInstance?.id)
			
			// get the disposition codes for display in create view
	        def consentResponseList = ConsentAgreementOutcomeResponseCodeGroup.findAllByConsent(consentInstrumentInstance).outcomeResponseCode

			def consentSecondaryResponseList = null
			// get response list for child consent agreement items if they exist
			if (consentInstrumentInstance?.hasChild){
				def consentInstrumentSecondaryInstance = ConsentInstrument.findByChildInstrument(consentInstrumentInstance?.childInstrument).id
				consentSecondaryResponseList = ConsentAgreementOutcomeResponseCodeGroup.findAllByConsent(consentInstrumentInstance).outcomeResponseCode
				
				if (debug) {
					println "consentSecondaryResponseList::::${consentSecondaryResponseList}"
				}
			}	
						
	        if (debug) {
	            println "consentInstance::::${consentInstrumentInstance}"
	            println "consentResponseList:s::${consentResponseList}"
				println "Returning stuff: Event ${consentAgreementInstance.trackedItem}, Consent Agreement ${consentAgreementInstance}, Response List ${consentResponseList}"
	        }
	
			return [instrumentInstance: instrumentInstance,
	            consentAgreementInstance: consentAgreementInstance,
	            consentResponseList: consentResponseList,
				consentSecondaryResponseList: consentSecondaryResponseList,
				trackedItemInstance: trackedItemInstance ]
		}
    }
		
    def save = {
		def now = new Date()
		def username = springSecurityService.principal.getUsername()
		def consentAgreementInstance = new ConsentAgreement(params)
		def trackedItemInstance = TrackedItem.get(consentAgreementInstance?.trackedItem?.id)
		// set username
		consentAgreementInstance.createdByWhom = username
		
		def receiptDate = params.receiptDate
		
        if (debug) {
			println "username:: ${username}"
            println "ConsentAgreementController:save:consentAgreementInstance.trackedItem::${consentAgreementInstance.trackedItem}"
            println "ConsentAgreementController:save:params::${params}"
        }
		// instance exists so let's process it
        if (consentAgreementInstance) {
			
			// valid response given so process
            if (params.responseCode?.id) { //} && resultLogged){
                
				// determine what the linked result for chosen response group is and use to pass to logResult service
                def responseCode = ConsentAgreementOutcomeResponseCode.findById(params.responseCode?.id)
				def responseGroup = ConsentAgreementOutcomeResponseCodeGroup.findByOutcomeResponseCode(responseCode)
				
				if (debug) {
					println "Transact:create:responseGroup?.linkedResult?.id::: ${responseGroup?.linkedResult?.id}"
					println "Transact:create:params.responseCode?.id ::::${params.responseCode?.id}"
					println "Transact:create:params.responseGroup ::::${responseGroup}"
				    println "params.agreementDate::::${params?.agreementDate}"
                }

				// TODO: get redirect with error message working!
                if (!consentAgreementInstance.agreementDate || consentAgreementInstance.agreementDate > now){
                    //flash.message = "<br>Invalid Agreement Date ${consentAgreementInstance.agreementDate}"
                    //redirect(action: "find", id: consentAgreementInstance.id)
					redirect(action: "find", params:[errorMessage:">Invalid agreement date ${consentAgreementInstance.agreementDate}"])
                }
                else {
					def consent = consentAgreementInstance.consent
					if (debug) {
						println "ConsentAgreementController:saveconsent.id::${consent.id}"
					}

                    consentAgreementInstance.createdWhen = now
					// if instrument type requires a witness, check that it is provided
					if(consent?.enableWitness) {
                        if (!consentAgreementInstance.witnessName){
							//flash.message = "<br>>You must enter the name of the witness"
							//redirect(action: "find", id: consentAgreementInstance.id)
                            redirect(action: "find", params:[errorMessage:">You must enter the name of the witness"],id: consentAgreementInstance.id)
                        }
                    }
                    if (debug) {
                        println "consentAgreementInstancee::::${consentAgreementInstance.properties}"
                    }

                    if (consentAgreementInstance.save(flush:true)) {
                        println "Success saving Consent: ${consentAgreementInstance.id}"
                    } else {
                        consentAgreementInstance.errors.each{ e ->
                            println "consentAgreementInstance:save:error::${e}"
                        }
                    }
                    if (debug) {
                        println "outcomeresponse:create:consentId::::${consent?.id}"
                        println "outcomeresponse:create:params.responseCode?.id ::::${params.responseCode?.id}"
                        println "outcomeresponse:create:params.consentAgreement ::::${consentAgreementInstance}"
                        println "outcomeresponse:create:responseCode::::${responseCode}"
                    }
                    // control for consents that have other outcomes/dispositions that are independent of the trackedItem result
                    if (consent?.hasOtherOutcome) {
                        // log other response
                        def consentAgreementOtherOutcome = new ConsentAgreementOtherOutcome(consentAgreement: consentAgreementInstance,
                            responseCode: responseCode,
                            userCreated: username,
                            userUpdated: username )

                        if (consentAgreementOtherOutcome.save(flush:true)) {
                            println "Success saving other outcome: ${consentAgreementOtherOutcome.id}"
                        } else {
                            consentAgreementOtherOutcome.errors.each{ e ->
                                println "consentAgreementOtherOutcome:save:error::${e}"
                            }
                        }
                    }
					
					// see if result logged as dead
					def dead = Result.findByAbbreviation('Dead')
					def deadLogged = ItemResult?.createCriteria()?.get{
							and{
								trackedItem{
									idEq(consentAgreementInstance.trackedItem?.id)
								}
								result{
									idEq(dead?.id)
								}
							}
							maxResults 1
						}
					// if a ConsentAgreement was not found then go to entry form
					if (deadLogged) { // do nothing
						redirect(action: "find", params:[errorMessage:">Consent logged as dead",id: consentAgreementInstance.id])
					} 
					
					// log trackedItem result
					logResultService.logResult(trackedItemInstance, responseGroup, receiptDate) 
					
					def secondaryResponseCode = null
					def secondaryResponseGroup = null
					
					// determine if there is a child instrument
					def childTrackedItemInstance = TrackedItem?.createCriteria()?.get{
						parentItem{
								idEq(consentAgreementInstance?.trackedItem?.id)
						}
						maxResults 1
					}
					
					if (debug) {
						println "save:childTrackedItemInstance::::${childTrackedItemInstance}"
					}
					
					def childConsentAgreementInstance = null
					
					// if child instrument exists then process it as per the secondaryResponseCode
					if (consent.childInstrument && childTrackedItemInstance) {
						// determine what the linked result for chosen response group is and use to pass to logResult service
						secondaryResponseCode = ConsentAgreementOutcomeResponseCode.findById(params.secondaryResponseCode?.id)
						secondaryResponseGroup = ConsentAgreementOutcomeResponseCodeGroup.findByOutcomeResponseCode(secondaryResponseCode)
						// get linked trackedItem for item 2, and log result for it
						logResultService.logResult(childTrackedItemInstance, secondaryResponseGroup, receiptDate)
						// child ConsentAgreement 
						childConsentAgreementInstance = new ConsentAgreement()
						// copy properties from parent ConsentAgreement
												
						// TODO: if separate dates exist then need to add second completion date to create view
						childConsentAgreementInstance.properties = consentAgreementInstance.properties
						// update to child TrackedItem record
						childConsentAgreementInstance?.trackedItem = childTrackedItemInstance
						
						// multiple dates?
						if (consentAgreementInstance?.consent?.hasOtherAgreementDate) {
							childConsentAgreementInstance?.agreementDate = params.secondaryAgreementDate
								
						}
						
						if (childConsentAgreementInstance.save(flush:true)) {
							println "Success creating result: ${childConsentAgreementInstance}"
						} else {
							childConsentAgreementInstance.errors.each{ e ->
								println "ChildConsentAgreementInstance:save:error::${e}"
							}
						}
					}
					println "Success saving consent!"
                    flash.message = "<br>${consentAgreementInstance?.consent?.name} Result saved as ${consentAgreementInstance?.trackedItem?.result?.name}"
					if (consent.childInstrument && childTrackedItemInstance) {
						flash.message = "<br>${childConsentAgreementInstance?.consent?.name} Result saved as ${childConsentAgreementInstance?.trackedItem?.result?.name}"
					}
                    redirect(action: "find")
                }
            } else {
				flash.message = "<br>Need result for item"
					redirect(action: "find")
			}
        }
    }
	
	def show = {
		def consentAgreementInstance  = ConsentAgreement.get( params.id )

		if(!consentAgreementInstance) {
			flash.message = "Consent not found"
			redirect(action:list)
		}
		else { return [ consentAgreementInstance : consentAgreementInstance ] }
	}
	
	def list = {
		// filter out which types of consents get listed
		def consent = ConsentInstrument.findByName('Mother consent')
		def consentAgreementInstanceList = ConsentAgreement.findAllByConsent(consent)
		
		params.sort = 'agreementDate'
		params.order = 'desc'
		
		if (params.sort) {
				consentAgreementInstanceList = consentAgreementInstanceList.sort{it[params.sort]}
		}
		
		[consentAgreementInstanceList: consentAgreementInstanceList]
	}
	
	def edit = {
		// get desired ConsentAgreement for edit
		def consentAgreementInstance = ConsentAgreement.get(params.id)
		// get trackedItem for ConsentAgreement
		def trackedItemInstance =  consentAgreementInstance?.trackedItem
		// get instrument for ConsentAgreement
		def instrumentInstance = trackedItemInstance?.batch?.primaryInstrument
		// get consent by instrument for ConsentAgreement
		def consentInstrumentInstance = ConsentInstrument.findByInstrument(instrumentInstance)
				
		if (debug) {
			println "consentInstrumentInstance:c:::${consentInstrumentInstance}"
			println "consentAgreementInstance.consent:c:::${consentAgreementInstance.consent}"
			println "consentAgreementInstance?.trackedItem?.result?.result?.id:c:::${consentAgreementInstance?.trackedItem?.result?.result?.id}"
		}
					
		//def consentInstrumentInstance = ConsentInstrument.findByEventCode(batchEventInstance?.id)
		
		// get the disposition codes for display in edit view
		def consentResponseList = ConsentAgreementOutcomeResponseCodeGroup.findAllByConsent(consentInstrumentInstance)
		
		if (debug) {
			println "consentResponseList::::${consentResponseList}"
		}
		
		def outcomeResponse = null
		def secondaryOutcomeResponse = null
		
		// get childInstrument associated with trackedItem for ConsentAgreement
		def childTrackedItemInstance = TrackedItem?.createCriteria()?.get{
			parentItem{
					idEq(consentAgreementInstance?.trackedItem?.id)
			}
			maxResults 1
		}
		
		if (debug) {
			println "childTrackedItemInstance::::${childTrackedItemInstance}"
			println "(params.id): ${params.id}"
		}
		
		def consentSecondaryResponseList = null
		def consentSecondaryAgreementInstance = null
		// get response list and selected disposition from list for child consent agreement items if they exist
		if (consentInstrumentInstance?.hasChild && childTrackedItemInstance){
			// get child ConsentAgreement
			consentSecondaryAgreementInstance = ConsentAgreement.findByTrackedItem(childTrackedItemInstance)
			// get instrument of child ConsentAgreement
			def consentInstrumentSecondaryInstance = ConsentInstrument.findByChildInstrument(consentInstrumentInstance?.childInstrument)
			
			if (debug) {
				println "consentSecondaryAgreementInstance::::${consentSecondaryAgreementInstance}"
				println "consentInstrumentSecondaryInstance::::${consentInstrumentSecondaryInstance}"
			}
							
			// get response list and selected disposition from logged trackedItem result of child ConsentAgreement
			consentSecondaryResponseList = ConsentAgreementOutcomeResponseCodeGroup.findAllByConsent(consentInstrumentSecondaryInstance)
			secondaryOutcomeResponse = consentSecondaryResponseList.find{it.linkedResult.id == consentSecondaryAgreementInstance?.trackedItem?.result?.result?.id}
			
			if (debug) {
				println "consentSecondaryResponseList::::${consentSecondaryResponseList}"
				println "secondaryOutComeResponse::::${secondaryOutcomeResponse}"
			}
		}
		
		if (!consentAgreementInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'consentAgreement.label', default: 'ConsentAgreement'), params.id])}"
			redirect(action: "list")
		}
		else {
			// get response list and selected disposition from logged trackedItem result of ConsentAgreement
			outcomeResponse = consentResponseList.find{it.linkedResult.id == consentAgreementInstance?.trackedItem?.result?.result?.id}
			if (debug) {
				println "outcomeResponse::::${outcomeResponse}"
			}
			return [consentAgreementInstance: consentAgreementInstance,
					consentResponseList: consentResponseList, 
					consentSecondaryResponseList: consentSecondaryResponseList,
					outcomeResponse: outcomeResponse,
					secondaryOutcomeResponse: secondaryOutcomeResponse]
		}
	}
	
	def update = {
        def consentAgreementInstance = ConsentAgreement.get(params.id)
		def trackedItemInstance = TrackedItem.get(consentAgreementInstance?.trackedItem?.id)

        //def username = springSecurityService.principal.username
        //consentAgreementInstance.userUpdated = username
        // I think this is optional
        //consentAgreementInstance.lastUpdated = new Date()

        if (consentAgreementInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (consentAgreementInstance.version > version) {

                    consentAgreementInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'consentAgreement.label', default: 'consentAgreement')] as Object[], "Another user has updated this consentAgreement while you were editing")
                    render(view: "edit", model: [consentAgreementInstance: consentAgreementInstance])
                    return
                }
            }
            consentAgreementInstance.properties = params
            if (!consentAgreementInstance.hasErrors() && consentAgreementInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'consentAgreement.label', default: 'consentAgreement'), consentAgreementInstance.id])}"
                redirect(action: "edit", id: consentAgreementInstance.id)
            }
            else {
                render(view: "edit", model: [consentAgreementInstance: consentAgreementInstance])
            }
			
			// determine what the linked result for chosen response group is and use to pass to logResult service
			def responseCode = ConsentAgreementOutcomeResponseCode.findById(params.responseCode?.id)
			def responseGroup = ConsentAgreementOutcomeResponseCodeGroup.findByOutcomeResponseCode(params.responseCode?.id)
			
			// log trackedItem result
			logResultService.logResult(trackedItemInstance, responseGroup, receiptDate)
			
			// update child if exists
			def consent = consentAgreementInstance.consent
			
			def secondaryResponseCode = null
			def secondaryResponseGroup = null
			
			// determine if there is a child instrument
			def childTrackedItemInstance = TrackedItem?.createCriteria()?.get{
				parentItem{
						idEq(consentAgreementInstance?.trackedItem?.id)
				}
				maxResults 1
			}
			
			if (debug) {
				println "save:childTrackedItemInstance::::${childTrackedItemInstance}"
			}
			
			// if child instrument exists then process it as per the secondaryResponseCode
			if (consent.childInstrument && childTrackedItemInstance) {
				// determine what the linked result for chosen response group is and use to pass to logResult service
				secondaryResponseCode = ConsentAgreementOutcomeResponseCode.findById(secondaryOutcomeResponse)
				secondaryResponseGroup = ConsentAgreementOutcomeResponseCodeGroup.findByOutcomeResponseCode(params.secondaryResponseCode?.id)
				// get linked trackedItem for item 2, and log result for it
				logResultService.logResult(childTrackedItemInstance, secondaryResponseGroup, receiptDate)
				// child ConsentAgreement
				def childConsentAgreementInstance = ConsentAgreement()
				// copy properties from parent ConsentAgreement
				// TODO: if separate dates exist then need to add second completion date to create view
				childConsentAgreementInstance.properties = consentAgreementInstance.properties
				// update to child TrackedItem record
				childConsentAgreementInstance?.trackedItem = childTrackedItemInstance
				
				// multiple dates?
				if (consentInstrumentInstance?.hasOtherAgreementDate) {
					childConsentAgreementInstance?.agreementDate = params.secondaryAgreementDate
						
				}
				
				if (childConsentAgreementInstance.save(flush:true)) {
					println "Success creating result: ${childConsentAgreementInstance}"
				} else {
					childConsentAgreementInstance.errors.each{ e ->
						println "ChildConsentAgreementInstance:save:error::${e}"
					}
				}
			}
			
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'consentAgreement.label', default: 'consentAgreement'), params.id])}"
            redirect(action: "find")
        }
    }
   
}

