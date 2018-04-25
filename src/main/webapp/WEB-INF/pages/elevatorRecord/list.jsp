<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		设备档案管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active">设备档案管理</li>
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
								<form id="searchForm" action="elevatorRecord/search" method="get">
								<input name="pageSize" id="pageSize" value="${page.size}" hidden="true">
									<div class="box-body">
										<div class="row">
											<input hidden="true" name="pageNumber" id="pageNumber">
											<div class="form-group col-md-2">
												<label for="addressLabel">设备地址:</label> <input type="text"
													class="form-control" id="addressLabel"
													name="search_address" value="${searchParamsMap.address }">
											</div>
											<div class="form-group col-md-2">
												<label for="unitLabel">使用单位:</label> <input type="text"
													class="form-control" id="unitLabel"
													name="search_unit"
													value="${searchParamsMap.unit }">
											</div>
											<div class="form-group col-md-2">
												<label for="groupNameLabel">小组:</label> <input type="text"
													class="form-control" id="groupNameLabel"
													name="search_groupName"
													value="${searchParamsMap.groupName }">
											</div>
											<div class="form-group col-md-2">
												<label for="modelNumberLabel">设备型号:</label> <input type="text"
													class="form-control" id="modelNumberLabel"
													name="search_modelNumber"
													value="${searchParamsMap.modelNumber }">
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
						<h3 class="box-title">设备档案列表</h3>
					</div>
					<div class="btn-group">
						<!-- 注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。 -->
						
						<shiro:hasPermission name="elevatorRecord:create">
							<button id="addBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="addItem()">
								<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="elevatorRecord:delete">
							<button id="deleteBtn" type="button"
								class="btn  btn-danger btn-flat margin">
								<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
							</button>
						</shiro:hasPermission>
						
						<%-- 
						<shiro:hasPermission name="elevatorRecord:bindMaintainPlan">
						<button id="bindMaintainPlanBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="bindMaintainPlan()">
								<span class="fa fa-fw fa-plus" aria-hidden="true"></span> 绑定保养计划 
						</button>
						</shiro:hasPermission>
						 --> 
						
						 
						 
						<%-- <shiro:hasPermission name="elevatorRecord:upload">
							<button id="uploadBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="uploadItem()">
								<span class="fa fa-fw fa-cloud-upload" aria-hidden="true"></span> 上传
							</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="elevatorRecord:download">
							<form id="downloadForm" action="elevatorRecord/download" method="get" >
							<button id="downloadBtn" type="submit"
								class="btn  btn-primary btn-flat margin2" 
								 onclick="downloadItem()">
								<span class="fa fa-fw fa-cloud-download" aria-hidden="true"></span> 下载
							</button>
							<input id="downloadIds" type="hidden" name="downloadIds[]">
							</form>
							</shiro:hasPermission> --%>
							<!-- <form action="elevatorRecord/changePageSize" id="changePageSizeForm" method="get"> -->
						
						<!-- </form> -->
					</div>
					<div class="pull-right">
					显示条数:&nbsp;<select id="changePageSize" name="pageSize" class=".js-example-basic-single" >
							<c:choose>
								<c:when test="${page.size==10}">
									<option value="10" selected>10</option>
									<option value="20">20</option>
									<option value="40">40</option>
								</c:when>
								<c:when test="${page.size==20}">
									<option value="10">10</option>
									<option value="20" selected>20</option>
									<option value="40">40</option>
								</c:when>
								<c:when test="${page.size==40}">
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="40" selected>40</option>
								</c:when>
								<c:otherwise>
									<option value="10" selected>10</option>
									<option value="20">20</option>
									<option value="40">40</option>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<th style="width: 10px"><label> <input id="allCheck"
									type="checkbox" class="minimal" value="0">
							</label></th>
							<!-- <th style="width: 10px">#</th> -->
							<th>设备编号</th>
							<th>设备地址</th>
							<th>使用单位</th>
							<th>设备类型</th>
							<th>所属小组</th>
							<th>客户电话</th>
							<!-- <th>上次保养时间</th> -->
							<!-- 
							<th>保养计划</th>
							 -->
							
							
							<th >操作</th>

						</tr>
						<c:forEach items="${elevatorRecords}" var="elevatorRecord" varStatus="status">
							<tr>
								<td><label><input type="checkbox"
										class="minimal deleteCheckbox" value="${elevatorRecord.id}"></label></td>
								<%-- <td>${status.count}</td> --%>
								<td>${elevatorRecord.no }</td>
								<td>${elevatorRecord.address}</td>
								<td>${elevatorRecord.unit}</td>
								<td>${elevatorRecord.type} </td>
								<td>${elevatorRecord.group.name }</td>
								<td>${elevatorRecord.phone}</td>
							<%-- 	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${elevatorRecord.lastMaintainTime}" /></td> --%>
								<td>${elevatorRecord.maintainPlan.name}</td>
								

								<td><shiro:hasPermission name="elevatorRecord:update">
										<button id="updateBtn" type="button"
											class="btn btn-xs btn-primary btn-flat" onclick='updateItem(${elevatorRecord.id})'>编辑</button>
									</shiro:hasPermission> 
									<shiro:hasPermission name="elevatorRecord:view">
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='detailItem(${elevatorRecord.id})'>详情</button>
									</shiro:hasPermission> 
									<shiro:hasPermission name="elevatorRecord:bind">
										<button id="bindRoleBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='bindItem(${elevatorRecord.id})'>小组绑定</button>
					<!--
									</shiro:hasPermission>
										<shiro:hasPermission name="elevatorRecord:addFaultOrder">
										 
										<button id="addFaultOrderBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='addFaultOrder(${elevatorRecord.id})'>发布故障工单</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="elevatorRecord:addMaintainOrder">
										<button id="addMaintainOrderBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='addMaintainOrder(${elevatorRecord.id})'>发布保养工单</button>
									</shiro:hasPermission>
					 -->				
									</td>
							</tr>
						</c:forEach>
					</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination paginationSize="${page.size}" page="${page}"
					action="elevatorRecord/search" contentSelector="#content-wrapper"></vino:pagination>
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
	$('#reservation').daterangepicker();
	//Date range picker with time picker
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});	
	/* icheck 初始化 详情：https://github.com/fronteed/icheck */
   	iCheckInit();
 	/* iCheck事件监听 详情：https://github.com/fronteed/icheck */
 	$(document).ready(function() {
	        $(".js-example-basic-single").select2();
	        
	      });
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
			deleteItemsUseModal("input[class*='deleteCheckbox']","elevatorRecord/delete");
		});
	});
	/* button监听事件 */
	$(document).ready(function(){
		$("#deleteBtn").click(function(){
			$("#deleteConfirmModal").modal();	
		});
		
	});	

	$('#changePageSize').change(function(){
		var pageSize=$('#changePageSize').val();
		$('#pageSize').val(pageSize);
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data : $("#searchForm").serialize(),		 
			url : "elevatorRecord/search",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#content-wrapper").html(data);//刷新content页面	
			
			}
		});
	});
	$("#searchBtn").click(function() {
		$('#pageNumber').val(1);
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data : $("#searchForm").serialize(),		 
			url : "elevatorRecord/search",//请求的action路径  
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
	//绑定保养计划，允许选择多个设备
	function bindMaintainPlan(){
		var elevatorRecordIds = [];
		var i = 0;
		$("input[class*='deleteCheckbox']").each(function(index, item) {
			var isChecked = item.checked;
			if (isChecked == true) {
				elevatorRecordIds[i++] = item.value;
			}
		});
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data : $.param({
				elevatorRecordIds : elevatorRecordIds
			}),		 
			url : "elevatorRecord/prepareBindMaintainPlan",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#modal .modal-content").html(data);//刷新content页面	
				$("#modal").modal();
			}
		});
		
	}
	function addItem(){
		modalLoadAndDisplay('elevatorRecord/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('elevatorRecord/'+id);
	}
	
	function detailItem(id){
	
		modalLoadAndDisplay('elevatorRecord/detail/'+id);
	}
	
	function uploadItem(){	
		modalLoadAndDisplay('elevatorRecord/prepareUpload');
	}
	function bindItem(id){
		modalLoadAndDisplay('elevatorRecord/prepareBind/'+id);
	}
	function addFaultOrder(id){
		modalLoadAndDisplay('elevatorRecord/prepareAddFaultOrder/'+id);
	}
	function addMaintainOrder(id){
		modalLoadAndDisplay('elevatorRecord/prepareAddMaintainOrder/'+id);
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