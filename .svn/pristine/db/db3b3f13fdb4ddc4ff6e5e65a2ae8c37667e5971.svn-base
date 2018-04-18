<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		消息通知 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
		<li class="active">消息通知</li>
	</ol>
</section>

<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="col-md-3">
		<!-- 	<a href="compose.html"
				class="btn btn-primary btn-block margin-bottom">Compose</a> -->
			<div class="box box-solid">
				<div class="box-header with-border">
					<h3 class="box-title">Folders</h3>
					<div class="box-tools">
						<button class="btn btn-box-tool" data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<div class="box-body no-padding">
					<ul class="nav nav-pills nav-stacked">
						<li class="active"><a id="noReadNoticeBtn" href="notice/noRead"><i class="fa fa-inbox"></i>
								未读 <span class="label label-primary pull-right noReadNoticeCount">${noReadNoticeCount}</span></a></li>
						<li><a id="readNoticeBtn" href="notice/read"><i class="fa fa-envelope-o"></i>已读</a></li>
						<li><a id="pushNoticeBtn" href="notice/preparePush"><i class="fa  fa-file-text-o"></i>推送</a></li>
					</ul>
				</div>
				<!-- /.box-body -->
			</div>
	
		</div>
		<!-- /.col -->
		<div class="col-md-9">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">未读</h3>
				
				</div>
				<!-- /.box-header -->
				<div class="box-body no-padding">
		
					<div class="table-responsive mailbox-messages">
						<table class="table table-hover table-striped">
							<thead>
								<tr><th>类型</th><th>内容</th><th>时间</th></tr>
							</thead>
							<tbody>
								<c:forEach var="notice" items="${noReadNotices}">
								<tr onclick="readItem(${notice.id})">
									
									<td class="mailbox-name">
									${notice.type }
									</td>
									<td class="mailbox-subject"><b><!-- 未读时候加粗 -->
										${notice.content}</b> </td>
									
									<td class="mailbox-date"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${notice.createTime}" /></td>
								</tr>
								</c:forEach>
								
								
							</tbody>
						</table>
						<!-- /.table -->
					</div>
					<!-- /.mail-box-messages -->
				</div>
				<!-- /.box-body -->
				<div class="box-footer no-padding">
					<div class="mailbox-controls">
					
						<div class="pull-right">
				<vino:pagination paginationSize="10" page="${page}"
					action="notice/noRead" contentSelector="#content-wrapper"></vino:pagination>
						
						
						</div>
						<!-- /.pull-right -->
					</div>
				</div>
			</div>
			<!-- /. box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
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

<script>

	function modalLoadAndDisplay(url){	
		$('#modal .modal-content').load(url,function(){
			$("#modal").modal();
		});		
	}
	function readItem(id){
		
		modalLoadAndDisplay('notice/read/'+id);
	}
	$(document).ready(function(){
    	 $("#noReadNoticeBtn,#readNoticeBtn,#pushNoticeBtn").click(function(){
    		 
    		 $.ajax({
                 async : false,
                 cache:false,
                 type: 'GET',               
                 url: this.href,//请求的action路径
                 error: function () {//请求失败处理函数
                     alert('请求失败');
                 },
                 success:function(data){ //请求成功后处理函数。
                 $("#content-wrapper").html(data);
                 }
    	 });
    	 return false;       		
      	});
      	});
		
</script>