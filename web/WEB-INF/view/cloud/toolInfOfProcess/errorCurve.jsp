<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>误差曲线</title>
<%@include file="/commons/include/get.jsp"%>
<script src="${ctx}/js/viewtree/jquery.js" type="text/javascript">
</script>
<script type="text/javascript">
$(document).ready(function(){
	var outputFileId=${outputFileId};
	setInterval(updateImage,500);

	function updateImage(){
		
		$("#curveImage").attr("src","${ctx }/cloud/toolInfOfProcess/curveImage.ht?outputFileId=${outputFileId}&runId=${runId}&random="+Math.random()*100000);
		
	}
	
	
});

</script>
</head>
<body>
<center>
<img src="${ctx }/cloud/toolInfOfProcess/curveImage.ht?outputFileId=${outputFileId}&runId=${runId}" id="curveImage"/>
</center>
</body>
</html>

