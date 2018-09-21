<%--
	time:2013-04-13 19:06:38
	desc:edit the cloud_pur_enq
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 采购询价单</title>

	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	
	<script type="text/javascript">
	$(function(){
		//放置脚本
		var link="${purEnquiry.attachment}";
		$("#attachment").val(link);
		link=link.split(";");
		for(var i=0;i<link.length-1;i++){
			var subs=link[i].split(",");
			var tr=getRow(subs[0],subs[1]);
			$("#attachmentTable").append(tr);
		}
	});
	
	function getRow(fileId,fileName){
		var tr = '<tr class="'+fileId+'">';
		tr += '<td>';
		tr += '	<span class="attach">&ensp;&ensp; </span>&nbsp;&nbsp;';
		tr += '	<a href="javascript:void(0)" onclick=javascript:downloadFile('+fileId+'); >'+fileName+'</a> ';
		tr += '</td>';
		tr += '<td><a href="javascript:void(0)" onclick=javascript:downloadFile('+fileId+'); ><span class="link-btn link-remove">查看 </span></a></td>';
		tr += '</tr>';
		return tr;
	}
	
	function downloadFile(fileId){
		var url = "${ctx}/platform/system/sysFile/download.ht?fileId="+fileId;
		window.open(url);
	}
	
	//企业选择器
	var dd;
	function add_supps(){
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
			var frm=$('#purEnquiryForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
	
        function userview(){
  			CommonDialog("busiareaList",
  			function(data) {
  				$("#suppEnterpriseIds").val(data.SYS_ORG_INFO_ID);
  				$("#e_name").val(data.NAME);  
  				
  			});
  		  }
        
        /*
 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/purchase/purEnquiry/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#purEnquiryForm').attr('action','apply.ht');
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		
		$("a.agree").click(function() {
			operatorType=1;
			var tmp = '1';
			$('#voteAgree').val(tmp);
			$('#back').val("1");

			$('#purEnquiryForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
	
		$("a.notAgree").click(function() {
			operatorType=2;
			var tmp = '2';
			$('#voteAgree').val(tmp);
			$('#back').val("1");
			
			$('#purEnquiryForm').attr("action","complete.ht");
			frm.setData();
			frm.ajaxForm(options);
			if(frm.valid()){
				form.submit();
			}
		});
		*/
		
		function selUsers(btn){
			UserDialog({
				isSingle:false,
				callback:function(userId,fullname){
					$('#userIds').val(userId);
					$('#userNames').val(fullname);
				}
			}).show();
		}
	
		
	/**
	 * 增加物品
	 */
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
	
	/* $(function(){
		$(".delproduct").click(function(){
			var i = $(".delproduct").index(this);
			alert(i);
		});
	}) */;
	
	 
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${purEnquiry.id !=null}">
			        <span class="tbar-label">采购询价单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">采购询价单</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
	    <div class="panel-toolbar" style="display: none">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back " href="list.ht">返回</a></div>
			</div>
		</div>
		
		<div class="panel-toolbar" style="display:none">
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
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="purEnquiryForm" method="post" action="${ctx}/cloud/purchase/purEnquiry/save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<input type="hidden" id="id" name="id"  value="${purEnquiry.id}" />
				<tr style="display:none">
					<th width="20%">runId: </th>
					<td><input type="text" id="runId" name="runId" readonly class="r" value="${purEnquiry.runId}"  validate="{required:false,maxlength:96}"  /></td>
					<td><input type="text" id="attachment" name="attachment"   value="${purEnquiry.attachment}"   /></td>
					
				</tr>
				
				<tr style="display:none">
					<th width="20%">ID: </th>
					<td><input type="text" id="id" name="id" readonly class="r" value="${purEnquiry.id}"  validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">询价单编码: </th>
					<td><input type="text" id="code" name="code" readonly class="r" value="${purEnquiry.code}"  validate="{required:false,maxlength:96}"  /></td>
			
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" readonly class="r" value="<fmt:formatDate value='${purEnquiry.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input readonly class="r" type="text" id="operatorName" name="operatorName" value="${purEnquiry.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
			
				
					<th width="20%">发布日期: </th>
					<td><input type="text" id="releaseDate" name="releaseDate" value="<fmt:formatDate value='${purEnquiry.releaseDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				<!--<th width="20%">制单人ID: </th>
					<td>-->	
					<input type="hidden" id="operatorId" name="operatorId" value="${purEnquiry.operatorId}"  class="inputText" validate="{required:false,maxlength:60}"  />
				 
				 </tr>
				<tr>
					<th width="20%">发布模式: </th>
					<td>
						<select id="releaseModel" name="releaseModel" value="${purEnquiry.releaseModel}" validate="{required:false}">
							<option>公开</option>
							<option>邀请</option>
						</select>
<%-- 						<input type="text" id="releaseModel" name="releaseModel" value="${purEnquiry.releaseModel}"  class="inputText" validate="{required:false,maxlength:96}"  />
 --%>					</td>
					<th width="20%">来源单据类型: </th>
					<td><input readonly class="r" type="text" id="sourceformType" name="sourceformType" value="${purEnquiry.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td><input readonly class="r" type="text" id="sourceformCode" name="sourceformCode" value="${purEnquiry.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					<th width="20%">报价截止时间: </th>
					<td><input type="text" id="offerEndtime" name="offerEndtime" value="<fmt:formatDate value='${purEnquiry.offerEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<!--<tr>
					 <th width="20%">来源单据ID: </th>
					<td>-->
					<input type="hidden" id="sourceformId" name="sourceformId" value="${purEnquiry.sourceformId}"  class="inputText" validate="{required:false,number:true }"  />
				<!--	</td>
				</tr>-->
				<tr>
					<th width="20%">报价处理截止时间: </th>
					<td><input type="text" id="doofferEndtime" name="doofferEndtime" value="<fmt:formatDate value='${purEnquiry.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" name="freightBearer" value="${purEnquiry.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">币种: </th>
					<td>
						<select id="currencyType" name="currencyType" value="${purEnquiry.currencyType}" validate="{required:false,maxlength:96}">
							<option>人民币</option>
							<option>美元</option>
							<option>日元</option>
							<option>英镑</option>
							<option>卢布</option>
						</select>
					<%--<input type="text" id="currencyType" name="currencyType" value="${purEnquiry.currencyType}"  class="inputText" validate="{required:false,maxlength:96}"  />--%>					
					<th width="20%">采购商联系人: </th>
					<td><input type="text" id="purenterpUsername" name="purenterpUsername" value="${purEnquiry.purenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
					</td>
				</tr>
				<!-- <tr>
					<th width="20%">采购商联系人ID: </th>
					<td> -->
					<input type="hidden" id="purenterpUserid" name="purenterpUserid" value="${purEnquiry.purenterpUserid}"  class="inputText" validate="{required:false,number:true }"  />
				<!--  	</td>
				</tr>-->
				<tr>
					<th width="20%">采购企业: </th>
					<td>
						<input readonly class="r" type="text" id="enterpriseName" name="enterpriseName" value="${purEnquiry.enterpriseName}" />
					</td>
					<th>选择供应商</th>
					<td>
						<input type="hidden"  name="suppEnterpriseIds" id="suppEnterpriseIds" value="" size="60"/>
						<input type="text" id="e_name" validate="{required:true,maxlength:300}" size="40"/>
						<a href="javascript:void(0)" onclick="add_supps()"  class="link detail">选择</a>
					</td>
				</tr>
				<!-- <tr>
					<th width="20%">采购企业ID: </th>
					<td> -->
					<input type="hidden" id="enterpriseId" name="enterpriseId" value="${purEnquiry.enterpriseId}"  class="inputText" validate="{required:false,number:true }"  />
				<!--	</td>
				</tr>-->
				
				<tr>
					<th width="20%">文档说明: </th>
				<td>
					<div>
						<table  width="145" name="attachmentTable"    id="attachmentTable" class="table-grid table-list"  cellpadding="2" cellspacing="2">
						</table>
					</div>
				</td>
				<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrice" name="sumPrice"  readonly  value="${purEnquiry.sumPrice}"  class="r" validate="{required:false,number:true}"  /></td>
				</tr>
				
				
			
			</table>
			
			
			
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purEnquiryDetail" formType="window" type="sub">
				<tr>
					<td colspan="10">
						<div class="group" align="left">
								<!-- <a id="btnAdd" class="link add">添加</a>-->
								
							</div>
			    		<div align="center">
						采购询价单: 采购询价单明细
			    		</div>
		    		</td>
				</tr>
				<tr>
				
					<th style="display:none" >物品ID</th>
					<th>产品订单号</th>
					<th>物品名</th>
					<th>物品编码</th>
					<th style="display:none">物品分类</th>
					<th>物品规格</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>预计单价</th>
					<th>总金额</th>
					<th>要求发货日期</th>
					<!--<th>操作</th>-->
				</tr>
				<c:forEach items="${purEnquiryDetailList}" var="purEnquiryDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="display:none" name="materielId">${purEnquiryDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${purEnquiryDetailItem.purorderCode}</td>
					    <td style="text-align: center" name="materielName">${purEnquiryDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielCode">${purEnquiryDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${purEnquiryDetailItem.model}</td>
					    <td style="display:none" center" name="materielLev">${purEnquiryDetailItem.materielLev}</td>
					    <td style="text-align: center" name="unit">${purEnquiryDetailItem.unit}</td>
					    <td style="display:none" center" name="attributeInfo">${purEnquiryDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="orderNum">${purEnquiryDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purEnquiryDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${purEnquiryDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					   <!-- <td style="text-align: center">
					    	<a href="javascript:void(0)" class="delproduct" >删除</a>
					        <a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
					    -->
						<input type="hidden" name="materielId" value="${purEnquiryDetailItem.materielId}"/>
						
					    <input type="hidden" name="materielCode" value="${purEnquiryDetailItem.purorderCode}"/>
						<input type="hidden" name="materielName" value="${purEnquiryDetailItem.materielName}"/>
					    <input type="hidden" name="materielCode" value="${purEnquiryDetailItem.materielCode}"/>
						<input type="hidden" name="materielLev" value="${purEnquiryDetailItem.materielLev}"/>
						<input type="hidden" name="model" value="${purEnquiryDetailItem.model}"/>
						<input type="hidden" name="unit" value="${purEnquiryDetailItem.unit}"/>
						<input type="hidden" name="attributeInfo" value="${purEnquiryDetailItem.attributeInfo}"/>
						<input type="hidden" name="orderNum" value="${purEnquiryDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${purEnquiryDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${purEnquiryDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${purEnquiryDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="display:none" name="materielId"></td>
			    	<td style="text-align: center" name="purorderCode"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="display:none" name="materielLev"></td>
			    	<td style="text-align: center" name="model"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="display:none" name="attributeInfo"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="purorderCode" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
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
		  <hotent:paging tableId="purEnquiryDetailList"/>
			<input type="hidden" name="id" value="${purEnquiry.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
		</form>
		
	</div>
	<form id="purEnquiryDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<input type="hidden" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/>
			<!-- 
			<tr style="display:none">
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/> </td>
			</tr>
			 -->
			<tr>
				<th width="20%">产品订单号: </th>
				<td><input type="text" name="purorderCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/> <a href="javascript:void(0)" onclick="add_product();"  class="link detail">选择</a></td>
			</tr>
			<!-- 
			<tr>
				<th width="20%">物品分类: </th>
				<td><input type="text" name="materielLev" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			 -->
			<tr>
				<th width="20%">物品属性说明: </th>
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
				<th width="20%">预计单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
		</table>
	</form>
</div>
</body>
</html>
