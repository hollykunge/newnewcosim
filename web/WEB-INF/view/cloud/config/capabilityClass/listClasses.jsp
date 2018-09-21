<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%String color=""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/commons/cloud/meta.jsp"%>
<title>在线能力列表</title>
</head>
<body>
<div id="all">
	<%@include file="/commons/cloud/top.jsp"%>
	
	<c:forEach items="${classes1}" var="c1">
	<div class="bggraybox" id="production">
		<h2>${c1.name}</h2>
		<div class="tag_list">
			<c:forEach items="${classes2}" var="c2">
        		<c:if test="${c1.id==c2.parentId }">
	        		<div class="tag">
	        		<strong><a href="javascript:void(0)">${c2.name}</a></strong>
	        		<c:forEach items="${classes3}" var="c3">
	        		<c:if test="${c2.id==c3.parentId}">
	        			<a href="${ctx}/cloud/config/capability/capabilityList.ht?type_id=${c3.id}">
	        				<c:if test="${c3.isused==null }">
	        			 	<%color=""; %>
	        			 	</c:if>
	        			 	<c:if test="${c3.isused=='yes' }">
	        			 	<%color="red"; %>
	        			 	</c:if>
	        			   	<font color="<%=color%>"> ${c3.name}</font>
	        			</a>
        			</c:if>
        			</c:forEach>
        			</div>
        		</c:if>
        	</c:forEach>
			<div class="clear"></div>
		</div>
	</div>
	</c:forEach>
</div>
	
<%@include file="/commons/cloud/foot.jsp"%>      
</body>
</html>
