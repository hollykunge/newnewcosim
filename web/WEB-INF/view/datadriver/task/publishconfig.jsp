<!DOCTYPE html>
<%--
	time:2011-11-28 10:17:09
	desc:edit the 用户表
--%>
<%@page language="java" pageEncoding="UTF-8" import="com.casic.datadriver.model.data.OrderDataRelation" %>
<%@page isELIgnored="false" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@include file="/commons/cloud/global.jsp" %>
<html lang="zh-CN">
<head>
    <title>任务数据定义</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
    <%--<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js"></script>--%>
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
            height: 100%
        }

        body {
            overflow: auto;
        }

        /*.layui-elem-field {*/
            /*margin-top: 0px;*/
        /*}*/
    </style>
</head>
<body>
<div class="panel">
    <div id="tabDataInfo" class="panel-nav" style="overflow:hidden; position:relative;">
        <div title="任务发布数据" tabid="orgdetail">
            <a class="layui-btn layui-btn-normal" id="dataFormSave" href="list.ht">返回</a>
            <table align="center" cellpadding="0" cellspacing="0" class="layui-table">
                <tr>
                    <td width="50%" valign="top" style="padding-left:0px !important;">
                        <fieldset class="layui-elem-field" id="fieldframe">
                            <a class="layui-btn layui-btn-small" id="list.ht">返回</a>
                            <legend>私有数据</legend>
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
                                            <td style="text-align: center;" type="hidden">
                                                <input name="ddDataId" value="${privateDataItem.ddDataId}"
                                                       type="hidden">
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
                                            <td style="text-align: center;" type="hidden">
                                                <input type="hidden" name="ddDataTaskId"
                                                       value="${privateDataItem.ddDataTaskId}" type="hidden">
                                                    ${privateDataItem.ddDataTaskId}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                    </td>
                    <td valign="top" style="padding-left:0px !important;">
                        <fieldset class="layui-elem-field">
                            <legend>发布数据</legend>
                            <div class="layui-field-box">
                                <form id="publishDataForm" method="post" action="savepublish.ht">
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
                                        <c:forEach items="${publishDataRelationList}" var="publishDataRelationItem">
                                            <tr>
                                                <td style="text-align: center;" type="hidden">
                                                    <input type="hidden" name="ddDataId"
                                                           value="${publishDataRelationItem.ddDataId}" type="hidden">
                                                        ${publishDataRelationItem.ddDataId}
                                                </td>
                                                <td style="text-align: center;">
                                                    <input type="hidden" name="ddDataName"
                                                           value="${publishDataRelationItem.ddDataName}" type="hidden">
                                                        ${publishDataRelationItem.ddDataName}
                                                </td>
                                                <td style="text-align: center;">
                                                    <input type="hidden" name="ddDataType"
                                                           value="${publishDataRelationItem.ddDataType}" type="hidden">
                                                        ${publishDataRelationItem.ddDataType}
                                                </td>
                                                <td style="text-align: center;">
                                                    <input type="hidden" name="ddDataDescription"
                                                           value="${publishDataRelationItem.ddDataDescription}" type="hidden">
                                                        ${publishDataRelationItem.ddDataDescription}
                                                </td>
                                                <td style="text-align: center;" type="hidden">
                                                    <input type="hidden" name="ddDataTaskId"
                                                           value="${publishDataRelationItem.ddDataTaskId}" type="hidden">
                                                        ${publishDataRelationItem.ddDataTaskId}
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
