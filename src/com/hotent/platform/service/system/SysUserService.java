package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.UniqueOrgIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.BpmNodeUserUplow;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmNodeUserUplowService;

/**
 * 对象功能:用户表 Service类 
 * 开发公司:
 * 开发人员:heyifan 
 * 创建时间:2012-12-17 16:02:46
 */
@Service
public class SysUserService {
	@Resource
	private IAuthenticate iAuthenticate;
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	@Resource
	private BpmNodeUserUplowService bpmNodeUserUplowService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private SysOrgTypeService sysOrgTypeService;
	@Resource
	private DictionaryService dicService;

	public SysUserService() {
		
	}
	
	public void add(ISysUser user){
		iAuthenticate.add(user);
	}
	
	public void delById(Long id){
		iAuthenticate.delById(ISysUser.class, id);
	}
	
	public void delByIds(Long[] ids){
		iAuthenticate.delByIds(ISysUser.class, ids);
	}
	
	public void update(ISysUser user){
		iAuthenticate.update(user);
	}
	
	public ISysUser getById(Long id){
		return iAuthenticate.getUserByUserId(id);
	}
	
	public List<ISysUser> getAll()	{
		return iAuthenticate.getAllUser();
	}
	
	public List<ISysUser> getAll(QueryFilter queryFilter){
		return iAuthenticate.queryUser(queryFilter);
	}

	public ISysUser getByAccount(String account) {
		return iAuthenticate.getUserByAccount(account);
	}

	/**
	 * 对象功能：根据查询条件查询用户
	 */
	public List<ISysUser> getUserByQuery(QueryFilter queryFilter) {
		return iAuthenticate.queryUser(queryFilter);
	}

	/**
	 * 返回某个角色的所有用户Id
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Long> getUserIdsByRoleId(Long roleId) {
		List<Long> ids = new ArrayList<Long>();
		List<ISysUser> users = iAuthenticate.getUserInRole(roleId);
		for(ISysUser user:users){
			ids.add(user.getUserId());
		}
		return ids;
	}

	/**
	 * 对象功能：根据角色id查询员工
	 */
	public List<ISysUser> getUserByRoleId(QueryFilter queryFilter) {
		return iAuthenticate.queryUserByRoleId(queryFilter);
	}

	/*
	 *  modifyBy hotent.xianggang  添加对应方法  b
	 */
	
	/**
	 * 对象功能：根据企业id查询员工
	 */
	public List<ISysUser> getUserByEnterprise(QueryFilter queryFilter) {
		return iAuthenticate.getUserByEnterprise(queryFilter);
	}
	
	/*
	 *  modifyBy hotent.xianggang  添加对应方法  e
	 */
	
	/**
	 * 获取没有分配组织的用户
	 * 
	 * @return
	 */
	public List<ISysUser> getUserNoOrg(QueryFilter queryFilter) {
		return iAuthenticate.queryUserNoOrg(queryFilter);
	}

	/**
	 * 对象功能：根据岗位路径查询用户
	 */
	public List<ISysUser> getDistinctUserByPosPath(QueryFilter queryFilter) {
		return iAuthenticate.queryUserByPosPath(queryFilter);
	}

	/**
	 * 对象功能：根据组织path查询用户
	 */
	public List<ISysUser> getDistinctUserByOrgPath(QueryFilter queryFilter) {
		return iAuthenticate.queryUserByOrgPath(queryFilter);
	}

	/**
	 * 对象功能：判断是否存在该账号
	 */
	public boolean isAccountExist(String account) {
		return iAuthenticate.isAccountExist(account);
	}
	
	/**
	 * zouping
	 * 2013-05-14
	 * 对象功能：判断同一企业是否存在该账号
	 */
	public boolean isAccountInCompanyExist(String shortAccount, Long orgSn) {
		return iAuthenticate.isAccountInCompanyExist(shortAccount, orgSn);
	}

	/**
	 * 判定帐号是否存在，在更新时使用。
	 * 
	 * @param userId
	 * @param account
	 * @return
	 */
	public boolean isAccountExistForUpd(Long userId, String account) {
		return iAuthenticate.isAccountExistForUpd(userId, account);
	}

	/**
	 * 查询用户属性
	 * 
	 * @param userParam
	 * @return
	 * @throws Exception
	 */
	public List<ISysUser> getByUserParam(String userParam) throws Exception {
		ParamSearch search = new ParamSearch<ISysUser>() {
			@Override
			/**
			 * 实现查找数据的接口。
			 */
			public List<ISysUser> getFromDataBase(Map<String, String> property) {
				return iAuthenticate.queryUserByUserParam(property);
			}
		};
		return search.getByParam(userParam);
	}

