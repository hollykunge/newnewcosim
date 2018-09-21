<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_crowdsourcing_result管理</title>
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
				<span class="tbar-label">cloud_crowdsourcing_result管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="start_prepublish.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">表单CODE:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">提交人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">提交人名称:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">提交人企业ID:</span><input type="text" name="Q_operaterEnterpId_SL"  class="inputText" />
						<span class="label">提交人企业名称:</span><input type="text" name="Q_operaterEnterpName_SL"  class="inputText" />
						<span class="label">来源众包需求表ID:</span><input type="text" name="Q_sourceformCrowdsourcingId_SL"  class="inputText" />
						<span class="label">来源众包需求表CODE:</span><input type="text" name="Q_sourceformCrowdsourcingCode_SL"  class="inputText" />
						<span class="label">来源合同ID:</span><input type="text" name="Q_sourceformAgreementId_SL"  class="inputText" />
						<span class="label">来源合同CODE:</span><input type="text" name="Q_sourceformAgreementCode_SL"  class="inputText" />
						<span class="label">状态:</span><input type="text" name="Q_state_SL"  class="inputText" />
						<span class="label">审核类型:</span><input type="text" name="Q_auditType_SL"  class="inputText" />
						<span class="label">审核意见:</span><input type="text" name="Q_auditOpinion_SL"  class="inputText" />
						<span class="label">结果附件IDs:</span><input type="text" name="Q_resultAttachmentIds_SL"  class="inputText" />
						<span class="label">结果包名称:</span><input type="text" name="Q_resultName_SL"  class="inputText" />
						<span class="label">结果包说明:</span><input type="text" name="Q_resultInfo_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="crowdsourcingResultList" id="crowdsourcingResultItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${crowdsourcingResultItem.id}">
				</display:column>
				<display:column property="code" title="表单CODE" sortable="true" sortName="CODE"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${crowdsourcingResultItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operaterId" title="提交人ID" sortable="true" sortName="OPERATER_ID"></display:column>
				<display:column property="operaterName" title="提交人名称" sortable="true" sortName="OPERATER_NAME"></display:column>
				<display:column property="operaterEnterpId" title="提交人企业ID" sortable="true" sortName="OPERATER_ENTERP_ID"></display:column>
				<display:column property="operaterEnterpName" title="提交人企业名称" sortable="true" sortName="OPERATER_ENTERP_NAME"></display:column>
				<display:column property="sourceformCrowdsourcingId" title="来源众包需求表ID" sortable="true" sortName="SOURCEFORM_CROWDSOURCING_ID"></display:column>
				<display:column property="sourceformCrowdsourcingCode" title="来源众包需求表CODE" sortable="true" sortName="SOURCEFORM_CROWDSOURCING_CODE"></display:column>
				<display:column property="sourceformAgreementId" title="来源合同ID" sortable="true" sortName="SOURCEFORM_AGREEMENT_ID"></display:column>
				<display:column property="sourceformAgreementCode" title="来源合同CODE" sortable="true" sortName="SOURCEFORM_AGREEMENT_CODE"></display:column>
				<display:column property="state" title="状态" sortable="true" sortName="STATE"></display:column>
				<display:column property="auditType" title="审核类型" sortable="true" sortName="AUDIT_TYPE"></display:column>
				<display:column property="auditOpinion" title="审核意见" sortable="true" sortName="AUDIT_OPINION" maxLength="80"></display:column>
				<display:column property="resultAttachmentIds" title="结果附件IDs" sortable="true" sortName="RESULT_ATTACHMENT_IDS" maxLength="80"></display:column>
				<display:column property="resultName" title="结果包名称" sortable="true" sortName="RESULT_NAME" maxLength="80"></display:column>
				<display:column property="resultInfo" title="结果包说明" sortable="true" sortName="RESULT_INFO" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${crowdsourcingResultItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${crowdsourcingResultItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${crowdsourcingResultItem.id}">
					</c:if>
					<a href="get.ht?id=${crowdsourcingResultItem.id}&runid=${crowdsourcingResultItem.runid}&status=${crowdsourcingResultItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="crowdsourcingResultItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


