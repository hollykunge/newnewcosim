<%--
	time:2013-04-19 11:40:23
	desc:edit the 维修工作单
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑维修工作单</title>
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
			var frm=$('#finStatementForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					form.submit();
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
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="${ctx}/cloud/finance/finStatement/get_m.ht?id=${finStatement.id}" target="_blank">查看维修单</a></div>
				 
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="finStatementForm" method="post" action="">	
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">单证号: </th>
					<td colspan="3"><input type="text" id="code" name="code" value="${finStatement.code}" size="30" readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
				 
					
				</tr>
				<tr>
					<th width="20%">制单日期: </th>
					<td><input type="text" id="operateDate" name="operateDate" value="<fmt:formatDate value='${finStatement.operateDate}' pattern='yyyy-MM-dd'/>" readonly="readonly" class="r"  /></td>
					<th width="20%">制单人: </th>
					<td><input type="text" id="operaterName" name="operaterName" value="${finStatement.operaterName}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>
					<!--<th width="20%">收款企业ID: </th>-->
					<input type="hidden" id="payeeenterpId" name="payeeenterpId" value="${finStatement.payeeenterpId}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款企业用户ID: </th>-->
					<input type="hidden" id="payenterpUserid" name="payenterpUserid" value="${finStatement.payenterpUserid}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<!--<th width="20%">付款企业ID: </th>-->
					<input type="hidden" id="payenterpId" name="payenterpId" value="${finStatement.payenterpId}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<!--<th width="20%">制单人ID: </th>-->
					<input type="hidden" id="operaterId" name="operaterId" value="${finStatement.operaterId}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<!--<th width="20%">来源单据ID: </th>-->
					<input type="hidden" id="sourceformId" name="sourceformId" value="${finStatement.sourceformId}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<!--<th width="20%">收款企业用户ID: </th>-->
					<input type="hidden" id="payeeenterpUserid" name="payeeenterpUserid" value="${finStatement.payeeenterpUserid}"  readonly="readonly" class="r" validate="{required:false,number:true }"  />
					<input type="hidden" id="remark1" name="remark1" value="0"  readonly="readonly" class="r"   />
				</tr>
				<tr>
					<th width="20%">来源单据类型: </th>
					<td><input type="text" id="sourceformType" name="sourceformType" value="采购入库" readonly="readonly" readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">来源单据号: </th>
					<td><input type="text" id="sourceformCode" name="sourceformCode" value="${finStatement.sourceformCode}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  />
						 </td>
				</tr>
				<tr>
					<th width="20%">总金额: </th>
					<td><input type="text" id="sumPrise" name="sumPrise" readonly value="${finStatement.sumPrise}"  readonly="readonly" class="r"   /></td>
					<th width="20%">币种: </th>
					<td><input type="text" id="currencyType" name="currencyType" value="${finStatement.currencyType}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  />
					
					</td>
				</tr>
				<tr>
					<th width="20%">付款企业名称: </th>
					<td><input type="text" id="payenterpName" name="payenterpName" value="${finStatement.payenterpName}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  />
					 </td>
					<th width="20%">付款企业用户名称: </th>
					<td><input type="text" id="payenterpUsername" name="payenterpUsername" value="${finStatement.payenterpUsername}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
				</tr>
				<tr>	
					<th width="20%">收款企业名称: </th>
					<td><input type="text" id="payeeenterpName" name="payeeenterpName" value="${finStatement.payeeenterpName}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
					<th width="20%">收款企业用户名称: </th>
					<td><input type="text" id="payeeenterpUsername" name="payeeenterpUsername" value="${finStatement.payeeenterpUsername}"  readonly="readonly" class="r" validate="{required:true,maxlength:96}"  /></td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1" id="finStatementDetail" formType="window" type="sub">
				<tr>
					<td colspan="23">
					
			    	
			    		<div align="center">
						结算单详细信息表
			    		</div>
		    		</td>
				</tr>
				<tr>
				 
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>单位</th>
					<th>单价</th>
					<th>数量</th>
					<th>总金额</th>
				</tr>
				<c:forEach items="${finStatementDetailList}" var="finStatementDetailItem" varStatus="status">
				    <tr type="subdata">
					    <td style="text-align: center" name="materielCode">${finStatementDetailItem.materielCode}</td>
					    <td style="text-align: center" name="materielName">${finStatementDetailItem.materielName}</td>
					    <td style="text-align: center" name="materielLev">${finStatementDetailItem.materielLev}</td>
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
		    </table>
			<input type="hidden" name="id" value="${finStatement.id}" />
		</form>
		
	</div>
	<form id="finStatementDetailForm" style="display:none">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th width="20%">code: </th>
				<td><input type="text" name="code" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">operate_date: </th>
				<td><input type="text" name="operateDate" value="" class="inputText date" validate="{date:true}"/></td>
			</tr>
			
			<tr>
				<th width="20%">operater_id: </th>
				<td><input type="text" name="operaterId" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">operater_name: </th>
				<td><input type="text" name="operaterName" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">order_id: </th>
				<td><input type="text" name="orderId" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">order_code: </th>
				<td><input type="text" name="orderCode" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">order_type: </th>
				<td><input type="text" name="orderType" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_id: </th>
				<td><input type="text" name="materielId" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_code: </th>
				<td><input type="text" name="materielCode" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_name: </th>
				<td><input type="text" name="materielName" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:768}"/></td>
			</tr>
			<tr>
				<th width="20%">materiel_lev: </th>
				<td><input type="text" name="materielLev" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">attribute_note: </th>
				<td><input type="text" name="attributeNote" value=""  readonly="readonly" class="r" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">order_num: </th>
				<td><input type="text" name="orderNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">currentstatement_num: </th>
				<td><input type="text" name="currentstatementNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">stated_num: </th>
				<td><input type="text" name="statedNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">normaltowarehourse_num: </th>
				<td><input type="text" name="normaltowarehourseNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">return_num: </th>
				<td><input type="text" name="returnNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">unstatement_num: </th>
				<td><input type="text" name="unstatementNum" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">unite: </th>
				<td><input type="text" name="unite" value=""  readonly="readonly" class="r" validate="{required:false,maxlength:96}"/></td>
			</tr>
			<tr>
				<th width="20%">price: </th>
				<td><input type="text" name="price" value=""  readonly="readonly" class="r" validate="{required:false}"/></td>
			</tr>
			<tr>
				<th width="20%">order_num2: </th>
				<td><input type="text" name="orderNum2" value=""  readonly="readonly" class="r" validate="{required:false,number:true }"/></td>
			</tr>
			<tr>
				<th width="20%">sum_prise: </th>
				<td><input type="text" name="sumPrise" value=""  readonly="readonly" class="r" validate="{required:false}"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
