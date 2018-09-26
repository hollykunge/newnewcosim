package com.casic.datadriver.controller.coin;

import com.casic.datadriver.model.coin.DdRank;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.casic.datadriver.service.coin.DdScoreService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import net.sf.json.JSONArray;
import org.compass.core.json.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.ListComparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.appleframe.utils.date.DateUtils.toDate;
import static com.sun.corba.se.impl.orbutil.CorbaResourceUtil.getText;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Controller
@RequestMapping("/coin/")
public class CoinController extends GenericController {

    private DdScoreInflowService ddScoreInflowService;

    private SysUserDao sysUserDao;

    private ScoreRegulation scoreRegulation;

    private DdScoreService ddScoreService;

    private CoinService coinService;

    @Autowired
    public CoinController(DdScoreInflowService ddScoreInflowService,
                          SysUserDao sysUserDao,
                          ScoreRegulation scoreRegulation,
                          DdScoreService ddScoreService,
                          CoinService coinService) {
        this.ddScoreInflowService = ddScoreInflowService;
        this.sysUserDao = sysUserDao;
        this.scoreRegulation = scoreRegulation;
        this.ddScoreService = ddScoreService;
        this.coinService = coinService;
    }

    /**
     * 赚取积分接口
     *
     * @throws Exception the exception
     */
    @RequestMapping("add")
    @ResponseBody
    public void save(String uid, String sourceScore, String sourceType, String sourceDetail, String updTime, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            resultMsg = coinService.addScore(uid, sourceScore, sourceType, sourceDetail, updTime);
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 获取三个榜单的排名，全局币前25名，奉献币前5名，求实币前20名
     * @param response 响应
     * @return MAP的key为币种，value是带DdRank的List
     * @throws Exception  扔
     */
    @RequestMapping("rank")
    @ResponseBody
    public JSONArray getRank(HttpServletResponse response) throws Exception {
        //String resultMsg = null;
        JSONArray jsonR = null;
        try {
            //初始化列表
            List<DdScore> totalList = ddScoreService.getAllScore();
            List<DdRank> quanjuList = new ArrayList<>();
            List<DdRank> fengxianList = new ArrayList<>();
            List<DdRank> qiushiList = new ArrayList<>();
            //列表填写
            for (DdScore aTotalList : totalList) {
                DdRank e = new DdRank();
                e.setUserName(aTotalList.getUserName());
                e.setScoreTotal(aTotalList.getScoreTotal());
                if ("quanju".equals(aTotalList.getScoreType())) {
                    //e.setScoreType("quanju");
                    quanjuList.add(e);
                } else if ("fengxian".equals(aTotalList.getScoreType())) {
                    //e.setScoreType("fengxian");
                    fengxianList.add(e);
                } else if ("qiushi".equals(aTotalList.getScoreType())) {
                    //e.setScoreType("qiushi");
                    qiushiList.add(e);
                }
            }
            //列表排序
            quanjuList.sort(new Comparator<DdRank>() {
                @Override
                public int compare(DdRank o1, DdRank o2) {
                    return o2.getScoreTotal().compareTo(o1.getScoreTotal());
                }
            });
            fengxianList.sort(new Comparator<DdRank>() {
                @Override
                public int compare(DdRank o1, DdRank o2) {
                    return o2.getScoreTotal().compareTo(o1.getScoreTotal());
                }
            });
            qiushiList.sort(new Comparator<DdRank>() {
                @Override
                public int compare(DdRank o1, DdRank o2) {
                    return o2.getScoreTotal().compareTo(o1.getScoreTotal());
                }
            });
            //列表截断
            if (quanjuList.size() > 25) {
                quanjuList = quanjuList.subList(0, 25);
            }
            if (fengxianList.size() > 5) {
                fengxianList = fengxianList.subList(0, 5);
            }
            if (qiushiList.size() > 15) {
                qiushiList = qiushiList.subList(0, 15);
            }
            //组装json
            Map<String, List<DdRank>> m = new HashMap<>(3);
            m.put("quanju", quanjuList);
            m.put("fengxian", fengxianList);
            m.put("qiushi", qiushiList);
            jsonR = JSONArray.fromObject(m);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
        return jsonR;
    }


    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
