<%--
	time:2013-07-21 15:48:29
	desc:edit the cloud_resource_class
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_resource_class</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#cloudResourceForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/cloudResource/resourceManagement/list.ht";
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
			    <c:when test="${cloudResource.id !=null}">
			        <span class="tbar-label">编辑cloud_resource_class</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_resource_class</span>
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
		<form id="cloudResourceForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">资源类ID:  <span class="required">*</span></th>
					<td><input type="text" id="id" name="id" value="${cloudResource.id}"  class="inputText" validate="{maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">资源类名称:  <span class="required">*</span></th>
					<td><input type="text" id="name" name="name" value="${cloudResource.name}"  class="inputText" validate="{required:true,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">父类ID:  <span class="required">*</span></th>
					<td><input type="text" id="parentId" name="parentId" value="${cloudResource.parentId}"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">所属层级:  <span class="required">*</span></th>
					<td><input type="text" id="levels" name="levels" value="${cloudResource.levels}"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">打开方式: </th>
					<td><input type="text" id="openType" name="openType" value="${cloudResource.openType}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">打开链接地址: </th>
					<td><input type="text" id="openUrl" name="openUrl" value="${cloudResource.openUrl}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="cloudResourceInstance" formType="window" type="sub">
				<tr>
					<th>资源名称</th>
					<th>资源信息</th>
					<th>图片地址</th>
					<th>链接1地址</th>
					<th>链接1名称</th>
					<th>链接2地址</th>
					<th>链接2名称</th>
					<th>blank1</th>
					<th>blank2</th>
					<th>mode</th>
					<th>操作</th>
				</tr>

				<tr type="append">
			    	<td style="text-align: center" name="title"></td>
			    	<td style="text-align: center" name="info"></td>
			    	<td style="text-align: center" name="urlPic"></td>
			    	<td style="text-align: center" name="url1"></td>
			    	<td style="text-align: center" name="url1Name"></td>
			    	<td style="text-align: center" name="url2"></td>
			    	<td style="text-align: center" name="url2Name"></td>
			    	<td style="text-align: center" name="blank1"></td>
			    	<td style="text-align: center" name="blank2"></td>
			    	<td style="text-align: center" name="mode"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="title" value=""/>
			    	<input type="hidden" name="info" value=""/>
			    	<input type="hidden" name="urlPic" value=""/>
			    	<input type="hidden" name="url1" value=""/>
			    	<input type="hidden" name="url1Name" value=""/>
			    	<input type="hidden" name="url2" value=""/>
			    	<input type="hidden" name="url2Name" value=""/>
			    	<input type="hidden" name="blank1" value=""/>
			    	<input type="hidden" name="blank2" value=""/>
			    	<input type="hidden" name="mode" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${cloudResource.id}" />
		</form>
		
	</div>
	<form id="cloudResourceInstanceForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">资源名称: </th>
				<td><input type="text" name="title" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">资源信息: </th>
				<td><input type="text" name="info" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">图片地址: </th>
				<td><input type="text" name="urlPic" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">链接1地址: </th>
				<td><input type="text" name="url1" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">链接1名称: </th>
				<td><input type="text" name="url1Name" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">链接2地址: </th>
				<td><input type="text" name="url2" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">链接2名称: </th>
				<td><input type="text" name="url2Name" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">blank1: </th>
				<td><input type="text" name="blank1" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">blank2: </th>
				<td><input type="text" name="blank2" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">mode: </th>
				<td><input type="text" name="mode" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
