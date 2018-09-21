/**
 * Created by 忠 on 2017/2/17.
 */

$(function () {
    //1.初始化Table
    new TableInit();
});
function TableInit () {
    $('#tb_departments').bootstrapTable({
        classes: "table table-condensed table-hover",
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 15,                       //每页的记录行数（*）
        pageList: [5,10,20,50],        //可供选择的每页的行数（*）
        queryParamsType:'',
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        uniqueId: "DdDataId",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: true,                   //是否显示父子表
        height: getHeight(),                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        columns: [
            {//第一列，数据名称
                field: 'DdDataName',
                title: '数据名称',
                sortable: true,
                editable: false,
                align: 'center',
                visible: true
            }, {//第二列，最新值
                field: 'DdDataLastestValue',
                title: '最新值',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//第三列
                field: 'DdDataType',
                title: '数据类型',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//第四列
                field: 'DdDataCreateTime',
                title: '更新时间',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//第五列，
                field: 'DdDataDescription',
                title: '描述',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            },
            {//第六列，
                field: 'DdDataId',
                title: '数据ID',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: false
            },
        ], onExpandRow: function (index, row, $detail) {
            InitSubTable(index, row, $detail);
        },
    });
    //初始化子表格(无线循环)
    function InitSubTable(index, row, $detail) {
        var parentid = row.ToolID;
        var cur_table = $detail.html('<table></table>').find('table');
        $(cur_table).bootstrapTable({
            url: 'dataversion.ht?id='+row.DdDataId,
            method: 'get',
            queryParams: { strParentID: parentid },
            ajaxOptions: { strParentID: parentid },
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            // pagination: true,                   //是否显示分页（*）
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            clickToSelect: true,
            detailView: false,//父子表
            uniqueId: "MENU_ID",
            pageSize: 10,
            pageList: [5,10,20,50],
            columns: [ {//第一列，数据名称
                field: 'DdDataVersion',
                title: '版本',
                sortable: true,
                editable: false,
                align: 'center',
                visible: true
            }, {//第二列，数据值
                field: 'ddDataValue',
                title: '数据值',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            }, {//第三列
                field: 'ddDataRecordTime',
                title: '更新时间',
                sortable: true,
                editable: false,
                // footerFormatter: ddDataNameFormatter,
                align: 'center',
                visible: true
            },
            ],
            //无线循环取子表，直到子表里面没有记录
            onExpandRow: function (index, row, $Subdetail) {
                InitSubTable(index, row, $Subdetail);
            },
            onClickRow:function (row, tr)
            {
                window.location.href=row.ToolUrl;
            }
        });
    };
    //设置table高度
    function getHeight() {
        return $(window).height() - $('.panel-heading').outerHeight(true) - 120;
    }
    // 得到查询的参数
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $("#txt_search_statu").val(),
            search:params.search
        };
    };
};