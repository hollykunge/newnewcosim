
<%--
	time:2013-04-17 15:21:31
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>维修单明细</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/IconDialog.js"></script>
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
				<span class="tbar-label">维修单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link close" href="javascript:window.close();">关闭</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<form id="taskMForm" method="post" >
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
			 <input type="hidden" name="id" value="${taskM.id }" />
			<tr>
				<th style="width:120px">任务单号:</th>
				<td colspan="3">${taskM.code}</td>
			</tr>
 
			<tr>
				<th style="width:120px">反馈用户:</th>
				<td>${taskM.feedback_name}</td>
				<th style="width:120px">产品编码:</th>
				<td>${taskM.prodcode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">产品名称:</th>
				<td>${taskM.prodname}</td>
				<th style="width:120px">产品型号:</th>
				<td>${taskM.prodmodel}</td>
			</tr>
 
			<tr>
				<th style="width:120px">问题描述:</th>
				<td colspan="3">${taskM.descn}</td>
			</tr>
 
			<tr>
				<th style="width:120px">维修厂商:</th>
				<td >${taskM.purenterp_id}</td>
				<th style="width:120px">维修人员:</th>
				<td >
				<input type="hidden" id="user_id" name="accendant_id">
				<input style="width:120px" type="text" id="user_name" name="accendant_name" readonly="readonly" validate="{required:true,maxlength:96}">
				<a href="javascript:void(0)" onclick="selUsers()" class="link detail">请选择</a>
				</td>
			</tr>
 
			<tr>
				<th style="width:120px">备注:</th>
				<td colspan="3">${taskM.remark}</td>
			</tr>
		</table>
		</form>
		</div>
		
	</div>
</body>
</html>

