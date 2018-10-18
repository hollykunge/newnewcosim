<%--
  Created by IntelliJ IDEA.
  User: jihainan
  Date: 2018/9/25
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>积分明细</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">消息详细信息</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link back" href="list.ht">返回</a></div>
            </div>
        </div>
    </div>
    <%--<div class="panel-detail">--%>
        <%--<table class="table-detail" cellpadding="0" cellspacing="0" border="0">--%>
            <%--<tr>--%>
                <%--<th width="20%">用户名:</th>--%>
                <%--<td>${detailList.userName}</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th width="20%">发信人:</th>--%>
                <%--<td>${detailList.uid}</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th width="20%">发信时间:</th>--%>
                <%--<td><fmt:formatDate value="${detailList.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th width="20%">内容:</th>--%>
                <%--<td>${detailList.sourceScore}</td>--%>
            <%--</tr>--%>
        <%--</table>--%>

    <%--</div>--%>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="detailList" id="detailItem" requestURI="detail.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px;">
                <input type="checkbox" class="pk" name="detailListId" value="${detailItem.id}">
            </display:column>
            <display:column property="userId" title="用户" sortable="true" sortName="userId" maxLength="80"></display:column>
            <display:column property="updTime" title="更新时间" sortable="true" sortName="updTime" maxLength="80"></display:column>
            <display:column property="sourceType" title="积分类型" sortable="true" sortName="sourceType"></display:column>
            <display:column property="sourceScore" title="获得积分" sortable="true" sortName="sourceScore"></display:column>
            <display:column property="sourceDetail" title="积分详情" sortable="true" sortName="sourceDetail" maxLength="80"></display:column>
            <%--<display:column title="管理" media="html" style="width:260px">--%>
                <%--<a href="del.ht?id=${detailList.id}" class="link del">删除</a>--%>
                <%--<a href="edit.ht?id=${detailList.id}" class="link edit">编辑</a>--%>
                <%--<a href="detail.ht?id=${detailList.id}&scoreType=${scoreItem.scoreType}" class="link detail">明细</a>--%>
            <%--</display:column>--%>
        </display:table>
        <hotent:paging tableId="detailItem"/>
    </div>
</div>
</body>
</html>
