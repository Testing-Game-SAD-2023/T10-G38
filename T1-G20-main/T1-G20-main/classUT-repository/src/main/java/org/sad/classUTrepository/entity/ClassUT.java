package org.sad.classUTrepository.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "classuts", catalog = "classut_repo")
@Data
public class ClassUT {
		
	
	public static final int LOCATION_LENGHT = 255;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "NAME", nullable = false, length = 45)
	private String name;
	@Column(name = "FILETYPE", nullable = false, length = 45)
	private String type;
	@Column(name = "COMPLEXITY", nullable = false)
	private int complexity;
	@Column(name = "ADDED_DATE", nullable = false)
	private Date added;
	@Column(name = "LAST_UPDATE")
	private Date lastupdate;
	@Column(name = "LOCATION", nullable = false, length = 250)
	private String location;
	@Column(name = "SIZE", nullable = false)
	private long size;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "administrators_idAdmin", nullable = false)
	private Admin admin;
	@OneToMany(mappedBy = "idClassUT")
	private List<Test> tests;
}
 