<%--
	time:2013-04-16 17:21:17
	desc:edit the cloud_business_chance
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_business_chance</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
 
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor/page-editor/editor_api.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/ueditor/themes/default/ueditor.css" />
	<script type="text/javascript">
	var manager = null;
		$(function() {
			
			$(".radio").click(function(){
				if($(this).attr("value")=='审核未通过'){
					$("#div_show").show();
				}else{
					$("#div_show").hide();
				}
			});
			
			var editor = new baidu.editor.ui.Editor({
				minFrameHeight : 200
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
				$.ligerMessageBox.confirm("提示信息", "审核成功,是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
					 
					window.location.href = "${ctx}/cloud/config/businessDevchase//list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	 
			
			
			
		 
		
		//上传图片	
		var dd = null;
		function selectIcon(){
			var urlShow = '${ctx}/cloud/pub/toUpload.ht';
			dd = $.ligerDialog.open({ url:urlShow, height: 130,width: 500, title :'图片上传器', name:"frameDialog_",
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
			});
		};

		 
			
				function _callbackImageUploadSucess(src){
				dd.close();
					$("#image").val(src);
						 var item = $('<img src="${ctx}'+src+'" width="80" height="84" />');
						$("#picView").append(item);
					 
				 
			}
		 
				
				
			
		</script>
							
	</head>
	<body  >
	 
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${businessDevchase.id !=null}">
			        <span class="tbar-label">编辑研发商机</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加研发商机</span>
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
		<form id="businessDevchaseForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				  <tr>
					 <th width="20%">审核状态:  <span class="required">*</span></th>
					<td>
					 
				                 
														<input type="radio" name="publishState"   class="radio" value="审核通过" checked="checked" />审核通过
													
														<input type="radio" name="publishState"   class="radio"   value="审核未通过" />审核未通过
					
					</td>
				</tr>
				
					<tr>
						<th width="20%">审核原因: </th>
					<td>
						<textarea rows="3" cols="60" id="publishInfo" name="publishInfo" style="width:550px;">${businessDevchase.publishInfo}</textarea>
						<span id="div_show" style="display: none;"><font color="red">请填写未通过原因</font></span>
					</td>
					</tr>
				<tr>
					<th width="20%">专业学科分类: </th>
				<td><input type="text" id="classid" name="classid" value="${businessDevchase.classid}"  class="inputText"  readonly="readonly" /></td>
				</tr>
				<tr>
					<th width="20%">商机名称: </th>
					<td><input type="text" id="name" name="name" value="${businessDevchase.name}" style="width:550px;" class="inputText" readonly="readonly"  /></td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startTime" name="startTime" value="<fmt:formatDate value='${businessDevchase.startTime}' pattern='yyyy-MM-dd'/>" class="inputText "    readonly="readonly"/></td>
				</tr>
				<tr>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="endTime" name="endTime" value="<fmt:formatDate value='${businessDevchase.endTime}' pattern='yyyy-MM-dd'/>" class="inputText "   readonly="readonly"/></td>
				</tr>
				<tr>
					<th width="20%">图片: </th>
					<td>
				 
			    
					       
					       <input id="image" name="image" type="hidden"
									value="${businessDevchase.image}">  
									<div class="addproduct-pic" id="picView" >
									<c:if test="${businessDevchase.image!=null }">
												<img src="${ctx}${businessDevchase.image}" onError="this.src='${ctx}/images/product_img05.jpg'" width="80" height="84" />
											</c:if>
									
									</div>
					</td>
				</tr>
				 
				 
				<tr>
					<th width="20%">研发伙伴资质要求: </th>
					<td>
					<textarea rows="3" cols="60" id="yfhbzzyq" name="yfhbzzyq" style="width:550px;" readonly="readonly">${businessDevchase.yfhbzzyq}</textarea>
					
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
				<input type="hidden" id="operateTime" name="operateTime"
							value="<fmt:formatDate value='${businessDevchase.operateTime}' pattern='yyyy-MM-dd'/>"class="inputText date" /> 
			<input type="hidden" name="id" id="id" value="${businessDevchase.id}" />
			<input type="hidden" id="userId" name="userId" value="${businessDevchase.userId}"  />
			<input type="hidden" id="companyId" name="companyId" value="${businessDevchase.companyId}"  />
				<input type="hidden" id="userName" name="userName" value="${businessDevchase.userName}"  />
			<input type="hidden" id="companyName" name="companyName" value="${businessDevchase.companyName}"  />
			<input id="content" name="content" type="hidden" value="${fn:escapeXml(businessDevchase.content)}"></input>
			<input type="hidden" id="classid" name="classid" value="${businessDevchase.classid}"    />
			 
		</form>
		
	</div>
</div>
 
</body>
</html>
