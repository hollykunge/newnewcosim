<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购优选单管理</title>
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
				<span class="tbar-label">采购优选单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<!--  
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
					-->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
				
					<div class="row">
					<table id="mytable">
						<tr>
							<td>单证号:</td>
							<td width="180px" ><input type="text" name="Q_code_SL"  class="inputText" /></td>
							<td >制单日期:</td>
							<td><input  name="Q_beginoperateDate_DL"  class="inputText date" />
							<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></td>
						</tr>
					</table>
						<!-- <span class="label">单证号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						<span class="label">制单人:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">处理截止日期 从:</span> <input  name="Q_begindoofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddoofferEndtime_DG" class="inputText date" />
						<span class="label">制单人ID:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
						<span class="label">中标供应商名:</span><input type="text" name="Q_supplierName_SL"  class="inputText" />
						<span class="label">中标供应商ID:</span><input type="text" name="Q_supplierId_SL"  class="inputText" />
						<span class="label">中标报价单ID:</span><input type="text" name="Q_quoteformId_SL"  class="inputText" />
						<span class="label">中标报价单code:</span><input type="text" name="Q_quoteformCode_SL"  class="inputText" />
					 -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="purOptimizeList" id="purOptimizeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${purOptimizeItem.id}">
				</display:column>
				<display:column property="code" title="单证号" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operateDate">
					<fmt:formatDate value="${purOptimizeItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="operatorName" title="制单人" sortable="true" sortName="operator_name"></display:column>
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceform_type"></display:column>
				<%-- 
				<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="sourceform_id"></display:column>
				--%>
				<display:column property="sourceformCode" title="来源单据号" sortable="true" sortName="sourceform_code"></display:column>
				<%-- <display:column  title="处理截止日期" sortable="true" sortName="doofferEndtime" >
					<fmt:formatDate value="${purOptimizeItem.doofferEndtime}" pattern="yyyy-MM-dd"/>
				</display:column> --%>
				<%--  
				<display:column property="operatorId" title="制单人ID" sortable="true" sortName="operator_d"></display:column>
				--%>
				<display:column property="supplierName" title="中标供应商名" sortable="true" sortName="supplier_ame"></display:column>
<%-- 				<display:column property="supplierId" title="中标供应商ID" sortable="true" sortName="supplier_id" ></display:column>
 --%>				<%--
				<display:column property="quoteformId" title="中标报价单ID" sortable="true" sortName="quoteform_id"></display:column>
				--%>
				<display:column property="quoteformCode" title="中标报价单code" sortable="true" sortName="quoteform_code"></display:column>
				<display:column title="管理" media="html" style="white-space:nowrap;">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${purOptimizeItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${purOptimizeItem.id}" class="link edit">编辑</a>			
						
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${purOptimizeItem.id}">
					</c:if>
					<a href="get.ht?id=${purOptimizeItem.id}&runid=${purOptimizeItem.runid}&status=${purOptimizeItem.runStatus}" class="link detail">明细</a>
				
					<%-- <a href="${ctx}/platform/bpm/taskInfo/startFlowForm.ht?defId=10000000240000&resultId=${purOptimizeItem.id}" class="link detail">生成采购订单</a> --%>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="purOptimizeItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


