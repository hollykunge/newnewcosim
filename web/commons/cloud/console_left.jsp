<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.sq{
	color:#067bc0; 
	font-size:14px; 
	font-weight:bold; 
	text-decoration:none;
	line-height:25px;  
}

.sq:hover{
	color:#024474; 
	font-size:14px; 
	font-weight:bold; 
	text-decoration:none;
	line-height:25px;
}
</style>
<!--左侧菜单-->
<div id="leftrq">
			<div id="leftmenu">				
				<div class="leftmenuh2">快捷操作</div>
				<ul>
					<li><a href="${ctx}/cloud/console/outline.ht?target=/platform/bpm/taskInfo/forMe.ht&resId=10000000290013"
				id="m_1">待办任务</a></li>
					<li><a
				href="${ctx}/cloud/console/outline.ht?target=/platform/bpm/processRun/myAttend.ht&resId=10000000290013"
				id="m_2">已办任务</a></li>
				
					<li><a href="${ctx}/cloud/console/calendar.ht" id="m_3">我的日程</a></li>
					<li><a href="${ctx}/cloud/console/userInfo.ht" id="m_4">个人信息</a></li>
					<li><a href="${ctx}/cloud/console/modifyPassword.ht" id="m_5">修改密码</a></li>
					<sec:authorize ifAnyGranted="cloud_ROLE_ORGADMIN">
					<li><a href="${ctx}/cloud/aftersale/jobcard/reportYwlCost.ht" id="m_2">业务量统计</a></li>
					</sec:authorize>
					<li><a href="${ctx}/cloud/tool/list.ht" id="m_3">工具管理</a></li>
				</ul>
			</div>

</div>