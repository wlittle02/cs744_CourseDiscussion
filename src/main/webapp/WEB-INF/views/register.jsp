<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.png">

<title>Online Course Discussion System</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="resources/css/bootstrap.css"/>" rel="stylesheet">


</head>

<body>
	<div id="wrap">	   
<div class="navbar navbar-default navbar-fixed-top">
	        <div class="container">
	          <div class="navbar-header">
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="#">Online Course Discussion System</a>
	          </div>
	          <div class="collapse navbar-collapse">
	            <ul  class="nav navbar-nav">	              
	             <li class="active"><a >Registration</a></li>
	              <li><a href="<c:url value="/viewinstructors"/>">Instructors</a></li>     
	              <li><a href="<c:url value="/logout"/>" >Log Out</a> </li>	
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
		</br>
		<div class="container">
			
			<div class="page-header">
				<h4>Instructor Registration</h4>
			</div>
			
		
			
			<form name="formregister" class="form-horizontal" role="form" action="<c:url value="/register"/>" method="post" onsubmit="return validate();">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">First Name *</label>
				<div class="col-sm-6">
					<input name="firstName" id="firstname" type="text" class="form-control" required/>
				</div>
			</div>	
			<div class="form-group">
				<label for="lname" class="col-sm-2 control-label">Last Name *</label>
				<div class="col-sm-6">
					<input name="lastName" id="lastname" type="text" class="form-control"  required/>
				</div>
			</div>					
			
			<div class="form-group">
				<label for="UserNameInput" class="col-sm-2 control-label" >User Name *</label>
				<div class="col-sm-6">
					<input name="userName" id="username" type="text" class="form-control" required/>
				</div>
			</div>
			<div class="form-group">
				<label for="passwordInput" class="col-sm-2 control-label">Password *</label>
				<div class="col-sm-6">
					<input name="password" id="password" type="password" class="form-control" required/>
				</div>	
			</div>	
			<div class="form-group">
				<label for="repasswordInput" class="col-sm-2 control-label">Re-Type Password *</label>				
				<div class="col-sm-6">
					<input id="repassword" type="password" class="form-control" required/>
				</div>	
			</div>			
			<div class="form-group">
				<label for="emailInput" class="col-sm-2 control-label">Email Address</label>
				<div class="col-sm-6">
					<input name="email" id="emailInput" type="email" class="form-control" required/>
				</div>
			</div>			
			  <c:if test="${regerror}">
     			 <div class="alert alert-danger">User Name already exists. Try another one.</div>
      		</c:if>    
      		 					
 			<div class="form-group">
 				<div class="col-sm-3" align="right">
				<button type="submit" class="btn btn-primary" >Create Account</button>
				</div>
			</div>			 				
			</form>
		</div>
	</div>
    <script>
    function validate() {
        var d = document.formregister;
        if((d.password.value != '') && (d.repassword.value == d.password.value)) {
            return true;
        }
        alert("Passwords not same");
        return false;
    }
    </script>
	

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	</body>
</html>

