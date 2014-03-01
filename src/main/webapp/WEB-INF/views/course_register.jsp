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
				<label for="name" class="col-sm-2 control-label">Course Name
					*</label>
				<div class="col-sm-6">
					<input name="name" id="name" type="text" class="form-control"
						required />
				</div>
			</div>

			<div class="form-group">
				<label for="year" class="col-sm-2 control-label">Year *</label>
				<div class="col-sm-6">
					<select name="year" id="year" class="form-control" required>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
						<option value="2017">2017</option>
						<option value="2018">2018</option>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="semester" class="col-sm-2 control-label">Semester
					*</label>
				<div class="col-sm-6">
					<select name="semester" id="semester" class="form-control" required>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
						<option value="Fall">Fall</option>
						<option value="Winter">Winter</option>
					</select>
				</div>
			</div>


			<div class="form-group">
				<label for="state" class="col-sm-2 control-label">State *</label>
				<div class="col-sm-6">
					<select name="state" id="state" class="form-control" required>
						<option value="Open">Open</option>
						<option value="Closed">Closed</option>
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


