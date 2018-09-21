<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>用户反馈管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">用户反馈管理列表</span>
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
						<span class="label">反馈单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<!-- <span class="label">反馈用户:</span><input type="text" name="Q_fdman_SL"  class="inputText" />
						<span class="label">反馈日期 从:</span> <input  name="Q_beginfddate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endfddate_DG" class="inputText date" /> -->
						<span class="label">产品名称:</span><input type="text" name="Q_prodname_SL"  class="inputText" />
						<!-- <span class="label">产品型号:</span><input type="text" name="Q_prodcode_SL"  class="inputText" />
						<span class="label">买购人:</span><input type="text" name="Q_purchaseman_SL"  class="inputText" /> -->
						<span class="label">购买日期 从:</span> <input  name="Q_beginpurchasedate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endpurchasedate_DG" class="inputText date" />
						<!-- <span class="label">反馈问题:</span><input type="text" name="Q_descn_SL"  class="inputText" />
						<span class="label">处理结果:</span><input type="text" name="Q_result_SL"  class="inputText" />
						<span class="label">厂商:</span><input type="text" name="Q_purenter_name_SL"  class="inputText" />
						<span class="label">状态:</span><input type="text" name="Q_statu_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="feedbackList" id="feedbackItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${feedbackItem.id}">
				</display:column>
				<display:column property="code" title="反馈单号" sortable="true" sortName="code"></display:column>
				<display:column property="fdman" title="反馈用户" sortable="true" sortName="fdman"></display:column>
				<display:column  title="反馈日期" sortable="true" sortName="fddate">
					<fmt:formatDate value="${feedbackItem.fddate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="prodname" title="产品名称" sortable="true" sortName="prodname"></display:column>
				<display:column property="prodcode" title="产品型号" sortable="true" sortName="prodcode"></display:column>
				<display:column property="purchaseman" title="买购人" sortable="true" sortName="purchaseman"></display:column>
				<display:column  title="购买日期" sortable="true" sortName="purchasedate">
					<fmt:formatDate value="${feedbackItem.purchasedate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="descn" title="反馈问题" sortable="true" sortName="descn" maxLength="80"></display:column>
				<display:column property="result" title="处理结果" sortable="true" sortName="result"></display:column>
				<display:column property="purenter_name" title="厂商" sortable="true" sortName="purenter_name"></display:column>
				<display:column property="statu" title="状态" sortable="true" sortName="statu"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="get.ht?id=${feedbackItem.id}" class="link detail">明细</a>
		            <a href="${ctx}/cloud/aftersale/feedback/addTask.ht?id=${feedbackItem.id}" class="link setting">维修</a></div>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="feedbackItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


