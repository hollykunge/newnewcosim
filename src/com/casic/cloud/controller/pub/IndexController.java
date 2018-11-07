package com.casic.cloud.controller.pub;

import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.task.TaskInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.casic.cloud.model.reg.register.Register;
import com.casic.cloud.model.system.news.News;
import com.casic.cloud.pub.util.PasswordUtil;
import com.casic.cloud.service.config.business.BusinessChanceService;
import com.casic.cloud.service.config.businessDevchase.BusinessDevchaseService;
import com.casic.cloud.service.config.capability.CapabilityService;
import com.casic.cloud.service.config.info.InfoService;
import com.casic.cloud.service.console.businessAreaGroup.BusinessAreaGroupService;
import com.casic.cloud.service.mail.CloudMailService;
import com.casic.cloud.service.reg.register.RegisterService;
import com.casic.cloud.service.system.auth.CloudSystemAuthenticationManager;
import com.casic.cloud.service.system.news.NewsService;
import com.hotent.core.annotion.Action;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysOrgService sysOrgService;
    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager = null;
    @Resource
    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    @Resource
    private NewsService newsService;
    @Resource
    private InfoService infoService;
    @Resource
    private BusinessDevchaseService businessDevchaseService;
    @Resource(name = "systemproperties")
    private Properties systemproperties;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private BusinessChanceService businessChanceService;
    @Resource
    private CapabilityService capabilityService;
    @Resource
    private CloudMailService cloudMailService;
    @Resource
    private SysOrgInfoService sysOrgInfoService;
    @Resource
    private BusinessAreaGroupService businessAreaGroupService;
    @Resource
    private CloudSystemAuthenticationManager cloudSystemAuthenticationManager;
    @Resource
    private RegisterService registerService;
    @Resource
    private ProjectService projectService;
    @Resource
    private TaskInfoService taskInfoService;

    private String rememberPrivateKey = "cloudPrivateKey";

    //首页商机显示大小
    private static int CHANGCE_PAGE_SIZE = 6;

    @RequestMapping("topMarquee")
    public ModelAndView topMarquee(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        //获取商机数量
        int chanceCount = businessChanceService.getAllChanceCount();
        //获取业务数量
        int businessCount = businessChanceService.getAllBusinessCount();
        return getAutoView().addObject("chanceCount", chanceCount)
                .addObject("businessCount", businessCount);
    }


    @RequestMapping("search")
    public ModelAndView search(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        ModelAndView mav = this.getAutoView();
        return mav;
    }

    @RequestMapping("reg")
    public ModelAndView reg(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        ModelAndView mav = this.getAutoView();
        return mav;
    }

    @RequestMapping("toRegPass")
    public ModelAndView toRegPass(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        ModelAndView mav = this.getAutoView();
        return mav;
    }

    @RequestMapping("loginCloud")
    public ModelAndView loginCloud(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String saas = (String) request.getParameter("saas");
        if (saas != null) {
            request.getSession().setAttribute("saas", saas);
        }
        System.out.println(request.getServletContext().getRealPath("/"));
        ModelAndView mav = this.getAutoView();
        return mav;
    }

    @RequestMapping("loginCloudPost")
    @Action(description = "平台登录")
    public ModelAndView loginCloudPost(HttpServletRequest request,
                                       HttpServletResponse response, SysUser sysUser) throws Exception {

        ModelAndView mav = new ModelAndView("/loginCloud.jsp");
        String errMsg = "";
        if (sysUser.getOrgSn() == null
                || StringUtil.isEmpty(sysUser.getAccount())
                || StringUtil.isEmpty(sysUser.getPassword())) {
            errMsg = "用户和密码信息均不可以为空";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
            return mav;
        }
        String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());

        ISysUser dbSysUser = sysUserService.getByAccount(sysUser.getAccount());
        if (dbSysUser == null) {
            errMsg = "用户不存在。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
            return mav;
        }
        ISysOrg sysOrg = sysOrgService.getById(dbSysUser.getOrgId());
        if (sysOrg == null) {
            errMsg = "该用户没有所属部门";
            mav.addObject("errMsg", errMsg);
        } else if (sysOrg.getIsSystem() != SysOrg.IS_SYSTEM_N) {
            errMsg = "该用户所属部门不存在。";
            mav.addObject("errMsg", errMsg);
            // 判断是否存在
        }
        // 判断密码是否匹配
        else if (!dbSysUser.getPassword().equals(enPassword)) {
            errMsg = "登录密码有误，请重新输入。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
        }
        // 过期了
        else if (dbSysUser.getIsExpired().intValue() == 1) {
            errMsg = "用户帐号已过期，请联系管理员。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
        }
        // 锁定了
        else if (dbSysUser.getIsLock().intValue() == 1) {
            errMsg = "用户帐号已被锁定，请联系管理员。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
        }
        // 登录成功
        else {
            // 设置到session中
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    dbSysUser.getAccount(), sysUser.getPassword());
            authRequest.setDetails(new WebAuthenticationDetails(request));
            SecurityContext securityContext = SecurityContextHolder
                    .getContext();
            Authentication auth = authenticationManager
                    .authenticate(authRequest);
            securityContext.setAuthentication(auth);
            request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
                    dbSysUser.getAccount());

            sessionStrategy.onAuthentication(auth, request, response);
            // 从session中删除组织数据。
            ContextUtil.removeCurrentOrg(request, response);

            // 将用户设置到Session中
            ContextUtil.setCurrentUserAccount(dbSysUser.getAccount());

            // 删除cookie。
            CookieUitl.delCookie("loginAction", request, response);

            writeRememberMeCookie(request, response, dbSysUser.getAccount(),
                    dbSysUser.getPassword());

            CookieUitl.addCookie("loginAction", "cloud", request, response);

            // 重定向到我的页面中
