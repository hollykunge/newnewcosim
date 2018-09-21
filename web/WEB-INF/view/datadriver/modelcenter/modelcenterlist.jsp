<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>模型</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>

    <link rel="stylesheet" href="${ctx}/newtable/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">
    <script src="${ctx}/newtable/jquery.js"></script>
    <script src="${ctx}/newtable/bootstrap.js"></script>
    <script src="${ctx}/newtable/bootstrap-table.js"></script>
    <script src="${ctx}/newtable/bootstrap-table-zh-CN.js"></script>
    <script src="${ctx}/newtable/tableExport.js"></script>
    <script src="${ctx}/newtable/bootstrap-editable.js"></script>
    <script src="${ctx}/newtable/bootstrap-table-editable.js"></script>
    <script src="${ctx}/newtable/bootstrap-table-filter-control.js"></script>

    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="toolbar" class="btn-group">
    <a class="btn btn-info" href="javascript:void(0)" id="create_index" onclick="createIndex()"><span
            class="glyphicon glyphicon-plus"></span> 新增模型</a>
</div>
<table id="tb_departments" data-filter-control="true"
       data-url="showmodel.ht?Modeltype=<%=request.getParameter("Modeltype")%>&son=1">
    <thead>
    <tr>
        <th data-field="ModelName" >模型名称----(双击进行附件下载)----</th>
        <th data-field="Modelbz" >说明</th>
    </tr>
    </thead>
</table>
<script src="${ctx}/newtable/showmodel.js"></script>

<div class="modal fade" id="addModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
</body>
<script charset=UTF-8>
    function createIndex() {
        $('#addModel').modal({
            keyboard: true,
            remote: 'edit.ht?Modeltype=<%=request.getParameter("Modeltype")%>'
        });
    }
</script>
</html>