
<%--
	time:2013-05-21 16:04:50
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档审签明细</title>
<%@include file="/commons/include/getById.jsp"%>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript">
	//放置脚本
	
	var fileType="doc";
	var fileUrl;
	function _fileCallback(fileId,fileName,filePath) {
		fileType = filePath.slice(filePath.lastIndexOf(".")+1);
		fileUrl = filePath;
		$('.l-dialog-table input[name="fileType"]').val(fileType);
		$('.l-dialog-table input[name="managementId"]').val(fileId);
		$('.l-dialog-table input[name="fileName"]').val(fileName);
		if(fileType=="doc"){
		$('.l-dialog-table input[name="fileUrl"]').val("<a href=# onclick=getFileInfo("+fileId+")>获取详情</a>&nbsp;<a href=# onclick=openFile("+fileId+")>打开</a>&nbsp;<a href=# onclick=downloadFile("+fileId+")>下载</a>&nbsp;");
		}
		else {
		$('.l-dialog-table input[name="fileUrl"]').val("<a href=# onclick=getFileInfo("+fileId+")>获取详情</a>&nbsp;<a href=# onclick=openModel("+fileId+")>打开</a>&nbsp;<a href=# onclick=downloadFile("+fileId+")>下载</a>&nbsp;");
		}
	}

	
	function openModel(fileId){
		url = "${ctx}/cloud/research/avx.ht";
		window.open(url);
	}
	function openFile(fileId){

		url = "${ctx}/cloud/research/office.ht?fileId="+fileId;
		window.open(url);
	}
	function downloadFile(fileId){
		var url = "${ctx}/platform/system/sysFile/download.ht?fileId="+fileId;
		window.open(url);
	}
	function getFileInfo(fileId){
		var url = "${ctx}/platform/system/sysFile/get.ht?fileId="+fileId;
		window.open(url);
	}
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">文档审签详细信息</span>
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
				<th width="20%">文档编码:</th>
				<td>${resFilecheck.code}</td>
			</tr>
 
			 
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${resFilecheck.operatorName}</td>
			</tr>
			
 			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${resFilecheck.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			 
 
			<tr>
				<th width="20%">企业:</th>
				<td>${resFilecheck.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">文档审签状态:</th>
				<td>${resFilecheck.status}</td>
			</tr>
 
		 
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="9" style="text-align: center">文档审签 :文档审签_文档列表</td>
			</tr>
			<tr>
				<th>文件名</th>
				<th>文件路径</th>
				<th>文件描述</th>
				<th>文件类型</th>
			 
				<th>上传人</th>
				<th>上传时间</th>
				<th>文件版本</th>
				 
			</tr>	
			<c:forEach items="${resFilecheckDetailList}" var="resFilecheckDetailItem" varStatus="status">
				<tr>
						<td style="text-align: center">${resFilecheckDetailItem.fileName}</td>								
						<input type="hidden" id="id" name="id" value="${resFilecheckDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${resFilecheckDetailItem.fileUrl}</td>								
						<td style="text-align: center">${resFilecheckDetailItem.fileComment}</td>								
						<td style="text-align: center">${resFilecheckDetailItem.fileType}</td>								
					 							
						<td style="text-align: center">${resFilecheckDetailItem.uploadUsername}</td>								
						<td style="text-align: center"><fmt:formatDate value='${resFilecheckDetailItem.uploadTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${resFilecheckDetailItem.fileVersion}</td>								
						 							
				</tr>
			</c:forEach>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="10" style="text-align: center">文档审签 :文档审签_审签意见</td>
			</tr>
			<tr>
				<!-- <th>任务id</th> -->
				<th>任务名</th>
				 
				<th>审签人</th>
			 
				<th>审签企业</th>
				<th>审签意见</th>

			</tr>	
			<c:forEach items="${resFilecheckOpinionList}" var="resFilecheckOpinionItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${resFilecheckOpinionItem.id}"  class="inputText"/>
					<!-- 	<td style="text-align: center">${resFilecheckOpinionItem.taskId}</td> -->								
						<td style="text-align: center">${resFilecheckOpinionItem.taskName}</td>								
						 						
						<td style="text-align: center">${resFilecheckOpinionItem.checkUsernames}</td>								
					 						
						<td style="text-align: center">${resFilecheckOpinionItem.checkEnterprisenames}</td>								
						<td style="text-align: center">${resFilecheckOpinionItem.checkOpinionids}</td>															
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

