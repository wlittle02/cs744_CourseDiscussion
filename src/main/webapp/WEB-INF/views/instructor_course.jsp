<%@ include file="instructor_base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

 <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Welcome to the Online Course Discussion System</h1>
                </div>
                <div>		
				<table class="table table-striped table-bordered" align="center">
					<tr>
					<th>Date Created</th>
					<th>Thread Name</th>
					<th>Date Last Modified</th>
					<th>Status</th>
				</tr>				
				</table>
                </div>
                <form name="formNewThread="form-horizontal" role="form"
			action="<c:url value="/createthread"/>" method="post">
                <div class="form-group">
				<div class="col-sm-3" align="right">
					<button type="submit" class="btn btn-primary">Create New Thread</button>
				</div>
			</div>
		</form>
                <!-- /.col-lg-12 -->
            </div>