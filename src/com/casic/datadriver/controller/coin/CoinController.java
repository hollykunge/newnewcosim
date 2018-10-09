package com.casic.datadriver.controller.coin;

import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.model.coin.DdRank;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.casic.datadriver.service.coin.DdScoreService;
import com.casic.datadriver.service.coin.GoldenCoinService;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysUserDao;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Controller
@RequestMapping("/coin/")
public class CoinController extends GenericController {

    private SysUserDao sysUserDao;

    private DdScoreService ddScoreService;

    private CoinService coinService;

    private DdScoreInflowService ddScoreInflowService;

    private GoldenCoinService goldenCoinService;

    private final static Integer RANK_NUM = 25;

    private SysOrgDao sysOrgDao;

    @Autowired
    public CoinController(DdScoreInflowService ddScoreInflowService,
                          SysUserDao sysUserDao,
                          DdScoreService ddScoreService,
                          CoinService coinService,
                          SysOrgDao sysOrgDao,
                          GoldenCoinService goldenCoinService) {
        this.sysUserDao = sysUserDao;
        this.ddScoreService = ddScoreService;
        this.coinService = coinService;
        this.ddScoreInflowService = ddScoreInflowService;
        this.sysOrgDao = sysOrgDao;
        this.goldenCoinService = goldenCoinService;
    }

    /**
     * 赚取积分接口
     *
     * @param account      身份证号
     * @param sourceScore  分数
     * @param sourceType   一级类型
     * @param sourceDetail 二级类型
     * @param updTime      更新时间
     * @throws Exception the exception
     */
    @RequestMapping("add")
    @ResponseBody
    public void add(String uid, String sourceScore, String sourceType, String sourceDetail, String updTime, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            resultMsg = coinService.addScore(uid, sourceScore, sourceType, sourceDetail, updTime);
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 获取个人所有详情
     *
     * @param response 响应
     * @throws Exception 扔
     */
    @RequestMapping("personalScore")
    @ResponseBody
    public void personalScore(String account, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR = null;
        try {
            //获取用户uid
            Long userId = sysUserDao.getByAccount(account).getUserId();
            //首先获取个人的一级类型总积分
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
            //全局总数
            int total = 0;
            for (DdScoreInflow ddScoreInflow : quanjuInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("quanjuTotalScore", total);
            //奉献总数
            total = 0;
            for (DdScoreInflow ddScoreInflow : fengxianInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("fengxianTotalScore", total);
            //求实总数
            total = 0;
            for (DdScoreInflow ddScoreInflow : qiushiInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("qiushiTotalScore", total);
            //创新总数
            total = 0;
            for (DdScoreInflow ddScoreInflow : chuangxinInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("chuangxinTotalScore", total);
            //首先获取个人的一级类型月积分
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
            if(!personalMap.containsKey("quanjuMonthScore")) {
                personalMap.put("quanjuMonthScore", 0);
            }
            if(!personalMap.containsKey("fengxianMonthScore")) {
                personalMap.put("fengxianMonthScore", 0);
            }
            if(!personalMap.containsKey("qiushiMonthScore")) {
                personalMap.put("qiushiMonthScore", 0);
            }
            if(!personalMap.containsKey("chuangxinMonthScore")) {
                personalMap.put("chuangxinMonthScore", 0);
            }
            //获取年币
            List<DdGoldenCoin> personalCoinList = goldenCoinService.getPersonal(userId);
            for(DdGoldenCoin ddGoldenCoin : personalCoinList) {
                if(ScoreRegulation.QUAN_JU.equals(ddGoldenCoin.getCoinType())) {
                    personalMap.put("quanjuTotalCoin", ddGoldenCoin.getTotal().intValue());
                } else if(ScoreRegulation.FENG_XIAN.equals(ddGoldenCoin.getCoinType())) {
                    personalMap.put("fengxianTotalCoin", ddGoldenCoin.getTotal().intValue());
                } else if(ScoreRegulation.QIU_SHI.equals(ddGoldenCoin.getCoinType())) {
                    personalMap.put("qiushiTotalCoin", ddGoldenCoin.getTotal().intValue());
                } else if(ScoreRegulation.CHUANG_XIN.equals(ddGoldenCoin.getCoinType())) {
                    personalMap.put("chuangxinTotalCoin", ddGoldenCoin.getTotal().intValue());
                }
            }
            if(!personalMap.containsKey("quanjuTotalCoin")) {
                personalMap.put("quanjuTotalCoin", 0);
            }
            if(!personalMap.containsKey("fengxianTotalCoin")) {
                personalMap.put("fengxianTotalCoin", 0);
            }
            if(!personalMap.containsKey("qiushiTotalCoin")) {
                personalMap.put("qiushiTotalCoin", 0);
            }
            if(!personalMap.containsKey("chuangxinTotalCoin")) {
                personalMap.put("chuangxinTotalCoin", 0);
            }
            //获取月币
            //todo 不想写了这sddx
            personalMap.put("quanjuMonthCoin", 0);
            personalMap.put("fengxianMonthCoin", 0);
            personalMap.put("qiushiMonthCoin", 0);
            personalMap.put("chuangxinMonthCoin", 0);
            //组装json
            jsonR = JSONArray.fromObject(personalMap);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * @param response 响应
     * @throws Exception 扔
     */
    @RequestMapping("rank")
    @ResponseBody
    public void getRank(String sourceType, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR = null;
        try {
            List<DdScore> ddScoreList = ddScoreService.getScoresByRankAndType(RANK_NUM, sourceType);
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
            //组装json
            jsonR = JSONArray.fromObject(itemList);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }


    private final static ThreadLocal<SimpleDateFormat> DATE_FORMATER2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
