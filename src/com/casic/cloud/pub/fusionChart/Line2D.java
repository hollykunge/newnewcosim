package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;

/**
 * Line2曲线,对应fusionChart的Line2.swf
 * @author zouping
 *
 */
public class Line2D extends FusionChart{
	public Line2D(){
		//初始化Data
		categories = new ArrayList<CategoryList>();
		dataset = new ArrayList<DateSet>();
		styles = new Styles();
		
		chart.setShowalternatehgridcolor("1");
	}	
}
