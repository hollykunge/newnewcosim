package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 目前add使用genericDao里的方法
 *
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
     * 通过id查一项
     */
    @Override
    public DdScoreInflow getById(Long id) {
        return this.getBySqlKey("getById", id).get(0);
    }

    public List<DdScoreInflow> getByUidAndType(Long userId, String sourceType) {
        Map<String, String> param = new HashMap<>(2);
        param.put("userId", String.valueOf(userId));
        param.put("sourceType", sourceType);
        return this.getList("getUserTypeScore", param);
    }

    public List<DdScoreInflow> getByUidAndDetail(Long userId, String sourceDetail) {
        Map<String, String> param = new HashMap<>(2);
        param.put("userId", String.valueOf(userId));
        param.put("sourceDetail", sourceDetail);
        return this.getList("getUserDetailScore", param);
    }
}