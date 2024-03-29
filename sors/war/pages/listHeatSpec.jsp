<%@ page import="java.util.List" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="com.registration.sors.model.HeatSpec" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Heat Specification:</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>EventId</th>
                    <th>Gender</th>
                    <th>Age</th>
                    <th>Time</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("heatSpecList")!=null){						
			List<HeatSpec> hslist = (List<HeatSpec>)request.getAttribute("heatSpecList");						
			Dictionary<String,String> events = (Dictionary<String,String>)request.getAttribute("eventList");
				if(!hslist.isEmpty()){
					for(HeatSpec h : hslist){
				%>
					<tr>
					  <td><%=events.get(h.getEventId().toString())%></td>
					  <td><%=h.getGender()%></td>
					  <td><%=h.getMinAge()%> - <%=h.getMaxAge()%></td>
					  <td><%=h.getTime()%></td>
					<td ><button onClick="document.location.href='update?id=<%=h.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=h.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Heat Specification</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>