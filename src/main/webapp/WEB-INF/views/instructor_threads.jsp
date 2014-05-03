<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
						<td>
							<c:if test="${thread.isActive == 'true'}">
								<a href="view_instructor_contributions?threadId=${thread.id}">
								<c:out value="${thread.name}" /></a>
							</c:if>
							<c:if test="${thread.isActive != 'true'}">
								<c:out value="${thread.name}" />
							</c:if>	
						</td>	
						<c:if test="${thread.isActive == 'true'}">
	  							<td>Active</td> 
	  					</c:if> 
	  					<c:if test="${thread.isActive != 'true'}">
	  							<td>Inactive</td> 
	  					</c:if>					
						<td>
							<a href="summarize_thread?thread_id=${thread.id}">Summary</a>
						</td>
						
						</tr>
						</c:forEach>			
				</table>
                </div>
                <form name="formNewThread" class="form-horizontal" 
			action="<c:url value="/create_instructor_thread?courseId=${courseId}"/>" method="post">
                <div class="form-group">
                <label for="threadname" class="col-sm-2 control-label">Thread Name</label>
				<div class="col-sm-6">
					<input name="threadName" id="threadname" type="text"
						class="form-control" required />
				</div>
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Create New Thread</button>
				</div>
				<br/>
				 <c:if test="${error}">
      					<div class="alert alert-danger">Thread already exists</div>
      			 </c:if> 
			</div>
		</form>
                <!-- /.col-lg-12 -->
            </div>
        </div>