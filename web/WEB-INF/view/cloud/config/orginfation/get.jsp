
<%--
	time:2013-04-16 18:08:14
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>sys_org_info明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">sys_org_info详细信息</span>
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
				<th width="20%">EMAIL:</th>
				<td>${OrgInfation.email}</td>
			</tr>
 
			<tr>
				<th width="20%">NAME:</th>
				<td>${OrgInfation.name}</td>
			</tr>
 
			<tr>
				<th width="20%">INDUSTRY:</th>
				<td>${OrgInfation.industry}</td>
			</tr>
 
			<tr>
				<th width="20%">SCALE:</th>
				<td>${OrgInfation.scale}</td>
			</tr>
 
			<tr>
				<th width="20%">ADDRESS:</th>
				<td>${OrgInfation.address}</td>
			</tr>
 
			<tr>
				<th width="20%">POSTCODE:</th>
				<td>${OrgInfation.postcode}</td>
			</tr>
 
			<tr>
				<th width="20%">CONNECTER:</th>
				<td>${OrgInfation.connecter}</td>
			</tr>
 
			<tr>
				<th width="20%">TEL:</th>
				<td>${OrgInfation.tel}</td>
			</tr>
 
			<tr>
				<th width="20%">FAX:</th>
				<td>${OrgInfation.fax}</td>
			</tr>
 
			<tr>
				<th width="20%">HOMEPHONE:</th>
				<td>${OrgInfation.homephone}</td>
			</tr>
 
			<tr>
				<th width="20%">LOGO:</th>
				<td>${OrgInfation.logo}</td>
			</tr>
 
			<tr>
				<th width="20%">INDUSTRY2:</th>
				<td>${OrgInfation.industry2}</td>
			</tr>
 
			<tr>
				<th width="20%">MEMBER:</th>
				<td>${OrgInfation.member}</td>
			</tr>
 
			<tr>
				<th width="20%">INFO:</th>
				<td>${OrgInfation.info}</td>
			</tr>
 
			<tr>
				<th width="20%">COUNTRY:</th>
				<td>${OrgInfation.country}</td>
			</tr>
 
			<tr>
				<th width="20%">PROVINCE:</th>
				<td>${OrgInfation.province}</td>
			</tr>
 
			<tr>
				<th width="20%">CITY:</th>
				<td>${OrgInfation.city}</td>
			</tr>
 
			<tr>
				<th width="20%">IS_PUBLIC:</th>
				<td>${OrgInfation.isPublic}</td>
			</tr>
 
			<tr>
				<th width="20%">CODE:</th>
				<td>${OrgInfation.code}</td>
			</tr>
 
			<tr>
				<th width="20%">TYPE:</th>
				<td>${OrgInfation.type}</td>
			</tr>
 
			<tr>
				<th width="20%">MODEL:</th>
				<td>${OrgInfation.model}</td>
			</tr>
 
			<tr>
				<th width="20%">PRODUCT:</th>
				<td>${OrgInfation.product}</td>
			</tr>
 
			<tr>
				<th width="20%">WEBSITE:</th>
				<td>${OrgInfation.website}</td>
			</tr>
 
			<tr>
				<th width="20%">REGISTERTIME:</th>
				<td>
				<fmt:formatDate value="${OrgInfation.registertime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

