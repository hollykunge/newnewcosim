package com.casic.datadriver.service.coin;

import com.casic.datadriver.jms.ScoreMessageProducer;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.*;
import com.casic.datadriver.service.exchange.ExchangeService;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/25
 */

@Service
public class CoinService {

    @Autowired
    private DdScoreService ddScoreService;
    @Autowired
    private DdScoreInflowService ddScoreInflowService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private ScoreRegulation scoreRegulation;

    @Autowired
    private ScoreMessageProducer scoreMessageProducer;

    /**
     * @param addScoreModel 加分模型
     *                      account 身份证号
     *                      sourceScore 分数
     *                      sourceType 一级类型
     *                      sourceDetail 二级类型
     *                      updTime 更新时间
     * @return 是否成功的字符串
     */
    public String addScore(AddScoreModel addScoreModel) {
        String resultMsg;
        //判断参数有效性
        if (!scoreRegulation.dataVerify(addScoreModel.getSourceDetail())) {
            resultMsg = "积分赚取失败！";
            return resultMsg;
        }
        scoreMessageProducer.send(addScoreModel);
        resultMsg = "积分已赚取！";
        return resultMsg;
    }

    /**
     * 获取个人的年积分，月积分，年币数，月币数
     *
     * @param account 身份证号
     * @return 十六行的种类和数目对应关系
     */
    public Map<String, Integer> getPersonal(String account) {
        //获取用户uid
        Long userId = sysUserDao.getByAccount(account).getUserId();
        //首先获取个人的一级类型流水
        List<DdScoreInflow> quanjuInflows =
                ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.QUAN_JU);
        List<DdScoreInflow> fengxianInflows =
                ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.FENG_XIAN);
        List<DdScoreInflow> qiushiInflows =
                ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.QIU_SHI);
        List<DdScoreInflow> chuangxinInflows =
                ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.CHUANG_XIN);
        //返回的map
        Map<String, Integer> personalMap = new HashMap<>(16);

        //全局总分
        int total = 0;
        for (DdScoreInflow ddScoreInflow : quanjuInflows) {
            total += ddScoreInflow.getSourceScore();
        }
        personalMap.put("quanjuTotalScore", total);
        //奉献总分
        total = 0;
        for (DdScoreInflow ddScoreInflow : fengxianInflows) {
            total += ddScoreInflow.getSourceScore();
        }
        personalMap.put("fengxianTotalScore", total);
        //求实总分
        total = 0;
        for (DdScoreInflow ddScoreInflow : qiushiInflows) {
            total += ddScoreInflow.getSourceScore();
        }
        personalMap.put("qiushiTotalScore", total);
        //创新总分
        total = 0;
        for (DdScoreInflow ddScoreInflow : chuangxinInflows) {
            total += ddScoreInflow.getSourceScore();
        }
        personalMap.put("chuangxinTotalScore", total);

        //获取个人的一级类型月积分，首先全置0以防缺项
        personalMap.put("quanjuMonthScore", 0);
        personalMap.put("fengxianMonthScore", 0);
        personalMap.put("qiushiMonthScore", 0);
        personalMap.put("chuangxinMonthScore", 0);
        //直接读取
        List<DdScore> monthList = ddScoreService.getPersonal(userId);
        for (DdScore ddScore : monthList) {
            if (ScoreRegulation.QUAN_JU.equals(ddScore.getScoreType())) {
                personalMap.put("quanjuMonthScore", ddScore.getScoreTotal());
            } else if (ScoreRegulation.FENG_XIAN.equals(ddScore.getScoreType())) {
                personalMap.put("fengxianMonthScore", ddScore.getScoreTotal());
            } else if (ScoreRegulation.QIU_SHI.equals(ddScore.getScoreType())) {
                personalMap.put("qiushiMonthScore", ddScore.getScoreTotal());
            } else if (ScoreRegulation.CHUANG_XIN.equals(ddScore.getScoreType())) {
                personalMap.put("chuangxinMonthScore", ddScore.getScoreTotal());
            }
        }

        //获取年币，首先全置0以防缺项
        personalMap.put("quanjuTotalCoin", 0);
        personalMap.put("fengxianTotalCoin", 0);
        personalMap.put("qiushiTotalCoin", 0);
        personalMap.put("chuangxinTotalCoin", 0);
        List<DdGoldenCoin> personalCoinList = exchangeService.getPersonal(userId);
        for (DdGoldenCoin ddGoldenCoin : personalCoinList) {
            if (ScoreRegulation.QUAN_JU.equals(ddGoldenCoin.getCoinType())) {
                personalMap.put("quanjuTotalCoin", ddGoldenCoin.getCoinNum().intValue());
            } else if (ScoreRegulation.FENG_XIAN.equals(ddGoldenCoin.getCoinType())) {
                personalMap.put("fengxianTotalCoin", ddGoldenCoin.getCoinNum().intValue());
            } else if (ScoreRegulation.QIU_SHI.equals(ddGoldenCoin.getCoinType())) {
                personalMap.put("qiushiTotalCoin", ddGoldenCoin.getCoinNum().intValue());
            } else if (ScoreRegulation.CHUANG_XIN.equals(ddGoldenCoin.getCoinType())) {
                personalMap.put("chuangxinTotalCoin", ddGoldenCoin.getCoinNum().intValue());
            }
        }

        //月币统计，首先全置0以防缺项，表征是否会获得币
        //TODO:未判断最少100分
        personalMap.put("quanjuMonthCoin", 0);
        personalMap.put("fengxianMonthCoin", 0);
        personalMap.put("qiushiMonthCoin", 0);
        personalMap.put("chuangxinMonthCoin", 0);
        //全局月币
        List<DdScore> ddScoreListQuanJu = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_QUAN_JU, ScoreRegulation.QUAN_JU);
        if (!ddScoreListQuanJu.isEmpty()) {
            Integer least = ddScoreListQuanJu.get(ddScoreListQuanJu.size() - 1).getScoreTotal();
            if (personalMap.get("quanjuMonthScore") >= least) {
                personalMap.put("quanjuMonthCoin", 1);
            }
        }
        //奉献月币
        List<DdScore> ddScoreListFengXian = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_FENG_XIAN, ScoreRegulation.FENG_XIAN);
        if (!ddScoreListFengXian.isEmpty()) {
            Integer least = ddScoreListFengXian.get(ddScoreListFengXian.size() - 1).getScoreTotal();
            if (personalMap.get("fengxianMonthScore") >= least) {
                personalMap.put("fengxianMonthCoin", 1);
            }
        }
        //求实月币
        List<DdScore> ddScoreListQiuShi = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_QIU_SHI, ScoreRegulation.QIU_SHI);
        if (!ddScoreListQiuShi.isEmpty()) {
            Integer least = ddScoreListQiuShi.get(ddScoreListQiuShi.size() - 1).getScoreTotal();
            if (personalMap.get("qiushiMonthScore") >= least) {
                personalMap.put("qiushiMonthCoin", 1);
            }
        }
        //创新月币
        if (personalMap.get("chuangxinMonthScore") >= 100) {
            personalMap.put("chuangxinMonthCoin", 1);
        }
        return personalMap;
    }

    /**
     * @param sourceType 一级类型
     * @return 该类型排行榜
     */
    public List<DdRank> getRank(String sourceType) {
        List<DdScore> ddScoreList = ddScoreService.getScoresByRankAndType(ScoreRegulation.RANK_NUM, sourceType);
        List<DdRank> itemList = new ArrayList<>();
        for (DdScore ddScore : ddScoreList) {
            DdRank e = new DdRank();
            e.setUserName(ddScore.getUserName());
            String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
            e.setOrgName(orgName);
            e.setScoreTotal(ddScore.getScoreTotal());
            itemList.add(e);
        }
        //写排名
        int i = 1;
        for (DdRank item : itemList) {
            item.setRank(i++);
        }
        return itemList;
    }

    private final static ThreadLocal<SimpleDateFormat> DATE_FORMATTER2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}

