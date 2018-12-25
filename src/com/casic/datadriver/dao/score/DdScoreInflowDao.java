package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 增加使用add
     * 删除使用delById
     * 更新使用update
     * 查询使用getById, getAll
     */


    /**
     * 通过uid和一级类型查找
     */
    public List<DdScoreInflow> getByUidAndType(Long userId, String sourceType) {
        Map<String, String> param = new HashMap<>(2);
        param.put("userId", String.valueOf(userId));
        param.put("sourceType", sourceType);
        return this.getList("getUserTypeScore", param);
    }

    /**
     * 带分页的
     */
    public List<DdScoreInflow> getByUidAndType(QueryFilter queryFilter) {
        return this.getBySqlKey("getUserTypeScore", queryFilter);
    }

    /**
     * 通过uid和二级类型查找
     */
    public List<DdScoreInflow> getByUidAndDetail(Long userId, String sourceDetail) {
        Map<String, String> param = new HashMap<>(2);
        param.put("userId", String.valueOf(userId));
        param.put("sourceDetail", sourceDetail);
        return this.getList("getUserDetailScore", param);
    }
}