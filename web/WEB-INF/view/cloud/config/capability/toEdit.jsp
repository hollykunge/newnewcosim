<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 
<%@ include file="/commons/include/html_doctype.html"%>
<html>
	<head>
 
	 <%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	
	<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
	
			 <!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css"/>

	<script type="text/javascript">
		$(function() {
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 200,
				initialContent : ''
			});
			editor.render("txtHtml");
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#capabilityForm').form();
			$("#save").click(function() {
			if(frm.valid()){
					$('#info').val(editor.getContent());
					 
					frm.setData();
					frm.ajaxForm(options);
					form.submit();
				}
			});
			
			$("#saveCg").click(function() {
				if(frm.valid()){
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
	function selectIcon(){
		var urlShow = '${ctx}/cloud/pub/image/toUpload.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_",
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
		});
	};
	
	function _callbackImageUploadSucess(src){
		dd.close();
		$("#pic").val(src);
		$(".addproduct-pic").empty();
		var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
		$("#picView").append(item);
	}
 
	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
				if(rtn){
					this.close();
				}else{
					window.location.href = "${ctx}/cloud/config/capability/listCg.ht";
				}
			});
		} else {
			$.ligerMessageBox.error("提示信息",obj.getMessage());
		}
	}
	/**
	 * 增加物品
	 */
	var rowArrs1;
	var rowArr1;
	function add_product(){
		CommonDialog("materialList",
		function(data) {
			var rows=data;
			rowArrs1 = new Array();

			for ( var i = 0; i < rows.length; i++) {
				rowArr1 = new Array();
				var row = rows[i];
				rowArr1["id"] = row.id;
				rowArr1["name"] = row.name;
				rowArr1["unit"] = row.unit;
				rowArr1["price"] = row.price;
				rowArrs1[i] = rowArr1;
			}
			for ( var i = 0; i < rowArrs1.length; i++) {
				var row = rowArrs1[i];
				var id = row["id"];
				var name = row["name"];
				var unit = row["unit"];
				var price = row["price"];
				var item = $('<tr><td><input type="checkbox" name="check" value="'
						+ id
						+ '"/></td> <input type="hidden"  name="materiel_ids" value="'
						+ id + '"/><td> ' + name + ' </td> <td> ' + unit
						+ ' </td> <td> ' + price + ' </td></tr>');
				$("#product_list").append(item);
			}
			 
			
			
		});
	}
	/**
	 * 删除物品
	 */
	function del_product(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
		 
		
		
	}
	
   /**
	*全选/反选
	*/
	
	 function checkall() {
		 
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					 $("[name=check]:checkbox").attr('checked', false);
				}else{
					 $("[name=check]:checkbox").attr('checked', true);
				}
			});
	
		 
	} 
 
