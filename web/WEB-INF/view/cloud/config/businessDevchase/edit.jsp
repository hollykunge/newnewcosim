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
<title>商机发布</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>


 
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
<style type="text/css">
/* 分类选择 */
.category-columns{overflow: hidden;zoom: 1;}
.category-columns .category-list{margin-left:0; padding-left:0;text-align:left;float: left;width: 150px;height: 200px;background-color: white;border: 1px solid #e0e0e0;overflow-x: hidden;overflow-y: scroll;}
.category-columns .category-list li{height: 22px;line-height: 22px;overflow: hidden;}
.category-columns .category-list a{display: block;height: 22px;padding-left: 10px;color: #454545;text-decoration: none;}
.category-columns .category-list a:hover{background-color: #d7d7d7;}
.category-columns .category-list a.select{background: #9ab8d6 !important;color: #fff !important;}

</style>
<script type="text/javascript">
	var manager = null;
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
			var frm=$('#businessDevchaseForm').form();
			$("a.save").click(function() {
				frm.setData();
				$('#content').val(editor.getContent());
				frm.ajaxForm(options);
				if(frm.valid()){
				 
						 manager = $.ligerDialog.waitting('正在保存中,请稍候...');
					form.submit();
				}
			});
		});
		 
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
			$.ligerMessageBox.alert("提示信息", "商机已提交、请等待管理员审核");
			 
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		
	//上传图片	
	var dd = null;
	function selectIcon(){
		var urlShow = '${ctx}/cloud/pub/image/toUpload.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_"});
	};

	 
		
			function _callbackImageUploadSucess(src){
			dd.close();
				$("#image").val(src);
					 var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
					$("#picView").append(item);
				 
			 
		}
 
			
			
		 
		
	</script>
						
</head>
<body >
	<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">添加商机</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<c:if test="${info.state == 2}">
								<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				 			</c:if>
				 			<div class="l-bar-separator"></div>
				 			<c:if test="${info.state != 2}">
								<div class="group">你的企业还未通过审核，不允许发布商机！</div>
				 			</c:if>
							
							 
						</div>
					</div>
				</div>
				<div class="panel-body">
		<form id="businessDevchaseForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">商机名称: </th>
					<td><input type="text" id="name" name="name" value="${businessDevchase.name}"  class="inputText"  style="width:550px;" validate="{required:true,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startTime" name="startTime" value="<fmt:formatDate value='${businessDevchase.startTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="endTime" name="endTime" value="<fmt:formatDate value='${businessDevchase.endTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">专业学科分类: </th>
					<td>
					 
						<ap:selectDB name="classid" id="classid" style="width:140px;"
											where="parentId=10000011450023" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${businessDevchase.classid}" styleClass="selectText">
										</ap:selectDB>
					</td>
				</tr>
				<tr>
					<th width="20%">图片: </th>
					<td>
					<input id="image" name="image" type="hidden"
									value="${businessDevchase.image}"> <a
									href="javascript:selectIcon();" class="btn-ctrl"
									id="addProductPic" js_tukubtn="true">添加图片</a>
										<div class="addproduct-pic" id="picView">
									<c:if test="${businessDevchase.image!=null }">
												<img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img05.jpg'"  width="80" height="84" />
											</c:if>
									</div>
					</td>
				</tr>
				<tr>
					<th width="20%">研发伙伴资质要求: </th>
					
					<td>
					<textarea rows="3" cols="100"   id="yfhbzzyq" name="yfhbzzyq" style="width:550px;">${businessDevchase.yfhbzzyq}</textarea>
					</td>
				</tr>
				<tr>
					<th width="20%">商机内容: </th>
					<td>
						<div id="editor" position="center" style="overflow: auto; height: 100%;">
						  	<textarea id="txtHtml" name="html">${fn:escapeXml(businessDevchase.content)}</textarea>
					    </div>
                    </td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${businessDevchase.id}" />
			<input type="hidden" id="operateTime" name="operateTime"
							value="<fmt:formatDate value='${businessDevchase.operateTime}' pattern='yyyy-MM-dd'/>"class="inputText date" /> 
			 
			<input type="hidden" id="publishState" name="publishState" value="未审核" />
			<input type="hidden" id="userId" name="userId" value="${businessDevchase.userId}"  />
			<input type="hidden" id="companyId" name="companyId" value="${businessDevchase.companyId}"  />
			<input type="hidden" id="userName" name="userName" value="${businessDevchase.userName}"  />
			<input type="hidden" id="companyName" name="companyName" value="${businessDevchase.companyName}"  />
			<input id="content" name="content" type="hidden"></input>
			 
		</form>
		
	</div>
 
</body>
</html>
