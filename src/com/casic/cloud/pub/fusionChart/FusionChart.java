package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图的基类
 * @author zouping
 *
 */
public class FusionChart {
	public FusionChart(){
		
	}
	
	/**
	 * chart参数
	 */
	protected ChartParams chart = new ChartParams();
	
	/**
	 * data参数
	 */
	protected List<LabelValue> data;
	
	/**
	 * categories参数
	 */
	protected List<CategoryList> categories;
	
	/**
	 * dateset参数
	 */
	protected List<DateSet> dataset;
	
	/**
	 * styles参数
	 */
	protected Styles styles;
	
	public ChartParams getChart() {
		return chart;
	}

	public void setChart(ChartParams chart) {
		this.chart = chart;
	}

	public List<LabelValue> getData() {
		return data;
	}

	public void setData(List<LabelValue> data) {
		this.data = data;
	}

	public List<CategoryList> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryList> categories) {
		this.categories = categories;
	}

	public List<DateSet> getDataset() {
		return dataset;
	}

	public void setDataset(List<DateSet> dataset) {
		this.dataset = dataset;
	}

	public Styles getStyles() {
		return styles;
	}

	public void setStyles(Styles styles) {
		this.styles = styles;
	}
}
