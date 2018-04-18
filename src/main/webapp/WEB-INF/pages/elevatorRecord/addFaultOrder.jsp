<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">发布故障工单</h4>
</div>
<form id="addFaultOrderForm" action="elevatorRecord/addFaultOrder" method="post">
	<div class="modal-body">
		<input type="hidden" class="form-control " id="elevatorRecordId"
			name="elevatorRecord.id"  value="${elevatorRecordId}">

		<div class="form-group">
			<label for="description" class="control-label"><font color="red">*</font>故障描述:</label>
			<textarea class="form-control " id="description" name="description" ></textarea>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
	</div>
</form>
<script>

/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
$('#modal').on('shown.bs.modal', function(event) {	
			$("#description").focus();//将焦点放在用户名输入上
			 $("#addFaultOrderForm").validate({
					 rules:{
						 description:{ //格式：domId: 规则
							 maxlength:300,
							 required:true							 	
							 }
						
							 
					 },
				
				 submitHandler : function(form){
			           	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data :  $("#addFaultOrderForm").serialize(),						 
							url : "elevatorRecord/addFaultOrder",//请求的action路径  
							error : function(jqXHR,textStatus,errorThrown ) {//请求失败处理函数  
								alert('失败');
								
								if(errorThrown=="Moved Permanently")//用户超时未登录，跳转登录界面
									window.location.href="login";
								
							},
							success : function(data) { //请求成功后处理函数。    
								if(data=="\"success\""){
									alert("成功");
								}
							}
						});
			        }    
			    });
	});	
</script>