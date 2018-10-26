package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdGoldenCoinDao;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 */

@Service
public class GoldenCoinService extends BaseService<DdGoldenCoin> {

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddGoldenCoinDao.delById(id);
        }
    }

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }


}