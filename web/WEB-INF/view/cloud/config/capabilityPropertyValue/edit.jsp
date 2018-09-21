<%--
	time:2013-04-17 19:23:27
	desc:edit the cloud_capability_property_value
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_capability_property_value</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#capabilityPropertyValueForm').form();
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
						window.location.href = "${ctx}/cloud/capabilityPropertyValue/capabilityPropertyValue/list.ht";
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
			    <c:when test="${capabilityPropertyValue.id !=null}">
			        <span class="tbar-label">编辑cloud_capability_property_value</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_capability_property_value</span>
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
		<form id="capabilityPropertyValueForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">capability_id: </th>
					<td><input type="text" id="capabilityId" name="capabilityId" value="${capabilityPropertyValue.capabilityId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">property_id: </th>
					<td><input type="text" id="propertyId" name="propertyId" value="${capabilityPropertyValue.propertyId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">属性值: </th>
					<td><input type="text" id="propertyValue" name="propertyValue" value="${capabilityPropertyValue.propertyValue}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">属性名称: </th>
					<td><input type="text" id="propertyName" name="propertyName" value="${capabilityPropertyValue.propertyName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${capabilityPropertyValue.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
