
<%--
	time:2013-05-06 16:34:55
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>sys_org_info_aptitude明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">sys_org_info_aptitude详细信息</span>
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
				<th width="20%">INFO_ID:</th>
				<td>${aptitude.infoId}</td>
			</tr>
 
			<tr>
				<th width="20%">CATE_TYPE:</th>
				<td>${aptitude.cateType}</td>
			</tr>
 
			<tr>
				<th width="20%">CATE_ORG:</th>
				<td>${aptitude.cateOrg}</td>
			</tr>
 
			<tr>
				<th width="20%">INURE_DATE:</th>
				<td>
				<fmt:formatDate value="${aptitude.inureDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">END_DATE:</th>
				<td>${aptitude.endDate}</td>
			</tr>
 
			<tr>
				<th width="20%">CATE_PIC:</th>
				<td>${aptitude.catePic}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

