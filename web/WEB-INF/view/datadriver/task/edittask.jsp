<%--
  Created by IntelliJ IDEA.
  User: dd
  Date: 2018/10/28
  Time: 5:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>编辑任务</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <!-- include the style of alertify-->
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/alertify.min.css"/>
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/themes/default.min.css"/>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/styles/select/bootstrap-select.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/newtable/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="${ctx}/styles/select/bootstrap-select.min.js"></script>
    <script src="${ctx}/newtable/union-select.js"></script>
    <script src="${ctx}/newtable/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/newtable/locales/bootstrap-datetimepicker.zh-CN.js"
            charset="UTF-8"></script>
    <!-- include the script alertify-->
    <script src="${ctx}/js/alertifyjs/alertify.min.js"></script>

</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title" id="exampleModalLabel">任务详情</h4>
</div>
<div class="modal-body">
    <%--<ul class="nav nav-tabs" role="tablist" id="myTab">--%>
        <%--<li role="presentation" class="active reponav-item" id="switch_attr_publish"><a href="#data"--%>
                                                                                        <%--data-toggle="tab"--%>
                                                                                        <%--role="tab"><span--%>
                <%--class="glyphicon glyphicon-log-out"></span> 任务编辑</a>--%>
        <%--</li>--%>
        <%--<li role="presentation" class="reponav-item" id="switch_attr_order"><a href="#publish" data-toggle="tab"--%>
                                                                               <%--role="tab"><span--%>
                <%--class="glyphicon glyphicon-log-in"></span> 任务活动</a>--%>
        <%--</li>--%>
    <%--</ul>--%>
    <form id="taskInfoForm" name="taskInfoForm" method="post" action="${ctx}/datadriver/task/save.ht"
          class="form-horizontal">
        <table id="AddHandlingFee" class="table" cellpadding="0" cellspacing="0"
               border="0"
               type="main">
            <tr>
                <th width="20%">任务名称:</th>
                <td>
                    <input name="ddTaskName" type="text" class="col-md-9 form-control" id="task-name"
                           value="${TaskInfo.ddTaskName}">
                </td>
            </tr>
            <tr>
                <th width="20%">项目名称:</th>
                <td>
                    <input name="ddTaskProjectName" type="text" class="col-md-9 form-control" id="project-name"
                           value="${TaskInfo.ddTaskProjectName}"
                           readonly>
                </td>
            </tr>
            <tr>

                <th width="20%">优先级:</th>
                <td>
                    <select id="ddTaskPriority" name="ddTaskPriority" class="form-control">
                        <option value="1"
                                <c:if test="${TaskInfo.ddTaskPriority == 1}">selected="selected"</c:if>><span
                                class="label label-primary">一般</span></option>
                        <option value="2"
                                <c:if test="${TaskInfo.ddTaskPriority == 2}">selected="selected"</c:if>><span
                                class="label label-warning">重要</span></option>
                        <option value="3"
                                <c:if test="${TaskInfo.ddTaskPriority == 3}">selected="selected"</c:if>><span
                                class="label label-danger">紧急</span></option>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%">计划开始时间:</th>
                <td>
                    <input name="ddTaskPlanStartTime" class="form-control" id="plan-start" size="16" type="text"
                           value="${TaskInfo.ddTaskPlanStartTime}" readonly>
                </td>

            </tr>
            <tr>
                <th width="20%">计划完成时间:</th>
                <td>
                    <input name="ddTaskPlanEndTime" class="form-control" id="plan-end" size="16" type="text"
                           value="${TaskInfo.ddTaskPlanEndTime}" readonly>
                </td>
            </tr>
            <tr>
                <th width="20%">里程碑:</th>
                <td>
                    <div class="radio radio-info radio-inline">
                        <input type="radio" name="ddTaskMilestone" id="ddTaskMilestone1" value="1"
                               <c:if test="${TaskInfo.ddTaskMilestone == 1}">checked</c:if>>
                        <label for="ddTaskMilestone1">
                            是
                        </label>
                    </div>
                    <div class="radio radio-info radio-inline">
                        <input type="radio" name="ddTaskMilestone" id="ddTaskMilestone0" value="0"
                               <c:if test="${TaskInfo.ddTaskMilestone == 0}">checked</c:if>>
                        <label for="ddTaskMilestone0">
                            否
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%">里程碑:</th>
                <td>
                    <input type="text" id="task-secret" name="ddProjectSecretLevel"
                            <c:choose>
                                <c:when test="${TaskInfo.ddSecretLevel == 'fm'}">value="非密"</c:when>
                                <c:when test="${TaskInfo.ddSecretLevel == 'mm'}">value="秘密"</c:when>
                                <c:when test="${TaskInfo.ddSecretLevel == 'jm'}">value="机密"</c:when>
                                <c:when test="${TaskInfo.ddSecretLevel == 'nb'}">value="内部"</c:when>
                                <c:otherwise>value="非密"</c:otherwise>
                            </c:choose>
                           class="form-control" readonly/>
                </td>
            </tr>
            <tr>
                <th width="20%">负责人:</th>
                <td>
                    <select class="selectpicker show-tick form-control"
                            noneResultsText="无匹配项"
                            data-live-search="true" id="select-first"
                            data-getDataUrl="${ctx}/platform/system/sysOrg/users.ht?projectId=${TaskInfo.ddTaskProjectId}"
                            validate="{required:true}">
                        <c:forEach var="orgItem" items="${sysOrgList}">
                            <option value="${orgItem.orgId}"
                                    <c:if test="${taskUser.orgName == orgItem.orgName}">selected="selected"</c:if>>${orgItem.orgName}</option>
                        </c:forEach>
                    </select>

                    <select name="ddTaskResponsiblePerson" class="selectpicker show-tick form-control"
                            noneResultsText="无匹配项"
                            data-live-search="true" id="select-second" validate="{required:true}">

                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%">任务类型:</th>
                <td>

                    <input type="text" name="ddTaskType"
                           value="${TaskInfo.ddTaskType}" class="form-control"/>
                </td>
            </tr>
            <tr>
                <th width="20%">任务描述:</th>
                <td>
                    <textarea class="form-control" name="ddTaskDescription">${TaskInfo.ddTaskDescription}</textarea>
                </td>
            </tr>
            <tr>
                <input type="hidden" id="ddTaskId" name="ddTaskId" value="${TaskInfo.ddTaskId}"/>
                <input type="hidden" id="ddTaskChildType" name="ddTaskChildType" value="${TaskInfo.ddTaskChildType}"/>
                <input type="hidden" id="ddTaskState" name="ddTaskState" value="${TaskInfo.ddTaskState}"/>
                <input type="hidden" id="ddTaskProjectId" name="ddTaskProjectId" value="${TaskInfo.ddTaskProjectId}"/>
                <input type="hidden" id="ddTaskPerson" name="ddTaskPerson" value="${TaskInfo.ddTaskPerson}"/>
                <%--<input type="hidden" id="ddTaskResponsiblePerson" name="ddTaskResponsiblePerson"--%>
                <%--value="${TaskInfo.ddTaskResponsiblePerson}"/>--%>
                <input type="hidden" id="ddSecretLevel" name="ddSecretLevel"
                       value="${TaskInfo.ddSecretLevel}"/>
                <input type="hidden" id="ddTaskCreatorId" name="ddTaskCreatorId"
                       value="${TaskInfo.ddTaskCreatorId}"/>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer">
    <c:choose>
        <c:when test="${TaskInfo.ddTaskChildType=='createpanel'}">
            <a type="button" class="btn btn-danger" href="${ctx}/datadriver/task/del.ht?id=${TaskInfo.ddTaskId}"><span
                    class="glyphicon glyphicon-trash"
                    aria-hidden="true"></span> 删除</a>
            <button id="dataFormSave" type="button" class="btn btn-success"><span
                    class="glyphicon glyphicon-floppy-saved"
                    aria-hidden="true"></span> 保存
            </button>
        </c:when>
        <c:when test="${TaskInfo.ddTaskChildType=='checkpanel'}">
            <a type="button" class="btn btn-default" id="reback" href="javascript:void(0)"><span
                    class="glyphicon glyphicon-repeat"></span> 退回</a>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</div>
