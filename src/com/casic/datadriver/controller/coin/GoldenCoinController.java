package com.casic.datadriver.controller.coin;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.service.coin.GoldenCoinService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 * @Modified:
 */
@Controller
@RequestMapping("/datadriver/golden/")
public class GoldenCoinController extends AbstractController {

    GoldenCoinService ddGoldenCoinService;

    @Autowired
    public GoldenCoinController(GoldenCoinService ddGoldenCoinService) {
        this.ddGoldenCoinService = ddGoldenCoinService;
    }

    @RequestMapping("del")
    @Action(description="币删除")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String preUrl= RequestUtil.getPrePage(request);
        ResultMessage message=null;
        try{
            Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
            ddGoldenCoinService.delAll(lAryId);
            message=new ResultMessage(ResultMessage.Success,"删除成功!");
        }catch(Exception ex){
            message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }

    @RequestMapping("edit")
    @Action(description="编辑个人币")
    public ModelAndView edit(HttpServletRequest request) throws Exception
    {
        Long scoreId = RequestUtil.getLong(request,"id");
        String returnUrl = RequestUtil.getPrePage(request);
        DdGoldenCoin ddGoldenCoin = ddGoldenCoinService.getById(scoreId);

        return getAutoView().addObject("golden",ddGoldenCoin)
                .addObject("returnUrl",returnUrl);
    }

    @RequestMapping("list")
    @Action(description="币列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<DdGoldenCoin> list=ddGoldenCoinService.getAll(new QueryFilter(request,"goldenItem"));

        return this.getAutoView().addObject("goldenList",list);
    }

    @RequestMapping("save")
    @Action(description="提交编辑个人币")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String resultMsg=null;
        try {
            Long id = RequestUtil.getLong(request,"id");
            Long userId = RequestUtil.getLong(request,"userId");
            Long total = RequestUtil.getLong(request,"total");

            DdGoldenCoin ddGoldenCoin = new DdGoldenCoin();
            ddGoldenCoin.setId(id);
            ddGoldenCoin.setUserId(userId);
            ddGoldenCoin.setTotal(total);

            ddGoldenCoinService.update(ddGoldenCoin);
            resultMsg = getText("record.added", "编辑成功");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception ex){
            resultMsg = getText("record.added", "编辑失败");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
        }
    }

    /**
     * 后台管理使用，按月点击进行兑换
     * @param request
     * @param response
     * @throws Exception
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
            ddGoldenCoinService.consume(type);
            resultMsg = getText("coin.consumed", "兑换成功");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e){
            resultMsg = getText("coin.consumed", "兑换失败");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
        }

//        String returnUrl = RequestUtil.getPrePage(request);
    }
}
