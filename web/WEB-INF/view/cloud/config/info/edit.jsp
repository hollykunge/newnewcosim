<%--
	time:2013-04-17 19:28:40
	desc:edit the sys_org_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>
	<title>编辑企业信息</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	
	<!-- tablegird -->
	<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/cloud/style.css" rel="stylesheet" type="text/css" />
	
	
	<!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
    <script type="text/javascript">
        $(function (){
            $("#navtab1").ligerTab({ dblClickToClose: false });                 
        });
    </script>
	<script type="text/javascript">
	 
		var manager = null;
		$(function() {
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 200,
				initialContent : ''
			});
			editor.render("txtHtml");
			
			
			var cks = $("#ck_mod").val();
			var arrs = new Array();
			arrs = cks.split(",");
			
			for(var i = 0;i<arrs.length;i++){
				if(arrs[i] == 1 ){
					$("#model1").attr("checked", true);
				}else if(arrs[i] == 2 ){
					$("#model2").attr("checked", true);
				}else if(arrs[i] == 3){
					$("#model3").attr("checked", true);
				}else if(arrs[i] == 4){
					$("#model4").attr("checked", true);
				}else{
					$("#model0").attr("checked", true);
				}
				
				
			}
			
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#infoForm').form();
			$("#dataFormSave").click(function() {
				$('#info').val(editor.getContent());
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					manager = $.ligerDialog.waitting('正在保存中,请稍候...');
					form.submit();
				}
			});
			
			
			//行业
			var indus = $('#industry').val();
			var ind2 = $('#ind2').val();
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : '${ctx}/cloud/config/info/getCascadeJsonData.ht',
				data : {value : indus },
				success : function(dics){
					var rows=dics;
					$('#industry2').html('');
					var opt ='';
					for(var i=0;i<rows.length;i++){
						var s ='';
						var iv =rows[i].itemValue + '';
						if(iv == ind2)
							s='selected';
						$('#industry2').append('<option ' + s + ' value="' +  rows[i].itemValue + '">' + rows[i].itemName + '</option>');
					};
				}
			});
			
			
		});
		
		
		//上传图片	
		var dd = null;
		function selectIcon(type){
			if(type=='logo'){
				var urlShow = '${ctx}/cloud/pub/image/toUpload.ht?_callback=' + type;
				dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_",
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
				});
			}else{
				var urlShow = '${ctx}/cloud/pub/toUpload.ht?_callback=' + type;
				dd = $.ligerDialog.open({ url:urlShow, height: 130,width: 500, title :'图片上传器', name:"frameDialog_",
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
				});
			}
		};
		
		//上传图片回调函数
		function _callbackImageUploadSucess(src,type){
			dd.close();
			if(type=='logo'){//上传企业logo
				$("#logo").val(src);
			 	var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
			 	$(".addproduct-pic").empty();
				$("#picView").append(item);
			}else if(type=='qyzz'){//上传资质
				$("#catePic").val(src);
			 	var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
			 	$(".qyzzView_pic").empty();
				$("#qyzzView").append(item);
			}else if(type=='dhpic'){//上传导航栏
				$("#showimage").val(src);
				var item = $('<img src="${ctx}'+src+'" width="260" height="84" />');
				$(".addqydhtp-pic").empty();
				$("#pic_showImage").append(item);
			}
		}
	 	
		//删除导航图片
		function deleteIcon(){
			$("#showimage").val('');
			$(".addqydhtp-pic").empty();
			$.ligerMessageBox.alert('删除导航图片成功');
		}
			
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.alert('保存成功');
				/**
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续", function(rtn) {
					if(rtn){
						window.location.href = "${ctx}/cloud/config/info/edit.ht";
					}else{
						this.close();
					}
				});**/
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
	 
		function shoudiv(){
			var ck = $("#Check_info");
			if(ck.is(':checked')==true){
				$("#isture_check").show();
			}else{
				$("#isture_check").hide();
				
			}
		}
	</script>
