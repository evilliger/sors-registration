<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.EventConflict" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="add">
		<%
			List<Event> events = (List<Event>)request.getAttribute("events");
		%>
		<table>
		  	<tr>
		  		<td>Event One:</td>
		  		<td>
	                <select id="e1Id" name="e1Id">
	                	<option value="-1" selected>None</option>
	                	<%  for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ">" + e.getName() + "</option>" %>
	                    <% } %>
	                </select>
            	</td>
            </tr>
		  	<tr>
		  		<td>Event Two:</td>
		  		<td>
	                <select id="e2Id" name="e2Id">
	                	<option value="-1" selected>None</option>
	                	<%  for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ">" + e.getName() + "</option>" %>
	                    <% } %>
	                </select>
            	</td>
            </tr>
            <tr><td colspan="2"><input type="submit" value="Save"/><button id="cancel">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>