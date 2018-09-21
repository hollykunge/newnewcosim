<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>结算单管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">结算单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="updateState.ht">生效</a></div>
					 
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">付款企业:</span><input type="text" name="Q_payenterpName_SL"  class="inputText" />
						
					</div>
					<div class="row">
						<span class="label">制单时间 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
							<span class="label">状态:</span>
						 <select name="Q_state_SL">
						  <option value="">全部</option>
						  <option value="草稿">草稿</option>
						  <option value="生效未付款">生效未付款</option>
						  <option value="已付款">已付款</option>
						</select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body"> 
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="finStatementList" id="finStatementItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${finStatementItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${finStatementItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column property="sourceformType" title="来源类型" sortable="true" sortName="sourceform_type"></display:column>
				<display:column property="sumPrise" title="总金额" sortable="true" sortName="sum_prise"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="currency_type"></display:column>
				<display:column property="payeeenterpName" title="收款企业" sortable="true" sortName="payeeenterp_name"></display:column>
				<display:column property="payenterpName" title="付款企业" sortable="true" sortName="payenterp_name"></display:column>
				<display:column property="state" title="状态" sortable="true" sortName="state"></display:column>
				<display:column title="管理" media="html" style="width:190px">
				<c:if test="${finStatementItem.state!='已支付' }">
					<a href="del.ht?id=${finStatementItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${finStatementItem.id}" class="link edit">编辑</a>
					<a href="updateState.ht?id=${finStatementItem.id}" class="link edit">生效</a>
					</c:if>
					 
					<a href="get.ht?id=${finStatementItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="finStatementItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


