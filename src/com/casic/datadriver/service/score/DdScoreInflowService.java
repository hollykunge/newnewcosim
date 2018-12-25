package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreInflowDao;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.cache.Cache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.web.query.QueryFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.casic.datadriver.manager.ScoreRegulation.CACHE_SCOREINFLOW_PREFIX;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreInflowService extends AbstractService<DdScoreInflow, Long> {

    private final Log logger = LogFactory.getLog(DdScoreInflowService.class);

    private boolean isUseCache = false;

    @Resource
    private DdScoreInflowDao ddScoreInflowDao;

    @Resource
    private ScoreRegulation scoreRegulation;

    @Resource
    private Cache cache;

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
    @SuppressWarnings("unchecked")
    private void addToCache(DdScoreInflow ddScoreInflow) {
        Long userId = ddScoreInflow.getUserId();
        String type = ddScoreInflow.getSourceDetail();
        String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
        List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) cache.getByKey(cacheKey);
        ddScoreInflows.add(ddScoreInflow);
        //TODO:在ddScoreInflows取出来不为空的情况下是否会自动更新缓存?
        cache.add(cacheKey, ddScoreInflows);
    }

    /**
     * 删除一条流水缓存的私有方法
     */
    @SuppressWarnings("unchecked")
    private void deleteFromCache(DdScoreInflow ddScoreInflow) {
        Long userId = ddScoreInflow.getUserId();
        String type = ddScoreInflow.getSourceDetail();
        String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
        if (cache.containKey(cacheKey)) {
            List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) cache.getByKey(cacheKey);
            for (DdScoreInflow ddScoreInflow1 : ddScoreInflows) {
                if (ddScoreInflow1.getId().equals(ddScoreInflow.getId())) {
                    ddScoreInflows.remove(ddScoreInflow1);
                    //TODO:在ddScoreInflows取出来不为空的情况下是否会自动更新缓存?
                    cache.add(cacheKey, ddScoreInflows);
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
    @SuppressWarnings("unchecked")
    private List<DdScoreInflow> formCacheReply(String keySection) {
        List<DdScoreInflow> resultList = new ArrayList<>();
        List<List<DdScoreInflow>> ddScoreInflowsList =
                (List<List<DdScoreInflow>>) cache.getByKeySection(CACHE_SCOREINFLOW_PREFIX, keySection);
        for (List<DdScoreInflow> ddScoreInflows : ddScoreInflowsList) {
            resultList.addAll(ddScoreInflows);
        }
        return resultList;
    }

    /**
     * 重写增加
     */
    @Override
    public void add(DdScoreInflow ddScoreInflow) {
        super.add(ddScoreInflow);
        //写缓存
        if (isUseCache) {
            addToCache(ddScoreInflow);
        }
    }

    /**
     * 重写删除
     */
    @Override
    public void delById(Long id) {
        if (isUseCache) {
            //删缓存
            DdScoreInflow ddScoreInflow = ddScoreInflowDao.getById(id);
            deleteFromCache(ddScoreInflow);
        }
        super.delById(id);
    }

    /**
     * 重写删除一片
     */
    @Override
    public void delByIds(Long[] ids) {
        for (Long id : ids) {
            delById(id);
        }
    }

    /**
     * 重写更改
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(DdScoreInflow ddScoreInflow) {
        super.update(ddScoreInflow);
        if (isUseCache) {
            //更新缓存
            Long userId = ddScoreInflow.getUserId();
            String type = ddScoreInflow.getSourceDetail();
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + type;
            List<DdScoreInflow> ddScoreInflows = (List<DdScoreInflow>) cache.getByKey(cacheKey);
            int index = -1;
            for (DdScoreInflow entity : ddScoreInflows) {
                if (entity.getId().equals(ddScoreInflow.getId())) {
                    index = ddScoreInflows.indexOf(entity);
                    break;
                }
            }
            if (index < 0) {
                logger.warn("DdScoreInflow缓存中没有该对象，更新失败 " + ddScoreInflow.getId());
            } else {
                ddScoreInflows.set(index, ddScoreInflow);
                cache.add(cacheKey, ddScoreInflow);
            }
        }
    }

    /**
     * 重写查找，缓存中没有则在数据库查询
     * 这个方法尽量少用，因为通过id从缓存索引不高效
     */
    @Override
    @SuppressWarnings("unchecked")
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
        return super.getById(id);
    }


    /**
     * 重写查找所有，缓存中没有则在数据库查询
     *
     * @return DdScoreInflow列表
     */
    @Override
    public List<DdScoreInflow> getAll() {
        if (isUseCache) {
            return formCacheReply("");
        }
        return super.getAll();
    }

    /**
     * 获取当前用户今日特定类型流水，按二级类型
     *
     * @param userId       用户id
     * @param sourceDetail 二级类型
     * @return 今日该类型所有流水
     */
    @SuppressWarnings("unchecked")
    public List<DdScoreInflow> getTodayScoreDetail(Long userId, String sourceDetail) {
        List<DdScoreInflow> ddScoreInflows;
        if (isUseCache) {
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + sourceDetail;
            ddScoreInflows = (List<DdScoreInflow>) cache.getByKey(cacheKey);
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
    @SuppressWarnings("unchecked")
    public boolean isResourceAvailable(Long userId, String sourceDetail, Long resourceId) {
        List<DdScoreInflow> ddScoreInflows;
        if (isUseCache) {
            String cacheKey = CACHE_SCOREINFLOW_PREFIX + userId + sourceDetail;
            ddScoreInflows = (List<DdScoreInflow>) cache.getByKey(cacheKey);
        } else {
            ddScoreInflows = ddScoreInflowDao.getByUidAndDetail(userId, sourceDetail);
        }
        Iterator<DdScoreInflow> it = ddScoreInflows.iterator();
        while (it.hasNext()) {
            DdScoreInflow x = it.next();
            if (resourceId.equals(x.getResourceId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取个人所在组织流水
     *
     * @param ogrId     ogrId
     * @param sourceType 一级类型
     * @return DdScoreInflow列表
     */
    public List<DdScoreInflow> getOrgTotalScore(Long ogrId, String sourceType) {
        //联合查询
        Map<String, String> param = new HashMap<>(2);
        param.put("orgId", String.valueOf(ogrId));
        param.put("sourceType", sourceType);
        return ddScoreInflowDao.getList("getOrgTotalScore", param);
    }

    /**
     * 分页获取个人的一级类型流水，不支持缓存
     *
     * @param queryFilter {userId："",sourceType: ""}
     * @return DdScoreInflow分页列表
     */
    public List<DdScoreInflow> getByUidAndType(QueryFilter queryFilter) {
        return ddScoreInflowDao.getByUidAndType(queryFilter);
    }
}