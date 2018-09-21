<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>委外加工询价单管理</title>
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
	<style type="text/css">
		#mytable {
			width: 800px;
			padding: 0;
			margin: 0;
			border-left: 1px solid #C1DAD7;
			border-top: 1px solid #C1DAD7;
		}
		#mytable td {
			width:40px;
			border-right: 1px solid #C1DAD7;
			border-bottom: 1px solid #C1DAD7;
			background: #fff;
			font-size:12px;
			padding: 6px 6px 6px 12px;
			color: #4f6b72;
		}
	</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">委外加工询价单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!--
					<div class="group"><a class="link add" href="javascript:void(0)" onclick="window.open('edit.ht')">添加</a></div>
					 -->
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<!-- 
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
							<td>单证号:</td>
							<td><input type="text" name="Q_code_SL"  class="inputText" /></td>
							<td>制单人姓名:</td>
							<td><input type="text" name="Q_operatorName_SL"  class="inputText" /></td>
							<td>企业名:</td>
							<td><input type="text" name="Q_enterpriseName_SL"  class="inputText" /></td>
							<td>联系人姓名:</td>
							<td><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" /></td>
						</tr>
						<tr>
							<td>制单日期:</td>
							<td colspan=3><input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></td>
						</tr>
					</table>
					<!-- 
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
 						<span class="label">制单人ID:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
 						<span class="label">制单人姓名:</span><input type="text" name="Q_operatorName_SL"  class="inputText" /></br>
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></br>
 						<span class="label">企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">企业名:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
 						<span class="label">联系人ID:</span><input type="text" name="Q_enterpriseUserid_SL"  class="inputText" />
						<span class="label">联系人姓名:</span><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" />
						<span class="label">询价截止日期 从:</span> <input  name="Q_beginofferEnddate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endofferEnddate_DG" class="inputText date" />
						<span class="label">发布日期 从:</span> <input  name="Q_beginreleaseDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endreleaseDate_DG" class="inputText date" />
						<span class="label">发布模式:</span><input type="text" name="Q_releaseModel_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">运输方式:</span><input type="text" name="Q_transportWay_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">是否带料:</span><input type="text" name="Q_isBringmaterial_SN"  class="inputText" />
						<span class="label">加工类型:</span><input type="text" name="Q_manufacturingType_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">来源单据编码:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">是否汇报加工进度:</span><input type="text" name="Q_isReport_SN"  class="inputText" />
						<span class="label">备注:</span><input type="text" name="Q_comments_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="manufEnquiryList" id="manufEnquiryItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${manufEnquiryItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="CODE" style="white-space:nowrap;  "></display:column>
<%-- 				<display:column property="operatorId" title="制单人ID" sortable="true" sortName="OPERATOR_ID"></display:column>
 --%>				<display:column property="operatorName" title="制单人姓名" sortable="true" sortName="OPERATOR_NAME"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${manufEnquiryItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				<display:column property="enterpriseId" title="企业ID" sortable="true" sortName="ENTERPRISE_ID"></display:column>
 --%>				<display:column property="enterpriseName" title="企业名" sortable="true" sortName="ENTERPRISE_NAME"></display:column>
<%-- 				<display:column property="enterpriseUserid" title="联系人ID" sortable="true" sortName="ENTERPRISE_USERID"></display:column>
 --%>				<display:column property="enterpriseUsername" title="联系人姓名" sortable="true" sortName="ENTERPRISE_USERNAME"></display:column>
				<%-- <display:column  title="询价截止日期" sortable="true" sortName="OFFER_ENDDATE">
					<fmt:formatDate value="${manufEnquiryItem.offerEnddate}" pattern="yyyy-MM-dd"/>
				</display:column> --%>
				<display:column  title="发布日期" sortable="true" sortName="RELEASE_DATE">
					<fmt:formatDate value="${manufEnquiryItem.releaseDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%-- <display:column property="releaseModel" title="发布模式" sortable="true" sortName="RELEASE_MODEL"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="FREIGHT_BEARER"></display:column>
				<display:column property="transportWay" title="运输方式" sortable="true" sortName="TRANSPORT_WAY" maxLength="80"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="CURRENCY_TYPE"></display:column>
				<display:column property="isBringmaterial" title="是否带料" sortable="true" sortName="IS_BRINGMATERIAL"></display:column>
				<display:column property="manufacturingType" title="加工类型" sortable="true" sortName="MANUFACTURING_TYPE"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="SOURCEFORM_TYPE"></display:column> --%>
<%-- 				<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="SOURCEFORM_ID"></display:column>
 --%>				<display:column property="sourceformCode" title="来源单据编码" sortable="true" sortName="SOURCEFORM_CODE"></display:column>
				<display:column property="isReport" title="是否汇报加工进度" sortable="true" sortName="IS_REPORT"></display:column>
				<display:column property="comments" title="备注" sortable="true" sortName="COMMENTS" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px;white-space:nowrap;  ">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${manufEnquiryItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${manufEnquiryItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${manufEnquiryItem.id}">
					</c:if>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${manufEnquiryItem.runId}" class="link detail">流程示意图</a>					
					<a href="get.ht?id=${manufEnquiryItem.id}&runid=${manufEnquiryItem.runid}&status=${manufEnquiryItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="manufEnquiryItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


