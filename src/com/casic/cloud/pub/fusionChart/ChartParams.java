package com.casic.cloud.pub.fusionChart;

/**
 * fusionchart的chart类
 * @author zouping
 *
 */
public class ChartParams {
	private String palette = "";
	
	//是否显示值
	private String showvalues = "1";
	
	//是否显示边框
	private String showborder = "1";
	
	//是否显示图形阴影
	private String showcolumnshadow = "1";
	
	//是否显示标签值
	private String showlabels = "1";
		
	//是否显示动画
	private String animation = "1";
		
	//是否显示表格背景颜色
	private String showalternatehgridcolor = "0";
	
	//线条粗细
	private String linethickness = "1";
	
	//步长
	private String labelstep = "1";
	
	//强制y轴显示小数点
	private String forceyaxisvaluedecimals = "1";
	
	//y轴小数点几位,默认显示两位
	private String yaxisvaluedecimals = "2";

	//间隔
	private String canvaspadding = "10";
	
	//统计标题：例如：销售统计
	private String caption = "";
	
	//统计子标题，例如：2013年销售数据
	private String subcaption = "";
	
	//x轴显示名称,例如：金额
	private String xaxisname = "";
	
	//y轴显示名称,例如：月份
	private String yaxisname = "";
	
	//值的前缀,例如：￥
	private String numberprefix = "";
	
	//表格背景颜色
	private String alternatehgridcolor = "ff5904";
	
	//Div线条颜色
	private String divlinecolor = "ff5904";
	
	//Div线条透明度
	private String divlinealpha = "20";
	
	//背景表格透明度
	private String alternatehgridalpha = "5";
	
	//边框颜色
	private String canvasbordercolor = "666666";
	
	//字体颜色
	private String basefontcolor = "666666";
	
	//线条颜色
	private String linecolor = "FF5904";
	
	//线条透明度
	private String linealpha = "85";
	
	//旋转值
	private String rotatevalues = "1";
	
	//值的位置
	private String valueposition = "auto";
	
	public String getPalette() {
		return palette;
	}

	public void setPalette(String palette) {
		this.palette = palette;
	}

	public String getShowvalues() {
		return showvalues;
	}

	public void setShowvalues(String showvalues) {
		this.showvalues = showvalues;
	}

	public String getShowborder() {
		return showborder;
	}

	public void setShowborder(String showborder) {
		this.showborder = showborder;
	}

	public String getForceyaxisvaluedecimals() {
		return forceyaxisvaluedecimals;
	}

	public void setForceyaxisvaluedecimals(String forceyaxisvaluedecimals) {
		this.forceyaxisvaluedecimals = forceyaxisvaluedecimals;
	}

	public String getYaxisvaluedecimals() {
		return yaxisvaluedecimals;
	}

	public void setYaxisvaluedecimals(String yaxisvaluedecimals) {
		this.yaxisvaluedecimals = yaxisvaluedecimals;
	}

	public String getCanvaspadding() {
		return canvaspadding;
	}

	public void setCanvaspadding(String canvaspadding) {
		this.canvaspadding = canvaspadding;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubcaption() {
		return subcaption;
	}

	public void setSubcaption(String subcaption) {
		this.subcaption = subcaption;
	}

	public String getXaxisname() {
		return xaxisname;
	}

	public void setXaxisname(String xaxisname) {
		this.xaxisname = xaxisname;
	}

	public String getYaxisname() {
		return yaxisname;
	}

	public void setYaxisname(String yaxisname) {
		this.yaxisname = yaxisname;
	}

	public String getNumberprefix() {
		return numberprefix;
	}

	public void setNumberprefix(String numberprefix) {
		this.numberprefix = numberprefix;
	}

	public String getShowlabels() {
		return showlabels;
	}

	public void setShowlabels(String showlabels) {
		this.showlabels = showlabels;
	}

	public String getShowcolumnshadow() {
		return showcolumnshadow;
	}

	public void setShowcolumnshadow(String showcolumnshadow) {
		this.showcolumnshadow = showcolumnshadow;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

	public String getShowalternatehgridcolor() {
		return showalternatehgridcolor;
	}

	public void setShowalternatehgridcolor(String showalternatehgridcolor) {
		this.showalternatehgridcolor = showalternatehgridcolor;
	}

	public String getAlternatehgridcolor() {
		return alternatehgridcolor;
	}

	public void setAlternatehgridcolor(String alternatehgridcolor) {
		this.alternatehgridcolor = alternatehgridcolor;
	}

	public String getDivlinecolor() {
		return divlinecolor;
	}

	public void setDivlinecolor(String divlinecolor) {
		this.divlinecolor = divlinecolor;
	}

	public String getDivlinealpha() {
		return divlinealpha;
	}

	public void setDivlinealpha(String divlinealpha) {
		this.divlinealpha = divlinealpha;
	}

	public String getAlternatehgridalpha() {
		return alternatehgridalpha;
	}

	public void setAlternatehgridalpha(String alternatehgridalpha) {
		this.alternatehgridalpha = alternatehgridalpha;
	}

	public String getCanvasbordercolor() {
		return canvasbordercolor;
	}

	public void setCanvasbordercolor(String canvasbordercolor) {
		this.canvasbordercolor = canvasbordercolor;
	}

	public String getBasefontcolor() {
		return basefontcolor;
	}

	public void setBasefontcolor(String basefontcolor) {
		this.basefontcolor = basefontcolor;
	}

	public String getLinecolor() {
		return linecolor;
	}

	public void setLinecolor(String linecolor) {
		this.linecolor = linecolor;
	}

	public String getLinealpha() {
		return linealpha;
	}

	public void setLinealpha(String linealpha) {
		this.linealpha = linealpha;
	}

	public String getRotatevalues() {
		return rotatevalues;
	}

	public void setRotatevalues(String rotatevalues) {
		this.rotatevalues = rotatevalues;
	}

	public String getValueposition() {
		return valueposition;
	}

	public void setValueposition(String valueposition) {
		this.valueposition = valueposition;
	}

	public String getLinethickness() {
		return linethickness;
	}

	public void setLinethickness(String linethickness) {
		this.linethickness = linethickness;
	}

	public String getLabelstep() {
		return labelstep;
	}

	public void setLabelstep(String labelstep) {
		this.labelstep = labelstep;
	}
}
