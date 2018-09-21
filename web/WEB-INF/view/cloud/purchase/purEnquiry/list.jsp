<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>采购询价单管理</title>
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
				<span class="tbar-label">采购询价单管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="create.ht">添加</a></div>
	<!-- 				<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<table id="mytable">
						<tr>
							<td>询价单编码:</td>
							<td width="180px" ><input type="text" name="Q_code_SL"  class="inputText" /></td>
							<td >制单日期:</td>
							<td><input  name="Q_beginoperateDate_DL"  class="inputText date" /><span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" /></td>
						</tr>
						
						<tr>
							<td>发布模式:</td>
							<td width="180px"><input type="text" name="Q_enterpriseName_SL"  class="inputText" /></td>
							<!-- 
							<td>供应商:</td>
							<td><input  name="Q_enterpriseName_DL"  class="inputText" /></td>
							 -->							
							<td>流程状态:</td>
							<td>
								<select name="Q_state_SE" class="inputText" onChange="this.form.submit();">
									<option value="">==所有状态==</option>									
									<option value="填写采购询价单">1-填写采购询价单</option> 
									<option value="采购主管审核">2-采购主管审核</option>
									<option value="填写销售报价单">3-填写销售报价单</option>
									<option value="采购优选">4-采购优选</option>
									<option value="下采购订单">5-下采购订单</option>
									<option value="生成销售订单">6-生成销售订单</option>
									<option value="下出货通知单">7-下出货通知单</option>
									<option value="待收货">8-待收货</option>
									<option value="分批入库">9-分批入库</option>
									<option value="全部入库">10-全部入库</option>
									<option value="已结算">11-已结算</option>
									<option value="已支付">12-已支付</option>
								</select>
							</td>
						</tr>
					</table>
						<!-- <span class="label">询价单编码:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">制单日期 从:</span> <input  name="Q_beginoperateDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endoperateDate_DG" class="inputText date" />
						
						<span class="label">制单人ID:</span><input type="text" name="Q_operatorId_SL"  class="inputText" />
						
						<span class="label">制单人:</span><input type="text" name="Q_operatorName_SL"  class="inputText" />
						<span class="label">发布日期 从:</span> <input  name="Q_beginreleaseDate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endreleaseDate_DG" class="inputText date" />
						<span class="label">发布模式:</span><input type="text" name="Q_releaseModel_SL"  class="inputText" />
						  
						<span class="label">来源单据类型:</span><input type="text" name="Q_sourceformType_SL"  class="inputText" />
						
						<span class="label">来源单据ID:</span><input type="text" name="Q_sourceformId_SL"  class="inputText" />
						
						<span class="label">来源单据号:</span><input type="text" name="Q_sourceformCode_SL"  class="inputText" />
						<span class="label">报价截止时间 从:</span> <input  name="Q_beginofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endofferEndtime_DG" class="inputText date" />
						<span class="label">报价处理截止时间 从:</span> <input  name="Q_begindoofferEndtime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_enddoofferEndtime_DG" class="inputText date" />
						 
						<span class="label">运费承担方:</span><input type="text" name="Q_freightBearer_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currencyType_SL"  class="inputText" />
						<span class="label">采购商联系人ID:</span><input type="text" name="Q_purenterpUserid_SL"  class="inputText" />
						<span class="label">采购商联系人:</span><input type="text" name="Q_purenterpUsername_SL"  class="inputText" />
						<span class="label">采购企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">采购企业:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						 -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="purEnquiryList" id="purEnquiryItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${purEnquiryItem.id}">
				</display:column>
				<display:column property="code" title="询价单编码" sortable="true" sortName="code"></display:column>
				<display:column  title="制单日期" sortable="true" sortName="operate_date">
					<fmt:formatDate value="${purEnquiryItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<%--  
				<display:column property="operatorId" title="制单人ID" sortable="true" sortName="operatorId"></display:column>
				--%>
				<display:column property="operatorName" title="制单人" sortable="true" sortName="operator_name"></display:column>
				<display:column  title="发布日期" sortable="true" sortName="release_date">
					<fmt:formatDate value="${purEnquiryItem.releaseDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column property="releaseModel" title="发布模式" sortable="true" sortName="release_model"></display:column>
				<%--   
				<display:column property="sourceformType" title="来源单据类型" sortable="true" sortName="sourceformType"></display:column>
				<display:column property="sourceformId" title="来源单据ID" sortable="true" sortName="sourceformId"></display:column>
				<display:column property="sourceformCode" title="来源单据号" sortable="true" sortName="sourceformCode"></display:column>
				--%>
				<display:column  title="报价截止时间" sortable="true" sortName="offer_endtime">
					<fmt:formatDate value="${purEnquiryItem.offerEndtime}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				<display:column  title="报价处理截止时间" sortable="true" sortName="dooffer_endtime"> --%>
<%-- 					<fmt:formatDate value="${purEnquiryItem.doofferEndtime}" pattern="yyyy-MM-dd"/> --%>
<%-- 				</display:column> --%>
				<%--  
				<display:column property="freightBearer" title="运费承担方" sortable="true" sortName="freightBearer"></display:column>
				<display:column property="currencyType" title="币种" sortable="true" sortName="currencyType"></display:column>
				<display:column property="purenterpUserid" title="采购商联系人ID" sortable="true" sortName="purenterpUserid"></display:column>
				<display:column property="purenterpUsername" title="采购商联系人" sortable="true" sortName="purenterpUsername"></display:column>
				<display:column property="enterpriseId" title="采购企业ID" sortable="true" sortName="enterpriseId"></display:column>
				<display:column property="enterpriseName" title="采购企业" sortable="true" sortName="enterpriseName"></display:column>
				--%>
				<display:column property="state" title="状态" sortable="true" sortName="state"></display:column>
				<display:column title="管理" media="html" style="width:160px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${purEnquiryItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${purEnquiryItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${purEnquiryItem.id}">
					</c:if>
					<c:if test="${purEnquiryItem.state=='1-填写采购询价单[追回]'}">
						<a href="del.ht?id=${purEnquiryItem.id}" class="link del">删除</a>
						<a href="create.ht?id=${purEnquiryItem.id}" class="link edit">编辑</a>
					</c:if>
										
					<a href="get.ht?id=${purEnquiryItem.id}&runid=${purEnquiryItem.runId}&status=${purEnquiryItem.runStatus}" class="link detail">明细</a>					
					 <a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${purEnquiryItem.runId}" class="link detail">流程示意图</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="purEnquiryItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


