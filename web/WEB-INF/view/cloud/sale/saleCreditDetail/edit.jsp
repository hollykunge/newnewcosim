<%--
	time:2013-09-13 10:32:40
	desc:edit the cloud_sale_credit_detail
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_sale_credit_detail</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#saleCreditDetailForm').form();
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
						window.location.href = "${ctx}/cloud/saleCreditDetail/saleCreditDetail/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleCreditDetailForm').attr('action','apply.ht');
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

			$('#saleCreditDetailForm').attr("action","complete.ht");
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
			
			$('#saleCreditDetailForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${saleCreditDetail.id !=null}">
			        <span class="tbar-label">编辑cloud_sale_credit_detail</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_sale_credit_detail</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${applyFlag==0}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
				<c:if test="${applyFlag==1}">
					<div class="group"><a id="btnAgree" class="link agree">同意</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="saleCreditDetailForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${saleCreditDetail.code}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${saleCreditDetail.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${saleCreditDetail.enterpriseName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业ID: </th>
					<td><input type="text" id="coopenterpriseId" name="coopenterpriseId" value="${saleCreditDetail.coopenterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业名称: </th>
					<td><input type="text" id="coopenterpriseName" name="coopenterpriseName" value="${saleCreditDetail.coopenterpriseName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">总金额: </th>
					<td><input type="text" id="totleMoney" name="totleMoney" value="${saleCreditDetail.totleMoney}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">id: </th>
					<td><input type="text" id="id" name="id" value="${saleCreditDetail.id}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">remark2: </th>
					<td><input type="text" id="remark2" name="remark2" value="${saleCreditDetail.remark2}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">remark3: </th>
					<td><input type="text" id="remark3" name="remark3" value="${saleCreditDetail.remark3}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${saleCreditDetail.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
</div>
</body>
</html>
