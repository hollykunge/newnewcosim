<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>sys_org_info_aptitude管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">sys_org_info_aptitude管理列表</span>
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
						<span class="label">INFO_ID:</span><input type="text" name="Q_infoId_SL"  class="inputText" />
						<span class="label">CATE_TYPE:</span><input type="text" name="Q_cateType_SL"  class="inputText" />
						<span class="label">CATE_ORG:</span><input type="text" name="Q_cateOrg_SL"  class="inputText" />
						<span class="label">INURE_DATE 从:</span> <input  name="Q_begininureDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endinureDate_DG" class="inputText date" />
						<span class="label">END_DATE:</span><input type="text" name="Q_endDate_SL"  class="inputText" />
						<span class="label">CATE_PIC:</span><input type="text" name="Q_catePic_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="AptitudeList" id="AptitudeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${AptitudeItem.id}">
				</display:column>
				<display:column property="infoId" title="INFO_ID" sortable="true" sortName="infoId"></display:column>
				<display:column property="cateType" title="CATE_TYPE" sortable="true" sortName="cateType"></display:column>
				<display:column property="cateOrg" title="CATE_ORG" sortable="true" sortName="cateOrg"></display:column>
				<display:column  title="INURE_DATE" sortable="true" sortName="inureDate">
					<fmt:formatDate value="${AptitudeItem.inureDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="endDate" title="END_DATE" sortable="true" sortName="endDate"></display:column>
				<display:column property="catePic" title="CATE_PIC" sortable="true" sortName="catePic" maxLength="80"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${AptitudeItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${AptitudeItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${AptitudeItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="AptitudeItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


