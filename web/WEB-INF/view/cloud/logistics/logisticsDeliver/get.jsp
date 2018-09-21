
<%--
	time:2013-05-16 15:35:33
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>CLOUD_LOGISTICS_DELIVER明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_LOGISTICS_DELIVER详细信息</span>
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
				<th width="20%">单证号:</th>
				<td>${logisticsDeliver.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${logisticsDeliver.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${logisticsDeliver.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人名称:</th>
				<td>${logisticsDeliver.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">配送企业ID:</th>
				<td>${logisticsDeliver.deliverEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">配送企业名称:</th>
				<td>${logisticsDeliver.deliverEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">配送人员ID:</th>
				<td>${logisticsDeliver.delivererId}</td>
			</tr>
 
			<tr>
				<th width="20%">配送人员名称:</th>
				<td>${logisticsDeliver.delivererName}</td>
			</tr>
 
			<tr>
				<th width="20%">配送时间:</th>
				<td>
				<fmt:formatDate value="${logisticsDeliver.deliverTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发货企业ID:</th>
				<td>${logisticsDeliver.postEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">发货企业名称:</th>
				<td>${logisticsDeliver.postEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">发货企业联系人ID:</th>
				<td>${logisticsDeliver.posterId}</td>
			</tr>
 
			<tr>
				<th width="20%">发货企业联系人名称:</th>
				<td>${logisticsDeliver.posterName}</td>
			</tr>
 
			<tr>
				<th width="20%">发货企业联系方式:</th>
				<td>${logisticsDeliver.posterPhone}</td>
			</tr>
 
			<tr>
				<th width="20%">发货地址:</th>
				<td>${logisticsDeliver.postAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">收货企业ID:</th>
				<td>${logisticsDeliver.receiveEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">收货企业名称:</th>
				<td>${logisticsDeliver.receiveEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货企业联系人ID:</th>
				<td>${logisticsDeliver.receiverId}</td>
			</tr>
 
			<tr>
				<th width="20%">收货企业联系人名称:</th>
				<td>${logisticsDeliver.receiverName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货企业联系方式:</th>
				<td>${logisticsDeliver.receiverPhone}</td>
			</tr>
 
			<tr>
				<th width="20%">收货地址:</th>
				<td>${logisticsDeliver.receiveAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">计划详单ID:</th>
				<td>${logisticsDeliver.planDetailId}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

