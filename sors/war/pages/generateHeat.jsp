<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Heat" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
	<form method="post" action="generate">
		<h3>Warning: This action will delete all of the current heats</h3>

		<% if(request.getAttribute("errors")!=null){						
			List<String> errorList = (List<String>)request.getAttribute("errors");						
				if(!errorList.isEmpty()){
					for(String s : errorList){
						
					%>
						<tr>
						<td><%=s%> </br></td>
						</tr>
		         	<%
					}
				}
		   	}
		%>
            <input type="submit" value="Generate"/><button id="cancel">Cancel</button>
    </form>

    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>