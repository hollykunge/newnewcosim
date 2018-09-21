<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>工具列表</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	$(function(){
		$("#toolItem").find("tr").bind('click', function() {
			var ch=$(this).find(":checkbox[name='userData']");
			window.parent.selectMulti(ch);
		});
	});
</script>
</head>
<body style="overflow-x: hidden;overflow-y: auto;">
	<div class="panel-search" style="width: 80%;">
		<form id="searchForm" method="post" action="${ctx}/cloud/tool/toolList.ht" >
			<div class="row">
				<span class="label">工具名称:</span><input size="14" type="text" name="Q_toolName_SL"  class="inputText" value="${param['Q_toolName_SL']}"/>
				&nbsp;<input type="submit" value="查询" onclick="window.parent.setCenterTitle('全部工具')"/>
			</div>
		</form>
	</div>
   	<c:set var="checkAll">
		<input onclick="window.parent.selectAll(this);" type="checkbox" />
	</c:set>
	<display:table  name="toolList" id="toolItem" requestURI="toolList.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
		<display:column title="${checkAll}" media="html" style="width:30px;">
			<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="userData" value="${toolItem.id}#${toolItem.toolName}#${toolItem.toolType}#${toolItem.toolTypeAsString}#${toolItem.toolAddress}">
		</display:column>
		<display:column  property="toolName" title="工具名称" sortable="true" sortName="toolName"></display:column>
		<display:column property="toolTypeAsString" title="工具类型" sortable="true" sortName="toolType"></display:column>
		<display:column property="toolAddress" title="用户缺省地址" sortable="true" sortName="toolAddress" maxLength="40"></display:column>
	</display:table>
	<hotent:paging tableId="toolItem" showExplain="false"/>
</body>
</html>