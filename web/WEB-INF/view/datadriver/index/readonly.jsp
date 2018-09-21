<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/4/5
  Time: 上午9:13
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-CN">
<head>
    <title>只读指标信息</title>
    <%--<link href="${ctx}/newtable/bootstrap.css" rel="stylesheet" type="text/css"/>--%>
</head>
<body>
<div class="btn-group" role="group" aria-label="..." style="margin-bottom: 5px;margin-top: 5px">
    <button type="button" class="btn btn-sm btn-default" onclick="importPrivateData(${taskId}, ${projectId})"><span
            class="glyphicon glyphicon-import"></span> 其它
    </button>

    <div class="btn-group" role="group">
        <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="false"><span class="glyphicon glyphicon-export"></span>
            导出数据
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li><a href="#">Excel格式</a></li>
            <li><a href="#">CVS格式</a></li>
        </ul>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <strong><span class="glyphicon glyphicon-list-alt"></span> 项目指标</strong>
    </div>
    <div class="panel-body">
        <div id="editor" style="overflow: scroll">
            <c:if test="${not empty indexContent}">
                ${indexContent}
            </c:if>
            <c:if test="${empty indexContent}">
                无指标项。
            </c:if>
        </div>
    </div>
</div>

<script type="text/javascript">
    //    function getWidth() {
    //        return $('#index').outerWidth();
    //    }
    //    function getHeight() {
    //        return $(window).height() - $('.nav-tabs').outerHeight(true) - 100;
    //    }
    //    $(function () {
    //        $('#editor').jqxEditor({
    //            height: getHeight(),
    //            width: getWidth(),
    //            theme: 'darkblue',
    //            editable: false
    //        });
    //    });
</script>
</body>
</html>





