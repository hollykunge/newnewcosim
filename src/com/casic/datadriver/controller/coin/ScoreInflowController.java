package com.casic.datadriver.controller.coin;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: hollykunge
 * @Description: 积分赚取流水
 * @Date: 创建于 2018/9/21
 * @Modified:
 */
public class ScoreInflowController extends AbstractController {
    /**
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @RequestMapping("save")
    @Action(description = "task")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;

        DdScoreInflow ddScoreInflow = getFormObject(request);
        try {
            if (taskInfo.getDdTaskId() == null || taskInfo.getDdTaskId() == 0) {
                taskInfo.setDdTaskId(UniqueIdUtil.genId());
                taskInfo.setDdTaskChildType("createpanel");
                taskInfo.setDdTaskState(taskInfo.createpanel);
                //TODO:需要添加任务优先级，否则会造成个人任务显示列表出错
                taskInfoService.addDDTask(taskInfo);
                proTaskDependance.setDdTaskId(UniqueIdUtil.genId());
                proTaskDependance.setDdTaskId(taskInfo.getDdTaskId());
                proTaskDependanceService.addDDProTaskDependance(proTaskDependance);

                resultMsg = getText("record.added", "添加完成");
            } else {
                taskInfoService.updateDDTask(taskInfo);
                resultMsg = getText("record.updated", "更新完成");
            }

            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }
}
