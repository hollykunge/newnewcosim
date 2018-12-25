package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/19
 */

@Repository
public class DdScoreOutflowDao extends BaseDao<DdScoreOutflow> {

    @Override
    public Class getEntityClass() {
        return DdScoreOutflow.class;
    }

    /**
     * 增加使用add
     * 删除使用delById
     * 更新使用update
     * 查询使用getById, getAll
     */

}