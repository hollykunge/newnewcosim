<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>CLOUD_MESSAGE管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_MESSAGE管理列表</span>
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
						<span class="label">sender_id:</span><input type="text" name="Q_senderId_SL"  class="inputText" />
						<span class="label">sendent_id:</span><input type="text" name="Q_sendentId_SL"  class="inputText" />
						<span class="label">receiver_id:</span><input type="text" name="Q_receiverId_SL"  class="inputText" />
						<span class="label">receiveent_id:</span><input type="text" name="Q_receiveentId_SL"  class="inputText" />
						<span class="label">sendtime 从:</span> <input  name="Q_beginsendtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endsendtime_DG" class="inputText date" />
						<span class="label">outtime 从:</span> <input  name="Q_beginouttime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endouttime_DG" class="inputText date" />
						<span class="label">result:</span><input type="text" name="Q_result_SL"  class="inputText" />
						<span class="label">link:</span><input type="text" name="Q_link_SL"  class="inputText" />
						<span class="label">type:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">content:</span><input type="text" name="Q_content_SL"  class="inputText" />
						<span class="label">source_id:</span><input type="text" name="Q_sourceId_SL"  class="inputText" />
						<span class="label">title:</span><input type="text" name="Q_title_SL"  class="inputText" />
						<span class="label">readtime 从:</span> <input  name="Q_beginreadtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endreadtime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="cloudMessageList" id="cloudMessageItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${cloudMessageItem.id}">
				</display:column>
				<display:column property="sender_id" title="sender_id" sortable="true" sortName="sender_id"></display:column>
				<display:column property="sendent_id" title="sendent_id" sortable="true" sortName="sendent_id"></display:column>
				<display:column property="receiver_id" title="receiver_id" sortable="true" sortName="receiver_id"></display:column>
				<display:column property="receiveent_id" title="receiveent_id" sortable="true" sortName="receiveent_id"></display:column>
				<display:column  title="sendtime" sortable="true" sortName="sendtime">
					<fmt:formatDate value="${cloudMessageItem.sendtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="outtime" sortable="true" sortName="outtime">
					<fmt:formatDate value="${cloudMessageItem.outtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="result" title="result" sortable="true" sortName="result"></display:column>
				<display:column property="link" title="link" sortable="true" sortName="link" maxLength="80"></display:column>
				<display:column property="type" title="type" sortable="true" sortName="type"></display:column>
				<display:column property="content" title="content" sortable="true" sortName="content" maxLength="80"></display:column>
				<display:column property="source_id" title="source_id" sortable="true" sortName="source_id"></display:column>
				<display:column property="title" title="title" sortable="true" sortName="title"></display:column>
				<display:column  title="readtime" sortable="true" sortName="readtime">
					<fmt:formatDate value="${cloudMessageItem.readtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${cloudMessageItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${cloudMessageItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${cloudMessageItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="cloudMessageItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


