<%@ include file="manager_base.jsp"%>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Update Course</h1>
		</div>
		<form name="formregister" class="form-horizontal" role="form"
			action="<c:url value="/modify_courses"/>" method="post"
			onsubmit="return validate();">
			<input name="id" type=hidden value="${course.id}" />

			<div class="form-group">
				<label for="id_num" class="col-sm-2 control-label">Course ID
					*</label>
				<div class="col-sm-6">
					<input id="id_num" type="text" class="form-control"
						value="${course.id_num}" disabled/>
				</div>
			</div>

			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">Course Name
					*</label>
				<div class="col-sm-6">
					<input name="name" value="${course.name}" type="text"
						class="form-control" disabled />
				</div>
			</div>

			<div class="form-group">
				<label for="section_num" class="col-sm-2 control-label">Section
					*</label>
				<div class="col-sm-6">
					<input name="section_num" id="section_num"
						value="${course.section_num}" type="text" class="form-control"
						disabled />
				</div>
			</div>
			<div class="form-group">
				<label for="year" class="col-sm-2 control-label">Year *</label>
				<div class="col-sm-6">
					<select name="year" id="year" class="form-control" required>
						<c:forEach items="${years}" var="year">
							<c:choose>
								<c:when test="${year == course.year}">
									<option value="${year}" selected>${year}</option>
								</c:when>
								<c:otherwise>
									<option value="${year}">${year}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="instructor_id" class="col-sm-2 control-label">Instructor
					*</label>
				<div class="col-sm-6">
					<select name="instructor_id" id="instructor_id"
						class="form-control" required>
						<c:forEach items="${instructors}" var="ins">
							<c:choose>
								<c:when test="${ins.id == course.instructor.id}">
									<option value="${ins.id}" selected>${ins.firstName}
										${ins.lastName}</option>
								</c:when>
								<c:otherwise>
									<option value="${ins.id}">${ins.firstName}
										${ins.lastName}</option>
								</c:otherwise>
							</c:choose>
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
							<c:choose>
								<c:when test="${sem == course.semester}">
									<option value="${sem}" selected>${sem}</option>
								</c:when>
								<c:otherwise>
									<option value="${sem}">${sem}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>


			<div class="form-group">
				<label for="state" class="col-sm-2 control-label">State *</label>
				<div class="col-sm-6">
					<select name="state" id="state" class="form-control" required>
						<c:forEach items="${status}" var="state">
							<c:choose>
								<c:when test="${state == course.state}">
									<option value="${state}" selected>${state}</option>
								</c:when>
								<c:otherwise>
									<option value="${state}">${state}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Update
						Course</button>
				</div>
			</div>
		</form>
	</div>
</div>