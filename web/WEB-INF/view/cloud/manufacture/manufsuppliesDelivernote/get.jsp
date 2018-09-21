
<%--
	time:2013-05-11 14:16:40
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>委外加工物料发货单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工物料发货单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">supplies_id:</th>
				<td>${manufsuppliesDelivernote.suppliesId}</td>
			</tr>
 
			<tr>
				<th width="20%">code:</th>
				<td>${manufsuppliesDelivernote.code}</td>
			</tr>
 
			<tr>
				<th width="20%">operate_date:</th>
				<td>
				<fmt:formatDate value="${manufsuppliesDelivernote.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">operator_id:</th>
				<td>${manufsuppliesDelivernote.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">operator_name:</th>
				<td>${manufsuppliesDelivernote.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_type:</th>
				<td>${manufsuppliesDelivernote.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_id:</th>
				<td>${manufsuppliesDelivernote.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_code:</th>
				<td>${manufsuppliesDelivernote.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">recenterp_id:</th>
				<td>${manufsuppliesDelivernote.recenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">recenterp_name:</th>
				<td>${manufsuppliesDelivernote.recenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">recenterp_userid:</th>
				<td>${manufsuppliesDelivernote.recenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">recenterp_username:</th>
				<td>${manufsuppliesDelivernote.recenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">suppenterp_id:</th>
				<td>${manufsuppliesDelivernote.suppenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">suppenterp_name:</th>
				<td>${manufsuppliesDelivernote.suppenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">suppenterp_userid:</th>
				<td>${manufsuppliesDelivernote.suppenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">suppenterp_username:</th>
				<td>${manufsuppliesDelivernote.suppenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">receivewarehouse_id:</th>
				<td>${manufsuppliesDelivernote.receivewarehouseId}</td>
			</tr>
 
			<tr>
				<th width="20%">receivewarehouse_name:</th>
				<td>${manufsuppliesDelivernote.receivewarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">receivewarehouse_address:</th>
				<td>${manufsuppliesDelivernote.receivewarehouseAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_id:</th>
				<td>${manufsuppliesDelivernote.receiverId}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_name:</th>
				<td>${manufsuppliesDelivernote.receiverName}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_phone:</th>
				<td>${manufsuppliesDelivernote.receiverPhone}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_mail:</th>
				<td>${manufsuppliesDelivernote.receiverMail}</td>
			</tr>
 
			<tr>
				<th width="20%">receiver_postcode:</th>
				<td>${manufsuppliesDelivernote.receiverPostcode}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverwarehouse_id:</th>
				<td>${manufsuppliesDelivernote.deliverwarehouseId}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverwarehouse_name:</th>
				<td>${manufsuppliesDelivernote.deliverwarehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverwarehouse_address:</th>
				<td>${manufsuppliesDelivernote.deliverwarehouseAddress}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverer_id:</th>
				<td>${manufsuppliesDelivernote.delivererId}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverer_name:</th>
				<td>${manufsuppliesDelivernote.delivererName}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverer_phone:</th>
				<td>${manufsuppliesDelivernote.delivererPhone}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverer_mail:</th>
				<td>${manufsuppliesDelivernote.delivererMail}</td>
			</tr>
 
			<tr>
				<th width="20%">deliverer_postcode:</th>
				<td>${manufsuppliesDelivernote.delivererPostcode}</td>
			</tr>
 
			<tr>
				<th width="20%">freight_bearer:</th>
				<td>${manufsuppliesDelivernote.freightBearer}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

