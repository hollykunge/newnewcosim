<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/3/24
  Time: 上午11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>导入私有数据</title>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title" id="myModalLabel">导入excel选择</h4>
</div>
<form class="form-horizontal" name="excelImportForm" action="${ctx}/datadriver/privatedata/importBrandSort.ht?id=<%=request.getParameter("id")%>&&projectId=<%=request.getParameter("projectId")%>" id="uploadForm"
      method="post" enctype="multipart/form-data">
    <div class="modal-body">
        <div class="alert alert-danger">导入excel文件格式必须严格按照<a class="alert-link" href="${ctx}/platform/system/sysFile/download.ht?fileId=10000028170042">上传文件格式(点击查看)</a></div>
        <div class="form-group">
            <div class="col-lg-12 col-lg-offset-2">
                <input class="btn btn-default" id="excel_file" type="file" name="filename"
                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <input class="btn btn-success" id="excel_button" type="submit" value="导入Excel"/>
    </div>
</form>

</body>
</html>
