package com.casic.datadriver.service.webservice;

import com.alibaba.fastjson.JSONObject;
import com.casic.datadriver.service.Util.Xml2Json;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.provider.MD5;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService
public class DataInterfacesImpl {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysUserOrgService sysUserOrgService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 根据XML文件自动创建用户和科室
     *
     * @return
     * @throws Exception
     */
    @WebMethod
    public String addUser(String xmlFile)
            throws Exception {
        Map<String, Object> map = new HashMap<>();

        JSONObject json = Xml2Json.xml2Json(xmlFile);
        String saveType = (String) json.get("mdtype");
        System.out.println(saveType);
        short shortByOne = 1;
        short shortByZero = 0;
        long longByZero = 0;
        long longByOne = 1;
        String flag = "";

        if (saveType.equals("User")) {
            System.err.println("用户表");
            JSONObject userJson = (JSONObject) json.get("data");
            System.out.println(userJson.get("User"));
            JSONObject infoJson = (JSONObject) userJson.get("User");
            long userId = UniqueIdUtil.genId();
            String account = (String) infoJson.get("identityNo");
            String fullName = (String) infoJson.get("userName");
            String isLock = (String) infoJson.get("fcFlag");
            String sex = (String) infoJson.get("gender");
            String phone = (String) infoJson.get("telephone");
            String shortAccount1 = (String) infoJson.get("xing");
            String shortAccount2 = (String) infoJson.get("ming");
            String orgCodeAndOrgSn = (String) infoJson.get("deptTyyhOrgCode");
            String orgCode = (String) infoJson.get("deptCode");
            try {
                SysUser sysUser = new SysUser();
                sysUser.setFullname(fullName);
                sysUser.setAccount(account);
                sysUser.setPassword(EncryptUtil.encryptSha256("123456"));
                sysUser.setIsExpired(shortByZero);
                sysUser.setIsLock(Short.parseShort(isLock));
                sysUser.setStatus(shortByOne);
                sysUser.setPhone(phone);
                sysUser.setSex(sex);
                sysUser.setFromType(shortByZero);
                //sysUser.setOrgId(Long.parseLong(orgCodeAndOrgSn));
                //sysUser.setOrgSn(Long.parseLong(orgCodeAndOrgSn));
                sysUser.setShortAccount(shortAccount1 + shortAccount2);

                SysUserOrg sysUserOrg = new SysUserOrg();
                //sysUserOrg.setOrgId(Long.parseLong(orgCodeAndOrgSn));
                sysUserOrg.setIsPrimary(shortByOne);
                sysUserOrg.setIsCharge(shortByZero);
                sysUserOrg.setIsGradeManage(shortByZero);
                long findOrgId = 0;

                List<ISysOrg> sysOrgList = sysOrgService.getAll();
                for (ISysOrg sysOrg : sysOrgList) {
                    String orgDesc = sysOrg.getOrgDesc();
                    if (orgDesc != null && orgDesc.equals(orgCode)) {
                        findOrgId = sysOrg.getOrgId();
                    }
                }
                UserRole userRole = new UserRole();
                long userRoldId = UniqueIdUtil.genId();
                if (sysUserService.isAccountExist(account)) {
                    ISysUser userByAccount = sysUserService.getByAccount(account);
                    Long findUserId = userByAccount.getUserId();
                    SysUserOrg userOrgModel = sysUserOrgService.getUserOrgModel(findUserId, userByAccount.getOrgId());
                    List<UserRole> userRoleList = userRoleService.getByUserId(findUserId);
                    Long findUserRoleId = 0L;
                    for (UserRole findUserRole : userRoleList) {
                        findUserRoleId = findUserRole.getUserRoleId();
                    }
                    sysUser.setUserId(findUserId);
                    sysUser.setCreatetime(new Date());
                    sysUser.setOrgId(findOrgId);
                    Long userOrgId = userOrgModel.getUserOrgId();
                    userRoleService.delById(findUserRoleId);
                    sysUserOrgService.delById(userOrgId);
                    sysUserService.update(sysUser);
                    sysUserOrg.setUserOrgId(userOrgId);
                    sysUserOrg.setUserId(findUserId);
                    sysUserOrg.setOrgId(findOrgId);
                    userRole.setUserRoleId(findUserRoleId);
                    userRole.setUserId(findUserId);
                    userRole.setRoleId(Long.parseLong("2018"));//硬编码
                    sysUserOrgService.add(sysUserOrg);
                    userRoleService.add(userRole);
                } else {
                    sysUser.setUserId(userId);
                    sysUser.setOrgId(findOrgId);
                    sysUserService.add(sysUser);
                    sysUserOrg.setUserId(userId);
                    sysUserOrg.setUserOrgId(UniqueIdUtil.genId());
                    sysUserOrg.setOrgId(findOrgId);
                    userRole.setUserRoleId(userRoldId);
                    userRole.setUserId(userId);
                    userRole.setRoleId(Long.parseLong("2018"));//硬编码
                    sysUserOrgService.add(sysUserOrg);
                    userRoleService.add(userRole);
                }
                flag = "success";
            } catch (Exception ex) {
                ex.printStackTrace();
                flag = "failed";
            }

        } else if (saveType.equals("Organization")) {
            System.err.println("群组表");
            JSONObject orgJson = (JSONObject) json.get("data");
            System.out.println(orgJson.get("Organization"));
            JSONObject infoJson = (JSONObject) orgJson.get("Organization");
            System.err.println(infoJson.get("orgName"));
            String orgSn = (String) infoJson.get("orgCode");
            String orgName = (String) infoJson.get("orgName");
            String orgSecName = (String) infoJson.get("mdCode");
            long orgId = UniqueIdUtil.genId();
            long orgSupId = (long) 100;
            try {
                SysOrg sysOrg = new SysOrg();
                sysOrg.setOrgId(orgId);
                sysOrg.setOrgName(orgName);
                sysOrg.setOrgSupId(orgSupId);
                sysOrg.setOrgDesc(orgSecName);
                sysOrg.setOrgType(longByOne);
                sysOrg.setFromType(shortByOne);
                //sysOrg.setSn(Long.parseLong(orgSn));
                sysOrg.setIsSystem(longByZero);
                sysOrg.setDemId(longByOne);
                sysOrg.setPath("1." + orgId);
                sysOrgService.add(sysOrg);
                flag = "success";
            } catch (Exception ex) {
                ex.printStackTrace();
                flag = "failed";
            }
        }
        return flag;
    }
}