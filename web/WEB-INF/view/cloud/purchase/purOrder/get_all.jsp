
<%--
	time:2013-05-06 13:02:00
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>采购订单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
	function loc_Detail(){
		
		var id = $("#p_id").val();
	    //弹出物品信息表
		window.open ('${ctx}/cloud/purchase/purOrder/list_deatails.ht?id='+id,'物品信息','height=400,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
	}
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购订单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list_all.ht?type=direct&suppenterpId=${purOrder.suppenterpId}">返回</a>
						<a class="link print" onclick="window.print();">打印</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">采购订单编号:</th>
				<td>${purOrder.code}
					<input type="hidden" id="p_id" value="${purOrder.id}"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${purOrder.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
		
 
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${purOrder.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${purOrder.sourceformType}</td>
			</tr>
 
			
 
			<tr>
				<th width="20%">来源单据编号:</th>
				<td>${purOrder.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款:</th>
				<td>${purOrder.advancePay}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款说明:</th>
				<td>${purOrder.advancepayNotes}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${purOrder.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${purOrder.currencyType}</td>
			</tr>
            <tr>
				<th width="20%">总金额:</th>
				<td>${purOrder.sumPrice}</td>
			</tr>
		
			<tr>
				<th width="20%">采购企业名称:</th>
				<td>${purOrder.purenterpName}</td>
			</tr>
 
		
			<tr>
				<th width="20%">采购商联系人姓名:</th>
				<td>${purOrder.purenterpUsername}</td>
			</tr>
 
		
			<tr>
				<th width="20%">供应企业名称:</th>
				<td>${purOrder.suppenterpName}</td>
			</tr>
 
 
			<tr>
				<th width="20%">供应商联系人姓名:</th>
				<td>${purOrder.suppenterpUsername}</td>
			</tr>
 
			
 
			<tr>
				<th width="20%">收货仓库名称:</th>
				<td>${purOrder.receivewarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库详细地址:</th>
				<td>${purOrder.receivewarehouseAddress}</td>
			</tr>
      	<!--  
			<tr>
				<th width="20%">receiver_id:</th>
				<td>${purOrder.receiverId}</td>
			</tr>
           	<tr>
				<th width="20%">suppenterp_id:</th>
				<td>${purOrder.suppenterpId}</td>
			</tr>
           	<tr>
				<th width="20%">purenterp_userid:</th>
				<td>${purOrder.purenterpUserid}</td>
			</tr>
          	<tr>
				<th width="20%">purenterp_id:</th>
				<td>${purOrder.purenterpId}</td>
			</tr>
 	          <tr>
				<th width="20%">operator_id:</th>
				<td>${purOrder.operatorId}</td>
			</tr>
			<tr>
				<th width="20%">receivewarehouse_id:</th>
				<td>${purOrder.receivewarehouseId}</td>
			</tr>
			
			<tr>
				<th width="20%">suppenterp_userid:</th>
				<td>${purOrder.suppenterpUserid}</td>
			</tr>
			<tr>
				<th width="20%">sourceform_id:</th>
				<td>${purOrder.sourceformId}</td>
			</tr>
			
		-->
			<tr>
				<th width="20%">收货人姓名:</th>
				<td>${purOrder.receiverName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货人手机号:</th>
				<td>${purOrder.receiverPhone}</td>
			</tr>
 
			<tr>
				<th width="20%">收货人邮箱:</th>
				<td>${purOrder.receiverMail}</td>
			</tr>
 
			<tr>
				<th width="20%">收货地址邮编:</th>
				<td>${purOrder.postcode}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="10">
				<div align="center">
						物品订单详情
			    		</div>
			    		<div align="right">
						<a href="javascript:void(0)" onclick="loc_Detail();">更多</a>
			    		</div>
				</td>
			</tr>
			<tr>
					<th>物品编码</th>
					<th>物品规格</th>
					<th>物品名称</th>
					<th style="display:none">物品分类</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
				<!-- 	<th>delivery_enddate</th>
					<th>materiel_id</th> -->
					<th>发货截止日期</th>
			</tr>	
			<c:forEach items="${purOrderDetailList}" var="purOrderDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${purOrderDetailItem.id}"  class="inputText"/>
						<td style="display:none">${purOrderDetailItem.materielId}</td>								
						<td style="text-align: center">${purOrderDetailItem.materielCode}</td>								
						<td style="text-align: center">${purOrderDetailItem.model}</td>
						<td style="text-align: center">${purOrderDetailItem.materielName}</td>								
						<td style="display:none">${purOrderDetailItem.materielLev}</td>								
						<td style="text-align: center">${purOrderDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${purOrderDetailItem.unit}</td>								
						<td style="text-align: center">${purOrderDetailItem.orderNum}</td>								
						<td style="text-align: center">${purOrderDetailItem.price}</td>								
						<td style="text-align: center">${purOrderDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

