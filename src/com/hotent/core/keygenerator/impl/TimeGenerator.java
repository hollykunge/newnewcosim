package com.hotent.core.keygenerator.impl;

import com.hotent.core.keygenerator.IKeyGenerator;
import com.hotent.core.util.UniqueIdUtil;

/**
 * 时间序列产生器。
 * @author ray
 *
 */
public class TimeGenerator implements IKeyGenerator {

	@Override
	public Object nextId() throws Exception {
		// TODO Auto-generated method stub
		return UniqueIdUtil.genId();
	}

	@Override
	public void setAlias(String alias) {
	}

}
