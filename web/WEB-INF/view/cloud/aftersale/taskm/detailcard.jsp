
<%--
	time:2013-04-17 15:21:31
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>维修单明细</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
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
					<!--  
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
					-->
					<div class="group">
						<a class="link back" href="${ctx}/cloud/aftersale/jobcard/listByTask.ht?tmid=${taskM.id}">查看工作单</a>
					</div>
					<div class="group">
						<a class="link close" href="javascript:window.close();">关闭</a>
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
				<th style="width:120px">维修厂商:</th>
				<td>${taskM.purenterp_id}</td>
				<th style="width:120px">状态:</th>
				<td>${taskM.statu}</td>
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
            
		</table>
		</div>
		
	</div>
</body>
</html>

