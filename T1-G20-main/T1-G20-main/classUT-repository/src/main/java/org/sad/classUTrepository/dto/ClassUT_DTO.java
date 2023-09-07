package org.sad.classUTrepository.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ClassUT")
public class ClassUT_DTO {

	@JacksonXmlProperty(isAttribute = true)
	private int id;
	@JacksonXmlProperty(isAttribute = true)
	private String name;
	@JacksonXmlProperty(isAttribute = true)
	private String type;
	@JacksonXmlProperty(isAttribute = true)
	private long size;
	@JacksonXmlProperty(isAttribute = true)
	private int complexity;
	
	//TODO: aggiungere i campi relativi all'amministratore
}
