package com.casic.cloud.service.compass;

import java.util.List;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.spring.CompassDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casic.cloud.model.compass.compassNews.CompassNews;



//@Service 
//@Transactional
public class SearchCompassNewsServiceBean extends CompassDaoSupport {
	
	@Resource(name="compass")
	public void setSessionFactory(Compass compass) {
		super.setCompass(compass);
	}
	
	public List<CompassNews> search(String key,int startIndex, int maxResult) {
		

			if(key!=null && !"".equals(key)){
				return getCompassTemplate().execute(
						new CompassNewsListResultCallback(key, startIndex, maxResult));
			}else{
				return null;
			}
			
	}

}
