<%@ include file="manager_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>

<%@ page session="false" %>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Contributions</h1>
                </div>
                <div>
				<table class="table table-striped table-bordered" >
					<tr>
					<th  rowspan="2" >Thread Name</th>
					<th rowspan="2">Total Contributions</th>
					<th colspan="3">Number of Contributions By</th>
					<tr>
					<th>Instructor</th>
					<th>TA</th>
					<th>Student</th>
				</tr>
				<c:forEach items="${threads}" var="thread">
					<tr>
						<td><c:out value="${thread.name}"/></td>						
						<c:forEach items="${contcountmap}" var="entry">
							<c:if test="${entry.key == thread.id}">
								<c:forEach items="${entry.value}" var="keyvalue">
	              					<td>${keyvalue}</td>
	       						</c:forEach> 
							</c:if>
   						</c:forEach>						
					</tr>
				</c:forEach>
				</table>
                </div>
                <!-- /.col-lg-12 -->
            </div>
 </div>
            