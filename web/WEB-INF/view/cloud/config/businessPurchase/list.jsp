<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购商机管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购商机管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					 	 
				 
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">商机名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">开始时间:</span> <input  name="Q_beginstartTime_DL"  class="inputText date" />
						<span class="label">结束时间:</span> <input  name="Q_beginendTime_DL"  class="inputText date" />
						<span class="label">发布时间 :</span> <input  name="Q_beginoperateTime_DL"  class="inputText date" />
				</form>
					 
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="businessPurchaseList" id="businessPurchaseItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${businessPurchaseItem.id}">
				</display:column>
				<display:column property="classid" title="采购物品分类" sortable="true" sortName="classid" maxLength="80"></display:column>
				<display:column property="name" title="商机名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column  title="开始时间" sortable="true" sortName="start_time">
					<fmt:formatDate value="${businessPurchaseItem.startTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="结束时间" sortable="true" sortName="end_time">
					<fmt:formatDate value="${businessPurchaseItem.endTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				 
				 
				 
				<display:column  title="发布时间" sortable="true" sortName="operate_time">
					<fmt:formatDate value="${businessPurchaseItem.operateTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="companyName" title="发布企业" sortable="true" sortName="company_name" maxLength="80"></display:column>
				 
				<display:column property="publishState" title="审核状态" sortable="true" sortName="publish_state"></display:column>
				 
				<display:column title="管理" media="html" style="width:200px">
					<a href="del.ht?id=${businessPurchaseItem.id}" class="link del">删除</a>
				 
					<a href="get.ht?id=${businessPurchaseItem.id}" class="link detail">明细</a>
					<a href="change.ht?id=${businessPurchaseItem.id}" class="link edit">审核</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="businessPurchaseItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


