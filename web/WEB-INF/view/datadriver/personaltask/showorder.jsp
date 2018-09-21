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
    <script type="text/javascript" src="${ctx}/jqwidgets/table/inputable.js"></script>
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
<div class="btn-group" role="group" aria-label="..." style="margin-bottom: 5px;margin-top: 5px">
    <button type="button" class="btn btn-sm btn-success" id="selectedOrder"><span
            class="glyphicon glyphicon-check"></span> 订阅数据
    </button>
    <div class="btn-group" role="group">
        <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="false"><span class="glyphicon glyphicon-export"></span>
            导出数据
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li><a href="javascript:void(0)" id="exportExcelIn">Excel格式</a></li>
            <li><a href="javascript:void(0)" id="exportCVSIn">CVS格式</a></li>
        </ul>
    </div>
    <button type="button" class="btn btn-sm btn-default" id="download1"><span
            class="glyphicon glyphicon-download"></span> 下载模型
    </button>
</div>
<div id="treeGridIn" style="width: 100%;"></div>
<%--模型预览--%>
<div class="modal fade" id="model_content" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    //@ sourceURL=showorder.ht
    $(function () {
        $("#exportExcelIn").click(function () {
            $("#treeGridIn").jqxTreeGrid('exportData', 'xls');
        });
        $("#exportCVSIn").click(function () {
            $("#treeGridIn").jqxTreeGrid('exportData', 'csv');
        });
        $("#unselectRow").click(function () {
            if (!unselectRow.jqxButton('disabled')) {
                $("#treeGridIn").jqxTreeGrid('unselectRow', rowKey);
                rowKey = null;
            }
        });
        //下载模型文件
        $("#download1").click(function () {
            var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
            for (var i = 0; i < selection.length; i++) {
                downloadF(selection[i].dataId);
            }
        });
        //订阅数据批量发布
        $("#selectedOrder").click(function () {
            var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
            var rowsDataIds = new Array();
            for (var i = 0; i < selection.length; i++) {
                if (selection[i].torderState == 0) {
                    rowsDataIds.push(selection[i].dataId);
                }
            }
            $.get("canOrderToOrder.ht?dataIds=" + rowsDataIds + "&parent=orderpanel" + "&taskId=${taskId}", function (data, status) {
                if (status == 'success') {
                    window.location.reload();
                }
            });
        });
        inputTableInit("${ctx}/datadriver/privatedata/inputData.ht?taskId=${taskId}&projectId=${projectId}", ${taskId});
    });
    //Excel批量导入
    function importPrivateData(taskId, projectId) {
        $('#importData').modal({
            keyboard: true,
            remote: "${ctx}/datadriver/privatedata/importPrivateData.ht?id=" + taskId + "&projectId=" + projectId
        });
    }
    //对话框关闭清除缓存
    $("#uploadPrivateFile").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
    //对话框关闭清除缓存
    $("#model_content").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    function downloadF(dataId) {
//        window.location.href = "getPrivatefile.ht?id=" + dataId;
        window.location.href = "${ctx}/datadriver/privatedata/getPrivatefile.ht?id=" + dataId;
    }
</script>
</html>
