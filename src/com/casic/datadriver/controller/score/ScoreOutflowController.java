package com.casic.datadriver.controller.score;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdScoreOutflow;
import com.casic.datadriver.service.score.DdScoreOutflowService;
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
import java.util.Date;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description: 积分消费记录
 * @Date: 创建于 2018/9/21
 */

@Controller
@RequestMapping("/datadriver/outflow/")
public class ScoreOutflowController extends AbstractController {

    @Resource
    private DdScoreOutflowService ddScoreOutflowService;

    /**
     * 列表
     */
    @RequestMapping("list")
    @Action(description = "积分消耗列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DdScoreOutflow> consumeList = ddScoreOutflowService.getAll(
                new QueryFilter(request,"scoreOutflowItem"));
        return this.getAutoView().addObject("scoreOutflowList",consumeList);
    }

    /**
     * 流水列表批量删除
     */
    @RequestMapping("del")
    @Action(description = "输出流水列表删除")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        ResultMessage message = null;
        try {
            Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
            ddScoreOutflowService.delByIds(lAryId);
            message = new ResultMessage(ResultMessage.Success, "删除成功!");
        } catch (Exception ex) {
            message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }

    /**
     * 编辑个人输出流水
     */
    @RequestMapping("edit")
    @Action(description = "编辑个人流水输出")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        Long scoreOutflowId = RequestUtil.getLong(request, "id");
        String returnUrl = RequestUtil.getPrePage(request);
        DdScoreOutflow ddScoreOutflow = ddScoreOutflowService.getById(scoreOutflowId);
        return getAutoView().addObject("bizDef", ddScoreOutflow)
                .addObject("returnUrl", returnUrl);
    }

    /**
     * 提交编辑个人流水
     */
    @RequestMapping("save")
    @Action(description = "提交编辑个人流水输出")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;

        Long scoreOutflowId = RequestUtil.getLong(request, "id");
        Long scoreOutflowUid = RequestUtil.getLong(request, "userId");
        String userName = RequestUtil.getString(request, "userName");
        Long orgId = RequestUtil.getLong(request, "orgId");
        String orgName = RequestUtil.getString(request, "orgName");
        String sourceType = RequestUtil.getString(request, "sourceType");
        Integer expendScore = RequestUtil.getInt(request, "expendScore");
        String expendDetail = RequestUtil.getString(request, "expendDetail");

        DdScoreOutflow ddScoreOutflow = new DdScoreOutflow();

        ddScoreOutflow.setId(scoreOutflowId);
        ddScoreOutflow.setUserId(scoreOutflowUid);
        ddScoreOutflow.setUserName(userName);
        ddScoreOutflow.setOrgId(orgId);
        ddScoreOutflow.setOrgName(orgName);
        ddScoreOutflow.setSourceType(sourceType);
        ddScoreOutflow.setExpendScore(expendScore);
        ddScoreOutflow.setExpendDetail(expendDetail);
        ddScoreOutflow.setUdpTime(new Date());

        try {
            ddScoreOutflowService.update(ddScoreOutflow);
            resultMsg = getText("record.updated", "消耗的积分");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }

    }
}