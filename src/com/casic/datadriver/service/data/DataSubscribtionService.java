package com.casic.datadriver.service.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.datadriver.model.data.DataSubscribtion;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.casic.datadriver.dao.data.DataSubscribtionDao;

/**
 * The Class DataSubscribtionService.
 */
@Service
public class DataSubscribtionService extends BaseService<DataSubscribtion> {

    /** The dataSubscribtion dao. */
    @Resource
    private DataSubscribtionDao dataSubscribtionDao;

    /**
     * Adds the DD dataSubscribtion.
     *
     * @param dataSubscribtion
     *            the dataSubscribtion
     * @return true, if successful
     */
    public boolean addDDDataSubscribtion(DataSubscribtion dataSubscribtion) {
        this.dataSubscribtionDao.add(dataSubscribtion);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<DataSubscribtion, Long> getEntityDao() {
        return this.dataSubscribtionDao;
    }
    /**
     * Query dataVersion basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSubscribtion> queryDataVersionBasicInfoList(QueryFilter queryFilter) {
        return this.dataSubscribtionDao.queryDataSubscribtionBasicInfoList(queryFilter);
    }

}
