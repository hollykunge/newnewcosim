<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2016/12/25
  Time: 下午9:57
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
<html lang="zh-CN">
<head>
    <title>办理任务</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/slide/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/slide/css/component.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/newtable/bootstrap.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/styles/wizard/bootstro.min.css"/>
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.base.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.bootstrap.css" type="text/css"/>

    <script src="${ctx}/newtable/jquery.js"></script>
    <script src="${ctx}/newtable/bootstrap.js"></script>

    <script type="text/javascript" src="${ctx}/jqwidgets/jqx-all.js"></script>

    <script type="text/javascript" src="${ctx}/timeselect/bootstrap-datetimepicker.min.js"></script>

    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.ext.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>

    <script type="text/javascript" src="${ctx}/styles/wizard/bootstro.min.js"></script>
    <script type="text/javascript" src="${ctx}/cookie/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/styles/wizard/bootwizard.js"></script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-9">
            <ol class="breadcrumb">
                <li><a href="list.ht"><span class="glyphicon glyphicon-book"></span> ${TaskInfo.ddTaskProjectName}</a>
                </li>
                <li class="active">${TaskInfo.ddTaskName}</li>
            </ol>
        </div>
        <div class="col-xs-3">
            <div class="pull-right">
                <a class="btn btn-success" href="javascript:void(0)" id="submit_btn"><span
                        class="glyphicon glyphicon-ok"></span> 提交审核</a>
                <%--<a id="statis_btn" href="javascript:void(0)" class="btn btn-default"><span--%>
                        <%--class="glyphicon glyphicon-stats"></span>--%>
                    <%--流程监控--%>
                <%--</a>--%>
                <%--<a id="data_btn" href="http://10.12.99.118:8080/octagon/?username=realrec@neusoft.com"--%>
                   <%--class="btn btn-default" target="_blank"><span--%>
                        <%--class="glyphicon glyphicon-stats" 　></span>--%>
                    <%--数据挖掘--%>
                <%--</a>--%>

            </div>
        </div>
    </div>
    <div class="row">
        <ul class="nav nav-tabs" role="tablist" id="myTab">
            <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">
                    ${TaskInfo.ddTaskName} <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" style="overflow: auto">
                    <c:forEach var="taskInfoListItem" items="${taskInfoList}">
                        <li>
                            <a href="todotask.ht?id=${taskInfoListItem.ddTaskId}&projectId=${taskInfoListItem.ddTaskProjectId}">${taskInfoListItem.ddTaskName}
                                <small> (${taskInfoListItem.ddTaskProjectName})</small>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
            <li role="presentation" class="active reponav-item" id="switch_attr_publish"><a href="#data"
                                                                                            data-toggle="tab"
                                                                                            role="tab"><span
                    class="glyphicon glyphicon-log-out"></span> 输出数据</a>
            </li>
            <li role="presentation" class="reponav-item" id="switch_attr_order"><a href="#publish" data-toggle="tab"
                                                                                   role="tab"><span
                    class="glyphicon glyphicon-log-in"></span> 输入数据</a>
            </li>

            <li role="presentation" class="reponav-item" id="switch_attr_index"><a href="#index" data-toggle="tab"
                                                                                   role="tab"><span
                    class="glyphicon glyphicon-compressed"></span> 任务约束</a>
            </li>
        </ul>
    </div>


    <div class="tab-content board-view">
        <div role="tabpanel" class="tab-pane active board-scrum-view" id="data" style="height: 100%">
        </div>
        <div role="tabpanel" class="tab-pane board-scrum-view" id="index" style="height: 100%">
        </div>
        <div role="tabpanel" class="tab-pane board-scrum-view" id="publish" style="height: 100%">
        </div>

        <%--<div role="tabpanel" class="tab-pane board-scrum-view" id="talk" style="height: 100%">--%>
        <%--</div>--%>
        <%--<div role="tabpanel" class="tab-pane board-scrum-view" id="source" style="height: 100%">--%>
        <%--</div>--%>
        <%--<div role="tabpanel" class="tab-pane board-scrum-view" id="child" style="height: 100%">--%>
        <%--</div>--%>
    </div>

    <%--统计--%>
    <div class="modal fade" id="statis" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">

            </div>
        </div>
    </div>

    <%--文件上传--%>
    <div class="modal fade" id="fileupload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="overflow: auto" id="fileuploadcontent" style="overflow: auto">

            </div>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/styles/slide/js/classie.js"></script>
<%--<script src="${ctx}/styles/loading/PerfectLoad.js"></script>--%>
<script type="text/javascript">
    //@ sourceURL=todotask.ht
    $(document).ready(function () {
//        var isbstro = $.cookie('bootstro');
//        if (!isbstro) {
//            wizard.bs.bstro([
//                ['#switch_attr_publish', '<strong style="margin-top:10px;">该tab页负责私有数据创建、展示、发布、管理，以及发布数据的展示、撤销。</strong>'],
//                ['#switch_attr_order', {
//                    content: '<strong style="margin-top:10px;">该tab页负责项目中所有可订阅数据的展示、订阅，以及已订阅数据的管理、展示、撤销订阅。</strong>',
//                    place: 'right'
//                }],
//            ], {
//                obtn: '下次不再提示',
//                exit:function () {
//                    $.cookie('bootstro', 'ok', {expires: 30, path: '/'});
//                }
//            });
//        }

        $.get("showdata.ht?id=${TaskInfo.ddTaskId}&projectId=${TaskInfo.ddTaskProjectId}", function (data) {
            $('#data').html(data);
        });

//        $("#child _btn").hide();

    });

    var switch_attr_index = document.getElementById('switch_attr_index'),
        switch_attr_data = document.getElementById('switch_attr_data'),
        switch_attr_publish = document.getElementById('switch_attr_publish'),
        switch_attr_order = document.getElementById('switch_attr_order'),
        // statis_btn = document.getElementById('statis_btn'),
        create_task = document.getElementById('create_task'),

        switch_attr_child = document.getElementById('switch_attr_child'),
        submit_btn = document.getElementById('submit_btn'),
        switch_attr_talk = document.getElementById('switch_attr_talk');


    $("#statis").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    submit_btn.onclick = function () {
        $.get("submittask.ht?id=${TaskInfo.ddTaskId}", function (data) {
            window.location.href = 'list.ht';
        });
    }
    //tab切换操作
    switch_attr_publish.onclick = function () {
        $.get("showdata.ht?id=${TaskInfo.ddTaskId}&projectId=${TaskInfo.ddTaskProjectId}", function (data) {
            $('#data').html(data);
        });
    }
    switch_attr_index.onclick = function () {
        $.get("${ctx}/datadriver/index/readonly.ht?id=${TaskInfo.ddTaskProjectId}", function (data) {
            $('#index').html(data);
        });
    }
    switch_attr_order.onclick = function () {
        $.get("showorder.ht?id=${TaskInfo.ddTaskId}&projectId=${TaskInfo.ddTaskProjectId}", function (data) {
            $('#publish').html(data);
        });
    }
    //
    <%--statis_btn.onclick = function () {--%>
        <%--$('#statis').modal({--%>
            <%--keyboard: true,--%>
            <%--remote: "statis.ht?id=${TaskInfo.ddTaskId}"--%>
        <%--});--%>
    <%--}--%>
</script>
</html>
