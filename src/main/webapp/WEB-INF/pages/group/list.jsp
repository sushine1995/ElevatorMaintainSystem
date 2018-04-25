<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		小组管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active">小组管理</li>
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
								<form id="searchForm" action="group/search" method="get">
									<div class="box-body">
										<div class="row">
											<input hidden="true" name="pageNumber" id="pageNumber">
										
											<div class="form-group col-md-2">
												<label for="aliasLabel">组名:</label> <input type="text"
													class="form-control" id="aliasLabel"
													name="search_name"
													value="${searchParamsMap.name }">
											</div>
											<!-- Date range -->
											<div class="form-group  col-md-4">
												<label>创建时间:</label>
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="reservation" name="search_createTimeRange"
														value="${searchParamsMap.createTimeRange}">
												</div>
												<!-- /.input group -->
											</div>
										

											<!-- /.form group -->
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
						<h3 class="box-title">小组列表</h3>
					</div>
					<div class="btn-group">
						
						<shiro:hasPermission name="group:create">
							<button id="addBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="addItem()">
								<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="group:delete">
							<button id="deleteBtn" type="button"
								class="btn  btn-danger btn-flat margin">
								<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="group:upload">
							<button id="uploadBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="uploadItem()">
								<span class="fa fa-fw fa-cloud-upload" aria-hidden="true"></span> 上传
							</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="group:download">
							<form id="downloadForm" action="group/download" method="get" >
							<button id="downloadBtn" type="submit"
								class="btn  btn-primary btn-flat margin2" 
								 onclick="downloadItem()">
								<span class="fa fa-fw fa-cloud-download" aria-hidden="true"></span> 下载
							</button>
							<input id="downloadIds" type="hidden" name="downloadIds[]">
							</form>
							</shiro:hasPermission>
							
					</div>
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<th style="width: 10px"><label> <input id="allCheck"
									type="checkbox" class="minimal" value="0">
							</label></th>
							<th style="width: 10px">#</th>
							
							<th>组名</th>
							<th>小组成员</th>
							<th>描述</th>
							<th>创建时间</th>
							<th>创建人</th>
							<th>操作</th>

						</tr>
						<c:forEach items="${groups}" var="group" varStatus="status">
							<tr>
								<td><label><input type="checkbox"
										class="minimal deleteCheckbox" value="${group.id}"></label></td>
								<td>${status.count}</td>
								<td>${group.name}</td>
								<td><c:forEach var="employee" items="${group.employees }">
									${employee.name} &nbsp;&nbsp;
								</c:forEach></td>
								<td>${group.description}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${group.createTime}" /></td>
								<td>${group.creatorName}</td>
								
								<td><shiro:hasPermission name="group:update">
										<button id="updateBtn" type="button"
											class="btn btn-xs btn-primary btn-flat" onclick='updateItem(${group.id})'>编辑</button>
									</shiro:hasPermission> <shiro:hasPermission name="group:view">
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='detailItem(${group.id})'>详情</button>
									</shiro:hasPermission> 
							</tr>
						</c:forEach>
					</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination paginationSize="10" page="${page}"
					action="group/search" contentSelector="#content-wrapper"></vino:pagination>
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
		<div class="modal-content">
			
		</div>
	</div>
</div>
<!-- ./新增页面 modal框 -->
<!-- 删除确认页面 modal框 -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">删除用户</h4>
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
			deleteItemsUseModal("input[class*='deleteCheckbox']","group/delete");
		
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
			url : "group/search",//请求的action路径  
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
		modalLoadAndDisplay('group/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('group/'+id);
	}
	
	function detailItem(id){
	
		modalLoadAndDisplay('group/detail/'+id);
	}
	
	function uploadItem(){	
		modalLoadAndDisplay('group/prepareUpload');
	}
	function bindItem(id){
		modalLoadAndDisplay('group/prepareBind/'+id);
		
	}
	
	
	/**
	AJAX不能下载文件，用表单来实现
	*/
	function downloadItem(){	
		var downloadIds = [];
		var i = 0;
		$("input[class*='deleteCheckbox']").each(function(index, item) {
			var isChecked = item.checked;
			if (isChecked == true) {
				downloadIds[i++] = item.value;
			}
		});
		$('#downloadIds').val(downloadIds)
		$('#downloadForm').submit(function(){
			
		});
	}
	
	
</script>