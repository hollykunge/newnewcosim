<%--
	time:2013-04-16 15:38:14
	desc:edit the CLOUD_MATERIAL
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#materialForm').form();
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
						window.location.href = "${ctx}/cloud/material/material/list.ht";
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
			    <c:when test="${material.id !=null}">
			        <span class="tbar-label">编辑</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加</span>
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
		<form id="materialForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					${levl1}-${levl2}-${levl3}-${levl4}
					<th width="20%">物品名称:  <span class="required">*</span></th>
					<td><input type="text" id="name" name="name" value="${material.name}"  class="inputText" validate="{required:true,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">物品规格: </th>
					<td><input type="text" id="model" name="model" value="${material.model}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">levl_type: </th>
					<td><input type="text" id="levlType" name="levlType" value="${material.levlType}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">levl_seq: </th>
					<td><input type="text" id="levlSeq" name="levlSeq" value="${material.levlSeq}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">ent_id: </th>
					<td><input type="text" id="entId" name="entId" value="${material.entId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">info: </th>
					<td><input type="text" id="info" name="info" value="${material.info}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">isinner: </th>
					<td><input type="text" id="isinner" name="isinner" value="${material.isinner}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">pic: </th>
					<td><input type="text" id="pic" name="pic" value="${material.pic}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">publisher: </th>
					<td><input type="text" id="publisher" name="publisher" value="${material.publisher}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">publishdate: </th>
					<td><input type="text" id="publishdate" name="publishdate" value="<fmt:formatDate value='${material.publishdate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">unit: </th>
					<td><input type="text" id="unit" name="unit" value="${material.unit}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">price: </th>
					<td><input type="text" id="price" name="price" value="${material.price}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">minsale: </th>
					<td><input type="text" id="minsale" name="minsale" value="${material.minsale}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">maxsale: </th>
					<td><input type="text" id="maxsale" name="maxsale" value="${material.maxsale}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">catalog_id: </th>
					<td><input type="text" id="catalogId" name="catalogId" value="${material.catalogId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">code: </th>
					<td><input type="text" id="code" name="code" value="${material.code}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">industry_code: </th>
					<td><input type="text" id="industryCode" name="industryCode" value="${material.industryCode}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">industry_file: </th>
					<td><input type="text" id="industryFile" name="industryFile" value="${material.industryFile}"  class="inputText" validate="{required:false,maxlength:600}"  /></td>
				</tr>
				<tr>
					<th width="20%">publish_state: </th>
					<td><input type="text" id="publishState" name="publishState" value="${material.publishState}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${material.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
