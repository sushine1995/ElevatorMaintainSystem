<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">详情</h4>
</div>
<div class="modal-body">
	<table class="table table-striped">
		<tr>
			<td>合同名:</td>
			<td>${maintainPlan.name}</td>
		</tr>
		<tr>
			<td>合同编号:</td>
			<td>${maintainPlan.no}</td>
		</tr>
		<tr>
			<td>负责小组:</td>
			<td>${maintainPlan.group.name}</td>
		</tr>
		<tr>
			<td>截止日期:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd"
					value="${maintainPlan.endDate}" /></td>
		</tr>
		<tr>
			<td>创建者:</td>
			<td>${maintainPlan.creatorName}</td>
		</tr>
		<tr>
			
			<td>创建时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${maintainPlan.createTime}" /></td>
		</tr>
	</table>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
</div>