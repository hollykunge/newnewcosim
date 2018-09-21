<%--
	time:2013-08-16 13:45:02
	desc:edit the cloud_finance_statement
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>
	<title>编辑 结算单</title>
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
			var frm=$('#finStatementForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
		
		
		
		//创建隐藏Input
		function createInput(name, value){
			
			return '<input type="hidden" name="' + name + '" value="' + value + '"/>';
		
		}	
		
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/finance/finStatement/list_m2.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		//增加一行
		function add_onesupps(){
    		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td><input type="text" style="width: 80px;" name="materielCode"  value=""/></td><td><input type="text" style="width: 80px;"  name="materielName"  value=""/></td><td><input type="text" style="width: 80px;" name="materielLev"  value=""/></td><td><input type="text" style="width: 80px;"  name="attributeNote"/></td><td><input type="text" style="width: 50px;" name="unite"  value=""/></td><td><input type="text" name="price" style="width:50px;"  onblur="getSumPrice(this);" /></td><td><input type="text" style="width: 50px;" name="orderNum2"  value=""  onblur="getSumPrice(this);"/></td><td><input type="text" readonly style="width: 80px;" name="sumPrice"  value="" onblur="getSumPrice(this);"/></td>');
				$("#finStatementDetail").append(item);
    	}
	    	
	    	
	    function delproduct(){
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
					var total=$('input[name="sumPrise"]').val();
					if(total!=""&&total!=null){
						total=total-$(this).closest("tr").find('input[name="sumPrise"]').val();
					}
					$('#sumPrise').val(total.toFixed(2));
				}
			});
		}
	    	
	    var add_supps_window;
    	function add_supps(){
    		//弹出物品选择框
    		var urlShow = '${ctx}/cloud/config/material/selectMaterialTree.ht';
    		add_supps_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'物品选择器', name:"frameDialog_"});
    	}	
    	
    	//物品列表回调函数
    	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
    		for ( var i = 0; i < ids.length; i++) {
				var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value="'+ ids[i]+ '"/><input type="hidden"  name="materielId" value="'+ ids[i] + '"/></td><td><input type="hidden" style="width: 80px;" name="materielCode"  value="'+ codes[i]+'"/>' + codes[i] + '</td><td><input type="hidden" style="width: 80px;"  name="materielName"  value="' + names[i] + '"/>' + names[i] + '</td> <td><input type="text" style="width: 80px;" name="materielLev"/></td><td><input type="text" style="width: 80px;"  name="attributeNote" /></td><td><input type="hidden" style="width: 50px;" name="unite"  value="' + units[i] + '"/>' + units[i]+ ' </td><td><input type="text" style="width: 50px;" name="price"  value='+prices[i]+'  onblur="getSumPrice(this);"></td><td><input type="text" name="orderNum2" style="width:50px;" onblur="getSumPrice(this);"/> </td> <td><input type="text" style="width: 80px;" name="sumPrice" readonly /></td></tr>');
				$("#finStatementDetail").append(item);
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
	function getSumPrice(obj) {
		//物品ID
		var materialId=$(obj).closest("tr").find('input[name="materielId"]').val();
		if(materialId==0||materialId==null){
			$.ligerMessageBox.alert("提示信息","未获取到物品ID");
			return;
		}
		//收款企业ID
		var enterpriseId=$('#payeeenterpId').val();
		if(enterpriseId==0||enterpriseId==null){
			$.ligerMessageBox.alert("提示信息","未获取到收款企业ID");
			return;
		}
		//付款企业ID
		var payenterpId=$('#payenterpId').val();
		if(payenterpId==0||payenterpId==null){
			
			$.ligerMessageBox.alert("提示信息","请选择付款企业");
			return;
		}
		var  discount=0;
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${ctx}/cloud/config/priceStrategy/getDiscount.ht',
			data : {materialId : materialId,enterpriseId:enterpriseId,payenterpId:payenterpId},
			success : function(rows){
				//判断是否有值
				if(rows.length>0){
					for(var i=0;i<rows.length;i++){
						discount=rows[i].discount;
						 
					}
				}else{
					discount=1;
					
				}
				
				$(obj).closest("tr").find('input[name="zk"]').val(discount);
				 
				var price=$(obj).closest("tr").find('input[name="price"]').val();
				var orderNum=$(obj).closest("tr").find('input[name="orderNum2"]').val();
				if(!isNaN(price)&&!isNaN(orderNum)){
					var total=$('input[name="sumPrise"]').val();
					var sumPrice=$(obj).closest("tr").find('input[name="sumPrise"]').val();
					if(sumPrice==""||sumPrice==null){
						sumPrice=0;
					}
					if(total==""||total==null){
						total=0;
					}
					total=total-sumPrice;
					if(price!=""&&orderNum!=""){
						var a=orderNum*price*discount;
						price=parseFloat(price);
						$(obj).closest("tr").find('input[name="sumPrise"]').val(a.toFixed(2));
						$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
						total+=a;
						$('#sumPrise').val(total.toFixed(2));
					}
				}else{
					$.ligerMessageBox.alert("请输入数字!");
				}
			}
		});
		
	}	
	
	
	function add_info(){
		CommonDialog("org_info_list",
				function(info){
				$("#payeeenterpName").val(info.NAME);
				$("#payeeenterpId").val(info.SYS_ORG_INFO_ID);
		});
		
	}
	
		function add_supp(){
			CommonDialog("fin_cgrk",
					function(order) {
					
					
						$("#sourceformId").val(order.id);
						$("#sourceformCode").val(order.code);
						$("#payenterpUserid").val(order.purenterp_userid);
						$("#payenterpUsername").val(order.purenterp_username);
						$("#payenterpId").val(order.payenterp_id);
						$("#payenterpName").val(order.payenterp_name);
						
						
						$("#rows").empty();
						$.ajax({
							type : 'post',
							dataType : 'json',
							url : '${ctx}/cloud/finance/finStatement/getOrderDetails.ht',
							data : {value : order.id},
							success : function(rows){
								//判断是否有值
								if(rows.length>0){
									for(var i=0;i<rows.length;i++){
										var serialnumber = i + 1 ;
										var materielId=rows[i].materielId;
										var materialcode = rows[i].materielCode;
										var materialname = rows[i].materielName;
										var attributedes = rows[i].attributeInfo;
										var receivedamount = rows[i].orderNum;
										var units = rows[i].unit;
										var price = rows[i].price;
										if(materielId==null){
											materielId="";
										}
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
										if(receivedamount==null){
											receivedamount="";
										}
										if(price==null){
											price="";
										}
										if(units==null){
											units="";
										}
										materialcode=trim(materialcode);
										materialname=trim(materialname);
										units=trim(units);
										appendRow(materielId,serialnumber,materialcode,attributedes,units,materialname,receivedamount,price);
									};
								}
							}
						});
			});
		}
		
		
		//销售出库
		function add_sale(){
			CommonDialog("fin_sale_ck",
					function(order) {
						
						$("#sourceformId").val(order.id);
						$("#sourceformCode").val(order.code);
						//采购企业，人员
						/* $("#payenterpUserid").val(order.purenterp_userid);
						$("#payenterpUsername").val(order.purenterp_username);
						$("#payenterpId").val(order.payenterp_id);
						$("#payenterpName").val(order.payenterp_name); */
						
						$("#rows").empty();
						
						
						$.ajax({
							type : 'post',
							dataType : 'json',
							url : '${ctx}/cloud/finance/finStatement/getSaleDetails.ht',
							data : {value : order.id},
							success : function(rows){
								
								//判断是否有值
								if(rows.length>0){
									for(var i=0;i<rows.length;i++){
										var serialnumber;
										var materielId=rows[i].materielId;
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
										if(receivedamount==null){
											receivedamount="";
										}
										if(price==null){
											price="";
										}
										if(units==null){
											units="";
										}
										materialcode=trim(materialcode);
										materialname=trim(materialname);
										units=trim(units);
										appendRow(materielId,serialnumber,materialcode,attributedes,units,materialname,receivedamount,price);
										
									};
									
								}
							}
						});
			});
		}
		
		
		
		
		
		function trim(str){
			if(str!=""&&str!=null){
    			return str.replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');
    		}else{
    			return str;
    		}   
		}	   
		function appendRow(materielId,serialnumber,materialcode,attributedes,units,materialname,receivedamount,price){
			var str = '<tr type="subdata">';
			str += '<td><input type="checkbox" name="check" value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="materielId"  value="'+materielId+'"/></td>';
			str += '<td><input style="width: 80px;" type="text" name="materielCode"  value="'+materialcode+'"/></td>';
			str += '<td><input style="width: 80px;" type="text" name="materielName"  value="'+materialname+'"/></td>';
			str += '<td><input style="width: 80px;" type="text" name="materielLev"  value=""/></td>';
			str += '<td><input style="width: 80px;" type="text" name="attributeNote"  value="'+attributedes+'"/></td>';
			str += '<td><input style="width: 50px;" type="text" name="unite"  value="'+units+'"/></td>';
			str += '<td><input style="width: 50px;" type="text" name="price"  value="'+price+'" onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 50px;" type="text" name="zk"  value="" onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 50px;" type="text" name="orderNum2"  value="'+receivedamount+'" onblur="getSumPrice(this);"/></td>';
			str += '<td><input style="width: 80px;" readonly type="text" name="sumPrise"  value="" onblur="getSumPrice(this);"/></td>';
			str += '</tr>';
			$('#rows').append(str);
		}
		
		
		function sel_type(){
			var type = $("#sourceformType").find("option:selected").text(); 
			
			if(type=='销售出库'){
				$("#div_supp").hide();
				$("#div_sale").show();
				$("#div_smwx").hide();
			}else if(type=='采购入库'){
				$("#div_supp").show();
				$("#div_sale").hide();
				$("#div_smwx").hide();
			}else if(type=='上门维修'){
				$("#div_supp").hide();
				$("#div_sale").hide();
				$("#div_smwx").show();
			}else{
				$("#div_supp").hide();
				$("#div_sale").hide();
				$("#div_smwx").hide();
			}
			
			
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${finStatement.id !=null}">
			        <span class="tbar-label">编辑结算单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加结算单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存并发起</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list_m2.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="finStatementForm" method="post" action="save_m2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td colspan="3"><input type="text" id="code" name="code" value="${finStatement.code}" size="30" class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<!-- 
					<th width="20%">状态: </th>
					<td><input type="text" id="status" name="status" value="${finStatement.status}"  class="inputText" validate="{required:false,number:true }"  /></td>
					 -->
					
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${finStatement.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${finStatement.operaterName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<!--<th width="20%">收款企业ID: </th>-->
					 
					<input type="hidden" id="state" name="state" value="生效"   />
					<input type="hidden" id="payeeenterpId" name="payeeenterpId" value="${finStatement.payeeenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款企业用户ID: </th>-->
					<input type="hidden" id="payenterpUserid" name="payenterpUserid" value="${finStatement.payenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款企业ID: </th>-->
					<input type="hidden" id="payenterpId" name="payenterpId" value="${finStatement.payenterpId}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">制单人ID: </th>-->
					<input type="hidden" id="operaterId" name="operaterId" value="${finStatement.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
					<!--<th width="20%">来源单据ID: </th>-->
					<input type="hidden" id="sourceformId" name="sourceformId" value="${finStatement.sourceformId}"  class="inputText" validate="{required:false,maxlength:512}"  />
					<!--<th width="20%">收款企业用户ID: </th>-->
					<input type="hidden" id="payeeenterpUserid" name="payeeenterpUserid" value="${finStatement.payeeenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
					<input type="hidden" id="remark1" name="remark1" value="1"  class="inputText"   />
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td>
					<select name="sourceformType" id="sourceformType" class="inputText" onchange="sel_type();">
						<c:remove var="checked"/>
							<c:if test="${finStatement.sourceformType==null || finStatement.sourceformType==''}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="" ${checked}>请选择</option>
							
							<c:remove var="checked"/>
							<c:if test="${finStatement.sourceformType=='采购入库'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="采购入库" ${checked}>采购入库</option>
							
							<c:remove var="checked"/>
							<c:if test="${finStatement.sourceformType=='销售出库'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="销售出库" ${checked}>销售出库</option>
							
							<c:remove var="checked"/>
							<c:if test="${finStatement.sourceformType=='上门维修'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="上门维修" ${checked}>上门维修</option>
							
							<c:remove var="checked"/>
							<c:if test="${finStatement.sourceformType=='其他'}">
								<c:set var="checked" value='selected="selected"'></c:set>
							</c:if>
							<option value="其他" ${checked}>其他</option>
					</select>
					<!--<input type="text" id="sourceformType" name="sourceformType" value="采购入库" readonly="readonly" class="inputText" validate="{required:true,maxlength:96}"  />-->
					</td>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${finStatement.sourceformCode}"  class="inputText" validate="{required:false,maxlength:512}"  />
						<font id="div_supp" style="display: none; "><a href="javascript:void(0)" onclick="add_supp()"  class="link detail">选择</a></font>
						<font id="div_sale" style="display: none; "><a href="javascript:void(0)" onclick="add_sale()"  class="link detail">选择</a></font>
						<font id="div_smwx" style="display: none; "><a href="javascript:void(0)" onclick="add_smwx()"  class="link detail">选择</a></font>
				</tr>
				<tr>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrise" name="sumPrise" readonly value="${finStatement.sumPrise}"  class="inputText"   /></td>
					<th width="20%">币种: </th>
					<td>
					<ap:selectDB name="currencyType" id="currencyType" where="typeId=10000000280575" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${finStatement.currencyType}">
										</ap:selectDB>
					
					<!--<input type="text" id="currencyType" name="currencyType" value="${finStatement.currencyType}"  class="inputText" validate="{required:true,maxlength:96}"  />-->
					
					</td>
				</tr>
				<tr>
					<th width="20%">收款企业: </th>
					<td><input type="text" id="payeeenterpName" name="payeeenterpName" value="${finStatement.payeeenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  />
					<a href="javascript:void(0)" onclick="add_info()"  class="link detail">选择</a></td>
					<th width="20%">收款人: </th>
					<td><input type="text" id="payeeenterpUsername" name="payeeenterpUsername" value="${finStatement.payeeenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>	
					<th width="20%">付款企业: </th>
					<td><input type="text" id="payenterpName" name="payenterpName" value="${finStatement.payenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">付款人: </th>
					<td><input type="text" id="payenterpUsername" name="payenterpUsername" value="${finStatement.payenterpUsername}"  class="inputText" validate="{required:true,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="finStatementDetail" formType="window" type="sub">
				<tr>
					<td colspan="23">
					
<!-- 						<div class="group" align="left"> -->
<!-- 				   			 <a href="javascript:void(0)" onclick="add_supps();"   style="text-decoration: none;"> -->
<%-- 											<img src="${ctx}/images/iconadd.jpg" width="52" height="18" --%>
<%-- 												onmouseover="src='${ctx}/images/iconadd2.jpg'" --%>
<%-- 												onmouseout="src='${ctx}/images/iconadd.jpg'" --%>
<!-- 												style="border: 0;"> </a>&nbsp;&nbsp; -->
								  
<!-- 								  <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;"> -->
<%-- 											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52" --%>
<%-- 												onmouseover="src='${ctx}/images/icon_del.jpg'" --%>
<%-- 												onmouseout="src='${ctx}/images/icon_del2.jpg'" --%>
<!-- 												style="border: 0;"> </a>&nbsp;&nbsp; -->
												
<!-- 								<a href="javascript:void(0)" onclick="add_onesupps();"   style="text-decoration: none;"> -->
<%-- 											<img src="${ctx}/images/iconadd3.jpg" width="52" height="18" --%>
<%-- 												onmouseover="src='${ctx}/images/iconadd4.jpg'" --%>
<%-- 												onmouseout="src='${ctx}/images/iconadd3.jpg'" --%>
<!-- 												style="border: 0;"> </a>&nbsp;&nbsp; -->
<!-- 			    		</div> -->
			    	
			    		<div align="center">
						结算单详细信息表
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th style="display:none">单证号</th>
					<th style="display:none">制单日期</th>
					<th style="display:none">制单人ID</th>
					<th style="display:none">制单人</th>
					<th style="display:none">订单ID</th>
					<th style="display:none">订单号</th>
					<th style="display:none">订单类型</th>
					<th>物品ID</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th style="display:none">订货数量</th>
					<th style="display:none">本次计算数量</th>
					<th style="display:none">已结算数量</th>
					<th style="display:none">收获入库数量</th>
					<th style="display:none">退货数量</th>
					<th style="display:none">未结算数量</th>
					<th>单位</th>
					<th>单价</th>
					<th>折扣</th>
					<th>数量</th>
					<th>总金额</th>
					<th style="display:none">操作</th>
				</tr>
				<tbody id="rows">
				<c:forEach items="${finStatementDetailList}" var="finStatementDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="code">${finStatementDetailItem.code}</td>
<%-- 						<td style="text-align: center" name="operateDate"><fmt:formatDate value='${finStatementDetailItem.operateDate}' pattern='yyyy-MM-dd'/></td>								 --%>
<%-- 					    <td style="text-align: center" name="operaterId">${finStatementDetailItem.operaterId}</td> --%>
<%-- 					    <td style="text-align: center" name="operaterName">${finStatementDetailItem.operaterName}</td> --%>
<%-- 					    <td style="text-align: center" name="orderId">${finStatementDetailItem.orderId}</td> --%>
<%-- 					    <td style="text-align: center" name="orderCode">${finStatementDetailItem.orderCode}</td> --%>
<%-- 					    <td style="text-align: center" name="orderType">${finStatementDetailItem.orderType}</td> --%>
<%-- 					    <td style="text-align: center" name="materielId">${finStatementDetailItem.materielId}</td> --%>
					    <td style="text-align: center" name="materielCode">${finStatementDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${finStatementDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${finStatementDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeNote">${finStatementDetailItem.attributeNote}</td>
<%-- 					    <td style="text-align: center" name="orderNum">${finStatementDetailItem.orderNum}</td> --%>
<%-- 					    <td style="text-align: center" name="currentstatementNum">${finStatementDetailItem.currentstatementNum}</td> --%>
<%-- 					    <td style="text-align: center" name="statedNum">${finStatementDetailItem.statedNum}</td> --%>
<%-- 					    <td style="text-align: center" name="normaltowarehourseNum">${finStatementDetailItem.normaltowarehourseNum}</td> --%>
<%-- 					    <td style="text-align: center" name="returnNum">${finStatementDetailItem.returnNum}</td> --%>
<%-- 					    <td style="text-align: center" name="unstatementNum">${finStatementDetailItem.unstatementNum}</td> --%>
					    <td style="text-align: center" name="unite">${finStatementDetailItem.unite}</td>
					    <td style="text-align: center" name="price">${finStatementDetailItem.price}</td>
					    <td style="text-align: center" name="orderNum2">${finStatementDetailItem.orderNum2}</td>
					    <td style="text-align: center" name="sumPrise">${finStatementDetailItem.sumPrise}</td>
					
						<input type="hidden" name="code" value="${finStatementDetailItem.code}"/>
					    <input type="hidden" name="operateDate" value="<fmt:formatDate value='${finStatementDetailItem.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="operaterId" value="${finStatementDetailItem.operaterId}"/>
						<input type="hidden" name="operaterName" value="${finStatementDetailItem.operaterName}"/>
						<input type="hidden" name="orderId" value="${finStatementDetailItem.orderId}"/>
						<input type="hidden" name="orderCode" value="${finStatementDetailItem.orderCode}"/>
						<input type="hidden" name="orderType" value="${finStatementDetailItem.orderType}"/>
						<input type="hidden" name="materielId" value="${finStatementDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${finStatementDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${finStatementDetailItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${finStatementDetailItem.materielLev}"/>
						<input type="hidden" name="attributeNote" value="${finStatementDetailItem.attributeNote}"/>
						<input type="hidden" name="orderNum" value="${finStatementDetailItem.orderNum}"/>
						<input type="hidden" name="currentstatementNum" value="${finStatementDetailItem.currentstatementNum}"/>
						<input type="hidden" name="statedNum" value="${finStatementDetailItem.statedNum}"/>
						<input type="hidden" name="normaltowarehourseNum" value="${finStatementDetailItem.normaltowarehourseNum}"/>
						<input type="hidden" name="returnNum" value="${finStatementDetailItem.returnNum}"/>
						<input type="hidden" name="unstatementNum" value="${finStatementDetailItem.unstatementNum}"/>
						<input type="hidden" name="unite" value="${finStatementDetailItem.unite}"/>
						<input type="hidden" name="price" value="${finStatementDetailItem.price}"/>
						<input type="hidden" name="orderNum2" value="${finStatementDetailItem.orderNum2}"/>
						<input type="hidden" name="sumPrise" value="${finStatementDetailItem.sumPrise}"/>
				    </tr>
				</c:forEach>
					</tbody>
				
<!-- 				<tr type="append"> -->
<!-- 			    	<td style="text-align: center" name="code"></td> -->
<!-- 					<td style="text-align: center" name="operateDate"></td>								 -->
<!-- 			    	<td style="text-align: center" name="operaterId"></td> -->
<!-- 			    	<td style="text-align: center" name="operaterName"></td> -->
<!-- 			    	<td style="text-align: center" name="orderId"></td> -->
<!-- 			    	<td style="text-align: center" name="orderCode"></td> -->
<!-- 			    	<td style="text-align: center" name="orderType"></td> -->
<!-- 			    	<td style="text-align: center" name="materielId"></td> -->
<!-- 			    	<td style="text-align: center" name="materielCode"></td> -->
<!-- 			    	<td style="text-align: center" name="materielName"></td> -->
<!-- 			    	<td style="text-align: center" name="materielLev"></td> -->
<!-- 			    	<td style="text-align: center" name="attributeNote"></td> -->
<!-- 			    	<td style="text-align: center" name="orderNum"></td> -->
<!-- 			    	<td style="text-align: center" name="currentstatementNum"></td> -->
<!-- 			    	<td style="text-align: center" name="statedNum"></td> -->
<!-- 			    	<td style="text-align: center" name="normaltowarehourseNum"></td> -->
<!-- 			    	<td style="text-align: center" name="returnNum"></td> -->
<!-- 			    	<td style="text-align: center" name="unstatementNum"></td> -->
<!-- 			    	<td style="text-align: center" name="unite"></td> -->
<!-- 			    	<td style="text-align: center" name="price"></td> -->
<!-- 			    	<td style="text-align: center" name="orderNum2"></td> -->
<!-- 			    	<td style="text-align: center" name="sumPrise"></td> -->
<!-- 			    	<td style="text-align: center"> -->
<!-- 			    		<a href="javascript:void(0)" class="link del">删除</a> -->
<!-- 			    		<a href="javascript:void(0)" class="link edit">编辑</a> -->
<!-- 			    	</td> -->
<!-- 			    	<input type="hidden" name="code" value=""/> -->
<!-- 			    	<input type="hidden" name="operateDate" value="" class="inputText date"/> -->
<!-- 			    	<input type="hidden" name="operaterId" value=""/> -->
<!-- 			    	<input type="hidden" name="operaterName" value=""/> -->
<!-- 			    	<input type="hidden" name="orderId" value=""/> -->
<!-- 			    	<input type="hidden" name="orderCode" value=""/> -->
<!-- 			    	<input type="hidden" name="orderType" value=""/> -->
<!-- 			    	<input type="hidden" name="materielId" value=""/> -->
<!-- 			    	<input type="hidden" name="materielCode" value=""/> -->
<!-- 			    	<input type="hidden" name="materielName" value=""/> -->
<!-- 			    	<input type="hidden" name="materielLev" value=""/> -->
<!-- 			    	<input type="hidden" name="attributeNote" value=""/> -->
<!-- 			    	<input type="hidden" name="orderNum" value=""/> -->
<!-- 			    	<input type="hidden" name="currentstatementNum" value=""/> -->
<!-- 			    	<input type="hidden" name="statedNum" value=""/> -->
<!-- 			    	<input type="hidden" name="normaltowarehourseNum" value=""/> -->
<!-- 			    	<input type="hidden" name="returnNum" value=""/> -->
<!-- 			    	<input type="hidden" name="unstatementNum" value=""/> -->
<!-- 			    	<input type="hidden" name="unite" value=""/> -->
<!-- 			    	<input type="hidden" name="price" value=""/> -->
<!-- 			    	<input type="hidden" name="orderNum2" value=""/> -->
<!-- 			    	<input type="hidden" name="sumPrise" value=""/> -->
<!-- 		 		</tr> -->
		    </table>
			<input type="hidden" name="id" value="${finStatement.id}" />
		</form>
		
	</div>
	<form id="finStatementDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">code: </th>
				<td><input type="text" name="code" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">operate_date: </th>
				<td><input type="text" name="operateDate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">operater_id: </th>
				<td><input type="text" name="operaterId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">operater_name: </th>
				<td><input type="text" name="operaterName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">order_id: </th>
				<td><input type="text" name="orderId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">order_code: </th>
				<td><input type="text" name="orderCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">order_type: </th>
				<td><input type="text" name="orderType" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_id: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_code: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_name: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_lev: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">attribute_note: </th>
				<td><input type="text" name="attributeNote" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">order_num: </th>
				<td><input type="text" name="orderNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">currentstatement_num: </th>
				<td><input type="text" name="currentstatementNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">stated_num: </th>
				<td><input type="text" name="statedNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">normaltowarehourse_num: </th>
				<td><input type="text" name="normaltowarehourseNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">return_num: </th>
				<td><input type="text" name="returnNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">unstatement_num: </th>
				<td><input type="text" name="unstatementNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">unite: </th>
				<td><input type="text" name="unite" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">price: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">order_num2: </th>
				<td><input type="text" name="orderNum2" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">sum_prise: </th>
				<td><input type="text" name="sumPrise" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
