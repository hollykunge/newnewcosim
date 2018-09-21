<%--
  Created by IntelliJ IDEA.
  User: d
  Date: 2017/4/13
  Time: 上午9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传私有模型文件</title>
    <script>
        $(function () {
            var options = {};
            if (showResponse) {
                options.success = showResponse;
            }
            var frm = $('#uploadForm').form();
            $("#upload_button").click(function () {
                frm.setData();
                frm.ajaxForm(options);
                if (frm.valid()) {
                    form.submit();
                }
                $("#uploadPrivateFile").modal('hide');
                $('#table_private').bootstrapTable('refresh');
            });
        });

        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            } else {

            }
        }

    </script>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title" id="myModalLabel">文件和模型上传</h4>
</div>
<form class="form-horizontal" name="uploadForm"
      action="${ctx}/datadriver/privatedata/uploadPrivate.ht?id=${id}"
      id="uploadForm"
      method="post" enctype="multipart/form-data">
    <table id="AddHandlingFee" class="table table-striped" cellpadding="0" cellspacing="0"
           border="0"
           type="main">
        <tr id="newUpload2">
            <th width="20%">上传工具:</th>
            <td><input type="file" name="file" value="上传"></td>
        </tr>
    </table>
</form>
<div class="modal-footer">
    <input class="btn btn-primary" id="upload_button" type="submit" value="开始上传"/>
</div>

</body>
</html>