</body>

<script>
    //@ sourceURL=edittask.js
    $(function () {
        $('.selectpicker').selectpicker();
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }

        var frm = $('#taskInfoForm').form();
        $("#dataFormSave").click(function () {
            frm.setData();
            frm.ajaxForm(options);
            if (frm.valid()) {
                form.submit();
            }
        });
        $.ajax({
            type: 'get',
            url: '${ctx}/platform/system/sysOrg/users.ht?projectId=${TaskInfo.ddTaskProjectId}',
            data: {
                parentId: '${taskUser.orgId}'
            },
            async: false,//使用同步的方式,true为异步方式
            cache: false,
            dataType: 'json',
            success: function (datas) {
                //遍历回传的数据添加到二级select
                $.each(datas, function () {
                    if (this.userId == ${TaskInfo.ddTaskResponsiblePerson})
                        var options = '<option selected="selected" value="' + this.userId + '">' + this.fullname + '</option>';
                    else if (this.userId != ${TaskInfo.ddTaskResponsiblePerson})
                        var options = '<option value="' + this.userId + '">' + this.fullname + '</option>';
                    $("#select-second").append(options);
                });
                // 刷新二级select
                $("#select-second").selectpicker('refresh');
            },
            error: function () {
                console.log("请求当前组织的用户失败！" + "edittask.js");
            }

        });
    });

    $("#reback").click(function () {
        $.get("${ctx}/datadriver/personaltask/recovertask.ht?id=${TaskInfo.ddTaskId}", function (data) {
            window.location.href = 'stepinto.ht?id=${TaskInfo.ddTaskProjectId}'
        });
    });

    $('#select-second').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        var fullName = $('#select-second').find("option:selected").text();
        $("#ddTaskPerson").val(fullName);
    });

    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
            window.location.href = "${ctx}/datadriver/project/stepinto.ht?id=${TaskInfo.ddTaskProjectId}";
        } else {
            alertify.set('notifier', 'position', 'top-right');
            alertify.error("创建失败！");
        }
    }

    //开始时间
    $('#plan-start').datetimepicker({
        format: 'yyyy-mm-dd',  //显示格式可为yyyymm/yyyy-mm-dd/yyyy.mm.dd
        weekStart: 1,  	//0-周日,6-周六 。默认为0
        autoclose: true,
        startView: 2,  	//打开时显示的视图。0-'hour' 1-'day' 2-'month' 3-'year' 4-'decade'
        minView: 3,  	//最小时间视图。默认0-'hour'
        // 	maxView: 4, 	//最大时间视图。默认4-'decade'
        todayBtn: true, 	//true时"今天"按钮仅仅将视图转到当天的日期。如果是'linked'，当天日期将会被选中。
        todayHighlight: true,	//默认值: false,如果为true, 高亮当前日期。
        initialDate: new Date(),	//初始化日期.默认new Date()当前日期
        forceParse: false,  	//当输入非格式化日期时，强制格式化。默认true
        bootcssVer: 3,	//显示向左向右的箭头
        language: 'zh-CN' //语言
    }).on('changeDate', function (ev) {
        // 约束 开始时间早于结束时间
        $('#plan-start').datetimepicker('setStartDate', ev.date);
    });
    //结束时间
    $('#plan-end').datetimepicker({
        format: 'yyyy-mm-dd',  //显示格式可为yyyymm/yyyy-mm-dd/yyyy.mm.dd
        weekStart: 1,  	//0-周日,6-周六 。默认为0
        autoclose: true,
        startView: 2,  	//打开时显示的视图。0-'hour' 1-'day' 2-'month' 3-'year' 4-'decade'
        minView: 3,  	//最小时间视图。默认0-'hour'
        // 	maxView: 4, 	//最大时间视图。默认4-'decade'
        todayBtn: true, 	//true时"今天"按钮仅仅将视图转到当天的日期。如果是'linked'，当天日期将会被选中。
        todayHighlight: true,	//默认值: false,如果为true, 高亮当前日期。
        initialDate: new Date(),	//初始化日期.默认new Date()当前日期
        forceParse: false,  	//当输入非格式化日期时，强制格式化。默认true
        bootcssVer: 3,	//显示向左向右的箭头
        language: 'zh-CN' //语言
    }).on('changeDate', function (ev) {
        // 约束 开始时间早于结束时间
        $('#plan-end').datetimepicker('setEndDate', ev.date);
    });
</script>
</html>