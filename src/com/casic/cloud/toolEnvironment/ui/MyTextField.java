package com.casic.cloud.toolEnvironment.ui;

import java.io.Serializable;

import javax.swing.JTextField;

public class MyTextField extends JTextField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyTextField(){
		super();
	}
	public MyTextField(String text){
		super(text);
	}

}
