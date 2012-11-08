<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.HeatSpec" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="update">
			<%
			HeatSpec h = new HeatSpec();
			
				if(request.getAttribute("heatspec")!=null){
				
					h = (HeatSpec)request.getAttribute("heatspec");
			
				}
		%>
		<table>
           	<tr><td>Gender:</td><td><input type="radio" name="gender" value="M" <%=(h.getGender().equals("M"))?"checked":"" %> />Male
           		<input type="radio" name="gender" value="F" <%=(h.getGender().equals("F"))?"checked":"" %>/>Female</td><tr>
            <tr><td>Minimum Age:</td><td><input type="text" name="fname" value="<%=h.getMinAge() %>"/></td></tr>
            <tr><td>Maximum Age:</td><td><input type="text" name="lname" value="<%=h.getMaxAge() %>"/></td></tr>
            <tr><td>Time:</td><td><input type="text" name="fax" value="<%=h.getTime() %>"/></td></tr>
            <tr><td>Number of Heats:</td><td><input type="text" name="email" value="<%=h.getNumHeats() %>"/></td></tr>
            <tr><td>Maximum Heat Size:</td><td><input type="text" name="pword" value="<%=h.getMaxInHeat() %>"/></td></tr>
           	<input type="hidden" name="id" value="<%=h.getId() %>">
            <tr><td colspan="2"><input type="submit" value="Save"/>
            <button id="cancel"  onClick="document.location.href='list'; return false;">Cancel</button></td></tr>
        </form>
    </table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>