package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdGambler;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author workhub
 */

@Repository
public class DdGamblerDao extends BaseDao<DdGambler> {

    @Override
    public Class getEntityClass() {
        return DdGambler.class;
    }

    /**
     * 通过月份索引
     *
     * @param period 月份
     */
    public List<DdGambler> getByPeriod(long period) {
        return this.getBySqlKey("getByPeriod", period);
    }
}