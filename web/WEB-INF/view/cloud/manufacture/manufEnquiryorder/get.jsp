
<%--
	time:2013-04-19 11:18:08
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>委托加工订单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委托加工订单详细信息</span>
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
				<td>${manufEnquiryorder.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${manufEnquiryorder.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${manufEnquiryorder.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${manufEnquiryorder.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">受托加工企业ID:</th>
				<td>${manufEnquiryorder.selectedenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">受托加工企业名:</th>
				<td>${manufEnquiryorder.selectedenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">受托加工联系人ID:</th>
				<td>${manufEnquiryorder.selectedenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">受托加工联系人姓名:</th>
				<td>${manufEnquiryorder.selectedenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${manufEnquiryorder.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">运输方式:</th>
				<td>${manufEnquiryorder.transportWay}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${manufEnquiryorder.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${manufEnquiryorder.sumPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款:</th>
				<td>${manufEnquiryorder.advancePay}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款说明:</th>
				<td>${manufEnquiryorder.advancepayNote}</td>
			</tr>
 
			<tr>
				<th width="20%">是否带料:</th>
				<td>${manufEnquiryorder.isBringmaterial}</td>
			</tr>
 
			<tr>
				<th width="20%">加工类型:</th>
				<td>${manufEnquiryorder.manufacturingType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${manufEnquiryorder.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${manufEnquiryorder.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据编码:</th>
				<td>${manufEnquiryorder.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业ID:</th>
				<td>${manufEnquiryorder.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业名称:</th>
				<td>${manufEnquiryorder.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人ID:</th>
				<td>${manufEnquiryorder.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人名:</th>
				<td>${manufEnquiryorder.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">是否汇报加工进度:</th>
				<td>${manufEnquiryorder.isReport}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库ID:</th>
				<td>${manufEnquiryorder.receivewarehouseId}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库名:</th>
				<td>${manufEnquiryorder.receivewarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">收货仓库地址:</th>
				<td>${manufEnquiryorder.receivewarehouseAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">收货联系人ID:</th>
				<td>${manufEnquiryorder.receivewarehouseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">收货联系人名:</th>
				<td>${manufEnquiryorder.receivewarehouseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${manufEnquiryorder.comments}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="14" style="text-align: center">委托加工订单 :委托加工订单产品详情</td>
			</tr>
			<tr>
				<th>物品编码</th>
				<th>编码规则</th>
				<th>物品名称</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>委托加工数量</th>
				<th>要求交货日期</th>
				<th>工业要求（多选）</th>
				<th>工艺附件</th>
				<th>产品BOM附件</th>
				<th>计划开工时间</th>
				<th>计划完工时间</th>
				<th>计划完工入库率下限</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufEnquiryorderDetailList}" var="manufEnquiryorderDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufEnquiryorderDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialCode}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialCodenotation}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialName}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialSpec}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialUnit}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.materialQuantity}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryorderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.crafts}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.craftAttachment}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.bom}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryorderDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryorderDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.planInrate}</td>								
						<td style="text-align: center">${manufEnquiryorderDetailItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="8" style="text-align: center">委托加工订单 :委托加工订单物料详情</td>
			</tr>
			<tr>
				<th>物品编码</th>
				<th>编码规则</th>
				<th>物品名称</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>计划下达日期</th>
				<th>计划到货日期</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufEnquiryorderSuppliesList}" var="manufEnquiryorderSuppliesItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufEnquiryorderSuppliesItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.suppliesCode}</td>								
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.suppliesCodenotation}</td>								
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.suppliesName}</td>								
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.suppliesSpec}</td>								
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.suppliesUnit}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planDeliverydate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planAogdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufEnquiryorderSuppliesItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

