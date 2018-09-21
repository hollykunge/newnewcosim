<%--
	time:2013-04-16 16:14:04
	desc:edit the cloud_capability_class
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑能力分类</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#capabilityClassForm').form();
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
						window.location.href = "${ctx}/cloud/config/capabilityClass/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		function preview(){
			CommonDialog("capabilityClassTree",
			function(data) {
				 
				$("#parentId").val(data.id);
				$("#parentName").val(data.name);
				
				
			});
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${capabilityClass.id !=null}">
			        <span class="tbar-label">编辑能力分类</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加能力分类</span>
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
		<form id="capabilityClassForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">能力分类名称: </th>
					<td><input type="text" id="name" name="name" value="${capabilityClass.name}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">父级: </th>
					<td>
					<input type="hidden" id="parentId" name="parentId" value="${capabilityClass.parentId}"  class="inputText"    />
					<input type="text" id="parentName" name="parentName" value="${capabilityClass.parentName}" readonly="readonly"  class="inputText"    />
					<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					
					</td>
				</tr>
				<tr>
					<th width="20%">层级: </th>
					<td><input type="text" id="levels" name="levels" value="${capabilityClass.levels}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				 
			</table>
			<input type="hidden" name="id" value="${capabilityClass.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
