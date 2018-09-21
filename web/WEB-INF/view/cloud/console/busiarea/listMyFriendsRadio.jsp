<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#ok').click(function() {
				var friend = [];
				$('#myFriendsList').find(':radio:checked').each(function() {
					var $tr = $(this).parent().parent();
					var tds = $tr.find('td');
					for ( var i = 0; i < tds.length; i++) {
						if (i != 0) {
							var td = $(tds[i]).text();
							friend[i - 1] = td;
						}
					}

				});
				window.parent._callBackMyFriends(friend);
			});
	})
	
	function _callBackMyHistory(id,suppenterpId,suppenterpName,suppenterpUsername){
		window.parent._callBackMyHistory(id,suppenterpId,suppenterpName,suppenterpUsername);
	}
			
	function getAll(id){
		window.open ('${ctx}/cloud/purchase/purOrder/list_all.ht?suppenterpId='+id,'采购记录','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	}
</script>
<title>选择供应商</title>
</head>
<body>
<div class="panel">	
	<div class="panel-top">
	        <div class="tbar-title">
				<span class="tbar-label">商友管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
				</div>	
			</div>
	</div>
	<div class="panel-search">
				<form id="searchForm" method="post" action="${ctx}/cloud/console/busiarea/listMyFriendsRadio.ht">
					<div class="row">
						<span class="label">企业名称:</span><input type="text" name="corpEntName"  class="inputText" />
					</div>
				</form>
			</div>
	<div class="panel-body">
		<display:table name="myFriendsList" id="myFriendsList"
			requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"
			export="true" class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<input type="radio" class="pk" name="id"
					value="${purEnquiryItem.corpEnt}">
			</display:column>
			<display:column property="corpEnterprise.sysOrgInfoId" title="ID" >
			</display:column>
			<display:column property="corpEnterprise.name" title="企业名称"
				sortable="true" sortName="name"></display:column>
			<display:column property="corpEnterprise.tel" title="电话"
				sortable="true" sortName="tel"></display:column>
			<display:column property="corpEnterprise.address" title="地址"
				sortable="true" sortName="address"></display:column>
			<display:column property="corpEnterprise.connecter" title="联系人"
				sortable="true" sortName="address"></display:column>
			<display:column  title="采购记录" sortable="true" sortName="" >
			<input value="" type="hidden" />
			<a href="javascript:void(0)" onclick="getAll('${myFriendsList.corpEnterprise.sysOrgInfoId}');">查看</a></display:column>
		</display:table>
		<div class="l-clear"></div>
	</div>
	</div>
	<hotent:paging tableId="myFriendsList" />
	</div>
	<div class="l-dialog-buttons" style="">
		<div class="l-dialog-buttons-inner">
			<div class="l-dialog-btn">
				<div class="l-dialog-btn-l"></div>
				<div class="l-dialog-btn-r"></div>
				<div class="l-dialog-btn-inner" id="ok">确定</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
