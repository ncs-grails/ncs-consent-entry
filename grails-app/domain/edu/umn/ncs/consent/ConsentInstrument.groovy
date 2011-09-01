package edu.umn.ncs.consent

import groovy.time.TimeDuration
import edu.umn.ncs.ConsentAgreement

// This represents a particular type of contract/consent/agreement
// Mother / BioRep / Lease
class ConsentInstrument {

	// Short name for the consent
	String name
	// How long is the consent good for TimeDuration
	Integer duration
	// Longer description
	String description
	// The revision number of this consent
	Integer revision = 1

	// Optional EventCode / BatchEvent.id
	// BatchEvent batchEvent
	Integer eventCode
	
	// The Doc Gen configuration that makes these
	// BatchCreationConfig batchCreationConfig
	Integer documentId

	// The path to the source .doc/.docx/.pdf/etc...
	// smb://rifle.cccs.umn.edu/rifle/documents/secret/consent.odt
	URL sourceDocument

	// provenance
	Date dateCreated
	String createdBy
	Date lastUpdated
	
	// Map from the consent itself to all of the signed consents
	static hasMany = [ agreements : ConsentAgreement ]

	String toString() { name }

    static constraints = {
		name(maxSize: 32)
		duration(nullable: true )
		description(maxSize:2000)
		revision()
		eventCode(nullable: true)
		documentId(nullable: true)
		sourceDocument(nullable: true, url:true)
		createdBy(display:false)
		dateCreated(display:false)
        lastUpdated(display:false)
    }
}