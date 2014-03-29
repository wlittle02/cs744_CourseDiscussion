<%@ include file="manager_base.jsp"%>

<div id="page-wrapper">
	<div class="row">


		<div class="col-lg-12">
			<h1 class="page-header">Course List</h1>
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

		<table class="list-table">
			<th><h1 class="page-header">Signed TAs</h1></th>
			<th></th>
			<th><h1 class="page-header">Unsigned TAs</h1></th>
			<tr>
				<td style="vertical-align:top">
					<!-- Strat of Signed TA -->
					<form id="form1" name="resigntas" class="form-horizontal" role="form"
						action="<c:url value="resigntas_course"/>" method="post">
						<div class="col-lg-12">
							<table class="table table-striped table-bordered">
								<tr>
									<th></th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
								</tr>
								<input type="hidden" name="id" value="${course.id}">
								<c:forEach items="${course.TAs}" var="ta">
									<tr>
										<td><input type="checkbox" name="to_resign"
											value="${ta.id}"></td>
										<td><c:out value="${ta.firstName}" /></td>
										<td><c:out value="${ta.lastName}" /></td>
										<td><c:out value="${ta.email}" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</form> <!-- End of Signed TA  -->
				</td>
				<td>
					<button type="submit" class="btn btn-primary" onclick="document.getElementById('form1').submit();">----->></button>				<br>
					<br>
					<button type="submit" class="btn btn-primary" onclick="document.getElementById('form2').submit();"><<-----</button>
				</td>
				<td style="vertical-align:top">
					<!-- Start of TA not Signed -->

					<form id="form2" name="signtas" class="form-horizontal" role="form"
						action="<c:url value="signtas_course"/>" method="post">
						<div class="col-lg-12">
							<table class="table table-striped table-bordered">
								<tr>
									<th></th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
								</tr>
								<input type="hidden" name="id" value="${course.id}">
								<c:forEach items="${not_signed}" var="ta">
									<tr>
										<td><input type="checkbox" name="to_sign"
											value="${ta.id}"></td>
										<td><c:out value="${ta.firstName}" /></td>
										<td><c:out value="${ta.lastName}" /></td>
										<td><c:out value="${ta.email}" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>

					</form> <!-- End of TAs not Enrolled -->
				</td>
			</tr>
		</table>






	</div>
	<!-- /.row -->
</div>