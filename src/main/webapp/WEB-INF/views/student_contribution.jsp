<%@ include file="student_base.jsp"%>

<div id = "page-wrapper">
	<div class = "row">
		<div class = "col-lg-12">
			<h1 class = "page-header">Contributions for</h1>
			<table class = "table table-striped table-bordered" align = "center">
				<tr>
					<th>Contribution</th>
					<th>Attachements</th>
					<th>Date/Time</th>
					<th>Contributor</th>
					<c:forEach items="${contributions}" var="contribution">
						<tr>
							<td><c:out value="${contribution.contribution.message}"/></td>
							<td/>
							<td><c:out value="${contribution.contribution.summaryId}"/></td>
							<td><c:out value="${contribution.userName}"/></td>
						</tr>
					</c:forEach>
				</tr>
			</table>
			<a href="createContribution?threadId=${threadId}">Test: Create Contribution</a>
		</div>
	</div>
</div>