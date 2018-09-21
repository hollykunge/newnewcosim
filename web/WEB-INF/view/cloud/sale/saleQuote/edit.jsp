<%--
	time:2013-04-13 19:06:38
	desc:edit the 销售报价单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 销售报价单</title>
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
			var frm=$('#saleQuoteForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
	 	function getSumPrice(obj) {
			var price=$(obj).closest("form").find('input[name="price"]').val();
			var orderNum=$(obj).closest("form").find('input[name="orderNum"]').val();
			if(price!=""&&orderNum!=""){
				var a=orderNum*price;
				price=parseFloat(price);
				$(obj).closest("form").find('input[name="sumPrice"]').val(a.toFixed(2));
				$(obj).closest("form").find('input[name="price"]').val(price.toFixed(2));
			}
    	}
		
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/sale/saleQuote/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleQuoteForm').attr('action','apply.ht');
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

			$('#saleQuoteForm').attr("action","complete.ht");
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
			
			$('#saleQuoteForm').attr("action","complete.ht");
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
			    <c:when test="${saleQuote.id !=null}">
			        <span class="tbar-label">编辑销售报价单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加销售报价单</span>
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
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="saleQuoteForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">报价单编码: </th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${saleQuote.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">制单人ID: </th>
					<td><input size="35" type="text" id="operatorId" name="operatorId" value="${saleQuote.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleQuote.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="operatorName" name="operatorName" value="${saleQuote.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">报价截止日期: </th>
					<td><input size="35" type="text" id="offerEndtime" name="offerEndtime" value="<fmt:formatDate value='${saleQuote.offerEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">预付款: </th>
					<td><input size="35" type="text" id="advancePay" name="advancePay" value="${saleQuote.advancePay}"  class="inputText" validate="{required:false}"  /></td>
					<th style="width:120px">预付款说明: </th>
					<td><input size="35" type="text" id="advancepayNotes" name="advancepayNotes" value="${saleQuote.advancepayNotes}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<td><input size="35" type="hidden" id="sourceformId" name="sourceformId" value="${saleQuote.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">来源单据类型: </th>
					<td><input size="35" type="text" id="sourceformType" name="sourceformType" value="${saleQuote.sourceformType}"  class="inputText" validate="{required:false,maxlength:32 }"  /></td>
					<th style="width:120px">来源单据编码: </th>
					<td><input size="35" type="text" id="sourceformCode" name="sourceformCode" value="${saleQuote.sourceformCode}"  class="inputText" validate="{required:false,maxlength:32}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">采购企业ID: </th>
					<td><input size="35" type="text" id="purenterpId" name="purenterpId" value="${saleQuote.purenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">采购企业: </th>
					<td><input size="35" type="text" id="purenterpName" name="purenterpName" value="${saleQuote.purenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">采购企业联系人ID: </th>
					<td><input size="35" type="text" id="purenterpUserid" name="purenterpUserid" value="${saleQuote.purenterpUserid}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">采购企业联系人姓名: </th>
					<td><input size="35" type="text" id="purenterpUsername" name="purenterpUsername" value="${saleQuote.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">币种: </th>
					<td>
					<select id="currencyType" name="currencyType">
					    <c:remove var="selected"/>
					    <c:if test="${saleQuote.currencyType=='RMB'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='RMB' ${selected} >人&nbsp;民&nbsp;币</option>
						<c:remove var="selected"/>
					    <c:if test="${saleQuote.currencyType=='$'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='$' ${selected} >美&nbsp;元</option>
					</select>
					<th style="width:120px">运费承担方: </th>
					<td><input size="35" type="text" id="freightBearer" name="freightBearer" value="${saleQuote.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">销售企业ID: </th>
					<td><input size="35" type="text" id="enterpriseId" name="enterpriseId" value="${saleQuote.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">销售企业: </th>
					<td><input size="35" type="text" id="enterpriseName" name="enterpriseName" value="${saleQuote.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">询价单发布日期: </th>
					<td colspan="3"><input size="35" type="text" id="purenqReleasedate" name="purenqReleasedate" value="<fmt:formatDate value='${saleQuote.purenqReleasedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				
				
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleQuoteDetail" formType="window" type="sub">
				<tr>
					<td colspan="12">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						销售报价单 ：销售报价单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>物品ID</th>
					<th>物品名</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总价</th>
					<th>要求发货时间</th>
					<th>预计发货时间</th>
					<th>物品编码</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${saleQuoteDetailList}" var="saleQuoteDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materielId">${saleQuoteDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielName">${saleQuoteDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${saleQuoteDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${saleQuoteDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${saleQuoteDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${saleQuoteDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${saleQuoteDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${saleQuoteDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${saleQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="predeliverDate"><fmt:formatDate value='${saleQuoteDetailItem.predeliverDate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="materielCode">${saleQuoteDetailItem.materielCode}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="materielId" value="${saleQuoteDetailItem.materielId}"/>
						<input size="35" type="hidden" name="materielName" value="${saleQuoteDetailItem.materielName}"/>
						<input size="35" type="hidden" name="materielLev" value="${saleQuoteDetailItem.materielLev}"/>
						<input size="35" type="hidden" name="attributeInfo" value="${saleQuoteDetailItem.attributeInfo}"/>
						<input size="35" type="hidden" name="unit" value="${saleQuoteDetailItem.unit}"/>
						<input size="35" type="hidden" name="orderNum" value="${saleQuoteDetailItem.orderNum}"/>
						<input size="35" type="hidden" name="price" value="${saleQuoteDetailItem.price}"/>
						<input size="35" type="hidden" name="sumPrice" value="${saleQuoteDetailItem.sumPrice}"/>
					    <input size="35" type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${saleQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input size="35" type="hidden" name="predeliverDate" value="<fmt:formatDate value='${saleQuoteDetailItem.predeliverDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input size="35" type="hidden" name="materielCode" value="${saleQuoteDetailItem.materielCode}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materielId"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
					<td style="text-align: center" name="predeliverDate"></td>								
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="materielId" value=""/>
			    	<input size="35" type="hidden" name="materielName" value=""/>
			    	<input size="35" type="hidden" name="materielLev" value=""/>
			    	<input size="35" type="hidden" name="attributeInfo" value=""/>
			    	<input size="35" type="hidden" name="unit" value=""/>
			    	<input size="35" type="hidden" name="orderNum" value=""/>
			    	<input size="35" type="hidden" name="price" value=""/>
			    	<input size="35" type="hidden" name="sumPrice" value=""/>
			    	<input size="35" type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input size="35" type="hidden" name="predeliverDate" value="" class="inputText date"/>
			    	<input size="35" type="hidden" name="materielCode" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${saleQuote.id}" />
			<input size="35" type="hidden" id="back" name="back" value=""/>
			<input size="35" type="hidden" name="formData" id="formData" />
			<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="saleQuoteDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">物品ID: </th>
				<td><input size="35" type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">物品名: </th>
				<td><input size="35" type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th style="width:120px">物品分类: </th>
				<td><input size="35" type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">属性说明: </th>
				<td><input size="35" type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th style="width:120px">单位: </th>
				<td><input size="35" type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">订单数量: </th>
				<td><input size="35" type="text" name="orderNum" value=""  onblur="getSumPrice(this);" class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">单价: </th>
				<td><input size="35" type="text" name="price" value="" onblur="getSumPrice(this);"  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th style="width:120px">总价: </th>
				<td><input size="35" type="text" name="sumPrice" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th style="width:120px">要求发货时间: </th>
				<td><input size="35" type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th style="width:120px">预计发货时间: </th>
				<td><input size="35" type="text" name="predeliverDate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th style="width:120px">物品编码: </th>
				<td><input size="35" type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:192}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
