<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>数据版本</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <script type="text/javascript" src="${ctx}/jqwidgets/table/taskactivity.js"></script>
    <%--<script type="text/javascript" src="${ctx}/jqwidgets/jqx-all.js"></script>--%>

</head>
<body>
<div id="table_version_detail"></div>
</body>
<script type="text/javascript">
    $(function () {
        dataVersionTableInitInProject('${ctx}/datadriver/project/getListByVerId.ht?versionId=${versionId}', ${versionId})
    });
</script>
</html>
