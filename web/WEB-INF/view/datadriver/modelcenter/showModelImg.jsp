<%--
  Created by IntelliJ IDEA.
  User: 忠
  Date: 2017/10/9
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>模型预览</title>
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">
    <script type="text/javascript">
        function imgPreview(fileDom){
            //判断是否支持FileReader
            if (window.FileReader) {
                var reader = new FileReader();
            } else {
                alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
            }

            //获取文件
            var file = fileDom.files[0];
            var imageType = /^image\//;
            //是否是图片
            if (!imageType.test(file.type)) {
                alert("请选择图片！");
                return;
            }
            //读取完成
            reader.onload = function(e) {
                //获取图片dom
                var img = document.getElementById("preview");
//                var img = "D:\\IMG_3210.jpg";
                //图片路径设置为读取的图片
//                img.src = e.target.result;
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    </script>
</head>
<body id="task_board">
<div class="row paneldocker">
    <div class="col-xs-3" style="height: 100%">

    </div>
    <div class="col-xs-7" style="height: 100%">
        <img id="preview" />
        <c:forEach var="d" items="${ImgList}">
            <img src="${ctx}${d}"/>
        </c:forEach>
        <a href="javascript:history.back(-1)">返回</a>
    </div>

    <div class="col-xs-2" style="height: 100%">
    </div>
</div>
</body>
<body>


</html>
