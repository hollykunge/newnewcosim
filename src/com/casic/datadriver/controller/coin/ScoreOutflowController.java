package com.casic.datadriver.controller.coin;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.coin.DdScoreOutflowService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.query.QueryFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description: 积分消费记录
 * @Date: 创建于 2018/9/21
 * @Modified:
 */
@Controller
@RequestMapping("/datadriver/coin/")
public class ScoreOutflowController extends AbstractController {

    @Resource
    DdScoreOutflowService ddScoreOutflowService;

    @RequestMapping("consumelist")
    @Action(description = "积分消耗列表")
    public ModelAndView consumelist(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<DdScoreOutflow> consumelist=ddScoreOutflowService.getAll(new QueryFilter(request,"scoreOutflowItem"));
        ModelAndView mv=this.getAutoView().addObject("scoreOutflowList",consumelist);
        return mv;
    }
}
