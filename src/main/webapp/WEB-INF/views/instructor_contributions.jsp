<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><c:out value="${threadName}" /></h1>
                </div>
                <div>		
				<table class="table table-striped table-bordered " >
					
				<c:forEach items="${contributions}" var="contribution">
					<tr><td>
						<c:if test="${contribution.isImportant == 'true'}">
						<a href="setContribution?isImportant=false&contributionId=${contribution.id}">
	  							<img border="0" src="<c:url value="/resources/images/yellowstar.jpg"/>" width="20" height="20"></a> 
	  					</c:if> 
	  					<c:if test="${contribution.isImportant != 'true'}">
	  						<a href="setContribution?isImportant=true&contributionId=${contribution.id}">
	  							<img border="0" src="<c:url value="/resources/images/white_star.jpg"/>" width="20" height="20"></a> 
	  					</c:if>	
	  					<br/>
						<c:out value="${contribution.dateTime}" /><br/>
						<c:out value="${contribution.enteredBy}" /></td>	
						<td><c:out value="${contribution.message}" />
						<br/>
						<c:out value="${contribution.attachment}" /></td>
						</tr>
						</c:forEach>			
				</table>
                </div>
                <form name="formNewThread=" class="form-horizontal"
			action="<c:url value="/createContribution?threadId=${threadId}"/>" method="post">
                <div class="form-group">                
				<div class="col-lg-10">					
						<textarea class="form-control" name="message" id="message" cols="40" rows="6" required ></textarea>
				</div>
				
				<div class="col-sm-3" >				
				<br/>
					<button type="submit" class="btn btn-primary" >Post</button>
				</div>				
			</div>
		</form>
                <!-- /.col-lg-12 -->
            </div>
   </div>