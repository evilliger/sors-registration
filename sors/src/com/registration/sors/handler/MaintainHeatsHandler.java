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
import java.util.logging.Logger;

import com.registration.sors.model.Athlete;
import com.registration.sors.model.Event;
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.HeatDAO;
import com.registration.sors.service.HeatSpecDAO;
import com.registration.sors.service.RegistrationDAO;

public class MaintainHeatsHandler {
	
	static Logger log = Logger.getLogger(MaintainHeatsHandler.class.getName());
	
	// This class needs access to the objects in
	// the 5 datastores 
	private static RegistrationDAO regDAO;
	private static AthleteDAO athDAO;
	private static EventDAO eventDAO;
	private static HeatSpecDAO heatSpecDAO;
	private static HeatDAO heatDAO;
	
	
	private List<Athlete> athList;
	private List<Registration> regList;
	private List<HeatSpec> heatSpecList;
	private List<Event> eventList;
	
	public Dictionary<String,Athlete>athDictionary;
	public Dictionary<String,Event>eventDictionary; 
	public Dictionary<String,List<Registration>>regDictionary;
	public List<Heat>heatList;

	
	// The contructor has to recieve the 5 dao classes from the controller
	// becuase of objectify only the controllers can init the daos
	// so the inited daos have to be passes through
	public MaintainHeatsHandler(HeatDAO heatDao2, RegistrationDAO regDao2, AthleteDAO athDao2, EventDAO eventDao2, HeatSpecDAO heatSpecDao2){
		regDAO = regDao2;
		athDAO = athDao2;
		eventDAO = eventDao2;
		heatSpecDAO = heatSpecDao2;
		heatDAO = heatDao2;
		
		try {
			athList = athDAO.loadAll();
		} catch (Exception e) {
			log.severe("Error loading list of athletes: " + e.toString());
		}
		regList = regDAO.loadAll();
		eventList = eventDAO.loadAll();
		heatSpecList = heatSpecDAO.loadAll();
		
	}
	
