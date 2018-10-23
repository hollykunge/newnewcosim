/**
 * Created by d on 2017/5/9.
 * 任务输出表单js
 */
//@ sourceURL=input.js
var newRowID = null;
function getWidth() {
    return $('#publish').outerWidth();
}
function getHeight() {
    return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
}
function inputTableInit(path, taskId) {
    var source =
        {
            dataType: "json",
            dataFields: [
                {name: "dataId", type: "number"},
                {name: "dataName", type: "string"},
                {name: "filePath", type: "string"},
                {name: "parentId", type: "number"},
                {name: "taskId", type: "number"},
                {name: "dataType", type: "string"},
                {name: "dataDescription", type: "string"},
                {name: "publishState", type: "string"},
                {name: "orderState", type: "string"},
                {name: "submitState", type: "string"},
                {name: "taskName", type: "string"},
                {name: "creator", type: "string"},
                {name: "createTime", type: "string"},
                {name: "projectId", type: "number"},
                {name: "creatorId", type: "number"},
                {name: "dataUnit", type: "string"},
                {name: "dataValue", type: "string"},
                {name: "torderState", type: "number"}
            ],
            hierarchy: {
                keyDataField: {name: 'dataId'},
                parentDataField: {name: 'parentId'}
            },
            id: 'dataId',
            url: path,
            // url: 'showstructdata.ht',
            addRow: function (rowID, rowData, position, parentID, commit) {
                commit(true);
                newRowID = rowID;
            },
            updateRow: function (rowID, rowData, commit) {
                commit(true);
            },
            deleteRow: function (rowID, commit) {
                commit(true);
            }
        };
    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadComplete: function () {
            // data is loaded.
        }
    });
    $("#treeGridIn").jqxTreeGrid(
        {
            width: getWidth(),
            height: getHeight(),
            source: dataAdapter,
            pageable: true,
            editable: true,
            showToolbar: true,
            altRows: true,
            // hierarchicalCheckboxes: true,
            // checkboxes: true,
            filterable: true,
            filterMode: 'advanced',
            theme: "bootstrap",
            ready: function () {
                // called when the DatatreeGrid is loaded.
                $("#treeGridIn").jqxTreeGrid('expandRow', '0');
                $("#treeGridIn").jqxTreeGrid('selectRow', '0');
                $("#treeGridIn").jqxTreeGrid('clearSelection');
            },
            // pagerButtonsCount: 8,
            toolbarHeight: 35,
            renderToolbar: function (toolBar) {
                var container = $("<div style='margin: 5px;'></div>");
                var orderRow = $('<button id="addRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-ok"></span> 订阅数据</button>');
                var cancelOrder = $('<button id="delRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-remove"></span> 取消订阅</button>');
                var downLoad = $('<button id="pubRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-download"></span> 下载文件</button>');
                var refreshInput = $('<button id="pubRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-refresh"></span> 刷新列表</button>');

                container.append(orderRow);
                container.append(cancelOrder);
                container.append(downLoad);
                container.append(refreshInput);
                toolBar.append(container);


                orderRow.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                orderRow.jqxTooltip({position: 'bottom', content: "点击订阅选中数据"});

                cancelOrder.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                cancelOrder.jqxTooltip({position: 'bottom', content: "点击取消选中行订阅"});
                downLoad.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                downLoad.jqxTooltip({position: 'bottom', content: "点击下载所选文件数据"});
                refreshInput.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                refreshInput.jqxTooltip({position: 'bottom', content: "点击刷新数据列表"});


                var updateButtons = function (action) {
                    switch (action) {
                        case "Select":
                            orderRow.jqxButton({disabled: false});
                            cancelOrder.jqxButton({disabled: false});
                            downLoad.jqxButton({disabled: false});
                            refreshInput.jqxButton({disabled: false});
                            break;
                        case "Unselect":
                            orderRow.jqxButton({disabled: true});
                            cancelOrder.jqxButton({disabled: true});
                            downLoad.jqxButton({disabled: true});
                            refreshInput.jqxButton({disabled: false});
                            break;
                    }
                };
                var rowKey = null;
                var rowParentId = 0;
                $("#treeGridIn").on('rowSelect', function (event) {
                    var args = event.args;
                    rowKey = args.key;
                    if (rowKey != "0") {
                        rowParentId = rowKey;
                    }
                    updateButtons('Select');
                });
                $("#treeGridIn").on('rowUnselect', function (event) {
                    updateButtons('Unselect');
                });

                //取消选择
                // unselectRow.click(function () {
                //     if (!unselectRow.jqxButton('disabled')) {
                //         $("#treeGridIn").jqxTreeGrid('unselectRow', rowKey);
                //         rowKey = null;
                //     }
                // });

                //下载
                downLoad.click(function () {
                    if (!downLoad.jqxButton('disabled')) {
                        var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[0].dataType=='模型' || selection[0].dataType=='文件'){
                                downloadF(selection[i].dataId);
                            } else {
                                //TODO 添加消息提示
                            }
                        }
                    }
                });

                //查看模型
                // viewModel.click(function () {
                //     if (!viewModel.jqxButton('disabled')) {
                //         var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
                //         if (selection.length==1){
                //             if (selection[0].dataType=='模型'){
                //                 $('#model_content').modal({
                //                     keyboard: true,
                //                     remote: "viewModel.ht?id="+selection[0].dataId
                //                 });
                //             } else {
                //                 $("#jqxNotificationModel").jqxNotification({ width: "auto", position: "top-right",
                //                     opacity: 0.9, autoOpen: true, autoClose: false, template: "warning"
                //                 });
                //             }
                //         }
                //     }
                // });

                //TODO:需要加提示，是否保存更改的内容，否则重载会清空之前修改的
                orderRow.click(function () {
                    if (!orderRow.jqxButton('disabled')) {
                        var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
                        var rowsDataIds = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[i] == undefined) {
                                continue;
                            }
                            if (selection[i].torderState == 0) {
                                rowsDataIds.push(selection[i].dataId);
                            }
                        }
                        $.get("canOrderToOrder.ht?dataIds=" + rowsDataIds + "&parent=orderpanel" + "&taskId=" + taskId, function (data, status) {
                            if (status == 'success') {
                                // window.location.reload();
                                $('#treeGridIn').jqxTreeGrid('updateBoundData');
                            }
                        });
                    }
                });
                cancelOrder.click(function () {
                    if (!cancelOrder.jqxButton('disabled')) {
                        var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
                        var rowsDataIds = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[i] == undefined) {
                                continue;
                            }
                            if (selection[i].torderState == 1) {
                                rowsDataIds.push(selection[i].dataId);
                            }
                        }
                        $.get("canOrderToOrder.ht?dataIds=" + rowsDataIds + "&parent=canorderpanel" + "&taskId=" + taskId, function (data, status) {
                            if (status == 'success') {
                                $('#treeGridIn').jqxTreeGrid('updateBoundData');
                            }
                        });
                    }
                });
            },
            // 数据表加载完事执行，发布数据使用
            rendered: function () {
                if ($(".orderButtons").length > 0) {
                    // $(".cancelButtons").jqxButton();
                    $(".orderButtons").jqxButton({template: "link"});

                    var orderClick = function (event) {
                        var target = $(event.target);
                        // get button's value.
                        var value = target.val();
                        // get clicked row.
                        var rowKey = event.target.getAttribute('data-row');
                        var rowId = event.target.getAttribute('data-tid');
                        if (value == "订阅") {
                            $.get("canOrderToOrder.ht?id=" + rowKey + "&parent=orderpanel" + "&taskId=" + taskId, function (data, status) {
                                if (status == 'success') {
                                    //TODO:添加表格刷新
                                    $("input[data-row = '" + rowKey + "']").attr("value", "取消");
                                }
                            });
                        }
                        if (value == "取消") {
                            $.get("canOrderToOrder.ht?id=" + rowKey + "&parent=canorderpanel" + "&taskId=" + taskId, function (data, status) {
                                if (status == 'success') {
                                    //TODO:添加表格刷新
                                    $("input[data-row = '" + rowKey + "']").attr("value", "订阅");
                                }
                            });
                        }
                        else {
                            // end edit and save changes.
                            // target.parent().find('.cancelButtons').hide();
                            // target.val("Edit");
                            // $("#treeGridOut").jqxTreeGrid('endRowEdit', rowKey);
                        }
                    }
                    $(".orderButtons").on('click', function (event) {
                        orderClick(event);
                    });

                    // $(".cancelButtons").click(function (event) {
                    //     // end edit and cancel changes.
                    //     var rowKey = event.target.getAttribute('data-row');
                    //     $("#treeGridOut").jqxTreeGrid('endRowEdit', rowKey, true);
                    // });
                }
            },
            columns: [
                {text: '名称', dataField: "dataName", align: 'left', width: '25%', editable: false},
                {text: '任务名称', dataField: "taskName", align: 'left', width: '20%', editable: false},
                {text: '类型', dataField: "dataType", align: 'left', width: '12%', editable: false},
                {text: '最新值', dataField: "dataValue", align: 'left', width: '25%', editable: false},
                {text: '单位', dataField: "dataUnit", align: 'left', width: '12%', editable: false},
                {
                    text: '订阅状态', dataField: "orderState", align: 'left', width: '6%', editable: false,
                    cellsRenderer: function (row, column, value, rowData) {
                        // render custom column.
                        if (rowData.torderState == 1) {
                            return "<strong style='color: #00B83F'>已订阅</strong>";
                        }
                        else {
                            return "<strong style='color: #b4372f'>未订阅</strong>";
                        }
                    }
                }
            ]
        });

}