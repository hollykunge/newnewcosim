package com.casic.datadriver.service.coin;

import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.util.UniqueIdUtil;
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
 * @Date: 创建于 2018/9/25
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

    /**
     * @param account 身份证号
     * @param sourceScore 分数
     * @param sourceType 一级类型
     * @param sourceDetail 二级类型
     * @param updTime 更新时间
     * @return 是否成功的字符串
     */
    public String addScore(String account, String sourceScore, String sourceType, String sourceDetail, String updTime){
        String resultMsg;
        //判断是否当天消息
        Date time = new Date();
        Date today = new Date();
        Boolean isToday = false;
//        if (updTime !=null && updTime != ""){
//            time = toDate(updTime);
//        }else {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        }


        if (time != null) {
            String nowDate = DATE_FORMATTER2.get().format(today);
            String timeDate = DATE_FORMATTER2.get().format(time);
            if (nowDate.equals(timeDate)) {
                isToday = true;
            }
        }
        if (account != null && isToday) {
            //获取用户
            ISysUser sysUser = sysUserDao.getByAccount(account);
            if (sourceScore != null) {
                //这里仅传入detail
                List<DdScoreInflow> todayInflows = ddScoreInflowService.getTodayScore(sysUser.getUserId(), sourceDetail);
                //判断当前积分是否超出当日上限
                Integer todayScore = 0;
                for (DdScoreInflow ddScoreInflow : todayInflows) {
                    todayScore += ddScoreInflow.getSourceScore();
                }
                Boolean isOverFlow = scoreRegulation.isOverFlow(Integer.valueOf(sourceScore), todayScore, sourceDetail);
                if (!isOverFlow) {
                    //增加流水
                    DdScoreInflow ddScoreInflow = new DdScoreInflow();
                    ddScoreInflow.setId(UniqueIdUtil.genId());
                    ddScoreInflow.setUid(sysUser.getUserId());
                    ddScoreInflow.setSourceScore(Integer.valueOf(sourceScore));
                    ddScoreInflow.setSourceDetail(sourceDetail);
                    ddScoreInflow.setSourceType(sourceType);
                    ddScoreInflow.setUpdTime(updTime);
                    ddScoreInflow.setUserName(sysUser.getFullname());
                    //写入数据库和缓存
                    ddScoreInflowService.add(ddScoreInflow);
                    resultMsg = "赚取积分成功";
                    //添加总积分量
                    ddScoreService.updateScore(ddScoreInflow, null);
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

    private final static ThreadLocal<SimpleDateFormat> DATE_FORMATTER2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}