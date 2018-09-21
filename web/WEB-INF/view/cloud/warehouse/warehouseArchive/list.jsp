<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>仓库档案信息管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">仓库档案信息管理列表</span>
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
						<span class="label">仓库名称:</span><input type="text" name="Q_warehousename_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
					</div>
					<div class="row">	
						<span class="label">联系人名称:</span><input type="text" name="Q_contactname_SL"  class="inputText" />
						<span class="label">联系人手机:</span><input type="text" name="Q_contactphone_SL"  class="inputText" />
						<span class="label">联系人邮箱:</span><input type="text" name="Q_contactemail_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="warehouseArchiveList" id="warehouseArchiveItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${warehouseArchiveItem.id}">
				</display:column>
				
				<display:column property="warehousename" title="仓库名称" sortable="true" sortName="warehousename"></display:column>
				<display:column property="address" title="仓库地址" sortable="true" sortName="address"></display:column>
				<display:column property="contactname" title="联系人" sortable="true" sortName="contactname"></display:column>
				<display:column property="contactphone" title="手机" sortable="true" sortName="contactphone"></display:column>
				<display:column property="operatename" title="制单人" sortable="true" sortName="operatename"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operatedate">
					<fmt:formatDate value="${warehouseArchiveItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${warehouseArchiveItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${warehouseArchiveItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${warehouseArchiveItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="warehouseArchiveItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


