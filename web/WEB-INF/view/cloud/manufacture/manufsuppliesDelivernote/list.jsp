<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>委外加工物料发货单管理</title>
<%@include file="/commons/include/get.jsp" %>

<script type="text/javascript">
	$(function() {
		$("a.apply").click(function() {
			$.ajax({
				type : "POST",
				url : 'apply.ht?id='+$('#userVacateId').val(),
				success : function(res) {
					var result = eval('('+res+')');
					$.ligerMessageBox.success('提示信息',result.message);
					window.location.href = "list.ht";
				},
				error : function(res) {
					$.ligerMessageBox.error('提示信息',result.message);
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工物料发货单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">supplies_id:</span><input type="text" name="Q_suppliesId_SL"  class="inputText" />
						<span class="label">code:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">operate_date 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">operator_id:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
						<span class="label">operator_name:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">sourceform_type:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">sourceform_id:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">sourceform_code:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">recenterp_id:</span><input type="text" name="Q_recenterpId_SL"  class="inputText" />
						<span class="label">recenterp_name:</span><input type="text" name="Q_recenterpName_SL"  class="inputText" />
						<span class="label">recenterp_userid:</span><input type="text" name="Q_recenterpUserid_SL"  class="inputText" />
						<span class="label">recenterp_username:</span><input type="text" name="Q_recenterpUsername_SL"  class="inputText" />
						<span class="label">suppenterp_id:</span><input type="text" name="Q_suppenterpId_SL"  class="inputText" />
						<span class="label">suppenterp_name:</span><input type="text" name="Q_suppenterpName_SL"  class="inputText" />
						<span class="label">suppenterp_userid:</span><input type="text" name="Q_suppenterpUserid_SL"  class="inputText" />
						<span class="label">suppenterp_username:</span><input type="text" name="Q_suppenterpUsername_SL"  class="inputText" />
						<span class="label">receivewarehouse_id:</span><input type="text" name="Q_receivewarehouseId_SL"  class="inputText" />
						<span class="label">receivewarehouse_name:</span><input type="text" name="Q_receivewarehouseName_SL"  class="inputText" />
						<span class="label">receivewarehouse_address:</span><input type="text" name="Q_receivewarehouseAddress_SL"  class="inputText" />
						<span class="label">receiver_id:</span><input type="text" name="Q_receiverId_SL"  class="inputText" />
						<span class="label">receiver_name:</span><input type="text" name="Q_receiverName_SL"  class="inputText" />
						<span class="label">receiver_phone:</span><input type="text" name="Q_receiverPhone_SL"  class="inputText" />
						<span class="label">receiver_mail:</span><input type="text" name="Q_receiverMail_SL"  class="inputText" />
						<span class="label">receiver_postcode:</span><input type="text" name="Q_receiverPostcode_SL"  class="inputText" />
						<span class="label">deliverwarehouse_id:</span><input type="text" name="Q_deliverwarehouseId_SL"  class="inputText" />
						<span class="label">deliverwarehouse_name:</span><input type="text" name="Q_deliverwarehouseName_SL"  class="inputText" />
						<span class="label">deliverwarehouse_address:</span><input type="text" name="Q_deliverwarehouseAddress_SL"  class="inputText" />
						<span class="label">deliverer_id:</span><input type="text" name="Q_delivererId_SL"  class="inputText" />
						<span class="label">deliverer_name:</span><input type="text" name="Q_delivererName_SL"  class="inputText" />
						<span class="label">deliverer_phone:</span><input type="text" name="Q_delivererPhone_SL"  class="inputText" />
						<span class="label">deliverer_mail:</span><input type="text" name="Q_delivererMail_SL"  class="inputText" />
						<span class="label">deliverer_postcode:</span><input type="text" name="Q_delivererPostcode_SL"  class="inputText" />
						<span class="label">freight_bearer:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="manufsuppliesDelivernoteList" id="manufsuppliesDelivernoteItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${manufsuppliesDelivernoteItem.id}">
				</display:column>
				<display:column property="suppliesId" title="supplies_id" sortable="true" sortName="supplies_id"></display:column>
				<display:column property="code" title="code" sortable="true" sortName="code"></display:column>
				<display:column  title="operate_date" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${manufsuppliesDelivernoteItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operatorId" title="operator_id" sortable="true" sortName="operator_id"></display:column>
				<display:column property="operatorName" title="operator_name" sortable="true" sortName="operator_name"></display:column>
				<display:column property="sourceformType" title="sourceform_type" sortable="true" sortName="sourceform_type"></display:column>
				<display:column property="sourceformId" title="sourceform_id" sortable="true" sortName="sourceform_id"></display:column>
				<display:column property="sourceformCode" title="sourceform_code" sortable="true" sortName="sourceform_code"></display:column>
				<display:column property="recenterpId" title="recenterp_id" sortable="true" sortName="recenterp_id"></display:column>
				<display:column property="recenterpName" title="recenterp_name" sortable="true" sortName="recenterp_name"></display:column>
				<display:column property="recenterpUserid" title="recenterp_userid" sortable="true" sortName="recenterp_userid"></display:column>
				<display:column property="recenterpUsername" title="recenterp_username" sortable="true" sortName="recenterp_username"></display:column>
				<display:column property="suppenterpId" title="suppenterp_id" sortable="true" sortName="suppenterp_id"></display:column>
				<display:column property="suppenterpName" title="suppenterp_name" sortable="true" sortName="suppenterp_name"></display:column>
				<display:column property="suppenterpUserid" title="suppenterp_userid" sortable="true" sortName="suppenterp_userid"></display:column>
				<display:column property="suppenterpUsername" title="suppenterp_username" sortable="true" sortName="suppenterp_username"></display:column>
				<display:column property="receivewarehouseId" title="receivewarehouse_id" sortable="true" sortName="receivewarehouse_id"></display:column>
				<display:column property="receivewarehouseName" title="receivewarehouse_name" sortable="true" sortName="receivewarehouse_name" maxLength="80"></display:column>
				<display:column property="receivewarehouseAddress" title="receivewarehouse_address" sortable="true" sortName="receivewarehouse_address" maxLength="80"></display:column>
				<display:column property="receiverId" title="receiver_id" sortable="true" sortName="receiver_id"></display:column>
				<display:column property="receiverName" title="receiver_name" sortable="true" sortName="receiver_name"></display:column>
				<display:column property="receiverPhone" title="receiver_phone" sortable="true" sortName="receiver_phone"></display:column>
				<display:column property="receiverMail" title="receiver_mail" sortable="true" sortName="receiver_mail"></display:column>
				<display:column property="receiverPostcode" title="receiver_postcode" sortable="true" sortName="receiver_postcode"></display:column>
				<display:column property="deliverwarehouseId" title="deliverwarehouse_id" sortable="true" sortName="deliverwarehouse_id"></display:column>
				<display:column property="deliverwarehouseName" title="deliverwarehouse_name" sortable="true" sortName="deliverwarehouse_name" maxLength="80"></display:column>
				<display:column property="deliverwarehouseAddress" title="deliverwarehouse_address" sortable="true" sortName="deliverwarehouse_address" maxLength="80"></display:column>
				<display:column property="delivererId" title="deliverer_id" sortable="true" sortName="deliverer_id"></display:column>
				<display:column property="delivererName" title="deliverer_name" sortable="true" sortName="deliverer_name"></display:column>
				<display:column property="delivererPhone" title="deliverer_phone" sortable="true" sortName="deliverer_phone"></display:column>
				<display:column property="delivererMail" title="deliverer_mail" sortable="true" sortName="deliverer_mail"></display:column>
				<display:column property="delivererPostcode" title="deliverer_postcode" sortable="true" sortName="deliverer_postcode"></display:column>
				<display:column property="freightBearer" title="freight_bearer" sortable="true" sortName="freight_bearer"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${manufsuppliesDelivernoteItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${manufsuppliesDelivernoteItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${manufsuppliesDelivernoteItem.id}">
					</c:if>
					<a href="get.ht?id=${manufsuppliesDelivernoteItem.id}&runid=${manufsuppliesDelivernoteItem.runid}&status=${manufsuppliesDelivernoteItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="manufsuppliesDelivernoteItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


