<%--
	time:2013-05-06 12:24:45
	desc:edit the cloud_sale_delivernote
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 发货通知单</title>
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
			var frm=$('#delivernoteForm').form();
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
						window.location.href = "${ctx}/cloud/sale/delivernote/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */


      	function add_archive(){
    		CommonDialog("warehouse_choose",
    	    		function(data) {
    	    			var row=data;
        				$('input[name="deliverwarehouseName"]').val(row.warehousename);
        				$('input[name="deliverwarehouseAddress"]').val(row.address);
        				$('input[name="delivererName"]').val(row.contactname);
        				$('input[name="delivererPhone"]').val(row.contactphone);
    	    		});
    	    	}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${delivernote.id !=null}">
			        <span class="tbar-label">编辑发货通知单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加发货通知单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar" style="display:none">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="delivernoteForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">出货通知单流水号: </th>
					<td><input type="text" id="code" name="code" value="${delivernote.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${delivernote.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${delivernote.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${delivernote.sourceformId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${delivernote.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据编号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${delivernote.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">采购企业ID: </th>
					<td><input type="text" id="purenterpId"  readonly="readonly"  name="purenterpId" value="${delivernote.purenterpId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">采购企业名称: </th>
					<td><input type="text" id="purenterpName"  readonly="readonly" name="purenterpName" value="${delivernote.purenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">采购商联系人姓名: </th>
					<td><input type="text" id="purenterpUsername"  readonly="readonly" name="purenterpUsername" value="${delivernote.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">供应企业ID: </th>
					<td><input type="text" id="suppenterpId"  readonly="readonly" name="suppenterpId" value="${delivernote.suppenterpId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">供应企业名称: </th>
					<td><input type="text" id="suppenterpName"  readonly="readonly" name="suppenterpName" value="${delivernote.suppenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
		
				<tr>
					<th width="20%">供应企业联系人姓名: </th>
					<td><input type="text" id="suppenterpUsername"  readonly="readonly" name="suppenterpUsername" value="${delivernote.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货仓库名称: </th>
					<td><input type="text" id="receivewarehouseName"  readonly="readonly" name="receivewarehouseName" value="${delivernote.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				
			<!--	<tr>
					<th width="20%">收货仓库ID: </th>  -->	
					<input type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${delivernote.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
				
				<tr>
					<th width="20%">收货仓库详细地址: </th>
					<td><input type="text" id="receivewarehouseAddress"  readonly="readonly" name="receivewarehouseAddress" value="${delivernote.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th width="20%">收货人姓名: </th>
					<td><input type="text" id="receiverName"  readonly="readonly" name="receiverName" value="${delivernote.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<!--  <tr>
					<th width="20%">收货人ID: </th>-->
					<input type="hidden" id="receiverId" name="receiverId"  readonly="readonly" value="${delivernote.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
			
				<tr>
					<th width="20%">收货人手机号: </th>
					<td><input type="text" id="receiverPhone"  readonly="readonly" name="receiverPhone" value="${delivernote.receiverPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">收货人邮箱: </th>
					<td><input type="text" id="receiverMail"  readonly="readonly" name="receiverMail" value="${delivernote.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货地址邮编: </th>
					<td><input type="text" id="receiverPostcode"  readonly="readonly" name="receiverPostcode" value="${delivernote.receiverPostcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">发货仓库名称: </th>
					<td><input type="text" id="deliverwarehouseName" name="deliverwarehouseName" value="${delivernote.deliverwarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /><a href="javascript:void(0)" onclick="add_archive()"  class="link detail">选择</a></td>
				</tr>
				
				<tr>
					<th width="20%">发货仓库详细地址: </th>
					<td><input type="text" id="deliverwarehouseAddress" name="deliverwarehouseAddress" value="${delivernote.deliverwarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th width="20%">发货人姓名: </th>
					<td><input type="text" id="delivererName" name="delivererName" value="${delivernote.delivererName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
		 	
		<!--  		<tr>
					<th width="20%">发货人ID: </th>-->
					<input type="hidden" id="receiverId" name="receiverId" value="${delivernote.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				
				
				<tr>
					<th width="20%">发货人手机号: </th>
					<td><input type="text" id="delivererPhone" name="delivererPhone" value="${delivernote.delivererPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">发货人邮箱: </th>
					<td><input type="text" id="delivererMail" name="delivererMail" value="${delivernote.delivererMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">发货人邮编: </th>
					<td><input type="text" id="delivererPostcode" name="delivererPostcode" value="${delivernote.delivererPostcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${delivernote.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">操作者ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${delivernote.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="delivernoteDetail" formType="window" type="sub">
				<tr>
					<td colspan="12">
						<div class="group" align="left" style="display:none">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						发货通知单 ：发货通知单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
				    <th style="display:none">物品ID</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th style="display:none">物品分类</th>
					<th>物品规格</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
					<th>要求发货时间</th>
					<th>预计发货时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${delivernoteDetailList}" var="delivernoteDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="display:none" name="materielId">${delivernoteDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${delivernoteDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${delivernoteDetailItem.materielName}</td>
					    <td style="display:none" name="materielLev">${delivernoteDetailItem.materielLev}</td>
					    <td style="text-align: center" name="model">${delivernoteDetailItem.model}</td>
					    <td style="text-align: center" name="attributeInfo">${delivernoteDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${delivernoteDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${delivernoteDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${delivernoteDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${delivernoteDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${delivernoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="predeliveryEnddate"><fmt:formatDate value='${delivernoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materielId" value="${delivernoteDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${delivernoteDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${delivernoteDetailItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${delivernoteDetailItem.materielLev}"/>
						<input type="hidden" name="model" value="${delivernoteDetailItem.model}"/>
						<input type="hidden" name="attributeInfo" value="${delivernoteDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${delivernoteDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${delivernoteDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${delivernoteDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${delivernoteDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${delivernoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="predeliveryEnddate" value="<fmt:formatDate value='${delivernoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="text-align: center" name="model"></td>
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
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="model" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="orderNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="predeliveryEnddate" value="" class="inputText date"/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${delivernote.id}" />
		</form>
		
	</div>
	<form id="delivernoteDetailForm" style="display:none">
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
				<th width="20%">交货截止日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">预计交货日期: </th>
				<td><input type="text" name="predeliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
		
		</table>
	</form>
</div>
</body>
</html>
