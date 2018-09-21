<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>委外加工报价单管理</title>
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
				<span class="tbar-label">委外加工报价单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- 
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
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
					<table>
						<tr>
							<td>编码:</td>
							<td><input type="text" name="Q_code_SL"  class="inputText" /></td>
							<td>制单人:</td>
							<td><input type="text" name="Q_operatorName_SL"  class="inputText" /></td>
							<td>企业:</td>
							<td><input type="text" name="Q_enterpriseName_SL"  class="inputText" /></td>
							<td>企业联系人:</td>
							<td><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" /></td>
						</tr>
						<tr>
							<td>制单日期:</td>
							<td colspan=3><input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></td>
						</tr>
					</table>
						<!-- <span class="label">编码:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单人ID:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
						<span class="label">制单人:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">企业:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">企业联系人ID:</span><input type="text" name="Q_enterpriseUserid_SL"  class="inputText" />
						<span class="label">企业联系人:</span><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" />
						<span class="label">询价截止时间 从:</span> <input  name="Q_beginofferEnddate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endofferEnddate_DG" class="inputText date" />
						<span class="label">发布时间 从:</span> <input  name="Q_beginreleaseDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endreleaseDate_DG" class="inputText date" />
						<span class="label">发布模式:</span><input type="text" name="Q_releaseModel_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">运输方式:</span><input type="text" name="Q_transportWay_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">总金额:</span><input type="text" name="Q_sumPrice_SL"  class="inputText" />
						<span class="label">预付款:</span><input type="text" name="Q_advancePay_SL"  class="inputText" />
						<span class="label">预付款说明:</span><input type="text" name="Q_advancepayNote_SL"  class="inputText" />
						<span class="label">是否带料:</span><input type="text" name="Q_isBringmaterial_SN"  class="inputText" />
						<span class="label">加工类型:</span><input type="text" name="Q_manufacturingType_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">来源单据CODE:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">委托企业ID:</span><input type="text" name="Q_enquiryenterpId_SL"  class="inputText" />
						<span class="label">委托企业:</span><input type="text" name="Q_enquiryenterpName_SL"  class="inputText" />
						<span class="label">委托企业联系人ID:</span><input type="text" name="Q_enquiryenterpUserid_SL"  class="inputText" />
						<span class="label">委托企业联系人:</span><input type="text" name="Q_enquiryenterpUsername_SL"  class="inputText" />
						<span class="label">是否汇报加工进度:</span><input type="text" name="Q_isReport_SN"  class="inputText" />
						<span class="label">备注（256）:</span><input type="text" name="Q_comments_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="manufQuoteList" id="manufQuoteItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${manufQuoteItem.id}">
				</display:column>
				<display:column property="code" title="编码" sortable="true" sortName="CODE"></display:column>
<%-- 				<display:column property="operatorId" title="制单人ID" sortable="true" sortName="OPERATOR_ID"></display:column>
 --%>				<display:column property="operatorName" title="制单人" sortable="true" sortName="OPERATOR_NAME"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${manufQuoteItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				<display:column property="enterpriseId" title="企业ID" sortable="true" sortName="ENTERPRISE_ID"></display:column>
 --%>				<display:column property="enterpriseName" title="企业" sortable="true" sortName="ENTERPRISE_NAME"></display:column>
<%-- 				<display:column property="enterpriseUserid" title="企业联系人ID" sortable="true" sortName="ENTERPRISE_USERID"></display:column>
 --%>				<display:column property="enterpriseUsername" title="企业联系人" sortable="true" sortName="ENTERPRISE_USERNAME"></display:column>
				<%-- <display:column  title="询价截止时间" sortable="true" sortName="OFFER_ENDDATE">
					<fmt:formatDate value="${manufQuoteItem.offerEnddate}" pattern="yyyy-MM-dd"/>
				</display:column> --%>
				<display:column  title="发布时间" sortable="true" sortName="RELEASE_DATE">
					<fmt:formatDate value="${manufQuoteItem.releaseDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%-- <display:column property="releaseModel" title="发布模式" sortable="true" sortName="RELEASE_MODEL"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="FREIGHT_BEARER"></display:column>
				<display:column property="transportWay" title="运输方式" sortable="true" sortName="TRANSPORT_WAY" maxLength="80"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="CURRENCY_TYPE"></display:column>
				<display:column property="sumPrice" title="总金额" sortable="true" sortName="SUM_PRICE"></display:column> --%>
<%-- 				<display:column property="advancePay" title="预付款" sortable="true" sortName="ADVANCE_PAY"></display:column>
<%--  --%>				<display:column property="advancepayNote" title="预付款说明" sortable="true" sortName="ADVANCEPAY_NOTE" maxLength="80"></display:column>
 --%><%-- 				<display:column property="isBringmaterial" title="是否带料" sortable="true" sortName="IS_BRINGMATERIAL"></display:column>
 --%>				<display:column property="manufacturingType" title="加工类型" sortable="true" sortName="MANUFACTURING_TYPE"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="SOURCEFORM_TYPE"></display:column>
			<%-- 	<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="SOURCEFORM_ID"></display:column>
				<display:column property="sourceformCode" title="来源单据CODE" sortable="true" sortName="SOURCEFORM_CODE"></display:column>
 				<display:column property="enquiryenterpId" title="委托企业ID" sortable="true" sortName="ENQUIRYENTERP_ID"></display:column>
				<display:column property="enquiryenterpName" title="委托企业" sortable="true" sortName="ENQUIRYENTERP_NAME"></display:column>
 				<display:column property="enquiryenterpUserid" title="委托企业联系人ID" sortable="true" sortName="ENQUIRYENTERP_USERID"></display:column>
				<display:column property="enquiryenterpUsername" title="委托企业联系人" sortable="true" sortName="ENQUIRYENTERP_USERNAME"></display:column>
  				<display:column property="isReport" title="是否汇报加工进度" sortable="true" sortName="IS_REPORT"></display:column>
				<display:column property="comments" title="备注（256）" sortable="true" sortName="COMMENTS" maxLength="80"></display:column> --%>
				<display:column title="管理" media="html" style="white-space:nowrap;">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${manufQuoteItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${manufQuoteItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${manufQuoteItem.id}">
					</c:if>
					<a href="get.ht?id=${manufQuoteItem.id}&runid=${manufQuoteItem.runid}&status=${manufQuoteItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="manufQuoteItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


