
<%--
	time:2013-04-23 09:39:31
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>返厂维修单明细</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript">
		var dd;
		function selUsers(){
			//弹出企业用户选择器
			var urlShow = '${ctx}/cloud/console/busiarea/listUsersByCompAndRole.ht?role=aftersaleman';
			dd = $.ligerDialog.open({ url:urlShow, height: 400,width: 600, title :'人员选择器', name:"frameDialog_"});
		}
		
		//商圈列表回调函数
		function _callBackUsers(users){
			var names='',ids='';
			for(var i=0; i<users.length; i++){
				var user = users[i];
				ids += ',' + user[0];
				names += ',' + user[1];
			}
			ids = ids!=''?ids.substring(1):"";
			names = names!=''?names.substring(1):"";
			
			$("#user_id").val(ids);
		    $("#user_name").val(names);
			dd.close();
		}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">返厂维修单详细信息</span>
			</div>
			
		</div>
		<div class="panel-body">
		<form id="returntaskForm" method="post" >
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			 
			<tr>
				<th style="width:120px">返厂维修单号:</th>
				<td colspan="3">${returntask.code}</td>
			</tr>
 
			<tr>
				<th style="width:120px">反馈用户Id:</th>
				<td>${returntask.feedbackid}</td>
				<th style="width:120px">维修厂商:</th>
				<td>${returntask.purenterid}</td>
			</tr>
            
            <tr>
				<th style="width:120px">送厂商:</th>
				<td>${returntask.currentpurenter}</td>
				<th style="width:120px">完成时间:</th>
				<td>
				<fmt:formatDate value="${returntask.enddata}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			
            <tr>
				<th style="width:120px">制单人:</th>
				<td>${returntask.operator}</td>
				<th style="width:120px">制单时间:</th>
				<td>
				<fmt:formatDate value="${returntask.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width:120px">返厂原因:</th>
				<td colspan="3">${returntask.reason}</td>
			</tr>
 
			<tr>
				<th style="width:120px">备注:</th>
				<td colspan="3">${returntask.remark}</td>
			</tr>
			<tr>
				<th style="width:120px">维修人员</th>
				<td colspan="3">
				<input type="hidden" id="user_id" name="accendant_id">
				<input style="width:120px" type="text" id="user_name" name="accendant_name" readonly="readonly" validate="{required:true,maxlength:96}">
				<a href="javascript:void(0)" onclick="selUsers()" class="link detail">请选择</a>
				</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">返厂维修单明细</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>规格型号</th>
				<th>计量单位</th>
				<th>参考价格</th>
			</tr>	
			<c:forEach items="${returntaskDetailList}" var="returntaskDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${returntaskDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${returntaskDetailItem.code}</td>								
						<td style="text-align: center">${returntaskDetailItem.prodcdoe}</td>								
						<td style="text-align: center">${returntaskDetailItem.prodname}</td>								
						<td style="text-align: center">${returntaskDetailItem.model}</td>								
						<td style="text-align: center">${returntaskDetailItem.unit}</td>								
						<td style="text-align: center">${returntaskDetailItem.price}</td>								
				</tr>
			</c:forEach>
		</table>
		</form>
		</div>
		
	</div>
</body>
</html>

