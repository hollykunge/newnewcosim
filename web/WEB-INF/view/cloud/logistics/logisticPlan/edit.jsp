<%--
	time:2013-05-16 14:48:10
	desc:edit the 物流计划单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 物流计划单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>

	<script type="text/javascript">
	var type;
	function select(){		
		CommonDialog("busiareaList2",
			function(data) {
				var row=data;		
				if(type=='ship'){
					$('.l-dialog-table input[name="shipEnterpName"]').val(data.name);
					$('.l-dialog-table input[name="shipEnterpId"]').val(data.enterpid);
					$('.l-dialog-table input[name="shiperPhone"]').val(data.tel);
					$('.l-dialog-table input[name="shiperName"]').val(data.connecter);	
				}else if(type=='post'){
					$('.l-dialog-table input[name="postEnterpName"]').val(data.name);
					$('.l-dialog-table input[name="postEnterpId"]').val(data.enterpid);
					$('.l-dialog-table input[name="posterPhone"]').val(data.tel);
					$('.l-dialog-table input[name="posterName"]').val(data.connecter);
					$('.l-dialog-table input[name="postAddress"]').val(data.address);	
				}else if(type=='receive'){
					$('.l-dialog-table input[name="receiveEnterpName"]').val(data.names);
					$('.l-dialog-table input[name="receiveEnterpId"]').val(data.ids);
					$('.l-dialog-table input[name="receiverPhone"]').val(data.phone);
					$('.l-dialog-table input[name="receiverName"]').val(data.connecter);
					$('.l-dialog-table input[name="receiverName"]').val(data.address);
				}
			}	
		);
	}
	function add_supps(t){
		type=t;
		//弹出供应商物品选择框
		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriendsRadio.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
	} 
	
	//商圈列表回调函数
	function _callBackMyFriends(friends){
		var name='',id='',phone='',address='',connecter='';
		var friend=friends.toString().split(',');
		id=friend[0];
		name=friend[1];
		phone=friend[2];
		address=friend[3];
		connecter=friend[4];
		if(type=='ship'){
			$('.l-dialog-table input[name="shipEnterpName"]').val(name);
			$('.l-dialog-table input[name="shipEnterpId"]').val(id);
			$('.l-dialog-table input[name="shiperPhone"]').val(phone);
			$('.l-dialog-table input[name="shiperName"]').val(connecter);	
		}else if(type=='post'){
			$('.l-dialog-table input[name="postEnterpName"]').val(name);
			$('.l-dialog-table input[name="postEnterpId"]').val(id);
			$('.l-dialog-table input[name="posterPhone"]').val(phone);
			$('.l-dialog-table input[name="posterName"]').val(connecter);
			$('.l-dialog-table input[name="postAddress"]').val(address);	
		}else if(type=='receive'){
			$('.l-dialog-table input[name="receiveEnterpName"]').val(name);
			$('.l-dialog-table input[name="receiveEnterpId"]').val(id);
			$('.l-dialog-table input[name="receiverPhone"]').val(phone);
			$('.l-dialog-table input[name="receiverName"]').val(connecter);
			$('.l-dialog-table input[name="receiverName"]').val(address);
		}else
		{
			$(":input[name='logisticsEnterpName']").val(name);
			$(":input[name='logisticsEnterpId']").val(id);
		}
		dd.close();
	}
	
	
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
		
		function showResponse(responseText) {
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
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#logisticPlanForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.agree").click(function() {
			operatorType=1;
			var tmp = '1';
			/*if(isDirectComlete){//直接一票通过
				tmp = '5';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");

			$('#logisticPlanForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.notAgree").click(function() {
			
			operatorType=2;
			var tmp = '2';
			/*if(isDirectComlete){//直接一票通过
				tmp = '6';
			}*/
			$('#voteAgree').val(tmp);
			$('#back').val("1");
			
			$('#logisticPlanForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${logisticPlan.id !=null}">
			        <span class="tbar-label">编辑物流计划单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加物流计划单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${applyFlag==0}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
				<c:if test="${applyFlag==1}">
					<div class="group"><a id="btnAgree" class="link agree">同意</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div>
				</c:if>
				<c:if test="${applyFlag==2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="logisticPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr style="display:none;" >
					<th style="width:120px">ID: </th>
					<td colspan="3"><input type="text" id="id" name="id" value="${logisticPlan.id}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">单证号: </th>
					<td colspan="3"><input type="text" id="code" name="code" value="${logisticPlan.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr style="display:none;">
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${logisticPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${logisticPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${logisticPlan.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr style="display:none">
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${logisticPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${logisticPlan.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${logisticPlan.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">预计发货日期: </th>
					<td><input type="text" id="forecastPostDate" name="forecastPostDate" value="<fmt:formatDate value='${logisticPlan.forecastPostDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">预计收货日期: </th>
					<td><input type="text" id="forecastReceiveDate" name="forecastReceiveDate" value="<fmt:formatDate value='${logisticPlan.forecastReceiveDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">物流公司ID : </th>
					<td><input type="text" id="logisticsEnterpId" name="logisticsEnterpId" value="${logisticPlan.logisticsEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">物流公司名称: </th>
					<td>
						<input type="text" id="logisticsEnterpName" readonly="readonly" name="logisticsEnterpName" value="${logisticPlan.logisticsEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<a href="javascript:void(0)" onclick="add_supps()"  class="link detail">选择</a>
					</td>
					<th width="20%">重量: </th>
					<td><input type="text" id="weight" name="weight" value="${logisticPlan.weight}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">运费承担方: </th>
					<td>
					<select id="freightBearer" name="freightBearer">
						<option value='发货方'>发货方</option>
						<option value='收货方'>收货方</option>
					</select>
					<th width="20%">运费: </th>
					<td><input type="text" id="shipPrise" name="shipPrise" value="${logisticPlan.shipPrise}"  class="inputText" validate="{required:false}"  /></td>
				</tr>
				<tr style="display:none">
					<th width="20%">起始发货企业ID: </th>
					<td><input type="text" id="startPostEnterpId" name="startPostEnterpId" value="${logisticPlan.startPostEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货人ID: </th>
					<td><input type="text" id="startPosterId" name="startPosterId" value="${logisticPlan.startPosterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货人名称: </th>
					<td><input type="text" id="startPosterName" name="startPosterName" value="${logisticPlan.startPosterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货人联系方式: </th>
					<td><input type="text" id="startPosterPhone" name="startPosterPhone" value="${logisticPlan.startPosterPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货地址: </th>
					<td><input type="text" id="startPostAddress" name="startPostAddress" value="${logisticPlan.startPostAddress}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货仓库ID: </th>
					<td><input type="text" id="startPostWarehouseId" name="startPostWarehouseId" value="${logisticPlan.startPostWarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">起始发货仓库名称: </th>
					<td><input type="text" id="startPostWarehouseName" name="startPostWarehouseName" value="${logisticPlan.startPostWarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none">
					<th width="20%">最终收货企业ID: </th>
					<td><input type="text" id="endReceiveEnterpId" name="endReceiveEnterpId" value="${logisticPlan.endReceiveEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">起始发货企业名称: </th>
					<td><input type="text" id="startPostEnterpName" name="startPostEnterpName" value="${logisticPlan.startPostEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">最终收货企业名称 : </th>
					<td><input type="text" id="endReceiveEnterpName" name="endReceiveEnterpName" value="${logisticPlan.endReceiveEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货人ID: </th>
					<td><input type="text" id="endReceiverId" name="endReceiverId" value="${logisticPlan.endReceiverId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货人名称: </th>
					<td><input type="text" id="endReceiverName" name="endReceiverName" value="${logisticPlan.endReceiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货人联系方式: </th>
					<td><input type="text" id="endReceiverPhone" name="endReceiverPhone" value="${logisticPlan.endReceiverPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货地址: </th>
					<td><input type="text" id="endReceiveAddress" name="endReceiveAddress" value="${logisticPlan.endReceiveAddress}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货仓库ID: </th>
					<td><input type="text" id="endReceiveWarehouseId" name="endReceiveWarehouseId" value="${logisticPlan.endReceiveWarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货仓库名称: </th>
					<td><input type="text" id="endReceiveWarehouseName" name="endReceiveWarehouseName" value="${logisticPlan.endReceiveWarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">最终收货仓库名称: </th>
					<td><input type="text" id="endReceiveWarehouseName" name="endReceiveWarehouseName" value="${logisticPlan.endReceiveWarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">runId: </th>
					<td><input type="text" id="runId" name="runId" value="${logisticPlan.runId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="logisticMaterial" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						发货通知详单
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th style="display:none;">物品ID</th>
					<th>物品编号</th>
					<th>物品名</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>数量</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${logisticMaterialList}" var="logisticMaterialItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center;display:none;" name="materielId">${logisticMaterialItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${logisticMaterialItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${logisticMaterialItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${logisticMaterialItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${logisticMaterialItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${logisticMaterialItem.unit}</td>
					    <td style="text-align: center" name="num">${logisticMaterialItem.num}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<%-- <input type="hidden" name="materielId" value="${logisticMaterialItem.materielId}"/> --%>
						<input type="hidden" name="materielCode" value="${logisticMaterialItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${logisticMaterialItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${logisticMaterialItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${logisticMaterialItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${logisticMaterialItem.unit}"/>
						<input type="hidden" name="num" value="${logisticMaterialItem.num}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center;display:none;" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="num"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<!-- <input type="hidden" name="materielId" value=""/> -->
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="num" value=""/>
		 		</tr>
		    </table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="logisticsPlanDtl" formType="window" type="sub">
				<tr>
					<td colspan="27">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						物流计划详单
			    		</div>
		    		</td>
				</tr>
				<tr>
					<!-- <th>发货单ID</th> -->
					<th>物流环节类型</th>
					<th>物流环节步骤</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<!-- <th>运输企业ID</th> -->
					<th>运输企业名称</th>
					<!-- <th>运输企业联系人ID</th>
					<th>运输企业联系人姓名</th>
					<th>运输企业联系方式</th>
					 -->
					<!-- <th>取货企业ID</th>
					<th>取货企业名称</th>
					<th>取货企业联系人ID</th>
					<th>取货企业联系人名称</th>
					<th>取货企业联系方式</th>
					<th>取货仓库ID</th>
					<th>取货仓库名称</th>
					<th>取货地址</th> 
					<th>收货企业ID</th>-->
					<th>收货企业名称</th>
					<!-- <th>收货企业联系人ID</th> 
					<th>收货企业联系人姓名</th>
					<th>收货企业联系方式</th>
					<th>收货仓库ID</th> -->
					<th>收货仓库名称</th>
					<th>收货地址</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${logisticsPlanDtlList}" var="logisticsPlanDtlItem" varStatus="status">
				    <tr type="subdata">
					  <td style="text-align: center;display:none;" name="shipOrderId">${logisticsPlanDtlItem.shipOrderId}</td>
					    <td style="text-align: center" name="shipStepType">${logisticsPlanDtlItem.shipStepType}</td>
					    <td style="text-align: center" name="shipStepLev">${logisticsPlanDtlItem.shipStepLev}</td>
						<td style="text-align: center" name="startTime"><fmt:formatDate value='${logisticsPlanDtlItem.startTime}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="endTime"><fmt:formatDate value='${logisticsPlanDtlItem.endTime}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center;display:none;" name="shipEnterpId">${logisticsPlanDtlItem.shipEnterpId}</td>
					    <td style="text-align: center" name="shipEnterpName">${logisticsPlanDtlItem.shipEnterpName}</td>
					    <td style="text-align: center;display:none;" name="shiperId">${logisticsPlanDtlItem.shiperId}</td>
					    <td style="text-align: center;display:none;" name="shiperName">${logisticsPlanDtlItem.shiperName}</td>
					    <td style="text-align: center;display:none;" name="shiperPhone">${logisticsPlanDtlItem.shiperPhone}</td>
					   <td style="text-align: center;display:none;" name="postEnterpId">${logisticsPlanDtlItem.postEnterpId}</td>
					    <td style="text-align: center;display:none;" name="postEnterpName">${logisticsPlanDtlItem.postEnterpName}</td>
					    <td style="text-align: center;display:none;" name="posterId">${logisticsPlanDtlItem.posterId}</td>
					    <td style="text-align: center;display:none;" name="posterName">${logisticsPlanDtlItem.posterName}</td>
					    <td style="text-align: center;display:none;" name="posterPhone">${logisticsPlanDtlItem.posterPhone}</td>
					    <td style="text-align: center;display:none;" name="postWarehouseId">${logisticsPlanDtlItem.postWarehouseId}</td>
					    <td style="text-align: center;display:none;" name="postWarehouseName">${logisticsPlanDtlItem.postWarehouseName}</td>
					    <td style="text-align: center;display:none;" name="postAddress">${logisticsPlanDtlItem.postAddress}</td>
					    <td style="text-align: center;display:none;" name="receiveEnterpId">${logisticsPlanDtlItem.receiveEnterpId}</td>
					    <td style="text-align: center" name="receiveEnterpName">${logisticsPlanDtlItem.receiveEnterpName}</td>
					    <td style="text-align: center;display:none;" name="receiverId">${logisticsPlanDtlItem.receiverId}</td>
					    <td style="text-align: center;display:none;" name="receiverName">${logisticsPlanDtlItem.receiverName}</td>
					    <td style="text-align: center;display:none;" name="receiverPhone">${logisticsPlanDtlItem.receiverPhone}</td>
					     <td style="text-align: center;display:none;" name="receiveWarehouseId">${logisticsPlanDtlItem.receiveWarehouseId}</td>
					    <td style="text-align: center" name="receiveWarehouseName">${logisticsPlanDtlItem.receiveWarehouseName}</td>
					    <td style="text-align: center" name="receiveAddress">${logisticsPlanDtlItem.receiveAddress}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<%-- <input type="hidden" name="shipOrderId" value="${logisticsPlanDtlItem.shipOrderId}"/> --%>
						<input type="hidden" name="shipStepType" value="${logisticsPlanDtlItem.shipStepType}"/>
						<input type="hidden" name="shipStepLev" value="${logisticsPlanDtlItem.shipStepLev}"/>
					    <input type="hidden" name="startTime" value="<fmt:formatDate value='${logisticsPlanDtlItem.startTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="endTime" value="<fmt:formatDate value='${logisticsPlanDtlItem.endTime}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<%-- <input type="hidden" name="shipEnterpId" value="${logisticsPlanDtlItem.shipEnterpId}"/> --%>
						<input type="hidden" name="shipEnterpName" value="${logisticsPlanDtlItem.shipEnterpName}"/>
						<%-- <input type="hidden" name="shiperId" value="${logisticsPlanDtlItem.shiperId}"/> --%>
						<%-- <input type="hidden" name="shiperName" value="${logisticsPlanDtlItem.shiperName}"/>
						<input type="hidden" name="shiperPhone" value="${logisticsPlanDtlItem.shiperPhone}"/> --%>
						<%-- <input type="hidden" name="postEnterpId" value="${logisticsPlanDtlItem.postEnterpId}"/>
						<input type="hidden" name="postEnterpName" value="${logisticsPlanDtlItem.postEnterpName}"/>
						<input type="hidden" name="posterId" value="${logisticsPlanDtlItem.posterId}"/>
						<input type="hidden" name="posterName" value="${logisticsPlanDtlItem.posterName}"/>
						<input type="hidden" name="posterPhone" value="${logisticsPlanDtlItem.posterPhone}"/>
						<input type="hidden" name="postWarehouseId" value="${logisticsPlanDtlItem.postWarehouseId}"/>
						<input type="hidden" name="postWarehouseName" value="${logisticsPlanDtlItem.postWarehouseName}"/>
						<input type="hidden" name="postAddress" value="${logisticsPlanDtlItem.postAddress}"/> 
						<input type="hidden" name="receiveEnterpId" value="${logisticsPlanDtlItem.receiveEnterpId}"/>--%>
						<input type="hidden" name="receiveEnterpName" value="${logisticsPlanDtlItem.receiveEnterpName}"/>
						<%-- <input type="hidden" name="receiverId" value="${logisticsPlanDtlItem.receiverId}"/> --%>
						<%-- <input type="hidden" name="receiverName" value="${logisticsPlanDtlItem.receiverName}"/>
						<input type="hidden" name="receiverPhone" value="${logisticsPlanDtlItem.receiverPhone}"/> --%>
						<%-- <input type="hidden" name="receiveWarehouseId" value="${logisticsPlanDtlItem.receiveWarehouseId}"/> --%>
						<input type="hidden" name="receiveWarehouseName" value="${logisticsPlanDtlItem.receiveWarehouseName}"/>
						<input type="hidden" name="receiveAddress" value="${logisticsPlanDtlItem.receiveAddress}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center;display:none;" name="shipOrderId"></td>
			    	<td style="text-align: center" name="shipStepType"></td>
			    	<td style="text-align: center" name="shipStepLev"></td>
					<td style="text-align: center" name="startTime"></td>								
					<td style="text-align: center" name="endTime"></td>								
			    	<td style="text-align: center;display:none;" name="shipEnterpId"></td>
			    	<td style="text-align: center" name="shipEnterpName" ></td>
			    	<td style="text-align: center;display:none;" name="shiperId"></td>
			    	<td style="text-align: center;display:none;" name="shiperName"></td>
			    	<td style="text-align: center;display:none;" name="shiperPhone"></td>
			    	<td style="text-align: center;display:none;" name="postEnterpId"></td>
			    	<td style="text-align: center;display:none;" name="postEnterpName"></td>
			    	<td style="text-align: center;display:none;" name="posterId"></td>
			    	<td style="text-align: center;display:none;" name="posterName"></td>
			    	<td style="text-align: center;display:none;" name="posterPhone"></td>
			    	<td style="text-align: center;display:none;" name="postWarehouseId"></td>
			    	<td style="text-align: center;display:none;" name="postWarehouseName"></td>
			    	<td style="text-align: center;display:none;" name="postAddress"></td>
			    	<td style="text-align: center;display:none;" name="receiveEnterpId"></td>
			    	<td style="text-align: center" name="receiveEnterpName"></td>
			    	<td style="text-align: center;display:none;" name="receiverId"></td>
			    	<td style="text-align: center;display:none;" name="receiverName"></td>
			    	<td style="text-align: center;display:none;" name="receiverPhone"></td>
			    	<td style="text-align: center;display:none;" name="receiveWarehouseId"></td>
			    	<td style="text-align: center" name="receiveWarehouseName"></td>
			    	<td style="text-align: center" name="receiveAddress"></td>
			    	<td style="text-align: center;white-space:nowrap;">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<!-- <input type="hidden" name="shipOrderId" value=""/> -->
			    	<input type="hidden" name="shipStepType" value=""/>
			    	<input type="hidden" name="shipStepLev" value=""/>
			    	<input type="hidden" name="startTime" value="" class="inputText date"/>
			    	<input type="hidden" name="endTime" value="" class="inputText date"/>
			    	<!-- <input type="hidden" name="shipEnterpId" value=""/> -->
			    	<input type="hidden" name="shipEnterpName" value=""/>
			    	<!-- <input type="hidden" name="shiperId" value=""/> 
			    	<input type="hidden" name="shiperName" value=""/>
			    	<input type="hidden" name="shiperPhone" value=""/>-->
			    	<!-- <input type="hidden" name="postEnterpId" value=""/>
			    	<input type="hidden" name="postEnterpName" value=""/>
			    	<input type="hidden" name="posterId" value=""/>
			    	<input type="hidden" name="posterName" value=""/>
			    	<input type="hidden" name="posterPhone" value=""/>
			    	<input type="hidden" name="postWarehouseId" value=""/>
			    	<input type="hidden" name="postWarehouseName" value=""/>
			    	<input type="hidden" name="postAddress" value=""/> -->
			    	<!-- <input type="hidden" name="receiveEnterpId" value=""/> -->
			    	<input type="hidden" name="receiveEnterpName" value=""/>
			    	<!-- <input type="hidden" name="receiverId" value=""/>
			    	<input type="hidden" name="receiverName" value=""/>
			    	<input type="hidden" name="receiverPhone" value=""/> -->
			    	<!-- <input type="hidden" name="receiveWarehouseId" value=""/> -->
			    	<input type="hidden" name="receiveWarehouseName" value=""/>
			    	<input type="hidden" name="receiveAddress" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${logisticPlan.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="logisticMaterialForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr style="display:none">
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品编号: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:true,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">属性说明: </th>
				<td><input type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">单位: </th>
				<td><input type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">数量: </th>
				<td><input type="text" name="num" value=""  class="inputText" validate="{required:true,number:true }"/></td>
			</tr>
		</table>
	</form>
	<form id="logisticsPlanDtlForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr style="display:none;">
				<th width="20%">发货单ID: </th>
				<td><input type="text" name="shipOrderId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">物流环节类型: </th>
				<td><input type="text" name="shipStepType" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">物流环节步骤: </th>
				<td><input type="text" name="shipStepLev" value=""  class="inputText" validate="{required:true,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">开始时间: </th>
				<td><input type="text" name="startTime" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">结束时间: </th>
				<td><input type="text" name="endTime" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr style="display:none;">
				<th width="20%">运输企业ID: </th>
				<td><input type="text" name="shipEnterpId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">运输企业名称: </th>
				<td>
					<input type="text" name="shipEnterpName" value=""  class="inputText" validate="{required:false,maxlength:384}"/>
					<a href="javascript:void(0)" onclick="add_supps('ship')"  class="link detail">选择</a>
				</td>
			</tr>
			<tr style="display:none;">
				<th width="20%">运输企业联系人ID: </th>
				<td><input type="text" name="shiperId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">运输企业联系人姓名: </th>
				<td><input type="text" name="shiperName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr>
				<th width="20%">运输企业联系方式: </th>
				<td><input type="text" name="shiperPhone" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货企业ID: </th>
				<td><input type="text" name="postEnterpId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货企业名称: </th>
				<td><input type="text" name="postEnterpName" value=""  class="inputText" validate="{required:false,maxlength:384}"/>
				<a href="javascript:void(0)" onclick="add_supps('post')"  class="link detail">选择</a></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货企业联系人ID: </th>
				<td><input type="text" name="posterId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货企业联系人名称: </th>
				<td><input type="text" name="posterName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货企业联系方式: </th>
				<td><input type="text" name="posterPhone" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货仓库ID: </th>
				<td><input type="text" name="postWarehouseId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货仓库名称: </th>
				<td><input type="text" name="postWarehouseName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">取货地址: </th>
				<td><input type="text" name="postAddress" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">收货企业ID: </th>
				<td><input type="text" name="receiveEnterpId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">收货企业名称: </th>
				<td><input type="text" name="receiveEnterpName" value=""  class="inputText" validate="{required:false,maxlength:384}"/>
				<a href="javascript:void(0)" onclick="add_supps('receive')"  class="link detail">选择</a>
				</td>
			</tr>
			 <tr style="display:none;">
				<th width="20%">收货企业联系人ID: </th>
				<td><input type="text" name="receiverId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">收货企业联系人姓名: </th>
				<td><input type="text" name="receiverName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr>
				<th width="20%">收货企业联系方式: </th>
				<td><input type="text" name="receiverPhone" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">收货仓库ID: </th>
				<td><input type="text" name="receiveWarehouseId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">收货仓库名称: </th>
				<td><input type="text" name="receiveWarehouseName" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
			<tr style="display:none;">
				<th width="20%">收货地址: </th>
				<td><input type="text" name="receiveAddress" value=""  class="inputText" validate="{required:false,maxlength:384}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
