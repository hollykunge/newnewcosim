<%--
	time:2013-09-13 10:30:32
	desc:edit the cloud_sale_credit
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_sale_credit</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#saleCreditForm').form();
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
						window.location.href = "${ctx}/cloud/sale/saleCredit/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleCreditForm').attr('action','apply.ht');
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

			$('#saleCreditForm').attr("action","complete.ht");
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
			
			$('#saleCreditForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
			var dd;
	      	function selSups(){
	      		//弹出供应企业选择框
	      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriendsRadio.ht';
	      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 800, title :'企业选择器', name:"frameDialog_"});
	      	}
	      	//商圈列表回调函数
	      	function _callBackMyFriends(friend){
	      		$("#coopenterpriseId").val(friend[0]);
				$("#coopenterpriseName").val(friend[1]);
	      		dd.close();
			}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${saleCredit.id !=null}">
			        <span class="tbar-label">编辑合作企业信用额度</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加合作企业信用额度</span>
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
		<form id="saleCreditForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<input type="hidden" id="coopenterpriseId" name="coopenterpriseId" value="${saleCredit.coopenterpriseId}"  class="inputText" validate="{required:false,number:true }"  />
					<input type="hidden" id="enterpriseId" name="enterpriseId" value="${saleCredit.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  />
				</tr>
				<tr>
					<th width="20%">供应企业名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${saleCredit.enterpriseName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr><tr>
					<th width="20%">合作企业名称: </th>
					<td><input type="text" id="coopenterpriseName" name="coopenterpriseName" value="${saleCredit.coopenterpriseName}"  class="inputText" validate="{required:true,maxlength:384}"  />
					<a href="javascript:void(0)" onclick="selSups()"  class="link detail">选择</a>
					</td>
				</tr>
				<tr>
					<th width="20%">信用额度: </th>
					<td><input type="text" id="credit" name="credit" value="${saleCredit.credit}"  class="inputText" validate="{required:true,number:true}"  /></td>
				</tr>
				<!--
				<tr>
					<th width="20%">remark1: </th>
					<td><input type="text" id="remark1" name="remark1" value="${saleCredit.remark1}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">remark2: </th>
					<td><input type="text" id="remark2" name="remark2" value="${saleCredit.remark2}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr>
					<th width="20%">remark3: </th>
					<td><input type="text" id="remark3" name="remark3" value="${saleCredit.remark3}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				-->
			</table>
			<input type="hidden" name="id" value="${saleCredit.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
</div>
</body>
</html>
