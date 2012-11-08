<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Events</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Units</th>
                    <th>Min Score</th>
                    <th>Max Score</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("eventList")!=null){						
			List<Event> events = (List<Event>)request.getAttribute("eventList");						
				if(!events.isEmpty()){
					for(Event e : events){
				%>
					<tr>
					  <td><%=e.getName()%></td>
					  <td><%=e.getUnits()%></td>
					  <td><%=e.getMin()%></td>
					  <td><%=e.getMax()%></td>
					<td ><button onClick="document.location.href='update?id=<%=e.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=e.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Event</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>