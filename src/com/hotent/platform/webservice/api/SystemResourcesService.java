package com.hotent.platform.webservice.api;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.model.system.ResourcesUrlExt;
import com.hotent.platform.webservice.adpter.SysRoleAdpter;
/**
 * 取得与子系统相关的资源。如子系统URL对应角色的映谢，子系统功能ID对应角色的映谢。
 */
@WebService
public interface SystemResourcesService {
	
	/**
	 * 取得子系统URL资源。
	 * @param alias	子系统别名
	 * @return List<ResourcesUrlExt>	子系统URL对应角色的映的谢对像集合。
	 */
	@WebMethod(operationName="loadSecurityUrl")
	public List<ResourcesUrlExt> loadSecurityUrl(@WebParam(name = "systemId") String systemId);
	/**
	 * 取得子系统功能ID资源。
	 * @param alias	子系统别名
	 * @return List<ResourcesUrlExt>	子系统功能ID对应角色的映的谢对像集合。
	 */
	@WebMethod(operationName="loadSecurityFunction")
	public List<ResourcesUrlExt> loadSecurityFunction(@WebParam(name = "systemId") String systemId);
	/**
	 * 取得子系统角色资源。
	 * @param alias	子系统别名
	 * @return List<SysRole>	子系统对应的角色集合。
	 */
	@WebMethod(operationName="loadSecurityRole")
	@XmlJavaTypeAdapter(SysRoleAdpter.class)
	public List<ISysRole> loadSecurityRole(@WebParam(name = "systemId") String systemId, @WebParam(name = "roleName") String roleName);
	/**
	 * 获取系统是否更新。
	 * @param alias	子系统别名
	 * @return 指定的系统是否更新。
	 */
	@WebMethod(operationName="getSystemResIsUpd")
	public boolean getSystemResIsUpd(@WebParam(name = "systemId") String systemId);
}
