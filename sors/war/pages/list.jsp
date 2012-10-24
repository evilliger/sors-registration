<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Contact" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<html>
<body>
	<h1>CpS 420 Proof of Concept Application v2</h1>
	<tag:header/>
	Function : <a href="add">Add Contact</a>
	<hr />

	<h2>All Contacts</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Name</td>
				<td>Email</td>
				<td>Created Date</td>
				<td>Action</td>
			</tr>
		</thead>
		
		<%
		  if(request.getAttribute("contactList")!=null){						
			List<Contact> contacts = (List<Contact>)request.getAttribute("contactList");						
				if(!contacts.isEmpty()){
					for(Contact c : contacts){
				%>
					<tr>
					  <td><%=c.getName() %></td>
					  <td><%=c.getEmail() %></td>
					  <td><%=c.getDate() %></td>
					  <td><a href="update?id=<%=c.getId()%>&name=<%=c.getName()%>&email=<%=c.getEmail()%>">Update</a> 
		                   | <a href="delete?id=<%=c.getId()%>&name=<%=c.getName()%>&email=<%=c.getEmail()%>">Delete</a></td>
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
	</table>
</body>
</html>