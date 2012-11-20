<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ page import="com.registration.sors.model.School" %>
<%@ page import="com.registration.sors.model.Classroom" %>
<%@ page import="com.registration.sors.service.ClassroomDAO" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tag:header/>

<section id="content">
 	<div class="container_24">
   	<div class="row">
     	<div class="wrapper">
     	
     	<% 	boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A"); 
     		boolean add = (Boolean)request.getAttribute("add");
     		List<School> schools = (List<School>)request.getAttribute("schools");
     		List<Classroom> classrooms = (List<Classroom>)request.getAttribute("classrooms"); 
     		Long school = (Long)request.getAttribute("school");
     		if (school == null) school = -1L;
     		Long classroom = (Long)request.getAttribute("classroom");
     		if (classroom == null) classroom = -1L;
     		List<Event> events = (List<Event>)request.getAttribute("events");
			Long pe = (Long)request.getAttribute("pevent"); 
			Long se = (Long)request.getAttribute("sevent");
      		String hidden = ((se == null || se.equals(-1L) || se.equals("")) && (pe == null || pe.equals(-1L))) ? "hidden" : "";
      		Double pscore = (Double)request.getAttribute("pscore");
      		Double sscore = (Double)request.getAttribute("sscore");
      		Long pregid = (Long)request.getAttribute("pregId");
      		Long sregid = (Long)request.getAttribute("sregId");
     	%>

		<form:form commandName='athlete' action='<%= (add) ? "add": "update" %>'>
		
		<ul class="errors">
		<form:errors path="*" >
			<c:forEach items="${messages}" var="message">
				<li>${message}</li>
			</c:forEach>
		</form:errors>
			<c:forEach items="${otherErrors}" var="error">
				<li>${error}</li>
			</c:forEach>
		</ul>
		<form:hidden path="id" />

		<br>
		<br>
		<table>
			<% if (isAdmin) { %>
		  	<tr>
		  		<td>School:</td>
				<td><form:select path="" id="school">
	                	<form:option value="-1" label ="--- Please Select ---" />
	                	<% for (School s : schools) { %>
		                	<form:option value="<%= s.getId() %>" label ="<%= s.getName() %>"
		                	selected='<%=((!add && s.getId().equals(school))?"selected":"") %>' />
	                    <% } %>
	                </form:select>
				<form:errors path="" cssClass="errors" /></td>
            </tr>
          	<tr class'<%=((add)?"hidden":"") %>' id="class">
				<td>Class:</td>
				<td><form:select path="classroomId">
						<form:option value="-1" label="--- Please select ---" />
	                	<% for (Classroom c : classrooms) { %>
							<form:option value="<%= c.getId() %>"
							label="<%= c.getClassName() %>"
			                selected='<%=((!add && c.getId().equals(classroom))?"selected":"") %>' />
	                    <% } %>
					</form:select>
				<form:errors path="classroomId" cssClass="errors" /></td>
			</tr>
          <tr><td><br></td><td></td></tr> 
          <% } else { %>
          	<input type="hidden" value="<%= school %>" />
          	<form:hidden path="classroomId" />
          <% } %>
          <tr>
           <td>First Name:</td>
           <td>
           	<form:input type='text' path='fname' />
			<form:errors path="fname" cssClass="errors" />
		   </td>
	      </tr>
          <tr>
          	<td>Middle Initial:</td>
          	<td>
          		<form:input type='text' path='mname' size="3" />
			<form:errors path="mname" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Last Name:</td>
          	<td>
          		<form:input type='text' path='lname' />
			<form:errors path="lname" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Date of Birth:</td>
          	<td>
          		<form:input type='text' path='bdate' />
			<form:errors path="bdate" cssClass="errors" />
          		<i>NOTE: format mm/dd/yyyy</i>
          	</td>
          </tr>
          <tr>
          	<td>Gender:</td>
          	<td>
          		<form:radiobutton path="gender" value="M" />Male 
          		<form:radiobutton path="gender" value="F" />Female 
			<form:errors path="mname" cssClass="errors" />
          	</td>
          <tr>
          
          <tr><td><br></td><td></td></tr>
		  	<tr>
		  		<td>Event One:</td>
		  		<td>
	                <select id="pevent" name="pevent">
	                	<option value="-1" <%= (pe == -1L)?"selected":"" %>>None</option>
	                	<%  String units = "";
	                		for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ((pe.equals(e.getId()))?"selected":"") + ">" + e.getName() + "</option>" %>
	                    <% units += "<input type=\"hidden\" id=\"" + e.getId() + "\" value=\"" + e.getUnits() + "\"/>"; %>
	                    <% } %>
	                    <%=units %>
	                </select>
	                <span id="pscore" class="<%=hidden%>">
	                	Score: <input type="text" name="pscore" size="10" value="<%= (pscore.equals(-1.0))?"":pscore %>"/>
	                	<i><span id="punits"></span></i>
	                </span>
	                <input type="hidden" name="pregid" value="<%= pregid %>" />
            	</td>
            </tr>
            
            <% 	 %>
            
            <tr id="sevent" class="<%=hidden%>">
            	<td>Event Two:</td>
            	<td>
	                <select id="ssevent" name="sevent">
	                	<option value="-1" <%= (se == -1L)?"selected":"" %>>None</option>
	                	<%  units = "";
	                		for (Event e : events) { %>
		                    <%="<option value='" + e.getId() + "'" + ((se.equals(e.getId()))?"selected":"") + ">" + e.getName() + "</option>" %>
		                    <% units += "<input type=\"hidden\" id=\"" + e.getId() + "\" value=\"" + e.getUnits() + "\"/>"; %>
	                    <% } %>
	                    <%=units %>
	                </select>
	                <span id="sscore" class="hidden">
	                	Score: <input type="text" name="sscore" size="10" value="<%= (sscore.equals(-1.0))?"":sscore %>"/>
	                	<i><span id="sunits"></span></i>
	                </span>
            	</td>
            	<input type="hidden" name="sregid" value="<%= sregid %>" />
            </tr>
          <tr><td><br></td><td></td></tr>
          
          <tr>
           <td colspan="2">
           	<input type="submit" value="Save"/>
           	<button id="cancel">Cancel</button>
           </td>
          </tr>
          </table>
      </form:form>

    	</div>
    </div>
  </div>
</section>

<tag:footer/>