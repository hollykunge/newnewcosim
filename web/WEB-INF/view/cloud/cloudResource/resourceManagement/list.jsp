<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_resource_class管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_resource_class管理列表</span>
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
						<span class="label">资源类名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">父类ID:</span><input type="text" name="Q_parentId_SL"  class="inputText" />
						<span class="label">所属层级:</span><input type="text" name="Q_levels_SL"  class="inputText" />
						<span class="label">打开方式:</span><input type="text" name="Q_opentype_SL"  class="inputText" />
						<span class="label">打开链接地址:</span><input type="text" name="Q_openurl_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="cloudResourceList" id="cloudResourceItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${cloudResourceItem.id}">
				</display:column>
				<display:column property="id" title="资源ID" sortable="true" sortName="id" maxLength="80"></display:column>
				<display:column property="name" title="资源类名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="parentId" title="父类ID" sortable="true" sortName="parentId"></display:column>
				<display:column property="levels" title="所属层级" sortable="true" sortName="levels"></display:column>
				<display:column property="openType" title="打开方式" sortable="true" sortName="openType"></display:column>
				<display:column property="openUrl" title="打开链接地址" sortable="true" sortName="openUrl" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${cloudResourceItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${cloudResourceItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${cloudResourceItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="cloudResourceItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


