<%--
	time:2013-05-06 13:02:00
	desc:edit the cloud_pur_order
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>
	<title>编辑采购订单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
	function add_archive(){
		CommonDialog("warehouse_choose",
	    		function(data) {
	    			var row=data;
    				$('input[name="receivewarehouseName"]').val(row.warehousename);
    				$('input[name="receivewarehouseAddress"]').val(row.address);
    				$('input[name="receiverName"]').val(row.contactname);
    				$('input[name="receiverPhone"]').val(row.contactphone);
    				$('input[name="postcode"]').val(row.zipcode);
    				$('input[name="receiverMail"]').val(row.contactemail);
	    		});
	    	}
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#purOrderForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		function order_pay(){
			  var pay = $("#pay_Type").find("option:selected").text();
				$('#payType').val(pay);
		}
		function order_bill(){
			  var pay = $("#bill_Type").find("option:selected").text();
				$('#billType').val(pay);
		}
		//选择任务执行用户（可多选）
		function changeTaskUser(){
			//显示用户选择器
			UserDialog({
				isSingle:false,
				callback:function(userId,fullname){
					if(userId=='' || userId==null || userId==undefined){ 
						return;
					}else{
						$("#invitedUserIds").val(userId);
						$("#invitedUserNames").val(fullname);
					}
					
				}
			});
		}
		function getSumPrice(obj) {
			var price=$(obj).closest("tr").find('input[name="bargain"]').val();
			var orderNum=$(obj).closest("tr").find('input[name="orderNum"]').val();
			if(!isNaN(price)&&!isNaN(orderNum)){
			var total=$('input[name="sumPrice"]').val();
			var sumPrice=$(obj).closest("tr").find('input[name="sumPrice"]').val();
			
			if(sumPrice==""||sumPrice==null){
				sumPrice=0;
			}
			if(total==""||total==null){
				total=0;
			}
			
			total=total-sumPrice;
		if(price!=""&&orderNum!=""){
			var a=orderNum*price;
			price=parseFloat(price);
			$(obj).closest("tr").find('input[name="sumPrice"]').val(a.toFixed(2));
			$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
			total+=a;
			$('#sumPrice').val(total.toFixed(2));
			tax_rate();
		}
		}else{
			$.ligerMessageBox.alert("请输入数字!");
		}
		
    	}
		function tax_rate(){
			var rate = $('#sumPrice').val() - 0;
			var tax = $('#taxRate').val() - 0;
			tax = tax / 100;
			var sum_tax = rate * tax ;

			$('#remark1').val(sum_tax.toFixed(2));
		}
		
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/purchase/purOrder/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		
		 function on_pay(obj){
		 obj.select();
	 }
	 function on_type(){
		var a = $("#payType").val();
		if(a==""||a==null){
			$("#payType").val("月结30天后付");
		}
	 }
	
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${purOrder.id !=null}">
			        <span class="tbar-label">编辑采购订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加采购订单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
