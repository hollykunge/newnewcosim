package com.casic.datadriver.service;

import com.casic.datadriver.dao.ModelCenterDao;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.modelcenter.ModelCenterModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 忠 on 2017/2/28.
 */
@Service
public class ModelCenterService extends BaseService<ModelCenterModel> {

    @Resource
    private ModelCenterDao modelcenterdao;

    @Override
    protected IEntityDao<ModelCenterModel, Long> getEntityDao() {
        return this.modelcenterdao;
    }


    public List<ModelCenterModel> querytoolBymodeltype(long modeltype) {
        return this.modelcenterdao.querytoolBymodeltype(modeltype);
    }

    public List<ModelCenterModel> querytoolBymodeltypeF(PageInfo model) {
        return this.modelcenterdao.querytoolBymodeltypeF(model);
    }
    public List<ModelCenterModel> querytoolBymodelname(PageInfo model) {
        return this.modelcenterdao.querytoolBymodelname(model);
    }

    public List<ModelCenterModel> querytoolBymodelnameF(PageInfo model) {
        return this.modelcenterdao.querytoolBymodelnameF(model);
    }
    public void delmodel(long id) {
        this.modelcenterdao.del(id);
    }

    public List<ModelCenterModel> getByTaskId(Long taskId) {
        return this.modelcenterdao.getByTaskId(taskId);
    }


    public List<ModelCenterModel> querytoolBytaskid(PageInfo model) {
        return this.modelcenterdao.querytoolBytaskid(model);
    }

    public List<ModelCenterModel> querytoolBytaskidF(PageInfo model) {
        return this.modelcenterdao.querytoolBytaskidF(model);
    }

}
