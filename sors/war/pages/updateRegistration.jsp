<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Registration" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="update">
			<%
			Registration r = new Registration();
			
				if(request.getAttribute("registration")!=null){
				
					r = (Registration)request.getAttribute("registration");
			
				}
		%>
		<table>
            <tr><td>Athlete ID:</td><td><input type="text" name="athleteID" value="<%=r.getAthleteID() %>"/></td></tr>
            <tr><td>Event ID:</td><td><input type="text" name="eventID" value="<%=r.getEventID() %>"/></td></tr>
            <tr><td>Score:</td><td><input type="text" name="score" value="<%=r.getScore() %>"/></td></tr>
            <tr><td>Rank:</td><td><input type="text" name="rank" value="<%=r.getRank() %>"/></td></tr>
            <input type="hidden" name="id" value="<%=r.getId() %>">
            <tr><td colspan="2"><input type="submit" value="Save"/>
            <button id="cancel"  onClick="document.location.href='list'; return false;">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>