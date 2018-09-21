package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:自定义表代码模版 Model对象
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2013-01-21 11:44:48
 */
public class SysCodeTemplate extends BaseModel
{
	// 主键
	protected Long  id;
	// 模版名称
	protected String  templateName;
	// 模版HTML
	protected String  html;
	// 模版备注
	protected String  memo;
	// 模版别名
	protected String  templateAlias;
	// 模版类型 1 系统模版 2自定义模版
	protected Short  templateType;
	//宏模版别名
	protected String macroAlias;
	//是否属于内容追加类型
	protected Short isAppend=0;
	//是否子表需要生成的模版文件
	protected Short isSub=0;
	//文件名称
	protected String fileName;
	//文件路径
	protected String fileDir;
	
	protected String startTag;
	
	protected String endTag;
	
	protected String insertTag;
	//是否是宏模版
	protected Short isMacro=0;
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setTemplateName(String templateName) 
	{
		this.templateName = templateName;
	}
	
	public Short getIsMacro() {
		return isMacro;
	}
	public void setIsMacro(Short isMacro) {
		this.isMacro = isMacro;
	}
	public Short getIsAppend() {
		return isAppend;
	}
	public void setIsAppend(Short isAppend) {
		this.isAppend = isAppend;
	}
	
	public String getMacroAlias() {
		return macroAlias;
	}
	public void setMacroAlias(String macroAlias) {
		this.macroAlias = macroAlias;
	}
	/**
	 * 返回 模版名称
	 * @return
	 */
	public String getTemplateName() 
	{
		return this.templateName;
	}
	public void setHtml(String html) 
	{
		this.html = html;
	}
	
	public String getInsertTag() {
		return insertTag;
	}
	public void setInsertTag(String insertTag) {
		this.insertTag = insertTag;
	}
	/**
	 * 返回 模版HTML
	 * @return
	 */
	public String getHtml() 
	{
		return this.html;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	
	public String getStartTag() {
		return startTag;
	}
	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}
	public String getEndTag() {
		return endTag;
	}
	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}
	/**
	 * 返回 模版备注
	 * @return
	 */
	public String getMemo() 
	{
		return this.memo;
	}
	public void setTemplateAlias(String templateAlias) 
	{
		this.templateAlias = templateAlias;
	}
	/**
	 * 返回 TEMPLATE_ALIAS
	 * @return
	 */
	public String getTemplateAlias() 
	{
		return this.templateAlias;
	}

   
   	public Short getTemplateType() {
		return templateType;
	}
	public void setTemplateType(Short templateType) {
		this.templateType = templateType;
	}
	
	public Short getIsSub() {
		return isSub;
	}
	public void setIsSub(Short isSub) {
		this.isSub = isSub;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysCodeTemplate)) 
		{
			return false;
		}
		SysCodeTemplate rhs = (SysCodeTemplate) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.templateName, rhs.templateName)
		.append(this.html, rhs.html)
		.append(this.memo, rhs.memo)
		.append(this.templateAlias, rhs.templateAlias)
		.append(this.templateType, rhs.templateType)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.templateName) 
		.append(this.html) 
		.append(this.memo) 
		.append(this.templateAlias) 
		.append(this.templateType) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("templateName", this.templateName) 
		.append("html", this.html) 
		.append("memo", this.memo) 
		.append("templateAlias", this.templateAlias) 
		.append("templateType", this.templateType) 
		.toString();
	}
   
	

}