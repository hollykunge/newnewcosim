<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>商机管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">商机管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list_all_bcs.ht">
					<div class="row">
						<span class="label">商机名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
					  
						<span class="label">商机类型:</span>
						          <select  name="Q_type_SL" id="type" >
						          
						                <option value="">请选择</option>
					                    <option value="1">采购商机</option>
					                    <option value="2">营销商机</option>
					                    <option value="3">生产商机</option>
					                    <option value="4">服务商机</option>
					                    <option value="5">研发商机</option>
					             </select>
				 
					   
						<span class="label">开始时间:</span> <input  name="Q_beginstartTime_DL"  class="inputText date" />
						<span class="label">结束时间:</span> <input  name="Q_endendTime_DL"  class="inputText date" />
		
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="businessChanceList" id="businessChanceItem" requestURI="getAllList.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${businessChanceItem.id}">
				</display:column>
				<display:column property="name" title="商机名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column  title="开始时间" sortable="true" sortName="startTime">
					<fmt:formatDate value="${businessChanceItem.startTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="结束时间" sortable="true" sortName="endTime">
					<fmt:formatDate value="${businessChanceItem.endTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column property="companyName" title="发布企业" sortable="true" sortName="companyName"></display:column>
				<display:column property="publishState" title="发布状态" sortable="true" sortName="publishState"></display:column>
				<display:column  title="发布时间" sortable="true" sortName="operateTime">
					<fmt:formatDate value="${businessChanceItem.operateTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column title="管理" media="html" style="width:80px">
					<a href="getview.ht?id=${businessChanceItem.id}&type=${businessChanceItem.type}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="businessChanceItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


