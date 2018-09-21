package com.hotent.core.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.validator.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import com.hotent.core.util.AppUtil;


/**
 * 消息操作类
 * @author hotent
 *
 */
public   class ResourceUtil {
	
	
	  /**
     * 取得资源键值 
     * @param msgKey
     * @param arg
     * @param locale
     * @return
     */
    public static String getText(String msgKey,Object arg,Locale local) {
    	MessageSource messageSource=(MessageSource)AppUtil.getBean(MessageSource.class);
    	return messageSource.getMessage(msgKey,new Object[]{ arg}, local);
    }
    
    /**
     * 取得资源键值 
     * @param msgKey
     * @param args
     * @param locale
     * @return
     */
    public static String getText(String msgKey,Object[] args,Locale local) {
    	MessageSource messageSource=(MessageSource)AppUtil.getBean(MessageSource.class);
    	return messageSource.getMessage(msgKey,args, local);
    }
	
    
   
}
