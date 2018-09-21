package com.casic.cloud.service.compass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.springframework.util.ReflectionUtils;

import com.fr.base.StringUtils;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;



public class ReflectListResultCallback<T> implements
		CompassCallback<List<T>> {
	private String key;
	private int startIndex;
	private int maxResult;
	private Class clazz;
	private String[] searchFields;
	private PageBean pageBean; 
	
	
	public ReflectListResultCallback(String key, int startIndex, int maxResult,Class clazz, String[] searchFields, PageBean pageBean) {
		this.key = key;
		this.startIndex = startIndex;
		this.maxResult = maxResult;
		this.clazz = clazz;
		this.searchFields = searchFields;
		this.pageBean = pageBean;
		
	}

	public List<T> doInCompass(CompassSession session)
			throws CompassException {
		List<T> datas = new ArrayList<T>();
		
		CompassQueryBuilder queryBuilder = session.queryBuilder();
		CompassQuery query = queryBuilder.queryString(key).toQuery();
	
		String[] aliases =clazz.getName().split("\\.");
		String aliase = aliases[aliases.length-1];
		query.setAliases(aliase);
		
		CompassHits hits =  query.hits();
		pageBean.setTotalCount(hits.length());
		
		int lastIndex = startIndex + maxResult -1;
		if(lastIndex > (hits.length()-1)) 
		lastIndex = hits.length()-1;
		for(int i = startIndex; i <= lastIndex; i++) {
			Object o = (Object)hits.data(i);
	
			try {
					@SuppressWarnings("unchecked")
				T t = (T)clazz.newInstance();
				
				BeanUtils.copyProperties(t, o);
			
				for(String field:searchFields){
					
					//高亮显示
					try{
						hits.highlighter(i).fragment(field);
					}catch(Exception e){
						continue;
					}
					if(hits.highlighter(i).fragment(field)!=null) {
							Field cfield;
						try {
							String subject = hits.highlighter(i).fragment(field);	
							if(StringUtils.trim(subject).length() > 0){
								cfield = clazz.getDeclaredField(field);
								//得到set方法的参数类型
								Class[] parameterTypes = new Class[1];
								parameterTypes[0] = cfield.getType(); 
								//拼凑set（Field）字符串
							    StringBuffer sb = new StringBuffer();       						  
							    sb.append("set");       							  
							    sb.append(field.substring(0, 1).toUpperCase());       							  
							    sb.append(field.substring(1));       
							    //得到该filed的set方法
							    Method method = clazz.getMethod(sb.toString(), parameterTypes);  
							    //执行set方法
							    method.invoke(t, new Object[] { subject });
															
									//ReflectionUtils.setField(cfield, t, subject);属性是protexted，不允许这样调用
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		
					
							//add.setName(hits.highlighter(i).fragment(field));
						}
					}
					
					datas.add(t);
					
				
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				

		}
		return datas;
	}

	
	@SuppressWarnings("unchecked")       
	  
	public static Method getSetMethod(Class objectClass, String fieldName) {       
	  
	    try {       
	  
	        Class[] parameterTypes = new Class[1];       
	  
	        Field field = objectClass.getDeclaredField(fieldName);       
	  
	        parameterTypes[0] = field.getType();       
	  
	        StringBuffer sb = new StringBuffer();       
	  
	        sb.append("set");       
	  
	        sb.append(fieldName.substring(0, 1).toUpperCase());       
	  
	        sb.append(fieldName.substring(1));       
	  
	        Method method = objectClass.getMethod(sb.toString(), parameterTypes);       
	  
	        return method;       
	  
	    } catch (Exception e) {       
	  
	        e.printStackTrace();       
	  
	    }       
	  
	    return null;       
	  
	}       
}