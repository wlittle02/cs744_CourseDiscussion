<%@ include file="student_base.jsp"%>

<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Welcome to the Online Course Discussion System</h1>
                </div>
                <div>		
				<table class="table table-striped table-bordered" >
					<tr>
					<th>Course Number</th>
					<th>Course Name Name</th>					
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><a href="view_student_threads?courseId=${course.id}"><c:out value="${course.name}"/></a></td>
						
					</tr>
				</c:forEach>
				</table>
                </div>
                <!-- /.col-lg-12 -->
            </div>
 </div>