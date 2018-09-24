package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdScoreDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Service
public class DdScoreService extends BaseService<DdScore> implements ApplicationListener {

    public static final Map<String, DdScore> scoreListCache = new ConcurrentHashMap<String, DdScore>();

    @Resource
    DdScoreDao ddScoreDao;

    @Resource
    SysUserDao sysUserDao;

    private void initCacheList() {
        //缓存所有dd_score数据
        List<DdScore> ddScoreList = ddScoreDao.getAll();
        for (DdScore ddScore : ddScoreList) {
            //用户名和
            initCache(String.valueOf(ddScore.getUid()) + ddScore.getScoreType(), ddScore);
        }
    }

    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddScoreDao.delById(id);
        }
    }

    @Override
    protected IEntityDao<DdScore, Long> getEntityDao() {
        return this.ddScoreDao;
    }

    public void increaseScore(Long uid, DdScoreInflow ddScoreInflow) {
        //生成积分统计对象
        DdScore ddScore = new DdScore();
        ddScore.setId(UniqueIdUtil.genId());
        ddScore.setUserName(sysUserDao.getById(ddScoreInflow.getUid()).getUsername());
        ddScore.setScoreTotal(ddScoreInflow.getSourceScore());
        ddScore.setScoreType(ddScoreInflow.getSourceType());
        ddScore.setCrtTime(ddScoreInflow.getUpdTime());
        ddScore.setUid(ddScoreInflow.getUid());

        //获取积分统计缓存，每一个用户对应一个积分统计对象，如果有就取出，没有就写数据库并写缓存
        DdScore ddScoreTemp = getCache(String.valueOf(ddScore.getUid()) + ddScore.getScoreType(), ddScore);
        if (ddScoreTemp != null) {
            //计算出
            Integer scoreTemp = ddScoreTemp.getScoreTotal() + ddScoreInflow.getSourceScore();

            ddScoreTemp.setUdpTime(ddScoreInflow.getUpdTime());
            ddScoreTemp.setScoreType(ddScoreInflow.getSourceType());
            ddScoreTemp.setScoreTotal(scoreTemp);

            ddScoreDao.updateScore(ddScoreTemp);
        }
    }

    /**
     * 获取缓存，如果缓存中没有就插入数据库并写缓存
     *
     * @param uid
     * @param ddScore
     * @return
     */
    public DdScore getCache(String uid, DdScore ddScore) {
        // 如果缓冲中有该用户，则返回积分对象
        if (scoreListCache.containsKey(uid)) {
            return scoreListCache.get(uid);
        }
        // 如果缓存中没有该用户，把该用户积分对象先写入数据库，再缓存到concurrentHashMap中
        ddScoreDao.add(ddScore);
        initCache(uid, ddScore);
        return null;
    }

    /**
     * 插入缓存
     *
     * @param uid
     * @param ddScore
     */
    private void initCache(String uid, DdScore ddScore) {
        // 一般是进行数据库查询，将查询的结果进行缓存
        scoreListCache.put(uid, ddScore);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        this.initCacheList();
    }
}
