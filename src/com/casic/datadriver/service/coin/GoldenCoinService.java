package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdGoldenCoinDao;
import com.casic.datadriver.dao.coin.DdScoreDao;
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
 * @Modified:
 */
@Service
public class GoldenCoinService extends BaseService<DdGoldenCoin> {

    @Resource
    DdGoldenCoinDao ddGoldenCoinDao;

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
