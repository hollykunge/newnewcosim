
<%--
	time:2013-05-16 15:35:33
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>CLOUD_LOGISTICS_INFO明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_LOGISTICS_INFO详细信息</span>
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
				<th width="20%">单证号:</th>
				<td>${logisticsInfo.code}</td>
			</tr>
 
			<tr>
				<th width="20%">生成日期:</th>
				<td>
				<fmt:formatDate value="${logisticsInfo.createDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${logisticsInfo.sourceId}</td>
			</tr>
 
			<tr>
				<th width="20%">物流动作时间:</th>
				<td>
				<fmt:formatDate value="${logisticsInfo.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">物流动作类型:</th>
				<td>${logisticsInfo.operateType}</td>
			</tr>
 
			<tr>
				<th width="20%">物流动态信息:</th>
				<td>${logisticsInfo.operateInfo}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

