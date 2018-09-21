package com.casic.cloud.dao.research.fileSign;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.research.fileSign.FileSignInfo;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_FILESIGN_FILEINFO Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-18 14:03:31
 *</pre>
 */
@Repository
public class FileSignInfoDao extends BaseDao<FileSignInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FileSignInfo.class;
	}

	public List<FileSignInfo> getByMainId(Long filesignId) {
		return this.getBySqlKey("getFileSignInfoList", filesignId);
	}
	
	public void delByMainId(Long filesignId) {
		this.delBySqlKey("delByMainId", filesignId);
	}
}