<%--
	time:2013-05-16 15:35:33
	desc:edit the 物流派送单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 物流派送单</title>
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
			var frm=$('#logisticPlanForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		  
		/* function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/logistics/logisticPlan/list.ht";
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
			    <c:when test="${logisticPlan.id !=null}">
			        <span class="tbar-label">编辑物流派送单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加物流派送单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
	</div>
	<div class="panel-body">
		<form id="logisticPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				
				<tr>
					<th style="width:120px">单证号: </th>
					<td ><input type="text" id="code" name="code" readonly="readonly" value="${logisticsDeliver.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input type="text" id="operateDate" readonly="readonly" name="operateDate" value="<fmt:formatDate value='${logisticsDeliver.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${logisticsDeliver.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					
				</tr>
				
				<tr>
					<th style="width:120px">制单人名称: </th>
					<td><input type="text" id="operaterName"  readonly="readonly" name="operaterName" value="${logisticsDeliver.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">配送企业名称: </th>
					<td><input type="text" id="deliverEnterpName"  readonly="readonly" name="deliverEnterpName" value="${LogisticsPlanDtl.shipEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">配送企业ID: </th>
					<td><input type="text" id="deliverEnterpId" name="deliverEnterpId" value="${LogisticsPlanDtl.shipEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">配送人员ID: </th>
					<td><input type="text" id="delivererId" name="delivererId" value="${LogisticsPlanDtl.shiperId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">配送人员名称: </th>
					<td><input type="text" id="delivererName" readonly="readonly" name="delivererName" value="${LogisticsPlanDtl.shiperName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">配送时间: </th>
					<td><input type="text" id="deliverTime"  readonly="readonly" name="deliverTime" value="<fmt:formatDate value='${LogisticsPlanDtl.startTime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">发货企业ID: </th>
					<td><input type="text" id="postEnterpId" name="postEnterpId" value="${LogisticsPlanDtl.postEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">发货企业联系人ID: </th>
					<td><input type="text" id="posterId" name="posterId" value="${LogisticsPlanDtl.posterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					
					<th style="width:120px">发货企业联系人名称: </th>
					<td><input type="text" id="posterName" name="posterName"  readonly="readonly" value="${LogisticsPlanDtl.posterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">发货企业联系人方式: </th>
					<td><input type="text" id="posterPhone" name="posterPhone" value="${LogisticsPlanDtl.posterPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
				    <th style="width:120px">发货企业名称: </th>
					<td><input type="text" id="postEnterpName" name="postEnterpName"  readonly="readonly" value="${LogisticsPlanDtl.postEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>	
					<th style="width:120px">发货地址: </th>
					<td><input type="text" id="postAddress" name="postAddress"  readonly="readonly" value="${LogisticsPlanDtl.postAddress}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">收货企业ID: </th>
					<td><input type="text" id="receiveEnterpId" name="receiveEnterpId" value="${LogisticsPlanDtl.receiveEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">收货企业联系人ID: </th>
					<td><input type="text" id="receiverId" name="receiverId" value="${LogisticsPlanDtl.receiverId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">收货企业名称: </th>
					<td><input type="text" id="receiveEnterpName" name="receiveEnterpName"  readonly="readonly" value="${LogisticsPlanDtl.receiveEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">收货地址: </th>
					<td><input type="text" id="receiveAddress" name="receiveAddress" readonly="readonly"  value="${LogisticsPlanDtl.receiveAddress}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">收货企业联系人名称: </th>
					<td><input type="text" id="receiverName" name="receiverName" readonly="readonly"  value="${LogisticsPlanDtl.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">收货企业联系人方式: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone"  value="${LogisticsPlanDtl.receiverPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">计划详单ID: </th>
					<td colspan='3'><input type="text" id="planDetailId" name="planDetailId" value="${logisticPlan.id}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${logisticsDeliver.id}" />
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="logisticMaterial">
				
				<tr>
					<!-- <th>物品ID</th> -->
					<th>物品编号</th>
					<th>物品名</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>数量</th>
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach items="${logisticMaterialList}" var="logisticMaterialItem" varStatus="status">
				    <tr >
					    <td style="text-align: center;display:none;" name="materielId">${logisticMaterialItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${logisticMaterialItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${logisticMaterialItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${logisticMaterialItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${logisticMaterialItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${logisticMaterialItem.unit}</td>
					    <td style="text-align: center" name="num">${logisticMaterialItem.num}</td>
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						<input type="hidden" name="materielId" value="${logisticMaterialItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${logisticMaterialItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${logisticMaterialItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${logisticMaterialItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${logisticMaterialItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${logisticMaterialItem.unit}"/>
						<input type="hidden" name="num" value="${logisticMaterialItem.num}"/>
				    </tr>
				</c:forEach>
		    </table>
		</form>
		
	</div>
</div>
</body>
</html>
