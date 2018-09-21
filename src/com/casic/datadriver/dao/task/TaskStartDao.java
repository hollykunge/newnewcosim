package com.casic.datadriver.dao.task;

import com.hotent.core.db.BaseDao;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.model.task.TaskStart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dodo on 2016/12/3.
 */
@Repository
public class TaskStartDao extends BaseDao<TaskStart> {
    @Override
    public Class<?> getEntityClass()
    {
        return TaskStart.class;
    }
    public List<TaskStart> getByMainId(Long projectStartId) {
        return this.getBySqlKey("getByMainId", projectStartId);
    }
    public List<TaskStart> getByProjectId(Long projectId) {
        return this.getBySqlKey("getByProjectId", projectId);
    }

    public List<TaskStart> queryTaskStartByResponceId(long taskResponcePerson) {
        return this.getBySqlKey("queryTaskStartByResponceId", taskResponcePerson);
    }
    public void delByMainId(Long projectStartId) {
        this.delBySqlKey("delByMainId", projectStartId);
    }
    public void delByTaskId(Long taskId) {
        this.delBySqlKey("delByTaskId", taskId);
    }
    public List<TaskStart> queryTaskStartByTaskId(long ddTaskId) {
        return this.getBySqlKey("queryTaskStartByTaskId", ddTaskId);
    }
    public TaskStart getByTaskId(Long taskId) {
        return this.getUnique("getByTaskId", taskId);
    }
}
