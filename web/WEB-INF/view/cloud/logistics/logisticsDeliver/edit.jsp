<%--
	time:2013-05-16 15:35:33
	desc:edit the CLOUD_LOGISTICS_DELIVER
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 CLOUD_LOGISTICS_DELIVER</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#logisticsDeliverForm').form();
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
						window.location.href = "${ctx}/cloud/logistics/logisticsDeliver/list.ht";
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
			    <c:when test="${logisticsDeliver.id !=null}">
			        <span class="tbar-label">编辑CLOUD_LOGISTICS_DELIVER</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加CLOUD_LOGISTICS_DELIVER</span>
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
		<form id="logisticsDeliverForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${logisticsDeliver.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${logisticsDeliver.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<%-- <tr>
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${logisticsDeliver.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">制单人名称: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${logisticsDeliver.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">配送企业ID: </th>
					<td><input type="text" id="deliverEnterpId" name="deliverEnterpId" value="${logisticsDeliver.deliverEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">配送企业名称: </th>
					<td><input type="text" id="deliverEnterpName" name="deliverEnterpName" value="${logisticsDeliver.deliverEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">配送人员ID: </th>
					<td><input type="text" id="delivererId" name="delivererId" value="${logisticsDeliver.delivererId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">配送人员名称: </th>
					<td><input type="text" id="delivererName" name="delivererName" value="${logisticsDeliver.delivererName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">配送时间: </th>
					<td><input type="text" id="deliverTime" name="deliverTime" value="<fmt:formatDate value='${logisticsDeliver.deliverTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<%-- <tr>
					<th width="20%">发货企业ID: </th>
					<td><input type="text" id="postEnterpId" name="postEnterpId" value="${logisticsDeliver.postEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">发货企业名称: </th>
					<td><input type="text" id="postEnterpName" name="postEnterpName" value="${logisticsDeliver.postEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">发货企业联系人ID: </th>
					<td><input type="text" id="posterId" name="posterId" value="${logisticsDeliver.posterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">发货企业联系人名称: </th>
					<td><input type="text" id="posterName" name="posterName" value="${logisticsDeliver.posterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发货企业联系方式: </th>
					<td><input type="text" id="posterPhone" name="posterPhone" value="${logisticsDeliver.posterPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发货地址: </th>
					<td><input type="text" id="postAddress" name="postAddress" value="${logisticsDeliver.postAddress}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">收货企业ID: </th>
					<td><input type="text" id="receiveEnterpId" name="receiveEnterpId" value="${logisticsDeliver.receiveEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">收货企业名称: </th>
					<td><input type="text" id="receiveEnterpName" name="receiveEnterpName" value="${logisticsDeliver.receiveEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">收货企业联系人ID: </th>
					<td><input type="text" id="receiverId" name="receiverId" value="${logisticsDeliver.receiverId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
				<tr>
					<th width="20%">收货企业联系人名称: </th>
					<td><input type="text" id="receiverName" name="receiverName" value="${logisticsDeliver.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货企业联系方式: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone" value="${logisticsDeliver.receiverPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货地址: </th>
					<td><input type="text" id="receiveAddress" name="receiveAddress" value="${logisticsDeliver.receiveAddress}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<%-- <tr>
					<th width="20%">计划详单ID: </th>
					<td><input type="text" id="planDetailId" name="planDetailId" value="${logisticsDeliver.planDetailId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr> --%>
			</table>
			<input type="hidden" name="id" value="${logisticsDeliver.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
