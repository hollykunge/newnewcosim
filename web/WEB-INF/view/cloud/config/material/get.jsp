
<%--
	time:2013-04-16 15:38:14
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>物品明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">物品名称:</th>
				<td>${material.name}</td>
			</tr>
 
			<tr>
				<th width="20%">物品分类:</th>
				<td>${material.catalogName}</td>
			</tr>
			<tr>
				<th width="20%">物品父级:</th>
				<td>${material.parentName}</td>
			</tr>
 
			<tr>
				<th width="20%">所属企业:</th>
				<td>${material.entId}</td>
			</tr>
 			
 			<tr>
				<th width="20%">物品规格:</th>
				<td>${material.model}</td>
			</tr>
 			
 			<tr>
				<th width="20%">物品属性:</th>
				<td>
					<c:forEach items="${mpvs}" var="v" >
					${v.propertyName }:  ${v.propertyValue }
					</c:forEach>
				</td>
			</tr>
 			
			<tr>
				<th width="20%">物品详情:</th>
				<td>${material.info}</td>
			</tr>
 
 			<tr>
				<th width="20%">物品图片:</th>
				<td><img src="${ctx}${material.pic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="80" height="84" />  </td>
			</tr>
			
			<tr>
				<th width="20%">发布人:</th>
				<td>${material.publisher}</td>
			</tr>
 
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${material.publishdate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">单位:</th>
				<td>${material.unit}</td>
			</tr>
 
			<tr>
				<th width="20%">价格:(${material.unit}/元)</th>
				<td>${material.price}</td>
			</tr>
 
 <!-- 
			<tr>
				<th width="20%">catalog_id:</th>
				<td>${material.catalogId}</td>
			</tr>
  -->
			<tr>
				<th width="20%">物品编码:</th>
				<td>${material.code}</td>
			</tr>
 
			<tr>
				<th width="20%">行业参考标准:</th>
				<td>${material.industryCode}</td>
			</tr>
 <!--
			<tr>
				<th width="20%">行业参考附件:</th>
				<td>
					<c:choose>
						<c:when test="${sysFile.delFlag eq 1}"><font color="red">对不起，该文件已经被删除</font></c:when>
						<c:otherwise>
						 <a href="download.ht?fileId=${sysFile.fileId }" target="_blank" class="link download">下载</a>
						<a href="${ctx}${material.industryFile}">下载</a>
						</c:otherwise>
					</c:choose>
				
				 ${material.industryFile} </td>
			</tr>
 -->
			<tr>
				<th width="20%">发布状态:</th>
				<td>${material.publishState}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

