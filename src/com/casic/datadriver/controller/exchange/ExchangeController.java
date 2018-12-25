package com.casic.datadriver.controller.exchange;

import com.casic.datadriver.model.coin.RankModel;
import com.casic.datadriver.service.score.DdGamblerService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.util.RequestUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author: workhub
 * @Date: 创建于 2018/10/24
 */

@Controller
@RequestMapping("/datadriver/exchange/")
public class ExchangeController extends GenericController {

    @Autowired
    private DdGamblerService ddGamblerService;

    /**
     * 获取单类积分的入围名单，每人一个币
     */
    @RequestMapping("getMonthRankByType")
    @ResponseBody
    public void getMonthRankByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray jsonR;
        try {
            String type = RequestUtil.getString(request, "scoreType");
            Set<RankModel> itemSet = ddGamblerService.getRankByType(type);
            jsonR = JSONArray.fromObject(itemSet);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

//    /**
//     * 获取三类积分和够某一数值后，并且没入围平分金钱名单的人名单
//     *
//     * @throws Exception e
//     */
//    @RequestMapping("getLotteryList")
//    public void getLotteryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        JSONArray jsonR;
//        try {
//            Set<RankModel> itemSet = exchangeService.getLotteryList();
//            jsonR = JSONArray.fromObject(itemSet);
//            //解决跨域
//            String callback = request.getParameter("callback");
//            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
//        } catch (Exception e) {
//            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
//        }
//    }

//    /**
//     * 传入抽奖列表生成抽奖结果并写入数据库
//     *
//     * @throws Exception e
//     */
//    @RequestMapping("getLotteryResult")
//    public void getLotteryResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        JSONArray jsonR;
//        try {
//            Set<RankModel> lotteryResult = exchangeService.getLotteryResult();
//            jsonR = JSONArray.fromObject(lotteryResult);
//            //解决跨域
//            String callback = request.getParameter("callback");
//            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
//        } catch (Exception e) {
//            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
//        }
//    }

//    /**
//     * 后台管理使用，按月点击进行兑换
//     *
//     * @throws Exception e
//     */
//    @RequestMapping("consume")
//    @Action(description = "消耗积分兑换币")
//    public void consume(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String resultMsg = null;
//        try {
////            Long userId = RequestUtil.getLong(request, "userId", 0);
//            String type = RequestUtil.getString(request, "scoreType");
////            Long scoreNum = RequestUtil.getLong(request, "scoreNum");
//            exchangeService.consume(type);
//            resultMsg = getText("coin.consumed", "兑换成功");
//            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
//        } catch (Exception e) {
//            resultMsg = getText("coin.consumed", "兑换失败");
//            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
//        }
////        String returnUrl = RequestUtil.getPrePage(request);
//    }
}
