
<%--
	time:2013-04-19 11:18:08
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>委外加工报价单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工报价单详细信息</span>
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
				<th width="20%">编码:</th>
				<td>${manufQuote.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${manufQuote.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${manufQuote.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${manufQuote.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">企业ID:</th>
				<td>${manufQuote.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">企业:</th>
				<td>${manufQuote.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">企业联系人ID:</th>
				<td>${manufQuote.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">企业联系人:</th>
				<td>${manufQuote.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">询价截止时间:</th>
				<td>
				<fmt:formatDate value="${manufQuote.offerEnddate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${manufQuote.releaseDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布模式:</th>
				<td>${manufQuote.releaseModel}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${manufQuote.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">运输方式:</th>
				<td>${manufQuote.transportWay}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${manufQuote.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${manufQuote.sumPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款:</th>
				<td>${manufQuote.advancePay}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款说明:</th>
				<td>${manufQuote.advancepayNote}</td>
			</tr>
 
			<tr>
				<th width="20%">是否带料:</th>
				<td>${manufQuote.isBringmaterial}</td>
			</tr>
 
			<tr>
				<th width="20%">加工类型:</th>
				<td>${manufQuote.manufacturingType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${manufQuote.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${manufQuote.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据CODE:</th>
				<td>${manufQuote.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业ID:</th>
				<td>${manufQuote.enquiryenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业:</th>
				<td>${manufQuote.enquiryenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人ID:</th>
				<td>${manufQuote.enquiryenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">委托企业联系人:</th>
				<td>${manufQuote.enquiryenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">是否汇报加工进度:</th>
				<td>${manufQuote.isReport}</td>
			</tr>
 
			<tr>
				<th width="20%">备注（256）:</th>
				<td>${manufQuote.comments}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="19" style="text-align: center">委外加工报价单 :委外加工报价单详情</td>
			</tr>
			<tr>
				<th>代加工物品编码</th>
				<th>编码规则</th>
				<th>代加工物品名称</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>委托加工数量</th>
				<th>单位加工费用</th>
				<th>加工费总金额</th>
				<th>要求交货日期</th>
				<th>预计交货日期</th>
				<th>工艺要求</th>
				<th>工艺附件</th>
				<th>BOM附件</th>
				<th>计划开工时间</th>
				<th>计划完工时间</th>
				<th>计划入库率下限</th>
				<th>预计开工时间</th>
				<th>预计完工时间</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufQuoteDetailList}" var="manufQuoteDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufQuoteDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufQuoteDetailItem.materialCode}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.materialCodenotation}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.materialName}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.materialAttribute}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.materialUnit}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.materialNumber}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.price}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufQuoteDetailItem.crafts}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.craftAttachment}</td>								
						<td style="text-align: center">${manufQuoteDetailItem.bom}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufQuoteDetailItem.planInrate}</td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.preStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${manufQuoteDetailItem.preEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${manufQuoteDetailItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

