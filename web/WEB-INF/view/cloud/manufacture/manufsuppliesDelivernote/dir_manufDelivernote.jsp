<%--
	time:2013-05-11 14:16:40
	desc:edit the cloud_manufsupplies_delivernote
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 委外加工物料发货单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#manufsuppliesDelivernoteForm').form();
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
						window.location.href = "${ctx}/cloud/manufsuppliesDelivernote/manufsuppliesDelivernote/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		}
		
		$("a.apply").click(function() {
			frm.setData();
			$('#manufsuppliesDelivernoteForm').attr('action','apply.ht');
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

			$('#manufsuppliesDelivernoteForm').attr("action","complete.ht");
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
			
			$('#manufsuppliesDelivernoteForm').attr("action","complete.ht");
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
			    <c:when test="${manufsuppliesDelivernote.id !=null}">
			        <span class="tbar-label">编辑委外加工物料发货单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加委外加工物料发货单</span>
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
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="manufsuppliesDelivernoteForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号:</th>
					<td><input type="text" id="code" name="code" value="${manufsuppliesDelivernote.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				  <!--  
					<th width="20%">物料ID:</th>-->
				<input type="hidden" id="suppliesId" name="suppliesId" value="${manufsuppliesDelivernote.suppliesId}"  class="inputText" validate="{required:false,maxlength:96}"  />
				</tr>
				
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${manufsuppliesDelivernote.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
			
					<th width="20%">制单人姓名: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${manufsuppliesDelivernote.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${manufsuppliesDelivernote.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">来源单据编号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${manufsuppliesDelivernote.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">接收企业名称: </th>
					<td><input type="text" id="recenterpName" readonly="readonly" name="recenterpName" value="${manufsuppliesDelivernote.recenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">接收企业联系人姓名: </th>
					<td><input type="text" id="recenterpUsername" readonly="readonly" name="recenterpUsername" value="${manufsuppliesDelivernote.recenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				
				<tr>
					<th width="20%">供应企业名称: </th>
					<td><input type="text" id="suppenterpName" readonly="readonly" name="suppenterpName" value="${manufsuppliesDelivernote.suppenterpName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
			
					<th width="20%">供应企业联系人姓名: </th>
					<td><input type="text" id="suppenterpUsername" readonly="readonly" name="suppenterpUsername" value="${manufsuppliesDelivernote.suppenterpUsername}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货仓库名称: </th>
					<td><input type="text" id="receivewarehouseName" readonly="readonly" name="receivewarehouseName" value="${manufsuppliesDelivernote.receivewarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				
					<th width="20%">收货仓库详细地址: </th>
					<td><input type="text" id="receivewarehouseAddress" readonly="readonly" name="receivewarehouseAddress" value="${manufsuppliesDelivernote.receivewarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">收货人姓名: </th>
					<td><input type="text" id="receiverName" readonly="readonly" name="receiverName" value="${manufsuppliesDelivernote.receiverName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">收货人手机号: </th>
					<td><input type="text" id="receiverPhone" name="receiverPhone" value="${manufsuppliesDelivernote.receiverPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">收货人邮箱: </th>
					<td><input type="text" id="receiverMail" name="receiverMail" value="${manufsuppliesDelivernote.receiverMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">收货地址邮编: </th>
					<td><input type="text" id="receiverPostcode" name="receiverPostcode" value="${manufsuppliesDelivernote.receiverPostcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">发货仓库名称: </th>
					<td><input type="text" id="deliverwarehouseName" readonly="readonly" name="deliverwarehouseName" value="${manufsuppliesDelivernote.deliverwarehouseName}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
			
					<th width="20%">发货仓库详细地址: </th>
					<td><input type="text" id="deliverwarehouseAddress" readonly="readonly" name="deliverwarehouseAddress" value="${manufsuppliesDelivernote.deliverwarehouseAddress}"  class="inputText" validate="{required:false,maxlength:768}"  /></td>
				</tr>
				<tr>
					<th width="20%">发货人姓名: </th>
					<td><input type="text" id="delivererName" readonly="readonly" name="delivererName" value="${manufsuppliesDelivernote.delivererName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">发货人手机号: </th>
					<td><input type="text" id="delivererPhone" name="delivererPhone" value="${manufsuppliesDelivernote.delivererPhone}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">发货人邮箱: </th>
					<td><input type="text" id="delivererMail" name="delivererMail" value="${manufsuppliesDelivernote.delivererMail}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				
					<th width="20%">发货人邮编: </th>
					<td><input type="text" id="delivererPostcode" name="delivererPostcode" value="${manufsuppliesDelivernote.delivererPostcode}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">运费承担方: </th>
					<td><input type="text" id="freightBearer" readonly="readonly" name="freightBearer" value="${manufsuppliesDelivernote.freightBearer}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${manufsuppliesDelivernote.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<fieldset>
			<legend>物料详情</legend>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1"  formType="window">
				
				<tr>
					<th>物品编码</th>
					<th>编码规则</th>
					<th>物品名称</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>计划下达日期</th>
					<th>计划到货日期</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${manufEnquiryorderSuppliesList}" var="manufEnquiryorderSuppliesItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="suppliesCode">${manufEnquiryorderSuppliesItem.suppliesCode}</td>
					    <td style="text-align: center" name="suppliesCodenotation">${manufEnquiryorderSuppliesItem.suppliesCodenotation}</td>
					    <td style="text-align: center" name="suppliesName">${manufEnquiryorderSuppliesItem.suppliesName}</td>
					    <td style="text-align: center" name="suppliesSpec">${manufEnquiryorderSuppliesItem.suppliesSpec}</td>
					    <td style="text-align: center" name="suppliesUnit">${manufEnquiryorderSuppliesItem.suppliesUnit}</td>
						<td style="text-align: center" name="planDeliverydate"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planDeliverydate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="planAogdate"><fmt:formatDate value='${manufEnquiryorderSuppliesItem.planAogdate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="comments">${manufEnquiryorderSuppliesItem.comments}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="suppliesCode" value="${manufEnquiryorderSuppliesItem.suppliesCode}"/>
						<input type="hidden" name="suppliesCodenotation" value="${manufEnquiryorderSuppliesItem.suppliesCodenotation}"/>
						<input type="hidden" name="suppliesName" value="${manufEnquiryorderSuppliesItem.suppliesName}"/>
						<input type="hidden" name="suppliesSpec" value="${manufEnquiryorderSuppliesItem.suppliesSpec}"/>
						<input type="hidden" name="suppliesUnit" value="${manufEnquiryorderSuppliesItem.suppliesUnit}"/>
					    <input type="hidden" name="planDeliverydate" value="<fmt:formatDate value='${manufEnquiryorderSuppliesItem.planDeliverydate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="planAogdate" value="<fmt:formatDate value='${manufEnquiryorderSuppliesItem.planAogdate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="comments" value="${manufEnquiryorderSuppliesItem.comments}"/>
				    </tr>
				</c:forEach>
				
		    </table>
		    </fieldset>	   
</div>
</body>
</html>
