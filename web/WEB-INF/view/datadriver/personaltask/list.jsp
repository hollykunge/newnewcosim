<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>个人任务</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/newtable/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/newtable/jquery.js"></script>
    <%@include file="/newtable/tablecontext.jsp" %>
    <style>
        html, body {
            height: 100%;
        }
    </style>
    <script>
        $(document).ready(function () {
            var isClient;
            try {
                isClient = JSInteraction.isme();
                $("#taskList").css({"width": "100%", "height": "100%", "margin-top": "12px"});
            }
            catch (e) {
                console.log(e)
            }
        });
    </script>
</head>
<body>
<div id="taskList" class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">个人任务</h3>
        </div>
        <div class="panel-body">
            <%--<div id="toolbar" class="btn-group">--%>
            <%--<button type="button" class="btn btn-default">新发布</button>--%>
            <%--<button type="button" class="btn btn-default">未完成</button>--%>
            <%--<button type="button" class="btn btn-default">待审核</button>--%>
            <%--<button type="button" class="btn btn-default">已完成</button>--%>
            <%--</div>--%>
            <table id="table_personal_task"></table>
        </div>
    </div>
</div>
</body>
<script>
    var $table_personal_task = $('#table_personal_task');
    var curRow = {};

    function initTable() {
        $table_personal_task.bootstrapTable({
            showHeader: true,
            dataType: "json",
            idField: "ddTaskId",
//            classes: "table table-hover table-striped",
            url: "taskList.ht",
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            queryParamsType: '',
            pageList: [5, 10, 20, 50],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            searchAlign: "right",
            searchOnEnterKey: true,
            showColumns: true,
            showToggle: true,
//            detailView: true,
            detailFormatter: true,
            showPaginationSwitch: true,
            showExport: true,                     //是否显示导出
            exportDataType: "basic",              //basic', 'all', 'selected'.
//            strictSearch: true,
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 5,             //当列数小于此值时，将隐藏内容列下拉框。
            clickToSelect: false,                //是否启用点击选中行
            height: getHeight(),                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ddTaskId",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            rowStyle: function (row, index) {
                var style = "";
                if (row.ddTaskPriority == 3) {
                    style = 'danger'
                }
                if (row.ddTaskPriority == 2) {
                    style = 'warning'
                }
                return {classes: style}
            },
            columns: [
                {
                    field: 'ddTaskId',
                    title: '任务ID',
                    sortable: false,
                    align: 'left',
                    visible: false
                }, {
                    field: 'ddTaskName',
                    title: '任务名称',
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'ddTaskProjectName',
                    title: '项目名称',
                    sortable: false,
                    align: 'left',
                    visible: true
                }
                , {
                    field: 'ddTaskSecretLevel',
                    title: '密级',
                    sortable: true,
                    align: 'left',
                    visible: true
                }
                , {
                    field: 'ddTaskPriority',
                    title: '优先级码',
                    sortable: true,
                    align: 'left',
                    visible: false
                }, {
                    field: 'priority',
                    title: '优先级',
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'state',
                    title: '任务状态',
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'ddTaskState',
                    title: '任务状态码',
                    sortable: true,
                    align: 'center',
                    visible: false
                }, {
                    field: 'operate',
                    width: 200,
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: operateTask
                }
            ],
            onClickRow: function (row, $element) {
                curRow = row;
            }
        });
    }

    //刷新列表
    function refresh(e) {
        if (e == 0) $table_personal_task.bootstrapTable('refresh')
    }

    //操作
    function operateTask(value, row, index) {
        if (row.ddTaskState == 1) {
            return [
                '<a id="todoTask" href="javascript:void(0)" class="btn btn-success btn-xs" title="点击执行当前任务"><span class="glyphicon glyphicon-edit"></span> 执行任务',
                '</a>'
//                ,' ',
//                '<a id="feedBack" href="javascript:void(0)" class="btn btn-default btn-xs" title="点击查看当前任务数据问题反馈"><span class="badge">0</span> 问题列表',
//                '</a>'
            ].join('');
        }
        if (row.ddTaskState == 2)
            return [
                '<a id="rebackTask" href="javascript:void(0)" class="btn btn-warning btn-xs"><span class="glyphicon glyphicon-repeat"></span> 收回</a>', ' ',
                '<a id="viewTask" href="javascript:void(0)" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"></span> 查看</a>'
            ].join('');
    }

    //设置table高度
    function getHeight() {
        return $(window).height() - $('.panel-heading').outerHeight(true) - 80;
    }

    window.operateEvents = {
        'click #todoTask': function (e, value, row, index) {
            window.location.href = "todotask.ht?id=" + row.ddTaskId + "&type=" + 1;
        },
        'click #rebackTask': function (e, value, row, index) {
            $.get("recovertask.ht?id=" + row.ddTaskId, function (data) {
                window.location.reload()
            });
        },
        'click #viewTask': function (e, value, row, index) {
            window.location.href = "todotask.ht?id=" + row.ddTaskId + "&type=" + 2;
        }
    };
    $(function () {
        initTable();
    });
</script>
</html>


