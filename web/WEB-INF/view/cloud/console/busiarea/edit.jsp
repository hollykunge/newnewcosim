<%--
	time:2013-04-19 13:31:05
	desc:edit the CLOUD_BUSINESS_AREA
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 CLOUD_BUSINESS_AREA</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#busiareaForm').form();
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
						window.location.href = "${ctx}/cloud/busiarea/busiarea/list.ht";
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
			    <c:when test="${busiarea.id !=null}">
			        <span class="tbar-label">编辑CLOUD_BUSINESS_AREA</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加CLOUD_BUSINESS_AREA</span>
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
		<form id="busiareaForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">role_id: </th>
					<td><input type="text" id="roleId" name="roleId" value="${busiarea.roleId}"  class="inputText" validate="{required:false,maxlength:60}"  /></td>
				</tr>
				<tr>
					<th width="20%">main_ent: </th>
					<td><input type="text" id="mainEnt" name="mainEnt" value="${busiarea.mainEnt}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">corp_ent: </th>
					<td><input type="text" id="corpEnt" name="corpEnt" value="${busiarea.corpEnt}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">state: </th>
					<td><input type="text" id="state" name="state" value="${busiarea.state}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${busiarea.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
