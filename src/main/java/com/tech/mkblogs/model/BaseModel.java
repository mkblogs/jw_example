package com.tech.mkblogs.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BaseModel {

	@Id
	private Integer id;
	
	@CreatedDate
	protected LocalDateTime createdTs;
	@CreatedBy
	protected String createdBy;
	
	@LastModifiedDate
	protected LocalDateTime lastModifiedTs;
	@LastModifiedBy
	protected String lastModifiedBy;
	@Version
	protected Integer version;
	
	BaseModel(Integer id){
		this.id = id;
	}
	
	private String createdName;	
	private String lastModifiedName;
}
