<%@ include file="student_base.jsp"%>

<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Welcome to the Online Course Discussion System</h1>
                </div>
                <div>
                <c:forEach items="${courses}" var="course">
					<div><c:out value="${course.name}" /></div>
				</c:forEach>		
				
                </div>
                <!-- /.col-lg-12 -->
            </div>