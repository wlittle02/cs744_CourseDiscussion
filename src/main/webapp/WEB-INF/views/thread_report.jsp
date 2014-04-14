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
					<th>Thread Name</th>
					<th>Total</th>
					<th>Instructor</th>
					<th>Student</th>
					<th>TA</th>
				</tr>
				<c:forEach items="${threads}" var="thread">
					<tr>
						<td><c:out value="${thread.name}"/></td>
						<td><c:out value="${contcountmap}"/></td>
						<%-- <c:forEach items="${contcountmap.get(thread.id)}" var="entry">
							<td><c:out value="${entry.key}" /></td>
   						</c:forEach> --%>
						
					</tr>
				</c:forEach>
				</table>
                </div>
                <!-- /.col-lg-12 -->
            </div>
 </div>
            