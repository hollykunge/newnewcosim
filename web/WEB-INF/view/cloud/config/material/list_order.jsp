<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@include file="/commons/cloud/global.jsp" %>
<html>
<head>
<title>物品管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script>
$(function(){
	$('#friends').change(function(){
		var tgtEntId = $(this).val();
		location.href='?ent_id=' + tgtEntId + '&type=' + ${type};
	});
	
});
$(function(){
	$('#friends').val(${tgtEntId}); 
});
function vsfun(type){
	if (type==0){
		$(".div1").show();
		$(".div2").hide();
	}else{
		$(".div2").show();
		$(".div1").hide();
	} 
} 

function changeAdd(type,materialId,a){
	var url="";
	var remark1 = $(a).parent().prev();
	if(type=="add"){
		url="addAndOff.ht?type=add&materialId="+materialId;
	}else if(type=="off"){
		url="addAndOff.ht?type=off&materialId="+materialId;
	}
	$.ajax({
		url:url,
		type:"POST",
		data:{type:type,materialId:materialId},
		success:function(data){
			if(data){
			   if(type=="add"){ 
					$(remark1).text("上架");
				}else if(type=="off"){
					$(remark1).text("下架");
				}
				//window.location="${ctx}/cloud/config/material/list_order.ht?type=${type}";
			}
		},
		error:function(data){
			alert("操作失败");
		}
	});  
	return false;
}


function toPriceStrategy(materialId){
	var currentEntid = $("#friends").val();
	if(currentEntid==""){
		window.location="${ctx}/cloud/config/priceStrategy/listCoop.ht?currentEntid=&materialId="+materialId;
	}else{
		window.location="${ctx}/cloud/config/priceStrategy/listCoop.ht?currentEntid="+currentEntid+"&materialId="+materialId;
	}
	
}
function viewPriceStrategy(materialId){
	var currentEntid = $("#friends").val();
	if(currentEntid==""){
		window.location="${ctx}/cloud/config/priceStrategy/listCoop.ht?currentEntid=&materialId="+materialId+"&type=view";
	}else{
		window.location="${ctx}/cloud/config/priceStrategy/listCoop.ht?currentEntid="+currentEntid+"&materialId="+materialId+"&type=view";
	}
	
}
function searchDiscount(materialId){
	var currentEntid = $("#friends").val();
	if(currentEntid==""){
		window.location="${ctx}/cloud/config/priceStrategy/searchDiscount.ht?currentEntid=&materialId="+materialId;
	}else{
		window.location="${ctx}/cloud/config/priceStrategy/searchDiscount.ht?currentEntid="+currentEntid+"&materialId="+materialId;
	}
	
}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list_order.ht?type=${type}">
					<div class="row">
						<span class="label">所属企业:</span>
						<input type="hidden" id="type"  value="${type}" />
						<select id="friends" name="friends" >
							<option value="0">--请选择企业--</option>
							<c:forEach items="${friends}" var="friends">
							<%--<option value="${friends.corpEnterprise.sysOrgInfoId}@@${friends.type}">${friends.corpEnterprise.name}${friends.type} </option>--%>
								<option value="${friends.corpEnterprise.sysOrgInfoId}">${friends.corpEnterprise.name}</option>
							</c:forEach>
						</select>
						<%-- <ap:selectList name="friends" id="friends" value="friends" selectedValue="${tgtEntId}">
							<ap:option value="0">--请选择企业--</ap:option>
							<ap:option value="corpEnterprise.sysOrgInfoId" text="corpEnterprise.name"></ap:option>
						</ap:selectList> --%>
						<!--<span class="label">物品名称:</span><input type="text" name="Q_name_SL"  class="inputText" />
						<span class="label">所属分类:</span><input type="text" name="Q_catalogName_SL"  class="inputText" />-->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="materialList" id="materialItem" requestURI="list_order.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${materialItem.id}">
				</display:column>
				<display:column property="name" title="物品名称" sortable="true" sortName="name" maxLength="80"></display:column>
				<display:column property="model" title="物品规格" sortable="true" sortName="model" maxLength="80"></display:column>
				<display:column property="unit" title="单位" sortable="true" sortName="unit"></display:column>
				<display:column property="price" title="价格" sortable="true" sortName="price"></display:column>
				<display:column property="code" title="物品代码" sortable="true" sortName="code"></display:column>
				<display:column property="parentName" title="物品父级" sortable="true" sortName="parent_name"></display:column>
				<display:column property="catalogName" title="物品分类" sortable="true" sortName="catalog_name"></display:column>
				<display:column property="remark1" title="上架状态" sortable="true" sortName="remark1"></display:column>
				<display:column title="管理" media="html" style="width:180px;white-space:nowrap">
				<c:if test="${materialItem.type == '1' }">		
						<a href = "#" onclick="changeAdd('add',${materialItem.id},this)" class="link detail">上架</a>
						<a href = "#" onclick="changeAdd('off',${materialItem.id},this)"class="link detail">下架</a>
						<c:if test="${materialItem.sharedRepository == '1' }">
							<a href="${ctx}/cloud/warehouse/warehouseAccounts/productNums.ht?id=${materialItem.id}" class="link detail" name="saleRepository" >经销商库存查询</a>
						</c:if>
						<a href="javascript:void(0)" onclick="toPriceStrategy(${materialItem.id})" class="link detail">经销商价格策略管理</a>
				</c:if>
				<c:if test="${materialItem.type == '0' }">
					<a href="${ctx}/cloud/warehouse/warehouseAccounts/productNums.ht?id=${materialItem.id}" class="link detail">供货商库存查询</a>
					<a href="javascript:void(0)" onclick="viewPriceStrategy(${materialItem.id})" class="link detail">返利折扣查询</a>
				</c:if>			
				<a href="get.ht?id=${materialItem.id}" class="link detail">明细</a>
					
					<%-- <a href = "#" onclick="changeAdd('add',${materialItem.id},this)" class="link detail">上架</a>
					<a href = "#" onclick="changeAdd('off',${materialItem.id},this)"class="link detail">下架</a>
					<a href="" class="link detail">供货商库存查询</a>
					<a href="" class="link detail">经销商库存查询</a></br>
					<a href="javascript:void(0)" onclick="searchDiscount(${materialItem.id})" class="link detail">返利折扣查询</a>
					<a href="javascript:void(0)" onclick="toPriceStrategy(${materialItem.id})" class="link detail">经销商价格策略管理</a>
					<a href="get.ht?id=${materialItem.id}" class="link detail">明细</a> --%>
					
				</display:column>
			</display:table>
			<%-- <hotent:paging tableId="materialItem"/> --%>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


