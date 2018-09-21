<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>CLOUD_LOGISTICS_DELIVER管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_LOGISTICS_DELIVER管理列表</span>
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
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">制单人名称:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">配送企业ID:</span><input type="text" name="Q_deliverEnterpId_SL"  class="inputText" />
						<span class="label">配送企业名称:</span><input type="text" name="Q_deliverEnterpName_SL"  class="inputText" />
						<span class="label">配送人员ID:</span><input type="text" name="Q_delivererId_SL"  class="inputText" />
						<span class="label">配送人员名称:</span><input type="text" name="Q_delivererName_SL"  class="inputText" />
						<span class="label">配送时间 从:</span> <input  name="Q_begindeliverTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddeliverTime_DG" class="inputText date" />
						<span class="label">发货企业ID:</span><input type="text" name="Q_postEnterpId_SL"  class="inputText" />
						<span class="label">发货企业名称:</span><input type="text" name="Q_postEnterpName_SL"  class="inputText" />
						<span class="label">发货企业联系人ID:</span><input type="text" name="Q_posterId_SL"  class="inputText" />
						<span class="label">发货企业联系人名称:</span><input type="text" name="Q_posterName_SL"  class="inputText" />
						<span class="label">发货企业联系方式:</span><input type="text" name="Q_posterPhone_SL"  class="inputText" />
						<span class="label">发货地址:</span><input type="text" name="Q_postAddress_SL"  class="inputText" />
						<span class="label">收货企业ID:</span><input type="text" name="Q_receiveEnterpId_SL"  class="inputText" />
						<span class="label">收货企业名称:</span><input type="text" name="Q_receiveEnterpName_SL"  class="inputText" />
						<span class="label">收货企业联系人ID:</span><input type="text" name="Q_receiverId_SL"  class="inputText" />
						<span class="label">收货企业联系人名称:</span><input type="text" name="Q_receiverName_SL"  class="inputText" />
						<span class="label">收货企业联系方式:</span><input type="text" name="Q_receiverPhone_SL"  class="inputText" />
						<span class="label">收货地址:</span><input type="text" name="Q_receiveAddress_SL"  class="inputText" />
						<span class="label">计划详单ID:</span><input type="text" name="Q_planDetailId_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="logisticsDeliverList" id="logisticsDeliverItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${logisticsDeliverItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="CODE"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${logisticsDeliverItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operaterId" title="制单人ID" sortable="true" sortName="OPERATER_ID"></display:column>
				<display:column property="operaterName" title="制单人名称" sortable="true" sortName="OPERATER_NAME"></display:column>
				<display:column property="deliverEnterpId" title="配送企业ID" sortable="true" sortName="DELIVER_ENTERP_ID"></display:column>
				<display:column property="deliverEnterpName" title="配送企业名称" sortable="true" sortName="DELIVER_ENTERP_NAME"></display:column>
				<display:column property="delivererId" title="配送人员ID" sortable="true" sortName="DELIVERER_ID"></display:column>
				<display:column property="delivererName" title="配送人员名称" sortable="true" sortName="DELIVERER_NAME"></display:column>
				<display:column  title="配送时间" sortable="true" sortName="DELIVER_TIME">
					<fmt:formatDate value="${logisticsDeliverItem.deliverTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="postEnterpId" title="发货企业ID" sortable="true" sortName="POST_ENTERP_ID"></display:column>
				<display:column property="postEnterpName" title="发货企业名称" sortable="true" sortName="POST_ENTERP_NAME"></display:column>
				<display:column property="posterId" title="发货企业联系人ID" sortable="true" sortName="POSTER_ID"></display:column>
				<display:column property="posterName" title="发货企业联系人名称" sortable="true" sortName="POSTER_NAME"></display:column>
				<display:column property="posterPhone" title="发货企业联系方式" sortable="true" sortName="POSTER_PHONE"></display:column>
				<display:column property="postAddress" title="发货地址" sortable="true" sortName="POST_ADDRESS"></display:column>
				<display:column property="receiveEnterpId" title="收货企业ID" sortable="true" sortName="RECEIVE_ENTERP_ID"></display:column>
				<display:column property="receiveEnterpName" title="收货企业名称" sortable="true" sortName="RECEIVE_ENTERP_NAME"></display:column>
				<display:column property="receiverId" title="收货企业联系人ID" sortable="true" sortName="RECEIVER_ID"></display:column>
				<display:column property="receiverName" title="收货企业联系人名称" sortable="true" sortName="RECEIVER_NAME"></display:column>
				<display:column property="receiverPhone" title="收货企业联系方式" sortable="true" sortName="RECEIVER_PHONE"></display:column>
				<display:column property="receiveAddress" title="收货地址" sortable="true" sortName="RECEIVE_ADDRESS"></display:column>
				<display:column property="planDetailId" title="计划详单ID" sortable="true" sortName="PLAN_DETAIL_ID"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${logisticsDeliverItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${logisticsDeliverItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${logisticsDeliverItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="logisticsDeliverItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


