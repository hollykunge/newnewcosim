<%--
	time:2013-05-06 13:02:00
	desc:edit the cloud_pur_order
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 采购订单</title>
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
			var frm=$('#purOrderForm').form();
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
			        <span class="tbar-label">编辑采购订单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加采购订单</span>
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
		<form id="purOrderForm" method="post" action="save2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">采购订单编号: </th>
					<td><input type="text" id="code" name="code" value="${purOrder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${purOrder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${purOrder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${purOrder.sourceformId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据编号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${purOrder.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
			
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${purOrder.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${purOrder.advancePay}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr>
					<th width="20%">预付款说明: </th>
					<td colspan="3"><textarea cols="95" rows="3" id="advancepayNotes" name="advancepayNotes" class="inputText" validate="{required:false,maxlength:768}"  >${purOrder.advancepayNotes}</textarea></td>
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
				</tr>
				<tr>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${purOrder.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice" value="${purOrder.sumPrice}"  class="inputText" validate="{required:false}"  /></td>
				<!--  	<td><input type="text" id="currencyType" name="currencyType" value="${purOrder.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>-->
				</tr>
			  
				<tr>
					<th width="20%">采购企业ID: </th>
					<td><input type="text" id="purenterpId" name="purenterpId" value="${purOrder.purenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">采购企业名称: </th>
					<td><input type="text" id="purenterpName" name="purenterpName" value="${purOrder.purenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
			
				
				<tr>
					<th width="20%">采购商联系人姓名: </th>
					<td><input type="text" id="purenterpUsername" name="purenterpUsername" value="${purOrder.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">供应企业ID </th>
					<td><input type="text" id="suppenterpId"  readonly="readonly" name="suppenterpId" value="${purOrder.suppenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				<tr>
					<th width="20%">供应企业名称: </th>
					<td><input type="text" id="suppenterpName"  readonly="readonly" name="suppenterpName" value="${purOrder.suppenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">供应商联系人姓名: </th>
					<td><input type="text" id="suppenterpUsername"  readonly="readonly" name="suppenterpUsername" value="${purOrder.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			<!--      <tr>
					<th width="20%">供应商联系人ID: </th> -->
					<input type="hidden" id="suppenterpUserid" name="suppenterpUserid" value="${purOrder.suppenterpUserid}"  class="inputText" validate="{required:false,maxlength:96}"  />
			
		<!--  		<tr>
					<th width="20%">接受仓库ID: </th>-->	
					<input type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${purOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
				
				<tr>
					<th width="20%">收货仓库名称: </th>
					<td><input type="text" id="receivewarehouseName" name="receivewarehouseName" value="${purOrder.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:600}"  /></td>
					<th width="20%">收货仓库详细地址: </th>
					<td><input type="text" id="receivewarehouseAddress" name="receivewarehouseAddress" value="${purOrder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			
			
				
		  <!--  	<tr>
					<th width="20%">收货人ID: </th>  -->
					<input type="hidden" id="receiverId" name="receiverId" value="${purOrder.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr>
					<th width="20%">收货人姓名: </th>
					<td><input type="text" id="receiverName" name="receiverName" value="${purOrder.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货人手机号: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone" value="${purOrder.receiverPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			
				<tr>
					<th width="20%">收货人邮箱: </th>
					<td><input type="text" id="receiverMail" name="receiverMail" value="${purOrder.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货地址邮编: </th>
					<td><input type="text" id="postcode" name="postcode" value="${purOrder.postcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				
				  	<tr>
					<th width="20%">操作者ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${purOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOrderDetail" formType="window" type="sub">
				<tr>
					<td colspan="11">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						采购订单 ：采购订单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>物品ID</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
			    	<th>发货截至时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${purOrderDetailList}" var="purOrderDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materielId">${purOrderDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${purOrderDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${purOrderDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${purOrderDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${purOrderDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${purOrderDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${purOrderDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purOrderDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${purOrderDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materielId" value="${purOrderDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${purOrderDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${purOrderDetailItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${purOrderDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${purOrderDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${purOrderDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${purOrderDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${purOrderDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${purOrderDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
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
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${purOrder.id}" />
		</form>
		
	</div>
	<form id="purOrderDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		     <tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		     
			<tr>
				<th width="20%">物品编号: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">订单数量: </th>
				<td><input type="text" name="orderNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">总金额: </th>
				<td><input type="text" name="sumPrice" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货时间: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
