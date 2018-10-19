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
    <%--<link href="${ctx}/jqwidgets/table/data.cell.css"/>--%>

    <%--<script type="text/javascript" src="${ctx}/jqwidgets/scripts/demos.js"></script>--%>
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
<%--<a class="btn btn-sm btn-default" href="javascript:void(0)"--%>
<%--onclick="importPrivateData(${taskId}, ${projectId})"><span--%>
<%--class="glyphicon glyphicon-import"></span> 批量导入</a>--%>
<div class="tableCtrl">
    <%--<div class="btn-group" role="group" aria-label="..." style="margin-right: 12px">--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="addPrivateData"><span--%>
                <%--class="glyphicon glyphicon-plus"></span> 添加数据--%>
        <%--</button>--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="cancelEditPrivate"><span--%>
                <%--class="glyphicon glyphicon-remove"></span> 取消编辑--%>
        <%--</button>--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="deletePrivateData"><span--%>
                <%--class="glyphicon glyphicon-trash"></span> 删除--%>
        <%--</button>--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="cancelSelected"><span--%>
                <%--class="glyphicon glyphicon-remove-circle"></span> 取消选择--%>
        <%--</button>--%>
    <%--</div>--%>
    <%--<div class="btn-group" role="group" aria-label="..." style="margin-right: 12px">--%>

        <%--<button type="button" class="btn btn-sm btn-default" id="publishPrivateData"><span--%>
                <%--class="glyphicon glyphicon-share"></span> 发布数据--%>
        <%--</button>--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="recylePrivateData"><span--%>
                <%--class="glyphicon glyphicon-remove-sign"></span> 收回数据--%>
        <%--</button>--%>
    <%--</div>--%>
    <%--<div class="btn-group" role="group" aria-label="..." style="margin-right: 12px">--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="uploadFile"><span--%>
                <%--class="glyphicon glyphicon-upload"></span> 上传模型--%>
        <%--</button>--%>
        <%--<button type="button" class="btn btn-sm btn-default" id="downloadFile"><span--%>
                <%--class="glyphicon glyphicon-download"></span> 下载模型--%>
        <%--</button>--%>
    <%--</div>--%>
    <%--<div class="btn-group" role="group" aria-label="..." style="margin-right: 12px">--%>
        <%--<button type="button" class="btn btn-sm btn-default" onclick="importPrivateData(${taskId}, ${projectId})"><span--%>
                <%--class="glyphicon glyphicon-import"></span> 导入数据--%>
        <%--</button>--%>

        <%--<div class="btn-group" role="group">--%>
            <%--<button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown"--%>
                    <%--aria-haspopup="true"--%>
                    <%--aria-expanded="false"><span class="glyphicon glyphicon-export"></span>--%>
                <%--导出数据--%>
                <%--<span class="caret"></span>--%>
            <%--</button>--%>
            <%--<ul class="dropdown-menu">--%>
                <%--<li><a href="javascript:void(0)" id="exportExcelOut">Excel格式</a></li>--%>
                <%--<li><a href="javascript:void(0)" id="exportCVSOut">CVS格式</a></li>--%>
            <%--</ul>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<button type="button" class="btn btn-sm btn-success pull-right" id="saveChangePrivate"><span--%>
            <%--class="glyphicon glyphicon-floppy-saved"></span> 保存--%>
    <%--</button>--%>
</div>
<%--<div id="Menu">--%>
    <%--<ul>--%>
        <%--<li><a id="" href="">添加子数据</a></li>--%>
        <%--<li>删除数据</li>--%>
        <%--<li>发布数据</li>--%>
        <%--<li>取消发布</li>--%>
    <%--</ul>--%>
<%--</div>--%>
<div id="treeGridOut" style="width: 100%"></div>
<%--导入数据--%>
<div class="modal fade" id="importData" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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

<%--添加数据--%>
<div class="modal fade" id="adddata" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
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
<script type="text/javascript" src="${ctx}/jqwidgets/table/outputable.js"></script>
<script type="text/javascript">
    //@ sourceURL=showdata.ht
    var delayTimer = 4;

    $(function () {
        var updateJson = new Array();
        $("#adddata").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
        });
        //在关闭页面时弹出确认提示窗口
