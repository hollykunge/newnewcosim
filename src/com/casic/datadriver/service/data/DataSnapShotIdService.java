package com.casic.datadriver.service.data;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;

import com.casic.datadriver.dao.data.DataSnapShotIdDao;
import com.casic.datadriver.model.data.DataSnapShotId;
/**
 * The Class DataSnapShotIdService.
 */
@Service
public class DataSnapShotIdService extends BaseService<DataSnapShotId> {

    /** The dataSnapShotId dao. */
    @Resource
    private DataSnapShotIdDao dataSnapShotIdDao;

    /**
     * Adds the DD dataSnapShotId.
     *
     * @param dataSnapShotId
     *            the dataSnapShotId
     * @return true, if successful
     */
    public boolean addDDDataSnapShotId(DataSnapShotId dataSnapShotId) {
        this.dataSnapShotIdDao.add(dataSnapShotId);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<DataSnapShotId, Long> getEntityDao() {
        return this.dataSnapShotIdDao;
    }

    /**
     * Query dataSnapShotId basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapShotId> queryDataSnapShotIdBasicInfoList(QueryFilter queryFilter) {
        return this.dataSnapShotIdDao.queryDataSnapShotIdBasicInfoList(queryFilter);
    }

}
