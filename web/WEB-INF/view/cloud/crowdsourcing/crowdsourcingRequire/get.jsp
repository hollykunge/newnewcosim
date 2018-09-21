
<%--
	time:2013-07-09 17:06:02
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>研发众包需求明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">研发众包需求详细信息</span>
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
				<th width="20%">需求编号:</th>
				<td>${crowdsourcingRequire.code}</td>
			</tr>
 
			<tr>
				<th width="20%">需求名称:</th>
				<td>${crowdsourcingRequire.requireName}</td>
			</tr>
 
			<tr>
				<th width="20%">需求分类:</th>
				<td>${crowdsourcingRequire.requireType}</td>
			</tr>
 
			<tr>
				<th width="20%">需求详细描述:</th>
				<td>${crowdsourcingRequire.requireDescription}</td>
			</tr>
 
			<tr>
				<th width="20%">状态:</th>
				<td>${crowdsourcingRequire.status}</td>
			</tr>
 
			<tr>
				<th width="20%">发布模式:</th>
				<td>${crowdsourcingRequire.publishMode}</td>
			</tr>
 
			<tr>
				<th width="20%">创建时间:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingRequire.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">创建人ID:</th>
				<td>${crowdsourcingRequire.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">创建人姓名:</th>
				<td>${crowdsourcingRequire.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">所属企业ID:</th>
				<td>${crowdsourcingRequire.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">所属企业名称:</th>
				<td>${crowdsourcingRequire.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">流程ID:</th>
				<td>${crowdsourcingRequire.runId}</td>
			</tr>
 
			<tr>
				<th width="20%">保证金:</th>
				<td>${crowdsourcingRequire.bail}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${crowdsourcingRequire.currency}</td>
			</tr>
 
			<tr>
				<th width="20%">入围审核要求列表:</th>
				<td>${crowdsourcingRequire.finalists}</td>
			</tr>
 
			<tr>
				<th width="20%">公告截止日期:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingRequire.announcementDeadline}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">要求完成时间:</th>
				<td>
				<fmt:formatDate value="${crowdsourcingRequire.requiredCompleteTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">附件详情（多个）:</th>
				<td>${crowdsourcingRequire.attachmentDetail}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品ID:</th>
				<td>${crowdsourcingRequire.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品CODE:</th>
				<td>${crowdsourcingRequire.materialCode}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品名称:</th>
				<td>${crowdsourcingRequire.materialName}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品类型:</th>
				<td>${crowdsourcingRequire.materialType}</td>
			</tr>
 
			<tr>
				<th width="20%">研发物品BOM层级:</th>
				<td>${crowdsourcingRequire.materialBomLevel}</td>
			</tr>
 
			<tr>
				<th width="20%">受邀研发组织ID:</th>
				<td>${crowdsourcingRequire.invitedOrgId}</td>
			</tr>
 
			<tr>
				<th width="20%">受邀研发组织名称:</th>
				<td>${crowdsourcingRequire.invitedOrgName}</td>
			</tr>
 
			<tr>
				<th width="20%">受邀研发人员IDs:</th>
				<td>${crowdsourcingRequire.invitedUserIds}</td>
			</tr>
 
			<tr>
				<th width="20%">受邀所有研发人员姓名:</th>
				<td>${crowdsourcingRequire.invitedUserNames}</td>
			</tr>
 
			<tr>
				<th width="20%">受邀研发组IDs:</th>
				<td>${crowdsourcingRequire.invitedGroupIds}</td>
			</tr>
 
			<tr>
				<th width="20%">共享文件夹路径:</th>
				<td>${crowdsourcingRequire.sharedPath}</td>
			</tr>
 
			<tr>
				<th width="20%">研发资源路径:</th>
				<td>${crowdsourcingRequire.resourcePath}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">研发众包需求 :研发众包需求附件详情</td>
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
			<c:forEach items="${crowdsourcingRequireDetailList}" var="crowdsourcingRequireDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${crowdsourcingRequireDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${crowdsourcingRequireDetailItem.attachmentName}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.attachmentType}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.attachmentInfo}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.uploadId}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.uploadName}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.attachmentManageId}</td>								
						<td style="text-align: center">${crowdsourcingRequireDetailItem.operatingAuthority}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

