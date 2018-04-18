<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">反馈</h4>
</div>
<form id="feedbackForm" action="faultOrder/feedback" method="post">
	<div class="modal-body">

		<input name="id" value="${faultOrder.id}" hidden="true" />

		<div class="form-group">
			<label for="reason" class="control-label"><font color="red">*</font>故障原因:</label>
			<textarea class="form-control " id="reason" name="reason">${faultOrder.reason }</textarea>
		</div>
		<div class="form-group">
			<label for="fixed" class="control-label">是否修好:</label> <select
				class="form-control" name="fixed" id="fixed">
				<option value="true" selected="selected">是</option>
				<option value="false">否</option>

			</select>

		</div>
		<div class="form-group">
			<label for="employee" class="control-label">选择维保人员:</label> <select
				id="employee" data-placeholder="选择维保人员" class="form-control select2" name="employee.id">

				<c:forEach var="employee" items="${employees}">
					<option value="${employee.id}">${employee.name}</option>
				</c:forEach>


			</select>
		</div>
		<div class="form-group">
			<label for="remark" class="control-label">备注:</label>
			<textarea class="form-control " id="remark" name="remark">${faultOrder.remark }</textarea>
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
	$("#reason").focus();//将焦点放在用户名输入上
	$("#feedbackForm").validate({
		
		rules:{			 
			 
			reason:{ //格式：domId: 规则
				 maxlength:300,
				 required:true					 	
				 },
			remark:{
				maxlength:300
			}
			
		 },
		 messages:{
			
		 },
		 submitHandler : function(form){			
			 $.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : $("#feedbackForm").serialize(),				
					url : "faultOrder/feedback",//请求的action路径  
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