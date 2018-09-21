<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_warehouse_in
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑入库信息</title>
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
			var frm=$('#warehouseInForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseIn/list.ht";
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
		
		$(function(){
			var seq =1;
			$("#btnAdd").click(function(){
				$(":input[name=serialnumber]").val(seq++);
			});
			
		});
		
		function preview_material(){
			CommonDialog("warehouse_material",
					function(data) {
						
						
						$(":input[name=materialname]").val(data.name);
						$(":input[name=materialcode]").val(data.code);
						$(":input[name=units]").val(data.unit);
						$(":input[name=price]").val(data.price);
					});
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${warehouseIn.id !=null}">
			        <span class="tbar-label">编辑入库信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加入库信息</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
	</div>
	<div class="panel-body">
		<form id="warehouseInForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">入库单号: </th>
					<td><input type="text" id="code" name="code" value="${warehouseIn.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">入库类型: </th>
					<td>
						<select name="inType" id="inType" class="inputText">
							<c:remove var="checked"/>
								<c:if test="${warehouseIn.inType==null || warehouseOut.inType==''}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
								<option value="" ${checked}>请选择</option>
								
							<c:remove var="checked"/>
								<c:if test="${warehouseIn.inType=='采购入库'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="采购入库" ${checked}>采购入库</option>
							
							<c:remove var="checked"/>
								<c:if test="${warehouseIn.inType=='收货入库'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="收货入库" ${checked}>收货入库</option>
							<c:remove var="checked"/>
								<c:if test="${warehouseIn.inType=='直接入库'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="直接入库" ${checked}>直接入库</option>
							<c:remove var="checked"/>
								<c:if test="${warehouseOut.inType=='其他'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="其他" ${checked}>其他</option>
						</select>	
					</td>
				</tr>
				<!-- 
				<tr>
					<th width="20%">来源单据类型: </th>
					<td>
						<input type="text" id="type" name="type" value="${warehouseIn.type}"  class="inputText" validate="{required:false,maxlength:96}"  />
					</td>
				</tr>
				 -->
				
				<tr>
					<th width="20%">来源单据号: </th>
					<td>
						<input type="hidden" id="inOrderId" name="inOrderId" value="${warehouseIn.inOrderId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="inOrderCode" name="inOrderCode" value="${warehouseIn.inOrderCode}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="add_supps()"  class="link detail">选择</a>
					</td>
				</tr>
				
				<tr>
					<th width="20%">仓库名称: </th>
					<td>
						<input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseIn.warehouseId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="warehouseName" name="warehouseName" value="${warehouseIn.warehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
						
					</td>
				</tr>
				<tr>
					<th width="20%">仓库详细地址: </th>
					<td><input type="text" id="warehouseAddress" name="warehouseAddress" value="${warehouseIn.warehouseAddress}"  class="inputText" validate="{required:false,maxlength:300}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operator" name="operator" value="${warehouseIn.operator}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operatedate" name="operatedate" value="<fmt:formatDate value='${warehouseIn.operatedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<!--
				<tr>
					<th width="20%">到货日期: </th>
					<td><input type="text" id="arrivatedDate" name="arrivatedDate" value="<fmt:formatDate value='${warehouseIn.arrivatedDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				 
				<tr>
					<th width="20%">质检单ID: </th>
					<td><input type="text" id="inspectid" name="inspectid" value="${warehouseIn.inspectid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				 -->
				 <tr style="display:none;">
					<th width="20%">运输企业ID: </th>
					<td><input type="hidden" id="shipEnterpId" name="shipEnterpId" value="${logisticsPlanDtl.shipEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehouseInDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<!-- <div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div> -->
			    		<div align="center">
						入库物品详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>属性描述</th>
					<th>单位</th>
					<th>已收数量</th>
					<th>单价</th>
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach items="${warehouseInDetailList}" var="warehouseInDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="serialnumber">${warehouseInDetailItem.serialnumber}</td>
					    <td style="text-align: center" name="materialcode">${warehouseInDetailItem.materialcode}</td>
					    <td style="text-align: center" name="materialname">${warehouseInDetailItem.materialname}</td>
					    <td style="text-align: center" name="attributedes">${warehouseInDetailItem.attributedes}</td>
					    <td style="text-align: center" name="units">${warehouseInDetailItem.units}</td>
					    <td style="text-align: center" name="receivedamount">${warehouseInDetailItem.receivedamount}</td>
					    <td style="text-align: center" name="price">${warehouseInDetailItem.price}</td>
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
						<input type="hidden" name="serialnumber" value="${warehouseInDetailItem.serialnumber}"/>
						<input type="hidden" name="materialcode" value="${warehouseInDetailItem.materialcode}"/>
						<input type="hidden" name="materialname" value="${warehouseInDetailItem.materialname}"/>
						<input type="hidden" name="attributedes" value="${warehouseInDetailItem.attributedes}"/>
						<input type="hidden" name="units" value="${warehouseInDetailItem.units}"/>
						<input type="hidden" name="receivedamount" value="${warehouseInDetailItem.receivedamount}"/>
						<input type="hidden" name="price" value="${warehouseInDetailItem.price}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="serialnumber"></td>
			    	<td style="text-align: center" name="materialcode"></td>
			    	<td style="text-align: center" name="materialname"></td>
			    	<td style="text-align: center" name="attributedes"></td>
			    	<td style="text-align: center" name="units"></td>
			    	<td style="text-align: center" name="receivedamount"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="serialnumber" value=""/>
			    	<input type="hidden" name="materialcode" value=""/>
			    	<input type="hidden" name="materialname" value=""/>
			    	<input type="hidden" name="attributedes" value=""/>
			    	<input type="hidden" name="units" value=""/>
			    	<input type="hidden" name="receivedamount" value=""/>
			    	<input type="hidden" name="price" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${warehouseIn.id}" />
		</form>
		
	</div>
	<form id="warehouseInDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号: </th>
				<td><input type="text" name="serialnumber" value="" class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td>
				<input type="text" name="materialcode" value="" class="inputText" validate="{required:false,maxlength:96}"/>
				 <a href="javascript:void(0)" onclick="preview_material()"  class="link detail">选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materialname" value="" class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value="" class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value="" class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">已收数量: </th>
				<td><input type="text" name="receivedamount" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性描述: </th>
				<td><input type="text" name="attributedes" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			
		</table>
	</form>
</div>
</body>
</html>
