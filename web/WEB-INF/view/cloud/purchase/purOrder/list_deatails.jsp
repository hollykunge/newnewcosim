<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品信息详情</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品信息详情</span>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			
		    <display:table name="purOrderDetail" id="DetailItem" requestURI="list_deatails.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
			  	<input type="hidden" name="purorderId" value="${DetailItem.purorderId}">
		    	<display:column property="materielCode" title="物品编码" sortable="true" sortName="materielCode" maxLength="80"></display:column>
		    	<display:column property="model" title="物品规格" sortable="true" sortName="model" maxLength="80"></display:column>
				<display:column property="materielName" title="物品名称" sortable="true" sortName="materielName" maxLength="80"></display:column>
				<display:column property="attributeInfo" title="物品属性" sortable="true" sortName="attributeInfo" maxLength="80"></display:column>
				<display:column property="unit" title="单位" sortable="true" sortName="unit" maxLength="80"></display:column>
				<display:column property="orderNum" title="订单数量" sortable="true" sortName="orderNum" maxLength="80"></display:column>
				<display:column property="price" title="单价" sortable="true" sortName="price" maxLength="80"></display:column>
				<display:column  title="发货时间" sortable="true" sortName="deliveryEnddate">
					<fmt:formatDate value="${DetailItem.deliveryEnddate}" pattern="yyyy-MM-dd"/>
				</display:column>
			</display:table>
			<hotent:paging tableId="DetailItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


