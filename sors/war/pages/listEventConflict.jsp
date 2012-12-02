<%@ page import="java.util.List" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.EventConflict" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Event Conflicts</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Event One</th>
                    <th>Event Two</th>
                </tr>
            </thead>
		
		<%
		  Dictionary<Long, String> eventNames = (Dictionary<Long, String>)request.getAttribute("eventNames");
		  if (eventNames == null) eventNames = new Hashtable<Long, String>();  
		
		  if(request.getAttribute("eventConflictList")!=null){						
			List<EventConflict> eventconflicts = (List<EventConflict>)request.getAttribute("eventConflictList");						
				if(!eventconflicts.isEmpty()){
					for(EventConflict ec : eventconflicts){
				%>
					<tr>
					  <td><%=eventNames.get(ec.getE1Id())%></td>
					  <td><%=eventNames.get(ec.getE2Id())%></td>
					<td ><button onClick="document.location.href='update?id=<%=ec.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=ec.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Conflict</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>