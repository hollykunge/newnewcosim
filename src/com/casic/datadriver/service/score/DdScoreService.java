package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.cache.ICache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.casic.datadriver.manager.ScoreRegulation.CACHE_SCORE;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreService extends BaseService<DdScore> {

    private final Log logger = LogFactory.getLog(DdScoreService.class);

    private boolean isUseCache = false;

    @Resource
    private DdScoreDao ddScoreDao;

    @Resource
    private ICache iCache;

    @Override
    protected IEntityDao<DdScore, Long> getEntityDao() {
        return this.ddScoreDao;
    }

    /**
     * 在程序启动时与其他缓存初始化一起调用
     */
    public void initScoreCache() {
        if (isUseCache) {
            List<DdScore> ddScoreList = ddScoreDao.getAll();
            for (DdScore ddScore : ddScoreList) {
                Long userId = ddScore.getUserId();
                String type = ddScore.getScoreType();
                String cacheKey = CACHE_SCORE + String.valueOf(userId) + type;
                iCache.add(cacheKey, ddScore);
            }
        }
    }

    /**
     * 通过一条输入或输出流水更新DdScore，默认在这之前inflow表和outflow表已经更新了
     *
     * @param ddScoreInflow  输入流水
     * @param ddScoreOutflow 输出流水
     */
    public Boolean updateScore(DdScoreInflow ddScoreInflow, DdScoreOutflow ddScoreOutflow) {
        DdScore ddScore = null;
        if (ddScoreInflow != null) {
            Long userId = ddScoreInflow.getUserId();
            String type = ddScoreInflow.getSourceType();
            String cacheKey = CACHE_SCORE + String.valueOf(userId) + type;
            if (isUseCache) {
                ddScore = (DdScore) iCache.getByKey(cacheKey);
            } else {
                ddScore = ddScoreDao.getByUidAndType(userId, type);
            }
            //如果有就取出，没有就写数据库并写缓存
            if (ddScore != null) {
                //计算出
                Integer scoreTemp = ddScore.getScoreTotal() + ddScoreInflow.getSourceScore();
                ddScore.setUdpTime(ddScoreInflow.getUpdTime());
                ddScore.setScoreTotal(scoreTemp);
                //TODO:拿出来的对象操作后会自己写入缓存，新缓存框架是否可以？
                ddScoreDao.updateScore(ddScore);
                logger.info("通过输入流水更新DdScore，更新缓存 " + cacheKey);
            } else {
                //生成积分统计对象
                DdScore ddScoreTemp = new DdScore();
                ddScoreTemp.setId(UniqueIdUtil.genId());
                ddScoreTemp.setUserId(userId);
                ddScoreTemp.setUserName(ddScoreInflow.getUserName());
                ddScoreTemp.setScoreTotal(ddScoreInflow.getSourceScore());
                ddScoreTemp.setScoreType(type);
                ddScoreTemp.setCrtTime(ddScoreInflow.getUpdTime());
                ddScoreTemp.setUdpTime(ddScoreInflow.getUpdTime());
                ddScoreTemp.setOrgId(ddScoreInflow.getOrgId());
                ddScoreTemp.setOrgName(ddScoreInflow.getOrgName());
                ddScoreDao.add(ddScoreTemp);
                iCache.add(cacheKey, ddScoreTemp);
                logger.info("通过输入流水添加DdScore，添加缓存 " + cacheKey);
            }
        }
        if (ddScoreOutflow != null) {
            Long userId = ddScoreOutflow.getUserId();
            String type = ddScoreOutflow.getSourceType();
            String cacheKey = CACHE_SCORE + String.valueOf(userId) + type;
            if (isUseCache) {
                ddScore = (DdScore) iCache.getByKey(cacheKey);
            } else {
                ddScore = ddScoreDao.getByUidAndType(userId, type);
            }
            if (ddScore != null) {
                //计算出
                Integer scoreTemp = ddScore.getScoreTotal() - ddScoreOutflow.getExpendScore();
                if (scoreTemp < 0) {
                    logger.error("用户分数为负，输出流水失败 " + cacheKey);
                    return false;
                }
                ddScore.setUdpTime(ddScoreOutflow.getUdpTime());
                ddScore.setScoreTotal(scoreTemp);
                ddScoreDao.updateScore(ddScore);
                logger.info("通过输出流水更新DdScore，更新缓存 " + cacheKey);
            } else {
                logger.error("DdScore中没有该对象，输出流水失败 " + cacheKey);
                return false;
            }
        }
        return true;
    }

    /**
     * 删除特定一些id的
     *
     * @param lAryId id列表
     */
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            if (isUseCache) {
                //删缓存
                DdScore ddScore = ddScoreDao.getById(id);
                Long userId = ddScore.getUserId();
                String type = ddScore.getScoreType();
                String cacheKey = CACHE_SCORE + String.valueOf(userId) + type;
                iCache.delByKey(cacheKey);
            }
            //删库
            ddScoreDao.delById(id);
        }
    }

    /**
     * 删除某一类型所有的
     *
     * @param sourceType 一级类型
     */
    public void delByType(String sourceType) {
        //删库
        ddScoreDao.delByType(sourceType);
        if (isUseCache) {
            //删缓存
            List<DdScore> ddScores = (List<DdScore>) iCache.getByKeySection(sourceType);
            for (DdScore ddScore : ddScores) {
                Long userId = ddScore.getUserId();
                String cacheKey = CACHE_SCORE + String.valueOf(userId) + sourceType;
                iCache.delByKey(cacheKey);
            }
        }
    }

    /**
     * 修改
     *
     * @param entity DdScore
     */
    public void updateOne(DdScore entity) {
        //更新数据库
        ddScoreDao.update(entity);
        if (isUseCache) {
            //更新缓存
            Long userId = entity.getUserId();
            String type = entity.getScoreType();
            String cacheKey = CACHE_SCORE + String.valueOf(userId) + type;
            iCache.add(cacheKey, entity);
        }
    }

    /**
     * 根据id查询，缓存中没有则在数据库查询
     * 这个方法尽量少用，因为通过id从缓存索引不高效
     */
    @Override
    public DdScore getById(Long id) {
        if (isUseCache) {
            List<DdScore> ddScores = (List<DdScore>) iCache.getByKeySection(CACHE_SCORE);
            for (DdScore ddScore : ddScores) {
                if (ddScore.getId().equals(id)) {
                    return ddScore;
                }
            }
            logger.warn("DdScore缓存中没有该对象，查找失败 " + id);
        }
        return ddScoreDao.getById(id);
    }

    /**
     * 查询所有
     */
    public List<DdScore> getAllScore() {
        if (isUseCache) {
            return (List<DdScore>) iCache.getByKeySection(CACHE_SCORE);
        }
        return ddScoreDao.getAll();
    }

    /**
     * 查询个人
     *
     * @param userId 用户id
     * @return DdScore列表
     */
    public List<DdScore> getPersonal(long userId) {
        if (isUseCache) {
            return (List<DdScore>) iCache.getByKeySection(String.valueOf(userId));
        }
        return ddScoreDao.getPersonal(userId);
    }

    /**
     * 查询类型
     *
     * @param sourceType 一级类型
     */
    public List<DdScore> getType(String sourceType) {
        if (isUseCache) {
            return (List<DdScore>) iCache.getByKeySection(sourceType);
        }
        return ddScoreDao.getType(sourceType);
    }

    /**
     * 通过最低分限和积分类型获取积分列表
     *
     * @param least      最低分限
     * @param sourceType 一级类型
     * @return 符合条件的列表
     */
    public List<DdScore> getScoresByLeastAndType(Integer least, String sourceType) {
        List<DdScore> ddScores = this.getType(sourceType);
        Iterator<DdScore> it = ddScores.iterator();
        while (it.hasNext()) {
            DdScore x = it.next();
            if (x.getScoreTotal() < least) {
                it.remove();
            }
        }
        return ddScores;
    }

    /**
     * 通过排名区间和积分类型获取积分列表
     *
     * @param rank       名次
     * @param sourceType 一级类型
     * @return ddScoreList 排序完成
     */
    public List<DdScore> getScoresByRankAndType(Integer rank, String sourceType) {
        List<DdScore> ddScores = this.getType(sourceType);
        Iterator<DdScore> it = ddScores.iterator();
        while (it.hasNext()) {
            DdScore x = it.next();
            if (0 == x.getScoreTotal()) {
                it.remove();
            }
        }
        //排序
        Collections.sort(ddScores, new Comparator<DdScore>() {
            @Override
            public int compare(DdScore ddScore1, DdScore ddScore2) {
                return ddScore2.getScoreTotal().compareTo(ddScore1.getScoreTotal());
            }
        });
        //根据排名数获取列表
        if (ddScores.size() > rank) {
            //符合条件的最后一名分数
            Integer base = ddScores.get(rank - 1).getScoreTotal();
            Iterator<DdScore> it2 = ddScores.iterator();
            while (it2.hasNext()) {
                DdScore x = it2.next();
                if (x.getScoreTotal() < base) {
                    it2.remove();
                }
            }
        }
        return ddScores;
    }
}