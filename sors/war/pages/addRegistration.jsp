<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Registration" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="add">
		<table>
            <tr><td>Athlete ID:</td><td><input type="text" name="athleteID" /></td></tr>
            <tr><td>Event ID:</td><td><input type="text" name="eventID" /></td></tr>
            <tr><td>Score:</td><td><input type="text" name="score" /></td></tr>
            <tr><td>Rank:</td><td><input type="text" name="rank" /></td></tr>
            <tr><td colspan="2"><input type="submit" value="Save"/><button id="cancel">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>