</head>
<body style="padding:10px">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">编辑企业信息</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<!-- 企业信息为审核通过时，提示信息 -->
				<c:if test="${info.state != 2}">
					<div class="group">请完善企业信息，并等待管理员对你企业信息进行审核</div>
				</c:if>
				<c:if test="${info.state == 2}">
					<div class="group">恭喜您的企业信息已经通过管理员的审核了！</div>
				</c:if>
			</div>
		</div>
	</div>
	<form id="infoForm" method="post" action="save.ht">
		<table  border="0" cellspacing="0" cellpadding="0" type="main">
			<tr>
				<td>
					<div class="panel-body" id="navtab1" style="width: 840px;overflow:hidden;">
						<div tabid="home" title="企业基本资料(必填)" lselected="true">
							<div id="maingrid1" style="margin:10px; ">
								<div class="corp-form">
									<input type="hidden" name="sysOrgInfoId" value="${info.sysOrgInfoId}" />
									<input type="hidden" name="flaglogo" value="${info.flaglogo}" />
									<c:if test="${info.state == 2}">
										<input type="hidden" name="state" value="${info.state }" />
									</c:if>
									<c:if test="${info.state != 2 }">
										<input type="hidden" name="state" value="1" />
									</c:if>
									<div class="row">
										<div class="label">
											企业账号：
										</div>
										<div class="cell">
											<h5>
											${info.sysOrgInfoId}
											</h5>
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											<em>*</em>公司名称：
										</div>
									<div class="cell">
										<input class="text" id="name" name="name" value="${info.name}" type="text" class="inputText" validate="{required:true,maxlength:50}"/>
									</div>
								</div>
								
								<div class="row pt0">
									<div class="note">
										请用中文完整填写在工商局注册的公司名称。
										<br />
										例如：上海西芝信息技术有限公司；
										
										<br />
										个体经营如：“张三（个体经营）”
									</div>
								</div>
								<div class="row">
									<div class="label">
										<em>*</em>企业类型：
									</div>
									<div class="cell">
										<ap:selectDB name="type" id="type"
											where="typeId=10000005420003" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.type}">
										</ap:selectDB>
									</div>
									
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>经营模式：
									</div>
									<div id="business_mode_div" class="cell">
										<input type="hidden" id="ck_mod" value="${info.model }">
										<label for="type_6">
											<input type="checkbox" name="model" id="model1" class="ck_model" value="1"/>生产型
										</label>
										<label for="type_7">
											<input type="checkbox" name="model" id="model2" class="ck_model" value="2"/>贸易型
										</label>
										<label for="type_8">
											<input type="checkbox" name="model" id="model3" class="ck_model" value="3"/>服务型
										</label>
										<label for="type_9">
											<input type="checkbox" name="model" id="model4" class="ck_model" value="4"/>研发型
										</label>
										<label for="type_10">
											<input type="checkbox" name="model" id="model0" class="ck_model" value="0"/>其他类型
										</label>
