package com.casic.datadriver.service.exchange;

import com.casic.datadriver.dao.score.DdGoldenCoinDao;
import com.casic.datadriver.dao.score.DdScoreOutflowDao;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.model.coin.DdRank;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysOrgDao;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.casic.datadriver.manager.ScoreRegulation.*;

/**
 * @Author: workhub
 * @Description:
 * @Date: 创建于 2018/10/24
 */

@Service
public class ExchangeService extends BaseService<DdGoldenCoin> {

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    @Resource
    private DdScoreService ddScoreService;

    @Resource
    private DdScoreOutflowDao ddScoreOutflowDao;

    @Resource
    private SysOrgDao sysOrgDao;

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }

    public List<DdGoldenCoin> getPersonal(long uid) {
        return ddGoldenCoinDao.getPersonal(uid);
    }

    /**
     * 获得某一种积分的排名中奖名单
     * @param scoreType 一级类型
     * @return 获奖表
     */
    public List<DdRank> getRankByType(String scoreType) {
        List<DdScore> ddScoreList = new ArrayList<>();
        switch (scoreType) {
            //全局
            case QUAN_JU:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
                break;
            //奉献
            case FENG_XIAN:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
            //求实
            case QIU_SHI:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QIU_SHI, QIU_SHI);
                break;
            default:
        }
        List<DdRank> rankList = new ArrayList<>();
        Integer i = 1;
        for(DdScore ddScore : ddScoreList) {
            DdRank ddRank = new DdRank();
            ddRank.setRank(i);
            ddRank.setUserName(ddScore.getUserName());
            String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
            ddRank.setOrgName(orgName);
            ddRank.setScoreTotal(ddScore.getScoreTotal());
            ddRank.setUserId(ddScore.getUserId());
            rankList.add(ddRank);
        }
        return rankList;
    }

    /**
     * 获取参与抽奖名单
     * @return Rank名单
     */
    public List<DdRank> getLotteryRank() {
        List<DdScore> ddScoreList = ddScoreService.getAllScore();
        List<DdRank> lotteryList = new ArrayList<>();
        //获取所有用户三项和
        for(DdScore ddScore : ddScoreList) {
            if(CHUANG_XIN.equals(ddScore.getScoreType())) {
                continue;
            }
            Boolean isHave = false;
            for(DdRank ddRank : lotteryList) {
                if(ddRank.getUserId().equals(ddScore.getUserId())) {
                    Integer newSum = ddRank.getScoreTotal() + ddScore.getScoreTotal();
                    ddRank.setScoreTotal(newSum);
                    isHave = true;
                    break;
                }
            }
            if(!isHave) {
                DdRank ddRank = new DdRank();
                ddRank.setUserId(ddScore.getUserId());
                ddRank.setUserName(ddScore.getUserName());
                String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
                ddRank.setOrgName(orgName);
                ddRank.setScoreType(SUM_QFQ);
                ddRank.setScoreTotal(ddScore.getScoreTotal());
            }
        }
        //已发币名单
        List<DdRank> quanjuList = getRankByType(QUAN_JU);
        List<DdRank> fengxianList = getRankByType(FENG_XIAN);
        List<DdRank> qiushiList = getRankByType(QIU_SHI);
        List<Long> exceptList = new ArrayList<>();
        for(DdRank ddRank : quanjuList) {
            if(!exceptList.contains(ddRank.getUserId())) {
                exceptList.add(ddRank.getUserId());
            }
        }
        for(DdRank ddRank : fengxianList) {
            if(!exceptList.contains(ddRank.getUserId())) {
                exceptList.add(ddRank.getUserId());
            }
        }
        for(DdRank ddRank : qiushiList) {
            if(!exceptList.contains(ddRank.getUserId())) {
                exceptList.add(ddRank.getUserId());
            }
        }
        //筛选
        Iterator<DdRank> it = lotteryList.iterator();
        while(it.hasNext()) {
            DdRank x = it.next();
            if(x.getScoreTotal() < LOTTERY_BASE) {
                it.remove();
                continue;
            }
            if(exceptList.contains(x.getUserId())) {
                it.remove();
            }
        }
        return lotteryList;
    }

    /**
     * 根据传入类型进行积分兑换，定时器周期调用还没有添加
     * @param scoreType 一级类型
     */
    public void consume(String scoreType) {
        //TODO:magic number 100
        List<DdScore> ddScoreList = new ArrayList<>();
        Integer lastRank = 0;
        switch (scoreType) {
            //全局
            case QUAN_JU:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
                //取出最末排名积分
                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
                break;
            //奉献
            case FENG_XIAN:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
                //取出最末排名积分
                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
                break;
            //求实
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
