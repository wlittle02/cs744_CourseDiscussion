<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="page-wrapper">
	<div class="row">

		<div class="col-lg-12">
			<h1 class="page-header">Course Information</h1>
			<table class="table table-striped table-bordered">
				<tr>
					<th>Course ID</th>
					<th>Course Name</th>
					<th>Section</th>
					<th>Year</th>
					<th>Semester</th>
					<th>State</th>
					<th>Instructor</th>
				</tr>
				<tr>
					<td><c:out value="${course.id_num}" /></td>
					<td><c:out value="${course.name}" /></td>
					<td><c:out value="${course.section_num}" /></td>
					<td><c:out value="${course.year}" /></td>
					<td><c:out value="${course.semester}" /></td>
					<td><c:out value="${course.state}" /></td>
					<td><c:out
							value="${course.instructor.firstName}  ${course.instructor.lastName}" />
					</td>
				</tr>
			</table>
		</div>
		<!-- Strat of Enrolled Student -->
		<div class="col-lg-12">
			<h3 class="page-header">Enrolled Student</h3>
			<table class="table table-striped table-bordered">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				<input type="hidden" name="id" value="${course.id}">
				<c:forEach items="${course.students}" var="student">
					<tr>
						<td><c:out value="${student.firstName}" /></td>
						<td><c:out value="${student.lastName}" /></td>
						<td><c:out value="${student.email}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- End of Student Enrolled  -->
		
		<!-- Strat of Enrolled TAs -->
		<div class="col-lg-12">
			<h3 class="page-header">Enrolled TAs</h3>
			<table class="table table-striped table-bordered">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				<input type="hidden" name="id" value="${course.id}">
				<c:forEach items="${course.TAs}" var="ta">
					<tr>
						<td><c:out value="${ta.firstName}" /></td>
						<td><c:out value="${ta.lastName}" /></td>
						<td><c:out value="${ta.email}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- End of TAs Enrolled  -->

	</div>
	<!-- /.row -->
</div>