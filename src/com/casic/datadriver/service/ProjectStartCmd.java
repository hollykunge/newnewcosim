package com.casic.datadriver.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casic.datadriver.service.project.ProjectService;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;

import com.hotent.platform.auth.ISysUser;

import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.project.ProjectStart;
import com.casic.datadriver.model.task.TaskStart;

import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.system.SysUserService;
//import com.casic.datadriver.model.task.TaskInfo;
/**
 * Created by dodo on 2016/12/3.
 *
 * 项目启动上下文
 *
 */
public class ProjectStartCmd implements Serializable {
    private static final long serialVersionUID = 1L;
    //发起人ID
    private Long startUserId;
    transient private ISysUser startUser;
    //当前用户
    transient private ISysUser currentUser;
    //当前项目ID
    private Long projectId;
    transient private Project project;
    //当前启动项目ID
    private Long projectStartId;
    transient private ProjectStart projectStart;
    //当前启动任务ID
    private Long taskStartId;
    transient private TaskStart taskStart;

    transient private List<TaskExecutor> executors;
    //流程扩展实例
    transient private ProcessRun processRun;
    transient private ProcessCmd processCmd;

    transient private Map<String,Object> bizVars = new HashMap<String, Object>();

    public Long getStartUserId() {
        return startUserId;
    }
    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
        SysUserService sysUserService = (SysUserService) AppUtil.getBean(SysUserService.class);
        this.startUser = sysUserService.getById(startUserId);
    }

    public ISysUser getStartUser() {
        return startUser;
    }
    public void setStartUser(ISysUser startUser) {
        this.startUser = startUser;
        this.startUserId = startUser.getUserId();
    }

    public ISysUser getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(ISysUser currentUser) {
        this.currentUser = currentUser;
    }

    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
        ProjectService projectService = (ProjectService) AppUtil.getBean(ProjectService.class);
        this.project = projectService.getById(projectId);
    }

    public Long getDdProjectId() {
        return projectId;
    }
    public void setProject(Project project) {
        this.project = project;
        this.projectId = Long.valueOf(this.project.getDdProjectId());
    }

    public ProjectStart getProjectStart() {
        return projectStart;
    }
    public void setProjectStart(ProjectStart projectStart) {
        this.projectStart = projectStart;
    }

    public TaskStart getTaskStart() {
        return taskStart;
    }
    public void setTaskStart(TaskStart taskStart) {
        this.taskStart = taskStart;
    }

    public Long getProjectStartId() {
        return projectStartId;
    }
    public void setProjectStartId(Long projectStartId) {
        this.projectStartId = projectStartId;
    }

    public Long getTaskStartId() {
        return taskStartId;
    }
    public void setTaskStartId(Long taskStartId) {
        this.taskStartId = taskStartId;
    }

    public List<TaskExecutor> getExecutors() {
        return executors;
    }
    public void setExecutors(List<TaskExecutor> executors) {
        this.executors=executors;
    }

    public ProcessRun getProcessRun() {
        return processRun;
    }
    public void setProcessRun(ProcessRun processRun) {
        this.processRun = processRun;
    }

    public ProcessCmd getProcessCmd() {
        return processCmd;
    }
    public void setProcessCmd(ProcessCmd processCmd) {
        this.processCmd = processCmd;
    }

    public Map<String,Object> getBizVars() {
        if(bizVars==null){
            bizVars = new HashMap<String, Object>();
        }
        return bizVars;
    }
    public void setBizVars(Map<String,Object> bizVars) {
        this.bizVars = bizVars;
    }
}
