package edu.umn.ncs.consent

class WitnessType {
	String name

	String toString() { name }

	// Map from the witness itself to all of the signed consents
	static hasMany = [ agreements : ConsentAgreement ]
	
    static constraints = {
			name(unique:true, maxSize: 32)
    }
}