package com.casic.cloud.dao.research.fileVerify;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.research.fileVerify.FileVerifyResult;
/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_VERIFY_RESULT Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-14 20:38:41
 *</pre>
 */
@Repository
public class FileVerifyResultDao extends BaseDao<FileVerifyResult>
{
	@Override
	public Class<?> getEntityClass()
	{
		return FileVerifyResult.class;
	}

	public List<FileVerifyResult> getByMainId(Long sourceId) {
		return this.getBySqlKey("getFileVerifyResultList", sourceId);
	}
	
	public void delByMainId(Long sourceId) {
		this.delBySqlKey("delByMainId", sourceId);
	}
}