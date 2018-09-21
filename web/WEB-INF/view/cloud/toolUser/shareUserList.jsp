<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>共享用户表</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript"
	src="${ctx }/js/lg/plugins/ligerWindow.js"></script>

</head>
<body>
	<c:set var="SysUser_EXPIRED" value="<%=SysUser.EXPIRED%>" />
	<c:set var="SysUser_UN_EXPIRED" value="<%=SysUser.UN_EXPIRED%>" />

	<c:set var="SysUser_LOCKED" value="<%=SysUser.LOCKED%>" />
	<c:set var="SysUser_UN_LOCKED" value="<%=SysUser.UN_LOCKED%>" />
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">共享用户表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<f:a alias="searchEnterpriseUser" css="link search" id="btnSearch">查询</f:a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<f:a alias="addEnterpriseUser" css="link add" href="edit.ht">添加</f:a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<f:a alias="delEnterpriseUser" css="link del" action="del.ht">删除</f:a>
					</div>
					<div class="l-bar-separator"></div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">姓名:</span><input type="text"
							name="Q_fullname_SL" class="inputText" style="width: 9%"
							value="${param['Q_fullname_SL']}" />
						<!-- 					
								<span class="label">组织sn:</span><input type="text" name="Q_orgSn_L"  class="inputText" style="width:9%" value="${param['Q_orgSn_L']}"/>					
								 -->
						<span class="label">创建时间从:</span><input type="text"
							id="Q_begincreatetime_DL" name="Q_begincreatetime_DL"
							class="inputText Wdate"
							onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endcreatetime_DG\');}'})"
							style="width: 9%" value="${param['Q_begincreatetime_DL']}" /> <span
							class="label">至</span><input type="text" id="Q_endcreatetime_DG"
							name="Q_endcreatetime_DG" class="inputText Wdate"
							onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_begincreatetime_DL\');}'})"
							style="width: 9%" value="${param['Q_endcreatetime_DG']}" /> <span
							class="label">是否过期:</span> <select name="Q_isExpired_S"
							class="select" style="width: 8%;"
							value="${param['Q_isExpired_S']}">
							<option value="">--选择--</option>
							<option value="${SysUser_EXPIRED}"
								<c:if test="${param['Q_isExpired_S'] == SysUser_EXPIRED }">selected</c:if>>是</option>
							<option value="${SysUser_UN_EXPIRED}"
								<c:if test="${param['Q_isExpired_S'] == SysUser_UN_EXPIRED }">selected</c:if>>否</option>
						</select> <span class="label">是否锁定:</span> <select name="Q_isLock_S"
							class="select" style="width: 8%;" value="${param['Q_isLock_S']}">
							<option value="">--选择--</option>
							<option value="${SysUser_LOCKED}"
								<c:if test="${param['Q_isLock_S'] == SysUser_LOCKED }">selected</c:if>>是</option>
							<option value="${SysUser_UN_LOCKED}"
								<c:if test="${param['Q_isLock_S'] == SysUser_UN_LOCKED }">selected</c:if>>否</option>
						</select> <span class="label">状态:</span> <select name="Q_status_S"
							class="select" style="width: 8%;" value="${param['Q_status_S']}">
							<option value="">--选择--</option>
							<option value="<%=SysUser.STATUS_OK%>">激活</option>
							<option value="<%=SysUser.STATUS_NO%>">禁用</option>
							<option value="<%=SysUser.STATUS_Del%>">删除</option>
						</select>
						<%-- /////ht del b
								<span class="label">来源:</span>
								<select name="Q_fromType_S" class="select" style="width:8%;" value="${param['Q_fromType_S']}">
									<option value="">--选择--</option>
									<option value="<%=SysUser.FROMTYPE_DB %>">系统添加</option>
									<option value="<%=SysUser.FROMTYPE_AD %>">AD同步</option>
								</select>
								/////ht del e --%>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="sysUserList" id="sysUserItem"
				requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"
				export="false" class="table-grid">
				<display:column title="${checkAll}" media="html"
					style="width:30px;text-align:center;">
					<input type="checkbox" class="pk" name="userId"
						value="${sysUserItem.userId}">
				</display:column>
				<display:column property="fullname" title="姓名" sortable="true"
					sortName="fullname" style="width:100px;text-align:left"></display:column>
				<display:column property="account" title="帐号" sortable="true"
					sortName="account" style="text-align:left"></display:column>
				<display:column property="shortAccount" title="人员角色" sortable="true"
					sortName="shortAccount" style="text-align:left"></display:column>
				<display:column title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${sysUserItem.createtime}"
						pattern="yyyy-MM-dd" />
				</display:column>
				<display:column title="是否过期" sortable="true" sortName="isExpired">
					<c:choose>
						<c:when test="${sysUserItem.isExpired==1}">
							<span class="red">已过期</span>
						</c:when>
						<c:otherwise>
							<span class="green">未过期</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="是否可用" sortable="true" sortName="isLock">
					<c:choose>
						<c:when test="${sysUserItem.isLock==1}">
							<span class="red">已锁定</span>
						</c:when>
						<c:otherwise>
							<span class="green">未锁定</span>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="状态" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${sysUserItem.status==1}">
							<span class="green">激活</span>

						</c:when>
						<c:when test="${sysUserItem.status==0}">
							<span class="red">禁用</span>

						</c:when>
						<c:otherwise>
							<span class="red">删除</span>

						</c:otherwise>
					</c:choose>
				</display:column>
				<%-- /////ht del b
					<display:column title="数据来源" sortable="true" sortName="fromType">
						<c:choose>
							<c:when test="${sysUserItem.fromType==1}">
								<span class="brown">AD</span>
								
						   	</c:when>
						   	<c:when test="${sysUserItem.fromType==0}">
						   		<span class="green">系统添加</span>
						   	</c:when>
					       	<c:otherwise>
					       		<span class="red">未知</span>
						   	</c:otherwise>
						</c:choose>
					</display:column>
					/////ht del e --%>
				<display:column title="管理" media="html"
					style="width:200px;text-align:center;">

					<f:a alias="updateEnterpriseUserInfo" css="link add"
						href="${ctx}/cloud/toolUser/shareTool.ht?cloudToolUserId=${cloudToolUserId}&userId=${sysUserItem.userId}">复制工具配置给该用户</f:a>

				</display:column>
			</display:table>
			<hotent:paging tableId="sysUserItem" />

		</div>
	</div>
</body>
</html>


