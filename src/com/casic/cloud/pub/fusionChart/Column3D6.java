package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;

/**
 * 3D柱状图样式,对应fusionChart的column3d6.swf
 * @author zouping
 *
 */
public class Column3D6 extends FusionChart{
	public Column3D6(){
		data = new ArrayList<LabelValue>();
		
		chart.setPalette("4");
		chart.setYaxisvaluedecimals("0");
		chart.setShowlabels("1");
	}
}
