<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ page import="com.registration.sors.model.School" %>
<%@ page import="com.registration.sors.model.Classroom" %>
<%@ page import="com.registration.sors.model.HeatSpec" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

<section id="content">
 	<div class="container_24">
   	<div class="row">
     	<div class="wrapper">
     	
     	<% 	boolean add = (Boolean)request.getAttribute("add");
     		List<Event> events = (List<Event>)request.getAttribute("events");
			Long evId = (Long)request.getAttribute("evid");
     	%>

		<form:form commandName='heatspec' action='<%= (add) ? "add": "update" %>'>
		
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
		  	<tr>
		  		<td>Event:</td>
		  		<td>
	                <select id="event" name="eventId">
	                	<option value="-1" <%= (evId == -1L)?"selected":"" %>>None</option>
	                	<%  for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ((evId.equals(e.getId()))?"selected":"") + ">" + e.getName() + "</option>" %>
	                    <% } %>
	                </select>
	           	</td>
           </tr>
          <tr>
          	<td>Gender:</td>
          	<td>
          		<form:radiobutton path="gender" value="M" />Male 
          		<form:radiobutton path="gender" value="F" />Female 
			<form:errors path="gender" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Minimum Age:</td>
          	<td>
          		<form:input type='text' path='minAge' />
			<form:errors path="minAge" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Maximum Age:</td>
          	<td>
          		<form:input type='text' path='maxAge' />
			<form:errors path="maxAge" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Time:</td>
          	<td>
          		<form:input type='text' path='time' />
			<form:errors path="time" cssClass="errors" />
          		<i>NOTE: format hh:mm</i>
          	</td>
          </tr>
          <tr>
          	<td>Number of Heats:</td>
          	<td>
          		<form:input type='text' path='numHeats' />
			<form:errors path="numHeats" cssClass="errors" />
          	</td>
          </tr>
          <tr>
          	<td>Maximum Heat Size:</td>
          	<td>
          		<form:input type='text' path='maxInHeat' />
			<form:errors path="maxInHeat" cssClass="errors" />
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