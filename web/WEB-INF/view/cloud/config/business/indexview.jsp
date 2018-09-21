<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商机详细信息</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/getById.jsp"%>
<%@include file="/commons/include/get.jsp" %>
 
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/commons/cloud/top.jsp"%>
<!-- 页面主体  开始 -->
<div id="chanceList">
<c:forEach items="${businessChance}" var="businessChance">
	<div class="pro_view_01"><img src="${ctx}${businessChance.image}" onError="this.src='${ctx}/images/default-chance.jpg';" width="220" height="220" /></div>
    <div class="pro_view_02">商机名称：${businessChance.name }</div>
    <div class="pro_view_03">企业名称：<a href="javascript:void(0)">${businessChance.companyName}</a></div>
    <div class="pro_view_03">开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/> 截止时间：<fmt:formatDate value="${businessChance.endTime }" pattern="yyyy-MM-dd"/></div>
    <div class="pro_view_03">商机类型：${businessChance.type}</div>
    <div class="pro_view_03">
    
    
    	<c:if test="${businessChance.type=='采购商机'}">
		            	 采购数量： 
		            	</c:if>
		               <c:if test="${businessChance.type=='营销商机'}">
		            	 代理区域：  
		            	</c:if>
		            	<c:if test="${businessChance.type=='生产商机'}">
		            	 生产要求： 
		            	</c:if>
		            	<c:if test="${businessChance.type=='服务商机'}">
		            	 服务区域：  
		            	</c:if>
		            	<c:if test="${businessChance.type=='研发商机'}">
		           		 研发要求： 
		            	</c:if>
		            ${businessChance.properties}
    
    
    
    </div>
    <div class="pro_view_03">${businessChance.info.country}：${businessChance.info.province}${businessChance.info.city}</div>
    <div class="pro_view_05">商机内容</div>
    <div class="pro_view_06">${businessChance.content}</div>
</c:forEach>
</div>
<!-- 页面主体  结束 -->
<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
