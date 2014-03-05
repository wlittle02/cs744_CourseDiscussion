<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">

    <title>Online Discussion Login</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/css/login.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
	<h1 align="center">Online Course Discussion System</h1>
    <div class="container container1" >    
     <div class="content">     
     <c:if test ="${error == null || error=='true'}">   
      <form class="form-signin" action="<c:url value="/j_spring_security_check"/>" method="post">
        <h2 class="form-signin-heading">Login</h2>
        <input type="text" class="form-control" placeholder="User Name" autofocus name="j_username" required>   
        <input type="password" class="form-control" placeholder="Password" name="j_password" required>
        
      <c:if test="${error}">
      <div class="alert alert-danger">Login failed. Try Again</div>
      </c:if>    
          
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
      </c:if>
       <c:if test="${error=='false'}">
      
      
      <h2 class="form-signin-heading">Login Success</h2>
      <h4 class="form-signin-heading">Select your role</h4>
      <form class="form-home" action="<c:url value="/home"/>" method="post">
      <c:set var="manager" value="false"/> 
						<c:set var="instructor" value="false"/> 
						<c:set var="student" value="false"/> 
						<c:forEach items="${roles}" var="role">							
	  						<c:if test="${role == 'ROLE_ADMIN'}">
	  							<c:set var="manager" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role == 'ROLE_INSTRUCTOR'}">
	  							<c:set var="instructor" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role == 'ROLE_STUDENT'}">
	  							<c:set var="student" value="true"/>   
	  						</c:if> 	  						
						</c:forEach>
      <select class="form-control" name="role" required >
      	<c:if test="${manager == 'true'}">
      		<option value="ROLE_ADMIN">Manager</option>
      	</c:if>  
	    <c:if test="${instructor == 'true'}">
      		<option value="ROLE_INSTRUCTOR">Instructor</option>
      	</c:if> 
      	<c:if test="${student == 'true'}">
      		<option value="ROLE_STUDENT">Student</option>
      	</c:if>     	      
        </select>
        <br/>
         <button class="btn btn-lg btn-primary btn-block" type="submit">Continue &gt&gt </button>
       </form> 
       </c:if>
       </div>
    </div> 

      <a href="<c:url value="/setup"/>">Setup Mock Data</a>
    <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>