<!-- 										<label for="type_6"> -->
<%-- 											<c:remove var="checked"/> --%>
<%-- 											<c:if test="${info.model==1}"> --%>
<%-- 												<c:set var="checked" value='checked="checked"'></c:set> --%>
<%-- 											</c:if> --%>
<%-- 											<input name="model" value="1" class="radio" id="model" ${checked} type="radio" /> --%>
<!-- 												生产型 -->
<!-- 										</label> -->
<!-- 										<label for="type_7"> -->
<%-- 											<c:remove var="checked"/> --%>
<%-- 											<c:if test="${info.model==2}"> --%>
<%-- 												<c:set var="checked" value='checked="checked"'></c:set> --%>
<%-- 											</c:if>	 --%>
<%-- 											<input name="model" value="2" class="radio" id="model" ${checked} type="radio" /> --%>
<!-- 												贸易型 -->
<!-- 										</label> -->
<!-- 										<label for="type_8"> -->
<%-- 											<c:remove var="checked"/> --%>
<%-- 											<c:if test="${info.model==3}"> --%>
<%-- 												<c:set var="checked" value='checked="checked"'></c:set> --%>
<%-- 											</c:if>	 --%>
<%-- 											<input name="model" value="3" class="radio" id="model" ${checked} type="radio" /> --%>
<!-- 												服务型 -->
<!-- 										</label> -->
<!-- 										<label for="type_9"> -->
<%-- 											<c:remove var="checked"/> --%>
<%-- 											<c:if test="${info.model==4}"> --%>
<%-- 												<c:set var="checked" value='checked="checked"'></c:set> --%>
<%-- 											</c:if>	 --%>
<%-- 											<input name="model" value="4" class="radio" id="model" ${checked} type="radio" /> --%>
<!-- 												研发型 -->
<!-- 										</label> -->
<!-- 										<label for="type_10"> -->
<%-- 											<c:remove var="checked"/> --%>
<%-- 											<c:if test="${info.model==0}"> --%>
<%-- 												<c:set var="checked" value='checked="checked"'></c:set> --%>
<%-- 											</c:if> --%>
<%-- 											<input name="model" value="0" class="radio" id="model" ${checked} type="radio" /> --%>
<!-- 												其他类型 -->
<!-- 										</label> -->
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>主营产品：
									</div>
									<div class="cell">
										<input class="text" id="product" name="product" value="${info.product }" type="text" class="inputText" validate="{required:true,maxlength:30}" />
									</div>
									
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>主营行业：
									</div>
									<div class="cell">
										<ap:selectDB name="industry" id="industry" where="parentId=10000003470025" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry}">
										</ap:selectDB>
										<ap:ajaxSelect srcElement="industry" url="${ctx}/cloud/config/info/getCascadeJsonData.ht" targetElement="industry2"/>
										<ap:selectDB name="industry2" id="industry2" 
											where="1!=1" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.industry2}">
										</ap:selectDB>
										<input id="ind2" type="hidden" value="${info.industry2}">
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>企业规模：
									</div>
									<div class="cell">
										<ap:selectDB name="scale" id="scale" where="typeId=10000005680006" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.scale}">
										</ap:selectDB>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>经营范围：
									</div>
									<div class="cell">
										<ap:selectDB name="manageRange" id="manageRange" where="typeId=10000005510001" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.manageRange}">
										</ap:selectDB>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										企业logo：
									</div>
									<div class="cell">
										<input type="hidden" name="logo" id="logo" value="${info.logo }"/>
										<a href="javascript:selectIcon('logo');" class="btn-ctrl"
												id="addProductPic" js_tukubtn="true">添加企业logo</a>
										<div class="addproduct-pic" id="picView">
											<c:if test="${info.logo!='' }">
												<img src="${ctx}${info.logo}" width="80" height="84" />
											</c:if>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>所在国家、省、市：
									</div>
									<div class="cell">
										<ap:selectDB name="country" id="country" where="parentId=10000000180001" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.country}">
										</ap:selectDB>
										<ap:ajaxSelect srcElement="country" url="${ctx}/cloud/config/info/getCascadeJsonData.ht" targetElement="province"/>
										<ap:selectDB name="province" id="province" defaultText="==请选择==" defaultValue=""
											where="itemKey='province'" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.province}">
										</ap:selectDB>
										<ap:ajaxSelect srcElement="province" url="${ctx}/cloud/config/info/getCascadeJsonData.ht" targetElement="city"/>
										<ap:selectDB name="city" id="city" 
											where="itemKey='city'" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.city}">
										</ap:selectDB>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>企业地址：
									</div>
									<div class="cell">
										<input class="text" id="address" name="address" value="${info.address}" type="text" class="inputText" validate="{required:false,maxlength:64}"/>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										邮政邮编：
									</div>
									<div class="cell">
										<input class="text" id="postcode" name="postcode" value="${info.postcode}" type="text" class="inputText" validate="{required:false,maxlength:20}"/>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										公司网址：
									</div>
									<div class="cell">
										<input class="text" id="website" name="website" value="${info.website}" type="text" class="inputText" validate="{required:false,maxlength:100}"/>
									</div>
								</div>
								
								
								
								<div class="row">
									<div class="label">
										企业导航图片：
									</div>
									<div class="cell">
										<input type="hidden" name="showimage" id="showimage" value="${info.showimage }"/>
										<a href="javascript:selectIcon('dhpic');" class="btn-ctrl"
												id="add_dhPic" js_tukubtn="true">添加导航图片</a>
										<a href="javascript:deleteIcon('dhpic');" class="btn-ctrl"
												id="add_dhPic" js_tukubtn="true">删除导航图片</a>
										<div class="addqydhtp-pic" id="pic_showImage">
											<c:if test="${info.showimage!='' }">
												<img src="${ctx}${info.showimage}" width="260" height="80" />
											</c:if>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										工商注册证明：
									</div>
									<div class="cell">
										<input class="text" id="regProve" name="regProve" value="${info.regProve}" type="text" />
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>组织机构代码：
									</div>
									<div class="cell">
										<input name="code" type="text" class="text" id="code" value="${info.code }"  />
									</div>
								</div>
								
								<div class="row">
									<div class="label">
										<em>*</em>公司简介：
									</div>
									<div class="cell">
										 <input id="info" name="info" type="hidden"></input>
										
										 <div class="postbox"   id="editor" position="center"   style="overflow:auto; width: 650px;">
										 
											<textarea id="txtHtml" name="html"  >${info.info }</textarea>													
									 </div>
										
									</div>
								</div>
								
								</div>	
							</div>
						</div>
						<div  title="企业联系方式(必填)">
							<div id="maingrid2" style="margin:10px; ">
								<div class="corp-form">
									<div class="row">
										<div class="label">
												<em>*</em>注册邮箱：
										</div>
										<div class="cell">
											<input type="text" name="email" value="${info.email}" style="width:200px;" class="inputText" validate="{required:true,email:true}"/>
										</div>
									</div>
										
									<div class="row">
										<div class="label">
											<em>*</em>是否公开：
										</div>
										<div class="cell">
											<c:remove var="checked"/>
									        <c:if test="${info.isPublic==1}">
									        	<c:set var="checked" value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio"  name="isPublic" ${checked} class="radio" value="1" />是
											<c:remove var="checked"/>
									        <c:if test="${info.isPublic==0}">
									        	<c:set var="checked" value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio" name="isPublic" class="radio" ${checked} value="0"/>否
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											<em>*</em>联系人：
										</div>
										<div class="cell">
											<input name="connecter" type="text" class="text" id="connecter" value="${info.connecter }"  />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											<em>*</em>手机号：
										</div>
										<div class="cell">
											<input type="text" value="${info.tel}"
													name="tel" class="text"  />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											<em>*</em>固定电话：
										</div>
										<div class="cell">
											<input type="text" value="${info.homephone}"
													name="homephone" class="text"  />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											<em>*</em>传真：
										</div>
										<div class="cell">
											<input name="fax" type="text" class="text" id="fax"
													value="${info.fax}" datatype="*" />
										</div>
									</div>
									
								</div>
							</div>
						</div>
						<div  title="企业详细信息(选填)">
							<div id="maingrid3" style="margin:10px; ">
								<div class="corp-form">
									<div class="row">
										<div class="label">
											主要销售区域：
										</div>
										<div class="cell">
											<ap:selectDB name="sellArea" id="sellArea" where="parentId=10000005610078" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.sellArea}">
											</ap:selectDB>
											<ap:ajaxSelect srcElement="sellArea" url="${ctx}/cloud/config/info/getCascadeJsonData.ht" targetElement="sellArea2"/>
											<ap:selectDB name="sellArea2" id="sellArea2" 
												where="itemKey='sell_area' and parentId != 10000005610078" optionValue="itemValue"
												optionText="itemName" table="SYS_DIC"
												selectedValue="${info.sellArea2}">
											</ap:selectDB>
											
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											主要客户群体：
										</div>
										<div class="cell">
											<input name="clients" type="text" class="text" id="clients"
											value="${info.clients}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											企业占地面积：
										</div>
										<div class="cell">
											<input name="area" type="text" class="text" id="area"
											value="${info.area}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											员工人数：
										</div>
										<div class="cell">
											<input name="employees" type="text" class="text" id="employees"
											value="${info.employees}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											年营业额：
										</div>
										<div class="cell">
											<ap:selectDB name="turnover" id="turnover" where="typeId=10000005640295" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${info.turnover}">
											</ap:selectDB>
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											年出口额：
										</div>
										<div class="cell">
											<input name="exportFore" type="text" class="text" id="exportFore"
											value="${info.exportFore}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											年进口额：
										</div>
										<div class="cell">
											<input name="importFore" type="text" class="text" id="importFore"
											value="${info.importFore}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											企业品牌：
										</div>
										<div class="cell">
											<input name="brand" type="text" class="text" id="brand"
											value="${info.brand}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											质量管理体系：
										</div>
										<div class="cell">
											<input name="qualityControl" type="text" class="text" id="qualityControl"
											value="${info.qualityControl}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											注册资本：
										</div>
										<div class="cell">
											<input name="regCapital" type="text" class="text" id="regCapital"
											value="${info.regCapital}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											企业注册时间：
										</div>
										<div class="cell">
											<input type="text" id="registertime" name="registertime" value="<fmt:formatDate value='${info.registertime}' pattern='yyyy-MM-dd'/>" class="inputText date " validate="{date:true}" />
										</div>
									</div>
									
										<div class="row">
										<div class="label">
											企业年检时间：
										</div>
										<div class="cell">
											<input type="text" id="createtime" name="createtime" value="<fmt:formatDate value='${info.createtime}' pattern='yyyy-MM-dd'/>" class="inputText date " validate="{date:true}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											注册地点：
										</div>
										<div class="cell">
											<input name="regAdd" type="text" class="text" id="regAdd"
											value="${info.regAdd}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											法人：
										</div>
										<div class="cell">
											<input name="incorporator" type="text" class="text" id="incorporator"
											value="${info.incorporator}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											开户银行：
										</div>
										<div class="cell">
											<input name="openBank" type="text" class="text" id="openBank"
											value="${info.openBank}" />
										</div>
									</div>
									
									<div class="row">
										<div class="label">
											开户账户：
										</div>
										<div class="cell">
											<input name="openAccount" type="text" class="text" id="openAccount"
											value="${info.openAccount}" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div  title="企业资质认证信息">
							<table class="table-grid table-list" border="0" cellpadding="1" cellspacing="1" id="aptitude" formType="window" type="sub">
								<tr>
									<td colspan="7">
										<div class="group" align="left">
								   			<a id="btnAdd" class="link add">添加</a>
							    		</div>
							    		<div align="center">
										企业资质认证信息表
							    		</div>
						    		</td>
								</tr>
								<tr>
									<th>所属企业</th>
									<th>证书分类</th>
									<th>发证机构</th>
									<th>生效日期</th>
									<th>截止日期</th>
									<th>证书扫描件</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${aptitudeList}" var="aptitudeItem" varStatus="status">
								    <tr type="subdata">
									    <td style="text-align: center" name="infoId">${info.name}</td>
									    <td style="text-align: center" name="cateType">${aptitudeItem.cateType}</td>
									    <td style="text-align: center" name="cateOrg">${aptitudeItem.cateOrg}</td>
										<td style="text-align: center" name="inureDate"><fmt:formatDate value='${aptitudeItem.inureDate}' pattern='yyyy-MM-dd'/></td>								
									    <td style="text-align: center" name="endDate">${aptitudeItem.endDate}</td>
									    <td style="text-align: center" name="catePic">${aptitudeItem.catePic} </td>
									    <td style="text-align: center">
									    	<a href="javascript:void(0)" class="link del">删除</a>
									    	<a href="javascript:void(0)" class="link edit">编辑</a>
									    </td>
										<input type="hidden" name="infoId" value="${aptitudeItem.infoId}"/>
										<input type="hidden" name="cateType" value="${aptitudeItem.cateType}"/>
										<input type="hidden" name="cateOrg" value="${aptitudeItem.cateOrg}"/>
									    <input type="hidden" name="inureDate" value="<fmt:formatDate value='${aptitudeItem.inureDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
										<input type="hidden" name="endDate" value="${aptitudeItem.endDate}"/>
										<input type="hidden" name="catePic" value="${aptitudeItem.catePic}"/>
										
								    </tr>
								</c:forEach>
								<tr type="append">
							    	<td style="text-align: center" name="infoId"></td>
							    	<td style="text-align: center" name="cateType"></td>
							    	<td style="text-align: center" name="cateOrg"></td>
									<td style="text-align: center" name="inureDate"></td>								
							    	<td style="text-align: center" name="endDate"></td>
							    	<td style="text-align: center" name="catePic"></td>
							    	<td style="text-align: center">
							    		<a href="javascript:void(0)" class="link del">删除</a>
							    		<a href="javascript:void(0)" class="link edit">编辑</a>
							    	</td>
							    	<input type="hidden" name="infoId" value=""/>
							    	<input type="hidden" name="cateType" value=""/>
							    	<input type="hidden" name="cateOrg" value=""/>
							    	<input type="hidden" name="inureDate" value="" class="inputText date"/>
							    	<input type="hidden" name="endDate" value=""/>
							    	<input type="hidden" name="catePic" value=""/>
						 		</tr>
						    </table>
						    
						    </td>
						
						    
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<form id="aptitudeForm" style="display:none" method="post">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">所属企业: </th>
				<td><input type="text" name="infoId" value="${info.sysOrgInfoId }" readonly="readonly" class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">证书分类: </th>
				<td>
					<ap:selectDB name="cateType" id="cateType" 
														where="parentId=10000005960001" optionValue="itemValue"
														optionText="itemName" table="SYS_DIC"
														selectedValue="">
													</ap:selectDB>
				</td>
			</tr>
			<tr>
				<th width="20%">发证机构: </th>
				<td><input type="text" name="cateOrg" value=""  class="inputText" validate="{required:false,maxlength:192}"/></td>
			</tr>
			<tr>
				<th width="20%">生效日期: </th>
				<td class="">
					<div class="cell">
						<input type="text" name="inureDate" value="" class="inputText date" validate="{date:true}"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<th width="20%">截止日期: </th>
				<td><input type="text" name="endDate" value="长期有效"  class="inputText" validate="{required:false,maxlength:192}"/></td>
			</tr>
			<tr>
				<th width="20%">证书扫描件: </th>
				<td>
					<!-- <input type="file" name="catePic" id="catePic" value=""/> -->
					<input type="text" name="catePic" readonly="readonly" id="catePic" value=""/>
					<a href="javascript:selectIcon('qyzz');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加企业资质</a>
					<div class="qyzzView_pic" id="qyzzView">
					</div>
				</td>
			</tr>
		</table>
		</form>
	
</body>
</html>
