<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_warehouse_out
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑出库信息</title>
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
			var frm=$('#warehouseOutForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseOut/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		function preview(){
			
			CommonDialog("warehouse_choose",
			function(data) {
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				$("#warehouseAddress").val(data.address);
				
				
			});
		}
		
		function preview_material(){
			CommonDialog("warehouse_material",
					function(data) {
						$(":input[name=materialName]").val(data.name);
						$(":input[name=materialCode]").val(data.code);
						$(":input[name=units]").val(data.unit);
						$(":input[name=price]").val(data.price);
					});
		}
		
		$(function(){
			var seq =1;
			$("#btnAdd").click(function(){
				$(":input[name=serialNum]").val(seq++);
			});
			
		});
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${warehouseOut.id !=null}">
			        <span class="tbar-label">编辑出库信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加出库信息</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		
	</div>
	<div class="panel-body">
		<form id="warehouseOutForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">出库单号: </th>
					<td><input size="35" type="text" id="code" name="code" value="${warehouseOut.code}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th style="width:120px">出库类型: </th>
					<td>
						<select name="outType" id="outType" class="inputText">
							<option value="" ${checked}>请选择</option>
							<option value="销售出库" >销售出库</option>
							<option value="发货出库" >发货出库</option>
							<option value="直接出库" >直接出库</option>
							<option value="中转出库" >中转出库</option>
							<option value="其他" >其他</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width:120px">来源单据号: </th>
					<td>
						<input size="35" type="hidden" id="inOrderId"  name="inOrderId" value="${warehouseOut.inOrderId}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<input size="35" type="text" id="inOrderCode" readonly="readonly" name="inOrderCode" value="${warehouseOut.inOrderCode}"  class="inputText" validate="{required:false,maxlength:768}"  />
					</td>
					<th style="width:120px">仓库名称: </th>
					<td>
						<input size="35" type="hidden" id="warehouseId" name="warehouseId" value="${warehouseOut.warehouseId}"  class="inputText" validate="{required:false,number:true }"  />
						<input size="35" type="text" id="warehouseName" readonly="readonly" name="warehouseName" value="${warehouseOut.warehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  />
						<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					</td>
				</tr>
				<tr>
					<th style="width:120px">所属企业: </th>
					<td><input size="35" type="text" id="companyid" name="companyid" value="${warehouseOut.companyid}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">仓库详细地址: </th>
					<td><input size="35" type="text" id="warehouseAddress" name="warehouseAddress" value="${warehouseOut.warehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="makingPeople" readonly="readonly" name="makingPeople" value="${warehouseOut.makingPeople}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="makingDate" name="makingDate" value="<fmt:formatDate value='${warehouseOut.makingDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				 <tr style="display:none;">
					<th style="width:120px">收货企业ID: </th>
					<td><input size="35" type="hidden" id="receiveEnterpId" name="receiveEnterpId" value="${warehouseOut.receiveEnterpId}" class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehouseOutDetail" formType="window" type="sub">
				<tr>
					<th>序号</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>属性描述</th>
					<th>单位</th>
					<th>已发数量</th>
					<!-- <th>单价</th> -->
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach items="${warehouseOutDetailList}" var="warehouseOutDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="serialNum">${warehouseOutDetailItem.serialNum}</td>
					    <td style="text-align: center" name="materialCode">${warehouseOutDetailItem.materialCode}</td>
					    <td style="text-align: center" name="materialName">${warehouseOutDetailItem.materialName}</td>
					    <td style="text-align: center" name="attributeDes">${warehouseOutDetailItem.attributeDes}</td>
					    <td style="text-align: center" name="units">${warehouseOutDetailItem.units}</td>
					    <td style="text-align: center" name="sendNum">${warehouseOutDetailItem.sendNum}</td>
					    <td style="text-align: center;display:none;" name="price">${warehouseOutDetailItem.price}</td>
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						<input size="35" type="hidden" name="serialNum" value="${warehouseOutDetailItem.serialNum}"/>
						<input size="35" type="hidden" name="materialCode" value="${warehouseOutDetailItem.materialCode}"/>
			    		<input size="35" type="hidden" name="materialName" value="${warehouseOutDetailItem.materialName}"/>
						<input size="35" type="hidden" name="attributeDes" value="${warehouseOutDetailItem.attributeDes}"/>
						<input size="35" type="hidden" name="units" value="${warehouseOutDetailItem.units}"/>
						<input size="35" type="hidden" name="sendNum" value="${warehouseOutDetailItem.sendNum}"/>
						<input size="35" type="hidden" name="price" value="${warehouseOutDetailItem.price}"/>
				    </tr>
				</c:forEach>
		    </table>
			<input size="35" type="hidden" name="id" value="${warehouseOut.id}" />
		</form>
	</div>
</div>
</body>
</html>
