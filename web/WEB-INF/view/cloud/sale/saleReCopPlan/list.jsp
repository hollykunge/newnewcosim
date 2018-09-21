<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>营销计划回复单管理</title>
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
				<span class="tbar-label">营销计划回复单管理列表</span>
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
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
<!--  						
						<span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
-->
						<span class="label">制单人:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
<!--
						<span class="label">sourceform_type:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">sourceform_id:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
-->
						<span class="label">来源单据Code:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">发布模式：公开/邀请:</span><input type="text" name="Q_deliveryType_SL"  class="inputText" />
<!--  						
						<span class="label">发布时间 从:</span> <input  name="Q_begindeliveryDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddeliveryDate_DG" class="inputText date" />
						<span class="label">回复截止时间 从:</span> <input  name="Q_beginofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endofferEndtime_DG" class="inputText date" />
						<span class="label">处理截止时间 从:</span> <input  name="Q_begindoofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddoofferEndtime_DG" class="inputText date" />
						<span class="label">合作营销区域:</span><input type="text" name="Q_cooperationArea_SL"  class="inputText" />
						<span class="label">合作开始时间 从:</span> <input  name="Q_begincooperationStarttime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcooperationStarttime_DG" class="inputText date" />
						<span class="label">合作截止时间 从:</span> <input  name="Q_begincooperationEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcooperationEndtime_DG" class="inputText date" />
						<span class="label">上报库存信息时间间隔:</span><input type="text" name="Q_reportstockTimespace_SL"  class="inputText" />
						<span class="label">上报销售订单时间间隔:</span><input type="text" name="Q_reportsalesTimespace_SL"  class="inputText" />
						<span class="label">说明:</span><input type="text" name="Q_comments_SL"  class="inputText" />
						<span class="label">企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">企业:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">企业联系人:</span><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" />
						<span class="label">enterprise_userid:</span><input type="text" name="Q_enterpriseUserid_SL"  class="inputText" />
						<span class="label">合作类型：代理/经销/联营:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">copenterprise_id:</span><input type="text" name="Q_copenterpriseId_SL"  class="inputText" />
						<span class="label">copenterprise_name:</span><input type="text" name="Q_copenterpriseName_SL"  class="inputText" />
						<span class="label">copenterprise_userid:</span><input type="text" name="Q_copenterpriseUserid_SL"  class="inputText" />
						<span class="label">合作企业联系人:</span><input type="text" name="Q_copenterpriseUsername_SL"  class="inputText" />
-->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="saleReCopPlanList" id="saleReCopPlanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${saleReCopPlanItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${saleReCopPlanItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operaterId" title="制单人ID" sortable="true" sortName="operater_id"></display:column>
				<display:column property="operaterName" title="制单人" sortable="true" sortName="operater_name"></display:column>
				<display:column property="sourceformType" title="sourceform_type" sortable="true" sortName="sourceform_type"></display:column>
				<display:column property="sourceformId" title="sourceform_id" sortable="true" sortName="sourceform_id"></display:column>
				<display:column property="sourceformCode" title="sourceform_code" sortable="true" sortName="sourceform_code"></display:column>
				<display:column property="deliveryType" title="发布模式：公开/邀请" sortable="true" sortName="delivery_type"></display:column>
				<display:column  title="发布时间" sortable="true" sortName="delivery_date">
					<fmt:formatDate value="${saleReCopPlanItem.deliveryDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="回复截止时间" sortable="true" sortName="offer_endtime">
					<fmt:formatDate value="${saleReCopPlanItem.offerEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="处理截止时间" sortable="true" sortName="dooffer_endtime">
					<fmt:formatDate value="${saleReCopPlanItem.doofferEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="cooperationArea" title="合作营销区域" sortable="true" sortName="cooperation_area"></display:column>
				<display:column  title="合作开始时间" sortable="true" sortName="cooperation_starttime">
					<fmt:formatDate value="${saleReCopPlanItem.cooperationStarttime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="合作截止时间" sortable="true" sortName="cooperation_endtime">
					<fmt:formatDate value="${saleReCopPlanItem.cooperationEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="reportstockTimespace" title="上报库存信息时间间隔" sortable="true" sortName="reportstock_timespace"></display:column>
				<display:column property="reportsalesTimespace" title="上报销售订单时间间隔" sortable="true" sortName="reportsales_timespace"></display:column>
				<display:column property="comments" title="说明" sortable="true" sortName="comments" maxLength="80"></display:column>
				<display:column property="enterpriseId" title="企业ID" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="enterpriseName" title="企业" sortable="true" sortName="enterprise_name"></display:column>
				<display:column property="enterpriseUsername" title="企业联系人" sortable="true" sortName="enterprise_username"></display:column>
				<display:column property="enterpriseUserid" title="enterprise_userid" sortable="true" sortName="enterprise_userid"></display:column>
				<display:column property="type" title="合作类型：代理/经销/联营" sortable="true" sortName="type"></display:column>
				<display:column property="copenterpriseId" title="copenterprise_id" sortable="true" sortName="copenterprise_id"></display:column>
				<display:column property="copenterpriseName" title="copenterprise_name" sortable="true" sortName="copenterprise_name"></display:column>
				<display:column property="copenterpriseUserid" title="copenterprise_userid" sortable="true" sortName="copenterprise_userid"></display:column>
				<display:column property="copenterpriseUsername" title="合作企业联系人" sortable="true" sortName="copenterprise_username"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${saleReCopPlanItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${saleReCopPlanItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${saleReCopPlanItem.id}">
					</c:if>
					<a href="get.ht?id=${saleReCopPlanItem.id}&runid=${saleReCopPlanItem.runid}&status=${saleReCopPlanItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="saleReCopPlanItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


