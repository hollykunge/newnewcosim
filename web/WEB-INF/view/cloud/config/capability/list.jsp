<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_capability管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">已发布能力管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="${ctx}/cloud/config/capability/capabilityClass.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					 
					 
					<div class="group"><a class="link update"  action="remove.ht">取消发布</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">能力名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">能力分类:</span><input type="text" name="Q_typeName_SL"  class="inputText" />
						 <span class="label">关键词:</span><input type="text" name="Q_prokey_SL"  class="inputText" />
							</div>
						<div class="row">
						<span class="label">发布时间 从:</span> <input  name="Q_beginpublishDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endpublishDate_DG" class="inputText date" />
						
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="capabilityList" id="capabilityItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${capabilityItem.id}">
				</display:column>
				<display:column property="name" title="能力名称" sortable="true" sortName="name"></display:column>
				<display:column property="typeName" title="能力分类" sortable="true" sortName="typeName"></display:column>
				<display:column property="entName" title="所属企业" sortable="true" sortName="entName"></display:column>
				<display:column property="publisher" title="发布人" sortable="true" sortName="publisher"></display:column>
				<display:column  title="发布时间" sortable="true" sortName="publishDate">
					<fmt:formatDate value="${capabilityItem.publishDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="useScope" title="适用范围" sortable="true" sortName="useScope" maxLength="80"></display:column>
				<display:column property="publishState" title="发布状态" sortable="true" sortName="publishState"></display:column>
				<display:column property="prokey" title="关键词" sortable="true" sortName="prokey"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="remove.ht?id=${capabilityItem.id}" class="link update">取消发布</a>
				 
					<a href="get.ht?id=${capabilityItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="capabilityItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>

</html>


