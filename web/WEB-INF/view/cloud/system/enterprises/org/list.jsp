<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>组织架构管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">组织架构管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">企业名称:</span><input type="text" name="Q_orgName_SL"  class="inputText" />
						<span class="label">建立时间 从:</span> <input  name="Q_begincreatetime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText date" />
						<span class="label">sn:</span><input type="text" name="Q_sn_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="sysOrgList" id="sysOrgItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="orgId" value="${sysOrgItem.orgId}">
				</display:column>
				<display:column property="demId" title="维度编号" sortable="true" sortName="demId"></display:column>
				<display:column property="orgName" title="名称" sortable="true" sortName="orgName" maxLength="80"></display:column>
				<display:column property="orgDesc" title="描述" sortable="true" sortName="orgDesc" maxLength="80"></display:column>
				<display:column property="orgSupId" title="上级" sortable="true" sortName="orgSupId"></display:column>
				<display:column property="path" title="路径" sortable="true" sortName="path" maxLength="80"></display:column>
				<display:column property="depth" title="层次" sortable="true" sortName="depth"></display:column>
				<display:column property="orgType" title="类型ID" sortable="true" sortName="orgType"></display:column>
				<display:column property="creatorId" title="建立人" sortable="true" sortName="creatorId"></display:column>
				<display:column  title="建立时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${sysOrgItem.createtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="updateId" title="修改人" sortable="true" sortName="updateId"></display:column>
				<display:column  title="修改时间" sortable="true" sortName="updatetime">
					<fmt:formatDate value="${sysOrgItem.updatetime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="sn" title="sn" sortable="true" sortName="sn"></display:column>
				<display:column property="fromType" title="数据来源" sortable="true" sortName="fromType"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="${ctx}/cloud/system/enterprises/org/edit.ht?orgId=${sysOrgItem.orgId}" class="link edit">编辑</a>
					<a href="${ctx}/cloud/system/enterprises/org/get.ht?orgId=${sysOrgItem.orgId}" class="link detail">明细</a>					
				</display:column>
			</display:table>
			<hotent:paging tableId="sysOrgItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


