package com.casic.datadriver.dao.coin;

import com.casic.datadriver.model.coin.DdScore;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/19
 * @Modified:
 */
@Repository
public class DdScoreDao extends BaseDao<DdScore> {
    @Override
    public Class getEntityClass() {return DdScore.class; }

    /**
     * 更新积分账户
     *
     * @param ddScore
     *            the query filter
     * @return the list
     */
    public void updateScore(DdScore ddScore){
        this.update(ddScore);
    }
    /**
     * 插入
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public void insert(){

    }
    /**
     * Query task basic info list.
     *
     * @param queryFilter
     *            the query filter
     * @return the list
     */
    public void delete(){

    }

    public List<DdScore> getById(long id) {
        return this.getBySqlKey("getById", id);
    }


    public List<DdScore> getPersonal(long uid) {
        return this.getBySqlKey("getPersonal", uid);
    }
}
