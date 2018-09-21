<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2016/12/26
  Time: 下午10:23
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--<%@ include file="/commons/include/get.jsp" %>--%>
<html lang="zh-CN">
<head>
    <title>项目流程绘制</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
</head>
<body>
<div class="container-fluid">
    <div class="col-xs-2">
        <div class="panel">
            <div class="panel-header">模版</div>
            <div class="panel-body"></div>
        </div>
    </div>
    <div class="col-xs-8">
        <div class="panel-header">设计面板</div>
        <div class="panel-body">
            <%--<iframe src="${ctx}/datadriver/designflow/projectflow.ht?flag=0&id=${projectId}&processFlowXml=${processFlowXml}"></iframe>--%>
        </div>
    </div>
    <div class="col-xs-2">
        <div class="panel">
            <div class="panel-header">属性</div>
            <div class="panel-body"></div>
        </div>
    </div>
</div>
</body>
</html>
