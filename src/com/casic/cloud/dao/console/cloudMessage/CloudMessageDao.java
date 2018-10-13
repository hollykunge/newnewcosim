package com.casic.cloud.dao.console.cloudMessage;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.console.cloudMessage.CloudMessage;
/**
 *<pre>
 * 对象功能:CLOUD_MESSAGE Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-19 13:32:09
 *</pre>
 */
@Repository
public class CloudMessageDao extends BaseDao<CloudMessage>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudMessage.class;
	}

}