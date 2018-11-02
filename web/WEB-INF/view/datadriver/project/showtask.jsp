<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/3/5
  Time: 下午3:35
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>任务看板</title>
    <!-- include the style of alertify-->
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/alertify.min.css" />
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/themes/default.min.css" />
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.base.css" type="text/css" />

    <script type="text/javascript" src="${ctx}/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="${ctx}/jqwidgets/jqxsortable.js"></script>
    <script type="text/javascript" src="${ctx}/jqwidgets/jqxkanban.js"></script>
    <script type="text/javascript" src="${ctx}/jqwidgets/jqxdata.js"></script>
    <!-- include the script alertify-->
    <script src="${ctx}/js/alertifyjs/alertify.min.js"></script>
</head>
<body id="task_board">

<div id="kanban"></div>

</body>

<script type="text/javascript">
    //@ sourceURL=showtask.ht
    $(document).ready(function () {
        function getHeight() {
            return $(window).height() - $('.nav-tabs').outerHeight(true) - 58;
        }
        var source =
            {
                dataType: "json",
                dataFields: [
                    { name: "id", map: "taskId", type: "string" },
                    { name: "status", map: "state", type: "string" },
                    { name: "text", map: "taskName", type: "string" },
                    { name: "tags", map: "endTime",  type: "string" },
                    { name: "content", map: "master", type: "string"},
                    { name: "color", type: "string" }
                ],
                url: "${ctx}/datadriver/project/kanban.ht?projectId=${Project.ddProjectId}"
            };
        var dataAdapter = new $.jqx.dataAdapter(source);
        // 看板初始化
        $('#kanban').jqxKanban({
            template: "<div class='jqx-kanban-item' id=''>"
            + "<div class='jqx-kanban-item-color-status'></div>"
            + "<div style='display: none;' class='jqx-kanban-item-avatar'></div>"
            + "<div style='display: none' class='jqx-icon jqx-icon-close-white jqx-kanban-item-template-content jqx-kanban-template-icon'></div>"
            + "<div style='display: none' class='jqx-kanban-item-text'></div>"
            + "<div class='jqx-kanban-item-footer'></div>"
            + "</div>",
            width: "100%",
            height: getHeight(),
            source: dataAdapter,
            itemRenderer: function(element, item, resource)
            {
                $(element).find(".jqx-kanban-item-color-status").html(
                    "<span style='line-height: 23px; margin-left: 5px; color:white;'>" + item.text + "</span>"
                    +"<a style='float: right; margin: 4px 4px 0 0; color: white;'>详情</a>"
                );
                $(element).find(".jqx-kanban-item-footer").html(
                    "<span style='line-height: 23px;'>负责人：" + item.content + "</span>"
                    +"<br>"
                    +"<span style='line-height: 23px;'>截止日期：" + item.tags + "</span>"
                );
            },
            columns: [
                { text: "新创建", collapsible: false, dataField: "createpanel" },
                { text: "已发布", collapsible: false, dataField: "publishpanel" },
                { text: "待审核", collapsible: false, dataField: "checkpanel" },
                { text: "已完成", collapsible: false, dataField: "completepanel" }
            ],
            columnRenderer: function (element, collapsedElement, column) {
                if (column.dataField == "createpanel") {
                    element.find(".jqx-kanban-column-header-status")
                        .html("<button type='button' class='btn btn-xs btn-success pull-right' style='margin-top: 4px;'onclick='OnePunchSend()'>\n" +
                        "<span class='glyphicon glyphicon-send'></span> 全部发布\n" +
                        "</button>");
                } else if (column.dataField == "publishpanel") {
                    element.find(".jqx-kanban-column-header-status")
                        .html("<button type='button' class='btn btn-xs btn-success pull-right' style='margin-top: 4px;' onclick='OnePunchBack()'>\n" +
                            "<span class='glyphicon glyphicon-repeat'></span> 全部收回\n" +
                            "</button>");
                }
            }
        });
        // 查看任务详情
        $('#kanban').on('itemAttrClicked', function (event) {
            var args = event.args;
            var taskId = args.itemId;
            showTaskContent(taskId);
        });
        //修改任务状态
        $('#kanban').on('itemReceived', function (event) {
            var args = event.args;
            var taskId = args.itemId;
            var oldTaskChildType = args.oldColumn.dataField;
            var newTaskChildType = args.newColumn.dataField;
            if (oldTaskChildType == newTaskChildType) return; // 在同一个面板内移动不处理
            if (oldTaskChildType == "createpanel" && newTaskChildType == "publishpanel") {
                moveTask(taskId, newTaskChildType, "任务发布成功！");
            } else if (oldTaskChildType == "publishpanel" && newTaskChildType == "createpanel") {
                moveTask(taskId, newTaskChildType, "任务收回成功！");
            } else if (oldTaskChildType == "checkpanel" && newTaskChildType == "completepanel") {
                moveTask(taskId, newTaskChildType, "任务审核完成！");
            } else if (oldTaskChildType == "completepanel" && newTaskChildType == "checkpanel") {
                moveTask(taskId, newTaskChildType, "任务驳回成功！");
            } else {
                refreshKanban();
                alertify.set('notifier','position', 'top-right');
                alertify.error("操作不合规定！");
            }
        });
    });

    // 刷新看板
    function refreshKanban() {
        $.get("showtask.ht?id=${Project.ddProjectId}", function (data) {
            $('#task').html(data);
        });
    }

    // 移动任务请求服务的方法
    function moveTask(taskId, newTaskChildType, msg) {
        $.ajax({
            type: "post",
            url: "movetask.ht?id=" + taskId + "&parent=" + newTaskChildType,
            success: function (data, status) {
                if (status == "success") {
                    alertify.set('notifier','position', 'top-right');
                    alertify.success(data);
                } else {
                    alertify.set('notifier','position', 'top-right');
                    alertify.error("操作失败！");
                    refreshKanban();
                }
            }
        });
    }

    // 一键发布全部任务
    function OnePunchSend() {
        var items = $('#kanban').jqxKanban('getColumnItems', 'createpanel');
        if (items.length == 0) {
            alertify.set('notifier','position', 'top-right');
            alertify.warning("当前没有任务可以发布！");
        } else {
            var valueList = new Array();
            for (var i = 0; i < items.length; i++) {
                valueList.push(items[i].id);
            }
            $.ajax({
                type: "post",
                url: "onepunchsend.ht?projectId=${Project.ddProjectId}&&parent=publishpanel",
                data: {strJson: JSON.stringify(valueList)},
                success: function (data, status) {
                    if (status == "success") {
                        refreshKanban();
                        alertify.set('notifier','position', 'top-right');
                        alertify.success("发布成功！");
                    } else {
                        alertify.set('notifier','position', 'top-right');
                        alertify.error("操作失败！");
                    }
                }
            });
        }
    }

    // 一键收回已发布的任务
    function OnePunchBack() {
        var items = $('#kanban').jqxKanban('getColumnItems', 'publishpanel');
        if (items.length == 0) {
            alertify.set('notifier','position', 'top-right');
            alertify.warning("当前没有任务可以收回！");
        } else {
            var valueList = new Array();
            for (var i = 0; i < items.length; i++) {
                valueList.push(items[i].id);
            }
            $.ajax({
                type: "post",
                url: "onepunchback.ht?projectId=${Project.ddProjectId}&&parent=createpanel",
                data: {strJsonBack: JSON.stringify(valueList)},
                success: function (data, status) {
                    if (status == "success") {
                        refreshKanban();
                        alertify.set('notifier','position', 'top-right');
                        alertify.success("收回成功！");
                    } else {
                        alertify.set('notifier','position', 'top-right');
                        alertify.error("操作失败！");
                    }
                }
            });
        }
    }
</script>

<style>
    .jqx-kanban-item-color-status {
        width: 100%;
        height: 25px;
        border-top-left-radius: 3px;
        border-top-right-radius: 3px;
        position:relative;
        margin-top:0px;
        top: 0px;
    }
    .jqx-kanban-item {
        padding-top: 0px;
    }
    .jqx-kanban-item-text {
        padding-top: 6px;
    }
    .jqx-kanban-item-avatar {
        top: 9px;
    }
    .jqx-kanban-template-icon {
        position: absolute;
        right: 3px;
        top:12px;
    }
</style>
</html>
