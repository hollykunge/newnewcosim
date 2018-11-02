package com.casic.datadriver.controller.exchange;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdRank;
import com.casic.datadriver.service.exchange.ExchangeService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: workhub
 * @Date: 创建于 2018/10/24
 */

@Controller
@RequestMapping("/datadriver/exchange/")
public class ExchangeController extends AbstractController  {

    @Autowired
    private ExchangeService exchangeService;

    /**
     * 获取单类积分的入围名单，每人一个币，平分奖池
     * @param scoreType 一级类型
     * @throws Exception e
     */
    @RequestMapping("getRankByType")
    public void getRankByType(String scoreType, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        JSONArray jsonR;
        try {
            List<DdRank> itemList = exchangeService.getRankByType(scoreType);
            jsonR = JSONArray.fromObject(itemList);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 获取三类积分和够某一数值后，并且没入围平分金钱名单的人名单
     * @throws Exception e
     */
    @RequestMapping("getLotteryRank")
    public void getLotteryRank(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        JSONArray jsonR;
        try {
            List<DdRank> itemList = exchangeService.getLotteryRank();
            jsonR = JSONArray.fromObject(itemList);
            //解决跨域
            String callback = request.getParameter("callback");
            response.getWriter().write(callback + "(" + jsonR.toString() + ")");
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), null + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 后台管理使用，按月点击进行兑换
     * @throws Exception e
     */
    @RequestMapping("consume")
    @Action(description="消耗积分兑换币")
    public void consume(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String resultMsg=null;
        try{
//            Long userId = RequestUtil.getLong(request, "userId", 0);
            String type = RequestUtil.getString(request, "scoreType");
//            Long scoreNum = RequestUtil.getLong(request, "scoreNum");
            exchangeService.consume(type);
            resultMsg = getText("coin.consumed", "兑换成功");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e){
            resultMsg = getText("coin.consumed", "兑换失败");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
        }
//        String returnUrl = RequestUtil.getPrePage(request);
    }

}