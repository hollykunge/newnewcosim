<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>项目基础信息添加</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">

    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.ext.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>

    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        $(function () {
//            var options = {};
//            if (showResponse) {
//                options.success = showResponse;
//            }
            var frm = $('#userForm2').form();
            $("#dataFormSave").click(function () {
                frm.setData();
//                frm.ajaxForm(options);
                if (frm.valid()) {
                    form.submit();

                    $("#addindex1").modal('hide');
                    $("#tb_departments").bootstrapTable("refresh");
                }
            });
        });

//        function showResponse(responseText) {
//            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//        }

    </script>
    <style type="text/css">
        html, body {
            padding: 0px;
            margin: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
        }
    </style>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">专业工具上传</h4>
</div>
<div class="modal-body">
    <form name="userForm2" id="userForm2"
          action="${ctx}/datadriver/tool/save.ht?major=<%=new String(request.getParameter("major").getBytes("ISO-8859-1"),"UtF-8")%>"
          enctype="multipart/form-data" method="post">
        <table id="AddHandlingFee" class="table" cellpadding="0" cellspacing="0"
               border="0"
               type="main">
            <tr>
                <th width="20%">工具名称:</th>
                <td><input type="text" id="ddToolName" name="ddToolName"
                           value="" class="form-control"/></td>
            </tr>
            <tr>
                <th width="20%">工具版本:</th>
                <td><input type="text" id="ddToolVersion" name="ddToolVersion"
                           value="" class="form-control"/></td>
            </tr>
            <tr>
                <th width="20%">工具状态:</th>
                <td>
                    <div class="radio">
                        <input style="margin-left: 3px" type="radio" name="ddToolBf2" id="ddToolBf21" value="1" checked>
                        <label for="ddToolBf21">
                            工具新增
                        </label>
                        <input style="margin-left: 3px" type="radio" name="ddToolBf2" id="ddToolBf20" value="0">
                        <label for="ddToolBf20">
                            工具更新
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <th width="20%">工具说明:</th>
                <td colspan="3"><textarea class="form-control" rows="4" id="ddToolBf" name="ddToolBf"></textarea></td>
            </tr>
            <tr id="newUpload2">
                <th width="20%">上传工具:</th>
                <td><input type="file" name="file" value="上传"></td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer">
    <input id="dataFormSave" value="上传" type="submit" class="btn btn-primary btn-block">
</div>
</body>
</html>