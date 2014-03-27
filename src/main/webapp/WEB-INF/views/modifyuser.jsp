<%@ include file="manager_base.jsp"%> 

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Modifying User Details</h1>
		</div>
		<form name="formupdate" class="form-horizontal" role="form"
			action="<c:url value="/update?username=${user.username}"/>"
			method="post" onsubmit="return validate();">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">First
					Name *</label>
				<div class="col-sm-6">
					<input name="firstName" id="firstname" type="text"
						class="form-control" value="${user.firstName}" required />
				</div>
			</div>
			<div class="form-group">
				<label for="lname" class="col-sm-2 control-label">Last Name
					*</label>
				<div class="col-sm-6">
					<input name="lastName" id="lastname" type="text"
						class="form-control" value="${user.lastName}" required />
				</div>
			</div>

			<div class="form-group">
				<label for="UserNameInput" class="col-sm-2 control-label">User
					Name *</label>
				<div class="col-sm-6">
					<input name="userName" id="username" type="text"
						class="form-control" value="${user.username}" disabled />
				</div>
			</div>
			<div class="form-group">
				<label for="emailInput" class="col-sm-2 control-label">Email
					Address *</label>
				<div class="col-sm-6">
					<input name="email" id="emailInput" type="email"
						class="form-control" value="${user.email}" required />
				</div>
			</div>
			<div class="form-group">
				<label for="UserNameInput" class="col-sm-2 control-label">Roles</label>
				<c:set var="manager" value="false" />
				<c:set var="instructor" value="false" />
				<c:set var="student" value="false" />
				<c:forEach items="${user.authorities}" var="role">
					<c:if test="${role.name == 'ROLE_ADMIN'}">
						<c:set var="manager" value="true" />
					</c:if>
					<c:if test="${role.name == 'ROLE_INSTRUCTOR'}">
						<c:set var="instructor" value="true" />
					</c:if>
					<c:if test="${role.name == 'ROLE_STUDENT'}">
						<c:set var="student" value="true" />
					</c:if>
					<c:if test="${role.name == 'ROLE_TA'}">
						<c:set var="ta" value="true" />
					</c:if>
				</c:forEach>

				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input id='checkmanager' type="checkbox"
							name="userroles" value="ROLE_ADMIN"
							<c:if test="${manager == 'true'}">checked="checked"</c:if>
							<c:if test="${user.username == loginuser}"> disabled="true"</c:if>>
							Manager
						</label>
					</div>
					<div class="checkbox">
						<label> <input id='checkinstructor' type="checkbox"
							name="userroles" value="ROLE_INSTRUCTOR"
							<c:if test="${instructor == 'true'}">checked="checked"</c:if>>
							Instructor
						</label>
					</div>
					<div class="checkbox">
						<label> <input id='checkstudent' type="checkbox"
							name="userroles" value="ROLE_STUDENT"
							<c:if test="${student == 'true'}">checked="checked"</c:if>>Student
						</label>
					</div>
					<div class="checkbox">
						<label> <input id='checkta' type="checkbox"
							name="userroles" value="ROLE_TA"
							<c:if test="${ta == 'true'}">checked="checked"</c:if>>Teaching Assistant
						</label>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Save
						Changes</button>
					<a href="<c:url value="/viewusers"/>"><input type="button"
						value="Cancel" class="btn btn-primary"></a>
				</div>
			</div>
		</form>
		<%-- <form name="formcancel" action="<c:url value="/viewusers"/>" >
			 <div class="col-sm-3" align="right">
			 <button type="button" class="btn btn-primary" >Cancel</button>
			 </div>
			 </form> --%>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
<script type="text/javascript">
 	function validate() {
        
 		 var manager = document.getElementById('checkmanager');
 	    if( !document.getElementById('checkmanager').checked 
 	     		&& !document.getElementById('checkinstructor').checked
 	     		&& !document.getElementById('checkstudent').checked
 	     		&& !document.getElementById('checkta').checked) {
 	    	alert("A Role must be selected");
 	    	return false;
 	    }
 	    if(document.getElementById('checkta').checked && !document.getElementById('checkstudent').checked ){
 	   		alert("If user has role of Teaching Assistant then must have role of Student");
 	    	return false;
 	     }	    	 
 	    return true;  
    }
</script>
