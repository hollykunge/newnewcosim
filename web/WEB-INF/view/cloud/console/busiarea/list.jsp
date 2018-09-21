<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>CLOUD_BUSINESS_AREA管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_BUSINESS_AREA管理列表</span>
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
						<span class="label">role_id:</span><input type="text" name="Q_roleId_SL"  class="inputText" />
						<span class="label">main_ent:</span><input type="text" name="Q_mainEnt_SL"  class="inputText" />
						<span class="label">corp_ent:</span><input type="text" name="Q_corpEnt_SL"  class="inputText" />
						<span class="label">state:</span><input type="text" name="Q_state_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="busiareaList" id="busiareaItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${busiareaItem.id}">
				</display:column>
				<display:column property="role_id" title="role_id" sortable="true" sortName="role_id"></display:column>
				<display:column property="main_ent" title="main_ent" sortable="true" sortName="main_ent"></display:column>
				<display:column property="corp_ent" title="corp_ent" sortable="true" sortName="corp_ent"></display:column>
				<display:column property="state" title="state" sortable="true" sortName="state"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${busiareaItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${busiareaItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${busiareaItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="busiareaItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


