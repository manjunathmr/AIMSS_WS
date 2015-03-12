package com.passionnambition.model;

import java.util.Date;

public class Notes {
	
	private String id;

	private String note;
	
	private Date dateOfCreation;

	public Notes() {
		dateOfCreation = new Date();
		id = dateOfCreation.toString();
	}

	public String getId() {
		return id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Date getDateOfCreation() {
		return dateOfCreation;
	}

}
