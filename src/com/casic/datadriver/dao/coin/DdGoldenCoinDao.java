package com.casic.datadriver.dao.coin;

import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.model.coin.DdScore;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 * @Modified:
 */
@Repository
public class DdGoldenCoinDao extends BaseDao<DdGoldenCoin> {
    @Override
    public Class getEntityClass() {
        return DdGoldenCoin.class;
    }

    public List<DdGoldenCoin> getById(long id) {
        return this.getBySqlKey("getById", id);
    }


    public List<DdGoldenCoin> getPersonal(long uid) {
        return this.getBySqlKey("getPersonal", uid);
    }

    public void updateCoin(DdGoldenCoin ddGoldenCoin){
        this.update(ddGoldenCoin);
    }
}
