<!DOCTYPE html>
<%@ taglib prefix="ap" uri="/appleTag" %>
<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/1/16
  Time: 上午11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>创建项目</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>
    <%@include file="/newtable/tablecontext.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body>
<div class="container">
    <div class="col-xs-8 col-xs-offset-2">
        <h2>创建一个新项目</h2>
        <p></p>
        <hr>
        <form class="form-horizontal" method="post" action="save.ht" id="createprojectform" name="createprojectform"
              enctype="multipart/form-data">
            <table id="AddHandlingFee" cellpadding="0" cellspacing="0"
                   border="0"
                   type="main" class="table">
                <tr>
                    <th>项目名称
                        <small style="color: #e2463e">(必填)</small>
                    </th>
                    <td>
                        <input type="text" class="form-control" placeholder="项目名称" value="" id="ddProjectName"
                               name="ddProjectName" validate="{required:true}">
                    </td>
                </tr>
                <%--<tr>--%>
                <%--&lt;%&ndash;所属型号&ndash;%&gt;--%>
                <%--<th>所属型号<small style="color: #e2463e">(必填)</small>:</th>--%>
                <%--<td>--%>
                <%--<ap:selectDB name="ddProjectBelongModel" id="ddProjectBelongModel"--%>
                <%--where="parentId=10000027440014" optionValue="itemValue"--%>
                <%--optionText="itemName" table="SYS_DIC"--%>
                <%--selectedValue="${projectBelongModelItem.id}" styleClass="form-control">--%>
                <%--</ap:selectDB>--%>
                <%--</td>--%>

                <%--</tr>--%>
                <tr>
                    <%--密级--%>
                    <th>密级
                        <small style="color: #e2463e">(必填)</small>
                        :
                    </th>
                    <td>
                        <select id="ddProjectSecretLevel" name="ddProjectSecretLevel" class="form-control" validate="{required:true}">
                            <option value="fm" <c:if test="${psnSecretLevelCode < 60 }">style="display:none"</c:if>>非密</option>
                            <option value="nb" <c:if test="${psnSecretLevelCode <= 60 }">style="display:none"</c:if>>内部</option>
                            <option value="mm" <c:if test="${psnSecretLevelCode <= 65 }">style="display:none"</c:if>>秘密</option>
                            <option value="jm" <c:if test="${psnSecretLevelCode <= 70 }">style="display:none"</c:if>>机密</option>
                        </select>
                    </td>
                </tr>
                <%--<tr>--%>
                <%--&lt;%&ndash;项目类型&ndash;%&gt;--%>
                <%--<th>项目类型<small style="color: #e2463e">(必填)</small>:</th>--%>
                <%--<td>--%>
                <%--<ap:selectDB name="ddProjectType" id="ddProjectType"--%>
                <%--where="parentId=10000027440025" optionValue="itemValue"--%>
                <%--optionText="itemName" table="SYS_DIC"--%>
                <%--selectedValue="${projectTypeListItem.id}" styleClass="form-control">--%>
                <%--</ap:selectDB>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <%--项目阶段--%>
                    <th>项目阶段
                        <small style="color: #e2463e">(必填)</small>
                        :
                    </th>
                    <td>
                        <ap:selectDB name="ddProjectPhaseName" id="ddProjectPhaseName"
                                     where="parentId=10000022110011" optionValue="itemValue"
                                     optionText="itemName" table="SYS_DIC"
                                     selectedValue="${projectPhaseItem.id}" styleClass="form-control">
                        </ap:selectDB>
                    </td>
                </tr>
                <tr>
                    <th>项目描述
                        <small>(选填)</small>
                        :
                    </th>
                    <td>
                <textarea class="form-control" rows="5" placeholder="项目简介" id="ddProjectDescription"
                          name="ddProjectDescription"></textarea>
                    </td>
                </tr>
            </table>
        </form>
        <hr>
        <div class="form-group pull-right">
            <button type="button" class="btn btn-success" id="dataFormSave">完成并创建</button>
        </div>
    </div>

</div>

</body>
<script type="text/javascript">
    $(function () {
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        var frm = $('#createprojectform').form();
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
            window.location.href = "${ctx}/datadriver/project/list.ht";
        } else {

        }
    }
</script>
</html>
