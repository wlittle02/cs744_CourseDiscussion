<%@ include file="manager_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">User List</h1>
                      <c:if test="${updateerror}">
     			 		<div class="alert alert-danger">Update Unsucessful. User does not exist in database.</div>
      				  </c:if>    
      				   <c:if test="${error}">
      					<div class="alert alert-danger">Update Unsucessful.</div>
      					</c:if>    
      					<c:if test="${message}">
				<c:out value="${message}" />
			</c:if>
            	<table class="table table-striped table-bordered">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>User Name</th>
					<th>Email</th>
					<th>Manager</th>
					<th>Instructor</th>
					<th>Student</th>
					<th>Teaching Assistant</th>
					<th>Modify</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.firstName}" /></td>
						<td><c:out value="${user.lastName}" /></td>
						<td><c:out value="${user.username}" /></td>
						<td><c:out value="${user.email}" /></td>
						<c:set var="manager" value="false"/> 
						<c:set var="instructor" value="false"/> 
						<c:set var="student" value="false"/> 
						<c:set var="ta" value="false"/> 						
						<c:forEach items="${user.authorities}" var="role">							
	  						<c:if test="${role.name == 'ROLE_ADMIN'}">
	  							<c:set var="manager" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role.name == 'ROLE_INSTRUCTOR'}">
	  							<c:set var="instructor" value="true"/>   
	  						</c:if> 
	  						<c:if test="${role.name == 'ROLE_STUDENT'}">
	  							<c:set var="student" value="true"/>   
	  						</c:if> 	
	  						<c:if test="${role.name == 'ROLE_TA'}">
	  							<c:set var="ta" value="true"/>   
	  						</c:if> 	  						
						</c:forEach>
						<c:choose>
							<c:when test="${manager == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${instructor == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${student == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${ta == 'true'}">
								<td>Yes</td>
							</c:when>
							<c:otherwise>
								<td>No</td>
							</c:otherwise>
						</c:choose>
						<td><a href="modify?username=${user.username}">
						<img border="0" src="<c:url value="/resources/images/pencil_2.png"/>" alt="Delete" width="20" height="20"></a></td>
						<c:if test="${user.username != loginuser}">
							<td><a href="delete?username=${user.username}">
							<img border="0" src="<c:url value="/resources/images/button_cancel.png"/>" alt="Delete" width="20" height="20"></a></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>

			<%-- <ul class="pagination">
				<li><a href="<c:url value="/pview?pageno=1&column=${page.sortColumn}"/>">&laquo;</a></li>
				<c:forEach items="${page.getSurroundingPages()}" var="spage">
					<li><a href="<c:url value="/pview?pageno=${spage.pageNumber}&column=${page.sortColumn}"/>">${spage.pageNumber}</a></li>
				</c:forEach>
				<li><a href="<c:url value="/pview?pageno=${page.numberOfPages}&column=${page.sortColumn}"/>">&raquo;</a></li>
			</ul> --%>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

  