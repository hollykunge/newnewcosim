<%--
	time:2013-05-11 14:43:06
	desc:edit the 账目初始化设置
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 账目初始化设置</title>
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
			var frm=$('#warehouseSettingsForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehouseSettings/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		
		function preview_material(){
			CommonDialog("warehouse_material",
					function(data) {
						
						
						$(":input[name=productname]").val(data.name);
						$(":input[name=productcode]").val(data.code);
						$(":input[name=units]").val(data.unit);
						$(":input[name=price]").val(data.price);
					});
		}
		
	 
		
		
		function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
		}
	
	
		var add_supps_window;
    	function add_supps(){
    		//弹出供应商物品选择框
    		var urlShow = '${ctx}/cloud/config/material/selectMaterialTree.ht';
    		add_supps_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'物品选择器', name:"frameDialog_"});
    	}	
    	
    	//商圈列表回调函数
    	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
    	  
	   
    		for ( var i = 0; i < ids.length; i++) {
    		var total=parseInt($("#total").val())+1;
				var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value="'
						+ ids[i]
						+ '"/><input type="hidden"  name="productid" value="'+ids[i]+'"/></td><td><input type="text" name="batchnumber" value="第'+ total+'批" style="width: 50px;"  class="inputText"  /></td><td ><input type="hidden"    name="productcode"  value='+codes[i]+'/>' + codes[i] + '</td> <td ><input type="hidden"    name="productname"  value='+names[i]+'/> ' + names[i] + ' </td> <td ><input type="hidden"   name="specifications"  value='+models[i]+'/>'+models[i]+'</td>  <td ><input type="hidden" style="width: 50px;"  name="units"  value='+units[i]+'/> ' + units[i]
						+ ' </td><td ><input type="text"    name="shelves"  value=""/>  </td><td ><input type="text" style="width: 50px;"  name="nums"   /> </td> <td ><input type="hidden" style="width: 50px;"  name="price"   value='+prices[i]+'/>' + prices[i] + ' </td>   </tr>');
				$("#warehouseSettingsDetail").append(item);
				
				$("#total").val(parseInt($("#total").val())+1);
			}
    		add_supps_window.close();
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
	 
	  var total=parseInt($("#total").val())+1;
	  
   		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden"  name="productid" value=""/></td><td><input type="text" name="batchnumber" value="第'+ parseInt(total) + '批" style="width: 50px;"  class="inputText"  /></td> <td><input type="text" name="productcode" value=""   class="inputText"  /></td><td ><input type="text"   name="productname"  value=""/>  </td> <td ><input type="text"   name="specifications"  value=""/></td> <td ><input type="text" style="width: 50px;"  name="units"  value=""/>  </td><td ><input type="text"    name="shelves"   /> </td> <td ><input type="text"  style="width: 50px;" name="nums"  value=""/>  </td><td ><input type="text"  style="width: 50px;" name="price"  value=""/>  </td>  </tr>');
			$("#warehouseSettingsDetail").append(item);
			$("#total").val(parseInt($("#total").val())+1);
   	}	
		
			function priview_warehouse(){
			
			CommonDialog("warehouse_choose",
			function(data) {
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				
				
			});
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${warehouseSettings.id !=null}">
			        <span class="tbar-label">编辑账目初始化设置</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加账目初始化设置</span>
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
		<form id="warehouseSettingsForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<!-- 所属公司 -->
				<input type="hidden" id="companyid" name="companyid" value="${warehouseSettings.companyid}"  class="inputText" />
				<!-- 填单人ID -->
				
				<input type="hidden" id="total" name="total" value=0  />
				<input type="hidden" id="operatorId" name="operatorId" value="${warehouseSettings.operatorId}"  class="inputText" />
				<!-- 初始化状态 -->
				<input type="hidden" id="setState" name="setState" value="${warehouseSettings.setState }"  class="inputText" />
				<tr>
					<th width="20%">编码: </th>
					<td><input type="text" id="code" name="code" value="${warehouseSettings.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
					
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operator" name="operator" value="${warehouseSettings.operator}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">日期: </th>
					<td><input type="text" id="operatedate" name="operatedate" value="<fmt:formatDate value='${warehouseSettings.operatedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">仓库名称: </th>
					<td>
						<input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseSettings.warehouseId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="warehouseName" name="warehouseName" value="${warehouseSettings.warehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="priview_warehouse()"  class="link detail">选择</a>
					</td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehouseSettingsDetail" formType="window" type="sub">
				<tr>
					<td colspan="9">
						<div class="group" align="left">
				   		 
				   			
				   			 <a href="javascript:void(0)"   onclick="add_supps();"   style="text-decoration: none;">
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
												style="border: 0;"> </a>&nbsp;&nbsp;
												
								-->				
			    		</div>
			    		<div align="center">
						账目初始化设置-详细 
			    		</div>
		    		</td>
				</tr>
				<tr>
				<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th>批次/编号</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品规格</th>
					<th>单位</th>
					
					<th>货架</th>
					<th>数量</th>
					 <th>单价</th>
				</tr>
				<c:forEach items="${warehouseSettingsDetailList}" var="warehouseSettingsDetailItem" varStatus="status">
				    <tr type="subdata">
				     <td><input type="checkbox" name="check" value="${warehouseSettingsDetailItem.id}" /></td>
					    <td><input type="text" name="batchnumber" style="width: 50px;" value="${warehouseSettingsDetailItem.batchnumber}"/></td>
					    <td>${warehouseSettingsDetailItem.productcode}</td>
					    <td>${warehouseSettingsDetailItem.productname}</td>
					    <td>${warehouseSettingsDetailItem.specifications}</td>
					    <td>${warehouseSettingsDetailItem.units}</td>
					    <td><input type="text" name="shelves" value="${warehouseSettingsDetailItem.shelves}"/></td>
					    <td><input type="text" name="nums" style="width: 50px;" value="${warehouseSettingsDetailItem.nums}"/></td>
					     <td>${warehouseSettingsDetailItem.price}</td>
					    
					     <input type="hidden" name="productcode" value="${warehouseSettingsDetailItem.productcode}"/>
					     <input type="hidden" name="productname" value="${warehouseSettingsDetailItem.productname}"/>
					    <input type="hidden" name="specifications" value="${warehouseSettingsDetailItem.specifications}"/>
						 <input type="hidden" name="units" value="${warehouseSettingsDetailItem.units}"/>
						  <input type="hidden" name="price" value="${warehouseSettingsDetailItem.price}"/>
						 
						
						
						
				    </tr>
				</c:forEach>
			 
		    </table>
			<input type="hidden" name="id" value="${warehouseSettings.id}" />
		</form>
		
	</div>
	<form id="warehouseSettingsDetailForm" style="display:none" width="500" formType="page">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">批次/编号: </th>
				<td><input type="text" name="batchnumber" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<input type="hidden" name="productid" value=""  class="inputText" validate="{required:false,number:true }"/>
			<tr>
				<th width="20%">物品编码: </th>
				<td>
					<input type="text" name="productcode" value=""  class="inputText" validate="{required:false,maxlength:96}"/>
					<a href="javascript:void(0)" onclick="preview_material()"  class="link detail">选择</a>
				</td>				
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="productname" value=""  class="inputText" validate="{required:false,maxlength:96}"/>				
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">货架: </th>
				<td><input type="text" name="shelves" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">数量: </th>
				<td><input type="text" name="nums" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">价格: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">规格说明: </th>
				<td>
				
					<textarea rows="2" cols="35" name="specifications"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
