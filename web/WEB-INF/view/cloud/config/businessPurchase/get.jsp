
<%--
	time:2013-04-16 17:21:17
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_business_chance明细</title>
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
				 
			      <a class="link back" href="list.ht">返回</a>
			 
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 	<tr>
				<th width="20%">采购物品分类:</th>
				<td>${businessPurchase.classid}</td>
			</tr>
			<tr>
				<th width="20%">商机名称:</th>
				<td>${businessPurchase.name}</td>
			</tr>
 				<tr>
				<th width="20%">开始时间:</th>
				<td>
				<fmt:formatDate value="${businessPurchase.startTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">结束时间:</th>
				<td>
				<fmt:formatDate value="${businessPurchase.endTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
					<th width="20%">采购数量: </th>
				<td>${businessPurchase.purnum}</td>
			</tr>
			<tr>
					<th width="20%">交货期要求: </th>
				<td>${businessPurchase.jhqyq}</td>
			</tr>
			<tr>
				<th width="20%">供应商资质要求: </th>
				<td>${businessPurchase.gyszzyq}</td>
			</tr>
 
			 
 
			<tr>
				<th width="20%">图片:</th>
				<td> 
				
				<img src="${ctx}${businessPurchase.image}" onError="this.src='${ctx}/images/product_img01.jpg'"  width="80" height="84" />
				</td>
			</tr>
 
		 
           
			<tr>
				<th width="20%">发布企业:</th>
				<td>${businessPurchase.companyName}</td>
			</tr>
 
			<tr>
				<th width="20%">发布人:</th>
				<td>${businessPurchase.userName}</td>
			</tr>
           <tr>
				<th width="20%">审核状态:</th>
				<td>${businessPurchase.publishState}</td>
			</tr>
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${businessPurchase.operateTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th width="20%">商机内容:</th>
				<td>
				
			 
				<div id="editor" position="center" style="overflow: auto; height: 100%;">
						  	<textarea id="txtHtml" name="html">${fn:escapeXml(businessPurchase.content)}</textarea>
					    </div>
				
				</td>
			</tr>
 
			
			
		</table>
		<input id="content" name="content" type="hidden"></input>
		</div>
		
	</div>
</body>
</html>

