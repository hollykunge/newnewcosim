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
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">维修任务ID:</span><input type="text" name="Q_taskid_SL"  class="inputText" />
						<span class="label">产品编码:</span><input type="text" name="Q_prodcode_SL"  class="inputText" />
						<span class="label">产品名称:</span><input type="text" name="Q_prodname_SL"  class="inputText" />
						<span class="label">产品型号:</span><input type="text" name="Q_prodmodel_SL"  class="inputText" />
						<span class="label">问题描述:</span><input type="text" name="Q_descn_SL"  class="inputText" />
						<span class="label">故障分类:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">故障原因:</span><input type="text" name="Q_reason_SL"  class="inputText" />
						<span class="label">检修措施:</span><input type="text" name="Q_measure_SL"  class="inputText" />
						<span class="label">工作内容:</span><input type="text" name="Q_content_SL"  class="inputText" />
						<span class="label">维修人员:</span><input type="text" name="Q_man_SL"  class="inputText" />
						<span class="label">维修工时:</span><input type="text" name="Q_manhours_SL"  class="inputText" />
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
				<display:column property="taskid" title="维修任务ID" sortable="true" sortName="taskid"></display:column>
				<display:column property="prodcode" title="产品编码" sortable="true" sortName="prodcode"></display:column>
				<display:column property="prodname" title="产品名称" sortable="true" sortName="prodname"></display:column>
				<display:column property="prodmodel" title="产品型号" sortable="true" sortName="prodmodel"></display:column>
				<display:column property="descn" title="问题描述" sortable="true" sortName="descn"></display:column>
				<display:column property="type" title="故障分类" sortable="true" sortName="type" maxLength="80"></display:column>
				<display:column property="reason" title="故障原因" sortable="true" sortName="reason" maxLength="80"></display:column>
				<display:column property="measure" title="检修措施" sortable="true" sortName="measure" maxLength="80"></display:column>
				<display:column property="content" title="工作内容" sortable="true" sortName="content" maxLength="80"></display:column>
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


