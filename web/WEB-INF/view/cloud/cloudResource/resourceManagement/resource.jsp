<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%String color=""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<title>制造资源</title>
</head>
<body  >

<div id="all">
<%@include file="/commons/cloud/top.jsp"%>
<!-- 动态展示三级Resource.ht页面，
	当openUrl==null时，显示为正常颜色，当openUrl ！== null时，显示为红色
	当openType=0时，以超链接的形式打开；当openType=1时，打开一个新的页面 
-->
<div style="padding-left: 25px;padding-bottom :3px"><c:if test='${isLogin == "1"}'>提示: 如需使用完整功能，请<a href="${ctx}/resource/link.jsp">下载安装插件</a></c:if></div>
<c:forEach var="list1" items="${cloudResource1}">
<div class="bggraybox" id="production">
		<h2>${list1.name }</h2>
		<div class="tag_list">
			<c:forEach var="list2" items="${cloudResource}">
				<c:if test='${list1.id==list2.parentId}'>
					<div class="tag">
						<strong><a href="javascript:void(0)"> ${list2.name}</a></strong>
						
							<c:forEach var="list3" items="${cloudResource2}">
					
							
								<c:if test='${list2.id==list3.parentId}'>
									<c:if test='${list3.openType=="0"}'>
										<c:if test='${not empty list3.openUrl }'>
											<a id="link" style="color:red" href="${ctx}${list3.openUrl}${list3.id}">${list3.name }</a>									
										</c:if>
										<c:if test='${empty list3.openUrl}'>
											<a id="link"  href="javascript:void(0)">${list3.name }</a>
										</c:if>
									</c:if>
									
									<c:if test='${list3.openType=="1"}'>
										<c:if test='${not empty list3.openUrl}'>
											<a style="color:red" href="javascript:void(0)" onClick="window.open('${list3.openUrl }') ">${list3.name }</a>
										</c:if>
										<c:if test='${empty list3.openUrl }'>
											<a href="javascript:void(0)" >${list3.name }</a>
										</c:if>										 	
									</c:if> 
									
							
									<c:if test='${list3.openType=="2"}'>
										<c:if test='${not empty list3.openUrl }'>
											<a id="link" style="color:red" href="${ctx}${list3.openUrl}">${list3.name }</a>									
										</c:if>
										<c:if test='${empty list3.openUrl}'>
											<a id="link"  href="javascript:void(0)">${list3.name }</a>
										</c:if>
									</c:if>
									
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
<!-- 页面主体  结束 -->
<!-- 底部版权区  开始 -->
<%@include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
