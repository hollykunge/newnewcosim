
<%--
	time:2013-05-16 14:48:10
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>物流计划单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物流计划单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list0.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th style="width:120px">单证号:</th>
				<td colspan="3">${logisticPlan.code}</td>
			</tr>
            
            <tr>
				<th style="width:120px">制单人ID:</th>
				<td>${logisticPlan.operaterId}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${logisticPlan.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			
 
			<tr>
				<th style="width:120px">制单人姓名:</th>
				<td>${logisticPlan.operaterName}</td>
				<th style="width:120px">来源单据ID:</th>
				<td>${logisticPlan.sourceformId}</td>
			</tr>
			<tr>
				<th style="width:120px">来源单据类型:</th>
				<td>${logisticPlan.sourceformType}</td>
				<th style="width:120px">来源单据号:</th>
				<td>${logisticPlan.sourceformCode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">预计发货日期:</th>
				<td>
				<fmt:formatDate value="${logisticPlan.forecastPostDate}" pattern="yyyy-MM-dd"/>
				</td>
				<th style="width:120px">预计收货日期:</th>
				<td>
				<fmt:formatDate value="${logisticPlan.forecastReceiveDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width:120px">物流公司ID :</th>
				<td>${logisticPlan.logisticsEnterpId}</td>
				<th style="width:120px">物流公司名称:</th>
				<td>${logisticPlan.logisticsEnterpName}</td>
			</tr>
 
			<tr>
				<th style="width:120px">重量:</th>
				<td>${logisticPlan.weight}</td>
				<th style="width:120px">运费:</th>
				<td>${logisticPlan.shipPrise}</td>
			</tr>
 
			<tr>
				<th style="width:120px">运费承担方:</th>
				<td>${logisticPlan.freightBearer}</td>
				<th style="width:120px">起始发货企业ID:</th>
				<td>${logisticPlan.startPostEnterpId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">起始发货企业名称:</th>
				<td>${logisticPlan.startPostEnterpName}</td>
				<th style="width:120px">起始发货人ID:</th>
				<td>${logisticPlan.startPosterId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">起始发货人名称:</th>
				<td>${logisticPlan.startPosterName}</td>
				<th style="width:120px">起始发货人联系方式:</th>
				<td>${logisticPlan.startPosterPhone}</td>
			</tr>
 
			<tr>
				<th style="width:120px">起始发货地址:</th>
				<td>${logisticPlan.startPostAddress}</td>
				<th style="width:120px">起始发货仓库ID:</th>
				<td>${logisticPlan.startPostWarehouseId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">起始发货仓库名称:</th>
				<td>${logisticPlan.startPostWarehouseName}</td>
				<th style="width:120px">最终收货企业ID:</th>
				<td>${logisticPlan.endReceiveEnterpId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">最终收货企业名称 :</th>
				<td>${logisticPlan.endReceiveEnterpName}</td>
				<th style="width:120px">最终收货人ID:</th>
				<td>${logisticPlan.endReceiverId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">最终收货人名称:</th>
				<td>${logisticPlan.endReceiverName}</td>
				<th style="width:120px">最终收货人联系方式:</th>
				<td>${logisticPlan.endReceiverPhone}</td>
			</tr>
 
			<tr>
				<th style="width:120px">最终收货地址:</th>
				<td>${logisticPlan.endReceiveAddress}</td>
				<th style="width:120px">最终收货仓库ID:</th>
				<td>${logisticPlan.endReceiveWarehouseId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">最终收货仓库名称:</th>
				<td colspan="3">${logisticPlan.endReceiveWarehouseName}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="7" style="text-align: center">物流计划详单</td>
			</tr>
			<tr>
				<th>物品ID</th>
				<th>物品编号</th>
				<th>物品名</th>
				<th>物品分类</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>数量</th>
			</tr>	
			<c:forEach items="${logisticMaterialList}" var="logisticMaterialItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${logisticMaterialItem.id}"  class="inputText"/>
						<td style="text-align: center">${logisticMaterialItem.materielId}</td>								
						<td style="text-align: center">${logisticMaterialItem.materielCode}</td>								
						<td style="text-align: center">${logisticMaterialItem.materielName}</td>								
						<td style="text-align: center">${logisticMaterialItem.materielLev}</td>								
						<td style="text-align: center">${logisticMaterialItem.attributeInfo}</td>								
						<td style="text-align: center">${logisticMaterialItem.unit}</td>								
						<td style="text-align: center">${logisticMaterialItem.num}</td>								
				</tr>
			</c:forEach>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="26" style="text-align: center">物流计划详单</td>
			</tr>
			<tr>
				<th>发货单ID</th>
				<th>物流环节类型</th>
				<th>物流环节步骤</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>运输企业ID</th>
				<th>运输企业名称</th>
				<th>运输企业联系人ID</th>
				<th>运输企业联系人姓名</th>
				<th>运输企业联系方式</th>
				<th>取货企业ID</th>
				<th>取货企业名称</th>
				<th>取货企业联系人ID</th>
				<th>取货企业联系人名称</th>
				<th>取货企业联系方式</th>
				<th>取货仓库ID</th>
				<th>取货仓库名称</th>
				<th>取货地址</th>
				<th>收货企业ID</th>
				<th>收货企业名称</th>
				<th>收货企业联系人ID</th>
				<th>收货企业联系人姓名</th>
				<th>收货企业联系方式</th>
				<th>收货仓库ID</th>
				<th>收货仓库名称</th>
				<th>收货地址</th>
			</tr>	
			<c:forEach items="${logisticsPlanDtlList}" var="logisticsPlanDtlItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${logisticsPlanDtlItem.id}"  class="inputText"/>
						<td style="text-align: center">${logisticsPlanDtlItem.shipOrderId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shipStepType}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shipStepLev}</td>								
						<td style="text-align: center"><fmt:formatDate value='${logisticsPlanDtlItem.startTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${logisticsPlanDtlItem.endTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shipEnterpId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shipEnterpName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shiperId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shiperName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.shiperPhone}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.postEnterpId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.postEnterpName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.posterId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.posterName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.posterPhone}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.postWarehouseId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.postWarehouseName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.postAddress}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiveEnterpId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiveEnterpName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiverId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiverName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiverPhone}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiveWarehouseId}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiveWarehouseName}</td>								
						<td style="text-align: center">${logisticsPlanDtlItem.receiveAddress}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

