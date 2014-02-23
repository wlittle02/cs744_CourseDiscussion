<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>


<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Online Course Discussion System</title>

    <!-- Core CSS - Include with every page -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/css/font-awesome.css" rel="stylesheet">

    <!-- Page-Level Plugin CSS - Blank -->

    <!-- SB Admin CSS - Include with every page -->
    <link href="resources/css/sb-admin.css" rel="stylesheet">

</head>

<body>
	<div id="wrap">	   
		
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Manager</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
              
            
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                    <!--  <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li> -->
                        <li><a  href="<c:url value="/logout"/>"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

        </nav>
        <!-- /.navbar-static-top -->

        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <!-- <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                        /input-group
                    </li> -->
<!--                     <li> -->
<!--                         <a href="index.html"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a> -->
<!--                     </li> -->
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Users<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<c:url value="/register"/>">New User Registration</a>
                            </li>
                            <li >
                                <a href="<c:url value="/viewusers"/>">User List</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="tables.html"><i class="fa fa-table fa-fw"></i> Courses</a>
                    </li>
<!--                     <li> -->
<!--                         <a href="forms.html"><i class="fa fa-edit fa-fw"></i> Forms</a> -->
<!--                     </li> -->
                    <li>
                        <a href="#"><i class="fa fa-wrench fa-fw"></i> Reports<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="panels-wells.html">Single Course</a>
                            </li>
                            <li>
                                <a href="buttons.html">All Courses</a>
                            </li>
                            
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <!-- <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#">Second Level Item</a>
                            </li>
                            <li>
                                <a href="#">Second Level Item</a>
                            </li>
                            <li>
                                <a href="#">Third Level <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a href="#">Third Level Item</a>
                                    </li>
                                    <li>
                                        <a href="#">Third Level Item</a>
                                    </li>
                                    <li>
                                        <a href="#">Third Level Item</a>
                                    </li>
                                    <li>
                                        <a href="#">Third Level Item</a>
                                    </li>
                                </ul>
                                /.nav-third-level
                            </li>
                        </ul>
                        /.nav-second-level
                    </li> -->
<!--                     <li class="active"> -->
<!--                         <a href="#"><i class="fa fa-files-o fa-fw"></i> Sample Pages<span class="fa arrow"></span></a> -->
<!--                         <ul class="nav nav-second-level"> -->
<!--                             <li> -->
<!--                                 <a href="blank.html">Blank Page</a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="login.html">Login Page</a> -->
<!--                             </li> -->
<!--                         </ul> -->
<!--                         /.nav-second-level -->
<!--                     </li> -->
                </ul>
                <!-- /#side-menu -->
            </div>
            <!-- /.sidebar-collapse -->
        </nav>
        <!-- /.navbar-static-side -->

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Register New User</h1>
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
			<div class="form-group">
				<label for="userSelection" class="col-sm-2 control-label">User Roles</label>
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
    				<label>
     			 		<input id ="checkmanager" type="checkbox"  name="userroles" value="ROLE_ADMIN"> Manager
    				</label>
    				</div>  
    				<div class="checkbox">
    				<label>
     			 		<input id ="checkinstructor" type="checkbox"  name="userroles" value="ROLE_INSTRUCTOR" > Instructor
    				</label>
    				</div>  
    				<div class="checkbox">
    				<label>
     			 		<input id ="checkstudent" type="checkbox"  name="userroles" value="ROLE_STUDENT">Student
    				</label>
    				</div>
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
    </div>
    <script>
    function validate() {
        var d = document.formregister;
        if((d.password.value != '') && (d.repassword.value == d.password.value)) {
            var manager = document.getElementById('checkmanager');
	        if( document.getElementById('checkmanager').checked 
	        		|| document.getElementById('checkinstructor').checked || document.getElementById('checkstudent').checked ) {
	        			
	            return true;
	        }
	        alert("A Role must be selected");
        }
        else
        {
        	alert("Passwords not same");
        }
        
        
        return false;
    }
    </script>
	<script src="resources/js/js/jquery-1.10.2.js"></script>
    <script src="resources/js/js/bootstrap.min.js"></script>
    <script src="resources/js/js/plugins/metisMenu/jquery.metisMenu.js"></script>

    <!-- Page-Level Plugin Scripts - Blank -->

    <!-- SB Admin Scripts - Include with every page -->
    <script src="resources/js/js/sb-admin.js"></script>

    <!-- Page-Level Demo Scripts - Blank - Use for reference -->
	

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	</body>
</html>

