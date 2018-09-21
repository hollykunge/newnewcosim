<%--
	time:2013-04-17 18:35:03
	desc:edit the cloud_material_property_value
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_material_property_value</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#materialPropertyValueForm').form();
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
						window.location.href = "${ctx}/cloud/materialPropertyValue/materialPropertyValue/list.ht";
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
			    <c:when test="${materialPropertyValue.id !=null}">
			        <span class="tbar-label">编辑cloud_material_property_value</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_material_property_value</span>
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
		<form id="materialPropertyValueForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">material_id:  <span class="required">*</span></th>
					<td><input type="text" id="materialId" name="materialId" value="${materialPropertyValue.materialId}"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">property_id:  <span class="required">*</span></th>
					<td><input type="text" id="propertyId" name="propertyId" value="${materialPropertyValue.propertyId}"  class="inputText" validate="{required:true,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">property_value: </th>
					<td><input type="text" id="propertyValue" name="propertyValue" value="${materialPropertyValue.propertyValue}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${materialPropertyValue.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
