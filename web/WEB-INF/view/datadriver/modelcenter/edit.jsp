<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<%@page import="com.hotent.core.util.ContextUtil" %>
<html>
<head>
    <title>模型增加</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-table.css">
    <link rel="stylesheet" href="${ctx}/newtable/bootstrap-editable.css">
    <%--&lt;%&ndash;<script src="${ctx}/newtable/jquery.js"></script>&ndash;%&gt;--%>
    <%--<script src="${ctx}/newtable/bootstrap.js"></script>--%>
    <script src="${ctx}/newtable/bootstrap-table.js"></script>
    <script src="${ctx}/newtable/bootstrap-table-zh-CN.js"></script>
    <%--<script src="${ctx}/newtable/tableExport.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-editable.js"></script>--%>
    <%--<script src="${ctx}/newtable/bootstrap-table-editable.js"></script>--%>

    <%--<%@include file="/commons/include/form.jsp" %>--%>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.validate.ext.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>

    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <link href="${ctx}/styles/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        //@ sourceURL=eidt.ht
        $(function () {
            var options = {};
            var frm = $('#userForm2').form();
            $("#dataFormSave").click(function () {
                frm.setData();
                frm.ajaxForm(options);
                if (frm.valid()) {
                    form.submit();
                    $("#addModel").modal('hide');
                    $("#tb_departments").bootstrapTable("refresh");
                }
            });
        });
            function _add(){
                var tb = document.getElementById("AddHandlingFee");
                //写入一行
                var tr = tb.insertRow();
                //写入列
                var td = tr.insertCell();
                //写入数据
                td.innerHTML="模型图片：";
                //再声明一个新的td
                var td2 = tr.insertCell();
                //写入一个input
                var timestamp = (new Date()).valueOf();
                td2.innerHTML='<input type="file" name='+timestamp+'><button onclick="_del(this);">删除</button>';
            }
        function _del(btn){
            var tr = btn.parentNode.parentNode;
            //alert(tr.tagName);
            //获取tr在table中的下标
            var index = tr.rowIndex;
            //删除
            var tb = document.getElementById("AddHandlingFee");
            tb.deleteRow(index);
        }
        function _submit(){
            //遍历所的有文件
            var files = document.getElementsByName("file");
            if(files.length==0){
                alert("没有可以上传的文件");
                return false;
            }
            for(var i=0;i<files.length;i++){
                if(files[i].value==""){
                    alert("第"+(i+1)+"个文件不能为空");
                    return false;
                }
            }
//            document.forms['xx'].submit();
        }
    </script>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">上传模型文件</h4>
</div>
<div class="container-fluid">
    <div class="modal-body">
        <form name="userForm2" id="userForm2"
              action="${ctx}/datadriver/modelcenter/save.ht?Modeltype=<%=new String(request.getParameter("Modeltype").getBytes("ISO-8859-1"),"UtF-8")%>"
              enctype="multipart/form-data" method="post" multiple="true">
            <table id="AddHandlingFee" class="table table-striped" cellpadding="0" cellspacing="0"
                   border="0"
                   type="main">
                <tr>
                    <th width="20%">模型名称:</th>
                    <td><input type="text" id="ddModelName" name="ddModelName"
                               value="" class="form-control"/></td>
                </tr>
                <tr>
                    <th width="20%">模型版本:</th>
                    <td><input type="text" id="ddModelVersion" name="ddModelVersion"
                               value="" class="form-control"/></td>
                </tr>
                <tr>
                    <th width="20%">模型状态:</th>
                    <td>
                        <div class="radio radio-info radio-inline">
                            <input type="radio" name="ddModelBf2" id="ddModelBf21" value="1" checked>
                            <label for="ddModelBf21">
                                模型上传
                            </label>
                            <br>
                            <input type="radio" name="ddModelBf2" id="ddModelBf20" value="0">
                            <label for="ddModelBf20">
                                模型更新
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="20%">模型说明:</th>
                    <td colspan="3"><textarea class="form-control" rows="4" id="DdModelExplain"
                                              name="DdModelExplain"></textarea></td>
                </tr>
                <tr id="newUpload2">
                    <th width="20%">上传模型:</th>
                    <td><input type="file" name="modelfile" value="上传"></td>
                </tr>
                <tr id="newUpload3">
                    <th width="20%">模型说明:</th>
                    <td><input type="file" name="modelsm" value="上传"></td>
                </tr>
                <tr id="newUpload4">
                    <th width="20%">模型结果:</th>
                    <td><input type="file" name="modeljg" value="上传"></td>
                </tr>
                <tr id="newUpload5">
                    <th width="20%">模型图片:</th>
                    <td><input type="file" name="modelImg" value="上传">
                        <button onclick="_del(this);">删除</button>
                    </td>
                </tr>

            </table>
        </form>
    </div>
    <div class="modal-footer">
        <input onclick="_add();" type="button" value="增加图片" class="btn btn-primary btn-block"/>
        <input id="dataFormSave" value="上传" type="submit" class="btn btn-primary btn-block"/>
    </div>
</div>
</body>
</html>