<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_warehouse_archive
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_warehouse_archive</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#warehouseArchiveForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseArchive/list.ht";
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
			    <c:when test="${warehouseArchive.id !=null}">
			        <span class="tbar-label">编辑仓库档案信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加仓库档案信息</span>
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
		<form id="warehouseArchiveForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">仓库名称: </th>
					<td><input type="text" id="warehousename" name="warehousename" value="${warehouseArchive.warehousename}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<!--
				<tr>
					<th width="20%">联系人: </th>
					<td><input type="text" id="contactid" name="contactid" value="${warehouseArchive.contactid}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				-->
				<tr>
					<th width="20%">联系人名称: </th>
					<td><input type="text" id="contactname" name="contactname" value="${warehouseArchive.contactname}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">联系人座机: </th>
					<td><input type="text" id="contactlandlilne" name="contactlandlilne" value="${warehouseArchive.contactlandlilne}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">联系人手机: </th>
					<td><input type="text" id="contactphone" name="contactphone" value="${warehouseArchive.contactphone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">联系人邮箱: </th>
					<td><input type="text" id="contactemail" name="contactemail" value="${warehouseArchive.contactemail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">仓库地址: </th>
					<td><input type="text" id="address" name="address" value="${warehouseArchive.address}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">邮编: </th>
					<td><input type="text" id="zipcode" name="zipcode" value="${warehouseArchive.zipcode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatename" name="operatename" value="${warehouseArchive.operatename}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operatedate" name="operatedate" value="<fmt:formatDate value='${warehouseArchive.operatedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">备注: </th>
					<td>
					
					<textarea rows="3" cols="35" id="remark" name="remark">${warehouseArchive.remark}</textarea>
					</td>
				</tr>
				<!-- 所属企业 -->
				<input type="hidden" id="companyid" name="companyid" value="${warehouseArchive.companyid}"  class="inputText" validate="{required:false,number:true }"  />
				<!-- 制单人id-->
				<input type="hidden" id="operateid" name="operateid" value="${warehouseArchive.operateid}"  class="inputText" validate="{required:false,maxlength:96}"  />
			</table>
			<input type="hidden" name="id" value="${warehouseArchive.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
