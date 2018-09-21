<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.模型预览
  User: d
  Date: 2017/5/3
  Time: 下午8:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模型预览</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel"></h4>
</div>
<div class="modal-body">
<object classid="CLSID:FE5180BC-62B4-40F0-83E2-AB4D96B12558" width="100%" height = "1px"></object>
    <param name="Filepath" value="${dataPath}"/>
    <param name="VC_MAJOR_VER" value="4"/>
    <param name="VC_MINOR_VER" value="5"/>
    <param name="xpForceClear" value="1"/>
    <object type="application/x-vnd-vcollab-cax" data="${dataPath}" width="100%" height = "500px"></object>
</div>

</body>
</html>
