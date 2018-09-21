
<%--
	time:2013-04-19 11:18:08
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>受托加工订单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">受托加工订单详细信息</span>
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
				<td>${manufOrder.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${manufOrder.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${manufOrder.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${manufOrder.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业ID:</th>
				<td>${manufOrder.enquiryenterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业:</th>
				<td>${manufOrder.enquiryenterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人ID:</th>
				<td>${manufOrder.enquiryenterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人:</th>
				<td>${manufOrder.enquiryenterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${manufOrder.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">运输方式:</th>
				<td>${manufOrder.transportWay}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${manufOrder.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${manufOrder.sumPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款:</th>
				<td>${manufOrder.advancePay}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款说明:</th>
				<td>${manufOrder.advancepayNote}</td>
			</tr>
 
			<tr>
				<th width="20%">是否带料:</th>
				<td>${manufOrder.isBringmaterial}</td>
			</tr>
 
			<tr>
				<th width="20%">加工类型:</th>
				<td>${manufOrder.manufacturingType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${manufOrder.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${manufOrder.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据编码:</th>
				<td>${manufOrder.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">受托企业ID:</th>
				<td>${manufOrder.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">受托企业名称:</th>
				<td>${manufOrder.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">受托企业联系人ID:</th>
				<td>${manufOrder.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">受托企业联系人名:</th>
				<td>${manufOrder.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">是否汇报加工进度:</th>
				<td>${manufOrder.isReport}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库ID:</th>
				<td>${manufOrder.receivewarehouseId}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库名:</th>
				<td>${manufOrder.receivewarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库地址:</th>
				<td>${manufOrder.receivewarehouseAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">收货联系人ID:</th>
				<td>${manufOrder.receivewarehouseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">收货联系人名:</th>
				<td>${manufOrder.receivewarehouseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">发货仓库ID:</th>
				<td>${manufOrder.deliverwarehouseId}</td>
			</tr>
 
			<tr>
				<th width="20%">发货仓库:</th>
				<td>${manufOrder.deliverwarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">发货仓库地址:</th>
				<td>${manufOrder.deliverwarehouseAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">发货联系人ID:</th>
				<td>${manufOrder.deliverwarehouseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">发货联系人:</th>
				<td>${manufOrder.deliverwarehouseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${manufOrder.comments}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="19" style="text-align: center">受托加工订单 :受托加工订单详情</td>
			</tr>
			<tr>
				<th>代加工物品编码</th>
				<th>编码说明</th>
				<th>代加工物品</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>委托加工数量</th>
				<th>单位加工费</th>
				<th>加工费总金额</th>
				<th>要求交货日期</th>
				<th>预计交货日期</th>
				<th>工艺要求</th>
				<th>工艺附件</th>
				<th>BOM附件</th>
				<th>计划开工时间</th>
				<th>计划完成时间</th>
				<th>计划完工入库率下限</th>
				<th>预计开工时间</th>
				<th>预计完工时间</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufOrderDetailList}" var="manufOrderDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufOrderDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufOrderDetailItem.materialCode}</td>								
						<td style="text-align: center">${manufOrderDetailItem.materialCodenotation}</td>								
						<td style="text-align: center">${manufOrderDetailItem.materialName}</td>								
						<td style="text-align: center">${manufOrderDetailItem.materialSpec}</td>								
						<td style="text-align: center">${manufOrderDetailItem.materialUnit}</td>								
						<td style="text-align: center">${manufOrderDetailItem.materialNumber}</td>								
						<td style="text-align: center">${manufOrderDetailItem.price}</td>								
						<td style="text-align: center">${manufOrderDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufOrderDetailItem.crafts}</td>								
						<td style="text-align: center">${manufOrderDetailItem.craftAttachment}</td>								
						<td style="text-align: center">${manufOrderDetailItem.bom}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufOrderDetailItem.planInrate}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.preStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufOrderDetailItem.preEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufOrderDetailItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

