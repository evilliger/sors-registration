//-------------------------------------------//
// Name: InitController					     //
//-------------------------------------------//
package com.registration.sors.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.Key;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.EventConflict;
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.model.School;
import com.registration.sors.model.User;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.ClassroomDAO;
import com.registration.sors.service.EventConflictDAO;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.HeatDAO;
import com.registration.sors.service.HeatSpecDAO;
import com.registration.sors.service.RegistrationDAO;
import com.registration.sors.service.SchoolDAO;
import com.registration.sors.service.UserDAO;

@Controller
@RequestMapping("/init")
public class InitController {
	
	@Autowired private AthleteDAO Athdao;
	@Autowired private EventDAO Evdao;
	@Autowired private EventConflictDAO EvConfdao;
	@Autowired private SchoolDAO Schdao;
	@Autowired private ClassroomDAO Cladao;
	@Autowired private UserDAO Usedao;
	@Autowired private HeatDAO Heatdao;
	@Autowired private HeatSpecDAO Hsdao;
	@Autowired private RegistrationDAO Regdao;
	
	List<String> roles = Arrays.asList("A", "T");

	// Dev Testing ONLY
	// Name: init
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {

		try {
			School parent = new School();
			parent.setId(School.parentId);
			Schdao.add(parent);
			
			School s = new School();
			s.setGroupID("012");
			s.setName("Bobby Jones");
			s.setVolunteerNum(1242);
			s.setParent(new Key<School>(School.class, School.parentId));
			Schdao.add(s);
	
			User up = new User();
			up.setId(User.parentId);
			Usedao.add(up);
			
			User u = new User();
			u.setRole("T");
			u.setFname("Frank");
			u.setLname("Harrison");
			u.setEmail("user1");
			u.setPword("x");
			u.setTitle("Mr.");
			u.setParent(new Key<User>(User.class, up.getId()));
			Usedao.add(u);
			
			User u2 = new User();
			u2.setRole("T");
			u2.setFname("James");
			u2.setLname("Zumsteg");
			u2.setEmail("user2");
			u2.setPword("x");
			u2.setTitle("Mr.");
			u2.setParent(new Key<User>(User.class, up.getId()));
			Usedao.add(u2);
			
			Classroom c = new Classroom();
			c.setClassName("Fifth Grade!!");
			c.setSchoolID(s.getId());
			c.setUserID(u.getId());
			c.setSchool(new Key<School>(School.class, s.getId()));
			Cladao.add(c);
			
			c = new Classroom();
			c.setClassName("Hundredth Grade!!");
			c.setSchoolID(s.getId());
			c.setUserID(u2.getId());
			c.setSchool(new Key<School>(School.class, s.getId()));
			Cladao.add(c);
			
			Athlete a = new Athlete();
			a.setFname("Fred");
			a.setLname("Jonesy");
			a.setBdate(new Date(2012,10,10));
			a.setGender("M");
			a.setClassroomId(c.getId());
			a.setClassroom(new Key<Classroom>(Classroom.class, c.getId()));
			Athdao.add(a);
			
			u = new User();
			u.setRole("A");
			u.setActive("T");
			u.setLname("Schaub");
			u.setFname("Steven");
			u.setEmail("Schaub");
			u.setPword("x");
			u.setParent(new Key<User>(User.class, User.parentId));
			Usedao.add(u);
			
			Event ep = new Event();
			ep.setId(Event.parentId);
			Evdao.add(ep);

			Event e = new Event();
			e.setParent(new Key<Event>(Event.class, ep.getId()));
			e.setName("Javelin Jumping Jack");
			e.setMin(3);
			e.setMax(10);
			e.setUnits("M");
			Evdao.add(e);
			Event e2 = new Event();
			e2.setParent(new Key<Event>(Event.class, ep.getId()));
			e2.setName("50 Meter Dash");
			e2.setUnits("S");
			e2.setMin(10);
			e2.setMax(20);
			Evdao.add(e2);
			Event e3 = new Event();
			e3.setParent(new Key<Event>(Event.class, ep.getId()));
			e3.setName("100 Meter Dash");
			e3.setUnits("S");
			e3.setMin(15);
			e3.setMax(50);
			Evdao.add(e3);
			
			EventConflict ec = new EventConflict();
			ec.setId(EventConflict.parentId);
			ec.setE1Id(0L);
			ec.setE2Id(0L);
			EvConfdao.add(ec);
			
			EventConflict ec1 = new EventConflict();
			ec1.setId(EventConflict.parentId);
			ec1.setE1Id(e2.getId());
			ec1.setE2Id(e3.getId());
			EvConfdao.add(ec1);
	
			Heat hp = new Heat();
			hp.setId(Heat.parentId);
			Heatdao.add(hp);
			
			Heat h = new Heat();
			h.setEventID(e.getId());
			h.setGender("M");
			h.setMaxAge(12);
			h.setMinAge(7);
			h.setDivision(1);
			h.setTime("11:00");
			Heatdao.add(h);
			
			
			HeatSpec hsp = new HeatSpec();
			hsp.setId(HeatSpec.parentId);
			Hsdao.add(hsp);
			
			HeatSpec hs = new HeatSpec();
			hs.setEventId(new Long(122));
			hs.setGender("F");
			hs.setMaxAge(15);
			hs.setMinAge(12);
			hs.setMaxInHeat(15);
			hs.setNumHeats(1);
			hs.setTime("10:30");
			hs.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
			Hsdao.add(hs);
			
			HeatSpec t = new HeatSpec();
			t.setEventId(new Long(123));
			t.setGender("M");
			t.setMaxAge(15);
			t.setMinAge(12);
			t.setMaxInHeat(15);
			t.setNumHeats(1);
			t.setTime("11:00");
			t.setParent(new Key<HeatSpec>(HeatSpec.class, HeatSpec.parentId));
			Hsdao.add(t);
			
			Registration rp = new Registration();
			rp.setId(Registration.parentId);
			Regdao.add(rp);
			
			Registration r = new Registration();
			r.setParent(new Key<Registration>(Registration.class, rp.getId()));
			r.setAthleteID(a.getId());
			r.setEventID(e.getId());
			r.setHeatID(h.getId());
			r.setRank(2);
			r.setScore(24.239);
			Regdao.add(r);
		} catch (Exception e) {
			Log.warn(e.toString());
		}
		return new ModelAndView("redirect:/athlete/list");
	}
	
}