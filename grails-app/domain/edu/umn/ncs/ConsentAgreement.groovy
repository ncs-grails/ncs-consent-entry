package edu.umn.ncs

//import edu.umn.ncs.Person
//import edu.umn.ncs.TrackedItem
import edu.umn.ncs.consent.ConsentInstrument
import edu.umn.ncs.consent.WitnessType
import edu.umn.ncs.consent.ExpirationType
import edu.umn.ncs.consent.AgreementType


// Represent that actual contract/agreement/consent between
// a person, and a particular contract

class ConsentAgreement {

	// Date of the agreement
	Date agreementDate
	// Witness name and type
	String witnessName
	WitnessType witnessType
	// Expiration date and type 
	Date expirationDate
	// ExpirationType expirationType -> moved to consentInstrument
	// Type of Agreement
	// AgreementType agreementType -> moved to consentInstrument
	// sid, if this is ties to one...
	TrackedItem trackedItem
	// Person to whom the consent pertains
	Person person
	// Alternate contact involved in the agreement (Simone won)
	// typically this is in the StudyData.dbo.contact_other table
	Integer alternatePersonId
	
	// provenance
	Date createdWhen = new Date()
	String createdByWhom
	Date lastUpdated	
	
	// The type of consent are they agreeing to
	static belongsTo = [ consent : ConsentInstrument]
	
	//static hasMany=[ terms : TermsOfAgreement ]

    static constraints = {
    	agreementDate()
		witnessName(nullable: true)
		witnessType(nullable: true)
		expirationDate(nullable: true)
		trackedItem(nullable: true)
		alternatePersonId(nullable: true)
		createdByWhom(display:false)
		createdWhen(display:false)
        lastUpdated(nullable: true, display:false)
		
    }
}