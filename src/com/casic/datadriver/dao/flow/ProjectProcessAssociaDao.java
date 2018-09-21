package com.casic.datadriver.dao.flow;

import com.casic.datadriver.model.flow.ProjectProcessAssocia;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by d on 2017/1/10.
 */
@Repository
public class ProjectProcessAssociaDao extends BaseDao<ProjectProcessAssocia> {
    @Override
    public Class<?> getEntityClass() {
        return ProjectProcessAssocia.class;
    }

    public ProjectProcessAssocia selectByProjectId(long projectId) {
        return this.getUnique("selectByProjectKey", projectId);
    }

}
