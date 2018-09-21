<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/16
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%--
	time:2013-04-11 11:48:44
	desc:edit the 业务定义，如邀标采购这样的大业务。
--%>
<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@page import="com.hotent.core.util.ContextUtil" %>
<html lang="zh-CN">
<head>
    <title>发布/订购数据</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/datadriver/formbase.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>

    <style>
        #iframe_tab {
            margin-top: 0px;
        }

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
        <li class="layui-this" id="init">发布数据</li>

    </ul>
    <div class="layui-tab-content" style="height: 100%;">
        <!--任务发布数据-->
        <blockquote class="layui-elem-quote">
            <div style="height: 40px;">
                <span class="fl">
                    <form id="searchForm" method="post" action="list.ht">
                      <input type="text" name="ddDataName" class="layui-input"
                             value="${param['ddDataName']}" placeholder="数据名称"/>
                    </form>
                </span>
                <span class="fr">
                    <a class="layui-btn layui-btn-normal" id="checktask" href="checktask.ht?id=${TaskId}"><i
                            class="layui-icon">&#xe615;</i> 审核通过</a>
                    <a class="layui-btn layui-btn-normal" id="Search"><i class="layui-icon">&#xe615;</i> 查询</a>
                    <a class="layui-btn layui-btn-primary" onclick="location.reload()"><i
                            class="layui-icon">&#x1002;</i> 刷新</a>
                </span>
            </div>
        </blockquote>
        <div class="layui-tab-item">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="privateDataList" id="privateDataList" requestURI="check.ht"
                           sort="external" cellpadding="1"
                           cellspacing="1" export="false" class="layui-table" pagesize="10">
                <display:column title="${checkAll}" media="html" style="width:5%;">
                    <input type="checkbox" class="pk" name="id" value="${privateDataList.ddTaskId}">
                </display:column>
                <display:column property="ddDataId" title="数据编号" sortable="true" maxLength="80"></display:column>
                <display:column property="ddDataName" title="数据名称" sortable="true" maxLength="80"></display:column>
                <display:column property="ddDataType" title="数据类型" sortable="true" maxLength="80"></display:column>
                <display:column property="ddDataDescription" title="数据描述" sortable="true"
                                maxLength="80"></display:column>
                <display:column property="ddDataTaskId" title="数据所属任务id" sortable="true"
                                maxLength="80"></display:column>
                <display:column title="操作" media="html" style="width:100px">
                    <a href="${ctx}/datadriver/datacenter/dataversion.ht?id=${privateDataList.ddDataId}"
                       class="layui-btn layui-btn-normal  layui-btn-small"><i
                            class="layui-icon">&#xe615;</i> 数据版本</a>
                </display:column>
            </display:table>
        </div>

    </div>
</div>
<script src="${ctx}/styles/layui/lay/dest/layui.all.js"></script>
<script>
    $('#init').trigger('click');
</script>
</body>
</html>

