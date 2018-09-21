package com.casic.datadriver.service.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.datadriver.model.task.ProTaskDependance;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.casic.datadriver.dao.task.ProTaskDependanceDao;

/**
 * The Class ProTaskDependanceService.
 */
@Service
public class ProTaskDependanceService extends BaseService<ProTaskDependance> {

    /**
     * The proTaskDependance dao.
     */
    @Resource
    private ProTaskDependanceDao proTaskDependanceDao;

    /**
     * Adds the DD proTaskDependance.
     *
     * @param proTaskDependance the proTaskDependance
     * @return true, if successful
     */
    public boolean addDDProTaskDependance(ProTaskDependance proTaskDependance) {
        this.proTaskDependanceDao.add(proTaskDependance);
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<ProTaskDependance, Long> getEntityDao() {
        return this.proTaskDependanceDao;
    }

    /**
     * Query proTaskDependance basic info list.
     *
     * @param queryFilter the query filter
     * @return the list
     */
    public List<ProTaskDependance> queryProTaskDependanceBasicInfoList(QueryFilter queryFilter) {
        return this.proTaskDependanceDao.queryProTaskDependanceBasicInfoList(queryFilter);
    }

    public List<ProTaskDependance> getProTaskDependanceList(long projectId) {
        return this.proTaskDependanceDao.getProTaskDependanceList(projectId);
    }

    public void deleteByProjectIdAndTaskId(Long ddProjectId, Long ddTaskID) {

        this.proTaskDependanceDao.deleteByProjectIdAndTaskId(ddProjectId, ddTaskID);
    }

    public void delByTaskId(long taskId) {
        this.proTaskDependanceDao.delByTaskId(taskId);
    }
}
