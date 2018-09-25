package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdScoreInflowDao;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.manager.ScoreRegulation;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Service
public class DdScoreInflowService extends BaseService<DdScoreInflow> {

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
    public void add(DdScoreInflow ddScoreInflow) {
        ddScoreInflowDao.addCoin(ddScoreInflow);
    }

    /**
     * 获取当前用户今日特定类型积分量
     *
     * @param uid
     * @param sourceDetail
     * @return
     */
    public List<DdScoreInflow> getTodayScore(Long uid, String sourceDetail, String updTime) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("uid", String.valueOf(uid));
        param.put("sourceDetail", sourceDetail);
        List<DdScoreInflow> ddScoreInflows = ddScoreInflowDao.getList("getTodayAllScore", param);

        List<DdScoreInflow> todayInflows = new ArrayList<>();
        for (DdScoreInflow ddScoreInflow : ddScoreInflows) {
            if (scoreRegulation.isToday(ddScoreInflow.getUpdTime())) {
                todayInflows.add(ddScoreInflow);
            }
        }
        return todayInflows;
    }

    //通过uid获取所有
    public List<DdScoreInflow> getByUid(long uid) {
        return ddScoreInflowDao.getBySqlKey("uid");
    }
    //批量删除
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddScoreInflowDao.delById(id);
        }
    }
    //修改一个
    public void updateOne(DdScoreInflow entity) {
        ddScoreInflowDao.update(entity);
    }
    //获取一个
    public DdScoreInflow getById(long id) {
        return ddScoreInflowDao.getById(id);
    }
    //获取所有
    public List<DdScoreInflow> getAllScoreInflow() {
        return ddScoreInflowDao.getAll();
    }
}
