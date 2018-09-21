<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品管理</title>
<%@include file="/commons/include/get.jsp" %>
<script>
function openHistory(code){
	window.open('${ctx}/cloud/purchase/purOrder/getDetailHistory.ht?materielCode=' + code + '&purenterpId=${companyId}','物品采购历史','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
}
</script>
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
				<!--<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>-->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list_material.ht">
					<div class="row">
						<span class="label">物品名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">物品编码:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">发布时间 从:</span> <input  name="Q_beginpublishdate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endpublishdate_DG" class="inputText date" />
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="MaterialList" id="MaterialItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${MaterialItem.id}">
				</display:column>
				<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="code" title="物品编码" sortable="true" sortName="code"></display:column>
				<display:column property="price" title="价格" sortable="true" sortName="price"></display:column>
				<display:column  title="发布日期" sortable="true" sortName="publishdate">
					<fmt:formatDate value="${MaterialItem.publishdate}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column property="entName" title="发布企业" sortable="true" sortName="ent_name"></display:column>
				<display:column title="管理" media="html" style="width:180px">					
					<a href="javascript:openHistory('${MaterialItem.code}');" class="link detail">物品采购历史</a>
					<!-- 
						<a href="reportMaterialByMonthWithJson.ht?id=${MaterialItem.id}" class="link detail">物品统计</a>
					 -->
					
				</display:column>
			</display:table>
			<hotent:paging tableId="MaterialItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


