package com.hotent.core.sms.impl;

import java.util.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hotent.core.sms.IShortMessage;

public class NoneMessageImpl implements IShortMessage {
	private static NoneMessageImpl instance;
	private static Lock lock = new ReentrantLock();

	public boolean sendSms(List<String> mobiles, String message) {
		System.out.println("不支持的短信类型...");
		return false;
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static NoneMessageImpl getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null)
					instance = new NoneMessageImpl();

			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

}
