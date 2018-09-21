<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品映射管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品映射管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="mappingSettings.ht">映射管理</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">物品编码:</span><input type="text" name="Q_srcProdCode_SL"  class="inputText" />
						<span class="label">物品名称:</span><input type="text" name="Q_srcProdName_SL"  class="inputText" />
						<br/>
						<span class="label">企业名称:</span><input type="text" name="Q_tgtEntName_SL"  class="inputText" />						
						<span class="label">映射物品编码:</span><input type="text" name="Q_tgtProdCode_SL"  class="inputText" />
						<span class="label">映射物品名称:</span><input type="text" name="Q_tgtProdName_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			<!-- 本企业映射表  -->
		    <display:table name="warehouseMaterialMappingList" id="warehouseMaterialMappingItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehouseMaterialMappingItem.id}">
				</display:column>
				<display:column property="srcEntName" title="源企业名称" sortable="true" sortName="SRC_ENT_NAME"></display:column>
				<display:column property="srcProdCode" title="物品编码" sortable="true" sortName="SRC_PROD_CODE"></display:column>
				<display:column property="srcProdName" title="物品名称" sortable="true" sortName="SRC_PROD_NAME"></display:column>
				<display:column property="tgtEntName" title="企业名称" sortable="true" sortName="TGT_ENT_NAME"></display:column>
				<display:column property="tgtProdCode" title="映射物品编码" sortable="true" sortName="TGT_PROD_CODE"></display:column>
				<display:column property="tgtProdName" title="映射物品名称" sortable="true" sortName="TGT_PROD_NAME"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${warehouseMaterialMappingItem.id}" class="link detail">明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="warehouseMaterialMappingItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


