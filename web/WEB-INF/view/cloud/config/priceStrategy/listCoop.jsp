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
				"discount":/^(0(\.\d+)?|1(\.0+)?)$/
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
			var flag = 0;
			$("[name=check]:checkbox").each(function(){
				if (this.checked) {
					var index = $("[name=check]:checkbox").index($(this)[0]);
					if(index>=0){
						flag = 1;
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
				}
			});
			if(flag==1){
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
			}else{
				alert("请选中一条记录再点击保存");			}
			
		});
	});
	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_price_strategy管理列表</span>
			</div>
			<c:if test="${type!='view'}">
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">供应商名称:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">经销商名称:</span><input type="text" name="Q_coopenterpName_SL"  class="inputText" />
						<span class="label">物品名称:</span><input type="text" name="Q_materialName_SL"  class="inputText" />
					</div>
				</form>
			</div>
			</c:if>
		</div>
		<div class="panel-body">
	    	<c:if test="${type!='view'}">
			<form action="" method="post" id="priceStrategyForm">
			<div class="panel-toolbar">
				<div class="toolBar">
				
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link save" id="saveAll" href="javascript:void(0)">保存</a></div>
					<div class="l-bar-separator"></div>
				
					<!-- <div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			</c:if>
			<table name="priceStrategyList" id="priceStrategyItem" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<thead>
					<c:if test="${type!='view'}">
						<th style="text-align: center"><input type="checkbox" name="id" id="checkall"></th>
					</c:if>	
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
							<c:if test="${type!='view'}">
								<td style="text-align: center"><input type="checkbox" name="check" value="${priceStrategyItem.id}"></td>
							</c:if>
							<td name="id" style="display:none">${priceStrategyItem.id}</td>
							<td name="enterpriseId" style="display:none">${priceStrategyItem.enterpriseId}</td>
							<td name="enterpriseName" style="text-align: center">${priceStrategyItem.enterpriseName}</td>
							<td name="coopenterpId" style="display:none">${priceStrategyItem.coopenterpId}</td>
							<td name="coopenterpName" style="text-align: center">${priceStrategyItem.coopenterpName}</td>
							<td name="materialId" style="display:none">${priceStrategyItem.materialId}</td>
							<td name="materialName" style="text-align: center">${priceStrategyItem.materialName}</td>
							<c:if test="${type!='view'}">
								<td style="text-align: center"><input name="number" type="text" value="${priceStrategyItem.number}" datatype="n"/></br><span id="numberTip"></span></td>
								<td style="text-align: center">
									<ap:select name="season" style="width:60px;text-align: center" selectedValue="${priceStrategyItem.season}">
										<ap:option value="spring">春</ap:option>
										<ap:option value="summer">夏</ap:option>
										<ap:option value="autumn">秋</ap:option>
										<ap:option value="winter">冬</ap:option>
									</ap:select>
								</td>
								<td style="text-align: center"><input name="discount" type="text" value="${priceStrategyItem.discount}" datatype="discount"/></br><span id="discountTip"></span></td>
							</c:if>
							<c:if test="${type=='view'}">
								<td style="text-align: center">${priceStrategyItem.number}</td>
								<td style="text-align: center">
									<c:if test="${priceStrategyItem.season=='spring'}">春</c:if>
									<c:if test="${priceStrategyItem.season=='summer'}">夏</c:if>
									<c:if test="${priceStrategyItem.season=='autumn'}">秋</c:if>
									<c:if test="${priceStrategyItem.season=='winter'}">冬</c:if>
								</td>
								<td style="text-align: center">${priceStrategyItem.discount}</td>
							</c:if>
							
							<input type="hidden" name="id" value="${priceStrategyItem.id}"/>
							<input type="hidden" name="enterpriseId" value="${priceStrategyItem.enterpriseId}"/>
							<input type="hidden" name="enterpriseName" value="${priceStrategyItem.enterpriseName}"/>
							<input type="hidden" name="coopenterpId" value="${priceStrategyItem.coopenterpId}"/>
							<input type="hidden" name="coopenterpName" value="${priceStrategyItem.coopenterpName}"/>
							<input type="hidden" name="materialId" value="${priceStrategyItem.materialId}"/>
							<input type="hidden" name="materialName" value="${priceStrategyItem.materialName}"/>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			</form>
			<hotent:paging tableId="priceStrategyItem"/>
			
		    <<%-- display:table name="priceStrategyList" id="priceStrategyItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${priceStrategyItem.id}">
				</display:column>
				<display:column property="enterpriseId" title="供应商id" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="enterpriseName" title="供应商名称" sortable="true" sortName="enterprise_name" maxLength="80"></display:column>
				<display:column property="coopenterpId" title="经销商id" sortable="true" sortName="coopenterp_id"></display:column>
				<display:column property="coopenterpName" title="经销商名称" sortable="true" sortName="coopenterp_name" maxLength="80"></display:column>
				<display:column property="materialId" title="物品id" sortable="true" sortName="material_id"></display:column>
				<display:column property="materialName" title="物品名称" sortable="true" sortName="material_name" maxLength="80"></display:column>
				<display:column property="number" title="数量" sortable="true" sortName="number"><input name="number" type="text" value="123123"/></display:column>
				<display:column property="season" title="季节" sortable="true" sortName="season" maxLength="80" >
					<select name="season">
						<option value="spring">春</option>
						<option value="summer">夏</option>
						<option value="autumn">秋</option>
						<option value="winter">冬</option>
					</select>
				</display:column>
				<display:column property="discount" title="折扣" sortable="true" sortName="discount"><input name="discount" type="text"/></display:column>
				<display:column title="操作" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${priceStrategyItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${priceStrategyItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${priceStrategyItem.id}">
					</c:if>
					<a href="get.ht?id=${priceStrategyItem.id}&runid=${priceStrategyItem.runid}&status=${priceStrategyItem.runStatus}" class="link detail">明细</a>
					<button>确定</button>
				</display:column>
			</display:table>
			<hotent:paging tableId="priceStrategyItem"/> --%>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


