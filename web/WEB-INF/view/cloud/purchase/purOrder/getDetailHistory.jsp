<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品信息详情</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link back"  onClick="location.href='reportDetailsByMonth.ht?code=${materielCode}'">价格统计（按月份）</a></div>
					<div class="l-bar-separator"></div>
				</div>
			</div>
		    <display:table name="purOrderDetailList" id="DetailItem" requestURI="getDetailHistory.ht?materielCode='+${materielCode}+'&purenterpId='+${purorderMainId.purenterpId}" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
			  	<input type="hidden" name="purorderId" value="${DetailItem.purorderId}">
		    	<display:column property="materielCode" title="物品编码"  sortName="materielCode" maxLength="80"></display:column>
		    	<display:column property="model" title="物品规格" sortName="model" maxLength="80"></display:column>
				<display:column property="materielName" title="物品名称" sortName="materielName" maxLength="80"></display:column>
				<display:column property="attributeInfo" title="物品属性" sortName="attributeInfo" maxLength="80"></display:column>
				<display:column property="unit" title="单位" sortName="unit" maxLength="80"></display:column>
				<display:column property="orderNum" title="订单数量" sortName="orderNum" maxLength="80"></display:column>
				<display:column property="price" title="单价"  sortable="true" sortName="price" maxLength="80"></display:column>
				<display:column property="sumPrice" title="总价"  sortName="sumPrice" maxLength="80"></display:column>
				<display:column  title="发货时间" sortName="deliveryEnddate">
					<fmt:formatDate value="${DetailItem.deliveryEnddate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="purorderMainId.suppenterpName" title="供应企业"  sortName="purorderMainId.suppenterpName" maxLength="80"></display:column>
				<display:column  title="采购时间"  sortName="purorderMainId.operateDate">
					<fmt:formatDate value="${DetailItem.purorderMainId.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
			</display:table>
			<hotent:paging tableId="DetailItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


