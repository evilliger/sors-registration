<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.HeatSpec" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="add">
		<table>
			<tr><td>Event:</td><td>
			    <select id="eventId" name="eventId">
			        <option value=""></option>
			        <option value="179">50 Meter Dash</option>
			        <option value="180">100 Meter Flash</option>
			        <option value="14">Sock Challenge</option>
			        
			    </select>
			</td></tr>
           	<tr><td>Gender:</td><td><input type="radio" name="gender" value="M"/>Male
           		<input type="radio" name="gender" value="F"/>Female</td><tr>
            <tr><td>Minimum Age:</td><td><input type="text" name="minAge" /></td></tr>
            <tr><td>Maximum Age:</td><td><input type="text" name="maxAge" /></td></tr>
            <tr><td>Time:</td><td><input type="text" name="time" /></td></tr>
            <tr><td>Number of Heats:</td><td><input type="text" name="numHeats" /></td></tr>
            <tr><td>Maximum Heat Size:</td><td><input type="text" name="maxInHeat" /></td></tr>
            <tr><td colspan="2"><input type="submit" value="Save"/><button id="cancel">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>