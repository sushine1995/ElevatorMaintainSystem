<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">编辑设备档案</h4>
</div>
<form id="updateForm" action="elevatorRecord/update" method="post">
	<div class="modal-body">

		<input name="id" value="${elevatorRecord.id}" hidden="true" />

		<div class="form-group">
			<label for="address" class="control-label">设备地址:</label> <input
				type="text" class="form-control" id="address" name="address"
				value="${elevatorRecord.address}">
		</div>
		<div class="form-group">
			<label for="unit" class="control-label">单位:</label> <input
				type="text" class="form-control" id="unit" name="unit"
				value="${elevatorRecord.unit}">
		</div>
		<div class="form-group">
			<label for="phone" class="control-label">联系电话:</label> <input
				type="text" class="form-control" id="phone" name="phone"
				value="${elevatorRecord.phone}">
		</div>
		<div class="form-group">
			<label for="type" class="control-label">设备类型:</label> <input
				type="text" class="form-control" id="type" name="type"
				value="${elevatorRecord.type}">
		</div>
		<div class="form-group">
			<label for="modelNumber" class="control-label">设备型号:</label> <input
				type="text" class="form-control" id="modelNumber" name="modelNumber"
				value="${elevatorRecord.modelNumber}">
		</div>
		<div class="form-group">
			<label for="maxWeight" class="control-label">载重(kg):</label> <input
				type="text" class="form-control" id="maxWeight" name="maxWeight"
				value="${elevatorRecord.maxWeight}">
		</div>
		<div class="form-group">
			<label for="speed" class="control-label">速度(m/s):</label> <input
				type="text" class="form-control" id="speed" name="speed"
				value="${elevatorRecord.speed}">
		</div>
			<div class="form-group">
						<label for="inspectionDate" class="control-label"><font color="red">*</font>年检日期:</label>					
						 <input
							type="text" class="form-control" id="inspectionDate" name="inspectionDate" value="${elevatorRecord.inspectionDate}">
					</div>
		<div class="form-group">
			<label for="manufacturingDate" class="control-label">生产日期:</label> <input
				type="text" class="form-control" id="manufacturingDate"
				name="manufacturingDate" value="<fmt:formatDate pattern="yyyy-MM-dd"
					value="${elevatorRecord.manufacturingDate}" />">
		</div>
		<%-- <div class="form-group">
						<label for="lastMaintainTime" class="control-label">上次保养时间:</label> <input
							type="text" class="form-control" id="lastMaintainTime" name="lastMaintainTime" value="${elevatorRecord.lastMaintainTime}">
					</div> --%>
		<%-- 	<div class="form-group">
						<label for="group" class="control-label">维保小组:</label> <input
							type="text" class="form-control" id="group" name="group" value="${elevatorRecord.group.name}">
					</div> --%>



	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary" id="updateSubmitBtn">提交</button>
	</div>
</form>
<script>
$('#manufacturingDate,#inspectionDate').datepicker({
    format: 'yyyy-mm-dd',
    language: 'zh-CN'
    
});
/* 异步提交表单及更新content */
$('#modal').on('shown.bs.modal', function(event) {
	$("#updateForm").validate({
		rules:{			 
			 address:{ //格式：domId: 规则
				 minlength:2,//无效
				 maxlength:100,
				 required:true,							 	
				 },
			 unit:{ //格式：domId: 规则
				 minlength:2,//无效
				 maxlength:100,
				 required:true,							 	
				 },
			 phone:{ //格式：domId: 规则
				 minlength:11,//无效
				 maxlength:20,
				 required:true,							 	
				 },
			 type:{ //格式：domId: 规则
				
				 maxlength:20						 	
				 },
			 modelNumber:{ //格式：domId: 规则
				
				 maxlength:20						 	
				 },
			 maxWeight:{ //格式：domId: 规则
				
				 range:[0,9999]
										 	
				 },
			 speed:{ //格式：domId: 规则
				
				
				 range:[0,100]					 	
				 },
				 inspectionDate:{
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
					url : "elevatorRecord/update",//请求的action路径  
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