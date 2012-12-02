<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="com.registration.sors.model.*" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>


<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %> 
<tag:header/>

 <section id="content">
  	<div class="container_24">
    	<div class="row">
      	<div class="wrapper">
    
		<% if(request.getAttribute("error") == ""){	
			
			Dictionary<String,Athlete>athDictionary = (Dictionary<String,Athlete>)request.getAttribute("athDictionary");
			Dictionary<String,Event>eventDictionary= (Dictionary<String,Event>)request.getAttribute("eventDictionary");
			Dictionary<String,List<Registration>>regDictionary= (Dictionary<String,List<Registration>>)request.getAttribute("regDictionary");
			List<Heat>hList= (List<Heat>)request.getAttribute("heatList");	
			if(!hList.isEmpty()){
				for(int i = 0; i < hList.size(); ++i){
					Heat h = hList.get(i);
					List<Registration>rList = regDictionary.get(h.getId().toString());
					
					if(rList != null){
					%>
						<table style="text-align:left;"><tr> 
						<th><%=eventDictionary.get(h.getEventID().toString()).getName()%></th>
						<th>Group <%=Integer.toString(h.getMinAge())%> to <%=Integer.toString(h.getMaxAge())%></th>
						<th>Time <%=h.getTime().toString()%></th>
						<th>Gender: <%=h.getGender()%></th>
						</tr>
					
						<tr>
						<th>Name</th>
						<th>Age</th>
						<th>Sex</th>
						<th>Rank</th>
						<th>Score</th>
						<th>Div</th>
						</tr>
					<%

					
					
						Collections.sort(rList, new Comparator<Registration>() {
						    public int compare(Registration r1, Registration r2) {
						    	if(r1.getRank() <= r2.getRank()){
						    		return -1;
						    	}else{
						    		return 1;
						    	}
						    }
						});
					
						for(int j = 0; j < rList.size(); ++j){
							Registration r = rList.get(j);
							Athlete a = athDictionary.get(r.getAthleteID().toString());
							Calendar cal1 = new GregorianCalendar();
						    Calendar cal2 = new GregorianCalendar();
						    int age = 0;
						    int factor = 0; 
						    cal1.setTime(a.getBdate());
						    cal2.setTime(new Date());
						    if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
						          factor = -1; 
						    }
						    age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
	
							%>
								<tr>
								<td><%=a.getFname()%> <%=a.getLname()%></td>
								<td><%=age%></td>
								<td><%=a.getGender()%></td>
								<td><%=r.getRank()%></td>
								<td><%=r.getScore()%></td>
								<td><%=h.getDivision()%></td></tr>
							<%
						}
					}
				}
			}
		   	}else{
		   		%>
		   		<tr>
				<td>There was a complication during Heat Generation.</td>
				</tr>
				<%
		   	}
		%>
		</table>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>