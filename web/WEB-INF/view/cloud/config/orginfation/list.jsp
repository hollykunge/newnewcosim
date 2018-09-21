<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>sys_org_info管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">sys_org_info管理列表</span>
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
						<span class="label">EMAIL:</span><input type="text" name="Q_email_SL"  class="inputText" />
						<span class="label">NAME:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">INDUSTRY:</span><input type="text" name="Q_industry_SL"  class="inputText" />
						<span class="label">SCALE:</span><input type="text" name="Q_scale_SL"  class="inputText" />
						<span class="label">ADDRESS:</span><input type="text" name="Q_address_SL"  class="inputText" />
						<span class="label">POSTCODE:</span><input type="text" name="Q_postcode_SL"  class="inputText" />
						<span class="label">CONNECTER:</span><input type="text" name="Q_connecter_SL"  class="inputText" />
						<span class="label">TEL:</span><input type="text" name="Q_tel_SL"  class="inputText" />
						<span class="label">FAX:</span><input type="text" name="Q_fax_SL"  class="inputText" />
						<span class="label">HOMEPHONE:</span><input type="text" name="Q_homephone_SL"  class="inputText" />
						<span class="label">LOGO:</span><input type="text" name="Q_logo_SL"  class="inputText" />
						<span class="label">INDUSTRY2:</span><input type="text" name="Q_industry2_SL"  class="inputText" />
						<span class="label">MEMBER:</span><input type="text" name="Q_member_SL"  class="inputText" />
						<span class="label">INFO:</span><input type="text" name="Q_info_SL"  class="inputText" />
						<span class="label">COUNTRY:</span><input type="text" name="Q_country_SL"  class="inputText" />
						<span class="label">PROVINCE:</span><input type="text" name="Q_province_SL"  class="inputText" />
						<span class="label">CITY:</span><input type="text" name="Q_city_SL"  class="inputText" />
						<span class="label">IS_PUBLIC:</span><input type="text" name="Q_isPublic_SL"  class="inputText" />
						<span class="label">CODE:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">TYPE:</span><input type="text" name="Q_type_SL"  class="inputText" />
						<span class="label">MODEL:</span><input type="text" name="Q_model_SL"  class="inputText" />
						<span class="label">PRODUCT:</span><input type="text" name="Q_product_SL"  class="inputText" />
						<span class="label">WEBSITE:</span><input type="text" name="Q_website_SL"  class="inputText" />
						<span class="label">REGISTERTIME 从:</span> <input  name="Q_beginregistertime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endregistertime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="OrgInfationList" id="OrgInfationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="sysOrgInfoId" value="${OrgInfationItem.sysOrgInfoId}">
				</display:column>
				<display:column property="email" title="EMAIL" sortable="true" sortName="email" maxLength="80"></display:column>
				<display:column property="name" title="NAME" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="industry" title="INDUSTRY" sortable="true" sortName="industry" maxLength="80"></display:column>
				<display:column property="scale" title="SCALE" sortable="true" sortName="scale" maxLength="80"></display:column>
				<display:column property="address" title="ADDRESS" sortable="true" sortName="address" maxLength="80"></display:column>
				<display:column property="postcode" title="POSTCODE" sortable="true" sortName="postcode" maxLength="80"></display:column>
				<display:column property="connecter" title="CONNECTER" sortable="true" sortName="connecter"></display:column>
				<display:column property="tel" title="TEL" sortable="true" sortName="tel"></display:column>
				<display:column property="fax" title="FAX" sortable="true" sortName="fax"></display:column>
				<display:column property="homephone" title="HOMEPHONE" sortable="true" sortName="homephone"></display:column>
				<display:column property="logo" title="LOGO" sortable="true" sortName="logo" maxLength="80"></display:column>
				<display:column property="industry2" title="INDUSTRY2" sortable="true" sortName="industry2" maxLength="80"></display:column>
				<display:column property="member" title="MEMBER" sortable="true" sortName="member" maxLength="80"></display:column>
				<display:column property="info" title="INFO" sortable="true" sortName="info" maxLength="80"></display:column>
				<display:column property="country" title="COUNTRY" sortable="true" sortName="country" maxLength="80"></display:column>
				<display:column property="province" title="PROVINCE" sortable="true" sortName="province" maxLength="80"></display:column>
				<display:column property="city" title="CITY" sortable="true" sortName="city" maxLength="80"></display:column>
				<display:column property="isPublic" title="IS_PUBLIC" sortable="true" sortName="isPublic"></display:column>
				<display:column property="code" title="CODE" sortable="true" sortName="code"></display:column>
				<display:column property="type" title="TYPE" sortable="true" sortName="type"></display:column>
				<display:column property="model" title="MODEL" sortable="true" sortName="model"></display:column>
				<display:column property="product" title="PRODUCT" sortable="true" sortName="product"></display:column>
				<display:column property="website" title="WEBSITE" sortable="true" sortName="website"></display:column>
				<display:column  title="REGISTERTIME" sortable="true" sortName="registertime">
					<fmt:formatDate value="${OrgInfationItem.registertime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?sysOrgInfoId=${OrgInfationItem.sysOrgInfoId}" class="link del">删除</a>
					<a href="edit.ht?sysOrgInfoId=${OrgInfationItem.sysOrgInfoId}" class="link edit">编辑</a>
					<a href="get.ht?sysOrgInfoId=${OrgInfationItem.sysOrgInfoId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="OrgInfationItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


