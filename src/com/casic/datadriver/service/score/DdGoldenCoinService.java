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
public class DdGoldenCoinService extends BaseService<DdGoldenCoin> {

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }

    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddGoldenCoinDao.delOneById(id);
        }
    }

    /**
     * 获取个人金币数
     *
     * @param uid 用户id
     * @return 如果都有的话应该是四项
     */
    public List<DdGoldenCoin> getPersonal(long uid) {
        return ddGoldenCoinDao.getPersonal(uid);
    }
}