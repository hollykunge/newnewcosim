<%--
	time:2013-04-26 14:32:24
	desc:edit the 营销合作计划单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 营销合作计划单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<style type="text/css">
			
			.link-remove {
				background: url(${ctx}/styles/default/images/button/remove.png) 0px 3px no-repeat !important;
				text-decoration: none;
			}
		</style>
	
	<script type="text/javascript">
	
	//企业选择器
	var dd;
	function add_supp(){
		//弹出供应商物品选择框
		var urlShow = '${ctx}/cloud/console/busiarea/listMyFriends.ht';
		dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'企业选择器', name:"frameDialog_"});
	}	
	
	//商圈列表回调函数
	function _callBackMyFriends(friends){
		var names='',ids='';
		var i=0;
		if(friends[0]==""){
			i=1;
		}else{
			i=0;
		}
		for(i; i<friends.length; i++){
			var friend = friends[i];
			ids += ',' + friend[0];
			names += ',' + friend[1];
		}
		ids = ids!=''?ids.substring(1):"";
		names = names!=''?names.substring(1):"";
		$('#e_name').val(names);
		$('#suppEnterpriseIds').val(ids);
		
		dd.close();
	}
	
	
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#saleCopPlanForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
			
		
			
		});
		function userview(){
  			CommonDialog("org_info_list",
  			function(data) {
   				/* //多个厂商
  			    $(data).each(function(i){
  				var did=data[i].id;
  				alert(did);
  				var dname=data[i].name;
  				data_id=data_id+did+",";
  				data_name=data_name+dname+",";
  				alert(data_id);
  				});
  			 
  				$("#enterprise_id").val(data_id);
  				$("#e_name").val(data_name);  
  				alert(data_id); */

  				$("#enterprise_id").val(data.SYS_ORG_INFO_ID);
  				$("#e_name").val(data.NAME);  
  				
  			});
  		  }
		
		
		if( "1" == ${newInstanceFlag}){
			
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/sale/saleCopPlan/list.ht?type=${saleCopPlan.type}";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} 
		
			
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#saleCopPlanForm').attr('action','apply.ht');
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

			$('#saleCopPlanForm').attr("action","complete.ht");
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
			
			$('#saleCopPlanForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
			
		});
		
		function add_product(){
      		
    		CommonDialog("warehouse_material",
    		function(data) {
    			var row=data;
    	
    				$('input[name="materialId"]').val(row.id);
    				
    				$('input[name="materialCode"]').val(row.code);
    				
    				$('input[name="materialName"]').val(row.name);
    				
    				$('input[name="unit"]').val(row.unit);
    				
    				$('input[name="prise"]').val(row.price);
    		
    		});
    	}
		
	
		var str="";
		function add_qua(){
		
			CommonDialog("qua",
			function(data) {
				var row=data;
		for(var i=0;i<row.length;i++){
			var val=row[i];
			if(str==""){
				str=str+val["itemValue"];
			}else{
				str=str+","+val["itemValue"];
			}
			
		}
			$('input[name="qualifications"]').val(str);	
					
					
			});
		}
		
		//添加附件
		function addFile(){
			FlexUploadDialog({isSingle:false,callback:fileCallback});
		};
		
		function fileCallback(fileIds,fileNames,filePaths)
		{
			var arrPath;
			if(filePaths=="") return ;
			arrPath=filePaths.split(",");
			var arrfileId;
			if(fileIds=="") return ;
			arrfileId=fileIds.split(",");
			var arrfileName;
			if(fileNames=="") return ;
			arrfileName=fileNames.split(",");
			
			for(var i=0;i<arrfileName.length;i++){
				var tr=getRow(arrfileId[i],arrPath[i],arrfileName[i]);
				$("#attachment").append(tr);	
			}
			$("#attachment").val(fileld);
			
		};
		
		
		function delRow(fileId){
			$("tr ."+fileId).remove();
		};
		
		function getRow(fileId,filePath,fileName){
			var tr = '<tr class="'+fileId+'">';
			tr += '<td>';
			tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
			tr += '	<input type="hidden" class="pk" name="attachments" value="' + filePath + '"/> ';
			tr += '	<a href="'+__ctx+"/"+filePath+'">'+fileName+'</a> ';
			tr += '</td>';
			tr += '<td><a onclick=javascript:delRow('+fileId+'); ><span class="link-btn link-remove">&ensp; </span></a> </td>';
			tr += '</tr>';
			return tr;
		}
		
		
		
		
		
		
		$(function(){
			
	    	$("#btnAdd").click(function(){
		    	$(":input[name='materielNum']").blur(function(){
					materielNum=$(":input[name='materielNum']").val();
				
			});
				$(":input[name='firstNumber']").blur(function(){
					firstNumber=$(":input[name='firstNumber']").val();
				//	alert(firstNumber);
						if(firstNumber>materielNum)
					{
					    alert("首批进货量应小于合作销售量");
					}
			});
		
			});
	    	
		});
   
	    var add_supps_window;
    	function add_supps(){
    		//弹出物品选择框
    		var urlShow = '${ctx}/cloud/config/material/selectMaterialTree.ht';
    		add_supps_window = $.ligerDialog.open({ url:urlShow, height: 480,width: 850, title :'物品选择器', name:"frameDialog_"});
    	}	
    	
    	//物品列表回调函数
    	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
    		for ( var i = 0; i < ids.length; i++) {
				var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value="'+ ids[i]+ '"/><input type="hidden"  name="materialId" value="'+ ids[i] + '"/></td><td><input type="hidden" style="width: 70px;" name="materialName"  value="'+ names[i]+'"/>' + names[i] + '</td><td><input type="hidden" style="width: 80px;" name="materialCode" value="' + codes[i] + '"/>' + codes[i] + '</td><td><input type="text" style="width: 80px;" name="materialCodenote"  value=""/></td><td><input type="text" style="width: 80px;" name="materielLev"/></td><td><input type="text" style="width: 40px;" name="attributeInfo"/></td><td><input type="hidden" style="width: 50px;" name="unit"  value="' + units[i] + '"/> ' + units[i]+ ' </td><td><input type="text" style="width: 50px;" name="materielNum" value=""></td><td><input type="text" style="width: 80px;" name="firstNumber" value=""/></td><td><input type="hidden" style="width: 50px;" id="prise" name="prise" value="'+prices[i]+'"/>' + prices[i] + '</td></tr>');
				$("#saleCopPlanDetail").append(item);
			}
    		add_supps_window.close();
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
		function add_onesupps(){
    		var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden" name="materialId"/></td><td><input type="text" style="width: 70px;" name="materialName"/></td><td><input type="text" style="width: 70px;" name="materialCode"/></td><td><input type="text" style="width: 70px;" name="materialCodenote"/> </td><td><input type="text" style="width: 80px;" name="materielLev"/></td> <td><input type="text" style="width: 40px;" name="attributeInfo"/></td><td><input type="text" style="width: 40px;" name="unit"/></td><td><input type="text" style="width: 50px;" name="materielNum"/></td><td><input type="text" style="width: 50px;" name="firstNumber"/></td><td><input type="text" style="width: 50px;" name="prise"/></td></tr>');
			$("#saleCopPlanDetail").append(item);
    	}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${saleCopPlan.id !=null}">
			        <span class="tbar-label">编辑营销合作计划单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加营销合作计划单</span>
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
		<form id="saleCopPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th style="width:120px">单证号: </th>
					<td colspan="3"><input size="35" type="text" id="code" name="code" value="${saleCopPlan.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th style="width:120px">制单人ID: </th>
					<td><input size="35" type="text" id="operaterId" name="operaterId" value="${saleCopPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">制单日期: </th>
					<td><input size="35" type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${saleCopPlan.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">制单人: </th>
					<td><input size="35" type="text" id="operaterName" name="operaterName" value="${saleCopPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">发布模式: </th>
					<td>
					  
			 		
			    <select id="deliveryType" name="deliveryType">
					    <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.deliveryType=='公开'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='公开' ${selected} >公&nbsp;开</option>
					    <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.deliveryType=='邀请'}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='邀请' ${selected}>邀&nbsp;请</option>
					</select>
					
					</td>
					
				</tr>
				<tr>
					<th style="width:120px">发布时间: </th>
					<td><input size="35" type="text" id="deliveryDate" name="deliveryDate" value="<fmt:formatDate value='${saleCopPlan.deliveryDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">回复截止时间: </th>
					<td><input size="35" type="text" id="offerEndtime" name="offerEndtime" value="<fmt:formatDate value='${saleCopPlan.offerEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">合作营销区域: </th>
				<td>	
				    <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					</select>
				</td>
				<!-- 	<td><input size="35" type="text" id="cooperationArea" name="cooperationArea" value="${saleCopPlan.cooperationArea}"  class="inputText" validate="{required:false,number:true }"  /></td> -->
					<th style="width:120px">处理截止时间: </th>
					<td><input size="35" type="text" id="doofferEndtime" name="doofferEndtime" value="<fmt:formatDate value='${saleCopPlan.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				
				<tr>
					<th style="width:120px">合作开始时间: </th>
					<td><input size="35" type="text" id="cooperationStarttime" name="cooperationStarttime" value="<fmt:formatDate value='${saleCopPlan.cooperationStarttime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th style="width:120px">合作截止时间: </th>
					<td><input size="35" type="text" id="cooperationEndtime" name="cooperationEndtime" value="<fmt:formatDate value='${saleCopPlan.cooperationEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th style="width:120px">上报库存信息时间间隔: </th>
					<td><input size="35" type="text" id="reportstockTimespace" name="reportstockTimespace" value="${saleCopPlan.reportstockTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th style="width:120px">上报销售订单时间间隔: </th>
					<td><input size="35" type="text" id="reportsalesTimespace" name="reportsalesTimespace" value="${saleCopPlan.reportsalesTimespace}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
					<input size="35" type="hidden" id="enterpriseId" name="enterpriseId" value="${saleCopPlan.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  />
				<tr>
				    <th style="width:120px">企业: </th>
					<td><input size="35" type="text" id="enterpriseName" name="enterpriseName" value="${saleCopPlan.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th style="width:120px">企业联系人: </th>
					<td><input size="35" type="text" id="enterpriseUsername" name="enterpriseUsername" value="${saleCopPlan.enterpriseUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<input size="35" type="hidden" id="enterpriseUserid" name="enterpriseUserid" value="${saleCopPlan.enterpriseUserid}"  class="inputText" validate="{required:false,number:true }"  />
				</tr>
				<tr>
					<th style="width:120px">合作模式: </th>
					 <td>
					 
					   <select id="type" name="type"  >
					    <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.type==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='代理' ${selected} >代理</option>
					    
					    <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.type==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					    <option value='经销' ${selected}>经销</option>
					    
					    <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.type==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
					     <option value='联营' ${selected} >联营</option>
					</select>
								</td>
			<!-- 	<td><input size="35" type="text" id="type" name="type" value="${saleCopPlan.type}"  class="inputText" validate="{required:false,maxlength:96}"  /></td> -->	
					<th style="width:120px">资质要求说明（逗号分隔）: </th>
					<td><input size="35" type="text" id="qualifications" name="qualifications" value="${saleCopPlan.qualifications}"  class="inputText" validate="{required:true,maxlength:768}"  />
					
					<a href="javascript:void(0)" onclick="add_qua();">选择</a>
					</td>
				</tr>
				<tr>
					<th style="width:120px">附件: </th>
			         <td>   <!--  
					      	<input type="file" name="attachments" id="attachments" value="${saleCopPlan.attachment}"  class="inputText" >
					      	-->
					      		<div class="cell" js_picarea="true"  size="100">
										<table  width="145" name="attachment"    id="attachment" class="table-grid table-list"  cellpadding="2" cellspacing="2"></table>
										<a href="javascript:void(0)" onclick="addFile()">添加附件</a>
								</div>
						</td>
					<th style="width:120px">库存信息: </th>
					<td>
						<select name="sharedRepository">
							<option name="sharedRepository" id="sharedRepository" value="0">不共享</option>
							<option name="sharedRepository" id="sharedRepository" value="1">共享</option>
						</select>
					</td>
				</tr>
					
				<!-- 	<td colspan="3"><input size="35" type="text" id="attachment" name="attachment" value="${saleCopPlan.attachment}"   validate="{required:false,maxlength:768}"  /></td> -->
				 <tr>
				 <th style="width:120px">销售信息: </th>
					<td>
						<select name="saleRepository">
							<option name="saleRepository" id="saleRepository" value="0">不共享</option>
							<option name="saleRepository" id="saleRepository" value="1">共享</option>
						</select>
					</td>
					<th>选择供应对象</th>
					<td colspan="3">
						<input size="35" type="hidden"  name="suppEnterpriseIds" id="suppEnterpriseIds" value="" size="80"/>
						<input size="35" type="text" id="e_name" value="" readonly="readonly"   validate="{required:true,maxlength:300}" size="80"/>
						<a href="javascript:void(0)" onclick="add_supp()"  class="link detail">选择</a>
					</td>
					
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="saleCopPlanDetail" formType="window" type="sub">
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
						营销合作计划单 : 营销合作计划单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th style="display:none">物品ID</th>
					<th>物品名</th>
					<th>物品编码</th>
					<th>编码参考附件</th>
					<th>分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>合作销售数量</th>
					<th>首批进货量</th>
					<th>合作单价</th>
					<th style="display:none">操作</th>
				</tr>
				<c:forEach items="${saleCopPlanDetailList}" var="saleCopPlanDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materialId">${saleCopPlanDetailItem.materialId}</td>
					    <td style="text-align: center" name="materialName">${saleCopPlanDetailItem.materialName}</td>
					    <td style="text-align: center" name="materialCode">${saleCopPlanDetailItem.materialCode}</td>
					    <td style="text-align: center" name="materialCodenote">${saleCopPlanDetailItem.materialCodenote}</td>
					    <td style="text-align: center" name="materielLev">${saleCopPlanDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${saleCopPlanDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${saleCopPlanDetailItem.unit}</td>
					    <td style="text-align: center" name="materielNum">${saleCopPlanDetailItem.materielNum}</td>
					    <td style="text-align: center" name="firstNumber">${saleCopPlanDetailItem.firstNumber}</td>
					    <td style="text-align: center" name="prise">${saleCopPlanDetailItem.prise}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input size="35" type="hidden" name="materialId" value="${saleCopPlanDetailItem.materialId}"/>
						<input size="35" type="hidden" name="materialName" value="${saleCopPlanDetailItem.materialName}"/>
						<input size="35" type="hidden" name="materialCode" value="${saleCopPlanDetailItem.materialCode}"/>
						<input size="35" type="hidden" name="materialCodenote" value="${saleCopPlanDetailItem.materialCodenote}"/>
						<input size="35" type="hidden" name="materielLev" value="${saleCopPlanDetailItem.materielLev}"/>
						<input size="35" type="hidden" name="attributeInfo" value="${saleCopPlanDetailItem.attributeInfo}"/>
						<input size="35" type="hidden" name="unit" value="${saleCopPlanDetailItem.unit}"/>
						<input size="35" type="hidden" name="materielNum" value="${saleCopPlanDetailItem.materielNum}"/>
						<input size="35" type="hidden" name="firstNumber" value="${saleCopPlanDetailItem.firstNumber}"/>
						<input size="35" type="hidden" name="prise" value="${saleCopPlanDetailItem.prise}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="materialId"></td>
			    	<td style="text-align: center" name="materialName"></td>
			    	<td style="text-align: center" name="materialCode"></td>
			    	<td style="text-align: center" name="materialCodenote"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="materielNum"></td>
			    	<td style="text-align: center" name="firstNumber"></td>
			    	<td style="text-align: center" name="prise"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input size="35" type="hidden" name="materialId" value=""/>
			    	<input size="35" type="hidden" name="materialName" value=""/>
			    	<input size="35" type="hidden" name="materialCode" value=""/>
			    	<input size="35" type="hidden" name="materialCodenote" value=""/>
			    	<input size="35" type="hidden" name="materielLev" value=""/>
			    	<input size="35" type="hidden" name="attributeInfo" value=""/>
			    	<input size="35" type="hidden" name="unit" value=""/>
			    	<input size="35" type="hidden" name="materielNum" value=""/>
			    	<input size="35" type="hidden" name="firstNumber" value=""/>
			    	<input size="35" type="hidden" name="prise" value=""/>
		 		</tr>
		    </table>
			<input size="35" type="hidden" name="id" value="${saleCopPlan.id}" />
			<input size="35" type="hidden" id="back" name="back" value=""/>
			<input size="35" type="hidden" name="formData" id="formData" />
			<input size="35" type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input size="35" type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="saleCopPlanDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th style="width:120px">物品ID: </th>
				<td><input size="35" type="text" name="materialId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">物品名: </th>
				<td><input size="35" type="text" name="materialName" value=""  class="inputText" validate="{required:false,maxlength:768}"/><a href="javascript:void(0)" onclick="add_product();">选择</a></td>
			</tr>
			<tr>
				<th style="width:120px">物品编码: </th>
				<td><input size="35" type="text" name="materialCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">编码参考附件: </th>
				<td><input size="35" type="text" name="materialCodenote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th style="width:120px">分类: </th>
				<td><input size="35" type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">属性说明: </th>
				<td><input size="35" type="text" name="attributeInfo" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th style="width:120px">单位: </th>
				<td><input size="35" type="text" name="unit" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th style="width:120px">合作销售数量: </th>
				<td><input size="35" type="text" name="materielNum" id="materielNum"  value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th style="width:120px">首批进货量: </th>
			  	<td><input size="35" type="text" name="firstNumber" id="firstNumber"  value=""  class="inputText" validate="{required:false,number:true }"/></td>
			  	
			</tr>
			<tr>
				<th style="width:120px">合作单价: </th>
				<td><input size="35" type="text" name="prise" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
