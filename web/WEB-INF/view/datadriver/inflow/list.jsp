<%--
  Created by IntelliJ IDEA.
  User: jihainan
  Date: 2018/9/24
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>积分赚取列表</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">积分赚取列表</span>
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
                    <%--<span class="label">用户名称:</span><input type="text" name="Q_userName_SL"  class="inputText" />--%>
                    <span class="label">积分类型:</span><input type="text" name="Q_sourceType_SL"  class="inputText" />
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="scoreInflowList" id="scoreInflowItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px;">
                <input type="checkbox" class="pk" name="bizDefId" value="${scoreInflowItem.id}">
            </display:column>
            <display:column property="uid" title="用户" sortable="true" sortName="uid" maxLength="80"></display:column>
            <display:column property="sourceScore" title="赚取积分值" sortable="true" sortName="sourceScore" maxLength="80"></display:column>
            <display:column property="sourceType" title="积分类型" sortable="true" sortName="sourceType" maxLength="80"></display:column>
            <display:column property="sourceDetail" title="积分详情" sortable="true" sortName="sourceDetail" maxLength="80"></display:column>
            <display:column property="updTime" title="更新时间" sortable="true" sortName="updTime"></display:column>
            <display:column title="管理" media="html" style="width:260px">
                <a href="del.ht?id=${scoreInflowItem.id}" class="link del">删除</a>
                <a href="edit.ht?id=${scoreInflowItem.id}" class="link edit">编辑</a>
            </display:column>
        </display:table>
        <hotent:paging tableId="scoreInflowItem"/>
    </div><!-- end of panel-body -->
</div> <!-- end of panel -->
</body>
</html>
