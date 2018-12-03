<%--
  Created by IntelliJ IDEA.
  User: hollykunge
  Date: 2018/11/23
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>抽奖名单和中奖名单</title>
    <%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">抽奖名单和中奖名单</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link search" id="btnSearch">查询</a></div>
                <div class="group"><a class="link del" action="del.ht">删除</a></div>
            </div>
        </div>
        <div class="panel-search">
            <form id="searchForm" method="post" action="list.ht">
                <div class="row">
                    <span class="label">中奖人姓名:</span><input type="text" name="Q_userName_SL" class="inputText"
                                                            value="${param['Q_userName_SL']}"/>
                    <span class="label">第几期:</span>
                    <select name="Q_period_S" class="select" value="${param['Q_period_S']}">
                        <option value="">全部</option>
                        <option value="quanju" <c:if test="${param['Q_sourceType_S'] == 'quanju'}">selected</c:if>>全局
                        </option>
                    </select>
                </div>
            </form>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="gamblerCollectionList" id="gamblerCollectionItem" requestURI="list.ht" sort="external"
                       cellpadding="1" cellspacing="1" export="true" class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px; text-align: center;">
                <input type="checkbox" class="pk" name="id" value="${gamblerCollectionItem.id}">
            </display:column>
            <display:column property="period" title="期数" sortable="true" sortName="period" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column property="gamblerNum" title="参与人数" sortable="true" sortName="gambler_num" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column property="winnerNum" title="中奖人数" sortable="true" sortName="winner_num" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column property="winnerName" title="中奖人员集合" sortable="true" sortName="winner_name" maxLength="80"
                            style="text-align: center;"></display:column>
            <display:column title="管理" media="html" style="width:160px; text-align:center;">
                <a href="edit.ht?id=${scoreInflowItem.id}" class="link edit">查看</a>
            </display:column>
        </display:table>
        <hotent:paging tableId="gamblerCollectionItem"/>
    </div><!-- end of panel-body -->
</div> <!-- end of panel -->
</body>
</html>
