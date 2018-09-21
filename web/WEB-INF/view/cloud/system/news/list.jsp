<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>帮助公告管理</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">帮助公告管理列表</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link search" id="btnSearch">查询</a></div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link add" href="edit.ht">添加</a></div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link del" action="del.ht">删除</a></div>
            </div>
        </div>
        <div class="panel-search">
            <form id="searchForm" method="post" action="list.ht">
                <div class="row">
                    <span class="label">标题:</span><input type="text" name="Q_title_SL" class="inputText"/>
                    <span class="label">内容:</span><input type="text" name="Q_content_SL" class="inputText"/>
                    <span class="label">发布时间:</span> <input name="Q_beginpublishtime_DL" class="inputText date"/>
                    <span class="label">至: </span><input name="Q_endpublishtime_DG" class="inputText date"/>
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="newsList" id="newsItem" requestURI="list.ht" sort="external" cellpadding="1"
                       cellspacing="1" export="true" class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px;">
                <input type="checkbox" class="pk" name="id" value="${newsItem.id}">
            </display:column>
            <display:column property="title" title="标题" sortable="true" sortName="title"
                            maxLength="80"></display:column>
            <display:column property="publisher" title="发布者" sortable="true" sortName="publisher"></display:column>
            <display:column title="发布时间" sortable="true" sortName="publishtime">
                <fmt:formatDate value="${newsItem.publishtime}" pattern="yyyy-MM-dd"/>
            </display:column>
            <display:column title="管理" media="html" style="width:180px">
                <a href="del.ht?id=${newsItem.id}" class="link del">删除</a>
                <a href="edit.ht?id=${newsItem.id}" class="link edit">编辑</a>
                <a href="get.ht?id=${newsItem.id}" class="link detail" target="_blank">明细</a>
            </display:column>
        </display:table>
        <hotent:paging tableId="newsItem"/>
    </div><!-- end of panel-body -->
</div> <!-- end of panel -->
</body>
</html>