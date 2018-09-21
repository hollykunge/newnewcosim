<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title> 发货通知单管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> 发货通知单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
<!-- 					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">出货通知单流水号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
				<!-- 	<span class="label">制单人姓名:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据编号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">采购企业名称:</span><input type="text" name="Q_purenterpName_SL"  class="inputText" />
						<span class="label">采购商联系人姓名:</span><input type="text" name="Q_purenterpUsername_SL"  class="inputText" />
						<span class="label">供应企业名称:</span><input type="text" name="Q_suppenterpName_SL"  class="inputText" />
						<span class="label">供应企业联系人姓名:</span><input type="text" name="Q_suppenterpUsername_SL"  class="inputText" />
						<span class="label">收货仓库名称:</span><input type="text" name="Q_receivewarehouseName_SL"  class="inputText" />
						<span class="label">收货仓库详细地址:</span><input type="text" name="Q_receivewarehouseAddress_SL"  class="inputText" />
						<span class="label">收货人姓名:</span><input type="text" name="Q_receiverName_SL"  class="inputText" />
						<span class="label">收货人手机号</span><input type="text" name="Q_receiverPhone_SL"  class="inputText" />
						<span class="label">收货人邮箱:</span><input type="text" name="Q_receiverMail_SL"  class="inputText" />
						<span class="label">收货地址邮编:</span><input type="text" name="Q_receiverPostcode_SL"  class="inputText" />
						<span class="label">发货仓库名称:</span><input type="text" name="Q_deliverwarehouseName_SL"  class="inputText" />
						<span class="label">发货仓库详细地址:</span><input type="text" name="Q_deliverwarehouseAddress_SL"  class="inputText" />
						<span class="label">发货人姓名:</span><input type="text" name="Q_delivererName_SL"  class="inputText" />
						<span class="label">发货人手机号:</span><input type="text" name="Q_delivererPhone_SL"  class="inputText" />
						<span class="label">发货人邮箱:</span><input type="text" name="Q_delivererMail_SL"  class="inputText" />
						<span class="label">发货人邮编:</span><input type="text" name="Q_delivererPostcode_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						 -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="delivernoteList" id="delivernoteItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${delivernoteItem.id}">
				</display:column>
				<display:column property="code" title="出货通知单流水号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${delivernoteItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operatorName" title="制单人姓名" sortable="true" sortName="operator_name"></display:column>
<%-- 
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceform_type"></display:column>
				<display:column property="sourceformCode" title="来源单据编号" sortable="true" sortName="sourceform_code"></display:column>
--%>
				<display:column property="purenterpName" title="采购企业名称" sortable="true" sortName="purenterp_name"></display:column>
<%-- 
				<display:column property="purenterpUsername" title="采购商联系人姓名" sortable="true" sortName="purenterp_username"></display:column>
				<display:column property="suppenterpName" title="供应企业名称" sortable="true" sortName="suppenterp_name"></display:column>
				<display:column property="suppenterpUsername" title="供应企业联系人姓名" sortable="true" sortName="suppenterp_username"></display:column>
--%>
				<display:column property="receivewarehouseName" title="收货仓库名称" sortable="true" sortName="receivewarehouse_name" maxLength="80"></display:column>
				<display:column property="receivewarehouseAddress" title="收货仓库详细地址" sortable="true" sortName="receivewarehouse_address" maxLength="80"></display:column>
<%-- 
				<display:column property="receiverName" title="收货人姓名" sortable="true" sortName="receiver_name"></display:column>
				<display:column property="receiverPhone" title="收货人手机号" sortable="true" sortName="receiver_phone"></display:column>
				<display:column property="receiverMail" title="收货人邮箱" sortable="true" sortName="receiver_mail"></display:column>
				<display:column property="receiverPostcode" title="收货地址邮编" sortable="true" sortName="receiver_postcode"></display:column>
--%>
				<display:column property="deliverwarehouseName" title="发货仓库名称" sortable="true" sortName="deliverwarehouse_name" maxLength="80"></display:column>
<%-- 
				<display:column property="deliverwarehouseAddress" title="发货仓库详细地址" sortable="true" sortName="deliverwarehouse_address" maxLength="80"></display:column>
				<display:column property="delivererName" title="发货人姓名" sortable="true" sortName="deliverer_name"></display:column>
				<display:column property="delivererPhone" title="发货人手机号" sortable="true" sortName="deliverer_phone"></display:column>
				<display:column property="delivererMail" title="发货人邮箱" sortable="true" sortName="deliverer_mail"></display:column>
				<display:column property="delivererPostcode" title="发货人邮编" sortable="true" sortName="deliverer_postcode"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="freight_bearer"></display:column>
--%>				
				<display:column title="管理" media="html" style="width:180px">
				<!--  
					<a href="del.ht?id=${delivernoteItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${delivernoteItem.id}" class="link edit">编辑</a>
				-->
					<a href="get.ht?id=${delivernoteItem.id}" class="link detail">明细</a>
				    <a href="${ctx}/cloud/logistics/logisticPlan/create.ht?delivernoteId=${delivernoteItem.id}" class="link edit">启动物流</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="delivernoteItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


