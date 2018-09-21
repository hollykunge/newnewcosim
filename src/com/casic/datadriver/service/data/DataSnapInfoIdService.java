package com.casic.datadriver.service.data;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;

import com.casic.datadriver.dao.data.DataSnapInfoIdDao;
import com.casic.datadriver.model.data.DataSnapInfoId;
/**
 * The Class DataSnapInfoIdService.
 */
@Service
public class DataSnapInfoIdService extends BaseService<DataSnapInfoId> {

    /** The dataSnapInfoId dao. */
    @Resource
    private DataSnapInfoIdDao dataSnapInfoIdDao;

    /**
     * Adds the DD dataSnapInfoId.
     *
     * @param dataSnapInfoId
     *            the dataSnapInfoId
     * @return true, if successful
     */
    public boolean addDDDataSnapInfoId(DataSnapInfoId dataSnapInfoId) {
        this.dataSnapInfoIdDao.add(dataSnapInfoId);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<DataSnapInfoId, Long> getEntityDao() {
        return this.dataSnapInfoIdDao;
    }

    /**
     * Query dataSnapInfoId basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapInfoId> queryDataSnapInfoIdBasicInfoList(QueryFilter queryFilter) {
        return this.dataSnapInfoIdDao.queryDataSnapInfoIdBasicInfoList(queryFilter);
    }

}
