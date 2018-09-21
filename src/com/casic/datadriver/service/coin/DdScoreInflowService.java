package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdScoreInflowDao;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.model.task.TaskInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Service
public class DdScoreInflowService extends BaseService<DdScoreInflow> {
    @Resource
    DdScoreInflowDao ddScoreInflowDao;
    @Override
    protected IEntityDao<DdScoreInflow, Long> getEntityDao() {
        return this.ddScoreInflowDao;
    }

    @Override
    public void add(DdScoreInflow ddScoreInflow) {
        ddScoreInflowDao.addCoin(ddScoreInflow);
    }

}
