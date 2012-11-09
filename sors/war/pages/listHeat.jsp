<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Heat" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Heats</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("heatList")!=null){						
			List<Heat> heats = (List<Heat>)request.getAttribute("heatList");						
				if(!heats.isEmpty()){
					for(Heat h : heats){
				%>
					<tr>
					  <td><%=h.getId()%></td>
					  
					<td ><button onClick="document.location.href='update?id=<%=h.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=h.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Heat</button>
		<button onClick="document.location.href='generate'">Generate Heats</button>
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>