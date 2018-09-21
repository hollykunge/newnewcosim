
<%--
	time:2013-07-21 15:48:29
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_resource_class明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_resource_class详细信息</span>
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
				<th width="20%">资源类名称:</th>
				<td>${cloudResource.name}</td>
			</tr>
 
			<tr>
				<th width="20%">父类ID:</th>
				<td>${cloudResource.parentId}</td>
			</tr>
 
			<tr>
				<th width="20%">所属层级:</th>
				<td>${cloudResource.levels}</td>
			</tr>
 
			<tr>
				<th width="20%">打开方式:</th>
				<td>${cloudResource.opentype}</td>
			</tr>
 
			<tr>
				<th width="20%">打开链接地址:</th>
				<td>${cloudResource.openurl}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="10" style="text-align: center">cloudResourceInstance :cloud_resource_instance</td>
			</tr>
			<tr>
				<th>资源名称</th>
				<th>资源信息</th>
				<th>图片地址</th>
				<th>链接1地址</th>
				<th>链接1名称</th>
				<th>链接2地址</th>
				<th>链接2名称</th>
				<th>blank1</th>
				<th>blank2</th>
				<th>mode</th>
			</tr>	
			<c:forEach items="${cloudResourceInstanceList}" var="cloudResourceInstanceListItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${cloudResourceInstanceListItem.id}"  class="inputText"/>
						<td style="text-align: center">${cloudResourceInstanceListItem.title}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.info}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.urlPic}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.url1}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.url1Name}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.url2}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.url2Name}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.blank1}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.blank2}</td>								
						<td style="text-align: center">${cloudResourceInstanceListItem.mode}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

