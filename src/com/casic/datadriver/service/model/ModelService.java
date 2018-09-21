package com.casic.datadriver.service.model;

import com.casic.datadriver.dao.model.ModelDao;
import com.casic.datadriver.model.model.Model;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 忠 on 2017/2/28.
 */
@Service
public class ModelService extends BaseService<Model> {
    @Resource
    private ModelDao modeldao;

    @Override
    protected IEntityDao<Model, Long> getEntityDao() {
        return this.modeldao;
    }

    public List<Model> findById(long Id) {
        return this.modeldao.findById(Id);
    }
    public List<Model> findByPid(long Pid) {
        return this.modeldao.findByPid(Pid);
    }
}
