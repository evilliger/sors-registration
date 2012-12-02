<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Users</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Options</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("userList")!=null){						
			List<User> users = (List<User>)request.getAttribute("userList");						
				if(!users.isEmpty()){
					for(User u : users){
				%>
					<tr>
					  <td><%=u.getTitle()%> <%=u.getFname()%> <%=u.getLname()%></td>
					  <td><%=u.getEmail()%></td>
					  <td><%=(u.getRole().equals("T"))?"Teacher":"Admin"%></td>
					<td ><button onClick="document.location.href='update?id=<%=u.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=u.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add User</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>