<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="/cloud/styles/default/css/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/cloud/js/dynamic.jsp"></script>
<script type="text/javascript" src="/cloud/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/cloud/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/cloud/js/util/util.js"></script>
<script type="text/javascript" src="/cloud/js/util/json2.js"></script>


<script type="text/javascript" src="/cloud/js/hotent/platform/form/rule.js"></script>

<script type="text/javascript" src="/cloud/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="/cloud/js/hotent/platform/form/AttachMent.js"></script>


<script>
function _callback(fileId,fileName,filePath) {
	alert("回调"+filePath);
	$('input[name="fileId"]').val(fileId);
}
</script>
</head>
<body>
<a onclick="AttachMent.addFile1(this);" field="s:upload:upload_file" href="javascript:;" class="link attachement">选择</a>
 <textarea name="s:upload:upload_file" validate="{'maxlength':2000}" controltype="attachment" class=" selectFile"></textarea>
 <input name="fileId" value="" />
</body>
</html>