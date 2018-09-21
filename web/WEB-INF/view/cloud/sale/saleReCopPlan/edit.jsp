<%--
	time:2013-04-26 14:32:25
	desc:edit the cloud_sale_recopplan
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑营销计划回复单</title>
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
			var frm=$('#saleReCopPlanForm').form();
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
						window.location.href = "${ctx}/cloud/saleReCopPlan/saleReCopPlan/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleReCopPlanForm').attr('action','apply.ht');
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

			$('#saleReCopPlanForm').attr("action","complete.ht");
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
			
			$('#saleReCopPlanForm').attr("action","complete.ht");
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
			    <c:when test="${saleReCopPlan.id !=null}">
			        <span class="tbar-label">编辑营销计划回复单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加营销计划回复单</span>
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
		<form id="saleReCopPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${saleReCopPlan.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleReCopPlan.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${saleReCopPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${saleReCopPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">sourceform_type: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${saleReCopPlan.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">sourceform_id: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${saleReCopPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">sourceform_code: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${saleReCopPlan.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布模式：公开/邀请: </th>
					<td><input type="text" id="deliveryType" name="deliveryType" value="${saleReCopPlan.deliveryType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">发布时间: </th>
					<td><input type="text" id="deliveryDate" name="deliveryDate" value="<fmt:formatDate value='${saleReCopPlan.deliveryDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">回复截止时间: </th>
					<td><input type="text" id="offerEndtime" name="offerEndtime" value="<fmt:formatDate value='${saleReCopPlan.offerEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">处理截止时间: </th>
					<td><input type="text" id="doofferEndtime" name="doofferEndtime" value="<fmt:formatDate value='${saleReCopPlan.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">合作营销区域: </th>
					<td><input type="text" id="cooperationArea" name="cooperationArea" value="${saleReCopPlan.cooperationArea}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作开始时间: </th>
					<td><input type="text" id="cooperationStarttime" name="cooperationStarttime" value="<fmt:formatDate value='${saleReCopPlan.cooperationStarttime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">合作截止时间: </th>
					<td><input type="text" id="cooperationEndtime" name="cooperationEndtime" value="<fmt:formatDate value='${saleReCopPlan.cooperationEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">上报库存信息时间间隔: </th>
					<td><input type="text" id="reportstockTimespace" name="reportstockTimespace" value="${saleReCopPlan.reportstockTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">上报销售订单时间间隔: </th>
					<td><input type="text" id="reportsalesTimespace" name="reportsalesTimespace" value="${saleReCopPlan.reportsalesTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">说明: </th>
					<td><input type="text" id="comments" name="comments" value="${saleReCopPlan.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${saleReCopPlan.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">企业: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${saleReCopPlan.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">企业联系人: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${saleReCopPlan.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">enterprise_userid: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${saleReCopPlan.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作类型：代理/经销/联营: </th>
					<td><input type="text" id="type" name="type" value="${saleReCopPlan.type}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">copenterprise_id: </th>
					<td><input type="text" id="copenterpriseId" name="copenterpriseId" value="${saleReCopPlan.copenterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">copenterprise_name: </th>
					<td><input type="text" id="copenterpriseName" name="copenterpriseName" value="${saleReCopPlan.copenterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">copenterprise_userid: </th>
					<td><input type="text" id="copenterpriseUserid" name="copenterpriseUserid" value="${saleReCopPlan.copenterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">合作企业联系人: </th>
					<td><input type="text" id="copenterpriseUsername" name="copenterpriseUsername" value="${saleReCopPlan.copenterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleReCopPlanDetail" formType="window" type="sub">
				<tr>
					<td colspan="13">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						营销计划回复单 :营销计划回复单详情
			    		</div>
		    		</td>
				</tr>
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
					<th>操作</th>
				</tr>
				<c:forEach items="${saleReCopPlanDetailList}" var="saleReCopPlanDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialId">${saleReCopPlanDetailItem.materialId}</td>
					    <td style="text-align: center" name="materialName">${saleReCopPlanDetailItem.materialName}</td>
					    <td style="text-align: center" name="materialCode">${saleReCopPlanDetailItem.materialCode}</td>
					    <td style="text-align: center" name="materialCodenote">${saleReCopPlanDetailItem.materialCodenote}</td>
					    <td style="text-align: center" name="materielLev">${saleReCopPlanDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${saleReCopPlanDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${saleReCopPlanDetailItem.unit}</td>
					    <td style="text-align: center" name="materielNum">${saleReCopPlanDetailItem.materielNum}</td>
					    <td style="text-align: center" name="firstNumber">${saleReCopPlanDetailItem.firstNumber}</td>
					    <td style="text-align: center" name="prise">${saleReCopPlanDetailItem.prise}</td>
					    <td style="text-align: center" name="priseRegion">${saleReCopPlanDetailItem.priseRegion}</td>
					    <td style="text-align: center" name="accountType">${saleReCopPlanDetailItem.accountType}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materialId" value="${saleReCopPlanDetailItem.materialId}"/>
						<input type="hidden" name="materialName" value="${saleReCopPlanDetailItem.materialName}"/>
						<input type="hidden" name="materialCode" value="${saleReCopPlanDetailItem.materialCode}"/>
						<input type="hidden" name="materialCodenote" value="${saleReCopPlanDetailItem.materialCodenote}"/>
						<input type="hidden" name="materielLev" value="${saleReCopPlanDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${saleReCopPlanDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${saleReCopPlanDetailItem.unit}"/>
						<input type="hidden" name="materielNum" value="${saleReCopPlanDetailItem.materielNum}"/>
						<input type="hidden" name="firstNumber" value="${saleReCopPlanDetailItem.firstNumber}"/>
						<input type="hidden" name="prise" value="${saleReCopPlanDetailItem.prise}"/>
						<input type="hidden" name="priseRegion" value="${saleReCopPlanDetailItem.priseRegion}"/>
						<input type="hidden" name="accountType" value="${saleReCopPlanDetailItem.accountType}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materialId"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center" name="materialCodenote"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="materielNum"></td>
			    	<td style="text-align: center" name="firstNumber"></td>
			    	<td style="text-align: center" name="prise"></td>
			    	<td style="text-align: center" name="priseRegion"></td>
			    	<td style="text-align: center" name="accountType"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materialId" value=""/>
			    	<input type="hidden" name="materialName" value=""/>
			    	<input type="hidden" name="materialCode" value=""/>
			    	<input type="hidden" name="materialCodenote" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="materielNum" value=""/>
			    	<input type="hidden" name="firstNumber" value=""/>
			    	<input type="hidden" name="prise" value=""/>
			    	<input type="hidden" name="priseRegion" value=""/>
			    	<input type="hidden" name="accountType" value=""/>
		 		</tr>
		    </table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleReCopPlanQualification" formType="window" type="sub">
				<tr>
					<td colspan="4">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						营销计划回复单 :营销计划回复单_企业资质
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>资质</th>
					<th>资质附件</th>
					<th>资质说明</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${saleReCopPlanQualificationList}" var="saleReCopPlanQualificationItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="qualificationName">${saleReCopPlanQualificationItem.qualificationName}</td>
					    <td style="text-align: center" name="qualificationAttachment">${saleReCopPlanQualificationItem.qualificationAttachment}</td>
					    <td style="text-align: center" name="qualificationNote">${saleReCopPlanQualificationItem.qualificationNote}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="qualificationName" value="${saleReCopPlanQualificationItem.qualificationName}"/>
						<input type="hidden" name="qualificationAttachment" value="${saleReCopPlanQualificationItem.qualificationAttachment}"/>
						<input type="hidden" name="qualificationNote" value="${saleReCopPlanQualificationItem.qualificationNote}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="qualificationName"></td>
			    	<td style="text-align: center" name="qualificationAttachment"></td>
			    	<td style="text-align: center" name="qualificationNote"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="qualificationName" value=""/>
			    	<input type="hidden" name="qualificationAttachment" value=""/>
			    	<input type="hidden" name="qualificationNote" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${saleReCopPlan.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="saleReCopPlanDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materialId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品名: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码参考附件: </th>
				<td><input type="text" name="materialCodenote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">合作销售数量: </th>
				<td><input type="text" name="materielNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">首批进货量: </th>
				<td><input type="text" name="firstNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">合作单价: </th>
				<td><input type="text" name="prise" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">价格区间（+-%）: </th>
				<td><input type="text" name="priseRegion" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">结算方式（返点/返利）: </th>
				<td><input type="text" name="accountType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		</table>
	</form>
	<form id="saleReCopPlanQualificationForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">资质: </th>
				<td><input type="text" name="qualificationName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">资质附件: </th>
				<td><input type="text" name="qualificationAttachment" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">资质说明: </th>
				<td><input type="text" name="qualificationNote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
