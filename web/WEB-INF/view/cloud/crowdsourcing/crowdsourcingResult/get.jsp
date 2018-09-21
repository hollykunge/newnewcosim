
<%--
	time:2013-07-11 12:50:28
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_crowdsourcing_result明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_crowdsourcing_result详细信息</span>
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
				<th width="20%">表单CODE:</th>
				<td>${crowdsourcingResult.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingResult.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">提交人ID:</th>
				<td>${crowdsourcingResult.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">提交人名称:</th>
				<td>${crowdsourcingResult.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">提交人企业ID:</th>
				<td>${crowdsourcingResult.operaterEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">提交人企业名称:</th>
				<td>${crowdsourcingResult.operaterEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表ID:</th>
				<td>${crowdsourcingResult.sourceformCrowdsourcingId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表CODE:</th>
				<td>${crowdsourcingResult.sourceformCrowdsourcingCode}</td>
			</tr>
 
			<tr>
				<th width="20%">来源合同ID:</th>
				<td>${crowdsourcingResult.sourceformAgreementId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源合同CODE:</th>
				<td>${crowdsourcingResult.sourceformAgreementCode}</td>
			</tr>
 
			<tr>
				<th width="20%">状态:</th>
				<td>${crowdsourcingResult.state}</td>
			</tr>
 
			<tr>
				<th width="20%">审核类型:</th>
				<td>${crowdsourcingResult.auditType}</td>
			</tr>
 
			<tr>
				<th width="20%">审核意见:</th>
				<td>${crowdsourcingResult.auditOpinion}</td>
			</tr>
 
			<tr>
				<th width="20%">结果附件IDs:</th>
				<td>${crowdsourcingResult.resultAttachmentIds}</td>
			</tr>
 
			<tr>
				<th width="20%">结果包名称:</th>
				<td>${crowdsourcingResult.resultName}</td>
			</tr>
 
			<tr>
				<th width="20%">结果包说明:</th>
				<td>${crowdsourcingResult.resultInfo}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">cloud_crowdsourcing_result_detail :cloud_crowdsourcing_result_detail</td>
			</tr>
			<tr>
				<th>结果包名称</th>
				<th>结果包说明</th>
				<th>审核类型</th>
				<th>审核意见</th>
				<th>结果附件IDs</th>
				<th>提交时间</th>
				<th>审核时间</th>
			</tr>	
			<c:forEach items="${crowdsourcingResultDetailList}" var="crowdsourcingResultDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${crowdsourcingResultDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${crowdsourcingResultDetailItem.resultName}</td>								
						<td style="text-align: center">${crowdsourcingResultDetailItem.resultInfo}</td>								
						<td style="text-align: center">${crowdsourcingResultDetailItem.auditType}</td>								
						<td style="text-align: center">${crowdsourcingResultDetailItem.auditOpinion}</td>								
						<td style="text-align: center">${crowdsourcingResultDetailItem.resultAttachmentIds}</td>								
						<td style="text-align: center"><fmt:formatDate value='${crowdsourcingResultDetailItem.submitTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${crowdsourcingResultDetailItem.auditTime}' pattern='yyyy-MM-dd'/></td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

