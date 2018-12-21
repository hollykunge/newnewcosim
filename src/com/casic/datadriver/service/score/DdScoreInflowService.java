package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreInflowDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.service.cache.ICache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.casic.datadriver.manager.ScoreRegulation.CACHE_SCORE;
import static com.casic.datadriver.manager.ScoreRegulation.CACHE_SCOREINFLOW;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreInflowService extends BaseService<DdScoreInflow> {

    private final Log logger = LogFactory.getLog(DdScoreInflowService.class);

    private boolean isUseCache = false;

    @Resource
    private DdScoreInflowDao ddScoreInflowDao;

    @Resource
    private ScoreRegulation scoreRegulation;

    @Resource
    private ICache iCache;

    @Override
    protected IEntityDao<DdScoreInflow, Long> getEntityDao() {
        return this.ddScoreInflowDao;
    }

    /**
     * 在程序启动时与其他缓存初始化一起调用
     */
    public void initScoreInflowCache() {
        if (isUseCache) {
            List<DdScoreInflow> ddScoreInflowList = ddScoreInflowDao.getAll();
            for (DdScoreInflow ddScoreInflow : ddScoreInflowList) {
                addToCache(ddScoreInflow);
            }
        }
    }

    /**
     * 增加
     *
     * @param ddScoreInflow DdScoreInflow
     */
    @Override
    public void add(DdScoreInflow ddScoreInflow) {
        //写数据库
        ddScoreInflowDao.add(ddScoreInflow);
        //写缓存
        if (isUseCache) {
            addToCache(ddScoreInflow);
        }
    }

    /**
     * 增加一条流水缓存的私有方法
     */
    private void addToCache(DdScoreInflow ddScoreInflow) {
        Long userId = ddScoreInflow.getUserId();
        String type = ddScoreInflow.getSourceDetail();
        String cacheKey = CACHE_SCOREINFLOW + String.valueOf(userId) + type;
        List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
        ddScoreInflows.add(ddScoreInflow);
        //TODO:在ddScoreInflows取出来不为空的情况下是否会自动更新缓存?
        iCache.add(cacheKey, ddScoreInflows);
    }

    /**
     * 删除一条流水缓存的私有方法
     */
    private void deleteFromCache(DdScoreInflow ddScoreInflow) {
        Long userId = ddScoreInflow.getUserId();
        String type = ddScoreInflow.getSourceDetail();
        String cacheKey = CACHE_SCOREINFLOW + String.valueOf(userId) + type;
        if (iCache.containKey(cacheKey)) {
            List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
            for (DdScoreInflow ddScoreInflow1 : ddScoreInflows) {
                if (ddScoreInflow1.getId().equals(ddScoreInflow.getId())) {
                    ddScoreInflows.remove(ddScoreInflow1);
                    iCache.add(cacheKey, ddScoreInflows);
                    return;
                }
            }
            logger.warn("缓存中没有该流水 " + cacheKey + ddScoreInflow.getId());
        } else {
            logger.warn("缓存中没有该键值 " + cacheKey);
        }
    }

    /**
     * 删除
     *
     * @param lAryId id列表
     */
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            if (isUseCache) {
                //删缓存
                DdScoreInflow ddScoreInflow = ddScoreInflowDao.getById(id);
                deleteFromCache(ddScoreInflow);
            }
            //删库
            ddScoreInflowDao.delById(id);
        }
    }

    /**
     * 通过id查找，缓存中没有则在数据库查询
     * 这个方法尽量少用，因为通过id从缓存索引不高效
     */
    @Override
    public DdScoreInflow getById(Long id) {
        if (isUseCache) {
            List<List<DdScoreInflow>> ddScoreInflowsList =
                    (List<List<DdScoreInflow>>) iCache.getByKeySection(CACHE_SCOREINFLOW);
            for (List<DdScoreInflow> ddScoreInflows : ddScoreInflowsList) {
                for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
                    if (ddScoreInflow.getId().equals(id)) {
                        return ddScoreInflow;
                    }
                }
            }
            logger.warn("DdScoreInflow缓存中没有该对象，查找失败 " + id);
        }
        return ddScoreInflowDao.getById(id);
    }

    /**
     * 更改
     *
     * @param entity DdScoreInflow
     */
    public void updateOne(DdScoreInflow entity) {
        //更新数据库
        ddScoreInflowDao.update(entity);
        if (isUseCache) {
            //更新缓存
            Long userId = entity.getUserId();
            String type = entity.getSourceDetail();
            String cacheKey = CACHE_SCOREINFLOW + String.valueOf(userId) + type;
            List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
            int index = -1;
            for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
                if (ddScoreInflow.getId().equals(entity.getId())) {
                    index = ddScoreInflows.indexOf(ddScoreInflow);
                    break;
                }
            }
            if (index < 0) {
                logger.warn("DdScoreInflow缓存中没有该对象，更新失败 " + entity.getId());
            } else {
                ddScoreInflows.set(index, entity);
                iCache.add(cacheKey, entity);

            }
        }
    }

    /**
     * 查找所有，缓存中没有则在数据库查询
     *
     * @return DdScoreInflow列表
     */
    public List<DdScoreInflow> getAllScoreInflow() {
        if (isUseCache) {
            List<DdScoreInflow> allInflow = null;
            List<List<DdScoreInflow>> ddScoreInflowsList =
                    (List<List<DdScoreInflow>>) iCache.getByKeySection(CACHE_SCOREINFLOW);
            for (List<DdScoreInflow> ddScoreInflows : ddScoreInflowsList) {
                allInflow.addAll(ddScoreInflows);
            }
            return allInflow;
        }
        return ddScoreInflowDao.getAll();
    }

    /**
     * 获取当前用户今日特定类型流水，按二级类型
     *
     * @param userId       用户id
     * @param sourceDetail 二级类型
     * @return 今日该类型所有流水
     */
    public List<DdScoreInflow> getTodayScore(Long userId, String sourceDetail) {
        List<DdScoreInflow> ddScoreInflows;
        if (isUseCache) {
            String cacheKey = CACHE_SCOREINFLOW + String.valueOf(userId) + sourceDetail;
            ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
        } else {
            ddScoreInflows = ddScoreInflowDao.getByUidAndDetail(userId, sourceDetail);
        }
        Iterator<DdScoreInflow> it = ddScoreInflows.iterator();
        while (it.hasNext()) {
            DdScoreInflow x = it.next();
            if (!scoreRegulation.isToday(x.getUpdTime())) {
                it.remove();
            }
        }
        return ddScoreInflows;
    }

    /**
     * 获取个人的一级类型流水
     *
     * @param userId     userId
     * @param sourceType 一级类型
     * @return DdScoreInflow列表
     */
    public List<DdScoreInflow> getTypeTotalScore(Long userId, String sourceType) {
        if (isUseCache) {
            //把这个list list的单独写一个函数
            List<List<DdScoreInflow>> ddScoreInflowsList =
                    (List<List<DdScoreInflow>>) iCache.getByKeySection(String.valueOf(userId));

        }
        return ddScoreInflowDao.getByUidAndType(userId, sourceType);
    }

    /**
     * 分页获取个人的一级类型流水
     *
     * @param queryFilter {userId："",sourceType: ""}
     * @return DdScoreInflow分页列表
     */
    public List<DdScoreInflow> getByUidAndType(QueryFilter queryFilter) {
        return ddScoreInflowDao.getBySqlKey("getUserTypeScore", queryFilter);
    }
}