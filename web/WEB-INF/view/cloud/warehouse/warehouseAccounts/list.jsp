<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_warehouse_accounts管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">库存查询管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">批号/编号:</span><input type="text" name="Q_batchnumber_SL"  class="inputText" />
						<span class="label">物品编号:</span><input type="text" name="Q_productcode_SL"  class="inputText" />
						<span class="label">物品名称:</span><input type="text" name="Q_productname_SL"  class="inputText" />
					</div>
					<div class="row">	
						<span class="label">型号规格:</span><input type="text" name="Q_specifications_SL"  class="inputText" />
						<span class="label">计量单位:</span><input type="text" name="Q_units_SL"  class="inputText" />
						<span class="label">货架:</span><input type="text" name="Q_shelves_SL"  class="inputText" />
						<input type="hidden" name="warehouseid"  class="inputText" value="${warehouseid}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="warehouseAccountsList" id="warehouseAccountsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehouseAccountsItem.id}">
				</display:column>
				
				<display:column property="productcode" title="物品编号" sortable="true" sortName="productcode"></display:column>
				<display:column property="productname" title="物品名称" sortable="true" sortName="productname"></display:column>
				<display:column property="specifications" title="物品规格" sortable="true" sortName="specifications"></display:column>
				<display:column property="units" title="单位" sortable="true" sortName="units"></display:column>
				<display:column property="nums" title="数量" sortable="true" sortName="nums"></display:column>
				<display:column property="warehousename" title="仓库名称" sortable="true" sortName="warehousename"></display:column>
				<display:column property="shelves" title="货架" sortable="true" sortName="shelves"></display:column>
				
				<display:column property="batchnumber" title="批号/编号" sortable="true" sortName="batchnumber"></display:column>
				<display:column title="管理" media="html" style="width:100px">
					
					<a href="get.ht?id=${warehouseAccountsItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="warehouseAccountsItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


