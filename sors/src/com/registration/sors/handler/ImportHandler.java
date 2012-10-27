package com.registration.sors.handler;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.model.School;
import com.registration.sors.model.User;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;


public class ImportHandler {
	
	
	// Returns a list of athletes created from a CSV string
	public List<Athlete> importAthletes(String data){
		
		CsvToBean<Athlete> bean = new CsvToBean<Athlete>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("AthleteID", "id");
	    columnMapping.put("FirstName", "fname");
	    columnMapping.put("LastName", "lname");
	    columnMapping.put("MiddleInitial", "mname");
	    columnMapping.put("Gender", "gender");
	    // I don't know what to do with the "classroom" key thing in the athlete
	    //columnMapping.put("", "classroom");
	    columnMapping.put("ClassroomID", "classroomId");
	    
	    HeaderColumnNameTranslateMappingStrategy<Athlete> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Athlete>();
	    strategy.setType(Athlete.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Athlete> l = bean.parse(strategy, new StringReader(data));
	    
	    return l;
	}

	public List<Classroom> importClassrooms(String data) {
		CsvToBean<Classroom> bean = new CsvToBean<Classroom>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("ClassroomID", "id");
	    columnMapping.put("ClassroomName", "className");
	    columnMapping.put("SchoolID", "school");
	    columnMapping.put("UserID", "userID");
	    
	    
	    HeaderColumnNameTranslateMappingStrategy<Classroom> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Classroom>();
	    strategy.setType(Classroom.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Classroom> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<Event> importEvent(String data) {
		CsvToBean<Event> bean = new CsvToBean<Event>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("EventID", "eventID");
	    columnMapping.put("EventName", "eventName");
	    columnMapping.put("ScoreUnit", "scoreUnits");
	    columnMapping.put("ScoreMin", "scoreMin");
	    columnMapping.put("ScoreMax", "scoreMax");
	    
	    HeaderColumnNameTranslateMappingStrategy<Event> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Event>();
	    strategy.setType(Event.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Event> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<Heat> importHeat(String data) {
		CsvToBean<Heat> bean = new CsvToBean<Heat>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("HeatID", "heatID");
	    columnMapping.put("Gender", "gender");
	    columnMapping.put("Division", "division");
	    columnMapping.put("MinAge", "minAge");
	    columnMapping.put("MaxAge", "maxAge");
	    columnMapping.put("Time", "time");
	    columnMapping.put("EventID", "eventID");
	    
	    HeaderColumnNameTranslateMappingStrategy<Heat> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Heat>();
	    strategy.setType(Heat.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Heat> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<HeatSpec> importHeatSpec(String data) {
		CsvToBean<HeatSpec> bean = new CsvToBean<HeatSpec>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("HeatSpecID", "HeatSpecID");
	    columnMapping.put("Gender", "gender");
	    columnMapping.put("MinAge", "minAge");
	    columnMapping.put("MaxAge", "maxAge");
	    columnMapping.put("Time", "time");
	    columnMapping.put("NumHeats", "numHeats");
	    columnMapping.put("MaxInHeat", "maxInHeat");
	    columnMapping.put("EventID", "eventID");
	    
	    HeaderColumnNameTranslateMappingStrategy<HeatSpec> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<HeatSpec>();
	    strategy.setType(HeatSpec.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<HeatSpec> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<Registration> importRegistration(String data) {
		CsvToBean<Registration> bean = new CsvToBean<Registration>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("RegID", "regID");
	    columnMapping.put("Score", "score");
	    columnMapping.put("Rank", "rank");
	    columnMapping.put("EventID", "eventID");
	    columnMapping.put("AthleteID", "athleteID");
	    
	    HeaderColumnNameTranslateMappingStrategy<Registration> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Registration>();
	    strategy.setType(Registration.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Registration> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<School> importSchool(String data) {
		CsvToBean<School> bean = new CsvToBean<School>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("SchoolID", "schoolID");
	    columnMapping.put("GroupCode", "groupID");
	    columnMapping.put("SchoolName", "schoolName");
	    columnMapping.put("VolunteerNum", "volunteerNum");
	    
	    HeaderColumnNameTranslateMappingStrategy<School> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<School>();
	    strategy.setType(School.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<School> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}

	public List<User> importUser(String data) {
		CsvToBean<User> bean = new CsvToBean<User>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("UserID", "id");
	    columnMapping.put("Title", "title");
	    columnMapping.put("UserFirstName", "fname");
	    columnMapping.put("UserLastName", "lname");
	    columnMapping.put("Fax", "fax");
	    columnMapping.put("Email", "email");
	    columnMapping.put("Password", "pword");
	    columnMapping.put("Role", "role");
	    columnMapping.put("Active", "active");
	    
	    HeaderColumnNameTranslateMappingStrategy<User> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<User>();
	    strategy.setType(User.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<User> l = bean.parse(strategy, new StringReader(data));
	    return l;
	}
	
}
