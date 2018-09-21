/**
 * Created by dodo on 2016/12/31.
 */
var $table = $('#tablelist'),
    $remove = $('#remove'),
    selections = [];
var curRow = {};
function initTable() {
    $table.bootstrapTable({
        height: getHeight(),
        //search:false,
        //showColumns: false,
        //showToggle: false,
        //showRefresh: false,
        //showExport: false,
        //sortTable: true,
        //detailView: true,
        //detailFormatter:"detailFormatter",
        //minimumCountColumns:2,
        //showPaginationSwitch:false,
        //pagination:true,
        //striped:true,
        //showHeader:true,
        //pageSize:10,
        //pageList:[10,25,50,100,ALL],
        //showFooter:false,
        //sidePagination:"server",
        //method: 'get',
        //url: "submitpublishjson.ht?id=${taskId}",
        //responseHandler:"responseHandler",
        //idField:"id",
        columns: [
            {//第一列，数据ID
                field: 'ddDataId',
                title: '数据ID',
                sortable: true,
                editable: false,
                align: 'center',
                visible: false
            }, {//第二列，名称
                field: 'ddDataName',
                title: '数据名称',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//所属任务ID
                field: 'ddDataTaskId',
                title: '所属任务ID',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: false
            }
            , {//第三列，数值
                field: 'ddDataLastestValue',
                title: '值',
                sortable: true,
                align: 'center',
                editable: {
                    type: 'text',
                    title: '值',
                    validate: function (v) {
                        if (isNaN(v)) return '值必须是数字';
                    }
                }
                //,
                // footerFormatter: ddDataLastestValueFormatter
            }, {//变更原因
                field: 'ddDataChangeReason',
                title: '变更原因',
                sortable: false,
                editable: true,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//数据类型
                field: 'ddDataType',
                title: '数据类型',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//所属任务
                field: 'ddDataTaskName',
                title: '所属任务',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }
            // , {//第四列，操作
            //     field: 'operate',
            //     title: '操作',
            //     align: 'center',
            //     events: operateEvents,
            //     formatter: operateFormatterRefresh
            // }
        ],
        onClickRow: function (row, $element) {
            curRow = row;
        },
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            InitSubTable(index, row, $detail);
        },
        //查询参数,每次调用是会带上这个参数，可自定义
        queryParams: function(params) {
            var name = $('#ddDataName').val();
            return {
                pageNumber: params.offset+1,
                pageSize: params.limit,
                name:name
            };
        },
        //更新发布数据value
        onEditableSave: function (field, row, oldValue, $el) {
            $.ajax({
                type: "post",
                url: "refreshlastvalue.ht",
                data: { strJson: JSON.stringify(row) },
                success: function (data, status) {
                    if (status == "success") {
                        alert("编辑成功");
                    }
                },
                error: function () {
                    alert("Error");
                },
                complete: function () {

                }

            });
        }
        // data: [{//数据
        //     id: 1,
        //     name: 'Item 1',
        //     price: '$1'
        // }, {
        //     id: 2,
        //     name: 'Item 2',
        //     price: '$2'
        // }]
    });

    // sometimes footer render error.
    setTimeout(function () {
        $table.bootstrapTable('resetView');
    }, 200);
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);

        // save your data, here just save the current page
        selections = getIdSelections();
        // push or splice the selections if you want to save all data selections
    });
    $table.on('expand-row.bs.table', function (e, index, row, $detail) {
        //if (index % 2 == 1) {
        //    $detail.html('Loading from ajax request...');
        //    $.get('LICENSE', function (res) {
        //        $detail.html(res.replace(/\n/g, '<br>'));
        //    });
        //}
       InitSubTable(index, row, $detail);
    });
    $remove.click(function () {
        var ids = getIdSelections();
        $table.bootstrapTable('remove', {
            field: 'id',
            values: ids
        });
        $remove.prop('disabled', true);
    });
    // $(window).resize(function () {
    //     $table.bootstrapTable('resetView', {
    //         height: getHeight()
    //     });
    // });
}

function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

function responseHandler(res) {
    $.each(res.rows, function (i, row) {
        row.state = $.inArray(row.id, selections) !== -1;
    });
    return res;
}

function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

