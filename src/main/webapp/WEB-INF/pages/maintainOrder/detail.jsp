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
			<td>工单:</td>
			<td>${maintainOrder.no}</td>
		</tr>
		<tr>
			<td>电梯编号:</td>
			<td>${maintainOrder.elevatorRecord.no}</td>
		</tr>
		<tr>
			<td>维保类型:</td>
			<td>${maintainOrder.maintainType.name}</td>
		</tr>
		<tr>
			<td>截止日期:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd"
					value="${maintainOrder.finalTime}" /></td>
		</tr>
		<tr>
			<td>员工接单时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${maintainOrder.receivingTime}" /></td>
		</tr>
		<tr>
			<td>员工签到时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${maintainOrder.signInTime}" /></td>
		</tr>
		<tr>
			<td>员工签退时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${maintainOrder.signOutTime}" /></td>
		</tr>
		<tr>
			<td>签到地点:</td>
			<td>${maintainOrder.signInAddress}</td>
		</tr>
		<tr>
			<td>签退地点:</td>
			<td>${maintainOrder.signOutAddress}</td>
		</tr>
		<tr>
			<td>维保小组:</td>
			<td>${maintainOrder.elevatorRecord.group.name}</td>
		</tr>
		<tr>
			<td>维保人员:</td>
			<td>${maintainOrder.employee.name}</td>
		</tr>
		<tr>
			<td>是否完成:</td>
			<c:choose>
				<c:when test="${maintainOrder.finished}">
					<td><span class="badge bg-green">已完成</span></td>
				</c:when>
				<c:otherwise>
					<td><span class="badge bg-red">未完成</span></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr><td>备注:</td><td>${maintainOrder.remark}</td></tr>
		<tr>
			<td>创建者:</td>
			<td>${maintainOrder.creatorName}</td>
		</tr>
		<tr>
			<td>创建时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${maintainOrder.createTime}" /></td>
		</tr>
	</table>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
</div>