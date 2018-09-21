package com.casic.cloud.CA.webservice.api;



import javax.jws.WebMethod;
import javax.jws.WebService;
/**
 * 提供给CA门户的service
 * @author ml
 *
 */
@WebService
public interface CAService {
	/**
	 * 二院由shortAccount取出用户所有的任务。并生成xml
	 * @param account ca传递的身份证
	 * @return
	 */
	@WebMethod
	public String getTasksXMLByShortAccount(String shortAccount);

}
