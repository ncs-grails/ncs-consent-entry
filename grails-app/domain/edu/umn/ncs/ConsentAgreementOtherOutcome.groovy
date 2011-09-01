package edu.umn.ncs

class ConsentAgreementOtherOutcome {
	ConsentAgreementOutcomeResponseCode responseCode
	Date dateCreated = new Date()
	String userCreated
	String appCreated = 'consent'
	Date lastUpdated
	String userUpdated

	static belongsTo = [ consentAgreement:ConsentAgreement ]

	static constraints = {
		dateCreated(display:false)
		lastUpdated(display:false)
		userCreated(maxSize: 16)
		userUpdated(maxSize: 16)
		appCreated(maxSize: 16)
		lastUpdated(display:false)
		userUpdated(display:false)
	}

}