<!DOCTYPE html>
<%--
	time:2016-11-28 10:17:09
	desc:edit the 数据订阅
--%>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@include file="/commons/cloud/global.jsp" %>
<html lang="zh-CN">
<head>
    <title>任务数据定义</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
    <link rel="stylesheet" href="${ctx}/js/tree/v35/zTreeStyle.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/tree/v35/jquery.ztree.exedit-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/displaytag.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <link rel="stylesheet" href="${ctx}/styles/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/styles/layui/jquery.dragsort-0.5.2.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#gridtbody,#gridtbody2").dragsort({
                itemSelector: "tr",
                dragSelector: "tr",
                dragBetween: true,
                dragEnd: saveOrder1,
                placeHolderTemplate: "<tr></tr>"
            });
        });
        function saveOrder1() {
            var data = $("[name='biaozhi']").map(function () {
                return $(this).html();
            }).get();
        }

    </script>
    <style type="text/css">
        html {
            height: 100%  }
        body {
            overflow: auto;  }
    </style>
</head>
<body>
<div class="panel">
    <div id="tabDataInfo" class="panel-nav" style="overflow:hidden; position:relative;">
        <div title="任务发布数据" tabid="orgdetail" icon="${ctx}/styles/default/images/icon/home.png">
            <table align="center" cellpadding="0" cellspacing="0" class="layui-table">
                <tr>
                    <td width="50%" valign="top" style="padding-left:2px !important;">
                        <fieldset class="layui-elem-field">
                            <a class="layui-btn layui-btn-small" id="list.ht">返回</a>

                            <legend>可订阅项目数据</legend>
                            <div class="layui-field-box">
                                <br>
                                <table id="PrivateData" class="layui-table" cellpadding="0" cellspacing="0">
                                    <thead>
                                    <th style="text-align:center !important;">数据编号</th>
                                    <th style="text-align:center !important;">数据名称</th>
                                    <th style="text-align:center !important;">数据类型</th>
                                    <th style="text-align:center !important;">数据描述</th>
                                    <th style="text-align:center !important;">数据所属任务</th>
                                    </thead>
                                    <tbody id="gridtbody">
                                    <c:forEach items="${privateDataList}" var="privateDataItem">
                                        <tr style="cursor:pointer">
                                        <tr style="cursor:pointer">
                                            <td style="text-align: center;">
                                                <input name="ddDataId" type="hidden"
                                                       value="${privateDataItem.ddDataId}">
                                                    ${privateDataItem.ddDataId}
                                            </td>
                                            <td style="text-align: center;">
                                                <input type="hidden" name="ddDataName"
                                                       value="${privateDataItem.ddDataName}">
                                                    ${privateDataItem.ddDataName}
                                            </td>
                                            <td style="text-align: center;">
                                                <input type="hidden" name="ddDataType"
                                                       value="${privateDataItem.ddDataType}">
                                                    ${privateDataItem.ddDataType}
                                            </td>
                                            <td style="text-align: center;">
                                                <input type="hidden" name="ddDataDescription"
                                                       value="${privateDataItem.ddDataDescription}">
                                                    ${privateDataItem.ddDataDescription}
                                            </td>
                                            <td style="text-align: center;">
                                                <input type="hidden" name="ddDataTaskId"
                                                       value="${privateDataItem.ddDataTaskId}">
                                                    ${privateDataItem.ddDataTaskId}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                    </td>
                    <td width="50%" valign="top" style="padding-left:2px !important;">
                        <fieldset class="layui-elem-field">
                            <legend>已订阅数据</legend>
                            <div class="layui-field-box">
                                <form id="orderDataForm" method="post" action="saveorder.ht?id=${nowTaskId}">
                                    <input type="submit" class="layui-btn layui-btn-small" value="提交">
                                    <table id="choosedData" class="layui-table" cellpadding="0"
                                           cellspacing="0">
                                        <thead>
                                        <th style="text-align:center !important;">数据编号</th>
                                        <th style="text-align:center !important;">数据名称</th>
                                        <th style="text-align:center !important;">数据类型</th>
                                        <th style="text-align:center !important;">数据描述</th>
                                        <th style="text-align:center !important;">数据所属任务</th>
                                        </thead>
                                        <tbody id="gridtbody2">
                                        <tr></tr>
                                        <c:forEach items="${orderDataRelationList}" var="orderDataRelationItem">
                                            <tr style="cursor:pointer">
                                                <td style="text-align: center;">
                                                        ${orderDataRelationItem.ddDataId}
                                                </td>
                                                <td style="text-align: center;">
                                                        ${orderDataRelationItem.ddDataName}
                                                </td>
                                                <td style="text-align: center;">
                                                        ${orderDataRelationItem.ddDataType}
                                                </td>
                                                <td style="text-align: center;">
                                                        ${orderDataRelationItem.ddDataDescription}
                                                </td>
                                                <td style="text-align: center;">
                                                        ${orderDataRelationItem.ddDataTaskId}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </fieldset>
                    </td>
            </table>
        </div>
    </div>
</div>
</body>
</html>
