<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>

<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>

<link href="${ctx}/styles/cloud/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/cloud/global.css" rel="stylesheet"
	type="text/css" />

<!-- ueditor -->
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/ueditor/themes/default/ueditor.css" />
<style type="text/css">
.link-remove {
	background: url(${ctx}/styles/default/images/button/remove.png) 0px 3px
		no-repeat !important;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	$(function() {
		var editor = new baidu.editor.ui.Editor({
			minFrameHeight : 200,
			initialContent : ''
		});
		editor.render("txtHtml");
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#capabilityForm').form();
		$("#save").click(function() {
			if (frm.valid()) {
				$('#info').val(editor.getContent());
				$('#publishState').val("发布");
				frm.setData();
				frm.ajaxForm(options);
				form.submit();
			}
		});

		$("#savecg").click(function() {
			if (frm.valid()) {
				$('#info').val(editor.getContent());
				$('#publishState').val("草稿");
				frm.setData();
				frm.ajaxForm(options);
				form.submit();
			}
		});
	});

	//上传图片	
	var dd = null;
	function selectIcon() {
		var urlShow = '${ctx}/cloud/pub/image/toUpload.ht';
		dd = $.ligerDialog.open({
			url : urlShow,
			height : 350,
			width : 500,
			title : '图片上传器',
			name : "frameDialog_",
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	};

	function _callbackImageUploadSucess(src) {
		dd.close();
		$("#pic").val(src);
		var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
		$("#picView").append(item);
	}

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerMessageBox
					.confirm(
							"提示信息",
							obj.getMessage() + ",是否继续操作?",
							function(rtn) {
								if (rtn) {
									this.close();
								} else {
									var state = $('#publishState').val();
									if (state == "发布") {

										window.location.href = "${ctx}/cloud/config/material/list.ht";
									} else {
										window.location.href = "${ctx}/cloud/config/material/list_cgs.ht";

									}

								}
							});
		} else {
			$.ligerMessageBox.error("提示信息", obj.getMessage());
		}
	}

	//添加附件
	function addFile() {
		FlexUploadDialog({
			isSingle : false,
			callback : fileCallback
		});
	};

	function fileCallback(fileIds, fileNames, filePaths) {
		var arrPath;
		if (filePaths == "")
			return;
		arrPath = filePaths.split(",");
		var arrfileId;
		if (fileIds == "")
			return;
		arrfileId = fileIds.split(",");
		var arrfileName;
		if (fileNames == "")
			return;
		arrfileName = fileNames.split(",");

		for ( var i = 0; i < arrfileName.length; i++) {
			var tr = getRow(arrfileId[i], arrPath[i], arrfileName[i]);
			$("#sysFileList").append(tr);
		}
		$("#fileIds").val(fileIds);
	};

	function delRow(fileId) {
		$("tr ." + fileId).remove();
	};

	function getRow(fileId, filePath, fileName) {
		var tr = '<tr class="'+fileId+'">';
		tr += '<td>';
		tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
		tr += '	<input type="hidden" class="pk" name="attachments" value="' + filePath + '"/> ';
		tr += '	<a href="'+__ctx+"/"+filePath+'">' + fileName + '</a> ';
		tr += '</td>';
		tr += '<td><a onclick=javascript:delRow('
				+ fileId
				+ '); ><span class="link-btn link-remove">&ensp; </span></a> </td>';
		tr += '</tr>';
		return tr;
	}

	function preview2() {
		CommonDialog("materialListOnly", function(data) {

			$("#parentId").val(data.id);
			$("#parentName").val(data.name);

		});
	}
</script>
</head>
<body>

	<form id="capabilityForm" method="post" action="save.ht">
		<table width="868" border="0" cellspacing="0" cellpadding="0"
			type="main">
			<tr>
				<td>
					<div js_flag="sale-info">
						<div class="title">
							<h3>发布物品</h3>
							<ul class="path">
								<li><span>1</span>选择类目</li>
								<li class="active"><span>2</span>填写物品详情</li>
								<li><span>3</span>提交成功，等待审核</li>
							</ul>
						</div>
						<!-- 
						<div class="filter">
							<div class="info-level">
								<span class="fl">物品信息评星：</span>
								<div class="star s-0">
									2星
								</div>
							</div>
						</div>
						 -->

						<div class="product-info-form">

							<div class="required-tip">
								<span class="fr"><em>*</em>为必填项</span>填写物品基本信息
							</div>





	


<!-- 							<div class="row"> -->
<!-- 								<div class="label"> -->
<!-- 									<em>*</em>物品父级： -->
<!-- 								</div> -->
<!-- 								<div class="cell"> -->
<!-- 									<input type="hidden" id="parentId" name="parentId" -->
<!-- 										class="inputText" /> <input type="text" id="parentName" -->
<!-- 										name="parentName" readonly="readonly" class="inputText" /> -->
<!-- 									<a href="javascript:void(0)" onclick="preview2()" class="link detail">选择</a> -->
<!-- 								</div> -->
<!-- 								<div id="pronameTip"></div> -->

<!-- 							</div> -->








							<div class="row">
								<div class="label">
									<em>*</em>物品标题：
								</div>
								<div class="cell">
									<input type="text" id="name" name="name" class="text"
										style="width: 380px;" validate="{required:true,maxlength:768}" />
								</div>
								<div id="pronameTip"></div>
								<div class="prductNameTip">物品标题越详细越容易被客户找到，最长40个字符。</div>
							</div>
							<div class="row">
								<div class="label">
									<em>*</em>物品编码：
								</div>
								<div class="cell">
									<input id="code" name="code" type="text" class="text"
										style="width: 180px;"
										validate="{required:false,maxlength:192}" />
								</div>
								<div id="pronameTip"></div>
								<div class="prductNameTip">物品所属编码</div>
							</div>
							<div class="row">
								<div class="label">物品规格：</div>
								<div class="cell">
									<input id="model" name="model" type="text" class="text"
										style="width: 180px;" />
								</div>
								<div id="pronameTip"></div>
								<div class="prductNameTip">对物品进行描述，例如长、宽、高等属性</div>
							</div>
							<div class="row">
								<div class="label">
									<em>*</em>行业参考标准：
								</div>
								<div class="cell">
									<input id="industryCode" name="industryCode" type="text"
										class="text" style="width: 100px;"
										validate="{required:false,maxlength:192}" />
								</div>
								<div id="prokeyTip"></div>
								<div class="prductNameTip">填写行业参考标准</div>
							</div>
							<div class="row">
								<div class="label">行业参考附件：</div>
								<div class="cell" js_picarea="true">
									<table width="145" id="sysFileList"
										class="table-grid table-list" id="0" cellpadding="1"
										cellspacing="1"></table>
									<a href="javascript:void(0)" onclick="addFile()">添加附件</a>
									<!-- <input type="file" name="attachments" id="attachments">  -->
								</div>
							</div>

							<div class="row" id="J_productProps">
								<div class="label">物品属性：</div>
								<div class="note">完整的物品属性有助于买家找到您的信息</div>

								<div id="attrArea" class="product-props">
									<c:forEach items="${properties}" var="p">
										<div class="row" d-attid="6811" d-inputtype="1" d-isneeded="N"
											d-parentid="0" d-parentvid="0">
											<div class="label">
												<span d-atttip="true">${p.propertyName}</span>：
											</div>
											<c:if test="${p.propertyType=='2'}">
												<div class="cell">
													<select id="property${p.id}" name="property${p.id}" style="width: 165px;">
														<c:forTokens items="${p.value}" delims="," var="t">
															<option value="${t}">
																${t}
																</o ption>
														</c:forTokens>
													</select>
												</div>
											</c:if>

											<c:if test="${p.propertyType=='1'}">
												<div class="cell">
													<input id="property${p.id}" name="property${p.id}"
														class="text" style="width: 160px;" maxlength="80" type="text">
												</div>
											</c:if>

										</div>
									</c:forEach>

								</div>
							</div>


							<div class="row">
								<div class="label">计量单位：</div>
								<ap:selectDB name="unit" id="unit"
									where="parentId=10000010570022" optionValue="itemValue"
									optionText="itemName" table="SYS_DIC"
									selectedValue="${material.unit}">
								</ap:selectDB>
							</div>
							<!-- 
								<div class="row">
									<div class="label">
										可售数量：
									</div>
									<div class="cell">
										<input id="maxsale" name="maxsale" type="text"
											class="text w80" value="" datatype="n">
									</div>
									<div class="cell mr5">
										<span js_relationid="unit" name="productUnit">件</span>
									</div>
									<div class="cell" id="sellcountTip"></div>
								</div>
								<div class="row">
									<div class="label">
										最小起订量：
									</div>
									<div class="cell">
										<input id="minsale" name="minsale" type="text"
											class="text w80" value="" datatype="n">
									</div>
									<div class="cell mr5">
										<span js_relationid="unit" name="productUnit">件</span>
									</div>
									<div class="cell" id="mincountTip"></div>
								</div>
								 -->
							<div class="row">
								<div class="label">价格：</div>
								<div class="cell">
									<input id="price" name="price" type="text" class="text w80"
										validate="{required:true,number:true }" />
									<p class="note">不填代表价格面议</p>
								</div>
								<div class="cell mr5">
									元/ <span js_relationid="unit" name="productUnit">件</span>
								</div>
								<div class="cell" id="priceTip"></div>
							</div>

							<div class="row">
								<div class="label">物品图片：</div>
								<div class="cell" js_picarea="true">
									<a href="javascript:selectIcon();" class="btn-ctrl"
										id="addProductPic" js_tukubtn="true">添加物品图片</a>
									<div class="addproduct-pic" id="picView"></div>
								</div>
							</div>
							<div class="row">
								<div class="label">物品详情：</div>
								<div class="cell">
									<div class="postbox" id="postbox">
										<textarea id="txtHtml" name="html"></textarea>
									</div>
								</div>
							</div>
							<div class="offer-scan">
								<!-- 
									<div class="row pb0">
										<div class="label">
											当前物品质量评星：
										</div>
										<div class="star s-0">
											star
										</div>
									</div>
									 -->
								<div class="row">
									<div class="offer-scan-con">
										<p>优质的信息更能得到买家的青睐，以下方面建议您发布前可以再检查一下：</p>
										<ul>
											<li>1、选择准确的类目；</li>
											<li>2、物品标题中包含物品关键词，并突出物品卖点；</li>
											<li>3、尽可能地完善物品属性及物品详情，必要时物品详情可图文并茂；注意突出物品的优势及特点；</li>
											<li>4、上传清晰的物品图片；</li>
										</ul>
									</div>
								</div>
							</div>

							<div class="row pt16">
								<!-- <input id="categoryid" name="categoryid" type="text" value="165"> -->

								<input type="hidden" name="typeClass" value="${typeClass}" /> <input
									type="hidden" name="id" value="${material.id}" /> <input
									id="info" name="info" type="hidden" value=""> <input
									id="publishState" name="publishState" type="hidden" value="">
								<input id="pic" name="pic" type="hidden" value=""> <input
									name="typeId" type="hidden" value="${materialCatalog.typeId}"> <input
									name="levlSeq" type="hidden" value="${levlseq}"> <input
									type="hidden" name="catalogId" value="${materialCatalog.id}">
								<input type="hidden" name="catalogName"
									value="${materialCatalog.name}"> <input type="button"
									class="submit" id="save" value="确认发布"> <span
									class="btn-next" style="width: 120px;">
									<button type="button" id="savecg">保存到草稿箱</button>
								</span>

							</div>
							</form>
						</div>
					</div>
					</div>
				</td>
			</tr>

		</table>
</body>
</html>