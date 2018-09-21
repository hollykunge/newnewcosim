
<%--
	time:2013-06-19 12:09:32
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>现场技术服务明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">现场技术服务详细信息</span>
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
				<th width="20%">客户反映时间:</th>
				<td>
				<fmt:formatDate value="${maitainTask.adate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th width="20%">预计维修时间:</th>
				<td>
				 ${maitainTask.ptate} 
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
 
			
 
			<tr style="display:none">
				<th width="20%">修维厂商ID:</th>
				<td>${maitainTask.purenterpId}</td>
			</tr>
 
		 
 
			<tr style="display:none">
				<th width="20%">维修人员ID:</th>
				<td>${maitainTask.accendantId}</td>
			</tr>
 
			<tr>
				<th width="20%">维修人员名称:</th>
				<td>${maitainTask.accendantName}</td>
			</tr>
 
		 
 
			<tr style="display:none">
				<th width="20%">质检意见:</th>
				<td>${maitainTask.qualityopinion}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">售后主管意见:</th>
				<td>${maitainTask.serviceopinion}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">服务厂商已见:</th>
				<td>${maitainTask.serviceenopinion}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">主机厂意见:</th>
				<td>${maitainTask.enterpriseopinion}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">状态:</th>
				<td>${maitainTask.statu}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">业企Id:</th>
				<td>${maitainTask.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">企业名称:</th>
				<td>${maitainTask.enterpriseName}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">run_id:</th>
				<td>${maitainTask.runId}</td>
			</tr>
 
			<tr style="display:none">
				<th width="20%">tasktype:</th>
				<td>${maitainTask.tasktype}</td>
			</tr>
 
			<tr>
				<th width="20%">是否过保:</th>
				<td>${maitainTask.isEnsure}</td>
			</tr>
			<tr>
				<th width="20%">问题描述:</th>
				<td>${maitainTask.descn}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${maitainTask.remark}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="12" style="text-align: center">售后服务领料单</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>产品规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>维修项目</th>
				<th>领料人</th>
			</tr>	
			<c:forEach items="${maitainTaskDetailList}" var="maitainTaskDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${maitainTaskDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${maitainTaskDetailItem.seq}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.prodcode}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.prodname}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.specifications}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.units}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.nums}</td>
						<td style="text-align: center">${maitainTaskDetailItem.price}</td>
						<td style="text-align: center">${maitainTaskDetailItem.sumprice}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.taskProject}</td>								
						<td style="text-align: center">${maitainTaskDetailItem.getper}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

