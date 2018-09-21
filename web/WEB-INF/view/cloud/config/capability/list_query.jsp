<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>能力列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp" %>
 
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />
</head>

<body>
<!-- 顶部浮动层  开始 -->
<%@include file="/commons/cloud/top.jsp"%>
 
<!-- 主导航  结束 -->
<!-- 页面主体  开始 -->
<div id="main">
<c:forEach items="${capabilityList}" var="c1">
<div id="tab01">
	<div class="tab01_1">
		<div class="tab01_2">
			<div class="tab01_3"><img src="${ctx}${c1.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" /></div>
			<div class="tab01_4"><a href="capabilityDetail.ht?id=${c1.id }">${c1.name }</a></div>
			<div class="tab01_5">
			<span class="grey">${c1.info}</span>
			</div>
		</div>
		
    </div>
	<div class="tab01_1">
		<div class="tab02_2">
			
			<div class="tab02_4"><a href="${ctx}/console/enterprise.action?EntId=${c1.entId}">${c1.entName}</a> </div>
			<div class="tab02_5"><span class="grey2"> ${c1.entName}</span></div>
			<div class="tab02_6">
			  <div class="tab02-07">
			  	<span class="tab02_7_span">主营：</span>
			  </div>
			  <div class="tab02-08">
			  	<span class="tab02_8_span">${c1.entName }</span>
				 
			  </div>
			</div>
		</div>
		
    </div>
</div>

</c:forEach>
 
<div class="pub_tab">
	<div class="fenye">
	
		  <hotent:paging tableId="capabilityItem"/>
	 </div>
</div>
<!-- 页面主体  结束 -->

<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
