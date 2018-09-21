package com.casic.datadriver.service.project;

import com.casic.datadriver.service.task.TaskStartService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.project.Project;

import com.casic.datadriver.model.project.ProjectStart;
import com.casic.datadriver.model.task.TaskStart;

import com.casic.datadriver.dao.task.TaskInfoDao;
import com.casic.datadriver.dao.task.TaskStartDao;

import com.casic.datadriver.dao.project.ProjectStartDao;
import com.casic.datadriver.dao.project.ProjectDao;

import com.hotent.core.util.UniqueIdUtil;

import com.casic.datadriver.service.ProjectStartCmd;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by dodo on 2016/12/3.
 */
@Service
public class ProjectStartService extends BaseService<ProjectStart> {

    @Resource
    private ProjectStartDao dao;

    @Resource
    private TaskStartService taskStartService;
    @Resource
    private TaskStartDao taskStartDao;

    @Resource
    private ProjectDao projectDao;

    @Resource
    private TaskInfoDao taskInfoDao;

    @Override
    protected IEntityDao<ProjectStart, Long> getEntityDao() {
        return dao;
    }

    @Resource
    protected ProjectStartDao projectStartDao;

    /**
     * 创建业务实例
     * @param projectId 项目ID
     * @throws Exception
     */
    public void startProject(Long projectId,ProjectStartCmd bizCmd) throws Exception{
        Project project=projectDao.getById(projectId);
        List<TaskInfo> taskInfoList=taskInfoDao.getByMainId(projectId);

        project.setTaskInfoList(taskInfoList);

        //项目启动
        ProjectStart projectStart = new ProjectStart();
        projectStart.setDdProjectStartId(UniqueIdUtil.genId());
        projectStart.setDdProjectId(projectId);
        projectStart.setCreateBy(bizCmd.getCurrentUser().getUserId());
        projectStart.setDdProjectStartTime(new Date());

//		bizCmd.setBizInstance(bizInstance);
        bizCmd.setProjectId(projectId);
        bizCmd.setProjectStartId(Long.valueOf(projectStart.getDdProjectStartId()));

        List<TaskStart> taskStart =startAllTask(project,projectStart,bizCmd);
        projectStart.setDdProjectStartStatus(ProjectStart.STATUS_RUNNING);

        dao.add(projectStart);
    }

    /**
     * 启动项目下的所有任务
     * @param project
     * @param projectStart
     * @param projectStartCmd
     * @return
     * @throws Exception
     */
    private List<TaskStart> startAllTask(Project project,ProjectStart projectStart,ProjectStartCmd projectStartCmd) throws Exception {
        List<TaskInfo> taskInfo = project.getTaskInfoList();
        if(taskInfo==null){
            throw new InternalError("启动失败，项目中没有任务！");
        }
        List<TaskStart> taskStart = taskStartService.start(projectStart, taskInfo, projectStartCmd);

        return taskStart;
    }


    /**
     * 启动项目下的所有任务
     * @param ddProjectId
     * @return Project
     */
    public List<ProjectStart> queryByProjectId(Long ddProjectId){
        return projectStartDao.queryByProjectId(ddProjectId);
    }
}
