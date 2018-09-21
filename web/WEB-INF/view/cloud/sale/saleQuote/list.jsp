<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>销售报价管理</title>
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
				<span class="tbar-label">销售报价管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
<!-- 					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">报价单编码:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<!-- <span class="label">制单人ID:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
						<span class="label">制单人:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">报价截止日期 从:</span> <input  name="Q_beginofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endofferEndtime_DG" class="inputText date" />
						<span class="label">预付款:</span><input type="text" name="Q_advancePay_SL"  class="inputText" />
						<span class="label">预付款说明:</span><input type="text" name="Q_advancepayNotes_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">采购企业ID:</span><input type="text" name="Q_purenterpId_SL"  class="inputText" />
						<span class="label">采购企业:</span><input type="text" name="Q_purenterpName_SL"  class="inputText" />
						<span class="label">采购企业联系人ID:</span><input type="text" name="Q_purenterpUserid_SL"  class="inputText" />
						<span class="label">采购企业联系人姓名:</span><input type="text" name="Q_purenterpUsername_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">询价单发布日期 从:</span> <input  name="Q_beginpurenqReleasedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endpurenqReleasedate_DG" class="inputText date" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="saleQuoteList" id="saleQuoteItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${saleQuoteItem.id}">
				</display:column>
				<display:column property="code" title="报价单编码" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operateDate">
					<fmt:formatDate value="${saleQuoteItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				
<%-- 				<display:column property="operatorId" title="制单人ID" sortable="true" sortName="operatorId"></display:column> --%>
				<display:column property="operatorName" title="制单人" sortable="true" sortName="operatorName"></display:column>
<%--				<display:column  title="报价截止日期" sortable="true" sortName="offerEndtime">
					<fmt:formatDate value="${saleQuoteItem.offerEndtime}" pattern="yyyy-MM-dd"/>
				</display:column> --%>
				
				<display:column property="advancePay" title="预付款" sortable="true" sortName="advancePay"></display:column>
				<%-- <display:column property="advancepayNotes" title="预付款说明" sortable="true" sortName="advancepayNotes" maxLength="80"></display:column>
				--%>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceform_type"></display:column>
				<%-- 
				<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="sourceform_id"></display:column>			
				<display:column property="sourceformCode" title="来源单据号" sortable="true" sortName="sourceform_code"></display:column>
				<display:column property="purenterpId" title="采购企业ID" sortable="true" sortName="purenterpId"></display:column> 
				--%>
				<display:column property="purenterpName" title="采购企业" sortable="true" sortName="purenterpName"></display:column>
				<%-- <display:column property="purenterpUserid" title="采购企业联系人ID" sortable="true" sortName="purenterpUserid"></display:column>
				<display:column property="purenterpUsername" title="采购企业联系人姓名" sortable="true" sortName="purenterpUsername"></display:column> --%>
				<display:column property="currencyType" title="币种" sortable="true" sortName="currencyType"></display:column>
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="freightBearer"></display:column>
				<display:column  title="询价单发布日期" sortable="true" sortName="purenqReleasedate">
					<fmt:formatDate value="${saleQuoteItem.purenqReleasedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<%-- <display:column property="enterpriseId" title="销售企业ID" sortable="true" sortName="enterpriseId"></display:column>
				<display:column property="enterpriseName" title="销售企业" sortable="true" sortName="enterpriseName"></display:column> --%>
				
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${saleQuoteItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${saleQuoteItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${saleQuoteItem.id}">
					</c:if>
					<a href="get.ht?id=${saleQuoteItem.id}&runid=${saleQuoteItem.runid}&status=${saleQuoteItem.runStatus}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="saleQuoteItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


