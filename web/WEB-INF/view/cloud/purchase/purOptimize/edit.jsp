<%--
	time:2013-04-13 19:06:38
	desc:edit the cloud_pur_optimize
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 采购优选单</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#purOptimizeForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
				}
			});
		});
		
/* 		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerMessageBox.confirm("提示信息", obj.getMessage()+",是否继续操作", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/cloud/purchase/purOptimize/list.ht";
					}
				});
			} else {
				$.ligerMessageBox.error("提示信息",obj.getMessage());
			}
		} */
		
		$("a.apply").click(function() {
			frm.setData();
			$('#purOptimizeForm').attr('action','apply.ht');
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

			$('#purOptimizeForm').attr("action","complete.ht");
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
			
			$('#purOptimizeForm').attr("action","complete.ht");
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
			    <c:when test="${purOptimize.id !=null}">
			        <span class="tbar-label">编辑采购优选单</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加采购优选单</span>
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
		<form id="purOptimizeForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td><input type="text" id="code" name="code" value="${purOptimize.code}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${purOptimize.operateDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operatorName" name="operatorName" value="${purOptimize.operatorName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="${purOptimize.sourceformType}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">来源单据ID: </th>
					<td><input type="text" id="sourceformId" name="sourceformId" value="${purOptimize.sourceformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${purOptimize.sourceformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">处理截止日期: </th>
					<td><input type="text" id="doofferEndtime" name="doofferEndtime" value="<fmt:formatDate value='${purOptimize.doofferEndtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">制单人ID: </th>
					<td><input type="text" id="operatorId" name="operatorId" value="${purOptimize.operatorId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr>
					<th width="20%">中标供应商名: </th>
					<td><input type="text" id="supplierName" name="supplierName" value="${purOptimize.supplierName}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">中标供应商ID: </th>
					<td><input type="text" id="supplierId" name="supplierId" value="${purOptimize.supplierId}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
				<tr style="display: none;">
					<th width="20%">中标报价单ID: </th>
					<td><input type="text" id="quoteformId" name="quoteformId" value="${purOptimize.quoteformId}"  class="inputText" validate="{required:false,number:true }"  /></td>
				</tr>
				<tr>
					<th width="20%">中标报价单code: </th>
					<td><input type="text" id="quoteformCode" name="quoteformCode" value="${purOptimize.quoteformCode}"  class="inputText" validate="{required:false,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="purOptimizeDetail" formType="window" type="sub">
				<tr>
					<td colspan="20">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加</a>
			    		</div>
			    		<div align="center">
						采购优选单 : 采购优选单详情
			    		</div>
		    		</td>
				</tr>
				<tr>
					<th style="display: none;">供应商ID</th>
					<th>供应商</th>
					<th>行号</th>
					<th style="display: none;">物品ID</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总价</th>
					<th>要求发货截止日期</th>
					<th>预计发货截止日期</th>
					<th>中标结果</th>
					<th>供应商编码</th>
					<th>结果说明</th>
					<th style="display: none;">来源报价单ID</th>
					<th>来源报价单Code</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${purOptimizeDetailList}" var="purOptimizeDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="display: none;" name="supplierId">${purOptimizeDetailItem.supplierId}</td>
					    <td style="text-align: center" name="supplierName">${purOptimizeDetailItem.supplierName}</td>
					    <td style="text-align: center" name="materielIndex">${purOptimizeDetailItem.materielIndex}</td>
					    <td style="display: none;" name="materielId">${purOptimizeDetailItem.materielId}</td>
					    <td style="text-align: center" name="materielCode">${purOptimizeDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${purOptimizeDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${purOptimizeDetailItem.materielLev}</td>
					    <td style="text-align: center" name="attributeInfo">${purOptimizeDetailItem.attributeInfo}</td>
					    <td style="text-align: center" name="unit">${purOptimizeDetailItem.unit}</td>
					    <td style="text-align: center" name="orderNum">${purOptimizeDetailItem.orderNum}</td>
					    <td style="text-align: center" name="price">${purOptimizeDetailItem.price}</td>
					    <td style="text-align: center" name="sumPrice">${purOptimizeDetailItem.sumPrice}</td>
						<td style="text-align: center" name="deliveryEnddate"><fmt:formatDate value='${purOptimizeDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center" name="predeliveryEnddate"><fmt:formatDate value='${purOptimizeDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
					    <td style="text-align: center" name="resulte">${purOptimizeDetailItem.resulte}</td>
					    <td style="text-align: center" name="supplierCode">${purOptimizeDetailItem.supplierCode}</td>
					    <td style="text-align: center" name="resulteNote">${purOptimizeDetailItem.resulteNote}</td>
					    <td style="display: none;" name="quoteformId">${purOptimizeDetailItem.quoteformId}</td>
					    <td style="text-align: center" name="quoteformCode">${purOptimizeDetailItem.quoteformCode}</td>
					    <td style="text-align: center">
					    	<a href="javascript:void(0)" class="link del">删除</a>
					    	<a href="javascript:void(0)" class="link edit">编辑</a>
					    </td>
						<input type="hidden" name="supplierId" value="${purOptimizeDetailItem.supplierId}"/>
						<input type="hidden" name="supplierName" value="${purOptimizeDetailItem.supplierName}"/>
						<input type="hidden" name="materielIndex" value="${purOptimizeDetailItem.materielIndex}"/>
						<input type="hidden" name="materielId" value="${purOptimizeDetailItem.materielId}"/>
						<input type="hidden" name="materielCode" value="${purOptimizeDetailItem.materielCode}"/>
						<input type="hidden" name="materielName" value="${purOptimizeDetailItem.materielName}"/>
						<input type="hidden" name="materielLev" value="${purOptimizeDetailItem.materielLev}"/>
						<input type="hidden" name="attributeInfo" value="${purOptimizeDetailItem.attributeInfo}"/>
						<input type="hidden" name="unit" value="${purOptimizeDetailItem.unit}"/>
						<input type="hidden" name="orderNum" value="${purOptimizeDetailItem.orderNum}"/>
						<input type="hidden" name="price" value="${purOptimizeDetailItem.price}"/>
						<input type="hidden" name="sumPrice" value="${purOptimizeDetailItem.sumPrice}"/>
					    <input type="hidden" name="deliveryEnddate" value="<fmt:formatDate value='${purOptimizeDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
					    <input type="hidden" name="predeliveryEnddate" value="<fmt:formatDate value='${purOptimizeDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/>" class="inputText date"/>
						<input type="hidden" name="resulte" value="${purOptimizeDetailItem.resulte}"/>
						<input type="hidden" name="supplierCode" value="${purOptimizeDetailItem.supplierCode}"/>
						<input type="hidden" name="resulteNote" value="${purOptimizeDetailItem.resulteNote}"/>
						<input type="hidden" name="quoteformId" value="${purOptimizeDetailItem.quoteformId}"/>
						<input type="hidden" name="quoteformCode" value="${purOptimizeDetailItem.quoteformCode}"/>
				    </tr>
				</c:forEach>
				<tr type="append">
			    	<td style="text-align: center" name="supplierId"></td>
			    	<td style="text-align: center" name="supplierName"></td>
			    	<td style="text-align: center" name="materielIndex"></td>
			    	<td style="text-align: center" name="materielId"></td>
			    	<td style="text-align: center" name="materielCode"></td>
			    	<td style="text-align: center" name="materielName"></td>
			    	<td style="text-align: center" name="materielLev"></td>
			    	<td style="text-align: center" name="attributeInfo"></td>
			    	<td style="text-align: center" name="unit"></td>
			    	<td style="text-align: center" name="orderNum"></td>
			    	<td style="text-align: center" name="price"></td>
			    	<td style="text-align: center" name="sumPrice"></td>
					<td style="text-align: center" name="deliveryEnddate"></td>								
					<td style="text-align: center" name="predeliveryEnddate"></td>								
			    	<td style="text-align: center" name="resulte"></td>
			    	<td style="text-align: center" name="supplierCode"></td>
			    	<td style="text-align: center" name="resulteNote"></td>
			    	<td style="text-align: center" name="quoteformId"></td>
			    	<td style="text-align: center" name="quoteformCode"></td>
			    	<td style="text-align: center">
			    		<a href="javascript:void(0)" class="link del">删除</a>
			    		<a href="javascript:void(0)" class="link edit">编辑</a>
			    	</td>
			    	<input type="hidden" name="supplierId" value=""/>
			    	<input type="hidden" name="supplierName" value=""/>
			    	<input type="hidden" name="materielIndex" value=""/>
			    	<input type="hidden" name="materielId" value=""/>
			    	<input type="hidden" name="materielCode" value=""/>
			    	<input type="hidden" name="materielName" value=""/>
			    	<input type="hidden" name="materielLev" value=""/>
			    	<input type="hidden" name="attributeInfo" value=""/>
			    	<input type="hidden" name="unit" value=""/>
			    	<input type="hidden" name="orderNum" value=""/>
			    	<input type="hidden" name="price" value=""/>
			    	<input type="hidden" name="sumPrice" value=""/>
			    	<input type="hidden" name="deliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="predeliveryEnddate" value="" class="inputText date"/>
			    	<input type="hidden" name="resulte" value=""/>
			    	<input type="hidden" name="supplierCode" value=""/>
			    	<input type="hidden" name="resulteNote" value=""/>
			    	<input type="hidden" name="quoteformId" value=""/>
			    	<input type="hidden" name="quoteformCode" value=""/>
		 		</tr>
		    </table>
			<input type="hidden" name="id" value="${purOptimize.id}" />
			<input type="hidden" id="back" name="back" value=""/>
			<input type="hidden" name="formData" id="formData" />
			<input type="hidden" id="voteAgree" name="voteAgree" value="1"/> 
			<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		</form>
		
	</div>
	<form id="purOptimizeDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">供应商ID: </th>
				<td><input type="text" name="supplierId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">供应商: </th>
				<td><input type="text" name="supplierName" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">行号: </th>
				<td><input type="text" name="materielIndex" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品ID: </th>
				<td><input type="text" name="materielId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">物品编码: </th>
				<td><input type="text" name="materielCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">物品名称: </th>
				<td><input type="text" name="materielName" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
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
				<th width="20%">订单数量: </th>
				<td><input type="text" name="orderNum" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">单价: </th>
				<td><input type="text" name="price" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">总价: </th>
				<td><input type="text" name="sumPrice" value=""  class="inputText" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">要求发货截止日期: </th>
				<td><input type="text" name="deliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">预计发货截止日期: </th>
				<td><input type="text" name="predeliveryEnddate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">中标结果: </th>
				<td><input type="text" name="resulte" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">供应商编码: </th>
				<td><input type="text" name="supplierCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">结果说明: </th>
				<td><input type="text" name="resulteNote" value=""  class="inputText" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">来源报价单ID: </th>
				<td><input type="text" name="quoteformId" value=""  class="inputText" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">来源报价单Code: </th>
				<td><input type="text" name="quoteformCode" value=""  class="inputText" validate="{required:false,maxlength:96}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
