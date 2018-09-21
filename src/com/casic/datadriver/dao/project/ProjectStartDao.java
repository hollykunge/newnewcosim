package com.casic.datadriver.dao.project;

import com.casic.datadriver.model.project.Project;
import com.hotent.core.db.BaseDao;
import com.casic.datadriver.model.project.ProjectStart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dodo on 2016/12/3.
 */
@Repository
public class ProjectStartDao extends BaseDao<ProjectStart> {

    @Override
    public Class<?> getEntityClass()
    {
        return ProjectStart.class;
    }

    /**
     * ������Ŀ�µ���������
     * @param ddProjectId
     * @return Project
     */
    public List<ProjectStart> queryByProjectId(Long ddProjectId){
        return this.getBySqlKey("queryByProjectId", ddProjectId);
    }


}
