<%--
	time:2013-06-07 17:51:21
	desc:edit the 资源
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 资源</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	
				 <!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css"/>
	
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
			var frm=$('#cloudSrcForm').form();
			$("a.save").click(function() {
			$('#info').val(editor.getContent());
				frm.setData();
				
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
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/config/cloudSrc/list.ht";
					}
				});
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
		$("#pic").val(src);
				 var item = $('<img src="${ctx}'+src+'" width="178" height="167" />');
				$("#picView").append(item);
			 
		 
	}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${cloudSrc.id !=null}">
			        <span class="tbar-label">编辑资源</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加资源</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="cloudSrcForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">资源名称: </th>
					<td><input type="text" id="name" name="name" value="${cloudSrc.name}"  class="inputText"  style="width: 80%;" validate="{required:true,maxlength:255}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">链接地址: </th>
					<td><input type="text" id="path" name="path" value="${cloudSrc.path}"  class="inputText"  style="width: 80%;" validate="{required:true,maxlength:255}"  /></td>
				</tr>
				<tr>
					<th width="20%">资源图片: </th>
					<td>
					<input id="pic" name="pic" type="hidden"
									value="${cloudSrc.pic}"> <a
									href="javascript:selectIcon();" class="btn-ctrl"
									id="addProductPic" js_tukubtn="true">添加图片</a>
										<div class="addproduct-pic" id="picView">
									<c:if test="${cloudSrc.pic!=null }">
												<img src="${ctx}${cloudSrc.pic}" onError="this.src='${ctx}/images/default-chance.jpg'"  width="178" height="167" />
											</c:if>
									</div>
					</td>
				</tr>
				<tr>
					<th width="20%">资源详情: </th>
					<td>
					
					  <div class="postbox"   id="editor" position="center"   style="overflow:auto; width: 650px;">
										 
											<textarea id="txtHtml" name="html">${fn:escapeXml(cloudSrc.info)}</textarea>													
									 </div>
					
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloudSrc.id}" />
			<input id="info" name="info" type="hidden"></input>
		 
		</form>
		
	</div>
</div>
</body>
</html>
