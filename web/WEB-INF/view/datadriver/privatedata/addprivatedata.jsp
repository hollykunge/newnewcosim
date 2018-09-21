<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: dodo
  Date: 2017/2/16
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
    <%--<%@include file="/commons/include/form.jsp" %>--%>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <title>添加私有数据</title>
    <style>
        #privateData {
            margin: 0px;
        }

        #addstructtd {
            padding: 0px;
        }
    </style>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">私有数据创建</h4>
</div>
<div class="modal-body">
    <div class="container-fluid">
        <table id="tab11" style="display: none">
            <tbody>
            <tr type="subdata">
                <td height="30" align="center">
                    <input type="text" name="NO" size="1" value="1" class="form-control" readonly/></td>
                <td align="center">
                    <input type="text" id="ddDataName" name="ddDataName" class="form-control"/></td>
                <td align="center">
                    <select name="ddDataType" class="form-control">
                        <option value="0">数值</option>
                        <%--<option value="1">结构型数据</option>--%>
                        <option value="2">文件</option>
                        <option value="3">模型</option>
                    </select>
                </td>
                <td align="center">
                    <ap:selectDB name="ddDataUnit" id="ddDataUnit"
                                 where="parentId=10000028500024" optionValue="itemValue"
                                 optionText="itemName" table="SYS_DIC"
                                 styleClass="form-control" selectedValue="">
                    </ap:selectDB>
                </td>
                <td align="center">
                    <input type="text" name="ddDataDescription" class="form-control"/></td>
                <td>
                    <input class="btn btn-default btn-sm" type="button" id="Button1" onClick="deltr(this)"
                           value="删行">
                </td>
            </tr>
            </tbody>
        </table>
        <form id="dataStructForm" method="post" action="${ctx}/datadriver/privatedata/save.ht">
            <table class="table table-bordered" cellpadding="0" cellspacing="0"
                   border="0"
                   type="main">
                <tr>
                    <th width="20%">数据名称:</th>
                    <td><input type="text" id="ddDataName1" name="ddDataName1"
                               value="" class="form-control" placeholder="请输入数据名称"/></td>
                    <%--<th width="20%">数据所属任务:</th>--%>
                    <%--<td><input type="text" id="ddDataTaskName" name="ddDataTaskName"--%>
                    <%--value="${taskInfo.ddTaskName}" class="form-control" readonly/></td>--%>
                </tr>
                <tr id="changemodelfile">
                    <th width="20%">数据类型:</th>
                    <td>
                        <ap:selectDB name="ddDataType" id="ddDataType"
                                     where="parentId=10000025100454" optionValue="itemValue"
                                     optionText="itemName" table="SYS_DIC"
                                     styleClass="form-control"
                                     onChange="change()" selectedValue="">
                        </ap:selectDB>
                    </td>
                    <%--<th width="20%">数据创建时间:</th>--%>
                    <%--<td><input type="text" id="ddDataCreateTime" name="ddDataCreateTime"--%>
                    <%--value="${currentTime}" readonly class="form_datetime form-control"/></td>--%>
                    <%--<th width="20%">数据敏感度:</th>--%>
                    <%--<td><input type="text" id="ddDataSensitiveness" name="ddDataSensitiveness"--%>
                    <%--value=""--%>
                    <%--class="form-control"/></td>--%>
                </tr>
                <tr id="initUnit">
                    <th width="20%">数据单位:</th>
                    <td>
                        <ap:selectDB name="ddDataUnit" id="ddDataUnit"
                                     where="parentId=10000028500024" optionValue="itemValue"
                                     optionText="itemName" table="SYS_DIC"
                                     styleClass="form-control" selectedValue="">
                        </ap:selectDB>
                    </td>
                </tr>
                <tr>

                    <%--<th width="20%">数据创建人:</th>--%>
                    <%--<td><input type="text" id="ddDataCreatePerson" name="ddDataCreatePerson"--%>
                    <%--value="${sysName}" readonly class="form-control"/></td>--%>

                    <%--<tr id="selectModeltr">--%>
                    <%--<th width="20%">请选择文件:</th>--%>
                    <%--<td colspan="5">--%>
                    <%--<select id="selectModel" name="ddDataLastestValue" class="form-control">--%>
                    <%--<c:forEach items="${modelCenterModelList}" var="modelCenterModelItem">--%>
                    <%--<option value="${modelCenterModelItem.ddModelName}">${modelCenterModelItem.ddModelName}</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                <tr id="initValue">
                    <th width="20%">创建数据结构:</th>
                    <td colspan="5" id="addstructtd">
                        <table id="privateData" width="700" border="0" cellspacing="0" cellpadding="0" type="sub"
                               formType="page">
                            <thead>
                            <tr>
                                <td height="20" align="center" bgcolor="#FFFCCC">序号</td>
                                <td align="center" bgcolor="#FFFCCC">数据名称</td>
                                <td align="center" bgcolor="#FFFCCC">数据类型</td>
                                <td align="center" bgcolor="#FFFCCC">单位</td>
                                <td align="center" bgcolor="#FFFCCC">备注</td>
                                <td><input class="btn btn-success btn-sm" type="button" id="btn_addtr" value="增行"></td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr type="subdata">
                                <td height="30" align="center"><input type="text" name="NO" size="1" value="1"
                                                                      class="form-control" readonly/></td>
                                <td align="center"><input type="text" name="ddDataName" class="form-control"/></td>
                                <td align="center">
                                    <%--<ap:selectDB name="ddDataType" id="ddDataType"--%>
                                    <%--where="parentId=10000025100454" optionValue="itemValue"--%>
                                    <%--optionText="itemName" table="SYS_DIC"--%>
                                    <%--selectedValue="${indexInfo.ddIndexTypeId}"--%>
                                    <%--styleClass="form-control"></ap:selectDB>--%>
                                    <select name="ddDataType" class="form-control">
                                        <option value="0">数值</option>
                                        <%--<option value="1">结构型数据</option>--%>
                                        <option value="2">文件</option>
                                        <option value="3">模型</option>
                                    </select>
                                </td>
                                <td align="center">
                                    <ap:selectDB name="ddDataUnit" id="ddDataUnit"
                                                 where="parentId=10000028500024" optionValue="itemValue"
                                                 optionText="itemName" table="SYS_DIC"
                                                 styleClass="form-control" selectedValue="">
                                    </ap:selectDB>
                                </td>
                                <td align="center"><input type="text" name="ddDataDescription" class="form-control"/>
                                </td>
                                <td><input class="btn btn-default btn-sm" type="button" id="Button2"
                                           onClick="deltr(this)" value="删行"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th width="20%">备注:</th>
                    <td colspan="3"><textarea id="ddDescription" name="ddDescription"
                                              value="" class="form-control"
                                              rows="3"/></textarea>
                    </td>
                </tr>
                <input type="hidden" id="ddDataTaskId" name="ddDataTaskId"
                       value="${taskInfo.ddTaskId}"/>
            </table>
        </form>
        <div class="row">
            <button class="btn btn-primary btn-block" id="dataFormSave">创建新私有数据</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //@ sourceURL=addprivatedata.ht
    $(function () {
        $('#initUnit').hide();
        var show_count = 20;   //要显示的条数
        var count = 1;    //递增的开始值，这里是你的ID
        $("#btn_addtr").click(function () {

            var length = $("#privateData tbody tr").length;
            //alert(length);
            if (length < show_count)    //点击时候，如果当前的数字小于递增结束的条件
            {
                $("#tab11 tbody tr").clone().appendTo("#privateData tbody");   //在表格后面添加一行
                changeIndex();//更新行号
            }
        });
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                $('#adddata').modal('hide');
            } else {
            }
        }

        var frm = $('#dataStructForm').form();
        $("#dataFormSave").click(function () {
            var form = $('#dataStructForm').setData();
            form.ajaxForm(options);
            if (frm.valid()) {
                form.submit();
            }
        });
        $("#ddType").change(function () {
            if ($('#ddType option:selected').val() == 2) {
                $('#subdata').remove();
            } else if ($('#ddType option:selected').val() == 3) {
                $('#subdata').hide();
            } else if ($('#ddType option:selected').val() == 0) {
                $('#subdata').hide();
            } else {
                $('#subdata').show();
            }
        });
    });

    function changeIndex() {
        var i = 1;
        $("#privateData tbody tr").each(function () { //循环tab tbody下的tr
            $(this).find("input[name='NO']").val(i++);//更新行号
        });
    }

    function deltr(opp) {
        var length = $("#privateData tbody tr").length;
        //alert(length);
        if (length <= 1) {
            alert("至少保留一行");
        } else {
            $(opp).parent().parent().remove();//移除当前行
            changeIndex();
        }
    }
    function change() {
        var value_change = $("#ddType").val();
        if (value_change == "2") {
            $('#initValue').hide();
            $('#initUnit').hide();
        } else if (value_change == "3") {
            $('#initValue').hide();
            $('#initUnit').hide();
        } else if (value_change == "0") {
            $('#initValue').hide();
            $('#initUnit').show();
        } else {
            $('#initValue').show();
            $('#initUnit').hide();
        }
    }
</script>
<script type="text/javascript" src="${ctx}/timeselect/bootstrap-datetimepicker.zh-CN.js"></script>
</html>
