//--------------------------------------//
// Name: Registration   				//
// Purpose: This class is to provide 	//
//		a  Registration object with the //
// 		same attributes as the 			//
//      Registration entity in the 		//
//		datastore.			         	//
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class EventConflict {
	@Id
	Long id;
	public static Long parentId = new Long(-1);
	
	Long e1Id;
	Long e2Id;
	
	@Parent
	Key<EventConflict> parent;
	
	public EventConflict() {}
	
	public EventConflict(Long _e1Id, Long _e2Id) {
		this.e1Id = _e1Id;
		this.e2Id = _e2Id;
	}
	
	public Key<EventConflict> getParent() {
		return parent;
	}

	public void setParent(Key<EventConflict> parent) {
		this.parent = parent;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getE1Id() {
		return e1Id;
	}

	public void setE1Id(Long e1Id) {
		this.e1Id = e1Id;
	}
	
	public Long getE2Id() {
		return e2Id;
	}

	public void setE2Id(Long e2Id) {
		this.e2Id = e2Id;
	}
}
