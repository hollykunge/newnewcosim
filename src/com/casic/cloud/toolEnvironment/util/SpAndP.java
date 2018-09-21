package com.casic.cloud.toolEnvironment.util;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SpAndP {
	private JScrollPane sp;
	private JPanel p;
	private int count = 0;
	public SpAndP(JScrollPane sp,JPanel p) {
		this.sp = sp;
		this.p = p;
	}
	public JScrollPane getSp() {
		return sp;
	}
	public void setSp(JScrollPane sp) {
		this.sp = sp;
	}
	public JPanel getP() {
		return p;
	}
	public void setP(JPanel p) {
		this.p = p;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
