<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>业务实例管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务实例管理列表</span>
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
						<span class="label">业务定义Id:</span><input type="text" name="Q_bizDefId_SL"  class="inputText" />
						<span class="label">状态:</span><input type="text" name="Q_status_SN"  class="inputText" />
						<span class="label">开始时间 从:</span> <input  name="Q_beginstartTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endstartTime_DG" class="inputText date" />
						<span class="label">结束时间 从:</span> <input  name="Q_beginendTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endendTime_DG" class="inputText date" />
<!-- 						<span class="label">创建人:</span><input type="text" name="Q_createBy_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bizInstanceList" id="bizInstanceItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="bizInstanceId" value="${bizInstanceItem.bizInstanceId}">
				</display:column>
				<display:column property="bizDefId" title="业务定义Id" sortable="true" sortName="bizDefId"></display:column>
				<display:column  title="状态:" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${bizInstanceItem.status==1 }">
							<span class="green">运行</span>
						</c:when>
						<c:when test="${bizInstanceItem.status==2 }">
							<span class="red">结束</span>
						</c:when>
						<c:when test="${bizInstanceItem.status==-1 }">
							<span class="brown">异常</span>
						</c:when>
						<c:otherwise>
							<span class="brown">未知</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column  title="开始时间" sortable="true" sortName="startTime">
					<fmt:formatDate value="${bizInstanceItem.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
				</display:column>
				<display:column  title="结束时间" sortable="true" sortName="endTime">
					<fmt:formatDate value="${bizInstanceItem.endTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
				</display:column>
<%-- 				<display:column property="createBy" title="创建人" sortable="true" sortName="createBy"></display:column> --%>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?bizInstanceId=${bizInstanceItem.bizInstanceId}" class="link del">删除</a>
					<a href="edit.ht?bizInstanceId=${bizInstanceItem.bizInstanceId}" class="link edit">编辑</a>
					<a href="get.ht?bizInstanceId=${bizInstanceItem.bizInstanceId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="bizInstanceItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


