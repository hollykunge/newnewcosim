<!DOCTYPE html>
<%--
	time:2013-04-11 11:48:44
	desc:edit the 业务定义，如邀标采购这样的大业务。
--%>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@page import="com.hotent.core.util.ContextUtil" %>
<html lang="zh-CN">
<head>
    <title>项目基础信息添加</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
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
            var frm = $('#projinfoForm').form();
            $("#dataFormSave").click(function () {
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
                        window.location.href = "${ctx}/datadriver/project/list.ht";
                    }
                });
            } else {
                $.ligerMessageBox.error("提示信息", obj.getMessage());
            }
        }

    </script>
    <style>
        .fr {
            float: right;
        }
    </style>
</head>
<body>

<form id="projinfoForm" name="projinfoForm" method="post" action="save.ht"
      enctype="multipart/form-data">
    <div class="layui-tab layui-tab-card" id="iframe_tab">
        <ul class="layui-tab-title">
            <li class="layui-this">项目参数配置</li>
            <%--<li>项目指标</li>--%>
        </ul>
        <div class="layui-tab-content" style="height: 100%;">
            <div class="layui-tab-item layui-show">
                <blockquote class="layui-elem-quote">
                    <div style="height: 40px;">
                        <div class="fr">
                            <a class="layui-btn layui-btn-normal" id="dataFormSave" href="javascript:void(0)"><i
                                    class="layui-icon">&#xe61e;</i> 保存</a>
                            <a class="layui-btn layui-btn-primary" id="back" href="list.ht"><i
                                    class="layui-icon">&#xe603;</i> 返回</a>
                        </div>
                    </div>
                </blockquote>
                <table id="AddHandlingFee" class="layui-table" cellpadding="0" cellspacing="0"
                       border="0"
                       type="main">
                    <th width="20%">项目名称:</th>
                    <td><input type="text" id="ddProjectName" name="ddProjectName"
                               value="${Project.ddProjectName}"
                               class="layui-input"/></td>
                    <th width="20%">项目研制阶段:</th>
                    <td><input type="text" id="ddProjectScheduleState" name="ddProjectScheduleState"
                               value="${Project.ddProjectScheduleState}"
                               class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">项目责任单位:</th>
                        <td><input type="text" id="ddProjectResponsibleUnits"
                                   name="ddProjectResponsibleUnits"
                                   value="${Project.ddProjectResponsibleUnits}"
                                   class="layui-input"/></td>
                        <th width="20%">项目阶段:</th>
                        <td><input type="text" id="ddProjectPhaseId" name="ddProjectPhaseId"
                                   value="${Project.ddProjectPhaseId}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">项目是否固化:</th>
                        <td><input type="text" id="ddProjectFixedPattern" name="ddProjectFixedPattern"
                                   value="${Project.ddProjectFixedPattern}"
                                   class="layui-input"/></td>
                        <th width="20%">项目基本描述:</th>
                        <td><input type="text" id="ddProjectDescription" name="ddProjectDescription"
                                   value="${Project.ddProjectDescription}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">项目所属系统:</th>
                        <td><input type="text" id="ddProjectOwnerSystemId" name="ddProjectOwnerSystemId"
                                   value="${Project.ddProjectOwnerSystemId}"
                                   class="layui-input"/></td>
                        <th width="20%">项目创建者id:</th>
                        <td><input type="text" id="ddProjectCreatorId" name="ddProjectCreatorId"
                                   value="${Project.ddProjectCreatorId}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">项目类型:</th>
                        <td><input type="text" id="ddProjectType" name="ddProjectType"
                                   value="${Project.ddProjectType}"
                                   class="layui-input"/></td>
                        <th width="20%">所属型号:</th>
                        <td><input type="text" id="ddProjectBelongModel" name="ddProjectBelongModel"
                                   value="${Project.ddProjectBelongModel}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">优先级:</th>
                        <td><input type="text" id="ddProjectPriority" name="ddProjectPriority"
                                   value="${Project.ddProjectPriority}"
                                   class="layui-input"/></td>
                        <th width="20%">备注:</th>
                        <td><input type="text" id="ddProjectRemark" name="ddProjectRemark"
                                   value="${Project.ddProjectRemark}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">计划开始日期:</th>
                        <td><input type="text" id="ddProjectPlanStartDate" name="ddProjectPlanStartDate"
                                   value="${Project.ddProjectPlanStartDate}"
                                   class="layui-input"/></td>

                        <th width="20%">计划完成日期:</th>
                        <td><input type="text" id="ddProjectCompleteDate" name="ddProjectCompleteDate"
                                   value="${Project.ddProjectCompleteDate}"
                                   class="layui-input"/></td>
                    </tr>
                    <tr>
                        <th width="20%">项目密级:</th>
                        <td><input type="text" id="ddProjectSecretLevel" name="ddProjectSecretLevel"
                                   value="${Project.ddProjectSecretLevel}"
                                   class="layui-input"/></td>
                    </tr>
                    <input type="hidden" name="ddProjectId" value="${Project.ddProjectId}">
                    <input type="hidden" name="ddProjectResponsiblePersonId"
                           value="${Project.ddProjectResponsiblePersonId}">
                    <input type="hidden" name="ddProjectCreateDatatime"
                           value="${Project.ddProjectCreateDatatime}">
                    <input type="hidden" name="ddProjectChangePersonId"
                           value="${Project.ddProjectChangePersonId}">
                    <input type="hidden" name="ddProjectState" value="${Project.ddProjectState}">
                    <input type="hidden" name="ddProjectActualStartDate"
                           value="${Project.ddProjectActualStartDate}">
                    <input type="hidden" name="ddProjectActualCompleteData"
                           value="${Project.ddProjectActualCompleteData}">
                    <input type="hidden" name="ddProjectCurrentStage"
                           value="${Project.ddProjectCurrentStage}">
                </table>
            </div>
        </div>
    </div>
</form>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</body>
</html>
