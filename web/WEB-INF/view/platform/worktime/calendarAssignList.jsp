
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>日历分配管理</title>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body>

<c:if test="${flag==1}">
	<script type="text/javascript">
		$.ligerMessageBox.warn('提示信息','请先进行工作日历设置',function(rtn){});
	</script>
</c:if>

	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">日历分配管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<!--<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>-->
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">工作日历名称:</span><input type="text" name="Q_calendarName_SL" class="inputText" value="${param['Q_calendarName_SL']}"/>
						<span class="label">被分配人名称:</span><input type="text" name="Q_assignUserName_SL" class="inputText" value="${param['Q_assignUserName_SL']}"/>
							
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="calendarAssignList" id="calendarAssignItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"    class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="id" value="${calendarAssignItem.id}">
					</display:column>
					<display:column title="使用工作日历" sortable="true" sortName="canlendarId">
					<c:out value="${calendarAssignItem.calendarName}"></c:out>
					</display:column>
					<display:column title="被分配人类型" sortable="true" sortName="assignType">
						<c:if test="${calendarAssignItem.assignType==1}">
							用户	
						</c:if>
						<c:if test="${calendarAssignItem.assignType==2}">
							组织
						</c:if>
					</display:column>
					<display:column title="被分配人名称" sortable="true" sortName="assignId">
					<c:out value="${calendarAssignItem.assignUserName}"></c:out>
					</display:column>
					<display:column title="管理" media="html" style="width:180px">
						<a href="del.ht?id=${calendarAssignItem.id}" class="link del">删除</a>
						<a href="get.ht?id=${calendarAssignItem.id}" class="link detail">明细</a>
					</display:column>
				</display:table>
				<hotent:paging tableId="calendarAssignItem"/>
			
		</div><!-- end of panel-body -->
	</div> <!-- end of panel -->
</body>
</html>


