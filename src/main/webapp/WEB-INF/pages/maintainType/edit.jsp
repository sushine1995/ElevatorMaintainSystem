<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
	<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">编辑</h4>
			</div>
				<form id="updateForm" action="maintainType/update" method="post">
			<div class="modal-body">

					<input name="id" value="${maintainType.id}" hidden="true"/>
					<div class="form-group">
						<label for="name" class="control-label">类型名:</label> ${maintainType.name}<%-- <input
							type="text" class="form-control " id="username" name="username" disabled="disabled" value="${user.username}" > --%>
					</div>					
					
				
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary" id="updateSubmitBtn">提交</button>
			</div>
			</form>
<script>
/* 异步提交表单及更新content */
$('#modal').on('shown.bs.modal', function(event) {
	$("#updateForm").validate({
		rules:{			 
			 
			maintainType:{ //格式：domId: 规则
			
				 required:true					 	
				 }
			
		 },
		 messages:{
			
		 },
		 submitHandler : function(form){			
			 $.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : $("#updateForm").serialize(),				
					url : "maintainType/update",//请求的action路径  
					error : function() {//请求失败处理函数  
						alert('失败');
					},
					success : function(data) { //请求成功后处理函数。    
						alert("success");						
						$('#modal').on('hidden.bs.modal',function(event){//当modal框完全隐藏后再刷新页面content，要不然有bug
							$("#content-wrapper").html(data);//刷新content页面
						});
					}
				});
			 }
		});		
	});

</script>