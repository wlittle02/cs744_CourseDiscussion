<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">
				<c:out value="Summary" />
			</h1>
		</div>
		<div>
			<table class="table table-striped table-bordered" align="center">
				<tr>
					<th align="center">Course ID</th>
					<th>Course Name</th>
					<th>Section</th>
					<th>Year</th>
					<th>Semester</th>
					<th>State</th>
					<th>Instructor</th>
				</tr>
				<tr>
					<td><c:out value="${thread.course.id_num}" /></td>
					<td><c:out value="${thread.course.name}" /></td>
					<td><c:out value="${thread.course.section_num}" /></td>
					<td><c:out value="${thread.course.year}" /></td>
					<td><c:out value="${thread.course.semester}" /></td>
					<td><c:out value="${thread.course.state}" /></td>
					<td><c:out
							value="${thread.course.instructor.firstName}  ${thread.course.instructor.lastName}" />
					</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table table-striped table-bordered">
				<tr>
					<th>Date Created</th>
					<th>Thread Name</th>
					<th>Status</th>
				</tr>
				<tr>
					<td><c:out value="${thread.dateTime}" /></td>
					<td><a
						href="view_instructor_contributions?threadId=${thread.id}"><c:out
								value="${thread.name}" /></a></td>
					<c:if test="${thread.isActive == 'true'}">
						<td>Active</td>
					</c:if>
					<c:if test="${thread.isActive != 'true'}">
						<td>Inactive</td>
					</c:if>
				</tr>
			</table>


		</div>
		<div>
			<table class="table table-striped table-bordered ">

				<c:forEach items="${contributions}" var="contribution">
					<tr>
						<c:if test="${thread.isActive == 'true'}">
						<td><a
							href="reorder_contribution?thread_id=${contribution.thread.id}&contribution_id=${contribution.id}&move=up">
								Move Up </a></br> <a
							href="reorder_contribution?thread_id=${contribution.thread.id}&contribution_id=${contribution.id}&move=down">
								Move Down </a></td>
							</c:if>

						<td>
						<c:if test="${thread.isActive == 'true'}">
						<c:if test="${contribution.isImportant == 'true'}">
								<a
									href="set_instructor_Contribution?isImportant=false&contributionId=${contribution.id}">
									<img border="0"
									src="<c:url value="/resources/images/yellowstar.jpg"/>"
									width="20" height="20">
								</a>
							</c:if> <c:if test="${contribution.isImportant != 'true'}">
								<a
									href="set_instructor_Contribution?isImportant=true&contributionId=${contribution.id}">
									<img border="0"
									src="<c:url value="/resources/images/white_star.jpg"/>"
									width="20" height="20">
								</a>
							</c:if> <br /> 
							</c:if>
							
							<c:if test="${thread.isActive == 'false'}">
						<c:if test="${contribution.isImportant == 'true'}">
								
									<img border="0"
									src="<c:url value="/resources/images/yellowstar.jpg"/>"
									width="20" height="20">
								
							</c:if> <c:if test="${contribution.isImportant != 'true'}">
								
									<img border="0"
									src="<c:url value="/resources/images/white_star.jpg"/>"
									width="20" height="20">
							</c:if> <br /> 
							</c:if>
							
							<c:out value="${contribution.dateTime}" /><br /> <c:out
								value="${contribution.enteredBy}" /></td>
						<td><c:out value="${contribution.message}" /> <br /> <c:out
								value="${contribution.attachment}" /></td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${thread.isActive == 'true'}">
				<button class="btn btn-primary"
					onclick="location='shutdown_thread?thread_id=${thread.id}'">Summary
					the Thread</button>
			</c:if>

		</div>
		<!-- /.col-lg-12 -->
	</div>
</div>