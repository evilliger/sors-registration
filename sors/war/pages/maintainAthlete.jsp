<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tag:header/>

<section id="content">
 	<div class="container_24">
   	<div class="row">
     	<div class="wrapper">

		<form:form commandName='athlete' action='<%=(request.getAttribute("add") != null) ? "add": "update" %>'>
		
		<form:errors path="*" >
			<ul class="errors">
				<c:forEach items="${messages}" var="message">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</form:errors>
		
		<form:hidden path="id" />

		<br>
		<br>
		<table>
          <tr><td>School:</td><td>
              <select id="school" name="school">
                  <option value=""></option>
                  <option value="1">Bob Jones University</option>
                  <option value="11">University of Southern Kansas</option>
                  <option value="14">Central Michigan College</option>
              </select>
          </td></tr>
          <tr id="class" class="hidden"><td>Class:</td><td>
              <select id="class" name="class">
                  <option value=""></option>
                  <option value="1">6th Grade</option>
                  <option value="11">7th Grade</option>
                  <option value="14">8th Grade</option>
              </select>
          </td></tr>
          
          <tr><td><br></td><td></td></tr>
          
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
          
          <tr><td>Event One:</td><td>
              <select id="pevent" name="pevent">
                  <option value="">None</option>
                  <option value="1">50 Meter Dash</option>
                  <option value="11">Running Long Jump</option>
                  <option value="14">Shot Put</option>
              </select>
              <span id="pscore" class="hidden">Score: <input type="text" name="pscore" size="10"/><i><span id="punits"></span></i></span>
          </td></tr>
          <tr id="sevent" class="hidden"><td>Event Two:</td><td>
              <select id="sevent" name="sevent">
                  <option value="">None</option>
                  <option value="1">50 Meter Dash</option>
                  <option value="11">Running Long Jump</option>
                  <option value="14">Shot Put</option>
              </select>
              <span id="sscore" class="hidden">Score: <input type="text" name="sscore" size="10"/><i><span id="sunits"></span></i></span>
          </td></tr>
          
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