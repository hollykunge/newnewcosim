package com.casic.datadriver.service.task;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.dao.task.TaskStartDao;
import com.hotent.core.util.UniqueIdUtil;

import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.model.project.ProjectStart;

import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;

import com.casic.datadriver.service.ProjectStartCmd;
//import com.hotent.platform.service.biz.BizProcessVarsConst;

import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.ProcessRunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dodo on 2016/12/3.
 */
@Service
public class TaskStartService  extends BaseService<TaskStart> {

    @Resource
    private TaskStartDao dao;

    @Resource
    private ProcessRunService processRunService;

    public TaskStartService()
    {
    }

    @Override
    protected IEntityDao<TaskStart, Long> getEntityDao() {
        return dao;
    }

    /**
     * 启动业务实例环节，启动任务列表
     * @param projectStart
     * @param taskInfoList
     * @param ctx
     * @return
     * @throws Exception
     */
    public List<TaskStart> start(ProjectStart projectStart, List<TaskInfo> taskInfoList, ProjectStartCmd ctx) throws Exception{

        TaskStart taskStart = new TaskStart();

        List<TaskStart> taskStartList = new ArrayList<TaskStart>();

        TaskInfo taskInfo = new TaskInfo();

        for(int i=0; i<taskInfoList.size(); i++) {
            taskInfo = taskInfoList.get(i);
            taskStart.setDdTaskStartId(UniqueIdUtil.genId());
            taskStart.setDdTaskId(taskInfo.getDdTaskId());
            taskStart.setDdTaskResponcePerson(taskInfo.getDdTaskResponsiblePerson());
            taskStart.setDdProjectStartId(projectStart.getDdProjectStartId());
            taskStart.setCreateBy(projectStart.getCreateBy());
            taskStart.setCreatetime(new Date());
            taskStart.setSortOrder(taskStart.getSortOrder());
            ctx.setTaskStartId(Long.valueOf(taskStart.getDdTaskStartId()));
            taskStart.setDdTaskStatus(TaskStart.publishpanel);
            taskStartList.add(taskStart);
            dao.add(taskStart);
        }

        return taskStartList;
    }
    /**
     * 启动业务实例环节，启动单个任务
     * @param taskStart
     * @return
     * @throws Exception
     */
    public void taskStart(TaskStart taskStart){
        taskStart.setDdTaskStatus(TaskStart.publishpanel);
        taskStart.setCreatetime(new Date());
        dao.add(taskStart);
    }

    /**
     * 启动业务实例环节 对应的流程
     * @param taskInfo
     * @param ctx
     * @return
     * @throws Exception
     */
    private ProcessRun startProcess(TaskInfo taskInfo,ProjectStartCmd ctx) throws Exception {
        ProcessCmd processCmd = ctx.getProcessCmd();
        if(processCmd==null){
            //判断流程定义是否被禁用
//            BpmDefinition bpmDefinition = bpmDefinitionService.getMainDefByActDefKey(TaskInfo.getActDefKey());
//            if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA){
//                throw new Exception("该流程已经被禁用");
//            }
            processCmd = new ProcessCmd();
//            processCmd.setFlowKey(bizDefSegment.getActDefKey());
            processCmd.setSubject(taskInfo.getDdTaskName());
//            processCmd.getVariables().put(BizProcessVarsConst.BIZ_CONTEXT,ctx);
            Long currentUserId = ctx.getCurrentUser().getUserId();
            processCmd.setCurrentUserId(String.valueOf(currentUserId));
            processCmd.setTaskExecutors(ctx.getExecutors());
        }
//        processCmd.getVariables().put(BizProcessVarsConst.BIZ_CONTEXT,ctx);
        ProcessRun processRun=processRunService.startProcess(processCmd);
        return processRun;
    }

    /**
     *  根据业务实例ID，获取业务实例环节列表
     * @param projectStartId
     * @return
     */
    public List<TaskStart> getByProjectStartId(Long projectStartId) {
        return dao.getByMainId(projectStartId);
    }
    /**
     *  根据项目id获取任务启动列表
     * @param projectId
     * @return
     */
    public List<TaskStart> getByProjectId(Long projectId) {
        return dao.getByProjectId(projectId);
    }

    /**
     * 根据项目ID，删除所有启动任务
     * @param projectStartId
     */
    public void delByBizInstanceId(Long projectStartId) {
        dao.delByMainId(projectStartId);
    }

    /**
     * 根据任务ID，删除启动任务
     * @param taskId
     */
    public void delByTaskId(Long taskId) {
        dao.delByTaskId(taskId);
    }
    /**
     * 根据任务责任人获得任务启动列表
     * @param taskResponcePerson
     */
    public List<TaskStart> queryTaskStartByResponceId(long taskResponcePerson) {
        return dao.queryTaskStartByResponceId(taskResponcePerson);
    }

    /**
     * 根据任务Id获得任务启动
     * @param ddTaskId
     */
    public  List<TaskStart> queryTaskStartByTaskId(long ddTaskId) {
        return dao.queryTaskStartByTaskId(ddTaskId);
    }

    public TaskStart getByTaskId(long taskId){return dao.getByTaskId(taskId);}
}
