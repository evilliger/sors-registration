//--------------------------------------//
// Name: HeatSpecDAO	    			//
// Purpose: This class is to provide 	//
//		access to the HeatSpec Datastore.//
//--------------------------------------//
package com.registration.sors.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.User;
@Service
@SuppressWarnings("javadoc")
public class HeatSpecDAO {

	public HeatSpecDAO() {}	
	@Autowired
	private ObjectifyFactory objectifyFactory;

	// Add a HeatSpec to DataStore
	// Parameters: HeatSpec h - HeatSpec to add
	// Return: HeatSpec - newly added HeatSpec
	
	public HeatSpec add(HeatSpec h){
		try {
			h.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(h);
			return h;
			
		} catch (Exception e) {
			return null;
		}
	}
	// Delete a HeatSpec from DataStore
	// Parameters: HeatSpec h - HeatSpec to delete
	// Return: void
	
	public void delete(HeatSpec h){
		Objectify ofy = this.objectifyFactory.begin();
		HeatSpec hs = ofy.get(new Key<HeatSpec>(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId), HeatSpec.class, h.getId()));
		ofy.delete(hs);
	}
	// update a HeatSpec in DataStore
	// Parameters: HeatSpec h - HeatSpec to update
	// Return: HeatSpec - newly updated HeatSpec
	
	public void update(HeatSpec h){
		Objectify ofy = this.objectifyFactory.begin();
		
		HeatSpec hs = ofy.get(new Key<HeatSpec>(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId), HeatSpec.class, h.getId()));

		hs.setEventId(hs.getEventId());
		hs.setGender(hs.getGender());
		hs.setMinAge(hs.getMinAge());
		hs.setMaxAge(hs.getMaxAge());
		hs.setTime(hs.getTime());
		hs.setNumHeats(hs.getNumHeats());
		hs.setMaxInHeat(hs.getMaxInHeat());
		
		ofy.put(hs);
	}
	
	// get a list of HeatSpecs in DataStore
	// Parameters: none
	// Return: list of HeatSpecs
	
	public List<HeatSpec> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<HeatSpec> list = new ArrayList<HeatSpec>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(HeatSpec.class).ancestor(new Key<HeatSpec>(HeatSpec.class,HeatSpec.parentId)).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	// find a heatspec whose classID is id
	// Parameters: id - heatspecID number
	// Return: heatspec - whose heatspecID is id
	
	public HeatSpec find(Long id){
		try {
			Objectify ofy = this.objectifyFactory.begin();
			
			// The following get would have to be run on every classroom id, instead of just "1".
			return ofy.get(new Key<HeatSpec>(new Key<HeatSpec>(HeatSpec.class, 1), HeatSpec.class, id));
		} catch (Exception e) {
			return null;
		}
	}
	
	// For Dev ONLY
	public void init() {
		Objectify ofy = this.objectifyFactory.begin();
		HeatSpec parent = new HeatSpec();
		parent.setId(HeatSpec.parentId);
		ofy.put(parent);
		
		HeatSpec s = new HeatSpec();
		s.setEventId(new Long(122));
		s.setGender("F");
		s.setMaxAge(15);
		s.setMinAge(12);
		s.setMaxInHeat(15);
		s.setNumHeats(1);
		s.setTime(new Date());
		
		s.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
		ofy.put(s);
		
		HeatSpec t = new HeatSpec();
		t.setEventId(new Long(123));
		t.setGender("M");
		t.setMaxAge(15);
		t.setMinAge(12);
		t.setMaxInHeat(15);
		t.setNumHeats(1);
		t.setTime(new Date());
		
		t.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
		ofy.put(t);
		
	}
	public void add(List<HeatSpec> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
