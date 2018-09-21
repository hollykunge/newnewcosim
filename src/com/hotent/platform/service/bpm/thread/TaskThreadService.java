package com.hotent.platform.service.bpm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import com.hotent.core.bpm.model.ProcessCmd;

/**
 * 流程任务执行线程绑定服务类
 * @author csx
 *
 */
public class TaskThreadService {
	//任务令牌服务类，一般是用于任务分发时，传递分发的令牌
	private static ThreadLocal<String> forkTaskTokenLocal=new ThreadLocal<String>();
	//用于任务完成后，收集产生的新任务
	private static ThreadLocal<List<Task>> newTasksLocal= new ThreadLocal<List<Task>>();
	//前面节点的执行用户。
	private static ThreadLocal<String> preUserLocal=new ThreadLocal<String>();
	
	private static ThreadLocal<ProcessCmd> processCmdLocal=new ThreadLocal<ProcessCmd>();
	//外部子流程调用,存放子流程实例id列表。
	private static ThreadLocal<List<String>> extSubProcess=new ThreadLocal<List<String>>();
	
	private static ThreadLocal<Object> objLocal=new ThreadLocal<Object>();
	
	private static ThreadLocal<Map<String,Object>> varsLocal=new ThreadLocal<Map<String,Object>>();
	
	public static void addTask(Task taskEntity){
		List<Task> taskList=newTasksLocal.get();
		if(taskList==null){
			taskList=new ArrayList<Task>();
			newTasksLocal.set(taskList);
		}
		taskList.add(taskEntity);
	}
	
	public static List<Task> getNewTasks(){
		List<Task> list=newTasksLocal.get();
		return list;
	}
	
	public static String getToken(){
		return forkTaskTokenLocal.get();
	}
	
	public static void setToken(String token){
		forkTaskTokenLocal.set(token);
	}
	
	public static void clearToken(){
		forkTaskTokenLocal.remove();
	}
	
	public static void clearNewTasks(){
		newTasksLocal.remove();
	}
	
	public static void setPreTaskUser(String user){
		preUserLocal.set(user);
	}
	
	public static void cleanTaskUser(){
		preUserLocal.remove();
	}
	
	public static String  getPreTaskUser(){
		if( preUserLocal.get()==null){
			return "";
		}
		return preUserLocal.get();
	}
	
	public static ProcessCmd getProcessCmd(){
		return processCmdLocal.get();
	}
	
	public static void setProcessCmd(ProcessCmd processCmd){
		processCmdLocal.set(processCmd);
	}
	
	public static void cleanProcessCmd(){
		processCmdLocal.remove();
	}

	public static List<String> getExtSubProcess() {
		return extSubProcess.get();
	}

	public static void setExtSubProcess(List<String> extSubProcessList) {
		extSubProcess.set(extSubProcessList);
	}
	
	/**
	 * 添加变量。
	 * @param key
	 * @param value
	 */
	public static void addExtSubProcess(String instanceId) {
		List<String> list=null;
		if(extSubProcess.get()==null){
			list=new ArrayList<String>();
			list.add(instanceId);
			extSubProcess.set(list);
		}
		else{
			extSubProcess.get().add(instanceId);
		}
	}
	
	public static void cleanExtSubProcess() {
		extSubProcess.remove();
	}
	
	public static void setObject(Object obj){
		objLocal.set(obj);
	}
	
	public static Object getObject(){
		return objLocal.get();
	}
	
	public static void removeObject(){
		 objLocal.remove();
	}
	
	public static void setVariables(Map<String,Object> vars_){
		varsLocal.set(vars_);
	}
	
	public static Map<String,Object> getVariables(){
		return varsLocal.get();
	}
	
	public static void clearVariables(){
		varsLocal.remove();
	}
	
	/**
	 * 清除所有的流程变量。
	 */
	public static void clearAll(){
		forkTaskTokenLocal.remove();
		newTasksLocal.remove();
		preUserLocal.remove();
		processCmdLocal.remove();
		extSubProcess.remove();
		objLocal.remove();
		varsLocal.remove();
	}
}
