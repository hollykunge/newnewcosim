<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign package=table.variable.package>
<#assign tabComment=table.tableDesc>
<#assign subtables=table.subTableList>
<#assign fieldList=table.fieldList>
<#function getJavaType dataType>
<#assign dbtype=dataType?lower_case>
<#assign rtn>
<#if  dbtype=="number" >
Long
<#elseif (dbtype=="varchar"||dbtype=="clob")  >
String
<#elseif (dbtype=="date")>
java.util.Date
</#if></#assign>
 <#return rtn?trim>
</#function>
package com.hotent.${system}.model.${package};

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:${tabComment} Model对象
 */
public class ${class} extends BaseModel
{

	//主键
	protected Long id;
	//当前用户ID
	protected Long curentUserId;
	//当前用户组织ID
	protected Long curentOrgId;
	//流程运行ID
	protected Long flowRunId;
	//流程定义ID
	protected Long defId;
	//创建时间
	protected java.util.Date createTime;
	//外键
	<#if table.isMain!=1>
	protected Long refId;
	</#if>
	<#list fieldList as field>
	//${field.fieldDesc}
	protected ${getJavaType(field.fieldType)}  ${field.fieldName};
	</#list>
<#if subtables?size!=0>
	<#list subtables as subtable>
	<#assign vars=subtable.variable>
	//${table.tabComment}列表
	protected List<${vars.class}> ${vars.classVar}List=new ArrayList<${vars.class}>();
	</#list>
	</#if>
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	<#if table.isMain!=1>
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	</#if>
	public Long getCurentUserId() {
		return curentUserId;
	}
	public void setCurentUserId(Long curentUserId) {
		this.curentUserId = curentUserId;
	}
	public Long getCurentOrgId() {
		return curentOrgId;
	}
	public void setCurentOrgId(Long curentOrgId) {
		this.curentOrgId = curentOrgId;
	}
	public Long getFlowRunId() {
		return flowRunId;
	}
	public void setFlowRunId(Long flowRunId) {
		this.flowRunId = flowRunId;
	}
	public Long getDefId() {
		return defId;
	}
	public void setDefId(Long defId) {
		this.defId = defId;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
<#list fieldList as field>
	public void set${field.fieldName?cap_first}(${getJavaType(field.fieldType)} ${field.fieldName}) 
	{
		this.${field.fieldName} = ${field.fieldName};
	}
	/**
	 * 返回 ${field.fieldDesc}
	 * @return
	 */
	public ${getJavaType(field.fieldType)} get${field.fieldName?cap_first}() 
	{
		return this.${field.fieldName};
	}
</#list>
<#if subtables?exists && subtables?size!=0>
<#list subtables as subtable>
<#assign vars=subtable.variable>
	public void set${vars.classVar?cap_first}List(List<${vars.class}> ${vars.classVar}List) 
	{
		this.${vars.classVar}List = ${vars.classVar}List;
	}
	/**
	 * 返回 ${table.tabComment}列表
	 * @return
	 */
	public List<${vars.class}> get${vars.classVar?cap_first}List() 
	{
		return this.${vars.classVar}List;
	}
</#list>
</#if>

   
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ${class})) 
		{
			return false;
		}
		${class} rhs = (${class}) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.curentUserId, rhs.curentUserId)
		.append(this.curentOrgId, rhs.curentOrgId)
		.append(this.flowRunId, rhs.flowRunId)
		.append(this.defId, rhs.defId)
		.append(this.createTime, rhs.createTime)
		<#list fieldList as field>
		.append(this.${field.fieldName}, rhs.${field.fieldName})
		</#list>
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.curentUserId)
		.append(this.curentOrgId)
		.append(this.flowRunId)
		.append(this.defId)
		.append(this.createTime)
		<#list fieldList as field>
		.append(this.${field.fieldName}) 
		</#list>
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("curentUserId",this.curentUserId)
		.append("curentOrgId",this.curentOrgId)
		.append("flowRunId",this.flowRunId)
		.append("defId",this.defId)
		.append("createTime",this.createTime)
		<#list fieldList as field>
		.append("${field.fieldName}", this.${field.fieldName}) 
		</#list>
		.toString();
	}

}