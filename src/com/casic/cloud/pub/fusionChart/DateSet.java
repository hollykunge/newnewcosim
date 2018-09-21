/**
 * 
 */
package com.casic.cloud.pub.fusionChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Fusionchart的Dateset参数
 * @author Administrator
 *
 */
public class DateSet {

	private String seriesname;
	
	private String color = "1D8BD1";
	
	private String anchorbordercolor = "1D8BD1";
	
	private String anchorbgcolor = "1D8BD1";
	
	private List<Data> data = new ArrayList<Data>();
	
	public class Data{
		private String value = "";

		/**
		 * @param value
		 */
		public Data(String value) {
			super();
			this.value = value;
		}


		public String getValue() {
			return value;
		}


		public void setValue(String value) {
			this.value = value;
		}
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAnchorbordercolor() {
		return anchorbordercolor;
	}

	public void setAnchorbordercolor(String anchorbordercolor) {
		this.anchorbordercolor = anchorbordercolor;
	}

	public String getAnchorbgcolor() {
		return anchorbgcolor;
	}

	public void setAnchorbgcolor(String anchorbgcolor) {
		this.anchorbgcolor = anchorbgcolor;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
}
