<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2017/1/3
  Time: 下午5:17
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ taglib prefix="ap" uri="/appleTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>指标编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <style>
        .nav-myself {
            padding-left: 0px !important;
            padding-right: 0px !important;
        }
    </style>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">指标创建</h4>
</div>
<div class="modal-body">
    <div class="container-fluid nav-myself">
        <div class="col-xs-2 nav-myself">
            <ul class="nav nav-pills nav-stacked" role="tablist">
                <li role="presentation" class="active"><a href="#addIndex" role="tab" data-toggle="tab">新建指标</a></li>
                <li role="presentation"><a href="#exportIndexXml" role="tab" data-toggle="tab">批量导入</a></li>
            </ul>
        </div>
        <div class="col-xs-10">
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="addIndex">
                    <form id="indexform" name="indexform" method="post" action="${ctx}/datadriver/index/save.ht"
                          enctype="multipart/form-data">
                        <table id="AddHandlingFee" class="table table-bordered" cellpadding="0" cellspacing="0"
                               border="0"
                               type="main">
                            <tr>
                                <th width="20%">指标名称:</th>
                                <td><input type="text" id="ddIndexName" name="ddIndexName"
                                           value="" class="form-control"/></td>
                            </tr>
                            <tr>
                                <th width="20%">指标类型:</th>
                                <td>
                                    <ap:selectDB name="ddIndexTypeId" id="ddIndexTypeId"
                                                 where="parentId=10000025100454" optionValue="itemValue"
                                                 optionText="itemName" table="SYS_DIC"
                                                 selectedValue="${indexInfo.ddIndexTypeId}" styleClass="form-control">
                                    </ap:selectDB>
                                </td>
                            </tr>
                            <tr>
                                <th width="20%">指标属性:</th>
                                <td>
                                    <ap:selectDB name="ddIndexProperty" id="ddIndexProperty"
                                                 where="parentId=10000025100454" optionValue="itemValue"
                                                 optionText="itemName" table="SYS_DIC"
                                                 selectedValue="${indexInfo.ddIndexTypeId}" styleClass="form-control">
                                    </ap:selectDB>
                                </td>
                            </tr>
                            <%--<tr>--%>
                            <%--<th width="20%">最优值:</th>--%>
                            <%--<td><input type="text" id="ddIndexOptimum" name="ddIndexOptimum"--%>
                            <%--value="" class="form-control"/></td>--%>
                            <%--</tr>--%>
                            <tr>
                                <th width="20%">值域:</th>
                                <td><input type="text" id="ddIndexLastestValue" name="ddIndexLastestValue"
                                           value="" class="form-control"/></td>
                            </tr>
                        </table>
                        <input id="ddIndexProjectId" name="ddIndexProjectId" type="hidden" value="${projectId}"/>
                    </form>
                    <div class="col-xs-12">
                        <a href="javascript:void(0)" class="btn btn-success btn-block" id="dataFormSave"><span
                                class="glyphicon glyphicon-ok"></span> 创建指标项</a>
                    </div>
                </div>

                <div role="tabpanel" class="tab-pane" id="exportIndexXml">
                    <div class="row">
                        <form class="form-horizontal" name="excelImportForm"
                              action="${ctx}/datadriver/privatedata/importIndexXml.ht" id="uploadForm"
                              method="post" enctype="multipart/form-data">
                            <div class="modal-body">
                                <div class="alert alert-danger">导入excel文件格式必须严格按照<a class="alert-link"
                                                                                    href="${ctx}/platform/system/sysFile/download.ht?fileId=10000026780072">上传文件格式(点击查看)</a>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <input class="btn btn-default btn-block" id="excel_file" type="file"
                                               name="filename"
                                               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input class="btn btn-success btn-block" id="excel_button" type="submit"
                                       value="导入Excel"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        var frm = $('#indexform').form();
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
            $('#addindex1').modal('hide');
            $('#indextable').bootstrapTable('refresh');
        } else {

        }
    }
</script>
</body>
</html>
