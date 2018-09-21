package com.casic.datadriver.dao.data;

import com.casic.datadriver.model.data.DataSnapInfoId;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;



/**
 * The Class DataSnaoInfoIdDao.
 */

@Repository
public class DataSnapInfoIdDao extends BaseDao<DataSnapInfoId> {

    /**
     * Query dataSnaoInfoId basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapInfoId> queryDataSnapInfoIdBasicInfoList(QueryFilter queryFilter) {
        return this.getBySqlKey("queryDataSnapInfoIdBasicInfoList", queryFilter);
    }
    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return DataSnapInfoId.class;
    }

}
