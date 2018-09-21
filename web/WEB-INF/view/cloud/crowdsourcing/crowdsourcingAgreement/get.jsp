
<%--
	time:2013-07-09 16:59:12
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>研发众包合同明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">研发众包合同详细信息</span>
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
				<th width="20%">表单code:</th>
				<td>${crowdsourcingAgreement.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${crowdsourcingAgreement.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人名称:</th>
				<td>${crowdsourcingAgreement.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单企业ID:</th>
				<td>${crowdsourcingAgreement.operaterEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单企业名称:</th>
				<td>${crowdsourcingAgreement.operaterEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingAgreement.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布状态:</th>
				<td>${crowdsourcingAgreement.publishStatus}</td>
			</tr>
 
			<tr>
				<th width="20%">接包方签署状态:</th>
				<td>${crowdsourcingAgreement.signedState}</td>
			</tr>
 
			<tr>
				<th width="20%">签署意见说明:</th>
				<td>${crowdsourcingAgreement.signedOpinion}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表ID:</th>
				<td>${crowdsourcingAgreement.sourceformCrowdsourcingId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表CODE:</th>
				<td>${crowdsourcingAgreement.sourceformCrowdsourcingCode}</td>
			</tr>
 
			<tr>
				<th width="20%">接包方组织ID:</th>
				<td>${crowdsourcingAgreement.receiveOrgId}</td>
			</tr>
 
			<tr>
				<th width="20%">接包方组织名称:</th>
				<td>${crowdsourcingAgreement.receiveOrgName}</td>
			</tr>
 
			<tr>
				<th width="20%">接包人ID:</th>
				<td>${crowdsourcingAgreement.receiverId}</td>
			</tr>
 
			<tr>
				<th width="20%">接包人名称:</th>
				<td>${crowdsourcingAgreement.receiverName}</td>
			</tr>
 
			<tr>
				<th width="20%">协议价格:</th>
				<td>${crowdsourcingAgreement.agreePrice}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${crowdsourcingAgreement.currency}</td>
			</tr>
 
			<tr>
				<th width="20%">研发任务完成时间:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingAgreement.completeTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品ID:</th>
				<td>${crowdsourcingAgreement.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品名称:</th>
				<td>${crowdsourcingAgreement.materialName}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品CODE:</th>
				<td>${crowdsourcingAgreement.materialCode}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品分类:</th>
				<td>${crowdsourcingAgreement.materialType}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品bom层级:</th>
				<td>${crowdsourcingAgreement.materialBomLevel}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">研发众包合同 :研发众包合同附件详情</td>
			</tr>
			<tr>
				<th>附件名</th>
				<th>附件分类</th>
				<th>附件说明</th>
				<th>上传者ID</th>
				<th>上传者姓名</th>
				<th>附件管理ID</th>
				<th>操作权限</th>
			</tr>	
			<c:forEach items="${crowdsourcingAgreementDetailList}" var="crowdsourcingAgreementDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${crowdsourcingAgreementDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.attachmentName}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.attachmentType}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.attachmentInfo}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.uploadId}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.uploadName}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.attachmentManageId}</td>								
						<td style="text-align: center">${crowdsourcingAgreementDetailItem.operatingAuthority}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

