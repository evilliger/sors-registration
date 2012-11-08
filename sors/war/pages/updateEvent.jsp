<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="update">
			<%
			Event e = new Event();
			
				if(request.getAttribute("event")!=null){
				
					e = (Event)request.getAttribute("event");
			
				}
		%>
		<table>
            <tr><td>Event Name:</td><td><input type="text" name="name" value="<%=e.getName() %>"/></td></tr>
            <tr><td>Event Units:</td><td><input type="radio" name="units" value="F" <%=(e.getUnits().equals("F"))?"checked":"" %>/>Feet
            	<input type="radio" name="units" value="M" <%=(e.getUnits().equals("M"))?"checked":"" %>/>Meters
            	<input type="radio" name="units" value="S" <%=(e.getUnits().equals("S"))?"checked":"" %>/>Seconds
            </td></tr>
            <tr><td>Min Score:</td><td><input type="text" name="min" value="<%=e.getMin() %>"/></td></tr>
            <tr><td>Max Score:</td><td><input type="text" name="max" value="<%=e.getMax() %>"/></td></tr>
			<input type="hidden" name="id" value="<%=e.getId() %>">
           	
            <tr><td colspan="2"><input type="submit" value="Save"/>
            <button id="cancel"  onClick="document.location.href='list'; return false;">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>