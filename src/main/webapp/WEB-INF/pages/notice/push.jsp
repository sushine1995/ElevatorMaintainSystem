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
						<li><a id="noReadNoticeBtn" href="notice/noRead"><i
								class="fa fa-inbox"></i> 未读 <span
								class="label label-primary pull-right noReadNoticeCount">${noReadNoticeCount}</span></a></li>
						<li><a id="readNoticeBtn" href="notice/read"><i
								class="fa fa-envelope-o"></i>已读</a></li>
						<li class="active"><a id="pushNoticeBtn"
							href="notice/preparePush"><i class="fa  fa-file-text-o"></i>推送</a></li>

					</ul>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /. box -->

			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="col-md-9">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">推送</h3>

					<!-- /.box-tools -->
				</div>
				<!-- /.box-header -->
				<div class="box-body ">

					<form id="pushForm" action="" method="post">
						<div class="form-group">
							<label for="title" class="control-label"><font
								color="red">*</font>通知标题:</label> <input type="text"
								class="form-control " id="title" name="title">
						</div>
						<div class="form-group">
							<label for="content" class="control-label"><font color="red">*</font>通知内容:</label>
							<input type="text" class="form-control " id="content" name="content">
						</div>
						<div class="form-group">
							<label for="type" class="control-label"><font
								color="red">*</font>目标用户:</label> 
								<input name="type" type="radio"
								value="app" checked="checked">全部 
								<!-- <input name="type" type="radio"
								value="employee" >特定用户  -->
								<input id="groupRadio"
								name="type" type="radio" value="group"  >特定小组
								
								<div id="groupSelectDiv" class="zTreeDemoBackground right " hidden="true">
							<ul id="groupTree" class="ztree"></ul></div>
						</div>
						
						
						<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
					</form>
				</div>
				<!-- /.box-body -->
				<div class="box-footer no-padding">
					<div class="mailbox-controls"></div>
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
		<div class="modal-content"></div>
	</div>
</div>
<!-- ./新增页面 modal框 -->

<script>
	$(document).ready(function(){
		$("input:radio[name='type']").click(function(){
			var isCheck=$(this).attr("checked");
			var val=$('input:radio[name="type"]:checked').val();
			console.log(val);
			switch(val){
			case "app":$("#groupSelectDiv").hide();break;
			case "group":$("#groupSelectDiv").show();break;
			case "employee":$("#groupSelectDiv").hide();break;
			default:break;
			}
			
		});
	});
	function modalLoadAndDisplay(url) {
		$('#modal .modal-content').load(url, function() {
			$("#modal").modal();
		});
	}
	function detailItem(id) {
		modalLoadAndDisplay('notice/detail/' + id);
	}
	$(document).ready(function() {
		$("#noReadNoticeBtn,#readNoticeBtn,#pushNoticeBtn").click(function() {

			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : this.href,//请求的action路径
				error : function() {//请求失败处理函数
					alert('请求失败');
				},
				success : function(data) { //请求成功后处理函数。
					$("#content-wrapper").html(data);
				}
			});
			return false;
		});
	});
	
	/* ztree */
    var setting = {  
    	data: {
			simpleData: {
					enable: true,
					idKey:"id",
					pIdKey:"pId"
				},
			
    		view: {
				showIcon: true
				}
			},
	    check: {
			enable: true,
			chkStyle : "checkbox"
			
		}
	};  
   	 
	   var zNodes;  
	   $(document).ready(function(){  
		   $.ajax({  
	            async : false,  
	            cache:false,  
	            type: 'GET',  
	            dataType : "json",  
	            url: "group/json/all/",//请求的action路径  
	            error: function () {//请求失败处理函数  
	                alert('请求失败');  
	            },  
	            success:function(data){ //请求成功后处理函数。    	                
	                zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes  
	            }  
	        });  
	      
	       $.fn.zTree.init($("#groupTree"), setting, zNodes);
	   });
	   
	   function getCheckedGroupIds(){
			var treeObj = $.fn.zTree.getZTreeObj("groupTree");	
			var nodes = treeObj.getCheckedNodes(true);
			var groupIds=[];
			for(var i=0;i<nodes.length;i++){
				groupIds[i]=nodes[i].id;
			}
			return groupIds;	
			}
	   $(document).ready(function(){   	
	    $("#title").focus();//将焦点放在用户名输入上
		 $("#pushForm").validate({
				 rules:{
					 title:{ //格式：domId: 规则
						 required:true						 	
						 },
					 content:{
						 required:true			
					 }
					
				 },
				 										
			 submitHandler : function(form){
				 var groupIds=getCheckedGroupIds();	//获取被选中的maitainPlan的id
		           	$.ajax({
						async : false,
						cache : false,
						type : 'POST',
						data :  $("#pushForm").serialize()+"&"+$.param({groupIds:groupIds}),
						//dataType : "json",
						url : "notice/push",//请求的action路径  
						error : function() {//请求失败处理函数  
							alert('失败');
						},
						success : function(data) { //请求成功后处理函数。
							if(data=="\"success\""){
								alert("success");
							}else{
								alert('失败');
								
							}
							
						}
					});
		        }    
		    });
	   });
</script>