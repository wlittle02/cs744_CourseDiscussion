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
     <c:if test="${invalid}">
      <h3 class="alert alert-danger">${message}</h3>
      </c:if>    
          
      <form class="form-signin" action="<c:url value="/login"/>" method="post">
        <h2 class="form-signin-heading">Login</h2>
        <input type="text" class="form-control" placeholder="User Name" autofocus name="username" required>   
        <input type="password" class="form-control" placeholder="Password" name="password" required>
        <select class="form-control" name="role" required >      	
      		<option value="ROLE_ADMIN">Manager</option>
      		<option value="ROLE_INSTRUCTOR">Instructor</option>
      		<option value="ROLE_STUDENT">Student</option>
      		<option value="ROLE_TA">Teaching Assistant</option>      		
        </select>
      <c:if test="${error}">
      <div class="alert alert-danger">${message}</div>
      </c:if>    
          
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form> 
       </div>
    </div> 

    <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>

