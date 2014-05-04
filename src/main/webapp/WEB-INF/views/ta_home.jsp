<%@ include file="ta_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Welcome to the Online Course Discussion System</h1>
                </div>
                <div>		
                 <c:if test="${empty courses}">
     				<h3>
					<i>You have not yet been registered as TA for any course.</i><br/>
					<i>Kindly contact the Manager for registering.</i>
					</h3>
     			 </c:if>  	
     			<c:if test="${not empty courses}">
				<table class="table table-striped table-bordered" >
					<tr>
					<th>Course Number</th>
					<th>Course Name</th>
					<th>Section</th>
					<th>Semester</th>					
				</tr>
				<c:forEach items="${courses}" var="course">
					<tr>
						<td><c:out value="${course.id_num}" /></td>
						<td><a href="view_ta_threads?courseId=${course.id}"><c:out value="${course.name}"/></a></td>
						<td><c:out value="${course.section_num}" /></td>
						<td><c:out value="${course.semester} - ${course.year}" /></td>
					</tr>
				</c:forEach>
				</table>
				</c:if>
                </div>
                <!-- /.col-lg-12 -->
            </div>
 </div>
            