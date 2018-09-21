package com.hotent.core.bpm.util;

/**
 * 流程使用的常量。
 * @author ray
 *
 */
public class BpmConst {
	
	/**
	 * 流程启动用户变量名
	 */
	public static final String START_USER_ID="startUserId";
	/**
	 * 开始变量
	 */
	public static final String StartUser="startUser";
	/**
	 * 上一个用户变量。
	 */
	public static final String PrevUser="prevUser";
	/**
	 * 开始
	 */
	public static final String StartEvent="start";
	
	public static final String EndEvent="end";
	
	public static final String CreateEvent="create";
	
	public static final String CompleteEvent="complete";
	
	public static final String AssignmentEvent="assignment";
	
	public static final String PROCESS_EXT_VARNAME="outPassVars";
	
	public static final String PROCESS_INNER_VARNAME="innerPassVars";
	
	public static final String FLOW_RUN_SUBJECT="subject_";
	
	public static final String FLOW_INFORM_TYPE="informType";
	/**
	 * 是否为外部的调用。
	 */
	public static final String IS_EXTERNAL_CALL="isExtCall";
	/**
	 * 1.事件前置脚本
	 */
	public static final Integer StartScript=1;
	
	/**
	 * 2.事件后置脚本
	 */
	public static final Integer EndScript=2;
	
	/**
	 * 3.脚本节点
	 */
	public static final Integer ScriptNodeScript=3;
	/**
	 * 4.分配脚本
	 */
	public static final Integer AssignScript=4;
	
	/**
	 * 节点的审批状态值变量名
	 */
	public static final String NODE_APPROVAL_STATUS="approvalStatus";
	/**
	 * 节点的审批意见变量名
	 */
	public static final String NODE_APPROVAL_CONTENT="approvalContent";
	
	/**
	 * 流程驳回。
	 */
	public static final Integer TASK_BACK=1;
	
	/**
	 * 流程驳回到发起人。
	 */
	public static final Integer TASK_BACK_TOSTART=2;
	
	
	
	/**
	 * 在线的表单
	 */
	public static final Short OnLineForm=0;
	
	/**
	 * 外部表单
	 */
	public static final Short UrlForm=1;
	
	/**
	 * 表单主键PK正则表达式。
	 */
	public static final String FORM_PK_REGEX="\\{pk\\}";
	/**
	 * 流程主键key
	 */
	public static final String FLOW_BUSINESSKEY="businessKey";
	
	/**
	 * 流程运行ID。
	 */
	public static final String FLOW_RUNID="flowRunId";
	
	/**
	 * 会签人员人员列表
	 */
	public static final String SIGN_USERIDS="signUsers";
	
	/**
	 * 多实例子流程节点人员列表
	 */
	public static final String SUBPRO_MULTI_USERIDS="subAssignIds";
	
	/**
	 * 多实例外部子流程节点人员列表
	 */
	public static final String SUBPRO_EXT_MULTI_USERIDS="subExtAssignIds";
	
	/**
	 * 
	 */
	 public static final String TABLE_AND_PK = "HT_tableAndPk";
}
