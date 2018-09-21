<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购结算管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购结算管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit_m.ht">添加</a></div>
					<div class="l-bar-separator"></div>
				 
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list_m.ht">
					<div class="row">
						<span class="label">任务单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">任务状态:</span>
						<select name="Q_statu_SE" class="inputText"  >
							<option value="">==所有状态==</option>									
							<option value="1-填写结算申请单">1-填写结算申请单</option> 
							<option value="2-结算审核">2-结算审核</option>
							<option value="3-生成付款单">3-生成付款单</option>
							<option value="4-生成收款单">4-生成收款单</option>
							<option value="已经结束">==已经结束==</option>
						</select>
						<span class="label">制单时间 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
					</div>
						 
				</form>
			</div>
		</div>
		<div class="panel-body"> 
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="finStatementList" id="finStatementItem" requestURI="list_m.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${finStatementItem.id}">
				</display:column>
				<display:column property="code" title="任务单号" sortable="true" sortName="code"></display:column>
				
				<display:column property="sumPrise" title="总金额" sortable="true" sortName="sum_prise"></display:column>
				<display:column property="payenterpName" title="付款企业" sortable="true" sortName="payenterp_name"></display:column>
				<display:column property="payeeenterpName" title="收款企业" sortable="true" sortName="payeeenterp_name"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${finStatementItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="statu" title="任务状态" sortable="true" sortName="statu"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get_m.ht?id=${finStatementItem.id}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${finStatementItem.runId}" class="link detail">流程示意图</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="finStatementItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


