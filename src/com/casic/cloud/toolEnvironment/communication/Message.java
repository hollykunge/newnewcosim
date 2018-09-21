package com.casic.cloud.toolEnvironment.communication;

import java.io.File;
import java.io.Serializable;

import com.casic.cloud.toolEnvironment.util.SoftData;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 消息类型 0代表sofData 1代表执行生成输入文件请求 2代表执行工具请求 3代表生成输入文件完成应答 4代表执行工具完成应答
	// 5代表下载输出文件请求 6代表下载输出文件应答 7代表输入文件上传请求 8代表强制停止工具请求
	private int type;
	private SoftData softData;
	// 传输的文件
	private String filePath;
	private byte[] fileBytes;
	private boolean isStarted = false;
	private boolean isEnd = true;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SoftData getSoftData() {
		return softData;
	}

	public void setSoftData(SoftData softData) {
		this.softData = softData;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

}
