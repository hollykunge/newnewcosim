<%@page import="com.hotent.core.util.ContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程文件管理</title>
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
	
	function showToUpload(){
		var winArgs="dialogWidth=800px;dialogHeight=600px;help=1;status=1;scroll=1;center=1;resizable:1";				
		var url='${ctx}/cloud/taskfile/upload.ht?taskId='+${param.taskId};
		url=url.getNewUrl();
		var win=window.open(url,"",winArgs);
	    $("#searchForm").submit();
	}	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程文件管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link upload"  action="upload.ht">上传</a></div>
				</div>	
			</div>
			<div class="panel-search">
					<form id="searchForm" method="post" action="list.ht">
						<input type="hidden" name="taskId" value="${param.taskId}"/>
						<div class="row">
							<span class="label">文件名:</span><input type="text" name="Q_fileName_SL"  class="inputText" style="width:13%" value="${param['Q_fileName_SL']}" />												
							<span class="label">上传者:</span><input type="text" name="Q_creator_SL"  class="inputText" style="width:13%" value="${param['Q_creator_SL']}"/>
							<span class="label">扩展名:</span><input type="text" name="Q_ext_SL"  class="inputText"  style="width:13%" value="${param['Q_ext_SL']}"/>									
							<span class="label">创建时间 从:</span> <input type="text"  name="Q_begincreatetime_DL"  class="inputText date" value="${param['Q_begincreatetime_DL']}"/>
							<span class="label">至: </span><input type="text" name="Q_endcreatetime_DG" class="inputText date" value="${param['Q_endcreatetime_DG']}"/>
						</div>
					</form>
			</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
				<%
					Long userId = ContextUtil.getCurrentUserId();
					request.setAttribute("userId", userId);
				%>
			    <display:table name="taskFileList" id="taskFileItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"   class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="fileId" value="${taskFileItem.fileId}">
					</display:column>
					<display:column media="html" title="文件名" sortable="true" sortName="fileName">
						<a href="javascript:;" class="file_name" path="${taskFileItem.filePath}">${taskFileItem.fileName}</a>
					</display:column>
					<display:column title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${taskFileItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column property="ext" title="扩展名" sortName="ext" sortable="true"></display:column>							
				    <display:column property="note" title="大小" sortable="true" sortName="note" maxLength="80"></display:column>
					
					<display:column property="creatorName" title="上传者"  ></display:column>
					
					
					<display:column title="管理" media="html" style="width:180px;text-align:center">
						<a href="del.ht?fileId=${taskFileItem.fileId}&taskId=${param.taskId}" class="link del">删除</a>
						<a href="download.ht?fileId=${taskFileItem.fileId}" class="link download">下载</a>
						<c:if test="${userId==taskFileItem.creatorId}">
						<a href="taskfileNode.ht?taskfileId=${taskFileItem.fileId}&taskId=${param.taskId}" class="link detail">权限</a>
						</c:if>
					</display:column>
				</display:table>
				<hotent:paging tableId="taskFileItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


