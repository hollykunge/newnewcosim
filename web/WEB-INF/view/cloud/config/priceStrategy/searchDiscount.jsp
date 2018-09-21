<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ include file="/commons/cloud/global.jsp"%>
<html>
<head>
<title>cloud_price_strategy管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@ include file="/commons/cloud/meta.jsp"%>
<link href="${ctx}/skins/validform.css"  rel="stylesheet"/>
<script type="text/javascript">
	$(function() {
		$("a.apply").click(function() {
			$.ajax({
				type : "POST",
				url : 'apply.ht?id='+$('#userVacateId').val(),
				success : function(res) {
					var result = eval('('+res+')');
					$.ligerMessageBox.success('提示信息',result.message);
					window.location.href = "list.ht";
				},
				error : function(res) {
					$.ligerMessageBox.error('提示信息',result.message);
				}
			});
		});
		$("#priceStrategyForm").Validform({
			tiptype:3,
			label:".label",
			showAllError:true,
			datatype:{
				"discount":/^(0\.[0-9]+)|1$/
			},
			tipmsg:{
				w:{
					"discount":"请输入正确折扣"
				}
			}
		});
		
	});
	 /**
	*全选/反选
	*/
	$(function(){
		$("#checkall").click(function(){
			var state = $("#checkall").attr("checked");
			if (state=='checked') {
				 $("[name=check]:checkbox").attr("checked", true);
			}else{
				 $("[name=check]:checkbox").attr("checked", false);
			}
		});
		$("#saveAll").click(function(){
			var ids = "";
			var numbers="";
			var seasons = "";
			var discounts="";
			$("[name=check]:checkbox").each(function(){
				if (this.checked) {
					var index = $("[name=check]:checkbox").index($(this)[0]);
					var id = $("#priceStrategyItem tbody tr:eq("+index+") td:eq(1)").text();
					var number = $("#priceStrategyItem tbody tr:eq("+index+") td:eq(8) input[name='number']").val();
					var season = $("#priceStrategyItem tbody tr:eq("+index+") td:eq(9) select[name='season']").val();
					var discount = $("#priceStrategyItem tbody tr:eq("+index+") td:eq(10) input[name='discount']").val();
					if(ids==""){
						ids = id;
					}else{
						ids = ids+","+id;
					}
					if(numbers==""){
						numbers = number;
					}else{
						numbers = numbers+","+number;
					}
					if(seasons==""){
						seasons = season;
					}else{
						seasons = seasons+","+season;
					}
					if(discounts==""){
						discounts = discount;
					}else{
						discounts = discounts+","+discount;
					}
				}
			});
			$.ajax({
				url:"updatePriceStrategy.ht",
				type:"post",
				data:{ids:ids,numbers:numbers,seasons:seasons,discounts:discounts},
				success:function(data){
					if(data){
						window.location="${ctx}/cloud/config/material/list_order.ht";
					}else{
						alert("操作失败");
					}
				}
				
			});
		});
	});
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">折扣查询表</span>
			</div>
		</div>
		<div class="panel-body">
	    	
			
			<table name="priceStrategyList" id="priceStrategyItem" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<thead>
					<th style="display:none">id</th>
					<th style="display:none">供应商id</th>
					<th>供应商名称</th>
					<th style="display:none">经销商id</th>
					<th>经销商名称</th>
					<th style="display:none">物品id</th>
					<th>物品名称</th>
					<th>数量</th>
					<th>季节</th>
					<th>折扣</th>
				</thead>
				<tbody>
					<c:forEach items="${priceStrategyList}" var="priceStrategyItem">
						<tr>
							<td name="id" style="display:none">${priceStrategyItem.id}</td>
							<td name="enterpriseId" style="display:none">${priceStrategyItem.enterpriseId}</td>
							<td name="enterpriseName" style="text-align: center">${priceStrategyItem.enterpriseName}</td>
							<td name="coopenterpId" style="display:none">${priceStrategyItem.coopenterpId}</td>
							<td name="coopenterpName" style="text-align: center">${priceStrategyItem.coopenterpName}</td>
							<td name="materialId" style="display:none">${priceStrategyItem.materialId}</td>
							<td name="materialName" style="text-align: center">${priceStrategyItem.materialName}</td>
							<td style="text-align: center">${priceStrategyItem.number}</td>
							<td style="text-align: center">
								<c:if test="${priceStrategyItem.season=='spring'}">春</c:if>
								<c:if test="${priceStrategyItem.season=='summer'}">夏</c:if>
								<c:if test="${priceStrategyItem.season=='autumn'}">秋</c:if>
								<c:if test="${priceStrategyItem.season=='winter'}">冬</c:if>
								<%-- <ap:select name="season" style="width:60px;text-align: center" selectedValue="${priceStrategyItem.season}">
									<ap:option value="spring">春</ap:option>
									<ap:option value="summer">夏</ap:option>
									<ap:option value="autumn">秋</ap:option>
									<ap:option value="winter">冬</ap:option>
								</ap:select> --%>
							</td>
							<td style="text-align: center">${priceStrategyItem.discount}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>

		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


