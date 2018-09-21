<%--
	time:2013-04-19 11:18:08
	desc:edit the cloud_manuf_quote
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 委外加工报价</title>
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
			var frm=$('#manufQuoteForm').form();
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
						window.location.href = "${ctx}/cloud/manufacture/manufQuote/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufQuoteForm').attr('action','apply.ht');
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

			$('#manufQuoteForm').attr("action","complete.ht");
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
			
			$('#manufQuoteForm').attr("action","complete.ht");
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
			    <c:when test="${manufQuote.id !=null}">
			        <span class="tbar-label">编辑委外加工报价</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加委外加工报价</span>
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
		<form id="manufQuoteForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">编码: </th>
					<td width="30%"><input type="text" id="code" name="code" value="${manufQuote.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${manufQuote.operatorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufQuote.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufQuote.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${manufQuote.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">企业: </th>
					<td><input type="text" id="enterpriseName" name="enterpriseName" value="${manufQuote.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">企业联系人ID: </th>
					<td><input type="text" id="enterpriseUserid" name="enterpriseUserid" value="${manufQuote.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">企业联系人: </th>
					<td><input type="text" id="enterpriseUsername" name="enterpriseUsername" value="${manufQuote.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">询价截止时间: </th>
					<td><input type="text" id="offerEnddate" name="offerEnddate" value="<fmt:formatDate value='${manufQuote.offerEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				
					<th width="20%">发布时间: </th>
					<td><input type="text" id="releaseDate" name="releaseDate" value="<fmt:formatDate value='${manufQuote.releaseDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">发布模式: </th>
					<td><input type="text" id="releaseModel" name="releaseModel" value="${manufQuote.releaseModel}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${manufQuote.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">运输方式: </th>
					<td><input type="text" id="transportWay" name="transportWay" value="${manufQuote.transportWay}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${manufQuote.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${manufQuote.sumPrice}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${manufQuote.advancePay}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款说明: </th>
					<td><input type="text" id="advancepayNote" name="advancepayNote" value="${manufQuote.advancepayNote}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">是否带料: </th>
					<td><input type="text" id="isBringmaterial" name="isBringmaterial" value="${manufQuote.isBringmaterial}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">加工类型: </th>
					<td><input type="text" id="manufacturingType" name="manufacturingType" value="${manufQuote.manufacturingType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufQuote.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${manufQuote.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">来源单据CODE: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufQuote.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">委托企业ID: </th>
					<td><input type="text" id="enquiryenterpId" name="enquiryenterpId" value="${manufQuote.enquiryenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">委托企业: </th>
					<td><input type="text" id="enquiryenterpName" name="enquiryenterpName" value="${manufQuote.enquiryenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">委托企业联系人ID: </th>
					<td><input type="text" id="enquiryenterpUserid" name="enquiryenterpUserid" value="${manufQuote.enquiryenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				
					<th width="20%">委托企业联系人: </th>
					<td><input type="text" id="enquiryenterpUsername" name="enquiryenterpUsername" value="${manufQuote.enquiryenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">是否汇报加工进度: </th>
					<td><input type="text" id="isReport" name="isReport" value="${manufQuote.isReport}"  class="inputText" validate="{required:false}"  /></td>
				
					<th width="20%">备注（256）: </th>
					<td><input type="text" id="comments" name="comments" value="${manufQuote.comments}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="manufQuoteDetail" formType="window" type="sub">
				<tr>
					<td colspan="20">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						委外加工报价 : 委外加工报价详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>代加工物品编码</th>
					<th>编码规则</th>
					<th>代加工物品名称</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>委托加工数量</th>
					<th>单位加工费用</th>
					<th>加工费总金额</th>
					<th>要求交货日期</th>
					<th>预计交货日期</th>
					<th>工艺要求</th>
					<th>工艺附件</th>
					<th>BOM附件</th>
					<th>计划开工时间</th>
					<th>计划完工时间</th>
					<th>计划入库率下限</th>
					<th>预计开工时间</th>
					<th>预计完工时间</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufQuoteDetailList}" var="manufQuoteDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialCode">${manufQuoteDetailItem.materialCode}</td>
					    <td style="text-align: center" name="materialCodenotation">${manufQuoteDetailItem.materialCodenotation}</td>
					    <td style="text-align: center" name="materialName">${manufQuoteDetailItem.materialName}</td>
					    <td style="text-align: center" name="materialAttribute">${manufQuoteDetailItem.materialAttribute}</td>
					    <td style="text-align: center" name="materialUnit">${manufQuoteDetailItem.materialUnit}</td>
					    <td style="text-align: center" name="materialNumber">${manufQuoteDetailItem.materialNumber}</td>
					    <td style="text-align: center" name="price">${manufQuoteDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${manufQuoteDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${manufQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="predeliveryEnddate"><fmt:formatDate value='${manufQuoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="crafts">${manufQuoteDetailItem.crafts}</td>
					    <td style="text-align: center" name="craftAttachment">${manufQuoteDetailItem.craftAttachment}</td>
					    <td style="text-align: center" name="bom">${manufQuoteDetailItem.bom}</td>
						<td style="text-align: center" name="planStartdate"><fmt:formatDate value='${manufQuoteDetailItem.planStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planEnddate"><fmt:formatDate value='${manufQuoteDetailItem.planEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="planInrate">${manufQuoteDetailItem.planInrate}</td>
						<td style="text-align: center" name="preStartdate"><fmt:formatDate value='${manufQuoteDetailItem.preStartdate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="preEnddate"><fmt:formatDate value='${manufQuoteDetailItem.preEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="comments">${manufQuoteDetailItem.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materialCode" value="${manufQuoteDetailItem.materialCode}"/>
						<input type="hidden" name="materialCodenotation" value="${manufQuoteDetailItem.materialCodenotation}"/>
						<input type="hidden" name="materialName" value="${manufQuoteDetailItem.materialName}"/>
						<input type="hidden" name="materialAttribute" value="${manufQuoteDetailItem.materialAttribute}"/>
						<input type="hidden" name="materialUnit" value="${manufQuoteDetailItem.materialUnit}"/>
						<input type="hidden" name="materialNumber" value="${manufQuoteDetailItem.materialNumber}"/>
						<input type="hidden" name="price" value="${manufQuoteDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${manufQuoteDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${manufQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="predeliveryEnddate" value="<fmt:formatDate value='${manufQuoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="crafts" value="${manufQuoteDetailItem.crafts}"/>
						<input type="hidden" name="craftAttachment" value="${manufQuoteDetailItem.craftAttachment}"/>
						<input type="hidden" name="bom" value="${manufQuoteDetailItem.bom}"/>
					    <input type="hidden" name="planStartdate" value="<fmt:formatDate value='${manufQuoteDetailItem.planStartdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planEnddate" value="<fmt:formatDate value='${manufQuoteDetailItem.planEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="planInrate" value="${manufQuoteDetailItem.planInrate}"/>
					    <input type="hidden" name="preStartdate" value="<fmt:formatDate value='${manufQuoteDetailItem.preStartdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="preEnddate" value="<fmt:formatDate value='${manufQuoteDetailItem.preEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="comments" value="${manufQuoteDetailItem.comments}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center" name="materialCodenotation"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	<td style="text-align: center" name="materialAttribute"></td>
			    	<td style="text-align: center" name="materialUnit"></td>
			    	<td style="text-align: center" name="materialNumber"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
					<td style="text-align: center" name="predeliveryEnddate"></td>								
			    	<td style="text-align: center" name="crafts"></td>
			    	<td style="text-align: center" name="craftAttachment"></td>
			    	<td style="text-align: center" name="bom"></td>
					<td style="text-align: center" name="planStartdate"></td>								
					<td style="text-align: center" name="planEnddate"></td>								
			    	<td style="text-align: center" name="planInrate"></td>
					<td style="text-align: center" name="preStartdate"></td>								
					<td style="text-align: center" name="preEnddate"></td>								
			    	<td style="text-align: center" name="comments"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materialCode" value=""/>
			    	<input type="hidden" name="materialCodenotation" value=""/>
			    	<input type="hidden" name="materialName" value=""/>
			    	<input type="hidden" name="materialAttribute" value=""/>
			    	<input type="hidden" name="materialUnit" value=""/>
			    	<input type="hidden" name="materialNumber" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="predeliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="crafts" value=""/>
			    	<input type="hidden" name="craftAttachment" value=""/>
			    	<input type="hidden" name="bom" value=""/>
			    	<input type="hidden" name="planStartdate" value="" class="inputText date"/>
			    	<input type="hidden" name="planEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="planInrate" value=""/>
			    	<input type="hidden" name="preStartdate" value="" class="inputText date"/>
			    	<input type="hidden" name="preEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="comments" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${manufQuote.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="manufQuoteDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">代加工物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">编码规则: </th>
				<td><input type="text" name="materialCodenotation" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">代加工物品名称: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="materialAttribute" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
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
				<th width="20%">单位加工费用: </th>
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
				<th width="20%">计划完工时间: </th>
				<td><input type="text" name="planEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">计划入库率下限: </th>
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
