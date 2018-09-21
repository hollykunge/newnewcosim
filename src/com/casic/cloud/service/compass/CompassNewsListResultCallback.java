package com.casic.cloud.service.compass;

import java.util.ArrayList;
import java.util.List;

import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;

import com.casic.cloud.model.compass.compassNews.CompassNews;


public class CompassNewsListResultCallback implements
		CompassCallback<List<CompassNews>> {
	private String key;
	private int startIndex;
	private int maxResult;
	
	public CompassNewsListResultCallback(String key, int startIndex, int maxResult) {
		this.key = key;
		this.startIndex = startIndex;
		this.maxResult = maxResult;
	}

	public List<CompassNews> doInCompass(CompassSession session)
			throws CompassException {
		List<CompassNews> datas = new ArrayList<CompassNews>();
		
		
		CompassQueryBuilder queryBuilder = session.queryBuilder();
		CompassQuery query1 = queryBuilder.queryString(key).toQuery();
	
		query1.setAliases("CompassNews");
		
		CompassHits hits =  query1.hits();
		
//		CompassHits hits= queryBuilder.bool().addMust(queryBuilder.term("alias", "compassNews")).
//				addMust(query1).toQuery().hits();
//		CompassHits hits = session.find(key);
		
		
		int lastIndex = startIndex + maxResult -1;
		if(lastIndex > (hits.length()-1)) 
			lastIndex = hits.length()-1;
		for(int i = startIndex; i <= lastIndex; i++) {
			
			Object o = hits.data(0);
			CompassNews add = (CompassNews)hits.data(i);
			
			//高亮显示
			if(hits.highlighter(i).fragment("subject")!=null) {
				add.setSubject(hits.highlighter(i).fragment("subject"));
			}
			datas.add(add);
		}
		return datas;
	}

}