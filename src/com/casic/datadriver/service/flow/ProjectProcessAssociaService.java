package com.casic.datadriver.service.flow;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;
import com.casic.datadriver.dao.flow.ProjectProcessAssociaDao;
import com.casic.datadriver.model.flow.ProjectProcessAssocia;

import javax.annotation.Resource;

/**
 * Created by d on 2017/1/10.
 */
@Service
public class ProjectProcessAssociaService extends BaseService<ProjectProcessAssocia> {

    @Resource
    ProjectProcessAssociaDao projectProcessAssociaDao;

    @Override
    protected IEntityDao<ProjectProcessAssocia, Long> getEntityDao() {
        return this.projectProcessAssociaDao;
    }

    public ProjectProcessAssocia selectByProjectId(long projectId) {
        return this.projectProcessAssociaDao.selectByProjectId(projectId);
    }
}
