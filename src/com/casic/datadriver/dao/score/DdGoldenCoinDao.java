package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 */

@Repository
public class DdGoldenCoinDao extends BaseDao<DdGoldenCoin> {

    @Override
    public Class getEntityClass() {
        return DdGoldenCoin.class;
    }

    /**
     * 通过id索引一项
     */
    public List<DdGoldenCoin> getById(long id) {
        return this.getBySqlKey("getById", id);
    }


    /**
     * 根据用户id获取所有币种
     *
     * @param uid 用户id
     */
    public List<DdGoldenCoin> getPersonal(long uid) {
        return this.getBySqlKey("getPersonal", uid);
    }

    /**
     * 更新
     */
    public void updateCoin(DdGoldenCoin ddGoldenCoin) {
        this.update(ddGoldenCoin);
    }
}