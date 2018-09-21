<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<script>
	function ok(path) {
		window.parent._callbackImageUploadSucess(path, '${_callback}');
	}
</script>
</head>
<body>
	<table align="center">
		<tr>
			<td>${resultMsg}!</td>
		</tr>
		<tr>
			<td>
				<img src="${ctx}${filePath}"/>
			</td>			
		</tr>
		<tr>
			<td>
				<input type="button" onclick="ok('${filePath}');" value="确定" />
				<input type="button" onclick="history.back()" value="返回" />
			</td>
		</tr>
	</table>
</body>
</html>
