<%@ page import="com.hotent.core.util.UniqueIdUtil" %><%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/3/5
  Time: 下午7:57
  To change this template use File | Settings | File Templates.
--%>
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
    <title>数据看板</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
</head>
<body>
<%--<div class="col-xs-12">--%>
<%--<div class="pull-right">--%>
<%--<input type="button" value="导出XML" id='xmlExport'/>--%>
<%--<input type="button" value="导出Excel" id='excelExport'/>--%>
<%--<input type="button" value="导出CSV" id='csvExport'/>--%>
<%--<input type="button" value="导出JSON" id='jsonExport'/>--%>
<%--<input type="button" value="导出PDF" id='pdfExport'/>--%>
<%--</div>--%>
<%--</div>--%>
<%--<br>--%>
<style>
    .max {
        color: black \9;
        background-color: #fcffc6 \9;
    }

    .avg {
        color: black \9;
        background-color: #f8e984 \9;
    }

    .minavg {
        color: black \9;
        background-color: #f9806f \9;
    }

    .min {
        color: black \9;
        background-color: #ffced9 \9;
    }

    .max:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .max:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
        color: black;
        background-color: #fcffc6;
    }

    .avg:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .avg:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
        color: black;
        background-color: #f8e984;
    }

    .minavg:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .minavg:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
        color: black;
        background-color: #f9806f;
    }

    .min:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .min:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
        color: black;
        background-color: #ffced9;
    }
</style>
<div id="treeGridOut" style="width: 100%"></div>
<div id='DataItemMenu'>
    <ul>
        <li id="addSubDataLi">添加子数据</li>
        <%--<li id="uploadFileLi">上传文件</li>--%>
    </ul>
</div>
<%--导入数据--%>
<div class="modal fade" id="importDataFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<%--上传私有数据文件或模型--%>
<div class="modal fade" id="uploadPrivateFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>

<%--错误提示--%>
<div id="jqxNotificationNull" style="display: none">
    未选择目标！
</div>
<div id="jqxNotificationTroppo" style="display: none">
    一次只能选择一条目标！
</div>
<div id="jqxNotificationType" style="display: none">
    请选择类型为模型或文件的目标！
</div>
</body>
<script type="text/javascript" src="${ctx}/jqwidgets/table/output.js"></script>
<script type="text/javascript">
    //@ sourceURL=showdata.ht
    $(function () {
        outputTableInit("${ctx}/datadriver/privatedata/outputData.ht?taskId=${taskId}", ${taskId}, ${projectId}, "${taskName}", ${type});

        $("#adddata").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
        });

        $("#exportExcelOut").click(function () {
            $("#treeGridOut").jqxTreeGrid('exportData', 'xls');
        });
        $("#exportCVSOut").click(function () {
            $("#treeGridOut").jqxTreeGrid('exportData', 'csv');
        });

        //下载模型文件
        $("#downloadFile").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
            for (var i = 0; i < selection.length; i++) {
                window.location.href = "${ctx}/datadriver/privatedata/getPrivatefile.ht?id=" + selection[i].dataId;
            }
        });

        var contextMenu = $("#DataItemMenu").jqxMenu({width: 200, height: 58, autoOpenPopup: false, mode: 'popup'});
        $("#treeGridOut").on('contextmenu', function () {
            return false;
        });
        $("#treeGridOut").on('rowClick', function (event) {
            var args = event.args;

            if (args.originalEvent.button == 2) {
                if (args.row.dataType != '模型' || args.row.dataType != '文件') {
                    // $('#Menu').jqxMenu('disable', 'uploadFileLi', false);
                } else {
                    // $('#Menu').jqxMenu('disable', 'uploadFileLi', true);
                }
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
                return false;
            }
        });
    });

    //对话框关闭清除缓存
    $("#uploadPrivateFile").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
    //对话框关闭清除缓存
    $("#importDataFile").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
</script>

</html>