package com.casic.datadriver.service.coin;

import com.alibaba.fastjson.JSON;
import com.casic.datadriver.jms.ScoreMessageProducer;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.*;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.service.data.OrderDataRelationService;
import com.casic.datadriver.service.data.PrivateDataService;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.score.DdGoldenCoinService;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.casic.datadriver.service.score.DdScoreService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.service.system.SysUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对云雀三大接口
 *
 * @Author: hollykunge
 * @Date: 创建于 2018/9/25
 */

@Service
public class CoinService {

    private final Log logger = LogFactory.getLog(CoinService.class);

    @Autowired
    private DdScoreService ddScoreService;
    @Autowired
    private DdScoreInflowService ddScoreInflowService;
    @Autowired
    private DdGoldenCoinService ddGoldenCoinService;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private ScoreRegulation scoreRegulation;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private PrivateDataService privateDataService;

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
        String detail = addScoreModel.getSourceDetail();
        if (!scoreRegulation.dataVerify(detail)) {
            resultMsg = "数据类型错误！";
            logger.warn("Detail类型未计入加分类型 " + detail);
            return resultMsg;
        }
        logger.info("错误追踪，开始发送至队列");
        ddScoreInflowService.handInflowScore(addScoreModel);
//        scoreMessageProducer.send(addScoreModel);
        resultMsg = "积分请求已加入队列！";
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
        Map<String, Integer> personalMap = new HashMap<>(16);

        personalMap.putAll(getPersonalByType(userId, ScoreRegulation.QUAN_JU));
        personalMap.putAll(getPersonalByType(userId, ScoreRegulation.QIU_SHI));
        personalMap.putAll(getPersonalByType(userId, ScoreRegulation.FENG_XIAN));
        personalMap.putAll(getPersonalByType(userId, ScoreRegulation.CHUANG_XIN));

