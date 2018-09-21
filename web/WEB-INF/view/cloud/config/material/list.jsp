<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				 
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link dep" id="btnUpd" action="remove.ht">取消发布</a></div>
					<div class="l-bar-separator"></div>
				 
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">物品名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">所属分类:</span><input type="text" name="Q_catalogName_SL"  class="inputText" />
					 
						<span class="label">发布时间 :</span> <input  name="Q_beginpublishdate_DL"  class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="materialList" id="materialItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${materialItem.id}">
				</display:column>
				<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="model" title="物品规格" sortable="true" sortName="model" maxLength="80"></display:column>
				<display:column property="unit" title="单位" sortable="true" sortName="unit"></display:column>
				<display:column property="price" title="价格" sortable="true" sortName="price"></display:column>
				<display:column property="code" title="物品代码" sortable="true" sortName="code"></display:column>
				<display:column  title="发布时间" sortable="true" sortName="publishdate">
					<fmt:formatDate value="${materialItem.publishdate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="publishState" title="发布状态" sortable="true" sortName="publish_state"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="remove.ht?id=${materialItem.id}" class="link dep">取消发布</a>
					<a href="get.ht?id=${materialItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="materialItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


