<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<%@ include file="/commons/cloud/meta.jsp"%>

<html>
<head>
<title>明细</title>
<%@include file="/commons/include/getById.jsp"%>
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
<script type="text/javascript">
$(function() {
	var editor = new baidu.editor.ui.Editor({
		minFrameHeight : 200
		
	});
	editor.render("txtHtml");
})
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
						<a class="link back" href="list_all_bcs.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			 
			 <tr>
			 	<c:if test="${businessDevchase.type==1}">
				<th width="20%">采购物品分类:</th>
				
				<td>${businessDevchase.classid}</td>
				</c:if>
				<c:if test="${businessDevchase.type==2}">
				<th width="20%">营销物品分类:</th>
				<td>${businessDevchase.classid}</td>
				</c:if>
				<c:if test="${businessDevchase.type==3}">
							<th width="20%">生产物品分类:</th>
				<td>${businessDevchase.classid}</td>
				</c:if>
				<c:if test="${businessDevchase.type==4}">
			
				
				<th width="20%">服务行业分类:</th>
				<td>${businessDevchase.industry}--${businessDevchase.industry2}</td>
				</c:if>
				<c:if test="${businessDevchase.type==5}">
				<th width="20%">专业学科分类:</th>
				<td>${businessDevchase.classid}</td>
				</c:if>
			</tr>
			<tr>
				<th width="20%">商机名称:</th>
				<td>${businessDevchase.name}</td>
			</tr>
 				<tr>
				<th width="20%">开始时间:</th>
				<td>
				<fmt:formatDate value="${businessDevchase.startTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">结束时间:</th>
				<td>
				<fmt:formatDate value="${businessDevchase.endTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		
				<c:if test="${businessDevchase.type==1}">
				
				<tr>
					<th width="20%">采购数量: </th>
				<td>${businessDevchase.purnum}</td>
			</tr>
			<tr>
					<th width="20%">交货期要求: </th>
				<td>${businessDevchase.jhqyq}</td>
			</tr>
			<tr>
				<th width="20%">供应商资质要求: </th>
				<td>${businessDevchase.gyszzyq}</td>
			</tr>
				 
				</c:if>
				<c:if test="${businessDevchase.type==2}">
				 <tr>
				<th width="20%">代理区域:</th>
				<td>${businessDevchase.dlqy}--${businessDevchase.dlqy2}</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">代理时间要求:</th>
				<td>${businessDevchase.dlsjyq}</td>
			</tr>
 
			<tr>
				<th width="20%">营销伙伴资质要求:</th>
				<td>${businessDevchase.yxhbzzyq}</td>
			</tr>
				</c:if>
				<c:if test="${businessDevchase.type==3}">
				 <tr>
				<th width="20%">生产工艺要求:</th>
				<td>${businessDevchase.scgyyq}</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">生产规模:</th>
				<td>${businessDevchase.scgm}</td>
			</tr>
 
			<tr>
				<th width="20%">关键设备要求:</th>
				<td>${businessDevchase.gjsbyq}</td>
			</tr>
 
			<tr>
				<th width="20%">产品质量要求:</th>
				<td>${businessDevchase.cpzlyq}</td>
			</tr>
 
			<tr>
				<th width="20%">合作加工企业资质要求:</th>
				<td>${businessDevchase.hzjgqyzzyq}</td>
			</tr>
				</c:if>
				<c:if test="${businessDevchase.type==4}">
				
			
			
			
			 <tr>
					<th width="20%">服务区域: </th>
				 
				<td>${businessDevchase.fwqy}--${businessDevchase.fwqy2}</td>
			</tr>
			<tr>
					<th width="20%">代理时间: </th>
					
				<td><fmt:formatDate value="${businessDevchase.dlsj}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th width="20%">合作服务企业资质要求: </th>
				<td>${businessDevchase.hzfwqyzzyq}</td>
			</tr>
				</c:if>
				<c:if test="${businessDevchase.type==5}">
				<tr>
				 <th width="20%">研发伙伴资质要求:</th>
				<td>${businessDevchase.yfhbzzyq}</td>
				</tr>
				</c:if>
				
			
 
			 
 
			<tr>
				<th width="20%">图片:</th>
				<td> 
				<c:if test="${businessDevchase.type==1}">
				 <img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img01.jpg'" width="80" height="84" />
				</c:if>
				<c:if test="${businessDevchase.type==2}">
				 <img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img02.jpg'" width="80" height="84" />
				</c:if>
				<c:if test="${businessDevchase.type==3}">
				 <img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img03.jpg'" width="80" height="84" />
				</c:if>
				<c:if test="${businessDevchase.type==4}">
				 <img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img04.jpg'" width="80" height="84" />
				</c:if>
				<c:if test="${businessDevchase.type==5}">
				 <img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img05.jpg'" width="80" height="84" />
				</c:if>
				
				</td>
			</tr>
 
		 
           
			<tr>
				<th width="20%">发布企业:</th>
				<td>${businessDevchase.companyName}</td>
			</tr>
 
			<tr>
				<th width="20%">发布人:</th>
				<td>${businessDevchase.userName}</td>
			</tr>
           <tr>
				<th width="20%">审核状态:</th>
				<td>${businessDevchase.publishState}</td>
			</tr>
			
			<tr>
				<th width="20%">审核原因:</th>
				<td>${businessDevchase.publishInfo}</td>
			</tr>
		 
			 
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${businessDevchase.operateTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th width="20%">商机内容:</th>
				<td>
				
			 
				<div id="editor" position="center" style="overflow: auto; height: 100%;">
						  	<textarea id="txtHtml" name="html">${fn:escapeXml(businessDevchase.content)}</textarea>
					    </div>
				
				</td>
			</tr>
			 
			 
			 
			 
			 
 
			
		</table>
		</div>
		
	</div>
		</div>
 
</body>
</html>

