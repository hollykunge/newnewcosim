package com.casic.datadriver.dao.data;

import com.casic.datadriver.model.data.TaskVerMap;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class PrivateDataDao.
 */
@Repository
public class TaskVerMapDao extends BaseDao<TaskVerMap> {
    @Override
    public Class<?> getEntityClass() {
        return TaskVerMap.class;
    }

    /**
     * 添加一个数据
     */
    public void addTaskVerMap(TaskVerMap taskVerMap) {
        this.add(taskVerMap);
    }

    /**
     * 根据任务ID查找版本列表
     */
    public List<TaskVerMap> getListByTaskId(Long taskId) {
        return this.getBySqlKey("getListByTaskId", taskId);
    }
    /**
     * 根据任务ID批量删除数据
     */
    //TODO:根据任务ID删除数据还没弄好
    public void delVerByTaskId(Long dataId) {
        this.delBySqlKey("delVerByTaskId", dataId);
    }

    /**
     * 获取任务当前数据版本号
     */
    public Long getVersionNum(Long taskId) {
        return (Long) this.getOne("getVersionNum", taskId);
    }
}
