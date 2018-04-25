<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">新增员工</h4>
			</div>
			<form id="addForm" action="employee/add" method="post">
			<div class="modal-body">

					<div class="form-group">
						<label for="username" class="control-label"><font color="red">*</font>用户名:</label> <input
							type="text" class="form-control " id="username"
							name="username">
					</div>
					<div class="form-group">
						<label for="name" class="control-label"><font color="red">*</font>姓名:</label> <input
							type="text" class="form-control " id="name"
							name="name">
					</div>
					<div class="form-group">
						<label for="password" class="control-label">密码:</label> <input placeholder="默认为123456"
							class="form-control" id="password" name="password" value="123456">
					</div>
					<div class="form-group">
						<label for="phone" class="control-label"><font color="red">*</font>手机号码:</label> <input
							type="text" class="form-control" id="phone"
							name="phone">
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
			$("#username").focus();//将焦点放在用户名输入上
			 $("#addForm").validate({
					 rules:{
						 username:{ //格式：domId: 规则
							 minlength:2,//无效
							 maxlength:30,
							 required:true,							 	
							 },
						 password:{ //格式：domId: 规则
							 minlength:6,//无效
							 maxlength:30,
							 required:true,							 	
							 },
						 name:{ //格式：domId: 规则
							 minlength:2,//无效
							 maxlength:20,
							 required:true,							 	
							 },
						 phone:{ //格式：domId: 规则
							 minlength:11,//无效
							 maxlength:20,
							 required:true,							 	
							 }
					 },
					 messages:{
						 username:{
							 required:"请输入用户名",
							 minlength:jQuery.validator.format("至少需要{0}字符"),
							 maxlength:jQuery.validator.format("不能超过{0}字符")
							 
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
							url : "employee/add",//请求的action路径  
							error : function(jqXHR,textStatus,errorThrown ) {//请求失败处理函数  
								alert('失败');
								$('#modal').modal('hide');
								if(errorThrown=="Moved Permanently")//用户超时未登录，跳转登录界面
									window.location.href="login";
								
							},
							success : function(data) { //请求成功后处理函数。
								
								if(data=="\"entityDuplicate\""){
									alert("用户名重复");
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