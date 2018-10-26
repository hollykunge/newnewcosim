package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreOutflowDao;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreOutflowService extends BaseService<DdScoreOutflow> {

    private DdScoreOutflowDao ddScoreOutflowDao;

    @Autowired
    public DdScoreOutflowService(DdScoreOutflowDao ddScoreOutflowDao) {
        this.ddScoreOutflowDao = ddScoreOutflowDao;
    }

    @Override
    protected IEntityDao<DdScoreOutflow, Long> getEntityDao() {
        return this.ddScoreOutflowDao;
    }

}
