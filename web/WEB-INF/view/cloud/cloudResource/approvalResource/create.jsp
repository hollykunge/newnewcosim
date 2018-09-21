<%--
	time:2013-06-27 10:27:37
	desc:edit the cloud_resource_approval
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>申请使用资源</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript">
		/* $(function() {
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
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/approvalResource/approvalResource/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
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
			alert("已成功申请资源的使用权限，再次进入后即可使用");
			window.top.location.href='${ctx}/cloud/cloudResource/resourceManagement/resource.ht'; 		
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#approvalResourceForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.agree").click(function() {
			operatorType=1;
			var tmp = '1';
			/*if(isDirectComlete){//直接一票通过
				tmp = '5';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");

			$('#approvalResourceForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.notAgree").click(function() {
			
			operatorType=2;
			var tmp = '2';
			/*if(isDirectComlete){//直接一票通过
				tmp = '6';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");
			
			$('#approvalResourceForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
	</script>
</head>
<body>

<div class="panel" style="width: 1030px; margin: 0px auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${approvalResource.id !=null}">
			        <span class="tbar-label">申请使用资源</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">申请使用资源</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<br/>
			<h3>目前平台资源开放使用中，如需使用请提交申请</h3>
		<br/>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">申请</a></div>
				<div class="l-bar-separator"></div>
				<!-- <div class="group"><a class="link back" href="list.ht">返回</a></div> -->
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="approvalResourceForm" method="post" action="${ctx }/cloud/cloudResource/approvalResource/save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr style="display: none;">
					<th width="20%">企业ID: </th>
					<td><input type="text" readonly="readonly" id="enpId" name="enpId" value="${approvalResource.enpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr  style="display: none;">
					<th width="20%">人员ID: </th>
					<td><input type="text" readonly="readonly"  id="userId" name="userId" value="${approvalResource.userId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr  style="display: none;">
					<th width="20%">资源ID: </th>
					<td><input type="text" readonly="readonly"  id="resId" name="resId" value="${approvalResource.resId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">资源名: </th>
					<td><input type="text" readonly="readonly"  id="resName" name="resName" value="${approvalResource.resName}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
				</tr>
				<tr>
					<th width="20%">申请日期: </th>
					<td><input type="text" readonly="readonly"  id="applyDate" name="applyDate" value="<fmt:formatDate value='${approvalResource.applyDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" readonly="readonly"/></td>
				</tr>
				<tr  style="display: none;">
					<th width="20%">审批日期: </th>
					<td><input type="text"  readonly="readonly" id="approvalData" name="approvalData" value="<fmt:formatDate value='${approvalResource.approvalData}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr  style="display: none;">
					<th width="20%">状态: </th>
					<td><input type="text"  readonly="readonly" id="state" name="state" value="1"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${approvalResource.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
