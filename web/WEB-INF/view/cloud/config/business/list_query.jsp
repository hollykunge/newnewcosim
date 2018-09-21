<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商机列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp" %>
 
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />
 
 

<script type="text/javascript">
	 
		function view(id){
			location.href = "${ctx }/cloud/config/business/indexview.ht?id="+id;
		return false;
	}	
</script>


</head>

<body>
	<%@include file="/commons/cloud/top.jsp"%>
	<div id="chanceList01">
		<div id="chanceTitle01">
			<span id="title01">商机搜索结果：${name}</span>
		</div>
		<div id="model01">
			
				<c:forEach items="${businessChanceList}" var="businessChance" varStatus="s">
                    <div class="div01_1">
                    	<div class="div01_2">
                       	    <div class="div01_3"><img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/></div>
                            <div class="div01_4">${businessChance.name}<span class="grey2">${businessChance.info.name}</span></div>
                            <div class="div01_5"><span class="grey3">开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>  <br>
                            	<c:if test="${businessChance.type=='生产商机'}">
                            	 生产要求：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='采购商机'}">
                            	 采购数量：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='营销商机'}">
                            	 代理区域：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='服务商机'}">
                            	 服务区域：${businessChance.properties} 
                            	 </c:if>
                            	 <c:if test="${businessChance.type=='研发商机'}">
                            	 研发要求：${businessChance.properties} 
                            	 </c:if>
                            	 </br>商机类型：${businessChance.type} </br>${businessChance.info.country}：${businessChance.info.province}${purchaseList.info.city}</span></div>
	                     
	                         <div class="tab01_6"><a href="javascript:void(0)" onclick="view('${businessChance.id}');" class="link01" name="${businessChance.id}">查看</a> </div>
                        </div>
                        
                    </div>
					
				</c:forEach>
		</div>
		<div class="pub_tab">
			<div class="fenye">
				 <hotent:paging tableId="businessChanceItem"/>
			</div>
		</div>
	</div>
	
	
	
	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
