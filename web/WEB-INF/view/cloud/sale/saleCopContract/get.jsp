
<%--
	time:2013-04-26 14:32:25
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>营销合同明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">营销合同详细信息</span>
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
				<td>${saleCopContract.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${saleCopContract.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${saleCopContract.operaterId}</td>
			</tr>
 
			<tr>
				<th width="20%">合作类型:</th>
				<td>${saleCopContract.copType}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${saleCopContract.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">合作营销区域:</th>
				<td>   <select id="cooperationArea" name="cooperationArea">
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
			</tr>
 
			<tr>
				<th width="20%">合作开始时间:</th>
				<td>
				<fmt:formatDate value="${saleCopContract.cooperationStarttime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">合作截止时间:</th>
				<td>
				<fmt:formatDate value="${saleCopContract.cooperationEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">库存上报时间间隔:</th>
				<td>${saleCopContract.reportstockTimespace}</td>
			</tr>
 
			<tr>
				<th width="20%">销售订单上报时间间隔:</th>
				<td>${saleCopContract.reportsalesTimespace}</td>
			</tr>
 
			<tr>
				<th width="20%">信用额度下限(金额）:</th>
				<td>${saleCopContract.creditEndline}</td>
			</tr>
 
			<tr>
				<th width="20%">附件:</th>
				<td>${saleCopContract.attachment}</td>
			</tr>
 
			<tr>
				<th width="20%">供应企业ID:</th>
				<td>${saleCopContract.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">供应企业:</th>
				<td>${saleCopContract.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">供应企业联系人ID:</th>
				<td>${saleCopContract.enterpriseUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">供应企业联系人:</th>
				<td>${saleCopContract.enterpriseUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业ID:</th>
				<td>${saleCopContract.coopenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业:</th>
				<td>${saleCopContract.coopenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业联系人ID:</th>
				<td>${saleCopContract.coopenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业联系人:</th>
				<td>${saleCopContract.coopenterpUsername}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="11" style="text-align: center">营销合同 :营销合同详情</td>
			</tr>
			<tr>
				<th>物品ID</th>
				<th>物品</th>
				<th>编码</th>
				<th>编码参考标准</th>
				<th>分类</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>数量</th>
				<th>信用额度（数量）</th>
				<th>单价</th>
				<th>库存下限</th>
			</tr>	
			<c:forEach items="${saleCopContractDetailList}" var="saleCopContractDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleCopContractDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${saleCopContractDetailItem.materielId}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.materielName}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.materielCode}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.materielCodenote}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.materielLev}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.unit}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.materielNum}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.creditLevel}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.prise}</td>								
						<td style="text-align: center">${saleCopContractDetailItem.stockEndline}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

