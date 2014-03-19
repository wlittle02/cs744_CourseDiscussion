<%@ include file="student_base.jsp"%>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Thread List</h1>
			<table class="table table-striped table-bordered" align="center">
				<tr>
					<th>Thread</th>
					<th>Creation Date</th>
					<th>TEST Delete Thread</th>
				</tr>
				<c:forEach items="${threads}" var="thread">
					<tr>
						<td>
							<a href="getAllContributions?threadId=${thread.id}">${thread.name}</a>
						</td>
						<td><c:out value="${thread.dateTime}"/></td>
						<td><a href="deleteThread?threadId=${thread.id}">!!!Delete Me!!!</a></td>
					</tr>
				</c:forEach>
			</table>
			<a href="<c:url value="createThread?courseId=${courseId}"/>">Create Thread</a>
			
		</div>
	</div>
</div>