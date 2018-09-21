package com.casic.datadriver.service.task;

import com.casic.datadriver.dao.task.TaskResRelationDao;
import com.casic.datadriver.model.task.TaskResRelation;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by blue on 2017/2/6.
 */
@Service
public class TaskResRelationService extends BaseService<TaskResRelation> {

    /** The TaskResRelation dao. */
    @Resource
    private TaskResRelationDao taskResRelationDao;




    public boolean addTaskResRelation(TaskResRelation taskResRelation) {
        this.taskResRelationDao.add(taskResRelation);
        return true;
    }



    protected IEntityDao<TaskResRelation, Long> getEntityDao() {
        return this.taskResRelationDao;
    }
}
