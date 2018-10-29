<%--
  Created by IntelliJ IDEA.
  User: jihainan
  Date: 2018/9/29
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>二币管理</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">二币列表</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link search" id="btnSearch">查询</a></div>
            </div>
        </div>
        <div class="panel-search">
            <form id="searchForm" method="post" action="list.ht">
                <div class="row">
                    <span class="label">用户(id):</span><input type="text" name="Q_userId_SL"  class="inputText" />
                    <span class="label">积分类型:</span>
                    <select name="Q_coinType_S" class="select" value="${param['Q_coinType_S']}">
                        <option value="">全部</option>
                        <option value="quanju" <c:if test="${param['Q_coinType_S'] == 'quanju'}">selected</c:if>>全局</option>
                        <option value="qiushi" <c:if test="${param['Q_coinType_S'] == 'qiushi'}">selected</c:if>>求实</option>
                        <option value="fengxian" <c:if test="${param['Q_coinType_S'] == 'fengxian'}">selected</c:if>>奉献</option>
                        <option value="chuangxin" <c:if test="${param['Q_coinType_S'] == 'chuangxin'}">selected</c:if>>创新</option>
                    </select>
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="goldenList" id="goldenItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px;">
                <input type="checkbox" class="pk" name="bizDefId" value="${goldenItem.id}">
            </display:column>
            <display:column property="userName" title="用户名" sortable="true" sortName="userName" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="total" title="二币总量" sortable="true" sortName="total" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="coinType" title="类型" sortable="true" sortName="coinType" maxLength="80" style="text-align: center;"></display:column>
            <display:column title="管理" media="html" style="width:260px; text-align: center;">
                <a href="del.ht?id=${goldenItem.id}" class="link del">删除</a>
                <a href="edit.ht?id=${goldenItem.id}" class="link edit">编辑</a>
            </display:column>
        </display:table>
        <hotent:paging tableId="goldenItem"/>
    </div><!-- end of panel-body -->
</div> <!-- end of panel -->
</body>
</html>
