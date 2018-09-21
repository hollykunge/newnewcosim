package com.casic.datadriver.service.coin;

import com.casic.datadriver.model.coin.DdScore;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Service
public class DdScoreService extends BaseService<DdScore> {
    @Override
    protected IEntityDao<DdScore, Long> getEntityDao() {
        return null;
    }
}
