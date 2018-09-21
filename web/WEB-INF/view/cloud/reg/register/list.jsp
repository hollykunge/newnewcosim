<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_user_register管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_user_register管理列表</span>
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
						<span class="label">姓名:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">性别:</span><input type="text" name="Q_sex_SL"  class="inputText" />
						<span class="label">邮箱:</span><input type="text" name="Q_email_SL"  class="inputText" />
						<span class="label">身份证号:</span><input type="text" name="Q_identity_SL"  class="inputText" />
						<span class="label">手机号码:</span><input type="text" name="Q_tellphone_SL"  class="inputText" />
						<span class="label">获奖证书:</span><input type="text" name="Q_credential_SL"  class="inputText" />
						<span class="label">作品介绍:</span><input type="text" name="Q_introduce_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="registerList" id="registerItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${registerItem.id}">
				</display:column>
				<display:column property="name" title="姓名" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="sex" title="性别" sortable="true" sortName="sex"></display:column>
				<display:column property="email" title="邮箱" sortable="true" sortName="email" maxLength="80"></display:column>
				<display:column property="identity" title="身份证号" sortable="true" sortName="identity"></display:column>
				<display:column property="tellphone" title="手机号码" sortable="true" sortName="tellphone"></display:column>
				<display:column property="credential" title="获奖证书" sortable="true" sortName="credential" maxLength="80"></display:column>
				<display:column property="introduce" title="作品介绍" sortable="true" sortName="introduce" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${registerItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${registerItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${registerItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="registerItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


