package com.hotent.platform.model.biz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:BIZ_RU_VARIABLE Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-20 14:29:43
 */
public class BizRuVariable extends BaseModel
{
	// VAR_ID
	protected Long  varId;
	// VAR_TYPE
	protected String  varType;
	// VAR_NAME
	protected String  varName;
	// BIZ_INST_ID
	protected Long  bizInstId;
	// BIZ_INST_SEG_ID
	protected Long  bizInstSegId;
	// CONTENT
	protected byte[]  content;
	public void setVarId(Long varId) 
	{
		this.varId = varId;
	}
	/**
	 * 返回 VAR_ID
	 * @return
	 */
	public Long getVarId() 
	{
		return this.varId;
	}
	public void setVarType(String varType) 
	{
		this.varType = varType;
	}
	/**
	 * 返回 VAR_TYPE
	 * @return
	 */
	public String getVarType() 
	{
		return this.varType;
	}
	public void setVarName(String varName) 
	{
		this.varName = varName;
	}
	/**
	 * 返回 VAR_NAME
	 * @return
	 */
	public String getVarName() 
	{
		return this.varName;
	}
	public void setBizInstId(Long bizInstId) 
	{
		this.bizInstId = bizInstId;
	}
	/**
	 * 返回 BIZ_INST_ID
	 * @return
	 */
	public Long getBizInstId() 
	{
		return this.bizInstId;
	}
	public void setBizInstSegId(Long bizInstSegId) 
	{
		this.bizInstSegId = bizInstSegId;
	}
	/**
	 * 返回 BIZ_INST_SEG_ID
	 * @return
	 */
	public Long getBizInstSegId() 
	{
		return this.bizInstSegId;
	}
	public void setContent(byte[] content) 
	{
		this.content = content;
	}
	
	public void setValue(Object value) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(value);
		this.content = byteArrayOutputStream.toByteArray();
	}
	
	public Object getValue() throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.content);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		return objectInputStream.readObject();
	}

	/**
	 * 返回 CONTENT
	 * @return
	 */
	public byte[] getContent() 
	{
		return this.content;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BizRuVariable)) 
		{
			return false;
		}
		BizRuVariable rhs = (BizRuVariable) object;
		return new EqualsBuilder()
		.append(this.varId, rhs.varId)
		.append(this.varType, rhs.varType)
		.append(this.varName, rhs.varName)
		.append(this.bizInstId, rhs.bizInstId)
		.append(this.bizInstSegId, rhs.bizInstSegId)
		.append(this.content, rhs.content)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.varId) 
		.append(this.varType) 
		.append(this.varName) 
		.append(this.bizInstId) 
		.append(this.bizInstSegId) 
		.append(this.content) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("varId", this.varId) 
		.append("varType", this.varType) 
		.append("varName", this.varName) 
		.append("bizInstId", this.bizInstId) 
		.append("bizInstSegId", this.bizInstSegId) 
		.append("content", this.content) 
		.toString();
	}
   
  

}