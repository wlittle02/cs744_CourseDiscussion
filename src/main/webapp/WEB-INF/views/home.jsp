<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.png">

<title>My PKU Diet Manager!</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
<script>
window.onload = function(){
	var d=new Date(document.getElementById("dt2").value);
	//d.setDate(d.getDate()+1);	
    var curr_date = d.getDate();
    
    var curr_month = d.getMonth() + 1; //Months are zero based
    var curr_year = d.getFullYear();
    var d=curr_month + "/" + curr_date + "/" + curr_year;	
	document.getElementById("dt2").value=d;	
	document.getElementById("dt3").value=d;
	document.getElementById("dt").value=d;
};
</script>



</head>

<body>
	<div id="wrap">

	      <!-- Fixed navbar -->
	      <div class="navbar navbar-default navbar-fixed-top">
	        <div class="container">
	          <div class="navbar-header">
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="#">My PKU Diet Manager</a>
	          </div>
	          <div class="collapse navbar-collapse">
	            <ul  class="nav navbar-nav">	              
	             <li class="active"><a >Food Diary</a></li>
	              <li><a href="<c:url value="/dietreport"/>">Diet Report</a></li>     
	              <li><a href="<c:url value="/logout"/>" >Log Out</a> </li>	
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>


		<div class="container">
		   
			<br/>
			<div class="page-header">
				<h2>Food Diary</h2>
			</div>
			 <div align ="right" class="userheader">Hello ${user}</div>
			<div class="form-group"> 
<%-- 			 --%>
			<form id ="frm1" class="form-horizontal"  action="<c:url value="/dated?date=${dt3}"/>" method="get"  >
			<input type="text" name="dt3" id="dt3" value="<c:out value="${date}"/>" style="display: none"  />
				<button type="button" id="btn1" class="btn btn-primary " onclick="prev()"  >&lt&lt</button>
				<input type="text" id="dt2" name="dt2" class="btn btn-primary " value="<c:out value="${date}"/>" disabled />
				<button type="button" id="btn3" class="btn btn-primary " onclick="next()" >&gt&gt</button>
			</form> 
			</div>	
			<form class="form-horizontal"  action="<c:url value="/add?date=${dt}"/>" method="get"   >
			<input type="text" name="dt" id="dt" value="<c:out value="${date}"/>" style="display: none"  />
			
			<br/>
			<br/>
			<table class="table table-striped table-bordered">
				<tr><th></th><th >Protein (g)</th><th>Phenylalanine (mg)</th><th>Delete</th></tr>
				
					<tr>					
						<th>Breakfast</th>
						<td></td>
						<td></td>
						<td></td>					
					</tr>	
					<c:forEach items="${dietRecords}" var="record">
					<c:if test="${record.meal=='Breakfast'}">				
						<tr>
							<td><c:out value="${record.item}" /></td>
							<td><c:out value="${record.protein}" /></td>
							<td><c:out value="${record.phenyl}" /></td>						
							<td><a href="delete?id=${record.id}">
							<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
						</tr>
					</c:if>	
					</c:forEach>
								
					<tr>					
						<th>Lunch</th>
						<td></td>
						<td></td>
						<td></td>					
					</tr>
					<c:forEach items="${dietRecords}" var="record">
					<c:if test="${record.meal=='Lunch'}">				
						<tr>
							<td><c:out value="${record.item}" /></td>
							<td><c:out value="${record.protein}" /></td>
							<td><c:out value="${record.phenyl}" /></td>						
							<td><a href="delete?id=${record.id}">
							<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
						</tr>
					</c:if>	
					</c:forEach>
					<tr>					
						<th>Dinner</th>
						<td></td>
						<td></td>
						<td></td>					
					</tr>
					<c:forEach items="${dietRecords}" var="record">
					<c:if test="${record.meal=='Dinner'}">				
						<tr>
							<td><c:out value="${record.item}" /></td>
							<td><c:out value="${record.protein}" /></td>
							<td><c:out value="${record.phenyl}" /></td>						
							<td><a href="delete?id=${record.id}">
							<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
						</tr>
					</c:if>	
					</c:forEach>
					<tr>					
						<th>Snacks</th>
						<td></td>
						<td></td>
						<td></td>					
					</tr>
					<c:forEach items="${dietRecords}" var="record">
					<c:if test="${record.meal=='Snacks'}">				
						<tr>
							<td><c:out value="${record.item}" /></td>
							<td><c:out value="${record.protein}" /></td>
							<td><c:out value="${record.phenyl}" /></td>						
							<td><a href="delete?id=${record.id}">
							<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
						</tr>
					</c:if>	
					</c:forEach>
				<tr><th>Total</th><th ><c:out value="${proteinTotal}" /></th><th><c:out value="${phenylTotal}" /></th><th></th></tr>
			</table>
			
			<div class="form-group">
 				<div class="col-sm-3" align="right">
				<button type="submit" class="btn btn-primary ">Add Food</button>
				</div>
			</div>	
			</form>
		</div>
	</div>
	<script>
	
	function prev()
	{
	var d=new Date(document.getElementById("dt2").value);
	d.setDate(d.getDate()-1);	
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; //Months are zero based
    var curr_year = d.getFullYear();
    var d=curr_month + "/" + curr_date + "/" + curr_year;	
	document.getElementById("dt2").value=d;	
	document.getElementById("dt3").value=d;
	document.getElementById("dt").value=d;
	document.getElementById("frm1").submit();
	}
	</script>


	<script>
	function next()
	{
		var d=new Date(document.getElementById("dt2").value);
		d.setDate(d.getDate()+1);	
		var todaysDate =new Date();
	    if (d >todaysDate)
	    	d.setDate(d.getDate()-1);
		var curr_date = d.getDate();
	    
	    var curr_month = d.getMonth() + 1; //Months are zero based
	    var curr_year = d.getFullYear();
	    var d=curr_month + "/" + curr_date + "/" + curr_year;	
		document.getElementById("dt2").value=d;	
		document.getElementById("dt3").value=d;
		document.getElementById("dt").value=d;
		document.getElementById("frm1").submit();
	}
	
</script>
</body>
</html>

