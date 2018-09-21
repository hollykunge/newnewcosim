
<%--
	time:2013-04-11 11:48:44
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>业务定义，如邀标采购这样的大业务。明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务定义，如邀标采购这样的大业务。详细信息</span>
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
				<th width="20%">业务编号:</th>
				<td>${businessDefinition.bizDefNo}</td>
			</tr>
 
			<tr>
				<th width="20%">业务名称:</th>
				<td>${businessDefinition.defName}</td>
			</tr>
 
			<tr>
				<th width="20%">描述:</th>
				<td>${businessDefinition.defDescription}</td>
			</tr>
 
			<tr>
				<th width="20%">版本号:</th>
				<td>${businessDefinition.versionNo}</td>
			</tr>
 
			<tr>
				<th width="20%">主版本:</th>
				<td>${businessDefinition.isMain}</td>
			</tr>
 
			<tr>
				<th width="20%">状态::</th>
				<td>${businessDefinition.status}</td>
			</tr>
 
			<tr>
				<th width="20%">创建人:</th>
				<td>${businessDefinition.createBy}</td>
			</tr>
 
			<tr>
				<th width="20%">创建日期:</th>
				<td>
				<fmt:formatDate value="${businessDefinition.createTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">更新人:</th>
				<td>${businessDefinition.updateBy}</td>
			</tr>
 
			<tr>
				<th width="20%">更新日期:</th>
				<td>
				<fmt:formatDate value="${businessDefinition.updateTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">BIZ_DEF_SEGMENT :业务环节</td>
			</tr>
			<tr>
				<th>业务环节编号</th>
				<th>业务环节名称</th>
				<th>描述</th>
				<th>默认流程定义Key</th>
				<th>状态</th>
				<th>序号</th>
			</tr>	
			<c:forEach items="${BizDefSegmentList}" var="BizDefSegmentItem" varStatus="status">
				<tr>
						<input type="hidden" id="bizDefSegmentId" name="bizDefSegmentId" value="${BizDefSegmentItem.bizDefSegmentId}"  class="inputText"/>
						<td style="text-align: center">${BizDefSegmentItem.bizDefSegmentNo}</td>								
						<td style="text-align: center">${BizDefSegmentItem.segmentName}</td>								
						<td style="text-align: center">${BizDefSegmentItem.segmentDescription}</td>								
						<td style="text-align: center">${BizDefSegmentItem.actDefKey}</td>								
						<td style="text-align: center">${BizDefSegmentItem.status}</td>								
						<td style="text-align: center">${BizDefSegmentItem.sortOrder}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

