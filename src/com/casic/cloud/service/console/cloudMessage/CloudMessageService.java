package com.casic.cloud.service.console.cloudMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.console.cloudMessage.CloudMessageDao;
import com.casic.cloud.model.console.cloudMessage.CloudMessage;
import com.casic.cloud.service.console.busiarea.BusiareaService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.SysOrgInfoService;

/**
 *<pre>
 * 对象功能:CLOUD_MESSAGE Service类
 * 开发公司:中国航天科工集团
 * 开发人员:xingchi
 * 创建时间:2013-04-19 13:32:09
 *</pre>
 */
@Service
public class CloudMessageService extends BaseService<CloudMessage>
{
	@Resource
	private CloudMessageDao dao;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private BusiareaService busiareaService;
	
	
	
	public CloudMessageService()
	{
	}
	
	@Override
	protected IEntityDao<CloudMessage, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 从发送消息的内容中获得发送对象
	 * @param content
	 * @param me
	 * @return
	 */
	public List<SysOrgInfo> tos(String content, Long me){
		String regex = ("@(\\S*)\\s");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		List<String> lists = new ArrayList<String>();
		SysOrgInfo to = new SysOrgInfo();
		List<SysOrgInfo> tos = new ArrayList<SysOrgInfo>();
		while (m.find()) {
			lists.add(m.group(1));
		}
		//获取发送对象列表
		for(String name : lists){
			to = sysOrgInfoService.getByName(name);
			//发送消息内容中是商友的才能够成为发送消息的对象
			if(to==null){
				continue;
			}else if(busiareaService.isFriend(me, to.getSysOrgInfoId())){
				tos.add(to);
			}
		}
		tos.add(sysOrgInfoService.getById(me));
		return tos;
	}
}
