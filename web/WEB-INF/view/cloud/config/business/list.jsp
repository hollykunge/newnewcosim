<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>商机管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">商机管理列表</span>
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
						<span class="label">商机名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">商机内容:</span><input type="text" name="Q_content_SL"  class="inputText" />
						<span class="label">开始时间:</span> <input  name="Q_beginstartTime_DL"  class="inputText date" />
						<span class="label">结束时间:</span> <input  name="Q_beginendTime_DL"  class="inputText date" />
						<span class="label">商机类型:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">图片:</span><input type="text" name="Q_image_SL"  class="inputText" />
						<span class="label">商机属性:</span><input type="text" name="Q_properties_SL"  class="inputText" />
						<span class="label">企业ID:</span><input type="text" name="Q_companyId_SL"  class="inputText" />
						<span class="label">用户ID:</span><input type="text" name="Q_userId_SL"  class="inputText" />
						<span class="label">发布时间 :</span> <input  name="Q_beginoperateTime_DL"  class="inputText date" />
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="businessChanceList" id="businessChanceItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${businessChanceItem.id}">
				</display:column>
				<display:column property="name" title="name" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="content" title="content" sortable="true" sortName="content" maxLength="80"></display:column>
				<display:column  title="start_time" sortable="true" sortName="startTime">
					<fmt:formatDate value="${businessChanceItem.startTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="end_time" sortable="true" sortName="endTime">
					<fmt:formatDate value="${businessChanceItem.endTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="type" title="type" sortable="true" sortName="type"></display:column>
				<display:column property="image" title="image" sortable="true" sortName="image" maxLength="80"></display:column>
				<display:column property="properties" title="properties" sortable="true" sortName="properties" maxLength="80"></display:column>
				<display:column property="companyId" title="company_id" sortable="true" sortName="companyId"></display:column>
				<display:column property="userId" title="user_id" sortable="true" sortName="userId"></display:column>
				<display:column  title="operate_time" sortable="true" sortName="operateTime">
					<fmt:formatDate value="${businessChanceItem.operateTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${businessChanceItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${businessChanceItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${businessChanceItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="businessChanceItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


