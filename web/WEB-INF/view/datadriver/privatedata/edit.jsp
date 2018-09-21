<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@ taglib prefix="ap" uri="/appleTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN" style="height: 100%;">
<head>
    <title>私有数据</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">
    <link href="${ctx}/styles/check/build.css" rel="stylesheet" type="text/css"/>

    <script src="${ctx}/newtable/bootstrap-editable.js"></script>
    <script src="${ctx}/timeselect/moment.js"></script>
    <style>
        .panel {
            margin: 0px;
            height: 100%;
        }

        #card1, #card2, #card3 {
            padding-right: 2px;
            padding-left: 2px;
        }

        #row {
            margin: 5px !important;
        }

        .detail-info {
            margin-bottom: 4px;
        }

        .task-info-title {
            margin: 5px 16px 10px;
            line-height: 12px;
            color: grey;
        }

        .panel .card {
            background: #FFFFFF;
        }

        .card .task-detail-executor {
            margin: 10px 16px 14px;
            line-height: 24px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .editable-pre-wrapped {
            white-space: normal !important;
        }

        .activities-list {
            padding: 0;
            position: relative;
        }

        .activity {
            position: relative;
            overflow: hidden;
            margin: 15px;
        }

        .activity .activity-type-icon {
            width: 36px;
            text-align: center;
            font-size: 16px;
        }

        .activity .activity-body-coyness {
            font-size: 12px;
            margin: 0 0 0 46px;
        }

        .muted {
            color: #A6A6A6;
        }
    </style>
</head>
<body style="height: 100%; margin: 0px;">
<div class="panel panel-default">
    <div class="panel-heading">
        ${privateData.ddDataName}
        <div class="btn-group pull-right">
            <button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                更多 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-menu-right">
                <li><a href="javascript:void(0)">
                    <center>任务菜单</center>
                </a></li>
                <li role="separator" class="divider"></li>
                <li><a href="javascript:void(0)"><span class="glyphicon glyphicon-download-alt"></span> 固化</a></li>
                <li>
                    <c:choose>
                        <c:when test="${privateData.ddDataPublishType==0}">
                            <a href="${ctx}/datadriver/privatedata/del.ht?id=${privateData.ddDataId}"><span
                                    class="glyphicon glyphicon-trash"></span> 删除</a>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>

                </li>
            </ul>
        </div>
    </div>
    <div class="panel-body" style="overflow: auto;">
        <div class="row" id="row">
            <div class="col-xs-4" id="card1">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            数据类型
                        </h5>
                        <a class="task-detail-executor" id="typeSelect" data-type="select" data-pk="1" href="javascript:void(0)">
                            ${privateData.ddDataType}
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-4" id="card2">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            数据最新值
                        </h5>
                        <a class="task-detail-executor" href="javascript:void(0)" id="lastestValue" data-type="text" data-pk="1">
                            ${privateData.ddDataLastestValue}</a>
                    </div>
                </div>
            </div>
            <div class="col-xs-4" id="card3">
                <div class="panel panel-default">
                    <div class="panel-body card">
                        <h5 class="task-info-title">
                            所属任务
                        </h5>
                        <a class="task-detail-executor" href="javascript:void(0)" id="belongTask" data-type="select" data-pk="1">
                            ${privateData.ddDataTaskName}</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" id="row">
            <div class="panel panel-default">
                <div class="list-group">
                    <a class="list-group-item" href="javascript:void(0)" id="description" data-type="textarea" data-pk="1" href="javascript:void(0)">
                        <c:choose>
                            <c:when test="${empty privateData.ddDataDescription}">
                                <h5 class="task-info-title">添加描述</h5>
                            </c:when>
                            <c:otherwise>
                                <p>${privateData.ddDataDescription}</p>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <a class="list-group-item" href="javascript:void(0)" title="暂不可用"><h5 class="task-info-title">添加标签</h5></a>
                </div>
            </div>
        </div>
        <div class="row" id="row">
            <div class="panel panel-default">
                <div class="panel-body card"><h5 class="task-info-title">记录</h5>
                    <div class="panel-group" id="record" role="tablist" aria-multiselectable="true">
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingThree">
                                <h5 class="panel-title">
                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        数据历史
                                    </a>
                                </h5>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                                 aria-labelledby="headingThree">
                                <div class="panel-body">
                                    <ul class="activities-list">
                                        <c:forEach items="${dataVersionList}" var="dataVersionItem">
                                            <li class="activity">
                                                <span class="glyphicon glyphicon-pencil pull-left activity-type-icon muted"></span>
                                                <div class="activity-body-coyness muted pull-right">
                                                    <span>${dataVersionItem.ddDataRecordTime}</span>
                                                </div>
                                                <div class="activity-body-coyness muted">
                                                    <span> ${dataVersionItem.ddDataRecordPersonId}修改了数据，修改值为：${dataVersionItem.ddDataValue}</span>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel-footer" style="position: relative; width: 100%; bottom: 1px;">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="输入@选择提醒人员" disabled="disabled">
            <span class="input-group-btn">
                <button class="btn btn-success" type="button" disabled="disabled">发送</button>
            </span>
        </div><!-- /input-group -->
    </div>
</div>
</body>

<script>
    var curRow = {};
    var taskItem = 0;

    function onChangePrivateData(params, dataItem) {
        curRow[dataItem] = params.value;
        $.ajax({
            type: 'post',
            url: "${ctx}/datadriver/privatedata/onchangeprivatedata.ht?id=${privateData.ddDataId}",
            data: {strJson: JSON.stringify(curRow)}
//                    success: function (data, textStatus, jqXHR) {
//                        alert('保存成功！');
//                    },
//                    error: function () { alert("error");}
        });
    }
    $(function () {
        //类型选择
        <%--$('#typeSelect').editable({--%>
            <%--showbuttons: false,--%>
            <%--value: ${privateData.ddDataType},--%>
            <%--placement: 'bottom',--%>
            <%--source: [<ap:selectDB name="ddDataType" id="ddDataType"--%>
                                     <%--where="parentId=10000025100454" optionValue="itemValue"--%>
                                     <%--optionText="itemName" table="SYS_DIC"--%>
                                     <%--selectedValue="${privateData.ddDataType}" styleClass="form-control">--%>
                <%--</ap:selectDB>],--%>
            <%--url: function (params) {--%>
                <%--taskItem = 0;--%>
                <%--onChangePrivateData(params, taskItem);--%>
            <%--}--%>
        <%--});--%>
        if('${privateData.ddDataType}'=='文件'||'${privateData.ddDataType}'=='模型'){
            $("#lastestValue").attr("data-type","select");
            //最新值
            $('#lastestValue').editable({
                showbuttons: false,
                value: ${privateData.ddDataLastestValue},
                placement: 'bottom',
                source: [
                    <c:forEach var="modelCenterModelItem" items="${modelCenterModelList}">
                    {value: ${modelCenterModelItem.ddModelName}, text: '${modelCenterModelItem.ddModelName}'},
                    </c:forEach>
                ],
                url: function (params) {
                    taskItem = 1;
                    onChangePrivateData(params, taskItem);
                }
            });
        }else {
            $("#lastestValue").attr("data-type","text");
            //最新值
            $('#lastestValue').editable({
                showbuttons: true,
                placement: 'bottom',
                value: '${privateData.ddDataLastestValue}',
                url: function (params) {
                    taskItem = 1;
                    onChangePrivateData(params, taskItem);
                }
            });
        }

        //所属任务
        <%--$('#belongTask').editable({--%>
            <%--showbuttons: true,--%>
            <%--placement: 'bottom',--%>
            <%--value: ${privateData.ddDataTaskName},--%>
            <%--source: [--%>
                <%--<c:forEach var="taskInfoItem" items="${taskInfoList}">--%>
                <%--{value: ${taskInfoItem.ddTaskId}, text: '${taskInfoItem.ddTaskName}'},--%>
                <%--</c:forEach>--%>
            <%--],--%>
            <%--url: function (params) {--%>
                <%--taskItem = 2;--%>
                <%--onChangePrivateData(params, taskItem);--%>
            <%--}--%>
        <%--});--%>
        //描述
        $('#description').editable({
            showbuttons: true,
            placement: 'bottom',
            rows: 5,
            url: function (params) {
                taskItem = 3;
                onChangePrivateData(params, taskItem);
            }
        });
    });
</script>
</html>
