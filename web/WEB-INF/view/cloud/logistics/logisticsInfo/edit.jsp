<%--
	time:2013-05-16 15:35:33
	desc:edit the CLOUD_LOGISTICS_INFO
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 CLOUD_LOGISTICS_INFO</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#logisticsInfoForm').form();
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
						window.location.href = "${ctx}/cloud/logisticsInfo/logisticsInfo/list.ht";
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
			    <c:when test="${logisticsInfo.id !=null}">
			        <span class="tbar-label">编辑CLOUD_LOGISTICS_INFO</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加CLOUD_LOGISTICS_INFO</span>
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
		<form id="logisticsInfoForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${logisticsInfo.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">生成日期: </th>
					<td><input type="text" id="createDate" name="createDate" value="<fmt:formatDate value='${logisticsInfo.createDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceId" name="sourceId" value="${logisticsInfo.sourceId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">物流动作时间: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${logisticsInfo.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">物流动作类型: </th>
					<td><input type="text" id="operateType" name="operateType" value="${logisticsInfo.operateType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">物流动态信息: </th>
					<td><input type="text" id="operateInfo" name="operateInfo" value="${logisticsInfo.operateInfo}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${logisticsInfo.id}" />
		</form>
		
	</div>
</div>
</body>
</html>
