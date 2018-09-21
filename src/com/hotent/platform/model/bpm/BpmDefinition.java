package com.hotent.platform.model.bpm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.xml.util.XmlDateAdapter;

/**
 * 对象功能:流程定义扩展 Model对象
 * 开发公司:
 * 开发人员:csx 
 * 创建时间:2011-11-30 15:41:24
 */
@XmlRootElement(name = "bpmDefinition")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmDefinition extends BaseModel implements Cloneable
{
	/**
	 * 流程定义规则
	 */
	public static final String DefaultSubjectRule="{流程标题:title}-{发起人:startUser}-{发起时间:startTime}";
	/**
	 * 为主流程 main=1
	 */
	public final static Short MAIN=1;
	/**
	 * 非主流程，即为历史版本   main=0;
	 */
	public final static Short NOT_MAIN=0;
	
	/**
	 * 可用
	 */
	public final static Short STATUS_DEPLOYED=1;
	/**
	 * 禁用
	 */
	public final static Short STATUS_NOTDEPLOYED=0;
	/**
	 * 挂起
	 */
	public final static Short STATUS_SUSPEND=-1;
	
	//表名称
	public final static String TABLE_NAME="bpm_definition";
	/**
	 * 禁用
	 */
	public final static Short DISABLEStATUS_DA=1;
	/**
	 * 启用
	 */
    public final static Short DISABLEStATUS_EN=0;
	// 流程定义ID
	@XmlAttribute
	protected Long defId;
	// 分类ID
	@XmlAttribute
	protected Long typeId;
	// 流程标题
	@XmlAttribute
	protected String subject;
	// 流程定义Key
	@XmlAttribute
	protected String defKey;
	// 任务标题生成规则
	@XmlAttribute
	protected String taskNameRule;
	// 流程描述
	@XmlAttribute
	protected String descp;
	// 创建时间
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	@XmlAttribute
	protected java.util.Date createtime;
	// 流程状态
	@XmlAttribute
	protected Short status;
	// 流程定义XML(设计器)
	@XmlAttribute
	protected String defXml;
	// activiti流程定义ID
	@XmlAttribute
	protected Long actDeployId;
	// act流程定义Key
	@XmlAttribute
	protected String actDefKey;
	// actDefId
	@XmlAttribute
	protected String actDefId;
	// createBy
	@XmlAttribute
	protected Long createBy;
	// updateBy
	@XmlAttribute
	protected Long updateBy;
	// reason
	@XmlAttribute
	protected String reason;
	// versionNo
	@XmlAttribute
	protected Integer versionNo;
	// 隶属主定义ID
	@XmlAttribute
	protected Long parentDefId;
	// 是否为主版本
	@XmlAttribute
	protected Short isMain;
	// updatetime
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	@XmlAttribute
	protected java.util.Date updatetime;
	
	// 流程分类名称
	protected String typeName;
	//需要启动表单（默认为1）
	@XmlAttribute
	protected Short needStartForm=1;
	
	//跳过第一个任务节点。
	@XmlAttribute
	protected Short toFirstNode=0;
	
	//流程启动时  是否可以选择下一步的执行人
	@XmlAttribute
	protected Short showFirstAssignee=0;
	
	//流程  发起人节点
	protected String startFirstNode;
	
	//可以选择条件同步路径的节点
	protected String canChoicePath;
	
	//可选择路径的节点MAP
	private Map canChoicePathNodeMap;
	
	//流程定义是否调用外部表单
	private Short isUseOutForm=0;
	
	protected String formDetailUrl;
	
	//流程启用或禁止，0启动，1禁止
    protected Short disableStatus=0;
    
	public Map getCanChoicePathNodeMap() {
		if(canChoicePathNodeMap==null){
			Map map = new HashMap();		
			if(StringUtil.isEmpty(this.canChoicePath)){
				canChoicePathNodeMap = map;
				return canChoicePathNodeMap;
			}
			Pattern regex = Pattern.compile("(\\w+):(\\w+)");
			Matcher regexMatcher = regex.matcher(this.canChoicePath);
			while (regexMatcher.find()) {
				map.put(regexMatcher.group(1), regexMatcher.group(2));
			}		
			canChoicePathNodeMap = map;
		}
		return canChoicePathNodeMap;
	}
	
	public void setDefId(Long defId)
	{
		this.defId = defId;
	}
	
	

	public Short getIsUseOutForm() {
		return isUseOutForm;
	}

	public void setIsUseOutForm(Short isUseOutForm) {
		this.isUseOutForm = isUseOutForm;
	}

	/**
	 * 返回 流程定义ID
	 * 
	 * @return
	 */
	public Long getDefId()
	{
		return defId;
	}

	public void setTypeId(Long typeId)
	{
		this.typeId = typeId;
	}
	
	public String getFormDetailUrl() {
		return formDetailUrl;
	}

	public void setFormDetailUrl(String formDetailUrl) {
		this.formDetailUrl = formDetailUrl;
	}

	/**
	 * 返回 分类ID
	 * 
	 * @return
	 */
	public Long getTypeId()
	{
		return typeId;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * 返回 流程标题
	 * 
	 * @return
	 */
	public String getSubject()
	{
		return subject;
	}

	public void setDefKey(String defKey)
	{
		this.defKey = defKey;
	}

	/**
	 * 返回 流程定义Key
	 * 
	 * @return
	 */
	public String getDefKey()
	{
		return defKey;
	}

	public void setTaskNameRule(String taskNameRule)
	{
		this.taskNameRule = taskNameRule;
	}

	/**
	 * 返回 任务标题生成规则
	 * 
	 * @return
	 */
	public String getTaskNameRule()
	{
		return taskNameRule;
	}

	public void setDescp(String descp)
	{
		this.descp = descp;
	}
	
	/**
	 * 返回流程发起人节点
	 * @return
	 */
	public String getStartFirstNode() {
		return startFirstNode;
	}

	public void setStartFirstNode(String startFirstNode) {
		this.startFirstNode = startFirstNode;
	}

	/**
	 * 返回 流程描述
	 * 
	 * @return
	 */
	public String getDescp()
	{
		return descp;
	}

	public void setCreatetime(java.util.Date createtime)
	{
		this.createtime = createtime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreatetime()
	{
		return createtime;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}

	/**
	 * 返回 流程状态
	 * 
	 * @return
	 */
	public Short getStatus()
	{
		return status;
	}

	public void setDefXml(String defXml)
	{
		this.defXml = defXml;
	}

	/**
	 * 返回 流程定义XML(设计器)
	 * 
	 * @return
	 */
	public String getDefXml()
	{
		return defXml;
	}

	public void setActDeployId(Long actDeployId)
	{
		this.actDeployId = actDeployId;
	}

	/**
	 * 返回 activiti流程定义ID
	 * 
	 * @return
	 */
	public Long getActDeployId()
	{
		return actDeployId;
	}

	public void setActDefKey(String actDefKey)
	{
		this.actDefKey = actDefKey;
	}

	/**
	 * 返回 act流程定义Key
	 * 
	 * @return
	 */
	public String getActDefKey()
	{
		return actDefKey;
	}

	public void setActDefId(String actDefId)
	{
		this.actDefId = actDefId;
	}

	/**
	 * 返回 actDefId
	 * 
	 * @return
	 */
	public String getActDefId()
	{
		return actDefId;
	}

	public void setCreateBy(Long createBy)
	{
		this.createBy = createBy;
	}

	/**
	 * 返回 createBy
	 * 
	 * @return
	 */
	public Long getCreateBy()
	{
		return createBy;
	}

	public void setUpdateBy(Long updateBy)
	{
		this.updateBy = updateBy;
	}

	/**
	 * 返回 updateBy
	 * 
	 * @return
	 */
	public Long getUpdateBy()
	{
		return updateBy;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * 返回 reason
	 * 
	 * @return
	 */
	public String getReason()
	{
		return reason;
	}

	public void setVersionNo(Integer versionNo)
	{
		this.versionNo = versionNo;
	}

	/**
	 * 返回 versionNo
	 * 
	 * @return
	 */
	public Integer getVersionNo()
	{
		return versionNo;
	}

	public void setParentDefId(Long parentDefId)
	{
		this.parentDefId = parentDefId;
	}

	/**
	 * 返回 隶属主定义ID
	 * 
	 * @return
	 */
	public Long getParentDefId()
	{
		return parentDefId;
	}

	public void setIsMain(Short isMain)
	{
		this.isMain = isMain;
	}

	/**
	 * 返回 是否为主版本
	 * 
	 * @return
	 */
	public Short getIsMain()
	{
		return isMain;
	}

	public void setUpdatetime(java.util.Date updatetime)
	{
		this.updatetime = updatetime;
	}

	/**
	 * 返回 updatetime
	 * 
	 * @return
	 */
	public java.util.Date getUpdatetime()
	{
		return updatetime;
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}
	

	public Short getShowFirstAssignee() {
		return showFirstAssignee;
	}

	public void setShowFirstAssignee(Short showFirstAssignee) {
		this.showFirstAssignee = showFirstAssignee;
	}

	public Short getToFirstNode() {
		return toFirstNode;
	}

	public void setToFirstNode(Short toFirstNode) {
		this.toFirstNode = toFirstNode;
	}

	public Short getNeedStartForm() {
		return needStartForm;
	}

	public void setNeedStartForm(Short needStartForm) {
		this.needStartForm = needStartForm;
	}

	public String getCanChoicePath() {
		return canChoicePath;
	}

	public void setCanChoicePath(String canChoicePath) {
		this.canChoicePath = canChoicePath;
	}
	
	public Short getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(Short disableStatus) {
		this.disableStatus = disableStatus;
	}
	
	/**
	 * 更新可以选择的路径。
	 * ORGateway1:UserTask1
	 */
	public void updateCanChoicePath(){
		if(this.canChoicePathNodeMap!=null){
			StringBuffer sb=new StringBuffer();
			Iterator iter = this.canChoicePathNodeMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				sb.append(",");
				sb.append(key);
				sb.append(":");
				sb.append(val);				
			}
			this.canChoicePath = sb.toString().replaceFirst(",", "");
		}
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object)
	{
		if (!(object instanceof BpmDefinition))
		{
			return false;
		}
		BpmDefinition rhs = (BpmDefinition) object;
		return new EqualsBuilder()
				.append(this.defId, rhs.defId)
				.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.defId)
			
				.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this)
				.append("defId", this.defId)
				.append("typeId", this.typeId)
				.append("subject", this.subject)
				.append("defKey", this.defKey)
				.append("taskNameRule", this.taskNameRule)
				.append("descp", this.descp)
				.append("createtime", this.createtime)
				.append("status", this.status)
				.append("defXml", this.defXml)
				.append("actDeployId", this.actDeployId)
				.append("actDefKey", this.actDefKey)
				.append("actDefId", this.actDefId)
				.append("createBy", this.createBy)
				.append("updateBy", this.updateBy)
				.append("reason", this.reason)
				.append("versionNo", this.versionNo)
				.append("parentDefId", this.parentDefId)
				.append("isMain", this.isMain)
				.append("updatetime", this.updatetime)
				.toString();
	}
	
	
	public Object clone()
	{
		BpmDefinition obj=null;
		try{
			obj=(BpmDefinition)super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	

}