<%@ include file="manager_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>

<%@ page session="false" %>
<head>
  <meta charset="utf-8">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#startdate" ).datepicker();
  });
  </script>
</head>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Reports</h1>
                </div>
                <form name="formreport" class="form-horizontal" action="<c:url value="/get_courses_report"/>" method="post">
			
			 
			<div class="form-group">
				<label for="startdate" class="col-sm-2 control-label">Select start date
					*</label>
					<div class="col-sm-6">
<%-- 					<c:if test="${start_date}"> --%>
					<input name="startdate" type="text" id="startdate" class="form-control"  required >
<%-- 					</c:if> --%>
					</div>								
			</div>			
			<div class="form-group">
				<label for="report_type" class="col-sm-2 control-label">Select Report Type
					*</label>
				<div class="col-sm-6">
					<select name="report_type" id="report_type" class="form-control" required>
						<c:if test="${reporttype == 'Weekly'}">
						<option selected>Weekly</option>
						</c:if>
						<c:if test="${reporttype != 'Weekly'}">
						<option>Weekly</option>
						</c:if>
						<c:if test="${reporttype == 'Weekly'}">
						<option selected>Monthly</option>
						</c:if>
						<c:if test="${reporttype != 'Weekly'}">
						<option>Monthly</option>
						</c:if>
						<c:if test="${reporttype == 'Weekly'}">
						<option selected>Yearly</option>
						</c:if>
						<c:if test="${reporttype != 'Weekly'}">
						<option>Yearly</option>
						</c:if>
						<option>Monthly</option>
						<option>Yearly</option>						
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Display Report</button>
				</div>
			</div>
		</form>
                
                <div>		
				<table class="table table-striped table-bordered" >
					<tr>
					<th>Course Number</th>
					<th>Course Name</th>
					<th>No. of Threads</th>					
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><a href="get_contribution_report?courseId=${course.id}&startdate=${start_date}&report_type=${reporttype}"><c:out value="${course.name}"/></a></td>
						<c:forEach items="${threadcountmap}" var="entry">
							<c:if test="${entry.key == course.id}">
							<td><c:out value="${entry.value}" /></td>   
							</c:if>
   						</c:forEach>
					</tr>
				</c:forEach>
				</table>
                </div>
                <!-- /.col-lg-12 -->
            </div>
 </div>
 