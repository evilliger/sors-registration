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
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatEntry;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.HeatDAO;
import com.registration.sors.service.HeatSpecDAO;
import com.registration.sors.service.RegistrationDAO;

public class MaintainHeatsHandler {
	

	// Turn a List of HeatSpec into a dictionary
	// parameters: heatSpecList - the list to turn into dictionary
	// return: a dictionary created from parameter
	private static Dictionary<String, List<HeatSpec>> getHeatSpecDictionary(List<HeatSpec> heatSpecList){
		Dictionary<String,List<HeatSpec>> specList = new Hashtable<String,List<HeatSpec>>();
		for(int i = 0; i < heatSpecList.size(); ++i){
			HeatSpec h = heatSpecList.get(i);
			List<HeatSpec> temp = specList.get(Integer.toString(h.getEventID()));
			if(temp == null){
				temp = new ArrayList<HeatSpec>();
			}
			temp.add(h);
			specList.put(Integer.toString(h.getEventID()), temp);
		}
		return specList;
	}
	// generate a list of heatentrys
	// parameters: regList - list of registraitons
	//				athleteCodes - dictionary of athletes
	//				specList - dictionary of heat specifications
	// return: list of HeatEntries
	private static List<HeatEntry> getHeatEntryList(List<Registration> regList, Dictionary<String,Athlete>athleteCodes, Dictionary<String,List<HeatSpec>> specList){
		List<HeatEntry> heatEntryList = new ArrayList<HeatEntry>();
		for(int i = 0; i < regList.size(); ++i){
			Registration r = regList.get(i);
			Athlete a = athleteCodes.get(Integer.toString(r.getAthleteID()));
			
			//calculate the birthday....
			Calendar cal1 = new GregorianCalendar();
		    Calendar cal2 = new GregorianCalendar();
		    int age = 0;
		    int factor = 0; 
		    cal1.setTime(a.getBdate());
		    cal2.setTime(new Date());
		    if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
		          factor = -1; 
		    }
		    age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		    
		    // Determine the specs for the event for the person
			List<HeatSpec>EventSpecsList = specList.get(r.getEventID());
			
			for(int j = 0; j < EventSpecsList.size(); ++j){
				HeatSpec s = EventSpecsList.get(i);
				if(age >= s.getMinAge() && age <= s.getMaxAge() && a.getGender() == s.getGender()){
					heatEntryList.add(new HeatEntry(s.getEventID(),s.getMinAge(),s.getMaxAge(),s.getTime(),s.getGender(),r.getScore()));
					break;
				}
			}
			
			
		}
		// sort the entries
		heatEntryList = sortHeatEntries(heatEntryList);
		return heatEntryList;
	}
	private static List<HeatEntry> sortHeatEntries(List<HeatEntry> heatEntryList){
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
	
	
	public static void GenerateHeats(){
		// 1. fill these lists
		List<Registration> regList = RegistrationDAO.loadAll();
		List<HeatSpec> heatSpecList = HeatSpecDAO.loadAll();
		List<Athlete> athleteList = AthleteDAO.loadAll();
		
		// 2. populate these dictionaries
		Dictionary<String,Athlete>athleteCodes = new Hashtable<String,Athlete>();
		Dictionary<String,List<HeatSpec>> specList = getHeatSpecDictionary(heatSpecList);
		
		// 3. turn athlete list into dictionary
		for(int i = 0; i < athleteList.size(); ++i){
			Athlete a = athleteList.get(i);
			athleteCodes.put(Long.toString(a.getId()), a);
		}
	
		// 4. Generate a list of heatentries based on registrations, athletes and heat specifications
		// 5. sort the heatentry list
		List<HeatEntry> heatEntryList = getHeatEntryList(regList,athleteCodes, specList);
		
	
		// 6. Generate the heats and add to datastore
		for(int i = 0; i< heatEntryList.size(); ++i){
			HeatEntry he = heatEntryList.get(i);
			Heat h = new Heat(0, he.getGender(), he.getMinAge(), he.getMaxAge(), he.getEventID(),he.getTime());
			HeatDAO.add(h);
		}
	}
}
