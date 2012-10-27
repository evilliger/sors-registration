<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tag:header/>
	<% 
		if(request.getAttribute("TableData")!=null){						
			List<Object> TableData = (List<Object>)request.getAttribute("TableData");						
			if(!TableData.isEmpty()){
				for(Object o : TableData){
					%>
					<%=o.toString()%><br/>
					<%
				}
			}
		}
	%>
<tag:footer/>