<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>出库信息管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">出库信息管理列表</span>
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
						<span class="label">仓库名称:</span><input type="text" name="Q_warehouseName_SL"  class="inputText" />
						<span class="label">出库单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">出库类型:</span><input type="text" name="Q_outType_SL"  class="inputText" />
					</div>
					<div class="row">
						<span class="label">来源单据号:</span><input type="text" name="Q_inOrderCode_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginmakingDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endmakingDate_DG" class="inputText date" />
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="warehouseOutList" id="warehouseOutItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehouseOutItem.id}">
				</display:column>
				<display:column property="code" title="出库单号" sortable="true" sortName="code" maxLength="80"></display:column>
				<display:column property="outType" title="出库类型" sortable="true" sortName="out_type" maxLength="80"></display:column>
				<display:column property="inOrderCode" title="来源单据号" sortable="true" sortName="in_order_code" maxLength="80"></display:column>
				
				<display:column property="warehouseName" title="仓库名称" sortable="true" sortName="warehouse_name" maxLength="80"></display:column>
				<display:column property="setState" title="状态" sortable="true" sortName="SET_STATE"></display:column>
				<display:column property="makingPeople" title="制单人" sortable="true" sortName="making_people" maxLength="80"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="makingDate">
					<fmt:formatDate value="${warehouseOutItem.makingDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${warehouseOutItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${warehouseOutItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${warehouseOutItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="warehouseOutItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


