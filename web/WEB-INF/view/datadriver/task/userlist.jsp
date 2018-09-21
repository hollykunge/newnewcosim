<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>用户列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/datadriver/getbase.jsp" %>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <style>
        .fl {
            float: left;
        }

        .fr {
            float: right;
        }
    </style>
</head>
<body>


<div style="height: 50px;">


    <form id="searchForm" method="post" action="list.ht">
<span class="fl">
          <input type="text" name="Q_id_SL" class="layui-input"
                 value="${param['Q_id_SL']}" placeholder="用户编号"/>
    </span>
        <span class="fl">
          <input type="text" name="Q_name_SL " class="layui-input"
                 value="${param['Q_name_SL']}" placeholder="用户名称"/>
 </span>
    </form>

    <span class="fr">
                  <a class="layui-btn layui-btn-normal" id="Search">查询</a>
        </span>
</div>
<c:set var="checkAll">
    <input type="checkbox" id="chkall"/>
</c:set>
<display:table name="sysUserList" id="sysUserItem" requestURI="userlist.ht" sort="external" cellpadding="1"
               cellspacing="1" export="false" class="layui-table">
    <display:column title="${checkAll}" media="html" style="width:30px;">
        <input type="checkbox" class="pk" name="id" value="${sysUserItem.userId}">
    </display:column>
    <display:column property="userId" title="人员编号" sortable="true"
                    maxLength="80"></display:column>
    <display:column property="fullname" title="人员名称" sortable="true"
                    maxLength="80"></display:column>
    <display:column property="account" title="人员账号" maxLength="80"></display:column>


    <display:column title="操作" media="html" style="width:260px">
        <a href="chooseuser.ht?UserId=${sysUserItem.userId}&TaskId=${TaskId}&fullname=${sysUserItem.fullname}&projectId=${projectId}"
           class="layui-btn layui-btn-small">选择人员</a>
    </display:column>
</display:table>
<hotent:paging tableId="sysUserItem"/>


</body>
</html>


