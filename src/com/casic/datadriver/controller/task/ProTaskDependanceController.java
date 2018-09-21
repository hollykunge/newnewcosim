package com.casic.datadriver.controller.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.task.ProTaskDependance;
import com.casic.datadriver.service.task.ProTaskDependanceService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
/**
 * Created by Administrator on 2016/11/21.
 */
public class ProTaskDependanceController extends AbstractController  {


    /** The task service. */
    @Resource
    private ProTaskDependanceService  proTaskDependanceService;

    /**
     * ?????????.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws Exception
     *             the exception
     */
    @RequestMapping("save")
    @Action(description = "��ӻ����taskinfo")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        ProTaskDependance proTaskDependance = this.getFormObject(request, ProTaskDependance.class);
        try {
            if (proTaskDependance.getDdDepenganceId() != null || proTaskDependance.getDdDepenganceId() != 0) {
                proTaskDependance.setDdTaskId(UniqueIdUtil.genId());
                proTaskDependanceService.addDDProTaskDependance(proTaskDependance);
                resultMsg = getText("record.added", "cloud_account_info");
            } else {
                proTaskDependanceService.update(proTaskDependance);
                resultMsg = getText("record.updated", "cloud_account_info");
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * Query task basic info list.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the list
     * @throws Exception
     *             the exception
     */
    @RequestMapping("list")
    @Action(description = "����������ѯ��Ŀ������Ϣ�б�")
    public ModelAndView queryProTaskDependanceBasicInfoList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        QueryFilter queryFilter = new QueryFilter(request, "TaskItem");
        ModelAndView mv = this.getAutoView().addObject("taskList",
                this.proTaskDependanceService.queryProTaskDependanceBasicInfoList(queryFilter));
        return mv;
    }

    /**
     * Del.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws Exception
     *             the exception
     */
    @RequestMapping("del")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.del(request, response, this.proTaskDependanceService);
    }

    /**
     * ???????????.
     *
     * @param bin
     *            the bin
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder bin) {
        bin.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }












}
