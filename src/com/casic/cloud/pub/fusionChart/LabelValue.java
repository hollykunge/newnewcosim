/**
 * 
 */
package com.casic.cloud.pub.fusionChart;

/**
 * fusionchart的data类型
 * @author zouping
 *
 */
public class LabelValue {
	//显示标签
	private String label = "";
	
	//显示值
	private String value = "";

	public String getLabel() {
		return label;
	}

	
	/**
	 * @param label
	 * @param value
	 */
	public LabelValue(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}


	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
