<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_use_res管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_use_res管理列表</span>
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
						<span class="label">企业Id:</span><input type="text" name="Q_entid_SL"  class="inputText" />
						<span class="label">资源名字:</span><input type="text" name="Q_resName_SL"  class="inputText" />
						<span class="label">资源地址链接:</span><input type="text" name="Q_resLink_SL"  class="inputText" />
						<span class="label">资源访问时间 从:</span> <input  name="Q_beginresTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endresTime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="cloudUseResList" id="cloudUseResItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${cloudUseResItem.id}">
				</display:column>
				<display:column property="entid" title="企业Id" sortable="true" sortName="entid"></display:column>
				<display:column property="resName" title="资源名字" sortable="true" sortName="resName" maxLength="80"></display:column>
				<display:column property="resLink" title="资源地址链接" sortable="true" sortName="resLink" maxLength="80"></display:column>
				<display:column  title="资源访问时间" sortable="true" sortName="resTime">
					<fmt:formatDate value="${cloudUseResItem.resTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${cloudUseResItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${cloudUseResItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${cloudUseResItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="cloudUseResItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


