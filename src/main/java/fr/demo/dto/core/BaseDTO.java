package fr.demo.dto.core;

import java.util.Date;

/**
 * Base properties using on the DTO's.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class BaseDTO {
	
	private String reference;
	private String description;
	private Date creationDate;
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
