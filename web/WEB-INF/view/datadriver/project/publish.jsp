<%--
  Created by IntelliJ IDEA.
  User: holly
  Date: 2018/12/25
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <%@include file="/newtable/tablecontext.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <title>发布数据审核</title>
</head>
<body>
<div id="publish_check">
    <table id="table_publish"></table>
</div>
<script type="text/javascript">
    $(function () {
        initTable();
    });

    var $table_order = $('#table_publish');
    var curRow = {};

    function initTable() {
        $table_order.bootstrapTable({
            showHeader: true,
            dataType: "json",
            idField: "dataId",
            url: "${ctx}/datadriver/project/publishData.ht?taskId=${taskId}",
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
            uniqueId: "dataId",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            columns: [
                {
                    field: 'dataId',
                    title: '数据ID',
                    sortable: false,
                    align: 'left',
                    visible: false
                }, {
                    field: 'dataName',
                    title: '数据名称',
                    sortable: false,
                    align: 'left',
                    visible: true
                }, {
                    field: 'dataValue',
                    title: '数值',
                    width: 160,
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'dataSenMin',
                    title: '最小值',
                    width: 160,
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'dataSenMax',
                    title: '最大值',
                    width: 160,
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: 220,
                    events: operateEvents,
                    formatter: operateData
                }
            ],
            onClickRow: function (row, $element) {
                curRow = row;
            }
        });
    }
    //操作
    function operateData(value, row, index) {
        return [
            '<a id="feedback" href="javascript:void(0)" class="btn btn-success btn-xs" title="点击进行反馈"><span class="glyphicon glyphicon-log-in"></span> 反馈',
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
</script>
</body>
</html>
