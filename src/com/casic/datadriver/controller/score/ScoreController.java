package com.casic.datadriver.controller.score;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdScore;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.casic.datadriver.service.score.DdScoreService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description: 积分记录
 * @Date: 创建于 2018/9/21
 */

@Controller
@RequestMapping("/datadriver/coin/")
public class ScoreController extends AbstractController {
    @Autowired
    private DdScoreService ddScoreService;
    @Autowired
    private DdScoreInflowService ddScoreInflowService;

    /**
     * 积分列表
     * @throws Exception e
     */
    @RequestMapping("list")
    @Action(description="积分列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DdScore> list=ddScoreService.getAll(new QueryFilter(request,"scoreItem"));
        return this.getAutoView().addObject("scoreList",list);
    }
    /**
     * 积分批量删除
     * @throws Exception e
     */
    @RequestMapping("del")
    @Action(description="积分列表删除")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        ResultMessage message = null;
        try{
            Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
            ddScoreService.delAll(lAryId);
            message = new ResultMessage(ResultMessage.Success,"删除成功!");
        }catch(Exception ex) {
            message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }
    /**
     * 积分编辑
     * @throws Exception e
     */
    @RequestMapping("edit")
    @Action(description="编辑个人积分")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        Long scoreId = RequestUtil.getLong(request,"id");
        String returnUrl = RequestUtil.getPrePage(request);
        DdScore ddScore = ddScoreService.getById(scoreId);
        return getAutoView().addObject("bizDef", ddScore)
                .addObject("returnUrl", returnUrl);
    }
    /**
     * 提交编辑
     * @param request r
     * @param response r
     * @throws Exception e
     */
    @RequestMapping("save")
    @Action(description="提交编辑个人积分")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;

        Long scoreId = RequestUtil.getLong(request,"id");
        Long scoreUid = RequestUtil.getLong(request,"userId");
        String userName = RequestUtil.getString(request,"userName");
        Integer scoreTotal = RequestUtil.getInt(request, "scoreTotal");
        String crtTime = RequestUtil.getString(request,"crtTime");
        String udpTime = RequestUtil.getString(request,"udpTime");
        String scoreType = RequestUtil.getString(request,"scoreType");
        String scoreAction = RequestUtil.getString(request,"scoreAction");
        String orgName = RequestUtil.getString(request,"orgName");
        Long orgId = RequestUtil.getLong(request,"orgId");
        DdScore ddScore = new DdScore();
        ddScore.setId(scoreId);
        ddScore.setUserId(scoreUid);
        ddScore.setUserName(userName);
        ddScore.setScoreTotal(scoreTotal);
        ddScore.setCrtTime(crtTime);
        ddScore.setUdpTime(udpTime);
        ddScore.setScoreType(scoreType);
        ddScore.setScoreAction(scoreAction);
        ddScore.setOrgName(orgName);
        ddScore.setOrgId(orgId);

        try {
            ddScoreService.updateOne(ddScore);
            resultMsg = getText("record.updated", "积分");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "，" + e.getMessage(), ResultMessage.Fail);
        }

    }
    /**
     * 积分明细
     * @throws Exception e
     */
    @RequestMapping("detail")
    @Action(description="积分明细")
    public ModelAndView detail(HttpServletRequest request) throws Exception {
        Long scoreId = RequestUtil.getLong(request,"id");
        DdScore ddScore = ddScoreService.getById(scoreId);

        QueryFilter queryFilter = new QueryFilter(request, "detailItem");
        queryFilter.addFilter("userId", ddScore.getUserId());
        queryFilter.addFilter("sourceType", ddScore.getScoreType());

        List<DdScoreInflow> detailList = ddScoreInflowService.getByUidAndType(queryFilter);

        return this.getAutoView().addObject("detailList", detailList);
    }
}