
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_warehouse_archive明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">仓库档案详细信息</span>
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
				<th width="20%">仓库名称:</th>
				<td>${warehouseArchive.warehousename}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人名称:</th>
				<td>${warehouseArchive.contactname}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人座机:</th>
				<td>${warehouseArchive.contactlandlilne}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人手机:</th>
				<td>${warehouseArchive.contactphone}</td>
			</tr>
 
			<tr>
				<th width="20%">联系人邮箱:</th>
				<td>${warehouseArchive.contactemail}</td>
			</tr>
			<tr>
				<th width="20%">仓库地址:</th>
				<td>${warehouseArchive.address}</td>
			</tr>
 
			<tr>
				<th width="20%">邮编:</th>
				<td>${warehouseArchive.zipcode}</td>
			</tr>
			<tr>
				<th width="20%">制单人:</th>
				<td>${warehouseArchive.operateid}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${warehouseArchive.operatename}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${warehouseArchive.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${warehouseArchive.remark}</td>
			</tr>
 
			<tr>
				<th width="20%">所属企业:</th>
				<td>${warehouseArchive.companyid}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

