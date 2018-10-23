<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/2/7
  Time: 上午10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <style>
        iframe{
            width: 100% !important;
            height: 600px !important;
            border: none !important;
        }
    </style>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>

        <h4 class="modal-title" id="myModalLabel">项目统计</h4>
</div>
<div class="modal-body">
    <strong style="color: #bf3014">发布</strong>
    <strong style="color: #00B83F">审核</strong>
    <strong style="color: #0052CC">完成</strong>
    <iframe src="${ctx}/datadriver/designflow/projectflow.ht?flag=1&projectId=${projectId}&processFlowXml=${processFlowXml}" style="width: 900px;height: 600px;" scrolling="no" frameborder="no"></iframe>
</div>
</body>
</html>
