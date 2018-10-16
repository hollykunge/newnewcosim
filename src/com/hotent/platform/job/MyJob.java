package com.hotent.platform.job;

import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hotent.core.scheduler.BaseJob;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.biz.BizRuBariableService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

public class MyJob extends BaseJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		//com.hotent.platform.job.MyJob
//		System.out.println("com.hotent.platform.job.MyJob");
		//context.getJobDetail().getJobDataMap()
//		BizRuBariableService bizRuBariableService = (BizRuBariableService)AppUtil.getBean(BizRuBariableService.class);
//		Map<String,Object> vars = bizRuBariableService.getByBizInstId(10000001430100L);
//		System.out.println(vars);
//		Object v = bizRuBariableService.getByBizInstIdAndName(10000001430100L, "sdsdsd");
//		System.out.println(v);
		SysOrgService sysOrgService = (SysOrgService) AppUtil.getBean(SysOrgService.class);
		ISysOrg sysOrg = sysOrgService.getById(100L);
		System.out.println(sysOrg.getOrgName());
		System.out.println(sysOrg.getOrgStatus());
		System.out.println(sysOrg.getIsSystem());
		
//		SysUserService sysUserService =(SysUserService) AppUtil.getBean(SysUserService.class);
//		sysUserService.getById(1L);
	}


}
