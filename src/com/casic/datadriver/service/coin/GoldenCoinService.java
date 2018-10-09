package com.casic.datadriver.service.coin;

import com.casic.datadriver.dao.coin.DdGoldenCoinDao;
import com.casic.datadriver.dao.coin.DdScoreDao;
import com.casic.datadriver.dao.coin.DdScoreOutflowDao;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.casic.datadriver.manager.ScoreRegulation.*;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 */
@Service
public class GoldenCoinService extends BaseService<DdGoldenCoin> {

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    @Resource
    private DdScoreService ddScoreService;

    @Resource
    private DdScoreOutflowDao ddScoreOutflowDao;

    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            ddGoldenCoinDao.delById(id);
        }
    }

    public List<DdGoldenCoin> getPersonal(long uid) {
        return ddGoldenCoinDao.getPersonal(uid);
    }

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }

    /**
     * 根据传入类型进行积分兑换，定时器周期调用还没有添加
     * @param scoreType
     */
    public void consume(String scoreType) {
        List<DdScore> ddScoreList = new ArrayList<>();
        Integer lastRank = 0;
        switch (scoreType) {
            //全局取前25名
            case QUAN_JU:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
                //取出最末排名积分
                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
                break;
            //奉献取前5名
            case FENG_XIAN:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
                //取出最末排名积分
                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
                break;
            //求实取前20名
            case QIU_SHI:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QIU_SHI, QIU_SHI);
                //取出最末排名积分
                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
                break;
            //创新每月100个积分换一个币
            case CHUANG_XIN:
                ddScoreList = ddScoreService.getType(CHUANG_XIN);
                break;
            default:
                return;
        }

        //写消耗积分的流水数据库
        for (DdScore ddScore : ddScoreList) {
            int getCoin = 1;
            if(CHUANG_XIN.equals(scoreType)) {
                Integer chuangxinCoin = ddScore.getScoreTotal()/100;
                lastRank = chuangxinCoin * 100;
                getCoin = chuangxinCoin;
            }
            DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
            ddScoreOutflow.setExpendDetail("yuedijiesuan");
            ddScoreOutflow.setExpendScore(lastRank - 1);
            ddScoreOutflow.setId(UniqueIdUtil.genId());
            ddScoreOutflow.setSourceType(ddScore.getScoreType());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ddScoreOutflow.setUdpTime(df.format(new Date()));
            ddScoreOutflow.setUserId(ddScore.getUserId());
            Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
            if (done) {
                ddScoreOutflowDao.add(ddScoreOutflow);
            }
            //获取币
            List<DdGoldenCoin> userCoinList = ddGoldenCoinDao.getPersonal(ddScore.getUserId());
            DdGoldenCoin userTypeCoin = new DdGoldenCoin();
            Boolean isHave = false;
            for(DdGoldenCoin ddGoldenCoin : userCoinList) {
                if(ddGoldenCoin.getCoinType().equals(ddScore.getScoreType())) {
                    userTypeCoin = ddGoldenCoin;
                    isHave = true;
                    break;
                }
            }
            if(isHave) {
                Long nowCoin = userTypeCoin.getTotal();
                userTypeCoin.setTotal(nowCoin + getCoin);
                ddGoldenCoinDao.updateCoin(userTypeCoin);
            } else {
                userTypeCoin.setId(UniqueIdUtil.genId());
                userTypeCoin.setUserId(ddScore.getUserId());
                userTypeCoin.setCoinType(ddScore.getScoreType());
                userTypeCoin.setTotal(Integer.toUnsignedLong(getCoin));
                ddGoldenCoinDao.add(userTypeCoin);
            }

        }
    }
}