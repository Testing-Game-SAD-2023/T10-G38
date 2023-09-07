package org.sad.classUTrepository.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "administrators", catalog = "classut_repo")
@Data
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "NAME", nullable = false, length = 45)
	private String name;
	@Column(name = "SURNAME",nullable = false, length = 45)
	private String surname;
	@Column(name = "EMAIL",nullable = false, length = 100, unique = true)
    private String email;
	@Column(name = "PSW",nullable = false, length = 32767)
	private String psw;
	@OneToMany(mappedBy = "admin", 
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private List<ClassUT> classes;
}
