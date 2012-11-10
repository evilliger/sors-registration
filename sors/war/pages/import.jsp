<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tag:header/>
	
	<div>
		<form action="/import/add" method="POST">
		<table class="center">
		<tr>
				
				<td colspan="2">
					<h3 class="aligncenter">SORS Import Utility</h3>
				</td>
				<tr>
				
				<td colspan="2" class="aligncenter">
					<p class="errors"><c:out value="${error}"></c:out></p>
					<h4><c:out value="${success}"></c:out></h4>
				</td>
			</tr>
			</tr>
			<tr>
				<td class="aligncenter">
					<label for="table">Table to Import</label>
					<select name="table">
						<option value="athlete">Athlete</option>
						<option value="classroom">Classroom</option>
						<option value="event">Event</option>
						<option value="heat">Heat</option>
						<option value="heatspec">HeatSpec</option>
						<option value="registration">Registration</option>
						<option value="school">School</option>
						<option value="user">User</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="aligncenter" colspan="2">
					CSV Data
				</td>
			</tr>
			<tr>
				
				<td colspan="2">
					<textarea name="csv" rows="20" cols="100"></textarea>
				</td>
			</tr>
			<tr>
				<td class="aligncenter" colspan="2">
					<input type="submit"/>
				</td>
			</tr>
		</table>
	    </form>
	</div>
<tag:footer/>