	/**
	 * 查询组织属性
	 * 
	 * @param userParam
	 * @return
	 * @throws Exception
	 */
	public List<ISysUser> getByOrgParam(String userParam) throws Exception {
		ParamSearch search = new ParamSearch<ISysUser>() {
			@Override
			/**
			 * 实现查找数据的接口。
			 */
			public List<ISysUser> getFromDataBase(Map<String, String> property) {
				return iAuthenticate.queryUserByOrgParam(property);
			}
		};
		return search.getByParam(userParam);
	}

	/**
	 * 通过用户属性、组织属性获取用户
	 * @param nodeUserId
	 * @return
	 * @throws Exception
	 */
	public List<ISysUser> getByParam(long nodeUserId) throws Exception {
		List<ISysUser> list = null;
		BpmNodeUser bpmNodeUser = bpmNodeUserService.getById(nodeUserId);
		Short ssignType = bpmNodeUser.getAssignType();
		String param = bpmNodeUser.getCmpIds();
		if (ssignType.shortValue() == BpmNodeUser.ASSIGN_TYPE_USER_ATTR) {
			list = getByUserParam(param);
		} else if (ssignType.shortValue() == BpmNodeUser.ASSIGN_TYPE_ORG_ATTR) {
			list = getByOrgParam(param);
		}
		return list;
	}

	/**
	 * 根据当前登陆用户与上下级关系取得当前登陆用户的上下级用户
	 * 
	 * @param userId
	 * @param nodeUserId
	 * @return
	 */
	public List<ISysUser> getByUserIdAndUplow(long userId, long nodeUserId) {
		List<BpmNodeUserUplow> uplowList = bpmNodeUserUplowService.getByNodeUserId(nodeUserId);
		return getByUserIdAndUplow(userId, uplowList);
	}

	/**
	 * 根据 当前登陆用户与上下级关系取得上下级用户
	 * 
	 * @param userId
	 * @param uplowList
	 * @return
	 */
	public List<ISysUser> getByUserIdAndUplow(long userId, List<BpmNodeUserUplow> uplowList) {
		if (uplowList == null)
			return null;
		List<ISysUser> list = new ArrayList<ISysUser>();
		// 当前登陆用户的岗位
		List<Position> pl = null;
		// 当前登陆用户的级织
		List<ISysOrg> ol = null;
		for (BpmNodeUserUplow uplow : uplowList) {
			short upLowType = uplow.getUpLowType();
			int upLowLevel = uplow.getUpLowLevel();
			if (uplow.getDemensionId().longValue() == Demension.positionDem.getDemId().longValue()) {
				// 岗位
				if (pl == null)
					pl = positionService.getByUserId(userId);
				if (pl != null) {
					for (Position p : pl) {
						String currentPath = p.getNodePath();
						int currentDepth = p.getDepth();
						Map<String, Object> param = handlerCondition(currentPath, currentDepth, upLowType, upLowLevel);

						List<ISysUser> l = iAuthenticate.getUserUpLowPost(param);
						list.addAll(l);
					}
				}
			} else {
				// 组织
				long demensionId = uplow.getDemensionId();
				if (ol == null)
					ol = iAuthenticate.getOrgByUserIdAndDemId(userId, demensionId);
				if (ol != null) {
					for (ISysOrg o : ol) {
						String currentPath = o.getPath();
//						int currentDepth = o.getDepth();
						Map<String, Object> param = handlerCondition(currentPath, 1, upLowType, upLowLevel);
						param.put("demensionId", demensionId);

						List<ISysUser> l = iAuthenticate.getUserUpLowOrg(param);
						list.addAll(l);
					}
				}

			}
		}
		return list;
	}

	/**
	 * 根据当前用户所在路径组织查询条件 //此段代码的前提是upLowType:1为上级,0为平级,-1为下级;如此方能计算正确
	 * 
	 * @param currentPath
	 * @param currentDepth
	 * @param upLowType
	 *            1为上级,0为平级,-1为下级
	 * @param upLowLevel
	 * @return
	 */
	private static Map<String, Object> handlerCondition(String currentPath, int currentDepth, short upLowType, int upLowLevel) {

		String pathArr[] = currentPath.split("\\.");

		String path = null;
		Integer depth = null;

		depth = currentDepth + upLowType * upLowLevel * -1 + upLowType;
		path = coverArray2Str(pathArr, depth);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("path", path);
		returnMap.put("depth", depth);
		String condition = "=";

		switch (upLowType) {
		case 1:
			condition = "<";
			break;
		case -1:
			condition = ">";
			break;
		case 0:
			condition = "=";
			break;
		}
		returnMap.put("condition", condition);
		return returnMap;
	}

