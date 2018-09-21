package com.casic.datadriver.service;

import com.casic.datadriver.dao.ToolCenterDao;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.tool.ToolCenterModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 忠 on 2017/1/13.
 */
@Service
public class ToolCenterService extends BaseService<ToolCenterModel> {

    @Resource
    private ToolCenterDao toolcenterdao;

    @Override
    protected IEntityDao<ToolCenterModel, Long> getEntityDao() {
        return this.toolcenterdao;
    }


    public List<ToolCenterModel> querytoolBymajor(String major) {
        return this.toolcenterdao.querytoolBymajor(major);
    }

    public List<ToolCenterModel> querytoolByname(String toolname) {
        return this.toolcenterdao.querytoolByname(toolname);
    }

    public List<ToolCenterModel> querytoolBymajorF(PageInfo model) {
        return this.toolcenterdao.querytoolBymajorF(model);
    }

    public void deltool(long id) {
        this.toolcenterdao.del(id);
    }
}
