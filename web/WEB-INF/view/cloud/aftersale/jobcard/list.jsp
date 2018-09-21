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
				<span class="tbar-label">维修工作单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- 
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					<div class="l-bar-separator"></div>
					 -->
					<div class="group"><a class="link back"  onClick="location.href='reportFaultByMonth.ht'">月度维修统计图</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">产品名称:</span><input type="text" name="Q_prodname_SL"  class="inputText" />
						<span class="label">产品编码:</span><input type="text" name="Q_prodcode_SL"  class="inputText" />
						<span class="label">故障分类:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">维修人员:</span><input type="text" name="Q_man_SL"  class="inputText" />
						<br/>
						<span class="label">维修时间从:</span><input type="text" name="Q_startOperatedate_DL"  class="inputText date" />
						<span class="label">至:</span><input type="text" name="Q_endOperatedate_GL"  class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="jobCardList" id="jobCardItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${jobCardItem.id}">
				</display:column>
				<%-- <display:column property="taskid" title="维修任务ID" sortable="true" sortName="taskid"></display:column> --%>
				<display:column property="prodcode" title="产品编码" sortable="true" sortName="prodcode"></display:column>
				<display:column property="prodname" title="产品名称" sortable="true" sortName="prodname"></display:column>
				<display:column property="type" title="故障分类" sortable="true" sortName="type" maxLength="80"></display:column>
				<display:column property="measure" title="检修措施" sortable="true" sortName="measure" maxLength="80"></display:column>
				<display:column property="man" title="维修人员" sortable="true" sortName="man"></display:column> 
				<display:column property="manhours" title="维修工时" sortable="true" sortName="manhours"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${jobCardItem.id}" class="link detail">明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="jobCardItem"/>
		</div><!-- end of panel-body -->	
	</div> <!-- end of panel -->
</body>
</html>


