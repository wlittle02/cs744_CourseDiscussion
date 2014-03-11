<%@ include file="student_base.jsp"%>

<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Welcome to the Online Course Discussion System</h1>
                </div>
                <div>		
				<table class="table table-striped table-bordered" align="center">
					<tr>
					<th>Course Number</th>
					<th>Course Name Name</th>
					<th>Threads</th>
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><c:out value="${course.name}"/></td>
						<td><a href="view_thread?courseId=${course.id}">
						<img border="0" src="<c:url value="/resources/images/pencil_2.png"/>" alt="Delete" width="20" height="20"></a></td>
					</tr>
				</c:forEach>
				</table>
                </div>
                <!-- /.col-lg-12 -->
            </div>