<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_finance_paymentbill管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">应付单据管理列表</span>
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
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">状态:</span>
						 <select name="Q_state_SL">
						  <option value="">全部</option>
						   <option value="草稿">草稿</option>
						  <option value="生效">生效</option>
						</select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="finPaymentList" id="finPaymentItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${finPaymentItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${finPaymentItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="payType" title="付款类型" sortable="true" sortName="pay_type"></display:column>
				<display:column property="payeeenterpName" title="收款企业名称" sortable="true" sortName="payeeenterp_name"></display:column>
				<display:column  title="付款日期" sortable="true" sortName="pay_date">
					<fmt:formatDate value="${finPaymentItem.payDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="payPrice" title="付款金额" sortable="true" sortName="pay_price"></display:column>
				<display:column property="state" title="状态" sortable="true" sortName="state"></display:column>
				<display:column title="管理" media="html" style="width:190px">
				
				<c:if test="${finPaymentItem.state!='生效' }">
					<a href="del.ht?id=${finPaymentItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${finPaymentItem.id}" class="link edit">编辑</a>
					<a href="updateState.ht?id=${finPaymentItem.id}" class="link edit">生效</a>
					</c:if>
				 
					<a href="get.ht?id=${finPaymentItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="finPaymentItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


