package com.casic.datadriver.service.data;

import com.casic.datadriver.dao.data.DataSnapshotDao;
import com.casic.datadriver.model.data.DataSnapshot;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by blue on 2017/1/23.
 */
@Service
public class DataSnapshotService extends BaseService<DataSnapshot> {

    /** The dataSnapshot dao. */
    @Resource
    private DataSnapshotDao dataSnapshotDao;

    /**
     * Adds the DD dataSnapshot.
     *
     * @param dataSnapshot
     *            the dataSnapshot
     * @return true, if successful
     */
    public boolean addDDDataSnapshot(DataSnapshot dataSnapshot) {
        this.dataSnapshotDao.add(dataSnapshot);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<DataSnapshot, Long> getEntityDao() {
        return this.dataSnapshotDao;
    }

    /**
     * Query dataSnapshot basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public List<DataSnapshot> queryDataSnapshotBasicInfoList(QueryFilter queryFilter) {
        return this.dataSnapshotDao.queryDataSnapshotBasicInfoList(queryFilter);
    }

    /**
     * ��������id��ѯ���ݿ���.
     *
     * @param ddDataId
     * @return the list
     */
    public List<DataSnapshot> getByddDataId(Long ddDataId) {
        return this.dataSnapshotDao.getByddDataId(ddDataId);
    }


}