	/**
	 * 将数组路径转化为字符串
	 * 
	 * @param pathArr
	 * @param len
	 * @return
	 */
	private static String coverArray2Str(String pathArr[], int len) {
		if (len < 0)
			return pathArr[0];
		if (len > pathArr.length)
			len = pathArr.length;

		StringBuilder sb = new StringBuilder();
		if (pathArr.length > 1) {
			int i = 0;
			do {
				sb.append(pathArr[i]);
				sb.append(".");
				i++;
			} while (i < len);

			sb = sb.delete(sb.length() - 1, sb.length());
		} else if (pathArr.length > 0)
			sb = sb.append(pathArr[0]);
		return sb.toString();
	}

	/**
	 * 获取在线用户
	 * @param list
	 * @return
	 */
	public List<ISysUser> getOnlineUser(List<ISysUser> list) {
		List<ISysUser> listOnl = new ArrayList<ISysUser>();
		Map<Long, OnlineUser> onlineUsers = AppUtil.getOnlineUsers();
		List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
		for (Long sessionId : onlineUsers.keySet()) {
			OnlineUser onlineUser = onlineUsers.get(sessionId);
			onlineList.add(onlineUser);
		}
		for (ISysUser sysUser : list) {
			for (OnlineUser onlineUser : onlineList) {
				Long sysUserId = sysUser.getUserId();
				Long onlineUserId = onlineUser.getUserId();
				if (sysUserId.toString().equals(onlineUserId.toString())) {
					listOnl.add(sysUser);
				}
			}
		}
		list = listOnl;
		return list;
	}

	/**
	 * 按用户Id组取到该用户列表
	 * 
	 * @param uIds
	 * @return
	 */
	public List<ISysUser> getByIdSet(Set uIds) {
		return iAuthenticate.getUserByIdSet(uIds);
	}

	public ISysUser getByMail(String address) {
		return iAuthenticate.getUserByMail(address);
	}
	
	/**
	 * 获取所有用户（包含用户的组织ID）
	 * @return
	 */
	public List<ISysUser> getAllIncludeOrg(){
		return iAuthenticate.getAllUser();
	}

	/**
	 * 更新用户密码。
	 * 
	 * @param userId
	 *            用户id
	 * @param pwd
	 *            明文密码。
	 */
	public void updPwd(Long userId, String pwd) {
		String enPassword = EncryptUtil.encryptSha256(pwd);
		iAuthenticate.changePassword(userId, enPassword);
	}

	/**
	 * 更新用户的状态。
	 * 
	 * @param userId
	 *            用户id
	 * @param status
	 *            1，激活，0，禁用，-1，删除
	 * @param isLock
	 *            0，未锁定，1，锁定
	 */
	public void updStatus(Long userId, Short status, Short isLock) {
		iAuthenticate.updUserStatus(userId, status, isLock);
	}

	/**
	 * 保存用户对象
	 * 
	 * @param sysUser
	 *            用户对象
	 * @param orgIdCharge
	 *            负责的组织。
	 * @param aryOrgIds
	 *            组织ID
	 * @param orgIdPrimary
	 *            主组织
	 * @param posIds
	 *            岗位ID
	 * @param posIdPrimary
	 *            主岗位
	 * @param roleIds
	 *            用户角色
	 * @throws Exception
	 */
	public void saveUser(Integer bySelf, ISysUser sysUser, Long[] aryOrgIds, Long[] orgIdCharge, Long orgIdPrimary, Long[] posIds, Long posIdPrimary, Long[] roleIds)
			throws Exception {
		if(aryOrgIds==null || aryOrgIds.length==0){//没有选择企业（即组织）
			throw new Exception("必须选择所属企业");
		}
		if(roleIds==null || roleIds.length==0){//没有选择角色
			throw new Exception("必须选择所属角色");
		}
		if (sysUser.getUserId() == null) {
			sysUser.setUserId(UniqueIdUtil.genId());
			iAuthenticate.add(sysUser);
		} else {
			iAuthenticate.update(sysUser);
		}
		if (bySelf == 0) {
			Long userId = sysUser.getUserId();
			// 添加用户和组织关系。
			sysUserOrgService.saveUserOrg(userId, aryOrgIds, orgIdPrimary, orgIdCharge);
			// 处理岗位信息。
			userPositionService.saveUserPos(userId, posIds, posIdPrimary);
			// 保存与角色的映射关系。
			userRoleService.saveUserRole(userId, roleIds);
			// 清除用户角色映射缓存。
			SecurityUtil.removeUserRoleCache(userId);
		}
		
	}
	
