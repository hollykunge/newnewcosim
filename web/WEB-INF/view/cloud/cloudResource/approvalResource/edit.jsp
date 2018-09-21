<%--
	time:2013-06-27 10:27:37
	desc:edit the cloud_resource_approval
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_resource_approval</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#approvalResourceForm').form();
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
			/* 	$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "list.ht";
					} 
				});*/
					alert("已审核用户的资源使用申请");
			window.location.href='list.ht'; 		
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
			    <c:when test="${approvalResource.id !=null}">
			        <span class="tbar-label">编辑cloud_resource_approval</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_resource_approval</span>
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
		<form id="approvalResourceForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">企业ID: </th>
					<td><input type="text"  readonly="readonly" id="enpId" name="enpId" value="${approvalResource.enpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">人员ID: </th>
					<td><input type="text"  readonly="readonly" id="userId" name="userId" value="${approvalResource.userId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">资源ID: </th>
					<td><input type="text"  readonly="readonly" id="resId" name="resId" value="${approvalResource.resId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">资源名: </th>
					<td><input type="text"  readonly="readonly" id="resName" name="resName" value="${approvalResource.resName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">申请日期: </th>
					<td><input type="text"  readonly="readonly" id="applyDate" name="applyDate" value="<fmt:formatDate value='${approvalResource.applyDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">审批日期: </th>
					<td><input type="text" readonly="readonly"  id="approvalData" name="approvalData" value="<fmt:formatDate value='${approvalResource.approvalData}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">状态: </th>
					<td>
<%-- 						<input type="text" id="state" name="state" value="${approvalResource.state}"  class="inputText" validate="{required:false,number:true }"  />
 --%>						同意：<input type="radio" name="state" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不同意：<input type="radio" name="state" value="0" checked="checked"/>
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${approvalResource.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