        //是否进入排名拿钱序列
        personalMap.put("quanjuMonthCoin", 0);
        personalMap.put("fengxianMonthCoin", 0);
        personalMap.put("qiushiMonthCoin", 0);
        //全局
        List<DdScore> ddScoreListQuanJu = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_QUAN_JU, ScoreRegulation.QUAN_JU);
        if (!ddScoreListQuanJu.isEmpty()) {
            Integer least = ddScoreListQuanJu.get(ddScoreListQuanJu.size() - 1).getScoreTotal();
            if (personalMap.get("quanjuMonthScore") >= least) {
                personalMap.put("quanjuMonthCoin", 1);
            }
        }
        //奉献
        List<DdScore> ddScoreListFengXian = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_FENG_XIAN, ScoreRegulation.FENG_XIAN);
        if (!ddScoreListFengXian.isEmpty()) {
            Integer least = ddScoreListFengXian.get(ddScoreListFengXian.size() - 1).getScoreTotal();
            if (personalMap.get("fengxianMonthScore") >= least) {
                personalMap.put("fengxianMonthCoin", 1);
            }
        }
        //求实
        List<DdScore> ddScoreListQiuShi = ddScoreService.getScoresByRankAndType(
                ScoreRegulation.LIMIT_QIU_SHI, ScoreRegulation.QIU_SHI);
        if (!ddScoreListQiuShi.isEmpty()) {
            Integer least = ddScoreListQiuShi.get(ddScoreListQiuShi.size() - 1).getScoreTotal();
            if (personalMap.get("qiushiMonthScore") >= least) {
                personalMap.put("qiushiMonthCoin", 1);
            }
        }
        //创新
        personalMap.put("chuangxinMonthCoin",
                personalMap.get("chuangxinMonthScore") / ScoreRegulation.CHUANG_XIN_BASE);

        return personalMap;
    }

    /**
     * 获取个人特定类型积分，否则getPersonal太长
     *
     * @param userId     用户
     * @param sourceType 一级类型
     * @return 总分、总币、月分
     */
    private Map<String, Integer> getPersonalByType(Long userId, String sourceType) {
        Map<String, Integer> personalMap = new HashMap<>(3);
        personalMap.put(sourceType + "TotalScore", 0);
        personalMap.put(sourceType + "MonthScore", 0);
        personalMap.put(sourceType + "TotalCoin", 0);

        //总分
        List<DdScoreInflow> inflows = ddScoreInflowService.getTypeTotalScore(userId, sourceType);
        int total = 0;
        for (DdScoreInflow ddScoreInflow : inflows) {
            total += ddScoreInflow.getSourceScore();
        }
        personalMap.put(sourceType + "TotalScore", total);

        //月分
        List<DdScore> monthList = ddScoreService.getPersonal(userId);
        for (DdScore ddScore : monthList) {
            if (sourceType.equals(ddScore.getScoreType())) {
                personalMap.put(sourceType + "MonthScore", ddScore.getScoreTotal());
                break;
            }
        }

        //总币
        //TODO:混币以一种更醒目的方式展示?
        List<DdGoldenCoin> coinList = ddGoldenCoinService.getPersonal(userId);
        for (DdGoldenCoin ddGoldenCoin : coinList) {
            if (sourceType.equals(ddGoldenCoin.getCoinType())) {
                personalMap.put(sourceType + "TotalCoin", ddGoldenCoin.getCoinNum().intValue());
                break;
            }
        }
        return personalMap;
    }

    /**
     * @param sourceType 一级类型
     * @return 该类型排行榜
     */
    public List<RankModel> getRank(String sourceType) {
        List<DdScore> ddScoreList = ddScoreService.getScoresByRankAndType(ScoreRegulation.RANK_NUM, sourceType);
        List<RankModel> itemList = new ArrayList<>();
        for (DdScore ddScore : ddScoreList) {
            RankModel e = new RankModel();
            e.setUserName(ddScore.getUserName());
            String orgName = sysOrgDao.getOrgsByUserId(ddScore.getUserId()).get(0).getOrgName();
            e.setOrgName(orgName);
            e.setScoreTotal(ddScore.getScoreTotal());
            itemList.add(e);
        }
        //写排名
        int i = 1;
        for (RankModel item : itemList) {
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

    /**
     * 个人积分总和计算
     *
     * @param uid
     * @return
     */
    public Double countUserScore(Long uid, String type) {
        Double scoreSum = 0.0d;
        List<DdScoreInflow> scoreInflows = ddScoreInflowService.getTypeTotalScore(uid, type);
        for (DdScoreInflow ddScoreInflow : scoreInflows) {
            scoreSum += ddScoreInflow.getSourceScore();
        }
        return scoreSum;
    }

    /**
     * 组织积分总和计算
     *
     * @param orgId
     * @return
     */
    public Double countOrgScore(Long orgId, String type) {
        Double scoreSum = 0.0d;
        List<DdScoreInflow> scoreInflows = ddScoreInflowService.getOrgTotalScore(orgId, type);
        for (DdScoreInflow ddScoreInflow : scoreInflows) {
            scoreSum += ddScoreInflow.getSourceScore();
        }
        return scoreSum;
    }

    public String getJsonForReport(String uid) {
        ISysUser sysUser = sysUserService.getByAccount(uid);

        DecimalFormat df = new DecimalFormat("0.0000");

        Long userId = sysUser.getUserId();
        if (userId != null && userId != 0) {
            List<Project> projectList = projectService.queryProjectBasicInfoList(userId);
            List<TaskInfo> taskListR = taskInfoService.getListByResponceId(userId);
            List<PrivateData> privateDataList = privateDataService.getDataByUserId(userId);
            int projectTotal = projectService.getAll().size();
            int taskTotal = taskInfoService.getAll().size();
            int dataTotal = privateDataService.getAll().size();

            //个人积分总和
            Double quanjuSum = countUserScore(userId, ScoreRegulation.QUAN_JU);
            Double fengxianSum = countUserScore(userId, ScoreRegulation.FENG_XIAN);
            Double qiushiSum = countUserScore(userId, ScoreRegulation.QIU_SHI);
            Double chuangxinSum = countUserScore(userId, ScoreRegulation.CHUANG_XIN);

            //所在研究室积分总和
            Double quanjuOrgSum = countOrgScore(sysUser.getOrgId(), ScoreRegulation.QUAN_JU);
            Double fengxianOrgSum = countOrgScore(sysUser.getOrgId(), ScoreRegulation.FENG_XIAN);
            Double qiushiOrgSum = countOrgScore(sysUser.getOrgId(), ScoreRegulation.QIU_SHI);
            Double chuangxinOrgSum = countOrgScore(sysUser.getOrgId(), ScoreRegulation.CHUANG_XIN);

            Integer projectNum = projectList.size();
            Integer taskNum = taskListR.size();
            double pubNum = privateDataList.size();

            HashMap<String, Object> tempMap = new HashMap<String, Object>(8);

            double taskRadar = (double) projectNum / projectTotal * 0.6 + (double) taskNum / taskTotal * 0.4;
            double dataRadar = pubNum / dataTotal;

            if (!Double.isNaN(taskRadar)) {
                tempMap.put("taskRadar", Double.parseDouble(df.format(taskRadar)));
            } else {
                tempMap.put("taskRadar", 0);
            }

            if (!Double.isNaN(dataRadar)) {
                tempMap.put("dataRadar", Double.parseDouble(df.format(dataRadar)));
            } else {
                tempMap.put("dataRadar", 0);
            }

            double quanjuNumber = (double) quanjuSum / quanjuOrgSum;
            double fengxianNumber = (double) fengxianSum / fengxianOrgSum;
            double qiushiNumber = (double) qiushiSum / qiushiOrgSum;
            double chuangxinNumber = (double) chuangxinSum / chuangxinOrgSum;

            if (!Double.isNaN(quanjuNumber)) {
                tempMap.put("quanju", Double.parseDouble(df.format(quanjuNumber)));
            } else {
                tempMap.put("quanju", 0);
            }

            if (!Double.isNaN(fengxianNumber)) {
                tempMap.put("fengxian", Double.parseDouble(df.format(fengxianNumber)));
            } else {
                tempMap.put("fengxian", 0);
            }

            if (!Double.isNaN(qiushiNumber)) {
                tempMap.put("qiushi", Double.parseDouble(df.format(qiushiNumber)));
            } else {
                tempMap.put("qiushi", 0);
            }

            if (!Double.isNaN(chuangxinNumber)) {
                tempMap.put("chuangxin", Double.parseDouble(df.format(chuangxinNumber)));
            } else {
                tempMap.put("chuangxin", 0);
            }

            tempMap.put("project", projectNum);
            tempMap.put("task", taskNum);

            return JSON.toJSONString(tempMap);
        }
        return null;
    }
}

