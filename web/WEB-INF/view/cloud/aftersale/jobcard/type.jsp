<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
		<c:forEach items="${typelist}" var="type" >
		   <c:if test="${type=='设计'}">
		      <c:set value="checked" var="checked"></c:set>
		      <c:set value="设计" var="sj" />
		   </c:if>
		   <c:if test="${type=='工艺' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="工艺" var="gy"></c:set>	
		   </c:if>
		   <c:if test="${type=='生产' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="生产" var="sc"></c:set>	
		   </c:if>
		   <c:if test="${type=='器材' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="器材" var="qc"></c:set>	
		   </c:if>
		   <c:if test="${type=='软件' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="软件" var="rj"></c:set>	
		   </c:if>
		   <c:if test="${type=='管理' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="管理" var="gl"></c:set>	
		   </c:if>
		   <c:if test="${type=='操作' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="操作" var="cz"></c:set>	
		   </c:if>
		   <c:if test="${type=='测试设备' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="测试" var="cs"></c:set>	
		   </c:if>
		   <c:if test="${type=='外购件' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="外购" var="wg"></c:set>	
		   </c:if>
		   <c:if test="${type=='其他' }">
		   	  <c:set value="checked" var="checked"></c:set>
		   	  <c:set value="其他" var="qt"></c:set>	
		   </c:if>
		</c:forEach>
		<input type="checkbox" id="type" <c:if test="${sj=='设计' }">${checked }</c:if> name="type" value="设计">设计
		<input type="checkbox" id="type" <c:if test="${gy=='工艺' }">${checked }</c:if> name="type" value="工艺">工艺
		<input type="checkbox" id="type" <c:if test="${sc=='生产' }">${checked }</c:if> name="type" value="生产">生产
		<input type="checkbox" id="type" <c:if test="${qc=='器材' }">${checked }</c:if> name="type" value="器材">器材
		<input type="checkbox" id="type" <c:if test="${rj=='软件' }">${checked }</c:if> name="type" value="软件">软件
		<input type="checkbox" id="type" <c:if test="${gl=='管理' }">${checked }</c:if> name="type" value="管理">管理
		<input type="checkbox" id="type" <c:if test="${cz=='操作' }">${checked }</c:if> name="type" value="操作">操作
		<input type="checkbox" id="type" <c:if test="${cs=='测试' }">${checked }</c:if> name="type" value="测试设备">测试设备
		<input type="checkbox" id="type" <c:if test="${wg=='外购' }">${checked }</c:if> name="type" value="外购件">外购(协)件
		<input type="checkbox" id="type" <c:if test="${qt=='其他' }">${checked }</c:if> name="type" value="其他">其他
</html>