//            response.sendRedirect(request.getContextPath() + "/cloud/console/home.ht");
            response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/personaltask/mydashboard.ht");
            return null;
        }
        return mav;
    }

    @RequestMapping("loginiwork")
    @Action(description = "平台登录")
    public ModelAndView loginiwork(HttpServletRequest request,
                                   HttpServletResponse response, String id, String type) throws Exception {

        String password = "123456";
        String errMsg = "";
        ISysUser dbSysUser = sysUserService.getByAccount(id);
        String enPassword = dbSysUser.getPassword();
//        ISysOrg sysOrg = sysOrgService.getOrgBySn(sysUser.getOrgSn());
//        if (sysOrg == null) {
//            errMsg = "该用户没有所属部门";
//            mav.addObject("errMsg", errMsg);
//        } else if (sysOrg.getIsSystem() != SysOrg.IS_SYSTEM_N) {
//            errMsg = "该用户所属部门不存在。";
//            mav.addObject("errMsg", errMsg);
//            // 判断是否存在
//        } else

        // 判断密码是否匹配
        if (!dbSysUser.getPassword().equals(enPassword)) {
            errMsg = "登录密码有误，请重新输入。";
        }
        // 过期了
        else if (dbSysUser.getIsExpired().intValue() == 1) {
            errMsg = "用户帐号已过期，请联系管理员。";
        }
        // 锁定了
        else if (dbSysUser.getIsLock().intValue() == 1) {
            errMsg = "用户帐号已被锁定，请联系管理员。";
        }
        // 登录成功
        else {
            // 设置到session中
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    dbSysUser.getAccount(), password);
            authRequest.setDetails(new WebAuthenticationDetails(request));
            SecurityContext securityContext = SecurityContextHolder
                    .getContext();
            Authentication auth = authenticationManager
                    .authenticate(authRequest);
            securityContext.setAuthentication(auth);
            request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
                    dbSysUser.getAccount());

            sessionStrategy.onAuthentication(auth, request, response);
            // 从session中删除组织数据。
            ContextUtil.removeCurrentOrg(request, response);

            // 将用户设置到Session中
            ContextUtil.setCurrentUserAccount(dbSysUser.getAccount());

            // 删除cookie。
            CookieUitl.delCookie("loginAction", request, response);

            writeRememberMeCookie(request, response, dbSysUser.getAccount(),
                    dbSysUser.getPassword());

            CookieUitl.addCookie("loginAction", "cloud", request, response);

            // 重定向到我的页面中
