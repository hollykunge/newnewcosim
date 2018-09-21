<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>上门维修服务管理列表</title>
<%@include file="/commons/include/get.jsp" %>

</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">上门维修服务管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">任务单号:</span><input type="text" name="Q_code_SL"  class="inputText" />
						<span class="label">任务状态:</span>
						<select name="Q_state_SE" class="inputText"  >
							<option value="">==所有状态==</option>									
							<option value="1-客户报修">1-客户报修</option> 
							<option value="2-填写维修任务单">2-填写维修任务单</option> 
							<option value="3-向客户报价">3-向客户报价</option>
							<option value="4-确认维修费用">4-确认维修费用</option>
							<option value="5-指派维修人员">5-指派维修人员</option>
							<option value="6-上门维修">6-上门维修</option>
							<option value="7-售后主管审核">7-售后主管审核</option>
							<option value="已经结束">==已经结束==</option>
						</select>
						<!-- <span class="label">务任来源:</span><input type="text" name="Q_source_SL"  class="inputText" />
						<span class="label">sourceid:</span><input type="text" name="Q_sourceid_SL"  class="inputText" />
						<span class="label">用户反馈ID:</span><input type="text" name="Q_feedbackId_SL"  class="inputText" />
						<span class="label">用户反馈名称:</span><input type="text" name="Q_feedbackName_SL"  class="inputText" /> -->
						<span class="label">维修日期 从:</span> <input  name="Q_begintdate_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endtdate_DG" class="inputText date" />
						<!-- <span class="label">产品编码:</span><input type="text" name="Q_prodcode_SL"  class="inputText" />
						<span class="label">产品名称:</span><input type="text" name="Q_prodname_SL"  class="inputText" />
						<span class="label">产品型号:</span><input type="text" name="Q_prodmodel_SL"  class="inputText" />
						<span class="label">问题描述:</span><input type="text" name="Q_descn_SL"  class="inputText" />
						<span class="label">修维厂商ID:</span><input type="text" name="Q_purenterpId_SL"  class="inputText" />
						<span class="label">修维厂名称:</span><input type="text" name="Q_purenterName_SL"  class="inputText" />
						<span class="label">维修人员ID:</span><input type="text" name="Q_accendantId_SL"  class="inputText" />
						<span class="label">维修人员名称:</span><input type="text" name="Q_accendantName_SL"  class="inputText" />
						<span class="label">备注:</span><input type="text" name="Q_remark_SL"  class="inputText" />
						<span class="label">质检意见:</span><input type="text" name="Q_qualityopinion_SL"  class="inputText" />
						<span class="label">售后主管意见:</span><input type="text" name="Q_serviceopinion_SL"  class="inputText" />
						<span class="label">服务厂商已见:</span><input type="text" name="Q_serviceenopinion_SL"  class="inputText" />
						<span class="label">主机厂意见:</span><input type="text" name="Q_enterpriseopinion_SL"  class="inputText" />
						<span class="label">状态:</span><input type="text" name="Q_statu_SL"  class="inputText" />
						<span class="label">业企Id:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">业企名称:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">run_id:</span><input type="text" name="Q_runId_SL"  class="inputText" />
						<span class="label">tasktype:</span><input type="text" name="Q_tasktype_SL"  class="inputText" />
						<span class="label">是否过保:</span><input type="text" name="Q_isEnsure_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
		<div tabid="home" title="企业基本资料(必填)" lselected="true">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="maitainTaskList" id="maitainTaskItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${maitainTaskItem.id}">
				</display:column>
				<display:column property="code" title="任务单号" sortable="true" sortName="code"></display:column>
				<%-- <display:column property="source" title="务任来源" sortable="true" sortName="source"></display:column>
				<display:column property="sourceid" title="sourceid" sortable="true" sortName="sourceid"></display:column>
				<display:column property="feedbackId" title="用户反馈ID" sortable="true" sortName="feedback_id"></display:column> --%>
				<display:column property="feedbackName" title="用户反馈名称" sortable="true" sortName="feedback_name"></display:column>
				<display:column  title="维修日期" sortable="true" sortName="tdate">
					<fmt:formatDate value="${maitainTaskItem.tdate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="prodcode" title="产品编码" sortable="true" sortName="prodcode"></display:column>
<%-- 				<display:column property="prodname" title="产品名称" sortable="true" sortName="prodname"></display:column> --%>
<%-- 				<display:column property="prodmodel" title="产品型号" sortable="true" sortName="prodmodel"></display:column> --%>
				<display:column property="accendantName" title="维修人员名称" sortable="true" sortName="accendant_name"></display:column>
				<display:column property="state" title="任务状态" sortable="true" sortName="state"></display:column>
				<%-- <display:column property="descn" title="问题描述" sortable="true" sortName="descn"></display:column>
				<display:column property="purenterpId" title="修维厂商ID" sortable="true" sortName="purenterp_id" maxLength="80"></display:column>
				<display:column property="purenterName" title="修维厂名称" sortable="true" sortName="purenter_name"></display:column>
				<display:column property="accendantId" title="维修人员ID" sortable="true" sortName="accendant_id"></display:column>
				
				<display:column property="remark" title="备注" sortable="true" sortName="remark" maxLength="80"></display:column>
				<display:column property="qualityopinion" title="质检意见" sortable="true" sortName="qualityopinion" maxLength="80"></display:column>
				<display:column property="serviceopinion" title="售后主管意见" sortable="true" sortName="serviceopinion" maxLength="80"></display:column>
				<display:column property="serviceopinion" title="服务厂商已见" sortable="true" sortName="serviceEnopinion" maxLength="80"></display:column>
				<display:column property="enterpriseopinion" title="主机厂意见" sortable="true" sortName="enterPriseopinion" maxLength="80"></display:column>
				<display:column property="enterpriseId" title="业企Id" sortable="true" sortName="enterprise_id"></display:column>
				<display:column property="enterpriseName" title="业企名称" sortable="true" sortName="enterprise_name"></display:column>
				<display:column property="runId" title="run_id" sortable="true" sortName="run_id"></display:column>
				<display:column property="tasktype" title="tasktype" sortable="true" sortName="tasktype"></display:column>
				<display:column property="isEnsure" title="是否过保" sortable="true" sortName="is_ensure"></display:column> --%>
				<display:column title="管理" media="html" style="width:180px">
					<%-- <a href="del.ht?id=${maitainTaskItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${maitainTaskItem.id}" class="link edit">编辑</a> --%>
					<a href="get.ht?id=${maitainTaskItem.id}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${maitainTaskItem.runId}" class="link detail">流程示意图</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="maitainTaskItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


