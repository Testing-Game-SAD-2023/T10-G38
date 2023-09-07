package com.project.ProgettoSad.model;

/**
*
* Classe relativa allo studente che partecipa come guest alla partita.
*/
public class Guest extends Student {
	
	//CONSTRUCTORS
	
	public Guest() {
		super();
	}
	
	public Guest(String studentId) {
		super(studentId);
	}

	@Override
	public String toString() {
		return "|["+this.getStudentId()+"]|";
	}
	
	

}
