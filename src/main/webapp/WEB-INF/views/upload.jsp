<%@ include file="instructor_base.jsp"%>

<script>
$(document).ready(function(){
	  $("#upload_submit").click(function(){
		  document.getElementsByName("name")[0].value = document.getElementsByName("file")[0].value;
	  });
});
</script>



<form method="post" action="<c:url value="upload"/>"
	enctype="multipart/form-data">
	<div class="pageFormContent nowrap" layoutH="97">
		<input type="hidden" name="name" /> <input type="file" name="file" />
		<div class="divider"></div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" id="upload_submit">Submit</button>
					</div>
				</div></li>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">Cancel</button>
					</div>
				</div></li>
		</ul>
	</div>
</form>