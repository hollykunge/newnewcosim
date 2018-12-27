package com.casic.datadriver.controller.coin;

import com.alibaba.fastjson.JSON;
import com.casic.datadriver.manager.ScoreRegulation;
import com.casic.datadriver.model.coin.AddScoreModel;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.model.coin.RankModel;
import com.casic.datadriver.model.data.OrderDataRelation;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.data.OrderDataRelationService;
import com.casic.datadriver.service.data.PrivateDataService;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.GenericController;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 主要是积分部分的对外接口，可以提供丰富的积分和协同币展示方式
 *
 * @Author: hollykunge
 * @Date: 创建于 2018/9/20
 */

@Controller
@RequestMapping("/coin/")
public class CoinController extends GenericController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private PrivateDataService privateDataService;

    @Autowired
    private OrderDataRelationService orderDataRelationService;

    @Autowired
    private DdScoreInflowService ddScoreInflowService;

    /**
     * 赚取积分接口，参数不能改
     *
     * @param uid          身份证号
     * @param sourceScore  分数
     * @param sourceType   一级类型
     * @param sourceDetail 二级类型
     * @param updTime      更新时间
     */
    @RequestMapping("add")
    @ResponseBody
    public void add(String uid,
                    String sourceScore,
                    String sourceType,
                    String sourceDetail,
                    String updTime,
                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            AddScoreModel addScoreModel = new AddScoreModel();
            addScoreModel.setAccount(uid);
            addScoreModel.setSourceScore(sourceScore);
            addScoreModel.setSourceType(sourceType);
            addScoreModel.setSourceDetail(sourceDetail);
            addScoreModel.setUpdTime(new Date());
            resultMsg = coinService.addScore(addScoreModel);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", resultMsg);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonObject + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 测试接口
     */
    @RequestMapping("test")
    @ResponseBody
    public void testGetUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (request.getHeader("clientip") != null) {
            jsonObject.put("clientip", request.getHeader("clientip"));
        }
        if (request.getHeader("username") != null) {
            jsonObject.put("username", request.getHeader("username"));
        }
        if (request.getHeader("password") != null) {
            jsonObject.put("password", request.getHeader("password"));
        }

        jsonObject.put("test", "test");
        try {
            // 解决跨域
            String callback = request.getParameter("callback");
            if (callback == null) {
                callback = "success";
            }
            response.getWriter().write("{" + callback + ":" + jsonObject + "}");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), "获取用户信息" + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 获取个人所有详情
     */
    @RequestMapping("personalScore")
    @ResponseBody
    public void personalScore(String account, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR = null;
        try {
            Map<String, Integer> personalMap = coinService.getPersonal(account);
            jsonR = JSONArray.fromObject(personalMap);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 获取排名
     */
    @RequestMapping("rank")
    @ResponseBody
    public void getRank(String sourceType, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR = null;
        try {
            List<RankModel> itemList = coinService.getRank(sourceType);
            jsonR = JSONArray.fromObject(itemList);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * @param response 年度报告接口：项目数
     * @throws Exception the exception
     */
    @RequestMapping("task")
    @ResponseBody
    public void taskanddata(String uid, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ISysUser sysUser = sysUserService.getByAccount(uid);
        Double quanjuSum = 0.0d;
        Double fengxianSum = 0.0d;
        Double qiushiSum = 0.0d;
        Double chuangxinSum = 0.0d;

        Double quanjuOrgSum = 0.0d;
        Double fengxianOrgSum = 0.0d;
        Double qiushiOrgSum = 0.0d;
        Double chuangxinOrgSum = 0.0d;

        double quanjuNumber = 0.0d;
        double fengxianNumber = 0.0d;
        double qiushiNumber = 0.0d;
        double chuangxinNumber = 0.0d;

        double taskRadar = 0.0d;
        double dataRadar = 0.0d;
                DecimalFormat df = new DecimalFormat("0.0000");
        try {
            Long userId = sysUser.getUserId();
            if (userId != null && userId != 0) {
                List<Project> projectList = projectService.queryProjectBasicInfoList(userId);
                List<TaskInfo> taskListR = taskInfoService.getListByResponceId(userId);
                List<PrivateData> privateDataList = privateDataService.getDataByUserId(userId);
                Integer projectTotal = projectService.getAll().size();
                Integer taskTotal = taskInfoService.getAll().size();
                Integer dataTotal = privateDataService.getAll().size();

                //个人积分总和
                List<DdScoreInflow> quanjuInflows =
                        ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.QUAN_JU);
                for (DdScoreInflow ddScoreInflow : quanjuInflows) {
                    quanjuSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> fengxianInflows =
                        ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.FENG_XIAN);
                for (DdScoreInflow ddScoreInflow : fengxianInflows) {
                    fengxianSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> qiushiInflows =
                        ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.QIU_SHI);
                for (DdScoreInflow ddScoreInflow : qiushiInflows) {
                    qiushiSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> chuangxinInflows =
                        ddScoreInflowService.getTypeTotalScore(userId, ScoreRegulation.CHUANG_XIN);
                for (DdScoreInflow ddScoreInflow : chuangxinInflows) {
                    chuangxinSum += ddScoreInflow.getSourceScore();
                }

                //所在研究室积分总和
                List<DdScoreInflow> quanjuOrg =
                        ddScoreInflowService.getOrgTotalScore(sysUser.getOrgId(), ScoreRegulation.QUAN_JU);
                for (DdScoreInflow ddScoreInflow : quanjuOrg) {
                    quanjuOrgSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> fengxianOrg =
                        ddScoreInflowService.getOrgTotalScore(sysUser.getOrgId(), ScoreRegulation.FENG_XIAN);
                for (DdScoreInflow ddScoreInflow : fengxianOrg) {
                    fengxianOrgSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> qiushiOrg =
                        ddScoreInflowService.getOrgTotalScore(sysUser.getOrgId(), ScoreRegulation.QIU_SHI);
                for (DdScoreInflow ddScoreInflow : qiushiOrg) {
                    qiushiOrgSum += ddScoreInflow.getSourceScore();
                }
                List<DdScoreInflow> chuangxinOrg =
                        ddScoreInflowService.getOrgTotalScore(sysUser.getOrgId(), ScoreRegulation.CHUANG_XIN);
                for (DdScoreInflow ddScoreInflow : chuangxinOrg) {
                    chuangxinOrgSum += ddScoreInflow.getSourceScore();
                }
//                Integer orderNum = 0;
//                for (TaskInfo taskInfo : taskListR) {
//                    List<OrderDataRelation> orderDataRelations = orderDataRelationService.getOrderDataRelationList(taskInfo.getDdTaskId());
//                    orderNum = +orderDataRelations.size();
//                }

                Integer projectNum = projectList.size();
                Integer taskNum = taskListR.size();
                Integer pubNum = privateDataList.size();

                HashMap<String, Object> tempRadar = new HashMap<String, Object>();
                HashMap<String, Object> tempContribution = new HashMap<String, Object>();
                HashMap<String, Object> tempMy2018 = new HashMap<String, Object>();

                taskRadar = (double) projectNum / projectTotal * 0.6 + (double) taskNum / taskTotal * 0.4;
                dataRadar = (double) pubNum / dataTotal;

                if (!Double.isNaN(taskRadar))
                tempRadar.put("taskRadar", Double.parseDouble(df.format(taskRadar)));
                if (!Double.isNaN(dataRadar))
                tempRadar.put("dataRadar", Double.parseDouble(df.format(dataRadar)));

                quanjuNumber = (double) quanjuSum / quanjuOrgSum;
                fengxianNumber = (double) fengxianSum / fengxianOrgSum;
                qiushiNumber = (double) qiushiSum / qiushiOrgSum;
                chuangxinNumber = (double) chuangxinSum / chuangxinOrgSum;

                if (!Double.isNaN(quanjuNumber))
                tempContribution.put("quanju", Double.parseDouble(df.format(quanjuNumber)));
                if (!Double.isNaN(fengxianNumber))
                tempContribution.put("fengxian", Double.parseDouble(df.format(fengxianNumber)));
                if (!Double.isNaN(qiushiNumber))
                tempContribution.put("qiushi", Double.parseDouble(df.format(qiushiNumber)));
                if (!Double.isNaN(chuangxinNumber))
                tempContribution.put("chuangxin", Double.parseDouble(df.format(chuangxinNumber)));

                tempMy2018.put("project", projectNum);
                tempMy2018.put("task", taskNum);

                HashMap<String, Object> temp1 = new HashMap<>();
                temp1.put("radarDT", tempRadar);
                temp1.put("Contribution", tempContribution);
                temp1.put("my2018", tempMy2018);

                String jsonString = JSON.toJSONString(temp1);
                response.getWriter().write(jsonString);
            }
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }
}