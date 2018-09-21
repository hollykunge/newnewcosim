
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>维修工作单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">维修工作单详细信息</span>
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
			<!-- 
			<tr>
				<th style="width:120px">维修任务ID:</th>
				<td colspan="3">${jobCard.taskid}</td>
			</tr>
  			-->
  			
			<tr>
				<th style="width:120px">产品编码:</th>
				<td>${jobCard.prodcode}</td>
				<th style="width:120px">产品名称:</th>
				<td>${jobCard.prodname}</td>
			</tr>
 
			<tr>
				<th style="width:120px">产品型号:</th>
				<td>${jobCard.prodmodel}</td>
				<th style="width:120px">检修措施:</th>
				<td>${jobCard.measure}</td>
			</tr>
			<tr>
					<th style="width:120px">制单时间:</th>
					<td colspan="3"><fmt:formatDate value="${jobCard.operatedate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th style="width:120px">问题描述:</th>
				<td colspan="3">${jobCard.descn}</td>
			</tr>
 
			<tr>
				<th style="width:120px">故障分类:</th>
				<td colspan="3">${jobCard.type}</td>
			</tr>
 
			<tr>
				<th style="width:120px">故障原因:</th>
				<td colspan="3">${jobCard.reason}</td>
			</tr>
 
			<tr>
				<th style="width:120px">工作内容:</th>
				<td colspan="3">${jobCard.content}</td>
			</tr>
 
			<tr>
				<th style="width:120px">维修人员:</th>
				<td>${jobCard.man}</td>
				<th style="width:120px">维修工时:</th>
				<td>${jobCard.manhours}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="5" style="text-align: center">维修工作单明细</td>
			</tr>
			<tr>
				<!-- 
				<th>维修任务ID</th>
				 -->
				<th>序号</th>
				<th>更換配件编号</th>
				<th>更換配件名称</th>				
				<th>更換数量</th>
			</tr>	
			<c:forEach items="${jobCardDetailList}" var="jobCardDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${jobCardDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${jobCardDetailItem.seq}</td>
						<td style="text-align: center">${jobCardDetailItem.prodcode}</td>									
						<td style="text-align: center">${jobCardDetailItem.prodname}</td>								
						<td style="text-align: center">${jobCardDetailItem.nums}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

