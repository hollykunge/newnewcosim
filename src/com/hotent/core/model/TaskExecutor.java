package com.hotent.core.model;

import java.io.Serializable;

/**
 * 任务执行人
 * @author ray
 *
 */
public class TaskExecutor implements Serializable {
	
	/**
	 * 把任务直接授权给一个或多个用户
	 */
	public static final String USER_TYPE_USER="user";
	/**
	 * 把任务直接授权给一个或多个组织
	 */
	public static final String USER_TYPE_ORG="org";
	/**
	 * 把任务直接授权给一个或多个角色
	 */
	public static final String USER_TYPE_ROLE="role";
	/**
	 * 把任务直接授权给一个或多个岗位
	 */
	public static final String USER_TYPE_POS="pos";
	/**
	 * 把任务直接授权给一个或多个岗位
	 */
	public static final String USER_TYPE_COMP="comp";
	
	/**
	 * 执行人的类型为。
	 * type:
	 * user，用户
	 * org,组织
	 * role,角色
	 * pos,岗位
	 */
	private String type="user";
	/**
	 * 执行者ID
	 */
	private String executeId="";
	
	/**
	 * 执行人。
	 */
	private String executor="";
	
	public TaskExecutor(){}
	
	public TaskExecutor(String type,String executeId,String name){
		this.type=type;
		this.executeId=executeId;
		this.executor=name;
	}
	
	/**
	 * 获取任务用户。
	 * @param executeId
	 * @return
	 */
	public static TaskExecutor getTaskUser(String executeId,String name){
		return new TaskExecutor("user",executeId,name);
	}
	
	/**
	 * 获取组织执行人。
	 * @param executeId
	 * @return
	 */
	public static TaskExecutor getTaskOrg(String executeId,String name){
		return new TaskExecutor("org",executeId, name);
	}
	
	/**
	 * 获取角色任务。
	 * @param executeId
	 * @return
	 */
	public static TaskExecutor getTaskRole(String executeId,String name){
		return new TaskExecutor("role",executeId,name);
	}
	
	/**
	 * 获取岗位。
	 * @param executeId
	 * @return
	 */
	public static TaskExecutor getTaskPos(String executeId,String name){
		return new TaskExecutor("pos",executeId,name);
	}
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	@Override
	public boolean equals(Object obj){
		if(! (obj instanceof TaskExecutor)){
			return false;
		}
		TaskExecutor tmp=(TaskExecutor)obj;
		if(this.type.equals(tmp.getType()) && this.executeId.equals(tmp.getExecuteId())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		String tmp=this.type +this.executeId;
		return tmp.hashCode();
	}
	
	

}
