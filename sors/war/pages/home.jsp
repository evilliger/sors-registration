
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tag:header/>
  <!-- content -->
    <ul style="text-align:left; padding:10px;">
    </ul>
<section id="login" class="main">
    <h2>Greenville Recreation Department</h2>
    <h3>Special Olympics Registration System</h3>
    <br><br>
    <form action="/user/login" method="POST">
        <table class="center">
        	<tr class="error" ><td colspan="2"><c:out value="${AuthError}" /></td></tr>
            <tr><td>Username:</td><td><input type="text" name="username"></td></tr>
            <tr><td>Password:</td><td><input type="password" name="pswd"></td></tr>
            <tr><td></td><td><input type="Submit" value="Login"></td></tr>
        </table>
    </form><br>
    <a href="#">Request Access</a>
</section>
<tag:footer/>