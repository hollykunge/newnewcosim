package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreInflowDao;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.cache.ICache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.casic.datadriver.manager.ScoreRegulation.CACHE_SCOREINFLOW_PREFIX;

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
     * 增加一条流水缓存的私有方法
     */
    private void addToCache(DdScoreInflow ddScoreInflow) {
        Long userId = ddScoreInflow.getUserId();
        String type = ddScoreInflow.getSourceDetail();
        String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
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
        String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
        if (iCache.containKey(cacheKey)) {
            List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
            for (DdScoreInflow ddScoreInflow1 : ddScoreInflows) {
                if (ddScoreInflow1.getId().equals(ddScoreInflow.getId())) {
                    ddScoreInflows.remove(ddScoreInflow1);
                    //TODO:在ddScoreInflows取出来不为空的情况下是否会自动更新缓存?
                    iCache.add(cacheKey, ddScoreInflows);
                    return;
                }
            }
            logger.warn("缓存键值中没有该流水 " + cacheKey + ddScoreInflow.getId());
        } else {
            logger.warn("缓存中没有该键值 " + cacheKey);
        }
    }

    /**
     * 通过关键字从缓存中获取与之相关的所有输入流水
     */
    private List<DdScoreInflow> formCacheReply(String keySection) {
        List<DdScoreInflow> resultList = new ArrayList<>();
        List<List<DdScoreInflow>> ddScoreInflowsList =
                (List<List<DdScoreInflow>>) iCache.getByKeySection(CACHE_SCOREINFLOW_PREFIX, keySection);
        for (List<DdScoreInflow> ddScoreInflows : ddScoreInflowsList) {
            resultList.addAll(ddScoreInflows);
        }
        return resultList;
    }

    /**
     * 增加
     */
    @Override
    public void add(DdScoreInflow ddScoreInflow) {
        //写数据库
        ddScoreInflowDao.addOne(ddScoreInflow);
        //写缓存
        if (isUseCache) {
            addToCache(ddScoreInflow);
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
            ddScoreInflowDao.delOneById(id);
        }
    }

    /**
     * 通过id查找，缓存中没有则在数据库查询
     * 这个方法尽量少用，因为通过id从缓存索引不高效
     */
    @Override
    public DdScoreInflow getById(Long id) {
        if (isUseCache) {
            List<DdScoreInflow> ddScoreInflows = formCacheReply("");
            for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
                if (id.equals(ddScoreInflow.getId())) {
                    return ddScoreInflow;
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
        ddScoreInflowDao.updateOne(entity);
        if (isUseCache) {
            //更新缓存
            Long userId = entity.getUserId();
            String type = entity.getSourceDetail();
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
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
            return formCacheReply("");
        }
        return ddScoreInflowDao.getAllInflow();
    }

    /**
     * 获取当前用户今日特定类型流水，按二级类型
     *
     * @param userId       用户id
     * @param sourceDetail 二级类型
     * @return 今日该类型所有流水
     */
    public List<DdScoreInflow> getTodayScoreDetail(Long userId, String sourceDetail) {
        List<DdScoreInflow> ddScoreInflows;
        if (isUseCache) {
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + sourceDetail;
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
            List<DdScoreInflow> ddScoreInflowsUser = formCacheReply(String.valueOf(userId));
            Iterator<DdScoreInflow> it = ddScoreInflowsUser.iterator();
            while (it.hasNext()) {
                DdScoreInflow x = it.next();
                if (!x.getSourceType().equals(sourceType)) {
                    it.remove();
                }
            }
            return ddScoreInflowsUser;
        }
        return ddScoreInflowDao.getByUidAndType(userId, sourceType);
    }

    /**
     * 判断该加分资源是否可用
     *
     * @param userId       uid
     * @param sourceDetail 二级类型
     * @param resourceId   资源类型，一定有才会进来
     * @return 是否能加分
     */
    public boolean isResourceAvailable(Long userId, String sourceDetail, Long resourceId) {
        List<DdScoreInflow> ddScoreInflows;
        if (isUseCache) {
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + sourceDetail;
            ddScoreInflows = (List<DdScoreInflow>) iCache.getByKey(cacheKey);
        } else {
            ddScoreInflows = ddScoreInflowDao.getByUidAndDetail(userId, sourceDetail);
        }
        Iterator<DdScoreInflow> it = ddScoreInflows.iterator();
        while (it.hasNext()) {
            DdScoreInflow x = it.next();
            if (x.getResourceId().equals(resourceId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 分页获取个人的一级类型流水
     *
     * @param queryFilter {userId："",sourceType: ""}
     * @return DdScoreInflow分页列表
     */
    public List<DdScoreInflow> getByUidAndType(QueryFilter queryFilter) {
        return ddScoreInflowDao.getByUidAndType(queryFilter);
    }
}