<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购订单管理</title>
<%@include file="/commons/include/get.jsp" %>
<script>
	function returnID(id) {
		window.opener._callBackCopyList(id);
		window.close();
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购订单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">采购订单编号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">供应商企业:</span><input type="text" name="Q_suppenterpName_SL"  class="inputText" />
						<br/>
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">流程状态:</span>
						<select name="Q_state_SE" class="inputText" onChange="this.form.submit();">
							<option value="">==所有状态==</option>									
							<option >1-下采购订单</option> 
							<option >2-领导审签 </option>
							<option >3-确认，下销售订单 </option>
							<option >4-发货通知</option>
							 
							<option value="待收货">5-待收货</option>
									<option value="分批入库">6-分批入库</option>
									<option value="全部入库">7-全部入库</option>
									<option value="已结算">8-已结算</option>
									<option value="已支付">9-已支付</option>
						</select>
						<!-- <span class="label">制单人姓名:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据编号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">预付款:</span><input type="text" name="Q_advancePay_SL"  class="inputText" />
						<span class="label">预付款说明:</span><input type="text" name="Q_advancepayNotes_SL"  class="inputText" />
						<span class="label">总金额:</span><input type="text" name="Q_sumPrice_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">采购企业名称:</span><input type="text" name="Q_purenterpName_SL"  class="inputText" />
						<span class="label">采购商联系人姓名:</span><input type="text" name="Q_purenterpUsername_SL"  class="inputText" />
						<span class="label">供应企业名称:</span><input type="text" name="Q_suppenterpName_SL"  class="inputText" />
						<span class="label">供应商联系人姓名:</span><input type="text" name="Q_suppenterpUsername_SL"  class="inputText" />
						<span class="label">收货仓库名称:</span><input type="text" name="Q_receivewarehouseName_SL"  class="inputText" />
						<span class="label">收货仓库详细地址:</span><input type="text" name="Q_receivewarehouseAddress_SL"  class="inputText" />
						<span class="label">收货人姓名:</span><input type="text" name="Q_receiverName_SL"  class="inputText" />
						<span class="label">收货人手机号:</span><input type="text" name="Q_receiverPhone_SL"  class="inputText" />
						<span class="label">收货人邮箱:</span><input type="text" name="Q_receiverMail_SL"  class="inputText" />
						<span class="label">收货地址邮编:</span><input type="text" name="Q_postcode_SL"  class="inputText" />
						 -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="purOrderList" id="purOrderItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${purOrderItem.id}">
				</display:column>
				<display:column property="code" title="采购订单编号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期 " sortable="true" sortName="operate_date">
					<fmt:formatDate value="${purOrderItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operatorName" title="制单人姓名" sortable="true" sortName="operator_name"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceform_type"></display:column>
				<display:column property="sourceformCode" title="来源单据编号" sortable="true" sortName="sourceform_code"></display:column>
<%-- 				<display:column property="advancePay" title="预付款" sortable="true" sortName="advance_pay"></display:column>
				<display:column property="advancepayNotes" title="预付款说明" sortable="true" sortName="advancepay_notes" maxLength="80"></display:column>
				<display:column property="sumPrice" title="总金额" sortable="true" sortName="sum_price"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="freight_bearer"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="currency_type"></display:column> --%>
<%-- 				<display:column property="purenterpName" title="采购企业名称" sortable="true" sortName="purenterp_name"></display:column> --%>
<%-- 				<display:column property="purenterpUsername" title="采购商联系人姓名" sortable="true" sortName="purenterp_username"></display:column>
--%>
				<display:column property="suppenterpName" title="供应企业名称" sortable="true" sortName="suppenterp_name"></display:column>
				<display:column property="state" title="流程状态" sortable="true" sortName="state"></display:column>
<%--				<display:column property="suppenterpUsername" title="供应商联系人姓名" sortable="true" sortName="suppenterp_username"></display:column>
				<display:column property="receivewarehouseName" title="收货仓库名称" sortable="true" sortName="receivewarehouse_name" maxLength="80"></display:column>
				<display:column property="receivewarehouseAddress" title="收货仓库详细地址" sortable="true" sortName="receivewarehouse_address" maxLength="80"></display:column>
				<display:column property="receiverName" title="收货人姓名" sortable="true" sortName="receiver_name"></display:column>
				<display:column property="receiverPhone" title="收货人手机号" sortable="true" sortName="receiver_phone"></display:column>
				<display:column property="receiverMail" title="收货人邮箱" sortable="true" sortName="receiver_mail"></display:column>
				<display:column property="postcode" title="收货地址邮编" sortable="true" sortName="postcode"></display:column> --%>
				<display:column title="管理" media="html" style="width:160px">
					<a href="get.ht?id=${purOrderItem.id}" class="link detail">明细</a>
					<a href="javascript:void(0)" onclick="returnID(${purOrderItem.id});" class="link save">确定</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="purOrderItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


