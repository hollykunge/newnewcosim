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
            <span class="tbar-label">积分详细信息</span>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link back" href="list.ht">返回</a></div>
            </div>
        </div>
    </div>
    <div class="panel-body">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="detailList" id="detailItem" requestURI="detail.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
            <display:column title="${checkAll}" media="html" style="width:30px;">
                <input type="checkbox" class="pk" name="id" value="${detailItem.id}">
            </display:column>
            <display:column property="userName" title="用户名" sortable="true" sortName="userName" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="orgName" title="组织" sortable="true" sortName="orgName" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="sourceType" title="积分类型" sortable="true" sortName="sourceType" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="sourceDetail" title="积分详情" sortable="true" sortName="sourceDetail" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="sourceScore" title="获得积分" sortable="true" sortName="sourceScore" maxLength="80" style="text-align: center;"></display:column>
            <display:column property="updTime" title="更新时间" sortable="true" sortName="updTime" style="text-align: center;"></display:column>
        </display:table>
        <hotent:paging tableId="detailItem"/>
    </div>
</div>
</body>
</html>
