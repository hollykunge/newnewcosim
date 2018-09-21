package com.casic.datadriver.dao.data;

import java.util.List;

import com.casic.datadriver.model.data.DataSnapshot;
import org.springframework.stereotype.Repository;


import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

/**
 * Created by blue on 2017/1/23.
 */
@Repository
public class DataSnapshotDao extends BaseDao<DataSnapshot>{


    /**
     * Query dataSnapShotIdDao basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapshot> queryDataSnapshotBasicInfoList(QueryFilter queryFilter) {
        return this.getBySqlKey("queryDataSnapshotBasicInfoList", queryFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return DataSnapshot.class;
    }

    /**
     * ��������id��ѯ���ݿ���.
     *
     * @param ddDataID
     * @return the list
     */
    public List<DataSnapshot> getByddDataId(Long ddDataID) {
        return this.getBySqlKey("getByddDataId", ddDataID);
    }

}
