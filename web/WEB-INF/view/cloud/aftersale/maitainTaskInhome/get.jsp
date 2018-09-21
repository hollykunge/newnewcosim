
<%--
	time:2013-06-19 12:09:32
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>上门维修服务明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">上门维修服务详细信息</span>
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
				<th width="20%">任务单号:</th>
				<td>${maitainTask.code}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">务任来源:</th>
				<td>${maitainTask.source}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">来源ID:</th>
				<td>${maitainTask.sourceid}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">用户反馈ID:</th>
				<td>${maitainTask.feedbackId}</td>
			</tr>
 
			<tr>
				<th width="20%">反馈用户:</th>
				<td>${maitainTask.feedbackName}</td>
			</tr>
 
			<tr>
				<th width="20%">维修日期:</th>
				<td>
				<fmt:formatDate value="${maitainTask.tdate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			 
 
			<tr>
				<th width="20%">产品编码:</th>
				<td>${maitainTask.prodcode}</td>
			</tr>
 
			<tr>
				<th width="20%">产品名称:</th>
				<td>${maitainTask.prodname}</td>
			</tr>
 
			<tr>
				<th width="20%">产品型号:</th>
				<td>${maitainTask.prodmodel}</td>
			</tr>
 
			<tr>
				<th width="20%">问题描述:</th>
				<td>${maitainTask.descn}</td>
			</tr>
 
			 
 
			 
 
		 
  
 
			 
 
			 
 
			 
 
			 
 
			 
			<tr>
				<th width="20%">企业名称:</th>
				<td>${maitainTask.enterpriseName}</td>
			</tr>
 
			 
 
			 
 
			 
			<tr>
				<th width="20%">备注:</th>
				<td>${maitainTask.remark}</td>
			</tr>
		</table>
		 
		</div>
		
	</div>
</body>
</html>

