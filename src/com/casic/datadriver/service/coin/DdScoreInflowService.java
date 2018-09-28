package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdScoreInflowDao;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.manager.ScoreRegulation;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Service
public class DdScoreInflowService extends BaseService<DdScoreInflow> implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, DdScoreInflow> SCORE_INFLOW_LIST_CACHE
            = new ConcurrentHashMap<String, DdScoreInflow>();

    private DdScoreInflowDao ddScoreInflowDao;

    private ScoreRegulation scoreRegulation;

    @Autowired
    public DdScoreInflowService(DdScoreInflowDao ddScoreInflowDao, ScoreRegulation scoreRegulation) {
        this.ddScoreInflowDao = ddScoreInflowDao;
        this.scoreRegulation = scoreRegulation;
    }
    @Override
    protected IEntityDao<DdScoreInflow, Long> getEntityDao() {
        return this.ddScoreInflowDao;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.initCacheList();
    }
    private void initCacheList() {
        //缓存所有数据
        List<DdScoreInflow> ddScoreInflows = ddScoreInflowDao.getAll();
        for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
            initCache(String.valueOf(ddScoreInflow.getUid()) + ddScoreInflow.getSourceDetail(), ddScoreInflow);
        }
    }
    private void initCache(String cacheKey, DdScoreInflow ddScoreInflow) {
        //一般是进行数据库查询，将查询的结果进行缓存
        SCORE_INFLOW_LIST_CACHE.put(cacheKey, ddScoreInflow);
    }

    /**
     * 增加
     * @param ddScoreInflow DdScoreInflow
     */
    @Override
    public void add(DdScoreInflow ddScoreInflow) {
        //写数据库
        ddScoreInflowDao.addCoin(ddScoreInflow);
        //写缓存
        String cacheKey = String.valueOf(ddScoreInflow.getUid()) + ddScoreInflow.getSourceDetail();
        initCache(cacheKey, ddScoreInflow);
    }
    /**
     * 删除
     * @param lAryId id列表
     */
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddScoreInflowDao.delById(id);
        }
    }
    /**
     * 通过id查找
     * @param id id
     * @return DdScoreInflow
     */
    public DdScoreInflow getById(long id) {
        return ddScoreInflowDao.getById(id);
    }
    /**
     * 通过用户id查找
     * @param uid uid
     * @return DdScoreInflow流水
     */
    public List<DdScoreInflow> getByUid(long uid) {
        Map<String, String> param = new HashMap<>(1);
        param.put("uid", String.valueOf(uid));
        return ddScoreInflowDao.getList("getByUid", param);
    }
    /**
     * 更改
     * @param entity DdScoreInflow
     */
    public void updateOne(DdScoreInflow entity) {
        ddScoreInflowDao.update(entity);
    }
    /**
     * 查找所有
     * @return DdScoreInflow列表
     */
    public List<DdScoreInflow> getAllScoreInflow() {
        return ddScoreInflowDao.getAll();
    }
    /**
     * 获取当前用户今日特定类型流水，按二级类型
     * @param uid 用户id
     * @param sourceDetail 二级类型
     * @return 今日该类型所有流水
     */
    List<DdScoreInflow> getTodayScore(Long uid, String sourceDetail) {
        //联合查询
        Map<String, String> param = new HashMap<>(2);
        param.put("uid", String.valueOf(uid));
        param.put("sourceDetail", sourceDetail);
        List<DdScoreInflow> ddScoreInflows =
                ddScoreInflowDao.getList("getTodayDetailScore", param);

        List<DdScoreInflow> todayInflows = new ArrayList<>();
        for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
            if (scoreRegulation.isToday(ddScoreInflow.getUpdTime())) {
                todayInflows.add(ddScoreInflow);
            }
        }
        return todayInflows;
    }
    /**
     * 获取个人的一级类型流水
     * @param uid uid
     * @param sourceType 一级类型
     * @return DdScoreInflow列表
     */
    public List<DdScoreInflow> getTypeTotalScore(Long uid, String sourceType) {
        //联合查询
        Map<String, String> param = new HashMap<>(2);
        param.put("uid", String.valueOf(uid));
        param.put("sourceType", sourceType);
        return ddScoreInflowDao.getList("getTypeTotalScore", param);
    }
}