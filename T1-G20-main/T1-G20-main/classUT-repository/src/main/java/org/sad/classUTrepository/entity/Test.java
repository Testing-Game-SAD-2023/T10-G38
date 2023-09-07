package org.sad.classUTrepository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "test_classes", catalog = "classut_repo")
@Data
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "NAME", nullable = false, length = 45)
	private String name;
	@Column(name = "FILETYPE", nullable = false, length = 45)
	private String filetype;
	@Column(name = "SIZE", nullable = false)
	private long size;
	@Column(name = "PATH", nullable = false, length = 45)
	private String path;
	@Column(name = "TEST_LEVEL", nullable = false, length = 45)
	private String level;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "classUTs_idclassUT")
	private ClassUT idClassUT;

}
