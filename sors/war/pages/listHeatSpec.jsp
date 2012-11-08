<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.HeatSpec" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Heat Specification:</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>EventId</th>
                    <th>Gender</th>
                    <th>Age</th>
                    <th>Time</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("heatSpecList")!=null){						
			List<HeatSpec> hslist = (List<HeatSpec>)request.getAttribute("heatSpecList");						
				if(!hslist.isEmpty()){
					for(HeatSpec h : hslist){
				%>
					<tr>
					  <td><%=h.getEventID()%></td>
					  <td><%=h.getGender()%></td>
					  <td><%=h.getMinAge()%> - <%=h.getMaxAge()%></td>
					  <td><%=h.getTime()%></td>
					<td ><button onClick="document.location.href='update?id=<%=h.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=h.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Heat Specification</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>