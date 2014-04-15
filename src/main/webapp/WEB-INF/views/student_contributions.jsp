<%@ include file="student_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<script>
$(document).ready(function(){
	  $("#upload_submit").click(function(){
		  document.getElementsByName("name")[0].value = document.getElementsByName("file")[0].value;
	  });
});
</script>


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
							<img border="0" src="<c:url value="/resources/images/yellowstar.jpg"/>" width="20" height="20">
	  					</c:if> 
	  					<c:if test="${contribution.isImportant != 'true'}">
	  						<img border="0" src="<c:url value="/resources/images/white_star.jpg"/>" width="20" height="20"> 
	  					</c:if>	
	  					<br/>
						<c:out value="${contribution.dateTime}" /><br/>
						<c:out value="${contribution.enteredBy}" />
						<br/>
						<c:if test="${contribution.attachment != ''}">
							<a href="download?fileName=${contribution.attachment}">Attachment</a>
						</c:if>
						</td>	
						<td><c:out value="${contribution.message}" />
						<br/>
						</td>
						</tr>
						</c:forEach>			
				</table>
                </div>
                <c:if test="${threadActive == 'true'}">
                <form name="formNewThread=" class="form-horizontal"
			action="<c:url value="/create_student_Contribution?threadId=${threadId}"/>" method="post" enctype="multipart/form-data">
                <div class="form-group">                
				<div class="col-lg-10">					
						<textarea class="form-control" name="message" id="message" cols="40" rows="6" required ></textarea>
				</div>
				
				<div class="col-sm-6">
				<br/>
					<input type="hidden" name="name" />
					<input name="file" type="file" class="form-control"
						 />
				</div>
				
				<div class="col-sm-3" >				
				<br/>
					<button type="submit" class="btn btn-primary" id="upload_submit">Post</button>
				</div>				
			</div>
		</form>
		</c:if>
                <!-- /.col-lg-12 -->
            </div>
   </div>