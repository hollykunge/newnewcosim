<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>业务定义，如邀标采购这样的大业务。管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">

function startBizInstance(bizDefId){
	var url= __ctx +"/platform/biz/bizDef/getCanDirectStart.ht";
	var params={bizDefId:bizDefId};
	$.post(url,params,function(data){
		if(data){
			var callBack=function(rtn){
				if(!rtn) return;
				var flowUrl= __ctx +"/platform/biz/bizDef/start.ht";
				var params={bizDefId:bizDefId};
				$.post(flowUrl,parameters,function(responseText){
					var obj=new com.hotent.form.ResultMessage(responseText);
					if(obj.isSuccess()){//成功
						$.ligerMessageBox.success('提示信息',"创建新业务实例成功!");
					}
					else{
						$.ligerMessageBox.error('出错了',"创建新业务实例失败!");
					}
				});
			};
			$.ligerMessageBox.confirm('提示信息',"需要创建新业务实例吗?",callBack);
		}else{
			var toStartUrl=__ctx +"/platform/biz/bizDef/toStart.ht?bizDefId="+bizDefId;
			jQuery.openFullWindow(toStartUrl);
		}
	});
};

</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务定义，如邀标采购这样的大业务。管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">业务编号:</span><input type="text" name="Q_bizDefNo_SL"  class="inputText" />
						<span class="label">业务名称:</span><input type="text" name="Q_defName_SL"  class="inputText" />
						<span class="label">状态:</span>
							<select name="Q_status_SN" class="select" style="width:8%;" value="${param['Q_status_SN']}">
								<option value="">--选择--</option>
								<option value="${DefDef_STATUS_ENABLE}"  <c:if test="${param['Q_status_SN'] == DefDef_STATUS_ENABLE }">selected</c:if>>激活</option>
								<option value="${DefDef_STATUS_DISABLE}" <c:if test="${param['Q_status_SN'] == DefDef_STATUS_DISABLE }">selected</c:if>>未激活</option>
							</select>
						<span class="label">创建日期 从:</span> <input  name="Q_begincreateTime_DL"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcreateTime_DG" class="inputText date" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="bizDefList" id="bizDefItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="bizDefId" value="${bizDefItem.bizDefId}">
				</display:column>
				<display:column property="bizDefNo" title="业务编号" sortable="true" sortName="bizDefNo" maxLength="80"></display:column>
				<display:column property="defName" title="业务名称" sortable="true" sortName="defName" maxLength="80"></display:column>
				<display:column property="defDescription" title="描述" sortable="true" sortName="defDescription" maxLength="80"></display:column>
				<display:column property="versionNo" title="版本号" sortable="true" sortName="versionNo"></display:column>
				<display:column property="isMain" title="主版本" sortable="true" sortName="isMain"></display:column>
				<display:column title="状态:" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${bizDefItem.status==DefDef_STATUS_ENABLE }">激活</c:when>
						<c:when test="${bizDefItem.status==DefDef_STATUS_DISABLE }">未激活</c:when>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html" style="width:260px">
					<a href="del.ht?bizDefId=${bizDefItem.bizDefId}" class="link del">删除</a>
					<a href="edit.ht?bizDefId=${bizDefItem.bizDefId}" class="link edit">编辑</a>
					<a onclick="startBizInstance(${bizDefItem.bizDefId})" class="link run">启动</a>
					<a href="get.ht?bizDefId=${bizDefItem.bizDefId}" class="link detail">明细</a>
					
				</display:column>
			</display:table>
			<hotent:paging tableId="bizDefItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


