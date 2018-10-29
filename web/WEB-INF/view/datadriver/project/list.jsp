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
<html lang="zh-CN">
<head>
    <title>项目基础信息列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <%@include file="/newtable/tablecontext.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<style>
    .boxed-group-action {
        position: relative;
        z-index: 2;
        float: right;
        margin: -24px -10px 0 0;
    }
</style>
</head>
<body>
<div class="container">
    <%--<div class="row">--%>
        <%--<div id="toolbar" class="btn-group">--%>
            <%--<button type="button" class="btn btn-default">未发布</button>--%>
            <%--<button type="button" class="btn btn-default">已发布</button>--%>
            <%--<button type="button" class="btn btn-default">已完成</button>--%>
        <%--</div>--%>
    <%--</div>--%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">我的项目</h3>
            <div class="boxed-group-action">
                <a class="btn btn-sm btn-success" href="create.ht">
                    <span class="glyphicon glyphicon-book"></span> 新建项目</a>
            </div>
        </div>
        <div class="panel-body">
            <div id="toolbar">
                <div class="btn-group" data-toggle="buttons">
                    <label class="btn btn-default active">
                        <input type="radio" name="options" id="myCreated" autocomplete="off" checked> 我创建的
                    </label>
                    <label class="btn btn-default">
                        <input type="radio" name="options" id="myJoined" autocomplete="off"> 我参与的
                    </label>
                    <label class="btn btn-default">
                        <input type="radio" name="options" id="myDone" autocomplete="off"> 已完成
                    </label>
                </div>
            </div>
            <table id="table_project_task"></table>
        </div>
    </div>

</div>

<%--项目设置--%>
<div class="modal fade" id="myCreate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
        </div>
    </div>
</div>
</body>
<script>
    var $table_project_task = $('#table_project_task');
    var curRow = {};
    function initTable() {
        $table_project_task.bootstrapTable({
            showHeader: true,
            dataType: "json",
            idField: "projectId",
            url: "projectList.ht",
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
            uniqueId: "projectId",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            columns: [
                {
                    field: 'projectId',
                    title: '项目ID',
                    sortable: false,
                    align: 'left',
                    visible: false
                }, {
                    field: 'projectName',
                    title: '项目名称',
                    sortable: false,
                    align: 'left',
                    visible: true
                },  {
                    field: 'projectSecret' +
                    'Level',
                    title: '密级',
                    width: 160,
                    sortable: true,
                    align: 'left',
                    visible: true
                },  {
                    field: 'phase',
                    title: '项目阶段',
                    width: 160,
                    sortable: true,
                    align: 'left',
                    visible: true
                }
                , {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: 220,
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
        if (e == 0) $table_project_task.bootstrapTable('refresh')
    }
    //操作
    function operateTask(value, row, index) {
        return [
            '<a id="stepIntoProject" href="javascript:void(0)" class="btn btn-success btn-xs" title="点击进入项目"><span class="glyphicon glyphicon-log-in"></span> 进入',
            '</a>', ' ',
            '<a id="setupProject" href="javascript:void(0)" class="btn btn-default btn-xs" title="点击设置项目"><span class="glyphicon glyphicon-cog"></span> 设置',
            '</a>', ' ',
            '<a id="processFlow" href="javascript:void(0)" class="btn btn-default btn-xs" title="点击进入进行任务创建"><span class="glyphicon glyphicon-road"></span> 流程创建',
            '</a>'
        ].join('');
    }
    //设置table高度
    function getHeight() {
        return $(window).height() - $('.panel-heading').outerHeight(true) - 80;
    }
    window.operateEvents = {
        'click #stepIntoProject': function (e, value, row, index) {
            window.location.href = "stepinto.ht?id=" + row.projectId;
        },
        'click #setupProject': function (e, value, row, index) {
            $('#myCreate').modal({
                keyboard: true,
                remote: "setup.ht?id=" + row.projectId
            })
        },
        'click #processFlow': function (e, value, row, index) {
            window.location.href = "${ctx}/datadriver/designflow/flowframe.ht?id=" + row.projectId;
        }
    };
    $(function () {
        initTable();
        $("#myCreate").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
            refresh(0); // 当模态框关闭时，刷新项目列表
        });
        $("#myModal").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
        });
    });
</script>
</html>


