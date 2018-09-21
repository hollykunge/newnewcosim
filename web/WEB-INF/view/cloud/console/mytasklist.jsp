<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/2/26
  Time: 下午4:31
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的任务列表</title>
    <%@include file="/newtable/tablecontext.jsp" %>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">待办任务列表</h4>
</div>
<div class="modal-body">
    <table id="mytasklist"></table>
</div>

</body>

<script type="text/javascript">
    var $table = $('#mytasklist'),
            $open = $('#open');
    var curRow = {};
    function initTable() {
        $table.bootstrapTable({
            idField: "ddTaskId",
            url: "mytasklistjson.ht?id=${projectId}",
            //   pageList: [10, 25],
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            queryParamsType: '',
            // queryParams:queryParams,
            pageList: [5, 10, 20, 50],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            height: 380,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ddTaskId",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if (row.ddTaskPriority == "3") {
                    strclass = 'danger';//还有一个active
                }else if (row.ddTaskPriority == "2"){
                    strclass = 'warning';//还有一个active
                }
                else {
                    return {};
                }
                return { classes: strclass }
            },
            columns: [
                {//第一列，数据ID
                    field: 'ddTaskId',
                    title: '任务Id',
                    sortable: true,
                    editable: false,
                    align: 'center',
                    visible: false
                }, {//第二列，名称
                    field: 'ddTaskName',
                    title: '任务名称',
                    sortable: true,
                    align: 'center',
                    visible: true
                }, {//第二列，名称
                    field: 'ddTaskProjectName',
                    title: '所属项目',
                    sortable: true,
                    align: 'center',
                    visible: true
                }, {
                    field: 'ddTaskPriority',
                    title: '优先级',
                    sortable: true,
                    align: 'center',
                    visible: false
                }, {
                    field: 'ddTaskPlanEndTime',
                    title: '截至时间',
                    sortable: true,
                    align: 'center',
                    visible: true
                }, {
                    field: 'ddTaskDescription',
                    title: '描述',
                    sortable: true,
                    align: 'center',
                    visible: true
                }
//                , {
//                    field: 'operate',
//                    title: '操作',
//                    align: 'center',
//                    events: operateEvents,
//                    formatter: operateFormatter()
//                }
            ],
            onClickRow: function (row, $element) {
                curRow = row;
            }
        });

        // sometimes footer render error.
        setTimeout(function () {
            $table.bootstrapTable('resetView');
        }, 200);
    }
    //原始操作按钮
    function operateFormatter(value, row, index) {
        return [
            '<a id="open" class="open" href="javascript:void(0)" title="打开工作面板">',
            '<i class="glyphicon glyphicon-folder-open"></i>',
            '</a>'
        ].join('');
    }

    window.operateEvents = {
        'click .open': function (e, value, row, index) {
            <%--window.location.href='${ctx}/datadriver/personaltask/todotask.ht?id=' + row.ddTaskId;--%>
            window.location.href='${ctx}/datadriver/personaltask/list.ht';
        }
    };
    $(function () {
        initTable();
    });
</script>
</html>
