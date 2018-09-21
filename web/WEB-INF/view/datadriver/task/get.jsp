<%--
	time:2013-09-05 10:22:51
--%>
<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html lang="zh-CN">
<head>
    <title>任务明细</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <%@include file="/commons/include/getById.jsp" %>
    <script type="text/javascript">
        //放置脚本
    </script>
</head>
<body>
<div class="panel">
    <div class="panel-top">
        <div class="tbar-title">
            <span class="tbar-label">任务详细信息</span>
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
                <th width="20%">所属项目:</th>
                <td>${TaskInfo.ddTaskProjectId}</td>
                <th width="20%">任务名称:</th>
                <td>${TaskInfo.ddTaskName}</td>
            </tr>
            <tr>
                <th width="20%">任务基本描述:</th>
                <td>${TaskInfo.ddTaskDescription}</td>
                <th width="20%">任务创建者id:</th>
                <td>${TaskInfo.ddTaskCreatorId}</td>
            </tr>
            <tr>
                <th width="20%">任务负责人:</th>
                <td>${TaskInfo.ddTaskResponsiblePerson}</td>
                <th width="20%">任务是否固化:</th>
                <td>${TaskInfo.ddTaskFixedPattern}</td>
            </tr>
            <tr>
                <th width="20%">任务类型:</th>
                <td>${TaskInfo.ddTaskType}</td>
                <th width="20%">任务子类型:</th>
                <td>${TaskInfo.ddTaskCreatorId}</td>
            </tr>
            <tr>
                <th width="20%">优先级:</th>
                <td>"${TaskInfo.ddTaskPriority}</td>
                <th width="20%">里程碑任务:</th>
                <td>${TaskInfo.ddTaskMilestone}</td>
            </tr>
            <tr>
                <th width="20%">任务计划工时:</th>
                <td>${TaskInfo.ddTaskEstimateTime}</td>
                <th width="20%">任务计划开始时间:</th>
                <td>${TaskInfo.ddTaskPlanStartTime}</td>
            </tr>
            <tr>
                <th width="20%">任务计划结束时间:</th>
                <td>${TaskInfo.ddTaskPlanEndTime}</td>
                <th width="20%">任务完成率:</th>
                <td>${TaskInfo.ddTaskCompleteRate}</td>
            </tr>
            <tr>
                <th width="20%">任务完成状态:</th>
                <td>${TaskInfo.ddTaskCompleteState}</td>
                <th width="20%">任务资源id:</th>
                <td>${TaskInfo.ddTaskResourceId}</td>
            </tr>
            <tr>
                <th width="20%">实际工时:</th>
                <td>${TaskInfo.ddTaskActualTime}</td>
                <th width="20%">计划工时:</th>
                <td>${TaskInfo.ddTaskPlanTime}</td>
            </tr>
            <tr>
                <th width="20%">实际开始日期:</th>
                <td>${TaskInfo.ddTaskActualStartTime}</td>
                <th width="20%">实际结束日期:</th>
                <td>${TaskInfo.ddTaskActualEndTime}</td>
            </tr>
            <tr>
                <th width="20%">计划工期:</th>
                <td>${TaskInfo.ddTaskPlanDuration}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>

