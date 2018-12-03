<%--
  Created by IntelliJ IDEA.
  User: holly
  Date: 2018/12/2
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>删除所有未发布数据</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="myModalLabel">删除所有已选类型数据</h4>
</div>
<div class="modal-body">
    <div class="btn-group" data-toggle="buttons">
        <label class="btn btn-default active">
            <input type="radio" name="options" id="option1" autocomplete="off" checked> 未发布数据
        </label>
        <label class="btn btn-default">
            <input type="radio" name="options" id="option2" autocomplete="off"> 数值型
        </label>
        <label class="btn btn-default">
            <input type="radio" name="options" id="option3" autocomplete="off"> 结构型
        </label>
        <label class="btn btn-default">
            <input type="radio" name="options" id="option4" autocomplete="off"> 文件型
        </label>
    </div>
</div>

<div class="modal-footer">
    <a type="button" class="btn btn-danger" href="delAllData.jsp"><span class="glyphicon glyphicon-ok"></span> 删除</a>
    <a type="button" class="btn btn-default" href=""><span class="glyphicon glyphicon-remove"></span> 取消</a>
</div>
</body>
</html>
