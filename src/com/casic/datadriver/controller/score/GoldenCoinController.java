package com.casic.datadriver.controller.score;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.service.score.DdGoldenCoinService;
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
import java.util.Date;
import java.util.List;

/**
 * @Author: hollykunge
 * @Date: 创建于 2018/9/27
 */

@Controller
@RequestMapping("/datadriver/golden/")
public class GoldenCoinController extends AbstractController {

    @Autowired
    private DdGoldenCoinService ddDdGoldenCoinService;

    /**
     * 列表
     */
    @RequestMapping("list")
    @Action(description = "币列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DdGoldenCoin> list = ddDdGoldenCoinService.getAll(new QueryFilter(request, "goldenItem"));

        return this.getAutoView().addObject("goldenList", list);
    }

    /**
     * 币批量删除
     */
    @RequestMapping("del")
    @Action(description = "币删除")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        ResultMessage message = null;
        try {
            Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
            ddDdGoldenCoinService.delByIds(lAryId);
            message = new ResultMessage(ResultMessage.Success, "删除成功!");
        } catch (Exception ex) {
            message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }

    /**
     * 编辑个人币
     */
    @RequestMapping("edit")
    @Action(description = "编辑个人币")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        Long scoreId = RequestUtil.getLong(request, "id");
        String returnUrl = RequestUtil.getPrePage(request);
        DdGoldenCoin ddGoldenCoin = ddDdGoldenCoinService.getById(scoreId);

        return getAutoView().addObject("bizDef", ddGoldenCoin)
                .addObject("returnUrl", returnUrl);
    }

    /**
     * 提交编辑个人币
     */
    @RequestMapping("save")
    @Action(description = "提交编辑个人币")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            Long id = RequestUtil.getLong(request, "id");
            Long userId = RequestUtil.getLong(request, "userId");
            Long coinNum = RequestUtil.getLong(request, "coinNum");
            String coinType = RequestUtil.getString(request, "coinType");
            String userName = RequestUtil.getString(request, "userName");
            String orgName = RequestUtil.getString(request, "orgName");
            Long orgId = RequestUtil.getLong(request, "orgId");

            DdGoldenCoin ddGoldenCoin = new DdGoldenCoin();

            ddGoldenCoin.setId(id);
            ddGoldenCoin.setUserId(userId);
            ddGoldenCoin.setCoinNum(coinNum);
            ddGoldenCoin.setCoinType(coinType);
            ddGoldenCoin.setOrgName(userName);
            ddGoldenCoin.setOrgId(orgId);
            ddGoldenCoin.setOrgName(orgName);
            ddGoldenCoin.setUpdTime(new Date());

            ddDdGoldenCoinService.update(ddGoldenCoin);
            resultMsg = getText("record.updated", "二币");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception ex) {
            resultMsg = getText("record.updated", "二币");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
        }
    }
}