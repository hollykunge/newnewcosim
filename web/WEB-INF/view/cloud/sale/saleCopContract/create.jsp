<%--
	time:2013-04-26 14:32:25
	desc:edit the cloud_sale_copcontract
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 营销合作合同</title>
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
			var frm=$('#saleCopContractForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/sale/saleCopContract/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleCopContractForm').attr('action','apply.ht');
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

			$('#saleCopContractForm').attr("action","complete.ht");
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
			
			$('#saleCopContractForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		function selectedProvider(reCopPlanId){
			$.ajax({
				   type: "POST",
				   url:  "${ctx}/cloud/sale/saleCopContract/selectedProvider.ht",
				   data: "reCopPlanId=" + reCopPlanId,
				   success: function(msg){
				    	$('#coopenterpName').val(msg.coopenterpName);
				    	$('#coopenterpId').val(msg.coopenterpId);
				    	$('#coopenterpUserid').val(msg.coopenterpUserid);
				    	$('#coopenterpUsername').val(msg.coopenterpUsername);
				    	$('#reCopPlanId').val(msg.reCopPlanId);
				    	$('#reCopPlanCode').val(msg.reCopPlanCode);
				   // 	alert(msg.saleCopContractDetailList);
				    	for(var i=0;i < msg.saleCopContractDetailList.length; i++){
				    		var row = msg.saleCopContractDetailList[i];
				    	}
				    	

				   },
		 		   error:function(){
				   alert("error");
				   }
				});
			
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${saleCopContract.id !=null}">
			        <span class="tbar-label">编辑营销合作合同</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加营销合作合同</span>
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
		<form id="saleCopContractForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">单证号: </th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${saleCopContract.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleCopContract.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">合作类型: </th>
					<td><input size="35" type="text" id="copType" name="copType" value="${saleCopContract.copType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					
				</tr>
				<tr>
					<th style="width:120px">库存信息: </th>
					<td>
					<c:if test='${saleCopContract.sharedRepository==1}'>
						共享
						<input size="35" type="hidden" id="sharedRepository" name="sharedRepository" value="1"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					<c:if test='${saleCopContract.sharedRepository==0}'>
					            不共享
						<input size="35" type="hidden" id="sharedRepository" name="sharedRepository" value="0"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					</td>
					
					<th style="width:120px">销售信息: </th>
					<td>
					<c:if test='${saleCopContract.saleRepository==1}'>	
					共享
				 		<input size="35" type="hidden" id="saleRepository" name="saleRepository" value="1"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					<c:if test='${saleCopContract.saleRepository==0}'>	
					  不共享
				 		<input size="35" type="hidden" id="saleRepository" name="saleRepository" value="0"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					</td>
				</tr>
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="operaterName" name="operaterName" value="${saleCopContract.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">合作营销区域: </th>
					<td>	
				 <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					</select>
				</td>
				</tr>
				<tr>
					<th style="width:120px">合作开始时间: </th>
					<td><input size="35" type="text" readonly="readonly" id="cooperationStarttime" name="cooperationStarttime" value="<fmt:formatDate value='${saleCopContract.cooperationStarttime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">合作截止时间: </th>
					<td><input size="35" type="text" readonly="readonly" id="cooperationEndtime" name="cooperationEndtime" value="<fmt:formatDate value='${saleCopContract.cooperationEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">库存上报时间间隔: </th>
					<td><input size="35" type="text" readonly="readonly" id="reportstockTimespace" name="reportstockTimespace" value="${saleCopContract.reportstockTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">销售订单上报时间间隔: </th>
					<td><input size="35" type="text" readonly="readonly" id="reportsalesTimespace" name="reportsalesTimespace" value="${saleCopContract.reportsalesTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">信用额度下限(金额）: </th>
					<td><input size="35" type="text" id="creditEndline" name="creditEndline" value="${saleCopContract.creditEndline}"  class="inputText" validate="{required:false}"  /></td>
					<th style="width:120px">附件: </th>
				<!--<td>	<input type="file" name="attachments" id="attachments"> </td>-->
				<td><input size="35"  type="text" id="attachment" name="attachment" value="${saleCopContract.attachment}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>  	
				</tr>
				<tr>
					<th style="width:120px">企业ID: </th>
					<td><input size="35" type="text" id="enterpriseId" name="enterpriseId" value="${saleCopContract.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">企业: </th>
					<td><input size="35" type="text" id="enterpriseName" name="enterpriseName" value="${saleCopContract.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">企业联系人ID: </th>
					<td><input size="35" type="text" id="enterpriseUserid" name="enterpriseUserid" value="${saleCopContract.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">企业联系人: </th>
					<td><input size="35" type="text" id="enterpriseUsername" name="enterpriseUsername" value="${saleCopContract.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">合作企业ID: </th>
					<td><input size="35" type="text" id="coopenterpId" name="coopenterpId" value="${saleCopContract.coopenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">合作企业: </th>
					<td><input size="35" type="text" id="coopenterpName" name="coopenterpName" value="${saleCopContract.coopenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">合作企业联系人ID: </th>
					<td><input size="35" type="text" id="coopenterpUserid" name="coopenterpUserid" value="${saleCopContract.coopenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">合作企业联系人: </th>
					<td><input size="35" type="text" id="coopenterpUsername" name="coopenterpUsername" value="${saleCopContract.coopenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<!--<th style="width:120px">制单人ID: </th>-->
					<input size="35" type="hidden" id="operaterId" name="operaterId" value="${saleCopContract.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
				</tr>	
				
				<tr>
					<th style="width:120px">中标回复单ID: </th>
					<td><input size="35" type="text" id="reCopPlanId" name="reCopPlanId" value="${saleCopContract.reCopPlanId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">中标回复单Code: </th>
					<td><input size="35" type="text" id="reCopPlanCode" name="reCopPlanCode" value="${saleCopContract.reCopPlanCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
			</table>
	

			<input size="35" type="hidden" name="id" value="${saleCopContract.id}" />
			<input size="35" type="hidden" id="back" name="back" value=""/>
			<input size="35" type="hidden" name="formData" id="formData" />
			<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
	

		
		<fieldset>
			<legend>计划详情</legend>
	
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
				<tr>
					<th style="width:120px">单证号:</th>
					<td colspan="3">${saleCopPlan.code}</td>
				</tr>
	 
				<tr>
					<th style="width:120px">制单人:</th>
					<td>${saleCopPlan.operaterName}</td>
					<th style="width:120px">制单日期:</th>
					<td>
					<fmt:formatDate value="${saleCopPlan.operateDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
	 
				<tr>
					<th style="width:120px">合作营销区域:</th>
					<td>	
				     <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					</select>
				</td>
					<th style="width:120px">发布时间:</th>
					<td>
					<fmt:formatDate value="${saleCopPlan.deliveryDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
	 
				<tr>
					<th style="width:120px">合作开始时间:</th>
					<td>
					<fmt:formatDate value="${saleCopPlan.cooperationStarttime}" pattern="yyyy-MM-dd"/>
					</td>
					<th style="width:120px">合作截止时间:</th>
					<td>
					<fmt:formatDate value="${saleCopPlan.cooperationEndtime}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<th style="width:120px">合作类型：</th>
						<td>${saleCopPlan.type}</td>
				</tr>
		</table>
	  </fieldset>
		
		<fieldset>
			<legend>合作伙伴回复明细</legend>
			<c:forEach items="${saleReCopPlanList}"  var="saleReCopPlan">
				设置该价格为中标价格:
				<input size="35" type="radio"  name="isCheck" value="${saleReCopPlan.id}" onclick="selectedProvider(${saleReCopPlan.id})" validate="{required:true}" />
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">		 
					<tr>
						<th style="width:120px">单证号:</th>
						<td colspan="3">${saleReCopPlan.code}</td>
					</tr>
		 
					
					<tr>
						<th style="width:120px">制单人:</th>
						<td>${saleReCopPlan.operaterName}</td>
						<th style="width:120px">制单日期:</th>
						<td>
						<fmt:formatDate value="${saleReCopPlan.operateDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<th style="width:120px">上报库存信息时间间隔:</th>
						<td>${saleReCopPlan.reportstockTimespace}</td>
						<th style="width:120px">上报销售订单时间间隔:</th>
						<td>${saleReCopPlan.reportsalesTimespace}</td>
					</tr>
		 
					<tr>
						<th style="width:120px">说明:</th>
						<td>${saleReCopPlan.comments}</td>
						<th style="width:120px">企业:</th>
						<td>${saleReCopPlan.enterpriseName}
						<a href="" onclick="jQuery.openFullWindow('${ctx}/cloud/warehouse/warehouseMaterialMapping/mappingSetting.ht')" class="link detail">物品绑定</a>
						</td>
					</tr>
 
				</table>
				<table class="table-grid table-list" cellpadding="1" cellspacing="1">
					<tr><td colspan="12" style="text-align: center">营销合同详情</td></tr>
					<tr>
						<th>物品ID</th>
						<th>物品名</th>
						<th>物品编码</th>
						<th>编码参考附件</th>
						<th>分类</th>
						<th>属性说明</th>
						<th>单位</th>
						<th>合作销售数量</th>
						<th>首批进货量</th>
						<th>合作单价</th>
						<th>价格区间（+-%）</th>
						<th>结算方式（返点/返利）</th>
					</tr>	
					<c:forEach items="${saleReCopPlan.saleReCopPlanDetailList}" var="saleReCopPlanDetailItem" varStatus="status">
						<tr>
								<input size="35" type="hidden" id="id" name="id" value="${saleReCopPlanDetailItem.id}"  class="inputText"/>
								<td style="text-align: center">${saleReCopPlanDetailItem.materialId}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.materialName}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.materialCode}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.materialCodenote}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.materielLev}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.attributeInfo}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.unit}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.materielNum}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.firstNumber}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.prise}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.priseRegion}</td>								
								<td style="text-align: center">${saleReCopPlanDetailItem.accountType}</td>								
						</tr>
					</c:forEach>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1">
				<tr><td colspan="3" style="text-align: center">资质详情</td></tr>
				<tr>
					<th>资质</th>
					<th>资质附件</th>
					<th>资质说明</th>
				</tr>	
				<c:forEach items="${saleReCopPlan.saleReCopPlanQualificationList}" var="saleReCopPlanQualificationItem" varStatus="status">
					<tr>
							<input size="35" type="hidden" id="id" name="id" value="${saleReCopPlanQualificationItem.id}"  class="inputText"/>
							<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationName}</td>								
							<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationAttachment}</td>								
							<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationNote}</td>								
					</tr>
				</c:forEach>
			</table>
		</c:forEach>	
	</fieldset>
				
		
	</form>
</div>
</body>
</html>
