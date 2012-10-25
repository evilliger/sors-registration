<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="update">
			<%
			User u = new User();
			
				if(request.getAttribute("user")!=null){
				
					u = (User)request.getAttribute("user");
			
				}
		%>
		<table>
            <tr><td>Title:</td><td><input type="text" name="title" value="<%=u.getTitle() %>"/></td></tr>
            <tr><td>First Name:</td><td><input type="text" name="fname" value="<%=u.getFname() %>"/></td></tr>
            <tr><td>Last Name:</td><td><input type="text" name="lname" value="<%=u.getLname() %>"/></td></tr>
            <tr><td>Fax:</td><td><input type="text" name="fax" value="<%=u.getFax() %>"/></td></tr>
            <tr><td>E-mail:</td><td><input type="text" name="email" value="<%=u.getEmail() %>"/></td></tr>
            <tr><td>Password:</td><td><input type="text" name="pword" value="<%=u.getPword() %>"/></td></tr>
           	<tr><td>Role:</td><td><input type="radio" name="role" value="A" <%=(u.getRole().equals("A"))?"checked":"" %> />Admin
           		<input type="radio" name="role" value="T" <%=(u.getRole().equals("T"))?"checked":"" %>/>Teacher</td><tr>
           	<tr><td>Active:</td><td><input type="checkbox" name="active"  <%=(u.getActive() != null)?"checked":"" %>/>Active</td><tr>
            <input type="hidden" name="id" value="<%=u.getId() %>">
            <tr><td colspan="2"><input type="submit" value="Save"/>
            <button id="cancel"  onClick="document.location.href='list'; return false;">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>