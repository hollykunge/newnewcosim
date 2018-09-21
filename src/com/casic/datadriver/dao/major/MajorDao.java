package com.casic.datadriver.dao.major;

import com.casic.datadriver.model.major.Major;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 忠 on 2017/2/7.
 */
@Repository
public class MajorDao  extends BaseDao<Major> {
    @Override
    public Class getEntityClass() {
        return Major.class;
    }
    public List<Major> findByPid(long pid) {
        return this.getBySqlKey("findByPid", pid);
    }

    public List<Major> findById(long id) {
        return this.getBySqlKey("findById", id);
    }
}
