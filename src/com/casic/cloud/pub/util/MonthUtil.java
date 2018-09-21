/**
 * 
 */
package com.casic.cloud.pub.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 月份显示工具类
 * @author zouping
 *
 */
public class MonthUtil {
	//map的排序是按照Hash值来排序的,所以显示的时候不会按照一月，二月...这样显示	
	public static Map<Integer,String> map = new TreeMap<Integer, String>();
	
	static{
		map.put(1, "一月");
		map.put(2, "二月");
		map.put(3, "三月");
		map.put(4, "四月");
		map.put(5, "五月");
		map.put(6, "六月");
		map.put(7, "七月");
		map.put(8, "八月");
		map.put(9, "九月");
		map.put(10, "十月");
		map.put(11, "十一月");
		map.put(12, "十二月");
	}
	
	/**
	 * 根据月份数字获取月份中文显示名称
	 * @param i
	 * @return
	 */
	public static String getByInteger(int i){
		return map.get(i);
	}
	
	/**
	 * 显示所有月份中文名称
	 * @return
	 */
	public static String[] listMonths(){
		String[] months = new String[12];
		for(int i = 1; i<13; i++){
			months[i-1] = getByInteger(i);
		}
		return months;
	}
}
