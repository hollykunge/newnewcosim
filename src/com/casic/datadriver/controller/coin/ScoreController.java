package com.casic.datadriver.controller.coin;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.service.coin.DdScoreService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description: 积分记录
 * @Date: 创建于 2018/9/21
 * @Modified:
 */
@Controller
@RequestMapping("/datadriver/coin/")
public class ScoreController extends AbstractController {

    @Resource
    DdScoreService ddScoreService;
    /**
     * 积分列表
     * @param request
     * @param response
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    @Action(description="积分列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<DdScore> list=ddScoreService.getAll(new QueryFilter(request,"scoreItem"));
        ModelAndView mv=this.getAutoView().addObject("scoreList",list);
        return mv;
    }

    /**
     * 积分列表删除
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("del")
    @Action(description="积分列表删除")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String preUrl= RequestUtil.getPrePage(request);
        ResultMessage message=null;
        try{
            Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
            ddScoreService.delAll(lAryId);
            message=new ResultMessage(ResultMessage.Success,"删除成功!");
        }catch(Exception ex){
            message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }

    /**
     * 	编辑个人积分
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("edit")
    @Action(description="编辑个人积分")
    public ModelAndView edit(HttpServletRequest request) throws Exception
    {
        Long scoreId=RequestUtil.getLong(request,"id");
        String returnUrl=RequestUtil.getPrePage(request);
        DdScore ddScore=ddScoreService.getById(scoreId);

        return getAutoView().addObject("bizDef",ddScore)
                .addObject("returnUrl",returnUrl);
    }
}
