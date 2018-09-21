<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>CLOUD_LOGISTICS_INFO管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_LOGISTICS_INFO管理列表</span>
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
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">生成日期 从:</span> <input  name="Q_begincreateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcreateDate_DG" class="inputText date" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceId_SL"  class="inputText" />
						<span class="label">物流动作时间 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">物流动作类型:</span><input type="text" name="Q_operateType_SL"  class="inputText" />
						<span class="label">物流动态信息:</span><input type="text" name="Q_operateInfo_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="logisticsInfoList" id="logisticsInfoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${logisticsInfoItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="CODE"></display:column>
				<display:column  title="生成日期" sortable="true" sortName="CREATE_DATE">
					<fmt:formatDate value="${logisticsInfoItem.createDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="sourceId" title="来源单据ID" sortable="true" sortName="SOURCE_ID"></display:column>
				<display:column  title="物流动作时间" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${logisticsInfoItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operateType" title="物流动作类型" sortable="true" sortName="OPERATE_TYPE"></display:column>
				<display:column property="operateInfo" title="物流动态信息" sortable="true" sortName="OPERATE_INFO" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${logisticsInfoItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${logisticsInfoItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${logisticsInfoItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="logisticsInfoItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


