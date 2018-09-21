<%@page import="com.casic.cloud.model.tool.ToolBpmNode"%>
<%@page import="com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile"%>
<%@page import="java.util.List"%>
<%@page import="com.casic.cloud.model.tool.ToolType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>节点工具映射表管理</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
//function execute(toolAddress,myToolAddress,toolType){
//if(toolType==<%=ToolType.LOCAL.getCode() %>){
//	var w = new ActiveXObject("WScript.Shell");
//	if(myToolAddress!=''){
//		alert("run");
//		w.run(myToolAddress);			
//	}else{
//		w.run(toolAddress);	
//	}
//}else if(toolType==<%=ToolType.NETWORK.getCode() %>){
//	window.open(toolAddress);		
//}
//}


function execute(toolNodeId,toolUserId,setId,toolAddress,myToolAddress,toolType){
	var inputFileId = "";
	inputFiles = $(".inputFile");
	var addDot = true;
	for(var i = 0;i<inputFiles.length;i++){
		var inputFile = inputFiles[i];
		var inputFileNameAttr = $(inputFile).attr("name");
		
		if(inputFile.checked&&inputFileNameAttr==toolNodeId){
			
			if(!addDot){
				inputFileId+=",";
			}
			inputFileId+=inputFile.id;
			addDot = false;
		}
	}
	
	var url = "${ctx}/cloud/callSoft/callSoft.ht?toolNodeId="+toolNodeId+"&toolUserId="+toolUserId+"&setId="+setId+"&toolType="+toolType+"&inputFileId="+inputFileId;
	url=url.getNewUrl();
	window.open(url);
	
	
}
function backfil(){
	//写cookie
	document.cookie="backfilFinished=yes;path=/";
}


</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">节点工具映射表管理</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" href="javascript:window.close()">关闭</a></div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="taskTools.ht?taskId=${taskId}">
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
				

				<display:column property="toolName" title="工具名称" sortable="false" sortName="toolName"></display:column>
				<display:column property="toolTypeAsString" title="工具类型" sortable="false" sortName="toolType"></display:column>
				<display:column property="toolAddress" title="缺省工具地址" sortable="false" sortName="toolAddress" maxLength="40"></display:column>
				<display:column property="myToolAddress" title="用户工具地址" sortable="false" sortName="myToolAddress" maxLength="40"></display:column>
				<display:column title="输入文件" sortable="false" sortName="myToolAddress" >
				
				
				<%
					for(Cloud_tool_user_inputFile inputFile:((ToolBpmNode)toolBpmNodeItem).getInputFiles()){
						out.write("<div><input name='"+((ToolBpmNode)toolBpmNodeItem).getToolNodeId()+"' class='inputFile' type='checkbox' id='"+inputFile.getId()+"'/>"+inputFile.getInputaddress()+"</div>");
						
					}
				%>
			
				
				
				</display:column>	
				<display:column title="管理" media="html" style="width:180px">
					<a href="javascript:execute(${toolBpmNodeItem.toolNodeId},${toolBpmNodeItem.toolUserId},${toolBpmNodeItem.setId},'${toolBpmNodeItem.toolAddress}','${toolBpmNodeItem.myToolAddress}',${toolBpmNodeItem.toolType})" class="link execute">执行</a>
					<a href="javascript:backfil()" class="link execute">回填参数</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="toolBpmNodeItem"/>
		</div>
		<!-- end of panel-body -->
	</div>
	<!-- end of panel -->
</body>
</html>


