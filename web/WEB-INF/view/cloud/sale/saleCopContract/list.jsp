<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>营销合同管理</title>
<%@include file="/commons/include/get.jsp" %>

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
	});
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">经销合同管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
 					<div class="l-bar-separator"></div>
	<!--				<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>-->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">制单人:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
					<!--	<span class="label">合作营销区域:</span><input type="text" name="Q_cooperationArea_SL"  class="inputText" />
						<span class="label">制单人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">合作开始时间 从:</span> <input  name="Q_begincooperationStarttime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcooperationStarttime_DG" class="inputText date" />
						<span class="label">合作截止时间 从:</span> <input  name="Q_begincooperationEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcooperationEndtime_DG" class="inputText date" />
						<span class="label">库存上报时间间隔:</span><input type="text" name="Q_reportstockTimespace_SL"  class="inputText" />
						<span class="label">销售订单上报时间间隔:</span><input type="text" name="Q_reportsalesTimespace_SL"  class="inputText" />
						<span class="label">信用额度下限(金额）:</span><input type="text" name="Q_creditEndline_SL"  class="inputText" />
						<span class="label">附件:</span><input type="text" name="Q_attachment_SL"  class="inputText" />
						<span class="label">供应企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">供应企业:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">供应企业联系人ID:</span><input type="text" name="Q_enterpriseUserid_SL"  class="inputText" />
						<span class="label">供应企业联系人:</span><input type="text" name="Q_enterpriseUsername_SL"  class="inputText" />
						<span class="label">合作企业ID:</span><input type="text" name="Q_coopenterpId_SL"  class="inputText" />
						<span class="label">合作企业:</span><input type="text" name="Q_coopenterpName_SL"  class="inputText" />
						<span class="label">合作企业联系人ID:</span><input type="text" name="Q_coopenterpUserid_SL"  class="inputText" />
						<span class="label">合作企业联系人:</span><input type="text" name="Q_coopenterpUsername_SL"  class="inputText" />
						  -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="saleCopContractList" id="saleCopContractItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${saleCopContractItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${saleCopContractItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operaterName" title="制单人" sortable="true" sortName="operater_name"></display:column>
	    	<%-- 	<display:column property="cooperationArea" title="合作营销区域" sortable="true" sortName="cooperation_area"></display:column>
				<display:column property="operaterId" title="制单人ID" sortable="true" sortName="operater_id"></display:column>
				<display:column  title="合作开始时间" sortable="true" sortName="cooperation_starttime">
					<fmt:formatDate value="${saleCopContractItem.cooperationStarttime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="合作截止时间" sortable="true" sortName="cooperation_endtime">
					<fmt:formatDate value="${saleCopContractItem.cooperationEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="reportstockTimespace" title="库存上报时间间隔" sortable="true" sortName="reportstock_timespace"></display:column>
				<display:column property="reportsalesTimespace" title="销售订单上报时间间隔" sortable="true" sortName="reportsales_timespace"></display:column>
				<display:column property="creditEndline" title="信用额度下限(金额）" sortable="true" sortName="credit_endline"></display:column>
				<display:column property="enterpriseUserid" title="供应企业联系人ID" sortable="true" sortName="enterprise_userid"></display:column>
				<display:column property="enterpriseUsername" title="供应企业联系人" sortable="true" sortName="enterprise_username"></display:column>
				<display:column property="attachment" title="附件" sortable="true" sortName="attachment" maxLength="80"></display:column>
				<display:column property="coopenterpUserid" title="合作企业联系人ID" sortable="true" sortName="coopenterp_userid"></display:column>
				<display:column property="enterpriseId" title="供应企业ID" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="coopenterpId" title="合作企业ID" sortable="true" sortName="coopenterp_id"></display:column>
				--%>
				<display:column property="enterpriseName" title="供应企业" sortable="true" sortName="enterprise_name"></display:column>
				
				<display:column property="coopenterpName" title="合作企业" sortable="true" sortName="coopenterp_name"></display:column>
				<display:column property="coopenterpUsername" title="合作企业联系人" sortable="true" sortName="coopenterp_username"></display:column>
				<display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${saleCopContractItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${saleCopContractItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${saleCopContractItem.id}">
					</c:if>
					<a href="get.ht?id=${saleCopContractItem.id}&runid=${saleCopContractItem.runid}&status=${saleCopContractItem.runStatus}" class="link detail">明细</a>
					<c:if test="${currentEnpId == saleCopContractItem.enterpriseId}">
						<a href="${ctx}/cloud/sale/saleOrder/sale_1.ht?id=${saleCopContractItem.id}&runid=${saleCopContractItem.runid}&status=${saleCopContractItem.runStatus}" class="link detail">向经销商发货</a>
						<c:if test="${saleCopContractItem.saleRepository==1}">
							<a href="${ctx}/cloud/sale/saleCopContract/salesView.ht?contractId=${saleCopContractItem.id}" class="link detail">销售情况查询</a>	
						</c:if>
					</c:if>
					<c:if test="${currentEnpId != saleCopContractItem.enterpriseId}">
						<a href="${ctx}/cloud/purchase/purOrder/dir_purorder.ht?id=${saleCopContractItem.id}&runid=${saleCopContractItem.runid}&status=${saleCopContractItem.runStatus}" class="link detail">采购提货</a>
					</c:if>
					<a href="${ctx}/cloud/sale/saleCredit/list.ht" class="link detail">经销商信用额度查询与设定</a>
					<a href="${ctx}/cloud/sale/saleCreditDetail/list.ht" class="link detail">经销商赊销记录查看</a>	
				</display:column>
			</display:table>
			<hotent:paging tableId="saleCopContractItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


