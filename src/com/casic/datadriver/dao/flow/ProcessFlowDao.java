package com.casic.datadriver.dao.flow;

import com.casic.datadriver.model.flow.ProcessFlow;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by dodo on 2016/12/27.
 */
@Repository
public class ProcessFlowDao extends BaseDao<ProcessFlow> {

    @Override
    public Class<?> getEntityClass() {
        return ProcessFlow.class;
    }

    public void addprocess(ProcessFlow processFlow) {
        this.delBySqlKey("addprocess", processFlow);
    }
}
