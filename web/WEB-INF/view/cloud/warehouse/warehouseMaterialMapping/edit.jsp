<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_material_mapping
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_material_mapping</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#warehouseMaterialMappingForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseMaterialMapping/list.ht";
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
			    <c:when test="${warehouseMaterialMapping.id !=null}">
			        <span class="tbar-label">编辑cloud_material_mapping</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_material_mapping</span>
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
		<form id="warehouseMaterialMappingForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">源企业ID: </th>
					<td><input type="text" id="srcEntId" name="srcEntId" value="${warehouseMaterialMapping.srcEntId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">源企业名称: </th>
					<td><input type="text" id="srcEntName" name="srcEntName" value="${warehouseMaterialMapping.srcEntName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">源物品ID: </th>
					<td><input type="text" id="srcProdId" name="srcProdId" value="${warehouseMaterialMapping.srcProdId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">源物品编码: </th>
					<td><input type="text" id="srcProdCode" name="srcProdCode" value="${warehouseMaterialMapping.srcProdCode}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">源物品名称: </th>
					<td><input type="text" id="srcProdName" name="srcProdName" value="${warehouseMaterialMapping.srcProdName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">目标企业ID: </th>
					<td><input type="text" id="tgtEntId" name="tgtEntId" value="${warehouseMaterialMapping.tgtEntId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">目标企业名称: </th>
					<td><input type="text" id="tgtEntName" name="tgtEntName" value="${warehouseMaterialMapping.tgtEntName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">目标物品ID: </th>
					<td><input type="text" id="tgtProdId" name="tgtProdId" value="${warehouseMaterialMapping.tgtProdId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">目标物品编码: </th>
					<td><input type="text" id="tgtProdCode" name="tgtProdCode" value="${warehouseMaterialMapping.tgtProdCode}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">目标物品名称: </th>
					<td><input type="text" id="tgtProdName" name="tgtProdName" value="${warehouseMaterialMapping.tgtProdName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${warehouseMaterialMapping.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
