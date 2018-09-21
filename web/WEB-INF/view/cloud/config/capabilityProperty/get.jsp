
<%--
	time:2013-04-16 16:10:48
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_capability_property明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">能力属性分类详细信息</span>
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
				<th width="20%">序号:</th>
				<td>${capabilityProperty.propertySequence}</td>
			</tr>
 
			<tr>
				<th width="20%">属性名称:</th>
				<td>${capabilityProperty.propertyName}</td>
			</tr>
 
			<tr>
				<th width="20%">属性类别:</th>
				<td>
				
				
					<c:if test="${capabilityProperty.propertyType==1}">
						文本框
					 </c:if>
					 <c:if test="${capabilityProperty.propertyType==2}">
						下拉选
					 </c:if>
				</td>
			</tr>
 
			<tr>
				<th width="20%">属性值:</th>
				<td>${capabilityProperty.value}</td>
			</tr>
 
			<tr>
				<th width="20%">能力分类:</th>
				<td>${capabilityProperty.capabilityClassName}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

