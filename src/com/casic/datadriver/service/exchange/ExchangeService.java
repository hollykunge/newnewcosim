package com.casic.datadriver.service.exchange;

import com.casic.datadriver.dao.score.DdGamblerDao;
import com.casic.datadriver.dao.score.DdGoldenCoinDao;
import com.casic.datadriver.dao.score.DdScoreOutflowDao;
import com.casic.datadriver.model.coin.*;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysOrgDao;

import com.hotent.platform.dao.system.SysUserDao;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.casic.datadriver.manager.ScoreRegulation.*;

/**
 * @Author: workhub
 * @Description: 为月底结算服务写的service
 * 首先获取三种币的排行前列名单（60，20，20），每人一个币
 * 创新分每100一个币
 * 获取结束后写分数输出表（同步更新了月积分表，除创新外清零），写得币表
 * 在剩余表中选抽奖人，写入gambler数据库
 * 抽奖，得出中奖人，写入gambler数据库，写得币表
 * 目前分数输出表并不是所有分数输出，对应了得币情况
 * @Date: 创建于 2018/10/24
 */

@Service
public class ExchangeService extends BaseService<DdGoldenCoin> {

    private final Log logger = LogFactory.getLog(ExchangeService.class);

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    @Resource
    private DdScoreService ddScoreService;

