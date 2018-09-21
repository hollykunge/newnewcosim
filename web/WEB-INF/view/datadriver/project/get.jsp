<!DOCTYPE html>
<%--
	time:2013-09-05 10:22:51
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html lang="zh-CN">
<head>
  <title>项目信息明细</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
  <%@include file="/commons/include/getById.jsp"%>
  <script type="text/javascript">
    //放置脚本
  </script>
</head>
<body>
<div class="panel">
  <div class="panel-top">
    <div class="tbar-title">
      <span class="tbar-label">项目详细信息</span>
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
        <th width="20%">项目名称: </th>
        <td>${Project.ddProjectName}</td>
        <th width="20%">项目研制阶段: </th>
        <td>${Project.ddProjectScheduleState}</td>
      </tr>
      <tr>
        <th width="20%">项目责任单位: </th>
        <td>${Project.ddProjectResponsibleUnits}</td>
        <th width="20%">项目阶段: </th>
        <td>${Project.ddProjectPhaseId}</td>
      </tr>
      <tr>
        <th width="20%">项目创建日期: </th>
        <td>${Project.ddProjectCreateDatatime}</td>
        <th width="20%">项目基本描述: </th>
        <td>${Project.ddProjectDescription}</td>
      </tr>
      <tr>
        <th width="20%">项目所属系统: </th>
        <td>${Project.ddProjectOwnerSystemId}</td>
        <th width="20%">项目创建者id: </th>
        <td>${Project.ddProjectCreatorId}</td>
      </tr>
      <tr>
        <th width="20%">项目是否固化: </th>
        <td>${Project.ddProjectFixedPattern}</td>
        <th width="20%">项目负责人id: </th>
        <td>${Project.ddProjectResponsiblePersonId}</td>
      </tr>
      <tr>
        <th width="20%">项目类型: </th>
        <td>${Project.ddProjectType}</td>
        <th width="20%">所属型号: </th>
        <td>${Project.ddProjectBelongModel}</td>
      </tr>
      <tr>
        <th width="20%">项目密级: </th>
        <td>${Project.ddProjectSecretLevel}</td>
        <th width="20%">修改人id: </th>
        <td>${Project.ddProjectChangePersonId}</td>
      </tr>
      <tr>
        <th width="20%">优先级: </th>
        <td>${Project.ddProjectPriority}</td>
        <th width="20%">备注: </th>
        <td>${Project.ddProjectRemark}</td>
      </tr>
      <tr>
        <th width="20%">状态: </th>
        <td>${Project.ddProjectState}</td>
        <th width="20%">计划开始日期: </th>
        <td>${Project.ddProjectPlanStartDate}</td>
      </tr>
      <tr>
        <th width="20%">计划完成日期: </th>
        <td>${Project.ddProjectCompleteDate}</td>
        <th width="20%">实际开始日期: </th>
        <td>${Project.ddProjectActualStartDate}</td>
      </tr>
      <tr>
        <th width="20%">实际结束日期: </th>
        <td>${Project.ddProjectActualCompleteData}</td>
        <th width="20%">当前项目进度: </th>
        <td>${Project.ddProjectCurrentStage}</td>
      </tr>
    </table>
  </div>
</div>
</body>
</html>

