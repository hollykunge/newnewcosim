<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_capability_property管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">能力属性分类管理列表</span>
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
						<span class="label">属性名称:</span><input type="text" name="Q_propertyName_SL"  class="inputText" />
						<span class="label">属性类别:</span><input type="text" name="Q_propertyType_SL"  class="inputText" />
						<span class="label">属性值:</span><input type="text" name="Q_value_SL"  class="inputText" />
						<span class="label">能力分类:</span><input type="text" name="Q_capabilityClassName_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="capabilityPropertyList" id="capabilityPropertyItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${capabilityPropertyItem.id}">
				</display:column>
				<display:column property="propertySequence" title="序号" sortable="true" sortName="propertySequence"></display:column>
				<display:column property="propertyName" title="属性名称" sortable="true" sortName="propertyName"></display:column>
				 
				<display:column property="value" title="属性值" sortable="true" sortName="value"></display:column>
				<display:column property="capabilityClassName" title="能力分类" sortable="true" sortName="capabilityClassName"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${capabilityPropertyItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${capabilityPropertyItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${capabilityPropertyItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="capabilityPropertyItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


