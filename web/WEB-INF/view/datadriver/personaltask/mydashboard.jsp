<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/cloud/global.jsp" %>
<html lang="zh">
<head>
    <title>协同设计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/newtable/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/newtable/jquery.js"></script>
    <script src="${ctx}/newtable/bootstrap.js"></script>
    <style>
        body {
            min-height: 2000px;
        }

        .project-card {
            padding-right: 16px !important;
            padding-left: 16px !important;
            display: flex;
            flex-direction: column;
            margin-bottom: 24px !important;
            box-sizing: border-box;
            display: list-item;
            text-align: -webkit-match-parent;
        }

        .project-card-inner {
            cursor: pointer;
            flex: 1;
            position: relative;
            display: block;
            height: 100%;
            padding-top: 24px;
            font-size: 14px;
            color: #555;
            border: 1px solid rgba(0, 0, 0, 0.075);
            border-bottom-color: rgba(0, 0, 0, 0.125);
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 5px 10px rgba(0, 0, 0, 0.05);
            transition: border-color 0.1s ease-in-out, box-shadow 0.1s ease-in-out;
            padding-bottom: 40px !important;
            padding-top: 32px !important;
            padding-right: 24px !important;
            padding-left: 24px !important;
            color: #0366d6;
            text-decoration: none !important;
            background-color: transparent;
        }

        .exploregrid {
            margin: 0;
            padding: 20px;
            list-style: none;
        }

        a.project-card-inner:hover, a.project-card-inner:active {
            background-color: #d9d9d9 !important;
        }

        .small-text {
            position: absolute;
            bottom: 16px;
            display: block;
            color: #586069;
        }

        .big-text {
            margin-top: 0;
            margin-bottom: 5px;
            font-size: 20px;
            font-weight: normal;
            line-height: 1.2;
            color: #24292e;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="panel panel-default" style="height: 100%">
        <div class="panel-heading"><strong>我的任务</strong>
        </div>
        <div class="panel-body" style="height: 100%">
            <ul class="exploregrid">
                <c:forEach var="taskItem" items="${taskInfos}">
                    <li class="project-card col-xs-3">
                        <a class="project-card-inner" href="todotask.ht?id=${taskItem.ddTaskId}">
                            <h4 class="big-text"><span
                                    class="glyphicon glyphicon-book"></span> ${taskItem.ddTaskName}</h4>
                            <p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                                <em>${taskItem.ddTaskProjectName}</em></p>
                            <p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                                <em>${taskItem.ddTaskPlanEndTime}</em></p>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>