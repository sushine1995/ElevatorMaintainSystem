<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">小组绑定</h4>
</div>

<div class="modal-body">
	<form id="bindForm" method="post">
		<input name="elevatorRecordId" value="${elevatorRecord.id}" hidden="true" />
		
		<div class="form-group">
			<label for="resourceSelect" class="control-label">绑定小组:</label> <select
				id="resourceSelect" data-placeholder="选择小组"
				class="form-control select2"
				tabindex="-1"  style="width: 100%"  name="groupId">
				
				<optgroup label="选择小组">
					<option value="0">无所属小组</option>
					<c:if test="${elevatorRecord.group.id>0}"> <!-- 已拥有的小组 -->
					<option value="${elevatorRecord.group.id}" selected="selected">${elevatorRecord.group.name}</option>
					</c:if>
					<c:forEach var="group" items="${availableGroups}" varStatus="status">
						<option value="${group.id}">${group.name}</option>
					</c:forEach>

				</optgroup>
				
			</select>
		</div>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary" id="bindSubmitBtn">提交</button>
</div>

<script type="text/javascript">	
/* 提交表单 */
	$('#modal').on('shown.bs.modal', function(event) {

		$("#bindSubmitBtn").click(function() {
			

			
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : $("#bindForm").serialize(),			  
				url : "elevatorRecord/bind",//请求的action路径  
				error : function() {//请求失败处理函数  
					alert('失败');
				},
				success : function(data) { //请求成功后处理函数。    
					alert("success");						
					$('#modal').on('hidden.bs.modal',function(event){//当modal框完全隐藏后再刷新页面content，要不然有bug
						$("#content-wrapper").html(data);//刷新content页面
					});
				}
			});
		});
	});
	   
	    $(document).ready(function() {
	        $(".select2").select2({
	        	  placeholder: "选择小组"
	        
	        });
	        
	      });
	       
	     
</script>
