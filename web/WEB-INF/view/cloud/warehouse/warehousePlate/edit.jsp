<%--
	time:2013-05-11 14:43:06
	desc:edit the cloud_warehouse_plate
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 盘库信息</title>
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
			var frm=$('#warehousePlateForm').form();
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
						window.location.href = "${ctx}/cloud/warehouse/warehousePlate/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		function priview_warehouse(){
			
			CommonDialog("warehouse_choose",
			function(data) {
				$("#warehouseId").val(data.id);
				$("#warehouseName").val(data.warehousename);
				
				
			});
		}
		
		function preview_accounts(){
			CommonDialog("warehouse_accounts",
					function(data) {
						$(":input[name=materialName]").val(data.productname);
						$(":input[name=materialCode]").val(data.productcode);
						$(":input[name=units]").val(data.units);
						$(":input[name=oldNums]").val(data.nums);
						$(":input[name=oldnumsId]").val(data.id);
					});
		}
		
		function chang_nums(){
			var num =$(":input[name=actualNums]").val() - 0;
			var oldnums = $(":input[name=oldNums]").val() - 0;
			
				var dif = num - oldnums;  
				$(":input[name=differences]").val(dif);
				
// 				if(dif == 0){
// 					$(":input[name=profitLossType]").val("零");
// 				}else if(dif > 0){
// 					$(":input[name=profitLossType]").val("正");
// 				}else{
// 					$(":input[name=profitLossType]").val("负");
// 				}
		}
		
		$(function(){
			var seq =1;
			$("#btnAdd").click(function(){
				$(":input[name=serialNum]").val(seq++);
			});
			
		});
		
		
		
		var dd;
      	function selSups(){
      		//弹出物品选择框
      		var urlShow = '${ctx}/cloud/warehouse/warehouseAccounts/selectWarAccTree.ht';
      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 800, title :'库存物品选择器', name:"frameDialog_"});
      	}
		
      //物品选择列表回调函数
      	function _callBackMyFriends3(friends){
      	 
      		 
      		for(var i=0; i<friends.length; i++){
      		var total=parseInt($("#total").val())+1;
      			var friend = friends[i];
      		 
      		 var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/> </td><td><input type="hidden" name="serialNum" value="'+ total+'"     />'+ total+'</td><td ><input type="hidden"    name="materialCode"  value='+friend[1]+' />'+friend[1]+'</td> <td ><input type="hidden"   name="materialName"  value='+friend[0]+' />'+friend[0]+' </td>   <td ><input type="hidden"    name="units"  value='+friend[2]+' /> '+friend[2]+' </td><td ><input type="hidden"    name="oldNums"  value='+friend[3]+' /> '+friend[3]+' </td><td ><input type="text" style="width: 100px;"  name="actualNums"   onblur="getSumPrice(this);"/> </td> <td ><input type="text"    name="differences"  style="width: 100px;" value="" readonly/> </td><td ><input type="text"    name="remark" style="width: 200px;" value=""/></td> </tr>');
      		 	$("#warehousePlateDetail").append(item);
				$("#total").val(parseInt($("#total").val())+1);
      		}
      		 
      		
    
			
      		dd.close();
      	}
      	
      	
      		function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
				}
			});
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
	
	//计算差异量
		function getSumPrice(obj) {
			//实际
    		var actualNums=$(obj).closest("tr").find('input[name="actualNums"]').val();
    		//账面数
    		var oldNums=$(obj).closest("tr").find('input[name="oldNums"]').val();
    		 
    		 
    		if(actualNums!=""&&!isNaN(actualNums)){
    		
    				var differences=actualNums-oldNums;
    				 //差异量
    				$(obj).closest("tr").find('input[name="differences"]').val(differences);
    		}else{
    			$.ligerMessageBox.alert("请输入数字!");
    		}
    	}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${warehousePlate.id !=null}">
			        <span class="tbar-label">编辑盘库信息</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加盘库信息</span>
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
		<form id="warehousePlateForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<!-- 盘库状态 -->
				<input type="hidden" id="total" name="total" value=0  />
				<input type="hidden" id="pubState" name="pubState" value="${warehousePlate.pubState}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr>
					<th width="20%">编码: </th>
					<td><input type="text" id="plateStorgeCode" name="plateStorgeCode" value="${warehousePlate.plateStorgeCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">仓库名称: </th>
					<td>
						<input type="hidden" id="warehouseId" name="warehouseId" value="${warehousePlate.warehouseId}"  class="inputText" validate="{required:false,number:true }"  />
						<input type="text" id="warehouseName" name="warehouseName" value="${warehousePlate.warehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="priview_warehouse()"  class="link detail">选择</a>
					</td>
				</tr>
				<tr>
					<th width="20%">编制人: </th>
					<td><input type="text" id="operator" name="operator" value="${warehousePlate.operator}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">编制日期: </th>
					<td><input type="text" id="operatedate" name="operatedate" value="<fmt:formatDate value='${warehousePlate.operatedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">所属企业: </th>
					<td><input type="text" id="companyid" name="companyid" value="${warehousePlate.companyid}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="warehousePlateDetail" formType="window" type="sub">
				<tr>
					<td colspan="11">
						<div class="group" align="left">
				   			<a href="javascript:void(0)" onclick="selSups();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd2.jpg'"
												onmouseout="src='${ctx}/images/iconadd.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
										 <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>
			    		</div>
			    		<div align="center">
						盘库详细信息表
			    		</div>
		    		</td>
				</tr>
				<tr>
				<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th>序号</th>
					<th>物品编码</th>
					<th>物品名称</th>
					 
					<th>单位</th>
					<th>账面数</th>
					<th>实际数</th>
					<th>差异量</th>
					<th>备注</th>
					 
				</tr>
				<c:forEach items="${warehousePlateDetailList}" var="warehousePlateDetailItem" varStatus="status">
				    <tr type="subdata">
					    <!-- <td style="text-align: center" name="plateStorageOrderId">${warehousePlateDetailItem.plateStorageOrderId}</td>-->
					     <td><input type="checkbox" name="check" value="${warehousePlateDetailItem.id}" /></td>
					    <td style="text-align: center" name="serialNum">${warehousePlateDetailItem.serialNum}</td>
					    <td style="text-align: center" name="materialCode">${warehousePlateDetailItem.materialCode}</td>
					    <td style="text-align: center" name="materialName">${warehousePlateDetailItem.materialName}</td>
					    <td style="text-align: center" name="units">${warehousePlateDetailItem.units}</td>
					    <td style="text-align: center" name="oldNums">${warehousePlateDetailItem.oldNums}</td>
					    <td style="text-align: center" name="actualNums"><input style="width:100px;" type="text" name="actualNums" onblur="getSumPrice(this);" value="${warehousePlateDetailItem.actualNums}"/></td>
					    <td style="text-align: center" name="differences"><input type="text" style="width:100px;" name="differences" value="${warehousePlateDetailItem.differences}"/></td>
					    <td style="text-align: center" name="remark"><input type="text" style="width:200px;" name="remark" value="${warehousePlateDetailItem.remark}"/></td>
					  
						<!--  <input type="hidden" name="plateStorageOrderId" value="${warehousePlateDetailItem.plateStorageOrderId}"/>-->
						<input type="hidden" name="serialNum" value="${warehousePlateDetailItem.serialNum}"/>
						<input type="hidden" name="materialCode" value="${warehousePlateDetailItem.materialCode}"/>
						<input type="hidden" name="materialName" value="${warehousePlateDetailItem.materialName}"/>
						<input type="hidden" name="attributeDes" value="${warehousePlateDetailItem.attributeDes}"/>
						<input type="hidden" name="units" value="${warehousePlateDetailItem.units}"/>
						<input type="hidden" name="oldNums" value="${warehousePlateDetailItem.oldNums}"/>
						<input type="hidden" name="oldnumsId" value="${warehousePlateDetailItem.oldnumsId}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<!-- <td style="text-align: center" name="plateStorageOrderId"></td> -->
			    	<td style="text-align: center" name="serialNum"></td>
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	 
			    	<td style="text-align: center" name="units"></td>
			    	<td style="text-align: center" name="oldNums"></td>
			    	<td style="text-align: center" name="actualNums"></td>
			    	<td style="text-align: center" name="differences"></td>
			    	<td style="text-align: center" name="remark"></td>
			    	 
			    	<input type="hidden" name="serialNum" value=""/>
			    	<input type="hidden" name="materialCode" value=""/>
			    	<input type="hidden" name="materialName" value=""/>
			    	<input type="hidden" name="units" value=""/>
			    	<input type="hidden" name="oldNums" value=""/>
			    	<input type="hidden" name="actualNums" value=""/>
			    	<input type="hidden" name="differences" value=""/>
			    	<input type="hidden" name="remark" value=""/>
			    	<input type="hidden" name="oldnumsId" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${warehousePlate.id}" />
		</form>
		
	</div>
	<form id="warehousePlateDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		<!-- 
			<tr>
				<th width="20%">盘库单ID: </th>
				<td><input type="text" name="plateStorageOrderId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
		 -->
		 	<input type="hidden" name="oldnumsId" value=""  class="inputText" validate="{required:false,number:true }"/>
			<tr>
				<th width="20%">序号: </th>
				<td><input type="text" name="serialNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/>
					<a href="javascript:void(0)" onclick="preview_accounts()"  class="link detail">选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="units" value=""  class="inputText" validate="{required:false,maxlength:30}"/></td>
			</tr>
			<tr>
				<th width="20%">账面数: </th>
				<td><input type="text" name="oldNums" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">实际数: </th>
				<td><input type="text" name="actualNums" onblur="chang_nums();" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th width="20%">盈亏类型: </th> -->
<!-- 				<td><input type="text" name="profitLossType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%">差异量: </th>
				<td><input type="text" name="differences" readonly="readonly" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td>
				<textarea rows="2" cols="30" name="attributeDes"></textarea>
				</td>
			</tr>
			<tr>
				<th width="20%">备注: </th>
				<td>
				<textarea rows="2" cols="30" name="remark"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
