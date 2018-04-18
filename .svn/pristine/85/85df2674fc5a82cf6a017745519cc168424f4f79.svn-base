<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">员工详情</h4>
</div>
<div class="modal-body">
	<table class="table table-striped">
		<tr>
			<td>用户名:</td>
			<td>${employee.username}</td>
		</tr>
		<tr>
			<td>姓名:</td>
			<td>${employee.name}</td>
		</tr>
		<tr>
			<td>状态:</td>
			<c:choose>
				<c:when test="${employee.locked}">
					<td><span class="badge bg-red">锁定</span></td>
				</c:when>
				<c:otherwise>
					<td><span class="badge bg-green">未锁定</span></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td>创建者:</td>
			<td>${employee.creatorName}</td>
		</tr>
		<tr>
			<td>创建时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${employee.createTime}" /></td>
		</tr>
	</table>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
</div>