<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>cloud_sale_credit_detail管理</title>
<%@include file="/commons/include/get.jsp" %>

<script type="text/javascript">

$(function(){
	$('#friends').change(function(){
		var tgtEntId = $(this).val();
		location.href='?ent_id=' + tgtEntId;
	});
	
});
$(function(){
	$('#friends').val(${tgtEntId}); 
})
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
				<span class="tbar-label">经销商赊销管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<!--<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>-->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">赊销企业:</span>
						
						<select id="friends" name="friends" >
							<option value="0">--请选择企业--</option>
							<c:forEach items="${friends}" var="friends">
								<option value="${friends.corpEnterprise.sysOrgInfoId}">${friends.corpEnterprise.name} </option>
							</c:forEach>
						</select>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="saleCreditDetailList" id="saleCreditDetailItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${saleCreditDetailItem.id}">
				</display:column>
				<display:column property="code" title="订单号" sortable="true" sortName="code" maxLength="80"></display:column>
				<display:column property="enterpriseName" title="供应企业" sortable="true" sortName="enterprise_name" maxLength="80"></display:column>
				<display:column property="coopenterpriseName" title="合作企业" sortable="true" sortName="coopenterprise_name" maxLength="80"></display:column>
				<display:column property="totleMoney" title="赊销金额" sortable="true" sortName="totle_money"></display:column>
				<display:column title="管理" media="html" style="width:180px">
				<a href="${ctx}/cloud/purchase/purOrder/get5.ht?id=${saleCreditDetailItem.purorderId}&runid=${saleCreditDetailItem.runid}&status=${saleCreditDetailItem.runStatus}" class="link detail">明细</a>
				<c:if test="${saleCreditDetailItem.type == '1' }">
					<a href="save1.ht?id=${saleCreditDetailItem.id}" class="link del">删除</a>
				</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="saleCreditDetailItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


