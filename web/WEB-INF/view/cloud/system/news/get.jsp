<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/cloud/global.jsp" %>
<html lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>协同设计-帮助</title>
<head>
    <%@include file="/commons/datadriver/getbase.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <%@include file="/newtable/tablecontext.jsp" %>
    <style>
        body {
            min-height: 800px;
            padding-top: 70px;
        }
    </style>
</head>
<body>
<div class="container">
    <%@include file="/commons/cloud/top_console.jsp" %>
    <div class="panel panel-default">
        <div class="panel-heading">
            帮助详情
            <a class="pull-right" id="reback" href="more.ht">
                <span class="glyphicon glyphicon-new-window"></span> 返回列表
            </a>
        </div>
        <div class="panel-body">
            <h3>${news.title}</h3>
            ${news.content}
        </div>
    </div>
</div>
</body>
</html>

