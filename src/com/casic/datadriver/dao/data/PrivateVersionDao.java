package com.casic.datadriver.dao.data;

import com.casic.datadriver.model.data.PrivateVersion;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class PrivateDataDao.
 */
@Repository
public class PrivateVersionDao extends BaseDao<PrivateVersion> {
    @Override
    public Class<?> getEntityClass() {
        return PrivateVersion.class;
    }

    /**
     * 添加一个数据
     */
    public void addPrivateVer(PrivateVersion privateVersion) {
        this.add(privateVersion);
    }

    /**
     * 根据版本ID查找私有数据
     */
    public List<PrivateVersion> getListByVerId(Long varsionId) {
        return this.getBySqlKey("getListByVerId", varsionId);
    }
    /**
     * 根据任务ID查找私有数据
     */
    public List<PrivateVersion> getListBytaskId(Long taskId) {
        return this.getBySqlKey("getListBytaskId", taskId);
    }

    /**
     * 根据任务ID删除数据
     */
    public void deleteByTaskId(Long taskId) {
        this.delBySqlKey("deleteByTaskId", taskId);
    }
}
