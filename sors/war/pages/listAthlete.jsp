<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ page import="com.registration.sors.model.AthData" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3><%= (String)request.getAttribute("title") %></h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Event 1</th>
                    <th>Event 2</th>
                    <th>Complete</th>
                    <th>Options</th>
                </tr>
            </thead>
		
		<% if(request.getAttribute("athleteList")!=null){						
			List<AthData> athletes = (List<AthData>)request.getAttribute("athleteList");						
				if(!athletes.isEmpty()){
					for(AthData a : athletes){
						String c = (a.completed)?"yes":"no";
					%>
						<tr>
						<td><%=a.athlete.getLname()%>, <%=a.athlete.getFname()%></td>
						<td><%=a.event1%></td>
						<td><%=a.event2%></td>
	                    <td><img src="../img/<%= c %>.png" class="confirm" alt="<%= c %>">  <%= c %></img></td>
						<td ><button onClick="document.location.href='update?id=<%=a.athlete.getId()%>&classroomid=<%=a.athlete.getClassroomId()%>'">Edit</button>
						<button onClick="document.location.href='delete?id=<%=a.athlete.getId()%>&classroomid=<%=a.athlete.getClassroomId()%>'" class="del">Delete</button></td>
						</tr>
		         	<%
					}
				}
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Athlete</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>