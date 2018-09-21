package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;

/**
 * 三室气动部件类
 * @author BUAA
 *
 */
public class Component_3Shi  implements Serializable{
	//编号
	private String num;
	//名称
	private String name;
	//最小细化数
	private String minRefine;
	//最大细化数
	private String maxRefine;
	//参考面积
	private String area;
	//参考长度
	private String length;
	//momentPoint
	private String momentPoint;
	//momentLine
	private String momentLine1,momentLine2;
	//切面
	private String plane1,plane2,plane3;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMinRefine() {
		return minRefine;
	}
	public void setMinRefine(String minRefine) {
		this.minRefine = minRefine;
	}
	public String getMaxRefine() {
		return maxRefine;
	}
	public void setMaxRefine(String maxRefine) {
		this.maxRefine = maxRefine;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getMomentPoint() {
		return momentPoint;
	}
	public void setMomentPoint(String momentPoint) {
		this.momentPoint = momentPoint;
	}
	public String getMomentLine1() {
		return momentLine1;
	}
	public void setMomentLine1(String momentLine1) {
		this.momentLine1 = momentLine1;
	}
	public String getMomentLine2() {
		return momentLine2;
	}
	public void setMomentLine2(String momentLine2) {
		this.momentLine2 = momentLine2;
	}
	public String getPlane1() {
		return plane1;
	}
	public void setPlane1(String plane1) {
		this.plane1 = plane1;
	}
	public String getPlane2() {
		return plane2;
	}
	public void setPlane2(String plane2) {
		this.plane2 = plane2;
	}
	public String getPlane3() {
		return plane3;
	}
	public void setPlane3(String plane3) {
		this.plane3 = plane3;
	}
	
	
}
