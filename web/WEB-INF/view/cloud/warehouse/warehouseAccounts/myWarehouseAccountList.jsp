<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#ok').click(function(){
			var friends = [];
			var j = 0;
			var arr_check = document.getElementById("warehouseAccountsList")
					.getElementsByTagName("tr");
			 		
					
			for ( var k = 1; k < arr_check.length; k++) {
				 
				 
				var tds = $(arr_check[k]).find('td');
				
				var friend = [];
				for(var i=0; i<tds.length; i++){
				 				
					if(i!=0){						
						var td = $(tds[i]).text();						
						friend[i-1] = td;
						 
					}
				}
				
				friends[j++] = friend;
			}
			 
			window.parent._callBackMyFriends3(friends);			
		})
	})
	
 
</script>
<title>选择物品</title>
</head>
<body>
 <div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">库存查询管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="myWarehouseAccountList.ht">
					<div class="row">
						<span class="label">物品编号:</span><input type="text" name="Q_productcode_SL"  class="inputText" />
						<span class="label">物品名称:</span><input type="text" name="Q_productname_SL"  class="inputText" />
						<input type="hidden" name="warehouseid"  class="inputText" value="${warehouseid}"/>
					</div>
					 
				</form>
			</div>
		</div>
	<div class="panel-body">
	<c:set var="checkAll">
			<input type="checkbox" id="chkall" />
		</c:set>
		<display:table name="warehouseAccountsList" id="warehouseAccountsList"
			requestURI="myWarehouseAccountList.ht" sort="external" cellpadding="1" cellspacing="1"
			export="false" class="table-grid">
			  <display:column title="${checkAll}" media="html" style="width:30px;">
				<input type="checkbox" class="pk" name="id"
					value="${warehouseAccountsList.id}">
			</display:column>			
			<display:column property="productname" title="物品名称" sortable="true" sortName="productname"></display:column>
			<display:column property="productcode" title="物品代码" sortable="true" sortName="code"></display:column>
			<display:column property="units" title="单位" sortable="true" sortName="units"></display:column>
			<display:column property="nums" title="数量" sortable="true" sortName="nums"></display:column>
			<display:column property="warehousename" title="仓库名称" sortable="true" sortName="warehousename"></display:column>
				 
				 
		</display:table>
		<div class="l-clear"></div>
	</div>
	</div>
		<hotent:paging tableId="warehouseAccountsItem" />
	</div>
	<div class="l-dialog-buttons" style="">
			<div class="l-dialog-buttons-inner">
			<div class="l-dialog-btn">
				<div class="l-dialog-btn-l"></div>
				<div class="l-dialog-btn-r"></div>
				<div class="l-dialog-btn-inner" id="ok">确定</div>
			</div></div>
		</div>
</body>
</html>
