package com.casic.datadriver.service.webservice;

import com.alibaba.fastjson.JSONObject;
import com.casic.datadriver.service.Util.Xml2Json;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebService
public class DataInterfacesImpl {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysUserOrgService sysUserOrgService;

    /**
     * 根据XML文件自动创建用户和科室
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
            long  userId= UniqueIdUtil.genId();
            String  account= (String)infoJson.get("identityNo");
            String  fullName= (String)infoJson.get("userName");
            String  isLock= (String)infoJson.get("fcFlag");
            String  sex= (String)infoJson.get("gender");
            String  phone= (String)infoJson.get("telephone");
            String  shortAccount1= (String)infoJson.get("xing");
            String  shortAccount2= (String)infoJson.get("ming");
            String  orgCodeAndOrgSn= (String)infoJson.get("deptTyyhOrgCode");
        try{
            SysUser sysUser = new SysUser();
            sysUser.setFullname(fullName);
            sysUser.setAccount(account);
            sysUser.setPassword("123456");
            sysUser.setIsExpired(shortByZero);
            sysUser.setIsLock(Short.parseShort(isLock));
            sysUser.setStatus(shortByOne);
            sysUser.setPhone(phone);
            sysUser.setSex(sex);
            sysUser.setFromType(shortByZero);
            sysUser.setOrgId(Long.parseLong(orgCodeAndOrgSn));
            sysUser.setOrgSn(Long.parseLong(orgCodeAndOrgSn));
            sysUser.setShortAccount(shortAccount1+shortAccount2);

            SysUserOrg sysUserOrg = new SysUserOrg();
            sysUserOrg.setOrgId(Long.parseLong(orgCodeAndOrgSn));
            sysUserOrg.setIsPrimary(shortByOne);
            sysUserOrg.setIsCharge(shortByZero);
            sysUserOrg.setIsGradeManage(shortByZero);


            if(sysUserService.isAccountExist(account)){
                ISysUser userByAccount = sysUserService.getByAccount(account);
                SysUserOrg userOrgModel = sysUserOrgService.getUserOrgModel(userByAccount.getUserId(), userByAccount.getOrgId());
                sysUser.setUserId(userByAccount.getUserId());
                sysUser.setCreatetime(new Date());
                Long userOrgId = userOrgModel.getUserOrgId();
                sysUserOrgService.delById(userOrgId);
                sysUserService.update(sysUser);
                sysUserOrg.setUserOrgId(userOrgId);
                sysUserOrg.setUserId(userByAccount.getUserId());
                sysUserOrgService.add(sysUserOrg);
            }else{
                sysUser.setUserId(userId);
                sysUserService.add(sysUser);
                sysUserOrg.setUserId(userId);
                sysUserOrg.setUserOrgId(UniqueIdUtil.genId());
                sysUserOrgService.add(sysUserOrg);
            }
            flag = "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = "failed";
        }

        } else if (saveType.equals("Organization")) {
            System.err.println("群组表");
            JSONObject orgJson =(JSONObject) json.get("data");
            System.out.println(orgJson.get("Organization"));
            JSONObject infoJson = (JSONObject) orgJson.get("Organization");
            System.err.println(infoJson.get("orgName"));
            String orgId = (String) infoJson.get("orgCode");
            String orgName = (String) infoJson.get("orgName");
            String orgSecName = (String)infoJson.get("orgSecName");
            Long orgSupId = (long) 1000;
            try {
                SysOrg sysOrg = new SysOrg();
                sysOrg.setOrgName(orgName);
                sysOrg.setOrgSupId(orgSupId);
                sysOrg.setOrgDesc(orgSecName);
                sysOrg.setOrgType(longByOne);
                sysOrg.setFromType(shortByOne);
                sysOrg.setSn(Long.parseLong(orgId));
                sysOrg.setIsSystem(longByZero);
                sysOrg.setOrgId(Long.parseLong(orgId));
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