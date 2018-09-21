<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_account_info管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">企业银行账户管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">开户行:</span><input type="text" name="Q_openBank_SL"  class="inputText" />
						<span class="label">开户人:</span><input type="text" name="Q_accountOp_SL"  class="inputText" />
						<span class="label">银行卡号:</span><input type="text" name="Q_bankCode_SL"  class="inputText" />
					</div>
					<div class="row">
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
					</div>
					<!--
					<span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">制单人:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						
					-->
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="AccountInfoList" id="AccountInfoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${AccountInfoItem.id}">
				</display:column>
				<display:column property="bankCode" title="银行卡号" sortable="true" sortName="bank_code"></display:column>
				<display:column property="openBank" title="开户行" sortable="true" sortName="open_bank" maxLength="80"></display:column>
				<display:column property="accountOp" title="开户人" sortable="true" sortName="account_op"></display:column>
				<display:column property="enterName" title="开户企业名称" sortable="true" sortName="enter_name"></display:column>
				<display:column property="operaterName" title="制单人" sortable="true" sortName="operater_name"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${AccountInfoItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${AccountInfoItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${AccountInfoItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${AccountInfoItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="AccountInfoItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


