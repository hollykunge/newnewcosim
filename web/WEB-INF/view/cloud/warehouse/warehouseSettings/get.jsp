
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>初始化设置明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">初始化设置详细信息</span>
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
				<th width="20%">所属公司:</th>
				<td>${warehouseSettings.companyid}</td>
			</tr>
 
			<tr>
				<th width="20%">编码:</th>
				<td>${warehouseSettings.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${warehouseSettings.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${warehouseSettings.operator}</td>
			</tr>
 
			<tr>
				<th width="20%">日期:</th>
				<td>
				<fmt:formatDate value="${warehouseSettings.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="8" style="text-align: center">初始化设置-明细 :初始化设置-明细</td>
			</tr>
			<tr>
				<th>批次/编号</th>
				<th>物品编码</th>
				<th>物品名称</th>
				<th>物品规格</th>
				<th>单位</th>
				<th>货架</th>
				<th>数量</th>
				<th>单价</th>
			</tr>	
			<c:forEach items="${warehouseSettingsDetailList}" var="warehouseSettingsDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${warehouseSettingsDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${warehouseSettingsDetailItem.batchnumber}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.productcode}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.productname}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.specifications}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.units}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.shelves}</td>								
						<td style="text-align: center">${warehouseSettingsDetailItem.nums}</td>	
							<td style="text-align: center">${warehouseSettingsDetailItem.price}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

