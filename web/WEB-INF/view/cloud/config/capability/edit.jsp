<%--
	time:2013-04-16 17:23:24
	desc:edit the 能力
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 能力</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#capabilityForm').form();
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
						window.location.href = "${ctx}/cloud/config/capability/list.ht";
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
			    <c:when test="${capability.id !=null}">
			        <span class="tbar-label">编辑能力</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加能力</span>
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
		<form id="capabilityForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">能力名称: </th>
					<td><input type="text" id="name" name="name" value="${capability.name}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">能力分类: </th>
					<td><input type="text" id="typeId" name="typeId" value="${capability.typeId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">层级: </th>
					<td><input type="text" id="currentLevl" name="currentLevl" value="${capability.currentLevl}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">所属企业: </th>
					<td><input type="text" id="entId" name="entId" value="${capability.entId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">能力详情: </th>
					<td><input type="text" id="info" name="info" value="${capability.info}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">能力图片: </th>
					<td><input type="text" id="pic" name="pic" value="${capability.pic}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布人: </th>
					<td><input type="text" id="publisher" name="publisher" value="${capability.publisher}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布时间: </th>
					<td><input type="text" id="publishDate" name="publishDate" value="<fmt:formatDate value='${capability.publishDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">适用范围: </th>
					<td><input type="text" id="useScope" name="useScope" value="${capability.useScope}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布状态: </th>
					<td><input type="text" id="publishState" name="publishState" value="${capability.publishState}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">关键词: </th>
					<td><input type="text" id="prokey" name="prokey" value="${capability.prokey}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">type_name: </th>
					<td><input type="text" id="typeName" name="typeName" value="${capability.typeName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">ent_name: </th>
					<td><input type="text" id="entName" name="entName" value="${capability.entName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${capability.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
