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
		<h4>something about athletes not being complete</h4>
            <input type="submit" value="Generate"/><button id="cancel">Cancel</button>
    </form>

    
      	</div>
      </div>
    </div>
  </section>

<tag:footer/>