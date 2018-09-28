package com.casic.datadriver.controller.coin;

import com.casic.datadriver.model.coin.DdRank;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.casic.datadriver.service.coin.DdScoreService;
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

    private SysOrgDao sysOrgDao;

    @Autowired
    public CoinController(DdScoreInflowService ddScoreInflowService,
                          SysUserDao sysUserDao,
                          DdScoreService ddScoreService,
                          CoinService coinService,
                          SysOrgDao sysOrgDao) {
        this.sysUserDao = sysUserDao;
        this.ddScoreService = ddScoreService;
        this.coinService = coinService;
        this.ddScoreInflowService = ddScoreInflowService;
        this.sysOrgDao = sysOrgDao;
    }

    /**
     * 赚取积分接口
     * @param account 身份证号
     * @param sourceScore 分数
     * @param sourceType 一级类型
     * @param sourceDetail 二级类型
     * @param updTime 更新时间
     * @throws Exception the exception
     */
    @RequestMapping("add")
    @ResponseBody
    public void add(String account, String sourceScore, String sourceType, String sourceDetail, String updTime, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            resultMsg = coinService.addScore(account, sourceScore, sourceType, sourceDetail, updTime);
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }
    /**
     * 获取个人所有详情
     * @param response 响应
     * @throws Exception 扔
     */
    @RequestMapping("personalScore")
    @ResponseBody
    public JSONArray personalScore(String account, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            //返回的map
            Map<String, Integer> personalMap = new HashMap<>(6);
            //全局总数
            int total = 0;
            for (DdScoreInflow ddScoreInflow : qiushiInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("quanjuTotal", total);
            //奉献总数
            total = 0;
            for (DdScoreInflow ddScoreInflow : fengxianInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("fengxianTotal", total);
            //求实总数
            total = 0;
            for (DdScoreInflow ddScoreInflow : qiushiInflows) {
                total += ddScoreInflow.getSourceScore();
            }
            personalMap.put("qiushiTotal", total);
            //首先获取个人的一级类型月积分
            List<DdScore> monthList = ddScoreService.getPersonal(userId);
            for (DdScore ddScore : monthList) {
                if (ScoreRegulation.QUAN_JU.equals(ddScore.getScoreType())) {
                    personalMap.put("quanjuMonth", ddScore.getScoreTotal());
                } else if (ScoreRegulation.FENG_XIAN.equals(ddScore.getScoreType())) {
                    personalMap.put("fengxianMonth", ddScore.getScoreTotal());
                } else if (ScoreRegulation.QIU_SHI.equals(ddScore.getScoreType())) {
                    personalMap.put("qiushiMonth", ddScore.getScoreTotal());
                }
            }
            //组装json
            jsonR = JSONArray.fromObject(personalMap);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
        return jsonR;
    }
    /**
     * @param response 响应
     * @return JSONArray
     * @throws Exception  扔
     */
    @RequestMapping("rank")
    @ResponseBody
    public void getRank(String sourceType,HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR = null;
        try {
            //初始化列表
            List<DdScore> totalList = ddScoreService.getAllScore();
            List<DdRank> itemList = new ArrayList<>();
            //列表填写
            for (DdScore aTotalList : totalList) {
                if (sourceType.equals(aTotalList.getScoreType())) {
                    DdRank e = new DdRank();
                    e.setUserName(aTotalList.getUserName());
                    String orgName = sysOrgDao.getOrgsByUserId(aTotalList.getUid()).get(0).getOrgName();
                    e.setOrgName(orgName);
                    e.setScoreTotal(aTotalList.getScoreTotal());
                    itemList.add(e);
                }
            }
            //列表排序
            itemList.sort(new Comparator<DdRank>() {
                @Override
                public int compare(DdRank o1, DdRank o2) {
                    return o2.getScoreTotal().compareTo(o1.getScoreTotal());
                }
            });
            //列表截断，应该是根据不同类型选择不同数目
            if (itemList.size() > 25) {
                itemList = itemList.subList(0, 25);
            }
            //写排名
            int i = 1;
            for(DdRank item : itemList) {
                item.setRank(i++);
            }
            //组装json
            jsonR = JSONArray.fromObject(itemList);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
//            writeResultMessage(response.getWriter(), callback + "("+jsonR.toString()+")", ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }


    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
