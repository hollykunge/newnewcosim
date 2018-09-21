package com.casic.cloud.service.compass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.spring.CompassDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casic.cloud.model.compass.compassNews.CompassNews;
import com.casic.cloud.model.config.capability.Capability;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysOrgInfo;



//@Service 
//@Transactional
public class SearchServiceBean<T> extends CompassDaoSupport {
	
	@Resource(name="compass")
	public void setSessionFactory(Compass compass) {
		super.setCompass(compass);
	}
	
	public List<T> search(Class clazz, QueryFilter queryFilter) {
		    
		String keywords = (String)queryFilter.getFilters().get("keywords");

	    String key;
		try {
			
			key = URLDecoder.decode(keywords,"UTF-8");
			if(key!=null && !"".equals(key)){
				String[] searchFields = (String[])queryFilter.getFilters().get("searchFields");
			
				int startIndex = queryFilter.getPageBean().getFirst();
				int maxResult =  queryFilter.getPageBean().getPageSize();
				
				List<T> results = getCompassTemplate().execute(
							new ReflectListResultCallback<T>(key, startIndex, maxResult,clazz,searchFields,queryFilter.getPageBean()));
					
				queryFilter.setForWeb();
				return results;
				}else{
					return null;
				}
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			return null;
		}

			
	}

}
