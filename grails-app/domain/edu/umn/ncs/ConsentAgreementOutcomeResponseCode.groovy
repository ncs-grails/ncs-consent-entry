package edu.umn.ncs

import edu.umn.ncs.consent.ConsentInstrument
class ConsentAgreementOutcomeResponseCode {
    String name
    //TrackedItem eventClass
    
    String toString() { name }

    static constraints = {
        name(unique:true)
    }

    static mapping = {
        version false
    }
}
