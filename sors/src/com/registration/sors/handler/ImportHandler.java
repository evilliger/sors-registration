package com.registration.sors.handler;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
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
	    //columnMapping.put("BirthDate", "bdate");
	    columnMapping.put("FK_ClassroomID", "classroomId");
	    
	    HeaderColumnNameTranslateMappingStrategy<Athlete> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Athlete>();
	    strategy.setType(Athlete.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Athlete> l = bean.parse(strategy, new StringReader(data));
	    
	    
	    //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    // Add parent keys
		for (Athlete ath : l) {
			ath.setClassroom(new Key<Classroom>(Classroom.class, ath.getClassroomId()));
			//ath.setBdate(formatter.parse());
		}
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
	    columnMapping.put("FK_SchoolID", "schoolID");
	    columnMapping.put("FK_PersonID", "userID");
	    
	    
	    HeaderColumnNameTranslateMappingStrategy<Classroom> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Classroom>();
	    strategy.setType(Classroom.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Classroom> l = bean.parse(strategy, new StringReader(data));
	    
	    for (Classroom cls : l) {
			cls.setSchool(new Key<School>(School.class, cls.getSchoolID()));
		}
	    
	    return l;
	}

	public List<Event> importEvent(String data) {
		CsvToBean<Event> bean = new CsvToBean<Event>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    // EventID,EventName,ScoreUnit,ScoreMin,ScoreMax
	    columnMapping.put("EventID", "id");
	    columnMapping.put("EventName", "name");
	    columnMapping.put("ScoreUnit", "units");
	    columnMapping.put("ScoreMin", "min");
	    columnMapping.put("ScoreMax", "max");
	    
	    HeaderColumnNameTranslateMappingStrategy<Event> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Event>();
	    strategy.setType(Event.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Event> l = bean.parse(strategy, new StringReader(data));
	    
	    for(Event eve : l) {
	    	eve.setParent(new Key<Event>(Event.class, Event.parentId));
	    }
	    
	    return l;
	}

	public List<Heat> importHeat(String data) {
		CsvToBean<Heat> bean = new CsvToBean<Heat>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("HeatID", "id");
	    columnMapping.put("Gender", "gender");
	    columnMapping.put("Division", "division");
	    columnMapping.put("MinAge", "minAge");
	    columnMapping.put("MaxAge", "maxAge");
	    //columnMapping.put("Time", "time");
	    columnMapping.put("FK_EventID", "eventID");
	    
	    HeaderColumnNameTranslateMappingStrategy<Heat> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Heat>();
	    strategy.setType(Heat.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Heat> l = bean.parse(strategy, new StringReader(data));
	    
	    for (Heat heat : l) {
	    	heat.setParent(new Key<Heat>(Heat.class, Heat.parentId));
	    }
	    
	    return l;
	}

	public List<HeatSpec> importHeatSpec(String data) {
		CsvToBean<HeatSpec> bean = new CsvToBean<HeatSpec>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    // HeatSpec_ID,Gender,MinAge,MaxAge,Time,NumHeats,MaxInHeat,FK_EventID
	    columnMapping.put("HeatSpec_ID", "id");
	    columnMapping.put("Gender", "gender");
	    columnMapping.put("MinAge", "minAge");
	    columnMapping.put("MaxAge", "maxAge");
	    //columnMapping.put("Time", "time");
	    columnMapping.put("NumHeats", "numHeats");
	    columnMapping.put("MaxInHeat", "maxInHeat");
	    columnMapping.put("FK_EventID", "eventID");
	    
	    HeaderColumnNameTranslateMappingStrategy<HeatSpec> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<HeatSpec>();
	    strategy.setType(HeatSpec.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<HeatSpec> l = bean.parse(strategy, new StringReader(data));
	    
	    for(HeatSpec hSpec : l) {
	    	hSpec.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
	    }
	    
	    return l;
	}

	public List<Registration> importRegistration(String data) {
		CsvToBean<Registration> bean = new CsvToBean<Registration>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    columnMapping.put("RegID", "id");
	    columnMapping.put("Score", "score");
	    columnMapping.put("Rank", "rank");
	    columnMapping.put("FK_HeatID", "heatID");
	    columnMapping.put("FK_EventID", "eventID");
	    columnMapping.put("FK_AthleteID", "athleteID");
	    
	    HeaderColumnNameTranslateMappingStrategy<Registration> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<Registration>();
	    strategy.setType(Registration.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<Registration> l = bean.parse(strategy, new StringReader(data));
	    for(Registration reg : l) {
	    	reg.setParent(new Key<Registration>(Registration.class, Registration.parentId));
	    }
	    
	    return l;
	}

	public List<School> importSchool(String data) {
		CsvToBean<School> bean = new CsvToBean<School>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    // SchoolID,GroupCode,SchoolName,VolunteerNum,FK_PersonID
	    columnMapping.put("SchoolID", "id");
	    columnMapping.put("GroupCode", "groupID");
	    columnMapping.put("SchoolName", "name");
	    columnMapping.put("VolunteerNum", "volunteerNum");
	    
	    HeaderColumnNameTranslateMappingStrategy<School> strategy = 
	        new HeaderColumnNameTranslateMappingStrategy<School>();
	    strategy.setType(School.class);
	    strategy.setColumnMapping(columnMapping);
	    
	    //Parse the CSV
	    List<School> l = bean.parse(strategy, new StringReader(data));
	    
	    for(School sch : l) {
	    	sch.setParent(new Key<School>(School.class, School.parentId));
	    }
	    
	    return l;
	}

	public List<User> importUser(String data) {
		CsvToBean<User> bean = new CsvToBean<User>();
	    
	    //Define strategy
	    //Header name to bean property name mapping
	    Map<String, String> columnMapping = new HashMap<String, String>();
	    
	    // DB name, DS name
	    // PersonID,Title,FirstName,LastName,Fax,Email,Password,Role,Active
	    columnMapping.put("PersonID", "id");
	    columnMapping.put("Title", "title");
	    columnMapping.put("FirstName", "fname");
	    columnMapping.put("LastName", "lname");
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
	    
	    for(User usr : l){
	    	usr.setParent(new Key<User>(User.class, User.parentId));
	    }
	    
	    return l;
	}
	
}
