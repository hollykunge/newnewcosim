package com.casic.cloud.model.tool;

public enum ToolType {
	LOCAL(1, "本地"), NETWORK(2, "网络"),CLUSTER(3,"集群");

	private int code;
	private String description;

	private ToolType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static ToolType codeOf(Integer code) {
		if (code == null) {
			return null;
		}
		for (ToolType tt : ToolType.values()) {
			if (tt.code == code.intValue()) {
				return tt;
			}
		}
		return null;
	}

}
