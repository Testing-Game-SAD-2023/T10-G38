package com.project.ProgettoSad.model;

import jakarta.validation.constraints.NotNull;
/**
*
* Classe relativa allo studente.
* @param	studentId	Identificativo dello studente
*/
public class Student {
	@NotNull
	private String studentId;

	//CONSTRUCTORS
	public Student(String studentId) {
		super();
		this.studentId = studentId;
	}
	
	public Student() {
		super();
	}

	//GETTERSETTER
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	//TOSTRING
	@Override
	public String toString() {
		return studentId;
	}

}
