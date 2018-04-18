<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">发布保养工单</h4>
</div>
<form id="addMaintainOrderForm" action="elevatorRecord/addMaintainOrder" method="post">
	<div class="modal-body">
		<input type="hidden" class="form-control " id="elevatorRecordId"
			name="elevatorRecord.id" value="${elevatorRecordId}">


		<div class="form-group">
			<label for="finalTime" class="control-label"><font
				color="red">*</font>截止日期:</label>
			<input class="form-control " id="finalTime" name="finalTime"/>
		</div>
		<div class="form-group">
			<label for="maintainType" class="control-label"><font
				color="red">*</font>选择维保类型:</label> <select id="maintainType"
				data-placeholder="选择类型" name="maintainType.id"
				class="form-control select2" tabindex="-1" style="width: 100%">
				<optgroup label="选择类型">
				
					<c:forEach var="maintainType" items="${maintainTypes}"
						>
						<option value="${maintainType.id}">${maintainType.name}</option>
					</c:forEach>

				</optgroup>

			</select>
		</div>

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
	</div>
</form>
<script>
$('#finalTime').datepicker({
	format : 'yyyy-mm-dd',
	language : 'zh-CN'

});
/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
$('#modal').on('shown.bs.modal', function(event) {	
			//$("#finalTime").focus();//将焦点放在用户名输入上
			 $("#addMaintainOrderForm").validate({
					 rules:{
						 finalTime:{ //格式：domId: 规则
							
							 required:true							 	
							 }												 
					 },
				
				 submitHandler : function(form){
			           	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data :  $("#addMaintainOrderForm").serialize(),
						   // contentType : 'application/json',    //发送信息至服务器时内容编码类型
							//dataType : "json",
							url : "elevatorRecord/addMaintainOrder",//请求的action路径  
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