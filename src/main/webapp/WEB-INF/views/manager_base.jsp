<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Online Course Discussion System</title>
    <!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
   -->
  

    <!-- Core CSS - Include with every page -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <link href="resources/css/css/font-awesome.css" rel="stylesheet">
	
    <!-- Page-Level Plugin CSS - Blank -->

    <!-- SB Admin CSS - Include with every page -->
    <link href="resources/css/sb-admin.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" >Manager - <%= (String)session.getAttribute("loginUsersName") %></a>
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
                    <li><a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>
							Courses<span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a href="<c:url value="register_course"/>">New
									Course Registration</a></li>
							<li><a href="<c:url value="/view_course"/>">Course List</a>
							</li>
						</ul> <!-- /.nav-second-level --></li>
<!--                     <li> -->
<!--                         <a href="forms.html"><i class="fa fa-edit fa-fw"></i> Forms</a> -->
<!--                     </li> -->
                    <li>
                        <a href="<c:url value="/courses_report"/>" ><i class="fa fa-wrench fa-fw"></i> Reports</a>
<%--                         <a href="<c:url value="/test"/>" ><i class="fa fa-wrench fa-fw"></i> Reports</a> --%>
                        <%-- <ul class="nav nav-second-level">
                            <li>
                                <a href="panels-wells.html">Single Course</a>
                            </li>
                            <li>
                                <a href="<c:url value="/courses_report"/>">All Courses</a>
                            </li>
                            
                        </ul> --%>
                        <!-- /.nav-second-level -->
                    </li>
     <!--                <li>
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


    </div>
    <!-- /#wrapper -->

    <!-- Core Scripts - Include with every page -->
    <script src="resources/js/js/jquery-1.10.2.js"></script>
    <script src="resources/js/js/bootstrap.min.js"></script>
    <script src="resources/js/js/plugins/metisMenu/jquery.metisMenu.js"></script>

    <!-- Page-Level Plugin Scripts - Blank -->

    <!-- SB Admin Scripts - Include with every page -->
    <script src="resources/js/js/sb-admin.js"></script>


    <!-- Page-Level Demo Scripts - Blank - Use for reference -->

</body>





</html>
