<%--
	time:2013-04-19 11:18:08
	desc:edit the cloud_manuf_order
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 受托加工订单</title>
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
			var frm=$('#manufOrderForm').form();
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
						window.location.href = "${ctx}/cloud/manufacture/manufOrder/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufOrderForm').attr('action','apply.ht');
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

			$('#manufOrderForm').attr("action","complete.ht");
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
			
			$('#manufOrderForm').attr("action","complete.ht");
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
			    <c:when test="${manufOrder.id !=null}">
			        <span class="tbar-label">编辑受托加工订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加受托加工订单</span>
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
				<c:if test="${applyFlag==2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="manufOrderForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${manufOrder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${manufOrder.operatorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufOrder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufOrder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">委托企业ID: </th>
					<td><input type="text" id="enquiryenterpriseId" readonly="readonly" name="enquiryenterpriseId" value="${manufOrder.enquiryenterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">委托企业: </th>
					<td><input type="text" id="enquiryenterpriseName" readonly="readonly" name="enquiryenterpriseName" value="${manufOrder.enquiryenterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">委托企业联系人ID: </th>
					<td><input type="text" id="enquiryenterpriseUserid" readonly="readonly" name="enquiryenterpriseUserid" value="${manufOrder.enquiryenterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">委托企业联系人: </th>
					<td><input type="text" id="enquiryenterpriseUsername" readonly="readonly" name="enquiryenterpriseUsername" value="${manufOrder.enquiryenterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${manufOrder.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">运输方式: </th>
					<td><input type="text" id="transportWay" name="transportWay" value="${manufOrder.transportWay}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${manufOrder.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
									<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${manufOrder.sumPrice}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${manufOrder.advancePay}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">预付款说明: </th>
					<td><input type="text" id="advancepayNote" name="advancepayNote" value="${manufOrder.advancepayNote}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">是否带料: </th>
				<!-- 	<td><input type="text" id="isBringmaterial" name="isBringmaterial" value="${manufOrder.isBringmaterial}"  class="inputText" validate="{required:false}"  /></td>  -->
				    	<td>	    <div class="cell">
											<c:remove var="checked"/>
									        <c:if test="${manufOrder.isBringmaterial==1}">
									        	<c:set var="checked" value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio"  name="isBringmaterial" ${checked} class="radio" value="1" />是
											<c:remove var="checked"/>
									        <c:if test="${manufOrder.isBringmaterial==0}">
									        	<c:set var="checked" value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio" name="isBringmaterial" class="radio" ${checked} value="0"/>否
										</div>
									</div>
									</td>
					<th width="20%">加工类型: </th>
					<td><input type="text" id="manufacturingType" name="manufacturingType" value="${manufOrder.manufacturingType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufOrder.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${manufOrder.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据编码: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufOrder.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">受托企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" readonly="readonly"   value="${manufOrder.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">受托企业名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" readonly="readonly"   value="${manufOrder.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">受托企业联系人ID: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${manufOrder.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">受托企业联系人名: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${manufOrder.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">是否汇报加工进度: </th>
		      <!-- 		<td><input type="text" id="isReport" name="isReport" value="${manufOrder.isReport}"  class="inputText" validate="{required:false}"  /></td>  -->	
						<td>
					         <div class="cell">
											<c:remove var="checked"/>
									        <c:if test="${manufOrder.isReport==1}">
									        	<c:set var="checked" value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio"   name="isReport" ${checked} class="radio" value="1" />是
											<c:remove var="checked"/>
									        <c:if test="${manufOrder.isReport==0}">
									        	<c:set var="checked"  value='checked="checked"'></c:set>
										    </c:if>
												<input type="radio"  name="isReport" class="radio" ${checked} value="0"/>否
										</div>
									</div>
									</td>
				</tr>
				<tr>
					<th width="20%">收货仓库ID: </th>
					<td><input type="text" id="receivewarehouseId" readonly="readonly"  name="receivewarehouseId" value="${manufOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">收货仓库名: </th>
					<td><input type="text" id="receivewarehouseName" readonly="readonly"   name="receivewarehouseName" value="${manufOrder.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货仓库地址: </th>
					<td><input type="text" id="receivewarehouseAddress" readonly="readonly"   name="receivewarehouseAddress" value="${manufOrder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">收货联系人ID: </th>
					<td><input type="text" id="receivewarehouseUserid" readonly="readonly"   name="receivewarehouseUserid" value="${manufOrder.receivewarehouseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">收货联系人名: </th>
					<td><input type="text" id="receivewarehouseUsername" readonly="readonly"   name="receivewarehouseUsername" value="${manufOrder.receivewarehouseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">发货仓库ID: </th>
					<td><input type="text" id="deliverwarehouseId"  name="deliverwarehouseId" value="${manufOrder.deliverwarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">发货仓库: </th>
					<td><input type="text" id="deliverwarehouseName" name="deliverwarehouseName" value="${manufOrder.deliverwarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">发货仓库地址: </th>
					<td><input type="text" id="deliverwarehouseAddress" name="deliverwarehouseAddress" value="${manufOrder.deliverwarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">发货联系人ID: </th>
					<td><input type="text" id="deliverwarehouseUserid" name="deliverwarehouseUserid" value="${manufOrder.deliverwarehouseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">发货联系人: </th>
					<td><input type="text" id="deliverwarehouseUsername" name="deliverwarehouseUsername" value="${manufOrder.deliverwarehouseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">备注: </th>
					<td><input type="text" id="comments" name="comments" value="${manufOrder.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="manufOrderDetail" formType="window" type="sub">
				<tr>
					<th>代加工物品编码</th>
					<th>编码说明</th>
					<th>代加工物品</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>委托加工数量</th>
					
					<th>要求交货日期</th>
				
					<th>工艺要求</th>
					<th>工艺附件</th>
					<th>BOM附件</th>
					<th>计划开工时间</th>
					<th>计划完成时间</th>
					<th>计划完工入库率下限</th>
				
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufEnquiryorderDetailList}" var="manufEnquiryorderDetailList" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialCode">${manufEnquiryorderDetailList.materialCode}</td>
					    <td style="text-align: center" name="materialCodenotation">${manufEnquiryorderDetailList.materialCodenotation}</td>
					    <td style="text-align: center" name="materialName">${manufEnquiryorderDetailList.materialName}</td>
					    <td style="text-align: center" name="materialSpec">${manufEnquiryorderDetailList.materialSpec}</td>
					    <td style="text-align: center" name="materialUnit">${manufEnquiryorderDetailList.materialUnit}</td>
					    <td style="text-align: center" name="materialNumber">${manufEnquiryorderDetailList.materialQuantity}</td>
					 
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${manufEnquiryorderDetailList.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="crafts">${manufEnquiryorderDetailList.crafts}</td>
					    <td style="text-align: center" name="craftAttachment">${manufEnquiryorderDetailList.craftAttachment}</td>
					    <td style="text-align: center" name="bom">${manufEnquiryorderDetailList.bom}</td>
						<td style="text-align: center" name="planStartdate"><fmt:formatDate value='${manufEnquiryorderDetailList.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planEnddate"><fmt:formatDate value='${manufEnquiryorderDetailList.planEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="planInrate">${manufEnquiryorderDetailList.planInrate}</td>
							
					    <td style="text-align: center" name="comments">${manufEnquiryorderDetailList.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materialCode" value="${manufEnquiryorderDetailList.materialCode}"/>
						<input type="hidden" name="materialCodenotation" value="${manufEnquiryorderDetailList.materialCodenotation}"/>
						<input type="hidden" name="materialName" value="${manufEnquiryorderDetailList.materialName}"/>
						<input type="hidden" name="materialSpec" value="${manufEnquiryorderDetailList.materialSpec}"/>
						<input type="hidden" name="materialUnit" value="${manufEnquiryorderDetailList.materialUnit}"/>
						<input type="hidden" name="materialNumber" value="${manufEnquiryorderDetailList.materialQuantity}"/>
					
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${manufEnquiryorderDetailList.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="crafts" value="${manufEnquiryorderDetailList.crafts}"/>
						<input type="hidden" name="craftAttachment" value="${manufEnquiryorderDetailList.craftAttachment}"/>
						<input type="hidden" name="bom" value="${manufEnquiryorderDetailList.bom}"/>
					    <input type="hidden" name="planStartdate" value="<fmt:formatDate value='${manufEnquiryorderDetailList.planStartdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planEnddate" value="<fmt:formatDate value='${manufEnquiryorderDetailList.planEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="planInrate" value="${manufEnquiryorderDetailList.planInrate}"/>
				
						<input type="hidden" name="comments" value="${manufEnquiryorderDetailList.comments}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center" name="materialCodenotation"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	<td style="text-align: center" name="materialSpec"></td>
			    	<td style="text-align: center" name="materialUnit"></td>
			    	<td style="text-align: center" name="materialQuantity"></td>
			 
					<td style="text-align: center" name="deliveryEnddate"></td>								
											
			    	<td style="text-align: center" name="crafts"></td>
			    	<td style="text-align: center" name="craftAttachment"></td>
			    	<td style="text-align: center" name="bom"></td>
					<td style="text-align: center" name="planStartdate"></td>								
					<td style="text-align: center" name="planEnddate"></td>								
			    	<td style="text-align: center" name="planInrate"></td>
								
			    	<td style="text-align: center" name="comments"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materialCode" value=""/>
			    	<input type="hidden" name="materialCodenotation" value=""/>
			    	<input type="hidden" name="materialName" value=""/>
			    	<input type="hidden" name="materialSpec" value=""/>
			    	<input type="hidden" name="materialUnit" value=""/>
			    	<input type="hidden" name="materialNumber" value=""/>
			 
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    
			    	<input type="hidden" name="crafts" value=""/>
			    	<input type="hidden" name="craftAttachment" value=""/>
			    	<input type="hidden" name="bom" value=""/>
			    	<input type="hidden" name="planStartdate" value="" class="inputText date"/>
			    	<input type="hidden" name="planEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="planInrate" value=""/>
			
			    	<input type="hidden" name="comments" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${manufOrder.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="manufOrderDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">代加工物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码说明: </th>
				<td><input type="text" name="materialCodenotation" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">代加工物品: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="materialSpec" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="materialUnit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">委托加工数量: </th>
				<td><input type="text" name="materialNumber" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单位加工费: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">加工费总金额: </th>
				<td><input type="text" name="sumPrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">要求交货日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">预计交货日期: </th>
				<td><input type="text" name="predeliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">工艺要求: </th>
				<td><input type="text" name="crafts" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">工艺附件: </th>
				<td><input type="text" name="craftAttachment" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">BOM附件: </th>
				<td><input type="text" name="bom" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">计划开工时间: </th>
				<td><input type="text" name="planStartdate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完成时间: </th>
				<td><input type="text" name="planEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完工入库率下限: </th>
				<td><input type="text" name="planInrate" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">预计开工时间: </th>
				<td><input type="text" name="preStartdate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">预计完工时间: </th>
				<td><input type="text" name="preEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">备注: </th>
				<td><input type="text" name="comments" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