	// name: age
	// Purpose: to calculate the age of a person
	// params: bday - the birthday of athlete
	// return : age
	private  int age(Date bday){
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
	
	// Purpose: convert event list to dictionary with eventID as key
	// params : none
	// return: dictionary with eventid as key and event as value
	private  Dictionary<String,Event> getEventDictionary(){
		Dictionary<String,Event>eventD = new Hashtable<String,Event>();
		for(int i = 0; i < eventList.size(); ++i){
			eventD.put(eventList.get(i).getId().toString(), eventList.get(i));		
		}
		return eventD;
	}
	
	// Purpose: convert registration list to dictionary with heatID as key
	// params : none
	// return: dictionary with heatid as key and registration as value
	public  Dictionary<String,List<Registration>> getRegHeatDictionary(){
		Dictionary<String,List<Registration>> regD = new Hashtable<String,List<Registration>>();
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			if(r.getHeatID() != null){
				List<Registration> temp = regD.get(r.getHeatID().toString());
				if(temp == null){
					temp = new ArrayList<Registration>();
				}
				temp.add(r);
				regD.put(r.getHeatID().toString(), temp);
			}
		}
		return regD;
	
	}
	// Purpose: convert registration list to dictionary with registrationID as key
	// params : none
	// return: dictionary with registrationid as key and registration as value
	public  Dictionary<String,Registration> getRegDictionary(){
		Dictionary<String,Registration> regD = new Hashtable<String,Registration>();
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			regD.put(r.getId().toString(), r);
		}
		return regD;
	
	}
	
	// Purpose: convert Athlete list to dictionary with AthleteID as key
	// params : none
	// return: dictionary with Athleteid as key and Athlete as value
	public  Dictionary<String,Athlete> getAthleteDictionary(){
		Dictionary<String,Athlete>athleteCodes = new Hashtable<String,Athlete>();

		// turn athlete list into dictionary
		for(int i = 0; i < athList.size(); ++i){
			Athlete a = athList.get(i);
			athleteCodes.put(Long.toString(a.getId()), a);
		}
		return athleteCodes;
	}
	
	
	
	// Turn a List of HeatSpec into a dictionary
	// parameters: heatSpecList - the list to turn into dictionary
	// return: a dictionary created from parameter
	private  Dictionary<String, List<HeatSpec>> getHeatSpecDictionary(){
		
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
	


	

	// create a dictionary whose key is a heatspecid and whose
	// value is a list of registrations that fit the heatspecs
	// params: none
	// return: dictionary as described above
	public Dictionary<String,List<Registration>> getHeatSpec_RegDictionary(){
		Dictionary<String,List<Registration>> list = new Hashtable<String,List<Registration>>();
			
		// key is event id
		Dictionary<String,List<HeatSpec>> specList = getHeatSpecDictionary();
		
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			Athlete a = athDictionary.get(r.getAthleteID().toString());
			
			int age = age(a.getBdate());
		    
		    // Determine the specs for the event for the person
			List<HeatSpec>EventSpecsList = specList.get(r.getEventID().toString());
			if(EventSpecsList != null){
				for(int j = 0; j < EventSpecsList.size(); ++j){
					HeatSpec s = EventSpecsList.get(j);
					if(age >= s.getMinAge() && age <= s.getMaxAge() && a.getGender().equals(s.getGender())){
						List<Registration> temp = list.get(s.getId().toString());
						if(temp == null){
							temp = new ArrayList<Registration>();
						}
						temp.add(r);
						list.put(s.getId().toString(), temp);
					}
				}				
			}
		}		
		return list;
	}
	
	
	// sort a list of registrations based on scores
	// params : rlist - list of registrations
	// return: sorted list of registrations
	private  List<Registration> sortRegistrations(List<Registration> rList){
		Collections.sort(rList, new Comparator<Registration>() {
		    public int compare(Registration r1, Registration r2) {
		    	if(r1.getScore() <= r2.getScore()){
		    		return -1;
		    	}else{
		    		return 1;
		    	}
		    }
		});
		return rList;
	}
	// sort a list of registrations based on scores
	// params : rlist - list of registrations
	// return: sorted list of registrations
	private  List<Registration> sortRank(List<Registration> rList){
		Collections.sort(rList, new Comparator<Registration>() {
		    public int compare(Registration r1, Registration r2) {
		    	if(r1.getRank() <= r2.getRank()){
		    		return -1;
		    	}else{
		    		return 1;
		    	}
		    }
		});
		return rList;
	}
	
	// sort a list of heats based on eventid
	// time, age range, gender, and division
	// params : list of heats
	// return: sorted list of heats
	private  List<Heat> sortHeats(List<Heat> hList){
		Collections.sort(hList, new Comparator<Heat>() {
		    public int compare(Heat h1, Heat h2) {
		    	// sort on event
		    	if(h1.getEventID() < h2.getEventID()){
		    		return -1;
		    	}else if(h1.getEventID() > h2.getEventID()){
		    		return 1;
		    	}else{
		    		String t1 = h1.getTime();
		    		String t2 = h2.getTime();
		    		int hour1 = 0;
		    		int minute1 = 0;
		    		int hour2 = 0;
		    		int minute2 = 0;
		    		
		    		int index = t1.indexOf(':');
		    		hour1 = Integer.parseInt(t1.substring(0,index));
		    		t1 = t1.substring(index+1);
		    		minute1 = Integer.parseInt(t1);
		    		
		    		index = t2.indexOf(':');
		    		hour2 = Integer.parseInt(t2.substring(0,index));
		    		t2 = t2.substring(index+1);
		    		minute2 = Integer.parseInt(t2);
		    		
		    		if(hour1 < hour2 || (hour1 == hour2 && minute1 < minute2)){
		    			return -1;
		    		}else if(hour1 > hour2 || (hour1 == hour2 && minute1 > minute2) ){
		    			return 1;
		    		}else{
		    			if(h1.getMinAge() < h2.getMinAge()){
		    				return -1;
		    			}else if(h1.getMinAge() > h2.getMinAge()){
		    				return 1;
		    			}else{
		    				if(h1.getGender().equals("M") && h2.getGender().equals("F")){
		    					return -1;
		    				}else if (h1.getGender().equals("F") && h2.getGender().equals("M")){
		    					return 1;
		    				}else{
		    					if(h1.getDivision() < h2.getDivision()){
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
		return hList;
	}
	
	
	// to validate the entries in the datastore
	// to reduce chance of exceptions
	// params: none
	public List<String> validate(){
		List<String> errors = new ArrayList<String>();
		// check all athletes
		for(int i = 0; i < athList.size(); ++i){
			Athlete a = athList.get(i);
			if(a.getBdate() == null || a.getGender() == null || a.getLname() == null || a.getFname() == null){
				athList.remove(i);
				--i;
				log.severe("Athlete: " + a.getId() + "is not completely registered");
				errors.add("Error: AthleteID: " + a.getId() + " entry is not complete and will be removed before generating heats.");
			}
		}
		
		// check events
		for(int i = 0; i < eventList.size(); ++i){
			Event e = eventList.get(i);
			if(e.getName() == null || e.getUnits() == null){
				eventList.remove(i);
				--i;
				log.severe("Event: " + e.getId() + " was not entered correctly");
				errors.add("Error: EventID: " + e.getId() + " entry is not complete and will be removed before generating heats.");
			}
		}
		
		Dictionary<String,Athlete>aList = getAthleteDictionary();
		Dictionary <String, Event> eList = getEventDictionary();
		
		Dictionary<String, List<Registration>> regAthDictionary = new Hashtable<String,List<Registration>>();
		
		// check registrations
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			
			if(r.getAthleteID() == null || r.getEventID() == null || aList.get(r.getAthleteID().toString())  == null || eList.get(r.getEventID().toString()) == null){
				regList.remove(i);
				--i;
				log.severe("Registration: " + r.getId() + " was not entered correctly");
				errors.add("Error: RegistrationID: " + r.getId() + " entry is not complete, athlete does not exist, or event does not exists and registration will be removed before generating heats.");
			}else{
				List<Registration> t = regAthDictionary.get(r.getAthleteID().toString());
				if(t == null){
					t = new ArrayList<Registration>();
				}
				t.add(r);
				regAthDictionary.put(r.getAthleteID().toString(), t);
			}
		}
		
		
		
		//check heatspecs
		for(int i = 0; i < heatSpecList.size(); ++i){
			HeatSpec h = heatSpecList.get(i);
			if(h.getEventId() == null || h.getGender() == null || h.getMaxAge() == 0 || h.getMaxInHeat() == 0  || h.getNumHeats() == 0 || h.getTime() == null || eList.get(h.getEventId().toString()) == null){
				heatSpecList.remove(i);
				--i;
				log.severe("HeatSpec: " + h.getId() + " was not entered correctly");
				errors.add("Error: HeatSpecID: " + h.getId() + " entry is not complete or event does not exist and heatspec will be removed before generating heats.");
			}
		}

		
		for(int i = 0; i < athList.size(); ++i){
			Athlete a = athList.get(i);
			List<Registration> r = regAthDictionary.get(a.getId().toString());
			if(r == null){
				errors.add("Warning: Athlete " + a.getFname() + " " + a.getLname() + " is not registered for any events.");
			}else if(r.size() < 2){
				errors.add("Warning: Athlete " + a.getFname() + " " + a.getLname() + " is only registered for one event.");
			}else if (r.size() > 2){
				errors.add("Warning: Athlete " + a.getFname() + " " + a.getLname() + " is registered for too many events.");
			}
		}
		return errors;
		
	}
	
	// main method called to generate heats
	// params: none
	// return: none
	public void GenerateHeats() {
		
		// validate the entries in the datastore
		// remove any objects that will break system
		validate();
		
		athDictionary = getAthleteDictionary();
		eventDictionary= getEventDictionary(); 
		regDictionary = getRegHeatDictionary();
		

		// do the join
		Dictionary<String,List<Registration>> heList = getHeatSpec_RegDictionary();
		
		// now have to split the entries into heats...
		for(int i = 0; i < heatSpecList.size(); ++i){
			HeatSpec he = heatSpecList.get(i);
			List<Registration> rList = heList.get(he.getId().toString());
			if(rList != null){
				
				// sort list of registrations
				rList = sortRegistrations(rList);
				
				int currReg = 0;
				// determine how many to put in each heat
				
				int numHeats = he.getNumHeats();
				int numberInHeats = rList.size()/numHeats;
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
					// change rank
					int rank = 1;
					for(int k = 0; k < numberInHeats; ++k){
						Registration r = rList.get(currReg);
						r.setHeatID(h.getId());
						r.setRank(rank);
						regDAO.update(r);
						++currReg;
						++rank;
					}
					if(remainder > 0){
						Registration r = rList.get(currReg);
						// add extra one
						r.setHeatID(h.getId());
						r.setRank(rank);
						regDAO.update(r);
						++rank;
						++currReg;
						--remainder;
					}
				}
				
			}
		}
		athDictionary = getAthleteDictionary();
		eventDictionary= getEventDictionary(); 
		regDictionary = getRegHeatDictionary();
		heatList = heatDAO.loadAll();
		heatList = sortHeats(heatList);
	}
}
