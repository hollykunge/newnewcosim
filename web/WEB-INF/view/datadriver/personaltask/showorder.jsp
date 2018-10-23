<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="ap" uri="/appleTag" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>数据发布列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
</head>
<body>
<%--<div class="col-xs-12">--%>
<%--<div class="pull-right">--%>
<%--<input type="button" value="导出Excel" id='excelExportIn'/>--%>
<%--<input type="button" value="导出XML" id='xmlExportIn'/>--%>
<%--<input type="button" value="导出CSV" id='csvExportIn'/>--%>
<%--<input type="button" value="导出JSON" id='jsonExportIn'/>--%>
<%--<input type="button" value="导出PDF" id='pdfExportIn'/>--%>
<%--</div>--%>
<%--</div>--%>
</br>
<div id="treeGridIn" style="width: 100%;"></div>
<%--模型预览--%>
<div class="modal fade" id="model_content" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>

<%--错误提示--%>
<div id="jqxNotificationModel" style="display: none">
    只能预览模型数据！
</div>
</body>
<script type="text/javascript" src="${ctx}/jqwidgets/table/input.js"></script>
<script type="text/javascript">
    //@ sourceURL=showorder.ht
    $(function () {
        $("#exportExcelIn").click(function () {
            $("#treeGridIn").jqxTreeGrid('exportData', 'xls');
        });
        $("#exportCVSIn").click(function () {
            $("#treeGridIn").jqxTreeGrid('exportData', 'csv');
        });
        inputTableInit("${ctx}/datadriver/privatedata/inputData.ht?taskId=${taskId}&projectId=${projectId}", ${taskId});
    });
    //下载文件和模型
    function downloadF(dataId) {
        window.location.href = "${ctx}/datadriver/privatedata/getPrivatefile.ht?id=" + dataId;
    }
    //对话框关闭清除缓存
    $("#uploadPrivateFile").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
    //对话框关闭清除缓存
    $("#model_content").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
</script>
</html>
