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
    <title>项目树</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">
    <script type="text/javascript" src="${ctx}/newtable/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/newtable/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/newtable/bootstrap-table.js"></script>
    <script type="text/javascript" src="${ctx}/newtable/bootstrap-table-zh-CN.js"></script>

    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.base.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/jqwidgets/styles/jqx.bootstrap.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/displaytag.js"></script>

    <style type="text/css">
        html, body {
            padding: 0px;
            margin: 0;
            width: 100%;
            height: 100%;
            /*overflow: auto;*/
        }

        .panel-body {
            background-color: #FFFFFF !important;
        }
    </style>
    <script type="text/javascript">
        function getWidth() {
            return $('#treeBorder').innerWidth() - 11;
        }
        function getHeight() {
            return $('#treeBorder').innerHeight() - 27;
        }
        $(document).ready(function () {
            var dataJson = null;
            var source = new Object();
            $.ajax({
                async: false,
                url: "projectTree.ht",
                success: function (data, status, xhr) {
                    dataJson = jQuery.parseJSON(data);
                    source =
                        {
                            datatype: "json",
                            datafields: [
                                {name: 'id'},
                                {name: 'parentId'},
                                {name: 'text'}
                            ],
                            id: 'id',
                            localdata: dataJson
                        };
                    var dataAdapter = new $.jqx.dataAdapter(source);
                    dataAdapter.dataBind();
                    var records = dataAdapter.getRecordsHierarchy('id', 'parentId', 'items', [{
                        name: 'text',
                        map: 'label'
                    }]);
                    $('#jqxTree').jqxTree({source: records, width: getWidth(), height: getHeight(), theme: 'bootstrap'});
                }
            });
            $('#jqxTree').on('select', function (event) {
                var taskId = event.args.element.id
                $.get("${ctx}/datadriver/datacenter/publishorderdata.ht?taskId=" + taskId, function (data) {
                    $('#listFrame').html(data);
                });
            });
            // Expand All
            $('#ExpandAll').click(function () {
                $('#jqxTree').jqxTree('expandAll');
            });
            // Collapse All
            $('#CollapseAll').click(function () {
                $('#jqxTree').jqxTree('collapseAll');
            });
        });
    </script>
</head>
<body>
<div class="container" style="height: 100%">
    <div class="col-xs-3" style="height: 100%">
        <div class="panel panel-default" style="height: 100%">
            <div class="panel-heading">项目树</div>
            <div class="panel-body" style="height: 93%;" id="treeBorder">
                <div class="btn-group" style="width: 100%">
                    <button type="button" class="btn btn-xs btn-default" id='ExpandAll'><span class="glyphicon glyphicon-resize-full"></span> 全部展开</button>
                    <button type="button" class="btn btn-xs btn-default" id='CollapseAll'><span class="glyphicon glyphicon-resize-small"></span> 全部缩回</button>
                </div>
                <div id="jqxTree" style="width: 100%">
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-9" style="height: 100%">
        <div class="panel panel-default" style="height: 100%">
            <div class="panel-heading">数据中心列表</div>
            <div class="panel-body" style="height: 93%">
                <div id="listFrame" style="height: 100%"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>