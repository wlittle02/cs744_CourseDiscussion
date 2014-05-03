<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id = "page-wrapper">
	<form name = "threadregister" class = "form-horizontal" role = "form"
		action = "<c:url value = ""/>" method = "post">
		<div class = "form-group">
			<label for = "coursename" class = "col-sm-2 control-label">Course *</label>
			<select>
				<c:forEach items="${courses}" var = "courses">
					<option value = "courses.id">course.name</option>
				</c:forEach>
			</select>
		</div>
		<div class = "form-group">
			<label for = "threadName" class = "col-sm-2 control-label">Thread Name *</label>
			<div class = "col-sm-6">
				<input name = "threadName" id = "threadName" type = "text"
					class = "form-control" required />
			</div>
		</div>
		<div class = "form-group">
			<div class = "col-sm-3" align = "right">
				<button type = "submit" class = "btn btn-primary">Create Thread</button>
			</div>
		</div>
	</form>
</div>