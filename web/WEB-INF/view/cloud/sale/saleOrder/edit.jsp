<%--
	time:2013-05-06 12:52:53
	desc:edit the cloud_sale_order
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 销售订单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#saleOrderForm').form();
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
						window.location.href = "${ctx}/cloud/sale/saleOrder/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		
		var dd;
      	function selSups(){
      		//弹出供应企业选择框
      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriendsRadio.ht';
      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
      	}
      	
      	//商圈列表回调函数
      	function _callBackMyFriends(friend){
      		$("#en_id").val(friend[0]);
			$("#en_name").val(friend[1]);
      		dd.close();   
		}

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
		
         function add_product(){
      		
    		CommonDialog("warehouse_material",
    		function(data) {
    			var row=data;
    	
    				$('.l-dialog-table input[name="materielCode"]').val(row.code);
    				
    				$('.l-dialog-table input[name="materielName"]').val(row.name);
    				
    				$('.l-dialog-table input[name="unit"]').val(row.unit);
    				
    				$('.l-dialog-table input[name="price"]').val(row.price);
    		
    		});
    	}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${saleOrder.id !=null}">
			        <span class="tbar-label">编辑销售订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加销售订单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="saleOrderForm" method="post" action="save2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">销售订单编号: </th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${saleOrder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人姓名: </th>
					<td><input size="35" type="text" id="operatorName" name="operatorName" value="${saleOrder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleOrder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
			<!--	<tr>
				 <th style="width:120px">来源单据ID: </th>
					<td><input size="35" type="text" id="sourceformId" name="sourceformId" value="${saleOrder.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">来源单据编号: </th>
					<td><input size="35" type="text" id="sourceformCode" name="sourceformCode" value="${saleOrder.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr> -->	
				<tr>
					<th style="width:120px">来源单据类型: </th>
					<td><input size="35" type="text" id="sourceformType" name="sourceformType" value="${saleOrder.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">预付款: </th>
					<td><input size="35" type="text" id="advancePay" name="advancePay" value="${saleOrder.advancePay}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">预付款说明: </th>
					<td colspan="3"><textarea cols="95" rows="3" id="advancepayNotes" name="advancepayNotes" class="inputText" validate="{required:false,maxlength:768}"  >${saleOrder.advancepayNotes}</textarea></td>
				</tr>
				<tr>
				    <th style="width:120px">币种：</th>
				    <td colspan="3">
				           <select id="currencyType" name="currencyType">
							<option value="RMB">人民币</option>
							<option value="美元">美元</option>
							<option value="日元">日元</option>
							<option value="英镑">英镑</option>
							<option value="卢布">卢布</option>
				       </select>
				    </td>
				</tr>
				<tr>
					<th style="width:120px">运费承担方: </th>
					<td><input size="35" type="text" id="freightBearer" name="freightBearer" value="${saleOrder.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">总金额: </th>
					<td><input size="35" type="text" id="sumPrice" name="sumPrice" value="${saleOrder.sumPrice}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr style="display:none">
				    <th style="width:120px">采购企业ID: </th>
					<td><input size="35" type="text" id="en_id" name="purenterpId" value="${saleOrder.purenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">采购企业联系人Id: </th>
					<td><input size="35'" type="text" id="purenterpUserid" name="purenterpUserid" value="${saleOrder.purenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
					</td>
			    </tr>
			    <tr>
					<th style="width:120px">采购企业名称: </th>
					<td><input size="35" type="text" id="en_name" name="purenterpName" value="${saleOrder.purenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  />
					<a href="javascript:void(0)" onclick="selSups()"  class="link detail">选择</a>
					</td>
					<th style="width:120px">采购企业联系人姓名: </th>
					<td><input size="35" type="text" id="purenterpUsername" name="purenterpUsername" value="${saleOrder.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<input type="hidden" id="suppenterpId" name="suppenterpId" value="${saleOrder.suppenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<th style="width:120px">供应商企业名称: </th>
					<td><input size="35" type="text" id="suppenterpName" name="suppenterpName" value="${saleOrder.suppenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">供应商联系人姓名: </th>
					<td ><input size="35" type="text" id="suppenterpUsername" name="suppenterpUsername" value="${saleOrder.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				    
				<tr>
					<!--  <th style="width:120px">供应商联系人ID: </th>  -->
					<td><input size="35" type="hidden" id="suppenterpUserid" name="suppenterpUserid" value="${saleOrder.suppenterpUserid}"  class="inputText" validate="{required:false,number:true }"  /></td>
		 	       </tr>
				<tr>
			<!--  
				<tr>
					<th style="width:120px">收货仓库ID: </th> 
					<input size="35" type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${saleOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
				
				<tr>
					<th style="width:120px">收货仓库名称: </th>
					<td><input size="35" type="hidden" id="receivewarehouseName" name="receivewarehouseName" value="${saleOrder.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">收货仓库详细地址: </th>
					<td><input size="35" type="hidden" id="receivewarehouseAddress" name="receivewarehouseAddress" value="${saleOrder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">收货地址邮编: </th>
					<td><input size="35" type="hidden" id="postcode" name="postcode" value="${saleOrder.postcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			  
	    	    <tr>
					<th style="width:120px">收货人ID: </th>
				<input size="35" type="hidden" id="receiverId" name="receiverId" value="${saleOrder.receiverId}"  class="inputText" validate="{required:false,number:true }"  />
				<tr>
					<th style="width:120px">收货人姓名: </th>
					<td><input size="35" type="hidden" id="receiverName" name="receiverName" value="${saleOrder.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">收货人手机号: </th>
					<td><input size="35" type="hidden" id="receiverPhone" name="receiverPhone" value="${saleOrder.receiverPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">收货人邮箱: </th>
					<td><input size="35" type="hidden" id="receiverMail" name="receiverMail" value="${saleOrder.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				-->
				<tr>
					<!--  <th style="width:120px">操作者ID: </th>-->
					<td><input size="35" type="hidden" id="operatorId" name="operatorId" value="${saleOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr> 
				
			
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleOrderDetail" formType="window" type="sub">
				<tr>
					<td colspan="12">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						销售订单 ：销售订单详情
			    		</div>
		    		</td>
				</tr>
						<tr>
				    <th style="display:none">物品ID</th>
					<th>物品名称</th>
					<th>物品编码</th>
					<th style="display:none">物品分类</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
					<th>要求发货时间</th>
					<th>预计发货时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${saleOrderDetailList}" var="saleOrderDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="display:none" name="materielId">${saleOrderDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielName">${saleOrderDetailItem.materielName}</td>
					     <td style="text-align: center" name="materielCode">${saleOrderDetailItem.materielCode}</td>
					    <td style="display:none" name="materielLev">${saleOrderDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${saleOrderDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${saleOrderDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${saleOrderDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${saleOrderDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${saleOrderDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${saleOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="predeliveryEnddate"><fmt:formatDate value='${saleOrderDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					  
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materielId" value="${saleOrderDetailItem.materielId}"/>
						<input type="hidden" name="materielName" value="${saleOrderDetailItem.materielName}"/>
						<input type="hidden" name="materielCode" value="${saleOrderDetailItem.materielCode}"/>
						<input type="hidden" name="materielLev" value="${saleOrderDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${saleOrderDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${saleOrderDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${saleOrderDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${saleOrderDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${saleOrderDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${saleOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="predeliveryEnddate" value="<fmt:formatDate value='${saleOrderDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
					<td style="text-align: center" name="predeliveryEnddate"></td>								
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="orderNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="predeliveryEnddate" value="" class="inputText date"/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${saleOrder.id}" />
		</form>
		
	</div>
	<form id="saleOrderDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<!-- 
			<tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			 -->	
			<tr>
				<th width="20%">物品名: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/><a href="javascript:void(0)" onclick="add_product();">选择</a></td>
			</tr>
			<!-- 
			<tr>
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			 -->
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
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
				<th width="20%">订单数量: </th>
				<td><input type="text" name="orderNum"  value=""  class="inputText" validate="{required:false,number:true }" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price"  value=""  class="inputText" validate="{required:false}" onblur="getSumPrice(this);"/></td>
			</tr>
			<tr>
				<th width="20%">总价: </th>
				<td><input type="text" name="sumPrice"  value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货时间: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">预计发货时间: </th>
				<td><input type="text" name="predeliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			
		</table>
	</form>
</div>
</body>
</html>
