package com.project.ProgettoSad.model;

import com.project.ProgettoSad.exception.ExceptionResourceNotFound;

import jakarta.validation.constraints.NotNull;
/**
*
* Classe che contiene le informazioni relative alla classe under test.
* @param	classId	L'identificativo della classe scelta per il testing.
* @param	classBody	La directory in cui </td><td> &#232; </td></tr> contenuto il file della classe.
*/
public class ClassUT {
	@NotNull
	private String classId;
	@NotNull
	private String classBody;

	//CONSTRUCTORS
	public ClassUT() {
		super();
	}

	public ClassUT(String classId, String classBody) {
		super();
		this.classId = classId;
		this.classBody = classBody;
	}

	//GETTERSETTER
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassBody() {
		return classBody;
	}

	public void setClassBody(String classBody) {
		this.classBody = classBody;
	}

	//TOSTRING
	@Override
	public String toString() {
		return "ClassUT [classId=" + classId + ", classBody=" + classBody + "]";
	}
}
