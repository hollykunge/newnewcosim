package com.hotent.platform.service.bpm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 流程节点用户设置算法选择器
 * @author Raise
 *
 */
public class  BpmNodeUserCalculationSelector {
	
	private LinkedHashMap<Short,IBpmNodeUserCalculation > bpmNodeUserCalculations=new LinkedHashMap<Short, IBpmNodeUserCalculation>();

	public LinkedHashMap<Short, IBpmNodeUserCalculation> getBpmNodeUserCalculation() {
		return bpmNodeUserCalculations;
	}

	public void setBpmNodeUserCalculation(
			LinkedHashMap<Short, IBpmNodeUserCalculation> bpmNodeUserCalculations) {
		this.bpmNodeUserCalculations = bpmNodeUserCalculations;
	}
	
	/**
	 * 根据算法的Key，取得具体算法的对象
	 * @param i
	 * @return
	 */
	public IBpmNodeUserCalculation getByKey(Short i){
		return this.bpmNodeUserCalculations.get(i);
	}
	
	/**
	 * 获取所有的用户设置类型
	 * @return
	 */
	public Map<Short,String> getNodeUserSetType(){
		Map<Short,String> map = new HashMap<Short, String>();
		Set<Entry<Short, IBpmNodeUserCalculation>>  entries = bpmNodeUserCalculations.entrySet();
		for(Entry<Short, IBpmNodeUserCalculation> entry:entries){
			map.put(entry.getKey(), entry.getValue().getTitle());
		}
		return map;
	}
	
}
