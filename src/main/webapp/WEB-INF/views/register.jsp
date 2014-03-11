<%@ include file="manager_base.jsp"%> 

<div id="page-wrapper">
	<c:if test="${success}">
		<h3>
			<i>User successfully registered.</i>
		</h3>
	</c:if>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Register New User</h1>
		</div>



		<form name="formregister" class="form-horizontal" role="form"
			action="<c:url value="/register"/>" method="post"
			onsubmit="return validate();">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">First
					Name *</label>
				<div class="col-sm-6">
					<input name="firstName" id="firstname" type="text"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group">
				<label for="lname" class="col-sm-2 control-label">Last Name
					*</label>
				<div class="col-sm-6">
					<input name="lastName" id="lastname" type="text"
						class="form-control" required />
				</div>
			</div>

			<div class="form-group">
				<label for="UserNameInput" class="col-sm-2 control-label">User
					Name *</label>
				<div class="col-sm-6">
					<input name="userName" id="username" type="text"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group">
				<label for="passwordInput" class="col-sm-2 control-label">Password
					*</label>
				<div class="col-sm-6">
					<input name="password" id="password" type="password"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group">
				<label for="repasswordInput" class="col-sm-2 control-label">Re-Type
					Password *</label>
				<div class="col-sm-6">
					<input id="repassword" type="password" class="form-control"
						required />
				</div>
			</div>
			<div class="form-group">
				<label for="emailInput" class="col-sm-2 control-label">Email
					Address *</label>
				<div class="col-sm-6">
					<input name="email" id="emailInput" type="email"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group">
				<label for="userSelection" class="col-sm-2 control-label">User
					Roles *</label>
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label> <input id="checkmanager" type="checkbox"
							name="userroles" value="ROLE_ADMIN"> Manager
						</label>
					</div>
					<div class="checkbox">
						<label> <input id="checkinstructor" type="checkbox"
							name="userroles" value="ROLE_INSTRUCTOR"> Instructor
						</label>
					</div>
					<div class="checkbox">
						<label> <input id="checkstudent" type="checkbox"
							name="userroles" value="ROLE_STUDENT">Student
						</label>
					</div>
				</div>
			</div>
			<c:if test="${regerror}">
				<div class="alert alert-danger">User Name already exists. Try
					another one.</div>
			</c:if>

			<div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Create
						Account</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
function validate() {
    var d = document.formregister;
    if((d.password.value != '') && (d.repassword.value == d.password.value)) {
        var manager = document.getElementById('checkmanager');
     if( document.getElementById('checkmanager').checked 
     		|| document.getElementById('checkinstructor').checked || document.getElementById('checkstudent').checked ) {
     			
         return true;
     }
     alert("A Role must be selected");
    }
    else
    {
    	alert("Passwords not same");
    }
    
    
    return false;
}
</script>
