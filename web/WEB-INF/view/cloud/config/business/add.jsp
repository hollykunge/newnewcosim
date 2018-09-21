<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ include file="/commons/cloud/global.jsp"%>
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet" type="text/css" />
<html>
<head>
<title>商机发布</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/design-setting/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/design-setting/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />

<script type="text/javascript">
	$(function() {
		var editor = new baidu.editor.ui.Editor({
			minFrameHeight : 200
		});
		editor.render("txtHtml");

		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#businessChanceForm').form();
		$("a.save").click(function() {
			frm.setData();
			$('#content').val(editor.getContent());
			 
			frm.ajaxForm(options);
			if (frm.valid()) {
				form.submit();
			}
		});
	});

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerMessageBox .confirm(
							"提示信息",
							obj.getMessage() + ",是否继续操作",
							function(rtn) {
								if (rtn) {
									this.close();
								} else {
									 
							    	window.location.href = "${ctx}/cloud/console/home.ht";
									  
								}
							});
		} else {
			$.ligerMessageBox.error("提示信息", obj.getMessage());
		}
	}

 

	
	/**
	 * 上传图片
	 */
	function selectIcon(){
		var url="${ctx}/cloud/pub/toUpload.ht";
	 	var winArgs = "dialogWidth=500px;dialogHeight=150px;help=0;status=0;scroll=1;center=0;resizable=1;";
	 	var rtn=window.open(url,"",winArgs);
	}

 
	
		function _callbackImageUploadSucess(src){
			$("#image").val(src);
				 var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
				$("#picView").append(item);
			 
		 
	}
 
	function isture() {

		var id = $("#id").val();
		if (id != null && id != "") {
			var src = $("#image").val();
			var item = $('<img src="'+src+'" width="80" height="84" />');
			$("#picView").append(item);

		}
	}
</script>
</head>
<body onload="isture();">
	<%@include file="/commons/cloud/top.jsp"%>
	<div id="main">
		<div id="main_left">
			<div id="sq_left">
				<div class="sq_left_img01">
					<div class="sq_btn01">
						<a href="${ctx}/cloud/console/outline.ht" class="linkblue2">待办任务</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img02">
					<div class="sq_btn01">
						<a href="${ctx}/console/outline.ht" class="linkblue2">已办未结</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img03">
					<div class="sq_btn01">
						<a href="${ctx}/console/outline.ht" class="linkblue2">已办完结</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
				<div class="clear_10"></div>
				<div class="sq_left_img06">
					<div class="sq_btn01">
						<a href="${ctx}/cloud/config/business/add.ht" class="linkblue2">商机发布</a>
					</div>
					<div class="sq_btn02">
						<a href="javascript:void(0)" class="linkblue"></a>
					</div>
				</div>
			</div>
			<div id="main_all" >
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">添加商机</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group">
								<a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a>
							</div>
							<div class="l-bar-separator"></div>
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
					<form id="businessChanceForm" method="post" action="save.ht">
						<table class="table-detail"   cellpadding="0" cellspacing="0"
							border="0" type="main">
							<tr>
								<th width="20%">商机名称: <span class="required">*</span></th>
								<td><input type="text" id="name" name="name"
									value="${businessChance.name}" class="inputText"
									validate="{required:true,maxlength:384}" /></td>
									
									
									<c:if test="${businessChance.type=='采购商机'}">
									<th width="20%">采购数量:</th>
								</c:if>
								<c:if test="${businessChance.type=='营销商机'}">
									<th width="20%">代理区域:</th>
								</c:if>
								<c:if test="${businessChance.type=='生产商机'}">
									<th width="20%">生产要求:</th>
								</c:if>
								<c:if test="${businessChance.type=='服务商机'}">
									<th width="20%">服务区域:</th>
								</c:if>
								<c:if test="${businessChance.type=='研发商机'}">
									<th width="20%">研发要求:</th>
								</c:if>

								<td><input type="text" id="properties" name="properties" class="inputText" validate="{required:true,maxlength:384}" /></td>
							</tr>
							<tr>
								<th width="20%">商机内容: <span class="required">*</span></th>
								<td colspan="3"><div id="editor" position="center"
										style="overflow: auto; height: 100%;">
										<textarea id="txtHtml" name="html">${fn:escapeXml(businessChance.content)}</textarea>
									</div></td>
							</tr>
							<br>
							<tr>
								<th width="20%">开始时间: <span class="required">*</span></th>
								<td ><input  type="text" id="startTime" name="startTime"	class="inputText date" validate="{date:true,required:true}" /></td>
								<th width="20%">结束时间: <span class="required">*</span></th>
								<td><input type="text" id="endTime" name="endTime"	class="inputText date" validate="{date:true,required:true}" /></td>
							</tr>
							<tr>
								
							</tr>
							<tr>
								<th width="20%">图片:</th>
								<td colspan="3">

									<input id="image" name="image" type="hidden"
									value="${businessChance.image}"> <a
									href="javascript:selectIcon();" class="btn-ctrl"
									id="addProductPic" js_tukubtn="true">添加图片</a>
									<div class="addproduct-pic" id="picView"></div>
								</td>
							</tr>
							 
							<tr>
								<!-- <th width="20%">审核状态:  <span class="required">*</span></th> -->
								<td><input type="hidden" id="publishState"
									name="publishState" value="未审核" /></td>
							</tr>
						</table>
						<input type="hidden" id="operateTime" name="operateTime"
							value="<fmt:formatDate value='${businessChance.operateTime}' pattern='yyyy-MM-dd'/>"class="inputText date" /> 
						 <input type="hidden" id="id"	name="id" value="${businessChance.id}" />
						  <input type="hidden" id="type"	name="type" value="${businessChance.type}" />
						 <input type="hidden" id="userId" name="userId" value="${businessChance.userId}"class="inputText" />
						 <input type="hidden" id="companyId" name="companyId" value="${businessChance.companyId}"class="inputText" />
						 <input id="content" name="content" type="hidden"></input>
					</form>
				</div>
			</div>
		</div>
			<%@ include file="/commons/cloud/foot.jsp"%>
</body>
</html>
