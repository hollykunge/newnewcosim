<%--
	time:2013-04-19 11:18:08
	desc:edit the cloud_manuf_enquiryorder
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 委托加工订单</title>
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
			var frm=$('#manufEnquiryorderForm').form();
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
						window.location.href = "${ctx}/cloud/manufacture/manufEnquiryorder/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufEnquiryorderForm').attr('action','apply.ht');
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

			$('#manufEnquiryorderForm').attr("action","complete.ht");
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
			
			$('#manufEnquiryorderForm').attr("action","complete.ht");
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
			    <c:when test="${manufEnquiryorder.id !=null}">
			        <span class="tbar-label">编辑委托加工订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加委托加工订单</span>
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
		<form id="manufEnquiryorderForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${manufEnquiryorder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${manufEnquiryorder.operatorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufEnquiryorder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufEnquiryorder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">受托加工企业ID: </th>
					<td><input type="text" id="selectedenterpId" name="selectedenterpId" value="${manufEnquiryorder.selectedenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">受托加工企业名: </th>
					<td><input type="text" id="selectedenterpName" name="selectedenterpName" value="${manufEnquiryorder.selectedenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">受托加工联系人ID: </th>
					<td><input type="text" id="selectedenterpUserid" name="selectedenterpUserid" value="${manufEnquiryorder.selectedenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">受托加工联系人姓名: </th>
					<td><input type="text" id="selectedenterpUsername" name="selectedenterpUsername" value="${manufEnquiryorder.selectedenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${manufEnquiryorder.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">运输方式: </th>
					<td><input type="text" id="transportWay" name="transportWay" value="${manufEnquiryorder.transportWay}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${manufEnquiryorder.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${manufEnquiryorder.sumPrice}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${manufEnquiryorder.advancePay}"  class="inputText" validate="{required:false,number:true }"  /></td>
			
					<th width="20%">预付款说明: </th>
					<td><input type="text" id="advancepayNote" name="advancepayNote" value="${manufEnquiryorder.advancepayNote}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">是否带料: </th>
					<td><input type="text" id="isBringmaterial" name="isBringmaterial" value="${manufEnquiryorder.isBringmaterial}"  class="inputText" validate="{required:false}"  /></td>
				
					<th width="20%">加工类型: </th>
					<td><input type="text" id="manufacturingType" name="manufacturingType" value="${manufEnquiryorder.manufacturingType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufEnquiryorder.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
			
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${manufEnquiryorder.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据编码: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufEnquiryorder.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
			
					<th width="20%">委托企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${manufEnquiryorder.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">委托企业名称: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${manufEnquiryorder.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">委托企业联系人ID: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${manufEnquiryorder.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">委托企业联系人名: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${manufEnquiryorder.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">是否汇报加工进度: </th>
					<td><input type="text" id="isReport" name="isReport" value="${manufEnquiryorder.isReport}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货仓库ID: </th>
					<td><input type="text" id="receivewarehouseId" name="receivewarehouseId" value="${manufEnquiryorder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">收货仓库名: </th>
					<td><input type="text" id="receivewarehouseName" name="receivewarehouseName" value="${manufEnquiryorder.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货仓库地址: </th>
					<td><input type="text" id="receivewarehouseAddress" name="receivewarehouseAddress" value="${manufEnquiryorder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">收货联系人ID: </th>
					<td><input type="text" id="receivewarehouseUserid" name="receivewarehouseUserid" value="${manufEnquiryorder.receivewarehouseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">收货联系人名: </th>
					<td><input type="text" id="receivewarehouseUsername" name="receivewarehouseUsername" value="${manufEnquiryorder.receivewarehouseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">备注: </th>
					<td><input type="text" id="comments" name="comments" value="${manufEnquiryorder.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			
			<fieldset>
			<legend>委外订单详情</legend>
			<table class="table-grid table-list" id="manufEnquiryorderDetail" cellpadding="1" cellspacing="1" formType="window" type="sub">
	
				<tr>
					<th>物品编码</th>
					<th>编码规则</th>
					<th>物品名称</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>委托加工数量</th>
					<th>要求交货日期</th>
					<th>工艺要求（多选）</th>
					<th>工艺附件</th>
					<th>产品BOM附件</th>
					<th>计划开工时间</th>
					<th>计划完工时间</th>
					<th>计划完工入库率下限</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufQuoteDetailList}" var="manufQuoteDetailList" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialCode">${manufQuoteDetailList.materialCode}</td>
					    <td style="text-align: center" name="materialCodenotation">${manufQuoteDetailList.materialCodenotation}</td>
					    <td style="text-align: center" name="materialName">${manufQuoteDetailList.materialName}</td>
					    <td style="text-align: center" name="materialSpec">${manufQuoteDetailList.materialAttribute}</td>
					    <td style="text-align: center" name="materialUnit">${manufQuoteDetailList.materialUnit}</td>
					    <td style="text-align: center" name="materialQuantity">${manufQuoteDetailList.materialNumber}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${manufQuoteDetailList.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="crafts">${manufQuoteDetailList.crafts}</td>
					    <td style="text-align: center" name="craftAttachment">${manufQuoteDetailList.craftAttachment}</td>
					    <td style="text-align: center" name="bom">${manufQuoteDetailList.bom}</td>
						<td style="text-align: center" name="planStartdate"><fmt:formatDate value='${manufQuoteDetailList.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planEnddate"><fmt:formatDate value='${manufQuoteDetailList.planEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="planInrate">${manufQuoteDetailList.planInrate}</td>
					    <td style="text-align: center" name="comments">${manufQuoteDetailList.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materialCode" value="${manufQuoteDetailList.materialCode}"/>
						<input type="hidden" name="materialCodenotation" value="${manufQuoteDetailList.materialCodenotation}"/>
						<input type="hidden" name="materialName" value="${manufQuoteDetailList.materialName}"/>
						<input type="hidden" name="materialSpec" value="${manufQuoteDetailList.materialAttribute}"/>
						<input type="hidden" name="materialUnit" value="${manufQuoteDetailList.materialUnit}"/>
						<input type="hidden" name="materialQuantity" value="${manufQuoteDetailList.materialNumber}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${manufQuoteDetailList.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="crafts" value="${manufQuoteDetailList.crafts}"/>
						<input type="hidden" name="craftAttachment" value="${manufQuoteDetailList.craftAttachment}"/>
						<input type="hidden" name="bom" value="${manufQuoteDetailList.bom}"/>
					    <input type="hidden" name="planStartdate" value="<fmt:formatDate value='${manufQuoteDetailList.planStartdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planEnddate" value="<fmt:formatDate value='${manufQuoteDetailList.planEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="planInrate" value="${manufQuoteDetailList.planInrate}"/>
						<input type="hidden" name="comments" value="${manufQuoteDetailList.comments}"/>
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
			    	<input type="hidden" name="materialQuantity" value=""/>
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
			</fieldset>
			<fieldset>
			<c:if test="${manufEnquiryorder.isBringmaterial == 1 }" >
			<legend>物料详情</legend>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="manufEnquiryorderSupplies" formType="window" type="sub">
				<tr>
					<td colspan="9">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						委托加工订单 : 委托加工订单产品详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>物品编码</th>
					<th>编码规则</th>
					<th>物品名称</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>计划下达日期</th>
					<th>计划到货日期</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufEnquiryorderSuppliesList}" var="manufEnquiryorderSuppliesItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="suppliesCode">${manufEnquiryorderSuppliesItem.suppliesCode}</td>
					    <td style="text-align: center" name="suppliesCodenotation">${manufEnquiryorderSuppliesItem.suppliesCodenotation}</td>
					    <td style="text-align: center" name="suppliesName">${manufEnquiryorderSuppliesItem.suppliesName}</td>
					    <td style="text-align: center" name="suppliesSpec">${manufEnquiryorderSuppliesItem.suppliesSpec}</td>
					    <td style="text-align: center" name="suppliesUnit">${manufEnquiryorderSuppliesItem.suppliesUnit}</td>
						<td style="text-align: center" name="planDeliverydate"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planDeliverydate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planAogdate"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planAogdate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="comments">${manufEnquiryorderSuppliesItem.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="suppliesCode" value="${manufEnquiryorderSuppliesItem.suppliesCode}"/>
						<input type="hidden" name="suppliesCodenotation" value="${manufEnquiryorderSuppliesItem.suppliesCodenotation}"/>
						<input type="hidden" name="suppliesName" value="${manufEnquiryorderSuppliesItem.suppliesName}"/>
						<input type="hidden" name="suppliesSpec" value="${manufEnquiryorderSuppliesItem.suppliesSpec}"/>
						<input type="hidden" name="suppliesUnit" value="${manufEnquiryorderSuppliesItem.suppliesUnit}"/>
					    <input type="hidden" name="planDeliverydate" value="<fmt:formatDate value='${manufEnquiryorderSuppliesItem.planDeliverydate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planAogdate" value="<fmt:formatDate value='${manufEnquiryorderSuppliesItem.planAogdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="comments" value="${manufEnquiryorderSuppliesItem.comments}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="suppliesCode"></td>
			    	<td style="text-align: center" name="suppliesCodenotation"></td>
			    	<td style="text-align: center" name="suppliesName"></td>
			    	<td style="text-align: center" name="suppliesSpec"></td>
			    	<td style="text-align: center" name="suppliesUnit"></td>
					<td style="text-align: center" name="planDeliverydate"></td>								
					<td style="text-align: center" name="planAogdate"></td>								
			    	<td style="text-align: center" name="comments"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="suppliesCode" value=""/>
			    	<input type="hidden" name="suppliesCodenotation" value=""/>
			    	<input type="hidden" name="suppliesName" value=""/>
			    	<input type="hidden" name="suppliesSpec" value=""/>
			    	<input type="hidden" name="suppliesUnit" value=""/>
			    	<input type="hidden" name="planDeliverydate" value="" class="inputText date"/>
			    	<input type="hidden" name="planAogdate" value="" class="inputText date"/>
			    	<input type="hidden" name="comments" value=""/>
		 		</tr>
		    </table>
		    </fieldset>
		    </c:if>
			<input type="hidden" name="id" value="${manufEnquiryorder.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="manufEnquiryorderDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码规则: </th>
				<td><input type="text" name="materialCodenotation" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
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
				<td><input type="text" name="materialQuantity" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">要求交货日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">工业要求（多选）: </th>
				<td><input type="text" name="crafts" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">工艺附件: </th>
				<td><input type="text" name="craftAttachment" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">产品BOM附件: </th>
				<td><input type="text" name="bom" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">计划开工时间: </th>
				<td><input type="text" name="planStartdate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完工时间: </th>
				<td><input type="text" name="planEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划完工入库率下限: </th>
				<td><input type="text" name="planInrate" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">备注: </th>
				<td><input type="text" name="comments" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
	<form id="manufEnquiryorderSuppliesForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">物料编码: </th>
				<td><input type="text" name="suppliesCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码规则: </th>
				<td><input type="text" name="suppliesCodenotation" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">物料名称: </th>
				<td><input type="text" name="suppliesName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="suppliesSpec" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="suppliesUnit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">计划下达日期: </th>
				<td><input type="text" name="planDeliverydate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划到货日期: </th>
				<td><input type="text" name="planAogdate" value="" class="inputText date" validate="{date:true}"/></td>
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
