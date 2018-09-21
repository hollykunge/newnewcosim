<%--
	time:2013-09-11 16:18:27
	desc:edit the cloud_price_strategy
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_price_strategy</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#priceStrategyForm').form();
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
						window.location.href = "${ctx}/cloud/priceStrategy/priceStrategy/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#priceStrategyForm').attr('action','apply.ht');
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

			$('#priceStrategyForm').attr("action","complete.ht");
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
			
			$('#priceStrategyForm').attr("action","complete.ht");
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
			    <c:when test="${priceStrategy.id !=null}">
			        <span class="tbar-label">编辑cloud_price_strategy</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_price_strategy</span>
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
		<form id="priceStrategyForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">供应商id: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${priceStrategy.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">供应商名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${priceStrategy.enterpriseName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">经销商id: </th>
					<td><input type="text" id="coopenterpId" name="coopenterpId" value="${priceStrategy.coopenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">经销商名称: </th>
					<td><input type="text" id="coopenterpName" name="coopenterpName" value="${priceStrategy.coopenterpName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">物品id: </th>
					<td><input type="text" id="materialId" name="materialId" value="${priceStrategy.materialId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">物品名称: </th>
					<td><input type="text" id="materialName" name="materialName" value="${priceStrategy.materialName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">数量: </th>
					<td><input type="text" id="number" name="number" value="${priceStrategy.number}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">季节: </th>
					<td><input type="text" id="season" name="season" value="${priceStrategy.season}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">折扣: </th>
					<td><input type="text" id="discount" name="discount" value="${priceStrategy.discount}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${priceStrategy.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
</div>
</body>
</html>
