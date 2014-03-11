<%@ include file="manager_base.jsp"%>

<!-- Right Block Starts -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Register New Course</h1>
		</div>
		<form name="formregister" class="form-horizontal" role="form"
			action="<c:url value="/register_course"/>" method="post"
			onsubmit="return validate();">
			<div class="form-group">
				<label for="id_num" class="col-sm-2 control-label">Course ID
					*</label>
				<div class="col-sm-6">
					<input name="id_num" id="id_num" type="text" class="form-control"
						required />
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">Course Name
					*</label>
				<div class="col-sm-6">
					<input name="name" id="name" type="text" class="form-control"
						required />
				</div>
			</div>
			<div class="form-group">
				<label for="section_num" class="col-sm-2 control-label">Section
					*</label>
				<div class="col-sm-6">
					<select name="section_num" id="section_num" class="form-control" required>
						<c:forEach items="${sections}" var="sec">
							<option value="${sec}">${sec}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="year" class="col-sm-2 control-label">Year *</label>
				<div class="col-sm-6">
					<select name="year" id="year" class="form-control" required>
						<c:forEach items="${years}" var="year">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="semester" class="col-sm-2 control-label">Semester
					*</label>
				<div class="col-sm-6">
					<select name="semester" id="semester" class="form-control" required>
						<c:forEach items="${semesters}" var="sem">
							<option value="${sem}">${sem}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="instructor_id" class="col-sm-2 control-label">Instructor
					*</label>
				<div class="col-sm-6">
					<select name="instructor_id" id="instructor_id" class="form-control" required>
						<c:forEach items="${instructors}" var="ins">
							<option value="${ins.id}">${ins.firstName} ${ins.lastName} </option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="state" class="col-sm-2 control-label">Status *</label>
				<div class="col-sm-6">
					<select name="state" id="state" class="form-control" required>
						<c:forEach items="${status}" var="sta">
							<option value="${sta}">${sta}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<c:if test="${error}">
				<div class="alert alert-danger">Course Name already exists.
					Try another one.</div>
			</c:if>
			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Create
						Course</button>
				</div>
			</div>
		</form>
	</div>
</div>
<!-- Right Block Ends -->


