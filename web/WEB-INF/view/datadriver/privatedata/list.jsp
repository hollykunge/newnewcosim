<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>私有数据列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/datadriver/getbase.jsp" %>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <style>
        .pages {
            float: right;
        }

        .page_line {
            display: inline;
        }
    </style>
</head>
<body>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this">私有数据列表</li>
    </ul>
    <div class="layui-tab-content">
        <form id="searchForm" method="post" action="list.ht">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">数据名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="Q_name_SL " class="layui-input"
                               value="${param['Q_name_SL']}"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">任务名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="Q_taskid_SL " class="layui-input"
                               value="${param['Q_taskid_SL']}"/>
                    </div>
                </div>
                <a class="layui-btn layui-btn-normal" id="Search">查询</a>
                <a class="layui-btn layui-btn-normal" href="edit.ht?id=${taskInfo.ddTaskId}">添加</a>
                <a class="layui-btn layui-btn-normal" href="${ctx}/datadriver/task/list.ht">返回</a>
            </div>
            <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="privateDataList" id="PrivateDataItem" requestURI="list.ht" sort="external"
                           cellpadding="1"
                           cellspacing="1" export="false" class="layui-table" pagesize="10">
            <display:column title="${checkAll}" media="html" style="width:3%;">
            <input type="checkbox" class="pk" name="id" value="${PrivateDataItem.ddDataId}">
            </display:column>
                <display:column property="ddDataName" title="数据名称" sortable="true" sortName="DD_DATA_NAME"
                                maxLength="80"></display:column>
                <display:column property="ddDataType" title="数据类型" maxLength="80"></display:column>
                <display:column property="ddDataSubmiteState" title="数据订阅状态" maxLength="80"></display:column>
            <display:column title="操作" media="html" style="width:260px">
            <a href="edit.ht?id=${PrivateDataItem.ddDataId}" class="layui-btn layui-btn-normal layui-btn-small">编辑</a>
            <a href="del.ht?id=${PrivateDataItem.ddDataId}" class="layui-btn layui-btn-normal layui-btn-small">删除</a>
            </display:column>
            </display:table>
    </div>
</div>
</body>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</html>


