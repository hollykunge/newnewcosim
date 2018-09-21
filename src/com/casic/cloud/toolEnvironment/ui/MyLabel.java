package com.casic.cloud.toolEnvironment.ui;

import java.io.Serializable;

import javax.swing.JLabel;

public class MyLabel extends JLabel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyLabel(String text){
		super(text);
	}
	public MyLabel(){
		super();
	}

}
