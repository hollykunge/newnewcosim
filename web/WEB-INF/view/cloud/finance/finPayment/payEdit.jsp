<%--
	time:2013-08-23 10:32:22
	desc:edit the cloud_finance_paymentbill
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_finance_paymentbill</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#finPaymentForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
 
		function add_supp(){
			CommonDialog("ware_finStatement",
					function(order) {
				
				
				var purids = "";
				var purcode = "";
				for(var i = 0; i<order.length;i++){
					var oid = order[i].id +",";
					var ocd = order[i].code + ",";
					purids += oid;
					purcode += ocd;
				}
				$("#sourceformId").val(purids);
				$("#sourceformCode").val(purcode);
						 
					 
						 
						$("#rows").empty();
						$.ajax({
							type : 'post',
							dataType : 'json',
							url : '${ctx}/cloud/finance/FinGathering/getOrderDetails.ht',
							data : {value : purids},
							success : function(rows){
								//判断是否有值
								if(rows.length>0){
									for(var i=0;i<rows.length;i++){
										var serialnumber = i + 1 ;
										var materielId=rows[i].materielId;
										var materialcode = rows[i].materielCode;
										var materialname = rows[i].materielName;
										var attributedes = rows[i].attributeNote;
										var units = rows[i].unite;
										var receivedamount = rows[i].orderNum2;
										var price = rows[i].price;
										var sumprice = rows[i].sumPrise;
											if(materielId==null){
											materielId="";
										}
										if(materialcode==null){
											materialcode="";
										}
										if(materialname==null){
											materialname="";
										}
										if(attributedes==null){
											attributedes="";
										}
										if(units==null){
											units="";
										}
										if(receivedamount==null){
											receivedamount="";
										}
										if(price==null){
											price="";
										}
										if(sumprice==null){
											sumprice="";
										}
										appendRow(materielId,serialnumber,materialcode,attributedes,units,materialname,receivedamount,price,sumprice);
									};
								}
							}
						});
			});
		}
		function appendRow(materielId,serialnumber,materialcode,attributedes,units,materialname,receivedamount,price,sumprice){
			var str = '<tr type="subdata">';
			str += '<td><input type="checkbox" name="check" value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="materielId"  value="'+materielId+'"/></td>';
			str += '<td><input style="width: 80px;" type="text" name="invoice"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="product"  value="'+materialname+'"/></td>';
			str += '<td><input style="width: 80px;" type="text" name="spec"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="unite"  value="'+units+'"/></td>';
			//str += '<td><input style="width: 50px;" type="text" name="consignmentNum"  value=""/></td>';
			str += '<td><input style="width: 50px;" type="text" name="receiveNum"  value="'+receivedamount+'"  onblur="getSumPrice(this);"/></td>';
			//str += '<td><input style="width: 50px;" type="text" name="checkNum"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="price"  value="'+price+'"  onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 50px;" type="text" name="zk"  value="" onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 80px;"  readonly type="text" name="sumPrice"  value="'+sumprice+'"  onblur="getSumPrice(this);"/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="balancePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="invoicePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="disbursePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="nonPay"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="currentPrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="consignmentDate"  value="<fmt:formatDate value='${finPaymentDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" "/></td>';
			str += '<td><input style="width: 80px;" type="text" name="arriveDate"  value="<fmt:formatDate value='${finPaymentDetailItem.arriveDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="sellBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="balanceBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="arriveBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="remark"  value=""/></td>';
			str += '</tr>';
			$('#rows').append(str);
		}
		//增加一行
		function add_onesupps(){
			var str = '<tr type="subdata">';
			str += '<td><input type="checkbox" name="check" value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="invoice"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="product"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="spec"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="unite"  value=""/></td>';
			//str += '<td><input style="width: 50px;" type="text" name="consignmentNum"  value=""/></td>';
			str += '<td><input style="width: 50px;" type="text" name="receiveNum"  value=""  onblur="getSumPrice(this);"/></td>';
			//str += '<td><input style="width: 50px;" type="text" name="checkNum"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="price"  value="" onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 80px;" readonly type="text" name="sumPrice"  value=""  onblur="getSumPrice(this);"/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="balancePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="invoicePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="disbursePrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="nonPay"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="currentPrice"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="consignmentDate"  value="<fmt:formatDate value='${finPaymentDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" "/></td>';
			str += '<td><input style="width: 80px;" type="text" name="arriveDate"  value="<fmt:formatDate value='${finPaymentDetailItem.arriveDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" "/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="sellBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="balanceBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="arriveBill"  value=""/></td>';
			//str += '<td><input style="width: 80px;" type="text" name="remark"  value=""/></td>';
			str += '</tr>';
			$('#rows').append(str);
    	}
	    	
	    	
	    function delproduct(){
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
		}
		/**
		*全选/反选
		*/
	 	function checkall() {
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					 $("[name=check]:checkbox").attr('checked', false);
				}else{
					 $("[name=check]:checkbox").attr('checked', true);
				}
			});
		}
	
	
	function add_info(){
		CommonDialog("org_info_list",
				function(info){
				$("#payeeenterpName").val(info.NAME);
				$("#payeeenterpId").val(info.SYS_ORG_INFO_ID);
		});
		
	}
	function getSumPrice(obj) {
			//物品ID
		var materialId=$(obj).closest("tr").find('input[name="materielId"]').val();
		var number=$(obj).closest("tr").find('input[name="receiveNum"]').val();
		var date = $('#operateDate').val();
		if(materialId==0||materialId==null){
			$.ligerMessageBox.alert("提示信息","未获取到物品ID");
			return;
		}
		if(number==0||number==null){
			$.ligerMessageBox.alert("提示信息","未获取到物品数量");
			return;
		}
		//收款企业ID
		var enterpriseId=$('#payeeenterpId').val();
		if(enterpriseId==0||enterpriseId==null){
			$.ligerMessageBox.alert("提示信息","未获取到收款企业ID");
			return;
		}
		//付款企业ID
		var payenterpId=$('#payenterpId').val();
		if(payenterpId==0||payenterpId==null){
			
			$.ligerMessageBox.alert("提示信息","请选择付款企业");
			return;
		}
		var discount=0;
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/cloud/config/priceStrategy/getDiscount.ht',
			data : {materialId : materialId,enterpriseId:enterpriseId,payenterpId:payenterpId,number:number,date:date},
			success : function(rows){
				//判断是否有值
				if(rows.length>0){
					for(var i=0;i<rows.length;i++){
						  discount=rows[i].discount;
					}
				}else{
					discount=1;
				}
				$(obj).closest("tr").find('input[name="zk"]').val(discount);
				 
				var price=$(obj).closest("tr").find('input[name="price"]').val();
var orderNum=$(obj).closest("tr").find('input[name="receiveNum"]').val();
if(!isNaN(price)&&!isNaN(orderNum)){
var sumPrice=$(obj).closest("tr").find('input[name="sumPrice"]').val();
if(price!=""&&orderNum!=""){
	sumPrice=orderNum*price*discount;
	price=parseFloat(price);
	$(obj).closest("tr").find('input[name="sumPrice"]').val(sumPrice.toFixed(2));
	$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
}
}else{
$.ligerMessageBox.alert("请输入数字!");
}
			}
		});
	}	
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		  
			        <span class="tbar-label">添加应付单据</span>
		 
		</div>
		 
	</div>
	<div class="panel-body">
		<form id="finPaymentForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${finPayment.code}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${finPayment.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${finPayment.operaterName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">付款类型: </th>
					<td>
					<select name="payType" id="payType" class="inputText" style="width: 128px;">
							<c:remove var="checked"/>
							<c:if test="${finPayment.payType==null || finPayment.payType==''}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="" ${checked}>请选择</option>
							
							<c:remove var="checked"/>
							<c:if test="${finPayment.payType=='付货款'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="付货款" ${checked}>付货款</option>
							<c:remove var="checked"/>
							<c:if test="${finPayment.payType=='预付款'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="预付款" ${checked}>预付款</option>
						</select>
					
					<!--<input type="text" id="payType" name="payType" value="${finPayment.payType}"  class="inputText" validate="{required:false,maxlength:36}"  />-->
					</td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="采购结算"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${finPayment.sourceformCode}"  class="inputText" validate="{required:true,maxlength:512}"  />
						<font  id="div_show"><a href="javascript:void(0)" onclick="add_supp()"  class="link detail">选择</a></font></td>
				</tr>
				<tr>
					<th width="20%">付款企业名称: </th>
					<td><input type="text" id="payenterpName" name="payenterpName" value="${finPayment.payenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">付款人: </th>
					<td><input type="text" id="payenterpUsername" name="payenterpUsername" value="${finPayment.payenterpUsername}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收款企业名称: </th>
					<td><input type="text" id="payeeenterpName" name="payeeenterpName" value="${finPayment.payeeenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="add_info()"  class="link detail">选择</a></td>
					<th width="20%">收款人: </th>
					<td><input type="text" id="payeeenterpUsername" name="payeeenterpUsername" value="${finPayment.payeeenterpUsername}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">付款日期: </th>
					<td><input type="text" id="payDate" name="payDate" value="<fmt:formatDate value='${finPayment.payDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">付款方式: </th>
					<td>
					<select name="payMode" id="payMode" class="inputText" style="width: 128px;">
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode==null || finPayment.payMode==''}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="" ${checked}>请选择</option>
						
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode=='现金'}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="现金" ${checked}>现金</option>
						
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode=='银行转帐'}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="银行转帐" ${checked}>银行转帐</option>
						
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode=='汇款'}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="汇款" ${checked}>汇款</option>
						
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode=='支票'}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="支票" ${checked}>支票</option>
						
						<c:remove var="checked"/>
						<c:if test="${finPayment.payMode=='本票'}">
							<c:set var="checked" value='selected="selected"'></c:set>
						</c:if>
						<option value="本票" ${checked}>本票</option>
					</select>
					
					<!--<input type="text" id="payMode" name="payMode" value="${finPayment.payMode}"  class="inputText" validate="{required:false,maxlength:36}"  />-->
					</td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${finPayment.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">汇率: </th>
					<td><input type="text" id="exchangeRate" name="exchangeRate" value="${finPayment.exchangeRate}"  class="inputText" validate="{required:false,maxlength:36}"  /></td>
				</tr>
				<tr>
					<th width="20%">支付银行: </th>
					<td><input type="text" id="gatherBank" name="gatherBank" value="${finPayment.gatherBank}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
					<th width="20%">支付账号: </th>
					<td><input type="text" id="gatherNumber" name="gatherNumber" value="${finPayment.gatherNumber}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">付款银行: </th>
					<td><input type="text" id="payenterpBank" name="payenterpBank" value="${finPayment.payenterpBank}"  class="inputText" validate="{required:false,maxlength:192}"  /></td>
					<th width="20%">付款账号: </th>
					<td><input type="text" id="payenterpNumber" name="payenterpNumber" value="${finPayment.payenterpNumber}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">付款金额: </th>
					<td><input type="text" id="payPrice" name="payPrice" value="${finPayment.payPrice}"  class="inputText" validate="{required:true,number:true }"  /></td>
					<th width="20%">票据号: </th>
					<td><input type="text" id="noteCode" name="noteCode" value="${finPayment.noteCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">备注: </th>
					<td colspan="3">
					
					<textarea cols="95" rows="3" id="remark" name="remark" class="inputText" validate="{required:false,maxlength:768}"  >${finPayment.remark}</textarea></td>
				</tr>
				<tr>
					<!--<th width="20%">收款人ID: </th>-->
					<input type="hidden" id="payeeenterpUserid" name="payeeenterpUserid" value="${finPayment.payeeenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
					<input type="hidden" id="state" name="state" value="生效"   />
					<!--<th width="20%">收款企业ID: </th>-->
					<input type="hidden" id="payeeenterpId" name="payeeenterpId" value="${finPayment.payeeenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款人id: </th>-->
					<input type="hidden" id="payenterpUserid" name="payenterpUserid" value="${finPayment.payenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款企业ID: </th>-->
					<input type="hidden" id="payenterpId" name="payenterpId" value="${finPayment.payenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">来源单据ID: </th>-->
					<input type="hidden" id="sourceformId" name="sourceformId" value="${finPayment.sourceformId}"  class="inputText" validate="{required:false,maxlength:512 }"  />
					<!--<th width="20%">制单人ID: </th>-->
					<input type="hidden" id="operaterId" name="operaterId" value="${finPayment.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="finPaymentDetail" formType="window" type="sub">
				<tr>
					<td colspan="21">
						<div class="group" align="left" style="display:none">
								<a href="javascript:void(0)" onclick="add_onesupps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd3.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd4.jpg'"
												onmouseout="src='${ctx}/images/iconadd3.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
								  <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
			    		</div>
			    		<div align="center">应付单据详细信息</div>
		    		</td>
				</tr>
				<tr>
					<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th>物品ID</th>
				<th>发票号</th>
				<th>产品</th>
				<th>规格</th>
				<th>单位</th>
				<th style="display:none">发货数量</th>
				<th>接收数量</th>
				<th style="display:none">合格品数量</th>
				<th>单价</th>
				<th>折扣</th>
				<th>金额</th>
				<th style="display:none">结算金额</th>
				<th style="display:none">开票金额</th>
				<th style="display:none">已付金额</th>
				<th style="display:none">未付金额</th>
				<th style="display:none">本次付款额</th>
				<th style="display:none">发货日期</th>
				<th>到货日期</th>
				<th style="display:none">销售订单号</th>
				<th style="display:none">结算单号</th>
				<th style="display:none">到货单号</th>
				<th style="display:none">备注</th>
					<th style="display:none">操作</th>
				</tr>
				<tbody id="rows">
				<c:forEach items="${finPaymentDetailList}" var="finPaymentDetailItem" varStatus="status">
				    <tr type="subdata">
				    	<td style="text-align: center"></td>
					    <td style="text-align: center" name="invoice">${finPaymentDetailItem.invoice}</td>
					    <td style="text-align: center" name="product">${finPaymentDetailItem.product}</td>
					    <td style="text-align: center" name="spec">${finPaymentDetailItem.spec}</td>
					    <td style="text-align: center" name="unite">${finPaymentDetailItem.unite}</td>
					    <td style="display:none" name="consignmentNum">${finPaymentDetailItem.consignmentNum}</td>
					    <td style="text-align: center" name="receiveNum">${finPaymentDetailItem.receiveNum}</td>
					    <td style="display:none" name="checkNum">${finPaymentDetailItem.checkNum}</td>
					    <td style="text-align: center" name="price">${finPaymentDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${finPaymentDetailItem.sumPrice}</td>
					    <td style="display:none" name="balancePrice">${finPaymentDetailItem.balancePrice}</td>
					    <td style="display:none" name="invoicePrice">${finPaymentDetailItem.invoicePrice}</td>
					    <td style="display:none" name="disbursePrice">${finPaymentDetailItem.disbursePrice}</td>
					    <td style="display:none" name="nonPay">${finPaymentDetailItem.nonPay}</td>
					    <td style="display:none" name="currentPrice">${finPaymentDetailItem.currentPrice}</td>
						<td style="display:none" name="consignmentDate"><fmt:formatDate value='${finPaymentDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="arriveDate"><fmt:formatDate value='${finPaymentDetailItem.arriveDate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="display:none" name="sellBill">${finPaymentDetailItem.sellBill}</td>
					    <td style="display:none" name="balanceBill">${finPaymentDetailItem.balanceBill}</td>
					    <td style="display:none" name="arriveBill">${finPaymentDetailItem.arriveBill}</td>
					    <td style="display:none" name="remark">${finPaymentDetailItem.remark}</td>
					    <td style="display:none">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="invoice" value="${finPaymentDetailItem.invoice}"/>
						<input type="hidden" name="product" value="${finPaymentDetailItem.product}"/>
						<input type="hidden" name="spec" value="${finPaymentDetailItem.spec}"/>
						<input type="hidden" name="unite" value="${finPaymentDetailItem.unite}"/>
						<input type="hidden" name="consignmentNum" value="${finPaymentDetailItem.consignmentNum}"/>
						<input type="hidden" name="receiveNum" value="${finPaymentDetailItem.receiveNum}"/>
						<input type="hidden" name="checkNum" value="${finPaymentDetailItem.checkNum}"/>
						<input type="hidden" name="price" value="${finPaymentDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${finPaymentDetailItem.sumPrice}"/>
						<input type="hidden" name="balancePrice" value="${finPaymentDetailItem.balancePrice}"/>
						<input type="hidden" name="invoicePrice" value="${finPaymentDetailItem.invoicePrice}"/>
						<input type="hidden" name="disbursePrice" value="${finPaymentDetailItem.disbursePrice}"/>
						<input type="hidden" name="nonPay" value="${finPaymentDetailItem.nonPay}"/>
						<input type="hidden" name="currentPrice" value="${finPaymentDetailItem.currentPrice}"/>
					    <input type="hidden" name="consignmentDate" value="<fmt:formatDate value='${finPaymentDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="arriveDate" value="<fmt:formatDate value='${finPaymentDetailItem.arriveDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="sellBill" value="${finPaymentDetailItem.sellBill}"/>
						<input type="hidden" name="balanceBill" value="${finPaymentDetailItem.balanceBill}"/>
						<input type="hidden" name="arriveBill" value="${finPaymentDetailItem.arriveBill}"/>
						<input type="hidden" name="remark" value="${finPaymentDetailItem.remark}"/>
				    </tr>
				</c:forEach>
				</tbody>
				<tr type="append">
			    	<td style="text-align: center" name="invoice"></td>
			    	<td style="text-align: center" name="product"></td>
			    	<td style="text-align: center" name="spec"></td>
			    	<td style="text-align: center" name="unite"></td>
			    	<td style="display:none" name="consignmentNum"></td>
			    	<td style="text-align: center" name="receiveNum"></td>
			    	<td style="display:none" name="checkNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
			    	<td style="display:none" name="balancePrice"></td>
			    	<td style="display:none" name="invoicePrice"></td>
			    	<td style="display:none" name="disbursePrice"></td>
			    	<td style="display:none" name="nonPay"></td>
			    	<td style="display:none" name="currentPrice"></td>
					<td style="display:none" name="consignmentDate"></td>								
					<td style="text-align: center" name="arriveDate"></td>								
			    	<td style="display:none" name="sellBill"></td>
			    	<td style="display:none" name="balanceBill"></td>
			    	<td style="display:none" name="arriveBill"></td>
			    	<td style="display:none" name="remark"></td>
			    	<td style="display:none">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="invoice" value=""/>
			    	<input type="hidden" name="product" value=""/>
			    	<input type="hidden" name="spec" value=""/>
			    	<input type="hidden" name="unite" value=""/>
			    	<input type="hidden" name="consignmentNum" value=""/>
			    	<input type="hidden" name="receiveNum" value=""/>
			    	<input type="hidden" name="checkNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="balancePrice" value=""/>
			    	<input type="hidden" name="invoicePrice" value=""/>
			    	<input type="hidden" name="disbursePrice" value=""/>
			    	<input type="hidden" name="nonPay" value=""/>
			    	<input type="hidden" name="currentPrice" value=""/>
			    	<input type="hidden" name="consignmentDate" value="" class="inputText date"/>
			    	<input type="hidden" name="arriveDate" value="" class="inputText date"/>
			    	<input type="hidden" name="sellBill" value=""/>
			    	<input type="hidden" name="balanceBill" value=""/>
			    	<input type="hidden" name="arriveBill" value=""/>
			    	<input type="hidden" name="remark" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${finPayment.id}" />
		</form>
		
	</div>
	<form id="finPaymentDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">发票号: </th>
				<td><input type="text" name="invoice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">产品: </th>
				<td><input type="text" name="product" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">规格: </th>
				<td><input type="text" name="spec" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unite" value=""  class="inputText" validate="{required:false,maxlength:72}"/></td>
			</tr>
			<tr>
				<th width="20%">发货数量: </th>
				<td><input type="text" name="consignmentNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">接受数量: </th>
				<td><input type="text" name="receiveNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">合格品数量: </th>
				<td><input type="text" name="checkNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">金额: </th>
				<td><input type="text" name="sumPrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">结算金额: </th>
				<td><input type="text" name="balancePrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">开票金额: </th>
				<td><input type="text" name="invoicePrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">已付金额: </th>
				<td><input type="text" name="disbursePrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">未付金额: </th>
				<td><input type="text" name="nonPay" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">本次付款额: </th>
				<td><input type="text" name="currentPrice" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">发货日期: </th>
				<td><input type="text" name="consignmentDate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">到货日期: </th>
				<td><input type="text" name="arriveDate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">销售订单号: </th>
				<td><input type="text" name="sellBill" value=""  class="inputText" validate="{required:false,maxlength:72}"/></td>
			</tr>
			<tr>
				<th width="20%">结算单号: </th>
				<td><input type="text" name="balanceBill" value=""  class="inputText" validate="{required:false,maxlength:72}"/></td>
			</tr>
			<tr>
				<th width="20%">到货单号: </th>
				<td><input type="text" name="arriveBill" value=""  class="inputText" validate="{required:false,maxlength:72}"/></td>
			</tr>
			<tr>
				<th width="20%">备注: </th>
				<td><input type="text" name="remark" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
