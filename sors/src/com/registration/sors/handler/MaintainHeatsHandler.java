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
	
	public MaintainHeatsHandler(){}
	
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
	
	public  Dictionary<String,List<Registration>> getRegDictionary(){
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
	public  Dictionary<String,Athlete> getAthleteDictionary(){
		Dictionary<String,Athlete>athleteCodes = new Hashtable<String,Athlete>();
		
		List<Athlete>aList = athDAO.loadAll();
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
	

	private  List<HeatEntry> sortHeatEntries(List<HeatEntry> heatEntryList){
		// sort the heat entry list by event id
		// then time
		// then age
		// then gender
		// then score
		Collections.sort(heatEntryList, new Comparator<HeatEntry>() {
		    public int compare(HeatEntry h1, HeatEntry h2) {
		        if(h1.getEventID() < h2.getEventID()){
		        	return -1;
		        }else if( h1.getEventID() > h2.getEventID()){
		        	return 1;
		        }else{
		        	if(h1.getTime().before(h2.getTime())){
		        		return -1;
		        	}else if(h1.getTime().after(h2.getTime())){
		        		return 1;
		        	}else{
			        	if(h1.getMinAge() < h2.getMinAge()){
			        		return -1;
			        	}else if(h1.getMinAge() > h2.getMinAge()){
			        		return 1;
			        	}else{
			        		if(h1.getGender() == "Male" && h2.getGender() != "Male"){
			        			return -1;
			        		}else if(h1.getGender() == "Female" && h2.getGender() != "Female"){
			        			return 1;
			        		}else{
			        			// score
			        			if(h1.getScore() <= h2.getScore()){
			        				return -1;
			        			}else{
			        				return 1;
			        			}
			        		}
			        	}
		        	}
		        	
		        }
		    }
		});
		return heatEntryList;
	}
	
	
	
	// generate a list of heatentrys
	// parameters: regList - list of registraitons
	//				athleteCodes - dictionary of athletes
	//				specList - dictionary of heat specifications
	// return: list of HeatEntries
	private List<HeatEntry> getHeatEntryList(){
		
		List<Registration> regList = regDAO.loadAll();
		Dictionary<String,Athlete>athleteCodes = getAthleteDictionary();		
		Dictionary<String,List<HeatSpec>> specList = getHeatSpecDictionary();
		
		
		
		List<HeatEntry> heatEntryList = new ArrayList<HeatEntry>();
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			Athlete a = athleteCodes.get(r.getAthleteID().toString());
			
			int age = findBdate(a.getBdate());
		    
		    // Determine the specs for the event for the person
			List<HeatSpec>EventSpecsList = specList.get(r.getEventID());
			
			for(int j = 0; j < EventSpecsList.size(); ++j){
				HeatSpec s = EventSpecsList.get(i);
				if(age >= s.getMinAge() && age <= s.getMaxAge() && a.getGender() == s.getGender()){
					heatEntryList.add(new HeatEntry(s.getEventId(),s.getMinAge(),s.getMaxAge(),s.getTime(),s.getGender(),r.getScore(),r.getId()));
					break;
				}
			}			
			
		}
		// sort the entries
		heatEntryList = sortHeatEntries(heatEntryList);
		return heatEntryList;
	}
	

	
	
	public String ToString(){
		Dictionary<String,Athlete>athList = getAthleteDictionary();
		Dictionary<String,Event>eventList = getEventDictionary(); 
		Dictionary<String,List<Registration>>regList = getRegDictionary();
		List<Heat>heatList = heatDAO.loadAll();
		String html = "";
		
		for(int i = 0; i < heatList.size(); ++i){
			Heat h = heatList.get(i);
			html += "<table><tr>" 
			+ " <th>" + eventList.get(h.getId().toString()).getName() + "</th>"
			+ "<th>Group " + Integer.toString(h.getMinAge()) + " to " + Integer.toString(h.getMaxAge()) + "</th>"
			+ "<th>Time " + h.getTime().toString() + "</th>"
			+ "<th>Gender: " + h.getGender() + "<\th>"
			+ "</tr>";
			
			html += "<tr>"
			+ "<th>Name</th>"
			+ "<th>Age</th>"
			+ "<th>Sex</th>"
			+ "<th>Rank</th>"
			+ "<th>Score</th>"
			+ "<th>Div</th></tr>";
			
			List<Registration>rList = regList.get(h.getId().toString());
			
			for(int j = 0; j < rList.size(); ++i){
				Registration r = rList.get(i);
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
	public void GenerateHeats(){
		regDAO = new RegistrationDAO();
		athDAO = new AthleteDAO();
		eventDAO = new EventDAO();
		heatSpecDAO = new HeatSpecDAO();
		heatDAO = new HeatDAO();
		regDAO.init();
		athDAO.init();
		eventDAO.init();
		heatSpecDAO.init();
		heatDAO.init();
		

	
		// Generate a list of heatentries based on registrations, athletes and heat specifications
		// and sort the heatentry list
		List<HeatEntry> heatEntryList = getHeatEntryList();
		
	    
		// Generate the heats and add to datastore
		for(int i = 0; i< heatEntryList.size(); ++i){
			HeatEntry he = heatEntryList.get(i);
			Heat h = new Heat(0, he.getGender(), he.getMinAge(), he.getMaxAge(), he.getEventID(),he.getTime());
			heatDAO.add(h);
		}
	}
}
