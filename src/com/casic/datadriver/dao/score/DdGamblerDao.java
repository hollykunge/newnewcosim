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
     * 增加使用add
     * 删除使用delById
     * 更新使用update
     * 查询使用getById, getAll
     */

    /**
     * 通过期数索引
     *
     * @param period 期数
     */
    public List<DdGambler> getByPeriod(long period) {
        return this.getBySqlKey("getByPeriod", period);
    }
}