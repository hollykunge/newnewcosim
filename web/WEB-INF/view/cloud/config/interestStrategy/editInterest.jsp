<%--
	time:2013-09-24 17:29:56
	desc:edit the cloud_interest_strategy
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_interest_strategy</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#interestStrategyForm').form();
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
						window.location.href = "${ctx}/cloud/config/interestStrategy/listInterest.ht?currentEntid=${interestStrategy.enterpriseId}&materialId=${interestStrategy.materialId}";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#interestStrategyForm').attr('action','apply.ht');
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

			$('#interestStrategyForm').attr("action","complete.ht");
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
			
			$('#interestStrategyForm').attr("action","complete.ht");
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
			    <c:when test="${interestStrategy.id !=null}">
			        <span class="tbar-label">编辑cloud_interest_strategy</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_interest_strategy</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="listInterest.ht?currentEntid=${interestStrategy.enterpriseId}&materialId=${interestStrategy.materialId}">返回</a></div>
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
		<form id="interestStrategyForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr  style="display: none;">
					<th width="20%">供应商id: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${interestStrategy.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">供应商名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${interestStrategy.enterpriseName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">经销商id: </th>
					<td><input type="text" id="coopenterpId" name="coopenterpId" value="${interestStrategy.coopenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">经销商名称: </th>
					<td><input type="text" id="coopenterpName" name="coopenterpName" value="${interestStrategy.coopenterpName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">物品id: </th>
					<td><input type="text" id="materialId" name="materialId" value="${interestStrategy.materialId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">物品名称: </th>
					<td><input type="text" id="materialName" name="materialName" value="${interestStrategy.materialName}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="interestStrategyDetail" formType="window" type="sub">
				<tr>
					<td colspan="4">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						返利数量区间设定
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>下限</th>
					<th>上限</th>
					<th>返利值</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${interestStrategyDetailList}" var="interestStrategyDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="lowerNumber">${interestStrategyDetailItem.lowerNumber}</td>
					    <td style="text-align: center" name="capsNumber">${interestStrategyDetailItem.capsNumber}</td>
					    <td style="text-align: center" name="interestValue">${interestStrategyDetailItem.interestValue}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
					    <input type="hidden" name="lowerNumber" value="${interestStrategyDetailItem.lowerNumber}"/>
						<input type="hidden" name="capsNumber" value="${interestStrategyDetailItem.capsNumber}"/>
						<input type="hidden" name="interestValue" value="${interestStrategyDetailItem.interestValue}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
					<td style="text-align: center" name="lowerNumber"></td>
			    	<td style="text-align: center" name="capsNumber"></td>
			    	<td style="text-align: center" name="interestValue"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="lowerNumber" value=""/>
			    	<input type="hidden" name="capsNumber" value=""/>
			    	<input type="hidden" name="interestValue" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${interestStrategy.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="interestStrategyDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">下限: </th>
				<td><input type="text" name="lowerNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">上限: </th>
				<td><input type="text" name="capsNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">返利值: </th>
				<td><input type="text" name="interestValue" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
