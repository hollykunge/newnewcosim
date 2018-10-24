package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/19
 */

@Repository
public class DdScoreInflowDao extends BaseDao<DdScoreInflow> {
    @Override
    public Class getEntityClass() {
        return DdScoreInflow.class;
    }
    /**
     * 增加流水
     */
    public void addCoin(DdScoreInflow ddScoreInflow) {
        this.add(ddScoreInflow);
    }
}
