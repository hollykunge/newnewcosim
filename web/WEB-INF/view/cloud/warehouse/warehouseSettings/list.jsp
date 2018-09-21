<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>初始化设置管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>

<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">初始化设置管理列表</span>
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
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnCsh" action="dep.ht">初始化</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">编码:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单人:</span><input type="text" name="Q_operator_SL"  class="inputText" />
						<span class="label">日期 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="warehouseSettingsList" id="warehouseSettingsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehouseSettingsItem.id}">
				</display:column>
				<display:column property="code" title="编码" sortable="true" sortName="CODE"></display:column>
				<display:column property="operator" title="制单人" sortable="true" sortName="OPERATOR"></display:column>
				
				<display:column  title="日期" sortable="true" sortName="OPERATEDATE">
					<fmt:formatDate value="${warehouseSettingsItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="setState" title="初始化状态" sortable="true" sortName="SET_STATE"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${warehouseSettingsItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${warehouseSettingsItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${warehouseSettingsItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="warehouseSettingsItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


