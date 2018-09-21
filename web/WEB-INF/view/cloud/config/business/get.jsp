
<%--
	time:2013-04-16 17:21:17
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_business_chance明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">商机详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
				<c:choose>
			    <c:when test="${businessChance.type=='采购商机'}">
			      <a class="link back" href="purchaseList.ht">返回</a>
			    </c:when>
			    
			     <c:when test="${businessChance.type=='营销商机'}">
			      <a class="link back" href="marketingList.ht">返回</a>
			    </c:when>
			    
			     <c:when test="${businessChance.type=='生产商机'}">
			      <a class="link back" href="produceList.ht">返回</a>
			    </c:when>
			    
			     <c:when test="${businessChance.type=='服务商机'}">
			      <a class="link back" href="serveList.ht">返回</a>
			    </c:when>
			    
			    <c:when test="${businessChance.type=='研发商机'}">
			      <a class="link back" href="developmentList.ht">返回</a>
			    </c:when>
			  		   
		    </c:choose>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">商机名称:</th>
				<td>${businessChance.name}</td>
			</tr>
 
			<tr>
				<th width="20%">商机内容:</th>
				<td>${businessChance.content}</td>
			</tr>
 
			<tr>
				<th width="20%">开始时间:</th>
				<td>
				<fmt:formatDate value="${businessChance.startTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">结束时间:</th>
				<td>
				<fmt:formatDate value="${businessChance.endTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">商机类型:</th>
				<td>${businessChance.type}</td>
			</tr>
 
			<tr>
				<th width="20%">图片:</th>
				<td> 
				
				<img src="${ctx}${businessChance.image}" onError="this.src='${ctx}${businessChance.image}';" width="80" height="84" />
				</td>
			</tr>
 
			<tr>
				<c:if test="${businessChance.type=='采购商机'}">
		            		<th width="20%">采购数量: </th>
		            	</c:if>
		               <c:if test="${businessChance.type=='营销商机'}">
		            		 <th width="20%">代理区域: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='生产商机'}">
		            		  <th width="20%">生产要求: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='服务商机'}">
		            		 <th width="20%">服务区域: </th>
		            	</c:if>
		            	<c:if test="${businessChance.type=='研发商机'}">
		            		<th width="20%">研发要求: </th> 
		            	</c:if>
				<td>${businessChance.properties} </td>
			</tr>
           
			<tr>
				<th width="20%">发布企业:</th>
				<td>${businessChance.companyName}</td>
			</tr>
 
			<tr>
				<th width="20%">发布人:</th>
				<td>${businessChance.userName}</td>
			</tr>
           <tr>
				<th width="20%">审核状态:</th>
				<td>${businessChance.publishState}</td>
			</tr>
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${businessChance.operateTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			
		</table>
		</div>
		
	</div>
</body>
</html>

