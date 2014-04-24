<%@ include file="student_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><c:out value="${courseName}" /></h1>
                </div>
                <div>		
				<table class="table table-striped table-bordered" >
					<tr>
					<th>Date Created</th>
					<th>Thread Name</th>					
					<th>Status</th>
					<th>Summary</th>
				</tr>	
				<c:forEach items="${threads}" var="thread">
					<tr>
						<td><c:out value="${thread.dateTime}" /></td>
						<td><a href="view_student_contributions?threadId=${thread.id}"><c:out value="${thread.name}" /></a></td>	
						<c:if test="${thread.isActive == 'true'}">
	  							<td>Active</td>
	  							<td>Not Available</td> 
	  					</c:if> 
	  					<c:if test="${thread.isActive != 'true'}">
	  							<td>Inactive</td>
	  							<td><a href="summarize_thread?thread_id=${thread.id}">Summary</a></td>
	  					</c:if>					
						
						
						</tr>
						</c:forEach>			
				</table>
                </div>
              
                <!-- /.col-lg-12 -->
            </div>
   </div>         