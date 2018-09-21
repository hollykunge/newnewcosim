<%@page import="com.casic.cloud.model.tool.ToolType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>用户工具映射管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx }/js/cloud/toolDialog.js"></script>
<script type="text/javascript">
	function dlgCallBack(toolIds, toolNames) {
		
		if (toolIds.length > 0) {
			var form = new com.hotent.form.Form();
			form.creatForm("form", "${ctx}/cloud/toolUser/add.ht");
			form.addFormEl("userId", "${userId}");
			form.addFormEl("toolIds", toolIds);
 	 		form.submit();
		}
	};

	function add() {
		
		ToolDialog({
			callback : dlgCallBack
		});
	}
	
	$(document).ready(function(){
		$(".paras").click(function(){
			var toolUserId = $(this).attr("id");
			window.open("../tool/parasMapper/list.ht?cloudToolUserId="+toolUserId,'newwindow','height=400,width=800,top=300,left=400,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=no');  
		});	
		$(".input").click(function(){
			var toolUserId = $(this).attr("id");
			window.open("../tool/input/list.ht?cloudToolUserId="+toolUserId,'newwindow','height=400,width=800,top=300,left=400,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=no');  
		});
		$(".output").click(function(){
			var toolUserId = $(this).attr("id");
			window.open("../tool/output/list.ht?cloudToolUserId="+toolUserId,'newwindow','height=400,width=800,top=300,left=400,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=no');  
		});
		$(".share").click(function(){
			var toolUserId = $(this).attr("id");
			window.open("../toolUser/shareUserList.ht?cloudToolUserId="+toolUserId,'newwindow','height=400,width=800,top=300,left=400,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=no');  
		});
	});
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户工具映射管理</span>
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
					
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht?userId=${userId}">
						<div class="row">
						<span class="label">工具名称:</span>
						<input type="text" name="Q_toolName_SL"  class="inputText" value="${param['Q_toolName_SL']}"/>
						<c:set var="LOCAL"><%=ToolType.LOCAL.getCode() %></c:set>
						<c:set var="NETWORK"><%=ToolType.NETWORK.getCode() %></c:set>
						<select name="Q_toolType_S" class="select" style="width:8%;" value="${param['Q_toolType_S']}">
							<option value="">所有</option>
							<option value="<%=ToolType.LOCAL.getCode() %>" <c:if test="${param['Q_toolType_S'] == LOCAL }">selected</c:if>><%=ToolType.LOCAL.getDescription()%></option>
							<option value="<%=ToolType.NETWORK.getCode() %>" <c:if test="${param['Q_toolType_S'] == NETWORK }">selected</c:if>><%=ToolType.NETWORK.getDescription() %></option>
						</select>				
						</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="toolUserList" id="toolUserItem" requestURI="list.ht" sort="external" cellpadding="1"
				cellspacing="1" export="false" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="toolUserId" value="${toolUserItem.toolUserId}">
				</display:column>

				<display:column property="toolName" title="工具名称" sortable="true" sortName="toolName"></display:column>
				<display:column property="toolTypeAsString" title="工具类型" sortable="true" sortName="toolType"></display:column>
				
				<display:column property="toolAddress" title="工具缺省地址" sortable="true" sortName="toolAddress" maxLength="40"></display:column>
				
				
			
				<display:column property="myToolAddress" title="用户工具地址" sortable="true" sortName="myToolAddress" maxLength="40"></display:column>
		
				
				<display:column title="管理" media="html" style="width:100px">
					<a href="del.ht?toolUserId=${toolUserItem.toolUserId}" class="link del">删除</a>
					<a href="edit.ht?toolUserId=${toolUserItem.toolUserId}" class="link edit">编辑</a>
				</display:column>
				
				
				<display:column title="配置" media="html" style="width:240px">
					<a href="javascript:void(0)" class="paras"  id="${toolUserItem.toolUserId}">参数配置</a>
					<a href="javascript:void(0)" class="input"  id="${toolUserItem.toolUserId}" style="margin-left: 10px;margin-right:10px">输入配置</a>
					<a href="javascript:void(0)" class="output"  id="${toolUserItem.toolUserId}" style="margin-right:10px">输出配置</a>
					<a href="javascript:void(0)" class="share"  id="${toolUserItem.toolUserId}" style="">共享配置</a>
				</display:column>
				
				
			</display:table>
			<hotent:paging tableId="toolUserItem"/>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


