//--------------------------------------------//
// Name: MaintainHeatsHandler				  //
// Purpose: The purpose of this class is to   //
// 		provide the functionality for the 	  //
//		maintain heats use case.			  //
//--------------------------------------------//
package com.registration.sors.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.registration.sors.model.Athlete;
import com.registration.sors.model.Event;
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatEntry;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.HeatDAO;
import com.registration.sors.service.HeatSpecDAO;
import com.registration.sors.service.RegistrationDAO;

public class MaintainHeatsHandler {
	
	private static RegistrationDAO regDAO;
	private static AthleteDAO athDAO;
	private static EventDAO eventDAO;
	private static HeatSpecDAO heatSpecDAO;
	private static HeatDAO heatDAO;
	
	private Dictionary<String,Athlete>aList;

	
	
	public MaintainHeatsHandler(HeatDAO heatDao2, RegistrationDAO regDao2, AthleteDAO athDao2, EventDAO eventDao2, HeatSpecDAO heatSpecDao2){
		regDAO = regDao2;
		athDAO = athDao2;
		eventDAO = eventDao2;
		heatSpecDAO = heatSpecDao2;
		heatDAO = heatDao2;
	}
	
	private  int findBdate(Date bday){
		//calculate the birthday....
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();
	    int age = 0;
	    int factor = 0; 
	    cal1.setTime(bday);
	    cal2.setTime(new Date());
	    if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
	          factor = -1; 
	    }
	    age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		return age;
	}
	
	private  Dictionary<String,Event> getEventDictionary(){
		Dictionary<String,Event>eventList = new Hashtable<String,Event>();
		List<Event>eList = eventDAO.loadAll();
		for(int i = 0; i < eList.size(); ++i){
			eventList.put(eList.get(i).getId().toString(), eList.get(i));		
		}
		return eventList;
	}
	
	public  Dictionary<String,List<Registration>> getRegHeatDictionary(){
		Dictionary<String,List<Registration>> regList = new Hashtable<String,List<Registration>>();
		List<Registration>rList = regDAO.loadAll();
		for(int i = 0; i < rList.size(); ++i){
			Registration r = rList.get(i);
			List<Registration> temp = regList.get(r.getHeatID().toString());
			if(temp == null){
				temp = new ArrayList<Registration>();
			}
			temp.add(r);
			regList.put(r.getHeatID().toString(), temp);
		}
		return regList;
	
	}
	public  Dictionary<String,Registration> getRegDictionary(){
		Dictionary<String,Registration> regList = new Hashtable<String,Registration>();
		List<Registration>rList = regDAO.loadAll();
		for(int i = 0; i < rList.size(); ++i){
			Registration r = rList.get(i);
			regList.put(r.getId().toString(), r);
		}
		return regList;
	
	}
	public  Dictionary<String,Athlete> getAthleteDictionary(){
		Dictionary<String,Athlete>athleteCodes = new Hashtable<String,Athlete>();
		
		List<Athlete>aList = athDAO.loadAll();
		//aList.remove(0);
		// turn athlete list into dictionary
		for(int i = 0; i < aList.size(); ++i){
			Athlete a = aList.get(i);
			athleteCodes.put(Long.toString(a.getId()), a);
		}
		return athleteCodes;
	}
	
	
	
	// Turn a List of HeatSpec into a dictionary
	// parameters: heatSpecList - the list to turn into dictionary
	// return: a dictionary created from parameter
	private  Dictionary<String, List<HeatSpec>> getHeatSpecDictionary(){
		List<HeatSpec>heatSpecList = heatSpecDAO.loadAll();
		
		Dictionary<String,List<HeatSpec>> specList = new Hashtable<String,List<HeatSpec>>();
		for(int i = 0; i < heatSpecList.size(); ++i){
			HeatSpec h = heatSpecList.get(i);
			List<HeatSpec> temp = specList.get(h.getEventId().toString());
			if(temp == null){
				temp = new ArrayList<HeatSpec>();
			}
			temp.add(h);
			specList.put(h.getEventId().toString(), temp);
		}
		return specList;
	}
	

	private  List<Registration> sortRegistrations(List<Registration> rList){
		Collections.sort(rList, new Comparator<Registration>() {
		    public int compare(Registration r1, Registration r2) {
		    	if(r1.getScore() <= r2.getScore()){
		    		return 1;
		    	}else{
		    		return -1;
		    	}
		    }
		});
		return rList;
	}
	
	
	
	
	public String ToString(){
		Dictionary<String,Athlete>athList = getAthleteDictionary();
		Dictionary<String,Event>eventList = getEventDictionary(); 
		Dictionary<String,List<Registration>>regList = getRegHeatDictionary();
		List<Heat>heatList = heatDAO.loadAll();
		String html = "";
		
		for(int i = 0; i < heatList.size(); ++i){
			Heat h = heatList.get(i);
			html += "<table style=\"text-align:left;\"><tr>" 
			+ " <th>" + eventList.get(h.getEventID().toString()).getName() + "</th>"
			+ "<th>Group " + Integer.toString(h.getMinAge()) + " to " + Integer.toString(h.getMaxAge()) + "</th>"
			+ "<th>Time " + h.getTime().toString() + "</th>"
			+ "<th>Gender: " + h.getGender() + "</th>"
			+ "</tr>";
			
			html += "<tr>"
			+ "<th>Name</th>"
			+ "<th>Age</th>"
			+ "<th>Sex</th>"
			+ "<th>Rank</th>"
			+ "<th>Score</th>"
			+ "<th>Div</th></tr>";
			
			List<Registration>rList = regList.get(h.getId().toString());
			
			for(int j = 0; j < rList.size(); ++j){
				Registration r = rList.get(j);
				Athlete a = athList.get(r.getAthleteID().toString());
				html += "<tr><td>" + a.getFname() + " " + a.getLname() + "</td>"
						+ "<td>" + findBdate(a.getBdate()) + "</td>"
						+ "<td>" + a.getGender() + "</td>"
						+ "<td>" + r.getRank() + "</td>"
						+ "<td>" + r.getScore() + "</td>"
						+ "<td>" + h.getDivision() + "</td></tr>";
			
			}
			html += "</table>";		
		}
		return html;
	}
	public Dictionary<String,List<Registration>> getHeatSpec_RegDictionary(){
		Dictionary<String,List<Registration>> list = new Hashtable<String,List<Registration>>();
		
		List<Registration> regList = regDAO.loadAll();
			
		// key is event id
		Dictionary<String,List<HeatSpec>> specList = getHeatSpecDictionary();
		
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			Athlete a = aList.get(r.getAthleteID().toString());
			
			int age = findBdate(a.getBdate());
		    
		    // Determine the specs for the event for the person
			List<HeatSpec>EventSpecsList = specList.get(r.getEventID().toString());
			
			for(int j = 0; j < EventSpecsList.size(); ++j){
				HeatSpec s = EventSpecsList.get(j);
				if(age >= s.getMinAge() && age <= s.getMaxAge() && a.getGender().equals(s.getGender())){
					List<Registration> temp = list.get(s.getId().toString());
					if(temp == null){
						temp = new ArrayList<Registration>();
					}
					temp.add(r);
					list.put(s.getId().toString(), temp);
					break;
				}
			}				
		}		
		return list;
	}
	public void GenerateHeats() {
		
		aList = getAthleteDictionary();
		List<HeatSpec>heatSpecList = heatSpecDAO.loadAll();
				// do the join
		Dictionary<String,List<Registration>> heList = getHeatSpec_RegDictionary();
		
		// now have to split the entries into heats...
		for(int i = 0; i < heatSpecList.size(); ++i){
			HeatSpec he = heatSpecList.get(i);
			List<Registration> rList = heList.get(he.getId().toString());
			// sort list of registrations
			rList = sortRegistrations(rList);	
			
			int currReg = 0;
			// determine how many to put in each heat
			
			int numHeats = he.getNumHeats();
			int numberInHeats = numHeats/rList.size();
			int remainder = rList.size() - (numberInHeats * numHeats);
			
			// enter new heat
			for(int j = 0; j < numHeats; ++j){
				Heat h = new Heat();
				h.setDivision(j+1);
				h.setEventID(he.getEventId());
				h.setGender(he.getGender());
				h.setMaxAge(he.getMaxAge());
				h.setMinAge(he.getMinAge());
				h.setTime(he.getTime());
				
				heatDAO.add(h);
				// change heatid in reg
				for(int k = 0; k < numberInHeats; ++k){
					Registration r = rList.get(currReg);
					r.setHeatID(h.getId());
					regDAO.update(r);
					++currReg;
				}
				if(remainder > 0){
					Registration r = rList.get(currReg);
					// add extra one
					r.setHeatID(h.getId());
					regDAO.update(r);
					++currReg;
					--remainder;
				}
			}
		}
		

	}
}
