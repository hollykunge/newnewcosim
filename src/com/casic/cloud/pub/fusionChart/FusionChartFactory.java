/**
 * 
 */
package com.casic.cloud.pub.fusionChart;

/**
 * fusionChar工厂类
 * @author zouping
 *
 */
public class FusionChartFactory {
	public static FusionChart getInstance(String type){
		if(type.equals("Column3D6"))
			return new Column3D6();
		if(type.equals("Line2"))
			return new Line2();
		if(type.equals("Line2D"))
			return new Line2D();
		
		return new FusionChart();
	}
}
