<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_pur_order_detail管理</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_pur_order_detail管理列表</span>
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
						<span class="label">materiel_id:</span><input type="text" name="Q_materielId_SL"  class="inputText" />
						<span class="label">materiel_code:</span><input type="text" name="Q_materielCode_SL"  class="inputText" />
						<span class="label">materiel_name:</span><input type="text" name="Q_materielName_SL"  class="inputText" />
						<span class="label">materiel_lev:</span><input type="text" name="Q_materielLev_SL"  class="inputText" />
						<span class="label">attribute_info:</span><input type="text" name="Q_attributeInfo_SL"  class="inputText" />
						<span class="label">unit:</span><input type="text" name="Q_unit_SL"  class="inputText" />
						<span class="label">order_num:</span><input type="text" name="Q_orderNum_SL"  class="inputText" />
						<span class="label">price:</span><input type="text" name="Q_price_SL"  class="inputText" />
						<span class="label">议价:</span><input type="text" name="Q_bargain_SL"  class="inputText" />
						<span class="label">sum_price:</span><input type="text" name="Q_sumPrice_SL"  class="inputText" />
						<span class="label">delivery_enddate 从:</span> <input  name="Q_begindeliveryEnddate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddeliveryEnddate_DG" class="inputText date" />
						<span class="label">purorder_id:</span><input type="text" name="Q_purorderId_SL"  class="inputText" />
						<span class="label">品物规格:</span><input type="text" name="Q_model_SL"  class="inputText" />
						<span class="label">标记是否存入物品库:</span><input type="text" name="Q_ismat_SL"  class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="PurOrderDetailList" id="PurOrderDetailItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${PurOrderDetailItem.id}">
				</display:column>
				<display:column property="materielId" title="materiel_id" sortable="true" sortName="materiel_id"></display:column>
				<display:column property="materielCode" title="materiel_code" sortable="true" sortName="materiel_code"></display:column>
				<display:column property="materielName" title="materiel_name" sortable="true" sortName="materiel_name" maxLength="80"></display:column>
				<display:column property="materielLev" title="materiel_lev" sortable="true" sortName="materiel_lev"></display:column>
				<display:column property="attributeInfo" title="attribute_info" sortable="true" sortName="attribute_info" maxLength="80"></display:column>
				<display:column property="unit" title="unit" sortable="true" sortName="unit"></display:column>
				<display:column property="orderNum" title="order_num" sortable="true" sortName="order_num"></display:column>
				<display:column property="price" title="price" sortable="true" sortName="price"></display:column>
				<display:column property="bargain" title="议价" sortable="true" sortName="bargain"></display:column>
				<display:column property="sumPrice" title="sum_price" sortable="true" sortName="sum_price"></display:column>
				<display:column  title="delivery_enddate" sortable="true" sortName="delivery_enddate">
					<fmt:formatDate value="${PurOrderDetailItem.deliveryEnddate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="purorderId" title="purorder_id" sortable="true" sortName="purorder_id"></display:column>
				<display:column property="model" title="品物规格" sortable="true" sortName="model" maxLength="80"></display:column>
				<display:column property="ismat" title="标记是否存入物品库" sortable="true" sortName="ismat"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<a href="del.ht?id=${PurOrderDetailItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${PurOrderDetailItem.id}" class="link edit">编辑</a>
					<a href="get.ht?id=${PurOrderDetailItem.id}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="PurOrderDetailItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


