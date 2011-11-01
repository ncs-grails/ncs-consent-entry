package edu.umn.ncs.consent

import edu.umn.ncs.Instrument
import edu.umn.ncs.ConsentAgreement
import groovy.time.TimeDuration

// This represents a particular type of contract/consent/agreement
// Mother / BioRep / Lease
class ConsentInstrument {

	// Short name for the consent
	String name
	// How long is the consent good for; duration
	Integer duration
	// Longer description
	String description
	// The revision number of this consent
	Integer revision = 1

	// Optional EventCode / BatchEvent.id
	//Integer eventCode // StudyData version: change to ->
	// BatchEvent batchEvent
	
	Instrument instrument // need instrumentId
	
	// The Doc Gen configuration that makes these
	// BatchCreationConfig batchCreationConfig
	Integer documentId

	// The path to the source .doc/.docx/.pdf/etc...
	// smb://rifle.cccs.umn.edu/rifle/documents/secret/consent.odt
	URL sourceDocument

	// In previous version this wax in ConsentAgreement: 
	// however, these are actually tied to the consent instrument not an agreement.
	ExpirationType expirationType
	// Type of Agreement
	AgreementType agreementType
	
	ConsentInstrument childInstrument 
	Boolean hasChild
	
	Boolean hasOtherOutcome // has a disposition beyond accept/reject
	
	Boolean enableWitness // requires a witness to sign instrument
	// provenance
	Date dateCreated
	String createdBy
	Date lastUpdated
	
	// Map from the consent itself to all of the signed consents of its type
	static hasMany = [ agreements : ConsentAgreement ]

	String toString() { name }

    static constraints = {
		name(maxSize: 32)
		duration(nullable: true )
		description(maxSize:2000)
		revision()
		instrument(nullable: true)
		expirationType(nullable: true)
		agreementType(nullable: true)
		childInstrument(nullable:true)
		documentId(nullable: true)
		sourceDocument(nullable: true, url:true)
		createdBy(display:false)
		dateCreated(display:false)
        lastUpdated(display:false)
    }
}