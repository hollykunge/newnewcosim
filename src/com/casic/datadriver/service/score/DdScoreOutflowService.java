package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreOutflowDao;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.cache.Cache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.UniqueIdUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreOutflowService extends AbstractService<DdScoreOutflow, Long> {

    private final Log logger = LogFactory.getLog(DdScoreService.class);

    private boolean isUseCache = false;

    @Resource
    private DdScoreOutflowDao ddScoreOutflowDao;

    @Resource
    private DdScoreService ddScoreService;

    @Resource
    private Cache cache;

    @Override
    protected IEntityDao<DdScoreOutflow, Long> getEntityDao() {
        return this.ddScoreOutflowDao;
    }

    /**
     * 在程序启动时与其他缓存初始化一起调用
     */
    public void initScoreOutflowCache() {
        //outflow未使用缓存
    }

    /**
     * 消耗积分
     *
     * @param userId       用户ID
     * @param scoreType    一级类型
     * @param consumeType  消耗原因
     * @param consumeScore 消耗分数
     * @param isClearMonth 是否同步删除月积分
     */
    void consumeScore(Long userId,
                      String scoreType,
                      String consumeType,
                      Integer consumeScore,
                      boolean isClearMonth) {
        DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
        ddScoreOutflow.setId(UniqueIdUtil.genId());
        ddScoreOutflow.setUserId(userId);
        ddScoreOutflow.setSourceType(scoreType);
        ddScoreOutflow.setExpendDetail(consumeType);
        ddScoreOutflow.setExpendScore(consumeScore);
        ddScoreOutflow.setUdpTime(new Date());
        if (isClearMonth) {
            Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
            if (done) {
                ddScoreOutflowDao.add(ddScoreOutflow);
            } else {
                logger.warn("月度积分表消耗积分失败");
                return;
            }
        } else {
            ddScoreOutflowDao.add(ddScoreOutflow);
        }
        logger.info(userId + "使用" + consumeScore + "分兑换一枚" + scoreType + "币");
    }
}