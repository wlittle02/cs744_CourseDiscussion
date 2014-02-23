<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>

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

    <div id="wrapper">

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
                            <li class="active">
                                <a >User List</a>
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
                    <li>
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
                                <!-- /.nav-third-level -->
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
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
                    <h1 class="page-header">User List</h1>
                      <c:if test="${updateerror}">
     			 		<div class="alert alert-danger">Update Unsucessful. User does not exist in database.</div>
      				  </c:if>    
      				   <c:if test="${error}">
      					<div class="alert alert-danger">Update Unsucessful.</div>
      					</c:if>    
      					<c:if test="${message}">
				<c:out value="${message}" />
			</c:if>
            	<table class="table table-striped table-bordered">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>User Name</th>
					<th>Email</th>
					<th>Manager</th>
					<th>Instructor</th>
					<th>Student</th>
					<th>Modify</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.firstName}" /></td>
						<td><c:out value="${user.lastName}" /></td>
						<td><c:out value="${user.username}" /></td>
						<td><c:out value="${user.email}" /></td>
						<c:set var="manager" value="false"/> 
						<c:set var="instructor" value="false"/> 
						<c:set var="student" value="false"/> 
						<c:forEach items="${user.authorities}" var="role">							
	  						<c:if test="${role.name == 'ROLE_ADMIN'}">
	  							<c:set var="manager" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role.name == 'ROLE_INSTRUCTOR'}">
	  							<c:set var="instructor" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role.name == 'ROLE_STUDENT'}">
	  							<c:set var="student" value="true"/>   
	  						</c:if> 	  						
						</c:forEach>
						<c:choose>
							<c:when test="${manager == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${instructor == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${student == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<td><a href="modify?username=${user.username}">
						<img border="0" src="<c:url value="/resources/images/pencil_2.png"/>" alt="Delete" width="20" height="20"></a></td>
						<td><a href="delete?username=${user.username}">
						<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
					</tr>
				</c:forEach>
			</table>

			<%-- <ul class="pagination">
				<li><a href="<c:url value="/pview?pageno=1&column=${page.sortColumn}"/>">&laquo;</a></li>
				<c:forEach items="${page.getSurroundingPages()}" var="spage">
					<li><a href="<c:url value="/pview?pageno=${spage.pageNumber}&column=${page.sortColumn}"/>">${spage.pageNumber}</a></li>
				</c:forEach>
				<li><a href="<c:url value="/pview?pageno=${page.numberOfPages}&column=${page.sortColumn}"/>">&raquo;</a></li>
			</ul> --%>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

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
