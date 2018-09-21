<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>任务基础信息列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <%@include file="/commons/datadriver/getbase.jsp" %>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <style>
        .fl {
            float: left;
        }

        .fr {
            float: right;
        }

        .pages {
            float: right;
        }

        .page_line {
            display: inline;
        }
    </style>

</head>
<body>

<div class="layui-tab layui-tab-card" id="iframe_tab">
    <ul class="layui-tab-title">
        <li class="layui-this" id="init">任务管理列表</li>
        <li>任务审核</li>
    </ul>
    <div class="layui-tab-content" style="height: 100%;">
        <!--任务管理-->
        <blockquote class="layui-elem-quote">
            <div style="height: 40px;">
                <span class="fl">
                    <form id="searchForm" method="post" action="list.ht">
                        <input type="text" name="Q_name_SL " class="layui-input"
                               value="${param['Q_name_SL']}" placeholder="任务名称"/>
                    </form>
                </span>
                <span class="fr">
                    <a class="layui-btn layui-btn-normal" id="Search"><i class="layui-icon">&#xe615;</i> 查询</a>
                            <a class="layui-btn" href="edit.ht" id="taskInfoForm"><i class="layui-icon">&#xe61f;</i> 添加</a>
                    <a class="layui-btn layui-btn-danger" action="del.ht"><i class="layui-icon">&#xe640;</i> 删除</a>
                            <a class="layui-btn layui-btn-primary" action="#" onclick="location.reload()"><i
                                    class="layui-icon">&#x1002;</i> 刷新</a>
                </span>
            </div>
        </blockquote>
        <div class="layui-tab-item layui-show">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="taskList" id="TaskItem" requestURI="list.ht" sort="external" cellpadding="0"
                           cellspacing="0" export="false" class="layui-table lay-even" pagesize="10">
                <display:column title="${checkAll}" media="html" style="width:3%;">
                    <input type="checkbox" class="pk" name="id" value="${TaskItem.ddTaskId}">
                </display:column>
                <display:column property="ddTaskName" title="任务名称" sortable="true" maxLength="60"></display:column>
                <display:column property="ddTaskPerson" title="任务负责人" sortable="true"></display:column>
                <display:column property="ddTaskType" title="任务类型" maxLength="80"></display:column>
                <display:column property="ddTaskProjectName" title="所属项目" maxLength="80"></display:column>
                <display:column title="操作" media="html" style="width:400px">
                    <a href="edit.ht?id=${TaskItem.ddTaskId}" class="layui-btn layui-btn-mini"><i class="layui-icon">
                        &#xe642;</i> 编辑</a>
                    <a href="${ctx}/datadriver/privatedata/list.ht?id=${TaskItem.ddTaskId}"
                       class="layui-btn layui-btn-normal layui-btn-mini"><i class="layui-icon">&#xe612;</i> 私有</a>
                    <a href="stepinto.ht?id=${TaskItem.ddTaskId}"
                       class="layui-btn layui-btn-mini"><i class="layui-icon">&#x1005;</i> 进入</a>
                    <a href="del.ht?id=${TaskItem.ddTaskId}"
                       class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe640;</i> 删除</a>
                </display:column>
            </display:table>
        </div>
        <!--审核任务-->
        <div class="layui-tab-item">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="taskCheckList" id="TaskCheckItem" requestURI="taskcheck.ht" sort="external"
                           cellpadding="0"
                           cellspacing="0" export="false" class="layui-table lay-even" pagesize="10">
                <display:column title="${checkAll}" media="html" style="width:3%;">
                    <input type="checkbox" class="pk" name="id" value="${TaskCheckItem.ddTaskId}">
                </display:column>
                <display:column property="ddTaskName" title="任务名称" sortable="true" maxLength="60"></display:column>
                <display:column property="ddTaskPerson" title="任务负责人" sortable="true"></display:column>
                <display:column property="ddTaskType" title="任务类型" maxLength="80"></display:column>
                <display:column property="ddTaskProjectName" title="所属项目" maxLength="80"></display:column>
                <display:column title="审核" media="html" style="width:320px">
                    <a href="${ctx}/datadriver/datacenter/publishorderdata.ht?id=${TaskCheckItem.ddTaskId}"
                       class="layui-btn layui-btn-mini"><i class="layui-icon">&#xe642;</i> 审核</a>
                    <a href="rejecttask.ht"
                       class="layui-btn layui-btn-normal layui-btn-mini"><i class="layui-icon">&#xe612;</i> 驳回</a>
                </display:column>
            </display:table>
        </div>
    </div>
</div>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
</body>
<script>
    $('#init').trigger('click');
    $('#taskInfoForm').on('click', function () {
        layer.open({
            type: 2,
            title: "任务信息配置", //不显示标题栏
            shadeClose: true,
            shade: 0.8,
            area: ['95%', '80%'],
            id: 'layer_taskinfo', //设定一个id，防止重复弹出
            moveType: 1,//拖拽模式，0或者1
            content: '${ctx}/datadriver/task/addtask.ht?id=${projectId}'
//            ,
//            success: function (layero) {
//                var btn = layero.find('.layui-layer-btn');
//                btn.css('text-align', 'center');
//                btn.find('.layui-layer-btn0').attr({
//                    href: 'save.ht'
//                });
//            }
        });
    });
</script>
</html>


