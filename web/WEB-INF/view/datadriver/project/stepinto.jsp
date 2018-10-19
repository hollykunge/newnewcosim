<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/1/19
  Time: 上午10:51
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN" style="height: 100%; margin: 0px">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <title>进入任务页面</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/slide/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/slide/css/component.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/newtable/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/check/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/check/build.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/fourpanel/fourpanel.css"/>
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.base.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.bootstrap.css" type="text/css"/>

    <!-- include the style of alertify-->
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/alertify.min.css" />
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/themes/default.min.css" />

    <script src="${ctx}/newtable/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/jqwidgets/jqx-all.js"></script>

    <%@include file="/newtable/tablecontext.jsp" %>
    <script type="text/javascript" src="${ctx}/styles/slide/js/modernizr.custom.js"></script>
    <%--<script type="text/javascript" src="${ctx}/styles/layui/jquery.dragsort-0.5.2.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.ext.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>

    <!-- include the script alertify-->
    <script src="${ctx}/js/alertifyjs/alertify.min.js"></script>
</head>
<body style="height: 100%">
<div class="container" style="height: 100%">
    <ol class="breadcrumb">
        <li><a href="list.ht"><span class="glyphicon glyphicon-th-list"></span> 项目管理</a></li>
        <li class="active">${Project.ddProjectName}</li>
    </ol>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">
                ${Project.ddProjectName} <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <c:forEach var="projectListbyUserItem" items="${projectListbyUser}">
                    <li>
                        <a href="stepinto.ht?id=${projectListbyUserItem.ddProjectId}">${projectListbyUserItem.ddProjectName}</a>
                    </li>
                </c:forEach>
            </ul>
        </li>
        <li role="presentation" class="active" id="switch_attr_task"><a href="#task" data-toggle="tab" role="tab"><span class="glyphicon glyphicon-bookmark"></span> 任务</a>
        </li>
        <li role="presentation" id="switch_attr_index"><a href="#indextab" data-toggle="tab" role="tab"><span class="glyphicon glyphicon-list-alt"></span> 指标</a></li>
        <%--<li role="presentation"><a href="#calendar" data-toggle="tab" role="tab" title="暂不可用">日程</a></li>--%>
        <div class="pull-right">
            <a id="statis_btn" class="btn btn-default" onclick="showStatis(${Project.ddProjectId})"><span
                    class="glyphicon glyphicon-stats"></span> 进度监控</a>
            <a class="btn btn-default" href="javascript:void(0)" id="create_task"
               onclick="createTask(${Project.ddProjectId})"><span class="glyphicon glyphicon-bookmark"></span> 创建任务</a>
            <a class="btn btn-default" href="javascript:void(0)" id="done"
               onclick="done(${Project.ddProjectId})"><span class="glyphicon glyphicon-ok"></span> 完成项目</a>
        </div>
    </ul>

    <br>
    <div class="tab-content board-view" style="height: 100%">
        <div role="tabpanel" class="tab-pane active board-scrum-view" id="task" style="height: 100%">
        </div>
        <div role="tabpanel" class="tab-pane board-scrum-view" id="indextab" style="height: 100%">
        </div>
        <%--<div role="tabpanel" class="tab-pane" id="calendar">--%>
        <%--</div>--%>
    </div>
</div>
<%--任务创建--%>
<div class="modal fade" id="addtask" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<div class="modal fade" id="done_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<%--统计--%>
<div class="modal fade" id="statis" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<%--任务详细--%>
<div class="modal fade" id="taskdetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="overflow: auto" id="taskdetailcontent">

        </div>
    </div>
</div>
</body>
<script src="${ctx}/styles/slide/js/classie.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.get("showtask.ht?id=${Project.ddProjectId}", function (data) {
            $('#task').html(data);
        });

        $("#create_task").show();
        $("#indexDataFormSave").hide();
        //关闭任务详情模态框
        $("#taskdetail").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
        });
    });

    var switch_attr_index = document.getElementById('switch_attr_index'),
        switch_attr_task = document.getElementById('switch_attr_task');
    //显示任务详情
    function showTaskContent(taskId) {
        $('#taskdetail').modal({
            keyboard: true,
            remote: "${ctx}/datadriver/task/edit.ht?id=" + taskId
        });
    }
    //创建任务
    function createTask(projectId) {
        $('#addtask').modal({
            keyboard: true,
            remote: "${ctx}/datadriver/task/addtask.ht?id=" + projectId
        });
    }
    //完成项目
    function done(projectId) {
        $.ajax({
            type: "get",
            url: "${ctx}/datadriver/project/done.ht?id=" + projectId,
            success: function (res) {
                var resJson = eval('('+res+')');
                if (resJson.result == 0) {
                    alertify.alert("提示信息", resJson.message).set('label', '确定');
                } else if (resJson.result == 1) {
                    alertify.alert("提示信息", resJson.message).set('label', '确定');
                }
            },
            error: function (err) {
                errJson = eval('('+err+')');
                alertify.alert("提示信息", errJson.message).set('label', '确定');
            }
        })
    }
    //监控信息
    function showStatis(projectId) {
        $('#statis').modal({
            keyboard: true,
            remote: "statis.ht?id=" + projectId
        });
    }
    //关闭任务详情模态框
    $("#taskdetail").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
    //关闭任务详情模态框
    //    $("#addindex1").on("hidden.bs.modal", function() {
    //        $(this).removeData("bs.modal");
    //    });
    //关闭统计模态框
//    $("#statis").on("hidden.bs.modal", function () {
//        $(this).removeData("bs.modal");
//    });
    switch_attr_index.onclick = function () {
        $("#create_task").hide();
        $("#indexDataFormSave").show();
        $.get("${ctx}/datadriver/index/indexlist.ht?id=${Project.ddProjectId}", function (data) {
            $('#indextab').html(data);
        });
    };
    switch_attr_task.onclick = function () {
        $("#indexDataFormSave").hide();
        $("#create_task").show();
        $.get("showtask.ht?id=${Project.ddProjectId}", function (data) {
            $('#task').html(data);
        });
    };
</script>
</html>
