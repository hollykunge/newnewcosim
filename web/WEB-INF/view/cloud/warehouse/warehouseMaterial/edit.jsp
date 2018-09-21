<%--
	time:2013-06-20 10:18:32
	desc:edit the 物品入库
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 物品入库</title>
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
			var frm=$('#warehouseMaterialForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseMaterial/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		
		
			function preview(){
			CommonDialog("warehouse_choose",
			function(data) {
				 
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				
				
			});
		}
			 
		
				function preview2(){
			CommonDialog("materialListOnly",
			function(data) {
				  
				$("#materialId").val(data.id);
				$("#materialName").val(data.name);
				
				
			});
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${warehouseMaterial.id !=null}">
			        <span class="tbar-label">编辑物品入库</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加物品入库</span>
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
		<form id="warehouseMaterialForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			 
			 
				 
			 		
				<tr>
					<th width="20%">仓库: </th>
					<td>
					<input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseMaterial.warehouseId}"  class="inputText"    />
					<input type="text" id="warehouseName" name="warehouseName" value="${warehouseMaterial.warehouseName}" readonly="readonly"  class="inputText"    />
					<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					
					</td>
				</tr>
				
				
				
					<tr>
					<th width="20%">物品: </th>
					<td>
					<input type="hidden" id="materialId" name="materialId" value="${warehouseMaterial.materialId}"  class="inputText"    />
					<input type="text"   id="materialName" name="materialName" value="${warehouseMaterial.materialName}" readonly="readonly"  class="inputText"    />
					<a href="javascript:void(0)" onclick="preview2()"  class="link detail">选择</a>
					
					</td>
				</tr>
				
				
				
			 
			</table>
			<input type="hidden" name="id" value="${warehouseMaterial.id}" />
			 
		</form>
		
	</div>
</div>
</body>
</html>
