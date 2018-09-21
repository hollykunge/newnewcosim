package com.casic.datadriver.dao.flow;

import com.casic.datadriver.publicClass.QueryParameters;
import com.casic.datadriver.model.flow.ProcessBlock;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by blue on 2017/5/4.
 */
@Repository

public class ProcessBlockDao extends BaseDao<ProcessBlock> {

    @Override
    public Class<?> getEntityClass() {
        return ProcessBlock.class;
    }

    public ProcessBlock getRelBProId(QueryParameters queryParameters) {
        return this.getUnique("getRelBProId", queryParameters);
    }

    public ProcessBlock getRelTaskId(Long taskId) {
        return this.getUnique("getRelTaskId", taskId);
    }
}
