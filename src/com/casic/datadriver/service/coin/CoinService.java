package com.casic.datadriver.service.coin;

import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.appleframe.utils.date.DateUtils.toDate;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/25
 * @Modified:
 */
@Service
public class CoinService {

    private DdScoreInflowService ddScoreInflowService;
    private SysUserDao sysUserDao;
    private ScoreRegulation scoreRegulation;
    private DdScoreService ddScoreService;

    @Autowired
    public CoinService(DdScoreInflowService ddScoreInflowService,
                       SysUserDao sysUserDao,
                       ScoreRegulation scoreRegulation,
                       DdScoreService ddScoreService) {
        this.ddScoreInflowService = ddScoreInflowService;
        this.sysUserDao = sysUserDao;
        this.scoreRegulation = scoreRegulation;
        this.ddScoreService = ddScoreService;
    }

    public String addScore(String uid, String sourceScore, String sourceType, String sourceDetail, String updTime){
        String resultMsg = null;
        Boolean isNow = false;
        Date time = toDate(updTime);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
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
                    ddScoreInflow.setUserName(sysUser.getFullname());
                    ddScoreInflowService.add(ddScoreInflow);
                    resultMsg = "赚取积分成功";
                    //添加总积分量
                    ddScoreService.increaseScore(ddScoreInflow.getUid(), ddScoreInflow);
                } else {
                    resultMsg = "单日积分总量已满";
                }
            } else {
                resultMsg = "积分为空";
            }
        } else {
            resultMsg = "用户id为空或者获取日期不正确";
        }
        return resultMsg;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
