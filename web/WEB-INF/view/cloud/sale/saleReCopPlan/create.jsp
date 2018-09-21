<%--
	time:2013-04-26 14:32:25
	desc:edit the cloud_sale_recopplan
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 营销计划回复单</title>
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
						window.location.href = "${ctx}/cloud/sale/saleReCopPlan/list.ht";
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
		
		
		function selectfile(){
			CommonDialog("sys_org_info_aptitude",
			function(data) {
				var row=data;
		
					$('input[name="qualificationAttachment"]').val(row.CATE_PIC);
					
					$('input[name="qualificationNote"]').val(row.CATE_TYPE);
				
					
				
			
			});
		}
	
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
					<th style="width:120px">单证号: </th>
					<td colspan="3"><input size="35" type="text"code" name="code" value="${saleReCopPlan.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人ID: </th>
					<td><input size="35" type="text" id="operaterId" name="operaterId" value="${saleReCopPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleReCopPlan.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="operaterName" name="operaterName" value="${saleReCopPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">数据来源类型: </th>
					<td><input size="35" type="text" id="sourceformType" name="sourceformType" value="${saleReCopPlan.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">库存信息: </th>
					<td>
					<c:if test='${saleReCopPlan.sharedRepository==1}'>
						共享
						<input size="35" type="hidden" id="sharedRepository" name="sharedRepository" value="1"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					<c:if test='${saleReCopPlan.sharedRepository==0}'>
					            不共享
						<input size="35" type="hidden" id="sharedRepository" name="sharedRepository" value="0"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					</td>
					
					<th style="width:120px">销售信息: </th>
					<td>
					<c:if test='${saleReCopPlan.saleRepository==1}'>	
					共享
				 		<input size="35" type="hidden" id="saleRepository" name="saleRepository" value="1"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					<c:if test='${saleReCopPlan.saleRepository==0}'>	
					  不共享
				 		<input size="35" type="hidden" id="saleRepository" name="saleRepository" value="0"  class="inputText" validate="{required:false,maxlength:96}"  />
					</c:if>
					</td>
				</tr>
				<tr>
					<th style="width:120px">数据来源ID: </th>
					<td><input size="35" type="text" id="sourceformId" name="sourceformId" value="${saleReCopPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">数据来源编号: </th>
					<td><input size="35" type="text" id="sourceformCode" name="sourceformCode" value="${saleReCopPlan.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">发布模式:</th>
					
					<td>
					        
					    	<select id="deliveryType" name="deliveryType">
					    <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.deliveryType=='公开'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='公开' ${selected} >公&nbsp;开</option>
					    <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.deliveryType=='邀请'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='邀请' ${selected}>邀&nbsp;请</option>
					</select>
					</td>
					       
		 		</select>
					 <!--  	  <input size="35" type="text" id="deliveryType" name="deliveryType" value="${saleReCopPlan.deliveryType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>-->	
					<th style="width:120px">发布时间: </th>
					<td><input size="35" type="text" id="deliveryDate" name="deliveryDate" value="<fmt:formatDate value='${saleReCopPlan.deliveryDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">回复截止时间: </th>
					<td><input size="35" type="text" id="offerEndtime" readonly="readonly" name="offerEndtime" value="<fmt:formatDate value='${saleReCopPlan.offerEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">处理截止时间: </th>
					<td><input size="35" type="text" id="doofferEndtime" readonly="readonly" name="doofferEndtime" value="<fmt:formatDate value='${saleReCopPlan.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">合作营销区域: </th>
				<td>	
				    <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					</select>
				</td>
					<th style="width:120px">合作开始时间: </th>
					<td><input size="35" type="text" id="cooperationStarttime"  readonly="readonly" name="cooperationStarttime" value="<fmt:formatDate value='${saleReCopPlan.cooperationStarttime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">上报库存信息时间间隔: </th>
					<td><input size="35" type="text" id="reportstockTimespace"  readonly="readonly" name="reportstockTimespace" value="${saleReCopPlan.reportstockTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">合作截止时间: </th>
					<td><input size="35" type="text" id="cooperationEndtime" readonly="readonly" name="cooperationEndtime" value="<fmt:formatDate value='${saleReCopPlan.cooperationEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">上报销售订单时间间隔: </th>
					<td><input size="35" type="text" id="reportsalesTimespace" readonly="readonly" name="reportsalesTimespace" value="${saleReCopPlan.reportsalesTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">说明: </th>
					<td><input size="35" type="text" id="comments" name="comments" value="${saleReCopPlan.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">企业ID: </th>
					<td><input size="35" type="text" id="enterpriseId" name="enterpriseId" value="${saleReCopPlan.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">企业: </th>
					<td><input size="35" type="text" id="enterpriseName" name="enterpriseName" value="${saleReCopPlan.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">企业联系人: </th>
					<td><input size="35" type="text" id="enterpriseUsername" name="enterpriseUsername" value="${saleReCopPlan.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">企业联系人Id: </th>
					<td><input size="35" type="text" id="enterpriseUserid" name="enterpriseUserid" value="${saleReCopPlan.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">合作类型： </th>
					 <td>
					      <select id="type" name="type">
					    <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.type=='代理'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='代理' ${selected} >代理</option>
					    
					    <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.type=='经销'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='经销' ${selected}>经销</option>
					    
					    <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.type=='联营'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					     <option value='联营' ${selected} >联营</option>
					</select>
								</td>
					<th style="width:120px">合作企业ID: </th>
					<td><input size="35" type="text" readonly="readonly"   id="copenterpriseId" name="copenterpriseId" value="${saleReCopPlan.copenterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">合作企业名称: </th>

					<td><input size="35" type="text"   readonly="readonly"   id="copenterpriseName" name="copenterpriseName" value="${saleReCopPlan.copenterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>

					<th style="width:120px">合作企业联系人ID: </th>
					<td><input size="35" type="text"  readonly="readonly"   id="copenterpriseUserid" name="copenterpriseUserid" value="${saleReCopPlan.copenterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">合作企业联系人: </th>
					<td colspan="3"><input size="35"  readonly="readonly"  type="text" id="copenterpriseUsername" name="copenterpriseUsername" value="${saleReCopPlan.copenterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleReCopPlanDetail" formType="window" type="sub">
				<tr>
					<td colspan="13">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						营销计划回复单 : 营销计划回复单详情
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
						<input size="35" type="hidden" name="materialId" value="${saleReCopPlanDetailItem.materialId}"/>
						<input size="35" type="hidden" name="materialName" value="${saleReCopPlanDetailItem.materialName}"/>
						<input size="35" type="hidden" name="materialCode" value="${saleReCopPlanDetailItem.materialCode}"/>
						<input size="35" type="hidden" name="materialCodenote" value="${saleReCopPlanDetailItem.materialCodenote}"/>
						<input size="35" type="hidden" name="materielLev" value="${saleReCopPlanDetailItem.materielLev}"/>
						<input size="35" type="hidden" name="attributeInfo" value="${saleReCopPlanDetailItem.attributeInfo}"/>
						<input size="35" type="hidden" name="unit" value="${saleReCopPlanDetailItem.unit}"/>
						<input size="35" type="hidden" name="materielNum" value="${saleReCopPlanDetailItem.materielNum}"/>
						<input size="35" type="hidden" name="firstNumber" value="${saleReCopPlanDetailItem.firstNumber}"/>
						<input size="35" type="hidden" name="prise" value="${saleReCopPlanDetailItem.prise}"/>
						<input size="35" type="hidden" name="priseRegion" value="${saleReCopPlanDetailItem.priseRegion}"/>
						<input size="35" type="hidden" name="accountType" value="${saleReCopPlanDetailItem.accountType}"/>
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
			    	<input size="35" type="hidden" name="materialId" value=""/>
			    	<input size="35" type="hidden" name="materialName" value=""/>
			    	<input size="35" type="hidden" name="materialCode" value=""/>
			    	<input size="35" type="hidden" name="materialCodenote" value=""/>
			    	<input size="35" type="hidden" name="materielLev" value=""/>
			    	<input size="35" type="hidden" name="attributeInfo" value=""/>
			    	<input size="35" type="hidden" name="unit" value=""/>
			    	<input size="35" type="hidden" name="materielNum" value=""/>
			    	<input size="35" type="hidden" name="firstNumber" value=""/>
			    	<input size="35" type="hidden" name="prise" value=""/>
			    	<input size="35" type="hidden" name="priseRegion" value=""/>
			    	<input size="35" type="hidden" name="accountType" value=""/>
		 		</tr>
		    </table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleReCopPlanQualification" formType="window" type="sub">
				<tr>
					<td colspan="4">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						营销计划回复单 : 营销计划回复单资质附件
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
						<input size="35" type="hidden" name="qualificationName" value="${saleReCopPlanQualificationItem.qualificationName}"/>
						<input size="35" type="hidden" name="qualificationAttachment" value="${saleReCopPlanQualificationItem.qualificationAttachment}"/>
						<input size="35" type="hidden" name="qualificationNote" value="${saleReCopPlanQualificationItem.qualificationNote}"/>
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
			    	<input size="35" type="hidden" name="qualificationName" value=""/>
			    	<input size="35" type="hidden" name="qualificationAttachment" value=""/>
			    	<input size="35" type="hidden" name="qualificationNote" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${saleReCopPlan.id}" />
			<input size="35" type="hidden" id="back" name="back" value=""/>
			<input size="35" type="hidden" name="formData" id="formData" />
			<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="saleReCopPlanDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">物品ID: </th>
				<td colspan="3"><input size="35" type="text" name="materialId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">物品名: </th>
				<td><input size="35" type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
				<th style="width:120px">物品编码: </th>
				<td><input size="35" type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">编码参考附件: </th>
				<td><input size="35" type="text" name="materialCodenote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
				<th style="width:120px">分类: </th>
				<td><input size="35" type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">属性说明: </th>
				<td><input size="35" type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
				<th style="width:120px">单位: </th>
				<td><input size="35" type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">合作销售数量: </th>
				<td><input size="35" type="text" name="materielNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
				<th style="width:120px">首批进货量: </th>
				<td><input size="35" type="text" name="firstNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">合作单价: </th>
				<td><input size="35" type="text" name="prise" value=""  class="inputText" validate="{required:false}"/></td>
				<th style="width:120px">价格区间（+-%）: </th>
				<td><input size="35" type="text" name="priseRegion" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">结算方式（返点/返利）: </th>
				<td colspan="3"><input size="35" type="text" name="accountType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		</table>
	</form>
	<form id="saleReCopPlanQualificationForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">资质: </th>
				<td><input size="35" type="text" name="qualificationName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">资质附件: </th>
				<td><input size="35" type="text" name="qualificationAttachment" value=""  class="inputText" validate="{required:false,maxlength:768}"/><a href="javascript:void(0)" onclick="selectfile();">选择</a></td>
			</tr>
			<tr>
				<th style="width:120px">资质说明: </th>
				<td><input size="35" type="text" name="qualificationNote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
