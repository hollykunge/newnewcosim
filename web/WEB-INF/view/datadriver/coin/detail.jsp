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
    <div class="panel-detail">
        <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <th width="20%">标题:</th>
                <td>${detailList.id}</td>
            </tr>
            <tr>
                <th width="20%">发信人:</th>
                <td>${detailList.uid}</td>
            </tr>
            <tr>
                <th width="20%">发信时间:</th>
                <td><fmt:formatDate value="${detailList.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th width="20%">内容:</th>
                <td>${detailList.sourceScore}</td>
            </tr>
        </table>

    </div>
</div>
</body>
</html>
