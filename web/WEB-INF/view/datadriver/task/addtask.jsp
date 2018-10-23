<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2016/12/14
  Time: 下午3:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ap" uri="/appleTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>添加任务</title>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/newtable/bootstrap-editable.css" rel="stylesheet">
    <link href="${ctx}/newtable/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="${ctx}/styles/check/build.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/styles/select/bootstrap-select.min.css" rel="stylesheet" type="text/css"/>
    <!-- include the style of alertify-->
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/alertify.min.css" />
    <link rel="stylesheet" href="${ctx}/js/alertifyjs/css/themes/default.min.css" />

    <script src="${ctx}/newtable/bootstrap-editable.js"></script>
    <script src="${ctx}/newtable/bootstrap-datetimepicker.min.js"></script>
    <script src="${ctx}/styles/select/bootstrap-select.min.js"></script>
    <script src="${ctx}/timeselect/moment.js"></script>
    <script src="${ctx}/newtable/union-select.js"></script>
    <!-- include the script alertify-->
    <script src="${ctx}/js/alertifyjs/alertify.min.js"></script>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">任务创建</h4>
</div>
<div class="modal-body">
    <div class="container-fluid">
        <form id="taskInfoForm" name="taskInfoForm" method="post" action="${ctx}/datadriver/task/save.ht"
              enctype="multipart/form-data">
            <table id="AddHandlingFee" class="table table-striped" cellpadding="0" cellspacing="0"
                   border="0"
                   type="main">
                <tr>
                    <th width="20%">任务名称:</th>
                    <td><input type="text" id="ddTaskName" name="ddTaskName"
                               value="" class="form-control" validate="{required:true}"/></td>
                    <th width="20%">任务所属项目:</th>
                    <td><input type="text" id="ddTaskProjectName" name="ddTaskProjectName"
                               value="${projectItem.ddProjectName}" class="form-control" readonly/></td>
                </tr>
                <tr>
                    <th width="20%">任务开始时间:</th>
                    <td><input id="datetimeStart" name="ddTaskPlanStartTime"
                               value="" class="form-control" readonly validate="{required:true}"/></td>
                    <th width="20%">任务截至时间:</th>
                    <td><input id="datetimeEnd" name="ddTaskPlanEndTime"
                               value="" class="form-control" readonly validate="{required:true}"/></td>
                </tr>
                <tr>
                    <th width="20%">优先级:</th>
                    <td>
                        <select id="ddTaskPriority" name="ddTaskPriority" class="form-control">
                            <option value="1">一般</option>
                            <option value="2">重要</option>
                            <option value="3">紧急</option>
                        </select>
                    </td>
                    <th width="20%">是否里程碑:</th>
                    <td>

                        <div class="radio radio-info radio-inline">
                            <input type="radio" name="ddTaskMilestone" id="ddTaskMilestone1" value="1" checked>
                            <label for="ddTaskMilestone1">
                                是
                            </label>
                        </div>
                        <div class="radio radio-info radio-inline">
                            <input type="radio" name="ddTaskMilestone" id="ddTaskMilestone0" value="0">
                            <label for="ddTaskMilestone0">
                                否
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="20%">密级:</th>
                    <td><input type="text" id="ddProjectSecretLevel" name="ddProjectSecretLevel"
                            <c:choose>
                                <c:when test="${projectItem.ddProjectSecretLevel == 'fm'}">value="非密"</c:when>
                                <c:when test="${projectItem.ddProjectSecretLevel == 'mm'}">value="秘密"</c:when>
                                <c:when test="${projectItem.ddProjectSecretLevel == 'jm'}">value="机密"</c:when>
                                <c:when test="${projectItem.ddProjectSecretLevel == 'nb'}">value="内部"</c:when>
                                <c:otherwise>value="非密"</c:otherwise>
                            </c:choose>
                               class="form-control" readonly/></td>
                    <th width="20%">任务类型:</th>
                    <td><input type="text" id="ddTaskType" name="ddTaskType"
                               value="" class="form-control"/></td>
                </tr>
                <tr>
                    <th width="20%">负责人:</th>
                    <td>
                        <select class="selectpicker show-tick form-control"
                                noneResultsText="无匹配项"
                                data-live-search="true" id="select-first" title="请选择组织"
                                data-getDataUrl="${ctx}/platform/system/sysOrg/users.ht?projectId=${projectItem.ddProjectId}" validate="{required:true}">
                            <c:forEach var="orgItem" items="${sysOrgList}">
                                <option value="${orgItem.orgId}"
                                        <c:if test="${TaskInfo.userOrg == '${orgItem.orgName}'}">selected="selected"</c:if>>${orgItem.orgName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="ddTaskResponsiblePerson" class="selectpicker show-tick form-control"
                                noneResultsText="无匹配项"
                                data-live-search="true" id="select-second" title="请选择人员" validate="{required:true}">
                            <%--<c:forEach var="personItem" items="${sysUserList}">--%>
                            <%--<option value="${personItem.userId}"--%>
                            <%--<c:if test="${TaskInfo.ddTaskPerson == '${personItem.fullname}'}">selected="selected"</c:if>>${personItem.fullname}</option>--%>
                            <%--</c:forEach>--%>
                        </select>
                    </td>

                </tr>
                <tr>
                    <th width="20%">任务基本描述:</th>
                    <td colspan="3"><textarea id="ddTaskDescription" name="ddTaskDescription"
                                              value="" class="form-control"
                                              rows="3"/></textarea>
                    </td>
                </tr>
                <input type="hidden" id="ddTaskProjectId" name="ddTaskProjectId" value="${projectItem.ddProjectId}"/>
                <input type="hidden" id="ddTaskPerson" name="ddTaskPerson" value=""/>
                <input type="hidden" id="ddSecretLevel" name="ddSecretLevel" value="${projectItem.ddProjectSecretLevel}"/>
                <input type="hidden" id="ddTaskCreatorId" name="ddTaskCreatorId"
                       value="${projectItem.ddProjectCreatorId}"/>
            </table>
        </form>
        <div class="row">
            <div class="col-xs-6">
                <button class="btn btn-success btn-block" id="dataFormSave">创建新任务</button>
            </div>
            <div class="col-xs-6">
                <button class="btn btn-default btn-block" id="createfrommodel" disabled="disabled" title="暂不可用">
                    从模版创建任务
                </button>
            </div>
        </div>

    </div>
</div>
</body>
<script type="text/javascript">
    //@ sourceURL=add.js

    //开始时间
    $('#datetimeStart').datetimepicker({
        format: 'yyyy-mm-dd',  //显示格式可为yyyymm/yyyy-mm-dd/yyyy.mm.dd
        weekStart: 1,  	//0-周日,6-周六 。默认为0
        autoclose: true,
        startView: 2,  	//打开时显示的视图。0-'hour' 1-'day' 2-'month' 3-'year' 4-'decade'
        minView: 3,  	//最小时间视图。默认0-'hour'
        // 	maxView: 4, 	//最大时间视图。默认4-'decade'
        // 	todayBtn:true, 	//true时"今天"按钮仅仅将视图转到当天的日期。如果是'linked'，当天日期将会被选中。
        // 	todayHighlight:true,	//默认值: false,如果为true, 高亮当前日期。
        initialDate: new Date(),	//初始化日期.默认new Date()当前日期
        forceParse: false,  	//当输入非格式化日期时，强制格式化。默认true
        bootcssVer:3,	//显示向左向右的箭头
        language: 'zh-CN' //语言
    }).on('changeDate', function(ev){
        // 约束 开始时间早于结束时间
        $('#datetimeEnd').datetimepicker('setStartDate', ev.date);
    });
    //结束时间
    $('#datetimeEnd').datetimepicker({
        format: 'yyyy-mm-dd',  //显示格式可为yyyymm/yyyy-mm-dd/yyyy.mm.dd
        weekStart: 1,  	//0-周日,6-周六 。默认为0
        autoclose: true,
        startView: 2,  	//打开时显示的视图。0-'hour' 1-'day' 2-'month' 3-'year' 4-'decade'
        minView: 3,  	//最小时间视图。默认0-'hour'
        // 	maxView: 4, 	//最大时间视图。默认4-'decade'
        // 	todayBtn:true, 	//true时"今天"按钮仅仅将视图转到当天的日期。如果是'linked'，当天日期将会被选中。
        // 	todayHighlight:true,	//默认值: false,如果为true, 高亮当前日期。
        initialDate: new Date(),	//初始化日期.默认new Date()当前日期
        forceParse: false,  	//当输入非格式化日期时，强制格式化。默认true
        bootcssVer:3,	//显示向左向右的箭头
        language: 'zh-CN' //语言
    }).on('changeDate', function (ev) {
        // 约束 开始时间早于结束时间
        $('#datetimeStart').datetimepicker('setEndDate', ev.date);
    });


    $(function () {

        $(".selectpicker").selectpicker();

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
    });
    $('#select-second').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        var fullName = $('#select-second').find("option:selected").text();
        $("#ddTaskPerson").val(fullName);
    });

    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
            window.location.href = "${ctx}/datadriver/project/stepinto.ht?id=${projectItem.ddProjectId}";
        } else {
            alertify.set('notifier','position', 'top-right');
            alertify.error("创建失败！");
        }
    }
</script>
<script type="text/javascript" src="${ctx}/timeselect/bootstrap-datetimepicker.zh-CN.js"></script>
</html>
