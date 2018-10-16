/**
 * 对象功能:组织架构SYS_ORG Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:pkq
 * 创建时间:2011-11-09 11:20:13
 */
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.model.system.SysOrg;

@Repository
public class SysOrgDao extends BaseDao<ISysOrg> {
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return SysOrg.class;
	}

	/**
	 * 对象功能：根据组织id查询下级组织 开发公司:广州宏天软件有限公司 开发人员:pkq 创建时间:2011-11-08 12:04:22
	 */
	public List<ISysOrg> getOrgByOrgId(QueryFilter queryFilter) {
		return this.getBySqlKey("getOrgByOrgId", queryFilter);
	}

	/**
	 * 对象功能：根据维度找组织节点
	 */
	public List<ISysOrg> getOrgByDemId(Long demId) {
		return this.getBySqlKey("getOrgByDemId", demId);
	}

	/**
	 * 更新序号。
	 * 
	 * @param orgId
	 * @param sn
	 */
	public void updSn(Long orgId, long sn) {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("sn", sn);
		this.update("updSn", params);
	}

	/**
	 * 对象功能：获取需要删除的用户ID列表
	 */
	public Long getOneByuserOrgId(Long userOrgId) {
		return (Long) this.getOne("getOneByuserOrgId", userOrgId);
	}

	/**
	 * 根据userId取得组织列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ISysOrg> getOrgsByUserId(Long userId) {
		return getBySqlKey("getOrgsByUserId", userId);
	}

	/**
	 * 取得所有组织列表或按维度取得所有组织
	 * 
	 * @param demId
	 * @return
	 */
	public List<ISysOrg> getOrgsByDemIdOrAll(Long demId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (demId != 0) {
			params.put("demId", demId);
		}
		return getBySqlKey("getOrgsByDemIdOrAll", params);
	}

	public List<ISysOrg> getByUserIdAndDemId(Long userId, Long demId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("userId", userId);
		m.put("demId", demId);
		return getBySqlKey("getByUserIdAndDemId", m);
	}

	public List<ISysOrg> getByDepth(Integer depth) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depth", depth);
		return getBySqlKey("getByDepth", params);
	}

	/**
	 * 根据路径得到组织集合
	 * 
	 * @param path
	 * @return
	 */
	public List<ISysOrg> getByOrgPath(String path) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("path", StringUtil.isNotEmpty(path) ? (path + "%") : "");
		return getBySqlKey("getByOrgPath", params);
	}

	/**
	 * 根据组织来源得到组织集合
	 * 
	 * @param type
	 *            0-系统添加，1-来自AD同步
	 * @return
	 */
	public List<ISysOrg> getByFromType(short type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromType", type);
		return getBySqlKey("getByFromType", params);
	}

	/**
	 * 根据路径删除组织。
	 * 
	 * @param path
	 *            组织路径。
	 */
	public void delByPath(String path) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("path", StringUtil.isNotEmpty(path) ? (path + "%") : "");
		this.delBySqlKey("delByPath", params);

	}

	/**
	 * 根据用户取得主组织。
	 * 
	 * @param userId
	 * @return
	 */
	public ISysOrg getPrimaryOrgByUserId(Long userId) {
		return this.getUnique("getPrimaryOrgByUserId", userId);
	}

	/**
	 * 根据组织ID获取组织列表。
	 * 
	 * @param userId
	 * @return
	 */
	public List<ISysOrg> getOrgByIds(String orgIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgIds", orgIds);
		List<ISysOrg> list = this.getBySqlKey("getOrgByIds", params);
		return list;
	}

	/**
	 * 根据上级组织ID获取组织列表。
	 * 
	 * @param userId
	 * @return
	 */
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgSupId", orgSupId);
		List<ISysOrg> list = this.getBySqlKey("getOrgByOrgSupId", params);
		return list;
	}

	/**
	 * zouping 2013-05-18 根据上级组织ID获取组织列表。
	 * @param orgSupId 上级组织Id
	 * @param compId 公司Id
	 * @return
	 */
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId, Long compId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgSupId", orgSupId);
		params.put("sn", compId);
		List<ISysOrg> list = this.getBySqlKey("getOrgByOrgSupIdAndSn",params);
		return list;
	}
}