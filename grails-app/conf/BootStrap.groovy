import edu.umn.ncs.*
import edu.umn.ncs.consent.*

class BootStrap {
	
    def init = { servletContext ->
		def grailsApplication
		environments {
			development {
	        // This will be done on the MySQL side using an identity seed
	        
	        // Global BootStrap
	
	        // itemId = 0
	        // itemBarcode = "B" + Batch.id
	
	        def today = new Date()
	        def appName = 'ncs-case-management'
	
	        def site = "umn"
				
	        /*** People Seciton ***/
	        /* Items: AddressType, ContactRole, Country, EmailType,
	         * EnrollmentType, Ethnicity, Gender, PhoneType, RelationshipType,
	         * Study */
			
			// Verifying Documents!
			def config = grailsApplication.config.ncs
			BatchCreationDocument.list(sort:'documentLocation').each{ batchCreationDocumentInstance ->
				def fileLocation = "${config.documents}/${batchCreationDocumentInstance.documentLocation}"
				// get the file
				def file = new File(fileLocation)			
				if (! file.exists()) {
					println "# WARNING!!!  The following file is expected by Doc Gen, but missing:"
					println "\thttps://secure.ncs.umn.edu/ncs-case-management/batchCreationConfig/edit/${batchCreationDocumentInstance.batchCreationConfig.id}"
					println "\t${batchCreationDocumentInstance.documentLocation}"
					
				}
			}
	
	        //AddressType
	        def homeAddress = AddressType.findByName('home')
	        if (! homeAddress) {
	            homeAddress = new AddressType(name:'home').save()
	        }
	        def seasonalAddress = AddressType.findByName('seasonal')
	        if (! seasonalAddress) {
	            seasonalAddress = new AddressType(name:'seasonal').save()
	        }
	        def workAddress = AddressType.findByName('work')
	        if (! workAddress) {
	            workAddress = new AddressType(name:'work').save()
	        }
	
	        //ContactRoleType
	        def emergencyContact = ContactRoleType.findByName('emergency')
	        if (! emergencyContact) {
	            emergencyContact = new ContactRoleType(name:'emergency').save()
	        }
	        //Country
	        def us = Country.findByAbbreviation('us')
	        if (! us) {
	            us = new Country(name:'United States', abbreviation:'us').save()
	        }
	        //EmailType
	        def personalEmail = EmailType.findByName('personal')
	        if (! personalEmail) {
	            personalEmail = new EmailType(name:'personal').save()
	        }
	        //EnrollmentType
	        def highIntensity = EnrollmentType.findByName('high intensity')
	        if (! highIntensity) {
	            highIntensity = new EnrollmentType(name:'high intensity').save()
	        }
	        //Ethnicity
	        def latino = Ethnicity.findByName('latino')
	        if (! latino) {
	            latino = new Ethnicity(name:'latino').save()
	        }
	        //Gender
	        def male = Gender.findByName('male')
	        if (! male) {
	            male = new Gender(name:'male').save()
	        }
	        def female = Gender.findByName('female')
	        if (! female) {
	            female = new Gender(name:'female').save()
	        }
	        //PhoneType
	        def mobilePhone = PhoneType.findByName('mobile')
	        if (! mobilePhone) {
	            mobilePhone = new PhoneType(name:'mobile').save()
	        }
	        //RelationshipType
	        def mother = RelationshipType.findByName('mother')
	        if (! mother) {
	            mother = new RelationshipType(name:'mother').save()
	        }
	        //Study
	        /*def ncs = Study.findByName("NCS")
	        if (! ncs) {
	            ncs = new Study(name:"NCS",
	                fullName:"National Children's Study",
	                active:true, sponsor:'NCI/NICHD',
	                coordinator:"NICHD",
	                collaborator:"TBA",
	                purpose:"improving the health of america's children",
	                exclusionary: false)
	            if (!ncs.save()) {
	                ncs.errors.each{
	                    println "${it}"
	                }
	            }
	        }*/
			
			//Study
			def momle = Study.findByName("MOMle")
			if (! momle) {
				momle = new Study(name:"MOMle",
					fullName:"Measurement of Maternal Life Experience",
					active:true, sponsor:'NCI/NICHD',
					coordinator:"NICHD",
					collaborator:"Northwestern University",
					purpose:"improving the health of america's children",
					exclusionary: false)
				if (!momle.save()) {
					momle.errors.each{
						println "${it}"
					}
				}
			}
	
	        /*** Tracking Section ***/
	        /* Items: BatchDirection, InstrumentFormat, IsInitial, Result
	         */
	
	        // BatchDirection
	        def outgoing = BatchDirection.findByName('outgoing')
	        if (! outgoing) {
	            outgoing = new BatchDirection(name:'outgoing').save()
	        }
	        def incoming = BatchDirection.findByName('incoming')
	        if (! incoming) {
	            incoming = new BatchDirection(name:'incoming').save()
	        }
	        def internal = BatchDirection.findByName('internal')
	        if (! internal) {
	            internal = new BatchDirection(name:'internal').save()
	        }
	
	        // MergeDataSource
	        def dwellingUnitData = DataSetType.findByCode("dwelling")
	        if (! dwellingUnitData) {
	            dwellingUnitData = new DataSetType(name:"Dwelling Unit", code:'dwelling').save()
	        }
	
	        def personData = DataSetType.findByCode("person")
	        if (! personData) {
	            personData = new DataSetType(name:"Person", code:'person').save()
	        }
	
			def appointmentData = DataSetType.findByCode("appointment")
			if (! appointmentData) {
				appointmentData = new DataSetType(name:"Appointment Data", code:'appointment').save()
			}
	
	        def customData = DataSetType.findByCode("custom")
	        if (! customData) {
	            customData = new DataSetType(name:"Custom", code:'custom').save()
	        }
	
	        // InstrumentFormat
	        def firstClassMail = InstrumentFormat.findByName('first class mail')
	        if (!firstClassMail) {
	            firstClassMail = new InstrumentFormat(name:'first class mail', groupName:'mail').save()
	        }
	        def inPerson = InstrumentFormat.findByName('in person')
	        if (!inPerson) {
	            inPerson = new InstrumentFormat(name:'in person', groupName:'person').save()
	        }
	        def onThePhone = InstrumentFormat.findByName('phone')
	        if (!onThePhone) {
	            onThePhone = new InstrumentFormat(name:'phone', groupName:'phone').save()
	        }
	
	        // IsInitial
	        def initial = IsInitial.findByName('initial')
	        if (!initial) {
	            initial = new IsInitial(name:'initial').save()
	        }
	        def reminder = IsInitial.findByName('reminder')
	        if (!reminder) {
	            reminder = new IsInitial(name:'reminder').save()
	        }
	
	        // Result
	        def acceptedResult = Result.findByName('accepted')
	        if (!acceptedResult) {
	            acceptedResult = new Result(name:'accepted', abbreviation:'accept').save()
	        }
			
			println "Result:accepted:${acceptedResult}"
			
			def refusedResult = Result.findByName('refused')
			if (!refusedResult) {
				refusedResult = new Result(name:'refused', abbreviation:'refuse').save()
			}
			
			println "refusedResult:${refusedResult}"
			
	        def receivedResult = Result.findByName('received')
	        if (!receivedResult) {
	            receivedResult = new Result(name:'received', abbreviation:'rcvd').save()
	        }
	
	        // Relationship between sub batches
	        // id = 1
	        def attachmentOf = BatchCreationItemRelation.findByName('attachment')
	        if (!attachmentOf) {
	            attachmentOf = new BatchCreationItemRelation(name:'attachment').save()
	        }
	        // id = 2
	        def childOf = BatchCreationItemRelation.findByName('child')
	        if (!childOf) {
	            childOf = new BatchCreationItemRelation(name:'child').save()
	        }
	        // id = 3
	        def sisterOf = BatchCreationItemRelation.findByName('sister')
	        if (!sisterOf) {
	            sisterOf = new BatchCreationItemRelation(name:'sister').save()
	        }
	
	        // Source of the Batch
	        // id = 1
	        def personSource = BatchCreationQueueSource.findByName("person")
	        if (!personSource) {
	            personSource = new BatchCreationQueueSource(name:'person').save()
	        }
	
	        // id = 2
	        def householdSource = BatchCreationQueueSource.findByName("household")
	        if (!householdSource) {
	            householdSource = new BatchCreationQueueSource(name:'household').save()
	        }
	
	        // id = 3
	        def dwellingUnitSource = BatchCreationQueueSource.findByName("dwellingUnit")
	        if (!dwellingUnitSource) {
	            dwellingUnitSource = new BatchCreationQueueSource(name:'dwellingUnit').save()
	        }
	
			// Instrument Revision Types
			def newRevision = InstrumentRevision.findByName("new")
			if (!newRevision) {
				newRevision = new InstrumentRevision(name:'new').save()
			}
	
			def minorRevision = InstrumentRevision.findByName("minor")
			if (!minorRevision) {
				minorRevision = new InstrumentRevision(name:'minor').save()
			}
	
			def majorRevision = InstrumentRevision.findByName("major")
			if (!majorRevision) {
				majorRevision = new InstrumentRevision(name:'major').save()
			}
	
			// Instrument Revision Status
			def developmentRevisionStatus = InstrumentStatus.findByName("development")
			if (!developmentRevisionStatus) {
				developmentRevisionStatus = new InstrumentStatus(name:'development').save()
			}
			def productionRevisionStatus = InstrumentStatus.findByName("production")
			if (!productionRevisionStatus) {
				productionRevisionStatus = new InstrumentStatus(name:'production').save()
			}
	
			// Instrument Approval Types
			def internalApproval = InstrumentApprovalType.findByName("internal")
			if (!internalApproval) {
				internalApproval = new InstrumentApprovalType(name:'internal').save()
			}
	
			def irbApproval = InstrumentApprovalType.findByName("irb")
			if (!irbApproval) {
				irbApproval = new InstrumentApprovalType(name:'irb').save()
			}
	
	
			//
			// APPOINTMENTS
			//
			
			// IncentiveType
			def visaGiftCard = IncentiveType.findByName("Visa Gift Card")
			if (! visaGiftCard) {
				visaGiftCard = new IncentiveType(name: "Visa Gift Card").save()
			}
			
			// AppointmentResultCategory
			def confirmed = AppointmentResultCategory.findByName("Confirmed")
			if ( ! confirmed ) {
				confirmed = new AppointmentResultCategory(name:"Confirmed").save()
			}
			def cancelled = AppointmentResultCategory.findByName("Cancelled")
			if ( ! cancelled ) {
				cancelled = new AppointmentResultCategory(name:"Cancelled").save()
			}
			def superceded = AppointmentResultCategory.findByName("Superceded")
			if ( ! superceded ) {
				superceded = new AppointmentResultCategory(name:"Superceded").save()
			}
	
			
			// AppointmentResult
			def confirmedAppt = AppointmentResult.findByName("Confirmed")
			if ( ! confirmedAppt ) {
				confirmedAppt = new AppointmentResult(name:"Confirmed", category:confirmed , allowBillable: true).save()
			}
			def confirmedApptRetReq = AppointmentResult.findByName("Return Visit Required")
			if ( ! confirmedApptRetReq ) {
				confirmedApptRetReq = new AppointmentResult(name:"Return Visit Required", category:confirmed , allowBillable: true).save()
			}
			def noShow = AppointmentResult.findByName("No Show")
			if ( ! noShow ) {
				noShow = new AppointmentResult(name:"No Show", category:cancelled , allowBillable: false).save()
			}
			def cancelAppt = AppointmentResult.findByName("Cancel")
			if ( ! cancelAppt ) {
				cancelAppt = new AppointmentResult(name:"Cancel", category:cancelled , allowBillable: false).save()
			}
			def reschedRequest = AppointmentResult.findByName("Reschedule Request")
			if ( ! reschedRequest ) {
				reschedRequest = new AppointmentResult(name:"Reschedule Request", category:superceded , allowBillable: false).save()
			}
	
			// AppointmentType
			def highIntensityConsent = AppointmentType.findByName("High Intensity Consent")
			if ( ! highIntensityConsent ) {
				highIntensityConsent = new AppointmentType(name:"High Intensity Consent").save()
			}
			
			// AppointmentLocation
			def atHome = AppointmentLocation.findByName("Subject's Residence")
			if ( ! atHome ) {
				atHome = new AppointmentLocation(name:"Subject's Residence")
				if (! atHome.save() ) {
					println "Failed to save location."
					atHome.errors.each{
						println it
					}
				}
			}
			
			// Events
			def eventSourceList = [
				[ name:"Incoming Call", incoming:true ],
				[ name:"Outgoing Call", incoming:false ],
				[ name:"Mail", incoming:true ],
				[ name:"Other", incoming:true ]
			]
			eventSourceList.each{ obj ->
				def eventSourceInstance = EventSource.findByName(obj.name)
				if (! eventSourceInstance ) {
					eventSourceInstance = new EventSource(name:obj.name, incoming:obj.incoming).save(flush:true)
				}
			}
	
			def eventTypeList = [
				[ name:"Pregnancy",useEventCode:false,useEventDate:true,useEventDescription:false,useEventPickOne:false ],
				[ name:"Birth",useEventCode:false,useEventDate:true,useEventDescription:false,useEventPickOne:false ],
				[ name:"Miscarriage",useEventCode:false,useEventDate:true,useEventDescription:false,useEventPickOne:false ],
				[ name:"Withdrawn from Study",useEventCode:false,useEventDate:false,useEventDescription:false,useEventPickOne:true ],
				[ name:"Reactivate",useEventCode:false,useEventDate:true,useEventDescription:false,useEventPickOne:false ],
				[ name:"Not Interested",useEventCode:false,useEventDate:false,useEventDescription:false,useEventPickOne:true ]
			]
			eventTypeList.each{ obj ->
				def eventTypeInstance = EventType.findByName(obj.name)
				if (! eventTypeInstance ) {
					eventTypeInstance = new EventType(name:obj.name,useEventCode:obj.useEventCode,useEventDate:obj.useEventDate,useEventDescription:obj.useEventDescription,useEventPickOne:obj.useEventPickOne).save(flush:true)
				}
			}
			/*
			def eventPickOneList = [
				[ name:"Refuses-no explanation or other",eventType:EventType.findByName("Not Interested") ],
				[ name:"Hostile or Bad experience with study",eventType:EventType.findByName("Withdrawn from Study") ],
				[ name:"Moving or Moved",eventType:EventType.findByName("Withdrawn from Study") ],
				[ name:"Schedule conflict due to work, frequent traveling, family obligations or transportation problems",eventType:EventType.findByName("Not Interested") ]
			]
			eventPickOneList.each{ obj ->
				def eventPickOneInstance = EventPickOne.findByName(obj.name)
				if (! eventPickOneInstance ) {
					eventPickOneInstance = new EventPickOne(name:obj.name,eventType:obj.eventType).save(flush:true)
				}
			}
			*/
			
	        if (site == "umn") {
	            // Create Address for Screening Center Locations
	            def mac = StreetAddress.findByAddressAndZipCode('200 Oak St SE Ste 350', 55455)
	            if ( ! mac ) {
	                mac = new StreetAddress(address:'200 Oak St SE Ste 350',
	                    city:'Minneapolis',
	                    state:'MN', zipCode:55455, zip4:2008,
	                    county:'Hennepin', country:us,
	                    appCreated:'byHand').save()
	            }
	
	            def mtc = StreetAddress.findByAddressAndZipCode('1100 Washington Ave S Ste 102', 55415)
	            if ( ! mtc ) {
	                mtc = new StreetAddress(address:'1100 Washington Ave S Ste 102',
	                    city:'Minneapolis',
	                    state:'MN', zipCode:55415, zip4:1273,
	                    county:'Hennepin', country:us,
	                    appCreated:'byHand').save()
	            }
	
	            // Create names for tracking document recipients
	            def bstewardRecipient
	
	            def bsteward = Person.findByFirstNameAndLastName('Bonika', 'Steward')
	            if ( ! bsteward ) {
	                bsteward = new Person(title:'Ms',
	                    firstName:'Bonika',
	                    lastName:'Steward',
	                    suffix:null,
	                    gender:female,
	                    alive:true,
	                    isRecruitable:false,
						appCreated:'byHand').save()
	
	                    bstewardRecipient = new TrackingDocumentRecipient(person: bsteward, address:mtc).save()
	            }
				
				
	            def dmd = Person.findByFirstNameAndLastName('Donna', 'DesMarais')
	            def dmdRecipient
	            if ( ! dmd ) {
	                dmd = new Person(title:'Ms',
	                    firstName:'Donna',
	                    lastName:'DesMarais',
	                    suffix:null,
	                    gender:female,
	                    alive:true,
	                    isRecruitable:false,
						appCreated:'byHand').save()
	
	                dmdRecipient = new TrackingDocumentRecipient(person: dmd, address:mac).save()
	            }
	
	            def sd = Person.findByFirstNameAndLastName('Sample', 'Document')
	            def sdRecipient
	            if (!sd) {
	                sd = new Person(title:null,
	                    firstName:'Sample',
	                    lastName:'Document',
	                    suffix:null,
	                    gender:null,
	                    alive:true,
						isRecruitable:false,
	                    appCreated:'byHand').save()
	
	                sdRecipient = new TrackingDocumentRecipient(person: sd, address:mac).save()
	            }
				
				// create some batches
				
				def batchDirectionInstance = BatchDirection.read(1)
				def instrumentFormatInstance = InstrumentFormat.read(1)
				def batchCreationConfigInstance = BatchCreationConfig.read(1)
		
				def now = new Date()
				
				//if (!Instrument.findByName("consent test")){
					
					
					def masterBatch = new Batch(batchRunBy: 'test',
									format: instrumentFormatInstance,
									direction: batchDirectionInstance,
									instrumentDate: now,
									batchRunByWhat: 'test',
									trackingDocumentSent: false,
									creationConfig:batchCreationConfigInstance ).save(flush:true)
									
					def instrument = new Instrument(name: "consent test",
						nickName: "test",
						reportThisEvent: true,
						requiresPrimaryContact: true )
					
					if (instrument.save(flush:true)) {
						println "Success saving instrument: ${instrument.id}"
					} else {
						instrument.errors.each{ e ->
							println "Result:save:error::${e}"
						}
					}
				
					def batchInstrument = new BatchInstrument(instrument: instrument,
						batch: masterBatch,
						isPrimary: true,
						isInitial: initial,
						isResend: false)
		
					if (batchInstrument.save(flush:true)) {
						println "Success saving batchInstrument: ${batchInstrument.id}"
					} else {
						batchInstrument.errors.each{ e ->
							println "Result:save:error::${e}"
						}
					}
					
					def masterTrackedItem = new TrackedItem(batch: masterBatch,
						person: bsteward)
						
					if (masterTrackedItem.save(flush:true)) {
						println "Success saving masterTrackedItem: ${masterTrackedItem.id}"
					} else {
						masterTrackedItem.errors.each{ e ->
							println "Result:save:error::${e}"
						}
					}
				//}
		
					def childTrackedItem = new TrackedItem(batch: masterBatch, 
						parentItem: masterTrackedItem,
						person: bsteward)
						
					if (childTrackedItem.save(flush:true)) {
						println "Success saving childTrackedItem: ${childTrackedItem.id}"
					} else {
						childTrackedItem.errors.each{ e ->
							println "Result:save:error::${e}"
						}
					}
		
				/*def childBatch1 = new Batch(batchRunBy: 'test1',
								format: instrumentFormatInstance,
								direction: batchDirectionInstance,
								instrumentDate: now,
								batchRunByWhat: 'test1',
								trackingDocumentSent: false,
								creationConfig:batchCreationConfigInstance,
								master: masterBatch ).save(flush:true)
		
				def childBatch2 = new Batch(batchRunBy: 'test2',
								format: instrumentFormatInstance,
								direction: batchDirectionInstance,
								instrumentDate: now,
								batchRunByWhat: 'test2',
								trackingDocumentSent: false,
								creationConfig:batchCreationConfigInstance,
								master: masterBatch ).save(flush:true)
		
				def childBatch3 = new Batch(batchRunBy: 'test3',
								format: instrumentFormatInstance,
								direction: batchDirectionInstance,
								instrumentDate: now,
								batchRunByWhat: 'test3',
								trackingDocumentSent: false,
								creationConfig:batchCreationConfigInstance ).save(flush:true) */
				// consent instrumentation:
				
				def transfer = new ConsentAgreementOutcomeResponseCode(name: "Transfer").save(flush:true)
				def separate = new ConsentAgreementOutcomeResponseCode(name: "Separate").save(flush:true)
				def destroy = new ConsentAgreementOutcomeResponseCode(name: "None")
				if (destroy.save(flush:true)) {
					println "Success saving Consent: ${destroy.id}"
				} else {
					destroy.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
				
				def agreement = new AgreementType(name: "Study")
				//def separate = new ConsentAgreementOtherOutcomeResponseCode(name: "Accepted).save(flush:true)
				//def destroy = new ConsentAgreementOtherOutcomeResponseCode(name: "Refused").save(flush:true)
		
				if (agreement.save(flush:true)) {
					println "Success saving AgreementType: ${agreement}"
				} else {
					agreement.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
				
				def witness = new WitnessType(name: "Study staff")
				
				if (witness.save(flush:true)) {
					println "Success saving WitnessType: ${witness}"
				} else {
					witness.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
				
				def expire = new ExpirationType(name: "Up to responder").save(flush:true)
				
				if (expire.save(flush:true)) {
					println "Success saving ExpireType: ${expire}"
				} else {
					expire.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
				
				def consent = new ConsentInstrument(name:"Mother consent",description:"MOM-le study consent",instrument:instrument,documentId: 1,duration: 1,createdBy:"gms",enableWitness:true,expirationType:expire,hasChild:true,hasOtherOutcome:false,agreementType:agreement)
				//def consent2 = new ConsentInstrument(name:"SIT Mother",description:"Study consent",batchInstrument:batchInstrument,documentId: 2,duration: 1,createdBy:"gms",enableWitness:true,expirationType:expire,hasChild:true,hasOtherOutcome:true,agreementType:agreement)
				//def consent3 = new ConsentInstrument(name:"SIT HIPAA",description:"HIPAA consent",batchInstrument:batchInstrument,documentId: 2,duration: 1,createdBy:"gms",enableWitness:true,expirationType:expire,hasChild:true,hasOtherOutcome:true,agreementType:agreement)
				//def consent4 = new ConsentInstrument(name:"PPS4 Kidney",description:"HIPAA consent",batchInstrument:batchInstrument,documentId: 2,duration: 1,createdBy:"gms",enableWitness:true,expirationType:expire,hasChild:true,hasOtherOutcome:true,agreementType:agreement)
				
				if (consent.save(flush:true)) {
					println "Success saving Consent: ${consent.id}"
				} else {
					consent.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
		
				consent?.childInstrument = consent
				
				/*if (consent2.save(flush:true)) {
					println "Success saving Consent: ${consent2.id}"
				} else {
					consent2.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
		
				if (consent3.save(flush:true)) {
					println "Success saving Consent: ${consent3.id}"
				} else {
					consent3.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}
		
				if (consent4.save(flush:true)) {
					println "Success saving Consent: ${consent3.id}"
				} else {
					consent4.errors.each{ e ->
						println "Result:save:error::${e}"
					}
				}*/
				
				def consentEvent = ConsentInstrument.findById(1)
				//def consentEvent2 = ConsentInstrument.findById(2)
				def accept2 = new ConsentAgreementOutcomeResponseCode(name: "Accept Item 1").save(flush:true)
				def refuse2a = new ConsentAgreementOutcomeResponseCode(name: "Refuse Item 2").save(flush:true)
				
				// From the result table
								
				//def consentEvent3 = ConsentInstrument.findById(3)
				//def consentEvent4 = ConsentInstrument.findById(4)
				def accept3 = new ConsentAgreementOutcomeResponseCode(name: "Accept").save(flush:true)
				def refuse3 = new ConsentAgreementOutcomeResponseCode(name: "Refuse").save(flush:true)
						
				// create groups for use in sharing controls
				def group1a = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 1, consent: consentEvent,
					linkedResult: acceptedResult, outcomeResponseCode: transfer).save(flush:true)
				def group1b = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 1, consent: consentEvent,
					linkedResult: refusedResult, outcomeResponseCode: separate).save(flush:true)
				def group1c = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 1, consent: consentEvent,
					linkedResult: refusedResult, outcomeResponseCode: destroy).save(flush:true)
		
				/*def group2a = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 2, consent: consentEvent2,
					linkedResult: acceptedResult, outcomeResponseCode: accept2).save(flush:true)
				def group2b = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 2, consent: consentEvent2,
					linkedResult: refusedResult, outcomeResponseCode: refuse2a).save(flush:true)
				
				def group3a = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 3, consent: consentEvent3,
					linkedResult: acceptedResult, outcomeResponseCode: accept3).save(flush:true)
				def group3b = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 3, consent: consentEvent3,
					linkedResult: refusedResult, outcomeResponseCode: refuse3).save(flush:true)
				def group4a = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 3, consent: consentEvent4,
					linkedResult: acceptedResult, outcomeResponseCode: accept3).save(flush:true)
				def group4b = new ConsentAgreementOutcomeResponseCodeGroup(responseGroup: 3, consent: consentEvent4,
					linkedResult: refusedResult, outcomeResponseCode: refuse3).save(flush:true)*/
	
	  
                /*
                 * 1636 BREDA AVE            |           |          | SAINT PAUL | MN         | 55108 | 2701 |
                 * 4 WYOMING ST E            |           |          | SAINT PAUL | MN         | 55107 | 3240 |
                 * 1372 HAZEL ST N           |           |          | SAINT PAUL | MN         | 55119 | 4507 |
                 * 180 WAYZATA ST            | APT       | 114      | SAINT PAUL | MN         | 55117 | 5351 |
                 * 2122 WOODLYNN AVE         | APT       | 4        | SAINT PAUL | MN         | 55109 | 1480 |
                 * 3744 CLEVELAND AVE N      | APT       | 104      | SAINT PAUL | MN         | 55112 | 3264 |
                 * 1255 FLANDRAU ST          |           |          | SAINT PAUL | MN         | 55106 | 2302 |
                 * 4310 OLD WHITE BEAR AVE N |           |          | SAINT PAUL | MN         | 55110 | 3874 |
                 * 1131 MARION ST            |           |          | SAINT PAUL | MN         | 55117 | 4461 |
                 * 305 EDMUND AVE            |           |          | SAINT PAUL | MN         | 55103 | 1708 |
                 * 1412 COUNTY ROAD E W      |           |          | SAINT PAUL | MN         | 55112 | 3653 |
                 * 1952 OAK KNOLL DR         |           |          | SAINT PAUL | MN         | 55110 | 4263 |
                 * 480 GERANIUM AVE E        |           |          | SAINT PAUL | MN         | 55130 | 3709 |
                 * 1140 4TH ST E             | APT       | 306      | SAINT PAUL | MN         | 55106 | 5353 |
                 * 1793 MORGAN AVE           |           |          | SAINT PAUL | MN         | 55116 | 2721 |
                 * 346 CLEVELAND AVE SW      | APT       | 14       | SAINT PAUL | MN         | 55112 | 3535 |
                 * 1575 SAINT PAUL AVE       | APT       | 9        | SAINT PAUL | MN         | 55116 | 2862 |
                 * 4041 BETHEL DR            | APT       | 27       | SAINT PAUL | MN         | 55112 | 6921 |
                 * 1265 3RD ST E             |           |          | SAINT PAUL | MN         | 55106 | 5778 |
                 * 1528 BREDA AVE            |           |          | SAINT PAUL | MN         | 55108 | 2610 |
                 */
/*
                def myAddressList = [
                    ['1636 BREDA AVE', 'SAINT PAUL', 'MN', '55108', '2701'],
                    ['WYOMING ST E', 'SAINT PAUL', 'MN', '55107', '3240'],
                    ['1372 HAZEL ST N', 'SAINT PAUL', 'MN', '55119', '4507 '],
                    ['180 WAYZATA ST APT 114', 'SAINT PAUL', 'MN', '55117', '5351'],
                    ['2122 WOODLYNN AVE APT 4', 'SAINT PAUL', 'MN', '55109', '1480'],
                    ['3744 CLEVELAND AVE N APT 104', 'SAINT PAUL', 'MN', '55112', '3264'],
                    ['1255 FLANDRAU ST', 'SAINT PAUL', 'MN', '55106', '2302'],
                    ['4310 OLD WHITE BEAR AVE N', 'SAINT PAUL', 'MN', '55110', '3874'],
                    ['1131 MARION ST', 'SAINT PAUL', 'MN', '55117', '4461'],
                    ['305 EDMUND AVE', 'SAINT PAUL', 'MN', '55103', '1708'],
                    ['1412 COUNTY ROAD E W', 'SAINT PAUL', 'MN', '55112', '3653'],
                    ['1952 OAK KNOLL DR', 'SAINT PAUL', 'MN', '55110', '4263'],
                    ['480 GERANIUM AVE E', 'SAINT PAUL', 'MN', '55130', '3709'],
                    ['1140 4TH ST E APT 306', 'SAINT PAUL', 'MN', '55106', '5353'],
                    ['1793 MORGAN AVE', 'SAINT PAUL', 'MN', '55116', '2721'],
                    ['346 CLEVELAND AVE SW APT 14', 'SAINT PAUL', 'MN', '55112', '3535'],
                    ['1575 SAINT PAUL AVE APT 9', 'SAINT PAUL', 'MN', '55116', '2862'],
                    ['4041 BETHEL DR APT 27', 'SAINT PAUL', 'MN', '55112', '6921'],
                    ['1265 3RD ST E','SAINT PAUL', 'MN', '55106', '5778'],
                    ['1528 BREDA AVE','SAINT PAUL', 'MN', '55108', '2610']
                ]

                myAddressList.each{
                    def sa = new StreetAddress(address:it[0],
                        city:it[1], state:it[2], zipCode:it[3], zip4:it[4],
                        country:us, county:'Ramsey', appCreated:'byHand').save()

                    def du = new DwellingUnit(address:sa,
                        appCreated:'byHand').save()

                    //println "Created Dwelling unit: ${du?.id}:${sa?.id}"
                }

                // throw some test data into the database
                def myAddress = new StreetAddress(address:'3323 Buchanan St NE',
                    city:'Minneapolis',
                    state:'MN', zipCode:55418, zip4:1449,
                    county:'Hennepin', country:us,
                    appCreated:'byHand').save()

                def me = new Person(title:'Mr',
                    firstName:'Aaron',
                    middleName:'James',
                    lastName:'Zirbes',
                    suffix:null,
                    birthDate:new Date(79,11,21),
                    gender:male,
                    alive:true,
                    isRecruitable:false,
                    appCreated:'byHand').save()

                def myUnit = new DwellingUnit(address:myAddress,
                    appCreated:'byHand').save()

                def advLtr = new Instrument(name:'Advance Letter',
                    nickName:'AdvLtr', study:ncs, requiresPrimaryContact:true).save()

                def sql = "CALL advance_letter_pilot(CURDATE())";

                def bccAdv = new BatchCreationConfig(name:'Advance Letter',
                    instrument:advLtr, format:firstClassMail, direction: outgoing,
                    isInitial:initial, selectionQuery:sql, active:true, manualSelection:true,
                    oneBatchEventPerson:true).save()

                // add a document
                bccAdv.addToDocuments(
                    documentLocation:'n:/Production Documents/Advance Letter/ncs_advance_letter_merge.docx',
                    mergeSourceFile:'q:/merge_data/advltr_source.txt').save()

                def sourceEnHS = new Source(name:"EnHS", selectable:false).save()

                def bstewardRecipient = TrackingDocumentRecipient.get(1)

                bccAdv.addToRecipients(bstewardRecipient)


                // set up mailing
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2010,10,22), quota: 406)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2010,10,28), quota: 832)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2010,11,05), quota: 1292)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2010,11,12), quota: 1797)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2010,11,19), quota: 2356)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2011,0,2), quota: 2978)
                new MailingSchedule(instrumnet:advLtr, checkpointDate: new Date(2011,0,9), quota: 3669)
                */

            }
            test {

            }
        }

        // add 'capitalize()' function to Strings
        String.metaClass.capitalize = {->
            return delegate.tokenize().collect{ word ->
                word.substring(0,1).toUpperCase() + word.substring(1, word.size())
            }.join(' ')
        }
		
		def env = System.getenv()
		def username = env['USERNAME']
		if (username) {
			println "\nBrowse to https://${env['USERNAME']}.healthstudies.umn.edu:8443/ncs-consent-entry/\n"
		      }
    	}
    }
    def destroy = {
    	println "sleepy time!"
    }
}
