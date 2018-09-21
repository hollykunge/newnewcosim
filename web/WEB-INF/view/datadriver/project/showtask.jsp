<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/3/5
  Time: 下午3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>任务看板</title>
    <script type="text/javascript" src="${ctx}/styles/layui/jquery.dragsort-0.5.2.min.js"></script>
</head>
<body id="task_board">
<div class="row paneldocker">
    <div class="col-xs-3" style="height: 100%">
        <div class="panel panel-default task-panel">
            <div class="panel-heading">
                新创建
                <button type="button" class="btn btn-xs btn-success pull-right" id="OnePunchSend">
                    <span class="glyphicon glyphicon-send"></span> 全部发布
                </button>
            </div>
            <div class="panel-body panelheight" style="overflow-y:auto; overflow-x: hidden;">
                <ul id="createpanel" class="scrum-stage-tasks">
                    <c:forEach var="taskListbyUserItem" items="${taskListbyUser}">
                        <li class="task task-card ui-sortable-handle "
                            onclick="showTaskContent(${taskListbyUserItem.ddTaskId})">
                                ${taskListbyUserItem.ddTaskName}
                            <input value="${taskListbyUserItem.ddTaskId}" type="hidden">
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-3" style="height: 100%">
        <div class="panel panel-default task-panel">
            <div class="panel-heading">
                已发布
                <button type="button" class="btn btn-xs btn-default pull-right" id="OnePunchBack">
                    <span class="glyphicon glyphicon-arrow-left"></span> 全部收回
                </button>
            </div>
            <div class="panel-body panelheight" style="overflow-y:auto; overflow-x: hidden">
                <ul id="publishpanel" class="scrum-stage-tasks">
                    <c:forEach var="publishtaskListbyUserItem" items="${publishtaskListbyUser}">
                        <li class="task task-card ui-sortable-handle"
                            onclick="showTaskContent(${publishtaskListbyUserItem.ddTaskId})">
                                ${publishtaskListbyUserItem.ddTaskName}
                            <input value="${publishtaskListbyUserItem.ddTaskId}" type="hidden">
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-3" style="height: 100%">
        <div class="panel panel-default task-panel">
            <div class="panel-heading">
                待审核
            </div>
            <div class="panel-body panelheight" style="overflow-y:auto; overflow-x: hidden">
                <ul id="checkpanel" class="scrum-stage-tasks">
                    <c:forEach var="checkTaskInfoListItem" items="${checkTaskInfoList}">
                        <li class="task task-card ui-sortable-handle"
                            onclick="showTaskContent(${checkTaskInfoListItem.ddTaskId})">
                                ${checkTaskInfoListItem.ddTaskName}
                            <input value="${checkTaskInfoListItem.ddTaskId}" type="hidden">
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-xs-3" style="height: 100%">
        <div class="panel panel-default task-panel">
            <div class="panel-heading">
                已完成
            </div>
            <div class="panel-body panelheight" style="overflow-y:auto; overflow-x: hidden;">
                <ul id="completepanel" class="scrum-stage-tasks">
                    <c:forEach var="completeTaskInfoListItem" items="${completeTaskInfoList}">
                        <li class="task task-card ui-sortable-handle"
                            onclick="showTaskContent(${completeTaskInfoListItem.ddTaskId})">
                                ${completeTaskInfoListItem.ddTaskName}
                            <input value="${completeTaskInfoListItem.ddTaskId}" type="hidden">
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //@ sourceURL=showtask.ht
    function isEmptyValue(value) {
        var type;
        if (value == null || value == '') { // 等同于 value === undefined || value === null
            return true;
        }
        type = Object.prototype.toString.call(value).slice(8, -1);
        switch (type) {
            case 'String':
                return !$.trim(value);
            case 'Array':
                return !value.length;
            case 'Object':
                return $.isEmptyObject(value); // 普通对象使用 for...in 判断，有 key 即为 false
            default:
                return false; // 其他对象均视作非空
        }
    }
    ;
    $(document).ready(function () {
        $(".paneldocker").height($(window).height() - $('.nav-tabs').outerHeight(true)-100);
        $(".panelheight").height($('.task-panel').innerHeight()-55);
        $("#createpanel,#publishpanel").dragsort({
            itemSelector: "li",
            dragSelector: "li",
            dragBetween: true,
            dragEnd: saveOrder,
            placeHolderTemplate: '<li class="task task-card ui-sortable-handle dropdown-color"></li>'
        });

        $("#checkpanel,#completepanel").dragsort({
            itemSelector: "li",
            dragSelector: "li",
            dragBetween: true,
            dragEnd: saveOrder,
            placeHolderTemplate: '<li class="task task-card ui-sortable-handle dropdown-color"></li>'
        });

        function saveOrder() {
            var data = $(this).children('input').val();
            var parentid = $(this).parent().attr("id");
            $.post("movetask.ht?id=" + data + "&parent=" + parentid);
        }
    });
    //生成全部新建panel中li的list并转换成json发送
    $('#OnePunchSend').click(function (index) {

        var feedbackMap = new Object();
        var valueList = new Array();
        $("#createpanel>li").each(function () {
            //将input=hidden的值压入list
            var a = $(this).find("input").val();
            if (a != null && a != "" && a != undefined) {
                feedbackMap = a;
                valueList.push(feedbackMap);
            } else {
                alert('生成list异常');
            }
        });
        if (isEmptyValue(valueList)) {
            $(".paneldocker").prepend('<div class="alert alert-danger fade in"><button class="close" data-dismiss="alert"><span>&times;</span></button><p>列表为空不能进行相应的操作！</p></div>');
        } else {
            $.ajax({
                type: "post",
                url: "onepunchsend.ht?id=${Project.ddProjectId}&&parent=publishpanel",
                data: {strJson: JSON.stringify(valueList)},
                success: function (data, status) {
                    if (status == "success") {
                        $.get("showtask.ht?id=${Project.ddProjectId}", function (data) {
                            $('#task').html(data);
                        });
                    }
                },
                error: function () {
                },
                complete: function () {
                }
            });
        }
    });
    //生成全部发布panel中li的list并转换成json发送
    $('#OnePunchBack').click(function (index) {
        var feedbackMap = new Object();
        var valueList = new Array();
        $("#publishpanel>li").each(function () {
            //将input=hidden的值压入list
            var a = $(this).find("input").val();
            if (a != null && a != "" && a != undefined) {
                feedbackMap = a;
                valueList.push(feedbackMap);
            } else {
                alert('生成list异常');
            }
        });

        $.ajax({
            type: "post",
            url: "onepunchback.ht?id=${Project.ddProjectId}&&parent=createpanel",
            data: {strJsonBack: JSON.stringify(valueList)},
            success: function (data, status) {
                if (status == "success") {
                    $.get("showtask.ht?id=${Project.ddProjectId}", function (data) {
                        $('#task').html(data);
                    });
                } else {
                }
            },
            error: function () {
            },
            complete: function () {
            }
        });
    });
</script>
</html>
