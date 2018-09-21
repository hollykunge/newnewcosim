package com.casic.datadriver.service.coin;

import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.model.task.TaskInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Service
public class DdScoreOutflowService extends BaseService<DdScoreOutflow> {


    @Override
    protected IEntityDao<DdScoreOutflow, Long> getEntityDao() {
        return null;
    }

}
