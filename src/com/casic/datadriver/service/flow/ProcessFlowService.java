package com.casic.datadriver.service.flow;

import com.casic.datadriver.dao.flow.ProcessFlowDao;
import com.casic.datadriver.model.flow.ProcessFlow;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dodo on 2016/12/27.
 */
@Service
public class ProcessFlowService extends BaseService<ProcessFlow> {
    @Resource
    private ProcessFlowDao processflowDao;

    public ProcessFlowService()
    {
    }
    @Override
    protected IEntityDao<ProcessFlow, Long> getEntityDao() {
        return this.processflowDao;
    }

    public void addProcessFlow(ProcessFlow processFlow){
        this.processflowDao.addprocess(processFlow);
    }
}
