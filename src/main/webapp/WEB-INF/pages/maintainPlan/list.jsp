<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		保养计划管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active">保养计划管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- <div class="col-md-6"> -->
		<div class="box">
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">数据查询</h3>
							</div>
							<div class="box-body">
								<!-- form start -->
								<form id="searchForm" action="maintainPlan/search" method="get">
									<div class="box-body">
										<div class="row">
											<div class="form-group col-md-2">
												<label for="nameLabel">合同名:</label> <input type="text"
													class="form-control" id="nameLabel" name="search_name"
													value="${searchParamsMap.name }">
											</div>
											<div class="form-group col-md-2">
											<label for="noLabel">合同号:</label> <input type="text"
													class="form-control" id="noLabel" name="search_no"
													value="${searchParamsMap.no }">
											</div>
												<div class="form-group col-md-2">
												<label for="groupNameLabel">小组名:</label> <input type="text"
													class="form-control" id="groupNameLabel" name="search_groupName"
													value="${searchParamsMap.groupName }">
											</div>
											

											<!-- Date range -->
											<div class="form-group  col-md-4">
												<label>截止日期:</label>
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="reservation" name="search_endDateRange"
														value="${searchParamsMap.endDateRange}">
												</div>
												<!-- /.input group -->
											</div>

										</div>
										<!-- other rows -->
									</div>
									<!-- /.box-body -->
									<div class="box-footer">
										<button id="searchBtn" type="submit"
											class="btn btn-info pull-right">查询</button>
									</div>
									<!-- /.box-footer -->
								</form>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col (right) -->
				</div>
				<!-- /.row -->
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">保养计划列表</h3>
					</div>
					<div class="btn-group">
						<!-- 注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。 -->

						<shiro:hasPermission name="maintainPlan:create">
							<button id="addBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="addItem()">
								<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="maintainPlan:delete">
							<button id="deleteBtn" type="button"
								class="btn  btn-danger btn-flat margin">
								<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
							</button>
						</shiro:hasPermission>


					</div>
					<div class="table-responsive">
						<table class="table table-hover center">
							<tr>
								<th style="width: 10px"><label> <input
										id="allCheck" type="checkbox" class="minimal" value="0">
								</label></th>
								<th style="width: 10px">#</th>
								<th>合同编号</th>
								<th>合同名</th>
								<th>截止日期</th>
								<th>负责小组</th>
								<th>操作</th>

							</tr>
							<c:forEach items="${maintainPlans}" var="maintainPlan"
								varStatus="status">
								<tr>
									<td><label><input type="checkbox"
											class="minimal deleteCheckbox" value="${maintainPlan.id}"></label></td>
									<td>${status.count}</td>
									<td>${maintainPlan.no}</td>
									<td>${maintainPlan.name}</td>

									<td><fmt:formatDate pattern="yyyy-MM-dd"
											value="${maintainPlan.endDate}" /></td>
									<td>${maintainPlan.group.name}</td>

									<td><shiro:hasPermission name="maintainPlan:update">
											<button id="updateBtn" type="button"
												class="btn btn-xs btn-primary btn-flat"
												onclick='updateItem(${maintainPlan.id})'>编辑</button>
										</shiro:hasPermission> <shiro:hasPermission name="maintainPlan:view">
											<button id="detailBtn" type="button"
												class="btn  btn-xs btn-primary btn-flat"
												onclick='detailItem(${maintainPlan.id})'>详情</button>
										</shiro:hasPermission>
										<%--  <shiro:hasPermission name="maintainPlan:bindElevator">
											<button id="bindRoleBtn" type="button"
												class="btn  btn-xs btn-primary btn-flat"
												onclick='bindItem(${maintainPlan.id})'>设备绑定</button>
										</shiro:hasPermission> --%>
										</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination paginationSize="10" page="${page}"
					action="maintainPlan/search" contentSelector="#content-wrapper"></vino:pagination>
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->

<!-- 新增页面 modal框 -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content"></div>
	</div>
</div>
<!-- ./新增页面 modal框 -->
<!-- 删除确认页面 modal框 -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">删除</h4>
			</div>
			<div class="modal-body">
				<div>确定要删除吗？</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" id="deleteConfirmBtn">提交</button>
			</div>
		</div>
	</div>
</div>
<script>

	//Date range picker
	$('#reservation').daterangepicker({
		 
	        showDropdowns: true
	});
	//Date range picker with time picker
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});	
	/* icheck 初始化 详情：https://github.com/fronteed/icheck */
   	iCheckInit();
 	/* iCheck事件监听 详情：https://github.com/fronteed/icheck */
 	/* 全选和取消全选 */
	$(document).ready(function(){
		$('#allCheck').on('ifToggled', function(event){
			if(this.checked)
				$('input[class*="deleteCheckbox"]').iCheck('check');	
			else
				$('input[class*="deleteCheckbox"]').iCheck('uncheck');	
		});		
	});
	//删除确认modal事件处理
	$('#deleteConfirmModal').on('shown.bs.modal', function(event) {
		$('#deleteConfirmBtn').click(function(){
			deleteItemsUseModal("input[class*='deleteCheckbox']","maintainPlan/delete");
		});
	});
	/* button监听事件 */
	$(document).ready(function(){
		$("#deleteBtn").click(function(){
			$("#deleteConfirmModal").modal();	
		});
		
	});	
	$("#searchBtn").click(function() {
		$('#pageNumber').val(1);
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data : $("#searchForm").serialize(),		 
			url : "maintainPlan/search",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#content-wrapper").html(data);//刷新content页面		
			}
		});
	});
	
	function modalLoadAndDisplay(url){	
		$('#modal .modal-content').load(url,function(){
			$("#modal").modal();
		});		
	}
	function addItem(){
		modalLoadAndDisplay('maintainPlan/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('maintainPlan/'+id);
	}
	
	function detailItem(id){
	
		modalLoadAndDisplay('maintainPlan/detail/'+id);
	}
		
	function bindItem(id){
		modalLoadAndDisplay('maintainPlan/prepareBindGroup/'+id);
		
	}
	
	
</script>