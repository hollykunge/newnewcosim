/**
 * Created by d on 2017/5/18.
 * 数据版本详情表格初始化
 */
function getWidth() {
    return $('#act').outerWidth();
}
function getHeight() {
    return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
}
function dataVersionTableInitInProject(path, versionId) {
    var cellClass = function (row, dataField, cellText, rowData) {
        var cellValue = rowData[dataField];
        if (cellValue < rowData.dataSenMin) {
            return "min";
        }
        if (cellValue > rowData.dataSenMax) {
            return "max";
        }
        return;
    }
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
                {name: "dataSenMin", type: "number"}
            ],
            hierarchy: {
                keyDataField: {name: 'dataId'},
                parentDataField: {name: 'parentId'}
            },
            id: 'dataId',
            url: path
        };
    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadComplete: function () {
            // data is loaded.
        }
    });
// 创建列表
    $("#table_version_detail").jqxTreeGrid(
        {
            width: getWidth(),
            height: getHeight(),
            source: dataAdapter,
            sortable: true,
            pageable: true,
            theme: 'bootstrap',
            pagerMode: 'advanced',
            ready: function () {
                $("#table_version_detail").jqxTreeGrid('expandRow', '2');
            },
            columns: [
                {text: '名称', dataField: "dataName", align: 'left', width: '20%', pinned: true},
                {text: '类型', dataField: "dataType", align: 'left', width: '5%'},
                {text: '最新值', dataField: "dataValue", align: 'left', width: '35%', cellClassName: cellClass},
                {text: '最小值', dataField: "dataSenMin", align: 'left', width: '20%'},
                {text: '最大值', dataField: "dataSenMax", align: 'left', width: '20%'}
            ]
        });
}