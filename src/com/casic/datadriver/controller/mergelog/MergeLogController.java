package com.casic.datadriver.controller.mergelog;

import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysAudit;
import com.hotent.platform.service.system.SysAuditService;
import com.hotent.platform.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: yzq
 * @Date: 创建于 2018/10/26 12:50
 */
@Controller
@RequestMapping("/mergelog/")
public class MergeLogController extends GenericController {

    @Autowired
    private SysAuditService sysAuditService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加其他模块日志
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("add")
    @ResponseBody
    //http://localhost:8080/mergelog/add.ht?logType="搜索"&logContent="搜索"&logUser="user1"&logIP="127.0.0.1"&logFrom="search"
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String logUser = RequestUtil.getString(request, "logUser");
            String logType = RequestUtil.getString(request, "logType");
            String logContent = RequestUtil.getString(request, "logContent");
            String logIP = RequestUtil.getString(request, "logIP");
            String logFrom = RequestUtil.getString(request, "logFrom");
            SysAudit sysAudit = new SysAudit();
            ISysUser sysUser = sysUserService.getByAccount(logUser);
            sysAudit.setAuditId(UniqueIdUtil.genId());
            sysAudit.setOpName(logType);
            sysAudit.setExecutorId(sysUser.getUserId());
            sysAudit.setExecutor(sysUser.getFullname());
            sysAudit.setExeTime(new Date());
            sysAudit.setExeMethod(logContent);
            sysAudit.setFromIp(logIP);
            sysAudit.setRequestURI(request.getRequestURI());
            sysAudit.setReqParams(logFrom);
            sysAuditService.add(sysAudit);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
