<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/commons/cloud/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<%@ include file="/commons/cloud/meta.jsp"%>
	<title>操作提示及返回</title>		
	<script>
		function isImage(str){
			if(str.lastIndexOf("\\")!=-1){
				str = str.substring(str.lastIndexOf("\\")+1,str.length);
			}
			if(str.lastIndexOf(".")!=-1){
				str = str.substring(str.lastIndexOf(".")+1,str.length);
			}
			str = str.toUpperCase();
			/* var img = "JPG,JPGE,GIF,PNG,BMP";
			if(img.indexOf(str)!=-1){
				return true;
			} */
			return false;
		}
		
		$(function(){
			$('#ff').submit(function(){
				if($('#appleFile').val()==''){
					alert("请选择要上传的文件 ！");
					return false;
				}
				
				/* //判断后缀					
				if(!isImage($('#appleFile').val())){
					alert('文件类型不对,必须是图片格式');
					return false;
				} */
			});		
		})
	</script>
</head>
<body>	
<form id='ff' name="ff" action="personalRegUpload.ht?_callback=${param._callback}" method="post" enctype="multipart/form-data">
	<table class="dataTable">
		<tr>
			<td>上传文件:</td>
			<td><input id="appleFile" name="file" type="file"/></td>
			<td colspan="2"><input type="submit" value="上传文件 "/></td>
		</tr>		
	</table>
</form>
</body>
</html>