<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Event" %>
<%@ page import="com.registration.sors.model.User" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<% Boolean isAdmin = ((User)request.getAttribute("user")).getRole().equals("A");  %>
<tag:header admin="<%= isAdmin.toString() %>"/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="add">
		<table>
            <tr><td>Event Name:</td><td><input type="text" name="name" /></td></tr>
            <tr><td>Event Units:</td><td><input type="radio" name="units" value="F"/>Feet
            	<input type="radio" name="units" value="M"/>Meters
            	<input type="radio" name="units" value="S"/>Seconds
            </td></tr>
            <tr><td>Min Score:</td><td><input type="text" name="min" /></td></tr>
            <tr><td>Max Score:</td><td><input type="text" name="max" /></td></tr>
            

            <tr><td colspan="2"><input type="submit" value="Save"/><button id="cancel">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>