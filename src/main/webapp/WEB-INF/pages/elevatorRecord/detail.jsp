<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">节点档案</h4>
</div>
<div class="modal-body">
	<table class="table table-striped">
		<tr>
			<td>设备编号:</td>
			<td>${elevatorRecord.no}</td>
		</tr>
		<tr>
			<td>设备地址:</td>
			<td>${elevatorRecord.address}</td>
		</tr>
		<tr>
			<td>使用单位:</td>
			<td>${elevatorRecord.unit}</td>
		</tr>
		<tr>
			<td>所属养殖场:</td>
			<td>${elevatorRecord.buildingNumber}</td>
		</tr>
		<tr>
			<td>所属农舍</td>
			<td>${elevatorRecord.elevatorNumber}</td>
		</tr>
		<tr>
			<td>联系方式:</td>
			<td>${elevatorRecord.phone}</td>
		</tr>
		<tr>
			<td>设备类型:</td>
			<td>${elevatorRecord.type}</td>
		</tr>
		<tr>
			<td>风机型号:</td>
			<td>${elevatorRecord.modelNumber}</td>
		</tr>
		<tr>
			<td>载重(kg):</td>
			<td>${elevatorRecord.maxWeight}</td>
		</tr>
		<tr>
			<td>速度(m/s):</td>
			<td>${elevatorRecord.speed}</td>
		</tr>
		<tr>
			<td>年检日期:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd"
					value="${elevatorRecord.inspectionDate}" /></td>
		</tr>
		<tr>
			<td>生产日期:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd"
					value="${elevatorRecord.manufacturingDate}" /></td>
		</tr>
		<tr>
			<td>上次维保时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${elevatorRecord.lastMaintainTime}" /></td>
		</tr>
		<tr>
			<td>维保小组:</td>
			<td>${elevatorRecord.group.name}</td>
		</tr>
		<tr>
			<td>创建者:</td>
			<td>${elevatorRecord.creatorName}</td>
		</tr>
		<tr>
			<td>创建时间:</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${elevatorRecord.createTime}" /></td>
		</tr>
	</table>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
</div>