<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2016/12/14
  Time: 下午3:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>提交信息</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <c:choose><c:when test="${valueLength==0}">
        <h4 class="modal-title" id="myModalLabel" style="color: #18a05e">提交成功</h4>
    </c:when>
        <c:otherwise>
            <h4 class="modal-title" id="myModalLabel" style="color: #dd0031">提交失败，请确认以下数据结构是否有值</h4>
        </c:otherwise></c:choose>
</div>
<div class="modal-body">
    <c:choose>
        <c:when test="${valueLength==0}">
            <i class="glyphicon glyphicon-ok" style="font-size: large;color: #18a05e"></i>
        </c:when>
        <c:otherwise>
            <display:table name="childListWithoutValue" id="childListWithoutValue" requestURI="movetask.ht"
                           sort="external"
                           cellpadding="1"
                           cellspacing="1" export="false" class="table table-striped">
                <display:column property="ddDataPath" title="数据结构名称" maxLength="80"></display:column>
                <display:column property="ddDataName" title="私有数据名称" maxLength="80"></display:column>
                <display:column property="ddDataType" title="私有数据类型" maxLength="80"></display:column>
            </display:table>
        </c:otherwise>
    </c:choose>
</div>
<div class="modal-footer">
    <a class="btn btn-success btn-block" href="list.ht" id="confirm">确认</a>
</div>
</body>
</html>
