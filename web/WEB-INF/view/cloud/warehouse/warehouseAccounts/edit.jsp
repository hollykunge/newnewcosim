<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_warehouse_accounts
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_warehouse_accounts</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#warehouseAccountsForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseAccounts/list.ht";
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
			    <c:when test="${warehouseAccounts.id !=null}">
			        <span class="tbar-label">编辑cloud_warehouse_accounts</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_warehouse_accounts</span>
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
		<form id="warehouseAccountsForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">公司ID: </th>
					<td><input type="text" id="companyid" name="companyid" value="${warehouseAccounts.companyid}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">批号/编号: </th>
					<td><input type="text" id="batchnumber" name="batchnumber" value="${warehouseAccounts.batchnumber}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">物品编号: </th>
					<td><input type="text" id="productcode" name="productcode" value="${warehouseAccounts.productcode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">物品名称: </th>
					<td><input type="text" id="productname" name="productname" value="${warehouseAccounts.productname}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">型号规格: </th>
					<td><input type="text" id="specifications" name="specifications" value="${warehouseAccounts.specifications}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">计量单位: </th>
					<td><input type="text" id="units" name="units" value="${warehouseAccounts.units}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">货架: </th>
					<td><input type="text" id="shelves" name="shelves" value="${warehouseAccounts.shelves}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">数量: </th>
					<td><input type="text" id="nums" name="nums" value="${warehouseAccounts.nums}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${warehouseAccounts.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
