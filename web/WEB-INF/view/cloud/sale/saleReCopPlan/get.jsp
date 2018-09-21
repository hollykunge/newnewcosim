
<%--
	time:2013-04-26 14:32:25
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>营销计划回复单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">营销计划回复单详细信息</span>
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
				<th width="20%">单证号:</th>
				<td>${saleReCopPlan.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${saleReCopPlan.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${saleReCopPlan.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_type:</th>
				<td>${saleReCopPlan.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_id:</th>
				<td>${saleReCopPlan.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">sourceform_code:</th>
				<td>${saleReCopPlan.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">发布模式：公开/邀请:</th>
				<td>${saleReCopPlan.deliveryType}</td>
			</tr>
 
			<tr>
				<th width="20%">发布时间:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.deliveryDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">回复截止时间:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.offerEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">处理截止时间:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.doofferEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">合作营销区域:</th>
				<td>    <select id="cooperationArea" name="cooperationArea">
					   <c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==1}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='1'  ${selected}>华中</option>
						<c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==2}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='2' ${selected}>华南</option>
						<c:remove var="selected"/>
					    <c:if test="${saleReCopPlan.cooperationArea==3}">
					    <c:set var="selected" value="selected" />
					    </c:if>
						<option value='3' ${selected}>华北</option>
					</select></td>
			</tr>
 
			<tr>
				<th width="20%">合作开始时间:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.cooperationStarttime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">合作截止时间:</th>
				<td>
				<fmt:formatDate value="${saleReCopPlan.cooperationEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">上报库存信息时间间隔:</th>
				<td>${saleReCopPlan.reportstockTimespace}</td>
			</tr>
 
			<tr>
				<th width="20%">上报销售订单时间间隔:</th>
				<td>${saleReCopPlan.reportsalesTimespace}</td>
			</tr>
 
			<tr>
				<th width="20%">说明:</th>
				<td>${saleReCopPlan.comments}</td>
			</tr>
 
			<tr>
				<th width="20%">企业ID:</th>
				<td>${saleReCopPlan.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">企业:</th>
				<td>${saleReCopPlan.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">企业联系人:</th>
				<td>${saleReCopPlan.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">enterprise_userid:</th>
				<td>${saleReCopPlan.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">合作类型：代理/经销/联营:</th>
				<td>${saleReCopPlan.type}</td>
			</tr>
 
			<tr>
				<th width="20%">copenterprise_id:</th>
				<td>${saleReCopPlan.copenterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">copenterprise_name:</th>
				<td>${saleReCopPlan.copenterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">copenterprise_userid:</th>
				<td>${saleReCopPlan.copenterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业联系人:</th>
				<td>${saleReCopPlan.copenterpriseUsername}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="12" style="text-align: center">营销计划回复单 :营销计划回复单详情</td>
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
				<th>价格区间（+-%）</th>
				<th>结算方式（返点/返利）</th>
			</tr>	
			<c:forEach items="${saleReCopPlanDetailList}" var="saleReCopPlanDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleReCopPlanDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${saleReCopPlanDetailItem.materialId}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.materialName}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.materialCode}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.materialCodenote}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.materielLev}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.unit}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.materielNum}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.firstNumber}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.prise}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.priseRegion}</td>								
						<td style="text-align: center">${saleReCopPlanDetailItem.accountType}</td>								
				</tr>
			</c:forEach>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="3" style="text-align: center">营销计划回复单 :营销计划回复单_企业资质</td>
			</tr>
			<tr>
				<th>资质</th>
				<th>资质附件</th>
				<th>资质说明</th>
			</tr>	
			<c:forEach items="${saleReCopPlanQualificationList}" var="saleReCopPlanQualificationItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleReCopPlanQualificationItem.id}"  class="inputText"/>
						<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationName}</td>								
						<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationAttachment}</td>								
						<td style="text-align: center">${saleReCopPlanQualificationItem.qualificationNote}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

