<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="com.registration.sors.model.*" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

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
			//if(!hList.isEmpty()){
				for(int i = 0; i < hList.size(); ++i){
					Heat h = hList.get(i);
					
					%>
						<table style=\"text-align:left;\"><tr> 
						<th>eventList.get(h.getEventID().toString()).getName()</th>
						<th>Group Integer.toString(h.getMinAge()) to Integer.toString(h.getMaxAge())</th>
						<th>Time h.getTime().toString()</th>
						<th>Gender: h.getGender()</th>
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
					List<Registration>rList = regDictionary.get(h.getId().toString());
					
					if(rList != null){
						Collections.sort(rList, new Comparator<Registration>() {
						    public int compare(Registration r1, Registration r2) {
						    	if(r1.getRank() <= r2.getRank()){
						    		return -1;
						    	}else{
						    		return 1;
						    	}
						    }
						});
					}
					for(int j = 0; j < rList.size(); ++j){
						Registration r = rList.get(j);
						Athlete a = athDictionary.get(r.getAthleteID().toString());
						%>
							<tr>
							<td>a.getFname() a.getLname()</td>
							<td>age(a.getBdate())</td>
							<td>a.getGender()</td>
							<td>r.getRank()</td>
							<td>r.getScore()</td>
							<td>h.getDivision()</td></tr>
						<%
					
					}
				}
			}
		   	//}else{
		   		%>
		   		<tr>
				<td>There was an error</td>
				</tr>
				<%
		   //	}
		%>
    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>