<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>研发众包需求管理</title>
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
				<span class="tbar-label">研发众包需求管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="create.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
					<table id="mytable">
						<tr>
							<td>需求编号:</td>
							<td width="180px"><input type="text" name="Q_code_SL"  class="inputText" /></td>						
							<td>需求名称:</td>
							<td><input type="text" name="Q_requireName_SL"  class="inputText" /></td>					
							<td>发布模式:</td>
						    <td><input type="text" name="Q_publishMode_SL"  class="inputText" /></td>
						</tr>
					
						<!-- <span class="label">需求分类:</span><input type="text" name="Q_requireType_SL"  class="inputText" />
						<span class="label">需求详细描述:</span><input type="text" name="Q_requireDescription_SL"  class="inputText" /> -->
						
						<tr>
							<td>流程状态:</td>
							<td width="180px">
								<select name="Q_status_SL" class="inputText" onChange="this.form.submit();">
									<option value="">==所有状态==</option>									
									<option>1-创建需求表</option> 
									<option>2-响应需求，提交报价</option>
									<option>3-审核，确认接包方</option>
									<option>4-下发合同</option>
									<option>5-确认合同</option>
									<option>6-开展研发，提交研发结果</option>
									<option>7-审查研发结果，确认完成</option>
									<option value="已经结束">==已经结束==</option>
								</select>
							</td>

							<td>创建时间 从:</td> 
							<td width="180px"><input  name="Q_beginoperateDate_DL"  class="inputText date" /></td>
							<td>至: </td>
							<td><input  name="Q_endoperateDate_DG" class="inputText date" /></td>
						</tr>
					</table>
						<!-- <span class="label">创建人ID:</span><input type="text" name="Q_operaterId_SL"  class="inputText" />
						<span class="label">创建人姓名:</span><input type="text" name="Q_operaterName_SL"  class="inputText" />
						<span class="label">所属企业ID:</span><input type="text" name="Q_enterpriseId_SL"  class="inputText" />
						<span class="label">所属企业名称:</span><input type="text" name="Q_enterpriseName_SL"  class="inputText" />
						<span class="label">流程ID:</span><input type="text" name="Q_runId_SL"  class="inputText" />
						<span class="label">保证金:</span><input type="text" name="Q_bail_SL"  class="inputText" />
						<span class="label">币种:</span><input type="text" name="Q_currency_SL"  class="inputText" />
						<span class="label">入围审核要求列表:</span><input type="text" name="Q_finalists_SL"  class="inputText" />
						<span class="label">公告截止日期 从:</span> <input  name="Q_beginannouncementDeadline_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endannouncementDeadline_DG" class="inputText date" />
						<span class="label">要求完成时间 从:</span> <input  name="Q_beginrequiredCompleteTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endrequiredCompleteTime_DG" class="inputText date" />
						<span class="label">附件详情（多个）:</span><input type="text" name="Q_attachmentDetail_SL"  class="inputText" />
						<span class="label">研发物品ID:</span><input type="text" name="Q_materialId_SL"  class="inputText" />
						<span class="label">研发物品CODE:</span><input type="text" name="Q_materialCode_SL"  class="inputText" />
						<span class="label">研发物品名称:</span><input type="text" name="Q_materialName_SL"  class="inputText" />
						<span class="label">研发物品类型:</span><input type="text" name="Q_materialType_SL"  class="inputText" />
						<span class="label">研发物品BOM层级:</span><input type="text" name="Q_materialBomLevel_SL"  class="inputText" />
						<span class="label">受邀研发组织ID:</span><input type="text" name="Q_invitedOrgId_SL"  class="inputText" />
						<span class="label">受邀研发组织名称:</span><input type="text" name="Q_invitedOrgName_SL"  class="inputText" />
						<span class="label">受邀研发人员IDs:</span><input type="text" name="Q_invitedUserIds_SL"  class="inputText" />
						<span class="label">受邀所有研发人员姓名:</span><input type="text" name="Q_invitedUserNames_SL"  class="inputText" />
						<span class="label">受邀研发组IDs:</span><input type="text" name="Q_invitedGroupIds_SL"  class="inputText" />
						<span class="label">共享文件夹路径:</span><input type="text" name="Q_sharedPath_SL"  class="inputText" />
						<span class="label">研发资源路径:</span><input type="text" name="Q_resourcePath_SL"  class="inputText" /> -->
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="crowdsourcingRequireList" id="crowdsourcingRequireItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${crowdsourcingRequireItem.id}">
				</display:column>
				<display:column property="code" title="需求编号" sortable="true" sortName="CODE"></display:column>
				<display:column property="requireName" title="需求名称" sortable="true" sortName="REQUIRE_NAME"></display:column>
		<%-- 		<display:column property="requireType" title="需求分类" sortable="true" sortName="REQUIRE_TYPE"></display:column>
				<display:column property="requireDescription" title="需求详细描述" sortable="true" sortName="REQUIRE_DESCRIPTION" maxLength="80"></display:column>
		 --%>		<display:column property="status" title="状态" sortable="true" sortName="STATUS"></display:column>
	<%-- 			<display:column property="publishMode" title="发布模式" sortable="true" sortName="PUBLISH_MODE"></display:column>
				 --%><display:column  title="创建时间" sortable="true" sortName="OPERATE_DATE">
					<fmt:formatDate value="${crowdsourcingRequireItem.operateDate}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				<display:column property="operaterId" title="创建人ID" sortable="true" sortName="OPERATER_ID"></display:column>
				<display:column property="operaterName" title="创建人姓名" sortable="true" sortName="OPERATER_NAME"></display:column>
				<display:column property="enterpriseId" title="所属企业ID" sortable="true" sortName="ENTERPRISE_ID"></display:column>
				<display:column property="enterpriseName" title="所属企业名称" sortable="true" sortName="ENTERPRISE_NAME"></display:column>
				<display:column property="runId" title="流程ID" sortable="true" sortName="RUN_ID"></display:column>
				<display:column property="bail" title="保证金" sortable="true" sortName="BAIL"></display:column>
				<display:column property="currency" title="币种" sortable="true" sortName="CURRENCY"></display:column>
				<display:column property="finalists" title="入围审核要求列表" sortable="true" sortName="FINALISTS" maxLength="80"></display:column>
			 --%>	<display:column  title="公告截止日期" sortable="true" sortName="ANNOUNCEMENT_DEADLINE">
					<fmt:formatDate value="${crowdsourcingRequireItem.announcementDeadline}" pattern="yyyy-MM-dd"/>
				</display:column>
