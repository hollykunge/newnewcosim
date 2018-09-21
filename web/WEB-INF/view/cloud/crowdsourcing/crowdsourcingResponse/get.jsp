
<%--
	time:2013-07-09 17:13:50
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>需求响应表明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">需求响应表详细信息</span>
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
				<td>${crowdsourcingResponse.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制表人ID:</th>
				<td>${crowdsourcingResponse.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">制表人名称:</th>
				<td>${crowdsourcingResponse.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">制表企业ID:</th>
				<td>${crowdsourcingResponse.operaterEnterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">制表企业名称:</th>
				<td>${crowdsourcingResponse.operaterEnterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">制表日期:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingResponse.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">发布状态:</th>
				<td>${crowdsourcingResponse.publishStatus}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表ID:</th>
				<td>${crowdsourcingResponse.sourceformCrowdsourcingId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源众包需求表CODE:</th>
				<td>${crowdsourcingResponse.sourceformCrowdsourcingCode}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品ID:</th>
				<td>${crowdsourcingResponse.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品CODE:</th>
				<td>${crowdsourcingResponse.materialCode}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品名称:</th>
				<td>${crowdsourcingResponse.materialName}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品分类:</th>
				<td>${crowdsourcingResponse.materialType}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品BOM层级:</th>
				<td>${crowdsourcingResponse.materialBomLevel}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${crowdsourcingResponse.currency}</td>
			</tr>
 
			<tr>
				<th width="20%">报价:</th>
				<td>${crowdsourcingResponse.quote}</td>
			</tr>
 
			<tr>
				<th width="20%">预计完成时间:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingResponse.forecastCompleteTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">需求响应表 :需求响应附件详情</td>
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
			<c:forEach items="${crowdsourcingResponseDetailList}" var="crowdsourcingResponseDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${crowdsourcingResponseDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${crowdsourcingResponseDetailItem.attachmentName}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.attachmentType}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.attachmentInfo}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.uploadId}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.uploadName}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.attachmentManageId}</td>								
						<td style="text-align: center">${crowdsourcingResponseDetailItem.operatingAuthority}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

