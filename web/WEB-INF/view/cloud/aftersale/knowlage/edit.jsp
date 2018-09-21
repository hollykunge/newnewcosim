<%--
	time:2013-04-19 11:40:23
	desc:edit the cloud_service_knowlage
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_service_knowlage</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#knowLageForm').form();
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
						window.location.href = "${ctx}/cloud/aftersale/knowlage/list.ht";
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
			    <c:when test="${knowLage.id !=null}">
			        <span class="tbar-label">编辑cloud_service_knowlage</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_service_knowlage</span>
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
		<form id="knowLageForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">故障现象:  <span class="required">*</span></th>
					<td><input type="text" id="phenomenon" name="phenomenon" value="${knowLage.phenomenon}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">关键字:  <span class="required">*</span></th>
					<td><input type="text" id="keyword" name="keyword" value="${knowLage.keyword}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">故障分类:  <span class="required">*</span></th>
					<td><input type="text" id="type" name="type" value="${knowLage.type}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">解决措施:  <span class="required">*</span></th>
					<td><input type="text" id="measure" name="measure" value="${knowLage.measure}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${knowLage.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
