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
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>
<!-- 页面主体  开始 -->
<div id="chanceList">
<c:forEach items="${businessChance}" var="businessChance">
	<div class="pro_view_01"><img src="${ctx}${businessChance.image}" onError="this.src='${ctx}/images/default-chance.jpg';" width="220" height="220" /></div>
    <div class="pro_view_02">商机名称：${businessChance.classid }-${businessChance.name }</div>
    <div class="pro_view_03">企业名称：<a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}">${businessChance.companyName}</a>&nbsp;&nbsp;&nbsp;${businessChance.country}：${businessChance.province}${businessChance.city}</div>
    <div class="pro_view_03">开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/> 截止时间：<fmt:formatDate value="${businessChance.endTime }" pattern="yyyy-MM-dd"/></div>
    <div class="pro_view_03">商机类型：
         <c:if test="${businessChance.type=='1'}">
                            	  采购商机
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='2'}">
                            	营销商机
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='3'}">
                            	生产商机
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='4'}">
                            	服务商机
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='5'}">
                            	研发商机
                            	 </c:if></div>
    							 
    
    
    	 						<c:if test="${businessChance.type=='1'}">
                            	 <div class="pro_view_03"> 采购数量：${businessChance.purnum}  </div>
                            	<div class="pro_view_03">交货期要求：${businessChance.jhqyq}  </div>
                            	<div class="pro_view_03">供应商资质要求：${businessChance.gyszzyq}  </div>
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='2'}">
                            	 <div class="pro_view_03">  代理区域：${businessChance.dlqy}  </div>
                            	 <div class="pro_view_03">代理时间要求：${businessChance.dlsjyq} </div>
                            	 <div class="pro_view_03">营销伙伴资质要求：${businessChance.yxhbzzyq} </div>
                            	
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='3'}">
                            	 
                            	 <div class="pro_view_03"> 生产工艺要求：${businessChance.scgyyq}  </div>
                            	 <div class="pro_view_03"> 生产规模：${businessChance.scgm}  </div>
                            	 <div class="pro_view_03"> 关键设备要求：${businessChance.gjsbyq}  </div>
                            	 <div class="pro_view_03"> 产品质量要求：${businessChance.cpzlyq}  </div>
                            	 <div class="pro_view_03"> 合作加工企业资质要求：${businessChance.hzjgqyzzyq}  </div>
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='4'}">
                            	 <div class="pro_view_03"> 服务区域：${businessChance.fwqy}  </div>
                            	 <div class="pro_view_03"> 代理时间：<fmt:formatDate value="${businessChance.dlsj}" pattern="yyyy-MM-dd"/>  </div>
                            	 <div class="pro_view_03"> 合作服务企业资质要求：${businessChance.hzfwqyzzyq}  </div>
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='5'}">
                            	 
                            	 <div class="pro_view_03"> 研发伙伴资质要求：${businessChance.yfhbzzyq}  </div>
                            	 </c:if>
    
    
    
    
    <div class="pro_view_05">商机内容</div>
    <div class="pro_view_06">${businessChance.content}</div>
</c:forEach>
</div>
<!-- 页面主体  结束 -->
</div>

<!-- 底部版权区  开始 -->
<%@ include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
