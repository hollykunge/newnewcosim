<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/commons/cloud/meta.jsp"%>
<title>在线商机列表</title>
</head>

<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
		
	<div class="bggraybox" id="production">
		<h2>生产能力</h2>
		<c:forEach items="${businessChanceList}" var="businessChance" varStatus="s">
                    <div class="div01_1">
                    	<div class="div01_2">
                       	    <div class="div01_3"><img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/></div>
                            <div class="div01_4"><span class="grey2"><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');" class="link0423" name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link01">${businessChance.companyName}</a></span></div>
                            <div class="div01_5"><span class="grey3">开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>  <br>
                            	 <c:if test="${businessChance.type=='1'}">
                            	  采购数量：${businessChance.purnum} 
                            	
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='2'}">
                            	  代理区域：${businessChance.dlqy} 
                            	
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='3'}">
                            	 
                            	  生产要求：${businessChance.scgyyq}  </br>
                            	  生产规模：${businessChance.scgm}
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='4'}">
                            	 服务区域：${businessChance.fwqy} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='5'}">
                            	 
                            	 研发要求：${businessChance.yfhbzzyq} 
                            	 </c:if>
                            	</br>商机类型：
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
                            	 </c:if>
                            	 </br>${businessChance.country}：${businessChance.province}${businessChance.city}</span></div>
                        </div>
                    </div>
				</c:forEach>
	</div>
</div>
	
	<%@include file="/commons/cloud/foot.jsp"%>      
</body>
</html>
