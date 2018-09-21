<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/20 0020
  Time: 下午 3:41
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
    <title>项目指标信息列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.ext.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body>
<form id="indexform" name="indexform" method="post" action="${ctx}/datadriver/index/save.ht"
      enctype="multipart/form-data">
    <table id="AddHandlingFee" class="table table-bordered" cellpadding="0" cellspacing="0"
           border="0"
           type="main">
        <tr>
            <th width="20%">指标属性:</th>
            <td>
                <textarea id="editor" id="ddIndexLastestValue" name="ddIndexLastestValue">
                    <c:if test="${not empty indexContent}">
                        ${indexContent}
                    </c:if>
                     <c:if test="${empty indexContent}">
                         无指标项（请在此编辑指标内容，也可直接粘贴指标文档内容到此处）。
                     </c:if>
                </textarea>
            </td>
        </tr>
    </table>
    <input id="ddIndexProjectId" name="ddIndexProjectId" type="hidden" value="${projectId}"/>
    <input id="ddIndexId" name="ddIndexId" type="hidden" value="${indexId}"/>
</form>
<a href="javascript:void(0)" class="btn btn-success pull-right" id="indexDataFormSave"><span
        class="glyphicon glyphicon-floppy-saved"></span> 保存指标</a>
</body>
<script type="text/javascript">
    function getWidth() {
        return $('#AddHandlingFee').outerWidth() - 60;
    }
    function getHeight() {
        return $(window).height() - $('.nav-tabs').outerHeight(true) - 180;
    }
    $(function () {
        $('#editor').jqxEditor({
            height: getHeight(),
            width: getWidth(),
            theme: 'bootstrap',
        });
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        var frm = $('#indexform').form();
        $("#indexDataFormSave").click(function () {
            frm.setData();
            frm.ajaxForm(options);
            if (frm.valid()) {
                form.submit();
            }
        });
    });

    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
            alert("保存成功！")
        } else {
        }
    }
</script>
</html>




