<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.EventConflict" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="update">
			<%
			EventConflict ec = new EventConflict();
			if(request.getAttribute("eventconflict")!=null){
				ec = (EventConflict)request.getAttribute("eventconflict");
			}
			Long e1 = ec.getE1Id();
			Long e2 = ec.getE2Id();
			
			List<Event> events = (List<Event>)request.getAttribute("events");
		%>
		<table>

		  	<tr>
		  		<td>Event One:</td>
		  		<td>
	                <select id="e1Id" name="e1Id">
	                	<option value="-1" <%= (e1 == -1L)?"selected":"" %>>None</option>
	                	<%  for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ((e1.equals(e.getId()))?"selected":"") + ">" + e.getName() + "</option>" %>
	                    <% } %>
	                </select>
            	</td>
            </tr>
		  	<tr>
		  		<td>Event Two:</td>
		  		<td>
	                <select id="e2Id" name="e2Id">
	                	<option value="-1" <%= (e2 == -1L)?"selected":"" %>>None</option>
	                	<%  for (Event e : events) { %>
	                    <%="<option value='" + e.getId() + "'" + ((e2.equals(e.getId()))?"selected":"") + ">" + e.getName() + "</option>" %>
	                    <% } %>
	                </select>
            	</td>
            </tr>
			
            <tr><td colspan="2"><input type="submit" value="Save"/>
            <button id="cancel"  onClick="document.location.href='list'; return false;">Cancel</button></td></tr>

		    </table>
			<input type="hidden" name="id" value="<%=ec.getId() %>">
          </form>
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>