package com.casic.datadriver.dao.data;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.casic.datadriver.model.data.DataSnapShotId;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

/**
 * The Class DataSnapShotIdDao.
 */
@Repository
public class DataSnapShotIdDao extends BaseDao<DataSnapShotId> {

    /**
     * Query dataSnapShotIdDao basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapShotId> queryDataSnapShotIdBasicInfoList(QueryFilter queryFilter) {
        return this.getBySqlKey("queryDataSnapShotIdBasicInfoList", queryFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return DataSnapShotId.class;
    }

}
