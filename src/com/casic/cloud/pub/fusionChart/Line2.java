package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

/**
 * Line2曲线,对应fusionChart的Line2.swf
 * @author zouping
 *
 */
public class Line2 extends FusionChart{
	public Line2(){
		//初始化Data
		data = new ArrayList<LabelValue>();
		
		chart.setShowalternatehgridcolor("1");
	}	
}
