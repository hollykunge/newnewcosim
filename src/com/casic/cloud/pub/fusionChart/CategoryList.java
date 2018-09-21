/**
 * 
 */
package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Fusionchart的Category对象
 * @author Administrator
 *
 */
public class CategoryList {
	private List<Label> category = new ArrayList<Label>();

	public class Label{
		private String label = "";

		public Label(String label){
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	public List<Label> getCategory() {
		return category;
	}

	public void setCategory(List<Label> category) {
		this.category = category;
	}
}
