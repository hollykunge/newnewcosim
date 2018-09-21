<%--
	time:2013-05-06 13:02:00
	desc:edit the cloud_pur_order
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/cloud/global.jsp"%>
<html>
<head>
	<title>编辑 采购订单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
	
	<script type="text/javascript">
			
	function getHistory(obj){
		var code=$(obj).closest("tr").find('input[name="materielCode"]').val();
		var suppenterpId=$('input[name="suppenterpId"]').val();
		var purenterpId=$('input[name="purenterpId"]').val();
		if(code!=null&&code!=""){
			window.open ('${ctx}/cloud/purchase/purOrder/getDetailHistory.ht?suppenterpId='+suppenterpId+'&materielCode='+code+'&purenterpId='+purenterpId,'物品采购历史','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
		}else{
			$.ligerMessageBox.alert("提示","请输入物品编码!");
		}
		
	}
	
	var manager = null;
	$(function() {
		
		
		
			 
		 
		
		
		var options={};
		
		if(showResponse){
			options.success=showResponse;
		}
		var frm=$('#purOrderForm').form();
		$("a.save").click(function() {
			
			var a = $("input[name='materielName']").val();  
			if(a==null){
				 alert("请录入采购物品信息！");
			 }else{
					frm.setData();
					frm.ajaxForm(options);
					if(frm.valid()){
					manager = $.ligerDialog.waitting('正在保存中,请稍候...');
						form.submit();
					}
			 }
		});
	});
	function getSumPrice(obj) {
		var price=$(obj).closest("tr").find('input[name="price"]').val();
		var orderNum=$(obj).closest("tr").find('input[name="orderNum"]').val();
		if(!isNaN(price)&&!isNaN(orderNum)){
			var total=$('input[name="sumPrice"]').val();
			var sumPrice=$(obj).closest("tr").find('input[name="sumPrice"]').val();
			if(sumPrice==""||sumPrice==null){
				sumPrice=0;
			}
			if(total==""||total==null){
				total=0;
			}
			total=total-sumPrice;
		
			if(price!=""&&orderNum!=""){
				var a=orderNum*price;
				price=parseFloat(price);
				$(obj).closest("tr").find('input[name="sumPrice"]').val(a.toFixed(2));
				$(obj).closest("tr").find('input[name="price"]').val(price.toFixed(2));
				total+=a;
				$('#sumPrice').val(total.toFixed(2));
				tax_rate();
			}
		}else{
			$.ligerMessageBox.alert("请输入数字!");
		}
	}	
	
	function order_pay(){
			var pay = $("#pay_Type").find("option:selected").text();
			$('#payType').val(pay);
		}
		function order_bill(){
			  var pay = $("#bill_Type").find("option:selected").text();
				$('#billType').val(pay);
		}
	
	function tax_rate(){
		var rate = $('#sumPrice').val() - 0;
		var tax = $('#taxRate').val() - 0;
		tax = tax / 100;
		var sum_tax = rate * tax ;

		$('#remark1').val(sum_tax.toFixed(2));
	}
	
	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if(manager!=null){//添加保存
			manager.close();
			if (obj.isSuccess()) {//流程收回后再保存
				var s = window.location.href;
				if(s.indexOf('taskId')!=-1){
					$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
						if(rtn){
							window.location.reload();
						}else{
							window.close();
							window.opener.location.reload();
						}
					});
				}else{//保存并发起
					$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
						if(rtn){
							window.location.href = "${ctx}/cloud/purchase/purOrder/dir_purchase.ht";
						}else{
						window.location.href = "${ctx}/cloud/purchase/purOrder/list.ht?type=direct";
						}
					});
				}
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}else{//流程点击同意
			if (obj.isSuccess()) {// 成功
				$.ligerMessageBox.success('提示信息', '任务执行成功！', function() {
					window.onbeforeunload = null;
					window.close();
					window.opener.location.reload();
				});
			} else {// 失败
				$.ligerMessageBox.error('出错了', obj.getMessage());
			}
		}
	}
			
			var dd;
	      	function selSups(){
	      		//弹出供应企业选择框
	      		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriendsRadio.ht';
	      		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 800, title :'企业选择器', name:"frameDialog_"});
	      	}
	      	
	      	//商圈列表回调函数
	      	function _callBackMyFriends(friend){
	      		$("#en_id").val(friend[0]);
				$("#en_name").val(friend[1]);
				$("#suppenterpUsername").val(friend[4]);
			 	
	      		dd.close();
			}
			
			//采购历史记录回调
	      	function _callBackMyHistory(id,suppenterpId,suppenterpName,suppenterpUsername){
	      	
				$("#purOrderDetail tr").eq(2).nextAll().remove();
	      		$("#en_id").val(suppenterpId);
				$("#en_name").val(suppenterpName);
				$("#suppenterpUsername").val(suppenterpUsername);
			 	$.ajax({
			 		type : 'post',
					dataType : 'json',
					url : '${ctx}/cloud/purchase/purOrder/getMyHistory.ht',
					data : {value : id},
					success : function(rows){
						//判断是否有值
						if(rows.length>0){
							var total="";
							for(var i=0;i<rows.length;i++){
								var materielId=rows[i].materielId;
								var	materielCode=rows[i].materielCode;
					 			var	model=rows[i].model;
								var	materielName=rows[i].materielName;
								var	materielLev=rows[i].materielLev;
								var	attributeInfo=rows[i].attributeInfo;
								var	unit=rows[i].unit;
								var	orderNum=rows[i].orderNum;
								var	price=rows[i].price.toFixed(2);
								var	sumPrice=rows[i].sumPrice.toFixed(2);
								var	deliveryEnddate=rows[i].deliveryEnddate;
								if(sumPrice==""||sumPrice==null){
									sumPrice=0;
								}
								total=total*1+sumPrice*1;
    							var item = $('<tr type="subdata"><td style="text-align: center"><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td style="text-align: center" ><input type="text" style="width: 70px;" name="materielCode"  value=" '+materielCode+ ' "/></td> <td style="text-align: center" ><input type="text" style="width: 80px;"  name="model"  value=" '+model+' "/> </td> <td style="text-align: center" ><input type="text"  style="width: 70px;" name="materielName"  value=" '+materielName+' "/>  </td><td style="text-align: center" ><input type="text" style="width: 80px;"  name="attributeInfo"   value=" '+attributeInfo+' "/> <td style="text-align: center" ><input type="text" style="width: 40px;" name="unit"  value=" '+unit+' "/>  </td><td style="text-align: center" ><input type="text"   name="orderNum" style="width:40px;"  value=" '+orderNum+' "  onblur="getSumPrice(this);" /> </td> <td style="text-align: center" ><input type="text" style="width: 50px;" name="price"  value=" '+price+' "  onblur="getSumPrice(this);"/>  </td><td style="text-align: center" ><input type="text" readonly style="width: 80px;" name="sumPrice"  value=" '+sumPrice+' "/>  </td> <td style="text-align: center"> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /> <input type="hidden"  name="ismat" value="0"/> </td><td style="text-align: center"><a href="javascript:void(0)" onclick="getHistory(this)">查看</a></td></tr>');
								$("#purOrderDetail").append(item);
							};
							$('#sumPrice').val(total.toFixed(2));
						}
					}
			 	});
	      		dd.close();
			}
	 
	 var tip;
     var num = 0;
     function f_tip2(rows) {
         if (!tip) {
        	 var $d = $('<div class="msg"></div>');
        	 var $u = $d.append('<ul></ul>');
        	 for(var i=0; i<rows.length; i++){
        		var row = rows[i];
        		var t = row.createTime;
        		t = new Date(t);
        		t = t.format('yyyy-MM-dd hh:mm:ss');
        	 	$u.append('<li><a href="${ctx}/platform/bpm/taskInfo/toStart.ht?taskId=' + row.id + '" target="_blank">' + row.subject + ' ' + t + '</a></li>');
        	 }
        	 var h = 70 + rows.length * 15;
             tip = $.ligerDialog.tip({ title: '待办消息', content: $d.html(), height: h, width: 400 });
         }
         else {
             var visabled = tip.get('visible');
             if (!visabled) tip.show();
             tip.set('content', '内容改变' + num++);
         }
     }
	 
     
	      	function add_archive(){
	    		CommonDialog("warehouse_choose",
	    	    		function(data) {
	    	    			var row=data;
	        				$('input[name="receivewarehouseName"]').val(row.warehousename);
	        				$('input[name="receivewarehouseAddress"]').val(row.address);
	        				$('input[name="receiverName"]').val(row.contactname);
	        				$('input[name="receiverPhone"]').val(row.contactphone);
	        				$('input[name="postcode"]').val(row.zipcode);
	        				$('input[name="receiverMail"]').val(row.contactemail);
	    	    		});
	    	    	}
	      	function add_product(){
	    		CommonDialog("warehouse_material",
	    		function(data) {
	    			var row=data;
    				$('.l-dialog-table input[name="materielCode"]').val(row.code);
    				$('.l-dialog-table input[name="model"]').val(row.model);
    				$('.l-dialog-table input[name="materielName"]').val(row.name);
    				$('.l-dialog-table input[name="unit"]').val(row.unit);
    				$('.l-dialog-table input[name="price"]').val(row.price);
	    		});
	    	}
	    	
	    	
	    		function delproduct(){
		 
			$("[name=check]:checkbox").each(function() {
				if (this.checked) {
					$(this).parent().parent().remove();
					
					
					var total=$('input[name="sumPrice"]').val();
					if(total!=""&&total!=null){
						total=total-$(this).closest("tr").find('input[name="sumPrice"]').val();
					}
					$('#sumPrice').val(total.toFixed(2));
					
					tax_rate();
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
				var item = $('<tr type="subdata"><td style="text-align: center"><input type="checkbox" name="check" value="'
						+ ids[i]
						+ '"/><input type="hidden"  name="materielId" value="'+ ids[i] + '"/></td><td  style="text-align: center"><input type="hidden" style="width: 70px;" name="materielCode"  value="'+ codes[i]+'"/>' + codes[i] + '</td> <td style="text-align: center" ><input type="hidden" style="width: 80px;"  name="model"  value="' + models[i] + '"/>' + models[i] + '</td> <td style="text-align: center" ><input type="hidden"  style="width: 80px;" name="materielName"  value="' + names[i] + '"/> ' + names[i] + ' </td><td  style="text-align: center"><input type="text" style="width: 80px;"  name="attributeInfo"   /> <td style="text-align: center" ><input type="hidden" style="width: 50px;" name="unit"  value="' + units[i] + '"/> ' + units[i]
						+ ' </td><td style="text-align: center" ><input type="text"   name="orderNum" style="width:40px;"    onblur="getSumPrice(this);"/> </td> <td style="text-align: center" ><input type="text" style="width: 50px;" name="price"  value='+prices[i]+'  onblur="getSumPrice(this);">   </td><td style="text-align: center" ><input type="text" style="width: 80px;" name="sumPrice" readonly value=""/> <td style="text-align: center"> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /> <input type="hidden"  name="ismat" value="0"/>  </td><td style="text-align: center"><a href="javascript:void(0)" onclick="getHistory(this)">查看</a></td></tr>');
				$("#purOrderDetail").append(item);
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
    		var item = $('<tr type="subdata"><td style="text-align: center"><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td style="text-align: center" ><input type="text" style="width: 70px;" name="materielCode"  value=""/></td> <td style="text-align: center" ><input type="text" style="width: 70px;"  name="model"  value=""/> </td> <td style="text-align: center" ><input type="text"  style="width: 70px;" name="materielName"  value=""/>  </td><td style="text-align: center" ><input type="text" style="width: 80px;"  name="attributeInfo"   /> <td style="text-align: center" ><input type="text" style="width: 40px;" name="unit"  value=""/>  </td><td style="text-align: center" ><input type="text"   name="orderNum" style="width:40px;"   onblur="getSumPrice(this);" /> </td> <td style="text-align: center" ><input type="text" style="width: 50px;" name="price"  value=""  onblur="getSumPrice(this);"/>  </td><td style="text-align: center" ><input type="text" readonly style="width: 80px;" name="sumPrice"  value=""/>  </td> <td style="text-align: center"> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /> <input type="hidden"  name="ismat" value="1"/> </td><td style="text-align: center"><a href="javascript:void(0)" onclick="getHistory(this)">查看</a></td></tr>');
				$("#purOrderDetail").append(item);
    	}
	 
	 
		//选择任务执行用户（可多选）
		function changeTaskUser(){
			//显示用户选择器
			UserDialog({
				isSingle:false,
				callback:function(userId,fullname){
					if(userId=='' || userId==null || userId==undefined){ 
						return;
					}else{
						$("#invitedUserIds").val(userId);
						$("#invitedUserNames").val(fullname);
					}
				}
			});
		}
	
	 function on_pay(obj){
		 obj.select();
	 }
	 function on_type(){
		var a = $("#payType").val();
		if(a==""||a==null){
			$("#payType").val("月结30天后付");
		}
	 }
	 function is_Bearer(){
		 var a = $("#freightBearer").val();
		 if(a == "" || a == null){
			 $("#freightBearer").val("卖方");
	}
	 }
	 //复制订单list
	 function copyList(){
		 window.open ('${ctx}/cloud/purchase/purOrder/copylist.ht?type=direct','采购记录','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	 }
			
			//复制订单
	      	function _callBackCopyList(id){
			 	//$("#purOrderDetail  tr:not(:nth-child(2))").empty()
				$("#purOrderDetail tr").eq(2).nextAll().remove();
			 	$.ajax({
			 		type : 'post',
					dataType : 'json',
					url : '${ctx}/cloud/purchase/purOrder/getCopyHistory.ht',
					data : {value : id},
					success : function(rows){
						//判断是否有值
						if(rows.length>0){
							for(var i=0;i<rows.length;i++){
								$("#sourceformType").val(rows[i].sourceformType);
								$("#sourceformId").val(rows[i].sourceformId);
								$("#sourceformCode").val(rows[i].sourceformCode);
								$("#advancePay").val(rows[i].advancePay);
								$("#advancepayNotes").val(rows[i].advancepayNotes);
								var	sumPrice=rows[i].sumPrice.toFixed(2);
								if(sumPrice==""||sumPrice==null){
									sumPrice=0;
								}
								$("#sumPrice").val(sumPrice);
								$("#freightBearer").val(rows[i].freightBearer);
								$("#currencyType").val(rows[i].currencyType);
								$("#en_id").val(rows[i].suppenterpId);
								$("#en_name").val(rows[i].suppenterpName);
								$("#suppenterpUserid").val(rows[i].suppenterpUserid);
								$("#suppenterpUsername").val(rows[i].suppenterpUsername);
								$("#receivewarehouseId").val(rows[i].receivewarehouseId);
								$("#receivewarehouseName").val(rows[i].receivewarehouseName);
								$("#receivewarehouseAddress").val(rows[i].receivewarehouseAddress);
								$("#receiverId").val(rows[i].receiverId);
								$("#receiverName").val(rows[i].receiverName);
								$("#receiverPhone").val(rows[i].receiverPhone);
								$("#receiverMail").val(rows[i].receiverMail);
								$("#postcode").val(rows[i].postcode);
								$("#payType").val(rows[i].payType);
								$("#billType").val(rows[i].billType);
								$("#taxRate").val(rows[i].taxRate);
								$("#invitedUserIds").val(rows[i].invitedUserIds);
								$("#invitedUserNames").val(rows[i].invitedUserNames);
							};
							
						}
					}
			 	});
	      		$.ajax({
			 		type : 'post',
					dataType : 'json',
					url : '${ctx}/cloud/purchase/purOrder/getMyHistory.ht',
					data : {value : id},
					success : function(rows){
						//判断是否有值
						if(rows.length>0){
							var total="";
							for(var i=0;i<rows.length;i++){
								var materielId=rows[i].materielId;
								var	materielCode=rows[i].materielCode;
					 			var	model=rows[i].model;
								var	materielName=rows[i].materielName;
								var	materielLev=rows[i].materielLev;
								var	attributeInfo=rows[i].attributeInfo;
								var	unit=rows[i].unit;
								var	orderNum=rows[i].orderNum;
								var	price=rows[i].price.toFixed(2);
								var	sumPrice=rows[i].sumPrice.toFixed(2);
								var	deliveryEnddate=rows[i].deliveryEnddate;
								if(sumPrice==""||sumPrice==null){
									sumPrice=0;
								}
								total=total*1+sumPrice*1;
    							var item = $('<tr type="subdata"><td style="text-align: center"><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td style="text-align: center" ><input type="text" style="width: 70px;" name="materielCode"  value=" '+materielCode+ ' "/></td> <td style="text-align: center" ><input type="text" style="width: 80px;"  name="model"  value=" '+model+' "/> </td> <td style="text-align: center" ><input type="text"  style="width: 70px;" name="materielName"  value=" '+materielName+' "/>  </td><td style="text-align: center" ><input type="text" style="width: 80px;"  name="attributeInfo"   value=" '+attributeInfo+' "/> <td style="text-align: center" ><input type="text" style="width: 40px;" name="unit"  value=" '+unit+' "/>  </td><td style="text-align: center" ><input type="text"   name="orderNum" style="width:40px;"  value=" '+orderNum+' "  onblur="getSumPrice(this);" /> </td> <td style="text-align: center" ><input type="text" style="width: 50px;" name="price"  value=" '+price+' "  onblur="getSumPrice(this);"/>  </td><td style="text-align: center" ><input type="text" readonly style="width: 80px;" name="sumPrice"  value=" '+sumPrice+' "/>  </td> <td style="text-align: center"> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /> <input type="hidden"  name="ismat" value="0"/> </td><td style="text-align: center"><a href="javascript:void(0)" onclick="getHistory(this)">查看</a></td></tr>');
								$("#purOrderDetail").append(item);
							};
							$('#sumPrice').val(total.toFixed(2));
						}
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
				<div class="group"><a class="link back" href="${ctx}/cloud/purchase/purOrder/list.ht?type=direct">返回</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link detail" onclick="copyList();" href="javascript:void(0)">复制订单</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="purOrderForm" method="post" action="${ctx}/cloud/purchase/purOrder/save2.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">采购订单编号: </th>
					<td><input type="text" readonly class="r" id="code" name="code" value="${purOrder.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" readonly class="r" id="operateDate" name="operateDate" value="<fmt:formatDate value='${purOrder.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" readonly class="r" id="operatorName" name="operatorName" value="${purOrder.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
				<!-- <th width="20%">来源单据ID: </th>
					<td><input type="text" readonly id="sourceformId" name="sourceformId" value="${purOrder.sourceformId}"  class="inputText r" validate="{required:false,maxlength:96}"  /></td> -->	
					<th width="20%">来源单据编号: </th>
					<td><input type="text" readonly id="sourceformCode" name="sourceformCode" value="${purOrder.sourceformCode}"  class="inputText r" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" readonly id="sourceformType" name="sourceformType" value="${purOrder.sourceformType}"  class="inputText r" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">采购企业名称: </th>
					<td><input type="text" readonly class="r" id="purenterpName" name="purenterpName" value="${purOrder.purenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">采购商联系人姓名: </th>
					<td><input type="text"  readonly class="r" id="purenterpUsername" name="purenterpUsername" value="${purOrder.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
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
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice"  readonly  value="${purOrder.sumPrice}"  class="inputText" validate="{required:false,number:true}"  /></td>
					
				<!--  	<td><input type="text" id="currencyType" name="currencyType" value="${purOrder.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>-->
				</tr>
				<tr>
					<th width="20%">支付方式: </th>
					<td>
						<%-- <ap:selectDB id="pay_Type" name="pay_Type"
											where="typeId=10000020070088" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${purOrder.payType}" onChange="order_pay();">
										</ap:selectDB> --%>
						<input type="text" id="payType" name="payType" value="月结30天后付" onblur="on_type();" onfocus="on_pay(this);" class="inputText" validate="{required:false}"  />			
					</td>
					<th width="20%">票据要求: </th>
					<td>
					<ap:selectDB id="billType" name="billType"
											where="typeId=10000020070112" optionValue="itemValue"
											optionText="itemName" table="SYS_DIC"
											selectedValue="${purOrder.billType}" >
										</ap:selectDB>
					</td>
				</tr>
				
				<tr>
					<th width="20%">预付款: </th>
					<td><input type="text" id="advancePay" name="advancePay" value="${purOrder.advancePay}"  class="inputText" validate="{required:false}"  /></td>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" onblur="is_Bearer();" onfocus="on_pay(this);" value="卖方"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				
				<tr style="display:none">
					<th width="20%">采购企业ID: </th>
					<td><input type="text" id="purenterpId" name="purenterpId" value="${purOrder.purenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">供应企业ID </th>
					<td><input type="text" id="en_id" name="suppenterpId" value="${purOrder.suppenterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				
				
			
				<tr>
					<th width="20%">供应企业名称: </th>
					<td><input size="35" type="text"  readonly="readonly" class="r" id="en_name" name="suppenterpName" value="${purOrder.suppenterpName}"  class="inputText" validate="{required:true,maxlength:96}"  readonly="readonly"/>
					<a href="javascript:void(0)" onclick="selSups()"  class="link detail">选择</a>
					</td>
					<th width="20%">供应商联系人姓名: </th>
					<td><input type="text" id="suppenterpUsername" name="suppenterpUsername" value="${purOrder.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			  <!-- <tr>
					<th width="20%">供应商联系人ID: </th>  --> 
					<input type="hidden" id="suppenterpUserid" name="suppenterpUserid" value="${purOrder.suppenterpUserid}"  class="inputText" validate="{required:false,maxlength:96}"  />
				
		<!-- 		<tr>
					<th width="20%">接受仓库ID: </th> -->
					<input type="hidden" id="receivewarehouseId" name="receivewarehouseId" value="${purOrder.receivewarehouseId}"  class="inputText" validate="{required:false,number:true }"  />
		
				
				<tr>
					<th width="20%">收货仓库名称: </th>
					<td><input type="text" id="receivewarehouseName" readonly="readonly" class="r" name="receivewarehouseName" value="${purOrder.receivewarehouseName}"    validate="{required:true}"  />
					<a href="javascript:void(0)" onclick="add_archive()"  class="link detail">选择</a>
					</td>
					<th width="20%">收货仓库详细地址: </th>
					<td><input type="text" id="receivewarehouseAddress"   name="receivewarehouseAddress" value="${purOrder.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
			
				
				
		    <!--<tr>
					<th width="20%">收货人ID: </th>  -->	
					<input type="hidden" id="receiverId" name="receiverId" value="${purOrder.receiverId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				<tr>
					<th width="20%">收货人姓名: </th>
					<td><input type="text" id="receiverName" name="receiverName"  value="${purOrder.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货人联系方式: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone" value="${purOrder.receiverPhone}"  class="inputText" /></td>
				</tr>
				<tr>
					<th width="20%">收货人邮箱: </th>
					<td><input type="text" id="receiverMail" name="receiverMail" value="${purOrder.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">收货地址邮编: </th>
					<td><input type="text" id="postcode" name="postcode" value="${purOrder.postcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				
				<input type="hidden" id="operatorId" name="operatorId" value="${purOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  />
		    	<tr style="display:none">
					<th width="20%">操作者ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${purOrder.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">审签领导: </th>
					<td><input type="text" readonly class="r" id="invitedUserNames" name="invitedUserNames"  readonly="readonly" value="${purOrder.invitedUserNames}"  class="inputText" validate="{required:true}"  />
					   <a href="javascript:void(0)" onclick="changeTaskUser()"  class="link detail">选择</a>
					    <input type="hidden" id="invitedUserIds" name="invitedUserIds"    value="${purOrder.invitedUserIds}"    />
					</td>
			 
					<th width="20%">税率(%): </th>
					<td><input type="text" id="taxRate" name="taxRate"  onblur="tax_rate();" value="${purOrder.taxRate}"  class="inputText" validate="{required:false,number:true}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">订单备注: </th>
					<td colspan="3"><textarea cols="95" rows="3" id="advancepayNotes" name="advancepayNotes" class="inputText" validate="{required:false,maxlength:768}"  >${purOrder.advancepayNotes}</textarea></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOrderDetail" formType="window" type="sub">
				<tr>
					<td colspan="11">
						<div class="group" align="left">
				   			 <a href="javascript:void(0)" onclick="add_supps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd2.jpg'"
												onmouseout="src='${ctx}/images/iconadd.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
								  
								  <a href="javascript:void(0)"  onclick="delproduct();"   style="text-decoration: none;">
											<img src="${ctx}/images/icon_del2.jpg" height="18" width="52"
												onmouseover="src='${ctx}/images/icon_del.jpg'"
												onmouseout="src='${ctx}/images/icon_del2.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
												
								<a href="javascript:void(0)" onclick="add_onesupps();"   style="text-decoration: none;">
											<img src="${ctx}/images/iconadd3.jpg" width="52" height="18"
												onmouseover="src='${ctx}/images/iconadd4.jpg'"
												onmouseout="src='${ctx}/images/iconadd3.jpg'"
												style="border: 0;"> </a>&nbsp;&nbsp;
			    		</div>
			    		<div align="center">
						采购订单 ：采购订单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th style="display:none">物品ID</th>
					<th>物品编码</th>
					<th>物品规格</th>
					<th>物品名称</th>
					<th style="display:none">物品分类</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
				 
			    	<th>发货截至时间</th>
				 
			    	<th>采购历史</th>
				</tr>
				<c:forEach items="${purOrderDetailList}" var="purOrderDetailItem" varStatus="status">
				    <tr type="subdata" >
				    	<td style="text-align: center"><input type="checkbox" style="" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td>
					    <td style="display:none" name="materielId">${purOrderDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${purOrderDetailItem.materielCode}</td>
					    <td style="text-align: center" name="model">${purOrderDetailItem.model}</td>
					    <td style="text-align: center" name="materielName">${purOrderDetailItem.materielName}</td>
					    <td style="display:none" name="materielLev">${purOrderDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${purOrderDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${purOrderDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${purOrderDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purOrderDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${purOrderDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" onclick="getHistory(this)">查看</a>
					    </td>							
						<input type="hidden" name="materielId" value="${purOrderDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${purOrderDetailItem.materielCode}"/>
						<input type="hidden" name="model" value="${purOrderDetailItem.model}"/>
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
				    <td></td>
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="model"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" onclick="getHistory(this)">查看</a>
			    	</td>							
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="model" value=""/>
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
		    <!-- 
		     <tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		     --> 
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" readonly="readonly" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/> <a href="javascript:void(0)" onclick="add_product();">选择</a></td>
			</tr>
			<tr>
				<th width="20%">物品规格: </th>
				<td><input type="text" readonly="readonly" name="model" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" readonly="readonly" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<!-- 
			<tr>
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			 -->
			<tr>
				<th width="20%">物品属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unit" readonly="readonly" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price"  value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">订单数量: </th>
				<td><input type="text" name="orderNum" id="orderNum1" value=""  class="inputText" validate="{required:false,number:true }"  onblur="getSumPrice(this);" /></td>
			</tr>
			 
			<tr>
				<th width="20%">总金额: </th>
				<td><input readonly="readonly" type="text" name="sumPrice" id="sumPrice1" value=""  class="inputText" validate="{required:false,number:true}"/></td>
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
