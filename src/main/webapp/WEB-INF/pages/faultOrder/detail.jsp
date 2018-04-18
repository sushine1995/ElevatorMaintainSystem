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
			<td>工单号:</td>
			<td>${faultOrder.no}</td>
		</tr>
		<tr>
			<td>设备编号:</td>
			<td>${faultOrder.elevatorRecord.no}</td>
		</tr>
		<tr>
			<td>故障描述:</td>
			<td>${faultOrder.description}</td>
		</tr>
		<tr>
			<td>故障发生时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${faultOrder.occuredTime}" /></td>
		</tr>
		<tr>
			<td>员工接单时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${faultOrder.receivingTime}" /></td>
		</tr>
		<tr>
			<td>员工签到时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${faultOrder.signInTime}" /></td>
		</tr>
		<tr>
			<td>员工签退时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${faultOrder.signOutTime}" /></td>
		</tr>
		<tr>
			<td>签到地点:</td>
			<td>${faultOrder.signInAddress}</td>
		</tr>
		<tr>
			<td>签退地点:</td>
			<td>${faultOrder.signOutAddress}</td>
		</tr>
		<tr>
			<td>维保小组:</td>
			<td>${faultOrder.elevatorRecord.group.name}</td>
		</tr>
		<tr>
			<td>维保人员:</td>
			<td>${faultOrder.employee.name}</td>
		</tr>
		<tr>
			<td>是否修好:</td>
			<c:choose>
				<c:when test="${faultOrder.fixed}">
					<td><span class="badge bg-green">已修好</span></td>
				</c:when>
				<c:otherwise>
					<td><span class="badge bg-red">未修好</span></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr><td>备注:</td><td>${faultOrder.remark}</td></tr>
		<tr>
			<td>创建者:</td>
			<td>${faultOrder.creatorName}</td>
		</tr>
		<tr>
			<td>创建时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${faultOrder.createTime}" /></td>
		</tr>
	</table>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
</div>