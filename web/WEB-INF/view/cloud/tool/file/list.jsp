<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>工具输入输出管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">工具输入输出管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>">添加</a></div>

					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<input type="hidden" name="cloudToolUserId"  class="inputText" value="<%=request.getAttribute("cloudToolUserId")%>"/>
						<span class="label">输入文件地址:</span><input type="text" name="Q_inputaddress_SL"  class="inputText" />
						<span class="label">输出文件地址:</span><input type="text" name="Q_outputaddress_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="cloud_tool_user_fileList" id="cloud_tool_user_fileItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${cloud_tool_user_fileItem.id}">
				</display:column>
				
				<display:column property="inputaddress" title="输入文件地址" sortable="true" sortName="inputAddress" maxLength="80"></display:column>
				<display:column property="outputaddress" title="输出文件地址" sortable="true" sortName="outputAddress" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${cloud_tool_user_fileItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${cloud_tool_user_fileItem.id}&cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>" class="link edit">编辑</a>
					<a href="get.ht?id=${cloud_tool_user_fileItem.id}&cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="cloud_tool_user_fileItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


