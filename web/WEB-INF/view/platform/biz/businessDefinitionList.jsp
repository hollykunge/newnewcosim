<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>业务定义，如邀标采购这样的大业务。管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务定义，如邀标采购这样的大业务。管理列表</span>
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
						<span class="label">业务编号:</span><input type="text" name="Q_bizDefNo_SL"  class="inputText" />
						<span class="label">业务名称:</span><input type="text" name="Q_defName_SL"  class="inputText" />
						<span class="label">描述:</span><input type="text" name="Q_defDescription_SL"  class="inputText" />
						<span class="label">版本号:</span><input type="text" name="Q_versionNo_SN"  class="inputText" />
						<span class="label">主版本:</span><input type="text" name="Q_isMain_SN"  class="inputText" />
						<span class="label">状态::</span><input type="text" name="Q_status_SN"  class="inputText" />
						<span class="label">创建人:</span><input type="text" name="Q_createBy_SL"  class="inputText" />
						<span class="label">创建日期 从:</span> <input  name="Q_begincreateTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcreateTime_DG" class="inputText date" />
						<span class="label">更新人:</span><input type="text" name="Q_updateBy_SL"  class="inputText" />
						<span class="label">更新日期 从:</span> <input  name="Q_beginupdateTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endupdateTime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="businessDefinitionList" id="businessDefinitionItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="bizDefId" value="${businessDefinitionItem.bizDefId}">
				</display:column>
				<display:column property="bizDefNo" title="业务编号" sortable="true" sortName="bizDefNo" maxLength="80"></display:column>
				<display:column property="defName" title="业务名称" sortable="true" sortName="defName" maxLength="80"></display:column>
				<display:column property="defDescription" title="描述" sortable="true" sortName="defDescription" maxLength="80"></display:column>
				<display:column property="versionNo" title="版本号" sortable="true" sortName="versionNo"></display:column>
				<display:column property="isMain" title="主版本" sortable="true" sortName="isMain"></display:column>
				<display:column property="status" title="状态:" sortable="true" sortName="status"></display:column>
				<display:column property="createBy" title="创建人" sortable="true" sortName="createBy"></display:column>
				<display:column  title="创建日期" sortable="true" sortName="createTime">
					<fmt:formatDate value="${businessDefinitionItem.createTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="updateBy" title="更新人" sortable="true" sortName="updateBy"></display:column>
				<display:column  title="更新日期" sortable="true" sortName="updateTime">
					<fmt:formatDate value="${businessDefinitionItem.updateTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?bizDefId=${businessDefinitionItem.bizDefId}" class="link del">删除</a>
					<a href="edit.ht?bizDefId=${businessDefinitionItem.bizDefId}" class="link edit">编辑</a>
					<a href="get.ht?bizDefId=${businessDefinitionItem.bizDefId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="businessDefinitionItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


