<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ include file="/commons/cloud/meta.jsp"%>

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
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="myBusinessChase.ht">
					<div class="row">
						<span class="label">商机名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						 
						 <span class="label">审核状态:</span> <select name="Q_publishState_SL"><option value="">全部</option><option value="未审核">未审核</option><option value="审核通过">审核通过</option><option value="审核未通过">审核未通过</option> </select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table   name="myBusinessChase" id="myBusinessChaseItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${myBusinessChaseItem.id}">
				</display:column>
				<display:column property="classid" title="专业学科分类" sortable="true" sortName="classid" maxLength="80"></display:column>
				<display:column property="name" title="商机名称" sortable="true" sortName="name" maxLength="80"></display:column>
				 
				 
				 
				 
				<display:column  title="发布时间" sortable="true" sortName="operateTime">
					<fmt:formatDate value="${myBusinessChaseItem.operateTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="companyName" title="发布企业" sortable="true" sortName="companyName" maxLength="80"></display:column>
				 
				<display:column property="publishState" title="审核状态" sortable="true" sortName="publishState"></display:column>
				<display:column title="管理" media="html" style="width:150px">
				   <c:if test="${myBusinessChaseItem.publishState=='审核未通过'}">
					<a href="del.ht?id=${myBusinessChaseItem.id}" class="link del">删除</a>
					
					<a href="edit.ht?id=${myBusinessChaseItem.id}" class="link edit">修改</a>  
				    
				    </c:if>
					<a href="mget.ht?id=${myBusinessChaseItem.id}" class="link detail">明细</a>
				</display:column>
				 
			</display:table>
			<hotent:paging tableId="myBusinessChaseItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	 
 
</body>
</html>


