package com.casic.datadriver.dao.data;

import com.casic.datadriver.model.data.DataSubscribtion;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class DataSubscribtionDaoDao.
 */
@Repository
public class DataSubscribtionDao extends BaseDao<DataSubscribtion> {

    /**
     * Query dataSubscribtionDao basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSubscribtion> queryDataSubscribtionBasicInfoList(QueryFilter queryFilter) {
        return this.getBySqlKey("queryDataSubscribtionBasicInfoList", queryFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return DataSubscribtion.class;
    }

}
