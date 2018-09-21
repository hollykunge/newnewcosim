
<%--
	time:2013-04-26 14:32:24
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>营销合作计划明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">营销合作计划详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th style="width:120px">单证号:</th>
				<td colspan="3">${saleCopPlan.code}</td>
			</tr>
 
			
 
			<tr>
				<th style="width:120px">制单人ID:</th>
				<td>${saleCopPlan.operaterId}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px">制单人:</th>
				<td>${saleCopPlan.operaterName}</td>
				<th style="width:120px">发布模式:</th>
				<td>${saleCopPlan.deliveryType}</td>
			</tr>
 
			<tr>
				<th style="width:120px">发布时间:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.deliveryDate}" pattern="yyyy-MM-dd"/>
				</td>
				<th style="width:120px">回复截止时间:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.offerEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			
 
			<tr>
				<th style="width:120px">合作营销区域:</th>
                      <td>					   
					    <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					
					</select></td>
				<th style="width:120px">处理截止时间:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.doofferEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px">合作开始时间:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.cooperationStarttime}" pattern="yyyy-MM-dd"/>
				</td>
				<th style="width:120px">合作截止时间:</th>
				<td>
				<fmt:formatDate value="${saleCopPlan.cooperationEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width:120px">上报库存信息时间间隔:</th>
				<td>${saleCopPlan.reportstockTimespace}</td>
				<th style="width:120px">上报销售订单时间间隔:</th>
				<td>${saleCopPlan.reportsalesTimespace}</td>
			</tr>
 
			<tr>
				<th style="width:120px">附件:</th>
				<td>${saleCopPlan.attachment}</td>
				<th style="width:120px">企业ID:</th>
				<td>${saleCopPlan.enterpriseId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">企业:</th>
				<td>${saleCopPlan.enterpriseName}</td>
				<th style="width:120px">企业联系人:</th>
				<td>${saleCopPlan.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th style="width:120px">企业联系人ID:</th>
				<td>${saleCopPlan.enterpriseUserid}</td>
				<th style="width:120px">合作类型：代理/经销/联营:</th>
					<td>${saleCopPlan.type}</td>
	    		</tr>
 
			<tr>
				<th style="width:120px">资质要求说明（逗号分隔）:</th>
				<td colspan="3">${saleCopPlan.qualifications}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="10" style="text-align: center">营销合作计划明细</td>
			</tr>
			<tr>
				<th>物品ID</th>
				<th>物品名</th>
				<th>物品编码</th>
				<th>编码参考附件</th>
				<th>分类</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>合作销售数量</th>
				<th>首批进货量</th>
				<th>合作单价</th>
			</tr>	
			<c:forEach items="${saleCopPlanDetailList}" var="saleCopPlanDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleCopPlanDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${saleCopPlanDetailItem.materialId}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.materialName}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.materialCode}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.materialCodenote}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.materielLev}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.unit}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.materielNum}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.firstNumber}</td>								
						<td style="text-align: center">${saleCopPlanDetailItem.prise}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

