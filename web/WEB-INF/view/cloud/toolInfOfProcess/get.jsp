<%@page
	import="com.casic.cloud.model.tool.toolDataOfProcess.ToolDataOfNode"%>
<%@page import="java.util.List"%>
<%@page import="com.hotent.platform.model.bpm.BpmDefinition"%>
<%@page
	import="com.casic.cloud.model.tool.toolDataOfProcess.ToolDataOfProcess"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<html>
<head>
<title>流程工具输出数据文件</title>
<%@include file="/commons/include/get.jsp"%>

<link rel="stylesheet" href="${ctx}/js/viewtree/jquery.treeview.css" />

<link rel="stylesheet" href="${ctx}/js/viewtree/red-treeview.css" />

<!-- <link rel="stylesheet" href="${ctx}/js/viewtree/demo/screen.css" /> -->

<f:link href="Aqua/css/bootstrap.css"></f:link>



<script src="${ctx}/js/viewtree/jquery.js" type="text/javascript"></script>

<script src="${ctx}/js/viewtree/lib/jquery.cookie.js"
	type="text/javascript"></script>

<script src="${ctx}/js/viewtree/jquery.treeview.js"
	type="text/javascript"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$("#browser").treeview({
			toggle : function() {
				console.log("%s was toggled.", $(this).find(">span").text());
			}
		});

		$(".errorCurve").click(function(){
			var href = $(this).attr("href");
			
			window.open(href,'误差曲线',"height=400, width=600, top=0, left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
			return false;
		});
	});
	
	
</script>
</head>
<body>

	<%
		ToolDataOfProcess toolDataOfProcess = (ToolDataOfProcess) request
				.getAttribute("toolDataOfProcess");
		BpmDefinition bpmDefinition = toolDataOfProcess.getBpmDefinition();
		List<ToolDataOfNode> toolDataOfNodes = toolDataOfProcess
				.getToolDataOfNodes();
		//System.out.println(toolDataOfNodes.get(0).getToolDatas().get(0).getCloud_tool_user_outputFiles().get(0).getId());
	%>
	<div class="panel">
	<div class="tbar-title">
				<span class="tbar-label">流程工具数据查看</span>
			</div>
	<div id="main" style="margin-left: 40%;">

		<ul id="browser" class="filetree treeview-famfamfam">
			<li><span class="folder"><%=bpmDefinition.getSubject()%></span>
				<ul>
					<c:forEach var="toolDataOfNode" items="<%=toolDataOfNodes%>">
						<li><span class="folder">${toolDataOfNode.bpmNodeSet.nodeName}</span>
							<ul>
								<c:forEach var="tooData"
									items="${toolDataOfNode.toolDatas}">
									<li><span class="folder">${tooData.toolBpmNode.toolName}
									</span>
										<ul>
											<c:forEach var="outFile"
												items="${tooData.cloud_tool_user_outputFiles}">
												<li><span class="file">
												<c:choose>
												<c:when test="${outFile.name==\"error.dat\"}">
												<c:choose>
												
												<c:when test="${tooData.tool.toolType==1||outFile.exist}">
												
												<a class="errorCurve" href="${ctx}/cloud/toolInfOfProcess/errorCurve.ht?outputFileId=${outFile.id}&toolNodeId=${tooData.toolBpmNode.toolNodeId}&runId=<%=request.getAttribute("runId") %>">${outFile.name}</a>
												</c:when>
												<c:otherwise>
												
												${outFile.name}
												</c:otherwise>
												</c:choose>
												
												</c:when>
												<c:otherwise>
												<c:choose>
												<c:when test="${tooData.tool.toolType==1||outFile.exist}">
												
												<a href="cosim://outputFileId=${outFile.id}&toolNodeId=${tooData.toolBpmNode.toolNodeId}&runId=<%=request.getAttribute("runId") %>&type=2">${outFile.name}</a>
												</c:when>
												<c:otherwise>
												
												${outFile.name}
												</c:otherwise>
												</c:choose>
												
												</c:otherwise>
												</c:choose>
												</span></li>
											</c:forEach>

										</ul></li>
								</c:forEach>

							</ul></li>

					</c:forEach>
				</ul></li>
		</ul>


	</div>
	
	</div>
</body>
</html>

