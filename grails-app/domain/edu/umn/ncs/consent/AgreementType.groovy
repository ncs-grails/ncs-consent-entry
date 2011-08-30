package edu.umn.ncs.consent

// The type of agreement
// Signed / Phone / Emailed / Tatooed

class AgreementType {

    String name
    
    
    String toString() { name }

    static constraints = {
        name(unique:true, maxSize: 32)
    }
}