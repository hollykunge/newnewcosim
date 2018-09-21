<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page import="com.casic.cloud.model.tool.ToolType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>工具管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading" style="background:#76B0EA">
			<div class="panel-title" style="color:#ffffff;font-weight:bold;font-size:15px;">
			工具管理列表
			</div>
		</div>
		<div class="panel-body">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a href="${ctx}/cloud/console/outline.ht?target=/cloud/toolUser/list.ht?userId=<%=ContextUtil.getCurrentUser().getUserId() %>" target="_parent">用户工具映射配置</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">工具名称:</span><input type="text" name="Q_toolName_SL"  class="inputText" value="${param['Q_toolName_SL']}"/>
						<select name="Q_toolType_S" class="select" style="width:8%;" value="${param['Q_toolType_S']}">
							<option value="">所有</option>
							<option value="<%=ToolType.LOCAL.getCode() %>" <c:if test="${param['Q_toolType_S'] == 1 }">selected</c:if>><%=ToolType.LOCAL.getDescription()%></option>
							<option value="<%=ToolType.NETWORK.getCode() %>" <c:if test="${param['Q_toolType_S'] == 2 }">selected</c:if>><%=ToolType.NETWORK.getDescription() %></option>
							<option value="<%=ToolType.CLUSTER.getCode() %>" <c:if test="${param['Q_toolType_S'] == 2 }">selected</c:if>><%=ToolType.CLUSTER.getDescription() %></option>
						</select>						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-footer">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="toolList" id="toolItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table table-bordered table-hover">
				<display:column title="${checkAll}" media="html" style="width:30px;" class="active">
			  		<input type="checkbox" class="pk" name="id" value="${toolItem.id}">
				</display:column>
				<display:column property="toolName" title="工具名称" sortable="true" sortName="toolName" class="active"></display:column>
				<display:column property="toolTypeAsString" title="类型" sortable="true" sortName="toolTypeAsString" class="active"></display:column>
				<display:column property="toolAddress" title="用户默认地址" sortable="true" sortName="toolAddress" maxLength="80" class="active"></display:column>
				
				<display:column property="toolDesc" title="描述" sortable="true" sortName="toolDesc" maxLength="80" class="active"></display:column>
				<display:column  title="更新时间 " sortable="true" sortName="updatetime" class="active">
					<fmt:formatDate value="${toolItem.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>				
				<display:column title="管理" media="html" style="width:180px" class="active">
					<a href="del.ht?id=${toolItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${toolItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${toolItem.id}" class="link detail">明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="toolItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


