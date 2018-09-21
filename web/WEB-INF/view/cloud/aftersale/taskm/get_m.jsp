
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>维修单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">维修单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list_m.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th style="width:120px">任务单号:</th>
				<td colspan="3">${taskM.code}</td>
			</tr>
 
			<tr>
				<th style="width:120px">反馈用户:</th>
				<td>${taskM.feedback_name}</td>
				<th style="width:120px">产品编码:</th>
				<td>${taskM.prodcode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">产品名称:</th>
				<td>${taskM.prodname}</td>
				<th style="width:120px">产品型号:</th>
				<td>${taskM.prodmodel}</td>
			</tr>
 
            <tr>
				<th style="width:120px">维修人员:</th>
				<td colspan="3">${taskM.accendant_name}</td>
			</tr>
			<tr>
				<th style="width:120px">问题描述:</th>
				<td colspan="3">${taskM.descn}</td>
			</tr>
 
			<tr>
				<th style="width:120px">备注:</th>
				<td colspan="3">${taskM.remark}</td>
			</tr>
 
			<tr>
				<th style="width:120px">质检意见:</th>
				<td colspan="3">${taskM.qualityopinion}</td>
			</tr>
 
			<tr>
				<th style="width:120px">售后主管意见:</th>
				<td colspan="3">${taskM.serviceopinion}</td>
			</tr>
 
			<tr>
				<th style="width:120px">状态:</th>
				<td colspan="3">${taskM.statu}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

