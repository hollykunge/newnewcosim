<%--
	time:2013-04-13 19:06:38
	desc:edit the cloud_pur_optimize
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>
	<title>编辑 采购优选单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#purOptimizeForm').form();
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
						window.location.href = "${ctx}/cloud/purchase/purOptimize/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
// 		$("a.apply").click(function() {
// 			frm.setData();
// 			$('#purOptimizeForm').attr('action','apply.ht');
// 			frm.ajaxForm(options);
// 			if(frm.valid()){
// 				form.submit();
// 			}
// 		});

// 		$("a.agree").click(function() {
			
// 			operatorType=1;
// 			var tmp = '1';
// 			if(isDirectComlete){//直接一票通过
// 				tmp = '5';
// 			}
// 			$('#voteAgree').val(tmp);
// 			$('#back').val("1");

// 			$('#purOptimizeForm').attr("action","complete.ht");
// 			frm.setData();
// 			frm.ajaxForm(options);
// 			if(frm.valid()){
// 				form.submit();
// 			}
// 		});
	
// 		$("a.notAgree").click(function() {
			
// 			operatorType=2;
// 			var tmp = '2';
// 			/*if(isDirectComlete){//直接一票通过
// 				tmp = '6';
// 			}*/
// 			$('#voteAgree').val(tmp);
// 			$('#back').val("1");
			
// 			$('#purOptimizeForm').attr("action","complete.ht");
// 			frm.setData();
// 			frm.ajaxForm(options);
// 			if(frm.valid()){
// 				form.submit();
// 			}
			
// 			/*进入页面时，默认第一个中标供应商被选中*/
// 			$("input[name='isCheck']").click(function(){
// 				alert('1111111111');
// 			});
// 		});
		
		function selectedProvider(quoteId){
			$.ajax({
				   type: "POST",
				   url:  "${ctx}/cloud/purchase/purOptimize/selectedProvider.ht",
				   data: "quoteId=" + quoteId,
				   success: function(msg){
				    	$('#supplierName').val(msg.supplierName);
				    	$('#supplierId').val(msg.supplierId);
				    	$('#quoteformId').val(msg.quoteformId);
				    	$('#quoteformCode').val(msg.quoteformCode);
				   },
				   error:function(){
					   alert("error");
				   }
				});
		}
	</script>
	
	<style type="text/css">
		.myth{
		    background-color: #EBF5FF;
		    border: 1px solid #7BABCF;
		    font-size: 12px;
		    width:20%;
		    height: 32px;
		    padding-right: 6px;
		    text-align: right;
		}
	</style>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${purOptimize.id !=null}">
			        <span class="tbar-label">编辑采购优选单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加采购优选单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
