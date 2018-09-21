package com.casic.cloud.service.cloudUseRes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.cloudUseRes.CloudUseRes;
import com.casic.cloud.dao.cloudUseRes.CloudUseResDao;

/**
 *<pre>
 * 对象功能:cloud_use_res Service类
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-16 17:40:22
 *</pre>
 */
@Service
public class CloudUseResService extends BaseService<CloudUseRes>
{
	@Resource
	private CloudUseResDao dao;
	
	
	
	public CloudUseResService()
	{
	}
	
	@Override
	protected IEntityDao<CloudUseRes, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CloudUseRes> getUseRes(Long id,String name){
		List<CloudUseRes> useRes = new ArrayList<CloudUseRes>();
		List<CloudUseRes> list = dao.getUseRes(id);
		int count;
		if(list.size()>1){
			if(list.size()>2){
				count=2;
			}else{
				count=list.size();
			}
			for(int i=0;i<count;i++){
				if(list.get(i).getResName().equals(name)){
					list.get(i).setResTime(new Date());
				}
			}
			if(list.get(0).getResName().equals(list.get(1).getResName())){
				useRes.add(list.get(0));
			}else {
				useRes.add(list.get(0));
				useRes.add(list.get(1));
			}
			return useRes;
		}else{
			return list;
		}
	}
}
