<%@ page import="java.util.List" %>
<%@ page import="com.registration.sors.model.Athlete" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

  <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
    
        <h3>Athletes 2012</h3>
        
        
        <table id="ath_table" class="tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>School</th>
                    <th>Event One</th>
                    <th>Event Two</th>
                    <th>Complete</th>
                    <th>Options</th>
                </tr>
            </thead>
		
		<%
		  if(request.getAttribute("athleteList")!=null){						
			List<Athlete> athletes = (List<Athlete>)request.getAttribute("athleteList");						
				if(!athletes.isEmpty()){
					for(Athlete a : athletes){
				%>
					<tr>
					  <td><%=a.getLname()%>, <%=a.getFname()%></td>
					  <td>--school--</td>
					  <td>--event1--</td>
					  <td>--event2--</td>
                    <td><img src="../img/no.png" class="confirm" alt="no">No</img></td>
					<td ><button onClick="document.location.href='update?id=<%=a.getId()%>'">Edit</button>
					<button onClick="document.location.href='delete?id=<%=a.getId()%>'" class="del">Delete</button></td>
                    
					</tr>
		         <%
			
					}
		    
				}
			
		   	}
		%>
		</table>
        <button onClick="document.location.href='add'">Add Athlete</button>

      	</div>
      </div>
    </div>
  </section>

<tag:footer/>