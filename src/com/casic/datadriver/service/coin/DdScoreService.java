package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdScoreDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DdScoreService extends BaseService<DdScore> {

    private final Map<String, DdScore> scoreListCache = new ConcurrentHashMap<String, DdScore>();

    DdScoreDao ddScoreDao;

    @Autowired
    public DdScoreService(DdScoreDao ddScoreDao) {
        this.ddScoreDao = ddScoreDao;
//        List<DdScore> ddScoreList = ddScoreDao.selectAllScore();
//        for (DdScore ddScore:ddScoreList){
//            initCache(String.valueOf(ddScore.getUid()), ddScore);
//        }
    }

    public void delAll(Long[] lAryId) {
        for(Long id:lAryId){
            ddScoreDao.delById(id);
        }
    }

    @Override
    protected IEntityDao<DdScore, Long> getEntityDao() {
        return this.ddScoreDao;
    }

    public void increaseScore(Long uid, DdScoreInflow ddScoreInflow){
        //生成积分统计对象
        DdScore ddScore = new DdScore();
        ddScore.setId(UniqueIdUtil.genId());
        ddScore.setScoreTotal(ddScoreInflow.getSourceScore());
        ddScore.setScoreType(ddScoreInflow.getSourceType());
        ddScore.setCrtTime(ddScoreInflow.getUpdTime());
        ddScore.setUid(ddScoreInflow.getUid());

        //获取积分统计缓存，每一个用户对应一个积分统计对象，如果有就取出，没有就写数据库并写缓存
        DdScore ddScoreTemp = getCache(String.valueOf(uid), ddScore);
        if (ddScoreTemp != null){
            //计算出
            Integer scoreTemp = ddScoreTemp.getScoreTotal() + ddScoreInflow.getSourceScore();

            ddScoreTemp.setUdpTime(ddScoreInflow.getUpdTime());
            ddScoreTemp.setScoreType(ddScoreInflow.getSourceType());
            ddScoreTemp.setScoreTotal(scoreTemp);

            ddScoreDao.updateScore(ddScoreTemp);
        }
    }

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

    private void initCache(String uid, DdScore ddScore) {
        // 一般是进行数据库查询，将查询的结果进行缓存
        scoreListCache.put(uid, ddScore);
    }
}
