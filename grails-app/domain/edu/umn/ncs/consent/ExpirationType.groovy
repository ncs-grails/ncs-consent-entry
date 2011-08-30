package edu.umn.ncs.consent

class ExpirationType {
	String name

	String toString() { name }

    static constraints = {
			name(unique:true, maxSize: 32)
    }
}