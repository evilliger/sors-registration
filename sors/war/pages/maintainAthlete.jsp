<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ page import="com.registration.sors.model.School" %>
<%@ page import="com.registration.sors.model.Classroom" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tag:header/>

<section id="content">
 	<div class="container_24">
   	<div class="row">
     	<div class="wrapper">
     	
     	<% boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A"); %>

		<form:form commandName='athlete' action='<%= ((Boolean)request.getAttribute("add")) ? "add": "update" %>'>
		
		<form:errors path="*" >
			<ul class="errors">
				<c:forEach items="${messages}" var="message">
					<li>${message}</li>
				</c:forEach>
				<c:forEach items="${scores}" var="error">
					<li>${error}</li>
				</c:forEach>
				
			</ul>
		</form:errors>
		
		<form:hidden path="id" />

		<br>
		<br>
		<table>
			<% if (isAdmin) { %>
          		<% List<School> schools = (List<School>)request.getAttribute("schools"); %>
		  	<tr>
		  		<td>School:</td>
				<td><form:select path="" id="school">
	                	<form:option value="-1" label ="--- Please Select ---" />
	                	<% for (School s : schools) { %>
	                	<form:option value="<%= s.getId() %>" label ="<%= s.getName() %>" />
	                    <% } %>
	                </form:select>
				<form:errors path="" cssClass="errors" /></td>
            </tr>
          		<% List<Classroom> classrooms = (List<Classroom>)request.getAttribute("classrooms"); %>
          	<tr class="hidden" id="class">
				<td>Class:</td>
				<td><form:select path="classroomId">
						<form:option value="-1" label="--- Please select ---" />
	                	<% for (Classroom c : classrooms) { %>
						<form:option value="<%= c.getId() %>"
						label="<%= c.getClassName() %>" />
	                    <% } %>
					</form:select>
				<form:errors path="classroomId" cssClass="errors" /></td>
			</tr>
          <tr><td><br></td><td></td></tr> 
          <% } else { %>
          	<input type="hidden" value="<%= (Long)request.getAttribute("schoolID")%>" />
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
          
          <% List<Event> events = (List<Event>)request.getAttribute("events"); %>
          
		  	<tr>
		  		<td>Event One:</td>
		  		<td>
	                <select id="pevent" name="pevent">
	                	<option value="">None</option>
	                	<% for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'>" + e.getName() + "</option>" %>
	                    <input type="hidden" id="<%=e.getId()%>" value="<%=e.getUnits()%>"/>
	                    <% } %>
	                </select>
	                <span id="pscore" class="hidden">
	                	Score: <input type="text" name="pscore" size="10"/>
	                	<i><span id="punits"></span></i>
	                </span>
            	</td>
            </tr>
            
            <tr id="sevent" class="hidden">
            	<td>Event Two:</td>
            	<td>
	                <select id="ssevent" name="sevent">
	                	<option value="">None</option>
	                	<% for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'>" + e.getName() + "</option>" %>
	                    <input type="hidden" id="<%=e.getId()%>" value="<%=e.getUnits()%>"/>
	                    <% } %>
	                </select>
	                <span id="sscore" class="hidden">
	                	Score: <input type="text" name="sscore" size="10"/>
	                	<i><span id="sunits"></span></i>
	                </span>
            	</td>
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