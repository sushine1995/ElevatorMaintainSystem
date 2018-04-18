<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增</h4>
</div>
<form id="addForm" action="maintainPlan/add" method="post">
	<div class="modal-body">
	<div class="form-group">
			<label for="no" class="control-label"><font color="red">*</font>合同编号:</label>
			<input type="text" class="form-control " id="no" name="no">
		</div>
		<div class="form-group">
			<label for="name" class="control-label"><font color="red">*</font>合同名:</label>
			<input type="text" class="form-control " id="name" name="name">
		</div>
		<div class="form-group">
			<label for="endDate" class="control-label">合同截止日期:</label> <input
				type="text" class="form-control" id="endDate" name="endDate">
		</div>
		<!-- <div class="form-group">
							<label for="isEffectiveImmediate" class="control-label"><font color="red">*</font>是否生效:</label> 
							<input  name="isEffectiveImmediate"  type="radio" checked="checked" value="true">立即生效
							<input  name="isEffectiveImmediate"  type="radio" value="false">次月生效
					</div> -->
		<div class="form-group">
			<label for="resourceSelect" class="control-label"><font
				color="red">*</font>选择负责小组:</label> <select id="resourceSelect"
				data-placeholder="选择负责小组:" name="group.id"
				class="form-control select2" tabindex="-1" style="width: 100%">

				<optgroup label="选择负责小组">

					<!-- 可选小组 -->
					<c:forEach var="group" items="${groups}" varStatus="status">
						<option value="${group.id}">${group.name}</option>
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
	$('#endDate').datepicker({
		format : 'yyyy-mm-dd',
		language : 'zh-CN'

	});

	/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
	$('#modal').on('shown.bs.modal', function(event) {
		$("#name").focus();//将焦点放在用户名输入上
		$("#addForm").validate({
			rules : {
				group : { //格式：domId: 规则

					required : true
				},

				name : { //格式：domId: 规则
					minlength : 2,
					maxlength : 25,
					required : true,
				}

			},
			messages : {

			},
			submitHandler : function(form) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : $("#addForm").serialize(),
					// contentType : 'application/json',    //发送信息至服务器时内容编码类型
					//dataType : "json",
					url : "maintainPlan/add",//请求的action路径  
					error : function(jqXHR, textStatus, errorThrown) {//请求失败处理函数  
						alert('失败');

						if (errorThrown == "Moved Permanently")//用户超时未登录，跳转登录界面
							window.location.href = "login";

					},
					success : function(data) { //请求成功后处理函数。

						if (data == "\"entityDuplicate\"") {
							alert("合同名重复");
						} else {
							alert("success");
							$('#modal').on('hidden.bs.modal', function(event) {//当modal框完全隐藏后再刷新页面content，要不然有bug
								$("#content-wrapper").html(data);//刷新content页面
							});
						}

					}
				});
			}
		});
	});
</script>