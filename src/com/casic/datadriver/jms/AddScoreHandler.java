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

        logger.info("handle message : " + addScoreModel.getAccount()
                + " " + addScoreModel.getSourceDetail() + " score : " + addScoreModel.getSourceScore());

        //TODO:判断是否当天消息
        if (addScoreModel.getAccount() == null) {
            logger.warn("用户id为空或者获取日期不正确");
            return;
        }
        //获取用户
        ISysUser sysUser = sysUserDao.getByAccount(addScoreModel.getAccount());
        if (addScoreModel.getResourceId() != null) {
            if (!ddScoreInflowService.isResourceAvailable(
                    sysUser.getUserId(), addScoreModel.getSourceDetail(), addScoreModel.getResourceId())) {
                logger.warn("该资源分数已加");
                return;
            }
        }
        if (addScoreModel.getSourceScore() == null) {
            logger.warn("积分为空");
            return;
        }
        //这里仅传入detail
        List<DdScoreInflow> todayInflows =
                ddScoreInflowService.getTodayScoreDetail(sysUser.getUserId(), addScoreModel.getSourceDetail());
        //判断当前积分是否超出当日上限
        Integer todayScore = 0;
        for (DdScoreInflow ddScoreInflow : todayInflows) {
            todayScore += ddScoreInflow.getSourceScore();
        }
        Boolean isOverFlow = scoreRegulation.isOverFlow(
                Integer.valueOf(addScoreModel.getSourceScore()), todayScore, addScoreModel.getSourceDetail());
        if (isOverFlow) {
            logger.warn("单日积分总量已满");
            return;
        }
        //增加流水
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setId(UniqueIdUtil.genId());
        ddScoreInflow.setUserId(sysUser.getUserId());
        ddScoreInflow.setSourceScore(Integer.valueOf(addScoreModel.getSourceScore()));
        ddScoreInflow.setSourceDetail(addScoreModel.getSourceDetail());
        ddScoreInflow.setSourceType(addScoreModel.getSourceType());
        ddScoreInflow.setUpdTime(new Date());
        ddScoreInflow.setUserName(sysUser.getFullname());
        ddScoreInflow.setOrgId(sysUser.getOrgId());
        String orgName = sysOrgDao.getOrgsByUserId(sysUser.getUserId()).get(0).getOrgName();
        ddScoreInflow.setOrgName(orgName);
        ddScoreInflow.setResourceId(addScoreModel.getResourceId());
        //写入数据库和缓存
        ddScoreInflowService.add(ddScoreInflow);
        logger.info("赚取积分成功 " + ddScoreInflow.getUserId() + ddScoreInflow.getSourceDetail());
        //添加总积分量
        ddScoreService.updateScore(ddScoreInflow, null);
    }
}