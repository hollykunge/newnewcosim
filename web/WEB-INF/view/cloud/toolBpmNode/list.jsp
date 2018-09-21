<%@page import="com.casic.cloud.model.tool.ToolType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户角色映射表管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx }/js/cloud/toolUserDialog.js"></script>
<script type="text/javascript">
	function dlgCallBack(toolIds, toolNames) {
		if (toolIds.length > 0) {
			var form = new com.hotent.form.Form();
			form.creatForm("form", "${ctx}/cloud/toolBpmNode/add.ht");
			form.addFormEl("setId", "${setId}");
			form.addFormEl("toolIds", toolIds);
 	 		form.submit();
		}
	};

	function add() {
		ToolUserDialog({
			callback : dlgCallBack
		});
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程工具映射表管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link add" href="javascript:add();">加入工具</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link del" action="del.ht">删除</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" href="javascript:window.close()">关闭</a></div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht?setId=${setId}">
						<div class="row">
						<span class="label">工具名称:</span>
						<input type="text" name="Q_toolName_SL"  class="inputText" value="${param['Q_toolName_SL']}"/>
						<c:set var="LOCAL"><%=ToolType.LOCAL.getCode() %></c:set>
						<c:set var="NETWORK"><%=ToolType.NETWORK.getCode() %></c:set>
						<c:set var="CLUSTER"><%=ToolType.CLUSTER.getCode() %></c:set>
						<select name="Q_toolType_S" class="select" style="width:8%;" value="${param['Q_toolType_S']}">
							<option value="">所有</option>
							<option value="<%=ToolType.LOCAL.getCode() %>" <c:if test="${param['Q_toolType_S'] == LOCAL }">selected</c:if>><%=ToolType.LOCAL.getDescription()%></option>
							<option value="<%=ToolType.NETWORK.getCode() %>" <c:if test="${param['Q_toolType_S'] == NETWORK }">selected</c:if>><%=ToolType.NETWORK.getDescription() %></option>
							<option value="<%=ToolType.CLUSTER.getCode() %>" <c:if test="${param['Q_toolType_S'] == CLUSTER }">selected</c:if>><%=ToolType.CLUSTER.getDescription() %></option>
						</select>				
						</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="toolBpmNodeList" id="toolBpmNodeItem"
				requestURI="list.ht" sort="external" cellpadding="1"
				cellspacing="1" export="false" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="toolNodeId" value="${toolBpmNodeItem.toolNodeId}">
				</display:column>

				<display:column property="toolName" title="工具名称" sortable="true" sortName="toolName"></display:column>
				<display:column property="toolTypeAsString" title="工具类型" sortable="true" sortName="toolType"></display:column>
				<display:column property="toolAddress" title="地址" sortable="true" sortName="toolAddress" maxLength="40"></display:column>
				<display:column property="myToolAddress" title="我的地址" sortable="true" sortName="myToolAddress" maxLength="40"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?toolNodeId=${toolBpmNodeItem.toolNodeId}" class="link del">删除</a>
					<c:if test="${toolBpmNodeItem.toolType == LOCAL }">
					<!-- 
					<a href="edit.ht?toolNodeId=${toolBpmNodeItem.toolNodeId}" class="link edit">编辑</a>
					 -->
					
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="toolBpmNodeItem"/>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


