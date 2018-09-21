<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物流计划单管理</title>
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
				<span class="tbar-label">物流计划单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="create.ht?">添加</a></div>
					<!-- 
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					 -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></br>
						<!-- <span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" /> -->
						<!-- <span class="label">制单人姓名:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" /> -->
<!-- 						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
 						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />-->
						<!-- <span class="label">预计发货日期 从:</span> <input  name="Q_beginforecastPostDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endforecastPostDate_DG" class="inputText date" />
						<span class="label">预计收货日期 从:</span> <input  name="Q_beginforecastReceiveDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endforecastReceiveDate_DG" class="inputText date" />
						<span class="label">物流公司ID :</span><input type="text" name="Q_logisticsEnterpId_SL"  class="inputText" />
						<span class="label">物流公司名称:</span><input type="text" name="Q_logisticsEnterpName_SL"  class="inputText" />
						<span class="label">重量:</span><input type="text" name="Q_weight_SL"  class="inputText" />
						<span class="label">运费:</span><input type="text" name="Q_shipPrise_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">起始发货企业ID:</span><input type="text" name="Q_startPostEnterpId_SL"  class="inputText" /> -->
						<span class="label">起始发货企业名称:</span><input type="text" name="Q_startPostEnterpName_SL"  class="inputText" />
						<!-- <span class="label">起始发货人ID:</span><input type="text" name="Q_startPosterId_SL"  class="inputText" /> -->
<!-- 						<span class="label">起始发货人名称:</span><input type="text" name="Q_startPosterName_SL"  class="inputText" />
 -->						<!-- <span class="label">起始发货人联系方式:</span><input type="text" name="Q_startPosterPhone_SL"  class="inputText" />
						<span class="label">起始发货地址:</span><input type="text" name="Q_startPostAddress_SL"  class="inputText" />
						<span class="label">起始发货仓库ID:</span><input type="text" name="Q_startPostWarehouseId_SL"  class="inputText" /> -->
						<!--<span class="label">起始发货仓库名称:</span><input type="text" name="Q_startPostWarehouseName_SL"  class="inputText" />
						 <span class="label">最终收货企业ID:</span><input type="text" name="Q_endReceiveEnterpId_SL"  class="inputText" /> -->
						<span class="label">最终收货企业名称 :</span><input type="text" name="Q_endReceiveEnterpName_SL"  class="inputText" />
						<!-- <span class="label">最终收货人ID:</span><input type="text" name="Q_endReceiverId_SL"  class="inputText" />
						<span class="label">最终收货人名称:</span><input type="text" name="Q_endReceiverName_SL"  class="inputText" />
						<span class="label">最终收货人联系方式:</span><input type="text" name="Q_endReceiverPhone_SL"  class="inputText" />
						<span class="label">最终收货地址:</span><input type="text" name="Q_endReceiveAddress_SL"  class="inputText" />
						<span class="label">最终收货仓库ID:</span><input type="text" name="Q_endReceiveWarehouseId_SL"  class="inputText" />
						<span class="label">最终收货仓库名称:</span><input type="text" name="Q_endReceiveWarehouseName_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="logisticPlanList" id="logisticPlanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${logisticPlanItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="CODE"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${logisticPlanItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%-- <display:column property="operaterId" title="制单人ID" sortable="true" sortName="OPERATER_ID"></display:column> --%>
				<display:column property="operaterName" title="制单人姓名" sortable="true" sortName="OPERATER_NAME"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="SOURCEFORM_TYPE"></display:column>
				<%-- <display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="SOURCEFORM_ID"></display:column>
				<display:column property="sourceformCode" title="来源单据号" sortable="true" sortName="SOURCEFORM_CODE"></display:column>
				<display:column  title="预计发货日期" sortable="true" sortName="FORECAST_POST_DATE">
					<fmt:formatDate value="${logisticPlanItem.forecastPostDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="预计收货日期" sortable="true" sortName="FORECAST_RECEIVE_DATE">
					<fmt:formatDate value="${logisticPlanItem.forecastReceiveDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="logisticsEnterpId" title="物流公司ID " sortable="true" sortName="LOGISTICS_ENTERP_ID"></display:column>
				<display:column property="logisticsEnterpName" title="物流公司名称" sortable="true" sortName="LOGISTICS_ENTERP_NAME"></display:column>
				<display:column property="weight" title="重量" sortable="true" sortName="WEIGHT"></display:column>
				<display:column property="shipPrise" title="运费" sortable="true" sortName="SHIP_PRISE"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="FREIGHT_BEARER"></display:column> --%>
				<display:column property="startPostEnterpId" title="起始发货企业ID" sortable="true" sortName="START_POST_ENTERP_ID"></display:column>
				<display:column property="startPostEnterpName" title="起始发货企业名称" sortable="true" sortName="START_POST_ENTERP_NAME"></display:column>
				<%-- <display:column property="startPosterId" title="起始发货人ID" sortable="true" sortName="START_POSTER_ID"></display:column>
				<display:column property="startPosterName" title="起始发货人名称" sortable="true" sortName="START_POSTER_NAME"></display:column>
				<display:column property="startPosterPhone" title="起始发货人联系方式" sortable="true" sortName="START_POSTER_PHONE"></display:column>
				<display:column property="startPostAddress" title="起始发货地址" sortable="true" sortName="START_POST_ADDRESS" maxLength="80"></display:column>
				<display:column property="startPostWarehouseId" title="起始发货仓库ID" sortable="true" sortName="START_POST_WAREHOUSE_ID"></display:column>
				<display:column property="startPostWarehouseName" title="起始发货仓库名称" sortable="true" sortName="START_POST_WAREHOUSE_NAME"></display:column> 
				<display:column property="endReceiveEnterpId" title="最终收货企业ID" sortable="true" sortName="END_RECEIVE_ENTERP_ID"></display:column>--%>
				<display:column property="endReceiveEnterpName" title="最终收货企业名称 " sortable="true" sortName="END_RECEIVE_ENTERP_NAME"></display:column>
				<%-- <display:column property="endReceiverId" title="最终收货人ID" sortable="true" sortName="END_RECEIVER_ID"></display:column>
				<display:column property="endReceiverName" title="最终收货人名称" sortable="true" sortName="END_RECEIVER_NAME"></display:column>
				<display:column property="endReceiverPhone" title="最终收货人联系方式" sortable="true" sortName="END_RECEIVER_PHONE"></display:column>
				<display:column property="endReceiveAddress" title="最终收货地址" sortable="true" sortName="END_RECEIVE_ADDRESS" maxLength="80"></display:column>
				<display:column property="endReceiveWarehouseId" title="最终收货仓库ID" sortable="true" sortName="END_RECEIVE_WAREHOUSE_ID"></display:column>
				<display:column property="endReceiveWarehouseName" title="最终收货仓库名称" sortable="true" sortName="END_RECEIVE_WAREHOUSE_NAME"></display:column> --%>
				<display:column title="管理" media="html" style="width:180px;white-space:nowrap;">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${logisticPlanItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${logisticPlanItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${logisticPlanItem.id}">
					</c:if>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${logisticPlanItem.runId}" class="link detail">流程示意图</a>	
					<a href="get.ht?id=${logisticPlanItem.id}&runid=${logisticPlanItem.runid}&status=${logisticPlanItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="logisticPlanItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


