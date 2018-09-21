<%@page import="com.hotent.core.util.ContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程文件节点关系管理</title>
<style type="text/css">
.file_name{
	text-decoration:none;
	color:black;
}
</style>
<%@include file="/commons/include/get.jsp" %>
<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ImageQtip.js" ></script>
<script type="text/javascript">
	$(function() {
		$("a.file_name").each(function() {
			var path = $(this).attr("path");			
			//图片类型
			if (/\w+.(png|gif|jpg)/gi.test(path)) {
				ImageQtip.drawImg(this,"${ctx}/"+path,{maxWidth:265});
			}
		});
		$("a.link.upload").click(function(){
			showToUpload();
		});
	});
	
	function updateRights(){
		
	    $("#rightForm").submit();
	    alert("授权完成");
	}	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程文件节点关系列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link update"  onclick="updateRights()">更新权限</a></div>
				</div>	
			</div>
			<div class="panel-detail">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">						
							<tr>
								<th width="20%">文件名:</th>
								<td>${taskFile.fileName}</td>
								<th width="20%">创建时间:</th>
								<td>
									<fmt:formatDate value="${taskFile.createtime }" pattern="yyyy-MM-dd HH:ss"/>
								</td>
							</tr>
							<tr>
								<th width="20%">扩展名:</th>
								<td>${taskFile.ext}</td>
								<th width="20%">说明:</th>
								<td>${taskFile.note}</td>
							</tr>
							<tr>
								<th width="20%">上传者:</th>
								<td>${taskFile.creator}</td>
								<th width="20%">字节数:</th>
								<td>${taskFile.totalBytes}</td>
							</tr>
							<tr>
						</table>												
			</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>下载权限
				</c:set>
				<form id="rightForm" action="${ctx }/cloud/taskfile/updateRights.ht" method="post">
				<input type="hidden" name="taskfileId" value="${taskFile.fileId}"/>
			    <display:table name="fileNodeRights" id="rightItem" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column property="node.nodeId" title="节点Id"></display:column>				
					<display:column property="node.nodeName" title="节点名称"></display:column>							
					<display:column title="${checkAll}" media="html" style="width:180px;text-align:center;">
						<c:choose>
						  	<c:when test="${rightItem.hasRight && currentSetId==rightItem.setId}">
						  		<input type="hidden" class="pk" name="setId" value="${rightItem.setId}">文件所在节点不能更改下载权限
						  	</c:when>
						  	<c:when test="${rightItem.hasRight}">
						  		<input type="checkbox" class="pk" name="setId" value="${rightItem.setId}" checked="checked">
						  	</c:when>
						  	<c:otherwise>
						  		<input type="checkbox" class="pk" name="setId" value="${rightItem.setId}">
						  	</c:otherwise>
					  	</c:choose>					  	
					</display:column>
				</display:table>
			</form>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


