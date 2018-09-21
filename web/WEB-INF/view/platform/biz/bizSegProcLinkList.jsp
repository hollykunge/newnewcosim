<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>BIZ_SEG_PROC_LINK管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">BIZ_SEG_PROC_LINK管理列表</span>
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
						<span class="label">业务环节ID:</span><input type="text" name="Q_bizDefSegmentId_SL"  class="inputText" />
						<span class="label">企业ID（即组织）:</span><input type="text" name="Q_orgId_SL"  class="inputText" />
						<span class="label">流程定义Key:</span><input type="text" name="Q_actDefKey_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bizSegProcLinkList" id="bizSegProcLinkItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="bizSegProLinkId" value="${bizSegProcLinkItem.bizSegProLinkId}">
				</display:column>
				<display:column property="bizDefSegmentId" title="业务环节ID" sortable="true" sortName="bizDefSegmentId"></display:column>
				<display:column property="orgId" title="企业ID（即组织）" sortable="true" sortName="orgId"></display:column>
				<display:column property="actDefKey" title="流程定义Key" sortable="true" sortName="actDefKey" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?bizSegProLinkId=${bizSegProcLinkItem.bizSegProLinkId}" class="link del">删除</a>
					<a href="edit.ht?bizSegProLinkId=${bizSegProcLinkItem.bizSegProLinkId}" class="link edit">编辑</a>
					<a href="get.ht?bizSegProLinkId=${bizSegProcLinkItem.bizSegProLinkId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="bizSegProcLinkItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


