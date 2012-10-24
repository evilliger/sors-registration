package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class addressBook {
	@Id
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
