<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_resource_approval管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_resource_approval管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">企业ID:</span><input type="text" name="Q_enpId_SL"  class="inputText" />
						<span class="label">人员ID:</span><input type="text" name="Q_userId_SL"  class="inputText" />
						<span class="label">资源ID:</span><input type="text" name="Q_resId_SL"  class="inputText" />
						<span class="label">资源名:</span><input type="text" name="Q_resName_SL"  class="inputText" />
						<span class="label">申请日期 从:</span> <input  name="Q_beginapplyDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endapplyDate_DG" class="inputText date" />
						<span class="label">审批日期 从:</span> <input  name="Q_beginapprovalData_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endapprovalData_DG" class="inputText date" />
						<span class="label">状态:</span><input type="text" name="Q_state_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="approvalResourceList" id="approvalResourceItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${approvalResourceItem.id}">
				</display:column>
				<display:column property="enpId" title="企业ID" sortable="true" sortName="enp_id"></display:column>
				<display:column property="userId" title="人员ID" sortable="true" sortName="user_id"></display:column>
				<display:column property="resId" title="资源ID" sortable="true" sortName="res_id"></display:column>
				<display:column property="resName" title="资源名" sortable="true" sortName="res_name"></display:column>
				<display:column  title="申请日期" sortable="true" sortName="apply_date">
					<fmt:formatDate value="${approvalResourceItem.applyDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="审批日期" sortable="true" sortName="approval_data">
					<fmt:formatDate value="${approvalResourceItem.approvalData}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="state" title="状态" sortable="true" sortName="state"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${approvalResourceItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${approvalResourceItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${approvalResourceItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="approvalResourceItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


