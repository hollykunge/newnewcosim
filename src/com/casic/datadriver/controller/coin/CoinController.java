package com.casic.datadriver.controller.coin;

import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.casic.datadriver.service.coin.DdScoreService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    DdScoreInflowService ddScoreInflowService;

    SysUserDao sysUserDao;

    ScoreRegulation scoreRegulation;

    DdScoreService ddScoreService;

    @Autowired
    public CoinController(DdScoreInflowService ddScoreInflowService,
                          SysUserDao sysUserDao,
                          ScoreRegulation scoreRegulation,
                          DdScoreService ddScoreService) {
        this.ddScoreInflowService = ddScoreInflowService;
        this.sysUserDao = sysUserDao;
        this.scoreRegulation = scoreRegulation;
        this.ddScoreService = ddScoreService;
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
            Boolean isNow = false;
            Date time = toDate(updTime);
            Date today = new Date();
            if(time != null){
                String nowDate = dateFormater2.get().format(today);
                String timeDate = dateFormater2.get().format(time);
                if(nowDate.equals(timeDate)){
                    isNow = true;
                }
            }
            if (uid != null && isNow) {
                //通过身份证号获取user
                ISysUser sysUser = sysUserDao.getByAccount(uid);
                if (sourceScore != null) {
                    List<DdScoreInflow> todayInflows = ddScoreInflowService.getTodayScore(sysUser.getUserId(), sourceDetail, updTime);

                    Integer todayScore = 0;
                    for (DdScoreInflow ddScoreInflow : todayInflows) {
                        todayScore += ddScoreInflow.getSourceScore();
                    }
                    Boolean isOverFlow = scoreRegulation.isOverFlow(Integer.valueOf(sourceScore), todayScore, sourceDetail);
                    //判断当前积分是否超出当日上限
                    if (!isOverFlow) {
                        DdScoreInflow ddScoreInflow = new DdScoreInflow();
                        ddScoreInflow.setId(UniqueIdUtil.genId());
                        ddScoreInflow.setUid(sysUser.getUserId());
                        ddScoreInflow.setSourceScore(Integer.valueOf(sourceScore));
                        ddScoreInflow.setSourceDetail(sourceDetail);
                        ddScoreInflow.setSourceType(sourceType);
                        ddScoreInflow.setUpdTime(updTime);
                        ddScoreInflowService.add(ddScoreInflow);
                        resultMsg = getText("赚取积分成功", ddScoreInflow.toString());
                        ddScoreService.increaseScore(ddScoreInflow.getUid(), ddScoreInflow);
                    } else {
                        resultMsg = getText("单日积分总量已满", "");
                    }
                } else {
                    resultMsg = getText("积分为空", uid);
                }
            } else {
                resultMsg = getText("用户id为空或者获取日期不正确", sourceType);
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
