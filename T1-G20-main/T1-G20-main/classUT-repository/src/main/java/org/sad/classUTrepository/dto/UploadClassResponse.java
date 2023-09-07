package org.sad.classUTrepository.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadClassResponse {
	
	private String fileName;
	private String fileDownloadURI;
	private String fileType;
	private long size;
	private String notes;

}
