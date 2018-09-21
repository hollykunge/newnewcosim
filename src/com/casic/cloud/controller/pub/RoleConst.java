/**
 * 
 */
package com.casic.cloud.controller.pub;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Administrator
 * 角色常量
 */
public class RoleConst {
	//采购主管
	public static Long ROLE_PURCHASE=10000000270000L;
	
	//销售主管
	public static Long ROLE_SALE=10000000040005L;
	
	//生产主管
	public static Long ROLE_PROD=10000002190001L;
	
	//开发主管
	public static Long ROLE_DEV=10000004000223L;
	
	//售后主管
	public static Long ROLE_AFTERSALE=10000000960000L;
	
	//售后人员
	public static Long ROLE_AFTERSALEMAN=10000001180040L;
		
	//运输主管
	public static Long ROLE_DELIVERY=10000014770006L;
		
	public static Map<String,Long> roleAlias = new HashMap<String,Long>();
	
	static{
		roleAlias.put("purchase", ROLE_PURCHASE);
		roleAlias.put("sale", ROLE_SALE);
		roleAlias.put("prod", ROLE_PROD);
		roleAlias.put("dev", ROLE_DEV);
		roleAlias.put("aftersale", ROLE_AFTERSALE);
		roleAlias.put("aftersaleman", ROLE_AFTERSALEMAN);
		roleAlias.put("delivery", ROLE_DELIVERY);
	}
	
	public static Long getRole(String alias){
		Long l = roleAlias.get(alias);
		return l;
	}
	
	@Test
	public void testRole(){
	}
}
