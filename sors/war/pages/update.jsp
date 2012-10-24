<%@ page import="com.registration.sors.model.Contact" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<html>
<body>
	<h1>Update contact</h1>
	
	<%
			Contact contact = new Contact();
			
				if(request.getAttribute("contact")!=null){
				
			contact = (Contact)request.getAttribute("contact");
			
				}
		%>
	
	<form method="post" action="../update" >
		<input type="hidden" name="id" id="id" 
			value="<%=contact.getId() %>" /> 
		
		<table>
			<tr>
				<td>
					UserName :
				</td>
				<td>
					<input type="text" style="width: 185px;" maxlength="30" name="name" id="name" 
						value="<%=contact.getName() %>" />
				</td>
			</tr>
			<tr>
				<td>
					Email :
				</td>
				<td>
					<input type="text" style="width: 185px;" maxlength="30" name="email" id="email" 
						value="<%=contact.getEmail() %>" />
				</td>
			</tr>
		</table>
		<input type="submit" class="update" title="Update" value="Update" />
	</form>
	
</body>
</html>