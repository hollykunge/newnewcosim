
<%--
	time:2013-06-07 17:51:21
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>资源明细</title>
<%@include file="/commons/include/getById.jsp"%>

				 <!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css"/>
	
<script type="text/javascript">
$(function() {
		var editor = new baidu.editor.ui.Editor({
			minFrameHeight : 200,
			initialContent : ''
		});
		editor.render("txtHtml");
	 
		});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">资源详细信息</span>
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
				<th width="20%">资源名称:</th>
				<td>${cloudSrc.name}</td>
			</tr>
 
			<tr>
				<th width="20%">资源图片:</th>
					<td><img src="${ctx}${cloudSrc.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="178" height="167" />  </td>
			 
			</tr>
 
			<tr>
				<th width="20%">链接地址:</th>
				<td>${cloudSrc.path}</td>
			</tr>
 
			<tr>
				<th width="20%">资源详情:</th>
					<td>
					
					  <div class="postbox"   id="editor" position="center"   style="overflow:auto; width: 650px;">
										 
											<textarea id="txtHtml" name="html">${fn:escapeXml(cloudSrc.info)}</textarea>													
									 </div>
					
					</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