<!-- 		<div class="panel-toolbar"> -->
<!-- 			<div class="toolBar"> -->
<%-- 				<c:if test="${applyFlag==0}"> --%>
<!-- 					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div> -->
<!-- 					<div class="l-bar-separator"></div> -->
<!-- 					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div> -->
<!-- 					<div class="l-bar-separator"></div> -->
<!-- 					<div class="group"><a class="link back" href="list.ht">返回</a></div> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${applyFlag==1}"> --%>
<!-- 					<div class="group"><a id="btnAgree" class="link agree">同意</a></div> -->
<!-- 					<div class="l-bar-separator"></div> -->
<!-- 					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div> -->
<%-- 				</c:if> --%>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
	<div class="panel-body">
		<form id="purOptimizeForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			<input type="hidden" id="runid" name="runid" value="${purOptimize.runid}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr >
					<th width="20%">单证号: </th>
					<td><input  readonly class="r"  type="text" id="code" name="code" value="${purOptimize.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input  readonly class="r"  type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${purOptimize.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人: </th>
					<td><input readonly class="r"  type="text" id="operatorName" name="operatorName" value="${purOptimize.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">来源单据ID: </th>
					<td><input readonly class="r"  type="text" id="sourceformId" name="sourceformId" value="${purOptimize.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td><input  readonly class="r"  type="text" id="sourceformCode" name="sourceformCode" value="${purOptimize.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">处理截止日期: </th>
					<td><input  readonly class="r"  type="text" id="doofferEndtime" name="doofferEndtime" value="<fmt:formatDate value='${purOptimize.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">制单人ID: </th>
					<td><input  readonly class="r"  type="text" id="operatorId" name="operatorId" value="${purOptimize.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input  readonly class="r"  type="text" id="sourceformType" name="sourceformType" value="${purOptimize.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					
					<th width="20%">中标供应商名: </th>
					<td><input  readonly class="r"  type="text" id="supplierName" name="supplierName"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">中标供应商ID: </th>
					<td><input  readonly class="r"  type="text" id="supplierId" name="supplierId"   class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">中标报价单ID: </th>
					<td><input  readonly class="r"  type="text" id="quoteformId" name="quoteformId"   class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">中标报价单号: </th>
					<td><input  readonly class="r"  type="text" id="quoteformCode" name="quoteformCode"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
		<fieldset>
		<legend>询价详情</legend>
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0"> 
			<tr>
				<th width="20%">单号:</th>
				<td>${purEnquiry.code}</td>
			
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${purEnquiry.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th width="20%">报价截止日期:</th>
				<td>
				<fmt:formatDate value="${purEnquiry.offerEndtime}" pattern="yyyy-MM-dd"/>
				</td>
				<th width="20%">制单人:</th>
				<td>${purEnquiry.operatorName}</td>
			</tr>
			<tr>
				<th width="20%">总金额:</th>
				<td>${purEnquiry.sumPrice}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<th>物品名称</th>
				<th>单位</th>
				<th>数量</th>
			</tr>	
			<c:forEach items="${purEnquiryDetailList}" var="purEnquiryDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="detailId" name="detailId" value="${purEnquiryDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${purEnquiryDetailItem.materielName}</td>								
						<td style="text-align: center">${purEnquiryDetailItem.unit}</td>								
						<td style="text-align: center">${purEnquiryDetailItem.orderNum}</td>								
				</tr>
			</c:forEach>
		</table>
		</fieldset>

<fieldset>
			
	<legend>供应商报价明细</legend>
	<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOptimizeDetail" formType="window" type="sub">			
		<c:forEach items="${saleQuoteList}"  var="saleQuote">
			<tr><td colspan="8">
			<c:if test="${saleQuote.record!='1'}">
			            设置该价格为中标价格:
			    <input type="radio"  name="isCheck" value="${saleQuote.id}" onclick="selectedProvider(${saleQuote.id})" validate="{required:true}">
			</c:if>
			<c:if test="${saleQuote.record=='1'}">
			   	 该记录为历史投标价格
			</c:if>
			</td>
			</tr>
				<tr>
					<td class="myth">单证号:</th>
					<td>${saleQuote.code}</td>
					<td class="myth">制单日期:</th>
					<td colspan="2">
						<fmt:formatDate value="${saleQuote.operateDate}" pattern="yyyy-MM-dd"/>
					</td>					
					<td class="myth">预付款:</th>
					<td colspan="2">${saleQuote.advancePay}</td>
				</tr>
				<tr>	
					<td class="myth">报价单号:</th>
					<td>${saleQuote.code}</td>	
					<td class="myth">报价企业:</th>
					<td colspan="2">${saleQuote.enterpriseName}</td>
					<td class="myth">预付款说明:</th>
					<td colspan="2">${saleQuote.advancepayNotes}</td>
				</tr>
	
				<tr>
					<th>物品名称</th>
					<th>物品规格</th>
					<th>单位</th>
					<th>数量</th>
					<th>单位报价</th>
					<th>总金额</th>
					<th>要求发货时间</th>
				    <th>预计发货时间</th>				
				</tr>	
				<c:forEach items="${saleQuote.saleQuoteDetailList}" var="saleQuoteDetailItem" varStatus="status">
					 <tr type="subdata">				
						<td style="text-align: center" name="materielName">${saleQuoteDetailItem.materielName}</td>	
						<td style="text-align: center" name="materielName">${saleQuoteDetailItem.model}</td>								
						<td style="text-align: center" name="unit">${saleQuoteDetailItem.unit}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.orderNum}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.price}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.sumPrice}</td>												
						<td style="text-align: center"><fmt:formatDate value='${saleQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${saleQuoteDetailItem.predeliverDate}' pattern='yyyy-MM-dd'/></td>								
					
						<input type="hidden" name="supplierId" value="${saleQuote.enterpriseId}"/>
						<input type="hidden" name="supplierName" value="${saleQuote.enterpriseName}"/>
						<input type="hidden" name="materielIndex" value="${purOptimizeDetailItem.materielIndex}"/>
						<input type="hidden" name="materielId" value="${saleQuoteDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${saleQuoteDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${saleQuoteDetailItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${saleQuoteDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${saleQuoteDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${saleQuoteDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${saleQuoteDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${saleQuoteDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${saleQuoteDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${saleQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="predeliveryEnddate" value="<fmt:formatDate value='${saleQuoteDetailItem.predeliverDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>				
						<input type="hidden" name="resulte" value="${purOptimizeDetailItem.resulte}"/>
						<input type="hidden" name="supplierCode" value="${purOptimizeDetailItem.supplierCode}"/>
						<input type="hidden" name="resulteNote" value="${purOptimizeDetailItem.resulteNote}"/>
						<input type="hidden" name="quoteformId" value="${saleQuote.id}"/>
						<input type="hidden" name="quoteformCode" value="${saleQuote.code}"/>
					</tr>
				</c:forEach>

		</c:forEach>
	</table>
			
	</fieldset>
			<input type="hidden" name="id" value="${purOptimize.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
<%-- 			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/> --%>
</form>	
</div>
</body>
</html>
