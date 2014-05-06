<%@ include file="manager_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.*" %>

<%@ page session="true" %>
<head>
  <meta charset="utf-8">
  
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

					<input name="startdate" type="text" id="startdate" class="form-control"  required >

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
						<!-- <option>Monthly</option>
						<option>Yearly</option>		 -->				
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Display Report</button>
				</div>
			</div>
			
		</form>
		<br/>
		
             <c:if test="${reportActive}">
             <h3> ${reporttype} Report from ${start_date} to ${end_date} </h3>
                <div>		
				<table class="table table-striped table-bordered" >
					<tr>
					<th>Course Number</th>
					<th>Course Name</th>
					<th>Section</th>
					<th>No. of Threads</th>					
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><a href="get_contribution_report?courseId=${course.id}&enddate=${end_date}&startdate=${start_date}&report_type=${reporttype}"><c:out value="${course.name}"/></a></td>
						<td><c:out value="${course.section_num}" />
						<c:forEach items="${threadcountmap}" var="entry">
							<c:if test="${entry.key == course.id}">
							<td><c:out value="${entry.value}" /></td>   
							</c:if>
   						</c:forEach>
					</tr>
				</c:forEach>
				</table>
                </div>
                </c:if>
                <!-- /.col-lg-12 -->
            </div>
 </div>
 