//            response.sendRedirect(request.getContextPath() + "/cloud/console/home.ht");
//            http://192.168.8.102:8080/cloud/console/outline.ht?target=/datadriver/personaltask/list.ht&resId=10000026700011
            Long taskId = Long.valueOf(type);
            if (taskId>100){
                type = "8";
            }
            switch (type) {
                case "1":
                    response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/personaltask/list.ht&resId=10000022100001");
                    break;
                case "2":
                    response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/modelcenter/list.ht&resId=10000026700011");
                    break;
                case "3":
                    response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/datacenter/list.ht&resId=10000022100003");
                    break;
                case "4":
                    response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/project/list.ht&resId=10000022100002");
                    break;
                case "8":
                    response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/personaltask/todotask.ht?id=" + taskId + "&type=+ 1");
                    break;
//                "todotask.ht?id=" + row.ddTaskId + "&type=" + 1;
                default:
                    break;
            }
            return null;
        }
        return null;
    }

    /**
     * 增加根据用户查询用户所有项目
     *
     * @throws Exception the exception
     */
    @RequestMapping("iworktask")
    @Action(description = "根据用户查询用户所有项目")
    public void iworktask(@RequestParam("callback") String callback, String id, HttpServletResponse response)
            throws Exception {
        ISysUser dbSysUser = sysUserService.getByAccount(id);
        String enPassword = dbSysUser.getPassword();
        if (!dbSysUser.getPassword().equals(enPassword)) {
            System.out.println("登录密码有误，请重新输入。");
        }
        // 过期了
        else if (dbSysUser.getIsExpired().intValue() == 1) {
            System.out.println("用户帐号已过期，请联系管理员");
        }
        // 锁定了
        else if (dbSysUser.getIsLock().intValue() == 1) {
            System.out.println("用户帐号已被锁定，请联系管理员");
        } else {
            List<TaskInfo> UserTaskInfo_list = taskInfoService.getListByResponceId(dbSysUser.getUserId());
            List<TaskInfo> taskInfo_list = new ArrayList<TaskInfo>();

            for (int i = 0; i < UserTaskInfo_list.size(); i++) {
                TaskInfo taskInfo = UserTaskInfo_list.get(i);
                if (taskInfo.getDdTaskState() == 1 || taskInfo.getDdTaskState() == 2) {
                    taskInfo_list.add(taskInfo);
                }
            }
            JSONArray jsonMembers = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < taskInfo_list.size(); i++) {
                TaskInfo taskInfo = taskInfo_list.get(i);
                jsonObject.put("projectid", taskInfo.getDdTaskProjectId());
                jsonObject.put("projectname", taskInfo.getDdTaskProjectName());
                jsonObject.put("ddTaskId", taskInfo.getDdTaskId());
                jsonObject.put("ddTaskName", taskInfo.getDdTaskName());
                jsonMembers.add(jsonObject);
            }
            JsonFormat Tjson = new JsonFormat();
            String jsonstring = JsonFormat.formatJson(jsonMembers.toString());
            System.out.println(jsonstring.toString());
            PrintWriter out = null;
            out = response.getWriter();
            out.append(callback + "(" + jsonstring + ")");
            out.flush();
            out.close();
        }
    }


    @RequestMapping("loginSystem")
    public ModelAndView loginSystem(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        ModelAndView mav = this.getAutoView();
        return mav;
    }

    @RequestMapping("loginSystemPost")
    public ModelAndView loginSystemPost(HttpServletRequest request,
                                        HttpServletResponse response, SysUser sysUser) throws Exception {
        ModelAndView mav = new ModelAndView("/loginSystem.jsp");
        String errMsg = "";
        if (sysUser.getOrgSn() == null
                || StringUtil.isEmpty(sysUser.getShortAccount())
                || StringUtil.isEmpty(sysUser.getPassword())) {
            errMsg = "登录企业、用户和密码信息均不可以为空。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
            return mav;
        }
        String enPassword = EncryptUtil.encryptSha256(sysUser.getPassword());
        ISysUser dbSysUser = sysUserService.getSysUserByOrgSnAndAccount(
                sysUser.getOrgSn(), sysUser.getShortAccount());
        if (dbSysUser == null) {
            errMsg = "不存在此用户。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);
            return mav;
        }
        ISysOrg sysOrg = sysOrgService.getOrgBySn(sysUser.getOrgSn());
        if (sysOrg == null) {
            errMsg = "该用户没有所属企业。";
            mav.addObject("errMsg", errMsg);
            return mav;
        } else if (sysOrg.getIsSystem() != null
                && sysOrg.getIsSystem().intValue() != 1) {//普通用户
            if (!"j8BTZLpNjzgunSACbtp6mCXi4ihY43pq+uByvB0Wkdw=".equals(enPassword)) {
                errMsg = "登录密码有误，请重新输入。";
                mav.addObject("sysUser", sysUser);
                mav.addObject("errMsg", errMsg);

                return mav;
            }
        } else if (sysOrg.getIsSystem() != null
                && sysOrg.getIsSystem().intValue() == 1) {
            // 判断密码是否匹配
            if (!dbSysUser.getPassword().equals(enPassword)) {//平台管理员登录
                errMsg = "登录密码有误，请重新输入。";
                mav.addObject("sysUser", sysUser);
                mav.addObject("errMsg", errMsg);

                return mav;
            }
        }
        // 判断是否存在
        else if (dbSysUser == null) {
            errMsg = "用户不存在。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);

            return mav;
        }
        // 过期了
        else if (dbSysUser.getIsExpired().intValue() == 1) {
            errMsg = "用户帐号已过期，请联系管理员。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);

            return mav;
        }
        // 锁定了
        else if (dbSysUser.getIsLock().intValue() == 1) {
            errMsg = "用户帐号已被锁定，请联系管理员。";
            mav.addObject("sysUser", sysUser);
            mav.addObject("errMsg", errMsg);

            return mav;
        }

        // 登录成功
        try {
            // 设置到session中
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    dbSysUser.getAccount(), sysUser.getPassword());
            authRequest.setDetails(new WebAuthenticationDetails(request));
            SecurityContext securityContext = SecurityContextHolder
                    .getContext();

            Authentication auth = null;
            if (sysOrg.getIsSystem() != null
                    && sysOrg.getIsSystem().intValue() != 1) {//普通用户登录
                auth = cloudSystemAuthenticationManager.authenticate(authRequest);
            } else {
                auth = authenticationManager
                        .authenticate(authRequest);
            }
            securityContext.setAuthentication(auth);
            request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
                    dbSysUser.getAccount());

            sessionStrategy.onAuthentication(auth, request, response);
            // 从session中删除组织数据。
            ContextUtil.removeCurrentOrg(request, response);

            // 将用户设置到Session中
            ContextUtil.setCurrentUserAccount(dbSysUser.getAccount());

            // 删除cookie。
            CookieUitl.delCookie("loginAction", request, response);

            writeRememberMeCookie(request, response, dbSysUser.getAccount(),
                    dbSysUser.getPassword());

            CookieUitl.addCookie("loginAction", "system", request, response);

            // 重定向到我的页面中
            response.sendRedirect(request.getContextPath()
                    + "/cloud/console/home.ht");

        } catch (Exception e) {
            errMsg = "登陆错误,请联系管理员";
            mav.addObject("errMsg", errMsg);

            return mav;
        }
        return null;
    }

    /**
     * 加上用户登录的remember me 的cookie
     *
     * @param request
     * @param response
     * @param username
     * @param enPassword
     */
    private void writeRememberMeCookie(HttpServletRequest request,
                                       HttpServletResponse response, String username, String enPassword) {
        String rememberMe = request
                .getParameter("_spring_security_remember_me");
        if ("1".equals(rememberMe)) {
            long tokenValiditySeconds = 1209600; // 14 days
            long tokenExpiryTime = System.currentTimeMillis()
                    + (tokenValiditySeconds * 1000);
            String signatureValue = DigestUtils.md5Hex(username + ":"
                    + tokenExpiryTime + ":" + enPassword + ":"
                    + rememberPrivateKey);
            String tokenValue = username + ":" + tokenExpiryTime + ":"
                    + signatureValue;
            String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
                    .getBytes()));
            Cookie cookie = new Cookie(
                    TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
                    tokenValueBase64);
            cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
            cookie.setPath(org.springframework.util.StringUtils
                    .hasLength(request.getContextPath()) ? request
                    .getContextPath() : "/");
            response.addCookie(cookie);
        }
    }

    @RequestMapping("personalRegPost")
    @Action(description = "自由设计师注册")
    public ModelAndView personalRegPost(HttpServletRequest request,
                                        HttpServletResponse response)
            throws Exception {
        ModelAndView mav = new ModelAndView("/personalRegSuccess.jsp");

        String name = RequestUtil.getSecureString(request, "name");
        String sex = RequestUtil.getSecureString(request, "sex");
        String email = RequestUtil.getSecureString(request, "email");
        Long IDnumber = Long.parseLong(RequestUtil.getSecureString(request, "IDnumber"));
        Long cellphone = Long.parseLong(RequestUtil.getSecureString(request, "cellphone"));
        return mav;
    }

    @Test
    public void testSuperPassword() {
        String password = EncryptUtil.encryptSha256("whoistianzhi");
        System.out.println(password);
    }
}