<%--
	time:2013-07-09 12:18:14
	desc:edit the cloud_user_register
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_user_register</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#registerForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		var dd = null;
		function selectIcon(type){
			if(type=='logo'){
				var urlShow = '${ctx}/cloud/pub/image/toUpload.ht?_callback=' + type;
				dd = $.ligerDialog.open({ url:urlShow, height: 350,width: 500, title :'图片上传器', name:"frameDialog_",
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
				});
			}else{
				var urlShow = '${ctx}/cloud/pub/toUpload.ht?_callback=' + type;
				dd = $.ligerDialog.open({ url:urlShow, height: 130,width: 500, title :'文件上传器', name:"frameDialog_",
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.close(); } } ] 
				});
			}
		};
		
		function _callbackImageUploadSucess(src,type){
			dd.close();
			if(type =='introduce'){
				$('#introduce').val(src);
			}else if(type =='credential'){
				$('#credential').val(src);
			}
		}
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/register/register/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${register.id !=null}">
			        <span class="tbar-label">编辑cloud_user_register</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_user_register</span>
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
		<form id="registerForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				
				<tr>
					<th width="20%">姓名: </th>
					<td><input type="text" id="name" name="name" value="${register.name}"  class="inputText" validate="{required:false,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">性别: </th>
					<td><input type="radio" id="sex" name="sex" value="1"  class="inputText" validate="{required:false,number:true }"  checked/>男
					<input type="radio" id="sex" name="sex" value="0"  class="inputText" validate="{required:false,number:true }"  />女
					</td>
				</tr>
				<tr>
					<th width="20%">邮箱: </th>
					<td><input type="text" id="email" name="email" value="${register.email}"  class="inputText" validate="{required:false,maxlength:765}"  /></td>
				</tr>
				<tr>
					<th width="20%">身份证号: </th>
					<td><input type="text" id="identity" name="identity" value="${register.identity}"  class="inputText" validate="{required:false,maxlength:54}"  /></td>
				</tr>
				<tr>
					<th width="20%">手机号码: </th>
					<td><input type="text" id="tellphone" name="tellphone" value="${register.tellphone}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				<tr>
				<th width="20%">获奖证书: </th>
				<td>
					<!-- <input type="file" name="catePic" id="catePic" value=""/> -->
					<input type="text" name="credential" id="credential" value="${register.credential}"/>
					<a href="javascript:selectIcon('credential');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加获奖证书</a>
					<div class="qyzzView_pic" id="qyzzView">
					</div>
				</td>
				</tr>
				<tr>
				<th width="20%">作品介绍: </th>
				<td>
					<!-- <input type="file" name="catePic" id="catePic" value=""/> -->
					<input type="text" name="introduce" id="introduce" value="${register.introduce}"/>
					<a href="javascript:selectIcon('introduce');" class="btn-ctrl"
							id="addProductPic" js_tukubtn="true">添加作品</a>
					<div class="qyzzView_pic" id="qyzzView">
					</div>
				</td>
			</tr>
			</table>
			<input type="hidden" name="id" value="${register.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