	public void saveUser(ISysUser sysUser, Long orgId, Short isCharge,Long[] roleIds)
			throws Exception {
		if(orgId==null){//没有选择企业（即组织）
			throw new Exception("必须选择所属企业");
		}
		if(roleIds==null || roleIds.length==0){//没有选择角色
			throw new Exception("必须选择所属角色");
		}
		if (sysUser.getUserId() == null) {
			sysUser.setUserId(UniqueIdUtil.genId());
			iAuthenticate.add(sysUser);
		} else {
			iAuthenticate.update(sysUser);
		}
		Long userId = sysUser.getUserId();
		// 添加用户和组织关系。
		sysUserOrgService.saveUserOrg(userId, orgId,isCharge);
		// 保存与角色的映射关系。
		userRoleService.saveUserRole(userId, roleIds);
		// 清除用户角色映射缓存。
		SecurityUtil.removeUserRoleCache(userId);
	}
	
	/**
	 * 通过用户来源类型获取用户列表
	 * @param type
	 * @return
	 */
	public List<ISysUser> getByFromType(Short type){
		return iAuthenticate.getUserByFromType(type);
	}
	
	//【HT】根据企业SN和用户帐号查询用户
	public ISysUser getSysUserByOrgSnAndAccount(Long orgSn,String account){
		return this.iAuthenticate.getSysUserByOrgSnAndAccount(orgSn, account);
	}
	
	//【HT】企业注册
	public Map<String,Object> registerSysOrg(SysOrgInfo sysOrgInfo, String password) throws Exception{	
		SysOrg sysOrg = new SysOrg();
		//企业id单独添加
		if(sysOrgInfo.getSysOrgInfoId()==null)
			sysOrg.setOrgId(UniqueOrgIdUtil.genId());
		else
			sysOrg.setOrgId(sysOrgInfo.getSysOrgInfoId());
		
		//设置Path
		Long demId = Long.valueOf("1");//维度，默认是1，如果需要维度功能，可扩展这里。
		sysOrg.setDemId(Long.valueOf("1"));
		sysOrg.setPath(demId + "." + sysOrg.getOrgId() + ".");
		sysOrg.setOrgType(Long.valueOf("1"));//TODO 这里硬编码，有需要可以调整为从组织类型中读取
		sysOrg.setOrgName(sysOrgInfo.getName());
		sysOrg.setOrgDesc(sysOrg.getOrgName());
		sysOrg.setOrgSupId(1L);
		sysOrg.setSn(sysOrg.getOrgId());
		sysOrgService.add(sysOrg);
		
		//创建1对1的企业信息
		//sysOrgInfo.setRegisttime(new Date());
		sysOrgInfo.setState(0);
		sysOrgInfo.setSysOrgInfoId(sysOrg.getOrgId());
		sysOrgInfo.setCreatetime(new Date());
		//根据国家填写国旗
		sysOrgInfo.setFlaglogo(getFlagLogoByCountry(sysOrgInfo.getCountry()));
		sysOrgInfoService.add(sysOrgInfo);
		sysOrg.setSysOrgInfo(sysOrgInfo);
		//创建若干部门
		doInitSubOrgs(sysOrg);
		
		//初始化
		SysUser sysUser = doInitAfterRegister(sysOrg, password);
		sysOrg.setSysUser(sysUser);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sysOrgInfo", sysOrgInfo);
		map.put("sysOrg", sysOrg);
		map.put("sysUser", sysUser);
		
		return map;
	}
	
	/**
	 * 根据国家获取国别
	 * @return
	 */
	public String getFlagLogoByCountry(String country){
		if(country==null) return "";
		List<Dictionary> dics = dicService.getByTypeId(10000003990001L,false);
		for(Dictionary dic : dics){
			if(dic.getItemName().equals(country)){
				return dic.getItemValue();
			}
		}
		return "";
	}
	
