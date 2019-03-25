<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/1/19
  Time: 上午10:51
  To change this template use File | Settings | File Templates.
--%>
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
    <%--<script type="text/javascript" src="${ctx}/jqwidgets/jqx-all.js"></script>--%>
    <title>进入任务页面</title>

</head>

<body style="height: 100%;">
<div class="row" id="toolbar_center"></div>
<%--<div id="dataVersionList">
    <table id="data_center_table"></table>
</div>--%>
<table id="data_center_table"></table>
</body>
<script type="text/javascript">
    //设置table高度
    function getHeight() {
        return $(window).height() - $('.panel-heading').outerHeight(true) - 80;
    }
    var $data_center_table = $('#data_center_table');
    var curRow = {};
    function initTable() {
        $data_center_table.bootstrapTable({
            showHeader: true,
            url: "${ctx}/datadriver/project/getVersionList.ht?taskId=${taskId}",
            dataType: "json",
            idField: "ddTaskVerId",
//            classes: "table table-hover table-striped",
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar_center',                //工具按钮用哪个容器
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
            uniqueId: "ddTaskVerId",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
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
                    sortable: false,
                    align: 'left',
                    visible: true
                }, {
                    field: 'ddTaskVerId',
                    title: '任务版本ID',
                    sortable: true,
                    align: 'left',
                    visible: false
                }
                , {
                    field: 'ddVersionDescription',
                    title: '版本说明',
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'ddVersionId',
                    title: '版本ID',
                    sortable: true,
                    align: 'left',
                    visible: false
                }, {
                    field: 'ddVersionNum',
                    title: '版本号',
                    sortable: true,
                    align: 'left',
                    visible: true
                }, {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: operateTaskVersion
                }
            ],
            onClickRow: function (row, $element) {
                curRow = row;
            }
        });
    }

    //操作
    function operateTaskVersion(value, row, index) {
        return [
            '<a id="getVersionList" href="javascript:void(0)" class="btn btn-success btn-xs" title="点击查看任务数据版本列表"><span class="glyphicon glyphicon-list-alt"></span> 查看版本',
            '</a>'
        ].join('');
    }
    window.operateEvents = {
        'click #getVersionList': function (e, value, row, index) {
            $.get("${ctx}/datadriver/project/taskactivity.ht?versionId=" + row.ddTaskVerId, function (data) {
                // $('#dataVersionList').html(data);
                $('#act').html(data);
            })
        }
    };
    $(function () {
        initTable();
    });
</script>
</html>
