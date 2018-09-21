<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@page import="com.hotent.core.util.ContextUtil" %>
<html lang="zh-CN">
<head>
    <title>任务基础信息添加</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        $(function () {
            var options = {};
            if (showResponse) {
                options.success = showResponse;
            }
            var frm = $('#taskInfoForm').form();
            $("a.save").click(function () {
                frm.setData();
                frm.ajaxForm(options);
                if (frm.valid()) {
                    form.submit();
                }
            });
        });

        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                $.ligerMessageBox.confirm("提示信息", obj.getMessage() + ",是否继续操作", function (rtn) {
                    if (rtn) {
                        this.close();
                    } else {
                        window.location.href = "${ctx}/datadriver/task/list.ht";
                    }
                });
            } else {
                $.ligerMessageBox.error("提示信息", obj.getMessage());
            }
        }

    </script>

</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
            </div>
        </div>
    </div>
    <div class="panel-body">


        <form id="taskInfoForm" name="taskInfoForm" method="post" action="save.ht" enctype="multipart/form-data">
            <div class="layui-tab layui-tab-card">
                <ul class="layui-tab-title">
                    <li class="layui-this">任务配置</li>
                    <li>任务私有数据</li>
                    <li>任务发布数据</li>
                    <li>任务订阅数据</li>
                </ul>
                <div class="layui-tab-content" style="height: 100%;">
                    <div class="layui-tab-item layui-show">
                        <table id="AddHandlingFee" class="table-detail" cellpadding="0" cellspacing="0" border="0"
                               type="main">
                            <tr>
                                <th width="20%">任务编号:</th>
                                <td><input type="text" id="ddTaskId" name="ddTaskId" value="${TaskInfo.ddTaskId}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务所属项目:</th>
                                <td><input type="text" id="ddTaskProjectId" name="ddTaskProjectId"
                                           value="${TaskInfo.ddTaskProjectId}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">任务基本描述:</th>
                                <td><input type="text" id="ddTaskDescription" name="ddTaskDescription"
                                           value="${TaskInfo.ddTaskDescription}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务创建者id:</th>
                                <td><input type="text" id="ddTaskCreatorId" name="ddTaskCreatorId"
                                           value="${TaskInfo.ddTaskCreatorId}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">任务负责人:</th>
                                <td><input type="hiden" id="ddTaskfullname" name="ddTaskResponsiblePerson"
                                           value="${fullname}"
                                           validate="{required:false,maxlength:768}" class="inputText"/>
                                    <a href="userlist.ht?TaskId=${TaskInfo.ddTaskId}" class="link del">责任人选定</a>
                                </td>
                                <th width="20%">任务是否固化:</th>
                                <td><input type="text" id="ddTaskFixedPattern" name="ddTaskFixedPattern"
                                           value="${TaskInfo.ddTaskFixedPattern}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">任务类型:</th>
                                <td><input type="text" id="ddTaskType" name="ddTaskType" value="${TaskInfo.ddTaskType}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务子类型:</th>
                                <td><input type="text" id="ddTaskChildType" name="ddTaskChildType"
                                           value="${TaskInfo.ddTaskChildType}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">优先级:</th>
                                <td><input type="text" id="ddTaskPriority" name="ddTaskPriority"
                                           value="${TaskInfo.ddTaskPriority}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                                <th width="20%">里程碑任务:</th>
                                <td><input type="text" id="ddTaskMilestone" name="ddTaskMilestone"
                                           value="${TaskInfo.ddTaskMilestone}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                            </tr>

                            <tr>
                                <th width="20%">任务计划工时:</th>
                                <td><input type="text" id="ddTaskEstimateTime" name="ddTaskEstimateTime"
                                           value="${TaskInfo.ddTaskEstimateTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务计划开始时间:</th>
                                <td><input type="text" id="ddTaskPlanStartTime" name="ddTaskPlanStartTime"
                                           value="${TaskInfo.ddTaskPlanStartTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>

                            <tr>
                                <th width="20%">任务计划结束时间:</th>
                                <td><input type="text" id="ddTaskPlanEndTime" name="ddTaskPlanEndTime"
                                           value="${TaskInfo.ddTaskPlanEndTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务完成率:</th>
                                <td><input type="text" id="ddTaskCompleteRate" name="ddTaskCompleteRate"
                                           value="${TaskInfo.ddTaskCompleteRate}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>

                            <tr>
                                <th width="20%">任务完成状态:</th>
                                <td><input type="text" id="ddTaskCompleteState" name="ddTaskCompleteState"
                                           value="${TaskInfo.ddTaskCompleteState}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务资源id:</th>
                                <td><input type="text" id="ddTaskResourceId" name="ddTaskResourceId"
                                           value="${TaskInfo.ddTaskResourceId}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">实际工时:</th>
                                <td><input type="text" id="ddTaskActualTime" name="ddTaskActualTime"
                                           value="${TaskInfo.ddTaskActualTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">计划工时:</th>
                                <td><input type="text" id="ddTaskPlanTime" name="ddTaskPlanTime"
                                           value="${TaskInfo.ddTaskPlanTime}" validate="{required:false,maxlength:768}"
                                           class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">实际开始日期:</th>
                                <td><input type="text" id="ddTaskActualStartTime" name="ddTaskActualStartTime"
                                           value="${TaskInfo.ddTaskActualStartTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">实际结束日期:</th>
                                <td><input type="text" id="ddTaskActualEndTime" name="ddTaskActualEndTime"
                                           value="${TaskInfo.ddTaskActualEndTime}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>
                            <tr>
                                <th width="20%">计划工期:</th>
                                <td><input type="text" id="ddTaskPlanDuration" name="ddTaskPlanDuration"
                                           value="${TaskInfo.ddTaskPlanDuration}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                                <th width="20%">任务名称:</th>
                                <td><input type="text" id="ddTaskName" name="ddTaskName" value="${TaskInfo.ddTaskName}"
                                           validate="{required:false,maxlength:768}" class="inputText"/></td>
                            </tr>
                            <tr>
                                <td><input type="hiden" id="ddTaskResponsiblePerson"
                                           name="ddTaskResponsiblePerson"
                                           value="${userId}"
                                           validate="{required:false,maxlength:768}" class="inputText"/>
                            </tr>
                        </table>
                    </div>
                    <!--任务私有数据-->
                    <div class="layui-tab-item">
                        <table class="layui-table" cellpadding="1" cellspacing="1" id="privateData"
                               formType="window" type="sub">
                            <tr>
                                <td colspan="11">
                                    <div class="group" align="left">
                                        <a id="btnAdd" class="link add">添加</a>
                                    </div>
                                    <div align="center">
                                        任务编辑：添加私有数据
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>数据名称</th>
                                <th>数据类型</th>
                                <th>灵敏阈</th>
                                <th>操作</th>
                            </tr>
                            <c:forEach items="${privateDataList}" var="privateDataList"
                                       varStatus="status">
                                <tr type="subdata">
                                    <td style="text-align: center"
                                        name="ddDataName">${privateDataList.ddDataName}</td>
                                    <td style="text-align: center"
                                        name="ddDataType">${privateDataList.ddDataType}</td>
                                    <td style="text-align: center"
                                        name="ddDataSensitiveness">${privateDataList.ddDataSensitiveness}</td>
                                    <td style="text-align: center">
                                        <a href="javascript:void(0)" class="link del">删除</a>
                                        <a href="javascript:void(0)" class="link edit">编辑</a>
                                    </td>
                                    <input type="hidden" name="ddDataId" value="${privateDataList.ddDataId}"/>
                                    <input type="hidden" name="ddDataName" value="${privateDataList.ddDataName}"/>
                                    <input type="hidden" name="ddDataType" value="${privateDataList.ddDataType}"/>
                                    <input type="hidden" name="ddDataDescription"
                                           value="${privateDataList.ddDataDescription}"/>
                                    <input type="hidden" name="ddDataTaskId" value="${privateDataList.ddDataTaskId}"/>
                                    <input type="hidden" name="ddDataPublishType"
                                           value="${privateDataList.ddDataPublishType}"/>
                                    <input type="hidden" name="ddDataLastestValue"
                                           value="${privateDataList.ddDataLastestValue}"/>
                                    <input type="hidden" name="ddDataSubmiteState"
                                           value="${privateDataList.ddDataSubmiteState}"/>
                                    <input type="hidden" name="ddDataCreatePerson"
                                           value="${privateDataList.ddDataCreatePerson}"/>
                                    <input type="hidden" name="ddDataCreateTime"
                                           value="${privateDataList.ddDataCreateTime}"/>
                                    <input type="hidden" name="ddDataIsDelivery"
                                           value="${privateDataList.ddDataIsDelivery}"/>
                                    <input type="hidden" name="ddDataSensitiveness"
                                           value="${privateDataList.ddDataSensitiveness}"/>
                                </tr>
                            </c:forEach>
                            <tr type="append">
                                <td style="text-align: center" name="ddDataName"></td>
                                <td style="text-align: center" name="ddDataType"></td>
                                <td style="text-align: center" name="ddDataSensitiveness"></td>

                                <td style="text-align: center">
                                    <a href="javascript:void(0)" class="link del">删除</a>
                                    <a href="javascript:void(0)" class="link edit">编辑</a>
                                </td>
                                <input type="hidden" name="ddDataId" value=""/>
                                <input type="hidden" name="ddDataName" value=""/>
                                <input type="hidden" name="ddDataType" value=""/>
                                <input type="hidden" name="ddDataDescription" value=""/>
                                <input type="hidden" name="ddDataTaskId" value=""/>
                                <input type="hidden" name="ddDataPublishType" value=""/>
                                <input type="hidden" name="ddDataLastestValue" value=""/>
                                <input type="hidden" name="ddDataSubmiteState" value=""/>
                                <input type="hidden" name="ddDataCreatePerson" value=""/>
                                <input type="hidden" name="ddDataCreateTime" value=""/>
                                <input type="hidden" name="ddDataIsDelivery" value=""/>
                                <input type="hidden" name="ddDataSensitiveness" value=""/>
                            </tr>
                        </table>
                    </div>
                    <!--任务发布数据-->
                    <div class="layui-tab-item">
                        <iframe src="${ctx}/datadriver/task/publishconfig.ht?id=${TaskInfo.ddTaskId}"
                                style="width: 100%;height: 500px" scrolling="no"></iframe>
                    </div>
                    <!--任务订阅数据-->
                    <div class="layui-tab-item">
                        <iframe src="${ctx}/datadriver/task/orderconfig.ht?id=${TaskInfo.ddTaskId}"
                                style="width: 100%;height: 500px" scrolling="no"></iframe>
                    </div>
                </div>
            </div>
        </form>
        <form id="privateDataForm" style="display:none">
            <table class="layui-table" cellpadding="1" cellspacing="1" border="1">
                <colgroup>
                    <col width="150">
                    <col width="200">
                </colgroup>
                <tr>
                    <th width="20%">数据名称:</th>
                    <td><input type="text" name="ddDataName" value="" class="inputText"
                               validate="{required:false,maxlength:96}"/></td>
                </tr>
                <tr>
                    <th width="20%">数据类型:</th>
                    <td><input type="text" name="ddDataType" value="" class="inputText" validate="{required:false}"/>
                    </td>
                </tr>
                <tr>
                    <th width="20%">灵敏阈:</th>
                    <td><input type="text" name="ddDataSensitiveness" value="" class="inputText"
                               validate="{required:false,maxlength:768}"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</html>