	//【HT】企业创建后的初始化操作
	private SysUser doInitAfterRegister(SysOrg sysOrg, String password) throws Exception{
		Long[] roleIds = this.getInitRoleIds();
		SysUser sysUser = new SysUser();
		sysUser.setOrgId(sysOrg.getOrgId());
		sysUser.setOrgSn(sysOrg.getSn());
		sysUser.setFullname(sysOrg.getOrgName() + "_企业内部管理员");
		sysUser.setAccount(sysOrg.getSn() + "_system");
		sysUser.setShortAccount("system");
		String enPassword = EncryptUtil.encryptSha256(password);
		sysUser.setPassword(enPassword);
		sysUser.setIsExpired(Short.valueOf("0"));
		sysUser.setIsLock(Short.valueOf("0"));
		sysUser.setStatus(Short.valueOf("1"));
		//创建管理员用户（关联到该企业下，并授予默认的角色）
		this.saveUser(sysUser, sysOrg.getOrgId(), SysUserOrg.CHARRGE_YES, roleIds);
		
//		//采购管理员
//		SysUser buyer = new SysUser();
//		buyer.setOrgId(sysOrg.getOrgId());
//		buyer.setOrgSn(sysOrg.getSn());
//		buyer.setFullname("企业采购经理");
//		buyer.setAccount(sysOrg.getSn() + "_buyer");
//		buyer.setShortAccount("buyer");
//		buyer.setPassword(enPassword);
//		buyer.setIsExpired(Short.valueOf("0"));
//		buyer.setIsLock(Short.valueOf("0"));
//		buyer.setStatus(Short.valueOf("1"));
		
//		//创建管理员用户（关联到该企业下，并授予默认的角色）
//		this.saveUser(saler, sysOrg.getOrgId(), SysUserOrg.CHARRGE_YES, new Long[]{10000000040005L});
		return sysUser;
	}
	private Long[] getInitRoleIds(){
		//查询所有系统角色
		Long systemId = Long.valueOf("1");
		List<ISysRole> sysRoleList  = this.iAuthenticate.getRoleBySystemId(systemId);
		//授予两个角色（企业内管理员角色和普通用户角色），这里是硬编码两个角色的别名
		Long[] roleIds = new Long[2];
		for(ISysRole sysRole:sysRoleList){
			if(sysRole.getAlias().equals("cloud_ROLE_COMMON")){
				roleIds[0] = sysRole.getRoleId();				
			}else if(sysRole.getAlias().equals("cloud_ROLE_ORGADMIN")){
				roleIds[1] = sysRole.getRoleId();
			}
		}
		return roleIds;
	}
	//【HT】在该企业下建立若干的部门，采购部，销售部，服务部、生产部和研发部
	private void doInitSubOrgs(SysOrg companyOrg){
		createDepartmentSysOrg(companyOrg,"采购部");
		createDepartmentSysOrg(companyOrg,"销售部");
		createDepartmentSysOrg(companyOrg,"服务部");
		createDepartmentSysOrg(companyOrg,"生产部");
		createDepartmentSysOrg(companyOrg,"研发部");
	}
	private void createDepartmentSysOrg(SysOrg companyOrg,String orgName){
		SysOrg subOrg = new SysOrg();
		subOrg.setOrgId(UniqueIdUtil.genId());
		subOrg.setDemId(companyOrg.getDemId());
		subOrg.setOrgName(orgName);
		subOrg.setOrgSupId(companyOrg.getOrgId());
		subOrg.setOrgType(2L);
		subOrg.setPath(companyOrg.getPath() + subOrg.getOrgId() +".");
		subOrg.setSn(companyOrg.getSn());
		subOrg.setFromType(Short.valueOf("1"));
		sysOrgService.add(subOrg);
	}
////ht:raise add b
	
	/**
	 * 根据企事ID和角色ID，取得用户。如果compIds为空，返回空的
	 * @param compIds
	 * @param roleIds
	 * @return
	 */
	public List<ISysUser> getByCompsAndRoles(List<Long> compIds,List<Long> roleIds){
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		if(BeanUtils.isEmpty(compIds)){
			return sysUsers;
		}
		for(Long compId:compIds){
			List<ISysUser> list;
			if(BeanUtils.isEmpty(roleIds)){
				list =  iAuthenticate.getCompDefaultUser(compId);
			}else{
				list = iAuthenticate.getByCompAndRoles(compId, roleIds);
				if(BeanUtils.isEmpty(list)){
					list = iAuthenticate.getCompDefaultUser(compId);
				}
			}
			sysUsers.addAll(list);
		}
		return sysUsers;
	}
	
	/**
	 * 根据企业ID，取得默认用户（管理员）
	 * @param compId
	 * @return
	 */
	public List<ISysUser> getCompDefaultUser(Long compId){
		return iAuthenticate.getCompDefaultUser(compId);
	}
	
	////ht:raise add e
}
