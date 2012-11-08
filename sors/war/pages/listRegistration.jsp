<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Registration" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Registrations</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Athlete ID</th>
                    <th>Event ID</th>
                    <th>Score</th>
                    <th>Rank</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("registrationList")!=null){						
			List<Registration> registrations = (List<Registration>)request.getAttribute("registrationList");						
				if(!registrations.isEmpty()){
					for(Registration r : registrations){
				%>
					<tr>
					  <td><%=r.getAthleteID()%></td>
					  <td><%=r.getEventID()%></td>
					  <td><%=r.getScore()%></td>
					  <td><%=r.getRank()%></td>
					<td ><button onClick="document.location.href='update?id=<%=r.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=r.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Registration</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>