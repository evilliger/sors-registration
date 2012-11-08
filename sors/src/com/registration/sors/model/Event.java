//--------------------------------------//
// Name: Event   						//
// Purpose: This class is to provide 	//
//		an event object with the same   //
// 		attributes as the event entity  //
//		in the datastore.				//
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;
import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Event implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	public static Long parentId = new Long(1);
	

	String name;
	double min;
	double max;
	String units;
	
	@Parent
	Key<Event> parent;

	public Event() {}
	
	public Key<Event> getParent() {
		return parent;
	}

	public void setParent(Key<Event> parent) {
		this.parent = parent;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Event(String n, String su){
		name = n;
		units = su;
	}
}
