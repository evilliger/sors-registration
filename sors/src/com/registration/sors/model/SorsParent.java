package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("javadoc")
@Entity
public class SorsParent {
	
	@Id
	Long id;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
