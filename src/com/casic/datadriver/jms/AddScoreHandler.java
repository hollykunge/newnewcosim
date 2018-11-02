package com.casic.datadriver.jms;

import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.AddScoreModel;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysUserDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wokhub
 */

public class AddScoreHandler implements IJmsHandler {

    private final Log logger = LogFactory.getLog(AddScoreHandler.class);

    @Override
    public void handMessage(Object model) {

        SysUserDao sysUserDao = (SysUserDao) AppUtil.getBean(SysUserDao.class);
        SysOrgDao sysOrgDao = (SysOrgDao) AppUtil.getBean(SysOrgDao.class);
        DdScoreInflowService ddScoreInflowService =
                (DdScoreInflowService) AppUtil.getBean(DdScoreInflowService.class);
        DdScoreService ddScoreService =
                (DdScoreService) AppUtil.getBean(DdScoreService.class);
        ScoreRegulation scoreRegulation =
                (ScoreRegulation) AppUtil.getBean(ScoreRegulation.class);

        AddScoreModel addScoreModel = (AddScoreModel) model;

        String resultMsg;
        //判断是否当天消息
        String timeDate;
        Date time = new Date();
        timeDate = DATE_FORMATTER2.get().format(time);
        if (addScoreModel.getAccount() != null) {
            //获取用户
            ISysUser sysUser = sysUserDao.getByAccount(addScoreModel.getAccount());
            if (addScoreModel.getSourceScore() != null) {
                //这里仅传入detail
                List<DdScoreInflow> todayInflows =
                        ddScoreInflowService.getTodayScore(sysUser.getUserId(), addScoreModel.getSourceDetail());
                //判断当前积分是否超出当日上限
                Integer todayScore = 0;
                for (DdScoreInflow ddScoreInflow : todayInflows) {
                    todayScore += ddScoreInflow.getSourceScore();
                }
                Boolean isOverFlow = scoreRegulation.isOverFlow(
                        Integer.valueOf(addScoreModel.getSourceScore()), todayScore, addScoreModel.getSourceDetail());
                if (!isOverFlow) {
                    //增加流水
                    DdScoreInflow ddScoreInflow = new DdScoreInflow();
                    ddScoreInflow.setId(UniqueIdUtil.genId());
                    ddScoreInflow.setUserId(sysUser.getUserId());
                    ddScoreInflow.setSourceScore(Integer.valueOf(addScoreModel.getSourceScore()));
                    ddScoreInflow.setSourceDetail(addScoreModel.getSourceDetail());
                    ddScoreInflow.setSourceType(addScoreModel.getSourceType());
                    ddScoreInflow.setUpdTime(timeDate);
                    ddScoreInflow.setUserName(sysUser.getFullname());
                    ddScoreInflow.setOrgId(sysUser.getOrgId());
                    String orgName = sysOrgDao.getOrgsByUserId(sysUser.getUserId()).get(0).getOrgName();
                    ddScoreInflow.setOrgName(orgName);
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
    }

    private final static ThreadLocal<SimpleDateFormat> DATE_FORMATTER2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}