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
				$('#purOrderList').find(':radio:checked').each(function() {
					var $tr = $(this).parent().parent();
					var tds = $tr.find('td');
					for ( var i = 0; i < tds.length; i++) {
						if (i != 0) {
							var td = $(tds[i]).text();
							friend[i - 1] = td;
						}
					}

				});
				window.parent._callBackGetPurOrders(friend);
			});
	});
	
 
</script>
<title>选择采购订单</title>
</head>
<body>
<div class="panel-top">
		 
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				 
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="getPurOrders.ht">
				 
					<div class="row">
						<span class="label">单据号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
	<div class="panel-body">
		<display:table name="purOrderList" id="purOrderList"
			requestURI="getPurOrders.ht" sort="external" cellpadding="1" cellspacing="1"
			export="false" class="table-grid">
			<display:column title="${checkAll}" media="html" style="width:30px;">
				<input type="radio" class="pk" name="id" value="${purOrderList.id}">
			</display:column>
			 <display:column property="id" title="ID" sortable="true" sortName="id"></display:column>
			<display:column property="code" title="单据号" sortable="true" sortName="code"></display:column>
			<display:column property="sourceformCode" title="来源单据号" sortable="true" sortName="sourceform_code"></display:column>
			 
			<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${purOrderList.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
			<display:column property="state" title="状态" sortable="true" sortName="state"></display:column>
			 	<display:column title="管理" media="html" >
					 		<c:if test="${purOrderList.state=='分批入库'}">
				<a href="getinDetail.ht?id=${purOrderList.id}" class="link detail">明细</a>
 					 
				</c:if>
					
					
				</display:column>
		</display:table>
		<div class="l-clear"></div>
	</div>
	</div>
	<hotent:paging tableId="purOrderList" />
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
</body>
</html>
