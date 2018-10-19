/**
 * Created by d on 2017/5/9.
 * 任务输出表单js
 */
//@ sourceURL=inputable.js
var newRowID = null;
function getWidth() {
    return $('#publish').outerWidth();
}
function getHeight() {
    return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
}
function inputTableInit(path, taskId) {
    // prepare the data
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
            },
            // pagerButtonsCount: 8,
            toolbarHeight: 35,
            renderToolbar: function (toolBar) {
                var toTheme = function (className) {
                    return className;
                }
                // appends buttons to the status bar.
                var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                var buttonTemplate = "<div style='float: left; padding: 4px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";

                var unselectRow = $(buttonTemplate);
                var orderSelection = $(buttonTemplate);
                var orderCancel = $(buttonTemplate);
                // var feedBack = $(buttonTemplate);
                var viewModel = $(buttonTemplate);

                container.append(unselectRow);
                container.append(orderSelection);
                container.append(orderCancel);
                // container.append(feedBack);
                container.append(viewModel);

                toolBar.append(container);

                unselectRow.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 23
                });
                unselectRow.find('div:first').addClass(toTheme('glyphicon glyphicon-remove-circle'));
                unselectRow.jqxTooltip({position: 'bottom', content: "取消行选择"});

                orderSelection.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 23
                });
                orderSelection.find('div:first').addClass(toTheme('glyphicon glyphicon-check'));
                orderSelection.jqxTooltip({position: 'bottom', content: "订阅已选择数据"});

                orderCancel.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 23
                });
                orderCancel.find('div:first').addClass(toTheme('glyphicon glyphicon-remove-sign'));
                orderCancel.jqxTooltip({position: 'bottom', content: "取消订阅已选择数据"});

                // feedBack.jqxButton({
                //     cursor: "pointer",
                //     disabled: true,
                //     enableDefault: false,
                //     height: 25,
                //     width: 23
                // });
                // feedBack.find('div:first').addClass(toTheme('glyphicon glyphicon-exclamation-sign'));
                // feedBack.jqxTooltip({position: 'bottom', content: "反馈选择的已订阅数据问题"});

                viewModel.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 23
                });
                viewModel.find('div:first').addClass(toTheme('glyphicon glyphicon-eye-open'));
                viewModel.jqxTooltip({position: 'bottom', content: "查看模型"});

                var updateButtons = function (action) {
                    switch (action) {
                        case "Select":
                            unselectRow.jqxButton({disabled: false});
                            orderSelection.jqxButton({disabled: false});
                            orderCancel.jqxButton({disabled: false});
                            viewModel.jqxButton({disabled: false});
                            break;
                        case "Unselect":
                            unselectRow.jqxButton({disabled: true});
                            orderSelection.jqxButton({disabled: true});
                            orderCancel.jqxButton({disabled: true});
                            viewModel.jqxButton({disabled: true});
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

                unselectRow.click(function () {
                    if (!unselectRow.jqxButton('disabled')) {
                        $("#treeGridIn").jqxTreeGrid('unselectRow', rowKey);
                        rowKey = null;
                    }
                });

                //查看模型
                viewModel.click(function () {
                    if (!viewModel.jqxButton('disabled')) {
                        var selection = $("#treeGridIn").jqxTreeGrid('getSelection');
                        if (selection.length==1){
                            if (selection[0].dataType=='模型'){
                                $('#model_content').modal({
                                    keyboard: true,
                                    remote: "viewModel.ht?id="+selection[0].dataId
                                });
                            } else {
                                $("#jqxNotificationModel").jqxNotification({ width: "auto", position: "top-right",
                                    opacity: 0.9, autoOpen: true, autoClose: false, template: "warning"
                                });
                            }
                        }
                    }
                });

                //TODO:需要加提示，是否保存更改的内容，否则重载会清空之前修改的
                orderSelection.click(function () {
                    if (!orderSelection.jqxButton('disabled')) {
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
                orderCancel.click(function () {
                    if (!orderSelection.jqxButton('disabled')) {
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