<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">新增节点档案</h4>
			</div>
			<form id="addForm" action="elevatorRecord/add" method="post">
			<div class="modal-body">
					<div class="form-group">
						<label for="no" class="control-label"><font color="red">*</font>设备编号:</label> <input
							type="text" class="form-control " id="no"
							name="no">
					</div>
					
					<div class="form-group">
						<label for="address" class="control-label"><font color="red">*</font>设备地址:</label> <input
							type="text" class="form-control " id="address"
							name="address">
					</div>
					<div class="form-group">
						<label for="unit" class="control-label"><font color="red">*</font>所属单位:</label> <input
							type="text" class="form-control " id="unit"
							name="unit">
					</div>
					<div class="form-group">
						<label for="buildingNumber" class="control-label"><font color="red">*</font>所属养殖场:</label> <input
							type="text" class="form-control " id="buildingNumber"
							name="buildingNumber">
					</div>
					<div class="form-group">
						<label for="elevatorNumber" class="control-label"><font color="red">*</font>所属农舍:</label> <input
							type="text" class="form-control " id="elevatorNumber"
							name="elevatorNumber">
					</div>
					<div class="form-group">
						<label for="phone" class="control-label"><font color="red">*</font>联系方式:</label> <input
							type="text" class="form-control " id="phone"
							name="phone">
					</div>
					<div class="form-group">
						<label for="inspectionDate" class="control-label"><font color="red">*</font>年检日期:</label>					
						 <input
							type="text" class="form-control" id="inspectionDate" name="inspectionDate" value="${elevatorRecord.inspectionDate}">
					</div>
					<div class="form-group">
						<label for="manufacturingDate" class="control-label">生产日期:</label>					
						 <input
							type="text" class="form-control" id="manufacturingDate" name="manufacturingDate" value="${elevatorRecord.manufacturingDate}">
					</div>
					<div class="form-group">
						<label for="type" class="control-label">节点类型:</label> <input
							class="form-control" id="type" name="type">
					</div>
					<div class="form-group">
						<label for="modelNumber" class="control-label">风机型号:</label> <input
							type="text" class="form-control" id="modelNumber"
							name="modelNumber">
					</div>
						<div class="form-group">
						<label for="maxWeight" class="control-label">风速(kg):</label> <input
							type="text" class="form-control " id="maxWeight"
							name="maxWeight">
					</div>
					<div class="form-group">
						<label for="speed" class="control-label">速度(m/s):</label> <input
							type="text" class="form-control " id="speed"
							name="speed">
					</div>
			
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
			</div>
		</form>
<script>
/* $("#manufacturingDate").daterangepicker({
	 singleDatePicker: true,
     showDropdowns: true
}); */
$('#manufacturingDate,#inspectionDate').datepicker({
    format: 'yyyy-mm-dd',
    language: 'zh-CN'
    
});
/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
$('#modal').on('shown.bs.modal', function(event) {	
			$("#address").focus();//将焦点放在用户名输入上
			 $("#addForm").validate({
					 rules:{
						 no:{ //格式：domId: 规则
							 minlength:1,//无效
							 maxlength:100,
							 required:true							 	
							 },
						 address:{ //格式：domId: 规则
							 minlength:1,//无效
							 maxlength:100,
							 required:true							 	
							 },
						 unit:{ //格式：domId: 规则
							 minlength:1,//无效
							 maxlength:100,
							 required:true						 	
							 },
						 type:{ //格式：domId: 规则
							 minlength:1,//无效
							 maxlength:20
													 	
							 },
						 modelNumber:{ //格式：domId: 规则
							 minlength:1,//无效
							 maxlength:20
												 	
							 },
						 maxWeight:{
							 range:[0,9999]
						 },
						 speed:{
							 range:[0,100]
						 },
						 inspectionDate:{
							 required:true		
						 }
						
							 
					 },
				
				 submitHandler : function(form){
			           	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data :  $("#addForm").serialize(),
						   // contentType : 'application/json',    //发送信息至服务器时内容编码类型
							//dataType : "json",
							url : "elevatorRecord/add",//请求的action路径  
							error : function(jqXHR,textStatus,errorThrown ) {//请求失败处理函数  
								alert('失败');
								
								if(errorThrown=="Moved Permanently")//用户超时未登录，跳转登录界面
									window.location.href="login";
								
							},
							success : function(data) { //请求成功后处理函数。    
								if(data=="\"entityDuplicate\""){
									alert("设备编号重复");
								}else{
									alert("success");
									$('#modal').on('hidden.bs.modal',function(event){//当modal框完全隐藏后再刷新页面content，要不然有bug
										$("#content-wrapper").html(data);//刷新content页面
									});
								}
							}
						});
			        }    
			    });
	});	
</script>