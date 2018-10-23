//@ sourceURL=outputable.ht
/**
 * Created by d on 2017/5/9.
 * 任务输出表单js
 */
//@ sourceURL=outputable.js
var newRowID = null;
var count = 0;

function getWidth() {
    return $('#data').outerWidth();
}
function getHeight() {
    return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
}

//生成随机整数
function rd(n, m) {
    var c = m - n + 1;
    return Math.floor(Math.random() * c + n);
}
function arrayToJson(o) {
    var total = o.length;
    var tempJson = '{"total":' + total + ',' +
        '"rows":[' + o + ']}';
    return tempJson;
}

function jsonarrayToJson(o) {
    var total = o.length;
    var tempJson = '{"total":' + total + ',' +
        '"rows":' + o + '}';
    return tempJson;
}

function strToJson(o) {
    var tempJson = '[' + o + ']';
    return tempJson;
}

/**
 *  生成随机数
 * @param len
 * @param radix
 * @returns {string}
 */
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data. At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
}

/**
 *  将对象元素转换成字符串以作比较
 * @param obj
 * @param keys
 * @returns {string}
 */
function obj2key(obj, keys){
    var n = keys.length,
        key = [];
    while(n--){
        key.push(obj[keys[n]]);
    }
    return key.join('|');
}

/**
 *  去重操作
 * @param array
 * @param keys
 * @returns {Array}
 */
function uniqeByKeys(array,keys){
    var arr = [];
    var hash = {};
    for (var i = 0, j = array.length; i < j; i++) {
        var k = obj2key(array[i], keys);
        if (!(k in hash)) {
            hash[k] = true;
            arr .push(array[i]);
        }
    }
    return arr ;
}


