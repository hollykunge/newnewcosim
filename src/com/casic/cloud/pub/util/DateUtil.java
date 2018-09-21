package com.casic.cloud.pub.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author zouping
 *
 */
public class DateUtil {
	
	/**
	 * 返回年份
	 */
	public static String getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String year = String.valueOf(c.get(Calendar.YEAR));
		return year;
	}
	
	/**
	 * 返回今年的年份
	 */
	public static int getCurrentYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 返回当前月份
	 */
	public static int getCurrentMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}
	
	/**
	 * 返回本年度第一天
	 * @return
	 */
	public static Date getFirstDate(int year){
		Calendar c = Calendar.getInstance();
		c.set(year, 0, 1);
		return c.getTime();
	}
	
	/**
	 * 返回本年度最后一天
	 * @return
	 */
	public static Date getLastDate(int year){
		Calendar c = Calendar.getInstance();
		c.set(year, 11, 31);
		return c.getTime();
	}
	
	/**
	 * 根据月份返回该月第一天的日期
	 * @return
	 */
	public static Date getBeginDateMonthByCurrentYear(int month){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.get(Calendar.YEAR), month-1, 1);
		
		return c.getTime();
	}
	
	/**
	 * 根据月份和年份返回该月第一天的日期
	 * @return
	 */
	public static Date getBeginDateMonthByCurrentYear(int year, int month){
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, 1);
		return c.getTime();
	}
	
	/**
	 * 根据月份返回该月最后一天的日期
	 * @return
	 */
	public static Date getEndDateMonthByCurrentYear(int month){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int day = 28;
		if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
			day = 31;
		}else if(month==4 || month==6 || month==9 || month==11){
			day = 30;
		}else if(month == 2){
			//判断是否为闰年
			if((year%4==0 && year%100!=0) || year%400==0){
				day = 29;
			}else{
				day = 28;
			}
			
		}
		c.set(c.get(Calendar.YEAR), month-1, day);
		
		return c.getTime();
	}
	
	/**
	 * 根据月份和年份返回该月最后一天的日期
	 * @return
	 */
	public static Date getEndDateMonthByCurrentYear(int year, int month){
		Calendar c = Calendar.getInstance();
		int day = 28;
		if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
			day = 31;
		}else if(month==4 || month==6 || month==9 || month==11){
			day = 30;
		}else if(month == 2){
			//判断是否为闰年
			if((year%4==0 && year%100!=0) || year%400==0){
				day = 29;
			}else{
				day = 28;
			}
			
		}
		c.set(year, month-1, day);
		
		return c.getTime();
	}
}
