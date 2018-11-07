package com.casic.datadriver.controller.coin;

import com.casic.datadriver.model.coin.AddScoreModel;
import com.casic.datadriver.model.coin.RankModel;
import com.casic.datadriver.service.coin.CoinService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.ResultMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    /**
     * 赚取积分
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
    @Action(description = "赚取积分")
    public void add(String account, String sourceScore, String sourceType, String sourceDetail, String updTime, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            //TODO：传整个model
            AddScoreModel addScoreModel = new AddScoreModel();
            addScoreModel.setAccount(account);
            addScoreModel.setSourceScore(sourceScore);
            addScoreModel.setSourceType(sourceType);
            addScoreModel.setSourceDetail(sourceDetail);
            addScoreModel.setUpdTime(updTime);
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
     * 获取个人所有详情
     *
     * @param response 响应
     * @throws Exception the exception
     */
    @RequestMapping("personalScore")
    @ResponseBody
    @Action(description = "个人所有详情")
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
     * @param response 响应
     * @throws Exception the exception
     */
    @RequestMapping("rank")
    @ResponseBody
    @Action(description = "获取排名")
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
}