
<%--
	time:2013-04-19 11:18:08
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>委外加工优选单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工优选单详细信息</span>
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
				<td>${manufOptimize.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${manufOptimize.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人姓名:</th>
				<td>${manufOptimize.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${manufOptimize.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">中标企业ID:</th>
				<td>${manufOptimize.selectedenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">中标企业:</th>
				<td>${manufOptimize.selectedenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">中标企业联系人ID:</th>
				<td>${manufOptimize.selectedenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">中标企业联系人:</th>
				<td>${manufOptimize.selectedenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">中标委外报价单ID:</th>
				<td>${manufOptimize.quoteformId}</td>
			</tr>
 
			<tr>
				<th width="20%">中标委外报价单CODE:</th>
				<td>${manufOptimize.quoteformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">结果说明:</th>
				<td>${manufOptimize.resultNote}</td>
			</tr>
 
			<tr>
				<th width="20%">运费承担方:</th>
				<td>${manufOptimize.freightBearer}</td>
			</tr>
 
			<tr>
				<th width="20%">运输方式:</th>
				<td>${manufOptimize.transportWay}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${manufOptimize.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${manufOptimize.sumPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款:</th>
				<td>${manufOptimize.advancePay}</td>
			</tr>
 
			<tr>
				<th width="20%">预付款说明:</th>
				<td>${manufOptimize.advancepayNote}</td>
			</tr>
 
			<tr>
				<th width="20%">是否带料:</th>
				<td>${manufOptimize.isBringmaterial}</td>
			</tr>
 
			<tr>
				<th width="20%">加工类型:</th>
				<td>${manufOptimize.manufacturingType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${manufOptimize.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${manufOptimize.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据CODE:</th>
				<td>${manufOptimize.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">询价发布日期:</th>
				<td>
				<fmt:formatDate value="${manufOptimize.releaseDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">询价企业ID:</th>
				<td>${manufOptimize.enquiryenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">询价企业名称:</th>
				<td>${manufOptimize.enquiryenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">询价企业联系人ID:</th>
				<td>${manufOptimize.enquiryenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">询价企业联系人:</th>
				<td>${manufOptimize.enquiryenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">是否汇报加工进度:</th>
				<td>${manufOptimize.isReport}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${manufOptimize.comments}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="5" style="text-align: center">委外加工优选单 :委外加工优选单详情</td>
			</tr>
			<tr>
				<th>报价企业ID</th>
				<th>报价企业名</th>
				<th>报价单ID</th>
				<th>报价单CODE</th>
				<th>备注</th>
			</tr>	
			<c:forEach items="${manufOptimizeDetailList}" var="manufOptimizeDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${manufOptimizeDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${manufOptimizeDetailItem.quoteenterpId}</td>								
						<td style="text-align: center">${manufOptimizeDetailItem.quoteenterpName}</td>								
						<td style="text-align: center">${manufOptimizeDetailItem.quoteformId}</td>								
						<td style="text-align: center">${manufOptimizeDetailItem.quoteformCode}</td>								
						<td style="text-align: center">${manufOptimizeDetailItem.comments}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