</script>
	</head>
	<body>
		<form id="capabilityForm" method="post" action="save.ht">
			<table width="868" border="0" cellspacing="0" cellpadding="0" type="main">
				<tr>
					<td>
						<div js_flag="sale-info">
							<div class="title">
								<h3>
									发布能力
								</h3>
								<ul class="path">
									<li>
										<span>1</span>选择类目
									</li>
									<li class="active">
										<span>2</span>修改能力详情
									</li>
									<li>
										<span>3</span>提交成功，等待审核
									</li>
								</ul>
							</div>
							<!-- 
							<div class="filter">
								<div class="info-level">
									<span class="fl">能力信息评星：</span>
									<div class="star s-0">
										2星
									</div>
								</div>
							</div>
							 -->
							<div class="category-selected">
								<p class="fl">
									<b>您选择的类目是：</b>${levl1}
									<span>&gt;</span>${levl2}
									<span>&gt;</span>${levl3}
								</p>
								<a href="javascript:history.back();" class="btn-ctrl fl">返回重选类目</a>
							</div>
							<div class="product-info-form">

								<div class="required-tip">
									<span class="fr"><em>*</em>为必填项</span>填写能力基本信息
								</div>
								<div class="row">
									<div class="label">
										<em>*</em>能力标题：
									</div>
									<div class="cell">
										<input id="name" name="name" value="${capability.name }" type="text" class="text"   style="width: 380px;" validate="{required:true,maxlength:40}" ></input>
									</div>
									<div id="pronameTip"></div>
									<div class="prductNameTip">
										能力标题越详细越容易被客户找到，最长40个字符。
									</div>
								</div>
								<div class="row">
									<div class="label">
										<em>*</em>能力关键词：
									</div>
									<div class="cell">
										<input id="prokey" name="prokey" value="${capability.prokey }" type="text" class="text"  style="width: 100px;" validate="{required:true,maxlength:10}" />
									</div>
									<div id="prokeyTip"></div>
									<div class="prductNameTip">
										准确的能力关键词利于获取更好的排名及更多的展示曝光机会，2~7个字符
									</div>
								</div>
								<div class="row" id="J_productProps">
									<div class="label">
										能力属性：
									</div>
									<div class="note">
										完整的能力属性有助于买家找到您的信息
									</div>

									<div id="attrArea" class="product-props">

										<c:forEach items="${property}" var="p">


											<div class="row" d-attid="6811" d-inputtype="1"
												d-isneeded="N" d-parentid="0" d-parentvid="0">
												<div class="label">
													<span d-atttip="true">${p.propertyName}</span>：
												</div>

												<c:if test="${p.propertyType=='2'}">
													<div class="cell">
														<select id="property${p.id}" name="property${p.id}">


															<c:forTokens items="${p.value}" delims="," var="t">
																<option value="${t}">
																	${t}
																</option>
															</c:forTokens>
														</select>
													</div>
												</c:if>

												<c:if test="${p.propertyType=='1'}">
													<div class="cell">
														<input id="property${p.id}" name="property${p.id}" class="text" maxlength="80" type="text" />
													</div>
												</c:if>

											</div>
										</c:forEach>

									</div>
								</div>
								<div class="row">
									<div class="label">
										物品信息：
									</div>
									<div class="cell">
										<a href="javascript:void(0)" onclick="add_product();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd2.jpg'"
												onmouseout="src='${ctx}/images/iconadd.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
										<a href="javascript:void(0)"  onclick="del_product();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>
									</div>
								</div>
								 
								<div class="row">

									<div class="cell">
										<table class="tt" cellspacing="0" width="650px" 
											id="product_list">
											<tr>
												<th width="5%">
													<input type="checkbox"  id="checkbox" onclick="checkall();"/>
												</th>
												<th>
													名称
												</th>
												<th>
													单位
												</th>
												<th>
													单价
												</th>
											</tr>
											
												<c:forEach items="${materiallist }" var="ml">
													<c:if test="${ml == null }">
													
													</c:if>
													<c:if test="${ml != null }">
													<tr>
														<td>
														<input type="checkbox" name="check" value="${ml.id }"/>
														<input type="hidden"  name="materiel_ids" value="${ml.id }"/>
														</td> 
														<td>${ml.name }</td>
														<td>${ml.unit }</td>
														<td>${ml.price }</td>
													</tr>
													</c:if>
												</c:forEach>
										</table>
									</div>
								</div>


								<div class="row">
									<div class="label">
										<em>*</em>适用范围：
									</div>
									<div class="cell">
										<input id="useScope" name="useScope" type="text" class="text w80" value="${capability.useScope }" validate="{required:true,maxlength:40}" />
										
									</div>

								</div>

								<div class="row">
									<div class="label">
										能力图片：
									</div>
									<div class="cell" js_picarea="true">
										<a href="javascript:selectIcon();" class="btn-ctrl"
											id="addProductPic" js_tukubtn="true">添加能力图片</a>
										<div class="addproduct-pic" id="picView">
											<c:if test="${capability.pic != '' }">
												<img src="${ctx}${capability.pic}" width="80" height="84" />
											</c:if>
										</div>
									</div>
								</div>
								
							 
								<div class="row">
									<div class="label">
										能力详情：
									</div>
									<div class="cell">
									  <div class="postbox"   id="editor" position="center"   style="overflow:auto; width: 650px;">
										 
											<textarea id="txtHtml" name="html">${capability.info }</textarea>													
									 </div>
									</div>
								</div>
								<div class="offer-scan">
									<!-- 
									<div class="row pb0">
										<div class="label">
											质量评星：
										</div>
										<div class="star s-0">

										</div>
									</div>
									 -->
									<div class="row">
										<div class="offer-scan-con">
											<p>
												优质的信息更能得到买家的青睐，以下方面建议您发布前可以再检查一下：
											</p>
											<ul>
												<li>
													1、选择准确的类目；
												</li>
												<li>
													2、能力标题中包含能力关键词，并突出能力卖点；
												</li>
												<li>
													3、尽可能地完善能力属性及能力详情，必要时能力详情可图文并茂；注意突出能力的优势及特点；
												</li>
												<li>
													4、上传清晰的能力图片；
												</li>
											</ul>
										</div>
									</div>
								</div>
								

							<div class="row pt16">

							 
								<input type="button" class="submit" id="save" value="确认保存" >
								

							</div>

						</div>
						</div>
						</div>
					</td>
				</tr>

			</table>
			<input type="hidden" name="id" value="${capability.id}" />
			<input id="info" name="info" type="hidden" value="${capability.info }"></input>
			<input id="typeId" name="typeId" type="hidden" value="${capability.typeId}"></input>
			<input id="typeName" name="typeName" type="hidden" value="${capability.typeName}"></input>
			<input id="pic" name="pic" type="hidden" value="${capability.pic }" ></input>
			<input id="publishState" name="publishState" type="hidden" value="${capability.publishState }"></input>
		</form>
	</body>
</html>