function outputTableInit(path, taskId, projectId) {
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
    var updateJson = new Array();
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
    $("#treeGridOut").jqxTreeGrid({
        width: getWidth(),
        height: getHeight(),
        source: dataAdapter,
        pageable: true,
        pagerMode: 'advanced',
        showHeader: true,
        editable: true,
        pageSize: 20,
        pageSizeOptions: ['10', '20', '50', '100', '500'],
        editSettings: {
            saveOnPageChange: true,
            saveOnBlur: true,
            saveOnSelectionChange: true,
            cancelOnEsc: true,
            saveOnEnter: true,
            editSingleCell: true,
            editOnDoubleClick: true,
            editOnF2: true
        },
        showToolbar: true,
        altRows: true,
        hierarchicalCheckboxes: false,
        checkboxes: false,
        filterable: true,
        filterMode: 'advanced',
        theme: "bootstrap",
        ready: function () {
            // called when the DatatreeGrid is loaded.
            $("#treeGridOut").jqxTreeGrid('expandRow', '0');
            $("#treeGridOut").jqxTreeGrid('selectRow', '0');
            // focus jqxTreeGrid.
            $("#treeGridOut").jqxTreeGrid('focus');
        },
        pagerButtonsCount: 8,
        toolbarHeight: 35,
        renderToolbar: function (toolBar) {
            var toTheme = function (className) {
                // if (theme == "")
                return className;
                // return className + " " + className + "-" + theme;
            };
            // appends buttons to the status bar.
            var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
            var buttonTemplate = "<div style='float: left; padding: 4px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
            var addButton = $(buttonTemplate);
            var editButton = $(buttonTemplate);
            var deleteButton = $(buttonTemplate);
            var cancelButton = $(buttonTemplate);
            var updateButton = $(buttonTemplate);
            var unselectRow = $(buttonTemplate);
            var publishSelection = $(buttonTemplate);
            var publishCancel = $(buttonTemplate);
            var uploadModel = $(buttonTemplate);
            container.append(addButton);
            container.append(editButton);
            container.append(deleteButton);
            container.append(cancelButton);
            container.append(updateButton);
            container.append(unselectRow);
            container.append(publishSelection);
            container.append(publishCancel);
            container.append(uploadModel);

            toolBar.append(container);

            addButton.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            addButton.find('div:first').addClass(toTheme('glyphicon glyphicon-plus'));
            addButton.jqxTooltip({position: 'bottom', content: "添加数据"});

            editButton.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            editButton.find('div:first').addClass(toTheme('glyphicon glyphicon-pencil'));
            editButton.jqxTooltip({position: 'bottom', content: "编辑行"});

            deleteButton.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            deleteButton.find('div:first').addClass(toTheme('glyphicon glyphicon-trash'));
            deleteButton.jqxTooltip({position: 'bottom', content: "删除行"});

            updateButton.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            updateButton.find('div:first').addClass(toTheme('glyphicon glyphicon-floppy-saved'));
            updateButton.jqxTooltip({position: 'bottom', content: "保存变更"});

            cancelButton.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            cancelButton.find('div:first').addClass(toTheme('glyphicon glyphicon-remove'));
            cancelButton.jqxTooltip({position: 'bottom', content: "取消编辑"});

            unselectRow.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            unselectRow.find('div:first').addClass(toTheme('glyphicon glyphicon-remove-circle'));
            unselectRow.jqxTooltip({position: 'bottom', content: "取消行选择"});

            publishSelection.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            publishSelection.find('div:first').addClass(toTheme('glyphicon glyphicon-share'));
            publishSelection.jqxTooltip({position: 'bottom', content: "发布已选择数据"});

            publishCancel.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            publishCancel.find('div:first').addClass(toTheme('glyphicon glyphicon-remove-sign'));
            publishCancel.jqxTooltip({position: 'bottom', content: "收回已发布的数据"});

            uploadModel.jqxButton({
                cursor: "pointer",
                disabled: true,
                enableDefault: false,
                height: 25,
                width: 23
            });
            uploadModel.find('div:first').addClass(toTheme('glyphicon glyphicon-upload'));
            uploadModel.jqxTooltip({position: 'bottom', content: "上传文件或者模型"});

            var updateButtons = function (action) {
                switch (action) {
                    case "Select":
                        addButton.jqxButton({disabled: false});
                        deleteButton.jqxButton({disabled: false});
                        editButton.jqxButton({disabled: false});
                        cancelButton.jqxButton({disabled: true});
                        updateButton.jqxButton({disabled: false});
                        unselectRow.jqxButton({disabled: false});
                        publishSelection.jqxButton({disabled: false});
                        publishCancel.jqxButton({disabled: false});
                        uploadModel.jqxButton({disabled: false});
                        break;
                    case "Unselect":
                        addButton.jqxButton({disabled: false});
                        deleteButton.jqxButton({disabled: true});
                        editButton.jqxButton({disabled: true});
                        cancelButton.jqxButton({disabled: true});
                        updateButton.jqxButton({disabled: false});
                        unselectRow.jqxButton({disabled: true});
                        publishSelection.jqxButton({disabled: true});
                        publishCancel.jqxButton({disabled: true});
                        uploadModel.jqxButton({disabled: true});
                        break;
                    case "Edit":
                        addButton.jqxButton({disabled: false});
                        deleteButton.jqxButton({disabled: true});
                        editButton.jqxButton({disabled: true});
                        cancelButton.jqxButton({disabled: false});
                        updateButton.jqxButton({disabled: true});
                        unselectRow.jqxButton({disabled: true});
                        publishSelection.jqxButton({disabled: true});
                        publishCancel.jqxButton({disabled: true});
                        uploadModel.jqxButton({disabled: true});
                        break;
                    case "End Edit":
                        addButton.jqxButton({disabled: false});
                        deleteButton.jqxButton({disabled: false});
                        editButton.jqxButton({disabled: false});
                        cancelButton.jqxButton({disabled: true});
                        updateButton.jqxButton({disabled: false});
                        unselectRow.jqxButton({disabled: true});
                        publishSelection.jqxButton({disabled: true});
                        publishCancel.jqxButton({disabled: true});
                        uploadModel.jqxButton({disabled: true});
                        break;
                }
            };
            var rowKey = null;
            var rowParentId = 0;
            // var rowDataForParentId = 0;
            $("#treeGridOut").on('rowSelect', function (event) {
                var args = event.args;
                rowKey = args.key;
                if (rowKey != "0") {
                    // var rowDataForParentId = args.row;
                    rowParentId = rowKey;
                }
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
            addButton.click(function (event) {
                if (!addButton.jqxButton('disabled')) {
                    //TODO:判断不完全
                    if (rowKey != null & rowKey != "0") {
                        // $("#treeGridOut").jqxTreeGrid('expandRow', rowKey);
                        // add new empty row.
                        $("#treeGridOut").jqxTreeGrid('addRow', null, {
                            type: 3,
                            dataName: "未定义子数据名称",
                            taskId: taskId,
                            dataSenMax: 10000,
                            dataSenMin: 0,
                            isLeaf: 1,
                            dataType: "数值",
                            publishState: "未发布",
                            parentId: rowParentId,
                            projectId: projectId
                        }, 'first', rowKey);
                    } else {
                        $("#treeGridOut").jqxTreeGrid('addRow', null, {
                            type: 3,
                            dataName: "未定义数据名称",
                            taskId: taskId,
                            dataSenMax: 10000,
                            dataSenMin: 0,
                            isLeaf: 0,
                            dataType: "数值",
                            publishState: "未发布",
                            parentId: rowParentId,
                            projectId: projectId
                        }, 'first');
                    }

                    // select the first row and clear the selection.
                    $("#treeGridOut").jqxTreeGrid('clearSelection');
                    $("#treeGridOut").jqxTreeGrid('selectRow', newRowID);
                    // edit the new row.
                    $("#treeGridOut").jqxTreeGrid('beginRowEdit', newRowID);
                    updateButtons('add');
                    rowKey = null;
                }
            });
            cancelButton.click(function (event) {
                if (!cancelButton.jqxButton('disabled')) {
                    // cancel changes.
                    $("#treeGridOut").jqxTreeGrid('endRowEdit', rowKey, true);
                }
            });
            updateButton.click(function (event) {
                if (!updateButton.jqxButton('disabled')) {
                    // save changes.
                    var yid = uuid(14, 10) //唯一数据ID
                    var array = new Map();//键值和数据ID 映射关系

                    var jsonObj =  strToJson(updateJson);//转换为json对象
                    var Rjson_O =  JSON.parse(jsonObj);//转换为json对象
                    var Rjson  = uniqeByKeys(Rjson_O.reverse(), ['dataId']);
                    for(var i=0;i<Rjson.length;i++){
                        if (Rjson[i].dataId<10000000000) {
                            array.set(Rjson[i].dataId,yid);
                            Rjson[i].dataId= yid++;
                        }
                        if (Rjson[i].parentId != 0&Rjson[i].parentId != '0') {
                            array.forEach(function (value, key, map) {
                                if(Rjson[i].parentId == key)
                                {
                                    Rjson[i].parentId = value;
                                }
                            });
                        }
                    }
                    var orderJson = jsonarrayToJson(JSON.stringify(Rjson));
                    //TODO:添加是否确认提交的判断
                    $.ajax({
                        //json数组
                        type: 'POST',
                        url: "updatePrivateData.ht",
                        data: "orderJson=" + orderJson,
                        ContentType: "application/json; charset=utf-8",
                        success: function (data) {
                            window.location.reload();
                        },
                        error: function (err) {
                        }
                    });
                }
            });
            editButton.click(function () {
                if (!editButton.jqxButton('disabled')) {
                    $("#treeGridOut").jqxTreeGrid('beginRowEdit', rowKey);
                    updateButtons('edit');
                }
            });

            unselectRow.click(function () {
                if (!unselectRow.jqxButton('disabled')) {
                    $("#treeGridOut").jqxTreeGrid('unselectRow', rowKey);
                    rowKey = null;
                }
            });
            //上传文件或者模型
            uploadModel.click(function () {
                if (!unselectRow.jqxButton('disabled')) {
                    var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                    if (selection.length==1){
                        if (selection[0] == undefined) {
                            $("#jqxNotificationNull").jqxNotification({ width: "auto", position: "top-right",
                                opacity: 0.9, autoOpen: true, autoClose: false, template: "warning"
                            });
                            return;
                        }
                        if (selection[0].dataType=='模型'||selection[0].dataType == '文件'){
                            $('#uploadPrivateFile').modal({
                                keyboard: true,
                                remote: "uploadPrivateFile.ht?id="+selection[0].dataId
                            });
                        } else {
                            $("#jqxNotificationType").jqxNotification({ width: "auto", position: "top-right",
                                opacity: 0.9, autoOpen: true, autoClose: true, template: "warning"
                            });
                            return;
                        }
                    } else if (selection.length > 1) {
                        $("#jqxNotificationTroppo").jqxNotification({ width: "auto", position: "top-right",
                            opacity: 0.9, autoOpen: true, autoClose: true, template: "warning"
                        });
                        return;
                    }
                }
            });
            //TODO:需要加提示，是否保存更改的内容，否则重载会清空之前修改的
            publishSelection.click(function () {
                if (!publishSelection.jqxButton('disabled')) {
                    var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                    var rowsDataIds = new Array();
                    for (var i = 0; i < selection.length; i++) {
                        if (selection[i] == undefined) {
                            continue;
                        }
                        if (selection[i].publishState == 0) {
                            rowsDataIds.push(selection[i].dataId);
                        }
                    }
                    $.get("createToPublish.ht?dataIds=" + rowsDataIds + "&parent=publishpanel", function (data, status) {
                        if (status == 'success') {
                            $('#treeGridOut').jqxTreeGrid('updateBoundData');
                        }
                    });
                }
            });
            publishCancel.click(function () {
                if (!publishCancel.jqxButton('disabled')) {
                    var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                    var rowsDataIds = new Array();
                    for (var i = 0; i < selection.length; i++) {
                        if (selection[0] == undefined) {
                            continue;
                        }
                        if (selection[i].publishState == 1) {
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
            });
            deleteButton.click(function () {
                if (!deleteButton.jqxButton('disabled')) {
                    var selection = $("#treeGridOut").jqxTreeGrid('getSelection');
                    if (selection.length > 1) {
                        var keys = new Array();
                        for (var i = 0; i < selection.length; i++) {
                            if (selection[i] == undefined) {
                                continue;
                            }
                            var temp_row_key = $("#treeGridOut").jqxTreeGrid('getKey', selection[i]);

                            updateJson.push('{"type":2,' +
                                '"dataId":' + temp_row_key + ',' +
                                '"taskId":' + selection[i].taskId + ',' +
                                '"dataName":"' + selection[i].dataName + '",' +
                                '"isLeaf":"' + selection[i].isLeaf + '",' +
                                '"filePath":"' + selection[i].filePath + '",' +
                                '"dataType":"' + selection[i].dataType + '",' +
                                '"dataDescription":"' + selection[i].dataDescription + '",' +
                                '"dataUnit":"' + selection[i].dataUnit + '",' +
                                '"dataValue":"' + selection[i].dataValue + '",' +
                                '"parentId":"' + selection[i].parentId + '",' +
                                '"projectId":"' + projectId + '",' +
                                '"dataSenMin":"' + selection[i].dataSenMin + '",' +
                                '"dataSenMax":"' + selection[i].dataSenMax + '"}');

                            keys.push(temp_row_key);
                        }
                        $("#treeGridOut").jqxTreeGrid('deleteRow', keys);


                    } else {
                        $("#treeGridOut").jqxTreeGrid('deleteRow', rowKey);
                        for (var i = 0; i < updateJson.length; i++) {
                            var tempJson = $.parseJSON(updateJson[i]);

                            if (selection[0].dataId == tempJson.dataId) {
                                var num = updateJson.splice(i, 1);   //从i位置开始删除
                                i = i - 1;    //改变循环变量
                            }
                        }
                        updateJson.push('{"type":2,' +
                            '"dataId":' + rowKey + ',' +
                            '"taskId":' + selection[0].taskId + ',' +
                            '"dataName":"' + selection[0].dataName + '",' +
                            '"isLeaf":"' + selection[0].isLeaf + '",' +
                            '"filePath":"' + selection[0].filePath + '",' +
                            '"dataType":"' + selection[0].dataType + '",' +
                            '"dataDescription":"' + selection[0].dataDescription + '",' +
                            '"dataUnit":"' + selection[0].dataUnit + '",' +
                            '"dataValue":"' + selection[0].dataValue + '",' +
                            '"parentId":"' + selection[0].parentId + '",' +
                            '"projectId":"' + projectId + '",' +
                            '"dataSenMin":"' + selection[0].dataSenMin + '",' +
                            '"dataSenMax":"' + selection[0].dataSenMax + '"}');
                    }
                    updateButtons('delete');
                }
                rowKey = null;
            });
        },
        columns: [
            {text: '名称', dataField: "dataName", align: 'left', width: '25%', pinned: true, editable: true},
            {
                text: '类型', dataField: "dataType", align: 'left', width: '10%', columnType: "template",value:"数值",
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
                    }
                    else {
                        return "<strong style='color: #b4372f' id=" + rowData.dataId + ">未发布</strong>";
                    }
                }
            }
        ]
    });

    // $("#xmlExport").jqxButton();
    // $("#csvExport").jqxButton();
    // $("#jsonExport").jqxButton();
    // $("#pdfExport").jqxButton();
    // $("#excelExport").jqxButton();
    //
    // $("#xmlExport").click(function () {
    //     $("#treeGridOut").jqxTreeGrid('exportData', 'xml');
    // });
    // $("#excelExport").click(function () {
    //     $("#treeGridOut").jqxTreeGrid('exportData', 'xls');
    // });
    // $("#csvExport").click(function () {
    //     $("#treeGridOut").jqxTreeGrid('exportData', 'csv');
    // });
    // $("#jsonExport").click(function () {
    //     $("#treeGridOut").jqxTreeGrid('exportData', 'json');
    // });
    // $("#pdfExport").click(function () {
    //     $("#treeGridOut").jqxTreeGrid('exportData', 'pdf');
    // });

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

        // getid();
        // alert(id);
        //  for (var i = 0; i < updateJson.length; i++) {
        //      var tempJson = $.parseJSON(updateJson[i]);
        //      if (rowKey == tempJson.dataId) {
        //          var num = updateJson.splice(i, 1);   //从i位置开始删除
        //          i = i - 1;    //改变循环变量
        //      }
        //  }
        // alert(rowKey);
        if (rowData.type == 0) rowData.type = 1
        // var target = $(event.target);
        // target.find('.publishButtons').hide();
        // if (rowData.dataId == 0 || rowData.dataId == "0") {
        //     rowData.dataId = rd(1, 999999)
        // }
        updateJson.push('{"type":' + rowData.type + ',' +
            '"dataId":' + rowKey + ',' +
            '"taskId":' + rowData.taskId + ',' +
            '"dataName":"' + rowData.dataName + '",' +
            '"isLeaf":"' + rowData.isLeaf + '",' +
            '"filePath":"' + rowData.filePath + '",' +
            '"dataType":"' + rowData.dataType + '",' +
            '"dataDescription":"' + rowData.dataDescription + '",' +
            '"dataUnit":"' + rowData.dataUnit + '",' +
            '"dataValue":"' + rowData.dataValue + '",' +
            '"parentId":"' + rowData.parentId + '",' +
            '"projectId":"' + projectId + '",' +
            '"dataSenMin":"' + rowData.dataSenMin + '",' +
            '"dataSenMax":"' + rowData.dataSenMax + '"}');
        // $("#treeGridOut").on('rowUnselect', function (event) {
        // });
    });
}