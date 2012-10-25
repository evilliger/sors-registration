<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="add">
		<table>
            <tr><td>Title:</td><td><input type="text" name="title" /></td></tr>
            <tr><td>First Name:</td><td><input type="text" name="fname" /></td></tr>
            <tr><td>Last Name:</td><td><input type="text" name="lname" /></td></tr>
            <tr><td>Fax:</td><td><input type="text" name="fax" /></td></tr>
            <tr><td>E-mail:</td><td><input type="text" name="email" /></td></tr>
            <tr><td>Password:</td><td><input type="text" name="pword" /></td></tr>
           	<tr><td>Role:</td><td><input type="radio" name="role" value="A"/>Admin
           		<input type="radio" name="role" value="T"/>Teacher</td><tr>
           	<tr><td>Active:</td><td><input type="checkbox" name="active"/>Active</td><tr>
            <tr><td colspan="2"><input type="submit" value="Save"/><button id="cancel">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>