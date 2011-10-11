package edu.umn.ncs

import edu.umn.ncs.consent.ConsentInstrument
import edu.umn.ncs.Result


class ConsentAgreementOutcomeResponseCodeGroup {

    Integer responseGroup
	
	Boolean accepted = false
	Boolean refused = false
	
	//TO DO: link to Result
	//Result linkedResult
	
	Result linkedResult
	
    static belongsTo = [ consent: ConsentInstrument, 
		outcomeResponseCode: ConsentAgreementOutcomeResponseCode ]
	
	// TODO: Should be:
	// static hasMany = [ outcomeResponseCodes: ConsentAgreementOutcomeResponseCode ]
    
    static constraints = {
		linkedResult(nullable:true)
		Boolean accepted = false
		Boolean refused = false
    }
}
