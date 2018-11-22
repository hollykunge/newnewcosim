package com.casic.datadriver.service.exchange;

import com.casic.datadriver.dao.score.DdGoldenCoinDao;
import com.casic.datadriver.dao.score.DdScoreOutflowDao;
import com.casic.datadriver.jms.AddScoreHandler;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.model.coin.RankModel;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysOrgDao;

import org.apache.commons.lang.math.RandomUtils;
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

    private static final List<Long> EXCEPT_USER_LIST = new ArrayList<>();

    /**
     * 获取个人金币数
     *
     * @param uid 用户id
     * @return 如果都有的话应该是四项
     */
    public List<DdGoldenCoin> getPersonal(long uid) {
        return ddGoldenCoinDao.getPersonal(uid);
    }

    /**
     * 根据积分类型清除月积分
     *
     * @param scoreType
     * @return
     */
    private void clearMonthScore(String scoreType) {
        DdScore ddScore = new DdScore();
        Date updTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeDate = dateFormat.format(updTime);
        ddScore.setScoreTotal(0);
        ddScore.setUdpTime(timeDate);
        ddScore.setScoreType(scoreType);
        ddScoreService.updateByType(ddScore);
    }

    /**
     * 统计兑币人员，不包含抽奖人员，加币
     *
     * @param scoreType
     * @return
     */
    public Set<RankModel> getRankByType(String scoreType) {
        List<DdScore> ddScoreList;
        //本月消耗的积分
        Integer consumeScore = 0;
        //积分消耗类型
        String consumeType;
        switch (scoreType) {
            //全局
            case QUAN_JU:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
                clearMonthScore(QUAN_JU);
                consumeType = QUAN_JU_MONTH_RANK;
                break;
            //奉献
            case FENG_XIAN:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
                clearMonthScore(FENG_XIAN);
                consumeType = FENG_XIAN_MONTH_RANK;
                break;
            //求实
            case QIU_SHI:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QIU_SHI, QIU_SHI);
                clearMonthScore(QIU_SHI);
                consumeType = QIU_SHI_MONTH_RANK;
                break;
            //创新
            case CHUANG_XIN:
                ddScoreList = ddScoreService.getScoresByLeastAndType(CHUANG_XIN_BASE, scoreType);
                consumeScore = CHUANG_XIN_BASE;
                consumeType = CHUANG_XIN_MONTH_RANK;
                break;
            default:
                return null;
        }

        Set<RankModel> rankList = new HashSet<>();
        Integer i = 1;
        //币数
        Integer getCoin;
        for (DdScore ddScore : ddScoreList) {
            if (CHUANG_XIN.equals(scoreType)) {
                Integer chuangxinCoin = ddScore.getScoreTotal() / CHUANG_XIN_BASE;
                consumeScore = chuangxinCoin * CHUANG_XIN_BASE;
                getCoin = chuangxinCoin;
            } else {
                getCoin = 1;
                //其他三种币加LIST，LIST的清除在摇完奖后
                if (!EXCEPT_USER_LIST.contains(ddScore.getUserId())) {
                    EXCEPT_USER_LIST.add(ddScore.getUserId());
                }
            }
            RankModel ddRank = new RankModel();
            ddRank.setRank(i);
            ddRank.setUserName(ddScore.getUserName());
            String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
            ddRank.setOrgName(orgName);
            ddRank.setScoreTotal(ddScore.getScoreTotal());
            ddRank.setUserId(ddScore.getUserId());
            rankList.add(ddRank);
            this.consumeScore(consumeType, ddScore);
            this.gainCoin(ddScore, getCoin);
        }
        return rankList;
    }

    /**
     * 消耗积分
     *
     * @param consumeType
     * @param ddScore
     * @return
     */
    private void consumeScore(String consumeType, DdScore ddScore) {
        //消耗积分
        DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
        ddScoreOutflow.setExpendDetail(consumeType);
        ddScoreOutflow.setExpendScore(ddScore.getScoreTotal());
        ddScoreOutflow.setId(UniqueIdUtil.genId());
        ddScoreOutflow.setSourceType(ddScore.getScoreType());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ddScoreOutflow.setUdpTime(df.format(new Date()));
        ddScoreOutflow.setUserId(ddScore.getUserId());
        Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
        if (done) {
            ddScoreOutflowDao.add(ddScoreOutflow);
        }
    }

    /**
     * 赚取二币
     *
     * @param ddScore
     * @return
     */
    private void gainCoin(DdScore ddScore, Integer getCoin) {
        List<DdGoldenCoin> userCoinList = ddGoldenCoinDao.getPersonal(ddScore.getUserId());
        DdGoldenCoin userTypeCoin = new DdGoldenCoin();
        //币表中是否有
        Boolean isHave = false;
        for (DdGoldenCoin ddGoldenCoin : userCoinList) {
            if (ddGoldenCoin.getCoinType().equals(ddScore.getScoreType())) {
                userTypeCoin = ddGoldenCoin;
                isHave = true;
                break;
            }
        }
        if (isHave) {
            Long nowCoin = userTypeCoin.getCoinNum();
            userTypeCoin.setCoinNum(nowCoin + getCoin);
            ddGoldenCoinDao.updateCoin(userTypeCoin);
        } else {
            userTypeCoin.setId(UniqueIdUtil.genId());
            userTypeCoin.setUserId(ddScore.getUserId());
            userTypeCoin.setCoinType(ddScore.getScoreType());
            userTypeCoin.setCoinNum(Integer.toUnsignedLong(getCoin));
            userTypeCoin.setUserName(ddScore.getUserName());
            userTypeCoin.setOrgId(ddScore.getOrgId());
            userTypeCoin.setOrgName(ddScore.getOrgName());
            ddGoldenCoinDao.add(userTypeCoin);
        }
    }

    /**
     * 获取参与抽奖名单
     *
     * @return Rank名单
     */
    public List<RankModel> getLotteryList() {
        List<DdScore> ddScoreList = ddScoreService.getAllScore();
        List<RankModel> lotteryList = new ArrayList<>();
        //获取所有用户三项和
        for (DdScore ddScore : ddScoreList) {
            //非创新分数
            if (CHUANG_XIN.equals(ddScore.getScoreType())) {
                continue;
            }
            //非已排名用户
            if (EXCEPT_USER_LIST.contains(ddScore.getUserId())) {
                continue;
            }
            //写所有人列表
            Boolean isHave = false;
            for (RankModel rankModel : lotteryList) {
                if (rankModel.getUserId().equals(ddScore.getUserId())) {
                    Integer newSum = rankModel.getScoreTotal() + ddScore.getScoreTotal();
                    rankModel.setScoreTotal(newSum);
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                RankModel rankModel = new RankModel();
                rankModel.setUserId(ddScore.getUserId());
                rankModel.setUserName(ddScore.getUserName());
                String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
                rankModel.setOrgName(orgName);
                rankModel.setScoreType(SUM_QFQ);
                rankModel.setScoreTotal(ddScore.getScoreTotal());
            }
        }
        //筛选
        Iterator<RankModel> it = lotteryList.iterator();
        while (it.hasNext()) {
            RankModel x = it.next();
            if (x.getScoreTotal() < LOTTERY_BASE) {
                it.remove();
            }
        }
        return lotteryList;
    }

    /**
     * 抽奖过程
     *
     * @param rankModelList 抽奖列表
     * @return 中奖列表
     */
    public List<RankModel> getLotteryResult(List<RankModel> rankModelList) {
        List<RankModel> lotteryList = new ArrayList<>();
        if (rankModelList.size() > LOTTERY_MIN_POOL) {
            List<Integer> result = randomList(rankModelList.size());
            Iterator<Integer> it = result.iterator();
            while (it.hasNext()) {
                Integer x = it.next();
                lotteryList.add(rankModelList.get(x));
            }
        } else {
            lotteryList = rankModelList;
        }

        //写outflow数据库
        for (RankModel rankModel : lotteryList) {
            DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
            ddScoreOutflow.setExpendDetail(LOTTERY_MONTH);
            ddScoreOutflow.setExpendScore(0);
            ddScoreOutflow.setId(UniqueIdUtil.genId());
            ddScoreOutflow.setSourceType(rankModel.getScoreType());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ddScoreOutflow.setUdpTime(df.format(new Date()));
            ddScoreOutflow.setUserId(rankModel.getUserId());
            Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
            if (done) {
                ddScoreOutflowDao.add(ddScoreOutflow);
            }
        }
        //清除已中奖用户LIST
        EXCEPT_USER_LIST.clear();
        return lotteryList;
    }

    /**
     * 生成随机数接口
     *
     * @param count 最大下标
     * @return 100个随机数
     */
    private List<Integer> randomList(Integer count) {
        List<Integer> result = new ArrayList<>();
        while (result.size() < LOTTERY_MIN_POOL) {
            Integer i = RandomUtils.nextInt(count);
            if (!result.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 根据传入类型进行积分兑换
     *
     * @param scoreType 一级类型
     */
//    public void consume(String scoreType) {
//        //TODO:magic number 100
//        List<DdScore> ddScoreList = new ArrayList<>();
//        Integer lastRank = 0;
//        switch (scoreType) {
//            //全局
//            case QUAN_JU:
//                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
//                //取出最末排名积分
//                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
//                break;
//            //奉献
//            case FENG_XIAN:
//                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
//                //取出最末排名积分
//                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
//                break;
//            //求实
//            case QIU_SHI:
//                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QIU_SHI, QIU_SHI);
//                //取出最末排名积分
//                lastRank = ddScoreList.get(ddScoreList.size() - 1).getScoreTotal();
//                break;
//            //创新每月100个积分换一个币
//            case CHUANG_XIN:
//                ddScoreList = ddScoreService.getType(CHUANG_XIN);
//                break;
//            default:
//                return;
//        }
//
//        //写消耗积分的流水数据库
//        for (DdScore ddScore : ddScoreList) {
//            int getCoin = 1;
//            if (CHUANG_XIN.equals(scoreType)) {
//                Integer chuangxinCoin = ddScore.getScoreTotal() / 100;
//                lastRank = chuangxinCoin * 100;
//                getCoin = chuangxinCoin;
//            }
//            DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
//            ddScoreOutflow.setExpendDetail("yuedijiesuan");
//            ddScoreOutflow.setExpendScore(lastRank - 1);
//            ddScoreOutflow.setId(UniqueIdUtil.genId());
//            ddScoreOutflow.setSourceType(ddScore.getScoreType());
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            ddScoreOutflow.setUdpTime(df.format(new Date()));
//            ddScoreOutflow.setUserId(ddScore.getUserId());
//            Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
//            if (done) {
//                ddScoreOutflowDao.add(ddScoreOutflow);
//            }
//            //获取币
//            List<DdGoldenCoin> userCoinList = ddGoldenCoinDao.getPersonal(ddScore.getUserId());
//            DdGoldenCoin userTypeCoin = new DdGoldenCoin();
//            Boolean isHave = false;
//            for (DdGoldenCoin ddGoldenCoin : userCoinList) {
//                if (ddGoldenCoin.getCoinType().equals(ddScore.getScoreType())) {
//                    userTypeCoin = ddGoldenCoin;
//                    isHave = true;
//                    break;
//                }
//            }
//            if (isHave) {
//                Long nowCoin = userTypeCoin.getCoinNum();
//                userTypeCoin.setCoinNum(nowCoin + getCoin);
//                ddGoldenCoinDao.updateCoin(userTypeCoin);
//            } else {
//                userTypeCoin.setId(UniqueIdUtil.genId());
//                userTypeCoin.setUserId(ddScore.getUserId());
//                userTypeCoin.setCoinType(ddScore.getScoreType());
//                userTypeCoin.setCoinNum(Integer.toUnsignedLong(getCoin));
//                ddGoldenCoinDao.add(userTypeCoin);
//            }
//
//        }
//    }
}
