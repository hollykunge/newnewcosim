package com.hotent.platform.model.biz;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:业务定义，如邀标采购这样的大业务。 Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 */
public class BizDef extends BaseModel
{
	/**
	 * 激活状态
	 */
	public static final Short STATUS_ENABLE						=1;
	/**
	 * 未激活状态
	 */
	public static final Short STATUS_DISABLE					=0;
	
	/**
	 * 主版本
	 */
	public static final Short MAIN_VERSION_Y						=1;
	public static final Short MAIN_VERSION_N						=2;
	// 业务Id
	protected Long  bizDefId;
	// 业务编号
	protected String  bizDefNo;
	// 业务名称
	protected String  defName;
	// 描述
	protected String  defDescription;
	// 版本号
	protected Short  versionNo=1;
	// 主版本
	protected Short  isMain=MAIN_VERSION_Y;
	// 状态:
	protected Short  status=STATUS_ENABLE;
	// 创建人
	protected Long  createBy;
	// 创建日期
	protected java.util.Date  createTime;
	// 更新人
	protected Long  updateBy;
	// 更新日期
	protected java.util.Date  updateTime;
	//业务环节列表
	protected List<BizDefSegment> bizDefSegmentList=new ArrayList<BizDefSegment>();
	public void setBizDefId(Long bizDefId) 
	{
		this.bizDefId = bizDefId;
	}
	/**
	 * 返回 业务Id
	 * @return
	 */
	public Long getBizDefId() 
	{
		return this.bizDefId;
	}
	public void setBizDefNo(String bizDefNo) 
	{
		this.bizDefNo = bizDefNo;
	}
	/**
	 * 返回 业务编号
	 * @return
	 */
	public String getBizDefNo() 
	{
		return this.bizDefNo;
	}
	public void setDefName(String defName) 
	{
		this.defName = defName;
	}
	/**
	 * 返回 业务名称
	 * @return
	 */
	public String getDefName() 
	{
		return this.defName;
	}
	public void setDefDescription(String defDescription) 
	{
		this.defDescription = defDescription;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDefDescription() 
	{
		return this.defDescription;
	}
	public void setVersionNo(Short versionNo) 
	{
		this.versionNo = versionNo;
	}
	/**
	 * 返回 版本号
	 * @return
	 */
	public Short getVersionNo() 
	{
		return this.versionNo;
	}
	public void setIsMain(Short isMain) 
	{
		this.isMain = isMain;
	}
	/**
	 * 返回 主版本
	 * @return
	 */
	public Short getIsMain() 
	{
		return this.isMain;
	}
	public void setStatus(Short status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态:
	 * @return
	 */
	public Short getStatus() 
	{
		return this.status;
	}
	public void setCreateBy(Long createBy) 
	{
		this.createBy = createBy;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public Long getCreateBy() 
	{
		return this.createBy;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建日期
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setUpdateBy(Long updateBy) 
	{
		this.updateBy = updateBy;
	}
	/**
	 * 返回 更新人
	 * @return
	 */
	public Long getUpdateBy() 
	{
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date updateTime) 
	{
		this.updateTime = updateTime;
	}
	/**
	 * 返回 更新日期
	 * @return
	 */
	public java.util.Date getUpdateTime() 
	{
		return this.updateTime;
	}
	public void setBizDefSegmentList(List<BizDefSegment> bizDefSegmentList) 
	{
		this.bizDefSegmentList = bizDefSegmentList;
	}
	/**
	 * 返回 业务环节列表
	 * @return
	 */
	public List<BizDefSegment> getBizDefSegmentList() 
	{
		return this.bizDefSegmentList;
	}

	/**
	 * 获取第一个业务环节
	 * @return
	 */
	public BizDefSegment getFirstDefSegment(){
		if(bizDefSegmentList==null){
			return null;
		}
		for(BizDefSegment segment:bizDefSegmentList){
			if(segment.sortOrder==1){
				return segment;
			}
		}
		return null;
	}
	
   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BizDef)) 
		{
			return false;
		}
		BizDef rhs = (BizDef) object;
		return new EqualsBuilder()
		.append(this.bizDefId, rhs.bizDefId)
		.append(this.bizDefNo, rhs.bizDefNo)
		.append(this.defName, rhs.defName)
		.append(this.defDescription, rhs.defDescription)
		.append(this.versionNo, rhs.versionNo)
		.append(this.isMain, rhs.isMain)
		.append(this.status, rhs.status)
		.append(this.createBy, rhs.createBy)
		.append(this.createTime, rhs.createTime)
		.append(this.updateBy, rhs.updateBy)
		.append(this.updateTime, rhs.updateTime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.bizDefId) 
		.append(this.bizDefNo) 
		.append(this.defName) 
		.append(this.defDescription) 
		.append(this.versionNo) 
		.append(this.isMain) 
		.append(this.status) 
		.append(this.createBy) 
		.append(this.createTime) 
		.append(this.updateBy) 
		.append(this.updateTime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("bizDefId", this.bizDefId) 
		.append("bizDefNo", this.bizDefNo) 
		.append("defName", this.defName) 
		.append("defDescription", this.defDescription) 
		.append("versionNo", this.versionNo) 
		.append("isMain", this.isMain) 
		.append("status", this.status) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}
   
  

}