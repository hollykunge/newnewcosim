<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>模型在线浏览</title>
</head>
<body>
<script type="text/javascript">
var type ="${type}";
if(type=="doc"||type=="xls" ){
	window.location.href="${ctx}/cloud/research/office.ht?fileId=${id}";
//	window.location.href="AutoVueMeWord://";
}
 else if(type=="stl"){
	 window.location.href="${ctx}/cloud/research/openModel.ht?fileId=${id}";
//	window.location.href="AutoVueMe1://";
} 
else if(type=="catproduct"){
	window.location.href="AutoVueMe2://";
}
else{
	window.location.href="${ctx}/cloud/research/openModel.ht?fileId=${id}";
}
</script>
   
</body>
</html>

