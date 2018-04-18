<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		故障工单管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active">故障工单管理</li>
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
								<form id="searchForm" action="faultOrder/search" method="get">
									<div class="box-body">
										<div class="row">
											<input hidden="true" name="pageNumber" id="pageNumber">
											<div class="form-group col-md-2">
												<label for="employeeNameLabel">员工名:</label> <input type="text"
													class="form-control" id="employeeNameLabel"
													name="search_employeeName" value="${searchParamsMap.employeeName }">
											</div>
											<div class="form-group col-md-2">
												<label for="groupNameLabel">小组名:</label> <input type="text"
													class="form-control" id="groupNameLabel"
													name="search_groupName" value="${searchParamsMap.groupName }">
											</div>
											<div class="form-group col-md-1">
												<label >是否修好:</label> 
												<select class="form-control" name="search_fixed" >
													<c:choose>
														<c:when test="${searchParamsMap.fixed=='true'}">
															<option value="true" selected="selected">是</option>
															<option value="false" >否</option>
														</c:when>												
														<c:otherwise>
															<option value="false" selected="selected">否</option>
															<option value="true">是</option>
														
														</c:otherwise>
														
														</c:choose>
														
			
												</select>
											</div>
											
									
										
											<!-- Date range -->
											<div class="form-group  col-md-4">
												<label>故障发生时间:</label>
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="occuredTimeRange" name="search_occuredTimeRange"
														value="${searchParamsMap.occuredTimeRange}">
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
						<h3 class="box-title">故障工单列表</h3>
					</div>
					<div class="btn-group">
						<!-- 注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。 -->
						
				<%-- 		<shiro:hasPermission name="faultOrder:create">
							<button id="addBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="addItem()">
								<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
							</button>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="faultOrder:delete">
							<button id="deleteBtn" type="button"
								class="btn  btn-danger btn-flat margin">
								<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="faultOrder:upload">
							<button id="uploadBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="uploadItem()">
								<span class="fa fa-fw fa-cloud-upload" aria-hidden="true"></span> 上传
							</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="faultOrder:download">
							<form id="downloadForm" action="faultOrder/download" method="get" >
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
							<th>工单号</th>
							<th>电梯编号</th>
							<th>故障描述</th>
							<th>故障原因</th>							
							<th>故障时间</th>
							<th>接单时间</th>
							<th>维保小组</th>
							<th>维保人员</th>
							<th>是否修好</th>
							<th >操作</th>

						</tr>
						<c:forEach items="${faultOrders}" var="faultOrder" varStatus="status">
							<tr>
								<td><label><input type="checkbox"
										class="minimal deleteCheckbox" value="${faultOrder.id}"></label></td>
								<td>${status.count}</td>
								<td>${faultOrder.no }</td>
								<td>${faultOrder.elevatorRecord.no}</td>
								<td>${faultOrder.description}</td>
								<td>${faultOrder.reason}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${faultOrder.occuredTime}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${faultOrder.receivingTime}" /></td>
								<td>${faultOrder.elevatorRecord.group.name}</td>
								<td>${faultOrder.employee.name}</td>
								<c:choose>
									<c:when test="${faultOrder.fixed}">
										<td><span class="badge bg-green">已修好</span></td>
									</c:when>
									<c:otherwise>
										<td><span class="badge bg-red">未修好</span></td>
									</c:otherwise>
								</c:choose>				

								<td><shiro:hasPermission name="faultOrder:update">
										<button id="updateBtn" type="button"
											class="btn btn-xs btn-primary btn-flat" onclick='updateItem(${faultOrder.id})'>编辑</button>
									</shiro:hasPermission> <shiro:hasPermission name="faultOrder:view">
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='detailItem(${faultOrder.id})'>详情</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="faultOrder:feedback">
										<button id="feedbackBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='feedbackOrder(${faultOrder.id})'>反馈</button>
									</shiro:hasPermission>
									
									</td>
							</tr>
						</c:forEach>
					</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination paginationSize="10" page="${page}"
					action="faultOrder/search" contentSelector="#content-wrapper"></vino:pagination>
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
	$('#occuredTimeRange').daterangepicker({		 
	        showDropdowns: true
	});

	
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
			deleteItemsUseModal("input[class*='deleteCheckbox']","faultOrder/delete");
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
			url : "faultOrder/search",//请求的action路径  
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
		modalLoadAndDisplay('faultOrder/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('faultOrder/'+id);
	}
	
	function detailItem(id){	
		modalLoadAndDisplay('faultOrder/detail/'+id);
	}
	
	function feedbackOrder(id){
		
		modalLoadAndDisplay('faultOrder/prepareFeedback/'+id);
	}

	
</script>