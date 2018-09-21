<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>维修工作单管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">指定服务站-维修任务单列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<!-- 
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					 -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">任务单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<!-- <span class="label">用户反馈:</span><input type="text" name="Q_feedback_name_SL"  class="inputText" />
						<span class="label">产品编码:</span><input type="text" name="Q_prodcode_SL"  class="inputText" /> -->
						<span class="label">产品名称:</span><input type="text" name="Q_prodname_SL"  class="inputText" />
						<!-- <span class="label">产品型号:</span><input type="text" name="Q_prodmodel_SL"  class="inputText" />
						<span class="label">问题描述:</span><input type="text" name="Q_descn_SL"  class="inputText" />
						<span class="label">维修厂商:</span><input type="text" name="Q_purenter_name_SL"  class="inputText" />
						<span class="label">备注:</span><input type="text" name="Q_remark_SL"  class="inputText" />
						<span class="label">质检意见:</span><input type="text" name="Q_qualityopinion_SL"  class="inputText" />
						<span class="label">售后主管意见:</span><input type="text" name="Q_serviceopinion_SL"  class="inputText" /> 
						<span class="label">状态:</span><input type="text" name="Q_statu_SL"  class="inputText" />-->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="taskMList" id="taskMItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${taskMItem.id}">
				</display:column>
				<display:column property="code" title="任务单号" sortable="true" sortName="code"></display:column>
				<%-- <display:column property="feedback_name" title="用户反馈" sortable="true" sortName="feedback_name"></display:column> --%>
				<display:column property="prodcode" title="产品编码" sortable="true" sortName="prodcode"></display:column>
				<display:column property="prodname" title="产品名称" sortable="true" sortName="prodname"></display:column>
				<display:column property="prodmodel" title="产品型号" sortable="true" sortName="prodmodel"></display:column>
				<%-- <display:column property="descn" title="问题描述" sortable="true" sortName="descn"></display:column> --%>
				<display:column property="purenter_name" title="维修厂商" sortable="true" sortName="purenter_name" maxLength="80"></display:column>
				<%-- <display:column property="remark" title="备注" sortable="true" sortName="remark" maxLength="80"></display:column>
				<display:column property="qualityopinion" title="质检意见" sortable="true" sortName="qualityopinion" maxLength="80"></display:column>
				<display:column property="serviceopinion" title="售后主管意见" sortable="true" sortName="serviceopinion" maxLength="80"></display:column>
				<display:column property="statu" title="状态" sortable="true" sortName="statu"></display:column> --%>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${taskMItem.id}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/get.ht?runId=${taskMItem.runId}" class="link detail">查看流程</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="taskMItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


