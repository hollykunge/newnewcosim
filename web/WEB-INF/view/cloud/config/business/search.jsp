<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<title>在线商机列表</title>
<script type="text/javascript">
function view(id,type){
	location.href = "${ctx }/cloud/config/businessDevchase/indexview.ht?id="+id+"&type="+type;
	return false;
}	
</script>
</head>

<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
		
	<div id="zxsj" class="bggraybox">
		<div class="title"><a href="javascript:void(0)">在线商机</a></div>
		<c:if test="${not empty businessChanceList1}">
		<ul>			
			<li>
			<c:forEach items="${businessChanceList1}" var="businessChance" varStatus="s">
				<div class="left">
					<img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/>
					<div class="left_desc">
						<h5><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');"  name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link_zxsj">${businessChance.companyName}</a></h5>
						开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
						<p>
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
						</p>
					</div>
				</div>
			</c:forEach>
			<p class="clear"></p>
			</li>
		</ul>
		<div class="bgbox_gray">
			<hotent:paging tableId="businessDevchaseItem1" showPageSize="false"	/>
		</div>
		</c:if>
		
		<c:if test="${not empty businessChanceList2}">
		<ul>			
			<li>
			<c:forEach items="${businessChanceList2}" var="businessChance" varStatus="s">
				<div class="left">
					<img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/>
					<div class="left_desc">
						<h5><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');"  name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link_zxsj">${businessChance.companyName}</a></h5>
						开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
						<p>
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
						</p>
					</div>
				</div>
			</c:forEach>
			<p class="clear"></p>
			</li>
		</ul>
		<div class="bgbox_gray">
			<hotent:paging tableId="businessDevchaseItem2" showPageSize="false"	/>
		</div>
		</c:if>
		
		<c:if test="${not empty businessChanceList3}">
		<ul>			
			<li>
			<c:forEach items="${businessChanceList3}" var="businessChance" varStatus="s">
				<div class="left">
					<img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/>
					<div class="left_desc">
						<h5><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');"  name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link_zxsj">${businessChance.companyName}</a></h5>
						开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
						<p>
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
						</p>
					</div>
				</div>
			</c:forEach>
			<p class="clear"></p>
			</li>
		</ul>
		<div class="bgbox_gray">
			<hotent:paging tableId="businessDevchaseItem3" showPageSize="false"	/>
		</div>
		</c:if>
		
		<c:if test="${not empty businessChanceList4}">
		<ul>			
			<li>
			<c:forEach items="${businessChanceList4}" var="businessChance" varStatus="s">
				<div class="left">
					<img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/>
					<div class="left_desc">
						<h5><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');"  name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link_zxsj">${businessChance.companyName}</a></h5>
						开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
						<p>
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
						</p>
					</div>
				</div>
			</c:forEach>
			<p class="clear"></p>
			</li>
		</ul>
		<div class="bgbox_gray">
			<hotent:paging tableId="businessDevchaseItem4" showPageSize="false"	/>
		</div>
		</c:if>
		
		<c:if test="${not empty businessChanceList5}">
		<ul>			
			<li>
			<c:forEach items="${businessChanceList5}" var="businessChance" varStatus="s">
				<div class="left">
					<img src="${ctx}${businessChance.image}" width="100" height="100" onError="this.src='${ctx}/images/default-chance.jpg';"/>
					<div class="left_desc">
						<h5><a href="javascript:void(0)" onclick="view('${businessChance.id}','${businessChance.type}');"  name="${businessChance.id}">${businessChance.classid}-${businessChance.name}</a></span> <span class="grey2"><a href="${ctx}/cloud/console/enterprise.ht?EntId=${businessChance.companyId}" class="link_zxsj">${businessChance.companyName}</a></h5>
						开始时间：<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>  截止时间：<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
						<p>
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
						</p>
					</div>
				</div>
			</c:forEach>
			<p class="clear"></p>
			</li>
		</ul>
		<div class="bgbox_gray">
			<hotent:paging tableId="businessDevchaseItem5" showPageSize="false"	/>
		</div>
		</c:if>
		<!--底部灰色背景-->
		<script type="text/javascript">
			$('#detail_list li:last').css('margin-bottom','2px');
		</script>
		
	</div>
</div>
<%@include file="/commons/cloud/foot.jsp"%>      
</body>
</html>
