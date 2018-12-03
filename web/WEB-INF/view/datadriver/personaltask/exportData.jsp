<%--
  Created by IntelliJ IDEA.
  User: holly
  Date: 2018/12/3
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>导出数据</title>
    <script>
        $(function () {
            // var options = {};
            // if (showResponse) {
            //     options.success = showResponse;
            // }
            // var frm = $('#exportForm').form();
            // $("#export_button").click(function () {
            //     frm.setData();
            //     frm.ajaxForm(options);
            //     if (frm.valid()) {
            //         form.submit();
            //     }
            //     $("#exportAllData").modal('hide');
            //
            // });
            $("#export_button").click(function () {
                window.location.href = "${ctx}/datadriver/privatedata/exportDataFile.ht?id=${taskId}&dataAttr=${dataIds}";
                $("#exportAllData").modal('hide');
            });
        });

        // function showResponse(responseText) {
        //     var obj = new com.hotent.form.ResultMessage(responseText);
        //     if (obj.isSuccess()) {
        //         var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        //     } else {
        //
        //     }
        // }
    </script>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title" id="myModalLabel">导出excel数据</h4>
</div>
<form class="form-horizontal" name="excelExportForm"
      action="${ctx}/datadriver/privatedata/exportDataFile.ht?id=${taskId}&dataAttr=${dataIds}" id="exportForm"
      method="post" enctype="multipart/form-data">
    <div class="modal-body">
        <table id="AddHandlingFee" class="table table-striped" cellpadding="0" cellspacing="0"
               border="0"
               type="main">
            <tr>
                <th>密级
                    <small style="color: #e2463e">(必填)</small>
                    :
                </th>
                <td>
                    <select id="secretLevel" name="secretLevel" class="form-control"
                            validate="{required:true}">
                        <option value="fm" <c:if test="${psnSecretLevelCode < 60 }">style="display:none"</c:if>>非密
                        </option>
                        <option value="nb" <c:if test="${psnSecretLevelCode <= 60 }">style="display:none"</c:if>>内部
                        </option>
                        <option value="mm" <c:if test="${psnSecretLevelCode <= 65 }">style="display:none"</c:if>>秘密
                        </option>
                        <option value="jm" <c:if test="${psnSecretLevelCode <= 70 }">style="display:none"</c:if>>机密
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>类型
                    <small style="color: #e2463e">(默认全部)</small>
                    :
                </th>
                <td>
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-primary active">
                            <input value="0" type="radio" name="type" id="optionAll" autocomplete="off" checked> 全部
                        </label>
                        <label class="btn btn-primary">
                            <input value="1" type="radio" name="type" id="optionSelected" autocomplete="off"> 已选择
                        </label>
                        <label class="btn btn-primary">
                            <input value="2" type="radio" name="type" id="optionOrder" autocomplete="off"> 已订阅
                        </label>
                    </div>
                </td>
            </tr>
        </table>

    </div>
    <div class="modal-footer">
        <button class="btn btn-success" id="export_button">导出数据</button>
    </div>
</form>
</body>
</html>
