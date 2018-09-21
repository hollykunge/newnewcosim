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
		}
		}else{
			$.ligerMessageBox.alert("请输入数字!");
		}
		
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
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${purOrder.id !=null}">
			        <span class="tbar-label">审签采购订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">采购订单</span>
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
					<td>${purOrder.code}</td>
					<th width="20%">制单日期: </th>
					<td><fmt:formatDate value="${purOrder.operateDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td>${purOrder.operatorName}</td>
					<th width="20%">来源单据类型: </th>
					<td colspan="3">${purOrder.sourceformType}</td>
				</tr>
					<input type="hidden" id="sourceformId" name="sourceformId" value="${purOrder.sourceformId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				<tr>
					<th width="20%">来源单据编号: </th>
					<td>${purOrder.sourceformCode}</td>
					<th width="20%">预付款: </th>
					<td>${purOrder.advancePay}</td>
				</tr>
				<tr>
					<th width="20%">预付款说明: </th>
					<td>${purOrder.advancepayNotes}</td>
					<th width="20%">运费承担方: </th>
					<td>${purOrder.freightBearer}</td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td>${purOrder.currencyType}
					</td>
					<th width="20%">总金额: </th>
					<td>${purOrder.sumPrice}</td>
				</tr>
				<tr>
					<th width="20%">支付方式: </th>
					<td>${purOrder.payType}</td>
					<th width="20%">票据要求: </th>
					<td>${purOrder.billType}</td>
				</tr>
				<tr>
					<th width="20%">采购企业名称: </th>
					<td>${purOrder.purenterpName}</td>
					<th width="20%">采购商联系人姓名: </th>
					<td>${purOrder.purenterpUsername}</td>
				</tr>
					<input  type="hidden" id="suppenterpId" name="suppenterpId" value="${purOrder.suppenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<input  type="hidden" id="suppenterpUserid"  name="suppenterpUserid" value="${purOrder.suppenterpUserid}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				<tr>	
					<th width="20%">供应企业名称: </th>
					<td>${purOrder.suppenterpName}</td>
					<th width="20%">供应商联系人姓名: </th>
					<td>${purOrder.suppenterpUsername}</td>
				</tr>
			      <tr>
					<th width="20%">收货仓库名称: </th>
					<td>${purOrder.receivewarehouseName}</td>
			
					<th width="20%">收货仓库详细地址: </th>
					<td>${purOrder.receivewarehouseAddress}</td>
				</tr>
					<input type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${purOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
				<tr>
					<th width="20%">收货人姓名: </th>
					<td>${purOrder.receiverName}</td>
					<th width="20%">收货人联系方式: </th>
					<td>${purOrder.receiverPhone}</td>
				</tr>
					<input type="hidden" id="receiverId" name="receiverId" value="${purOrder.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr>
					<th width="20%">收货人邮箱: </th>
					<td>${purOrder.receiverMail}</td>
					<th width="20%">收货地址邮编: </th>
					<td colspan="3">${purOrder.postcode}</td>
					<input type="hidden" id="operatorId" name="operatorId" value="${purOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				<tr>
					<th width="20%">税率: </th>
					<td>${purOrder.taxRate}</td>
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
					    <td style="text-align: center" name="bargain">${purOrderDetailItem.bargain}</td>
					    <td style="text-align: center" name="sumPrice">${purOrderDetailItem.sumPrice}</td>
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