    @Resource
    private DdScoreOutflowDao ddScoreOutflowDao;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private DdGamblerDao ddGamblerDao;

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }

    /**
     * 特定种类积分每月排名前列人员公开方法，删月积分对应项（耗光），增加输出流水，加币
     *
     * @param scoreType 一级类型
     * @return 人员列表
     */
    public Set<RankModel> getRankByType(String scoreType) {
        List<DdScore> ddScoreList;
        //读月积分数据库，获取排名集
        switch (scoreType) {
            case QUAN_JU:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QUAN_JU, QUAN_JU);
                break;
            case FENG_XIAN:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_FENG_XIAN, FENG_XIAN);
                break;
            case QIU_SHI:
                ddScoreList = ddScoreService.getScoresByRankAndType(LIMIT_QIU_SHI, QIU_SHI);
                break;
            case CHUANG_XIN:
                ddScoreList = ddScoreService.getScoresByLeastAndType(CHUANG_XIN_BASE, CHUANG_XIN);
                break;
            default:
                return null;
        }
        //返回集
        Set<RankModel> rankList = new HashSet<>();
        Integer i = 1;
        //处理排名集
        for (DdScore ddScore : ddScoreList) {
            //消耗的积分，默认所有，创新会变
            Integer consumeScore = ddScore.getScoreTotal();
            //获取币数，默认为1，创新会变
            Integer getCoin = 1;
            if (CHUANG_XIN.equals(scoreType)) {
                //TODO:创新每月累计，不删？
                //统计获币数
                Integer chuangxinCoin = ddScore.getScoreTotal() / CHUANG_XIN_BASE;
                //消耗积分
                consumeScore = chuangxinCoin * CHUANG_XIN_BASE;
                //赋值获币数
                getCoin = chuangxinCoin;
            }
            //更新输出流水表，其中会同步更新月积分表
            this.consumeScore(ddScore.getUserId(), scoreType, MONTH_RANK, consumeScore, true);
            //写得币表
            this.gainCoin(ddScore.getUserId(), scoreType, getCoin);
            //返回集
            RankModel ddRank = new RankModel();
            ddRank.setRank(i);
            ISysUser user = sysUserDao.getById(ddScore.getUserId());
            ddRank.setUserName(user.getFullname());
            ddRank.setOrgName(user.getOrgName());
            ddRank.setScoreTotal(consumeScore);
            ddRank.setUserId(ddScore.getUserId());
            rankList.add(ddRank);
            i++;
        }
        return rankList;
    }

    /**
     * 写消耗积分私有方法
     *
     * @param userId       用户ID
     * @param scoreType    一级类型
     * @param consumeType  消耗原因
     * @param consumeScore 消耗分数
     * @param isClearMonth 是否同步删除月积分
     */
    private void consumeScore(Long userId,
                              String scoreType,
                              String consumeType,
                              Integer consumeScore,
                              boolean isClearMonth) {
        DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();
        ddScoreOutflow.setId(UniqueIdUtil.genId());
        ddScoreOutflow.setUserId(userId);
        ddScoreOutflow.setSourceType(scoreType);
        ddScoreOutflow.setExpendDetail(consumeType);
        ddScoreOutflow.setExpendScore(consumeScore);
        ddScoreOutflow.setUdpTime(DATE_FORMATTER2.get().format(new Date()));
        if(isClearMonth) {
            Boolean done = ddScoreService.updateScore(null, ddScoreOutflow);
            if (done) {
                ddScoreOutflowDao.add(ddScoreOutflow);
            } else {
                logger.warn("兑币失败");
                return;
            }
        } else {
            ddScoreOutflowDao.add(ddScoreOutflow);
        }
        logger.info(userId + "使用" + consumeScore + "分兑换一枚" + scoreType + "币");
    }

    /**
     * 写得币表私有方法
     *
     * @param userId   用户
     * @param coinType 一级类型
     * @param coinNum  数目
     */
    private void gainCoin(Long userId, String coinType, Integer coinNum) {
        List<DdGoldenCoin> userCoinList = ddGoldenCoinDao.getPersonal(userId);
        DdGoldenCoin userTypeCoin = new DdGoldenCoin();
        //币表中是否有
        Boolean isHave = false;
        for (DdGoldenCoin ddGoldenCoin : userCoinList) {
            if (coinType.equals(ddGoldenCoin.getCoinType())) {
                userTypeCoin = ddGoldenCoin;
                isHave = true;
                break;
            }
        }
        if (isHave) {
            Long nowCoin = userTypeCoin.getCoinNum();
            userTypeCoin.setCoinNum(nowCoin + coinNum);
            ddGoldenCoinDao.updateCoin(userTypeCoin);
        } else {
            userTypeCoin.setId(UniqueIdUtil.genId());
            userTypeCoin.setUserId(userId);
            userTypeCoin.setCoinType(coinType);
            userTypeCoin.setCoinNum(Integer.toUnsignedLong(coinNum));
            ISysUser user = sysUserDao.getById(userId);
            userTypeCoin.setUserName(user.getFullname());
            userTypeCoin.setOrgId(user.getOrgId());
            userTypeCoin.setOrgName(user.getOrgName());
            ddGoldenCoinDao.add(userTypeCoin);
        }
    }

    /**
     * 获取参与抽奖名单公开方法
     * 三项和至少200分，上限150人
     *
     * @return 一张名单
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
            //非零分用户
            if (0 == ddScore.getScoreTotal()) {
                continue;
            }
            //写个人三项积分和列表
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
                String orgName = sysUserDao.getById(ddScore.getUserId()).getOrgName();
                rankModel.setOrgName(orgName);
                rankModel.setScoreType(SUM_QFQ);
                rankModel.setScoreTotal(ddScore.getScoreTotal());
            }
        }
        //筛选200分以上
        StringBuilder gamList = new StringBuilder();
        Iterator<RankModel> it = lotteryList.iterator();
        while (it.hasNext()) {
            RankModel x = it.next();
            if (x.getScoreTotal() < LOTTERY_BASE) {
                it.remove();
            } else {
                gamList.append(x.getUserId()).append(",");
            }
        }
        //写gambler数据库
        DdGambler ddGambler = new DdGambler();
        ddGambler.setId(UniqueIdUtil.genId());
        //TODO:DANGER
        ddGambler.setPeriod(Integer.toUnsignedLong(201811));
        ddGambler.setGamblerNum(lotteryList.size());
        ddGambler.setGamblerName(gamList.toString());
        ddGamblerDao.add(ddGambler);
        return lotteryList;
    }

    /**
     * 抽奖公开方法
     *
     * @return 一张名单
     */
    public List<RankModel> getLotteryResult() {
        //TODO:DANGER
        DdGambler ddGambler = ddGamblerDao.getByPeriod(Integer.toUnsignedLong(201811)).get(0);
        String lotteryList = ddGambler.getGamblerName();
        String[] lotteryArray = lotteryList.split(",");
        Set<Long> winnerUidSet = new HashSet<>();
        if(lotteryArray.length > LOTTERY_MIN_POOL) {
            List<Integer> result = randomList(lotteryArray.length);
            Iterator<Integer> it = result.iterator();
            while (it.hasNext()) {
                Integer x = it.next();
                winnerUidSet.add(Long.getLong(lotteryArray[x]));
            }
        } else {
            for(String s : lotteryArray) {
                winnerUidSet.add(Long.getLong(s));
            }
        }

        List<RankModel> winnerList = new ArrayList<>();
        for(Long uid : winnerUidSet) {
            RankModel rankModel = new RankModel();
            ISysUser user = sysUserDao.getById(uid);
            rankModel.setUserName(user.getFullname());
            rankModel.setOrgName(user.getOrgName());
            rankModel.setScoreType(SUM_QFQ);
            rankModel.setScoreTotal(0);
            winnerList.add(rankModel);
        }
        /*
        if (rankModelList.size() > LOTTERY_MIN_POOL) {
            List<Integer> result = randomList(rankModelList.size());
            Iterator<Integer> it = result.iterator();
            while (it.hasNext()) {
                Integer x = it.next();
                winnerList.add(rankModelList.get(x));
            }
        } else {
            winnerList = rankModelList;
        }
        */
        //写获币数据库，抽奖数据库，不写输出流水和月积分数据库
        StringBuilder winList = new StringBuilder();
        for (RankModel rankModel : winnerList) {
            winList.append(rankModel.getUserId()).append(",");
            this.consumeScore(rankModel.getUserId(), rankModel.getScoreType(),
                    MONTH_LOTTERY, rankModel.getScoreTotal(), false);
            this.gainCoin(rankModel.getUserId(), rankModel.getScoreType(), 1);
        }

        ddGambler.setWinnerNum(winnerList.size());
        ddGambler.setWinnerName(winList.toString());
        ddGamblerDao.update(ddGambler);
        //最后，清空本月三种积分数据库
        ddScoreService.delByType(QUAN_JU);
        ddScoreService.delByType(FENG_XIAN);
        ddScoreService.delByType(QIU_SHI);
        return winnerList;
    }

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

    private final static ThreadLocal<SimpleDateFormat> DATE_FORMATTER2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}