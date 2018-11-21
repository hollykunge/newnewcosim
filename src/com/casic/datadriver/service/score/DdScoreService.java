package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdScoreDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.hotent.core.bpmn20.entity.activiti.In;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreService extends BaseService<DdScore> implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, DdScore> SCORE_LIST_CACHE = new ConcurrentHashMap<String, DdScore>();

    @Resource
    private DdScoreDao ddScoreDao;

    @Override
    protected IEntityDao<DdScore, Long> getEntityDao() {
        return this.ddScoreDao;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.initCacheList();
    }

    private void initCacheList() {
        //缓存所有dd_score数据
        List<DdScore> ddScoreList = ddScoreDao.getAll();
        for (DdScore ddScore : ddScoreList) {
            initCache(String.valueOf(ddScore.getUserId()) + ddScore.getScoreType(), ddScore);
        }
    }

    /**
     * 供对外的CoinService调用的，增加DdScore信息
     *
     * @param ddScoreInflow 一条流水
     */
    public Boolean updateScore(DdScoreInflow ddScoreInflow, DdScoreOutflow ddScoreOutflow) {
        if (ddScoreInflow != null) {
            //首先获取积分统计缓存，每一个用户的一种SourceType对应一个积分统计对象
            String cacheKey = String.valueOf(ddScoreInflow.getUserId()) + ddScoreInflow.getSourceType();
            DdScore ddScoreTemp = getCache(cacheKey);
            //如果有就取出，没有就写数据库并写缓存
            if (ddScoreTemp != null) {
                //计算出
                Integer scoreTemp = ddScoreTemp.getScoreTotal() + ddScoreInflow.getSourceScore();
                ddScoreTemp.setUdpTime(ddScoreInflow.getUpdTime());
                ddScoreTemp.setScoreTotal(scoreTemp);
                ddScoreDao.updateScore(ddScoreTemp);
            } else {
                //生成积分统计对象
                DdScore ddScore = new DdScore();
                ddScore.setId(UniqueIdUtil.genId());
                ddScore.setUserId(ddScoreInflow.getUserId());
                ddScore.setUserName(ddScoreInflow.getUserName());
                ddScore.setScoreTotal(ddScoreInflow.getSourceScore());
                ddScore.setScoreType(ddScoreInflow.getSourceType());
                ddScore.setCrtTime(ddScoreInflow.getUpdTime());
                ddScore.setUdpTime(ddScoreInflow.getUpdTime());
                ddScore.setOrgId(ddScoreInflow.getOrgId());
                ddScore.setOrgName(ddScoreInflow.getOrgName());
                //如果缓存中没有该用户，把该用户积分对象先写入数据库，再缓存到concurrentHashMap中
                ddScoreDao.add(ddScore);
                initCache(cacheKey, ddScore);
            }
        }
        if (ddScoreOutflow != null) {
            //首先获取积分统计缓存，每一个用户的一种SourceType对应一个积分统计对象
            String cacheKey = String.valueOf(ddScoreOutflow.getUserId()) + ddScoreOutflow.getSourceType();
            DdScore ddScoreTemp = getCache(cacheKey);
            if (ddScoreTemp != null) {
                //计算出
                Integer scoreTemp = ddScoreTemp.getScoreTotal() - ddScoreOutflow.getExpendScore();
                ddScoreTemp.setUdpTime(ddScoreOutflow.getUdpTime());
                ddScoreTemp.setScoreTotal(scoreTemp);
                ddScoreDao.updateScore(ddScoreTemp);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @param cacheKey 缓存key
     * @return DdScore
     */
    public DdScore getCache(String cacheKey) {
        //如果缓冲中有该用户，则返回积分对象
        if (SCORE_LIST_CACHE.containsKey(cacheKey)) {
            return SCORE_LIST_CACHE.get(cacheKey);
        }
        return null;
    }

    /**
     * @param cacheKey 缓存key
     * @param ddScore  DdScore
     */
    private void initCache(String cacheKey, DdScore ddScore) {
        //一般是进行数据库查询，将查询的结果进行缓存
        SCORE_LIST_CACHE.put(cacheKey, ddScore);
    }

    /**
     * 删除
     *
     * @param lAryId id列表
     */
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddScoreDao.delById(id);
        }
    }

    /**
     * 修改
     *
     * @param entity DdScore
     */
    public void updateOne(DdScore entity) {
        ddScoreDao.update(entity);
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return DdScore列表
     */
    public List<DdScore> getById(long id) {
        return ddScoreDao.getById(id);
    }

    /**
     * 查询所有
     *
     * @return 所有
     */
    public List<DdScore> getAllScore() {
        return ddScoreDao.getAll();
    }

    /**
     * 查询个人
     *
     * @param userId 用户id
     * @return DdScore列表
     */
    public List<DdScore> getPersonal(long userId) {
        return ddScoreDao.getPersonal(userId);
    }

    /**
     * 查询类型
     *
     * @param sourceType s
     */
    public List<DdScore> getType(String sourceType) {
        return ddScoreDao.getType(sourceType);
    }

    /**
     * 通过最低分限和积分类型获取积分列表
     *
     * @param least     最低分限
     * @param scoreType 一级类型
     * @return 符合条件的列表
     */
    public List<DdScore> getScoresByLeastAndType(Integer least, String scoreType) {
        List<DdScore> ddScoreList = this.getAllScore();
        Iterator<DdScore> it = ddScoreList.iterator();
        while (it.hasNext()) {
            DdScore x = it.next();
            if ((x.getScoreTotal() < 100) || (!scoreType.equals(x.getScoreType()))) {
                it.remove();
            }
        }
        return ddScoreList;
    }

    /**
     * 通过排名区间和积分类型获取积分列表
     *
     * @param rank      名次
     * @param scoreType 一级类型
     * @return ddScoreList 排序完成
     */
    public List<DdScore> getScoresByRankAndType(Integer rank, String scoreType) {
        //初始化列表
        List<DdScore> ddScoreList = this.getAllScore();
        //筛选类型并去除末尾零分项
        Iterator<DdScore> it = ddScoreList.iterator();
        while (it.hasNext()) {
            DdScore x = it.next();
            if ((x.getScoreTotal() == 0) || (!scoreType.equals(x.getScoreType()))) {
                it.remove();
            }
        }
        //排序
        Collections.sort(ddScoreList, new Comparator<DdScore>() {
            @Override
            public int compare(DdScore ddScore1, DdScore ddScore2) {
                return ddScore2.getScoreTotal().compareTo(ddScore1.getScoreTotal());
            }
        });
        //根据排名数获取列表
        if (ddScoreList.size() > rank) {
            //符合条件的最后一名分数
            Integer base = ddScoreList.get(rank - 1).getScoreTotal();
            Iterator<DdScore> it2 = ddScoreList.iterator();
            while (it2.hasNext()) {
                DdScore x = it2.next();
                if (x.getScoreTotal() < base) {
                    it2.remove();
                }
            }
        }
        return ddScoreList;
    }

    public void updateByType(DdScore ddScore){
        ddScoreDao.updateByType(ddScore);
    }
}