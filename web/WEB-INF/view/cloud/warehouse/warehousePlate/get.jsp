
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_warehouse_plate明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">盘库详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">盘库编号:</th>
				<td>${warehousePlate.plateStorgeCode}</td>
			</tr>
 
			<tr>
				<th width="20%">编制人:</th>
				<td>${warehousePlate.operator}</td>
			</tr>
 
			<tr>
				<th width="20%">编制日期:</th>
				<td>
				<fmt:formatDate value="${warehousePlate.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">仓库名称:</th>
				<td>${warehousePlate.warehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">所属企业:</th>
				<td>${warehousePlate.companyid}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="10" style="text-align: center"> 盘库详细信息表</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>物品编码</th>
				<th>物品名称</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>账面数</th>
				<th>实际数</th>
				<th>差异量</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${warehousePlateDetailList}" var="warehousePlateDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${warehousePlateDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${warehousePlateDetailItem.serialNum}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.materialCode}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.materialName}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.attributeDes}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.units}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.oldNums}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.actualNums}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.differences}</td>								
						<td style="text-align: center">${warehousePlateDetailItem.remark}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

