package com.hotent.platform.service.bpm;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public interface ISignComplete {	
	/**
	 * 会签投票通过
	 */
	public static String SIGN_RESULT_PASS="pass";
	/**
	 * 会签未通过投票
	 */
	public static String SIGN_RESULT_REFUSE="refuse";
	

	boolean isComplete(ActivityExecution execution);
}
