package com.casic.cloud.dao.tool;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.tool.Cloud_tool_user_parasMapper;

/**
 * <pre>
 * 对象功能:cloud_tool_user_parasMapper Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-01 17:11:23
 * </pre>
 */
@Repository
public class Cloud_tool_user_parasMapperDao extends
		BaseDao<Cloud_tool_user_parasMapper> {
	@Override
	public Class<?> getEntityClass() {
		return Cloud_tool_user_parasMapper.class;
	}

	public List<Cloud_tool_user_parasMapper> getByToolUserId(long toolUserId) {
		List<Cloud_tool_user_parasMapper> cloud_tool_user_parasMappers = null;
		cloud_tool_user_parasMappers = getSqlSessionTemplate()
				.selectList(
						"com.casic.cloud.maper.Cloud_tool_user_parasMapper.getByToolUserId",
						toolUserId);
		return cloud_tool_user_parasMappers;
	}
}