//        $(window).bind('beforeunload', function () {
//            return '您可能有数据没有保存，是否确认离开该页面？';
//        });
        $("#exportExcelOut").click(function () {
            $("#treeGridOut").jqxTreeGrid('exportData', 'xls');
        });
        $("#exportCVSOut").click(function () {
            $("#treeGridOut").jqxTreeGrid('exportData', 'csv');
        });
        //数据添加功能
        $("#addPrivateData").click(function () {
            $.get("${ctx}/datadriver/privatedata/addPrivateData.ht?projectId=${projectId}&taskId=${taskId}", function (data, status) {
                if (status == 'success') {
                }
                else {
                }
            });
        });
        //取消编辑
        $("#cancelEditPrivate").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
            if (selection.length == 1) {
                if (selection[0] == undefined) {
                } else {
                    $("#treeGridOut").jqxTreeGrid('endRowEdit', selection[0].dataId, true);
                }
            }
            if (selection.length > 1) {
                alertify.set('notifier', 'position', 'top-right');
                var notification = alertify.notify('选择了多行数据，不能进行取消编辑！', 'warning', delayTimer, function () {
                });
            }
        });

        //删除选中行
        $("#deletePrivateData").click(function () {
            var arrayList = new Array();
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
            if (selection.length > 1 || selection.length == 1) {
                if (selection[0] == undefined) {

                } else {
                    for (var i = 0; i < selection.length; i++) {
                        if (selection[i].dataState == 0) {
                            arrayList.push(selection[i].dataId)
                        }
                    }
                    $.get("${ctx}/datadriver/privatedata/deletePrivateData.ht?dataIds=" + arrayList, function (data, status) {
                        if (status == 'success') {
                            for (var j = 0; j < arrayList.length; j++) {
                                $("#treeGridOut").jqxTreeGrid('deleteRow', arrayList[j]);
                            }
                            alertify.set('notifier', 'position', 'top-right');
                            var notification = alertify.notify('删除成功！', 'success', delayTimer, function () {
                            });
                        }
                        else {
                            alertify.set('notifier', 'position', 'top-right');
                            var notification = alertify.notify('删除失败！', 'error', delayTimer, function () {
                            });
                        }
                    });
                }
            }
        });

        //发布数据
        $("#publishPrivateData").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');

            if (selection.length > 1 || selection.length == 1) {
                if (selection[0] != undefined) {
                    var rowsDataIds = new Array();
                    for (var i = 0; i < selection.length; i++) {
                        if (selection[i].dataState == 0) {
                            rowsDataIds.push(selection[i].dataId);
                        }
                    }
                    if (rowsDataIds.length > 0) {
                        $.get("createToPublish.ht?dataIds=" + rowsDataIds + "&parent=publishpanel", function (data, status) {
                            if (status == 'success') {
                                $('#treeGridOut').jqxTreeGrid('updateBoundData');
                            }
                        });
                    }

                }
            }
        });


        //取消选中
        $("#cancelSelected").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
            if (selection.length == 1) {
                if (selection[0] == undefined) {
                } else {
                    $("#treeGridOut").jqxTreeGrid('unselectRow', selection[0].dataId);
                }
            }
            if (selection.length > 1) {
                for (var i = 0; i < selection.length; i++) {
                    $("#treeGridOut").jqxTreeGrid('unselectRow', selection[i].dataId);
                }
            }
        });

        //上传模型文件
        $("#uploadFile").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');

            if (selection.length > 1 || selection.length == 1) {
                if (selection[0] != undefined) {
                    for (var i = 0; i < selection.length; i++) {
                        uploadFile(selection[i].dataId);
//                        $.get("uploadPrivateFile.ht?id=" + selection[i].dataId, function (data, status) {
//                            if (status == 'success') {
//                                $('#treeGridOut').jqxTreeGrid('updateBoundData');
//                            }
//                        });
                    }
                }
            }
        });

        //下载模型文件
        $("#downloadFile").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
            for (var i = 0; i < selection.length; i++) {
                window.location.href = "${ctx}/datadriver/privatedata/getPrivatefile.ht?id=" + selection[i].dataId;
                <%--$.ajax({--%>
                <%--async: false,--%>
                <%--url: "${ctx}/datadriver/privatedata/getPrivatefile.ht?id=" + selection[i].dataId,--%>
                <%--success: function (data) {--%>
                <%--}--%>
                <%--});--%>
//            $.get("getPrivatefile.ht?id=" + selection[i].dataId, function (data, status) {
////                if (status == 'success') {
////                    for (var j = 0; j < arrayList.length; j++) {
////                        $("#treeGridOut").jqxTreeGrid('deleteRow', arrayList[j]);
////                    }
////                    alertify.set('notifier', 'position', 'top-right');
////                    var notification = alertify.notify('删除成功！', 'success', delayTimer, function () {
////                    });
////                }
////                else {
////                    alertify.set('notifier', 'position', 'top-right');
////                    var notification = alertify.notify('删除失败！', 'error', delayTimer, function () {
////                    });
////                }
//            });
            }
        });

        //收回数据
        $("#recylePrivateData").click(function () {
            var selection = $("#treeGridOut").jqxTreeGrid('getSelection');

            if (selection.length > 1 || selection.length == 1) {
                if (selection[0] != undefined) {
                    var rowsDataIds = new Array();
                    for (var i = 0; i < selection.length; i++) {
                        if (selection[i].dataState == 1) {
                            rowsDataIds.push(selection[i].dataId);
                        }
                    }
                    if (rowsDataIds.length > 0) {
                        $.get("createToPublish.ht?dataIds=" + rowsDataIds + "&parent=createpanel", function (data, status) {
                            if (status == 'success') {
                                $('#treeGridOut').jqxTreeGrid('updateBoundData');
                            }
                        });
                    }
                }
            }
        });
        //保存
        $("#saveChangePrivate").click(function () {

            <%
               Long id = UniqueIdUtil.genId();
            %>
            var yid = <%=id%>//唯一数据ID
            var array = new Map();//键值和数据ID 映射关系

            var jsonObj =  strToJson(updateJson);//转换为json对象
            var Rjson =  JSON.parse(jsonObj);//转换为json对象
            for(var i=0;i<Rjson.length;i++){
                // alert(jsonObj[i].id);  //取json中的值
                if (Rjson[i].dataId<10000000000)
                {
                    var id = (yid -10000000000000)*10000+i;
                    array.set(Rjson[i].dataId,id);
                    Rjson[i].dataId= id;
                }
                if (Rjson[i].parentId != 0&Rjson[i].parentId != '0')
                {
                    array.forEach(function (value, key, map) {
                        if(Rjson[i].parentId == key)
                        {
                            Rjson[i].parentId = value;
                        }
                    });
                }
            }
            var orderJson = jsonarrayToJson(JSON.stringify(Rjson));
            console.log(orderJson);
            //TODO:添加是否确认提交的判断
            $.ajax({
                //json数组
                type: 'POST',
                url: "updatePrivateData.ht",
                data: "orderJson=" + orderJson,
                ContentType: "application/json; charset=utf-8",
                success: function (data) {
                    alertify.set('notifier', 'position', 'top-right');
                    // var notification = alertify.notify('保存成功！', 'success', delayTimer, function () {
                    // });
                }
            });
        });
        outputTableInit("${ctx}/datadriver/privatedata/outputData.ht?taskId=${taskId}", ${taskId}, ${projectId});
        // 结束编辑触发事件
        $("#treeGridOut").on('cellEndEdit', function (event) {
            var args = event.args;
            // 行键值
            var rowKey = args.key;
            // 行数据
            var rowData = args.row;
            // 列数据域
            var columnDataField = args.dataField;
            // 列显示域
            var columnDisplayField = args.displayField;
            // 当前值
            var value = args.value;
            // if (columnDataField == "ShippedDate")
            //     value = dataAdapter.formatDate(value, 'dd/MM/yyyy');

            for (var i = 0; i < updateJson.length; i++) {
                var tempJson = $.parseJSON(updateJson[i]);
                if (rowKey == tempJson.dataId) {
                    var num = updateJson.splice(i, 1);   //从i位置开始删除
                    i = i - 1;    //改变循环变量
                }
            }

            if (rowData.type == 0) {
                rowData.type = 1
            }

            updateJson.push('{"type":' + rowData.type + ',' +
                    '"dataId":' +rowKey + ',' +
                    '"taskId":' + rowData.taskId + ',' +
                    '"dataName":"' + rowData.dataName + '",' +
                    '"isLeaf":"' + rowData.isLeaf + '",' +
                    '"filePath":"' + rowData.filePath + '",' +
                    '"dataType":"' + rowData.dataType + '",' +
                    '"dataDescription":"' + rowData.dataDescription + '",' +
                    '"dataUnit":"' + rowData.dataUnit + '",' +
                    '"dataValue":"' + rowData.dataValue + '",' +
                    '"parentId":"' + rowData.parentId + '",' +
                    '"projectId":"' + ${projectId} + '",' +
                    '"dataSenMin":"' + rowData.dataSenMin + '",' +
                    '"dataSenMax":"' + rowData.dataSenMax + '"}');

            $("#treeGridOut").on('rowUnselect', function (event) {
            });
        });
    });
    //Excel批量导入
    function importPrivateData(taskId, projectId) {
        $('#importData').modal({
            keyboard: true,
            remote: "${ctx}/datadriver/privatedata/importPrivateData.ht?id=" + taskId + "&projectId=" + projectId
        });
    }
    //模型上传
    function uploadFile(dataId) {
        $('#uploadPrivateFile').modal({
            keyboard: true,
            remote: "uploadPrivateFile.ht?id=" + dataId
        });
    }
    //模型下载
    function downloadFile(dataId) {
//        window.location.href = "getPrivatefile.ht?id=" + dataId;
        $('#uploadPrivateFile').modal({
            keyboard: true,
            remote: "getPrivatefile.ht?id=" + dataId
        });
    }


    //对话框关闭清除缓存
    $("#uploadPrivateFile").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

</script>

</html>
