<%--
  Created by IntelliJ IDEA.
  User: dd
  Date: 2018/10/28
  Time: 5:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN" style="height: 100%;">
<head>
    <title>编辑任务</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <!-- include the style of alertify-->
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/alertify.min.css" />
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/themes/default.min.css" />
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/styles/select/bootstrap-select.min.css" rel="stylesheet" type="text/css"/>

    <script src="${ctx}/newtable/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/newtable/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <!-- include the script alertify-->
    <script src="${ctx}/js/alertifyjs/alertify.min.js"></script>
    <style>
        .panel {
            margin: 0px;
            height: 100%;
        }

        #card1, #card2, #card3 {
            padding-right: 2px;
            padding-left: 2px;
        }

        #row {
            margin: 5px !important;
        }

        .detail-info {
            margin-bottom: 4px;
        }

        .task-info-title {
            margin: 5px 16px 10px;
            line-height: 12px;
            color: grey;
        }

        .panel .card {
            background: #FFFFFF;
        }

        .card .task-detail-executor {
            margin: 10px 16px 14px;
            line-height: 24px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .editable-pre-wrapped {
            white-space: normal !important;
        }

        .activities-list {
            padding: 0;
            position: relative;
        }

        .activity {
            position: relative;
            overflow: hidden;
            margin: 15px;
        }

        .activity .activity-type-icon {
            width: 36px;
            text-align: center;
            font-size: 16px;
        }

        .activity .activity-body-coyness {
            font-size: 12px;
            margin: 0 0 0 46px;
        }

        .muted {
            color: #A6A6A6;
        }

        .editable-input{
            width: 100%;
            margin: 5px;
        }
    </style>
</head>
<body style="height: 100%; margin: 0px;">
<div class="panel panel-default">
    <div class="panel-heading">
        任务编辑
        ${TaskInfo.ddTaskName}
        <div class="btn-group pull-right">
            <button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                更多 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-menu-right">
                <li><a href="javascript:void(0)">
                    <center>任务菜单</center>
                </a></li>
                <li role="separator" class="divider"></li>
                <li><a href="javascript:void(0)"><span class="glyphicon glyphicon-download-alt"></span> 固化</a></li>
                <li>
                    <c:choose>
                        <c:when test="${TaskInfo.ddTaskChildType=='createpanel'}">
                            <a href="${ctx}/datadriver/task/del.ht?id=${TaskInfo.ddTaskId}"><span
                                    class="glyphicon glyphicon-trash"></span> 删除</a>
                        </c:when>
                        <c:when test="${TaskInfo.ddTaskChildType=='checkpanel'}">
                            <a id="reback" href="javascript:void(0)"><span
                                    class="glyphicon glyphicon-repeat"></span> 退回</a>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>
    </div>
    <div class="panel-body" style="overflow: auto">
        <form class="form-horizontal">
            <div class="form-group">
                <label for="taskName" class="col-sm-2 control-label">任务名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="taskName" placeholder="任务名称" value="${TaskInfo.ddTaskName}">
                </div>
            </div>
            <div class="form-group">
                <label for="select-first" class="col-sm-2 control-label">负责人</label>
                <div class="col-sm-10">
                    <select class="selectpicker show-tick form-control"
                            noneResultsText="无匹配项"
                            data-live-search="true" id="select-first" title="请选择组织"
                            data-getDataUrl="${ctx}/platform/system/sysOrg/users.ht?projectId=${projectItem.ddProjectId}" validate="{required:true}">
                        <c:forEach var="orgItem" items="${sysOrgList}">
                            <option value="${orgItem.orgId}"
                                    <c:if test="${TaskInfo.userOrg == '${orgItem.orgName}'}">selected="selected"</c:if>>${orgItem.orgName}</option>
                        </c:forEach>
                    </select>
                    <select name="ddTaskResponsiblePerson" class="selectpicker show-tick form-control"
                            noneResultsText="无匹配项"
                            data-live-search="true" id="select-second" title="请选择人员" validate="{required:true}">
                        <%--<c:forEach var="personItem" items="${sysUserList}">--%>
                        <%--<option value="${personItem.userId}"--%>
                        <%--<c:if test="${TaskInfo.ddTaskPerson == '${personItem.fullname}'}">selected="selected"</c:if>>${personItem.fullname}</option>--%>
                        <%--</c:forEach>--%>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="select-first" class="col-sm-2 control-label">计划开始时间</label>
                <div class="col-sm-4">

                </div>
                <label for="select-first" class="col-sm-2 control-label">计划结束时间</label>
                <div class="col-sm-4">

                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Sign in</button>
                </div>
            </div>
        </form>
        <div class="row">
            <div class="col-xs-3" id="card4">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            开始时间
                        </h5>
                        <a class="task-detail-executor" href="javascript:void(0)" id="planstar" data-type="combodate" data-pk="1"
                           data-value="${startime}">
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-3" id="card2">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            截止时间
                        </h5>
                        <a class="task-detail-executor" href="javascript:void(0)" id="dob" data-type="combodate" data-pk="1"
                           data-value="${endtime}">
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-3" id="card3">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            优先级
                        </h5>
                        <a class="task-detail-executor" id="taskPrioritySelect" data-type="select" data-pk="1"
                           href="javascript:void(0)">

                            <c:choose>
                                <c:when test="${TaskInfo.ddTaskPriority==3}"><span
                                        class="label label-danger">紧急</span></c:when>
                                <c:when test="${TaskInfo.ddTaskPriority==2}"><span
                                        class="label label-warning">重要</span></c:when>
                                <c:otherwise><span class="label label-primary">一般</span></c:otherwise>
                            </c:choose>
                            点击设置
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel-footer" style="position: relative; width: 100%; bottom: 1px;">
        <%--<div class="col-xs-6">--%>
        <%--<button class="btn btn-primary btn-block" id="taskEdit" onclick="onChangeTaskInfo()" >保存信息</button>--%>
        <%--</div>--%>
        <div class="input-group">
            <input type="text" class="form-control" placeholder="输入@选择提醒人员" disabled="disabled" title="暂不可用">
            <span class="input-group-btn">
                <button class="btn btn-success" type="button" disabled="disabled">发送</button>
            </span>
        </div><!-- /input-group -->
    </div>
</div>

</body>

<script>
    //@ sourceURL=edit.js
    var curRow = {};
    var taskItem = 0;
    var ddTaskMilestone1 = $('#ddTaskMilestone1');
    var ddTaskMilestone0 = $('#ddTaskMilestone0');
    var reback = document.getElementById('reback');
    $("#personSelect").change(function () {
        var taskPerson = $("#personSelect").find("option:selected").text();
        $("#ddTaskPerson").val(taskPerson);
    });
    $(function () {
        $(".selectpicker").selectpicker();

        //任务截至时间选择
        $('.input-group.date').datepicker({
            weekStart: 0,
            autoclose: true,
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            toggleActive: true,
            todayHighlight: true,
            todayBtn: true
        }).on('changeDate', function () {
            var endTime = $('#ddTaskPlanEndTime').val();
            $.ajax({
                type: 'post',
                url: "${ctx}/datadriver/task/onchangetaskinfo.ht?taskId=${TaskInfo.ddTaskId}&eventId=2",
                data: {strJson: endTime},
                success: function (data) {
                }
            });
        });


        //任务负责人变更
        $('#manager').on('changed.bs.select', function (e) {
            var managerId = $('.selectpicker, #manager').val();
            $.ajax({
                type: 'post',
                url: "${ctx}/datadriver/task/onchangetaskinfo.ht?taskId=${TaskInfo.ddTaskId}&eventId=0",
                data: {strJson: managerId},
                success: function (data) {
                    alertify.set('notifier','position', 'top-right');
                    alertify.success("设置成功！");
                },
                error: function () {
                    alertify.set('notifier','position', 'top-right');
                    alertify.error("设置失败！");
                }
            });
        });

        //优先级变更
        $('#taskPriority').on('changed.bs.select', function (e) {
            var taskPriority = $('#taskPriority').val();
            $.ajax({
                type: 'post',
                url: "${ctx}/datadriver/task/onchangetaskinfo.ht?taskId=${TaskInfo.ddTaskId}&eventId=1",
                data: {strJson: taskPriority},
                success: function (data) {

                }
            });
        });

        ddTaskMilestone1.onclick = function () {
            <%--$.get("${ctx}/datadriver/index/readonly.ht?id=${TaskInfo.ddTaskProjectId}", function (data) {--%>
            <%--$('#index').html(data);--%>
            <%--});--%>
        }
        ddTaskMilestone0.onclick = function () {
            <%--$.get("${ctx}/datadriver/index/readonly.ht?id=${TaskInfo.ddTaskProjectId}", function (data) {--%>
            <%--$('#index').html(data);--%>
            <%--});--%>
        }

        if (document.getElementById("reback")) {
            reback.onclick = function () {
                $.get("${ctx}/datadriver/personaltask/recovertask.ht?id=${TaskInfo.ddTaskId}", function (data) {
                    window.location.href = 'stepinto.ht?id=${TaskInfo.ddTaskProjectId}'
                });
            }
        }

    });
    function onChangeTaskInfo(params, taskItem) {

        curRow[taskItem] = params.value;
        $.ajax({
            type: 'post',
            url: "${ctx}/datadriver/task/onchangetaskinfo.ht?taskId=${TaskInfo.ddTaskId}&strJson="+params.value+"&eventId="+taskItem,
            success: function (data) {
                alertify.set('notifier','position', 'top-right');
                alertify.success("设置成功！");
            },
            error: function () {
                alertify.set('notifier','position', 'top-right');
                alertify.error("设置失败！");
            }
        });
    }
    $(function () {
        $('#executorSelect').editable({
            showbuttons: false,
            value: ${executorName.userId},
            placement: 'right',
            mode: 'inline',
            onblur: 'submit',
            source: [
                <c:forEach var="personItem" items="${sysUserList}">
                {value: ${personItem.userId}, text: '${personItem.fullname}'},
                <%--source: [--%>
                <%--<c:forEach var="personItem" items="${sysUserList}">--%>
                <%--{value: ${personItem.userId}, text: '${personItem.fullname}'},--%>

                <%--</c:forEach>--%>
//        ],
                </c:forEach>
            ],

            url: function (params) {
                taskItem = 0;
                onChangeTaskInfo(params, taskItem);
            }
        });
        $('#taskPrioritySelect').editable({
            showbuttons: false,
            value: ${TaskInfo.ddTaskPriority}, // 默认显示当前优先级
            mode: 'inline',
            onblur: 'submit',
            source: [
                {value: 3, text: '紧急'},
                {value: 2, text: '重要'},
                {value: 1, text: '一般'}
            ],
            url: function (params) {
                taskItem = 1;
                onChangeTaskInfo(params, taskItem);
            }
        });

        $('#dob').editable({
            showbuttons: true,
            mode: 'popup',
            onblur: 'submit',
            placement: 'bottom',
            format: 'YYYY-MM-DD',
            viewformat: 'YYYY年 MM月 DD日',
            template: 'YYYY年 MM月 DD日',
            showbuttons: false,
            combodate: {
                minYear: 2000,
                maxYear: 2030,
                minuteStep: 1
            },
            url: function (params) {
                taskItem = 2;
                onChangeTaskInfo(params, taskItem);
            }
        });
        $('#planstar').editable({
            showbuttons: true,
            mode: 'popup',
            onblur: 'submit',
            placement: 'bottom',
            format: 'YYYY-MM-DD',
            viewformat: 'YYYY年 MM月 DD日',
            template: 'YYYY年 MM月 DD日',
            showbuttons: false,
            combodate: {
                minYear: 2000,
                maxYear: 2030,
                minuteStep: 1
            },
            url: function (params) {
                taskItem = 4;
                onChangeTaskInfo(params, taskItem);
            }
        });
        ddTaskMilestone1.onclick = function () {
            <%--$.get("${ctx}/datadriver/index/readonly.ht?id=${TaskInfo.ddTaskProjectId}", function (data) {--%>
            <%--$('#index').html(data);--%>
            <%--});--%>
        }
        ddTaskMilestone0.onclick = function () {
            <%--$.get("${ctx}/datadriver/index/readonly.ht?id=${TaskInfo.ddTaskProjectId}", function (data) {--%>
            <%--$('#index').html(data);--%>
            <%--});--%>
        }
        $('#comments').editable({
            showbuttons: false,
            placement: 'bottom',
            mode: 'inline',
            onblur: 'submit',
            inputclass: 'editable-input',
            rows: 5,
            url: function (params) {
                taskItem = 3;
                onChangeTaskInfo(params, taskItem);
            }
        });
        if (document.getElementById("reback")){
            reback.onclick = function () {
                $.get("${ctx}/datadriver/personaltask/recovertask.ht?id=${TaskInfo.ddTaskId}", function (data) {
                    window.location.href = 'stepinto.ht?id=${TaskInfo.ddTaskProjectId}'
                });
            }
        }

    })
    ;
</script>
</html>