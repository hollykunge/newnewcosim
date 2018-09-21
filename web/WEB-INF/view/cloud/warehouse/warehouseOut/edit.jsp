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
		
		function showResponse(responseText) {
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
		}
		
		function preview(){
			
			CommonDialog("warehouse_choose",
			function(data) {
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				$("#warehouseAddress").val(data.address);
				
				
			});
		}
		
		
		//销售出库
		var sale_out_window;
    	function getSaleOrders(){
    		//弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/warehouse/warehouseOut/getSaleOrders.ht';
    		sale_out_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'销售出库', name:"frameDialog_"});
    	}	
    	
    	//物品列表回调函数
    	function _callBackGetSaleOrders(friend){
				$("#inOrderId").val(friend[0]);
				$("#inOrderCode").val(friend[1]);
				$("#rows").empty();
				$.ajax({
					type : 'post',
					dataType : 'json',
					url : '${ctx}/cloud/warehouse/warehouseOut/getOrderDetails.ht',
					data : {value : friend[0]},
					success : function(rows){
						
						//判断是否有值
						if(rows.length>0){
							for(var i=0;i<rows.length;i++){
								 
								var materialcode = rows[i].materielCode;
								var materialname = rows[i].materielName;
								var attributedes = rows[i].attributeInfo;
								var units = rows[i].unit;
								var receivedamount = rows[i].orderNum;
								var price = rows[i].price;
								if(attributedes==null){
									attributedes="";
								}
								appendRow(materialcode,attributedes,units,materialname,receivedamount,price);
								
//									$(":input[name=materialname]").val(materielName);
							};
							
						}
					}
				});
    		
    		
    		
    		
    		sale_out_window.close();
    	}
    	
    	
		
		//创建隐藏Input
		function createInput(name, value){
			
			return '<input type="hidden" name="' + name + '" value="' + value + '"/>';
		
		}	
		
		function appendRow(materialcode,attributedes,units,materialname,receivedamount,price){
			var str = '<tr type="subdata">';
			
			str += '<td ><input type="checkbox" name="check" value=""/></td>';
			str += '<td style="text-align: center" name="materialCode">'+ createInput('materialCode',materialcode) + materialcode + '</td>';
			str += '<td style="text-align: center" name="materialName">'+ createInput('materialName',materialname) + materialname + '</td>';
			
			str += '<td style="text-align: center" name="units">' + createInput('units',units) + units+ '</td>';
			str += '<td style="text-align: center" name="price">' + createInput('price',price) + price + '</td>';
			str += '<td ><input type="text"    name="sendNum"  value="'+receivedamount+'"/></td>';
			str += '<td style="text-align: center" name="attributeDes">'+ createInput('attributeDes',attributedes)+ attributedes + '</td>';
			str += '<td ><input type="text"    name="shelves"  value=""/></td>';
			str += '</tr>';
			$('#rows').append(str);
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
		
		function sel_type(){
			var type = $("#outType").find("option:selected").text(); 
			
			if(type=='销售出库'){
				$("#div_show").show();
				$("#div_cgck").hide();
			}else if(type=='发货出库'){
				$("#div_cgck").show();
				$("#div_show").hide();
			}else{
				$("#div_cgck").hide();
				$("#div_show").hide();
			}
			
			
		}
		
		
		//物品增加
		var add_wpsupps_window;
    	function add_wpsupps(){
    		//弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/config/material/selectMaterialTree.ht';
    		add_wpsupps_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'物品选择器', name:"frameDialog_"});
    	}	
    	
    	//物品列表回调函数
    	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
    		for ( var i = 0; i < ids.length; i++) {
				var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value="'
						+ ids[i]
						+ '"/><input type="hidden"  name="materialid" value="'+ ids[i] + '"/></td><td ><input type="hidden"    name="materialCode"  value='+ codes[i] +'/>' + codes[i] + '</td> <td ><input type="hidden"    name="materialName"  value='+ names[i] +'/> ' + names[i] + ' </td>   <td ><input type="hidden" style="width: 50px;"  name="units"  value='+ units[i] +'/> ' + units[i]
						+ ' </td><td ><input type="hidden"  style="width: 80px;" name="price"  value='+ prices[i] +'/> ' + prices[i] + ' </td><td ><input type="text" style="width: 50px;"  name="sendNum"   /> </td> <td ><input type="text"    name="attributeDes"   /> </td> <td ><input type="text"    name="shelves"  value=""/></td></tr>');
				$("#warehouseOutDetail").append(item);
			}
    		add_wpsupps_window.close();
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
	 
	//增加一行

   	
   	function add_onesupps(){
   		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden"  name="materialid" value=""/></td><td ><input type="text"    name="materialCode"  value=""/></td> <td ><input type="text"   name="materialName"  value=""/> </td>   <td ><input type="text" style="width: 50px;"  name="units"  value=""/>  </td><td ><input type="text"  style="width: 80px;" name="price"  value=""/>  </td><td ><input type="text" style="width: 50px;"  name="sendNum"   /> </td> <td ><input type="text"    name="attributeDes"   /> </td> <td ><input type="text"    name="shelves"  value=""/></td></tr>');
				$("#warehouseOutDetail").append(item);
   	}
   		function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
		}
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
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="warehouseOutForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">出库单号: </th>
					<td><input type="text" id="code" name="code" value="${warehouseOut.code}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">出库类型: </th>
					<td>
						<select name="outType" id="outType" class="inputText" onchange="sel_type();">
							<c:remove var="checked"/>
							<c:if test="${warehouseOut.outType==null || warehouseOut.outType==''}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="" ${checked}>请选择</option>
							<c:remove var="checked"/>
							<c:if test="${warehouseOut.outType=='销售出库'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="销售出库" ${checked}>销售出库</option>
							<c:remove var="checked"/>
						 
							<c:if test="${warehouseOut.outType=='其他出库'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="其他" ${checked}>其他出库</option>
						</select>
					
					</td>
				</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td>
						<input type="hidden" id="inOrderId" name="inOrderId" value="${warehouseOut.inOrderId}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<input type="text" id="inOrderCode" name="inOrderCode" value="${warehouseOut.inOrderCode}"  class="inputText" validate="{required:false,maxlength:768}"  />
						<font id="div_show" style="display: none; "><a href="javascript:void(0)" onclick="getSaleOrders()"  class="link detail">选择</a></font>
						<font id="div_cgck" style="display: none; "><a href="javascript:void(0)" onclick="add_cgck()"  class="link detail">选择</a></font>
					</td>
				</tr>
				<tr>
					<th width="20%">仓库名称: </th>
					<td>
						<input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseOut.warehouseId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="warehouseName" name="warehouseName" value="${warehouseOut.warehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  />
						<a href="javascript:void(0)" onclick="preview()"  class="link detail">选择</a>
					</td>
				</tr>
				<tr>
					<th width="20%">仓库详细地址: </th>
					<td><input type="text" id="warehouseAddress" name="warehouseAddress" value="${warehouseOut.warehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">所属企业: </th>
					<td><input type="text" id="companyid" name="companyid" value="${warehouseOut.companyid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="makingPeople" name="makingPeople" value="${warehouseOut.makingPeople}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="makingDate" name="makingDate" value="<fmt:formatDate value='${warehouseOut.makingDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehouseOutDetail" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<div class="group" align="left">
				   			 <a href="javascript:void(0)" onclick="add_wpsupps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd2.jpg'"
												onmouseout="src='${ctx}/images/iconadd.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
								  
								  <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
								<!--				
								<a href="javascript:void(0)" onclick="add_onesupps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd3.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd4.jpg'"
												onmouseout="src='${ctx}/images/iconadd3.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;-->
			    		</div>
			    		<div align="center">
						出库单详细信息
			    		</div>
		    		</td>
				</tr>
				<tr>
					 <th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>单位</th>
					<th>单价</th>
					<th>已发数量</th>
					<th>属性描述</th>
					<th>货架</th>
				 
				</tr>
				<tbody id="rows">
					</tbody>
				<c:forEach items="${warehouseOutDetailList}" var="warehouseOutDetailItem" varStatus="status">
				    <tr type="subdata">
					     <td><input type="checkbox" name="check" value="${warehouseOutDetailItem.id}" /></td>
					     <td ><input type="text" name="materialCode" value=${warehouseOutDetailItem.materialCode}></td>
					     <td ><input type="text" name="materialName" value=${warehouseOutDetailItem.materialName}></td>
					     <td ><input type="text" name="units" style="width: 50px;" value=${warehouseOutDetailItem.units}></td>
					     <td ><input type="text" name="price" style="width: 80px;" value="${warehouseOutDetailItem.price}"/></td>
					     <td ><input type="text" name="sendNum" style="width: 50px;"  value="${warehouseOutDetailItem.sendNum}"/></td>
					     <td ><input type="text" name="attributeDes" value="${warehouseOutDetailItem.attributeDes}"/></td>
					     <td><input type="text" name="shelves" value="${warehouseOutDetailItem.shelves}"/></td>
					     <td style="display:none;"><input type="hidden" name="materialid" value="${warehouseOutDetailItem.materialid}"/></td>
				    </tr>
					  
				</c:forEach>
			 
		    </table>
			<input type="hidden" name="id" value="${warehouseOut.id}" />
		</form>
		
	</div>
	<form id="warehouseOutDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">序号: </th>
				<td><input type="text" name="serialNum" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:768}"/>
					<a href="javascript:void(0)" onclick="preview_material()"  class="link detail">选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:768}"/>
								
				</td>
			</tr>
			
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value=""  class="inputText" validate="{required:false,maxlength:30}"/></td>
			</tr>
			
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">已发数量: </th>
				<td><input type="text" name="sendNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性描述: </th>
				<td>
					<textarea rows="2" cols="35" name="attributeDes"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