<%-- 				<display:column  title="要求完成时间" sortable="true" sortName="REQUIRED_COMPLETE_TIME">
					<fmt:formatDate value="${crowdsourcingRequireItem.requiredCompleteTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="attachmentDetail" title="附件详情（多个）" sortable="true" sortName="ATTACHMENT_DETAIL" maxLength="80"></display:column>
				<display:column property="materialId" title="研发物品ID" sortable="true" sortName="MATERIAL_ID"></display:column>
				<display:column property="materialCode" title="研发物品CODE" sortable="true" sortName="MATERIAL_CODE"></display:column>
				 --%><display:column property="materialName" title="研发物品名称" sortable="true" sortName="MATERIAL_NAME"></display:column>
				<%-- <display:column property="materialType" title="研发物品类型" sortable="true" sortName="MATERIAL_TYPE"></display:column>
				<display:column property="materialBomLevel" title="研发物品BOM层级" sortable="true" sortName="MATERIAL_BOM_LEVEL"></display:column>
				<display:column property="invitedOrgId" title="受邀研发组织ID" sortable="true" sortName="INVITED_ORG_ID"></display:column>
				<display:column property="invitedOrgName" title="受邀研发组织名称" sortable="true" sortName="INVITED_ORG_NAME"></display:column>
				<display:column property="invitedUserIds" title="受邀研发人员IDs" sortable="true" sortName="INVITED_USER_IDS" maxLength="80"></display:column>
				<display:column property="invitedUserNames" title="受邀所有研发人员姓名" sortable="true" sortName="INVITED_USER_NAMES" maxLength="80"></display:column>
				<display:column property="invitedGroupIds" title="受邀研发组IDs" sortable="true" sortName="INVITED_GROUP_IDS" maxLength="80"></display:column>
				<display:column property="sharedPath" title="共享文件夹路径" sortable="true" sortName="SHARED_PATH" maxLength="80"></display:column>
				<display:column property="resourcePath" title="研发资源路径" sortable="true" sortName="RESOURCE_PATH" maxLength="80"></display:column>
				 --%><display:column title="管理" media="html" style="width:180px">
					<c:if test="${userVacateItem.runStatus==0}">
						<a href="del.ht?id=${crowdsourcingRequireItem.id}" class="link del">删除</a>
						<a href="edit.ht?id=${crowdsourcingRequireItem.id}" class="link edit">编辑</a>
						<a class="link apply" id="">申请</a>
						<input type="hidden" id="userVacateId" value="${crowdsourcingRequireItem.id}">
					</c:if>
					<a href="get.ht?id=${crowdsourcingRequireItem.id}&runid=${crowdsourcingRequireItem.runid}&status=${crowdsourcingRequireItem.runStatus}" class="link detail">明细</a>
					<a href="${ctx}/platform/bpm/processRun/userImage.ht?action=process&action=process&runId=${crowdsourcingRequireItem.runId}" class="link detail">流程示意图</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="crowdsourcingRequireItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


