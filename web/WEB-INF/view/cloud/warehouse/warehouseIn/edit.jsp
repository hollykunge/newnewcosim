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
		
		function showResponse(responseText) {
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
		}
		
		function preview(){
			
			CommonDialog("warehouse_choose",
			function(data) {
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				$("#warehouseAddress").val(data.address);
				
				
			});
		}
		
		function add_xsth(){
			CommonDialog("ware_sale_note",
					function(order) {
						$("#inOrderId").val(order.id);
						$("#inOrderCode").val(order.code);
						$("#rows").empty();
						$.ajax({
							type : 'post',
							dataType : 'json',
							url : '${ctx}/cloud/warehouse/warehouseOut/getOrders.ht',
							data : {value : order.id},
							success : function(rows){
								
								//判断是否有值
								if(rows.length>0){
									for(var i=0;i<rows.length;i++){
										var serialnumber = i + 1 ;
										var materialcode = rows[i].materielCode;
										var materialname = rows[i].materielName;
										var attributedes = rows[i].attributeInfo;
										var units = rows[i].unit;
										var receivedamount = rows[i].orderNum;
										var price = rows[i].price;
										
										appendRow(serialnumber,materialcode,attributedes,units,materialname,receivedamount,price);
										
// 										$(":input[name=materialname]").val(materielName);
									};
									
								}
							}
						});
						
			});
		}
		
		
		
		function add_scrk(){
			CommonDialog("ware_manuf_order",
					function(order) {
						$("#inOrderId").val(order.ID);
						$("#inOrderCode").val(order.CODE);
						$("#rows").empty();
						$.ajax({
							type : 'post',
							dataType : 'json',
							url : '${ctx}/cloud/warehouse/warehouseIn/getMOrders.ht',
							data : {value : order.ID},
							success : function(rows){
								
								if(rows.length>0){
									for(var i=0;i<rows.length;i++){
										var serialnumber = i + 1 ;
										var materialcode = rows[i].materialCode;
										var materialname = rows[i].materialName;
										var attributedes = rows[i].materialSpec;
										var units = rows[i].materialUnit;
										var receivedamount = rows[i].materialNumber;
										var price = rows[i].price;
										appendRow(serialnumber,materialcode,attributedes,units,materialname,receivedamount,price);
									};
									
								}
								
							}
						});
			});
			
		}
		
		
		
		
		 
		
		
		
		//采购入库
		var sale_out_window;
    	function getPurOrders(){
    		//弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/warehouse/warehouseIn/getPurOrders.ht';
    		sale_out_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'采购入库', name:"frameDialog_"});
    	}	
    	
    	//物品列表回调函数
    	function _callBackGetPurOrders(friend){
    		 
				$("#inOrderId").val(friend[0]);
				$("#inOrderCode").val(friend[1]);
				$("#rows").empty();
				$.ajax({
					type : 'post',
					dataType : 'json',
					url : '${ctx}/cloud/warehouse/warehouseIn/getOrders.ht',
					data : {value : friend[0]},
					success : function(rows){
						
						//判断是否有值
						if(rows.length>0){
							for(var i=0;i<rows.length;i++){
								var serialnumber = i + 1 ;
								var materialcode = rows[i].materielCode;
								var materialname = rows[i].materielName;
								var attributedes = rows[i].attributeInfo;
								var units = rows[i].unit;
								var receivedamount = rows[i].orderNum;
								var price = rows[i].price;
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
								if(receivedamount==null){receivedamount="";}
								if(price==null){price="";}
								
								appendRow(serialnumber,materialcode,attributedes,units,materialname,receivedamount,price);
								
//									$(":input[name=materialname]").val(materielName);
							};
							
						}
					}
				});
    		
    		
    		
    		
    		sale_out_window.close();
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

		
		//创建隐藏Input
		function createInput(name, value){
			
			return '<input type="hidden" name="' + name + '" value="' + value + '"/>';
		
		}	
		
		
		function appendRow(serialnumber,materialcode,attributedes,units,materialname,receivedamount,price){
			var str = '<tr type="subdata">';
			str += '<td ><input type="checkbox" name="check" value=""/></td>';
			str += '<td style="text-align: center" name="materialcode">'+ createInput('materialcode',materialcode) + materialcode + '</td>';
			str += '<td style="text-align: center" name="materialname">'+ createInput('materialname',materialname) + materialname + '</td>';
			
			str += '<td style="text-align: center" name="units">' + createInput('units',units) + units+ '</td>';
			str += '<td style="text-align: center" name="price">' + createInput('price',price) + price + '</td>';
			str += '<td ><input type="text"    name="receivedamount"  value="'+receivedamount+'"/></td>';
			str += '<td style="text-align: center" name="attributedes">'+ createInput('attributedes',attributedes)+ attributedes + '</td>';
			str += '<td ><input type="text"    name="shelves"  value=""/></td>';
			str += '</tr>';
			$('#rows').append(str);
		}
		
		
		function sel_type(){

// 			var ware = null;
// 			var type = $("#inType").find("option:selected").text(); 
// 			if(type=='采购入库'){
// 				ware = 'ware_pur_purorder';
// 			}else if(type == '生产入库'){
// 				ware = 'ware_manuf_order';
// 			}
			
			var type = $("#inType").find("option:selected").text();
			if(type == '采购入库'){
				$("#div_show").show();
				$("#div_scrk").hide();
				$("#div_xsth").hide();
			}else if(type == '生产入库'){
				$("#div_scrk").show();
				$("#div_show").hide();
				$("#div_xsth").hide();
			}else if(type == '销售退货'){
				$("#div_scrk").hide();
				$("#div_show").hide();
				$("#div_xsth").show();
			}else{
				$("#div_scrk").hide();
				$("#div_show").hide();
				$("#div_xsth").hide();
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
						+ '"/><input type="hidden"  name="materialid" value="'+ ids[i] + '"/></td><td ><input type="hidden"    name="materialcode"  value='+ codes[i] +'/>' + codes[i] + '</td> <td ><input type="hidden"    name="materialname"  value='+names[i] +'/> ' + names[i] + ' </td>   <td ><input type="hidden" style="width: 50px;"  name="units"  value='+ units[i] +'/> ' + units[i]
						+ ' </td><td ><input type="hidden"  style="width: 80px;" name="price"  value="' + prices[i] + '"/> ' + prices[i] + ' </td><td ><input type="text" style="width: 50px;"  name="receivedamount"   /> </td> <td ><input type="text"    name="attributedes"   /> </td> <td ><input type="text"    name="shelves"  value=""/></td></tr>');
				$("#warehouseInDetail").append(item);
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
   		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td ><input type="text"    name="materialcode"  value=""/></td> <td ><input type="text"   name="materialname"  value=""/> </td>   <td ><input type="text" style="width: 50px;"  name="units"  value=""/>  </td><td ><input type="text"  style="width: 80px;" name="price"  value=""/>  </td><td ><input type="text" style="width: 50px;"  name="receivedamount"   /> </td> <td ><input type="text"    name="attributedes"   /> </td><td ><input type="text"    name="shelves"  value=""/></td> </tr>');
				$("#warehouseInDetail").append(item);
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
			    <c:when test="${warehouseIn.id !=null}">
			        <span class="tbar-label">编辑入库信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加入库信息</span>
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
		<form id="warehouseInForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">入库单号: </th>
					<td><input type="text" id="code" name="code" value="${warehouseIn.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">入库类型: </th>
					<td>
						<select name="inType" id="inType" class="inputText" onchange="sel_type();">
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
								<c:if test="${warehouseIn.inType=='生产入库'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="生产入库" ${checked}>生产入库</option>
							<c:remove var="checked"/>
								<c:if test="${warehouseIn.inType=='销售退货'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="销售退货" ${checked}>销售退货</option>
							<c:remove var="checked"/>
								<c:if test="${warehouseOut.inType=='其他入库'}">
									<c:set var="checked" value='selected="selected"'></c:set>
								</c:if>
							<option value="其他入库" ${checked}>其他入库</option>
						</select>	
					</td>
				</tr>
				
				<tr>
					<th width="20%">来源单据号: </th>
					<td>
						<input type="hidden" id="inOrderId" name="inOrderId" value="${warehouseIn.inOrderId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="inOrderCode" name="inOrderCode" value="${warehouseIn.inOrderCode}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<font style="display: none;" id="div_show"><a href="javascript:void(0)" onclick="getPurOrders()"  class="link detail">选择</a></font>
						<font style="display: none;" id="div_scrk"><a href="javascript:void(0)" onclick="add_scrk()"  class="link detail">选择</a></font>
						<font style="display: none;" id="div_xsth"><a href="javascript:void(0)" onclick="add_xsth()"  class="link detail">选择</a></font>
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
				<input type="hidden" id="companyid" name="companyid" value="${warehouseIn.companyid}"  class="inputText" validate="{required:false,number:true }"  />
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehouseInDetail" formType="window" type="sub">
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
						入库物品详情
			    		</div>
		    		</td>
				</tr>
				<tr>
				 
			        <th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
			     
					
					<th>物品编码</th>
					<th>物品名称</th>
					<th>单位</th>
					<th>单价</th>
					<th>已收数量</th>
					<th>属性描述</th>
					<th>货架</th>
					
			 
					
					 
				</tr>
				<tbody id="rows">
					</tbody>
				<c:forEach items="${warehouseInDetailList}" var="warehouseInDetailItem" varStatus="status">
				    <tr type="subdata">
				    <td><input type="checkbox" name="check" value="${warehouseInDetailItem.id}" /></td>
					    <td ><input type="text" name="materialcode" value=${warehouseInDetailItem.materialcode}></td>
					    <td ><input type="text" name="materialname" value=${warehouseInDetailItem.materialname}></td>
					      <td ><input type="text" name="units" style="width: 50px;" value=${warehouseInDetailItem.units}></td>
					      <td ><input type="text" name="price" style="width: 80px;" value="${warehouseInDetailItem.price}"/></td>
					       <td ><input type="text" name="receivedamount" style="width: 50px;"  value="${warehouseInDetailItem.receivedamount}"/></td>
					    <td ><input type="text" name="attributedes" value="${warehouseInDetailItem.attributedes}"/></td>
					     <td><input type="text" name="shelves" value="${warehouseInDetailItem.shelves}"/></td>
					     <td style="display:none"><input type="hidden" name="materialid" value="${warehouseInDetailItem.materialid}"/></td>
					     
				    </tr>
				</c:forEach>
				 
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
				<td>
					<textarea rows="2" cols="35" name="attributedes" ></textarea>
				</td>
			</tr>
			
		</table>
	</form>
</div>
</body>
</html>
