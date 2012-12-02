<%@ page import="java.util.List" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="com.registration.sors.model.Heat" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Heats</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Event</th>
                    <th>Age Group</th>
                    <th>Time</th>
                    <th>Gender</th>
                    <th>Division</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("heatList")!=null){						
			List<Heat> heats = (List<Heat>)request.getAttribute("heatList");
			Dictionary<String,String> events = (Dictionary<String,String>)request.getAttribute("eList");
				if(!heats.isEmpty()){
					for(Heat h : heats){
				%>
					<tr>
					  <td><%=events.get(h.getEventID().toString())%></td>
					  <td><%=h.getMinAge()%> to <%=h.getMaxAge()%></td>
					  <td><%=h.getTime()%></td>
					  <td><%=h.getGender()%></td>
					  <td><%=h.getDivision()%></td>
					  
					<td ><button onClick="document.location.href='update?id=<%=h.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=h.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Heat</button>
		<button onClick="document.location.href='generate'">Generate Heats</button>
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>