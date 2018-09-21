<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/2/24
  Time: 上午10:51
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>快照任务列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <script src="${ctx}/newtable/jquery.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this"></li>
    </ul>
    <div class="layui-tab-content">
        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <display:table name="snapshotTaskList" id="snapshotTaskList" requestURI="snapshottasklist.ht" sort="external"
                       cellpadding="1"
                       cellspacing="1" export="false" class="layui-table" pagesize="10">
            <display:column title="${checkAll}" media="html" style="width:3%;">
                <input type="checkbox" class="pk" name="id" value="${snapshotTaskList.ddTaskId}">
            </display:column>
            <display:column property="ddTaskName" title="任务名称" sortable="false" maxLength="80"></display:column>
            <display:column title="操作" media="html" style="width:15%">
                <a href="datasnapshotlist.ht?ddTaskId=${snapshotTaskList.ddTaskId}&&ddSnapShotTime=${ddSnapShotTime}"
                   class="layui-btn layui-btn-normal layui-btn-mini"><i
                        class="layui-icon">&#xe639;</i> 查看</a>
            </display:column>
        </display:table>
    </div>
</div>
</body>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</html>