<!-- 		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div> -->
	</div>
	<div class="panel-body">
		<form id="purOrderForm" method="post" action="save2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">采购订单编号: </th>
					<td><input  readonly class="r" type="text" id="code" name="code" value="${purOrder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">制单日期: </th>
					<td><input  readonly class="r" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${purOrder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td><input  readonly class="r" type="text" id="operatorName" name="operatorName" value="${purOrder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据类型: </th>
					<td colspan="3"><input  readonly class="r" type="text" id="sourceformType" name="sourceformType" value="${purOrder.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<!--<tr>
					<th width="20%">来源单据ID: </th>-->
					<input type="hidden" id="sourceformId" name="sourceformId" value="${purOrder.sourceformId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				<tr>
					<th width="20%">来源单据编号: </th>
					<td><input  readonly class="r" type="text" id="sourceformCode" name="sourceformCode" value="${purOrder.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${purOrder.advancePay}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款说明: </th>
					<td><input type="text" id="advancepayNotes" name="advancepayNotes" value="${purOrder.advancepayNotes}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th width="20%">运费承担方: </th>
					<td><input  readonly class="r" type="text" id="freightBearer" name="freightBearer" value="${purOrder.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			
				<tr>
					<th width="20%">币种: </th>
						<td>
						<select id="currencyType" name="currencyType" value="${purOrder.currencyType}" validate="{required:false,maxlength:96}">
							<option>人民币</option>
							<option>港币</option>
							<option>美元</option>
							<option>日元</option>
							<option>英镑</option>
							<option>卢布</option>
						</select>
					</td>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${purOrder.sumPrice}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">支付方式: </th>
					<td>
					<!-- 
						<ap:selectDB id="pay_Type" name="pay_Type"
											where="typeId=10000020070088" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${purOrder.payType}" onChange="order_pay();">
										</ap:selectDB> -->
						<input type="text" id="payType" name="payType" value="月结30天后付" onblur="on_type();" onfocus="on_pay(this);" class="inputText" validate="{required:false}"  />			
					</td>
					<th width="20%">票据要求: </th>
					<td>
					<ap:selectDB id="billType" name="billType"
											where="typeId=10000020070112" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${purOrder.billType}" >
										</ap:selectDB>
					</td>
					
				
				
				<!--  	<td><input type="text" id="currencyType" name="currencyType" value="${purOrder.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>-->
				
			    <!--<tr>
					<th width="20%">采购企业ID: </th>-->
					<input  type="hidden" id="purenterpId"   name="purenterpId" value="${purOrder.purenterpId}"  class="inputText" validate="{required:false,number:true }"  />
				</tr>
				
			
				
				<tr>
					<th width="20%">采购企业名称: </th>
					<td><input  readonly class="r" type="text" id="purenterpName"  readonly="readonly" name="purenterpName" value="${purOrder.purenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">采购商联系人姓名: </th>
					<td><input type="text" id="purenterpUsername" name="purenterpUsername" value="${purOrder.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<!--<tr style="display:none">
					<th width="20%">供应企业ID </th>
					<th width="20%">供应商联系人ID: </th>--> 
					<input  type="hidden" id="suppenterpId" name="suppenterpId" value="${purOrder.suppenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<input  type="hidden" id="suppenterpUserid"  name="suppenterpUserid" value="${purOrder.suppenterpUserid}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				<tr>	
					<th width="20%">供应企业名称: </th>
					<td><input  readonly class="r" type="text" id="suppenterpName"  readonly="readonly" name="suppenterpName" value="${purOrder.suppenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">供应商联系人姓名: </th>
					<td><input type="text" id="suppenterpUsername" name="suppenterpUsername" value="${purOrder.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				
			      <tr>
					<th width="20%">收货仓库名称: </th>
					<td><input type="text" id="receivewarehouseName" name="receivewarehouseName" value="${purOrder.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:600}"  /><a href="javascript:void(0)" onclick="add_archive()"  class="link detail">选择</a></td>
			
					<th width="20%">收货仓库详细地址: </th>
					<td><input type="text" id="receivewarehouseAddress" name="receivewarehouseAddress" value="${purOrder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
		<!--  		<tr>
					<th width="20%">接受仓库ID: </th>-->	
					<input type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${purOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
				
				<tr>
					<th width="20%">收货人姓名: </th>
					<td><input type="text" id="receiverName" name="receiverName" value="${purOrder.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货人联系方式: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone" value="${purOrder.receiverPhone}"  class="inputText"  /></td>
				</tr>
			
			
				
		  <!--  	<tr>
					<th width="20%">收货人ID: </th>  -->
					<input type="hidden" id="receiverId" name="receiverId" value="${purOrder.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr>
					<th width="20%">收货人邮箱: </th>
					<td><input type="text" id="receiverMail" name="receiverMail" value="${purOrder.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">收货地址邮编: </th>
					<td colspan="3"><input type="text" id="postcode" name="postcode" value="${purOrder.postcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
					</tr>
				<!--<tr>
					<th width="20%">操作者ID: </th>-->
					<input type="hidden" id="operatorId" name="operatorId" value="${purOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				
				
				<tr>
					<th width="20%">审签领导IDs: </th>
					<td><input type="text" id="invitedUserIds" name="invitedUserIds"  readonly="readonly" value="${purOrder.invitedUserIds}"  class="inputText" validate="{required:false,maxlength:384}"  />						
					</td>
<!-- 				</tr>
				<tr> -->
					<th width="20%">审签领导: </th>
					<td><input type="text" id="invitedUserNames" name="invitedUserNames"  readonly="readonly" value="${purOrder.invitedUserNames}"  class="inputText" validate="{required:true,maxlength:50}"  />
					   <a href="javascript:changeTaskUser()">选择</a>
					</td>
				</tr>
				<tr>
					<th width="20%">税率(%): </th>
					<td><input type="text" id="taxRate" name="taxRate"   value="${purOrder.taxRate}"  class="inputText" validate="{required:false,number:true}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOrderDetail" formType="window" type="sub">
				<tr>
					<td colspan="11">
					 
			    		<div align="center">
						采购订单 ：采购订单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th style="display:none">物品ID</th>
					<th>物品编码</th>
					<th>物品规格</th>
					<th>物品名称</th>
					<th style="display:none">物品分类</th>
					<th style="display:none">物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>议价</th>
					<th>总金额</th>
			    	<th>发货截至时间</th>
				</tr>
				<c:forEach items="${purOrderDetailList}" var="purOrderDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="display:none" name="materielId">${purOrderDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${purOrderDetailItem.materielCode}</td>
					    <td style="text-align: center" name="model">${purOrderDetailItem.model}</td>
					    <td style="text-align: center" name="materielName">${purOrderDetailItem.materielName}</td>
					    <td style="display:none" name="materielLev">${purOrderDetailItem.materielLev}</td>
					    <td style="display:none" name="attributeInfo">${purOrderDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${purOrderDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${purOrderDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purOrderDetailItem.price}</td>
					    <td style="text-align: center" name="bargain"><input type="text"  name="bargain" onblur="getSumPrice(this);" value="${purOrderDetailItem.bargain}"/></td>
					    <td style="text-align: center" name="sumPrice"><input type="text" readonly class="r" name="sumPrice" value="${purOrderDetailItem.sumPrice}"/></td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>
						
														
						<input type="hidden" name="materielId" value="${purOrderDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${purOrderDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${purOrderDetailItem.materielName}"/>
						<input type="hidden" name="model" value="${purOrderDetailItem.model}"/>
						<input type="hidden" name="attributeInfo" value="${purOrderDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${purOrderDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${purOrderDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${purOrderDetailItem.price}"/>
						
						
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="model"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="display:none" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="bargain"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="orderNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="bargain" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${purOrder.id}" />
		</form>
	</div>
	<form id="purOrderDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		     <tr  style="display:none">
				<th width="20%">物品ID: </th>
				<td><input type="text" class="r" readonly name="materielId" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品编号: </th>
				<td><input type="text" class="r" readonly name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品规格: </th>
				<td><input type="text" class="r" readonly name="model" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" class="r" readonly name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr  style="display:none">
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none">
				<th width="20%">物品属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" class="r" readonly name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">订单数量: </th>
				<td><input type="text" class="r" readonly name="orderNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" class="r" readonly name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">议价: </th>
				<td><input type="text" name="bargain" value=""  onblur="getSumPrice(this);" class="inputText" validate="{required:false}"/></td>
			</tr>

			<tr>
				<th width="20%">总金额: </th>
				<td><input type="text" class="r" readonly name="sumPrice" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货时间: </th>
				<td><input type="text" class="r" readonly name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
