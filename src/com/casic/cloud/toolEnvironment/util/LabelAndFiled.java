package com.casic.cloud.toolEnvironment.util;

import java.io.Serializable;
import java.util.List;

import com.casic.cloud.toolEnvironment.ui.MyLabel;
import com.casic.cloud.toolEnvironment.ui.MyTextField;


public class LabelAndFiled implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyLabel label;
	private MyTextField field;

	public LabelAndFiled(MyLabel label, MyTextField field) {
		this.label = label;
		this.field = field;
	}

	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		MyLabel newLabel = new MyLabel();
		MyTextField newField = new MyTextField();
		newLabel.setToolTipText(this.label.getToolTipText());
		newLabel.setText(this.label.getText());
		newField.setText(this.field.getText());
		LabelAndFiled labelAndFiled = new LabelAndFiled(newLabel, newField);
		return labelAndFiled;
		
	}
	/**
	 * 复制一个list
	 * @param source
	 * @param dest
	 */
	public static void copyList(List<LabelAndFiled> source,List<LabelAndFiled> dest){
		if(source!=null)
		for(int i =  0;i<source.size();i++){
			LabelAndFiled labelAndFiled = source.get(i);
			try {
				dest.add((LabelAndFiled) labelAndFiled.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public MyLabel getLabel() {
		return label;
	}

	public void setLabel(MyLabel label) {
		this.label = label;
	}

	public MyTextField getField() {
		return field;
	}

	public void setField(MyTextField field) {
		this.field = field;
	}

}
