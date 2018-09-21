<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>盘库信息管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">盘库信息管理列表</span>
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
					<div class="group"><a class="link update" id="btnUpd" action="dep.ht">生效</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">盘库编号:</span><input type="text" name="Q_plateStorgeCode_SL"  class="inputText" />
						<span class="label">仓库名称:</span><input type="text" name="Q_warehouseName_SL"  class="inputText" />
						<span class="label">编制日期 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="warehousePlateList" id="warehousePlateItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehousePlateItem.id}">
				</display:column>
				<display:column property="plateStorgeCode" title="盘库编号" sortable="true" sortName="plate_storge_code"></display:column>
				<display:column property="operator" title="编制人" sortable="true" sortName="operator"></display:column>
				<display:column  title="编制日期" sortable="true" sortName="operatedate">
					<fmt:formatDate value="${warehousePlateItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="warehouseId" title="仓库ID" sortable="true" sortName="warehouse_id"></display:column>
				<display:column property="warehouseName" title="仓库名称" sortable="true" sortName="warehouse_name"></display:column>
				<display:column property="pubState" title="盘库状态" sortable="true" sortName="pub_state"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${warehousePlateItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${warehousePlateItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${warehousePlateItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="warehousePlateItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