//原始操作按钮
function operateFormatter(value, row, index) {
    return [
        '<a class="like" href="javascript:void(0)" title="Like">',
        '<i class="glyphicon glyphicon-heart"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:void(0)" title="Remove">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('');
}

//发布数据更新
function operateFormatterRefresh(value, row, index) {
    return [
        '<a class="refresh" href="javascript:void(0)" title="refresh">',
        '<i class="glyphicon glyphicon-refresh"></i>',
        '</a>'
    ].join('');
}

//操作按钮功能实现
window.operateEvents = {
    'click .like': function (e, value, row, index) {
        alert('You click like action, row: ' + JSON.stringify(row));
    },
    'click .refresh': function (e, value, row, index) {
        //添加更新功能
    },
    'click .remove': function (e, value, row, index) {
        $table.bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

function totalTextFormatter(data) {
    return 'Total';
}

function ddDataNameFormatter(value, row, index) {
    return "<a id=\"table_a\" href=\"#\" name=\"ddDataName\" data-type=\"text\" data-pk=\""+row.id+"\" data-title=\"用户名\">" + value + "</a>";
}

function ddDataLastestValueFormatter(data) {
    return "<a href=\"#\" name=\"ddDataLastestValue\" data-type=\"text\" data-pk=\""+row.id+"\" data-title=\"值\">" + value + "</a>";
    // var total = 0;
    // $.each(data, function (i, row) {
    //     total += +(row.price.substring(1));
    // });
    // return '$' + total;
}

function getHeight() {
    var length = $(window).height() - $('.layui-tab-title').outerHeight(true);
    return $(window).height() - $('.layui-tab-title').outerHeight(true);
}

     InitSubTable = function (index, row, $detail) {
    var parentid = row.MENU_ID;
    var cur_table = $detail.html('<table></table>').find('table');
    $(cur_table).bootstrapTable({
        url: "submitpublishjson.ht?id=${taskId}",
        method: 'get',
        queryParams: {strParentID: parentid},
        ajaxOptions: {strParentID: parentid},
        clickToSelect: true,
        detailView: true,//父子表
        detailFormatter:"detailFormatter",
        uniqueId: "MENU_ID",
        pageSize: 2,
        pageList: [10, 25, 50, 100, ALL],
        columns: [
            //    {
            //    checkbox: true
            //},
            {//第一列，数据ID
                field: 'ddDataId',
                title: '数据ID',
                sortable: true,
                editable: false,
                align: 'center',
                visible: false
            }, {//第二列，名称
                field: 'ddDataName',
                title: '数据名称',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//所属任务ID
                field: 'ddDataTaskId',
                title: '所属任务ID',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: false
            }
            , {//第三列，数值
                field: 'ddDataLastestValue',
                title: '值',
                sortable: true,
                align: 'center',
                editable: {
                    type: 'text',
                    title: '值',
                    validate: function (v) {
                        if (isNaN(v)) return '值必须是数字';
                    }
                }
                //,
                // footerFormatter: ddDataLastestValueFormatter
            }
            , {//数据类型
                field: 'ddDataType',
                title: '数据类型',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//所属任务
                field: 'ddDataTaskName',
                title: '所属任务',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }
        ],
        ////无线循环取子表，直到子表里面没有记录
        //onExpandRow: function (index, row, $Subdetail) {
        //    oInit.InitSubTable(index, row, $Subdetail);
        //}
    });
}

//
//initTable.InitSubTable = function (index, row, $detail) {
//    var parentid = row.MENU_ID;
//    var cur_table = $detail.html('<table></table>').find('table');
//    $(cur_table).bootstrapTable({
//        url: 'show.ht?id=${taskId}',
//        //method: 'post',
//        queryParams: {strParentID: parentid},
//        ajaxOptions: {strParentID: parentid},
//        clickToSelect: true,
//        detailView: true,//父子表
//        detailFormatter:"detailFormatter",
//        uniqueId: "MENU_ID",
//        pageSize: 2,
//        pageList: [10, 25, 50, 100, ALL],
//        columns: [
//            //    {
//            //    checkbox: true
//            //},
//            {//第一列，数据ID
//                field: 'ddDataVersionId',
//                title: '数据版本ID',
//                sortable: true,
//                editable: false,
//                align: 'center',
//                visible: false
//            }, {//第二列，名称
//                field: 'ddDataId',
//                title: '数据ID',
//                sortable: true,
//                editable: false,
//                // footerFormatter: ddDataNameFormatter,
//                align: 'center',
//                visible: true
//            }, {//所属任务ID
//                field: 'ddDataRecordTime',
//                title: '数据记录时间',
//                sortable: true,
//                editable: false,
//                // footerFormatter: ddDataNameFormatter,
//                align: 'center',
//                visible: false
//            }
//            , {//第三列，数值
//                field: 'ddDataRecordPersonId',
//                title: '数据记录人ID',
//                sortable: true,
//                align: 'center',
//                editable: {
//                    type: 'text',
//                    title: '值',
//                    validate: function (v) {
//                        if (isNaN(v)) return '值必须是数字';
//                    }
//                }
//                //,
//                // footerFormatter: ddDataLastestValueFormatter
//            }
//            , {//数据类型
//                field: 'ddDataValue',
//                title: '数据值',
//                sortable: true,
//                editable: false,
//                // footerFormatter: ddDataNameFormatter,
//                align: 'center',
//                visible: true
//            }
//        ],
//        ////无线循环取子表，直到子表里面没有记录
//        //onExpandRow: function (index, row, $Subdetail) {
//        //    oInit.InitSubTable(index, row, $Subdetail);
//        //}
//    });
//}
$(function () {
    initTable();
});
