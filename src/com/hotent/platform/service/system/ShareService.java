package com.hotent.platform.service.system;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.Pingyin;
import com.hotent.platform.model.system.Demension;

/**
 * 对象功能:通用service
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2012-09-20 13:57
 */
@Service
public class ShareService extends BaseService<Demension>
{
	public ShareService()
	{
	}
	
	@Override
	protected IEntityDao<Demension, Long> getEntityDao() {
		return null;
	}
	
	/**
	 * 汉字转为拼音
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPingyin(String str) throws UnsupportedEncodingException{
		String nodeKey=Pingyin.getFirstSpell(str);
		return nodeKey;
	}
}
