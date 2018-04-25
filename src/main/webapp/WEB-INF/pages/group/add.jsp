<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增小组</h4>
</div>
<form id="addForm" action="group/add" method="post">
	<div class="modal-body">

		<div class="form-group">
			<label for="name" class="control-label"><font color="red">*</font>组名:</label>
			<input type="text" class="form-control " id="name" name="name">
		</div>


		<div class="form-group">
			<label for="description" class="control-label">描述:</label> <input
				type="text" class="form-control" id="description" name="description">
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
			$("#name").focus();//将焦点放在用户名输入上
			 $("#addForm").validate({
					 rules:{
						
						 name:{ //格式：domId: 规则
							 minlength:2,//无效
							 maxlength:50,
							 required:true,							 	
							 },
						description:{ //格式：domId: 规则
							 maxlength:300,
							 required:false,							 	
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
							url : "group/add",//请求的action路径  
							error : function() {//请求失败处理函数  
								alert('失败');
								$('#modal').modal('hide');
							},
							success : function(data) { //请求成功后处理函数。
								if(data=="\"entityDuplicate\""){
									alert("小组名重复");
								}else{
									
									alert("success");
									$('#modal').modal('hide');
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