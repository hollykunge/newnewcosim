<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_pur_bidplan管理</title>
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
				<span class="tbar-label">采购招标公告管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="create.ht?viewmode=bidPlan1">添加</a></div>
					<div class="l-bar-separator"></div>
<!-- 					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div> 
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				-->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">招标编号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
<!--  					
						<span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">制单人姓名:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">来源单据编号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">发布日期 从:</span> <input  name="Q_beginreleasedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endreleasedate_DG" class="inputText date" />
						<span class="label">发布模式:</span><input type="text" name="Q_releaseModel_SL"  class="inputText" />
						<span class="label">招标截止日期 从:</span> <input  name="Q_beginbidEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endbidEndtime_DG" class="inputText date" />
						<span class="label">招标处理截止日期 从:</span> <input  name="Q_begindoobidEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddoobidEndtime_DG" class="inputText date" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">审状态核:</span><input type="text" name="Q_status_SL"  class="inputText" />
						<span class="label">采购方联系人ID:</span><input type="text" name="Q_purchcontactorId_SL"  class="inputText" />
						<span class="label">采购方联系人:</span><input type="text" name="Q_purchcontactorName_SL"  class="inputText" />
						<span class="label">采购方联系人电话:</span><input type="text" name="Q_purchcontactorPhone_SL"  class="inputText" />
						<span class="label">采购企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">采购企业:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">service_enterprise:</span><input type="text" name="Q_serviceEnterprise_SL"  class="inputText" />
						<span class="label">link:</span><input type="text" name="Q_link_SL"  class="inputText" />
						<span class="label">附件:</span><input type="text" name="Q_attachment_SL"  class="inputText" />
						<span class="label">保证金:</span><input type="text" name="Q_margins_SL"  class="inputText" />
-->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="purBidPlanList" id="purBidPlanItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${purBidPlanItem.id}">
				</display:column>
				<display:column property="code" title="招标编号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operatedate">
					<fmt:formatDate value="${purBidPlanItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				
				<display:column property="operaterId" title="制单人ID" sortable="true" sortName="operater_id"></display:column>
--%>
				<display:column property="operaterName" title="制单人姓名" sortable="true" sortName="operater_name"></display:column>
 <%--
 				<display:column property="sourceformCode" title="来源单据编号" sortable="true" sortName="sourceform_code"></display:column>
				<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="sourceform_id"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceform_type"></display:column>
--%>
				<display:column  title="发布日期" sortable="true" sortName="releasedate">
					<fmt:formatDate value="${purBidPlanItem.releasedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="releaseModel" title="发布模式" sortable="true" sortName="release_model"></display:column>
				<display:column  title="招标截止日期" sortable="true" sortName="bid_endtime">
					<fmt:formatDate value="${purBidPlanItem.bidEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="招标处理截止日期" sortable="true" sortName="doobid_endtime">
					<fmt:formatDate value="${purBidPlanItem.doobidEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
<%--				
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="freight_bearer"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="currency_type"></display:column>
				<display:column property="status" title="审核状态" sortable="true" sortName="status"></display:column>
			    <display:column property="statusNote" title="审核结果说明" sortable="true" sortName="statusNote"></display:column>
				<display:column property="purchcontactorId" title="采购方联系人ID" sortable="true" sortName="purchcontactor_id"></display:column>
				<display:column property="purchcontactorName" title="采购方联系人" sortable="true" sortName="purchcontactor_name"></display:column>
				<display:column property="purchcontactorPhone" title="采购方联系人电话" sortable="true" sortName="purchcontactor_phone"></display:column>
				<display:column property="enterpriseId" title="采购企业ID" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="enterpriseName" title="采购企业" sortable="true" sortName="enterprise_name"></display:column>
				<display:column property="serviceEnterprise" title="service_enterprise" sortable="true" sortName="service_enterprise" maxLength="80"></display:column>
				<display:column property="link" title="link" sortable="true" sortName="link" maxLength="80"></display:column>
				<display:column property="attachment" title="附件" sortable="true" sortName="attachment" maxLength="80"></display:column>
				<display:column property="margins" title="保证金" sortable="true" sortName="margins"></display:column>
				<display:column property="qualifications" title="资质要求说明（逗号分隔）" sortable="true" sortName="qualifications" maxLength="80"></display:column>
--%>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${purBidPlanItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${purBidPlanItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${purBidPlanItem.id}">
					</c:if>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${purBidPlanItem.runId}" class="link detail">流程示意图</a>					
					<a href="get.ht?id=${purBidPlanItem.id}&runid=${purBidPlanItem.runid}&status=${purBidPlanItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="purBidPlanItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


