<%--
	time:2013-09-05 10:22:51
	desc:edit the cloud_account_info
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_account_info</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>

	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#AccountInfoForm').form();
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
						window.location.href = "${ctx}/cloud/account/AccountInfo/list.ht";
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
			    <c:when test="${AccountInfo.id !=null}">
			        <span class="tbar-label">编辑</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加银行账户信息</span>
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
		<form id="AccountInfoForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">开户行: </th>
					<td><input type="text" id="openBank" name="openBank" value="${AccountInfo.openBank}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">银行卡号: </th>
					<td><input type="text" id="bankCode" name="bankCode" value="${AccountInfo.bankCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">开户人: </th>
					<td><input type="text" id="accountOp" name="accountOp" value="${AccountInfo.accountOp}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">开户企业名称: </th>
					<td>
						<input type="text" id="enterName" name="enterName" value="${AccountInfo.enterName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<input type="hidden" id="enterId" name="enterId" value="${AccountInfo.enterId}"  class="inputText" validate="{required:false,number:true }"  />
					</td>
				</tr>
				
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${AccountInfo.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th width="20%">制单人: </th>
					<td>
						<input type="text" id="operaterName" name="operaterName" value="${AccountInfo.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<input type="hidden" id="operaterId" name="operaterId" value="${AccountInfo.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
					</td>
				</tr>
				
				
				
			</table>
			<input type="hidden" name="id" value="${AccountInfo.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
