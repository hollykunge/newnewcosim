<!DOCTYPE html>
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
    <h3>用户帮助中心</h3>
    <hr>
    <div id="toolbar_help">
        <a class="btn btn-success"><span class="glyphicon glyphicon-question-sign"></span>
        问题反馈</a>
    </div>

    <table id="table_help">
    </table>
</div>
</body>
<script>
    var $table_help = $('#table_help');
    var curRow = {};
    function initTable() {
        $table_help.bootstrapTable({
            showHeader: true,
            dataType: "json",
            idField: "helpId",
//            classes: "table table-hover table-striped",
            url: "moreDetail.ht",
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar_help',                //工具按钮用哪个容器
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
            uniqueId: "helpId",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            columns: [
                {
                    field: 'helpId',
                    title: '帮助ID',
                    sortable: false,
                    align: 'left',
                    visible: false
                },
                {
                    field: 'helpTitle',
                    title: '标题',
                    width: 700,
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'helpPublisher',
                    title: '发布人',
                    sortable: false,
                    align: 'left',
                    visible: true
                }, {
                    field: 'helpPublishTime',
                    title: '发布时间',
                    sortable: true,
                    align: 'left',
                    visible: true
                }
                ,
            ],
            onClickRow: function (row, $element) {
                curRow = row;
                location.href = "${ctx}/cloud/system/news/get.ht?id=" + row.helpId;
            }
        });
    }
    //刷新列表
    function refresh(e) {
        if (e == 0) $table_help.bootstrapTable('refresh')
    }

    //设置table高度
    function getHeight() {
        return $(window).height() - $('.panel-heading').outerHeight(true) - 80;
    }

    $(function () {
        initTable();
    });
</script>
</html>
