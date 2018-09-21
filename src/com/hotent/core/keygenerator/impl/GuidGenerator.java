package com.hotent.core.keygenerator.impl;

import com.hotent.core.keygenerator.IKeyGenerator;
import com.hotent.core.util.UniqueIdUtil;

/**
 * guid产生ID。
 * @author zhangyg
 *
 */
public class GuidGenerator implements IKeyGenerator {

	@Override
	public Object nextId() throws Exception {
		return UniqueIdUtil.getGuid();
	}

	@Override
	public void setAlias(String alias) {
	}

}
