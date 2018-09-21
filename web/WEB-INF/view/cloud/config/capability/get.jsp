
<%--
	time:2013-04-16 17:23:24
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_capability明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">能力详细信息</span>
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
				<th width="20%">能力分类:</th>
				<td>${capability.typeName}</td>
			</tr>
			<tr>
				<th width="20%">能力名称:</th>
				<td>${capability.name}</td>
			</tr>
 
			<tr>
				<th width="20%">能力分类:</th>
				<td>${capability.typeName}</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">所属企业:</th>
				<td>${capability.entName}</td>
			</tr>
			<tr>
				<th width="20%">能力属性:</th>
				<td>
					<c:forEach items="${propertyValues}" var="c1">
						${c1.propertyName }:  ${c1.propertyValue }
					</c:forEach>
				</td>
			</tr>
			 
 
 
			<tr>
				<th width="20%">能力详情:</th>
				<td>${capability.info}</td>
			</tr>
 
			<tr>
				<th width="20%">能力图片:</th>
				<td><img src="${ctx}${capability.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" />  </td>
			</tr>
 
			<tr>
				<th width="20%">发布人:</th>
				<td>${capability.publisher}</td>
			</tr>
 
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${capability.publishDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">适用范围:</th>
				<td>${capability.useScope}</td>
			</tr>
 
			<tr>
				<th width="20%">发布状态:</th>
				<td>${capability.publishState}</td>
			</tr>
 
			<tr>
				<th width="20%">关键词:</th>
				<td>${capability.prokey}</td>
			</tr>
			
 			
		 
		</table>
		<div class="tbar-title">
				<span class="tbar-label">物品信息</span>
			</div>
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
				<th>
					名称
				</th>
				 
				<th>
					描述
				</th>
				<th>
					单位
				</th>
				<th>
					价格
				</th>
				</tr>
				
				<c:forEach items="${materiallist}" var="c2">
				<tr>
					<td>
						<a href="${ctx}/cloud/config/material/get.ht?id=${c2.id }">${c2.name}</a>
					</td>
					 
					<td>
						${c2.info }
					</td>
					<td>
						${c2.unit }
					</td>
					<td>
						${c2.price }
					</td>
				</tr>

			</c:forEach>
				
				</table>
		</div>
		
	</div>
</body>
</html>

