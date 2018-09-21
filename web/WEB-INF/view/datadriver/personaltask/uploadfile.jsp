<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/3/2
  Time: 下午2:07
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>文件和模型上传</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
</head>
<script type="text/javascript">
    $(function () {
        var options = {};
        var frm = $('#userForm2').form();
        $("#dataFormSave").click(function () {
            frm.setData();
            frm.ajaxForm(options);
            if (frm.valid()) {
                form.submit();
                $("#fileupload").modal('hide');
                $("#tb_departments").bootstrapTable("refresh");
            }
        });
    });

</script>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">模型和文件上传窗口</h4>
</div>
<div class="modal-body">
    <div class="container-fluid">
        <form name="userForm2" id="userForm2"
              action="${ctx}/datadriver/modelcenter/save.ht?&id=<%=request.getParameter("id")%>"
              enctype="multipart/form-data" method="post">
            <table id="AddHandlingFee" class="table table-striped" cellpadding="0" cellspacing="0"
                   border="0"
                   type="main">
                <tr>
                    <th width="20%">模型名称:</th>
                    <td><input type="text" id="ddModelName" name="ddModelName"
                               value="" class="form-control"/></td>
                </tr>
                <tr>
                    <th width="20%">模型版本:</th>
                    <td><input type="text" id="ddModelVersion" name="ddModelVersion"
                               value="" class="form-control"/></td>
                </tr>
                <tr>
                    <th width="20%">模型状态:</th>
                    <td>
                        <div class="radio radio-info radio-inline">
                            <input type="radio" name="ddModelBf2" id="ddModelBf21" value="1" checked>
                            <label for="ddModelBf21">
                                模型上传
                            </label>
                            <br>
                            <input type="radio" name="ddModelBf2" id="ddModelBf20" value="0">
                            <label for="ddModelBf20">
                                模型更新
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="20%">模型说明:</th>
                    <td colspan="3"><textarea class="form-control" rows="4" id="DdModelExplain"
                                              name="DdModelExplain"></textarea></td>
                </tr>
                <tr id="newUpload2">
                    <th width="20%">上传模型:</th>
                    <td><input type="file" name="file" value="上传"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="modal-footer">
    <input id="dataFormSave" value="上传" type="submit" class="btn btn-success btn-block">
</div>
</body>
</html>
