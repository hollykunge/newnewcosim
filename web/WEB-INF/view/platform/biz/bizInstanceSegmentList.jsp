<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>业务环节实例管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务环节实例管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">业务实例ID:</span><input type="text" name="Q_bizInstanceId_SL"  class="inputText" />
						<span class="label">业务环节ID:</span><input type="text" name="Q_bizDefSegmentId_SL"  class="inputText" />
						<span class="label">流程实例ID:</span><input type="text" name="Q_actInstId_SL"  class="inputText" />
						<span class="label">状态:</span><input type="text" name="Q_status_SN"  class="inputText" />
						<span class="label">序号:</span><input type="text" name="Q_sortOrder_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bizInstanceSegmentList" id="bizInstanceSegmentItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="bizInstanceSegmentId" value="${bizInstanceSegmentItem.bizInstanceSegmentId}">
				</display:column>
				<display:column property="bizInstanceId" title="业务实例ID" sortable="true" sortName="bizInstanceId"></display:column>
				<display:column property="bizDefSegmentId" title="业务环节ID" sortable="true" sortName="bizDefSegmentId"></display:column>
				<display:column property="actInstId" title="流程实例ID" sortable="true" sortName="actInstId"></display:column>
				<display:column title="状态:" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${bizInstanceSegmentItem.status==1 }">
							<span class="green">运行</span>
						</c:when>
						<c:when test="${bizInstanceSegmentItem.status==2 }">
							<span class="red">结束</span>
						</c:when>
						<c:when test="${bizInstanceSegmentItem.status==-1 }">
							<span class="brown">异常</span>
						</c:when>
						<c:otherwise>
							<span class="brown">未知</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column property="sortOrder" title="序号" sortable="true" sortName="sortOrder"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?bizInstanceSegmentId=${bizInstanceSegmentItem.bizInstanceSegmentId}" class="link del">删除</a>
					<a href="edit.ht?bizInstanceSegmentId=${bizInstanceSegmentItem.bizInstanceSegmentId}" class="link edit">编辑</a>
					<a href="get.ht?bizInstanceSegmentId=${bizInstanceSegmentItem.bizInstanceSegmentId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="bizInstanceSegmentItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


