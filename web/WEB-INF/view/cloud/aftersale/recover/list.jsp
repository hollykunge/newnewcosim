<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>回收单管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">回收单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
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
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<!-- <span class="label">回收原因:</span><input type="text" name="Q_reason_SL"  class="inputText" /> -->
						<span class="label">制单人:</span><input type="text" name="Q_operator_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperatedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperatedate_DG" class="inputText date" />
						<!-- <span class="label">来源单据类型:</span><input type="text" name="Q_sourcetype_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceid_SL"  class="inputText" />
						<span class="label">来源单据号:</span><input type="text" name="Q_sourcecode_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="recoverList" id="recoverItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${recoverItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column property="reason" title="回收原因" sortable="true" sortName="reason" maxLength="80"></display:column>
				<display:column property="operator" title="制单人" sortable="true" sortName="operator"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operatedate">
					<fmt:formatDate value="${recoverItem.operatedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%-- <display:column property="sourcetype" title="来源单据类型" sortable="true" sortName="sourcetype"></display:column>
				<display:column property="sourceid" title="来源单据ID" sortable="true" sortName="sourceid"></display:column>
				<display:column property="sourcecode" title="来源单据号" sortable="true" sortName="sourcecode"></display:column> --%>
                <%--<display:column property="enterprise_id" title="业企Id" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="enterprise_name" title="业企名称" sortable="true" sortName="enterprise_name"></display:column>--%>				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${recoverItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="recoverItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


