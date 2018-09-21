<!DOCTYPE html>
<%--
	time:2017-01-22 11:48:44
	desc:edit the 业务定义，如邀标采购这样的大业务。
--%>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@page import="com.hotent.core.util.ContextUtil" %>
<html lang="zh-CN">
<head>
    <title>数据快照查看</title>
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
            var frm = $('#privateDataForm').form();
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
                        window.location.href = "${ctx}/datadriver/privatedata/list.ht?id=${taskId}";
                    }
                });
            } else {
                $.ligerMessageBox.error("提示信息", obj.getMessage());
            }
        }

    </script>

</head>
<body>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
    </ul>
    <div class="layui-tab-content">
        <form id="dataShot" name="dataShot" method="post" action="" enctype="multipart/form-data">
            <table id="AddHandlingFee" class="layui-table" cellpadding="0" cellspacing="0" border="0" type="main">
                <tr>
                    <th width="20%">数据名称:</th>
                    <td><input type="text" id="ddDataName" name="ddDataName" value="${dataShot.ddDataName}"
                               class="layui-input"/></td>
                    <th width="20%">灵敏阈:</th>
                    <td><input type="text" id="ddDataSensitiveness" name="ddDataSensitiveness"
                               value="${dataShot.ddDataSensitiveness}" class="layui-input"/></td>
                </tr>
                <tr>
                    <th width="20%">数据类型:</th>
                    <td><input type="text" id="ddDataType" name="ddDataType" value="${dataShot.ddDataType}"
                               class="layui-input"/></td>
                    <th width="20%">数据描述:</th>
                    <td><input type="text" id="ddDataDescription" name="ddDataDescription"
                               validate="{required:false}" class="layui-input"
                               value="${dataShot.ddDataDescription}"/>
                    </td>
                </tr>
                <tr>
                    <th width="20%">所属任务:</th>
                    <td>
                        <input type="text" id="ddDataTaskName" name="ddDataTaskName" value="${dataShot.ddDataTaskName}"
                               class="layui-input"/>
                    </td>
                    <th width="20%">数据可见性:</th>
                    <td><input type="text" id="ddDataPublishType" name="ddDataPublishType"
                               value="${dataShot.ddDataPublishType}" class="layui-input"/></td>
                </tr>

                <tr>
                    <th width="20%">数据快照值:</th>
                    <td><input type="text" id="ddDataValue" name="ddDataValue"
                               value="${dataShot.ddDataValue}" class="layui-input"/></td>
                    <th width="20%">数据订阅状态:</th>
                    <td><input type="text" id="ddDataSubmiteState" name="ddDataSubmiteState"
                               value="${dataShot.ddDataSubmiteState}" class="layui-input"/></td>
                </tr>
                <tr>
                    <th width="20%">创建人:</th>
                    <td><input type="text" id="ddDataCreatePerson" name="ddDataCreatePerson"
                               value="${dataShot.ddDataCreatePerson}" class="layui-input"/></td>
                    <th width="20%">创建时间:</th>
                    <td><input type="text" id="ddDataCreateTime" name="ddDataCreateTime"
                               value="${dataShot.ddDataCreateTime}" class="layui-input"/></td>
                </tr>
                <tr>
                    <th width="20%">是否交付:</th>
                    <td><input type="text" id="ddDataIsDelivery" name="ddDataIsDelivery"
                               value="${dataShot.ddDataIsDelivery}" class="layui-input"/></td>
                    <th width="20%">数据标签:</th>
                    <td><input type="text" id="ddDataTag" name="ddDataTag"
                               value="${dataShot.ddDataTag}" class="layui-input"/></td>
                </tr>
                <tr>
                    <th width="20%">快照时间:</th>
                    <td><input type="text" id="ddSnapshotPersonId" name="ddSnapshotPersonId"
                               value="${dataShot.ddSnapshotPersonId}" class="layui-input"/></td>
                    <th width="20%">快照时间:</th>
                    <td><input type="text" id="ddSnapshotTime" name="ddSnapshotTime"
                               value="${dataShot.ddSnapshotTime}" class="layui-input"/></td>
                </tr>
            </table>
            <input type="hidden" name="ddDataId" id="ddDataId" value="${dataShot.ddDataId}">
            <input type="hidden" name="ddDataTaskId" id="ddDataTaskId" value="${dataShot.ddDataTaskId}"/>
            <input type="hidden" name="ddDataSnapShotId" id="ddDataSnapShotId" value="${dataShot.ddDataSnapShotId}"/>
        </form>
    </div>
</div>
</body>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</html>
