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
	var dd;
	var type;
	function add_supps(t){
		type =t;
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
		if(type=="selectEnterp"){
			$('input[name="logisticsEnterpId"]').val(id);
			$('input[name="logisticsEnterpName"]').val(name);	
		}else if(type=="selectendReceive"){
			$('input[name="endReceiveEnterpId"]').val(id);
			$('input[name="endReceiveEnterpName"]').val(name);
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

					}else{
						window.location.href = "${ctx}/cloud/logistics/logisticPlan/list1.ht";
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
		
		$(function(){
			var model;
			$("#logisticsModel").val($("#selectLogisticsModel").val());
			$("#selectLogisticsModel").change(function(){
				model = $("#selectLogisticsModel").val();
				$("#logisticsModel").val(model);
				if(model=='0'){
					$("#selectEnterp").show();
				}else if(model=='1'){
					$("#selectEnterp").hide();
					$("#logisticsEnterpId").val($("#startPostEnterpId").val());
					$("#logisticsEnterpName").val($("#startPostEnterpName").val());
				}else if(model=='2'){
					$("#selectEnterp").hide();
					$("#logisticsEnterpId").val($("#startPostEnterpId").val());
					$("#logisticsEnterpName").val($("#startPostEnterpName").val());
				}
			});
			
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
					<div class="group"><a class="link back" href="${ctx}/cloud/sale/delivernote/list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="logisticPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">单证号: </th>
					<td colspan="3"><input type="text" id="code" readonly="readonly" name="code" value="${logisticPlan.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">制单人姓名: </th>
					<td><input type="text" id="operaterName" readonly="readonly" name="operaterName" value="${logisticPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" readonly="readonly" value="<fmt:formatDate value='${logisticPlan.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr style="display:none;">
					<th style="width:120px">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${logisticPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none">
					<th style="width:120px">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${logisticPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${logisticPlan.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">来源单据号: </th>
					<td><input type="text" id="sourceformCode" readonly="readonly" name="sourceformCode" value="${logisticPlan.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th style="width:120px">预计发货日期: </th>
					<td><input type="text" id="forecastPostDate" name="forecastPostDate" readonly="readonly" value="<fmt:formatDate value='${logisticPlan.forecastPostDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">预计收货日期: </th>
					<td><input type="text" id="forecastReceiveDate" name="forecastReceiveDate" readonly="readonly" value="<fmt:formatDate value='${logisticPlan.forecastReceiveDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">发货模式: </th>
					<td colspan="3">
						<select id="selectLogisticsModel" name="selectLogisticsModel">
							<option value="0">协同物流</option>
							<option value="1">第一方物流</option>
							<option value="2">第三方物流</option>
						</select>
					</td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">物流公司ID : </th>
					<td><input type="text" id="logisticsEnterpId" name="logisticsEnterpId" value="${logisticPlan.logisticsEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th style="width:120px">制定物流计划公司: </th>
					<td style="width:350px">
						<input type="text" id="logisticsEnterpName" name="logisticsEnterpName" readonly="readonly" value="${logisticPlan.logisticsEnterpName}"  class="inputText" validate="{required:true,maxlength:96}"  />
						<span id="selectEnterp"><a href="javascript:void(0)" onclick="add_supps('selectEnterp')"  class="link detail">选择</a></span>
					</td>
					<th style="width:120px">重量: </th>
					<td><input type="text" id="weight" name="weight" value="${logisticPlan.weight}"  class="inputText" validate="{required:false}"  />kg</td>
				</tr>
				
				<tr>
					<th style="width:120px">运费: </th>
					<td><input type="text" id="shipPrise" name="shipPrise" value="${logisticPlan.shipPrise}"  class="inputText" validate="{required:false}"  />元</td>
					<th style="width:120px">运费承担方: </th>
					<td>
					<select id="freightBearer" name="freightBearer">
						<option value="发货方">发货方</option>
						<option value="收货方">收货方</option>
						<option value="双方协商">双方协商</option>
					</select>
				</tr>
				
				<tr style="display:none;">
					<th style="width:120px">起始发货人ID: </th>
					<td><input type="text" id="startPosterId" name="startPosterId" value="${logisticPlan.startPosterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">起始发货人名称: </th>
					<td><input type="text" id="startPosterName" name="startPosterName" value="${logisticPlan.startPosterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">起始发货人联系方式: </th>
					<td><input type="text" id="startPosterPhone" name="startPosterPhone" value="${logisticPlan.startPosterPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">起始发货地址: </th>
					<td><input type="text" id="startPostAddress" name="startPostAddress" value="${logisticPlan.startPostAddress}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">起始发货仓库ID: </th>
					<td><input type="text" id="startPostWarehouseId" name="startPostWarehouseId" value="${logisticPlan.startPostWarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">起始发货仓库名称: </th>
					<td><input type="text" id="startPostWarehouseName" name="startPostWarehouseName" value="${logisticPlan.startPostWarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none">
					<th style="width:120px">最终收货企业ID: </th>
					<td><input type="text" id="endReceiveEnterpId" name="endReceiveEnterpId" value="${logisticPlan.endReceiveEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">起始发货企业ID: </th>
					<td><input type="text" id="startPostEnterpId" name="startPostEnterpId" value="${logisticPlan.startPostEnterpId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
				 	<th style="width:120px">起始发货企业名称: </th>
					<td><input type="text" id="startPostEnterpName" readonly="readonly" name="startPostEnterpName" value="${logisticPlan.startPostEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">最终收货企业名称 : </th>
					<td>
						<input type="text" id="endReceiveEnterpName" readonly="readonly" name="endReceiveEnterpName" value="${logisticPlan.endReceiveEnterpName}"  class="inputText" validate="{required:false,maxlength:96}"  />
						<c:if test="${addFlag==1}">
							<span id="selectendReceive"><a href="javascript:void(0)" onclick="add_supps('selectendReceive')"  class="link detail">选择</a></span>
						</c:if>
					</td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货人ID: </th>
					<td><input type="text" id="endReceiverId" name="endReceiverId" value="${logisticPlan.endReceiverId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货人名称: </th>
					<td><input type="text" id="endReceiverName" name="endReceiverName" value="${logisticPlan.endReceiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货人联系方式: </th>
					<td><input type="text" id="endReceiverPhone" name="endReceiverPhone" value="${logisticPlan.endReceiverPhone}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货地址: </th>
					<td><input type="text" id="endReceiveAddress" name="endReceiveAddress" value="${logisticPlan.endReceiveAddress}"  class="inputText" validate="{required:false,maxlength:384}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货仓库ID: </th>
					<td><input type="text" id="endReceiveWarehouseId" name="endReceiveWarehouseId" value="${logisticPlan.endReceiveWarehouseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">最终收货仓库名称: </th>
					<td><input type="text" id="endReceiveWarehouseName" name="endReceiveWarehouseName" value="${logisticPlan.endReceiveWarehouseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th style="width:120px">runId: </th>
					<td><input type="text" id="runId" name="runId" value="${logisticPlan.runId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display:none;">
					<th width="20%">物流模式: </th>
					<td><input type="text" id="logisticsModel" name="logisticsModel"  class="inputText" value="${logisticPlan.logisticsModel}" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="logisticMaterial" formType="window" type="sub">
				<tr>
					<td colspan="8">
						<c:if test="${addFlag==1}">
							<div class="group" align="left">
				   				<a id="btnAdd" class="link add">添加</a>
			    			</div>
						</c:if>	
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
					    <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td> -->
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
</div>
</body>
</html>
