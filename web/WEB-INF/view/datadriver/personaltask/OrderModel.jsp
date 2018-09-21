<%--
  Created by IntelliJ IDEA.
  User: 忠
  Date: 2017/3/2
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>模型</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <%--<link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">--%>
    <%--<link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">--%>
    <%--&lt;%&ndash;<script src="${ctx}/newtable/jquery.js"></script>&ndash;%&gt;--%>
    <%--<script src="${ctx}/newtable/bootstrap.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-table.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-table-zh-CN.js"></script>--%>
    <%--<script src="${ctx}/newtable/tableExport.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-editable.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-table-editable.js"></script>--%>



</head>
<body>
<table id="tb_departments" data-url="${ctx}/datadriver/modelcenter/showmodel.ht?id=<%=request.getParameter("id")%>&son=3">
</table>
</body>
<script type="text/javascript">
    $(function () {
//1.初始化Table
        new TableInit();

    });


    function TableInit() {
//初始化Table
        $('#tb_departments').bootstrapTable({
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            searchOnEnterKey: true,
            queryParamsType: '',
            pageList: [5, 10, 20, 50],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ModelID",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [
                {//第一列，模型ID
                    field: 'ModelID',
                    title: '模型ID',
                    sortable: false,
                    editable: false,
                    align: 'center',
                    visible: false
                }, {//第二列，模型地址
                    field: 'ModelUrl',
                    title: '模型地址',
                    sortable: false,
                    editable: false,
// footerFormatter: ddDataNameFormatter,
                    align: 'center',
                    visible: false
                }, {//第三列，模型版本
                    field: 'ModelVersion',
                    title: '模型版本',
                    sortable: false,
                    editable: false,
// footerFormatter: ddDataNameFormatter,
                    align: 'center',
                    visible: false
                }, {//第四列，名称
                    field: 'ModelName',
                    title: '模型名称',
                    sortable: true,
                    editable: false,
// footerFormatter: ddDataNameFormatter,
                    align: 'center',
                    visible: true
                }, {//第五列，名称
                    field: 'Modelbz',
                    title: '备注',
                    sortable: false,
                    editable: false,
// footerFormatter: ddDataNameFormatter,
                    align: 'center',
                    visible: true
                }, {//第六列，模型类型
                    field: 'ModelType',
                    title: '模型类型',
                    sortable: true,
                    editable: false,
// footerFormatter: ddDataNameFormatter,
                    align: 'center',
                    visible: false
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: operateFormatter(),
                    visible: true
                },
            ], onExpandRow: function (index, row, $detail) {
                InitSubTable(index, row, $detail);
            },
        });
    }
    ;
    //注册加载子表的事件。注意下这里的三个参数！
    //原始操作按钮
    function operateFormatter(value, row, index) {
        return [
            '<a id="download0" class="download" href="javascript:void(0)" title="下载">',
            '<i class="glyphicon glyphicon-download-alt"></i>',
            '</a>  ',
            '  <a id="removetr" class="remove" href="javascript:void(0)" title="删除">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
    var operateEvents;
    window.operateEvents = {
        'click .remove': function (e, value, row, index) {
            $.get('${ctx}/datadriver/modelcenter/remove.ht?id=' + row.ModelID);
            $("#tb_departments").bootstrapTable("refresh");
        },
        'click .download': function (e, value, row, index) {
            window.location.href = "${ctx}/datadriver/modelcenter/getmodel.ht?modelurl=" + row.ModelUrl + "&modelname=" + row.ModelName;
        }
    };

//    var ButtonInit = function ButtonInit() {
//        var oInit = new Object();
//        var postdata = {};
//
//        oInit.Init = function () {
////初始化页面上面的按钮事件
//        };
//        return oInit;
//    };
</script>
</html>
