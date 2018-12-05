/**
 * Created by d on 2017/5/9.
 * 任务输出表单js
 */
//@ sourceURL=output.js
function getWidth() {
    return $('#data').outerWidth();
}

function getHeight() {
    return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
}

function strToJson(o) {
    var tempJson = '[' + o + ']';
    return tempJson;
}

function outputTableInit(path, taskId, projectId, taskName, type) {
    var cellClass = function (row, dataField, cellText, rowData) {
        var cellValue = rowData[dataField];
        if (Number(cellValue) < rowData.dataSenMin) {
            return "min";
        }
        if (Number(cellValue) > rowData.dataSenMax) {
            return "max";
        }
        if (Number(cellValue) == NaN) {
            return;
        }
    }
    var isView = false;
    if (type == 1) {
        isView = true;
    }

    var newRowID = null;
    //加载数据
    var source =
        {
            dataType: "json",
            dataFields: [
                {name: "dataId", type: "number"},
                {name: "dataName", type: "string"},
                {name: "filePath", type: "string"},
                {name: "parentId", type: "number"},
                {name: "taskId", type: "number"},
                {name: "isLeaf", type: "number"},
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
                {name: "dataSenMax", type: "number"},
                {name: "dataSenMin", type: "number"},
                {name: "type", type: "number"}
            ],
            hierarchy: {
                keyDataField: {name: 'dataId'},
                parentDataField: {name: 'parentId'}
            },
            id: 'dataId',
            url: path,
            // addRow: function (rowID, rowData, position, parentID, commit) {
            //     $.post("addPrivateData.ht", rowData, function (data, status) {
            //         if (status == "success") {
            //             $("#treeGridOut").jqxTreeGrid('updateBoundData');
            //         }
            //     }, "json");
            //     commit(true);
            //     newRowID = rowID;
            // },
            addRow: function (rowID, rowData, position, parentID, commit) {
                // synchronize with the server - send insert command
                $.ajax({
                    cache: false,
                    dataType: 'json',
                    url: 'addPrivateData.ht',
                    data: rowData,
                    type: "POST",
                    async: false,
                    success: function (data, status, xhr) {
                        // insert command is executed.
                        $("#treeGridOut").jqxTreeGrid('updateBoundData');
                        commit(true);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert(errorThrown);
                        commit(false);
                    }
                });
            },
            updateRow: function (rowID, rowData, commit) {
                $.ajax({
                    cache: false,
                    dataType: 'json',
                    url: 'updatePrivateData.ht',
                    data: rowData,
                    type: "POST",
                    async: false,
                    success: function (data, status, xhr) {
                        // insert command is executed.
                        $("#treeGridOut").jqxTreeGrid('updateBoundData');
                        commit(true);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert(errorThrown);
                        commit(false);
                    }
                });
            },
            deleteRow: function (dataId, commit) {
                $.get("delPrivateData.ht?dataId=" + dataId, function (data, status) {
                    if (status == "success") {
                        $("#treeGridOut").jqxTreeGrid('updateBoundData');
                    }
                }, "json");
                commit(true);
            }
        };
    var dataAdapter = new $.jqx.dataAdapter(source);
    // create Tree Grid
    $("#treeGridOut").jqxTreeGrid(
        {
            width: '100%',
            height: getHeight(),
            source: dataAdapter,
            pageable: true,
            pagerMode: 'advanced',
            showHeader: true,
            editable: isView,
            pageSize: 20,
            pageSizeOptions: ['10', '20', '50', '100', '500'],
            columnsResize: true,
            showToolbar: isView,
            altRows: true,
            hierarchicalCheckboxes: false,
            checkboxes: false,
            filterable: true,
            filterMode: 'advanced',
            theme: "bootstrap",
            pagerButtonsCount: 8,
            toolbarHeight: 35,
            editSettings: {
                saveOnPageChange: isView,
                saveOnBlur: isView,
                saveOnSelectionChange: isView,
                cancelOnEsc: isView,
                saveOnEnter: isView,
                editSingleCell: isView,
                editOnDoubleClick: isView,
                editOnF2: isView
            },
            ready: function () {
                // called when the DatatreeGrid is loaded.
                $("#treeGridOut").jqxTreeGrid('expandRow', '0');
                $("#treeGridOut").jqxTreeGrid('selectRow', '0');
                $("#treeGridOut").jqxTreeGrid('clearSelection');
                // focus jqxTreeGrid.
                // $("#treeGridOut").jqxTreeGrid('focus');
            },
            renderToolbar: function (toolBar) {
                var toTheme = function (className) {
                    return className;
                };
                var container = $("<div style='margin: 5px;'></div>");
                var addRow = $('<button id="addRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-plus"></span> 添加数据</button>');
                var delRow = $('<button id="delRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-trash"></span> 删除已选</button>');
                var pubRow = $('<button id="pubRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-ok"></span> 发布数据</button>');
                var cancelRow = $('<button id="cancelRow" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-remove"></span> 撤销发布</button>');
                var refreshTable = $('<button id="refreshTable" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-refresh"></span> 刷新表单</button>');
                var uploadFile = $('<button id="uploadFile" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-upload"></span> 上传文件</button>');
                var importData = $('<button id="importData" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-import"></span> 导入数据</button>');
                var delAll = $('<button id="importData" type="button" style="margin-left: 5px"><span class="glyphicon glyphicon-remove-circle"></span> 全部删除</button>');

                container.append(addRow);
                container.append(" ");
                container.append(pubRow);
                container.append(cancelRow);
                container.append(" ");
                container.append(delRow);
                // container.append(delAll);
                container.append(" ");
                container.append(uploadFile);
                container.append(importData);
                container.append(" ");
                container.append(refreshTable);
                if (type == 1) {
                    toolBar.append(container);
                }

                addRow.jqxButton({
                    cursor: "pointer",
                    disabled: false,
                    template: "success"
                });
                addRow.jqxTooltip({position: 'bottom', content: "点击添加一行主数据"});

                delRow.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                delRow.jqxTooltip({position: 'bottom', content: "点击删除所选数据"});
                pubRow.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                pubRow.jqxTooltip({position: 'bottom', content: "点击发布所选数据"});
                cancelRow.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                cancelRow.jqxTooltip({position: 'bottom', content: "点击撤销所选发布数据"});
                refreshTable.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                refreshTable.jqxTooltip({position: 'bottom', content: "点击刷新数据列表"});
                uploadFile.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                uploadFile.jqxTooltip({position: 'bottom', content: "点击上传文件"});
                importData.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                importData.jqxTooltip({position: 'bottom', content: "点击导入数据"});
                delAll.jqxButton({
                    cursor: "pointer",
                    disabled: false
                });
                delAll.jqxTooltip({position: 'bottom', content: "点击全部删除"});
                var updateButtons = function (action) {
                    switch (action) {
                        case "Select":
                            addRow.jqxButton({disabled: false});
                            delRow.jqxButton({disabled: false});
                            pubRow.jqxButton({disabled: false});
                            cancelRow.jqxButton({disabled: false});
                            refreshTable.jqxButton({disabled: false});
                            uploadFile.jqxButton({disabled: false});
                            break;
                        case "Unselect":
                            addRow.jqxButton({disabled: false});
                            delRow.jqxButton({disabled: true});
                            pubRow.jqxButton({disabled: true});
                            cancelRow.jqxButton({disabled: true});
                            refreshTable.jqxButton({disabled: false});
                            uploadFile.jqxButton({disabled: true});
                            break;
                        case "Edit":
                            addRow.jqxButton({disabled: false});
                            delRow.jqxButton({disabled: true});
                            pubRow.jqxButton({disabled: true});
                            cancelRow.jqxButton({disabled: true});
                            refreshTable.jqxButton({disabled: false});
                            uploadFile.jqxButton({disabled: true});
                            break;
                        case "End Edit":
                            addRow.jqxButton({disabled: false});
                            delRow.jqxButton({disabled: false});
                            pubRow.jqxButton({disabled: false});
                            cancelRow.jqxButton({disabled: false});
                            refreshTable.jqxButton({disabled: false});
                            uploadFile.jqxButton({disabled: false});
                            break;
                    }
                };
                var rowKey = null;
                $("#treeGridOut").on('rowSelect', function (event) {
                    var args = event.args;
                    rowKey = args.key;
                    updateButtons('Select');
                });
                $("#treeGridOut").on('rowUnselect', function (event) {
                    updateButtons('Unselect');
                });

                $("#treeGridOut").on('rowEndEdit', function (event) {
                    updateButtons('End Edit');
                });

                $("#treeGridOut").on('rowBeginEdit', function (event) {
                    updateButtons('Edit');
                });

                addRow.click(function (event) {
                    if (!addRow.jqxButton('disabled')) {
                        // $("#treeGridOut").jqxTreeGrid('expandRow', rowKey);
                        $("#treeGridOut").jqxTreeGrid('addRow', null, {
                            type: 1,
                            dataName: "未定义数据名称",
                            taskId: taskId,
                            taskName: taskName,
                            dataSenMax: 10000,
                            dataSenMin: 0,
                            isLeaf: 0,
                            dataType: "数值",
                            publishState: "未发布",
                            parentId: 0,
                            projectId: projectId,
                        }, 'first');
                        // add new empty row.
                        // $("#treeGridOut").jqxTreeGrid('addRow', null, {}, 'first', rowKey);
                        // select the first row and clear the selection.
                        $("#treeGridOut").jqxTreeGrid('clearSelection');
                        $("#treeGridOut").jqxTreeGrid('selectRow', newRowID);
                        // edit the new row.
                        $("#treeGridOut").jqxTreeGrid('beginRowEdit', newRowID);
                        updateButtons('add');
                    }
                });
                delRow.click(function () {
                    if (!delRow.jqxButton('disabled')) {
                        var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                        if (selection.length > 1) {
                            var keys = new Array();
                            for (var i = 0; i < selection.length; i++) {
                                keys.push($("#treeGridOut").jqxTreeGrid('getKey', selection[i]));
                            }
                            $("#treeGridOut").jqxTreeGrid('deleteRow', keys);
                        } else {
                            $("#treeGridOut").jqxTreeGrid('deleteRow', rowKey);
                        }
                        updateButtons('delete');
                    }
                });
                pubRow.click(function () {
                    if (!pubRow.jqxButton('disabled')) {
                        var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                        var rowsDataIds = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[i] == undefined) {
                                continue;
                            }
                            if (selection[i].publishState == 0) {
                                rowsDataIds.push(selection[i].uid);
                            }
                        }
                        if (rowsDataIds.length > 0) {
                            $.get("createToPublish.ht?dataIds=" + rowsDataIds + "&parent=publishpanel", function (data, status) {
                                if (status == 'success') {
                                    $('#treeGridOut').jqxTreeGrid('updateBoundData');
                                    alertify.set('notifier', 'position', 'top-right');
                                    alertify.success(data);
                                }
                            });
                        }
                    }
                });
                cancelRow.click(function () {
                    if (!cancelRow.jqxButton('disabled')) {
                        var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                        var rowsDataIds = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[0] == undefined) {
                                continue;
                            }
                            if (selection[i].publishState == 1) {
                                rowsDataIds.push(selection[i].uid);
                            }
                        }
                        if (rowsDataIds.length > 0) {
                            $.get("createToPublish.ht?dataIds=" + rowsDataIds + "&parent=createpanel", function (data, status) {
                                if (status == 'success') {
                                    $('#treeGridOut').jqxTreeGrid('updateBoundData');
                                    alertify.set('notifier', 'position', 'top-right');
                                    alertify.success(data);
                                }
                            });
                        }
                    }
                });
                refreshTable.click(function () {
                    if (!refreshTable.jqxButton('disabled')) {
                        location.reload();
                    }
                });
                importData.click(function () {
                    if (!importData.jqxButton('disabled')) {
                        $('#importDataFile').modal({
                            keyboard: true,
                            remote: "importPrivateData.ht?id=" + taskId + "&projectId=" + projectId
                        });
                    }
                });
                delAll.click(function () {
                    if (!delAll.jqxButton('disabled')) {
                        $('#delAllData').modal({
                            keyboard: true,
                            remote: "delAllData.ht?id=" + taskId
                        });
                    }
                });
                uploadFile.click(function () {
                    if (!uploadFile.jqxButton('disabled')) {
                        var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                        if (selection[0].dataType == '模型' || selection[0].dataType == '文件') {
                            $('#uploadPrivateFile').modal({
                                keyboard: true,
                                remote: "uploadPrivateFile.ht?id=" + selection[0].dataId
                            });
                        }
                    }
                });
            },
            columns: [
                {text: '名称', dataField: "dataName", align: 'left', width: '25%', pinned: true, editable: true},
                {
                    text: '类型', dataField: "dataType", align: 'left', width: '10%', columnType: "template", value: "数值",
                    createEditor: function (row, cellvalue, editor, cellText, width, height) {
                        // construct the editor.
                        var source = ["数值", "文件", "模型", "结构型数据"];
                        editor.jqxDropDownList({
                            autoDropDownHeight: true,
                            source: source,
                            width: '100%',
                            height: '100%'
                        });
                    },
                    initEditor: function (row, cellvalue, editor, celltext, width, height) {
                        // set the editor's current value. The callback is called each time the editor is displayed.
                        editor.jqxDropDownList('selectItem', cellvalue);
                    },
                    getEditorValue: function (row, cellvalue, editor) {
                        // return the editor's value.
                        return editor.val();
                    }
                },
                {text: '最新值', dataField: "dataValue", align: 'left', width: '25%', cellClassName: cellClass},
                {
                    text: '单位', dataField: "dataUnit", align: 'left', width: '10%', columnType: "template",
                    createEditor: function (row, cellvalue, editor, cellText, width, height) {
                        // construct the editor.
                        var source = ["Km/s", "m/s", "s", "km", "m", "kg", "mm", "N", "mm*mm", "μm", "°", "mm*mm", "°/s", "°/h", "Hz", "g", "ppm", "ms", "mm*mm*mm"];
                        editor.jqxDropDownList({
                            autoDropDownHeight: true,
                            source: source,
                            width: '100%',
                            height: '100%'
                        });
                    },
                    initEditor: function (row, cellvalue, editor, celltext, width, height) {
                        // set the editor's current value. The callback is called each time the editor is displayed.
                        editor.jqxDropDownList('selectItem', cellvalue);
                    },
                    getEditorValue: function (row, cellvalue, editor) {
                        // return the editor's value.
                        return editor.val();
                    }
                },
                {text: '最小值', dataField: "dataSenMin", align: 'left', width: '12%'},
                {text: '最大值', dataField: "dataSenMax", align: 'left', width: '12%'},
                {
                    text: '发布状态', dataField: "publishState", align: ' center', width: '6%', editable: false,
                    cellsRenderer: function (row, column, value, rowData) {
                        // render custom column.
                        if (rowData.publishState == 1) {
                            return "<strong style='color: #00B83F' id=" + rowData.dataId + ">已发布</strong>";
                        } else {
                            return "<strong style='color: #b4372f' id=" + rowData.dataId + ">未发布</strong>";
                        }
                    }
                }
            ]
        });
}