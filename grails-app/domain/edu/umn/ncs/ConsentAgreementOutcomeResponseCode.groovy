package edu.umn.ncs

class ConsentAgreementOtherOutcomeResponseCode {
    String name
    TrackedItem eventClass
    
    String toString() { name }

    static constraints = {
        name(unique:true)
    }

    static mapping = {
        version false
    }
}
