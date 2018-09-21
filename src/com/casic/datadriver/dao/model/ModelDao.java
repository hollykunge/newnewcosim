package com.casic.datadriver.dao.model;

import com.casic.datadriver.model.model.Model;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 忠 on 2017/2/28.
 */
@Repository
public class ModelDao extends BaseDao<Model> {

    @Override
    public Class getEntityClass() {
        return Model.class;
    }
    public List<Model> findByPid(long pid) {
        return this.getBySqlKey("findByPid", pid);
    }

    public List<Model> findById(long id) {
        return this.getBySqlKey("findById", id);
    }
}
