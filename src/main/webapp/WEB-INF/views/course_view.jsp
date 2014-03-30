<%@ include file="manager_base.jsp"%>



<!-- Right Block Start -->
<div id="page-wrapper">
	<div class="row">
		<h1 class="page-header">Course List</h1>
		<div class="col-lg-12" style="max-height : 600px; overflow-y: auto">
			
			<table class="table table-striped table-bordered" align="center">
				<tr>
					<th align="center">Course ID</th>
					<th>Course Name</th>
					<th>Section</th>
					<th>Year</th>
					<th>Semester</th>
					<th>State</th>
					<th>Instructor</th>
					<th>Student</th>
					<th>TAs</th>
					<th>Modify</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><c:out value="${course.name}" /></td>
						<td><c:out value="${course.section_num}" /></td>
						<td><c:out value="${course.year}" /></td>
						<td><c:out value="${course.semester}" /></td>
						<td><c:out value="${course.state}" /></td>
						
						<td>
						<c:out value="${course.instructor.firstName}  ${course.instructor.lastName}" />
						</td>
						<td><a href="enrollstudent_course?id=${course.id}">Students </a></td>
						<td><a href="signta_course?id=${course.id}">TAs </a></td>
						
						<td><a href="modify_courses?id=${course.id}"> <img
								border="0" src="<c:url value="/resources/images/pencil_2.png"/>"
								alt="Delete" width="20" height="20"></a></td>
						<td><a href="delete_course?id=${course.id}"> <img
								border="0"
								src="<c:url value="/resources/images/button_cancel.png"/>"
								alt="Delete" width="20" height="20"></a></td>
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
<!-- Right Block ends -->

