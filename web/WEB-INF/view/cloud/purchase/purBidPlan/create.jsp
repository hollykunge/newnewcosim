<%--
	time:2013-05-18 12:36:15
	desc:edit the cloud_pur_bidplan
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 采购招标公告</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
	<script type="text/javascript">
	var manager = null;
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#purBidPlanForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
				manager = $.ligerDialog.waitting('正在保存中,请稍候...');
					form.submit();
				}
			});
		});
		
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
			var link="";
			for(var i=0;i<arrfileName.length;i++){
				var tr=getRow(arrfileId[i],arrPath[i],arrfileName[i]);
				$("#attachmentTable").append(tr);	
				link = link+arrfileId[i]+","+arrfileName[i]+";";
			}
			$('input[name="attachment"]').val(link);
		};
		function downloadFile(fileId){
			var url = "${ctx}/platform/system/sysFile/download.ht?fileId="+fileId;
			window.open(url);
		}
		function delRow(fileId){
			$("tr ."+fileId).remove();
		};
		
		function getRow(fileId,filePath,fileName){
			var tr = '<tr class="'+fileId+'">';
			tr += '<td>';
			tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
			tr += '	<input type="hidden" class="pk" name="attachments" value="' + filePath + '"/> ';
			tr += '	<a onclick=javascript:downloadFile('+fileId+'); >'+fileName+'</a> ';
			tr += '</td>';
			tr += '<td><a href="javascript:void(0)" onclick=javascript:downloadFile('+fileId+'); ><span class="link-btn link-remove">查看 </span></a></td>';
			tr += '<td><a href="javascript:void(0)" onclick=javascript:delRow('+fileId+'); ><span class="link-btn link-remove">删除 </span></a></td>';
			tr += '</tr>';
			return tr;
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
		
 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			manager.close();
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						window.location.href = "${ctx}/cloud/purchase/purBidPlan/create.ht";
					}else{
						window.location.href = "${ctx}/cloud/purchase/purBidPlan/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} 
		
		$("a.apply").click(function() {
			frm.setData();
			$('#purBidPlanForm').attr('action','apply.ht');
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

			$('#purBidPlanForm').attr("action","complete.ht");
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
			
			$('#purBidPlanForm').attr("action","complete.ht");
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
    	
    				$('.l-dialog-table input[name="materielCode"]').val(row.code);
    				
    				$('.l-dialog-table input[name="materielName"]').val(row.name);
    				
    				$('.l-dialog-table input[name="unit"]').val(row.unit);
    				
    				$('.l-dialog-table input[name="price"]').val(row.price);
    		
    		});
    	}
		
		function selectfile(){
			CommonDialog("sys_org_info_aptitude",
			function(data) {
				var row=data;
		
					$('.l-dialog-body input[name="attract"]').val(row.CATE_PIC);
					
					$('.l-dialog-body input[name="type"]').val(row.CATE_TYPE);
				
			
			});
		}
		function selectedQuoteEnts(){
			var entIds = [];
			var j = 0;
			$('#purBidPlanCertificate').find(':checkbox:checked').each(function(){
				var $tr = $(this).parent().parent();
				var tds = $tr.find('td');
				var friend = [];
				for(var i=0; i<tds.length; i++){					
					if(i!=0){						
						var td = $(tds[i]).text();							
						friend[i-1] = td;
					}
				}				
				entIds[j++] = friend[0].trim();
			});
			if(entIds.length < 3){
				alert("请至少选择三家候选供应商报价！");
			}else{
				var entIds_str = "";
				for(var i=0; i<entIds.length; i++){
					entIds_str = entIds_str + entIds[i] + ",";
				}
				entIds_str = entIds_str.substring(0, entIds_str.length -1);
			//	alert(entIds_str);
				$('input[name="entIds"]').val(entIds_str);

			}
			
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
     	
     	//商圈列表回调函数
     	function _callBackMaterialTrees(names,codes,ids,units,prices,models ){
     		for ( var i = 0; i < ids.length; i++) {
 				var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value="'
 						+ ids[i]
 						+ '"/><input type="hidden"  name="materielId" value="'+ ids[i] + '"/></td><td ><input type="hidden" style="width: 80px;" name="materielName"  value="' + names[i] + '"/> ' + names[i] + ' </td><td ><input type="hidden"  style="width: 80px;" name="materielCode"  value="' + codes[i] + '"/>' + codes[i] + '</td> <td ><input type="hidden" style="width: 80px;"  name="model"  value="' + models[i] + '"/>' + models[i] + '</td><td ><input type="text" style="width: 80px;"  name="attributeInfo"   /> </td>   <td ><input type="hidden"  style="width: 50px;" name="unit"  value="' + units[i] + '"/> ' + units[i]
 						+ ' </td><td ><input type="text"  name="orderNum" onblur="getSumPrice(this);"  style="width: 50px;"/> </td><td ><input type="hidden" style="width: 80px;" name="price" onblur="getSumPrice(this);"  value="' + prices[i] + '"/> ' + prices[i] + ' </td> <td ><input type="text"  name="sumPrice"   style="width: 80px;"/> </td> <td> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purBidPlanDetail.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /></td> </tr>');
 				$("#purBidPlanDetail").append(item);
 			}
     		add_supps_window.close();
     	}
     	
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
		}
		}else{
			$.ligerMessageBox.alert("请输入数字!");
		}
	}	
	
     	
     	
     		//增加一行
		function add_onesupps(){
    			var item = $('<tr type="subdata"><td><input type="checkbox" name="check" value=""/><input type="hidden"  name="materielId" value=""/></td><td ><input type="text" style="width: 80px;" name="materielName"  value=""/>  </td><td ><input type="text"  style="width: 80px;" name="materielCode"  value=""/></td> <td ><input type="text" style="width: 80px;"  name="model"  value=""/></td><td ><input type="text" style="width: 80px;"  name="attributeInfo"   /> </td>   <td ><input type="text"  style="width: 50px;" name="unit"  value=""/>  </td><td ><input type="text" onblur="getSumPrice(this);" name="orderNum"   style="width: 50px;"/> </td><td ><input type="text" style="width: 80px;" onblur="getSumPrice(this);" name="price"  value=""/> </td> <td ><input type="text" style="width: 80px;" name="sumPrice"  value=""/> </td> <td> <input type="text" style="width: 100px;" id="deliveryEnddate" name="deliveryEnddate" value="<fmt:formatDate value='${purBidPlanDetail.deliveryEnddate}' pattern="yyyy-MM-dd"/>" class="inputText date" validate="{date:true}" /></td> </tr>');
 				$("#purBidPlanDetail").append(item);
    	}	
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${purBidPlan.id !=null}">
			        <span class="tbar-label">编辑采购招标公告</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加采购招标公告</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<c:if test="${viewFlag.applyFlag==0}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link apply" id="dataFormStart" href="javascript:void(0)">申请</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
				<c:if test="${viewFlag.applyFlag==1}">
					<div class="group"><a id="btnAgree" class="link agree">同意</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a id="btnNotAgree" class="link notAgree">反对</a></div>
				</c:if>
				<c:if test="${viewFlag.applyFlag==2}">
					<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" href="list.ht">返回</a></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="purBidPlanForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">招标编号: </th>
					<td><input type="text" readonly class="r" id="code" name="code" value="${purBidPlan.code}" readonly="readonly" class="inputText" validate="{required:false,maxlength:96}"  onblur="addAttachment(this);"/></td>
					<th width="20%">发布模式: </th>
					<td>
						<select id="releaseModel" name="releaseModel" value="${purBidPlan.releaseModel}" validate="{required:false}">
						  <c:remove var="selected"/>
						     <c:if  test="${purBidPlan.releaseModel=='公开'}">
						         <c:set var="selected" value="selected"/>
						      </c:if>
							<option value="公开" ${selected}>公开</option>
						<c:remove var="selected"/>
					         <c:if test="${purBidPlan.releaseModel=='邀请'}">
					              <c:set var="selected" value="selected"></c:set>
					         </c:if>		
							<option value="邀请" ${selected}>邀请</option>
						</select></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operatedate"  readonly class="r"name="operatedate" value="<fmt:formatDate value='${purBidPlan.operatedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operaterName" readonly class="r" name="operaterName" value="${purBidPlan.operaterName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${purBidPlan.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">来源单据编号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${purBidPlan.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
					<!-- 制单人ID -->
					<input type="hidden" id="operaterId" name="operaterId" value="${purBidPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  />
					<!-- 来源单据ID -->
					<input  type="hidden" id="sourceformId" name="sourceformId" value="${purBidPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  />
				<!-- 
				<tr>
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operaterId" name="operaterId" value="${purBidPlan.operaterId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">来源单据ID: </th>
					<td><input  type="text" id="sourceformId" name="sourceformId" value="${purBidPlan.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				 -->
				<tr>
					<th width="20%">运费承担方: </th>
					<td>
          					<select id="freightBearer" name="freightBearer">
          					  <c:remove var="selected"/>
          					     <c:if test="${purBidPlan.freightBearer=='采购方' }">
          					        <c:set var="selected" value="selected"></c:set>
          					     </c:if>
          					    <option value="采购方" ${selected}>采购方    </option>
          					    <c:remove var="selected"/>
          					       <c:if test="${purBidPlan.freightBearer=='供应方'}">
          					          <c:set var="selected" value="selected"></c:set>
          					       </c:if>
          					    <option value="供应方" ${selected}>供应方    </option>
          					</select>
          			</td>
					<th width="20%">发布日期: </th>
					<td><input type="text" id="releasedate" name="releasedate" value="<fmt:formatDate value='${purBidPlan.releasedate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					
				</tr>
				<tr>
					<th width="20%">招标截止日期: </th>
					<td><input type="text" id="bidEndtime" name="bidEndtime" value="<fmt:formatDate value='${purBidPlan.bidEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">招标处理截止日期: </th>
					<td><input type="text" id="doobidEndtime" name="doobidEndtime" value="<fmt:formatDate value='${purBidPlan.doobidEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					
          			
					<th width="20%">币种: </th>
					  <td>
					<select id="currencyType" name="currencyType">
					    <c:remove var="selected"/>
					    <c:if test="${purBidPlan.currencyType=='人民币'}">
					         <c:set var="selected" value="selected" />
					    </c:if>
						<option value="人民币"  ${selected} >人&nbsp;民&nbsp;币</option>
						<c:remove var="selected"/>
					    <c:if test="${purBidPlan.currencyType=='美元'}">
					        <c:set var="selected" value="selected" />
					    </c:if>
						<option value="美元"  ${selected} >美&nbsp;元</option>
					</select>
					</td>
					<th width="20%">总价: </th>
					<td>
						<input  type="text" id="sumPrice" name="sumPrice" value="${purBidPlan.sumPrice}"  class="inputText" validate="{required:false,number:true }"  />
					</td>
				</tr>
				
				<c:if test="${viewFlag.statues==1}">
				<tr>
				   	<th width="20%">审核状态: </th>
					<td><input type="text" id="status" name="status" value="${purBidPlan.status}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
						
				
					<th width="20%">审核结果说明: </th>
					<td><textarea type="text" id="statusNote" name="statusNote" value="${purBidPlan.statusNote}"  class="inputText" validate="{required:false,maxlength:36}"  /></td>
				</tr>
				</c:if>
					<!-- 采购方联系人ID -->
					<input type="hidden" id="purchcontactorId" name="purchcontactorId" value="${purBidPlan.purchcontactorId}"  class="inputText" validate="{required:false,number:true }"  />
					<!-- 采购企业ID -->
					<input type="hidden" id="enterpriseId" name="enterpriseId" value="${purBidPlan.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  />
				<!-- 
				<tr>
					<th width="20%">采购方联系人ID: </th>
					<td><input type="text" id="purchcontactorId" name="purchcontactorId" value="${purBidPlan.purchcontactorId}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">采购企业ID: </th>
					<td><input type="text" id="enterpriseId" name="enterpriseId" value="${purBidPlan.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				 -->
				<tr>
					<th width="20%">采购方联系人电话: </th>
					<td><input type="text" id="purchcontactorPhone" name="purchcontactorPhone" value="${purBidPlan.purchcontactorPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
					<th width="20%">采购方联系人: </th>
					<td><input type="text" id="purchcontactorName" readonly class="r" name="purchcontactorName" value="${purBidPlan.purchcontactorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">采购企业: </th>
					<td><input type="text" id="enterpriseName"  readonly class="r" name="enterpriseName" value="${purBidPlan.enterpriseName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">上传文档: </th>
					<td>
					<input type="hidden" name ="attachment" id="attachment" value="">
			      		<div class="cell" js_picarea="true"  size="100">
							<table  width="145" name="attachmentTable"    id="attachmentTable" class="table-grid table-list"  cellpadding="2" cellspacing="2"></table>
							<a href="javascript:void(0)" onclick="addFile()">添加文档</a>
						</div>
					</td>
				</tr>
				<!-- 
				<tr>
					<th width="20%">link: </th>
					<td><input type="text" id="link" name="link" value="${purBidPlan.link}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
					<th width="20%">service_enterprise: </th>
					<td><input type="text" id="serviceEnterprise" name="serviceEnterprise" value="${purBidPlan.serviceEnterprise}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
 				-->
				<tr>
					<th width="20%">保证金: </th>
					<td><input type="text" id="margins" name="margins" value="${purBidPlan.margins}"  class="inputText" validate="{required:false}"  /></td>
			
					<th style="width:120px">资质要求说明（逗号分隔）: </th>
					<td><input size="35" type="text" id="qualifications" name="qualifications" value="${purBidPlan.qualifications}"  class="inputText" validate="{required:true,maxlength:256}"  />					
					    <c:if test="${viewFlag.addquo == 1 }">
					    	<a href="javascript:void(0)" onclick="add_qua();">选择</a>
					    </c:if>
					</td>
				</tr>
			
					<tr>		
					   <td><input type="hidden" id="entIds" name="entIds" value="${purBidPlan.entIds}"/></td>
					</tr>
				
			</table>
			
			<c:if test="${viewFlag.purBidPlanDetail==1}">
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purBidPlanDetail" formType="window" type="sub">
				<tr>
					<td colspan="10">
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
												style="border: 0;"> </a>
								
							</div>
			    		<div align="center">
						采购招标公告 : 采购招标公告详情
			    		</div>
		    		</td>
				</tr>
				<tr>
				<th><input type="checkbox"  id="checkbox" title="全选/反选" onclick="checkall();"/></th>
					<th style="display:none">物品ID</th>
					<th>物品名</th>
					<th>物品编码</th>
					<th style="display:none">物品分类</th>
					<th>物品规格</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总价</th>
					<th>要求交货截止日期</th>
					<th style="display:none">操作</th>
				</tr>
				<c:forEach items="${purBidPlanDetailList}" var="purBidPlanDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materielName">${purBidPlanDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielCode">${purBidPlanDetailItem.materielCode}</td>
					    <td style="display:none" name="materielLev">${purBidPlanDetailItem.materielLev}</td>
					    <td style="text-align: center" name="model">${purBidPlanDetailItem.model}</td>
					    <td style="text-align: center" name="attributeInfo">${purBidPlanDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${purBidPlanDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${purBidPlanDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purBidPlanDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${purBidPlanDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purBidPlanDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="materielId" value="${purBidPlanDetailItem.materielId}"/>
						<input type="hidden" name="materielName" value="${purBidPlanDetailItem.materielName}"/>
						<input type="hidden" name="materielCode" value="${purBidPlanDetailItem.materielCode}"/>
						<input type="hidden" name="materielLev" value="${purBidPlanDetailItem.materielLev}"/>
						<input type="hidden" name="model" value="${purBidPlanDetailItem.model}"/>
						<input type="hidden" name="attributeInfo" value="${purBidPlanDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${purBidPlanDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${purBidPlanDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${purBidPlanDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${purBidPlanDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${purBidPlanDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="text-align: center" name="model"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="model" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="orderNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
		 		</tr>
		    </table>
		    </c:if>
		    
		    <c:if test="${viewFlag.purBidPlanCertificate==1}">
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purBidPlanCertificate" formType="window" type="sub">
				<tr>
					<td colspan="7">
					   <c:if test="${viewFlag.addcers == 1 }">
							<div class="group" align="left">
					   			<a id="btnAdd" class="link add">添加</a>
				    		</div>
			    		</c:if>
			    		<div align="center">
						采购招标公告 : 采购招标公告_企业资质
			    		</div>
		    		</td>
				</tr>
				<tr>					
					<th>选择</th>
					<th>供应企业ID</th>
					<th>供应企业</th>
					<th>资质附件</th>
				  	<th>资质项</th>
					<th>资质类型</th>
					<th>说明</th>
     				<th>操作</th>
				</tr>
				<c:forEach  items="${purBidPlanCertificateList}" var="purBidPlanCertificateItem" varStatus="status">
				    <tr type="subdata">
				        <c:if test="${viewFlag.checkbox==1}"> 
				          <c:if test="${ purBidPlanCertificateItem.note !='不参加'}"> 
					      	<td style="text-align: center" name="num">
						    	<c:if test="${ purBidPlanCertificateItem.num ==1}">
						    	    <input type="checkbox" name="selectedValue" id="selectedValue"  onclick="selectedQuoteEnts(${purBidPlanCertificateItem.suppenterpriseId})" validate="{required:true}"/>
						        </c:if>
						    </td>
					        <!-- 需要在这里将选中的企业设置到 purBidPlan得entIds，以便于前置脚本分发会签任务 -->	
						    <td style="text-align: center" name="suppenterpriseId">
						        ${purBidPlanCertificateItem.suppenterpriseId}
						    </td>
						    
						    <td style="text-align: center" name="suppenterpriseName">${purBidPlanCertificateItem.suppenterpriseName}</td>				 			    
						    <td style="text-align: center" name="attract">${purBidPlanCertificateItem.attract}</td>
						    <td style="text-align: center" name="name">${purBidPlanCertificateItem.name}</td>
						    <td style="text-align: center" name="type">${purBidPlanCertificateItem.type}</td>
						    <td style="text-align: center" name="note">${purBidPlanCertificateItem.note}</td>
						    <td style="text-align: center">
						    	<a href="javascript:void(0)" class="link del">删除</a>
						    	<a href="javascript:void(0)" class="link edit">编辑</a>
						    </td>
						    <input type="hidden" name="id" value="${purBidPlanCertificateItem.id}"/>
						    <input type="hidden" name="num" value="${purBidPlanCertificateItem.num}"/>
							<input type="hidden" name="suppenterpriseId" value="${purBidPlanCertificateItem.suppenterpriseId}"/>
							<input type="hidden" name="suppenterpriseName" value="${purBidPlanCertificateItem.suppenterpriseName}"/>
							<input type="hidden" name="attract" value="${purBidPlanCertificateItem.attract}"/>
							<input type="hidden" name="name" value="${purBidPlanCertificateItem.name}"/>
							<input type="hidden" name="type" value="${purBidPlanCertificateItem.type}"/>
							<input type="hidden" name="note" value="${purBidPlanCertificateItem.note}"/>					    
					      </c:if>
					    </c:if>
					    <c:if test="${viewFlag.checkbox==0}"> 
						    <c:if test="${currentEnterpId == purBidPlanCertificateItem.suppenterpriseId}"> 
							   	<td style="text-align: center" name="num">${purBidPlanCertificateItem.num}</td>
							    <td style="text-align: center" name="suppenterpriseId">${purBidPlanCertificateItem.suppenterpriseId}</td>
							    <td style="text-align: center" name="suppenterpriseName">${purBidPlanCertificateItem.suppenterpriseName}</td>				 			    
							    <td style="text-align: center" name="attract">${purBidPlanCertificateItem.attract}</td>
							    <td style="text-align: center" name="name">${purBidPlanCertificateItem.name}</td>
							    <td style="text-align: center" name="type">${purBidPlanCertificateItem.type}</td>
							    <td style="text-align: center" name="note">${purBidPlanCertificateItem.note}</td>
							    <td style="text-align: center">
							    	<a href="javascript:void(0)" class="link del">删除</a>
							    	<a href="javascript:void(0)" class="link edit">编辑</a>
							    </td>
							<input type="hidden" name="id" value="${purBidPlanCertificateItem.id}"/>
							<input type="hidden" name="num" value="${purBidPlanCertificateItem.num}"/>
							<input type="hidden" name="suppenterpriseId" value="${purBidPlanCertificateItem.suppenterpriseId}"/>
							<input type="hidden" name="suppenterpriseName" value="${purBidPlanCertificateItem.suppenterpriseName}"/>
							<input type="hidden" name="attract" value="${purBidPlanCertificateItem.attract}"/>
							<input type="hidden" name="name" value="${purBidPlanCertificateItem.name}"/>
							<input type="hidden" name="type" value="${purBidPlanCertificateItem.type}"/>
							<input type="hidden" name="note" value="${purBidPlanCertificateItem.note}"/>
				     </c:if>
				     </c:if>
				    </tr>
				</c:forEach>
				<tr type="append">
				   	<td style="text-align: center" name="num"></td>
			    	<td style="text-align: center" name="suppenterpriseId"></td>
			    	<td style="text-align: center" name="suppenterpriseName"></td>
			    	<td style="text-align: center" name="attract"></td>
			    	<td style="text-align: center" name="name"></td>
			    	<td style="text-align: center" name="type"></td>
			    	<td style="text-align: center" name="note"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="id" value=""/>
			    	<input type="hidden" name="num" value=""/>
			    	<input type="hidden" name="suppenterpriseId" value=""/>
			    	<input type="hidden" name="suppenterpriseName" value=""/>
			    	<input type="hidden" name="attract" value=""/>
			    	<input type="hidden" name="name" value=""/>
			    	<input type="hidden" name="type" value=""/>
			    	<input type="hidden" name="note" value=""/>
		 		</tr>
		    </table>
		    </c:if>
		    
			<input type="hidden" name="id" value="${purBidPlan.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		
			
		</form>
		
	</div>
	<form id="purBidPlanDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
		<input type="hidden" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/>
			<!-- 
			<tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			 -->
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/><a href="javascript:void(0)" onclick="add_product();">选择</a></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">所属等级: </th>
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
				<th width="20%">订单数量: </th>
				<td><input type="text" name="orderNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货时间: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
		</table>
	</form>
	
	
	<form id="purBidPlanCertificateForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">编号: </th>
				<td><input type="text" name="num" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">供应企业ID: </th>
				<td><input type="text" name="suppenterpriseId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">供应企业: </th>
				<td><input type="text" name="suppenterpriseName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">资质附件: </th>
				<td><input type="text" name="attract" value=""  class="inputText" validate="{required:false,maxlength:768}"/>
				    <a href="javascript:void(0)" onclick="selectfile();">选择</a>
				</td>
			</tr>
			<tr>
				<th width="20%">资质项: </th>
				<td><input type="text" name="name" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">资质类型: </th>
				<td><input type="text" name="type" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">说明: </th>
				<td><input type="text" name="note" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
